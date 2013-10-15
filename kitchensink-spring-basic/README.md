kitchensink-spring-basic: Kitchensink Example using Spring 3.1
===========================================
Author: Marius Bogoevici
Level: Intermediate
Technologies: JSP, JPA, JSON, Spring, JUnit
Summary: An example that incorporates multiple technologies
Target Product: WFK
Product Versions: EAP 6.1, EAP 6.2, WFK 2.4
Source: <https://github.com/jboss-developer/jboss-wfk-quickstarts/>

What is it?
-----------


This is your project! It is a sample, deployable Maven 3 project to help you get your foot in the door developing with Java EE 6 on Red Hat JBoss Enterprise Application Platform 6.1 or later.

This project is setup to allow you to create a compliant Java EE 6 application using JSP, JPA 2.0 and Spring 3.2. It includes a persistence unit and some sample persistence and transaction code to introduce you to database access in enterprise Java:

* In `jboss-as-spring-mvc-context.xml` `<context:component-scan base-package="org.jboss.as.quickstarts.kitchensink.spring.basic.mvc"/>` and `<mvc:annotation-driven/>` are used to register both the non-rest and rest controllers.

* The controllers map the respective urls to methods using `@RequestMapping(url)`.

* To return JSON, the rest controller uses `@ResponseBody`.

* The datasource and entitymanager are retrieved via JNDI.

System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform (EAP) 6.1 or later with the Red Hat JBoss Web Framework Kit (WFK) 2.4. 

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.

Configure Maven
---------------

1. If you have not yet done so, you must Configure Maven before testing the quickstarts.
2. Start the JBoss Server
3. Open a command line and navigate to the root of the JBoss server directory.
4. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat

Build and Deploy the Quickstart
-------------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Build and Deploy the Quickstarts](../README.md#build-and-deploy-the-quickstarts) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

4. This will deploy `target/jboss-kitchensink-spring-basic.war` to the running instance of the server.


Access the application
----------------------

The application will be running at the following URL: <http://localhost:8080/jboss-kitchensink-spring-basic/>.

Undeploy the Archive
----------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy

Run the Arquillian Functional Tests
-----------------------------------

This quickstart provides Arquillian functional tests as well. They are located under the directory "functional-tests". Functional tests verify that your application behaves correctly from the user's point of view - simulating clicking around the page as a normal user would do.

To run these tests, you must build the main project as described above.

1. Open a command line and navigate to the root directory of this quickstart.
2. Build the quickstart WAR using the following command:

        mvn clean package

3. Navigate to the functional-tests/ directory in this quickstart.
4. If you have a running instance of the JBoss Server, as described above, run the remote tests by typing the following command:

        mvn clean verify -Parq-jbossas-remote

5. If you prefer to run the functional tests using managed instance of the JBoss server, meaning the tests will start the server for you, type fhe following command:

        mvn clean verify -Parq-jbossas-managed

Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------

You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts)


Debug the Application
---------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc

