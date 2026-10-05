package org.gwtbootstrap5.demo.client.pages.content;

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
 * Images demo page.
 */
public class ImagesPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ImagesPage> {
    }

    @UiTemplate("images/Fluid.ui.xml")
    interface FluidBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("images/Types.ui.xml")
    interface TypesBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("images/Figure.ui.xml")
    interface FigureBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("images/Fluid.ui.xml")
        TextResource fluid();

        @Source("images/Types.ui.xml")
        TextResource types();

        @Source("images/Figure.ui.xml")
        TextResource figure();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example fluid;
    @UiField
    Example types;
    @UiField
    Example figure;

    public ImagesPage() {
        initWidget(BINDER.createAndBindUi(this));
        fluid.show(GWT.<FluidBinder>create(FluidBinder.class).createAndBindUi(this), SOURCES.fluid());
        types.show(GWT.<TypesBinder>create(TypesBinder.class).createAndBindUi(this), SOURCES.types());
        figure.show(GWT.<FigureBinder>create(FigureBinder.class).createAndBindUi(this), SOURCES.figure());
    }
}
