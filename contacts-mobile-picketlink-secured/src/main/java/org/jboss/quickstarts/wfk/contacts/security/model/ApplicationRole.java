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
package org.jboss.quickstarts.wfk.contacts.security.model;

/**
 * <p>A {@link Enum} with all roles supported by the application.</p>
 *
 * @see org.jboss.quickstarts.wfk.contacts.security.annotation.AllowedRoles
 *
 * @author Pedro Igor
 */
public enum ApplicationRole {

    /**
     * <p>Read-only permission.</p>
     */
    USER,

    /**
     * <p>Create and edit permissions.</p>
     */
    MAINTAINER,

    /**
     * <p>Can do anything.</p>
     */
    ADMIN

}
