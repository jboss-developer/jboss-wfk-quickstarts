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

import org.jboss.quickstarts.wfk.contacts.security.authorization.ApplicationRole;
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
 * <p>This EJB is responsible to initialize PicketLink Identity Management with some initial/default data, such as
 * users and roles.</p>
 *
 * <p>Usually, you don't need it in your application because your users and roles are created from a specific functionality
 * in your application. In this case, we just create some default users, roles and how they relate with each other in other to
 * demonstrate some security features.</p>
 *
 * @author Pedro Igor
 */
@Startup
@Singleton
public class SecurityInitializer {

    @Inject
    private PartitionManager partitionManager;

    /**
     * <p>Create the initial set of users.</p> 
     * 
     * <p><b>This should be changed for production use.</b></p>
     */
    @PostConstruct
    public void createUsers() {
        createUser("john", "john", ApplicationRole.USER);
        createUser("duke", "duke", ApplicationRole.MAINTAINER);
        createUser("admin", "admin", ApplicationRole.ADMIN);
    }

    /**
     * Take in the username, password, and Role to create default users.
     * 
     * @param loginName
     * @param password
     * @param userRole
     */
    private void createUser(String loginName, String password, ApplicationRole userRole) {
        IdentityManager identityManager = this.partitionManager.createIdentityManager();

        // user already exists
        if (getUser(identityManager, loginName) != null) {
            return;
        }

        User user = new User(loginName);

        // let's store an user
        identityManager.add(user);

        Password credential = new Password(password);

        // let's update the password-based credential for this user
        identityManager.updateCredential(user, credential);

        Role role = getRole(identityManager, userRole.name());

        // role does not exists, let's create it
        if (role == null) {
            role = new Role(userRole.name());
            identityManager.add(role);
        }

        RelationshipManager relationshipManager = this.partitionManager.createRelationshipManager();

        // let's grant to the user the given role
        grantRole(relationshipManager, user, role);
    }

}
