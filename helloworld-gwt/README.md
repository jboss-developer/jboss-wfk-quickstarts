helloworld-gwt: Helloworld with a GWT (Google Web Toolkit) Front End Client
===========================================================================
Author: Christian Sadilek  
Level: Beginner  
Technologies: GWT  
Summary: Demonstrates the use of CDI 1.0 and JAX-RS with a GWT front-end client  
Target Product: WFK  
Product Versions: EAP 6.1, EAP 6.2, WFK 2.6  
Source: <https://github.com/jboss-developer/jboss-wfk-quickstarts/>  

What is it?
-----------

This example demonstrates the use of *CDI 1.0* and *JAX-RS* in Red Hat JBoss Enterprise Application Platform 6.1 or later with a GWT front-end client.
GWT is basically a typesafe, statically checked programming model for producing HTML5+CSS3+JavaScript front-ends. In this example, we use RESTful services on the backend.

You can test the REST endpoint at the URL http://localhost:8080/jboss-helloworld-gwt/hello/json/David

System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform (EAP) 6.1 or later with the  Red Hat JBoss Web Framework Kit (WFK) 2.6.

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

4. This will deploy `target/jboss-helloworld-gwt.war` to the running instance of the server.


Access the application 
---------------------

The application will be running at the following URL:  <http://localhost:8080/jboss-helloworld-gwt/>.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Run the Application in GWT Super Dev Mode
---------------------------------------

Super Dev Mode allows GWT developers to quickly recompile their code and see the results in a browser. If you plan to modify this demo, we recommend you start the application in Super Dev Mode so you don't have to repackage the entire application every time you change it. It also allows developers to use a debugger to inspect a running GWT application. 

1. Deploy the WAR file and start the JBoss EAP server as described above.
2. Open a command line and navigate to the helloworld-gwt quickstart directory.
3. Execute the following command:

        mvn gwt:run-codeserver

4. Open this link <http://localhost:9876/> in your browser.
5. Drag the two bookmarklets "Dev Mode On" and "Dev Mode Off" to your browser's bookmark bar.
6. Navigate your browser to <http://localhost:8080/jboss-helloworld-gwt/>.
7. Click "Dev Mode On" to start development mode.
8. If your browser support Source Maps, you can use it to see the Java source code and set breakpoints using the developer tools directly in the browser.


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

