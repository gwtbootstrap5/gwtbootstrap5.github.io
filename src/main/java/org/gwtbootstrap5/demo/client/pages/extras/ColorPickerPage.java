package org.gwtbootstrap5.demo.client.pages.extras;

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

import org.gwtbootstrap5.client.ui.html.Paragraph;
import org.gwtbootstrap5.demo.client.ui.Example;
import org.gwtbootstrap5.extras.colorpicker.client.ColorPicker;

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
 * Color picker demo page.
 */
public class ColorPickerPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ColorPickerPage> {
    }

    @UiTemplate("colorpicker/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Picker> {
    }

    interface Sources extends ClientBundle {
        @Source("colorpicker/Basic.ui.xml")
        TextResource basic();

        @Source("ColorPickerPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;

    public ColorPickerPage() {
        initWidget(BINDER.createAndBindUi(this));
        final Picker basicOwner = new Picker();
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(basicOwner), SOURCES.basic());
        basicOwner.init();
        basic.addJava(SOURCES.java(), "basic");
    }

    static class Picker {
        // [START basic]
        @UiField
        ColorPicker picker;
        @UiField
        HTMLPanel swatch;
        @UiField
        Paragraph value;

        void init() {
            picker.addValueChangeHandler(event -> show(event.getValue()));
            picker.setValue("#7952b3");
            show("#7952b3");
        }

        private void show(final String color) {
            swatch.getElement().getStyle().setBackgroundColor(color);
            value.setText(color);
        }
        // [END basic]
    }
}
