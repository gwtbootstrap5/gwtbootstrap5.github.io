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
import org.gwtbootstrap5.client.ui.Card;
import org.gwtbootstrap5.demo.client.ui.Example;
import org.gwtbootstrap5.extras.animate.client.ui.Animate;
import org.gwtbootstrap5.extras.animate.client.ui.constants.Animation;

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
 * Animate demo page.
 */
public class AnimatePage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, AnimatePage> {
    }

    @UiTemplate("animate/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Animations> {
    }

    interface Sources extends ClientBundle {
        @Source("animate/Basic.ui.xml")
        TextResource basic();

        @Source("AnimatePage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;

    public AnimatePage() {
        initWidget(BINDER.createAndBindUi(this));
        final Animations basicOwner = new Animations();
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(basicOwner), SOURCES.basic());
        basicOwner.init();
        basic.addJava(SOURCES.java(), "basic");
    }

    static class Animations {
        // [START basic]
        @UiField
        Button bounce;
        @UiField
        Button tada;
        @UiField
        Button shake;
        @UiField
        Button flip;
        @UiField
        Card card;

        void init() {
            bounce.addClickHandler(event -> play(Animation.BOUNCE));
            tada.addClickHandler(event -> play(Animation.TADA));
            shake.addClickHandler(event -> play(Animation.SHAKE_X));
            // Two times, 2 seconds each
            flip.addClickHandler(event -> Animate.removeAnimationOnEnd(card, Animate.animate(card, Animation.FLIP, 2, 2000)));
        }

        private void play(final Animation animation) {
            // The animation's classes stay until removed; remove them at the end so the card is clean
            Animate.removeAnimationOnEnd(card, Animate.animate(card, animation));
        }
        // [END basic]
    }
}
