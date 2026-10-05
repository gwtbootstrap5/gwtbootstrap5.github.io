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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SourceFormatterTest {

    @Test
    public void uiBinderDropsHeaderWrapperAndIndentation() {
        final String source = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<!--\n  license\n  -->\n"
                + "<ui:UiBinder xmlns:ui=\"urn:ui:com.google.gwt.uibinder\"\n"
                + "             xmlns:b=\"urn:import:org.gwtbootstrap5.client.ui\">\n"
                + "    <g:HTMLPanel>\n"
                + "        <b:Alert type=\"SUCCESS\">\n"
                + "            Saved\n"
                + "        </b:Alert>\n"
                + "    </g:HTMLPanel>\n"
                + "</ui:UiBinder>\n";
        assertEquals("<b:Alert type=\"SUCCESS\">\n    Saved\n</b:Alert>", SourceFormatter.uiBinder(source));
    }

    @Test
    public void uiBinderKeepsAWrapperWithAttributes() {
        final String source = "<ui:UiBinder xmlns:ui=\"x\">\n"
                + "  <g:HTMLPanel styleName=\"d-flex\">\n    <b:Badge/>\n  </g:HTMLPanel>\n"
                + "</ui:UiBinder>";
        assertEquals("<g:HTMLPanel styleName=\"d-flex\">\n  <b:Badge/>\n</g:HTMLPanel>", SourceFormatter.uiBinder(source));
    }

    @Test
    public void javaRegion() {
        final String source = "class A {\n"
                + "    void a() {\n"
                + "        // [START show]\n"
                + "        modal.show();\n"
                + "\n"
                + "        modal.hide();\n"
                + "        // [END show]\n"
                + "    }\n"
                + "}\n";
        assertEquals("modal.show();\n\nmodal.hide();", SourceFormatter.javaRegion(source, "show"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingJavaRegion() {
        SourceFormatter.javaRegion("class A {}", "show");
    }

    @Test
    public void snippetDropsDeclarationAndComments() {
        assertEquals("<dependency/>", SourceFormatter.snippet("<?xml version=\"1.0\"?>\n<!-- a -->\n<!-- b -->\n  <dependency/>\n"));
    }
}
