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

import com.google.gwt.user.client.ui.Widget;

/**
 * Creates the pages of the section; compiled into its own code fragment.
 */
public final class ComponentsPages {

    private ComponentsPages() {
    }

    public static Widget create(final String token) {
        switch (token) {
            case "components/alerts":
                return new AlertsPage();
            case "components/badges":
                return new BadgesPage();
            case "components/breadcrumbs":
                return new BreadcrumbsPage();
            case "components/buttons":
                return new ButtonsPage();
            case "components/button-groups":
                return new ButtonGroupsPage();
            case "components/cards":
                return new CardsPage();
            case "components/carousel":
                return new CarouselPage();
            case "components/close-button":
                return new CloseButtonPage();
            case "components/collapse":
                return new CollapsePage();
            case "components/dropdowns":
                return new DropdownsPage();
            case "components/icons":
                return new IconsPage();
            case "components/list-group":
                return new ListGroupPage();
            case "components/modal":
                return new ModalPage();
            case "components/navbar":
                return new NavbarPage();
            case "components/navs":
                return new NavsPage();
            case "components/tabs":
                return new TabsPage();
            case "components/offcanvas":
                return new OffcanvasPage();
            case "components/pagination":
                return new PaginationPage();
            case "components/placeholders":
                return new PlaceholdersPage();
            case "components/popovers":
                return new PopoversPage();
            case "components/progress":
                return new ProgressPage();
            case "components/scrollspy":
                return new ScrollspyPage();
            case "components/spinners":
                return new SpinnersPage();
            case "components/toasts":
                return new ToastsPage();
            case "components/tooltips":
                return new TooltipsPage();
            default:
                return new AccordionPage();
        }
    }
}
