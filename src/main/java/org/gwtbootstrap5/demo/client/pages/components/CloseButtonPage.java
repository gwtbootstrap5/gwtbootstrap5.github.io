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

import org.gwtbootstrap5.client.ui.base.button.CloseButton;
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
 * Close button demo page.
 */
public class CloseButtonPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, CloseButtonPage> {
    }

    @UiTemplate("closebutton/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Closes> {
    }

    interface Sources extends ClientBundle {
        @Source("closebutton/Basic.ui.xml")
        TextResource basic();

        @Source("CloseButtonPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;

    public CloseButtonPage() {
        initWidget(BINDER.createAndBindUi(this));
        final Closes basicOwner = new Closes();
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(basicOwner), SOURCES.basic());
        basicOwner.init();
        basic.addJava(SOURCES.java(), "basic");
    }

    static class Closes {
        // [START basic]
        @UiField
        CloseButton close;
        @UiField
        CloseButton white;
        @UiField
        Paragraph status;

        void init() {
            close.getElement().setAttribute("aria-label", "Close");
            white.getElement().setAttribute("aria-label", "Close");
            close.addClickHandler(event -> status.setText("Closed the light one."));
            white.addClickHandler(event -> status.setText("Closed the white one."));
        }
        // [END basic]
    }
}
