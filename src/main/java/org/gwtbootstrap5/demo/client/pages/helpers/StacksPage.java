package org.gwtbootstrap5.demo.client.pages.helpers;

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
 * Stacks demo page.
 */
public class StacksPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, StacksPage> {
    }

    @UiTemplate("stacks/Vertical.ui.xml")
    interface VerticalBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("stacks/Horizontal.ui.xml")
    interface HorizontalBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("stacks/Form.ui.xml")
    interface FormBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("stacks/Vertical.ui.xml")
        TextResource vertical();

        @Source("stacks/Horizontal.ui.xml")
        TextResource horizontal();

        @Source("stacks/Form.ui.xml")
        TextResource form();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example vertical;
    @UiField
    Example horizontal;
    @UiField
    Example form;

    public StacksPage() {
        initWidget(BINDER.createAndBindUi(this));
        vertical.show(GWT.<VerticalBinder>create(VerticalBinder.class).createAndBindUi(this), SOURCES.vertical());
        horizontal.show(GWT.<HorizontalBinder>create(HorizontalBinder.class).createAndBindUi(this), SOURCES.horizontal());
        form.show(GWT.<FormBinder>create(FormBinder.class).createAndBindUi(this), SOURCES.form());
    }
}
