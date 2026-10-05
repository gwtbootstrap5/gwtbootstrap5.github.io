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
import org.gwtbootstrap5.client.ui.Offcanvas;
import org.gwtbootstrap5.client.ui.constants.OffcanvasPlacement;
import org.gwtbootstrap5.client.ui.html.Paragraph;
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
 * Offcanvas demo page.
 */
public class OffcanvasPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, OffcanvasPage> {
    }

    @UiTemplate("offcanvas/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("offcanvas/FromJava.ui.xml")
    interface FromJavaBinder extends UiBinder<Widget, Placements> {
    }

    interface Sources extends ClientBundle {
        @Source("offcanvas/Basic.ui.xml")
        TextResource basic();

        @Source("offcanvas/FromJava.ui.xml")
        TextResource fromJava();

        @Source("OffcanvasPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example fromJava;

    public OffcanvasPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        final Placements fromJavaOwner = new Placements();
        fromJava.show(GWT.<FromJavaBinder>create(FromJavaBinder.class).createAndBindUi(fromJavaOwner), SOURCES.fromJava());
        fromJavaOwner.init();
        fromJava.addJava(SOURCES.java(), "fromJava");
    }

    static class Placements {
        // [START fromJava]
        @UiField
        Button start;
        @UiField
        Button end;
        @UiField
        Button top;
        @UiField
        Button bottom;
        @UiField
        Offcanvas offcanvas;
        @UiField
        Paragraph where;

        void init() {
            start.addClickHandler(event -> open(OffcanvasPlacement.START));
            end.addClickHandler(event -> open(OffcanvasPlacement.END));
            top.addClickHandler(event -> open(OffcanvasPlacement.TOP));
            bottom.addClickHandler(event -> open(OffcanvasPlacement.BOTTOM));
        }

        private void open(final OffcanvasPlacement placement) {
            offcanvas.setPlacement(placement);
            where.setText("This panel opened from " + placement.name().toLowerCase() + ". The page behind it still scrolls.");
            offcanvas.show();
        }
        // [END fromJava]
    }
}
