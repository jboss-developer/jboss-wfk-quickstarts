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
import org.jboss.as.quickstarts.kitchensink.test.Member;
import org.openqa.selenium.WebElement;

import static org.jboss.arquillian.graphene.Graphene.waitAjax;

public class RegistrationFormPageFragment {

    /**
     * Locator for name field
     */
    @FindByJQuery("[id*='newMemberPanel'] [id*='name']")
    private WebElement nameField;

    /**
     * Locator for email field
     */
    @FindByJQuery("[id*='newMemberPanel'] [id*='email']")
    private WebElement emailField;

    /**
     * Locator for phone number field
     */
    @FindByJQuery("[id*='newMemberPanel'] [id*='phoneNumber']")
    private WebElement phoneField;

    /**
     * Locator for registration button
     */
    @FindByJQuery("[id*='newMemberPanel'] [id*='register']")
    private WebElement registerButton;

    /**
     * Locator for name field validation message
     */
    @FindByJQuery("[id*='newMemberPanel'] span[id*='name'].invalid")
    private WebElement nameErrorMessage;

    /**
     * Locator for email field validation message
     */
    @FindByJQuery("[id*='newMemberPanel'] span[id*='email'].invalid")
    private WebElement emailErrorMessage;

    /**
     * Locator for phone number field validation message
     */
    @FindByJQuery("[id*='newMemberPanel'] span[id*='phoneNumber'].invalid")
    private WebElement phoneNumberErrorMessage;


    public void register(Member member) {
        nameField.clear();
        nameField.sendKeys(member.getName());
        emailField.clear();
        emailField.sendKeys(member.getEmail());
        phoneField.clear();
        phoneField.sendKeys(member.getPhoneNumber());
        registerButton.click();
    }

    public String waitForNameValidation() {
        waitAjax().until().element(nameErrorMessage).is().present();
        return nameErrorMessage.getText();
    }

    public String waitForEmailValidation() {
        waitAjax().until().element(emailErrorMessage).is().present();
        return emailErrorMessage.getText();
    }

    public String waitForPhoneValidation() {
        waitAjax().until().element(phoneNumberErrorMessage).is().present();
        return phoneNumberErrorMessage.getText();
    }
}
