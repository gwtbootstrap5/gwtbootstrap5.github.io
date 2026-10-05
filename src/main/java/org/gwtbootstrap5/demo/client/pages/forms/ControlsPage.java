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
 * Checks, radios and other inputs demo page.
 */
public class ControlsPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ControlsPage> {
    }

    @UiTemplate("controls/Checks.ui.xml")
    interface ChecksBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("controls/Radios.ui.xml")
    interface RadiosBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("controls/Inline.ui.xml")
    interface InlineBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("controls/Range.ui.xml")
    interface RangeBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("controls/FileColor.ui.xml")
    interface FileColorBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("controls/Multiple.ui.xml")
    interface MultipleBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("controls/Checks.ui.xml")
        TextResource checks();

        @Source("controls/Radios.ui.xml")
        TextResource radios();

        @Source("controls/Inline.ui.xml")
        TextResource inline();

        @Source("controls/Range.ui.xml")
        TextResource range();

        @Source("controls/FileColor.ui.xml")
        TextResource fileColor();

        @Source("controls/Multiple.ui.xml")
        TextResource multiple();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example checks;
    @UiField
    Example radios;
    @UiField
    Example inline;
    @UiField
    Example range;
    @UiField
    Example fileColor;
    @UiField
    Example multiple;

    public ControlsPage() {
        initWidget(BINDER.createAndBindUi(this));
        checks.show(GWT.<ChecksBinder>create(ChecksBinder.class).createAndBindUi(this), SOURCES.checks());
        radios.show(GWT.<RadiosBinder>create(RadiosBinder.class).createAndBindUi(this), SOURCES.radios());
        inline.show(GWT.<InlineBinder>create(InlineBinder.class).createAndBindUi(this), SOURCES.inline());
        range.show(GWT.<RangeBinder>create(RangeBinder.class).createAndBindUi(this), SOURCES.range());
        fileColor.show(GWT.<FileColorBinder>create(FileColorBinder.class).createAndBindUi(this), SOURCES.fileColor());
        multiple.show(GWT.<MultipleBinder>create(MultipleBinder.class).createAndBindUi(this), SOURCES.multiple());
    }
}
