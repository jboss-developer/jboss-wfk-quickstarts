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

import com.gargoylesoftware.htmlunit.WebRequestSettings;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.runner.RunWith;
import org.picketlink.common.util.Base64;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>Base class for REST security-related test cases.</p>
 *
 * @author Pedro Igor
 */
@RunWith(Arquillian.class)
public abstract class AbstractRESTSecurityTest {

    protected static final String DEFAULT_USERNAME = "duke";
    protected static final String DEFAULT_USER_PASSWD = "duke";

    @ArquillianResource
    private URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        return ArchiveUtil.createTestArchive();
    }

    protected URL getPath(String path) throws MalformedURLException {
        return new URL(this.contextPath.toString() + path);
    }

    /**
     * <p>Prepares a request with al the necessary information to initiate an authentication.</p>
     *
     * @param request the current request.
     * @param userName the user's name.
     * @param passwd the user's password.
     */
    protected void prepareAuthenticationRequest(WebRequestSettings request, String userName, String passwd) {
        request.addAdditionalHeader("Authorization", new String("Basic " + Base64.encodeBytes(String.valueOf(userName + ":" + passwd).getBytes())));
    }

}
