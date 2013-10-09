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




/**
 * @author mjobanek
 *
 */
public class Vet {

    private String firstName;

    private String lastName;

    private String[] specialties;

    /**
     * @param firstName
     * @param lastName
     * @param specialties
     */
    public Vet(String firstName, String lastName, String[] specialties) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialties = specialties;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the specialties
     */
    public String[] getSpecialties() {
        return specialties;
    }

    /**
     * @param specialties the specialties to set
     */
    public void setSpecialties(String[] specialties) {
        this.specialties = specialties;
    }



}
