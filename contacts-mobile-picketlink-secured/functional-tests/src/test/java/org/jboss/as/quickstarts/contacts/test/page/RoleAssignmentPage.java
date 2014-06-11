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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static org.jboss.arquillian.graphene.Graphene.waitAjax;

public class RoleAssignmentPage {

    @FindBy(id = "role-assignment-users-select")
    private WebElement userSelect;

    @FindBy(id = "role-assignment-role-select")
    private WebElement roleSelect;

    @FindBy(id = "submit-role-assignment-btn")
    private WebElement assignRoleButton;

    @FindByJQuery("#role-assignment-popup h3")
    private WebElement message;

    @FindByJQuery("#role-assignment-popup-ok-btn")
    private WebElement popupOkButton;

    @FindByJQuery("#role-assignment-popup-more-roles-btn")
    private WebElement popupAssignMoreButton;

    public void setRole(String user, String role) {
        new Select(userSelect).selectByVisibleText(user);
        new Select(roleSelect).selectByVisibleText(role);
        assignRoleButton.click();
        waitAjax().until().element(popupOkButton).is().visible();
        popupOkButton.click();
    }
}
