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
    /**
     * @desc messageBag directive that is used to display the contents of a messageBag service object
     * @example <div rh-message-bag messages="..."></div>
     */
    angular
        .module('app.contact')
        .directive('rhMessageBag', messageBag);

    function messageBag() {
        //Angular directives may be used to define small, reusable pieces of custom html
        var directive = {
            //Restrict the way in which a directive may be used in a partial
            // E = element, A = attribute, C = class, M = comment
            // AE therefore means <div rh-messageBag></div> OR <rh-messageBag></rh-messageBag> would be allowed
            restrict: 'AE',
            //Declares the scope to which the directive will belong and what will exist within it
            // = indicates two-way binding with the value of the messages property in html
            // e.g: for $scope.variableName => <div rh-messageBag messages="variableName"></div>
            scope: {
                messages: '='
            },
            //Defines the content that should be output from the directive
            //Can include HTML, data binding expressions, and even other expressions
            //Provides the html for a collection of alert boxes which: a) contain messages received
            // from the server; & b) may be closed
            templateUrl: 'templates/util/messageBag.directive.html',
            //Specified function is used for manipulating the template DOM after it has been rendered
            link: linkFunc
        };

        return directive;

        ////////////////

        function linkFunc() {}

    }
})();