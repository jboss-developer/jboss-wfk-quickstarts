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
/*
Unit tests that cover basic functionality of app.js.
 */

var server;

module('Member display tests', {
    setup: function() {
        server = sinon.fakeServer.create();
        $("#qunit-fixture").empty();
    },
    teardown: function() {
        server.restore();
    }
});

test('Render a single member', function() {
    expect(1);

    var members = [{"email": "jane.doe@company.com", "id": 1, "name": "Jane Doe", "phoneNumber": "12312312311"},{"email": "john.doe@company.com", "id": 0, "name": "John Doe", "phoneNumber": "2125551212"}];

    var member = new Member({"email": "jane.doe@company.com", "id": 1, "name": "Jane Doe", "phoneNumber": "12312312311"});

    var view = new MemberView({model : member});

    // Render the view
    view.render();

    var content = view.$el;
    ok(content.length == 1, 'Number of rows built: ' + content.length);
});

test('Render multiple members', function() {
    expect(1);

    $("#qunit-fixture").append("<table id='member-table'><tbody id='members'></tbody></table>");

    // Set up the rules for the fake server
    server.respondWith("GET", /rest\/members(.*)/,
        [200, { "Content-Type": "application/json" },
            '[{"email": "jane.doe@company.com", "id": 1, "name": "Jane Doe", "phoneNumber": "12312312311"},{"email": "john.doe@company.com", "id": 0, "name": "John Doe", "phoneNumber": "2125551212"}]']);

    var view = new ListAllMembersView({ el: "#qunit-fixture"});

    // Render the view
    view.render();

    // Fake the server's response
    server.respond();

    var rows = view.$el.find("tr");
    ok(rows.length == 2, 'Number of rows built: ' + rows.length);
});

module('Tests for Backbone View interaction with RESTful backend', {
    setup: function() {
        server = sinon.fakeServer.create();
        $("#qunit-fixture").empty();
    },
    teardown: function() {
        server.restore();
    }
});

test('Register a new member', function() {
    expect(3);

    // Add the registration form for this test
    var regForm = $("#register-form").html();
    $("#qunit-fixture").html(regForm);

    var view = new RegisterMemberView({ el: "#qunit-fixture"});

    // Set up the rules for the fake server
    server.respondWith("POST", /rest\/members(.*)/,
        [200, { "Content-Type": "application/json" },
            '{"email": "jane.doe@company.com", "id": 1, "name": "Jane Doe", "phoneNumber": "12312312311"}']);

    // Render the view
    view.render();

    // Populate the registration form
    $("#name").val("Jane Doe");
    $("#email").val("jane.doe@company.com");
    $("#phoneNumber").val("12312312311");

    // Submit the form
    $("#register").click();

    // Fake the server's response
    server.respond();

    // Verify that a success message is displayed, since the server responded with a HTTP 200.
    var msgs = $("span.success");
    var errors = $("span.invalid");
    ok(errors.length == 0, 'Number of errors: ' + errors.length);
    ok(msgs.length == 1, 'Number of messages: ' + msgs.length);
    equal(msgs.text(), "Member Registered", "The message should indicate that the member was registered");
});

test('Register a member with a duplicate email in the RegisterMemberView', function() {
    expect(3);

    // Add the registration form for this test
    var regForm = $("#register-form").html();
    $("#qunit-fixture").html(regForm);

    var view = new RegisterMemberView({ el: "#qunit-fixture"});

    // Set up the rules for the fake server
    server.respondWith("POST", /rest\/members(.*)/,
        [400, { "Content-Type": "application/json" },
            '{"email":"Email taken"}']);

    // Render the view
    view.render();

    // Populate the registration form
    $("#name").val("Jane Doe");
    $("#email").val("jane.doe@company.com");
    $("#phoneNumber").val("12312312311");

    // Submit the form
    $("#reg").submit();

    // Fake the server's response
    server.respond();

    // Verify that the error in the server response is displayed, since the server responded with a HTTP 400.
    var msgs = $("span.success");
    var errors = $("span.invalid");
    ok(msgs.length == 0, 'Number of messages: ' + msgs.length);
    ok(errors.length == 1, 'Number of errors: ' + errors.length);
    equal(errors.text(), "Email taken", "The message contain the server's response informing the user about the duplicate email.");
});

module('Validation Tests', {
    setup: function() {
        $("#qunit-fixture").empty();
    }
});

test('Attempt to register a member with an invalid name', function() {
    expect(2);

    // Add the registration form for this test
    var regForm = $("#register-form").html();
    $("#qunit-fixture").html(regForm);

    var view = new RegisterMemberView({ el: "#qunit-fixture"});

    // Render the view
    view.render();

    // Populate the registration form. Leave the name field blank
    $("#name").val("");

    // Submit the form
    $("#reg").submit();

    // Verify that a HTML5 validation error is displayed.
    var msgs = $("span.success");
    var errors = $("#name + span.invalid");
    ok(msgs.length == 0, 'Number of messages: ' + msgs.length);
    ok(errors.length == 1, 'Number of errors: ' + errors.length);
});

test('Attempt to register a member with an invalid email', function() {
    expect(2);

    // Add the registration form for this test
    var regForm = $("#register-form").html();
    $("#qunit-fixture").html(regForm);

    var view = new RegisterMemberView({ el: "#qunit-fixture"});

    // Render the view
    view.render();

    // Populate the registration form. Leave the name field blank
    $("#name").val("Jane Doe");
    $("#email").val("Jane Doe");

    // Submit the form
    $("#reg").submit();

    // Verify that a HTML5 validation error is displayed.
    var msgs = $("span.success");
    var errors = $("#email + span.invalid");
    ok(msgs.length == 0, 'Number of messages: ' + msgs.length);
    ok(errors.length == 1, 'Number of errors: ' + errors.length);
});

test('Attempt to register a member with an invalid phone number', function() {
    expect(2);

    // Add the registration form for this test
    var regForm = $("#register-form").html();
    $("#qunit-fixture").html(regForm);

    var view = new RegisterMemberView({ el: "#qunit-fixture"});

    // Render the view
    view.render();

    // Populate the registration form. Leave the name field blank
    $("#name").val("Jane Doe");
    $("#email").val("jane.doe@company.com");
    $("#phoneNumber").val("00");

    // Submit the form
    $("#reg").submit();

    // Verify that a HTML5 validation error is displayed.
    var msgs = $("span.success");
    var errors = $("#phoneNumber + span.invalid");
    ok(msgs.length == 0, 'Number of messages: ' + msgs.length);
    ok(errors.length == 1, 'Number of errors: ' + errors.length);
});
