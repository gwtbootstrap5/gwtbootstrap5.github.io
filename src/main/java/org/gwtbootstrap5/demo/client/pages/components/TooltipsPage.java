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
import org.gwtbootstrap5.client.ui.Tooltip;
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
 * Tooltips demo page.
 */
public class TooltipsPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, TooltipsPage> {
    }

    @UiTemplate("tooltips/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("tooltips/Widgets.ui.xml")
    interface WidgetsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("tooltips/FromJava.ui.xml")
    interface FromJavaBinder extends UiBinder<Widget, CountingTooltip> {
    }

    interface Sources extends ClientBundle {
        @Source("tooltips/Basic.ui.xml")
        TextResource basic();

        @Source("tooltips/Widgets.ui.xml")
        TextResource widgets();

        @Source("tooltips/FromJava.ui.xml")
        TextResource fromJava();

        @Source("TooltipsPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example widgets;
    @UiField
    Example fromJava;

    public TooltipsPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        widgets.show(GWT.<WidgetsBinder>create(WidgetsBinder.class).createAndBindUi(this), SOURCES.widgets());
        final CountingTooltip fromJavaOwner = new CountingTooltip();
        fromJava.show(GWT.<FromJavaBinder>create(FromJavaBinder.class).createAndBindUi(fromJavaOwner), SOURCES.fromJava());
        fromJavaOwner.init();
        fromJava.addJava(SOURCES.java(), "fromJava");
    }

    static class CountingTooltip {
        // [START fromJava]
        @UiField
        Tooltip tooltip;
        @UiField
        Button button;
        private int clicks;

        void init() {
            button.addClickHandler(event -> {
                clicks++;
                tooltip.setTitle("Clicked " + clicks + (clicks == 1 ? " time" : " times"));
            });
        }
        // [END fromJava]
    }
}
