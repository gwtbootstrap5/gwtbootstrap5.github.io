package org.gwtbootstrap5.demo.client.nav;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Every page of the demo, in menu order. A section's page tokens must match the ones its
 * {@code *Pages.create(String)} factory knows.
 */
public final class Pages {

    public static final Page HOME = new Page(Section.GENERAL, "home", "Home");

    private static final List<Page> ALL = new ArrayList<>();

    static {
        add(HOME);
        add(new Page(Section.GENERAL, "setup", "Setup"));

        add(new Page(Section.LAYOUT, "layout/grid", "Grid system"));
        add(new Page(Section.LAYOUT, "layout/responsive", "Responsive utilities"));

        add(new Page(Section.CONTENT, "content/typography", "Typography"));
        add(new Page(Section.CONTENT, "content/code", "Code"));
        add(new Page(Section.CONTENT, "content/images", "Images"));
        add(new Page(Section.CONTENT, "content/tables", "Tables"));

        add(new Page(Section.FORMS, "forms/overview", "Forms"));
        add(new Page(Section.FORMS, "forms/controls", "Checks, radios and inputs"));
        add(new Page(Section.FORMS, "forms/input-group", "Input group"));
        add(new Page(Section.FORMS, "forms/floating-labels", "Floating labels"));
        add(new Page(Section.FORMS, "forms/switch", "Switches"));
        add(new Page(Section.FORMS, "forms/validation", "Validation"));
    }

    private Pages() {
    }

    private static void add(final Page page) {
        ALL.add(page);
    }

    /**
     * @return every page, in menu order
     */
    public static List<Page> all() {
        return Collections.unmodifiableList(ALL);
    }

    /**
     * @return the pages of a section, in menu order
     */
    public static List<Page> of(final Section section) {
        final List<Page> pages = new ArrayList<>();
        for (final Page page : ALL) {
            if (page.getSection() == section) {
                pages.add(page);
            }
        }
        return pages;
    }

    /**
     * @return the page with the token, or {@code null}
     */
    public static Page find(final String token) {
        for (final Page page : ALL) {
            if (page.getToken().equals(token)) {
                return page;
            }
        }
        return null;
    }
}
