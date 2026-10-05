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
 * List group demo page.
 */
public class ListGroupPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ListGroupPage> {
    }

    @UiTemplate("listgroup/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("listgroup/Variants.ui.xml")
    interface VariantsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("listgroup/Links.ui.xml")
    interface LinksBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("listgroup/Basic.ui.xml")
        TextResource basic();

        @Source("listgroup/Variants.ui.xml")
        TextResource variants();

        @Source("listgroup/Links.ui.xml")
        TextResource links();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example variants;
    @UiField
    Example links;

    public ListGroupPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        variants.show(GWT.<VariantsBinder>create(VariantsBinder.class).createAndBindUi(this), SOURCES.variants());
        links.show(GWT.<LinksBinder>create(LinksBinder.class).createAndBindUi(this), SOURCES.links());
    }
}
