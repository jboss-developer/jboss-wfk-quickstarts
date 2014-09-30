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
(function() {
    'use strict';
    //Unit tests that cover the basic functionality of contact.controller.js
    //Initialise Angular.js dependency injection and perform other setup
    var injector = angular.injector(['ng', 'app', 'app.contact', 'app.specs']);
    var messageBag = injector.get('messageBag');
    var Contact = injector.get('Contact');
    var $controller = injector.get('$controller');
    var $filter = injector.get('$filter');
    var $location = injector.get('$location');
    var testData = [
        {"id":10002,"firstName":"Davey","lastName":"Jones","email":"davey.jones@locker.com",
            "phoneNumber":"(212)555-3333","birthDate":"1996-08-07"},
        {"id":10001,"firstName":"John","lastName":"Smith","email":"john.smith@mailinator.com",
            "phoneNumber":"(212) 555-1212","birthDate":"1963-06-03"}
    ];

    //Perform per-test setup
    var init = {
        setup: function() {
            this.$scope = injector.get('$rootScope').$new();
            this.testData = JSON.parse(JSON.stringify(testData));
            this.testContacts = [
                new Contact(this.testData[0]),
                new Contact(this.testData[1])
            ];
            Contact.data = this.testContacts;
            this.$httpBackend = injector.get('$httpBackend');
        }
    };

    //contact.controller.js tests module
    QUnit.module('ContactController Spec', init);

    QUnit.test('"ContactController" initialised successfully', function(assert) {
        $controller('ContactController', {
            $scope: this.$scope,
            $routeParams: {},
            $location: $location,
            Contact: Contact,
            messageBag: messageBag
        });
        assert.ok(angular.isFunction(this.$scope.messages.get), "The messageBag service has been injected successfully.");
    });

    QUnit.test('"ContactController" correctly attempts to create a Contact.', function(assert) {
        expect(1);
        delete this.testData[0].id;
        this.$httpBackend.expectPOST('rest/contacts', this.testData[0]).respond(409, 'Conflict');
        $controller('ContactController', {
            $scope: this.$scope,
            $routeParams: {},
            $location: $location,
            Contact: Contact,
            messageBag: messageBag
        });
        this.$scope.contacts.save(null, this.testData[0], null, function(data){
            assert.equal(data.status, 409);
        });
        this.$httpBackend.flush();
    });

    QUnit.test('"ContactController" correctly handles routeParams and requests the appropriate Contact.', function(assert) {
        this.$httpBackend.expectGET(/rest\/contacts\/10001[?&]_=[0-9]+$/).respond(200, this.testData[1]);
        $controller('ContactController', {
            $scope: this.$scope,
            $routeParams: {contactId: 10001},
            $location: $location,
            Contact: Contact,
            messageBag: messageBag
        });
        this.$httpBackend.flush();
        //Work around for the properties Angular adds to objects produced by the $resource factory
        var loadedContact = this.$scope.contact;
        delete loadedContact.$promise;
        delete loadedContact.$resolved;
        assert.deepEqual(loadedContact, this.testContacts[1]);
    });

    QUnit.test('"ContactController" correctly attempts to update a specified Contact.', function(assert) {
        expect(1);
        this.$httpBackend.expectGET(/rest\/contacts\/10001[?&]_=[0-9]+$/).respond(200, this.testData[1]);
        $controller('ContactController', {
            $scope: this.$scope,
            $routeParams: {contactId: 10001},
            $location: $location,
            Contact: Contact,
            messageBag: messageBag
        });
        this.$httpBackend.flush();
        this.$httpBackend.resetExpectations();
        this.$scope.contact.firstName = this.testData[1].firstName = 'Jack';
        this.$scope.contact.email = this.testData[1].email = 'jack.smith@mailinator.com';
        this.$httpBackend.expectPUT('rest/contacts/10001', this.testData[1]).respond(200);
        this.$scope.saveContact();
        this.$httpBackend.flush();
        assert.equal(this.$scope.contacts.data[1].firstName, 'Jack');
    });

    QUnit.test('"ContactController" correctly attempts to delete a specified Contact.', function(assert) {
        expect(1);
        this.$httpBackend.expectGET(/rest\/contacts\/10001[?&]_=[0-9]+$/).respond(200, this.testData[1]);
        $controller('ContactController', {
            $scope: this.$scope,
            $routeParams: {contactId: 10001},
            $location: $location,
            Contact: Contact,
            messageBag: messageBag
        });
        this.$httpBackend.flush();
        this.$httpBackend.resetExpectations();
        this.$httpBackend.expectDELETE('rest/contacts/10001').respond(204);
        this.$scope.deleteContact();
        this.$httpBackend.flush();
        assert.equal(this.$scope.contacts.data.length, 1);
    });

})();



