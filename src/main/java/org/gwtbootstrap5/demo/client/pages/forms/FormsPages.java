package org.gwtbootstrap5.demo.client.pages.forms;

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
public final class FormsPages {

    private FormsPages() {
    }

    public static Widget create(final String token) {
        switch (token) {
            case "forms/controls":
                return new ControlsPage();
            case "forms/input-group":
                return new InputGroupPage();
            case "forms/floating-labels":
                return new FloatingLabelPage();
            case "forms/switch":
                return new SwitchPage();
            case "forms/validation":
                return new ValidationPage();
            default:
                return new FormsPage();
        }
    }
}
