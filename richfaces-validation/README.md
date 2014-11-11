richfaces-validation: RichFaces and Bean Validation
=======================================================
Author: Lukas Fryc  
Level: Beginner  
Technologies: RichFaces  
Summary: The `richfaces-validation` quickstart demonstrates how to use JSF 2.0, RichFaces 4.2, CDI 1.0, JPA 2.0 and Bean Validation 1.0.  
Target Product: WFK  
Product Versions: EAP 6.1, EAP 6.2, EAP 6.3, WFK 2.7  
Source: <https://github.com/richfaces/as-quickstarts>  

What is it?
-----------

The `richfaces-validation` quickstart demonstrates how to use JSF 2.0, RichFaces 4.2, CDI 1.0, JPA 2.0 and Bean Validation 1.0. 

It consists of one entity, `Member`, which is annotated with JSR-303 (Bean Validation) constraints. In typical applications, these constraints are checked in several places:

* As database constraints
* In the persistence layer
* In the view layer (using JSF / Bean Validation integration)
* On the client (using RichFaces 4.2 - Client Side Validation)

This ensures the constraints are applied consistently, across all layers, allowing clear and precise error reporting, while not breaking the DRY (Don't Repeat Yourself) principle.

This quickstart does not contain a persistence layer. It only shows integration of RichFaces, JSF and Bean Validation. The validation is done using client-side validation where possible.

The application contains a view layer written using JSF and RichFaces and includes an AJAX wizard for new member registration. Each member needs to complete the following information in the wizard:

* e-mail
* name and phone
* confirmation of supplied information

Once users are successfully registered, they are redirected back to the initial page with a message that the registration was successful.

This quickstart also includes tests for all Bean Validation constraints for the `Member` entity. This allows you to check constraints without without the need for a view layer. The tests are written using the Arquillian framework.


System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform (EAP) 6.1 or later with the  Red Hat JBoss Web Framework Kit (WFK) 2.7.

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.

 
Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](../README.md#configure-maven) before testing the quickstarts.


Start the JBoss EAP Server
-------------------------

1. Open a command line and navigate to the root of the JBoss EAP directory.
2. The following shows the command line to start the server with the default profile:

        For Linux:   EAP_HOME/bin/standalone.sh
        For Windows: EAP_HOME\bin\standalone.bat

 
Build and Deploy the Quickstart
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Build and Deploy the Quickstarts](../README.md#build-and-deploy-the-quickstarts) for complete instructions and additional options._

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

4. This will deploy `target/jboss-richfaces-validation.war` to the running instance of the server.


Access the application 
---------------------

The application will be running at the following URL: <http://localhost:8080/jboss-richfaces-validation/>.

You will be provided with form to enter member information. 

* Enter an Email address and click on *Continue*. If the Email address is not valid, you will see an error message saying "Not a well-formed Email address"
* On the next page, you must enter a Name and Phone number. A valid name must be between 1 and 25 characters in length. A valid phone number must be between 10 and 12 digits in length.
* Click *Confirm* to register the member.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Run the Arquillian Tests 
-------------------------

This quickstart provides Arquillian tests. By default, these tests are configured to be skipped as Arquillian tests require the use of a container. 

_NOTE: The following commands assume you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Run the Arquillian Tests](../README.md#run-the-arquillian-tests) for complete instructions and additional options._

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type the following command to run the test goal with the following profile activated:

        mvn clean test -Parq-jbossas-remote 


Investigate the Console Output
-----------------------

When you run the tests, JUnit will present you test report summary:

    Tests run: 5, Failures: 0, Errors: 0, Skipped: 0

If you are interested in more details, check `target/surefire-reports` directory. You can check console output to verify that Arquillian had really used the real application server. Search for lines similar to the following in the server output log:

    [timestamp] INFO [org.jboss.as.server.deployment] (MSC service thread 1-2) Starting deployment of "test.war"
    ...
    [timestamp] INFO [org.jboss.as.server] (management-handler-threads - 1) JBAS018559: Deployed "test.war"
    ...
    [timestamp] INFO [org.jboss.as.server.deployment] (MSC service thread 1-3) Stopped deployment test.war in 48ms
    ...
    [timestamp] INFO [org.jboss.as.server] (management-handler-threads - 1) JBAS018558: Undeployed "test.war
	 
	 
Run the Arquillian Functional Tests
-----------------------------------

This quickstart provides Arquillian functional tests as well. They are located in the functional-tests/ subdirectory under the root directory of this quickstart.
Functional tests verify that your application behaves correctly from the user's point of view. The tests open a browser instance, simulate clicking around the page as a normal user would do, and then close the browser instance.

To run these tests, you must build the main project as described above.

1. Open a command line and navigate to the root directory of this quickstart.
2. Build the quickstart WAR using the following command:

        mvn clean package

3. Navigate to the functional-tests/ directory in this quickstart.
4. If you have a running instance of the JBoss EAP server, as described above, run the remote tests by typing the following command:

        mvn clean verify -Parq-jbossas-remote

5. If you prefer to run the functional tests using managed instance of the JBoss EAP server, meaning the tests will start the server for you, type fhe following command:

        mvn clean verify -Parq-jbossas-managed


Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------
You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc
