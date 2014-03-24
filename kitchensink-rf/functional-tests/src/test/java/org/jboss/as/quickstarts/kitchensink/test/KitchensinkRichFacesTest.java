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
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.quickstarts.kitchensink.test.page.MembersTablePageFragment;
import org.jboss.as.quickstarts.kitchensink.test.page.RegistrationFormPageFragment;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.net.URL;

import static junit.framework.Assert.assertEquals;

/**
 * Kitchensink RichFaces quickstart functional test
 *
 * @author Oliver Kiss
 */
@RunAsClient
@RunWith(Arquillian.class)
public class KitchensinkRichFacesTest {

    @FindBy(id = "reg")
    RegistrationFormPageFragment form;

    @FindBy(id = "reg:memberList")
    MembersTablePageFragment table;

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

    @Before
    public void loadPage() {
        browser.get(contextPath.toString());
    }

    @Test
    @InSequence(1)
    public void testEmptyRegistration() {
        form.register(new Member("", "", ""));
        assertEquals(form.waitForNameValidation(), NAME_INPUT_MESSAGE_SIZE);
        assertEquals(form.waitForEmailValidation(), EMAIL_INPUT_MESSAGE_NOT_EMPTY);
        form.waitForPhoneValidation();
        assertEquals("User should not be registered", 0, table.getMemberCount());
    }

    @Test
    @InSequence(2)
    public void testRegistrationWithBadNameFormat() {
        form.register(new Member(NAME_FORMAT_BAD, EMAIL_FORMAT_OK, PHONE_FORMAT_OK));
        assertEquals(form.waitForNameValidation(), NAME_INPUT_MESSAGE_FORMAT);
        assertEquals("User should not be registered", 0, table.getMemberCount());
    }

    @Test
    @InSequence(3)
    public void testRegistrationWithBadEmailFormat() {
        form.register(new Member(NAME_FORMAT_OK, EMAIL_FORMAT_BAD, PHONE_FORMAT_OK));
        assertEquals(form.waitForEmailValidation(), EMAIL_INPUT_MESSAGE_FORMAT);
        assertEquals("User should not be registered", 0, table.getMemberCount());
    }

    @Test
    @InSequence(4)
    public void testRegistrationWithBadPhoneFormat() {
        // phone number with illegal characters in it
        form.register(new Member(NAME_FORMAT_OK, EMAIL_FORMAT_OK, PHONE_FORMAT_BAD_ILLEGAL_CHARS));
        form.waitForPhoneValidation();

        // phone too short
        browser.get(contextPath.toString());
        form.register(new Member(NAME_FORMAT_OK, EMAIL_FORMAT_OK, PHONE_FORMAT_BAD_TOO_SHORT));
        form.waitForPhoneValidation();

        // phone too long
        browser.get(contextPath.toString());
        form.register(new Member(NAME_FORMAT_OK, EMAIL_FORMAT_OK, PHONE_FORMAT_BAD_TOO_LONG));
        form.waitForPhoneValidation();

        assertEquals("User should not be registered", 0, table.getMemberCount());
    }

    @Test
    @InSequence(5)
    public void testRegularRegistration() {
        Member newMember = new Member(NAME_FORMAT_OK, EMAIL_FORMAT_OK, PHONE_FORMAT_OK);
        form.register(newMember);
        table.waitForNewMember(newMember);

        assertEquals(1, table.getMemberCount());
        assertEquals(newMember, table.getLatestMember());
    }
}
