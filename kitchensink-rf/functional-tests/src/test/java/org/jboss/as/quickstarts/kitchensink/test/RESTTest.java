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

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.json.JSONArray;
import org.json.JSONStringer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

/**
 * Test for REST API of the application
 *
 * @author Oliver Kiss
 */
@RunAsClient
@RunWith(Arquillian.class)
public class RESTTest {

    private static final String NEW_MEMBER_NAME = "John Doe";
    private static final String NEW_MEMBER_EMAIL = "john.doe@redhat.com";
    private static final String NEW_MEMBER_PHONE = "1234567890";

    private static final String API_PATH = "rest/members";

    private final DefaultHttpClient httpClient = new DefaultHttpClient();

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

    @Test
    @InSequence(1)
    public void testAddMember() throws Exception {
        HttpPost post = new HttpPost(contextPath.toString() + API_PATH);
        post.setHeader("Content-Type", "application/json");
        String newMemberJSON = new JSONStringer().object()
                .key("name").value(NEW_MEMBER_NAME)
                .key("email").value(NEW_MEMBER_EMAIL)
                .key("phoneNumber").value(NEW_MEMBER_PHONE)
                .endObject().toString();
        post.setEntity(new StringEntity(newMemberJSON));

        HttpResponse response = httpClient.execute(post);

        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    @InSequence(2)
    public void testGetMember() throws Exception {
        DefaultHttpClient httpClient = this.httpClient;

        HttpResponse response = httpClient.execute(new HttpGet(contextPath.toString() + API_PATH));
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        String responseBody = EntityUtils.toString(response.getEntity());
        JSONArray members = new JSONArray(responseBody);

        Assert.assertEquals(1, members.length());

        Assert.assertEquals(1, members.getJSONObject(0).getInt("id"));
        Assert.assertEquals(NEW_MEMBER_NAME, members.getJSONObject(0).getString("name"));
        Assert.assertEquals(NEW_MEMBER_EMAIL, members.getJSONObject(0).getString("email"));
        Assert.assertEquals(NEW_MEMBER_PHONE, members.getJSONObject(0).getString("phoneNumber"));
    }
}
