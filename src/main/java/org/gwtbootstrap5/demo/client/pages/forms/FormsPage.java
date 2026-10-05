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
 * Forms demo page.
 */
public class FormsPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, FormsPage> {
    }

    @UiTemplate("forms/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("forms/Horizontal.ui.xml")
    interface HorizontalBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("forms/TextareaSelect.ui.xml")
    interface TextareaSelectBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("forms/Sizing.ui.xml")
    interface SizingBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("forms/Disabled.ui.xml")
    interface DisabledBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("forms/Basic.ui.xml")
        TextResource basic();

        @Source("forms/Horizontal.ui.xml")
        TextResource horizontal();

        @Source("forms/TextareaSelect.ui.xml")
        TextResource textareaSelect();

        @Source("forms/Sizing.ui.xml")
        TextResource sizing();

        @Source("forms/Disabled.ui.xml")
        TextResource disabled();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example horizontal;
    @UiField
    Example textareaSelect;
    @UiField
    Example sizing;
    @UiField
    Example disabled;

    public FormsPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        horizontal.show(GWT.<HorizontalBinder>create(HorizontalBinder.class).createAndBindUi(this), SOURCES.horizontal());
        textareaSelect.show(GWT.<TextareaSelectBinder>create(TextareaSelectBinder.class).createAndBindUi(this), SOURCES.textareaSelect());
        sizing.show(GWT.<SizingBinder>create(SizingBinder.class).createAndBindUi(this), SOURCES.sizing());
        disabled.show(GWT.<DisabledBinder>create(DisabledBinder.class).createAndBindUi(this), SOURCES.disabled());
    }
}
