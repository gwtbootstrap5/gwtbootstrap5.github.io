package org.gwtbootstrap5.demo.client.shell;

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

import org.gwtbootstrap5.client.ui.base.helper.ColorModeHelper;
import org.gwtbootstrap5.client.ui.constants.ColorMode;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.Storage;

/**
 * The demo's color mode: light, dark, or following the system, remembered in local storage.
 */
public final class ColorModes {

    /**
     * The choices of the color mode menu.
     */
    public enum Choice {
        LIGHT, DARK, AUTO
    }

    private static final String KEY = "gwtbootstrap5-demo-color-mode";

    private static HandlerRegistration following;

    private ColorModes() {
    }

    /**
     * @return the stored choice, {@link Choice#AUTO} if none
     */
    public static Choice current() {
        final Storage storage = Storage.getLocalStorageIfSupported();
        final String stored = storage != null ? storage.getItem(KEY) : null;
        for (final Choice choice : Choice.values()) {
            if (choice.name().equals(stored)) {
                return choice;
            }
        }
        return Choice.AUTO;
    }

    /**
     * Applies the stored choice to the page.
     */
    public static void applyStored() {
        apply(current());
    }

    /**
     * Applies a choice to the page and remembers it.
     */
    public static void choose(final Choice choice) {
        final Storage storage = Storage.getLocalStorageIfSupported();
        if (storage != null) {
            storage.setItem(KEY, choice.name());
        }
        apply(choice);
    }

    private static void apply(final Choice choice) {
        if (following != null) {
            following.removeHandler();
            following = null;
        }
        switch (choice) {
            case LIGHT:
                ColorModeHelper.setPageColorMode(ColorMode.LIGHT);
                break;
            case DARK:
                ColorModeHelper.setPageColorMode(ColorMode.DARK);
                break;
            default:
                following = ColorModeHelper.followSystem();
                break;
        }
    }
}
