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

import org.gwtbootstrap5.client.ui.AnchorListItem;
import org.gwtbootstrap5.client.ui.Pagination;
import org.gwtbootstrap5.client.ui.html.Paragraph;
import org.gwtbootstrap5.demo.client.ui.Example;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Pagination demo page.
 */
public class PaginationPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, PaginationPage> {
    }

    @UiTemplate("pagination/Basic.ui.xml")
    interface BasicBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("pagination/Sizes.ui.xml")
    interface SizesBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("pagination/FromJava.ui.xml")
    interface FromJavaBinder extends UiBinder<Widget, Pager> {
    }

    interface Sources extends ClientBundle {
        @Source("pagination/Basic.ui.xml")
        TextResource basic();

        @Source("pagination/Sizes.ui.xml")
        TextResource sizes();

        @Source("pagination/FromJava.ui.xml")
        TextResource fromJava();

        @Source("PaginationPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example basic;
    @UiField
    Example sizes;
    @UiField
    Example fromJava;

    public PaginationPage() {
        initWidget(BINDER.createAndBindUi(this));
        basic.show(GWT.<BasicBinder>create(BasicBinder.class).createAndBindUi(this), SOURCES.basic());
        sizes.show(GWT.<SizesBinder>create(SizesBinder.class).createAndBindUi(this), SOURCES.sizes());
        final Pager fromJavaOwner = new Pager();
        fromJava.show(GWT.<FromJavaBinder>create(FromJavaBinder.class).createAndBindUi(fromJavaOwner), SOURCES.fromJava());
        fromJavaOwner.init();
        fromJava.addJava(SOURCES.java(), "fromJava");
    }

    static class Pager {
        // [START fromJava]
        private static final int PAGES = 5;

        @UiField
        Pagination pagination;
        @UiField
        Paragraph status;
        private final AnchorListItem[] pages = new AnchorListItem[PAGES];
        private AnchorListItem previous;
        private AnchorListItem next;
        private int current;

        void init() {
            for (int i = 0; i < PAGES; i++) {
                final int page = i;
                pages[i] = new AnchorListItem(String.valueOf(i + 1));
                pages[i].addClickHandler(event -> select(page));
                pagination.add(pages[i]);
            }
            previous = pagination.addPreviousLink();
            previous.addClickHandler(event -> select(Math.max(0, current - 1)));
            next = pagination.addNextLink();
            next.addClickHandler(event -> select(Math.min(PAGES - 1, current + 1)));
            select(0);
        }

        private void select(final int page) {
            current = page;
            for (int i = 0; i < PAGES; i++) {
                pages[i].setActive(i == page);
            }
            previous.setEnabled(page > 0);
            next.setEnabled(page < PAGES - 1);
            status.setText("Page " + (page + 1) + " of " + PAGES + ".");
        }
        // [END fromJava]
    }
}
