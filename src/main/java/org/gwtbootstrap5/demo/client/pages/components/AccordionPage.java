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

import org.gwtbootstrap5.client.ui.AccordionItem;
import org.gwtbootstrap5.client.ui.Button;
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
 * Accordion demo page.
 */
public class AccordionPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, AccordionPage> {
    }

    @UiTemplate("accordion/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("accordion/Flush.ui.xml")
    interface FlushBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("accordion/FromJava.ui.xml")
    interface FromJavaBinder extends UiBinder<Widget, OpenAll> {
    }

    interface Sources extends ClientBundle {
        @Source("accordion/Basic.ui.xml")
        TextResource basic();

        @Source("accordion/Flush.ui.xml")
        TextResource flush();

        @Source("accordion/FromJava.ui.xml")
        TextResource fromJava();

        @Source("AccordionPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example flush;
    @UiField
    Example fromJava;

    public AccordionPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        flush.show(GWT.<FlushBinder>create(FlushBinder.class).createAndBindUi(this), SOURCES.flush());
        final OpenAll fromJavaOwner = new OpenAll();
        fromJava.show(GWT.<FromJavaBinder>create(FromJavaBinder.class).createAndBindUi(fromJavaOwner), SOURCES.fromJava());
        fromJavaOwner.init();
        fromJava.addJava(SOURCES.java(), "fromJava");
    }

    static class OpenAll {
        // [START fromJava]
        @UiField
        Button openAll;
        @UiField
        Button closeAll;
        @UiField
        AccordionItem one;
        @UiField
        AccordionItem two;

        void init() {
            openAll.addClickHandler(event -> {
                one.setOpen(true);
                two.setOpen(true);
            });
            closeAll.addClickHandler(event -> {
                one.setOpen(false);
                two.setOpen(false);
            });
        }
        // [END fromJava]
    }
}
