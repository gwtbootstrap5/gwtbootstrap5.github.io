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
import org.gwtbootstrap5.extras.select.client.ui.MultipleSelect;
import org.gwtbootstrap5.extras.select.client.ui.Select;
import org.gwtbootstrap5.extras.select.client.ui.base.SelectBase;

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
import java.util.List;

/**
 * Select demo page.
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

    interface Sources extends ClientBundle {
        @Source("select/Single.ui.xml")
        TextResource single();

        @Source("select/Multiple.ui.xml")
        TextResource multiple();

        @Source("SelectPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example single;
    @UiField
    Example multiple;

    public SelectPage() {
        initWidget(BINDER.createAndBindUi(this));
        final CountrySelect singleOwner = new CountrySelect();
        single.show(GWT.<SingleBinder>create(SingleBinder.class).createAndBindUi(singleOwner), SOURCES.single());
        singleOwner.init();
        single.addJava(SOURCES.java(), "single");
        final ToppingsSelect multipleOwner = new ToppingsSelect();
        multiple.show(GWT.<MultipleBinder>create(MultipleBinder.class).createAndBindUi(multipleOwner), SOURCES.multiple());
        multipleOwner.init();
        multiple.addJava(SOURCES.java(), "multiple");
    }

    static class CountrySelect {
        // [START single]
        @UiField
        Select<String> country;
        @UiField
        Paragraph status;

        void init() {
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

        void init() {
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
}
