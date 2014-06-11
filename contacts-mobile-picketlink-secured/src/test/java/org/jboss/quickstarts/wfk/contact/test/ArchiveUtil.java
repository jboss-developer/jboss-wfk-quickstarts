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
package org.jboss.quickstarts.wfk.contact.test;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

/**
 * @author Pedro Igor
 */
public class ArchiveUtil {

    /**
     * <p>Creates a default {@link org.jboss.shrinkwrap.api.spec.WebArchive} containing all the necessary resources
     * to fully test the application.</p>
     *
     * @return
     */
    public static Archive<?> createTestArchive() {
        File[] libs = Maven.resolver().resolve(
            "org.picketlink:picketlink-common:2.5.3.SP5-redhat-1",
            "org.picketlink:picketlink-api:2.5.3.SP5-redhat-1",
            "org.picketlink:picketlink-impl:2.5.3.SP5-redhat-1",
            "org.picketlink:picketlink-idm-api:2.5.3.SP5-redhat-1",
            "org.picketlink:picketlink-idm-impl:2.5.3.SP5-redhat-1",
            "org.apache.deltaspike.modules:deltaspike-security-module-api:0.5",
            "org.apache.deltaspike.modules:deltaspike-security-module-impl:0.5",
            "org.codehaus.jackson:jackson-mapper-asl:1.9.9"
        ).withTransitivity().asFile();

        WebArchive archive = ShrinkWrap
            .create(WebArchive.class, "test.war")
            .addPackages(true, "org.jboss.quickstarts.wfk.contacts")
            .addAsLibraries(libs)
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource("arquillian-ds.xml")
            .addAsWebInfResource(AuthenticationTest.class.getResource("/beans.xml"), "beans.xml")
            .addAsWebInfResource(AuthenticationTest.class.getResource("/web.xml"), "web.xml");

        return archive;
    }
}
