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

var currentUser;

CONTACTS.namespace('CONTACTS.security.loadCurrentUser');
CONTACTS.namespace('CONTACTS.security.submitSignIn');
CONTACTS.namespace('CONTACTS.security.submitSignUp');

CONTACTS.namespace('CONTACTS.security.restSecurityEndpoint');
CONTACTS.namespace('CONTACTS.security.restSignUpEndpoint');
CONTACTS.namespace('CONTACTS.security.restLogoutEndpoint');
CONTACTS.namespace('CONTACTS.security.restCurrentUserEndpoint');
CONTACTS.namespace('CONTACTS.security.restListUsersEndpoint');
CONTACTS.namespace('CONTACTS.security.restListRolesEndpoint');
CONTACTS.namespace('CONTACTS.security.restAssignRoleEndpoint');

// Set this variable for all Security REST APIs base URL.
CONTACTS.security.restSecurityEndpoint = 'rest/security/';

CONTACTS.security.restSignUpEndpoint = CONTACTS.security.restSecurityEndpoint + 'registration';
CONTACTS.security.restLogoutEndpoint = CONTACTS.security.restSecurityEndpoint + 'logout';
CONTACTS.security.restCurrentUserEndpoint = CONTACTS.security.restSecurityEndpoint + 'user/info';
CONTACTS.security.restListUsersEndpoint = CONTACTS.security.restSecurityEndpoint + 'user';
CONTACTS.security.restListRolesEndpoint = CONTACTS.security.restSecurityEndpoint + 'role';
CONTACTS.security.restAssignRoleEndpoint = CONTACTS.security.restSecurityEndpoint + 'role/assign';

