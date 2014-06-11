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

import static org.jboss.arquillian.graphene.Graphene.waitAjax;

public class LoginPage {

    @FindBy(id = "signin-form-input-email")
    private WebElement usernameInput;

    @FindBy(id = "signin-form-input-password")
    private WebElement passwordInput;

    @FindBy(id = "submit-signin-btn")
    private WebElement signInButton;

    @FindBy(id = "submit-go-signup-btn")
    private WebElement signUpButton;

    @FindByJQuery("#signin-form .invalid")
    private WebElement errorMessage;

    @Drone
    private WebDriver browser;

    public boolean signIn(String username, String password) {
        waitAjax().until().element(usernameInput).is().visible();
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        signInButton.click();
        return new WebElementConditionFactory(errorMessage).not().isPresent().apply(browser);
    }

    public void signUp() {
        signUpButton.click();
    }

    public void waitForPage() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
