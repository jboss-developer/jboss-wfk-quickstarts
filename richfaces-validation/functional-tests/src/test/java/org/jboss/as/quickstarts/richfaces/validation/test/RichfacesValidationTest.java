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
package org.jboss.as.quickstarts.richfaces.validation.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;

import static org.jboss.arquillian.graphene.Graphene.waitModel;

/**
 * This class tests the richfaces validation quickstart application.
 *
 * @author smikloso
 */
@RunWith(Arquillian.class)
public class RichfacesValidationTest {

    // at the first screen
    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "j_idt15:email")
    WebElement emailFieldFormatLabel;

    @FindBy(css = "form div.rf-tgp-itm input[type='submit']")
    WebElement continueButton1;

    @FindBy(id = "j_idt7")
    WebElement registrationForm;

    // at the second screen
    @FindBy(name = "j_idt28")
    WebElement continueButton2;

    @FindBy(id = "name")
    WebElement nameField;

    @FindBy(id = "phone")
    WebElement phoneField;

    // at the third screen
    @FindBy(id = "emailOutput")
    WebElement emailOutput;

    @FindBy(id = "nameOutput")
    WebElement nameOutput;

    @FindBy(id = "phoneOutput")
    WebElement phoneOutput;

    @FindBy(name = "j_idt37")
    WebElement continueButton3;

    @FindBy(css = "span[class=rf-msgs-sum]")
    WebElement finalMessage;

    // at the first screen
    protected final String EMAIL_FIELD_FORMAT_MESSAGE = "Not a well-formed email address";
    protected final String EMAIL_GOOD = "john@doe.com";
    protected final String EMAIL_BAD = "jonh";

    // at the second screen

    // name related
    protected final String NAME_GOOD = "John Doe";
    protected final String NAME_BAD_1 = "John .";
    protected final String NAME_BAD_2 = "Jonh 1";
    protected final String NAME_TOO_LONG = "John Doe John Doe John Doe";
    protected final String NAME_TOO_SHORT = "";
    protected final String NAME_FIELD_SIZE_MESSAGE = "size must be between 1 and 25";
    protected final String NAME_FIELD_FORMAT_MESSAGE = "must contain only letters and spaces";

    // phone related
    protected final String PHONE_TOO_SHORT = "123456789";
    protected final String PHONE_TOO_LONG = "1234567890123";
    protected final String PHONE_GOOD = "1234567890";
    protected final String PHONE_BAD = "1234.1234";
    protected final String PHONE_FIELD_FORMAT_MESSAGE = "Phone number must be 10-12 digits";
    // at fourth screen
    protected static final String REGISTRATION_SUCCESFULL = "Hello {0}, you have been successfully registered";

    /**
     * Specifies relative path to the war of built application in the main project.
     */
    private static final String DEPLOYMENT_WAR = "../target/jboss-richfaces-validation.war";

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
    public static WebArchive createDeployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File(DEPLOYMENT_WAR));
    }

    /**
     * This method loads the application.
     */
    @Before
    public void openStartUrl() {
        browser.get(contextPath.toString());
        waitModel().until().element(continueButton1).is().visible();
    }

    /**
     * This method tests entering of the empty e-mail into the e-mail field by directly clicking to the continue button
     */
    @Test
    @InSequence(1)
    public void testEmptyEmail() {
        openStartUrl();

        // click continue button while email field is empty
        continueButton1.click();
        // ensure there is the error message which informs user about bad-formatted e-mail
        waitModel().until().element(emailFieldFormatLabel).is().visible();
        Assert.assertTrue(emailFieldFormatLabel.isDisplayed());
    }

    /**
     * This method tests entering of the bad-formatted e-mail into the e-mail field.
     */
    @Test
    @InSequence(2)
    public void testEmailBadFormat() {
        openStartUrl();

        // type bad formatted e-mail into the e-mail field
        emailField.clear();
        emailField.sendKeys(EMAIL_BAD);
        // is email present in the email field?
        Assert.assertTrue(emailField.getAttribute("value").equals(EMAIL_BAD));
        // click on the continue button
        registrationForm.submit();
        // ensure there is the error message which informs user about bad-formatted e-mail entered
        waitModel().until().element(emailFieldFormatLabel).is().visible();
        Assert.assertTrue(emailFieldFormatLabel.isDisplayed());
    }

    @Test
    @InSequence(1)
    public void testEmailGoodFormat() {
        openStartUrl();

        // type e-mail into the e-mail field
        emailField.clear();
        emailField.sendKeys(EMAIL_GOOD);
        // is email present in the email field?
        Assert.assertTrue(emailField.getAttribute("value").equals(EMAIL_GOOD));
        // be sure there is well-formated e-mail entered
        Assert.assertFalse(browser.getPageSource().contains(EMAIL_FIELD_FORMAT_MESSAGE));
        registrationForm.submit();
        continueButton1.click();
    }

    @Test
    @InSequence(4)
    public void testEmptyNameAndPhone() {
        // fills the first screen
        openStartUrl();
        emailField.clear();
        emailField.sendKeys(EMAIL_GOOD);
        registrationForm.submit();
        continueButton1.click();
        waitModel().until().element(continueButton2).is().visible();

        // now name & phone number page loads, we click immediately on the continue button,
        // error messages about bad-formatted (empty) name & phone entries appear
        registrationForm.submit();
        continueButton2.click();
        Assert.assertTrue(browser.getPageSource().contains(NAME_FIELD_SIZE_MESSAGE)
                && browser.getPageSource().contains(PHONE_FIELD_FORMAT_MESSAGE));
    }

    @Test
    @InSequence(5)
    public void testBadFormattedNameAndPhone() {
        // fills the first screen
        openStartUrl();
        emailField.clear();
        emailField.sendKeys(EMAIL_GOOD);
        registrationForm.submit();
        continueButton1.click();
        waitModel().until().element(nameField).is().visible();
        // name

        // we enter bad formatted names
        nameField.clear();
        nameField.sendKeys(NAME_BAD_1);
        registrationForm.submit();
        continueButton2.click();
        Assert.assertTrue(browser.getPageSource().contains(NAME_FIELD_FORMAT_MESSAGE));
        nameField.clear();
        nameField.sendKeys(NAME_BAD_2);
        registrationForm.submit();
        continueButton2.click();
        Assert.assertTrue(browser.getPageSource().contains(NAME_FIELD_FORMAT_MESSAGE));

        // we enter too long name
        nameField.clear();
        nameField.sendKeys(NAME_TOO_LONG);
        registrationForm.submit();
        continueButton2.click();
        Assert.assertTrue(browser.getPageSource().contains(NAME_FIELD_SIZE_MESSAGE));
        // we enter too short name
        nameField.clear();
        nameField.sendKeys(NAME_TOO_SHORT);
        registrationForm.submit();
        continueButton2.click();
        Assert.assertTrue(browser.getPageSource().contains(NAME_FIELD_SIZE_MESSAGE));

        // phone

        // we enter too long phone
        phoneField.clear();
        phoneField.sendKeys(PHONE_TOO_LONG);
        registrationForm.submit();
        continueButton2.click();
        Assert.assertTrue(browser.getPageSource().contains(PHONE_FIELD_FORMAT_MESSAGE));
        // we enter too short phone
        phoneField.clear();
        phoneField.sendKeys(PHONE_TOO_SHORT);
        registrationForm.submit();
        continueButton2.click();
        Assert.assertTrue(browser.getPageSource().contains(PHONE_FIELD_FORMAT_MESSAGE));
        // we enter bad formatted phone
        phoneField.clear();
        phoneField.sendKeys(PHONE_BAD);
        registrationForm.submit();
        continueButton2.click();
        Assert.assertTrue(browser.getPageSource().contains(PHONE_FIELD_FORMAT_MESSAGE));
    }

    @Test
    @InSequence(6)
    public void testGoodNameAndPhone() {
        // fills the first screen
        openStartUrl();
        emailField.clear();
        emailField.sendKeys(EMAIL_GOOD);
        registrationForm.submit();
        continueButton1.click();
        waitModel().until().element(nameField).is().visible();
        // fills the second screen
        // we enter both name and phone in the good format
        nameField.clear();
        nameField.sendKeys(NAME_GOOD);
        phoneField.clear();
        phoneField.sendKeys(PHONE_GOOD);

        // checking the output
        registrationForm.submit();
        continueButton2.click();
        waitModel().until().element(emailOutput).is().visible();
        Assert.assertTrue(emailOutput.getText().equals(EMAIL_GOOD) && nameOutput.getText().equals(NAME_GOOD)
                && phoneOutput.getText().equals(PHONE_GOOD));

        registrationForm.submit();
        continueButton3.click();
        waitModel().until().element(finalMessage).is().visible();
        Assert.assertTrue(browser.getPageSource().contains(MessageFormat.format(REGISTRATION_SUCCESFULL, NAME_GOOD)));
    }
}
