/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
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
package org.jboss.resteasy.test.jboss;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Assert;
import org.junit.Test;
import org.jboss.resteasy.util.HttpResponseCodes;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 */
public class SmokeTest
{
    @Test
    public void testNoDefaultsResource() throws Exception
    {
        HttpClient client = new HttpClient();

        {
            GetMethod method = new GetMethod("http://localhost:8080/spring-integration-test/basic");
            int status = client.executeMethod(method);
            Assert.assertEquals(HttpResponseCodes.SC_OK, status);
            Assert.assertEquals("basic", method.getResponseBodyAsString());
            method.releaseConnection();
        }
        {
            PutMethod method = new PutMethod("http://localhost:8080/spring-integration-test/basic");
            method.setRequestEntity(new StringRequestEntity("basic", "text/plain", null));
            int status = client.executeMethod(method);
            Assert.assertEquals(204, status);
            method.releaseConnection();
        }
        {
            GetMethod method = new GetMethod("http://localhost:8080/spring-integration-test/queryParam");
            NameValuePair[] params = { new NameValuePair("param", "hello world") };
            method.setQueryString(params);
            int status = client.executeMethod(method);
            Assert.assertEquals(HttpResponseCodes.SC_OK, status);
            Assert.assertEquals("hello world", method.getResponseBodyAsString());
            method.releaseConnection();
        }
        {
            GetMethod method = new GetMethod("http://localhost:8080/spring-integration-test/uriParam/1234");
            int status = client.executeMethod(method);
            Assert.assertEquals(HttpResponseCodes.SC_OK, status);
            Assert.assertEquals("1234", method.getResponseBodyAsString());
            method.releaseConnection();
        }
    }

    @Test
    public void testLocatingResource() throws Exception
    {
        HttpClient client = new HttpClient();

        {
            GetMethod method = new GetMethod("http://localhost:8080/spring-integration-test/locating/basic");
            int status = client.executeMethod(method);
            Assert.assertEquals(HttpResponseCodes.SC_OK, status);
            Assert.assertEquals("basic", method.getResponseBodyAsString());
            method.releaseConnection();
        }
        {
            PutMethod method = new PutMethod("http://localhost:8080/spring-integration-test/locating/basic");
            method.setRequestEntity(new StringRequestEntity("basic", "text/plain", null));
            int status = client.executeMethod(method);
            Assert.assertEquals(204, status);
            method.releaseConnection();
        }
        {
            GetMethod method = new GetMethod("http://localhost:8080/spring-integration-test/locating/queryParam");
            NameValuePair[] params = { new NameValuePair("param", "hello world") };
            method.setQueryString(params);
            int status = client.executeMethod(method);
            Assert.assertEquals(HttpResponseCodes.SC_OK, status);
            Assert.assertEquals("hello world", method.getResponseBodyAsString());
            method.releaseConnection();
        }
        {
            GetMethod method = new GetMethod("http://localhost:8080/spring-integration-test/locating/uriParam/1234");
            int status = client.executeMethod(method);
            Assert.assertEquals(HttpResponseCodes.SC_OK, status);
            Assert.assertEquals("1234", method.getResponseBodyAsString());
            method.releaseConnection();
        }
    }

    /*
     * public static Test suite() throws Exception { System.out.println("***********");
     * System.out.println(System.getProperty("jbosstest.deploy.dir")); return getDeploySetup(SmokeTest.class,
     * "spring-integration-test.war"); }
     */
}
