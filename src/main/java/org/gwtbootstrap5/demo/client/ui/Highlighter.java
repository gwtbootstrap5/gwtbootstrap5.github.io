package org.gwtbootstrap5.demo.client.ui;

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

import com.google.gwt.dom.client.Element;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

/**
 * Colors code with highlight.js, which the host page loads. Without it the code shows plain.
 */
final class Highlighter {

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "hljs")
    private static final class Hljs {
        static native void highlightElement(Element element);
    }

    private Highlighter() {
    }

    static void highlight(final Element code) {
        if (!"undefined".equals(Js.typeof(Js.global().get("hljs")))) {
            Hljs.highlightElement(code);
        }
    }
}
