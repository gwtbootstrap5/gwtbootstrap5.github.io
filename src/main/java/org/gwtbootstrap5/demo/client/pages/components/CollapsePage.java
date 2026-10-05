package org.gwtbootstrap5.demo.client.pages.components;

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

import org.gwtbootstrap5.client.ui.Button;
import org.gwtbootstrap5.client.ui.Collapse;
import org.gwtbootstrap5.client.ui.html.Span;
import org.gwtbootstrap5.demo.client.ui.Example;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Collapse demo page.
 */
public class CollapsePage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, CollapsePage> {
    }

    @UiTemplate("collapse/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("collapse/Horizontal.ui.xml")
    interface HorizontalBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("collapse/FromJava.ui.xml")
    interface FromJavaBinder extends UiBinder<Widget, JavaCollapse> {
    }

    interface Sources extends ClientBundle {
        @Source("collapse/Basic.ui.xml")
        TextResource basic();

        @Source("collapse/Horizontal.ui.xml")
        TextResource horizontal();

        @Source("collapse/FromJava.ui.xml")
        TextResource fromJava();

        @Source("CollapsePage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example horizontal;
    @UiField
    Example fromJava;

    public CollapsePage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        horizontal.show(GWT.<HorizontalBinder>create(HorizontalBinder.class).createAndBindUi(this), SOURCES.horizontal());
        final JavaCollapse fromJavaOwner = new JavaCollapse();
        fromJava.show(GWT.<FromJavaBinder>create(FromJavaBinder.class).createAndBindUi(fromJavaOwner), SOURCES.fromJava());
        fromJavaOwner.init();
        fromJava.addJava(SOURCES.java(), "fromJava");
    }

    static class JavaCollapse {
        // [START fromJava]
        @UiField
        Button toggle;
        @UiField
        Span status;
        @UiField
        Collapse collapse;

        void init() {
            toggle.addClickHandler(event -> collapse.toggle());
            collapse.addShownHandler(event -> status.setText("Shown."));
            collapse.addHiddenHandler(event -> status.setText("Hidden."));
        }
        // [END fromJava]
    }
}
