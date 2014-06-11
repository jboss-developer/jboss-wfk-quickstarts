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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.quickstarts.wfk.contacts.customer.Contact;
import org.jboss.quickstarts.wfk.contacts.customer.ContactRESTService;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.picketlink.Identity;
import org.picketlink.credential.DefaultLoginCredentials;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// JAX-RS 2.0 import statement
//import javax.ws.rs.client.*;

/**
 * Uses Arquilian to test the JAX-RS processing class for contact registration.
 * 
 * @author balunasj
 * @author Joshua Wilson
 */
@RunWith(Arquillian.class)
public class ContactRegistrationTest {
    
    @Inject
    private ContactRESTService contactRESTService;
    
    @Inject
    private Logger log;

    @Inject
    private Identity identity;

    @Inject
    private DefaultLoginCredentials credentials;

    // The URI is needed for the JAX-RS 2.0 tests.
//  private static URI uri = UriBuilder.fromUri("http://localhost/jboss-contacts-mobile-picketlink-secured/rest/contact").port(8080).build();
  
  // JAX-RS 2.0 Client API
//  private static Client client = ClientBuilder.newClient();

    //Set millis 498484800000 from 1985-10-10T12:00:00.000Z
    private Date date = new Date(498484800000L);

    @Deployment
    public static Archive<?> deploy() {
        return ArchiveUtil.createTestArchive();
    }

    /**
     * <p>The {@link org.jboss.quickstarts.wfk.contacts.customer.ContactRESTService} is protected by PicketLink. Before invoking
     * its methods we first need to perform an authentication.</p>
     *
     * <p>This method performs an authentication before each test execution.</p>
     */
    @Before
    public void onSetup() {
        this.credentials.setUserId("admin");
        this.credentials.setPassword("admin");

        this.identity.login();
    }

    /**
     * <p>Just logs out the authenticated user.l</p>
     */
    @After
    public void onFinish() {
        this.identity.logout();
    }

    @Test
    @InSequence(1)
    public void testRegister() throws Exception {
        Contact contact = createContactInstance("Jack", "Doe", "jack@mailinator.com", "2125551234", date);
        Response response = contactRESTService.createContact(contact);

        assertEquals("Unexpected response status", 200, response.getStatus());
        log.info(" New contact was persisted and returned status " + response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    @InSequence(2)
    public void testInvalidRegister() throws Exception {
        Contact contact = createContactInstance("", "", "", "", date);
        Response response = contactRESTService.createContact(contact);

        assertEquals("Unexpected response status", 400, response.getStatus());
        assertNotNull("response.getEntity() should not be null", response.getEntity());
        assertEquals("Unexpected response.getEntity(). It contains " + response.getEntity(), 3,
            ((Map<String, String>) response.getEntity()).size());
        log.info("Invalid contact register attempt failed with return code " + response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    @InSequence(3)
    public void testDuplicateEmail() throws Exception {
        // Register an initial user
        Contact contact = createContactInstance("Jane", "Doe", "jane@mailinator.com", "2125551234", date);
        contactRESTService.createContact(contact);

        // Register a different user with the same email
        Contact anotherContact = createContactInstance("John", "Doe", "jane@mailinator.com", "2133551234", date);
        Response response = contactRESTService.createContact(anotherContact);

        assertEquals("Unexpected response status", 409, response.getStatus());
        assertNotNull("response.getEntity() should not be null", response.getEntity());
        assertEquals("Unexpected response.getEntity(). It contains" + response.getEntity(), 1,
            ((Map<String, String>) response.getEntity()).size());
        log.info("Duplicate contact register attempt failed with return code " + response.getStatus());
    }

    // Uncomment when you have access to JAX-RS 2.0
//    @Test
//    @InSequence(4)
//    public void shouldNotCreateANullContact() throws JAXBException {
//        //POSTs a null Contact
//        Response response = client.target(uri).request().post(Entity.entity(null, MediaType.APPLICATION_JSON));
//        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
//    }
//    
//    @Test
//    @InSequence(5)
//    public void shouldNotFindTheContactID() throws JAXBException {
//        // GETs a Contact with an unknown ID
//        Response response = client.target(uri).path("unknownID").request().get();
//        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
//    }
//    
//    @Test
//    @InSequence(6)
//    public void shouldCreateAndDeleteAContact() throws JAXBException {
//        
//        Contact contact = createContactInstance("Jason", "Smith", "jason@mailinator.com", "2125551234", date);
//        
//        // POSTs a Contact
//        Response response = client.target(uri).request().post(Entity.entity(contact, MediaType.APPLICATION_JSON));
//        
//        assertEquals(Response.Status.CREATED, response.getStatusInfo());
//        URI contactURI = response.getLocation();
//        
//        // With the location, GETs the Contact
//        response = client.target(contactURI).request().get();
//        contact = response.readEntity(Contact.class);
//        assertEquals(Response.Status.OK, response.getStatusInfo());
//        assertEquals("Jason", contact.getFirstName());
//        
//        // GETs the Contact ID and DELETEs it
//        String contactID = contactURI.toString().split("/")[6];
//        response = client.target(uri).path(contactID).request().delete();
//        assertEquals(Response.Status.NO_CONTENT, response.getStatusInfo());
//        
//        // GETs the Contact and checks if it has been deleted
//        response = client.target(bookURI).request().get();
//        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
//    }
    
    private Contact createContactInstance(String firstName, String lastName, String email, String phone, Date birthDate) {
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);
        contact.setPhoneNumber(phone);
        contact.setBirthDate(birthDate);
        return contact;
    }
}
