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
 * Input group demo page.
 */
public class InputGroupPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, InputGroupPage> {
    }

    @UiTemplate("inputgroup/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("inputgroup/Buttons.ui.xml")
    interface ButtonsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("inputgroup/Sizes.ui.xml")
    interface SizesBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("inputgroup/Basic.ui.xml")
        TextResource basic();

        @Source("inputgroup/Buttons.ui.xml")
        TextResource buttons();

        @Source("inputgroup/Sizes.ui.xml")
        TextResource sizes();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example buttons;
    @UiField
    Example sizes;

    public InputGroupPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        buttons.show(GWT.<ButtonsBinder>create(ButtonsBinder.class).createAndBindUi(this), SOURCES.buttons());
        sizes.show(GWT.<SizesBinder>create(SizesBinder.class).createAndBindUi(this), SOURCES.sizes());
    }
}
