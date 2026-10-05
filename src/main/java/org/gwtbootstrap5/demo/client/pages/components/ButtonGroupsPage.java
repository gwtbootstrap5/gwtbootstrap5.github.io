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

import org.gwtbootstrap5.client.ui.CheckBoxButton;
import org.gwtbootstrap5.client.ui.RadioButton;
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
 * Button groups demo page.
 */
public class ButtonGroupsPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ButtonGroupsPage> {
    }

    @UiTemplate("buttongroups/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("buttongroups/Toolbar.ui.xml")
    interface ToolbarBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("buttongroups/Checks.ui.xml")
    interface ChecksBinder extends UiBinder<Widget, Formatting> {
    }

    interface Sources extends ClientBundle {
        @Source("buttongroups/Basic.ui.xml")
        TextResource basic();

        @Source("buttongroups/Toolbar.ui.xml")
        TextResource toolbar();

        @Source("buttongroups/Checks.ui.xml")
        TextResource checks();

        @Source("ButtonGroupsPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example toolbar;
    @UiField
    Example checks;

    public ButtonGroupsPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        toolbar.show(GWT.<ToolbarBinder>create(ToolbarBinder.class).createAndBindUi(this), SOURCES.toolbar());
        final Formatting checksOwner = new Formatting();
        checks.show(GWT.<ChecksBinder>create(ChecksBinder.class).createAndBindUi(checksOwner), SOURCES.checks());
        checksOwner.init();
        checks.addJava(SOURCES.java(), "checks");
    }

    static class Formatting {
        // [START checks]
        @UiField
        CheckBoxButton bold;
        @UiField
        CheckBoxButton italic;
        @UiField
        RadioButton left;
        @UiField
        RadioButton center;
        @UiField
        RadioButton right;
        @UiField
        Paragraph status;

        void init() {
            bold.addValueChangeHandler(event -> update());
            italic.addValueChangeHandler(event -> update());
            left.addValueChangeHandler(event -> update());
            center.addValueChangeHandler(event -> update());
            right.addValueChangeHandler(event -> update());
            update();
        }

        private void update() {
            final String align = left.getValue() ? "left" : center.getValue() ? "center" : "right";
            status.setText("Bold: " + bold.getValue() + ", italic: " + italic.getValue() + ", aligned " + align + ".");
        }
        // [END checks]
    }
}
