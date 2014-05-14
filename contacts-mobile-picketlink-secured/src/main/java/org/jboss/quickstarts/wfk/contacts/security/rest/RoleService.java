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

import org.jboss.quickstarts.wfk.contacts.security.annotation.AllowedRoles;
import org.jboss.quickstarts.wfk.contacts.security.model.ApplicationRole;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Grant;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.User;
import org.picketlink.idm.query.RelationshipQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

/**
 * <p>A RESTful endpoint providing a few methods to manage roles.</p>
 *
 * @author Pedro Igor
 */
@Stateless
@Path("/security/role")
public class RoleService {

    @Inject
    private IdentityManager identityManager;

    @Inject
    private RelationshipManager relationshipManager;

    @AllowedRoles(ApplicationRole.ADMIN)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(ApplicationRole.values()).build();
    }

    @AllowedRoles(ApplicationRole.ADMIN)
    @Path("/assign/{userName}/{roleName}")
    @POST
    public Response assign(@PathParam("userName") String userName, @PathParam("roleName") String roleName) {
        User user = BasicModel.getUser(this.identityManager, userName);
        Role role = BasicModel.getRole(this.identityManager, roleName);

        if (user == null || role == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if ("admin".equals(user.getLoginName())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        RelationshipQuery<Grant> query = this.relationshipManager.createRelationshipQuery(Grant.class);

        query.setParameter(Grant.ASSIGNEE, user);

        List<Grant> result = query.getResultList();

        if (!result.isEmpty()) {
            Grant grant = result.get(0);

            this.relationshipManager.remove(grant);
        }

        BasicModel.grantRole(this.relationshipManager, user, role);

        return Response.ok().build();
    }

}
