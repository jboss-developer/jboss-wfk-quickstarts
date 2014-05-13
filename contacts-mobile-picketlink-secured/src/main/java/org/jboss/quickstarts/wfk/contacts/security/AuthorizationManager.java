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

import org.apache.deltaspike.security.api.authorization.Secures;
import org.jboss.quickstarts.wfk.contacts.security.annotation.AllowedRoles;
import org.jboss.quickstarts.wfk.contacts.security.annotation.UserLoggedIn;
import org.jboss.quickstarts.wfk.contacts.security.model.ApplicationRole;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;

import static org.picketlink.idm.model.basic.BasicModel.getRole;

/**
 * <p>This class centralizes all authorization services for this application.</p>
 *
 * @author Pedro Igor
 *
 */
@ApplicationScoped
public class AuthorizationManager {

    @Inject
    private Instance<Identity> identityInstance;

    @Inject
    private IdentityManager identityManager;

    @Inject
    private RelationshipManager relationshipManager;

    /**
     * <p>
     *  This authorization method provides the validation logic for resources annotated with the security annotation {@link org.jboss.quickstarts.wfk.contacts.security.annotation.AllowedRoles}.
     * </p>
     * <p>
     *  Note that this method is also annotated with {@link Secures}, which is an annotation from Apache DeltaSpike.
     *  This annotation tells the @{link SecurityInterceptor} that this method must be called before the execution of
     *  methods annotated with {@link org.jboss.quickstarts.wfk.contacts.security.annotation.AllowedRoles} in order to perform authorization checks.
     * </p>
     *
     * @param invocationContext
     * @param manager
     * @return true if the user can execute the method or class
     * @throws Exception
     */
    @Secures
    @AllowedRoles
    public boolean checkDeclaredRoles(InvocationContext invocationContext, BeanManager manager) throws Exception {
        if (!isLoggedIn()) {
            return false;
        }

        // administrators can access everything
        if (hasRole(ApplicationRole.ADMIN.name())) {
            return true;
        }

        AllowedRoles declareRoles = invocationContext.getTarget().getClass().getAnnotation(AllowedRoles.class);

        if (declareRoles == null) {
            declareRoles = invocationContext.getMethod().getAnnotation(AllowedRoles.class);
        }

        ApplicationRole[] requiredRoles = declareRoles.value();

        if (requiredRoles.length == 0) {
            throw new IllegalArgumentException("@AllowedRoles does not define any role.");
        }

        for (ApplicationRole requiredRole: requiredRoles) {
            if (hasRole(requiredRole.name())) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>Authorization check for {@link org.jboss.quickstarts.wfk.contacts.security.annotation.UserLoggedIn} annotation.</p>
     *
     * @return
     */
    @Secures
    @UserLoggedIn
    public boolean isLoggedIn() {
        return this.identityInstance.get().isLoggedIn();
    }

    public boolean hasRole(String roleName) {
        Account account = getIdentity().getAccount();
        Role role = getRole(this.identityManager, roleName);

        return BasicModel.hasRole(this.relationshipManager, account, role);
    }

    private Identity getIdentity() {
        return this.identityInstance.get();
    }
}