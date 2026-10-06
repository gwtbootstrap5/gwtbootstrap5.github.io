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
import org.gwtbootstrap5.extras.datepicker.client.ui.DatePicker;
import org.gwtbootstrap5.extras.datetimepicker.client.ui.DateTimePicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;

/**
 * Date and time pickers demo page.
 */
public class DateTimePickersPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, DateTimePickersPage> {
    }

    @UiTemplate("datetimepickers/Tempus.ui.xml")
    interface TempusBinder extends UiBinder<Widget, TempusPickers> {
    }

    @UiTemplate("datetimepickers/Air.ui.xml")
    interface AirBinder extends UiBinder<Widget, AirPickers> {
    }

    interface Sources extends ClientBundle {
        @Source("datetimepickers/Tempus.ui.xml")
        TextResource tempus();

        @Source("datetimepickers/Air.ui.xml")
        TextResource air();

        @Source("DateTimePickersPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example tempus;
    @UiField
    Example air;

    public DateTimePickersPage() {
        initWidget(BINDER.createAndBindUi(this));
        final TempusPickers tempusOwner = new TempusPickers();
        tempus.show(GWT.<TempusBinder>create(TempusBinder.class).createAndBindUi(tempusOwner), SOURCES.tempus());
        tempusOwner.init();
        tempus.addJava(SOURCES.java(), "tempus");
        final AirPickers airOwner = new AirPickers();
        air.show(GWT.<AirBinder>create(AirBinder.class).createAndBindUi(airOwner), SOURCES.air());
        airOwner.init();
        air.addJava(SOURCES.java(), "air");
    }

    static class TempusPickers {
        // [START tempus]
        @UiField
        DatePicker date;
        @UiField
        Paragraph picked;

        void init() {
            final DateTimeFormat format = DateTimeFormat.getFormat("d MMMM yyyy");
            date.addValueChangeHandler(event -> picked.setText(event.getValue() == null
                    ? "No date picked." : "Picked " + format.format(event.getValue()) + "."));
        }
        // [END tempus]
    }

    static class AirPickers {
        // [START air]
        @UiField
        DatePicker limited;
        @UiField
        DateTimePicker stepped;

        @SuppressWarnings("deprecation")
        void init() {
            final Date today = new Date();
            final Date first = new Date(today.getYear(), today.getMonth(), 1);
            final Date last = new Date(today.getYear(), today.getMonth() + 1, 0);
            limited.setMinDate(first);
            limited.setMaxDate(last);
            limited.setLocale("es");
            limited.setShowTodayButton(true);
            limited.setShowClearButton(true);
            stepped.setMinuteStep(15);
        }
        // [END air]
    }
}
