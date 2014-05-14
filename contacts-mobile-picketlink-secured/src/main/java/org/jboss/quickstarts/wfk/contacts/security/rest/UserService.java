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

import org.jboss.quickstarts.wfk.contacts.security.AuthorizationManager;
import org.jboss.quickstarts.wfk.contacts.security.annotation.UserLoggedIn;
import org.jboss.quickstarts.wfk.contacts.security.model.ApplicationRole;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.basic.User;
import org.picketlink.idm.query.IdentityQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>A RESTful endpoint providing a few methods to manage users.</p>
 *
 * @author Pedro Igor
 */
@Stateless
@Path("/security/user")
public class UserService {

    @Inject
    private Identity identity;

    @Inject
    private IdentityManager identityManager;

    @Inject
    private AuthorizationManager authorizationManager;

    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UserLoggedIn
    public Response getCurrentUser() {
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(this.identity.getAccount(), this.authorizationManager.hasRole(ApplicationRole.ADMIN.name()));

        return Response.ok().entity(authenticatedUser).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UserLoggedIn
    public Response getAll() {
        IdentityQuery<User> query = this.identityManager.createIdentityQuery(User.class);
        List<User> result = query.getResultList();
        User currentUser = (User) this.identity.getAccount();

        for (User user : new ArrayList<User>(result)) {
            if ("admin".equals(user.getLoginName()) || currentUser.getLoginName().equals(user.getLoginName())) {
                result.remove(user);
            }
        }

        return Response.ok().entity(result).build();
    }

    /**
     * <p>A simple class representing a user from a client perspective. It will usually be returned by the server with all user information, using JSON.</p>
     */
    public static class AuthenticatedUser {

        private Account account;
        private boolean isAdmin;

        public AuthenticatedUser(Account account, boolean isAdmin) {
            this.account = account;
            this.isAdmin = isAdmin;
        }

        public Account getAccount() {
            return this.account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public boolean isAdmin() {
            return this.isAdmin;
        }

        public void setAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }
    }
}
