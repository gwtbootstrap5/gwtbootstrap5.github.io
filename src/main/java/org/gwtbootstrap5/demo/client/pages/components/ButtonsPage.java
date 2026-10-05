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
 * Buttons demo page.
 */
public class ButtonsPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ButtonsPage> {
    }

    @UiTemplate("buttons/Types.ui.xml")
    interface TypesBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("buttons/Outline.ui.xml")
    interface OutlineBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("buttons/Sizes.ui.xml")
    interface SizesBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("buttons/Icons.ui.xml")
    interface IconsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("buttons/Clicks.ui.xml")
    interface ClicksBinder extends UiBinder<Widget, ClickCounter> {
    }

    interface Sources extends ClientBundle {
        @Source("buttons/Types.ui.xml")
        TextResource types();

        @Source("buttons/Outline.ui.xml")
        TextResource outline();

        @Source("buttons/Sizes.ui.xml")
        TextResource sizes();

        @Source("buttons/Icons.ui.xml")
        TextResource icons();

        @Source("buttons/Clicks.ui.xml")
        TextResource clicks();

        @Source("ButtonsPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example types;
    @UiField
    Example outline;
    @UiField
    Example sizes;
    @UiField
    Example icons;
    @UiField
    Example clicks;

    public ButtonsPage() {
        initWidget(BINDER.createAndBindUi(this));
        types.show(GWT.<TypesBinder>create(TypesBinder.class).createAndBindUi(this), SOURCES.types());
        outline.show(GWT.<OutlineBinder>create(OutlineBinder.class).createAndBindUi(this), SOURCES.outline());
        sizes.show(GWT.<SizesBinder>create(SizesBinder.class).createAndBindUi(this), SOURCES.sizes());
        icons.show(GWT.<IconsBinder>create(IconsBinder.class).createAndBindUi(this), SOURCES.icons());
        final ClickCounter clicksOwner = new ClickCounter();
        clicks.show(GWT.<ClicksBinder>create(ClicksBinder.class).createAndBindUi(clicksOwner), SOURCES.clicks());
        clicksOwner.init();
        clicks.addJava(SOURCES.java(), "clicks");
    }

    static class ClickCounter {
        // [START clicks]
        @UiField
        Button button;
        @UiField
        Paragraph status;
        private int clicks;

        void init() {
            button.addClickHandler(event -> {
                clicks++;
                status.setText("Clicked " + clicks + (clicks == 1 ? " time." : " times."));
            });
        }
        // [END clicks]
    }
}
