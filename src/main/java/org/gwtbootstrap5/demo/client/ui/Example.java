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

import org.gwtbootstrap5.client.ui.Collapse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * One example of a page: a heading, a short description, the example running, and its code.
 * <p>
 * The code shown is the example's own {@code .ui.xml} file, read through a {@link TextResource},
 * so it can't drift from what runs:
 *
 * <pre>{@code
 * interface BasicBinder extends UiBinder<Widget, AlertPage> {}  // @UiTemplate("examples/Basic.ui.xml")
 * basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
 * }</pre>
 */
public class Example extends Composite {

    interface Binder extends UiBinder<HTMLPanel, Example> {
    }

    private static final Binder BINDER = GWT.create(Binder.class);

    @UiField
    Element heading;
    @UiField
    Element description;
    @UiField
    SimplePanel live;
    @UiField
    Element toggle;
    @UiField
    Collapse sources;
    @UiField
    FlowPanel codes;

    public Example() {
        initWidget(BINDER.createAndBindUi(this));
        final String id = DOM.createUniqueId();
        sources.setId(id);
        toggle.setAttribute("data-bs-target", "#" + id);
        toggle.setAttribute("aria-controls", id);
    }

    public void setHeading(final String text) {
        heading.setInnerText(text);
    }

    public void setDescription(final String html) {
        description.setInnerHTML(html);
    }

    /**
     * Shows the example and the code of its UiBinder template.
     */
    public void show(final Widget example, final TextResource uiXml) {
        live.setWidget(example);
        addCode("xml", SourceFormatter.uiBinder(uiXml.getText()));
    }

    /**
     * Adds a Java region ({@code // [START name]} ... {@code // [END name]}) to the code shown.
     */
    public void addJava(final TextResource javaFile, final String region) {
        addCode("java", SourceFormatter.javaRegion(javaFile.getText(), region));
    }

    private void addCode(final String language, final String text) {
        final CodeBlock block = new CodeBlock();
        block.setLanguage(language);
        block.setCode(text);
        codes.add(block);
    }
}
