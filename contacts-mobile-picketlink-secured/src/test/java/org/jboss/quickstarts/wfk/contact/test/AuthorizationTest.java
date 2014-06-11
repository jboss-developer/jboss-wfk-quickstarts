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
package org.jboss.quickstarts.wfk.contact.test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.WebResponse;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>Tests the authorization capabilities of the application.</p>
 *
 * @author Pedro Igor
 */
public class AuthorizationTest extends AbstractRESTSecurityTest {

    @Test
    public void testAuthorized() throws Exception {
        WebClient client = new WebClient();
        WebRequestSettings request = new WebRequestSettings(getPath("rest/security/user/info"));

        prepareAuthenticationRequest(request, "admin", "admin");

        WebResponse response = client.loadWebResponse(request);

        assertEquals("User should be authenticated.", HttpStatus.SC_OK, response.getStatusCode());

        request = new WebRequestSettings(getPath("rest/security/role"));
        response = client.loadWebResponse(request);

        assertEquals("User should be authenticated.", HttpStatus.SC_OK, response.getStatusCode());
    }

    @Test
    public void testNotAuthorized() throws Exception {
        WebClient client = new WebClient();
        WebRequestSettings request = new WebRequestSettings(getPath("rest/security/user/info"));

        prepareAuthenticationRequest(request, DEFAULT_USERNAME, DEFAULT_USER_PASSWD);

        WebResponse response = client.loadWebResponse(request);

        assertEquals("User should be authenticated.", HttpStatus.SC_OK, response.getStatusCode());

        request = new WebRequestSettings(getPath("rest/security/role"));
        response = client.loadWebResponse(request);

        assertEquals("User should not have permission to access this resource.", HttpStatus.SC_FORBIDDEN, response.getStatusCode());
    }

}
