package org.gwtbootstrap5.demo.client.pages.layout;

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
 * Grid system demo page.
 */
public class GridPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, GridPage> {
    }

    @UiTemplate("grid/Equal.ui.xml")
    interface EqualBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("grid/Responsive.ui.xml")
    interface ResponsiveBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("grid/RowCols.ui.xml")
    interface RowColsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("grid/OffsetOrder.ui.xml")
    interface OffsetOrderBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("grid/Alignment.ui.xml")
    interface AlignmentBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("grid/Containers.ui.xml")
    interface ContainersBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("grid/Equal.ui.xml")
        TextResource equal();

        @Source("grid/Responsive.ui.xml")
        TextResource responsive();

        @Source("grid/RowCols.ui.xml")
        TextResource rowCols();

        @Source("grid/OffsetOrder.ui.xml")
        TextResource offsetOrder();

        @Source("grid/Alignment.ui.xml")
        TextResource alignment();

        @Source("grid/Containers.ui.xml")
        TextResource containers();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example equal;
    @UiField
    Example responsive;
    @UiField
    Example rowCols;
    @UiField
    Example offsetOrder;
    @UiField
    Example alignment;
    @UiField
    Example containers;

    public GridPage() {
        initWidget(BINDER.createAndBindUi(this));
        equal.show(GWT.<EqualBinder>create(EqualBinder.class).createAndBindUi(this), SOURCES.equal());
        responsive.show(GWT.<ResponsiveBinder>create(ResponsiveBinder.class).createAndBindUi(this), SOURCES.responsive());
        rowCols.show(GWT.<RowColsBinder>create(RowColsBinder.class).createAndBindUi(this), SOURCES.rowCols());
        offsetOrder.show(GWT.<OffsetOrderBinder>create(OffsetOrderBinder.class).createAndBindUi(this), SOURCES.offsetOrder());
        alignment.show(GWT.<AlignmentBinder>create(AlignmentBinder.class).createAndBindUi(this), SOURCES.alignment());
        containers.show(GWT.<ContainersBinder>create(ContainersBinder.class).createAndBindUi(this), SOURCES.containers());
    }
}
