package org.gwtbootstrap5.demo.client.pages.extras;

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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Frame;

/**
 * Font Awesome demo page. The FontAwesome extra replaces the icon lookup of the whole application,
 * so its examples are a separate module ({@code FontAwesomeDemo}), shown in an iframe that grows
 * with its content.
 */
public class FontAwesomePage extends Frame {

    private final Timer fit = new Timer() {
        @Override
        public void run() {
            final Document document = IFrameElement.as(getElement()).getContentDocument();
            if (document != null && document.getBody() != null) {
                setHeight(document.getDocumentElement().getScrollHeight() + "px");
            }
        }
    };

    public FontAwesomePage() {
        super("fontawesome.html");
        setStyleName("demo-frame-host");
        getElement().setTitle("Font Awesome examples");
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        fit.scheduleRepeating(250);
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        fit.cancel();
    }
}
