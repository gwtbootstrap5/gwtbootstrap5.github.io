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

import org.gwtbootstrap5.demo.client.ui.CodeBlock;
import org.gwtbootstrap5.demo.client.ui.SourceFormatter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * How to add GwtBootstrap5 to a project: the dependency, the GWT module, the host page and the
 * UiBinder namespaces.
 */
public class SetupPage extends Composite {

    interface Binder extends UiBinder<HTMLPanel, SetupPage> {
    }

    interface Snippets extends ClientBundle {
        @Source("setup/dependency.xml")
        TextResource dependency();

        @Source("setup/inherits.xml")
        TextResource inherits();

        @Source("setup/host.html")
        TextResource host();

        @Source("setup/uibinder.xml")
        TextResource uiBinder();

        @Source("setup/extras.xml")
        TextResource extras();
    }

    private static final Binder BINDER = GWT.create(Binder.class);
    private static final Snippets SNIPPETS = GWT.create(Snippets.class);

    @UiField
    CodeBlock dependency;
    @UiField
    CodeBlock inherits;
    @UiField
    CodeBlock host;
    @UiField
    CodeBlock uiBinder;
    @UiField
    CodeBlock extras;

    public SetupPage() {
        initWidget(BINDER.createAndBindUi(this));
        dependency.setCode(SourceFormatter.snippet(SNIPPETS.dependency().getText()));
        inherits.setCode(SourceFormatter.snippet(SNIPPETS.inherits().getText()));
        host.setCode(SourceFormatter.snippet(SNIPPETS.host().getText()));
        uiBinder.setCode(SourceFormatter.snippet(SNIPPETS.uiBinder().getText()));
        extras.setCode(SourceFormatter.snippet(SNIPPETS.extras().getText()));
    }
}