$(document).ready(function() {
    //Initialize the vars in the beginning so that you will always have access to them.
    var getCurrentTime = CONTACTS.util.getCurrentTime,
        restSecurityEndpoint = CONTACTS.security.restSecurityEndpoint;

    // Register a handler to be called when Ajax requests complete with an error. Whenever an Ajax request completes 
	// with an error, jQuery triggers the ajaxError event. Any and all handlers that have been registered with the 
	// .ajaxError() method are executed at this time. Note: This handler is not called for cross-domain script and 
	// cross-domain JSONP requests. - from the jQuery docs
	$(document).ajaxError(function( event, jqXHR, settings, errorThrown ) {
		// Whenever there is an AJAX event the authentication and authorization is verified. If the user is denied, then
		// the system will return an error instead of data. This is a 'universal' error catcher for those denials.
        if (jqXHR.status == 403) {
        	// Authorization denied. (Do not permissions)
            $( "body" ).pagecontainer( "change", "#access-denied-dialog", { transition: "pop" });
            console.log(getCurrentTime() + " [js/security.js] (document.ajaxError) - error in ajax" +
                    " - jqXHR = " + jqXHR.status +
                    ", errorThrown = " + errorThrown +
                    ", responseText = " + jqXHR.responseText);
            console.log(getCurrentTime() + " [js/security.js] (document.ajaxError) - error in ajax - Event Object ->");
            console.dir(event);
            console.log(getCurrentTime() + " [js/security.js] (document.ajaxError) - error in ajax - Settings Object ->");
    		console.dir(settings);
        } else if (jqXHR.status == 401) {
        	// Authentication denied. (Not logged in)
            $( "body" ).pagecontainer( "change", "#signin-page");
            console.log(getCurrentTime() + " [js/security.js] (document.ajaxError) - error in ajax" +
            		" - jqXHR = " + jqXHR.status +
            		", errorThrown = " + errorThrown +
            		", responseText = " + jqXHR.responseText);
            console.log(getCurrentTime() + " [js/security.js] (document.ajaxError) - error in ajax - Event Object ->");
            console.dir(event);
            console.log(getCurrentTime() + " [js/security.js] (document.ajaxError) - error in ajax - Settings Object ->");
            console.dir(settings);
        }
    });

    //Initialize all the AJAX form events.
    var initSecurity = function () {
    	console.log(getCurrentTime() + " [js/security.js] (initSecurity) - start");
        //Fetches the initial member data
        CONTACTS.security.submitSignIn();
        CONTACTS.security.submitSignUp();
        CONTACTS.security.submitAssignRole();
        console.log(getCurrentTime() + " [js/security.js] (initSecurity) - end");
    };

    $('#logout-page').on( "pagebeforeshow", function(e) {
    	console.log(getCurrentTime() + " [js/security.js] (#logout-page -> on pagebeforeshow) - start");
        var jqxhr = $.ajax({
            url: CONTACTS.security.restLogoutEndpoint,
            type: "POST"
        }).done(function(data, textStatus, jqXHR) {
            $( "body" ).pagecontainer( "change", '#signin-page');
        }).fail(function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
            console.log(getCurrentTime() + " [js/security.js] (#logout-page -> on pagebeforeshow) - error in ajax" +
            		" - jqXHR = " + jqXHR.status +
            		", errorThrown = " + errorThrown +
            		", responseText = " + jqXHR.responseText);
        });
        console.log(getCurrentTime() + " [js/security.js] (#logout-page -> on pagebeforeshow) - end");
    });

    $('#role-assignment-page').on( "pagebeforeshow", function(e) {
    	console.log(getCurrentTime() + " [js/security.js] (#role-assignment-page -> on pagebeforeshow) - start");
        if(e.handled !== true) {
            $('#role-assignment-users-select')
                .find('option')
                .remove()
                .end()
                .append('<option>Select an user</option>')
                .val('');
            
            $.ajax({
                url: CONTACTS.security.restListUsersEndpoint,
                cache: false,
                type: "GET"
            }).done(function(data, textStatus, jqXHR) {
                $.each(data, function(index, user) {
                    $("<option>").attr({"value":user.loginName}).html(user.loginName).appendTo("#role-assignment-users-select");
                });
                $("#role-assignment-users-select").selectmenu("refresh");
            }).fail(function(jqXHR, textStatus, errorThrown) {
                console.log(getCurrentTime() + " [js/security.js] (#role-assignment-page -> on pagebeforeshow) - restListUsersEndpoint - Could not query users" +
                    " - jqXHR = " + jqXHR.status +
                    ", textStatus = " + textStatus +
                    ", errorThrown = " + errorThrown +
                    ", responseText = " + jqXHR.responseText);
            });

            $('#role-assignment-role-select')
                .find('option')
                .remove()
                .end()
                .append('<option>Select a role</option>')
                .val('');
            
            $.ajax({
                url: CONTACTS.security.restListRolesEndpoint,
                cache: false,
                type: "GET"
            }).done(function(data, textStatus, jqXHR) {
                $.each(data, function(index, role) {
                    $("<option>").attr({"value":role}).html(role).appendTo("#role-assignment-role-select");
                });
                $("#role-assignment-role-select").selectmenu("refresh");
            }).fail(function(jqXHR, textStatus, errorThrown) {
                console.log(getCurrentTime() + " [js/security.js] (#role-assignment-page -> on pagebeforeshow) - restListRolesEndpoint - Could not query users" +
                        " - jqXHR = " + jqXHR.status +
                        ", textStatus = " + textStatus +
                        ", errorThrown = " + errorThrown +
                        ", responseText = " + jqXHR.responseText);
            });
            e.handled = true;
        }
        console.log(getCurrentTime() + " [js/security.js] (#role-assignment-page -> on pagebeforeshow) - end");
    });

    /**
     * Attempts to sign up using a JAX-RS POST.
     */
    CONTACTS.security.submitSignUp = function() {
    	console.log(getCurrentTime() + " [js/security.js] (submitSignUp) - start");
        $("#signup-form").submit(function(event) {
            CONTACTS.validation.signUpFormValidator.form();
            if (CONTACTS.validation.signUpFormValidator.valid()){
                event.preventDefault();

                var serializedForm = $("#signup-form").serializeObject();
                var userData = JSON.stringify(serializedForm);

                var jqxhr = $.ajax({
                    url: CONTACTS.security.restSignUpEndpoint,
                    contentType: "application/json",
                    dataType: "json",
                    data: userData,
                    type: "POST"
                }).done(function(data, textStatus, jqXHR) {
                    CONTACTS.validation.formEmail = null;

                    $('#signup-form')[0].reset();
                    $('.invalid').remove();

                    $( "body" ).pagecontainer( "change", "#signin-page");
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    $('.invalid').remove();

                    if ((jqXHR.status === 409) || (jqXHR.status === 400)) {
                        var newUser = $("#signup-form")[0];
                        var errorMsg = $.parseJSON(jqXHR.responseText);

                        $.each(errorMsg, function(index, val) {
                            if (index === 'email'){
                                $.each(newUser, function(index, val){
                                    if (val.name == "email"){
                                        CONTACTS.validation.formEmail = val.value;
                                        return false;
                                    }
                                });
                            }
                        });

                        CONTACTS.validation.displayServerSideErrors("#signup-form", errorMsg);
                        console.log(getCurrentTime() + " [js/security.js] (submitSignUp) - restSignUpEndpoint - error in ajax" +
                                " - jqXHR = " + jqXHR.status +
                                ", textStatus = " + textStatus +
                                ", errorThrown = " + errorThrown +
                                ", responseText = " + jqXHR.responseText);
                    } else {
                        var errorMsg = $.parseJSON(jqXHR.responseText);

                        CONTACTS.validation.displayServerSideErrors("#signup-form", errorMsg);
                        
                        console.log(getCurrentTime() + " [js/security.js] (submitSignUp) - restSignUpEndpoint - error in ajax" +
                                " - jqXHR = " + jqXHR.status +
                                ", textStatus = " + textStatus +
                                ", errorThrown = " + errorThrown +
                                ", responseText = " + jqXHR.responseText);
                    }
                });
            }
        });
        console.log(getCurrentTime() + " [js/security.js] (submitSignUp) - end");
    };

    /**
     * Attempts to sign in using a JAX-RS POST.
     */
    CONTACTS.security.submitSignIn = function() {
    	console.log(getCurrentTime() + " [js/security.js] (submitSignIn) - start");
        $("#signin-form").submit(function(event) {
            CONTACTS.validation.signInFormValidator.form();

            if (CONTACTS.validation.signInFormValidator.valid()){
                event.preventDefault();

                var serializedForm = $("#signin-form").serializeObject();
                var userData = JSON.stringify(serializedForm);

                var jqxhr = $.ajax({
                    url: CONTACTS.security.restCurrentUserEndpoint,
                    contentType: "application/json",
                    dataType: "json",
                    headers: {
                        "Authorization": 'Basic ' + btoa(serializedForm.loginName + ":" + serializedForm.password)
                    },
                    type: "GET"
                }).done(function(data, textStatus, jqXHR) {
                    CONTACTS.validation.formEmail = null;

                    $('#signin-form')[0].reset();
                    $('.invalid').remove();

                    CONTACTS.security.loadCurrentUser();

                    $( "body" ).pagecontainer( "change", "#contacts-list-page");
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    if (jqXHR.status === 401) {
                        $( "body" ).pagecontainer( "change", "#invalid-credentials-dialog", { transition: "pop" });
                        
                        console.log(getCurrentTime() + " [js/security.js] (submitSignIn) - error in ajax " +
                                " - jqXHR = " + jqXHR.status +
                                ", textStatus = " + textStatus +
                                ", errorThrown = " + errorThrown +
                                ", responseText = " + jqXHR.responseText);
                    }
                });
            }
        });
        console.log(getCurrentTime() + " [js/security.js] (submitSignIn) - end");
    };

    /**
     * Attempts to role assignment using a JAX-RS POST.
     */
    CONTACTS.security.submitAssignRole = function() {
    	console.log(getCurrentTime() + " [js/security.js] (submitAssignRole) - start");
        $("#role-assignment-form").submit(function(event) {
            CONTACTS.validation.assignRoleFormValidator.form();

            if (CONTACTS.validation.assignRoleFormValidator.valid()){
                event.preventDefault();

                var serializedForm = $("#role-assignment-form").serializeObject();

                var jqxhr = $.ajax({
                    url: CONTACTS.security.restAssignRoleEndpoint + "/" + serializedForm.userName + "/" + serializedForm.roleName,
                    type: "POST"
                }).done(function(data, textStatus, jqXHR) {
                    $( "body" ).pagecontainer( "change", "#role-assignment-dialog", { transition: "pop" });
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    alert('Could not assign role, please select an user and a role.');
                    
                    console.log(getCurrentTime() + " [js/security.js] (submitAssignRole) - error in ajax" +
                            " - jqXHR = " + jqXHR.status +
                            ", textStatus = " + textStatus +
                            ", errorThrown = " + errorThrown +
                            ", responseText = " + jqXHR.responseText);
                });
            }
        });
        console.log(getCurrentTime() + " [js/security.js] (submitAssignRole) - end");
    };

    /**
     * Attempts to load information about the current user.
     */
    CONTACTS.security.loadCurrentUser = function() {
    	console.log(getCurrentTime() + " [js/security.js] (loadCurrentUser) - start");
        var jqxhr = $.ajax({
            url: CONTACTS.security.restCurrentUserEndpoint,
            contentType: "application/json",
            dataType: "json",
            type: "GET",
            async: false
        }).done(function(data, textStatus, jqXHR) {
            currentUser = data;
        }).fail(function(jqXHR, textStatus, errorThrown) {
            currentUser = null;
            
            console.log(getCurrentTime() + " [js/security.js] (loadCurrentUser) - error in ajax" +
                    " - jqXHR = " + jqXHR.status +
                    ", textStatus = " + textStatus +
                    ", errorThrown = " + errorThrown +
                    ", responseText = " + jqXHR.responseText);
        });
        console.log(getCurrentTime() + " [js/security.js] (loadCurrentUser) - end");
    };

    initSecurity();
});