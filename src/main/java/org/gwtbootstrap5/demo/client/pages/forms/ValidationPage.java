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

import org.gwtbootstrap5.client.ui.Button;
import org.gwtbootstrap5.client.ui.Form;
import org.gwtbootstrap5.client.ui.Input;
import org.gwtbootstrap5.client.ui.TextBox;
import org.gwtbootstrap5.client.ui.form.validator.FieldMatchValidator;
import org.gwtbootstrap5.client.ui.form.validator.RegExValidator;
import org.gwtbootstrap5.client.ui.form.validator.SizeValidator;
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
 * Validation demo page.
 */
public class ValidationPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ValidationPage> {
    }

    @UiTemplate("validation/SignUp.ui.xml")
    interface SignUpBinder extends UiBinder<Widget, SignUpForm> {
    }

    interface Sources extends ClientBundle {
        @Source("validation/SignUp.ui.xml")
        TextResource signUp();

        @Source("ValidationPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example signUp;

    public ValidationPage() {
        initWidget(BINDER.createAndBindUi(this));
        final SignUpForm signUpOwner = new SignUpForm();
        signUp.show(GWT.<SignUpBinder>create(SignUpBinder.class).createAndBindUi(signUpOwner), SOURCES.signUp());
        signUpOwner.init();
        signUp.addJava(SOURCES.java(), "signUp");
    }

    static class SignUpForm {
        // [START signUp]
        @UiField
        Form form;
        @UiField
        TextBox email;
        @UiField
        Input password;
        @UiField
        Input confirm;
        @UiField
        Button submit;
        @UiField
        Button reset;

        void init() {
            email.addValidator(new RegExValidator("^[^@ ]+@[^@ ]+[.][^@ ]+$", "Enter a valid email address."));
            password.addValidator(new SizeValidator<>(8, 64, "Use at least 8 characters."));
            confirm.addValidator(new FieldMatchValidator<>(password, "The passwords don't match."));
            submit.addClickHandler(event -> form.validate());
            reset.addClickHandler(event -> form.reset());
        }
        // [END signUp]
    }
}
