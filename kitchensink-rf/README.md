kitchensink-rf: Kitchensink With a JSF Richfaces Front End
========================
Author: Pete Muir, Brian Leathem  
Level: Intermediate  
Technologies: CDI, JSF, JPA, EJB, JPA, JAX-RS, BV, RichFaces  
Summary: The `kitchensink-rf` quickstart demonstrates a Java EE 6 application using JSF 2.1 with Richfaces 4.5, CDI 1.0, EJB 3.1, JPA 2.0 and Bean Validation 1.0.  
Target Product: WFK  
Product Versions: EAP 6.1, EAP 6.2, EAP 6.3, WFK 2.7  
Source: <https://github.com/richfaces/jdf-quickstarts>  

What is it?
-----------

The `kitchensink-rf` quickstart is based on the `kitchensink` quickstart, but demonstrates how to create a Java EE 6 compliant application using JSF 2.1, CDI 1.0, RichFaces 4.5 in Red Hat JBoss Enterprise Application Platform. It includes a persistence unit and some sample persistence and transaction code to introduce you to database access in enterprise Java.

This application builds on top of the standard JSF approach, by incorporating the RichFaces project to provide a set of components, allowing for a rich user experience.  RichFaces builds on top of the JSF standard, and is a fully portable solution compatible with all JSF implementations.

The kitchensink quickstart is built using Vanilla JSF for its front end.  With this kitchensink-rf quickstart, we build on top of the JSF user interface, augmenting it with RichFaces JSF components and capabilities.  Some key points to make note of while running the application:

*   Ajax push: This application makes use of ajax push.  When a member is created in one browser, the member list is updated in **all** open browsers.

    Try this yourself, by opening two different browsers, create a new member in one browser, and watch for the list to be updated in both browsers.

*   Ajax: All page updates are made with an ajax call, increasing the page responsiveness, and leading to a more native **feeling** application.

*   Client-side validation: By simply nesting a <rich:validator /> tag in the input elements, we wire them with RichFaces client-side validation capabilities.  The inputs are validated locally using javascript, without requiring a round-trip to the server.

*   Popups: Click the view link next to a member in the member list to view a popup with the member details.  To close the popup, click the "X" in the top right-hand corner, or click anywhere on the background mask.

*   Mobile support: view the application form a webkit powered browser on a mobile device to try out the mobile version of the application.  Alternatively, view the mobile version on your desktop by navigating to the url: <http://localhost:8080/jboss-kitchensink-rf/mobile/>

System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform (JBoss EAP) 6.1 or later with the Red Hat JBoss Web Framework Kit (WFK) 2.7. 

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later, and the RichFaces library.


Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts) before testing the quickstarts.


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

4. This will deploy `target/jboss-kitchensink-rf.war` to the running instance of the server.


Access the application
---------------------

The application will be running at the following URL: <http://localhost:8080/jboss-kitchensink-rf/>.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Run the Arquillian Tests
-------------------------

This quickstart provides Arquillian tests. By default, these tests are configured to be skipped as Arquillian tests require the use of a container.

_NOTE: The following commands assume you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Run the Arquillian Tests](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/RUN_ARQUILLIAN_TESTS.md#run-the-arquillian-tests) for complete instructions and additional options._

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type the following command to run the test goal with the following profile activated:

        mvn clean test -Parq-jbossas-remote


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


Run the Quickstart in Red Hat JBoss Developer Studio or Eclipse
-------------------------------------
You can also start the server and deploy the quickstarts or run the Arquillian tests from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/USE_JBDS.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

    mvn dependency:sources
    mvn dependency:resolve -Dclassifier=javadoc
