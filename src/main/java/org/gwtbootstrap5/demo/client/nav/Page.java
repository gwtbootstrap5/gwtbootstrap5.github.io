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

/**
 * An entry of the menu: a history token, the title shown in the menu and the window title, and
 * the section that creates it.
 */
public final class Page {
    private final String token;
    private final String title;
    private final Section section;

    Page(final Section section, final String token, final String title) {
        this.section = section;
        this.token = token;
        this.title = title;
    }

    public String getToken() {
        return token;
    }

    public String getTitle() {
        return title;
    }

    public Section getSection() {
        return section;
    }
}
