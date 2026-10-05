package org.gwtbootstrap5.demo.client.pages.components;

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
import org.gwtbootstrap5.client.ui.constants.IconTypeBI;
import org.gwtbootstrap5.client.ui.html.Span;
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
 * Icons demo page.
 */
public class IconsPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, IconsPage> {
    }

    @UiTemplate("icons/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("icons/Widgets.ui.xml")
    interface WidgetsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("icons/Clicks.ui.xml")
    interface ClicksBinder extends UiBinder<Widget, LikeIcon> {
    }

    interface Sources extends ClientBundle {
        @Source("icons/Basic.ui.xml")
        TextResource basic();

        @Source("icons/Widgets.ui.xml")
        TextResource widgets();

        @Source("icons/Clicks.ui.xml")
        TextResource clicks();

        @Source("IconsPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example widgets;
    @UiField
    Example clicks;

    public IconsPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        widgets.show(GWT.<WidgetsBinder>create(WidgetsBinder.class).createAndBindUi(this), SOURCES.widgets());
        final LikeIcon clicksOwner = new LikeIcon();
        clicks.show(GWT.<ClicksBinder>create(ClicksBinder.class).createAndBindUi(clicksOwner), SOURCES.clicks());
        clicksOwner.init();
        clicks.addJava(SOURCES.java(), "clicks");
    }

    static class LikeIcon {
        // [START clicks]
        @UiField
        Icon like;
        @UiField
        Span likes;
        private boolean liked;

        void init() {
            like.getElement().setAttribute("role", "button");
            like.getElement().setAttribute("aria-label", "Like");
            like.getElement().setTabIndex(0);
            like.addClickHandler(event -> {
                liked = !liked;
                like.setType(liked ? IconTypeBI.HAND_THUMBS_UP_FILL : IconTypeBI.HAND_THUMBS_UP);
                likes.setText(liked ? "1 like" : "0 likes");
            });
        }
        // [END clicks]
    }
}
