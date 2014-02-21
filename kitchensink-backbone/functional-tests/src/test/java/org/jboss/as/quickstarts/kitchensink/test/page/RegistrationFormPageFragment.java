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

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.condition.element.WebElementConditionFactory;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.jboss.as.quickstarts.kitchensink.test.Member;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.*;

public class RegistrationFormPageFragment {
    /**
     * Injects browser to our test.
     */
    @Drone
    private WebDriver browser;

    /**
     * Locator for the registration form
     */
    @Root
    private WebElement registrationForm;

    /**
     * Locator for name field
     */
    @FindBy(id = "name")
    private WebElement nameField;

    /**
     * Locator for email field
     */
    @FindBy(id = "email")
    private WebElement emailField;

    /**
     * Locator for phone number field
     */
    @FindBy(id = "phoneNumber")
    private WebElement phoneField;

    /**
     * Locator for registration button
     */
    @FindBy(id = "register")
    private WebElement registerButton;

    /**
     * Locator for registration success message
     */
    @FindByJQuery("div#formMsgs span.success")
    private WebElement registeredMessageSuccess;

    /**
     * Locator for name field validation message
     */
    @FindBy(css = "#name ~ .invalid")
    private WebElement nameErrorMessage;


    public void register(Member member, boolean submissionExpected) {
        nameField.clear();
        nameField.sendKeys(member.getName());
        emailField.clear();
        emailField.sendKeys(member.getEmail());
        phoneField.clear();
        phoneField.sendKeys(member.getPhoneNumber());

        if (submissionExpected) {
            guardAjax(registerButton).click();
        } else {
            guardNoRequest(registerButton).click();
        }
    }

    public boolean isRegistrationMessagePresent() {
        return new WebElementConditionFactory(registeredMessageSuccess).isPresent().apply(browser);
    }

    public String nameValidationMessage() {
        waitModel().until().element(nameErrorMessage).is().visible();
        return nameErrorMessage.getText();
    }

    public void waitUntilPresent() {
        waitModel().until().element(registrationForm).is().present();
    }

}
