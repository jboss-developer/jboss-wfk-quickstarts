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
package org.jboss.quickstarts.wfk.contact;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.logging.Logger;

/**
 * This is a Data Access Object and connects the Service object with the Domain Object.  There are no access modifiers
 * on the methods making them 'package' scope.  They should only be accessed by a Service. 
 * 
 * @author Joshua Wilson
 *
 */
//The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class ContactDAO {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Contact> contactEventSrc;

    /**
     * Find just one Contact by it's ID.
     * 
     * @param id
     * @return Contact
     */
    Contact findById(Long id) {
        return em.find(Contact.class, id);
    }

    /**
     * Find just one Contact by the email that is passed in. If there is more then one, only the first will be returned.
     * 
     * @param email
     * @return Contact
     */
    Contact findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = cb.createQuery(Contact.class);
        Root<Contact> contact = criteria.from(Contact.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(contact).where(cb.equal(contact.get(Contact_.email), email));
        criteria.select(contact).where(cb.equal(contact.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }

    /**
     * Find just one Contact by the first name that is passed in. If there is more then one, only the first will be returned.
     * 
     * @param firstName
     * @return Contact
     */
    Contact findByFirstName(String firstName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = cb.createQuery(Contact.class);
        Root<Contact> contact = criteria.from(Contact.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(contact).orderBy(cb.asc(contact.get(Contact_.firstName), firstName));
        criteria.select(contact).where(cb.equal(contact.get("firstName"), firstName));
        return em.createQuery(criteria).getSingleResult();
    }

    /**
     * Find just one Contact by the last name that is passed in. If there is more then one, only the first will be returned.
     * 
     * @param lastName
     * @return Contact
     */
    Contact findByLastName(String lastName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = cb.createQuery(Contact.class);
        Root<Contact> contact = criteria.from(Contact.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(contact).orderBy(cb.asc(contact.get(Contact_.lastName), lastName));
        criteria.select(contact).where(cb.equal(contact.get("lastName"), lastName));
        return em.createQuery(criteria).getSingleResult();
    }

    /**
     * Find all the Contacts and sort them alphabetically by last name.
     * 
     * @return List of Contacts
     */
    List<Contact> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = cb.createQuery(Contact.class);
        Root<Contact> contact = criteria.from(Contact.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(contact).orderBy(cb.asc(contact.get(Contact_.lastName)));
        criteria.select(contact).orderBy(cb.asc(contact.get("lastName")));
        return em.createQuery(criteria).getResultList();
    }

    /**
     * Create a Contact and store it in the database.
     * 
     * Persist takes an entity instance, adds it to the context and makes that instance managed (ie future updates 
     * to the entity will be tracked)
     * 
     * persist() will set the @GeneratedValue @Id for an object. 
     * 
     * @param Contact
     * @return Contact
     * @throws Exception
     */
    Contact create(Contact contact) throws Exception {
        log.info("Creating " + contact.getFirstName() + " " + contact.getLastName());
        em.persist(contact);
        contactEventSrc.fire(contact);
        return contact;
    }

    /**
     * Update a Contact in the database.
     * 
     * Merge creates a new instance of your entity, copies the state from the supplied entity, and makes the new 
     * copy managed. The instance you pass in will not be managed (any changes you make will not be part of the 
     * transaction - unless you call merge again).
     * 
     * merge() however must have an object with the @Id already generated.
     * 
     * @param Contact
     * @return Contact
     * @throws Exception
     */
    Contact update(Contact contact) throws Exception {
        log.info("Updating " + contact.getFirstName() + " " + contact.getLastName());
        em.merge(contact);
        contactEventSrc.fire(contact);
        return contact;
    }

    /**
     * Delete a Contact in the database.
     * 
     * @param Contact
     * @return Contact
     * @throws Exception
     */
    Contact delete(Contact contact) throws Exception {
        log.info("Deleting " + contact.getFirstName() + " " + contact.getLastName());
        em.remove(em.merge(contact));
        contactEventSrc.fire(contact);
        return contact;
    }

}
