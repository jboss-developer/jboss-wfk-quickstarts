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
package org.jboss.as.quickstarts.kitchensink.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

/**
 * Kitchensink RichFaces quickstart functional test
 *
 * @author Oliver Kiss
 */
@RunAsClient
@RunWith(Arquillian.class)
public class KitchensinkRichFacesTest {

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
     * Creates deployment which is sent to the container upon test's start.
     *
     * @return war file which is deployed while testing, the whole application in our case
     */
    @Deployment(testable = false)
    public static WebArchive deployment() {
        return Deployments.kitchensink();
    }

    /**
     * Locator for name field
     */
    @FindByJQuery("[id*='newMemberPanel'] [id*='name']")
    WebElement nameField;

    /**
     * Locator for email field
     */
    @FindByJQuery("[id*='newMemberPanel'] [id*='email']")
    WebElement emailField;

    /**
     * Locator for phone number field
     */
    @FindByJQuery("[id*='newMemberPanel'] [id*='phoneNumber']")
    WebElement phoneFiled;

    /**
     * Locator for registration button
     */
    @FindByJQuery("[id*='newMemberPanel'] [id*='register']")
    WebElement registerButton;

    /**
     * Locator for the members table
     */
    @FindByJQuery("[id*='memberList'] table tbody[id*='tb']")
    WebElement tableMembers;

    /**
     * Locator for rows of the members table
     */
    @FindByJQuery("[id*='memberList'] table tbody[id*='tb'] tr")
    List<WebElement> tableMembersRows;

    /**
     * Locator for columns of the first row of the members table
     */
    @FindByJQuery("[id*='memberList'] table tbody[id*='tb'] tr:first td")
    List<WebElement> tableMembersRowColumns;

    /**
     * Locator for name field validation message
     */
    @FindByJQuery("[id*='newMemberPanel'] span[id*='name'].invalid")
    WebElement nameErrorMessage;

    /**
     * Locator for email field validation message
     */
    @FindByJQuery("[id*='newMemberPanel'] span[id*='email'].invalid")
    WebElement emailErrorMessage;

    /**
     * Locator for phone number field validation message
     */
    @FindByJQuery("[id*='newMemberPanel'] span[id*='phoneNumber'].invalid")
    WebElement phoneNumberErrorMessage;

    /**
     * Name of the member to register in the right format.
     */
    private static final String NAME_FORMAT_OK = "John Doe";

    /**
     * Name of the member to register in the bad format.
     */
    private static final String NAME_FORMAT_BAD = "John123";

    /**
     * E-mail of the member to register in the right format.
     */
    private static final String EMAIL_FORMAT_OK = "john@doe.com";

    /**
     * E-mail of the member to register in the bad format - #1.
     */
    private static final String EMAIL_FORMAT_BAD = "joe";

    /**
     * Phone number of the member to register in the right format.
     */
    private static final String PHONE_FORMAT_OK = "0123456789";

    /**
     * Phone number of the member to register in the bad format - illegal
     * characters.
     */
    private static final String PHONE_FORMAT_BAD_ILLEGAL_CHARS = "as/df.123@";

    /**
     * Phone number of the member to register in the bad format - too long.
     */
    private static final String PHONE_FORMAT_BAD_TOO_LONG = "12345678901234567890";

    /**
     * Phone nuber of the member to register in the bad format - too short
     */
    private static final String PHONE_FORMAT_BAD_TOO_SHORT = "123456789";

    /**
     * Name input message format - only letters and spaces
     */
    private static final String NAME_INPUT_MESSAGE_FORMAT = "Must not contain numbers";

    /**
     * Name input message size - between 1 and 25
     */
    private static final String NAME_INPUT_MESSAGE_SIZE = "size must be between 1 and 25";

    /**
     * Email may not be empty
     */
    private static final String EMAIL_INPUT_MESSAGE_NOT_EMPTY = "may not be empty";

    /**
     * Email format - not in the right format
     */
    private static final String EMAIL_INPUT_MESSAGE_FORMAT = "not a well-formed email address";

