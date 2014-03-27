Red Hat JBoss Web Framework Kit (WFK) Quickstarts
====================
Summary: The quickstarts demonstrate Java EE 6 and a few additional technologies from the JBoss stack. They provide small, specific, working examples that can be used as a reference for your own project.

Introduction
------------


These quickstarts run on Red Hat JBoss Enterprise Application Platform 6.1 or later with the Red Hat JBoss Web Framework Kit. We recommend using the JBoss EAP ZIP file. This version uses the correct dependencies and ensures you test and compile against your runtime environment. 

Be sure to read this entire document before you attempt to work with the quickstarts. It contains the following information:

* [Available Quickstarts](#available-quickstarts): List of the available quickstarts and details about each one.

* [Suggested Approach to the Quickstarts](#suggested-approach-to-the-quickstarts): A suggested approach on how to work with the quickstarts.

* [System Requirements](#system-requirements): List of software required to run the quickstarts.

* [Configure Maven](#configure-maven): How to configure the Maven repository for use by the quickstarts.

* [Run the Quickstarts](#run-the-quickstarts): General instructions for building, deploying, and running the quickstarts.

* [Run the Arquillian Tests](#run-the-arquillian-tests): How to run the Arquillian tests provided by some of the quickstarts.

* [Build and Deploy the Quickstart - to OpenShift](#build-and-deploy-the-quickstart-to-openshift): Deploy the application to OpenShift

* [Optional Components](#optional-components): How to install and configure optional components required by some of the quickstarts.


Available Quickstarts
---------------------

The following is a list of the currently available quickstarts. The table lists each quickstart name, the technologies it demonstrates, gives a brief description of the quickstart, and the level of experience required to set it up. For more detailed information about a quickstart, click on the quickstart name.

Some quickstarts are designed to enhance or extend other quickstarts. These are noted in the **Prerequisites** column. If a quickstart lists prerequisites, those must be installed or deployed before working with the quickstart.

Quickstarts with tutorials in the [Get Started Developing Applications](http://www.jboss.org/jdf/quickstarts/jboss-as-quickstart/guide/Introduction/ "Get Started Developing Applications") are noted with two asterisks ( ** ) following the quickstart name. 

_Note:_ The quickstart README files use the replaceable value `EAP_HOME` to denote the path to the JBoss EAP 6 installation. When you encounter this value in a README file, be sure to replace it with the actual path to your JBoss EAP 6 installation. The 'JBOSS_HOME' environment variable, which is used in scripts, continues to work as it has in the past.

[TOC-quickstart]

Suggested Approach to the Quickstarts
-------------------------------------

We suggest you approach the quickstarts as follows:

* Regardless of your level of expertise, we suggest you start with the **helloworld-html5** quickstart. It is the simplest example and is an easy way to prove your server is configured and started correctly.
* If you are a beginner or new to JBoss, start with the quickstarts labeled **Beginner**, then try those marked as **Intermediate**. When you are comfortable with those, move on to the **Advanced** quickstarts.
* Some quickstarts are based upon other quickstarts but have expanded capabilities and functionality. If a prerequisite quickstart is listed, be sure to deploy and test it before looking at the expanded version.


System Requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform (EAP) 6.1 or later with the  Red Hat JBoss Web Framework Kit (WFK) 2.5.

To run these quickstarts with the provided build scripts, you need the following:

1. Java 1.6, to run JBoss AS and Maven. You can choose from the following:
    * OpenJDK
    * Oracle Java SE
    * Oracle JRockit

2. Maven 3.0.0 or later, to build and deploy the examples
    * If you have not yet installed Maven, see the [Maven Getting Started Guide](http://maven.apache.org/guides/getting-started/index.html) for details.
    * If you have installed Maven, you can check the version by typing the following in a command line:

            mvn --version 

3. The JBoss EAP distribution ZIP.
    * For information on how to install and run JBoss, refer to the product documentation.

4. You can also use [JBoss Developer Studio or Eclipse](#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) to run the quickstarts. 


Configure Maven
---------------

### Configure Maven to Build and Deploy the Quickstarts

The quickstarts use artifacts located in the JBoss GA and Early Access repositories. You must configure Maven to use these repositories before you build and deploy the quickstarts. 

_Note: These instructions assume you are working with a released version of the quickstarts. If you are working with the quickstarts located in the GitHub master branch, follow the instructions located in the [Contributing Guide](CONTRIBUTING.md#configure-maven)._

1. Locate the Maven install directory for your operating system. It is usually installed in `${user.home}/.m2/`. 

            For Linux or Mac:   ~/.m2/
            For Windows: "\Documents and Settings\USER_NAME\.m2\"  -or-  "\Users\USER_NAME\.m2\"

2. If you have an existing `settings.xml` file, rename it so you can restore it later.

            For Linux or Mac:  mv ~/.m2/settings.xml ~/.m2/settings-backup.xml
            For Windows: ren "\Documents and Settings\USER_NAME\.m2\settings.xml" settings-backup.xml
                    -or- ren "\Users\USER_NAME\.m2\settings.xml" settings-backup.xml
                   
3. If you have an existing `repository/` directory, rename it so you can restore it later. For example

            For Linux or Mac:  mv ~/.m2/repository/ ~/.m2/repository-backup/
            For Windows: ren "\Documents and Settings\USER_NAME\.m2\repository\" repository-backup
                    -or- ren "\Users\USER_NAME\.m2\repository\" repository-backup
4. Copy the `settings.xml` file from the root of the quickstarts directory to your Maven install directory.
 
            For Linux or Mac:  cp QUICKSTART_HOME/settings.xml  ~/.m2/settings.xml
            For Windows: copy QUICKSTART_HOME/settings.xml "\Documents and Settings\USER_NAME\.m2\settings.xml"
                    -or- copy QUICKSTART_HOME/settings.xml "\Users\USER_NAME\.m2\settings.xml"

_Note:_ If you do not wish to configure the Maven settings, you must pass the configuration setting on every Maven command as follows: ` -s QUICKSTART_HOME/settings.xml`

### Restore Your Maven Configuration When You Finish Testing the Quickstarts

1. Locate the Maven install directory for your operating system. It is usually installed in `${user.home}/.m2/`. 

            For Linux or Mac:   ~/.m2/
            For Windows: "\Documents and Settings\USER_NAME\.m2\"  -or-  "\Users\USER_NAME\.m2\"

2. Restore the `settings.xml` file/

            For Linux or Mac:  mv ~/.m2/settings-backup.xml ~/.m2/settings.xml
            For Windows: ren "\Documents and Settings\USER_NAME\.m2\settings-backup.xml" settings.xml
                    -or- ren "\Users\USER_NAME\.m2\settings-backup.xml" settings.xml
                   
3. Restore the `repository/` directory

            For Linux or Mac:  mv ~/.m2/repository-backup/ ~/.m2/repository/
            For Windows: ren "\Documents and Settings\USER_NAME\.m2\repository-backup\" repository
                    -or- ren "\Users\USER_NAME\.m2\repository-backup\" repository
            

### Maven Profiles

Profiles are used by Maven to customize the build environment. The `pom.xml` in the root of the quickstart directory defines the following profiles:

* The `default` profile defines the list of modules or quickstarts that require nothing but JBoss Enterprise Application Platform.
* The `non-maven` profile lists quickstarts that do not require Maven, for example, quickstarts that use other Frameworks or technologies.
* The `functional-tests` profile lists quickstarts that provide functional tests.


Run the Quickstarts
-------------------

The root folder of each individual quickstart contains a README file with specific details on how to build and run the example. In most cases you do the following:

* [Start the JBoss EAP server](#start-the-jboss-eap-server)
* [Build and deploy the quickstarts](#build-and-deploy-the-quickstarts)


### Start the JBoss EAP Server

Before you deploy a quickstart, in most cases you need a running JBoss EAP server. A few of the Arquillian tests do not require a running server. This will be noted in the README for that quickstart. 

The JBoss EAP server can be started a few different ways.

* [Start the Default JBoss EAP Server](#start-the-default-jboss-eap-server): This is the default configuration. It defines minimal subsystems and services.
* [Start the JBoss EAP Server with the _full_ profile](#start-the-jboss-eap-server-with-the-full-profile): This profile configures many of the commonly used subsystems and services.
* [Start the JBoss EAP Server with a custom configuration](#start-the-jboss-eap-server-with-custom-configuration-options): Custom configuration parameters can be specified on the command line when starting the server.

The README for each quickstart will specify which configuration is required to run the example.

#### Start the Default JBoss EAP Server

To start JBoss EAP:

1. Open a command line and navigate to the root of the JBoss EAP directory.
2. The following shows the command line to start the JBoss EAP server:

        For Linux:   EAP_HOME/bin/standalone.sh
        For Windows: EAP_HOME\bin\standalone.bat

#### Start the JBoss EAP Server with the Full Profile

To start JBoss EAP with the Full Profile:

1. Open a command line and navigate to the root of the JBoss EAP directory.
2. The following shows the command line to start the JBoss EAP server with the full profile:

        For Linux:   EAP_HOME/bin/standalone.sh -c standalone-full.xml
        For Windows: EAP_HOME\bin\standalone.bat -c standalone-full.xml

#### Start the JBoss EAP Server with Custom Configuration Options

To start JBoss EAP with custom configuration options:

1. Open a command line and navigate to the root of the JBoss EAP directory.
2. The following shows the command line to start the JBoss EAP server. Replace the CUSTOM_OPTIONS with the custom optional parameters specified in the quickstart.

        For Linux:   EAP_HOME/bin/standalone.sh CUSTOM_OPTIONS
        For Windows: EAP_HOME\bin\standalone.bat CUSTOM_OPTIONS
           
### Build and Deploy the Quickstarts

See the README file in each individual quickstart folder for specific details and information on how to run and access the example.

#### Build the Quickstart Archive

In some cases, you may want to build the application to test for compile errors or view the contents of the archive. 

1. Open a command line and navigate to the root directory of the quickstart you want to build.
2. Use this command if you only want to build the archive, but not deploy it:

            mvn clean package

#### Build and Deploy the Quickstart Archive

1. Make sure you [start the JBoss EAP server](#start-the-jboss-eap-server) as described in the README.
2. Open a command line and navigate to the root directory of the quickstart you want to run.
3. Use this command to build and deploy the archive:

            mvn clean package jboss-as:deploy

#### Undeploy an Archive

The command to undeploy the quickstart is simply: 

        mvn jboss-as:undeploy
 
### Verify the Quickstarts Build with One Command
-------------------------------------------------

You can verify the quickstarts build using one command. However, quickstarts that have complex dependencies must be skipped. For example, the _jax-rs-client_ quickstart is a RESTEasy client that depends on the deployment of the _helloworld-rs_ quickstart. As noted above, the root `pom.xml` file defines a `complex-dependencies` profile to exclude these quickstarts from the root build process. 

To build the quickstarts:

1. Do not start the JBoss EAP server.
2. Open a command line and navigate to the root directory of the quickstarts.
3. Use this command to build the quickstarts that do not have complex dependencies:

            mvn clean install

_Note_: If you see a `java.lang.OutOfMemoryError: PermGen space` error when you run this command, increase the memory by typing the following command for your operating system, then try the above command again.

        For Linux:   export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=128m"
        For Windows: SET MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=128m"


### Undeploy the Deployed Quickstarts with One Command
------------------------------------------------------

To undeploy the quickstarts from the root of the quickstart folder, you must pass the argument `-fae` (fail at end) on the command line. This allows the command to continue past quickstarts that fail due to complex dependencies and quickstarts that only have Arquillian tests and do not deploy archives to the server.

You can undeploy quickstarts using the following procedure:

1. Start the JBoss EAP server.
2. Open a command line and navigate to the root directory of the quickstarts.
3. Use this command to undeploy any deployed quickstarts:

            mvn jboss-as:undeploy -fae

To undeploy any quickstarts that fail due to complex dependencies, follow the undeploy procedure described in the quickstart's README file.


### Run the Arquillian Tests
----------------------------

Some of the quickstarts provide Arquillian tests. By default, these tests are configured to be skipped, as Arquillian tests an application on a real server, not just in a mocked environment.

You can either start the server yourself or let Arquillian manage its lifecycle during the testing. The individual quickstart README should tell you what to expect in the console output and the server log when you run the test.


1. Test the quickstart on a remote server
    * Arquillian's remote container adapter expects a JBoss EAP server instance to be already started prior to the test execution. You must [Start the JBoss EAP server](#start-the-jboss-eap-server) as described in the quickstart README file.
    * If you need to run the tests on a JBoss EAP server running on a machine other than localhost, you can configure this, along with other options, in the `src/test/resources/arquillian.xml` file using the following properties:
        
            <container qualifier="jboss" default="true">
                <configuration>
                    <property name="managementAddress">myhost.example.com</property>
                    <property name="managementPort">9999</property>
                    <property name="username">customAdminUser</property>
                    <property name="password">myPassword</property>
                </configuration>
            </container>    
    * Run the test goal with the following profile activated:

            mvn clean test -Parq-jbossas-remote     
2. Test the quickstart on a managed server

    Arquillian's managed container adapter requires that your server is not running as it will start the container for you. However, you must first let it know where to find the JBoss EAP directory. The simplest way to do this is to set the `JBOSS_HOME` environment variable to the full path to your JBoss EAP directory. Alternatively, you can set the path in the `jbossHome` property in the Arquillian configuration file.
    * Open the `src/test/resources/arquillian.xml` file located in the quickstart directory.
    * Find the configuration for the JBoss container. It should look like this:

            <!-- Example configuration for a managed/remote JBoss EAP instance -->
            <container qualifier="jboss" default="true">
                <!-- If you want to use the JBOSS_HOME environment variable, just delete the jbossHome property -->
                <!--<configuration> -->
                <!--<property name="jbossHome">/path/to/jboss/as</property> -->
                <!--</configuration> -->
            </container>           
    * Uncomment the `configuration` element, find the `jbossHome` property and replace the "/path/to/jboss/as" value with the actual path to your JBoss EAP server.
    * Run the test goal with the following profile activated:

            mvn clean test -Parq-jbossas-managed


Use JBoss Developer Studio or Eclipse to Run the Quickstarts
------------------------------------------------------------

You can also deploy the quickstarts from Eclipse using JBoss tools. For more information on how to set up Maven and the JBoss tools, refer to the [JBoss Enterprise Application Platform Development Guide](https://access.redhat.com/site/documentation/JBoss_Enterprise_Application_Platform/) or [Get Started Developing Applications](http://www.jboss.org/jdf/quickstarts/jboss-as-quickstart/guide/Introduction/ "Get Started Developing Applications").


Build and Deploy the Quickstart - to OpenShift
-------------------------

_Note:_ The following variables are used in these instructions. Be sure to replace them as follows:
 
* QUICKSTART_NAME should be replaced with your quickstart name, for example:  my-quickstart
* YOUR_DOMAIN_NAME should be replaced with your OpenShift account user name.
* APPLICATION_NAME should be replaced with a variation of the quickstart name, for example: myquickstart
* APPLICATION_UUID should be replaced with the UUID generated by OpenShift for your application, for example: 52864af85973ca430200006f
 
### Create an OpenShift Account and Domain

If you do not yet have an OpenShift account and domain, [Sign in to OpenShift](https://openshift.redhat.com/app/login) to create the account and domain. 

[Get Started with OpenShift](https://openshift.redhat.com/app/getting_started) details how to install the OpenShift Express command line interface.

### Create the OpenShift Application

Open a shell command prompt and change to a directory of your choice. Enter the following command to create a JBoss EAP 6 application:

    rhc app create -a APPLICATION_NAME -t jbosseap-6

_NOTE_: The domain name for this application will be APPLICATION_NAME-YOUR_DOMAIN_NAME.rhcloud.com`. Be sure to replace the variables as noted above.

This command creates an OpenShift application named APPLICATION_NAME and will run the application inside the `jbosseap-6`  container. You should see some output similar to the following:

    Application Options
    -------------------
      Namespace:  YOUR_DOMAIN_NAME
      Cartridges: jbosseap-6 (addtl. costs may apply)
      Gear Size:  default
      Scaling:    no

    Creating application 'APPLICATION_NAME' ... done

    Waiting for your DNS name to be available ... done

    Cloning into 'APPLICATION_NAME'...
    Warning: Permanently added the RSA host key for IP address '54.237.58.0' to the list of known hosts.

    Your application 'APPLICATION_NAME' is now available.

      URL:        http://APPLICATION_NAME-YOUR_DOMAIN_NAME.rhcloud.com/
      SSH to:     APPLICATION_UUID@APPLICATION_NAME-YOUR_DOMAIN_NAME.rhcloud.com
      Git remote: ssh://APPLICATION_UUID@APPLICATION_NAME-YOUR_DOMAIN_NAME.rhcloud.com/~/git/APPLICATION_NAME.git/
      Cloned to:  CURRENT_DIRECTORY/APPLICATION_NAME

    Run 'rhc show-app APPLICATION_NAME' for more details about your app.

The create command creates a git repository in the current directory with the same name as the application. 

Notice that the output also reports the URL at which the application can be accessed. Make sure it is available by typing the published url into a browser or use command line tools such as curl or wget. Be sure to replace `APPLICATION_NAME` and `YOUR_DOMAIN_NAME` with your OpenShift application and account domain name.


### Migrate the Quickstart Source

Now that you have confirmed it is working you can migrate the quickstart source. You do not need the generated default application, so navigate to the new git repository directory created by the OpenShift command and tell git to remove the source and pom files:

    cd APPLICATION_NAME
    git rm -r src pom.xml

Copy the source for the QUICKSTART_NAME quickstart into this new git repository:

    cp -r QUICKSTART_HOME/QUICKSTART_NAME/src .
    cp QUICKSTART_HOME/QUICKSTART_NAME/pom.xml .

### Configure the OpenShift Server

See individual quickstart README file for any specific server configuration requiremens.

### Deploy the OpenShift Application

You can now deploy the changes to your OpenShift application using git as follows:

    git add src pom.xml
    git commit -m "QUICKSTART_NAME quickstart on OpenShift"
    git push

The final push command triggers the OpenShift infrastructure to build and deploy the changes. 

Note that the `openshift` profile in `pom.xml` is activated by OpenShift, and causes the WAR build by OpenShift to be copied to the `deployments/` directory, and deployed without a context path.

### Test the OpenShift Application

When the push command returns, you can test the application by getting the following URL either via a browser or using tools such as curl or wget. Be sure to replace the `APPLICATION_NAME` and `YOUR_DOMAIN_NAME` variables in the URL with your OpenShift application and account domain name.

* <http://APPLICATION_NAME-YOUR_DOMAIN_NAME.rhcloud.com/> 

You can use the OpenShift command line tools or the OpenShift web console to discover and control the application.

### Delete the OpenShift Application

When you are finished with the application you can delete it as follows:

    rhc app-delete -a APPLICATION_NAME

_Note_: There is a limit to the number of applications you can deploy concurrently to OpenShift. If the `rhc app create` command returns an error indicating you have reached that limit, you must delete an existing application before you continue. 

* To view the list of your OpenShift applications, type: `rhc domain show`
* To delete an application from OpenShift, type the following, substituting the application name you want to delete: `rhc app-delete -a APPLICATION_NAME_TO_DELETE`


Optional Components
-------------------
The following components are needed for only a small subset of the quickstarts. Do not install or configure them unless the quickstart requires it.

* [Add a User](#add-a-management-or-application-user): Add a Management or Application user for the quickstarts that run in a secured mode.

### Add a Management or Application User

By default, JBoss EAP is now distributed with security enabled for the management interfaces. 
A few of the quickstarts use these management interfaces and require that you create a management or application user to access the running application. 
An `add-user` script is provided in the `EAP_HOME/bin` directory for that purpose. 
You can run the script interactively or you can pass arguments on the command line.

The following procedures describe how to add a user with the appropriate permissions to run the quickstarts that depend on them.

#### Add a Management User

You can choose to run the script interactively or you can pass arguments on the command line.

##### Add a Management User Interactively
1. Open a command line.
2. Type the command for your operating system

        For Linux:   EAP_HOME/bin/add-user.sh
        For Windows: EAP_HOME\bin\add-user.bat
3. You should see the following response:

        What type of user do you wish to add? 

        a) Management User (mgmt-users.properties) 
        b) Application User (application-users.properties)
        (a):

    At the prompt, press enter to use the default Management User.
4. You should see the following response:

        Enter the details of the new user to add.
        Using realm 'ManagementRealm' as discovered from the existing property files.
        Username :
5. Enter the Username and, at the next prompt, enter the Password.
 
        Username : admin
        Password : (choose a password for the admin user)
    Repeat the password.
6. At the next prompt, you will be asked "What groups do you want this user to belong to? (Please enter a comma separated list, or leave blank for none)[ ]: ". Leave it blank and press enter.
7. Choose yes for the remaining prompts.

##### Add a Management User Passing Arguments on the Command Line

If you prefer, you can create the management user non-interactively by passing each argument on the command line. 

For example, to add the Management User `admin` in the default `ManagementRealm` realm with password `adminPass1!`, type the following:

        For Linux:   EAP_HOME/bin/add-user.sh -u 'admin' -p 'adminPass1!'
        For Windows: EAP_HOME\bin\add-user.bat -u 'admin' -p 'adminPass1!'

#### Add an Application User

You can choose to run the script interactively or you can pass arguments on the command line.

##### Add an Application User Interactively 

1. Open a command line
2. Type the command for your operating system

        For Linux:   EAP_HOME/bin/add-user.sh
        For Windows: EAP_HOME\bin\add-user.bat
3. You should see the following response:

        What type of user do you wish to add? 

        a) Management User (mgmt-users.properties) 
        b) Application User (application-users.properties)
        (a):

    At the prompt, type:  `b`
4. You should see the following response:

        Enter the details of the new user to add.
        Using realm 'ApplicationRealm' as discovered from the existing property files.
        Username :

5. Enter the the Username and at the next prompt, enter the Password. If the quickstart README specifies a Username and Password, enter them here. Otherwise, use the default Username `quickstartUser` and Password `quickstartPwd1!`.
 
        Username : quickstartUser
        Password : quickstartPwd1!
6. At the next prompt, you will be asked "What groups do you want this user to belong to? (Please enter a comma separated list, or leave blank for none)[  ]: ". If the quickstart README specifies the groups to use, enter that here. Otherwise, type the group: `guest`

#### Add an Application User Passing Arguments on the Command Line

If you prefer, you can create the application user non-interactively by passing each argument on the command line. 

For example, to add the Application User `quickstartUser` in the `ApplicationRealm` realm with password `quickstartPwd1!` in group `guest`, type the following:

        For Linux:   EAP_HOME/bin/add-user.sh -a -u 'quickstartUser' -p 'quickstartPwd1!' -g 'guest'
        For Windows: EAP_HOME\bin\add-user.bat  -a -u 'quickstartUser' -p 'quickstartPwd1!' -g 'guest'


