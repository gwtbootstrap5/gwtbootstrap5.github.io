"""Opens every page of the compiled demo in headless Firefox and checks that it works.

    python3 src/test/browser/check.py http://localhost:8000

For each entry of the menu, the page must load, every example must render something and
show its source code, and no JavaScript error may be thrown along the way. Exits with 1
when a check fails. Talks to Firefox through Marionette, so it needs nothing but Firefox.
"""
import json
import socket
import subprocess
import sys
import tempfile
import time

MARIONETTE_PORT = 2828


class Marionette:
    def __init__(self):
        self.firefox = subprocess.Popen(
            ['firefox', '--headless', '--marionette', '--no-remote', '--profile', tempfile.mkdtemp()],
            stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        for _ in range(120):
            try:
                self.socket = socket.create_connection(('127.0.0.1', MARIONETTE_PORT))
                break
            except OSError:
                time.sleep(0.5)
        else:
            raise RuntimeError('Firefox did not open Marionette')
        self.stream = self.socket.makefile('rb')
        self.message_id = 0
        self._receive()
        self.command('WebDriver:NewSession', {})

    def _receive(self):
        length = b''
        while (char := self.stream.read(1)) != b':':
            length += char
        return json.loads(self.stream.read(int(length)))

    def command(self, name, params):
        self.message_id += 1
        data = json.dumps([0, self.message_id, name, params]).encode()
        self.socket.sendall(str(len(data)).encode() + b':' + data)
        response = self._receive()
        if response[2]:
            raise RuntimeError(response[2])
        return response[3]

    def js(self, script):
        return self.command('WebDriver:ExecuteScript', {'script': script, 'args': []})['value']

    def wait(self, condition, timeout=20):
        end = time.time() + timeout
        while time.time() < end:
            if self.js('return !!(' + condition + ');'):
                return True
            time.sleep(0.2)
        return False

    def close(self):
        self.firefox.kill()


def main(base):
    failures = []

    def check(name, ok, detail=''):
        print(('PASS ' if ok else 'FAIL ') + name + (' ' + str(detail) if detail != '' else ''))
        if not ok:
            failures.append(name)

    pages = []
    browser = Marionette()
    try:
        browser.command('WebDriver:SetWindowRect', {'width': 1280, 'height': 900})
        browser.command('WebDriver:Navigate', {'url': base + '/index.html'})
        check('the demo loads', browser.wait("document.querySelector('.demo-menu .nav-link')", 60))
        browser.js("window.__errors = [];"
                   "window.addEventListener('error', e => window.__errors.push(e.message));"
                   "window.addEventListener('unhandledrejection', e => window.__errors.push(String(e.reason)));")
        pages = browser.js("return [...document.querySelectorAll('.demo-menu .nav-link')]"
                           ".map(a => a.getAttribute('href').substring(1))")
        check('the menu has pages', len(pages) > 1, len(pages))
        for token in pages:
            browser.js("location.hash = '%s'" % token)
            # The shell shows a lone spinner while a page loads; some pages (Font Awesome) are an
            # iframe holding another GWT module, whose examples live in the iframe's document
            loaded = browser.wait(
                "document.querySelector('.demo-menu .nav-link.active')"
                " && document.querySelector('.demo-menu .nav-link.active').getAttribute('href') == '#%s'"
                " && !document.querySelector('.demo-content > div > .spinner-border.m-5')"
                " && (document.querySelector('.demo-content h1')"
                "     || document.querySelector('.demo-content iframe')"
                "        && document.querySelector('.demo-content iframe').contentDocument"
                "        && document.querySelector('.demo-content iframe').contentDocument.querySelector('.demo-example'))"
                % token)
            time.sleep(0.4)
            examples = browser.js("""const frame = document.querySelector('.demo-content iframe');
                const root = frame ? frame.contentDocument : document;
                return [...root.querySelectorAll('.demo-example')].map(e => [
                e.querySelector('h3').textContent,
                e.querySelector('.demo-live').children.length > 0
                    && e.querySelector('.demo-live').getBoundingClientRect().height > 10,
                [...e.querySelectorAll('.demo-sources code')].map(c => c.textContent.length)])""")
            broken = [e[0] for e in examples if not e[1] or not e[2] or 0 in e[2]]
            check('%s: %d examples render with their code' % (token, len(examples)), loaded and not broken, broken or '')
        errors = browser.js('return window.__errors')
        check('no JavaScript errors', errors == [], errors)
    finally:
        browser.close()
    print('%d pages, %d failures' % (len(pages), len(failures)))
    return 1 if failures else 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1].rstrip('/')))
