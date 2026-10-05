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
import org.gwtbootstrap5.client.ui.Toast;
import org.gwtbootstrap5.client.ui.ToastContainer;
import org.gwtbootstrap5.demo.client.ui.Example;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Toasts demo page.
 */
public class ToastsPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ToastsPage> {
    }

    @UiTemplate("toasts/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, StaticToast> {
    }

    @UiTemplate("toasts/Live.ui.xml")
    interface LiveBinder extends UiBinder<Widget, Notifier> {
    }

    interface Sources extends ClientBundle {
        @Source("toasts/Basic.ui.xml")
        TextResource basic();

        @Source("toasts/Live.ui.xml")
        TextResource live();

        @Source("ToastsPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example live;

    public ToastsPage() {
        initWidget(BINDER.createAndBindUi(this));
        final StaticToast basicOwner = new StaticToast();
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(basicOwner), SOURCES.basic());
        basicOwner.init();
        basic.addJava(SOURCES.java(), "basic");
        final Notifier liveOwner = new Notifier();
        live.show(GWT.<LiveBinder>create(LiveBinder.class).createAndBindUi(liveOwner), SOURCES.live());
        liveOwner.init();
        live.addJava(SOURCES.java(), "live");
    }

    static class StaticToast {
        // [START basic]
        @UiField
        SimplePanel holder;

        void init() {
            final Toast toast = new Toast("GwtBootstrap5", "11 mins ago", "Hello, world! This is a toast message.");
            toast.setAutohide(false);
            toast.addAttachHandler(event -> {
                if (event.isAttached()) {
                    toast.show();
                }
            });
            holder.setWidget(toast);
        }
        // [END basic]
    }

    static class Notifier {
        // [START live]
        @UiField
        Button notify;
        @UiField
        ToastContainer container;
        private int count;

        void init() {
            notify.addClickHandler(event -> {
                count++;
                final Toast toast = new Toast("Notification " + count, "just now", "This toast hides itself after five seconds.");
                toast.addHiddenHandler(hidden -> toast.removeFromParent());
                container.add(toast);
                toast.show();
            });
        }
        // [END live]
    }
}
