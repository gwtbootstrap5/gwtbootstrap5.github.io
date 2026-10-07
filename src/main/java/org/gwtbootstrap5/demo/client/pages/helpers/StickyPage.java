package org.gwtbootstrap5.demo.client.pages.helpers;

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

import org.gwtbootstrap5.client.ui.Button;
import org.gwtbootstrap5.client.ui.base.helper.StickyHelper;
import org.gwtbootstrap5.client.ui.constants.StickyPosition;
import org.gwtbootstrap5.client.ui.html.Div;
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
 * Sticky demo page.
 */
public class StickyPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, StickyPage> {
    }

    @UiTemplate("sticky/Positions.ui.xml")
    interface PositionsBinder extends UiBinder<Widget, Object> {
    }

    @UiTemplate("sticky/Offset.ui.xml")
    interface OffsetBinder extends UiBinder<Widget, StickyLabel> {
    }

    interface Sources extends ClientBundle {
        @Source("sticky/Positions.ui.xml")
        TextResource positions();

        @Source("sticky/Offset.ui.xml")
        TextResource offset();

        @Source("StickyPage.java")
        TextResource java();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Sources SOURCES = GWT.create(Sources.class);

    @UiField
    Example positions;
    @UiField
    Example offset;

    public StickyPage() {
        initWidget(BINDER.createAndBindUi(this));
        positions.show(GWT.<PositionsBinder>create(PositionsBinder.class).createAndBindUi(this), SOURCES.positions());
        final StickyLabel offsetOwner = new StickyLabel();
        offset.show(GWT.<OffsetBinder>create(OffsetBinder.class).createAndBindUi(offsetOwner), SOURCES.offset());
        offsetOwner.init();
        offset.addJava(SOURCES.java(), "offset");
    }

    static class StickyLabel {
        // [START offset]
        @UiField
        Button toggle;
        @UiField
        Div label;

        void init() {
            StickyHelper.setSticky(label, StickyPosition.TOP, 12);
            toggle.addClickHandler(event -> {
                if (StickyHelper.getSticky(label) != null) {
                    StickyHelper.removeSticky(label);
                    toggle.setText("Stick again");
                } else {
                    StickyHelper.setSticky(label, StickyPosition.TOP, 12);
                    toggle.setText("Stop sticking");
                }
            });
        }
        // [END offset]
    }
}
