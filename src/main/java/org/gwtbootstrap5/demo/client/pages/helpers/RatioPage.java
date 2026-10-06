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
 * Ratio demo page.
 */
public class RatioPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, RatioPage> {
    }

    @UiTemplate("ratio/Types.ui.xml")
    interface TypesBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("ratio/Image.ui.xml")
    interface ImageBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("ratio/Types.ui.xml")
        TextResource types();

        @Source("ratio/Image.ui.xml")
        TextResource image();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example types;
    @UiField
    Example image;

    public RatioPage() {
        initWidget(BINDER.createAndBindUi(this));
        types.show(GWT.<TypesBinder>create(TypesBinder.class).createAndBindUi(this), SOURCES.types());
        image.show(GWT.<ImageBinder>create(ImageBinder.class).createAndBindUi(this), SOURCES.image());
    }
}
