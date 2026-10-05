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

import org.gwtbootstrap5.client.ui.Button;
import org.gwtbootstrap5.client.ui.Carousel;
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
 * Carousel demo page.
 */
public class CarouselPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, CarouselPage> {
    }

    @UiTemplate("carousel/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("carousel/FromJava.ui.xml")
    interface FromJavaBinder extends UiBinder<Widget, CarouselControls> {
    }

    interface Sources extends ClientBundle {
        @Source("carousel/Basic.ui.xml")
        TextResource basic();

        @Source("carousel/FromJava.ui.xml")
        TextResource fromJava();

        @Source("CarouselPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example fromJava;

    public CarouselPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        final CarouselControls fromJavaOwner = new CarouselControls();
        fromJava.show(GWT.<FromJavaBinder>create(FromJavaBinder.class).createAndBindUi(fromJavaOwner), SOURCES.fromJava());
        fromJavaOwner.init();
        fromJava.addJava(SOURCES.java(), "fromJava");
    }

    static class CarouselControls {
        // [START fromJava]
        @UiField
        Carousel carousel;
        @UiField
        Button prev;
        @UiField
        Button first;
        @UiField
        Button next;
        @UiField
        Span status;
        private int moves;

        void init() {
            prev.addClickHandler(event -> carousel.goToPrev());
            next.addClickHandler(event -> carousel.goToNext());
            first.addClickHandler(event -> carousel.jumpToSlide(0));
            carousel.addSlidHandler(event -> {
                moves++;
                status.setText(moves + (moves == 1 ? " slide moved." : " slides moved."));
            });
        }
        // [END fromJava]
    }
}
