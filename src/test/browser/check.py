"""Opens every page of the compiled demo in headless Firefox and checks that it works.

    python3 src/test/browser/check.py http://localhost:8000

For each entry of the menu, the page must load, every example must render something and
show its source code, and no JavaScript error may be thrown along the way. Then the extras
whose JavaScript widget lives outside the GWT widget (Select, the date pickers) are
removed from the page and added again, to check that the old widget is destroyed and the
new one keeps the value. Exits with 1 when a check fails. Talks to Firefox through Marionette, so it needs nothing but Firefox.
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


def open_page(browser, token):
    """Shows the page of the token and waits until it has loaded."""
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
    return loaded


# The live part of the example with the given heading
EXAMPLE = ("[...document.querySelectorAll('.demo-example')]"
           ".find(e => e.querySelector('h3').textContent == '%s').querySelector('.demo-live')")

# Opens every Tempus Dominus picker of the page, which creates its widget, and closes it again
OPEN_TEMPUS_PICKERS = """for (const input of document.querySelectorAll('.demo-content input')) {
        input.click();
        document.body.click();
    }"""

TEMPUS_WIDGETS = "document.querySelectorAll('.tempus-dominus-widget').length"


def check_reattach(browser, check):
    """Select and the date pickers destroy their JavaScript widget when they leave the page."""
    # Tom Select keeps its instance on the <select>; removed and added again, the select gets
    # a new one with the same options and value
    open_page(browser, 'extras/select')
    live = EXAMPLE % 'Removed and added again'
    state = ("const live = %s; const select = live.querySelector('select');"
             "return [live.querySelectorAll('.ts-wrapper').length, !!select.tomselect,"
             " select.tomselect && select.tomselect.getValue(),"
             " select.tomselect && Object.keys(select.tomselect.options).length];" % live)
    before = browser.js(state)
    browser.js("%s.querySelector('button').click();" % live)
    time.sleep(0.4)
    after = browser.js(state)
    check('extras/select: a select removed and added again has one Tom Select with its value',
          before == [1, True, 'Medium', 3] and after == before, [before, after])

    # Removed and added again, each picker keeps its date
    open_page(browser, 'extras/date-time-pickers')
    live = EXAMPLE % 'Removed and added again'
    values = "return [...%s.querySelectorAll('input')].map(i => i.value);" % live
    before = browser.js(values)
    browser.js("%s.querySelector('button').click();" % live)
    time.sleep(0.4)
    after = browser.js(values)
    check('extras/date-time-pickers: pickers removed and added again keep their date',
          len(before) == 2 and all(before) and after == before, [before, after])

    # Each Tempus Dominus picker creates its widget in <body>, outside the page; leaving the
    # page and coming back must not leave the old widgets behind
    browser.js(OPEN_TEMPUS_PICKERS)
    first = browser.js('return ' + TEMPUS_WIDGETS)
    open_page(browser, 'general/setup')
    left = browser.js('return ' + TEMPUS_WIDGETS)
    open_page(browser, 'extras/date-time-pickers')
    browser.js(OPEN_TEMPUS_PICKERS)
    again = browser.js('return ' + TEMPUS_WIDGETS)
    check('extras/date-time-pickers: leaving the page destroys its Tempus Dominus widgets',
          first > 0 and left == 0 and again == first, [first, left, again])

    # Tom Select's dropdown lives inside its wrapper: one wrapper per select, also after
    # leaving the page and coming back
    open_page(browser, 'general/setup')
    open_page(browser, 'extras/select')
    counts = browser.js("return [document.querySelectorAll('.demo-content select').length,"
                        " document.querySelectorAll('.ts-wrapper').length]")
    check('extras/select: one Tom Select per select after coming back to the page',
          counts[0] > 0 and counts[0] == counts[1], counts)


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
            loaded = open_page(browser, token)
            examples = browser.js("""const frame = document.querySelector('.demo-content iframe');
                const root = frame ? frame.contentDocument : document;
                return [...root.querySelectorAll('.demo-example')].map(e => [
                e.querySelector('h3').textContent,
                e.querySelector('.demo-live').children.length > 0
                    && e.querySelector('.demo-live').getBoundingClientRect().height > 10,
                [...e.querySelectorAll('.demo-sources code')].map(c => c.textContent.length)])""")
            broken = [e[0] for e in examples if not e[1] or not e[2] or 0 in e[2]]
            check('%s: %d examples render with their code' % (token, len(examples)), loaded and not broken, broken or '')
        check_reattach(browser, check)
        errors = browser.js('return window.__errors')
        check('no JavaScript errors', errors == [], errors)
    finally:
        browser.close()
    print('%d pages, %d failures' % (len(pages), len(failures)))
    return 1 if failures else 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1].rstrip('/')))
