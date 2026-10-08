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
import org.gwtbootstrap5.client.ui.html.Paragraph;
import org.gwtbootstrap5.demo.client.ui.Example;
import org.gwtbootstrap5.extras.select.client.ui.MultipleSelect;
import org.gwtbootstrap5.extras.select.client.ui.Select;
import org.gwtbootstrap5.extras.select.client.ui.base.SelectBase;
import org.gwtbootstrap5.extras.select.client.ui.engines.SelectEngine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Select demo page: the same examples with the three engines, chosen with buttons that create the
 * selects again.
 */
public class SelectPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, SelectPage> {
    }

    @UiTemplate("select/Single.ui.xml")
    interface SingleBinder extends UiBinder<Widget, CountrySelect> {
    }

    @UiTemplate("select/Multiple.ui.xml")
    interface MultipleBinder extends UiBinder<Widget, ToppingsSelect> {
    }

    @UiTemplate("select/Remote.ui.xml")
    interface RemoteBinder extends UiBinder<Widget, CitySelect> {
    }

    @UiTemplate("select/Reattach.ui.xml")
    interface ReattachBinder extends UiBinder<Widget, ReattachedSelect> {
    }

    interface Sources extends ClientBundle {
        @Source("select/Single.ui.xml")
        TextResource single();

        @Source("select/Multiple.ui.xml")
        TextResource multiple();

        @Source("select/Remote.ui.xml")
        TextResource remote();

        @Source("select/Reattach.ui.xml")
        TextResource reattach();

        @Source("SelectPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);
    private static final SingleBinder SINGLE = GWT.create(SingleBinder.class);
    private static final MultipleBinder MULTIPLE = GWT.create(MultipleBinder.class);
    private static final RemoteBinder REMOTE = GWT.create(RemoteBinder.class);
    private static final ReattachBinder REATTACH = GWT.create(ReattachBinder.class);

    @UiField
    Button tomSelect;
    @UiField
    Button choices;
    @UiField
    Button slimSelect;
    @UiField
    Example single;
    @UiField
    Example multiple;
    @UiField
    Example remote;
    @UiField
    Example reattach;

    public SelectPage() {
        initWidget(BINDER.createAndBindUi(this));

        single.show(createSingle(SelectEngine.TOMSELECT), SOURCES.single());
        single.addJava(SOURCES.java(), "single");
        multiple.show(createMultiple(SelectEngine.TOMSELECT), SOURCES.multiple());
        multiple.addJava(SOURCES.java(), "multiple");
        remote.show(createRemote(SelectEngine.TOMSELECT), SOURCES.remote());
        remote.addJava(SOURCES.java(), "remote");
        reattach.show(createReattach(SelectEngine.TOMSELECT), SOURCES.reattach());
        reattach.addJava(SOURCES.java(), "reattach");

        tomSelect.addClickHandler(event -> useEngine(SelectEngine.TOMSELECT));
        choices.addClickHandler(event -> useEngine(SelectEngine.CHOICESJS));
        slimSelect.addClickHandler(event -> useEngine(SelectEngine.SLIMSELECT));
    }

    // The old selects are detached, which destroys their JavaScript select, and new ones are created
    // with the engine; an engine can't change once a select is attached
    private void useEngine(final SelectEngine engine) {
        tomSelect.setActive(engine == SelectEngine.TOMSELECT);
        choices.setActive(engine == SelectEngine.CHOICESJS);
        slimSelect.setActive(engine == SelectEngine.SLIMSELECT);

        single.setLive(createSingle(engine));
        multiple.setLive(createMultiple(engine));
        remote.setLive(createRemote(engine));
        reattach.setLive(createReattach(engine));
    }

    private static Widget createSingle(final SelectEngine engine) {
        final CountrySelect owner = new CountrySelect();
        final Widget widget = SINGLE.createAndBindUi(owner);
        owner.init(engine);
        return widget;
    }

    private static Widget createMultiple(final SelectEngine engine) {
        final ToppingsSelect owner = new ToppingsSelect();
        final Widget widget = MULTIPLE.createAndBindUi(owner);
        owner.init(engine);
        return widget;
    }

    private static Widget createRemote(final SelectEngine engine) {
        final CitySelect owner = new CitySelect();
        final Widget widget = REMOTE.createAndBindUi(owner);
        owner.init(engine);
        return widget;
    }

    private static Widget createReattach(final SelectEngine engine) {
        final ReattachedSelect owner = new ReattachedSelect();
        final Widget widget = REATTACH.createAndBindUi(owner);
        owner.init(engine);
        return widget;
    }

    static class CountrySelect {
        // [START single]
        @UiField
        Select<String> country;
        @UiField
        Paragraph status;

        void init(final SelectEngine engine) {
            // Not needed when the app inherits a single engine
            country.setEngine(engine);
            country.setOptions(Arrays.asList("Argentina", "Brazil", "Canada", "France", "Germany", "Japan", "Mexico", "Spain"));
            country.addValueChangeHandler(event -> status.setText("Selected: " + event.getValue() + "."));
        }
        // [END single]
    }

    static class ToppingsSelect {
        // [START multiple]
        static class Topping {
            final String id;
            final String name;
            final double price;

            Topping(final String id, final String name, final double price) {
                this.id = id;
                this.name = name;
                this.price = price;
            }
        }

        @UiField
        MultipleSelect<Topping> toppings;
        @UiField
        Paragraph status;

        void init(final SelectEngine engine) {
            toppings.setEngine(engine);
            toppings.setItemProvider(new SelectBase.ItemProvider<Topping>() {
                @Override
                public String getValue(final Topping item) {
                    return item.id;
                }

                @Override
                public String getText(final Topping item) {
                    return item.name + " (" + item.price + " \u20ac)";
                }
            });
            toppings.setMultipleLimit(3);
            toppings.setOptions(Arrays.asList(new Topping("ham", "Ham", 1.5), new Topping("mushrooms", "Mushrooms", 1.0),
                    new Topping("olives", "Olives", 0.75), new Topping("onion", "Onion", 0.5), new Topping("pepper", "Pepper", 0.5)));
            toppings.addValuesChangeHandler(event -> {
                final List<Topping> picked = event.getValue();
                double total = 0;
                for (final Topping topping : picked) {
                    total += topping.price;
                }
                status.setText(picked.size() + " toppings, " + total + " \u20ac.");
            });
        }
        // [END multiple]
    }

    static class CitySelect {
        // [START remote]
        static final List<String> CITIES = Arrays.asList("Amsterdam", "Athens", "Barcelona", "Berlin", "Bogot\u00e1",
                "Buenos Aires", "Cairo", "Lagos", "Lima", "Lisbon", "London", "Madrid", "Melbourne", "Mexico City",
                "Montreal", "Mumbai", "Nairobi", "New York", "Osaka", "Paris", "Prague", "Rome", "San Francisco",
                "Santiago", "Seoul", "Sydney", "Tokyo", "Toronto", "Valencia", "Vienna");

        // The options come from asyncDataLoad, for each search, instead of setOptions
        @UiField(provided = true)
        Select<String> city = new Select<String>() {
            @Override
            protected void asyncDataLoad(final String query, final AsyncDataLoadCallback<String> callback) {
                // A server would answer this; here, a list and a delay stand in for it
                new Timer() {
                    @Override
                    public void run() {
                        final List<String> found = new ArrayList<>();
                        for (final String name : CITIES) {
                            if (name.toLowerCase().contains(query.toLowerCase()) && found.size() < 8) {
                                found.add(name);
                            }
                        }
                        callback.onResult(found);
                    }
                }.schedule(300);
            }
        };
        @UiField
        Paragraph status;

        void init(final SelectEngine engine) {
            city.setEngine(engine);
            city.addValueChangeHandler(event -> status.setText("Selected: " + event.getValue() + "."));
        }
        // [END remote]
    }

    static class ReattachedSelect {
        // [START reattach]
        @UiField
        FlowPanel holder;
        @UiField
        Select<String> size;
        @UiField
        Button reattach;

        void init(final SelectEngine engine) {
            size.setEngine(engine);
            size.setOptions(Arrays.asList("Small", "Medium", "Large"));
            size.setValue("Medium");
            // Removing the select destroys its JavaScript select; adding it again creates a new one
            // with the same options and value
            reattach.addClickHandler(event -> {
                holder.remove(size);
                holder.add(size);
            });
        }
        // [END reattach]
    }
}
