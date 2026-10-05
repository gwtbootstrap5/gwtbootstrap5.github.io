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
 * Responsive utilities demo page.
 */
public class ResponsivePage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ResponsivePage> {
    }

    @UiTemplate("responsive/Hidden.ui.xml")
    interface HiddenBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("responsive/Visible.ui.xml")
    interface VisibleBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("responsive/Print.ui.xml")
    interface PrintBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("responsive/Hidden.ui.xml")
        TextResource hidden();

        @Source("responsive/Visible.ui.xml")
        TextResource visible();

        @Source("responsive/Print.ui.xml")
        TextResource print();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example hidden;
    @UiField
    Example visible;
    @UiField
    Example print;

    public ResponsivePage() {
        initWidget(BINDER.createAndBindUi(this));
        hidden.show(GWT.<HiddenBinder>create(HiddenBinder.class).createAndBindUi(this), SOURCES.hidden());
        visible.show(GWT.<VisibleBinder>create(VisibleBinder.class).createAndBindUi(this), SOURCES.visible());
        print.show(GWT.<PrintBinder>create(PrintBinder.class).createAndBindUi(this), SOURCES.print());
    }
}
