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
    angular
        .module('app.util')
        //Defines the messageBag service, used to share simple objects ({status:'', body:''}) throughout the code
        .factory('messageBag', messageBag);

    //messages factory function, gets invoked upon load
    function messageBag() {
        //Create the messages array
        var messages = [];

        //Declare the messageBag service API
        var service =  {
            clear: clear,
            push: push,
            remove: remove,
            get: get
        };

        return service;

        //////////////

        //Clear the messages array
        function clear() {
            messages = [];
        };

        //Add to the messages array
        function push(status, body) {
            messages.push({
                status: status,
                body: body
            });
        };

        //Remove a specific message from the messages array
        function remove(message) {
            //Get the index of the message to be removed
            var idx = messages.indexOf(message);
            messages.splice(idx, 1);
        };

        //Get the messages array
        function get() {
            return messages;
        };
    }
})();