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
package org.jboss.as.quickstarts.contacts.test.page.fragment;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.condition.element.WebElementConditionFactory;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.waitGui;

/**
 * Page fragment for controlling navigation panel
 *
 * @author Oliver Kiss
 */
public class NavigationPageFragment {

    @FindByJQuery("[id*='page-menu-button']")
    private WebElement showMenuButton;

    @FindByJQuery(".ui-panel-open")
    private WebElement panel;

    @FindByJQuery(".ui-panel-open a[href*='contacts-add-page']")
    private WebElement addPageLink;

    @FindByJQuery(".ui-panel-open a[href*='contacts-list-page']")
    private WebElement listPageLink;

    @FindByJQuery(".ui-panel-open a[href*='contacts-detail-list-page']")
    private WebElement detailedlistPageLink;

    @FindByJQuery(".ui-panel-open a[href*='role-assignment-page']")
    private WebElement roleAssignmentPageLink;

    @FindByJQuery(".security-logout-btn:visible")
    private WebElement logoutLink;

    @Drone
    private WebDriver browser;

    public void openAddPage() {
        openPage(addPageLink);
    }

    public void openListPage() {
        openPage(listPageLink);
    }

    public void openDetailedListPage() {
        openPage(detailedlistPageLink);
    }

    public void openRoleAssignmentPage() {
        openPage(roleAssignmentPageLink);
    }

    public void logout() {
        openPage(logoutLink);
    }

    public boolean isRoleAssignmentAccessible() {
        showMenuButton.click();
        waitAjax().until().element(panel).is().present();
        boolean ret = new WebElementConditionFactory(roleAssignmentPageLink).isVisible().apply(browser);
        showMenuButton.click();
        waitAjax().until().element(panel).is().not().present();
        return ret;
    }

    private void openPage(WebElement link) {
        showMenuButton.click();
        waitGui().until().element(link).is().visible();
        link.click();
        waitGui().until().element(link).is().not().visible();
    }
}
