contacts-mobile-basic: Example Application Using Multiple HTML5, Mobile & JAX-RS Technologies 
=========================================================================================================
Author: Joshua Wilson  
Level: Beginner  
Technologies: jQuery Mobile, jQuery, JavaScript, HTML5, REST  
Summary: A basic example of CRUD operations in a mobile only website.  
Target Product: WFK  
Product Versions: EAP 6.1, EAP 6.2, WFK 2.5  
Source: <https://github.com/jboss-developer/jboss-wfk-quickstarts>  

What is it?
-----------

This is your project! It's a deployable Maven 3 project to help you get your foot in the door developing HTML5 based 
mobile web applications with Java EE 6 on JBoss. This project is setup to allow you to create a basic Java EE 6 
application using HTML5, jQuery Mobile, JAX-RS, CDI 1.0, EJB 3.1, JPA 2.0 and Bean Validation 1.0. It includes a 
persistence unit and some sample persistence and transaction code to help you get your feet wet with database access in enterprise Java.

This application is built using a HTML5 + REST approach.  This uses a pure HTML client that interacts with with the 
application server via restful end-points (JAX-RS).  This application also uses some of the latest HTML5 features and 
advanced JAX-RS. And since testing is just as important with client side as it is server side, this application uses 
QUnit to show you how to unit test your JavaScript.

This application focuses on **CRUD** in a strictly mobile app using only **jQuery Mobile**(no other frameworks). The user will have 
the ability to:

* **Create** a new contact.

* **Read** a list of contacts.

* **Update** an existing contact.

* **Delete** a contact.

