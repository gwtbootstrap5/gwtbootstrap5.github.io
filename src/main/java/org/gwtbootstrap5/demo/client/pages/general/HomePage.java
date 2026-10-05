package org.gwtbootstrap5.demo.client.pages.general;

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

import java.util.List;

import org.gwtbootstrap5.demo.client.nav.Page;
import org.gwtbootstrap5.demo.client.nav.Pages;
import org.gwtbootstrap5.demo.client.nav.Section;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Landing page: what GwtBootstrap5 is, what 0.2.0 brings, and a card per section of the demo.
 */
public class HomePage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, HomePage> {
    }

    interface Templates extends SafeHtmlTemplates {
        @Template("<a href=\"#{0}\">{1}</a>")
        SafeHtml link(String token, String title);

        @Template("<div class=\"card h-100\"><div class=\"card-body\"><h2 class=\"h6 card-title\">{0}</h2>"
                + "<p class=\"card-text small mb-0\">{1}</p></div></div>")
        SafeHtml card(String title, SafeHtml links);
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Templates TEMPLATES = GWT.create(Templates.class);

    @UiField
    FlowPanel sections;

    public HomePage() {
        initWidget(BINDER.createAndBindUi(this));
        for (final Section section : Section.values()) {
            final List<Page> pages = Pages.of(section);
            if (section == Section.GENERAL || pages.isEmpty()) {
                continue;
            }
            final SafeHtmlBuilder links = new SafeHtmlBuilder();
            for (int i = 0; i < pages.size(); i++) {
                if (i > 0) {
                    links.appendEscaped(" · ");
                }
                links.append(TEMPLATES.link(pages.get(i).getToken(), pages.get(i).getTitle()));
            }
            final HTML card = new HTML(TEMPLATES.card(section.getTitle(), links.toSafeHtml()));
            card.setStyleName("col");
            sections.add(card);
        }
        sections.setVisible(sections.getWidgetCount() > 0);
    }
}
