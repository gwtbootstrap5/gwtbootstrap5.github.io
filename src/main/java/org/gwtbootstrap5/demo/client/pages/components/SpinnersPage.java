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
import org.gwtbootstrap5.client.ui.Spinner;
import org.gwtbootstrap5.client.ui.constants.SpinnerType;
import org.gwtbootstrap5.demo.client.ui.Example;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Spinners demo page.
 */
public class SpinnersPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, SpinnersPage> {
    }

    @UiTemplate("spinners/Types.ui.xml")
    interface TypesBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("spinners/Small.ui.xml")
    interface SmallBinder extends UiBinder<Widget, SavingButton> {
    }

    interface Sources extends ClientBundle {
        @Source("spinners/Types.ui.xml")
        TextResource types();

        @Source("spinners/Small.ui.xml")
        TextResource small();

        @Source("SpinnersPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example types;
    @UiField
    Example small;

    public SpinnersPage() {
        initWidget(BINDER.createAndBindUi(this));
        types.show(GWT.<TypesBinder>create(TypesBinder.class).createAndBindUi(this), SOURCES.types());
        final SavingButton smallOwner = new SavingButton();
        small.show(GWT.<SmallBinder>create(SmallBinder.class).createAndBindUi(smallOwner), SOURCES.small());
        smallOwner.init();
        small.addJava(SOURCES.java(), "small");
    }

    static class SavingButton {
        // [START small]
        @UiField
        Button save;

        void init() {
            save.addClickHandler(event -> {
                final Spinner spinner = new Spinner(SpinnerType.BORDER, "Saving...");
                spinner.setSmall(true);
                spinner.addStyleName("me-2");
                save.setEnabled(false);
                save.setText("Saving");
                save.insert(spinner, 0);
                new Timer() {
                    @Override
                    public void run() {
                        spinner.removeFromParent();
                        save.setText("Save");
                        save.setEnabled(true);
                    }
                }.schedule(2000);
            });
        }
        // [END small]
    }
}
