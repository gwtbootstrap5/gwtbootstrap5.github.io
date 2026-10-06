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

import org.gwtbootstrap5.client.ui.Button;
import org.gwtbootstrap5.client.ui.Card;
import org.gwtbootstrap5.client.ui.base.helper.ColorModeHelper;
import org.gwtbootstrap5.client.ui.constants.ColorMode;
import org.gwtbootstrap5.client.ui.html.Paragraph;
import org.gwtbootstrap5.demo.client.ui.Example;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Color modes demo page.
 */
public class ColorModesPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ColorModesPage> {
    }

    @UiTemplate("colormodes/Widget.ui.xml")
    interface WidgetBinder extends UiBinder<Widget, DarkCard> {
    }

    @UiTemplate("colormodes/Page.ui.xml")
    interface PageBinder extends UiBinder<Widget, PageMode> {
    }

    interface Sources extends ClientBundle {
        @Source("colormodes/Widget.ui.xml")
        TextResource widget();

        @Source("colormodes/Page.ui.xml")
        TextResource page();

        @Source("ColorModesPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example widget;
    @UiField
    Example page;

    public ColorModesPage() {
        initWidget(BINDER.createAndBindUi(this));
        final DarkCard widgetOwner = new DarkCard();
        widget.show(GWT.<WidgetBinder>create(WidgetBinder.class).createAndBindUi(widgetOwner), SOURCES.widget());
        widgetOwner.init();
        widget.addJava(SOURCES.java(), "widget");
        final PageMode pageOwner = new PageMode();
        page.show(GWT.<PageBinder>create(PageBinder.class).createAndBindUi(pageOwner), SOURCES.page());
        pageOwner.init();
        page.addJava(SOURCES.java(), "page");
    }

    static class DarkCard {
        // [START widget]
        @UiField
        Card card;

        void init() {
            ColorModeHelper.setColorMode(card, ColorMode.DARK);
        }
        // [END widget]
    }

    static class PageMode {
        // [START page]
        @UiField
        Button light;
        @UiField
        Button dark;
        @UiField
        Button system;
        @UiField
        Paragraph status;
        private HandlerRegistration following;

        void init() {
            light.addClickHandler(event -> set(ColorMode.LIGHT));
            dark.addClickHandler(event -> set(ColorMode.DARK));
            system.addClickHandler(event -> {
                stopFollowing();
                following = ColorModeHelper.followSystem();
                status.setText("Following the system: " + ColorModeHelper.getPageColorMode() + ".");
            });
            status.setText("The page is " + ColorModeHelper.getPageColorMode() + ".");
        }

        private void set(final ColorMode mode) {
            stopFollowing();
            ColorModeHelper.setPageColorMode(mode);
            status.setText("The page is " + mode + ".");
        }

        private void stopFollowing() {
            if (following != null) {
                following.removeHandler();
                following = null;
            }
        }
        // [END page]
    }
}
