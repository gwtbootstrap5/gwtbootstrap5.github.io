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
 * Cards demo page.
 */
public class CardsPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, CardsPage> {
    }

    @UiTemplate("cards/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("cards/Parts.ui.xml")
    interface PartsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("cards/Images.ui.xml")
    interface ImagesBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("cards/Basic.ui.xml")
        TextResource basic();

        @Source("cards/Parts.ui.xml")
        TextResource parts();

        @Source("cards/Images.ui.xml")
        TextResource images();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example parts;
    @UiField
    Example images;

    public CardsPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        parts.show(GWT.<PartsBinder>create(PartsBinder.class).createAndBindUi(this), SOURCES.parts());
        images.show(GWT.<ImagesBinder>create(ImagesBinder.class).createAndBindUi(this), SOURCES.images());
    }
}
