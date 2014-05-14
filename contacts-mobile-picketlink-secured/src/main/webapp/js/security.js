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
//var currentUser;
CONTACTS.namespace("CONTACTS.security.currentUser");
CONTACTS.namespace("CONTACTS.security.loadCurrentUser");
CONTACTS.namespace("CONTACTS.security.submitSignIn");
CONTACTS.namespace("CONTACTS.security.submitSignUp");
CONTACTS.namespace("CONTACTS.security.restSecurityEndpoint");

// Set this to undefined so that when the user is not logged in system doesn't think they are. This is referenced in 
// app.js (#contacts-list-page -> pagebeforeshow)
CONTACTS.security.currentUser = undefined;

// Set this variable for all Security REST APIs base URL.
CONTACTS.security.restSecurityEndpoint = "rest/security/";

/**
 * It is recommended to bind to this event instead of DOM ready() because this will work regardless of whether 
 * the page is loaded directly or if the content is pulled into another page as part of the Ajax navigation system.
 * 
 * The first thing you learn in jQuery is to call code inside the $(document).ready() function so everything 
 * will execute as soon as the DOM is loaded. However, in jQuery Mobile, Ajax is used to load the contents of 
 * each page into the DOM as you navigate, and the DOM ready handler only executes for the first page. 
 * To execute code whenever a new page is loaded and created, you can bind to the pagecreate event. 
 * 
 * 
 * These functions perform the Log out and Role Assignments.
 * 
 * @author Pedro Igor, Joshua Wilson
 */
