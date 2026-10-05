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

import org.gwtbootstrap5.client.ui.Card;
import org.gwtbootstrap5.client.ui.base.helper.StyleHelper;
import org.gwtbootstrap5.client.ui.constants.PlaceholderAnimation;
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
 * Placeholders demo page.
 */
public class PlaceholdersPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, PlaceholdersPage> {
    }

    @UiTemplate("placeholders/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("placeholders/Card.ui.xml")
    interface CardBinder extends UiBinder<Widget, LoadingCard> {
    }

    interface Sources extends ClientBundle {
        @Source("placeholders/Basic.ui.xml")
        TextResource basic();

        @Source("placeholders/Card.ui.xml")
        TextResource card();

        @Source("PlaceholdersPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example card;

    public PlaceholdersPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        final LoadingCard cardOwner = new LoadingCard();
        card.show(GWT.<CardBinder>create(CardBinder.class).createAndBindUi(cardOwner), SOURCES.card());
        cardOwner.init();
        card.addJava(SOURCES.java(), "card");
    }

    static class LoadingCard {
        // [START card]
        @UiField
        Card card;

        void init() {
            StyleHelper.setPlaceholderAnimation(card, PlaceholderAnimation.GLOW);
        }
        // [END card]
    }
}
