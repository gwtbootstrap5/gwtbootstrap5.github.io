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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * A block of highlighted code ({@code pre > code}).
 */
public class CodeBlock extends Widget {

    private final Element code;

    public CodeBlock() {
        setElement(Document.get().createPreElement());
        setStyleName("demo-code");
        code = Document.get().createElement("code");
        getElement().appendChild(code);
    }

    /**
     * @param language a highlight.js language: {@code xml}, {@code java}, {@code html}
     */
    public void setLanguage(final String language) {
        code.setClassName("language-" + language);
    }

    public void setCode(final String text) {
        code.removeAttribute("data-highlighted");
        code.setInnerText(text);
        if (isAttached()) {
            Highlighter.highlight(code);
        }
    }

    public String getCode() {
        return code.getInnerText();
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        if (!code.hasAttribute("data-highlighted")) {
            Highlighter.highlight(code);
        }
    }
}
