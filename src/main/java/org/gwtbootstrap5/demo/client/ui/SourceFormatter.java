package org.gwtbootstrap5.demo.client.ui;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Turns a source file into the code shown under an example: drops the license header and the
 * wrapping markup that every file needs but readers don't, and removes the common indentation.
 */
public final class SourceFormatter {

    private static final String START = "// [START ";
    private static final String END = "// [END ";

    private SourceFormatter() {
    }

    /**
     * Formats a UiBinder template: the content of {@code <ui:UiBinder>}, without a bare
     * {@code <g:HTMLPanel>} wrapper.
     */
    public static String uiBinder(final String source) {
        String s = source;
        final int open = s.indexOf("<ui:UiBinder");
        if (open >= 0) {
            s = s.substring(s.indexOf('>', open) + 1);
            final int close = s.lastIndexOf("</ui:UiBinder>");
            if (close >= 0) {
                s = s.substring(0, close);
            }
        }
        final String trimmed = s.trim();
        if (trimmed.startsWith("<g:HTMLPanel>") && trimmed.endsWith("</g:HTMLPanel>")
                && trimmed.indexOf("<g:HTMLPanel", 1) < 0) {
            s = trimmed.substring("<g:HTMLPanel>".length(), trimmed.length() - "</g:HTMLPanel>".length());
        }
        return dedent(s);
    }

    /**
     * Formats the lines of a Java file between {@code // [START name]} and {@code // [END name]}.
     *
     * @throws IllegalArgumentException if the region doesn't exist
     */
    public static String javaRegion(final String source, final String name) {
        final int start = source.indexOf(START + name + "]");
        final int end = source.indexOf(END + name + "]");
        if (start < 0 || end < start) {
            throw new IllegalArgumentException("No region " + name);
        }
        return dedent(source.substring(source.indexOf('\n', start) + 1, end));
    }

    /**
     * Formats a plain snippet file (XML, HTML): drops a leading XML declaration and comments.
     */
    public static String snippet(final String source) {
        String s = source.trim();
        if (s.startsWith("<?xml")) {
            s = s.substring(s.indexOf("?>") + 2).trim();
        }
        while (s.startsWith("<!--")) {
            s = s.substring(s.indexOf("-->") + 3).trim();
        }
        return dedent(s);
    }

    static String dedent(final String text) {
        final String[] lines = text.replace("\t", "    ").split("\n", -1);
        int indent = Integer.MAX_VALUE;
        for (final String line : lines) {
            if (!line.trim().isEmpty()) {
                indent = Math.min(indent, line.length() - line.replaceAll("^ +", "").length());
            }
        }
        final List<String> out = new ArrayList<>();
        for (final String line : lines) {
            out.add(line.trim().isEmpty() ? "" : line.substring(indent).replaceAll("\\s+$", ""));
        }
        while (!out.isEmpty() && out.get(0).isEmpty()) {
            out.remove(0);
        }
        while (!out.isEmpty() && out.get(out.size() - 1).isEmpty()) {
            out.remove(out.size() - 1);
        }
        return String.join("\n", out);
    }
}
