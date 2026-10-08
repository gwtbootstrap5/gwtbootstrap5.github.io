"""Opens every page of the compiled demo in headless Firefox and checks that it works.

    python3 src/test/browser/check.py http://localhost:8000

For each entry of the menu, the page must load, every example must render something and
show its source code, and no JavaScript error may be thrown along the way. Then the
components driven by Bootstrap's JavaScript, and the extras, are opened and closed the way
a user would, which the GWT tests can't do in HtmlUnit. Last, the extras whose JavaScript
widget lives outside the GWT widget (Select, the date pickers) are removed from the page
and added again, to check that the old widget is destroyed and the new one keeps the
value. Exits with 1 when a check fails. Talks to Firefox through Marionette, so it needs nothing but Firefox.
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


# Each behaviour opens a page and runs its steps in order: a script that acts the way a user
# would, then a condition that must become true. Events are dispatched from JavaScript, so
# the steps don't depend on where the elements are on the screen.
LIVE = EXAMPLE

# Bootstrap ignores a hide while the modal is still showing: wait for shown.bs.modal
WATCH_SHOWN = ("window.__shown = false;"
               "document.addEventListener('shown.bs.modal', () => window.__shown = true, {once: true});")
BEHAVIOURS = [
    ('components/offcanvas', 'the offcanvas opens and closes', [
        (LIVE % 'Basic' + ".querySelector('.btn').click()",
         "document.querySelector('#offcanvas-basic.show')"
         " && document.querySelector('.offcanvas-backdrop')"),
        ("document.querySelector('#offcanvas-basic [data-bs-dismiss=offcanvas]').click()",
         "!document.querySelector('#offcanvas-basic.show, #offcanvas-basic.showing, #offcanvas-basic.hiding')"
         " && !document.querySelector('.offcanvas-backdrop')"),
    ]),
    ('components/modal', 'the modal opens and closes', [
        (WATCH_SHOWN + LIVE % 'Basic' + ".querySelector('.btn').click()",
         "window.__shown && document.querySelector('#modal-basic.show')"
         " && document.body.classList.contains('modal-open')"),
        ("document.querySelector('#modal-basic .modal-footer [data-bs-dismiss=modal]').click()",
         "!document.querySelector('#modal-basic.show') && !document.querySelector('.modal-backdrop')"
         " && !document.body.classList.contains('modal-open')"),
    ]),
    ('components/dropdowns', 'the dropdown opens and closes', [
        (LIVE % 'Basic' + ".querySelector('[data-bs-toggle=dropdown]').click()",
         LIVE % 'Basic' + ".querySelector('.dropdown-menu.show')"
         " && " + LIVE % 'Basic' + ".querySelector('[data-bs-toggle=dropdown]').getAttribute('aria-expanded') == 'true'"),
        (LIVE % 'Basic' + ".querySelector('[data-bs-toggle=dropdown]').click()",
         "!" + LIVE % 'Basic' + ".querySelector('.dropdown-menu.show')"),
    ]),
    ('components/collapse', 'the collapse opens and closes', [
        (LIVE % 'Basic' + ".querySelector('.btn').click()",
         "document.querySelector('#collapse-basic.collapse.show')"),
        (LIVE % 'Basic' + ".querySelector('.btn').click()",
         "document.querySelector('#collapse-basic.collapse:not(.show)')"),
    ]),
    ('components/tooltips', 'the tooltip shows on hover and hides', [
        (LIVE % 'Placement' + ".querySelector('.btn').dispatchEvent(new MouseEvent('mouseover', {bubbles: true}))",
         "document.querySelector('.tooltip.show')"
         " && document.querySelector('.tooltip.show .tooltip-inner').textContent == 'Tooltip on top'"),
        (LIVE % 'Placement' + ".querySelector('.btn').dispatchEvent(new MouseEvent('mouseout', {bubbles: true}))",
         "!document.querySelector('.tooltip')"),
    ]),
    ('helpers/sticky', 'the sticky header and footer stay at the edges of the box that scrolls', [
        ("const box = " + LIVE % 'Top and bottom' + ".querySelector('.overflow-auto'); box.scrollTop = 120",
         "(() => { const box = " + LIVE % 'Top and bottom' + ".querySelector('.overflow-auto'), r = box.getBoundingClientRect();"
         " const top = box.querySelector('.sticky-top').getBoundingClientRect(),"
         " bottom = box.querySelector('.sticky-bottom').getBoundingClientRect();"
         " return box.scrollTop > 0 && Math.abs(top.top - r.top - box.clientTop) < 1"
         " && Math.abs(r.top + box.clientTop + box.clientHeight - bottom.bottom) < 1; })()"),
    ]),
    ('helpers/sticky', 'the offset keeps the label 12 px from the top until removeSticky', [
        ("const box = " + LIVE % 'An offset, from Java' + ".querySelector('.overflow-auto'); box.scrollTop = 120",
         "(() => { const box = " + LIVE % 'An offset, from Java' + ".querySelector('.overflow-auto');"
         " const label = box.querySelector('.sticky-top');"
         " return box.scrollTop > 0 && label && label.style.top == '12px'"
         " && Math.abs(label.getBoundingClientRect().top - box.getBoundingClientRect().top - box.clientTop - 12) < 1; })()"),
        (LIVE % 'An offset, from Java' + ".querySelector('.btn').click()",
         "(() => { const box = " + LIVE % 'An offset, from Java' + ".querySelector('.overflow-auto');"
         " return !box.querySelector('.sticky-top') && box.firstElementChild.style.top == ''"
         " && box.firstElementChild.getBoundingClientRect().bottom < box.getBoundingClientRect().top"
         " && " + LIVE % 'An offset, from Java' + ".querySelector('.btn').textContent == 'Stick again'; })()"),
    ]),
    ('extras/date-time-pickers', 'the Tempus Dominus picker opens, picks a day and closes', [
        (LIVE % 'Tempus Dominus' + ".querySelector('input').click()",
         "document.querySelector('.tempus-dominus-widget.show [data-action=selectDay]')"),
        ("document.querySelector('.tempus-dominus-widget.show [data-action=selectDay]:not(.disabled)').click();"
         "document.body.click()",
         "!document.querySelector('.tempus-dominus-widget.show')"
         " && " + LIVE % 'Tempus Dominus' + ".querySelector('input').value != ''"
         " && " + LIVE % 'Tempus Dominus' + ".textContent.includes('Picked ')"),
    ]),
    ('extras/date-time-pickers', 'the Air Datepicker opens and closes', [
        (LIVE % 'Air Datepicker, with limits and a locale' + ".querySelector('input').dispatchEvent(new FocusEvent('focus'))",
         "document.querySelector('.air-datepicker.-active-')"),
        (LIVE % 'Air Datepicker, with limits and a locale' + ".querySelector('input').dispatchEvent(new FocusEvent('blur'))",
         "!document.querySelector('.air-datepicker.-active-')"),
    ]),
    ('extras/bootbox', 'the Bootbox alert opens and its callback runs when closed', [
        (WATCH_SHOWN + LIVE % 'Alert, confirm and prompt' + ".querySelector('.btn').click()",
         "window.__shown && document.querySelector('.bootbox.modal.show')"
         " && document.querySelector('.bootbox.modal.show').textContent.includes('Hello from Bootbox!')"),
        ("document.querySelector('.bootbox.modal.show .bootbox-accept').click()",
         "!document.querySelector('.bootbox.modal') && !document.querySelector('.modal-backdrop')"
         " && " + LIVE % 'Alert, confirm and prompt' + ".textContent.includes('The alert was closed.')"),
    ]),
    ('extras/bootbox', 'Bootbox.init counts every dialog shown until it is removed, and onShown / onHidden run', [
        ("window.__count = parseInt(" + LIVE % 'Show and hide callbacks' + ".textContent.match(/Dialogs shown: (\\d+)/)[1]);"
         + WATCH_SHOWN + LIVE % 'Show and hide callbacks' + ".querySelector('.btn').click()",
         "window.__shown && document.querySelector('.bootbox.modal.show')"
         " && " + LIVE % 'Show and hide callbacks' + ".textContent.includes('Dialogs shown: ' + (window.__count + 1))"
         " && " + LIVE % 'Show and hide callbacks' + ".textContent.includes('onShown ran.')"),
        ("document.querySelector('.bootbox.modal.show .bootbox-accept').click()",
         "!document.querySelector('.bootbox.modal')"
         " && " + LIVE % 'Show and hide callbacks' + ".textContent.includes('onHidden ran.')"),
        ("[...%s.querySelectorAll('.btn')].find(b => b.textContent == 'Stop counting').click();" % LIVE % 'Show and hide callbacks'
         + WATCH_SHOWN + LIVE % 'Show and hide callbacks' + ".querySelector('.btn').click()",
         "window.__shown && document.querySelector('.bootbox.modal.show')"
         " && " + LIVE % 'Show and hide callbacks' + ".textContent.includes('Dialogs shown: ' + (window.__count + 1) + '. No longer counting.')"),
        ("document.querySelector('.bootbox.modal.show .bootbox-accept').click()",
         "!document.querySelector('.bootbox.modal') && !document.querySelector('.modal-backdrop')"),
    ]),
    ('extras/bootbox', 'jQuery 4 is loaded, with jQuery Migrate for Summernote and the color picker', [
        ("void 0", "window.jQuery && jQuery.fn.jquery.startsWith('4.') && jQuery.migrateVersion && jQuery.migrateVersion.startsWith('4.')"),
    ]),
    ('extras/bootbox', 'confirm and prompt answer, and prompt cancels', [
        (WATCH_SHOWN + "[...%s.querySelectorAll('.btn')].find(b => b.textContent == 'Confirm').click()" % LIVE % 'Alert, confirm and prompt',
         "window.__shown && document.querySelector('.bootbox.modal.show')"),
        ("document.querySelector('.bootbox.modal.show .bootbox-accept').click()",
         "!document.querySelector('.bootbox.modal') && " + LIVE % 'Alert, confirm and prompt' + ".textContent.includes('Confirmed.')"),
        (WATCH_SHOWN + "[...%s.querySelectorAll('.btn')].find(b => b.textContent == 'Prompt').click()" % LIVE % 'Alert, confirm and prompt',
         "window.__shown && document.querySelector('.bootbox.modal.show input')"),
        ("const i = document.querySelector('.bootbox.modal.show input'); i.value = 'Ada';"
         " document.querySelector('.bootbox.modal.show .bootbox-accept').click()",
         "!document.querySelector('.bootbox.modal') && " + LIVE % 'Alert, confirm and prompt' + ".textContent.includes('Hello, Ada!')"),
        (WATCH_SHOWN + "[...%s.querySelectorAll('.btn')].find(b => b.textContent == 'Prompt').click()" % LIVE % 'Alert, confirm and prompt',
         "window.__shown && document.querySelector('.bootbox.modal.show input')"),
        ("document.querySelector('.bootbox.modal.show .bootbox-cancel').click()",
         "!document.querySelector('.bootbox.modal') && !document.querySelector('.modal-backdrop')"
         " && " + LIVE % 'Alert, confirm and prompt' + ".textContent.includes('Prompt cancelled.')"),
    ]),
    ('extras/summernote', 'the Summernote toolbar: bold, a dropdown, code view and the link dialog', [
        ("[...%s.querySelectorAll('.btn')].find(b => b.textContent == 'Fill with sample text').click()" % LIVE % 'Editor',
         LIVE % 'Editor' + ".querySelector('.note-editable p')"),
        ("const ed = " + LIVE % 'Editor' + ".querySelector('.note-editable'); ed.focus(); const r = document.createRange();"
         " r.selectNodeContents(ed.querySelector('p')); getSelection().removeAllRanges(); getSelection().addRange(r);"
         " " + LIVE % 'Editor' + ".querySelector('.note-btn-bold').click()",
         LIVE % 'Editor' + ".querySelector('.note-editable b, .note-editable strong')"),
        (LIVE % 'Editor' + ".querySelector('.note-toolbar .dropdown-toggle').click()",
         LIVE % 'Editor' + ".querySelector('.note-toolbar .dropdown-menu.show')"),
        (LIVE % 'Editor' + ".querySelector('.note-toolbar .dropdown-toggle').click()",
         "!" + LIVE % 'Editor' + ".querySelector('.note-toolbar .dropdown-menu.show')"),
        (LIVE % 'Editor' + ".querySelector('.btn-codeview').click()",
         LIVE % 'Editor' + ".querySelector('.note-editor.codeview') && " + LIVE % 'Editor' + ".querySelector('.note-codable').value.includes('GwtBootstrap5')"),
        (LIVE % 'Editor' + ".querySelector('.btn-codeview').click()",
         "!" + LIVE % 'Editor' + ".querySelector('.note-editor.codeview')"),
        (LIVE % 'Editor' + ".querySelector('.note-icon-link').closest('button').click()",
         "document.querySelector('.modal.show .note-link-url')"),
        ("document.querySelector('.modal.show .btn-close, .modal.show .close').click()",
         "!document.querySelector('.modal.show') && !document.querySelector('.modal-backdrop.show')"),
    ]),
    ('extras/color-picker', 'dragging on the color picker changes the color', [
        ("window.__color = " + LIVE % 'Picking a color' + ".querySelector('.font-monospace').textContent;"
         " const s = document.querySelector('.colorpicker-saturation'), r = s.getBoundingClientRect(),"
         " x = r.left + r.width * 0.8, y = r.top + r.height * 0.2;"
         " for (const t of ['mousedown', 'mousemove', 'mouseup']) (t == 'mousedown' ? s : document).dispatchEvent("
         "new MouseEvent(t, {bubbles: true, clientX: x, clientY: y, pageX: x + scrollX, pageY: y + scrollY}))",
         LIVE % 'Picking a color' + ".querySelector('.font-monospace').textContent != window.__color"),
    ]),
    ('extras/summernote', 'the Summernote editor takes and gives its HTML', [
        ("[...%s.querySelectorAll('.btn')].find(b => b.textContent == 'Fill with sample text').click()" % (LIVE % 'Editor'),
         LIVE % 'Editor' + ".querySelector('.note-editor .note-editable')"
         " && " + LIVE % 'Editor' + ".querySelector('.note-editable').textContent.includes('Hello GwtBootstrap5!')"),
        ("[...%s.querySelectorAll('.btn')].find(b => b.textContent == 'Show the HTML').click()" % (LIVE % 'Editor'),
         LIVE % 'Editor' + ".querySelector('pre.bg-body-tertiary').textContent.includes('<b>GwtBootstrap5</b>')"),
    ]),
]


def check_behaviour(browser, check):
    """Opens and closes the components driven by JavaScript."""
    for token, name, steps in BEHAVIOURS:
        open_page(browser, token)
        failed = ''
        for number, (action, condition) in enumerate(steps, 1):
            browser.js(action + ';')
            if not browser.wait(condition, 10):
                failed = 'step %d' % number
                break
        check('%s: %s' % (token, name), not failed, failed)


def check_reattach(browser, check):
    """Select and the date pickers destroy their JavaScript widget when they leave the page."""
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


# What each select engine draws, for the checks of the Select page. live is an example's
# .demo-live; the dropdown of Slim Select is in <body>, found by the id it shares with the control.
SELECT_JS = """
window.__select = {
  'Tom Select': {
    control: live => live.querySelector('.ts-wrapper'),
    open: live => live.querySelector('.ts-control').click(),
    isOpen: live => !!live.querySelector('.ts-dropdown') && live.querySelector('.ts-dropdown').style.display != 'none',
    option: (live, text) => [...live.querySelectorAll('.ts-dropdown .option')].find(o => o.textContent.trim().startsWith(text)),
    pick: (live, text) => window.__select['Tom Select'].option(live, text).dispatchEvent(new MouseEvent('click', {bubbles: true})),
    search: (live, text) => { const i = live.querySelector('.ts-dropdown input') || live.querySelector('.ts-control input'); i.focus(); i.value = text; i.dispatchEvent(new Event('input', {bubbles: true})); }
  },
  'Choices.js': {
    control: live => live.querySelector('.choices'),
    open: live => live.querySelector('.choices__inner').click(),
    isOpen: live => !!live.querySelector('.choices__list--dropdown.is-active'),
    option: (live, text) => [...live.querySelectorAll('.choices__list--dropdown [data-choice]')].find(o => o.textContent.trim().startsWith(text)),
    pick: (live, text) => window.__select['Choices.js'].option(live, text).dispatchEvent(new MouseEvent('mousedown', {bubbles: true})),
    search: (live, text) => { const i = live.querySelector('input.choices__input--cloned'); i.focus(); i.value = text; i.dispatchEvent(new Event('input', {bubbles: true})); i.dispatchEvent(new KeyboardEvent('keyup', {bubbles: true, key: text.slice(-1)})); }
  },
  'Slim Select': {
    control: live => live.querySelector('.ss-main'),
    content: live => document.querySelector('.ss-content[data-id="' + live.querySelector('.ss-main').dataset.id + '"]'),
    open: live => live.querySelector('.ss-main').click(),
    isOpen: live => window.__select['Slim Select'].content(live).classList.contains('ss-open'),
    option: (live, text) => [...window.__select['Slim Select'].content(live).querySelectorAll('.ss-option:not(.ss-hide)')].find(o => o.textContent.trim().startsWith(text)),
    pick: (live, text) => window.__select['Slim Select'].option(live, text).click(),
    search: (live, text) => { const i = window.__select['Slim Select'].content(live).querySelector('.ss-search input'); i.focus(); i.value = text; i.dispatchEvent(new Event('input', {bubbles: true})); }
  }
};
"""


def check_select(browser, check):
    """Every engine of the Select page opens, picks, loads remote options and survives a reattach."""
    open_page(browser, 'extras/select')
    for engine in ['Tom Select', 'Choices.js', 'Slim Select']:
        browser.js(SELECT_JS)
        browser.js("[...document.querySelectorAll('.demo-content .btn-group .btn')]"
                   ".find(b => b.textContent == '%s').click();" % engine)
        use = "const e = window.__select['%s']; " % engine
        single = EXAMPLE % 'Single select'
        multiple = EXAMPLE % 'Multiple select, with objects'
        remote = EXAMPLE % 'Remote search'
        reattach = EXAMPLE % 'Removed and added again'
        drawn = browser.wait("(() => { %s return !!e.control(%s) && !!e.control(%s)"
                             " && !!e.control(%s) && !!e.control(%s); })()" % (use, single, multiple, remote, reattach))
        # Creating a select and giving it its options is not a change of its value
        untouched = browser.js("return [%s.textContent.includes('Nothing selected.'),"
                               " %s.textContent.includes('Nothing selected.')];" % (single, remote))
        check('extras/select (%s): every example is drawn by the engine, with nothing selected' % engine,
              drawn and untouched == [True, True], untouched)

        steps = [
            # Without asyncLoad, the search filters the options the select has, and still does once
            # a load would have answered
            ("%s window.__searched = 0; e.open(%s);"
             " setTimeout(() => { e.search(%s, 'Sp'); window.__searched = Date.now(); }, 300)" % (use, single, single),
             "(() => { %s return window.__searched && Date.now() - window.__searched > 1200 && e.isOpen(%s)"
             " && !!e.option(%s, 'Spain') && !e.option(%s, 'Argentina'); })()" % (use, single, single, single)),
            ("%s e.pick(%s, 'Spain')" % (use, single),
             "(() => { %s return %s.textContent.includes('Selected: Spain.') && !e.isOpen(%s); })()"
             % (use, single, single)),
            ("%s e.open(%s)" % (use, multiple),
             "(() => { %s return e.isOpen(%s) && !!e.option(%s, 'Ham'); })()" % (use, multiple, multiple)),
            ("%s e.pick(%s, 'Ham')" % (use, multiple),
             "%s.textContent.includes('1 toppings, 1.5')" % multiple),
            ("%s e.open(%s); setTimeout(() => e.search(%s, 'par'), 300)" % (use, remote, remote),
             "(() => { %s return !!e.option(%s, 'Paris'); })()" % (use, remote)),
            ("%s e.pick(%s, 'Paris')" % (use, remote),
             "%s.textContent.includes('Selected: Paris.')" % remote),
        ]
        failed = ''
        for number, (action, condition) in enumerate(steps, 1):
            browser.js(action)
            if not browser.wait(condition, 10):
                failed = 'step %d' % number
                break
        check('extras/select (%s): picks an option in the single, multiple and remote examples' % engine,
              not failed, failed)
        browser.js("document.body.click()")

        # Removed and added again, the select gets a new instance with the same value
        state = ("%s const live = %s; return [live.querySelectorAll('.ts-wrapper, .choices, .ss-main').length,"
                 " live.querySelector('select').value];" % (use, reattach))
        before = browser.js(state)
        browser.js("%s.querySelector('button.btn:not(.dropdown-toggle)').click();" % reattach)
        time.sleep(0.5)
        after = browser.js(state)
        check('extras/select (%s): a select removed and added again has one instance with its value' % engine,
              before == [1, 'Medium'] and after == before, [before, after])

    # Leaving the page destroys every instance, also the dropdowns Slim Select puts in <body>
    open_page(browser, 'general/setup')
    left = browser.js("return document.querySelectorAll('.ts-wrapper, .choices, .ss-main, .ss-content').length")
    open_page(browser, 'extras/select')
    counts = browser.js("return [document.querySelectorAll('.demo-content select').length,"
                        " document.querySelectorAll('.ts-wrapper').length]")
    check('extras/select: leaving the page destroys the selects; one Tom Select per select when back',
          left == 0 and counts[0] > 0 and counts[0] == counts[1], [left, counts])


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
        check_behaviour(browser, check)
        check_reattach(browser, check)
        check_select(browser, check)
        errors = browser.js('return window.__errors')
        check('no JavaScript errors', errors == [], errors)
    finally:
        browser.close()
    print('%d pages, %d failures' % (len(pages), len(failures)))
    return 1 if failures else 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1].rstrip('/')))
