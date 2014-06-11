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
import org.jboss.arquillian.graphene.condition.element.WebElementConditionFactory;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage {

    @FindBy(id = "signup-form-input-firstName")
    private WebElement firstNameInput;

    @FindBy(id = "signup-form-input-lastName")
    private WebElement lastNameInput;

    @FindBy(id = "signup-form-input-userName")
    private WebElement usernameInput;

    @FindBy(id = "signup-form-input-password")
    private WebElement passwordInput;

    @FindByJQuery("label[for*='input-userName'].error")
    private WebElement usernameValidationMessage;

    @FindByJQuery("[onclick*='#submit']:visible")
    private WebElement saveButton;

    @Drone
    private WebDriver browser;

    public void register(String firstName, String lastName, String username, String password, boolean requestExpected) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        if (requestExpected) {
            saveButton.click();
        } else {
            saveButton.click();
        }
    }

    public boolean isUsernameValid() {
        return new WebElementConditionFactory(usernameValidationMessage).not().isPresent().apply(browser);
    }


}
