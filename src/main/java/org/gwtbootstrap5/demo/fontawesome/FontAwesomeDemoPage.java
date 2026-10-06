package org.gwtbootstrap5.demo.fontawesome;

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

import org.gwtbootstrap5.client.ui.Icon;
import org.gwtbootstrap5.demo.client.ui.Example;
import org.gwtbootstrap5.extras.fontawesome.client.ui.IconTypeFABrands;
import org.gwtbootstrap5.extras.fontawesome.client.ui.IconTypeFARegular;

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
 * Font Awesome demo page.
 */
public class FontAwesomeDemoPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, FontAwesomeDemoPage> {
    }

    @UiTemplate("examples/Styles.ui.xml")
    interface StylesBinder extends UiBinder<Widget, IconStyles> {
    }

    @UiTemplate("examples/Options.ui.xml")
    interface OptionsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("examples/Stack.ui.xml")
    interface StackBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("examples/Widgets.ui.xml")
    interface WidgetsBinder extends UiBinder<Widget, Object> {
    }

    interface Sources extends ClientBundle {
        @Source("examples/Styles.ui.xml")
        TextResource styles();

        @Source("examples/Options.ui.xml")
        TextResource options();

        @Source("examples/Stack.ui.xml")
        TextResource stack();

        @Source("examples/Widgets.ui.xml")
        TextResource widgets();

        @Source("FontAwesomeDemoPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example styles;
    @UiField
    Example options;
    @UiField
    Example stack;
    @UiField
    Example widgets;

    public FontAwesomeDemoPage() {
        initWidget(BINDER.createAndBindUi(this));
        final IconStyles stylesOwner = new IconStyles();
        styles.show(GWT.<StylesBinder>create(StylesBinder.class).createAndBindUi(stylesOwner), SOURCES.styles());
        stylesOwner.init();
        styles.addJava(SOURCES.java(), "styles");
        options.show(GWT.<OptionsBinder>create(OptionsBinder.class).createAndBindUi(this), SOURCES.options());
        stack.show(GWT.<StackBinder>create(StackBinder.class).createAndBindUi(this), SOURCES.stack());
        widgets.show(GWT.<WidgetsBinder>create(WidgetsBinder.class).createAndBindUi(this), SOURCES.widgets());
    }

    static class IconStyles {
        // [START styles]
        @UiField
        Icon regular;
        @UiField
        Icon regularStar;
        @UiField
        Icon github;

        void init() {
            regular.setType(IconTypeFARegular.HEART);
            regularStar.setType(IconTypeFARegular.STAR);
            github.setType(IconTypeFABrands.GITHUB);
        }
        // [END styles]
    }
}
