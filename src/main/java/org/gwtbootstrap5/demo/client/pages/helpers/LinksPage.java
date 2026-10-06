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

import org.gwtbootstrap5.client.ui.Anchor;
import org.gwtbootstrap5.client.ui.base.helper.StyleHelper;
import org.gwtbootstrap5.client.ui.constants.FocusRing;
import org.gwtbootstrap5.client.ui.constants.LinkColor;
import org.gwtbootstrap5.client.ui.constants.LinkOffset;
import org.gwtbootstrap5.client.ui.constants.LinkUnderline;
import org.gwtbootstrap5.client.ui.constants.TextBackground;
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
 * Links and text demo page.
 */
public class LinksPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, LinksPage> {
    }

    @UiTemplate("links/Colors.ui.xml")
    interface ColorsBinder extends UiBinder<Widget, LinkStyles> {
    }

    @UiTemplate("links/IconLink.ui.xml")
    interface IconLinkBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("links/FocusBg.ui.xml")
    interface FocusBgBinder extends UiBinder<Widget, FocusAndBackgrounds> {
    }

    interface Sources extends ClientBundle {
        @Source("links/Colors.ui.xml")
        TextResource colors();

        @Source("links/IconLink.ui.xml")
        TextResource iconLink();

        @Source("links/FocusBg.ui.xml")
        TextResource focusBg();

        @Source("LinksPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example colors;
    @UiField
    Example iconLink;
    @UiField
    Example focusBg;

    public LinksPage() {
        initWidget(BINDER.createAndBindUi(this));
        final LinkStyles colorsOwner = new LinkStyles();
        colors.show(GWT.<ColorsBinder>create(ColorsBinder.class).createAndBindUi(colorsOwner), SOURCES.colors());
        colorsOwner.init();
        colors.addJava(SOURCES.java(), "colors");
        iconLink.show(GWT.<IconLinkBinder>create(IconLinkBinder.class).createAndBindUi(this), SOURCES.iconLink());
        final FocusAndBackgrounds focusBgOwner = new FocusAndBackgrounds();
        focusBg.show(GWT.<FocusBgBinder>create(FocusBgBinder.class).createAndBindUi(focusBgOwner), SOURCES.focusBg());
        focusBgOwner.init();
        focusBg.addJava(SOURCES.java(), "focusBg");
    }

    static class LinkStyles {
        // [START colors]
        @UiField
        Anchor primary;
        @UiField
        Anchor danger;
        @UiField
        Anchor offset;
        @UiField
        Anchor faded;

        void init() {
            StyleHelper.addEnumStyleName(primary, LinkColor.PRIMARY);
            StyleHelper.addEnumStyleName(danger, LinkColor.DANGER);
            StyleHelper.addEnumStyleName(offset, LinkOffset.OFFSET_3);
            StyleHelper.addEnumStyleName(offset, LinkOffset.OFFSET_1_HOVER);
            StyleHelper.addEnumStyleName(faded, LinkUnderline.DEFAULT);
            StyleHelper.addEnumStyleName(faded, LinkUnderline.OPACITY_25);
            StyleHelper.addEnumStyleName(faded, LinkUnderline.OPACITY_100_HOVER);
        }
        // [END colors]
    }

    static class FocusAndBackgrounds {
        // [START focusBg]
        @UiField
        Anchor focus;
        @UiField
        HTMLPanel warning;
        @UiField
        HTMLPanel dark;

        void init() {
            StyleHelper.addEnumStyleName(focus, FocusRing.DANGER);
            StyleHelper.addEnumStyleName(warning, TextBackground.WARNING);
            StyleHelper.addEnumStyleName(dark, TextBackground.DARK);
        }
        // [END focusBg]
    }
}
