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

import org.gwtbootstrap5.client.ui.Button;
import org.gwtbootstrap5.client.ui.html.Paragraph;
import org.gwtbootstrap5.demo.client.ui.Example;
import org.gwtbootstrap5.extras.bootbox.client.Bootbox;
import org.gwtbootstrap5.extras.bootbox.client.options.BootboxSize;
import org.gwtbootstrap5.extras.bootbox.client.options.DialogOptions;
import org.gwtbootstrap5.extras.bootbox.client.options.PromptOptions;

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
 * Bootbox demo page.
 */
public class BootboxPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, BootboxPage> {
    }

    @UiTemplate("bootbox/Dialogs.ui.xml")
    interface DialogsBinder extends UiBinder<Widget, Dialogs> {
    }

    @UiTemplate("bootbox/Custom.ui.xml")
    interface CustomBinder extends UiBinder<Widget, PlanDialog> {
    }

    interface Sources extends ClientBundle {
        @Source("bootbox/Dialogs.ui.xml")
        TextResource dialogs();

        @Source("bootbox/Custom.ui.xml")
        TextResource custom();

        @Source("BootboxPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example dialogs;
    @UiField
    Example custom;

    public BootboxPage() {
        initWidget(BINDER.createAndBindUi(this));
        final Dialogs dialogsOwner = new Dialogs();
        dialogs.show(GWT.<DialogsBinder>create(DialogsBinder.class).createAndBindUi(dialogsOwner), SOURCES.dialogs());
        dialogsOwner.init();
        dialogs.addJava(SOURCES.java(), "dialogs");
        final PlanDialog customOwner = new PlanDialog();
        custom.show(GWT.<CustomBinder>create(CustomBinder.class).createAndBindUi(customOwner), SOURCES.custom());
        customOwner.init();
        custom.addJava(SOURCES.java(), "custom");
    }

    static class Dialogs {
        // [START dialogs]
        @UiField
        Button alert;
        @UiField
        Button confirm;
        @UiField
        Button prompt;
        @UiField
        Paragraph result;

        void init() {
            alert.addClickHandler(event -> Bootbox.alert("Hello from Bootbox!", () -> result.setText("The alert was closed.")));
            confirm.addClickHandler(event -> Bootbox.confirm("Are you sure?",
                    confirmed -> result.setText(confirmed ? "Confirmed." : "Cancelled.")));
            prompt.addClickHandler(event -> {
                // Bootbox 6 requires a title for prompts
                final PromptOptions options = PromptOptions.newOptions("");
                options.setTitle("What is your name?");
                options.setCallback(name -> result.setText(name == null ? "Prompt cancelled." : "Hello, " + name + "!"));
                Bootbox.prompt(options);
            });
        }
        // [END dialogs]
    }

    static class PlanDialog {
        // [START custom]
        @UiField
        Button open;
        @UiField
        Paragraph choice;

        void init() {
            open.addClickHandler(event -> {
                final DialogOptions options = DialogOptions.newOptions("Which plan do you want?");
                options.setTitle("Plans");
                options.setSize(BootboxSize.LARGE);
                options.setCloseButton(true);
                options.addButton("Free", "btn-secondary", () -> choice.setText("You chose the free plan."));
                options.addButton("Pro", "btn-primary", () -> choice.setText("You chose the pro plan."));
                Bootbox.dialog(options);
            });
        }
        // [END custom]
    }
}
