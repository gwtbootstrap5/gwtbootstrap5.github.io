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
 * Code demo page.
 */
public class CodePage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, CodePage> {
    }

    @UiTemplate("code/Inline.ui.xml")
    interface InlineBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("code/Block.ui.xml")
    interface BlockBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("code/Kbd.ui.xml")
    interface KbdBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("code/Inline.ui.xml")
        TextResource inline();

        @Source("code/Block.ui.xml")
        TextResource block();

        @Source("code/Kbd.ui.xml")
        TextResource kbd();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example inline;
    @UiField
    Example block;
    @UiField
    Example kbd;

    public CodePage() {
        initWidget(BINDER.createAndBindUi(this));
        inline.show(GWT.<InlineBinder>create(InlineBinder.class).createAndBindUi(this), SOURCES.inline());
        block.show(GWT.<BlockBinder>create(BlockBinder.class).createAndBindUi(this), SOURCES.block());
        kbd.show(GWT.<KbdBinder>create(KbdBinder.class).createAndBindUi(this), SOURCES.kbd());
    }
}
