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

import com.google.gwt.user.client.ui.Widget;

/**
 * Creates the pages of the section; compiled into its own code fragment.
 */
public final class ExtrasPages {

    private ExtrasPages() {
    }

    public static Widget create(final String token) {
        switch (token) {
            case "extras/bootbox":
                return new BootboxPage();
            case "extras/color-picker":
                return new ColorPickerPage();
            case "extras/date-time-pickers":
                return new DateTimePickersPage();
            case "extras/font-awesome":
                return new FontAwesomePage();
            case "extras/range":
                return new RangePage();
            case "extras/select":
                return new SelectPage();
            case "extras/summernote":
                return new SummernotePage();
            default:
                return new AnimatePage();
        }
    }
}
