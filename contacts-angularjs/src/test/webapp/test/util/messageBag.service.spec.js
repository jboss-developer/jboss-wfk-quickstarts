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
    //Unit tests that cover the basic functionality of messageBag.service.js
    //Initialise Angular.js dependency injection and perform other setup
    var injector = angular.injector(['ng', 'app.util', 'app.specs']);
    var messageBag = injector.get('messageBag');

    //Create test data
    var testMessage1 = {status: 200, body: 'Ok'};
    var testMessage2 = {status: 400, body: 'Bad request'};

    //Perform per-test setup
    var init = {
        setup: function() {
            messageBag.clear();
            messageBag.push(testMessage1.status, testMessage1.body);
        },
        teardown: function() {
            messageBag.clear();
        }
    };

    //messageBag.service.js tests module
    QUnit.module('messageBag Service spec', init);

    QUnit.test('Can retrieve messages stored', function(assert) {
        assert.equal(messageBag.get().length, 1);
        assert.deepEqual(messageBag.get()[0], testMessage1);
    });

    QUnit.test('Can add messages', function(assert) {
        messageBag.push(testMessage2.status, testMessage2.body);
        assert.equal(messageBag.get().length, 2);
        assert.deepEqual(messageBag.get()[1], testMessage2);
    });

    QUnit.test('Can remove messages', function(assert) {
        messageBag.remove(testMessage1);
        assert.equal(messageBag.get().length, 0);
    });

    QUnit.test('Can clear multiple messages', function(assert) {
        messageBag.push(testMessage2.status, testMessage2.body);
        assert.equal(messageBag.get().length, 2);
        messageBag.clear();
        assert.equal(messageBag.get().length, 0);
    });

})();