    @Test
    @InSequence(1)
    public void testEmptyRegistration() {
        browser.get(contextPath.toString());
        registerButton.click();
        waitAjax().until().element(nameErrorMessage).text().contains(NAME_INPUT_MESSAGE_SIZE);
        waitAjax().until().element(emailErrorMessage).text().contains(EMAIL_INPUT_MESSAGE_NOT_EMPTY);
        waitAjax().until().element(phoneNumberErrorMessage).is().present();
        assertEquals("User should not be registered", 0, tableMembersRows.size());
    }

    @Test
    @InSequence(2)
    public void testRegistrationWithBadNameFormat() {
        browser.get(contextPath.toString());
        setInputFields(NAME_FORMAT_BAD, EMAIL_FORMAT_OK, PHONE_FORMAT_OK);
        registerButton.click();
        waitAjax().until().element(nameErrorMessage).is().present();
        assertTrue(nameErrorMessage.getText().contains(NAME_INPUT_MESSAGE_FORMAT));

        assertEquals("User should not be registered", 0, tableMembersRows.size());
    }

    @Test
    @InSequence(3)
    public void testRegistrationWithBadEmailFormat() {
        browser.get(contextPath.toString());
        setInputFields(NAME_FORMAT_OK, EMAIL_FORMAT_BAD, PHONE_FORMAT_OK);
        registerButton.click();
        waitAjax().until().element(emailErrorMessage).is().present();
        assertTrue(emailErrorMessage.getText().contains(EMAIL_INPUT_MESSAGE_FORMAT));

        assertEquals("User should not be registered", 0, tableMembersRows.size());
    }

    @Test
    @InSequence(4)
    public void testRegistrationWithBadPhoneFormat() {
        // phone number with illegal characters in it
        browser.get(contextPath.toString());
        setInputFields(NAME_FORMAT_OK, EMAIL_FORMAT_OK, PHONE_FORMAT_BAD_ILLEGAL_CHARS);
        registerButton.click();
        waitAjax().until().element(phoneNumberErrorMessage).is().present();

        // phone too short
        browser.get(contextPath.toString());
        setInputFields(NAME_FORMAT_OK, EMAIL_FORMAT_OK, PHONE_FORMAT_BAD_TOO_SHORT);
        registerButton.click();
        waitAjax().until().element(phoneNumberErrorMessage).is().present();

        // phone too long
        browser.get(contextPath.toString());
        setInputFields(NAME_FORMAT_OK, EMAIL_FORMAT_OK, PHONE_FORMAT_BAD_TOO_LONG);
        registerButton.click();
        waitAjax().until().element(phoneNumberErrorMessage).is().present();

        assertEquals("User should not be registered", 0, tableMembersRows.size());
    }

    @Test
    @InSequence(5)
    public void testRegularRegistration() {
        browser.get(contextPath.toString());
        setInputFields(NAME_FORMAT_OK, EMAIL_FORMAT_OK, PHONE_FORMAT_OK);
        registerButton.click();

        waitModel().until().element(tableMembers).text().contains(NAME_FORMAT_OK);

        assertEquals(1, tableMembersRows.size());
        assertEquals(6, tableMembersRowColumns.size());

        assertTrue((tableMembersRowColumns.get(2)).getText().equals(NAME_FORMAT_OK));
        assertTrue((tableMembersRowColumns.get(3)).getText().equals(EMAIL_FORMAT_OK));
        assertTrue((tableMembersRowColumns.get(4)).getText().equals(PHONE_FORMAT_OK));
    }

    /**
     * This helper method sets values into the according input fields.
     *
     * @param name  name to set into the name input field
     * @param email email to set into the email input field
     * @param phone phone to set into the phone input field
     */
    private void setInputFields(String name, String email, String phone) {
        nameField.clear();
        nameField.sendKeys(name);
        emailField.clear();
        emailField.sendKeys(email);
        phoneFiled.clear();
        phoneFiled.sendKeys(phone);
    }

}
