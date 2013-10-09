/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
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
package org.jboss.as.quickstarts.spring.petclinic.test.webdriver;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 *
 * @author <a href="mailto:kpiwko@redhat.com">Karel Piwko</a>
 *
 */
public class Deployments {
    private static final String PETCLINIC = "../target/jboss-petclinic-spring.war";

    public static WebArchive archive() {
        WebArchive archive = ShrinkWrap.createFromZipFile(WebArchive.class, new File(PETCLINIC));

        String discriminator = System.getProperty("discriminator");
        if (discriminator.equals("jdbc")) {
            archive.addAsWebInfResource(new File("src/test/resources/jdbc_web.xml"), "web.xml");
        } else if (discriminator.equals("spring-data-jpa")) {
            archive.addAsWebInfResource(new File("src/test/resources/spring_data_jpa_web.xml"), "web.xml");
        }
        return archive;
    }
}
