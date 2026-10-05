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

import org.gwtbootstrap5.demo.client.pages.general.GeneralPages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * A group of pages in the menu. Each section's pages are compiled into their own code fragment
 * ({@link GWT#runAsync}), so the first load only downloads the shell and the home page.
 */
public enum Section {
    GENERAL("General") {
        @Override
        void create(final Page page, final PageCallback callback) {
            GWT.runAsync(GeneralPages.class, new Loader(callback) {
                @Override
                public void onSuccess() {
                    callback.onPage(GeneralPages.create(page.getToken()));
                }
            });
        }
    };

    private final String title;

    Section(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Loads the section's code fragment if needed and creates the page.
     */
    abstract void create(Page page, PageCallback callback);

    /**
     * Receives the created page widget.
     */
    public interface PageCallback {
        void onPage(Widget page);

        void onError(Throwable reason);
    }

    private abstract static class Loader implements RunAsyncCallback {
        private final PageCallback callback;

        Loader(final PageCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onFailure(final Throwable reason) {
            callback.onError(reason);
        }
    }
}
