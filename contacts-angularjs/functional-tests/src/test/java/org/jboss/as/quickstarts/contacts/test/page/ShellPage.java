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
package org.jboss.as.quickstarts.contacts.test.page;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;

import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitGui;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

/**
 * <p>Class to describe how to manipulate the Shell of all pages (navigation etc..)</p>
 *
 * @author hugofirth
 * @see org.openqa.selenium.WebElement
 */
public class ShellPage {


    @FindByJQuery(".header ul.nav li a[href*='#/add']")
    private WebElement addPageLink;

    @FindByJQuery(".header ul.nav li a[href='#/']")
    private WebElement homePageLink;

    @FindByJQuery("form#contactForm")
    private WebElement contactForm;

    @FindByJQuery("ul.contact-list")
    private WebElement contactList;


    public void openAddPage() {
        addPageLink.click();
    }

    public void openListPage() {
        homePageLink.click();
    }
}
