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
    //Unit tests that cover the basic functionality of app.controller.js
    //Initialise Angular.js dependency injection and perform other setup
    var injector = angular.injector(['ng', 'app', 'app.specs']);
    var messageBag = injector.get('messageBag');
    var Contact = injector.get('Contact');
    var $controller = injector.get('$controller');
    var $filter = injector.get('$filter');
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
            this.$httpBackend = injector.get('$httpBackend');
            this.$httpBackend.expectGET(/rest\/contacts[?&]_=[0-9]+$/).respond(200, this.testContacts);
            $controller('AppController', {
                $scope: this.$scope,
                $filter: $filter,
                Contact: Contact,
                messageBag: messageBag
            });
            this.$httpBackend.flush();
        }
    };

    //app.controller.js tests module
    QUnit.module('AppController Spec', init);

    QUnit.test('"AppController" initialised successfully', function(assert) {
        assert.deepEqual(this.$scope.contacts.data, this.testContacts, "The Contacts data is requested upon AppController init.");
        assert.ok(angular.isFunction(this.$scope.messages.get), "The messageBag service has been injected successfully.");
    });

    QUnit.test('"AppController" dynamically generates contact list heads correctly', function(assert) {
        assert.deepEqual(this.$scope.contactsList, {'D':[this.testContacts[0]], 'J':[this.testContacts[1]]});
    });

    QUnit.test('"AppController" search filter works correctly', function(assert) {
        this.$scope.search = "D";
        this.$scope.$digest();
        assert.ok(!this.$scope.contactsList.hasOwnProperty('J'));
        this.$scope.search = "Dd";
        this.$scope.$digest();
        assert.ok(!this.$scope.contactsList.hasOwnProperty('D'));
    });

})();



