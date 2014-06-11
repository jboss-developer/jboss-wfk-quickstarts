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
package org.jboss.quickstarts.wfk.contacts.security.authorization;

import org.apache.deltaspike.security.api.authorization.Secures;
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
    private Instance<IdentityManager> identityManager;

    @Inject
    private Instance<RelationshipManager> relationshipManager;

    /**
     * <p>
     *  This authorization method provides the validation logic for resources annotated with the security annotation {@link RolesAllowed}.
     * </p>
     * <p>
     *  Note that this method is also annotated with {@link Secures}, which is an annotation from Apache DeltaSpike.
     *  This annotation tells the @{link SecurityInterceptor} that this method must be called before the execution of
     *  methods annotated with {@link RolesAllowed} in order to perform authorization checks.
     * </p>
     *
     * @param invocationContext
     * @param manager
     * @return true if the user can execute the method or class
     * @throws Exception
     */
    @Secures
    @RolesAllowed
    public boolean checkDeclaredRoles(InvocationContext invocationContext, BeanManager manager) throws Exception {

        // If not logged in then exit the method.
        if (!isLoggedIn()) {
            return false;
        }

        // First check if they are an administrator, they can access everything so no need to keep looking.
        if (hasRole(ApplicationRole.ADMIN.name())) {
            return true;
        }
        ApplicationRole[] requiredRoles = getDeclaredRoles(invocationContext);

        // Loop through the Roles and check if the user has one. 
        for (ApplicationRole requiredRole: requiredRoles) {
            if (hasRole(requiredRole.name())) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>Returns the declared roles allowed to access the bean or method within the current {@link javax.interceptor.InvocationContext}.</p>
     *
     * <p>It first tries to look for {@link org.jboss.quickstarts.wfk.contacts.security.authorization.RolesAllowed} annotation at the bean level.
     * If not found checks if it is defined in the method being invoked.</p>
     *
     * @param invocationContext The current invocation context.
     *
     * @return An array with all roles declared by the bean or method related with the current invocation context. The array is never empty.
     *
     * @throws java.lang.IllegalArgumentException If no {@link org.jboss.quickstarts.wfk.contacts.security.authorization.RolesAllowed}
     * annotation was declared in the bean or method related with the current invocation context. Or if no role was specified within
     * a {@link org.jboss.quickstarts.wfk.contacts.security.authorization.RolesAllowed} delcaration.
     */
    private ApplicationRole[] getDeclaredRoles(InvocationContext invocationContext) throws IllegalArgumentException {
        // Get the security annotation of the Roles from to be used.
        RolesAllowed declareRoles = invocationContext.getTarget().getClass().getAnnotation(RolesAllowed.class);

        // If the security annotation did not populate the value with the Roles from the enum, do that now.
        if (declareRoles == null) {
            declareRoles = invocationContext.getMethod().getAnnotation(RolesAllowed.class);
        }

        // Store the Roles in an enum.
        ApplicationRole[] requiredRoles;

        if (declareRoles != null) {
            requiredRoles = declareRoles.value();
        } else {
            throw new IllegalArgumentException("Bean or method does not declares a @RolesAllowed security annotation.");
        }

        // If we don't have any Roles we have a problem.
        if (requiredRoles.length == 0) {
            throw new IllegalArgumentException("@AllowedRoles does not define any role.");
        }

        return requiredRoles;
    }

    /**
     * <p>Authorization check for {@link RequiresAccount} annotation.</p>
     *
     * @return true if the user is logged in
     */
    @Secures
    @RequiresAccount
    public boolean isLoggedIn() {
        return getIdentity().isLoggedIn();
    }

    /**
     * <p>This method checks if the current Identity has the specified Role.</p>
     * 
     * @param roleName
     * @return true if the Identity has the Role
     */
    public boolean hasRole(String roleName) {
        Account account = getIdentity().getAccount();
        Role role = getRole(getIdentityManager(), roleName);

        return BasicModel.hasRole(getRelationshipManager(), account, role);
    }

    /**
     * <p>Given that this bean is application scoped, we need to always get a {@link org.picketlink.Identity} reference
     * from an {@link javax.enterprise.inject.Instance}.</p>
     * 
     * @return Identity
     */
    private Identity getIdentity() {
        return this.identityInstance.get();
    }

    /**
     * <p>Given that this bean is application scoped, we need to always get a {@link org.picketlink.idm.RelationshipManager} reference
     * from an {@link javax.enterprise.inject.Instance}.</p>
     *
     * @return Identity
     */
    private RelationshipManager getRelationshipManager() {
        return this.relationshipManager.get();
    }

    /**
     * <p>Given that this bean is application scoped, we need to always get a {@link org.picketlink.idm.IdentityManager} reference
     * from an {@link javax.enterprise.inject.Instance}.</p>
     *
     * @return Identity
     */
    private IdentityManager getIdentityManager() {
        return this.identityManager.get();
    }
}