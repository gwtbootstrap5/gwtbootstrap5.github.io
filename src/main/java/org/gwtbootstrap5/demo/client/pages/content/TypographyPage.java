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
 * Typography demo page.
 */
public class TypographyPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, TypographyPage> {
    }

    @UiTemplate("typography/Headings.ui.xml")
    interface HeadingsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("typography/Display.ui.xml")
    interface DisplayBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("typography/Lead.ui.xml")
    interface LeadBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("typography/Inline.ui.xml")
    interface InlineBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("typography/Quote.ui.xml")
    interface QuoteBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("typography/Description.ui.xml")
    interface DescriptionBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("typography/Headings.ui.xml")
        TextResource headings();

        @Source("typography/Display.ui.xml")
        TextResource display();

        @Source("typography/Lead.ui.xml")
        TextResource lead();

        @Source("typography/Inline.ui.xml")
        TextResource inline();

        @Source("typography/Quote.ui.xml")
        TextResource quote();

        @Source("typography/Description.ui.xml")
        TextResource description();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example headings;
    @UiField
    Example display;
    @UiField
    Example lead;
    @UiField
    Example inline;
    @UiField
    Example quote;
    @UiField
    Example description;

    public TypographyPage() {
        initWidget(BINDER.createAndBindUi(this));
        headings.show(GWT.<HeadingsBinder>create(HeadingsBinder.class).createAndBindUi(this), SOURCES.headings());
        display.show(GWT.<DisplayBinder>create(DisplayBinder.class).createAndBindUi(this), SOURCES.display());
        lead.show(GWT.<LeadBinder>create(LeadBinder.class).createAndBindUi(this), SOURCES.lead());
        inline.show(GWT.<InlineBinder>create(InlineBinder.class).createAndBindUi(this), SOURCES.inline());
        quote.show(GWT.<QuoteBinder>create(QuoteBinder.class).createAndBindUi(this), SOURCES.quote());
        description.show(GWT.<DescriptionBinder>create(DescriptionBinder.class).createAndBindUi(this), SOURCES.description());
    }
}
