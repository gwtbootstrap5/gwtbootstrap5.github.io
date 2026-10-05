package org.gwtbootstrap5.demo.client.shell;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap5.client.ui.AnchorListItem;
import org.gwtbootstrap5.client.ui.Nav;
import org.gwtbootstrap5.client.ui.NavItem;
import org.gwtbootstrap5.client.ui.NavLink;
import org.gwtbootstrap5.client.ui.NavbarDropdownButton;
import org.gwtbootstrap5.client.ui.Offcanvas;
import org.gwtbootstrap5.client.ui.Spinner;
import org.gwtbootstrap5.client.ui.constants.IconTypeBI;
import org.gwtbootstrap5.client.ui.constants.SpinnerType;
import org.gwtbootstrap5.demo.client.nav.Page;
import org.gwtbootstrap5.demo.client.nav.Pages;
import org.gwtbootstrap5.demo.client.nav.Router;
import org.gwtbootstrap5.demo.client.nav.Section;
import org.gwtbootstrap5.demo.client.shell.ColorModes.Choice;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The page around every demo page: the navbar, the menu (an offcanvas below the {@code md}
 * breakpoint, a sidebar from it up) and the content area.
 */
public class DemoShell extends Composite implements Router.Display {

    interface Binder extends UiBinder<HTMLPanel, DemoShell> {
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final String TITLE = "GwtBootstrap5";

    @UiField
    Offcanvas sidebar;
    @UiField
    FlowPanel menu;
    @UiField
    SimplePanel content;
    @UiField
    NavbarDropdownButton colorModeButton;
    @UiField
    AnchorListItem light;
    @UiField
    AnchorListItem dark;
    @UiField
    AnchorListItem auto;

    private final Map<String, NavLink> links = new HashMap<>();

    public DemoShell() {
        initWidget(BINDER.createAndBindUi(this));
        sidebar.setId("demo-sidebar");
        buildMenu();
        initColorModeMenu();
    }

    private void buildMenu() {
        for (final Section section : Section.values()) {
            final List<Page> pages = Pages.of(section);
            if (pages.isEmpty()) {
                continue;
            }
            final HTML heading = new HTML(section.getTitle());
            heading.setStyleName("demo-menu-heading");
            menu.add(heading);
            final Nav nav = new Nav();
            nav.setVertical(true);
            for (final Page page : pages) {
                final NavLink link = new NavLink();
                link.setText(page.getTitle());
                link.setTargetHistoryToken(page.getToken());
                // Below md the menu is an offcanvas: close it once a page is chosen
                link.addClickHandler(event -> {
                    if (sidebar.isShown()) {
                        sidebar.hide();
                    }
                });
                final NavItem item = new NavItem();
                item.add(link);
                nav.add(item);
                links.put(page.getToken(), link);
            }
            menu.add(nav);
        }
    }

    private void initColorModeMenu() {
        colorModeButton.setIcon(IconTypeBI.CIRCLE_HALF);
        light.setIcon(IconTypeBI.SUN_FILL);
        dark.setIcon(IconTypeBI.MOON_STARS_FILL);
        auto.setIcon(IconTypeBI.CIRCLE_HALF);
        light.addClickHandler(event -> chooseColorMode(Choice.LIGHT));
        dark.addClickHandler(event -> chooseColorMode(Choice.DARK));
        auto.addClickHandler(event -> chooseColorMode(Choice.AUTO));
        markColorMode(ColorModes.current());
    }

    private void chooseColorMode(final Choice choice) {
        ColorModes.choose(choice);
        markColorMode(choice);
    }

    private void markColorMode(final Choice choice) {
        light.setActive(choice == Choice.LIGHT);
        dark.setActive(choice == Choice.DARK);
        auto.setActive(choice == Choice.AUTO);
    }

    @Override
    public void showLoading(final Page page) {
        for (final Map.Entry<String, NavLink> entry : links.entrySet()) {
            entry.getValue().setActive(entry.getKey().equals(page.getToken()));
        }
        Window.setTitle(page == Pages.HOME ? TITLE : page.getTitle() + " · " + TITLE);
        final Spinner spinner = new Spinner(SpinnerType.BORDER);
        spinner.addStyleName("m-5");
        content.setWidget(spinner);
    }

    @Override
    public void showPage(final Page page, final Widget widget) {
        content.setWidget(widget);
        Window.scrollTo(0, 0);
    }

    @Override
    public void showError(final Page page, final Throwable reason) {
        final HTML error = new HTML();
        error.setStyleName("alert alert-danger m-4");
        error.setText("Could not load " + page.getTitle() + ": " + reason.getMessage());
        content.setWidget(error);
    }
}
