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

import org.gwtbootstrap5.client.ui.html.Paragraph;
import org.gwtbootstrap5.demo.client.ui.Example;
import org.gwtbootstrap5.extras.range.client.ui.RangeSlider;
import org.gwtbootstrap5.extras.range.client.ui.Slider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Arrays;

/**
 * Range slider demo page.
 */
public class RangePage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, RangePage> {
    }

    @UiTemplate("range/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Sliders> {
    }

    @UiTemplate("range/Ticks.ui.xml")
    interface TicksBinder extends UiBinder<Widget, SizeSlider> {
    }

    interface Sources extends ClientBundle {
        @Source("range/Basic.ui.xml")
        TextResource basic();

        @Source("range/Ticks.ui.xml")
        TextResource ticks();

        @Source("RangePage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example ticks;

    public RangePage() {
        initWidget(BINDER.createAndBindUi(this));
        final Sliders basicOwner = new Sliders();
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(basicOwner), SOURCES.basic());
        basicOwner.init();
        basic.addJava(SOURCES.java(), "basic");
        final SizeSlider ticksOwner = new SizeSlider();
        ticks.show(GWT.<TicksBinder>create(TicksBinder.class).createAndBindUi(ticksOwner), SOURCES.ticks());
        ticksOwner.init();
        ticks.addJava(SOURCES.java(), "ticks");
    }

    static class Sliders {
        // [START basic]
        @UiField
        Slider volume;
        @UiField
        RangeSlider price;
        @UiField
        Paragraph status;

        void init() {
            volume.addSlideHandler(event -> update());
            price.addSlideHandler(event -> update());
            volume.addValueChangeHandler(event -> update());
            price.addValueChangeHandler(event -> update());
            update();
        }

        private void update() {
            status.setText("Volume " + volume.getValue().intValue() + ", price from "
                    + (int) price.getValue().getMinValue() + " to " + (int) price.getValue().getMaxValue() + ".");
        }
        // [END basic]
    }

    static class SizeSlider {
        // [START ticks]
        @UiField
        Slider size;

        void init() {
            size.setTicks(Arrays.asList(0.0, 1.0, 2.0, 3.0));
            size.setTicksLabels(Arrays.asList("S", "M", "L", "XL"));
        }
        // [END ticks]
    }
}
