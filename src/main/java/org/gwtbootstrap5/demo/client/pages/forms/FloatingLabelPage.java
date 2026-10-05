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
 * Floating labels demo page.
 */
public class FloatingLabelPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, FloatingLabelPage> {
    }

    @UiTemplate("floating/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("floating/Textarea.ui.xml")
    interface TextareaBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("floating/Select.ui.xml")
    interface SelectBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("floating/Layout.ui.xml")
    interface LayoutBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("floating/Basic.ui.xml")
        TextResource basic();

        @Source("floating/Textarea.ui.xml")
        TextResource textarea();

        @Source("floating/Select.ui.xml")
        TextResource select();

        @Source("floating/Layout.ui.xml")
        TextResource layout();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example textarea;
    @UiField
    Example select;
    @UiField
    Example layout;

    public FloatingLabelPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        textarea.show(GWT.<TextareaBinder>create(TextareaBinder.class).createAndBindUi(this), SOURCES.textarea());
        select.show(GWT.<SelectBinder>create(SelectBinder.class).createAndBindUi(this), SOURCES.select());
        layout.show(GWT.<LayoutBinder>create(LayoutBinder.class).createAndBindUi(this), SOURCES.layout());
    }
}
