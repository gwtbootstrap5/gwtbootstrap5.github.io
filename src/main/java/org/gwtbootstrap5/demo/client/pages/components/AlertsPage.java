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

import org.gwtbootstrap5.client.ui.Alert;
import org.gwtbootstrap5.client.ui.Button;
import org.gwtbootstrap5.client.ui.constants.AlertType;
import org.gwtbootstrap5.demo.client.ui.Example;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Alerts demo page.
 */
public class AlertsPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, AlertsPage> {
    }

    @UiTemplate("alerts/Types.ui.xml")
    interface TypesBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("alerts/Content.ui.xml")
    interface ContentBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("alerts/Dismissable.ui.xml")
    interface DismissableBinder extends UiBinder<Widget, DismissableAlerts> {
    }

    interface Sources extends ClientBundle {
        @Source("alerts/Types.ui.xml")
        TextResource types();

        @Source("alerts/Content.ui.xml")
        TextResource content();

        @Source("alerts/Dismissable.ui.xml")
        TextResource dismissable();

        @Source("AlertsPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example types;
    @UiField
    Example content;
    @UiField
    Example dismissable;

    public AlertsPage() {
        initWidget(BINDER.createAndBindUi(this));
        types.show(GWT.<TypesBinder>create(TypesBinder.class).createAndBindUi(this), SOURCES.types());
        content.show(GWT.<ContentBinder>create(ContentBinder.class).createAndBindUi(this), SOURCES.content());
        final DismissableAlerts dismissableOwner = new DismissableAlerts();
        dismissable.show(GWT.<DismissableBinder>create(DismissableBinder.class).createAndBindUi(dismissableOwner), SOURCES.dismissable());
        dismissableOwner.init();
        dismissable.addJava(SOURCES.java(), "dismissable");
    }

    static class DismissableAlerts {
        // [START dismissable]
        @UiField
        Button add;
        @UiField
        FlowPanel alerts;
        private int count;

        void init() {
            add.addClickHandler(event -> {
                count++;
                final Alert alert = new Alert("Alert number " + count + ". Close it with the button on the right.", AlertType.INFO);
                alert.setDismissable(true);
                alert.setFade(true);
                alert.addClosedHandler(closed -> add.setText("Add an alert (" + alerts.getWidgetCount() + " left)"));
                alerts.add(alert);
            });
        }
        // [END dismissable]
    }
}
