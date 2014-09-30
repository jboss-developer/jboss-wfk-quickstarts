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
import org.jboss.as.quickstarts.contacts.test.Contact;
import org.jboss.as.quickstarts.contacts.test.page.fragment.ContactListItemPageFragment;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.jboss.arquillian.graphene.Graphene.waitGui;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;

/**
 * Manipulation with Contact List page
 *
 * @author Oliver Kiss
 */
public class ContactListPage {

    @FindByJQuery("li.contact")
    private List<ContactListItemPageFragment> contacts;

    @FindBy(id = "toggleDetails")
    private WebElement showDetailsButton;

    @FindByJQuery("div.contact-details:eq(0)")
    private WebElement firstContactsDetails;

    @FindBy(id = "contact-search-term")
    private WebElement filterInput;

    public void editContact(String name) {
        for (ContactListItemPageFragment contactPF : contacts) {
            if (contactPF.getContactName().contains(name)) {
                contactPF.editContact();
                return;
            }
        }
    }

    public List<Contact> getContacts() {
        List<Contact> ret = new ArrayList<Contact>();
        for (ContactListItemPageFragment contactPF : contacts) {
            ret.add(contactPF.getContact());
        }
        return ret;
    }

    public void showDetails() {
        showDetailsButton.click();
        waitGui().until().element(firstContactsDetails).is().visible();
    }

    public void waitForPage() {
        waitAjax().withTimeout(10, TimeUnit.SECONDS).pollingEvery(2, TimeUnit.SECONDS).until().element(filterInput).is().present();
    }
}
