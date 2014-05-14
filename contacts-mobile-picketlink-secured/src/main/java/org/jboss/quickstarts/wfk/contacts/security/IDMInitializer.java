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

import org.jboss.quickstarts.wfk.contacts.security.model.ApplicationRole;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import static org.picketlink.idm.model.basic.BasicModel.getRole;
import static org.picketlink.idm.model.basic.BasicModel.getUser;
import static org.picketlink.idm.model.basic.BasicModel.grantRole;

/**
 * <p>Initializes the identity stores with some default users and roles.</p>
 *
 * @author Pedro Igor
 */
@Startup
@Singleton
public class IDMInitializer {

    @Inject
    private PartitionManager partitionManager;

    @PostConstruct
    public void createUsers() {
        createUser("john", "john", ApplicationRole.USER);
        createUser("duke", "duke", ApplicationRole.MAINTAINER);
        createUser("admin", "admin", ApplicationRole.ADMIN);
    }

    private void createUser(String loginName, String password, ApplicationRole userRole) {
        IdentityManager identityManager = this.partitionManager.createIdentityManager();

        // user already exists
        if (getUser(identityManager, loginName) != null) {
            return;
        }

        User user = new User(loginName);

        identityManager.add(user);

        Password credential = new Password(password);

        identityManager.updateCredential(user, credential);

        Role role = getRole(identityManager, userRole.name());

        if (role == null) {
            role = new Role(userRole.name());
            identityManager.add(role);
        }

        RelationshipManager relationshipManager = this.partitionManager.createRelationshipManager();

        grantRole(relationshipManager, user, role);
    }

}
