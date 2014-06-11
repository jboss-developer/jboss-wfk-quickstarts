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
package org.jboss.quickstarts.wfk.contacts.security;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A RESTful endpoint to create new users.</p>
 *
 * @author Pedro Igor
 */
@Stateless
@Path("/security/registration")
public class UserRegistrationService {

    @Inject
    private IdentityManager identityManager;

    /**
     * Create a user based on the information passed in. 
     * 
     * @param userRegistration
     * @return Response - 200 (OK) with the new user 
     *      <p>Response - 400 (Bad Request) if the username already exists.</p>
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(UserRegistration userRegistration) {
        
        // Create a User based on the userName.
        User newUser = new User(userRegistration.getUserName());

        // Add the first and last name of the user.
        newUser.setFirstName(userRegistration.getFirstName());
        newUser.setLastName(userRegistration.getLastName());

        // Check if the userName is already being used. If it is send an error message back. If not store the password.
        if (BasicModel.getUser(this.identityManager, newUser.getLoginName()) != null) {
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("userName", "The userName is already registered.");
            return Response.status(Response.Status.BAD_REQUEST).entity(responseObj).build();
        } else {
            this.identityManager.add(newUser);

            Password password = new Password(userRegistration.getPassword());

            this.identityManager.updateCredential(newUser, password);

            return Response.ok().entity(newUser).build();
        }
    }

    /**
     * <p>This is an object that holds the user information before it gets registered in the system.  You can set the 
     * first and last name, username, and password.</p>
     * 
     */
    public static class UserRegistration {

        private String firstName;
        private String lastName;
        private String userName;
        private String password;

        public String getFirstName() {
            return this.firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return this.lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUserName() {
            return this.userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
