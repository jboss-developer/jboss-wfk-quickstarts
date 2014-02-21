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
package org.jboss.as.quickstarts.kitchensink.test.page;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebElement;

import static org.jboss.arquillian.graphene.Graphene.waitModel;

public class NavigationPageFragment {

    /**
     * Page Fragment root element
     */
    @Root
    private WebElement navigation;

    /**
     * Locator for Home page link
     */
    @FindByJQuery("a[href='#intro']")
    private WebElement homeLink;

    /**
     * Locator for Add Member page
     */
    @FindByJQuery("a[href='#register']")
    private WebElement registerLink;

    /**
     * Locator for List Members page
     */
    @FindByJQuery("a[href='#member']")
    private WebElement listLink;

    public void openHomePage() {
        homeLink.click();
    }

    public void openRegistrationPage() {
        registerLink.click();
    }

    public void openMemberListPage() {
        listLink.click();
    }

    public void waitUntilPresent() {
        waitModel().until().element(navigation).is().present();
    }

}
