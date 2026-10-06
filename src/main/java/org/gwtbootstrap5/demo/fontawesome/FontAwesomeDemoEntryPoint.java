package org.gwtbootstrap5.demo.fontawesome;

/*-
 * ==========================LICENSE_START===============================
 * GwtBootstrap5
 * ======================================================================
 * Copyright (C) 2026 GwtBootstrap5
 * ======================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ==========================LICENSE_END=================================
 */

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import elemental2.dom.Document;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.MutationObserver;
import elemental2.dom.MutationObserverInit;
import jsinterop.base.Js;

/**
 * The Font Awesome page, loaded in an iframe of the demo's Extras section.
 */
public class FontAwesomeDemoEntryPoint implements EntryPoint {

    private static final String THEME = "data-bs-theme";

    @Override
    public void onModuleLoad() {
        followParentColorMode();
        RootPanel.get("demo-page").add(new FontAwesomeDemoPage());
    }

    /**
     * Keeps the page in the color mode of the demo around it.
     */
    private static void followParentColorMode() {
        if (DomGlobal.window.parent == null || DomGlobal.window.parent == DomGlobal.window) {
            return;
        }
        final Document parentDocument = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window.parent).get("document"));
        final Element parentRoot = parentDocument.documentElement;
        final Element root = DomGlobal.document.documentElement;
        final Runnable copy = () -> {
            final String theme = parentRoot.getAttribute(THEME);
            if (theme == null) {
                root.removeAttribute(THEME);
            } else {
                root.setAttribute(THEME, theme);
            }
        };
        copy.run();
        final MutationObserverInit options = MutationObserverInit.create();
        options.setAttributes(true);
        options.setAttributeFilter(new String[] {THEME});
        new MutationObserver((records, observer) -> {
            copy.run();
            return null;
        }).observe(parentRoot, options);
    }
}
