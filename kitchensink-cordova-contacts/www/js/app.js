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

/*
 * Core JavaScript functionality for the application.  Performs the required
 * Restful calls, validates return values, and populates the member table.
 * 
 * @Author: Joshua Wilson
 * @Author: Vineet Reynolds
 */

/* Builds the updated table for the member list */
// Load the application once the DOM is ready, using `jQuery.ready`.
// We wait for the DOM ready event, since some of the views cache references to DOM elements.
$(function() {
	/*
	 * Models are the heart of any JavaScript application, containing the
	 * interactive data as well as a large part of the logic surrounding it:
	 * conversions, validations, computed properties, and access control.
	 * You extend Backbone.Model with your domain-specific methods, and Model
	 * provides a basic set of functionality for managing changes.
	 */
	// Our basic **Member** model
	window.Member = Backbone.Model.extend({
        //Intentionally left empty
	});

	/*
	 * Collections are ordered sets of models. You can bind "change" events to be
	 * notified when any model in the collection has been modified, listen for "add"
	 * and "remove" events, fetch the collection from the server, and use a full
	 * suite of Underscore.js methods.
	 *
	 * Any event that is triggered on a model in a collection will also be triggered
	 * on the collection directly, for convenience. This allows you to listen for
	 * changes to specific attributes in any model in a collection, for example:
	 * Documents.on("change:selected", ...)
	 */
	window.MemberList = Backbone.Collection.extend({
		// Specify the base url to target the rest-easy service
		url : 'http://html5-jdf.rhcloud.com/rest/members',

		// Reference to this collection's model.
		model : Member
	});

	// Create our global collection of **Members**.
	window.Members = new MemberList();

    // Create a global Member to store Member details for registration.
	window.FormMember = new Member();


	/*
	 * Backbone Views are almost more convention than they are code — they don't
	 * determine anything about your HTML or CSS for you, and can be used with any
	 * JavaScript templating library. The general idea is to organize your
	 * interface into logical views, backed by models, each of which can be
	 * updated independently when the model changes, without having to redraw the
	 * page. Instead of digging into a JSON object, looking up an element in the
	 * DOM, and updating the HTML by hand, you can bind your view's render function
	 * to the model's "change" event — and now everywhere that model data is
	 * displayed in the UI, it is always immediately up to date.
	 *
	 */
    // Extend the Backbone.View prototype to clean up gracefully when the View is closed.
    Backbone.View.prototype.close = function(){
        // If an 'onClose' function is defined in the View, then we invoke it.
        if (this.onClose){
            this.onClose();
        }
        // When the view is displayed again, a new instance of it is used.
        // New event listeners are ths added. Hence, stop delegating events for the current view.
        // This ensures the listeners in this view are no longer triggered.
        // We don't invoke 'this.remove()' since the View must not remove elements from DOM.
        this.undelegateEvents();
    };

    // This Backbone View is used to display the introduction page.
    window.IntroView = Backbone.View.extend({
        events : {
            // Bind the click event on the info button to the 'showInfo' method.
            "click a.ui-icon-info" : "showInfo"
        },

        render : function() {
            // Change to the jQuery Mobile page with id:'intro-art'.
            // Do not change the hash, since the hashchange was already triggered before navigating to this view.
            $.mobile.pageContainer.pagecontainer("change", "#intro-art", { reverse: false, changeHash: false, transition: "none"});
        },

        showInfo: function() {
            event.preventDefault();

            // Display the InfoView that is responsible for displaying the popup.
            new window.InfoView({el:$("#info-aside")}).render();
        }
    });

    window.InfoView = Backbone.View.extend({
        events : {
            "click #closePopup" : "closePopup"
        },

        render: function() {
            // Display the info-aside element as a popup
            $("#info-aside").popup("open");
        },

        closePopup: function() {
            // Close the popup
            $("#info-aside").popup("close");
        }
    });

    window.RegisterMemberView = Backbone.View.extend({
        events : {
            // Bind the click event on the Cancel button to the 'cancelRegistration' method.
            "click #cancel" : "cancelRegistration",

            // Bind the submit event on the Registration form to the 'registerMember' method.
            "submit #reg" : "registerMember"
        },

        initialize: function() {
            // Bind the 'onRegisterSuccess' method to be run in the context of the 'RegisterMemberView'.
            // The value of 'this' in the method will be this view instead of the global object.
            _.bindAll(this, "onRegisterSuccess");
        },

        render : function() {
            // Change to the jQuery Mobile page with id:'register-art'.
            $.mobile.pageContainer.pagecontainer("change", "#register-art", { reverse: false, changeHash: false, transition: "none"});
            this.resetForm();
        },

        //Clear member registration and error messages on page change
        resetForm : function(event) {
//        	console.log("RegisterMemberView - resetForm() - start");
            this.clearForm();

            // Populate the form input values with the model properties
            $("#name").val(this.model.get("name"));
            $("#email").val(this.model.get("email"));
            $("#phoneNumber").val(this.model.get("phoneNumber"));
        },

        registerMember : function(event) {
//        	console.log("RegisterMemberView - registerMember() - start");
            // The event was triggered by clicking on a link.
            // We prevent the browser from navigating to the destination.
            event.preventDefault();

            var elemName = $("#name"),
                elemEmail = $("#email"),
                elemPhoneNumber = $("#phoneNumber"),
                errors = [];

            // Verify if the name field is valid for the HTML5 constraints specified on it.
            if(!elemName.get(0).validity.valid) {
                errors.push({$elem : elemName});
            }

            // Verify if the email field is valid for the HTML5 constraints specified on it.
            if(!elemEmail.get(0).validity.valid) {
                errors.push({$elem : elemEmail});
            }

            // Verify if the phone number field is valid for the HTML5 constraints specified on it.
            if(!elemPhoneNumber.get(0).validity.valid) {
                errors.push({$elem : elemPhoneNumber});
            }
            // Clear existing msgs
            $('span.invalid').remove();
            $('span.success').remove();

            // Display errors if any
            if(errors.length > 0) {
                $.each(errors, function(idx, error) {
                    $('<span class="invalid">' + error.$elem.get(0).validationMessage + '</span>').insertAfter(error.$elem);
                });
            } else {
                var modelToAdd = {
                    name : elemName.val(),
                    email : elemEmail.val(),
                    phoneNumber : elemPhoneNumber.val()
                };
                // Trigger a 'create' event on the Members collection, providing callbacks for success and failure.
                window.Members.create(modelToAdd, {success:this.onRegisterSuccess, error:this.onRegisterFailure});
            }
        },

        // Invoked when a Member was registered successfully.
        onRegisterSuccess: function(model, response) {
//        	console.log("RegisterMemberView - onRegisterSuccess() - start");
            this.clearForm();

            // Mark success on the registration form
            $('#formMsgs').append($('<span class="success">Member Registered</span>'));

            // Create a new global Member instance.
            window.FormMember = new window.Member();
        },

        // Invoked when a Member was registered successfully.
        onRegisterFailure: function(model, jqXHR) {
//          console.log("RegisterMemberView - onRegisterFailure - ajax done");
            //clear existing  msgs
            $('span.success').remove();

            if ((jqXHR.status === 409) || (jqXHR.status === 400)) {
//              console.log("RegisterMemberView - onRegisterFailure - error in ajax - Validation error registering user! "
//                		+ jqXHR.status);
                //clear existing msgs so that when the new message is display you don't have 2 of them.
                $('span.invalid').remove();
                var errorMsg = $.parseJSON(jqXHR.responseText);

                $.each(errorMsg, function(index, val) {
                    $('<span class="invalid">' + val + '</span>').insertAfter($('#' + index));
                });
            } else {
//	        	console.log("RegisterMemberView - onRegisterFailure - error in ajax - " +
//	        			"jqXHR = " + jqXHR.status +
//	        			" - textStatus = " + textStatus +
//	        			" - errorThrown = " + errorThrown);
                //clear existing  msgs
                $('span.invalid').remove();
                $('#formMsgs').append($('<span class="invalid">Unknown server error</span>'));
            }
        },

        //Register the cancel listener
        cancelRegistration : function(event) {
//        	console.log("RegisterMemberView - start cancelRegistration");
            this.clearForm();

            // Create a new global Member instance.
            window.FormMember = new window.Member();
        },

        clearForm: function() {
            // Clear input fields
            $('#reg')[0].reset();

            // Clear existing msgs
            $('span.invalid').remove();
            $('span.success').remove();
            $('#formMsgs').empty();
        }
    });

    // This Backbone View is used to display the view for searching contacts on the device.
    window.ContactSearchView = Backbone.View.extend({
        // Use a property to store contacts from a search
        foundContacts : {},

        events : {
            // Bind the click event on the Copy Contact button to the 'copyContact' method.
            "click #copy-contact" : "copyContact",

            // Bind the 'filterablebeforefilter' event on the Contacts list to the 'searchContactForm' method.
            "filterablebeforefilter #contactsSearch" : "searchContactForm",

            // Bind the click event on the contact list elements to the 'populateContactFields' method.
            "click [id^='populate-form-']" : "populateContactFields"
        },

        render : function() {
            // Change to the jQuery Mobile page with id:'contact-art'.
            $.mobile.pageContainer.pagecontainer("change", "#contact-art", { reverse: false, changeHash: false, transition: "none"});

            // Reset the page by clearing the search form, and removing any previously displayed contacts.
            this.resetPage();
        },

        resetPage : function() {
            var page = $( "#contact-art" );
            // Locate the search input field, reset it's value, and trigger a new search.
            page.find("input[data-type='search']").val("").trigger("keyup");
        },

        searchContactForm : function(e, data ) {
            // Obtain the value input into the filter field of the list.
            var $ul = $( "#contactsSearch" ),
                $input = $( data.input ),
                value = $input.val();
            $ul.html( "" );
            // Search for contacts only if the value is not null or empty.
            if(value && value.length > 0) {
                $ul.html( "<li><div class='ui-loader'><span class='ui-icon ui-icon-loading'></span></div></li>" );
                $ul.listview( "refresh" );

                // Create a new instance of Cordova's ContactFindOptions
                var options = new ContactFindOptions();
                // use the provided input as the search filter criteria
                options.filter= value;
                // return multiple matching contacts
                options.multiple=true;
                // find all matching contacts in any name, phone number or email field
                var fields = ["displayName", "name", "phoneNumbers", "emails"];

                // Store a global reference to the View, to refernce it when the callback methods are invoked.
                // The Cordova Contacts API does not allow you to specify a context for 'this' in the callbacks.
                window.callbackObj = this;

                // Find the contacts, providing callbacks for success or failure
                navigator.contacts.find(fields, this.foundContact, this.contactSearchError, options);
            } else {
                // Hide the contact details form and the copy contact button if the filter criteria was empty.
                this.hidePageElements();
            }
        },

        // Cache the template function for a single contact.
        contactTemplate : _.template($('#contact-List-tmpl').html()),

        // Invoked when the user has input a value into the filter to search contacts.
        foundContact: function(contacts) {
            var $ul = $( "#contactsSearch" ),
            // Obtain a reference to the ContactSearchView instance
            self = window.callbackObj;
            self.foundContacts = contacts;

            // Update the jQuery Mobile list widget with the search results.
            $ul.html(self.contactTemplate({contacts:contacts}));

            // Enhance the jQuery Mobile list, since it was constructed dynamically
            $ul.listview( "refresh" );

            // Trigger a layout update, since contacts may have been added/removed from the list since last display.
            $ul.trigger( "updatelayout" );

            // Hide the elements that are not relevant yet - the contact details and the copy contact button.
            // At this point, the user hasn't selected any contact to copy.
            self.hidePageElements();
        },

        // Cache the template function for contact details.
        contactDetailsTemplate : _.template($('#contact-Details-tmpl').html()),

        // Invoked when the user has selected a contact in the contact search results.
        populateContactFields : function (event) {
            event.preventDefault();

            // Obtain the id of the selected contact.
            var id = $(event.currentTarget).data('id');

            // Obtain a reference to the selected contact from among the search results.
            var contact = this.foundContacts[id];

            // Create a form with the details of the selected contact.
            // The form is required since a contact may have multiple emails and phone numbers.
            $("#contact-details").html(this.contactDetailsTemplate({contact:contact}));

            // Enhance the jQuery Mobile list, since it was constructed dynamically
            $("#contact-details").listview("refresh");

            // Enhance the jQuery Mobile select menu, since it was constructed dynamically
            $("select").selectmenu();

            // Show the contact details of the selected contact and the 'copy contact' button.
            this.showPageElements();
        },

        contactSearchError : function (contactError) {
            navigator.notification.alert("An error occurred!", function(){}, "Error");
        },

        // Invoked when the user clicks the 'copy contact' button.
        copyContact : function(event) {
            event.preventDefault();

            var selectedName = $("#display-name").text(),
                selectedPhone = $("#select-choice-phone").val(),
                selectedEmail = $("#select-choice-email").val();

            // Retain only digits from the phone number.
            // This makes it easier for the end user to submit a contact for registration.
            // It does not account for a phone number whose length exceeds the accepted length.
            if(selectedPhone) {
                selectedPhone = selectedPhone.replace(/\D+/g,"");
            }

            // Update the global Member instance with the details of the selected contact
            window.FormMember = new Member({
                name: selectedName,
                phoneNumber: selectedPhone,
                email: selectedEmail});

            // Navigate to the register view, where the global Member will be used to populate the form
            App.navigate("register", {trigger:true});
        },

        // Hide the contact details form and the 'copy contact' button
        hidePageElements : function() {
            var page = this.$el;
            page.find('#contact-details').hide();
            page.find('#copy-contact').closest('.ui-btn').hide();
        },

        // Show the contact details form and the 'copy contact' button
        showPageElements : function() {
            var page = this.$el;
            page.find('#contact-details').show();
            page.find('#copy-contact').closest('.ui-btn').show();
        }
    });

    // This Backbone View is used to display a single member in the list of all members.
	window.MemberView = Backbone.View.extend({

		events : {
            // Bind the click event on the Save button to the 'saveContact' method.
			"click a.ui-icon-plus" : "saveContact"
		},

		// The HTML that gets created will be inserted into a parent element defined here.
		// The default is 'div' so we don't need to list it.
		tagName : "tr",

		// Cache the template function for a single item.
		template : _.template($('#member-Body-tmpl').html()),

		// The MemberView listens for changes to its model, re-rendering.
		initialize : function() {
//			console.log("MemberView - initialize() - start");
			_.bindAll(this, 'render');

			// Listen to model changes and register the 'render' method as the callback
			this.model.on('change', this.render, this);
		},

		// Re-render the contents of the member item.
		render : function() {
//			console.log("MemberView - render() - start");
			this.$el.html(this.template({member: this.model.toJSON()}));
			return this;
		},

        // Invoked when the user clicks the Save button to save a Contact to the device.
		saveContact : function(event) {
//			console.log("MemberView - saveContact() - start");
			event.preventDefault();
			event.stopPropagation();

            // Create a new contact
			var contact = navigator.contacts.create();
            // Set the displayName of the contact to the model's name.
			contact.displayName = contact.nickname = this.model.get("name");

            // Add the model's email address, as the email address of the contact for 'work'.
            var emails = [];
			emails[0] = new ContactField("work",this.model.get("email"),false);
			contact.emails = emails;

            // Add the model's phone number, as the phone number of the contact for 'work'.
			var phoneNumbers = [];
			phoneNumbers[0] = new ContactField("work",this.model.get("phoneNumber"),false);
			contact.phoneNumbers = phoneNumbers;

            // Save the contact to the device.
			contact.save(this.onSaveSuccess, this.onSaveError);
		},

		// onSaveSuccess: Callback invoked when the contact was saved.
		onSaveSuccess: function(contact) {
//			console.log("MemberView - onSaveSuccess() - start");
            // Beep to notify the user, since this modifies the contact database on the device.
            navigator.notification.beep(1);
			navigator.notification.alert("The contact was saved.", function(){}, "Success");
		},

		// onSaveError: Callback invoked when some error occurred during save.
		onSaveError: function(contactError) {
//			console.log("MemberView - onSaveError() - start");
			navigator.notification.alert("An error occurred.", function(){}, "Error");
		}
	});

    // This Backbone View is used to display the list of all members.
    window.ListAllMembersView = Backbone.View.extend({
        events : {
            // Bind the click event on the Refresh Members button to the 'updateMemberTable' method.
            "click #refreshButton" : "updateMemberTable"
        },

        render : function() {
//			console.log("ListAllMembersView - render() - start");
            // Change to the jQuery Mobile page with id:'member-art'.
            $.mobile.pageContainer.pagecontainer("change", "#member-art", { reverse: false, changeHash: false, transition: "none"});

            // Bind the reset event on the Members collection to the addAllMembers method.
            Members.on('reset', this.addAllMembers, this);

            // Display all the members
            this.updateMemberTable();
            return this;
        },

        addOneMember : function(member) {
//			console.log("AppView - addOneMembers() - start");
            // Create a new instance of a MemberView, designating the member instance as it's model.
            var view = new MemberView({
                model : member
            });

            // Display the new MemberView as a nested view of the current view
            this.$("#members").append(view.render().el);
        },

        // Add all items in the **Members** collection at once.
        addAllMembers : function() {
//			console.log("AppView - addAllMembers() - start");

            // Dsiplay the loader widget
            $.mobile.loading("show");

            // For every member in the Members collection, invoke the 'addOneMember' method.
            Members.each(this.addOneMember);

            // Update the jQuery Mobile table, since we dynamically added elements to it.
            $( "#member-table" ).table( "refresh" );

            // Create the buttons in the list, since the markup was added dynamically.
            $("#members a[data-role='button']").button();

            // Hide the loader widget
            $.mobile.loading("hide");
        },

        updateMemberTable : function() {
//			console.log("AppView - Update Member - start");
            // Remove the elements in the table body or else we will have more then one copy of each member.
            $( "#members" ).empty();

            // Fetch the Collection. This resets the collection and adds all retrieved Members to it.
            // This also triggers the 'reset' event on the collection.
            // Any event listeners associated with this event are invoked.
            Members.fetch({reset: true});
        },

        onClose : function() {
            // Stop listening to the 'reset' event on the collection, when this view is closed.
            Members.off("reset");
        }
    });

    window.JsonView = Backbone.View.extend({
        initialize : function(options) {
            this.memberId = options.memberId;
        },

        render: function() {
            // Change to the jQuery Mobile page with id:'json-art'.
            $.mobile.pageContainer.pagecontainer("change", "#json-art", { reverse: false, changeHash: false, transition: "none"});
            this.showJSON();
            return this;
        },

        /* Adds an iframe with source set to the /rest/members collection or an individual element in it */
        showJSON : function() {
            var $content = $("#json-art div[data-role='content']");
            $content.empty();
            var url = "http://html5-jdf.rhcloud.com/rest/members/";
            if(this.memberId != null) {
                url += this.memberId;
            }
            $content.append("<embed src='" + url + "' type='application/json'></embed>");
        }
    });

	window.ViewManager = {
        // A property to store the current view being displayed.
		currentView : null,

        // Display a Backbone View. Closes the previously displayed view gracefully.
		showView : function (view) {
            // Close the previous view
			if(this.currentView != null) {
                // Invoke the close method on the view.
                // All views have this method, defined via the 'Backbone.View.prototype.close' method.
				this.currentView.close();
			}

            // Display the current view
			this.currentView = view;
			return this.currentView.render();
		}
	};

    // Define a Backbone Router to handle transitions from one Backbone View to another
	window.Router = Backbone.Router.extend({
		routes : {
            // Bind various route fragments to methods defined in the Router.
			"" : "showIntro",
			"intro" : "showIntro",
			"register" : "showRegisterMember",
			"member" : "showAllMembers",
			"contact" : "showContactSearch",
            "json(/:memberId)" : "showJson"
		},
		
		initialize : function() {
            // Start the router.
			Backbone.history.start();
		},

        // Display the loader widget before executing the method for any Backbone route.
        execute : function(callback, args) {
            $.mobile.loading("show");
            window.setTimeout(function() {
                if (callback) {
                    callback.apply(this, args);
                }
                $.mobile.loading("hide");
            }, 300);
        },

        // Navigate to the Intro view.
		showIntro : function() {
            // Bind the 'intro-art' jQuery Mobile page as the 'el' for the Backbone View.
			var introView = new IntroView({ el: "#intro-art"});
			ViewManager.showView(introView);
		},

        // Navigate to the Register Member view.
		showRegisterMember: function() {
			var model = window.FormMember;
            // Bind the 'register-art' jQuery Mobile page as the 'el' for the Backbone View.
			var registerMemberView = new RegisterMemberView({ el: "#register-art", model: model});
			ViewManager.showView(registerMemberView);
		},

        // Navigate to display all the Members.
		showAllMembers: function() {
            // Bind the 'member-art' jQuery Mobile page as the 'el' for the Backbone View.
			var listMembersView = new ListAllMembersView({ el: "#member-art"});
			window.ViewManager.showView(listMembersView);
		},

        // Navigate to the search contact view.
		showContactSearch: function() {
            // Bind the 'contact-art' jQuery Mobile page as the 'el' for the Backbone View.
			var contactSearchView = new ContactSearchView({ el: "#contact-art"});
			window.ViewManager.showView(contactSearchView);
		},

        // Navigate to the show Json view.
        showJson : function(memberId) {
            // Bind the 'json-art' jQuery Mobile page as the 'el' for the Backbone View.
            var jsonView = new JsonView({el: "#json-art", memberId: memberId});
            window.ViewManager.showView(jsonView);
        }
	});

    // Mark the jQuery Deferred as resolved.
	appStartDeferred.resolve();
});
