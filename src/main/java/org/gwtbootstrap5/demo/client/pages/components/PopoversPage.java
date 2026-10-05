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

import org.gwtbootstrap5.client.ui.Popover;
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
 * Popovers demo page.
 */
public class PopoversPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, PopoversPage> {
    }

    @UiTemplate("popovers/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("popovers/Focus.ui.xml")
    interface FocusBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("popovers/Events.ui.xml")
    interface EventsBinder extends UiBinder<Widget, PopoverEvents> {
    }

    interface Sources extends ClientBundle {
        @Source("popovers/Basic.ui.xml")
        TextResource basic();

        @Source("popovers/Focus.ui.xml")
        TextResource focus();

        @Source("popovers/Events.ui.xml")
        TextResource events();

        @Source("PopoversPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example focus;
    @UiField
    Example events;

    public PopoversPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        focus.show(GWT.<FocusBinder>create(FocusBinder.class).createAndBindUi(this), SOURCES.focus());
        final PopoverEvents eventsOwner = new PopoverEvents();
        events.show(GWT.<EventsBinder>create(EventsBinder.class).createAndBindUi(eventsOwner), SOURCES.events());
        eventsOwner.init();
        events.addJava(SOURCES.java(), "events");
    }

    static class PopoverEvents {
        // [START events]
        @UiField
        Popover popover;
        @UiField
        Span status;
        private int opened;

        void init() {
            popover.setContent("You haven't opened it yet.");
            popover.addShownHandler(event -> {
                opened++;
                status.setText("Opened " + opened + (opened == 1 ? " time." : " times."));
            });
            popover.addHiddenHandler(event -> popover.setContent("You opened it " + opened + (opened == 1 ? " time." : " times.")));
        }
        // [END events]
    }
}
