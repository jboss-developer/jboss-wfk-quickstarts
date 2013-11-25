/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.quickstarts.greeter.greeter_spring.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.net.URL;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.junit.Assert.assertTrue;

/**
 * Tests Greeter Spring quickstart
 *
 * @author Oliver Kiss
 */
@RunAsClient
@RunWith(Arquillian.class)
public class GreeterSpringTest {

    /**
     * Locator for name input field
     */
    @FindByJQuery("input[name='username']")
    WebElement userNameInput;

    /**
     * Locator for submit button
     */
    @FindByJQuery("input[value='Greet!']")
    WebElement greetButton;

    /**
     * Locator for greeting message
     */
    @FindByJQuery("table tr:nth-child(2) td")
    WebElement message;

    /**
     * Locator for add new user link
     */
    @FindByJQuery("table tr:nth-child(4) a")
    WebElement newUserLink;

    /**
     * Locator for new user username input
     */
    @FindByJQuery("tr:contains(user) input")
    WebElement newUserUsername;

    /**
     * Locator for new user first name input
     */
    @FindByJQuery("tr:contains(first) input")
    WebElement newUserFirstname;

    /**
     * Locator for new user last name input
     */
    @FindByJQuery("tr:contains(last) input")
    WebElement newUserLastname;

    /**
     * Locator for add user button
     */
    @FindByJQuery("input[value='Add User']")
    WebElement addUserButton;

    /**
     * Existing user username
     */
    private static final String EXISTING_USERNAME = "jdoe";

    /**
     * Existing user first name
     */
    private static final String EXISTING_FISRT_NAME = "John";

    /**
     * Existing user last name
     */
    private static final String EXISTING_LAST_NAME = "Doe";

    /**
     * New user username
     */
    private static final String NEW_USERNAME = "janedoe";

    /**
     * New user first name
     */
    private static final String NEW_FISRT_NAME = "Jane";

    /**
     * New user last name
     */
    private static final String NEW_LAST_NAME = "Doe";

    /**
     * No such user error message
     */
    private static final String NO_SUCH_USER_MESSAGE = "No such user exists!";

    /**
     * Specifies relative path to the war of built application in the main project.
     */
    private static final String DEPLOYMENT_WAR = "../target/jboss-greeter-spring.war";

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
    public static WebArchive helloWorld() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File(DEPLOYMENT_WAR));
    }

    @Test
    public void existingUserTest() {
        browser.get(contextPath.toString());
        userNameInput.sendKeys(EXISTING_USERNAME);
        guardHttp(greetButton).click();
        assertTrue(message.getText().contains(EXISTING_FISRT_NAME + " " + EXISTING_LAST_NAME));
    }

    @Test
    public void nonExistentUserTest() {
        browser.get(contextPath.toString());
        userNameInput.sendKeys("notauser");
        guardHttp(greetButton).click();
        assertTrue(message.getText().contains(NO_SUCH_USER_MESSAGE));
    }

    @Test
    public void createUserTest() {
        browser.get(contextPath.toString());
        guardHttp(newUserLink).click();

        newUserUsername.sendKeys(NEW_USERNAME);
        newUserFirstname.sendKeys(NEW_FISRT_NAME);
        newUserLastname.sendKeys(NEW_LAST_NAME);
        guardHttp(addUserButton).click();

        browser.get(contextPath.toString());
        userNameInput.sendKeys(NEW_USERNAME);
        guardHttp(greetButton).click();
        assertTrue(message.getText().contains(NEW_FISRT_NAME + " " + NEW_LAST_NAME));

    }

}
