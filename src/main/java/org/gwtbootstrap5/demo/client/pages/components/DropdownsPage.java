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

import org.gwtbootstrap5.client.ui.AnchorListItem;
import org.gwtbootstrap5.client.ui.Button;
import org.gwtbootstrap5.client.ui.DropDown;
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
 * Dropdowns demo page.
 */
public class DropdownsPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, DropdownsPage> {
    }

    @UiTemplate("dropdowns/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("dropdowns/Options.ui.xml")
    interface OptionsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("dropdowns/FromJava.ui.xml")
    interface FromJavaBinder extends UiBinder<Widget, SizePicker> {
    }

    interface Sources extends ClientBundle {
        @Source("dropdowns/Basic.ui.xml")
        TextResource basic();

        @Source("dropdowns/Options.ui.xml")
        TextResource options();

        @Source("dropdowns/FromJava.ui.xml")
        TextResource fromJava();

        @Source("DropdownsPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example options;
    @UiField
    Example fromJava;

    public DropdownsPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        options.show(GWT.<OptionsBinder>create(OptionsBinder.class).createAndBindUi(this), SOURCES.options());
        final SizePicker fromJavaOwner = new SizePicker();
        fromJava.show(GWT.<FromJavaBinder>create(FromJavaBinder.class).createAndBindUi(fromJavaOwner), SOURCES.fromJava());
        fromJavaOwner.init();
        fromJava.addJava(SOURCES.java(), "fromJava");
    }

    static class SizePicker {
        // [START fromJava]
        @UiField
        DropDown dropDown;
        @UiField
        Button toggle;
        @UiField
        AnchorListItem small;
        @UiField
        AnchorListItem medium;
        @UiField
        AnchorListItem large;
        @UiField
        Paragraph status;

        void init() {
            for (final AnchorListItem item : new AnchorListItem[] {small, medium, large}) {
                item.addClickHandler(event -> {
                    event.preventDefault();
                    toggle.setText(item.getText());
                    small.setActive(item == small);
                    medium.setActive(item == medium);
                    large.setActive(item == large);
                });
            }
            dropDown.addShownHandler(event -> status.setText("The menu is open."));
            dropDown.addHiddenHandler(event -> status.setText("The menu is closed."));
        }
        // [END fromJava]
    }
}
