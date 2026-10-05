package org.gwtbootstrap5.demo.client.pages.forms;

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

import org.gwtbootstrap5.client.ui.Switch;
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
 * Switches demo page.
 */
public class SwitchPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, SwitchPage> {
    }

    @UiTemplate("switch/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("switch/Events.ui.xml")
    interface EventsBinder extends UiBinder<Widget, WifiSwitch> {
    }

    interface Sources extends ClientBundle {
        @Source("switch/Basic.ui.xml")
        TextResource basic();

        @Source("switch/Events.ui.xml")
        TextResource events();

        @Source("SwitchPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example events;

    public SwitchPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        final WifiSwitch eventsOwner = new WifiSwitch();
        events.show(GWT.<EventsBinder>create(EventsBinder.class).createAndBindUi(eventsOwner), SOURCES.events());
        eventsOwner.init();
        events.addJava(SOURCES.java(), "events");
    }

    static class WifiSwitch {
        // [START events]
        @UiField
        Switch wifi;
        @UiField
        Paragraph status;

        void init() {
            wifi.addValueChangeHandler(event -> status.setText(event.getValue() ? "Wi-Fi is on." : "Wi-Fi is off."));
        }
        // [END events]
    }
}