**Validation** is an important part of an application.  Typically in an HTML5 app you can let the built-in HTML5 form validation
do the work for you.  However in a mobile app it doesn't work, the mobile browsers just don't support it at this time. 
In order to validate the forms we added a plugin, jquery.validate. We provide both client-side and server-side validation 
through this plugin.  Over AJAX, if there is an error, the error is returned and displayed in the form.  You can see an 
example of this in the Edit form if you enter an email that is already in use.  There will be 3 errors on the screen; 
1 in the 'email' field and 2 at the top of the screen.  The application will attempt to insert the error message into a 
field if that field exists.  If the field does not exist then it display it at the top. In addition, there are 
[qunit tests](#run-the-qunit-tests) for every form of validation.  


System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform (EAP) 6.1 or 
later with the  Red Hat JBoss Web Framework Kit (WFK) 2.4.

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.

An HTML5 compatible browser such as Chrome, Safari 5+, Firefox 5+, or IE 9+ are required. and note that some behaviors 
will vary slightly (ex. validations) based on browser support, especially IE 9.

Mobile web support is limited to Android and iOS devices.  It should run on HP, and Black Berry devices as well.  
Windows Phone, and others will be supported as  jQuery Mobile announces support.
 
With the prerequisites out of the way, you're ready to build and deploy.


Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](../README.md#configure-maven) before testing the quickstarts.


Start the JBoss Server
-----------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the default profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat

   Note: Adding "-b 0.0.0.0" to the above commands will allow external clients (phones, tablets, desktops, etc...) connect through your local network.

   For example

        For Linux:   JBOSS_HOME/bin/standalone.sh -b 0.0.0.0
        For Windows: JBOSS_HOME\bin\standalone.bat -b 0.0.0.0


Build and Deploy the Quickstart
-------------------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

4. This deploys `target/jboss-contacts-mobile-basic.war` to the running instance of the server.


Access the application
----------------------

Access the running client application in a browser at the following URL: <http://localhost:8080/jboss-contacts-mobile-basic/>.

The app is made up of the following pages:

**Main page**

* Displays a list of contacts
* Search bar for the list
* Details button changes to the Detailed list
* Clicking on a contact brings up an Edit form
* Menu button (in upper left) opens menu

**Menu pullout**

* Add a new Contact
* List/Detail view switcher, depending on what is currently displayed
* About information

**Details page**

* Same as Main page except all information is displayed with each contact

**Add form**

* First name, Last name, Phone, Email, and BirthDate fields
* Save = submit the form
* Clear = reset the form but stay on the form
* Cancel = reset the form and go the Main page

**Edit form**

* Same as Add form
* Delete button will delete the contact currently viewed and return you to the Main page

FAQ
--------------------

1) Why can't I enter a date in the birthdate field?

  * Chrome has a [bug](https://code.google.com/p/chromium/issues/detail?id=232296) in it
    * Use the arrow keys to change the date: up arrow key, tab to day, up arrow key, tab to year, up arrow key
    * Use the date picker: a large black down arrow between the up/down arrows and the big X on the right side.
  * Firefox, IE, and Safari require strict formatting of YYYY-DD-MM, *Note:* It must be a dash and not a slash


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------

You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, 
see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 


### Deploying to OpenShift

You can also deploy the application directly to OpenShift, Red Hat's cloud based PaaS offering, follow the 
instructions [here](https://community.jboss.org/wiki/DeployingHTML5ApplicationsToOpenshift)

Minification
============================

By default, the project uses the [wro4j](http://code.google.com/p/wro4j/) plugin, which provides the ability to concatenate, 
validate and minify JavaScript and CSS files. These minified files, as well as their unmodified versions are deployed with the project.

With just a few quick changes to the project, you can link to the minified versions of your JavaScript and CSS files.

First, in the <project-root>/src/main/webapp/index.html file, search for references to minification and comment or 
uncomment the appropriate lines.

Finally, wro4j runs in the compile phase so any standard build command like package, install, etc. will trigger it. 
The plugin is in a profile with an id of "minify" so you will want to specify that profile in your maven build.

NOTE: You must either specify the default profile for no tests or the arquillian test profile to run tests when minifying 
to avoid test errors. For example:

    #No Tests
    mvn clean package jboss-as:deploy -Pminify,default

OR

    #With Tests
    mvn clean package jboss-as:deploy -Pminify,arq-jbossas-remote
 
Run the Arquillian tests
============================

By default, tests are configured to be skipped. The reason is that the sample test is an Arquillian test, which requires 
the use of a container. You can activate this test by selecting one of the container configuration provided  for JBoss.

To run the test in JBoss, first start the container instance. Then, run the test goal with the following profile activated:

    mvn clean test -Parq-jbossas-remote

Run the QUnit tests
============================

QUnit is a JavaScript unit testing framework used and built by jQuery. Because JavaScript code is the core of this HTML5 
application, this quickstart provides a set of QUnit tests that automate testing of this code in various browsers. Executing 
QUnit test cases are quite easy. Simply load the following HTML in the browser you wish to test.

        QUICKSTART_HOME/contacts-mobile-basic/src/test/qunit/index.html

Browsers:

* You can also display the tests using the Eclipse built-in browser.
* If you use Chrome there will be a false failure on the date tests.
* IE, FireFox, and Safari run the tests correctly. 

For more information on QUnit tests see http://docs.jquery.com/QUnit

Import the Project into an IDE
=================================

If you created the project using the Maven archetype wizard in your IDE (Eclipse, NetBeans or IntelliJ IDEA), then there 
is nothing to do. You should already have an IDE project.

Detailed instructions for using Eclipse / JBoss Tools with are provided in the 
[Get Started Developing Applications](http://www.jboss.org/jdf/quickstarts/jboss-as-quickstart/guide/Introduction/ "Get Started Developing Applications").

If you created the project from the command line using archetype:generate, then you need to import the project into your IDE. 
If you are using NetBeans 6.8 or IntelliJ IDEA 9, then all you have to do is open the project as an existing project. 
Both of these IDEs recognize Maven projects natively.

Debug the Application
=============================

If you want to be able to debug into the source code or look at the Javadocs of any library in the project, you can run 
either of the following two commands to pull them into your local repository. The IDE should then detect them.

    mvn dependency:sources
    mvn dependency:resolve -Dclassifier=javadoc
