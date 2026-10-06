package org.gwtbootstrap5.demo.client.pages.extras;

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

import org.gwtbootstrap5.client.ui.Button;
import org.gwtbootstrap5.client.ui.Pre;
import org.gwtbootstrap5.client.ui.html.Paragraph;
import org.gwtbootstrap5.demo.client.ui.Example;
import org.gwtbootstrap5.extras.summernote.client.ui.Summernote;

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
 * Summernote demo page.
 */
public class SummernotePage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, SummernotePage> {
    }

    @UiTemplate("summernote/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Editor> {
    }

    @UiTemplate("summernote/Air.ui.xml")
    interface AirBinder extends UiBinder<Widget, AirEditor> {
    }

    interface Sources extends ClientBundle {
        @Source("summernote/Basic.ui.xml")
        TextResource basic();

        @Source("summernote/Air.ui.xml")
        TextResource air();

        @Source("SummernotePage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example air;

    public SummernotePage() {
        initWidget(BINDER.createAndBindUi(this));
        final Editor basicOwner = new Editor();
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(basicOwner), SOURCES.basic());
        basicOwner.init();
        basic.addJava(SOURCES.java(), "basic");
        final AirEditor airOwner = new AirEditor();
        air.show(GWT.<AirBinder>create(AirBinder.class).createAndBindUi(airOwner), SOURCES.air());
        airOwner.init();
        air.addJava(SOURCES.java(), "air");
    }

    static class Editor {
        // [START basic]
        @UiField
        Summernote editor;
        @UiField
        Button show;
        @UiField
        Button fill;
        @UiField
        Pre html;

        void init() {
            show.addClickHandler(event -> html.setText(editor.isEmpty() ? "(empty)" : editor.getCode()));
            fill.addClickHandler(event -> editor.setCode("<p>Hello <b>GwtBootstrap5</b>!</p><ul><li>One</li><li>Two</li></ul>"));
        }
        // [END basic]
    }

    static class AirEditor {
        // [START air]
        @UiField
        Summernote air;
        @UiField
        Paragraph count;
        private int changes;

        void init() {
            air.addSummernoteChangeHandler(event -> {
                changes++;
                count.setText(changes + (changes == 1 ? " change." : " changes."));
            });
        }
        // [END air]
    }
}