$( document ).on( "pagecreate", function(mainEvent) {
    //Initialize the vars in the beginning so that you will always have access to them.
    var getCurrentTime = CONTACTS.util.getCurrentTime,
        restSecurityEndpoint = CONTACTS.security.restSecurityEndpoint;

    /* 
     * The "pagebeforeshow" event will delay this function until everything is set up.
     * 
     * Because of the interesting jQM loading architecture, multiple event triggering is a constant problem. 
     * The "e.handled" if statement used here and elsewhere is meant to keep jQM from running this code multiple 
     * times for one display. 
     */
    // Log out
    $("#logout-page").on( "pagebeforeshow", function(e) {
    	if(e.handled !== true) {
	    	console.log(getCurrentTime() + " [js/security.js] (#logout-page -> on pagebeforeshow) - start");
	        
	    	var jqxhr = $.ajax({
	            url: restSecurityEndpoint + "logout",
	            type: "POST"
	        }).done(function(data, textStatus, jqXHR) {
	        	console.log(getCurrentTime() + " [js/security.js] (#logout-page -> on pagebeforeshow) - Successfully logged out");
	        	
	        	// Once you have successfully logged out, redirect them to the log in page.
	            $( "body" ).pagecontainer( "change", "#signin-page");
	        }).fail(function(jqXHR, textStatus, errorThrown) {
	            alert(errorThrown);
	            console.log(getCurrentTime() + " [js/security.js] (#logout-page -> on pagebeforeshow) - error in ajax" +
	            		" - jqXHR = " + jqXHR.status +
	            		", errorThrown = " + errorThrown +
	            		", responseText = " + jqXHR.responseText);
	        });
	        
	        e.handled = true;
	        console.log(getCurrentTime() + " [js/security.js] (#logout-page -> on pagebeforeshow) - end");
    	}
    });

    // Role Assignment
    $("#role-assignment-page").on( "pagebeforeshow", function(e) {
        if(e.handled !== true) {
        	console.log(getCurrentTime() + " [js/security.js] (#role-assignment-page -> on pagebeforeshow) - start");
            
        	// Reset the text of the default display.
        	$("#role-assignment-users-select")
                .find("option")
                .remove()
                .end()
                .append("<option>Select a user</option>")
                .val("");
//        	$("#role-assignment-form option").remove().append("<option>Select a user</option>").val("");
            
        	// Get the list of Users and add them to the selection list.
            $.ajax({
                url: restSecurityEndpoint + "user",
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

            // Reset the text of the default display.
            $("#role-assignment-role-select")
                .find("option")
                .remove()
                .end()
                .append("<option>Select a role</option>")
                .val("");
            
            // Get the list of Roles and add them to the selection list.
            $.ajax({
                url: restSecurityEndpoint + "role",
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
            console.log(getCurrentTime() + " [js/security.js] (#role-assignment-page -> on pagebeforeshow) - end");
        }
    });
});

/**
 * The regural jQuery AJAX functions go here.  We do all the jQuery Mobile security work in the section above this.
 * 
 * @author Pedro Igor, Joshua Wilson
 */
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
		// the system will return an error instead of data. This is a "universal" error catcher for those denials.
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

    /**
     * Attempts to sign up using a JAX-RS POST.
     */
    CONTACTS.security.submitSignUp = function() {
    	console.log(getCurrentTime() + " [js/security.js] (submitSignUp) - start");
    	
        $("#signup-form").submit(function(event) {
        	console.log(getCurrentTime() + " [js/security.js] (submitSignUp) - submit event) - checking if the form is valid");
        	
        	// Ensure that the form has been validated.
            CONTACTS.validation.signUpFormValidator.form();
            // If there are any validation error then don't process the submit.
            if (CONTACTS.validation.signUpFormValidator.valid()){
            	console.log(getCurrentTime() + " [js/security.js] (submitSignUp) - submit event) - start");
                event.preventDefault();

                var serializedForm = $("#signup-form").serializeObject();
                var userData = JSON.stringify(serializedForm);

                var jqxhr = $.ajax({
                    url: restSecurityEndpoint + "registration",
                    contentType: "application/json",
                    dataType: "json",
                    data: userData,
                    type: "POST"
                }).done(function(data, textStatus, jqXHR) {

                	// Reset this flag when the form passes validation.
// We are not validating usernames for uniqueness yet, when we do we will need this.
//                    CONTACTS.validation.formEmail = null;

                	// Clear the form or else the next time you go to sign up the last one will still be there.
                    $("#signup-form")[0].reset();
                    
                    // Remove errors display as a part of the validation system.
                    $(".invalid").remove();

                    $( "body" ).pagecontainer( "change", "#signin-page");
                    
                }).fail(function(jqXHR, textStatus, errorThrown) {
                	// Remove errors display as a part of the validation system.
                	$(".invalid").remove();

                	// Check for server side validation errors.  This should catch the email uniqueness validation.
                    if ((jqXHR.status === 409) || (jqXHR.status === 400)) {
                        var newUser = $("#signup-form")[0];
                        var errorMsg = $.parseJSON(jqXHR.responseText);

                        $.each(errorMsg, function(index, val) {
                            if (index === "email"){
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
                    	//Catch anything else that is not a sign up form failure. 
                        console.log(getCurrentTime() + " [js/security.js] (submitSignUp) - restSignUpEndpoint - error in ajax" +
                                " - jqXHR = " + jqXHR.status +
                                ", textStatus = " + textStatus +
                                ", errorThrown = " + errorThrown +
                                ", responseText = " + jqXHR.responseText);
                        
                        // Extract the error messages from the server.
                        var errorMsg = $.parseJSON(jqXHR.responseText);
                        
                        // Apply the error to the form.
                        CONTACTS.validation.displayServerSideErrors("#signup-form", errorMsg);
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
        	console.log(getCurrentTime() + " [js/security.js] (submitSignIn) - submit event) - checking if the form is valid");
        	
        	// Ensure that the form has been validated.
            CONTACTS.validation.signInFormValidator.form();
            // If there are any validation error then don't process the submit.
            if (CONTACTS.validation.signInFormValidator.valid()){
            	console.log(getCurrentTime() + " [js/security.js] (submitSignIn) - submit event) - start");
                event.preventDefault();

                var serializedForm = $("#signin-form").serializeObject();
                var userData = JSON.stringify(serializedForm);

                // Send the login and password to the server for Auth-n and Auth-z
                var jqxhr = $.ajax({
                    url: restSecurityEndpoint + "user/info",
                    contentType: "application/json",
                    dataType: "json",
                    headers: {
                        "Authorization": "Basic " + btoa(serializedForm.loginName + ":" + serializedForm.password)
                    },
                    type: "GET"
                }).done(function(data, textStatus, jqXHR) {
                	console.log(getCurrentTime() + " [js/security.js] (submitSignIn) - ajax done");
                	
                	// Reset this flag when the form passes validation.
// I think this is not needed for sign-in.
//                    CONTACTS.validation.formEmail = null;

                	// Clear the form or else the next time you go to sign in the last one will still be there.
                    $("#signin-form")[0].reset();
                    
                    // Remove errors display as a part of the validation system.
                    $(".invalid").remove();

                    // Once the user has successfully signed in, their credintials gets loaded into the global 'currentUser'.
                    CONTACTS.security.loadCurrentUser();

                 // Because we turned off the automatic page transition to catch server side error we need to do it ourselves.
                    $( "body" ).pagecontainer( "change", "#contacts-list-page");
                }).fail(function(jqXHR, textStatus, errorThrown) {
                	// Remove errors display as a part of the validation system.
                	$(".invalid").remove();

                	if (jqXHR.status === 401) {
                		
                		// If the log in fails then send them to a pop-up telling them the log in attempt was invalid.
                        $( "body" ).pagecontainer( "change", "#invalid-credentials-dialog", { transition: "pop" });
                        
                        console.log(getCurrentTime() + " [js/security.js] (submitSignIn) - error in ajax " +
                                " - jqXHR = " + jqXHR.status +
                                ", textStatus = " + textStatus +
                                ", errorThrown = " + errorThrown +
                                ", responseText = " + jqXHR.responseText);
                    } else {
                    	// Catch anything else that is not a log-in failure.
                        console.log(getCurrentTime() + " [js/submissions.js] (submitCreate) - error in ajax" +
                                " - jqXHR = " + jqXHR.status +
                                ", textStatus = " + textStatus +
                                ", errorThrown = " + errorThrown +
                                ", responseText = " + jqXHR.responseText);
                    
	                    // Extract the error messages from the server.
	                    var errorMsg = $.parseJSON(jqXHR.responseText);
	                    
	                    // Apply the error to the form.
	                    CONTACTS.validation.displayServerSideErrors("#signin-form", errorMsg);
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
        	console.log(getCurrentTime() + " [js/security.js] (submitAssignRole) - submit event) - checking if the form is valid");
        	
        	// Ensure that the form has been validated.
            CONTACTS.validation.assignRoleFormValidator.form();
            // If there are any validation error then don't process the submit.
            if (CONTACTS.validation.assignRoleFormValidator.valid()){
            	console.log(getCurrentTime() + " [js/security.js] (submitAssignRole) - submit event) - start");
                event.preventDefault();

                var serializedForm = $("#role-assignment-form").serializeObject();

                var jqxhr = $.ajax({
                    url: restSecurityEndpoint + "role/assign/" + serializedForm.userName + "/" + serializedForm.roleName,
                    type: "POST"
                }).done(function(data, textStatus, jqXHR) {
                	console.log(getCurrentTime() + " [js/security.js] (submitAssignRole) - ajax done");
                	
                	// Because we turned off the automatic page transition to catch server side error we need to do it ourselves.
                    $( "body" ).pagecontainer( "change", "#role-assignment-dialog", { transition: "pop" });
                    
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    alert("Could not assign role, please select an user and a role.");
                    
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
     * 
     * This is called by CONTACTS.security.submitSignIn()
     */
    CONTACTS.security.loadCurrentUser = function() {
    	console.log(getCurrentTime() + " [js/security.js] (loadCurrentUser) - start");
    	
    	// The server knows which user is logged in for the session and will return that.
        var jqxhr = $.ajax({
            url: restSecurityEndpoint + "user/info",
            contentType: "application/json",
            dataType: "json",
            type: "GET",
            async: false
        }).done(function(data, textStatus, jqXHR) {
        	console.log(getCurrentTime() + " [js/security.js] (loadCurrentUser) - ajax done");
        	
        	// Store the logged in user credentials for Role access. 
        	CONTACTS.security.currentUser = data;
            
        }).fail(function(jqXHR, textStatus, errorThrown) {
        	
        	// If something goes wrong set the currentUser to undefined.
        	CONTACTS.security.currentUser = undefined;
            
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
