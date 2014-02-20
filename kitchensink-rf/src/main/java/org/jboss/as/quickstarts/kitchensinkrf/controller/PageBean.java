/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.kitchensinkrf.controller;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.net.MalformedURLException;

/**
 * The PageBean maps the location hash from the browser into a jsf view-id
 *
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
@Named
@SessionScoped
public class PageBean implements Serializable {
    private static final long serialVersionUID = 281964859634018452L;

    private String location;
    private String page;

    public String getPage() {
        return page;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String viewId = String.format("/mobile/%s.xhtml", location);
        try {
            if (context.getResource(viewId) != null) {
                this.page = viewId;
            }
        } catch (MalformedURLException e) {
            // do nothing
        }
    }
}
