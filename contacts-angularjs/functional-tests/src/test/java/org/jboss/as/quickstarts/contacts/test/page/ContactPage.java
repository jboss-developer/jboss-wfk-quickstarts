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

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.jboss.as.quickstarts.contacts.test.Contact;
import org.jboss.as.quickstarts.contacts.test.page.fragment.MessageFragment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.jboss.arquillian.graphene.Graphene.*;

/**
 * Manipulation with Add/Edit Contact pages
 *
 * @author Oliver Kiss
 */
public class ContactPage {

    /**
     * Injects browser to our test.
     */
    @Drone
    private WebDriver browser;

    @Root
    private WebElement page;

    @FindBy(className = "message")
    private List<MessageFragment> messages;

    @FindBy(name = "firstName")
    private WebElement firstName;

    @FindBy(name = "lastName")
    private WebElement lastName;

    @FindBy(name = "phoneNumber")
    private WebElement phoneNumber;

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(name = "birthDate")
    private WebElement birthDate;

    @FindByJQuery("p[id='firstName-required'].help-block")
    private WebElement firstNameRequiredValidationMessage;

    @FindByJQuery("p[id='firstName-format'].help-block")
    private WebElement firstNameFormatValidationMessage;

    @FindByJQuery("p[id='lastName-required'].help-block")
    private WebElement lastNameRequiredValidationMessage;

    @FindByJQuery("p[id='lastName-format'].help-block")
    private WebElement lastNameFormatValidationMessage;

    @FindByJQuery("p[id='phoneNumber-required'].help-block")
    private WebElement phoneNumberRequiredValidationMessage;

    @FindByJQuery("p[id='phoneNumber-format'].help-block")
    private WebElement phoneNumberFormatValidationMessage;

    @FindByJQuery("p[id='email-required'].help-block")
    private WebElement emailRequiredValidationMessage;

    @FindByJQuery("p[id='email-format'].help-block")
    private WebElement emailFormatValidationMessage;

    @FindByJQuery("p[id='birthdate-required'].help-block")
    private WebElement birthDateRequiredValidationMessage;

    @FindByJQuery("p[id='birthdate-future'].help-block")
    private WebElement birthDateFutureValidationMessage;

    @FindByJQuery("p[id='birthdate-max-age'].help-block")
    private WebElement birthDateMaxAgeValidationMessage;

    @FindByJQuery("p.help-block:visible")
    private List<WebElement> validationMessages;

    @FindByJQuery("button#save-button")
    private WebElement saveButton;

    public void fillContact(Contact contact) {
        String[] name = contact.getName().split(" ", 2);
        firstName.clear();
        firstName.sendKeys(name[0]);
        lastName.clear();
        lastName.sendKeys(name[1]);
        phoneNumber.clear();
        phoneNumber.sendKeys(contact.getPhoneNumber());
        email.clear();
        email.sendKeys(contact.getEmail());
        birthDate.clear();
        birthDate.sendKeys(contact.getBirthDate());
        page.click();
    }

    public void clearContact() {
        firstName.clear();
        lastName.clear();
        phoneNumber.clear();
        email.clear();
        birthDate.clear();
    }

    public Contact getContact() {
        Contact contact = new Contact();
        contact.setName(firstName.getAttribute("value") + " " + lastName.getAttribute("value"));
        contact.setPhoneNumber(phoneNumber.getAttribute("value"));
        contact.setEmail(email.getAttribute("value"));
        contact.setBirthDate(birthDate.getAttribute("value"));
        return contact;
    }

    public void submit(boolean requestExpected) {
        if (requestExpected) {
            saveButton.click();
        } else {
            guardNoRequest(saveButton).click();
        }
    }

    public boolean isFirstNameValid() {
        return !firstNameRequiredValidationMessage.isDisplayed() && !firstNameFormatValidationMessage.isDisplayed();
    }

    public boolean isLastNameValid() {
        return !lastNameRequiredValidationMessage.isDisplayed() && !lastNameFormatValidationMessage.isDisplayed();
    }

    public boolean isPhoneNumberValid() {
        return !phoneNumberRequiredValidationMessage.isDisplayed() && !phoneNumberFormatValidationMessage.isDisplayed();
    }

    public boolean isEmailValid() {
        return !emailRequiredValidationMessage.isDisplayed() && !emailFormatValidationMessage.isDisplayed();
    }

    public boolean isMessageDisplayed(String message) {
        System.out.println("Checking that email is unique");
        for(MessageFragment m : messages) {
            System.out.println("Message: "+m);
            if(m.getMessageText().equals(message)) {
                return false;
            }
        }
        return true;
    }

    public boolean isBirthDateValid() {
        return !birthDateRequiredValidationMessage.isDisplayed();
    }

    public boolean isFormValid() {
        return validationMessages.size() == 0;
    }

    public void waitForPage() {
        waitAjax().withTimeout(10, TimeUnit.SECONDS).pollingEvery(2, TimeUnit.SECONDS).until().element(firstName).is().present();
    }

}
