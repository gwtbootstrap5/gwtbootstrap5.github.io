package org.gwtbootstrap5.demo.client.pages.content;

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

import java.util.Arrays;
import java.util.List;

import org.gwtbootstrap5.client.ui.gwt.CellTable;
import org.gwtbootstrap5.demo.client.ui.Example;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Tables demo page.
 */
public class TablesPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, TablesPage> {
    }

    @UiTemplate("tables/Striped.ui.xml")
    interface StripedBinder extends UiBinder<Widget, StripedTable> {
    }

    @UiTemplate("tables/Bordered.ui.xml")
    interface BorderedBinder extends UiBinder<Widget, BorderedTable> {
    }

    @UiTemplate("tables/ResponsiveTable.ui.xml")
    interface ResponsiveTableBinder extends UiBinder<Widget, ResponsiveTable> {
    }

    interface Sources extends ClientBundle {
        @Source("tables/Striped.ui.xml")
        TextResource striped();

        @Source("tables/Bordered.ui.xml")
        TextResource bordered();

        @Source("tables/ResponsiveTable.ui.xml")
        TextResource responsiveTable();

        @Source("TablesPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example striped;
    @UiField
    Example bordered;
    @UiField
    Example responsiveTable;

    public TablesPage() {
        initWidget(BINDER.createAndBindUi(this));
        final StripedTable stripedOwner = new StripedTable();
        striped.show(GWT.<StripedBinder>create(StripedBinder.class).createAndBindUi(stripedOwner), SOURCES.striped());
        stripedOwner.init();
        striped.addJava(SOURCES.java(), "striped");
        final BorderedTable borderedOwner = new BorderedTable();
        bordered.show(GWT.<BorderedBinder>create(BorderedBinder.class).createAndBindUi(borderedOwner), SOURCES.bordered());
        borderedOwner.init();
        bordered.addJava(SOURCES.java(), "bordered");
        final ResponsiveTable responsiveTableOwner = new ResponsiveTable();
        responsiveTable.show(GWT.<ResponsiveTableBinder>create(ResponsiveTableBinder.class).createAndBindUi(responsiveTableOwner), SOURCES.responsiveTable());
        responsiveTableOwner.init();
        responsiveTable.addJava(SOURCES.java(), "responsiveTable");
    }

    static class StripedTable {
        // [START striped]
        @UiField
        CellTable<Person> table;

        void init() {
            table.addColumn(new TextColumn<Person>() {
                @Override
                public String getValue(final Person person) {
                    return person.name;
                }
            }, "Name");
            table.addColumn(new TextColumn<Person>() {
                @Override
                public String getValue(final Person person) {
                    return person.email;
                }
            }, "Email");
            table.addColumn(new TextColumn<Person>() {
                @Override
                public String getValue(final Person person) {
                    return person.role;
                }
            }, "Role");
            table.setRowData(PEOPLE);
        }
        // [END striped]
    }

    static class BorderedTable {
        @UiField
        CellTable<Person> table;

        void init() {
            // [START bordered]
            addColumns(table);
            table.setRowData(PEOPLE);
            // [END bordered]
        }
    }

    static class ResponsiveTable {
        @UiField
        CellTable<Person> table;

        void init() {
            // [START responsiveTable]
            addColumns(table);
            table.setRowData(PEOPLE);
            // [END responsiveTable]
        }
    }

    /**
     * A row of the example tables.
     */
    static final class Person {
        final String name;
        final String email;
        final String role;

        Person(final String name, final String email, final String role) {
            this.name = name;
            this.email = email;
            this.role = role;
        }
    }

    static final List<Person> PEOPLE = Arrays.asList(
            new Person("Ada Lovelace", "ada@example.com", "Admin"),
            new Person("Alan Turing", "alan@example.com", "Developer"),
            new Person("Grace Hopper", "grace@example.com", "Developer"),
            new Person("Edsger Dijkstra", "edsger@example.com", "Reviewer"),
            new Person("Barbara Liskov", "barbara@example.com", "Architect"));

    static void addColumns(final CellTable<Person> table) {
        table.addColumn(new TextColumn<Person>() {
            @Override
            public String getValue(final Person person) {
                return person.name;
            }
        }, "Name");
        table.addColumn(new TextColumn<Person>() {
            @Override
            public String getValue(final Person person) {
                return person.email;
            }
        }, "Email");
        table.addColumn(new TextColumn<Person>() {
            @Override
            public String getValue(final Person person) {
                return person.role;
            }
        }, "Role");
    }
}
