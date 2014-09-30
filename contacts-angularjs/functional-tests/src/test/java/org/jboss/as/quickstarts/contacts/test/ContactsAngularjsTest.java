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
package org.jboss.as.quickstarts.contacts.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.quickstarts.contacts.test.page.ContactListPage;
import org.jboss.as.quickstarts.contacts.test.page.ContactPage;
import org.jboss.as.quickstarts.contacts.test.page.ShellPage;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Contacts Angular.js quickstart functional test
 *
 * @author Oliver Kiss
 */
@RunAsClient
@RunWith(Arquillian.class)
public class ContactsAngularjsTest {

    /**
     * Locator for contact page
     */
    @FindByJQuery("div#view-pane")
    ContactPage contactPage;

    /**
     * Locator for contact list page
     */
    @FindByJQuery("div#view-pane")
    ContactListPage listPage;

    /**
     * Locator for page shell
     */
    @FindByJQuery("body")
    ShellPage shell;

    /**
     * Injects browser to our test.
     */
    @Drone
    WebDriver browser;

    /**
     * Injects URL on which application is running.
     */
    @ArquillianResource
    URL contextPath;

    /**
     * Test fixtures
     */
    private static String FIRST_NAME_INVALID = "John&";
    private static String FIRST_NAME_A = "John";
    private static String FIRST_NAME_B = "Jane";
    private static String LAST_NAME_INVALID = "Doe11";
    private static String LAST_NAME = "Doe";
    private static String PHONE_NUMBER_INVALID = "123a";
    private static String PHONE_NUMBER_A = "(200) 555-1111";
    private static String PHONE_NUMBER_B = "(200) 555-2222";
    private static String PHONE_NUMBER_C = "(200) 555-3333";
    private static String EMAIL_INVALID = "doe@";
    private static String EMAIL_A = "johndoe@mail.com";
    private static String EMAIL_B = "janedoe@mail.com";
    private static String EMAIL_C = "doe.jane@newmail.com";
    private static String DATE = "1970-01-01";
    private static String DATE_INVALID = "19aaa";
    private static String DATE_TOO_OLD = "1700-02-04";
    private static String DATE_FUTURE = "2020-01-01";


    /**
     * Creates deployment which is sent to the container upon test's start.
     *
     * @return war file which is deployed while testing, the whole application in our case
     */
    @Deployment(testable = false)
    public static WebArchive deployment() {
        return Deployments.contacts();
    }

    /**
     * Load web page before test's start.
     */
    @Before
    public void loadPage() {
            browser.get(contextPath.toString());
    }

    /**
     * Checks that all fields correctly display a "required" message when form is "dirty" but empty.
     */
    @Test
    @InSequence(1)
    public void requiredFieldsValidationTest() {
        shell.openAddPage();
        contactPage.waitForPage();
        contactPage.fillContact(new Contact(FIRST_NAME_A + " " + LAST_NAME, " ", " ", " "));
        contactPage.clearContact();
        assertFalse("First name should be required", contactPage.isFirstNameValid());
        assertFalse("Last name should be required", contactPage.isLastNameValid());
        assertFalse("Phone Number should be required", contactPage.isPhoneNumberValid());
        assertFalse("Email should be required", contactPage.isEmailValid());
        assertFalse("Birthdate should be required", contactPage.isBirthDateValid());
    }

    @Test
    @InSequence(2)
    public void contactFormValidationTest() {
        shell.openAddPage();
        contactPage.waitForPage();
        contactPage.fillContact(new Contact(FIRST_NAME_INVALID + " " + LAST_NAME_INVALID, PHONE_NUMBER_INVALID, EMAIL_INVALID, DATE_INVALID));
        assertFalse("First name should not be valid", contactPage.isFirstNameValid());
        assertFalse("Last name should not be valid", contactPage.isLastNameValid());
        assertFalse("Phone Number should not be valid", contactPage.isPhoneNumberValid());
        assertFalse("Email should not be valid", contactPage.isEmailValid());
        //Don't check birthdate validity message as Angular doesn't support date input validation as of 1.2.8
        contactPage.submit(false);

        contactPage.fillContact(new Contact(FIRST_NAME_A + " " + LAST_NAME, PHONE_NUMBER_A, EMAIL_INVALID, DATE_INVALID));
        assertTrue("First name should be valid", contactPage.isFirstNameValid());
        assertTrue("Last name should be valid", contactPage.isLastNameValid());
        assertTrue("Phone Number should be valid", contactPage.isPhoneNumberValid());
        assertFalse("Email should not be valid", contactPage.isEmailValid());
        //Don't check birthdate validity message as Angular doesn't support date input validation as of 1.2.8
        contactPage.submit(false);
    }

    @Test
    @InSequence(3)
    public void addContactTest() {
        shell.openAddPage();
        contactPage.waitForPage();
        Contact newContact = new Contact(FIRST_NAME_A + " " + LAST_NAME, PHONE_NUMBER_A, EMAIL_A, DATE);
        contactPage.fillContact(newContact);
        assertTrue("First name should be valid", contactPage.isFirstNameValid());
        assertTrue("Last name should be valid", contactPage.isLastNameValid());
        assertTrue("Phone Number should be valid", contactPage.isPhoneNumberValid());
        assertTrue("Email should be valid", contactPage.isEmailValid());
        assertTrue("Birthdate should be valid", contactPage.isBirthDateValid());
        assertTrue(contactPage.isFormValid());
        contactPage.submit(true);
        shell.openListPage();
        listPage.waitForPage();
        listPage.showDetails();
        assertTrue(listPage.getContacts().contains(newContact));
    }

    @Test
    @InSequence(4)
    public void duplicateEmailValidationTest() {
        shell.openListPage();
        listPage.waitForPage();
        listPage.showDetails();
        String existingEmail = listPage.getContacts().get(0).getEmail();
        Contact newContact = new Contact(FIRST_NAME_B + " " + LAST_NAME, PHONE_NUMBER_B, existingEmail, DATE);

        shell.openAddPage();
        contactPage.waitForPage();
        contactPage.fillContact(newContact);
        assertTrue("Contact form should be valid", contactPage.isFormValid());

        contactPage.submit(true);
        assertFalse("Email should not be unique", contactPage.isMessageDisplayed("That email is already used, please use a unique email"));

        newContact.setEmail(EMAIL_B);
        contactPage.fillContact(newContact);
        contactPage.submit(true);

        shell.openListPage();
        listPage.waitForPage();
        listPage.showDetails();
        assertTrue(listPage.getContacts().contains(newContact));
    }

    @Test
    @InSequence(5)
    public void editContactTest() {
        listPage.editContact(FIRST_NAME_B);
        contactPage.waitForPage();
        Contact contact = contactPage.getContact();
        contact.setPhoneNumber(PHONE_NUMBER_C);
        contact.setEmail(EMAIL_C);
        contactPage.fillContact(contact);
        contactPage.submit(true);

        shell.openListPage();
        listPage.waitForPage();
        listPage.showDetails();
        assertTrue(listPage.getContacts().contains(contact));
    }


}
