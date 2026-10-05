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
import org.gwtbootstrap5.client.ui.ProgressBar;
import org.gwtbootstrap5.demo.client.ui.Example;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Progress demo page.
 */
public class ProgressPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, ProgressPage> {
    }

    @UiTemplate("progress/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("progress/Striped.ui.xml")
    interface StripedBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("progress/Live.ui.xml")
    interface LiveBinder extends UiBinder<Widget, Upload> {
    }

    interface Sources extends ClientBundle {
        @Source("progress/Basic.ui.xml")
        TextResource basic();

        @Source("progress/Striped.ui.xml")
        TextResource striped();

        @Source("progress/Live.ui.xml")
        TextResource live();

        @Source("ProgressPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example striped;
    @UiField
    Example live;

    public ProgressPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        striped.show(GWT.<StripedBinder>create(StripedBinder.class).createAndBindUi(this), SOURCES.striped());
        final Upload liveOwner = new Upload();
        live.show(GWT.<LiveBinder>create(LiveBinder.class).createAndBindUi(liveOwner), SOURCES.live());
        liveOwner.init();
        live.addJava(SOURCES.java(), "live");
    }

    static class Upload {
        // [START live]
        @UiField
        ProgressBar bar;
        @UiField
        Button start;

        void init() {
            start.addClickHandler(event -> {
                start.setEnabled(false);
                new Timer() {
                    private int percent;

                    @Override
                    public void run() {
                        percent += 5;
                        bar.setPercent(percent);
                        bar.setText(percent + "%");
                        if (percent >= 100) {
                            cancel();
                            start.setEnabled(true);
                            percent = 0;
                        }
                    }
                }.scheduleRepeating(100);
            });
        }
        // [END live]
    }
}
