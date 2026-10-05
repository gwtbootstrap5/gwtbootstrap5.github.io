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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Widget;

/**
 * Maps the history token ({@code #setup}, {@code #components/modal}) to a page and shows it.
 * An unknown or empty token shows the home page.
 */
public final class Router implements ValueChangeHandler<String> {

    /**
     * Where the router shows pages.
     */
    public interface Display {
        void showLoading(Page page);

        void showPage(Page page, Widget widget);

        void showError(Page page, Throwable reason);
    }

    private final Display display;
    private Page requested;

    public Router(final Display display) {
        this.display = display;
    }

    /**
     * Starts listening to history changes and shows the page of the current token.
     */
    public void start() {
        History.addValueChangeHandler(this);
        History.fireCurrentHistoryState();
    }

    @Override
    public void onValueChange(final ValueChangeEvent<String> event) {
        final Page found = Pages.find(event.getValue());
        final Page page = found != null ? found : Pages.HOME;
        requested = page;
        display.showLoading(page);
        page.getSection().create(page, new Section.PageCallback() {
            @Override
            public void onPage(final Widget widget) {
                // A later click may have asked for another page while this one was loading
                if (requested == page) {
                    display.showPage(page, widget);
                }
            }

            @Override
            public void onError(final Throwable reason) {
                if (requested == page) {
                    display.showError(page, reason);
                }
            }
        });
    }
}
