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
package org.jboss.quickstarts.wfk.contacts.security.rest;

import org.jboss.quickstarts.wfk.contacts.security.annotation.UserLoggedIn;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>This is a simple RESTful service that returns a HTTP Status Code 200 after a successful authentication.</p>
 *
 * <p>Before reaching this service, the {@link org.picketlink.authentication.web.AuthenticationFilter}, configured in <code>web.xml</code>,
 * kicks in to authenticate the user.</p>
 *
 * <p>The {@link org.jboss.quickstarts.wfk.contacts.security.annotation.UserLoggedIn} enforce that only previously authenticated users are allowed to invoke this service.</p>
 *
 * @author Pedro Igor
 */
@Path("/security/login")
public class LoginService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @UserLoggedIn
    public Response login() {
        return Response.ok().build();
    }

}
