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
import org.gwtbootstrap5.client.ui.Modal;
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
 * Modal demo page.
 */
public class ModalPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ModalPage> {
    }

    @UiTemplate("modal/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("modal/Options.ui.xml")
    interface OptionsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("modal/FromJava.ui.xml")
    interface FromJavaBinder extends UiBinder<Widget, Question> {
    }

    interface Sources extends ClientBundle {
        @Source("modal/Basic.ui.xml")
        TextResource basic();

        @Source("modal/Options.ui.xml")
        TextResource options();

        @Source("modal/FromJava.ui.xml")
        TextResource fromJava();

        @Source("ModalPage.java")
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

    public ModalPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        options.show(GWT.<OptionsBinder>create(OptionsBinder.class).createAndBindUi(this), SOURCES.options());
        final Question fromJavaOwner = new Question();
        fromJava.show(GWT.<FromJavaBinder>create(FromJavaBinder.class).createAndBindUi(fromJavaOwner), SOURCES.fromJava());
        fromJavaOwner.init();
        fromJava.addJava(SOURCES.java(), "fromJava");
    }

    static class Question {
        // [START fromJava]
        @UiField
        Button open;
        @UiField
        Paragraph answer;
        @UiField
        Modal modal;
        @UiField
        Button no;
        @UiField
        Button yes;
        private String choice;

        void init() {
            open.addClickHandler(event -> {
                choice = "You closed the modal without answering.";
                modal.show();
            });
            yes.addClickHandler(event -> {
                choice = "You like modals.";
                modal.hide();
            });
            no.addClickHandler(event -> {
                choice = "You don't like modals.";
                modal.hide();
            });
            modal.addHiddenHandler(event -> answer.setText(choice));
        }
        // [END fromJava]
    }
}
