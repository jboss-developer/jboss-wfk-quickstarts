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

* [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts): How to configure the Maven repository for use by the quickstarts.

* [Run the Quickstarts](#run-the-quickstarts): General instructions for building, deploying, and running the quickstarts.

* [Run the Arquillian Tests](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/RUN_ARQUILLIAN_TESTS.md#run-the-arquillian-tests): How to run the Arquillian tests provided by some of the quickstarts.

* [Build and Deploy the Quickstart - to OpenShift](#build-and-deploy-the-quickstart-to-openshift): Deploy the application to OpenShift

* [Optional Components](#optional-components): How to install and configure optional components required by some of the quickstarts.

* [Contributing Guide](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONTRIBUTING.md#jboss-developer-contributing-guide): This document contains information targeted for developers who want to contribute to JBoss developer projects.

Use of EAP_HOME and JBOSS_HOME Variables
---------------------------------

The quickstart README files use the *replaceable* value `EAP_HOME` to denote the path to the JBoss EAP 6 installation. When you encounter this value in a README file, be sure to replace it with the actual path to your JBoss EAP 6 installation. 

* If you installed JBoss EAP using the ZIP, the install directory is the path you specified when you ran the command.

* If you installed JBoss EAP using the RPM, the install directory is `/var/lib/jbossas/`.

* If you used the installer to install JBoss EAP, the default path for `EAP_HOME` is `${user.home}/EAP-6.4.0`. 

        For Linux: /home/USER_NAME/EAP-6.4.0/
        For Windows: "C:\Users\USER_NAME\EAP-6.4.0\"

* If you used the JBoss Developer Studio installer to install and configure the JBoss EAP Server, the default path for `EAP_HOME` is `${user.home}/jbdevstudio/runtimes/jboss-eap`.

        For Linux: /home/USER_NAME/jbdevstudio/runtimes/jboss-eap/
        For Windows: "C:\Users\USER_NAME\jbdevstudio\runtimes\jboss-eap" or "C:\Documents and Settings\USER_NAME\jbdevstudio\runtimes\jboss-eap\" 

The `JBOSS_HOME` *environment* variable, which is used in scripts, continues to work as it has in the past.

Available Quickstarts
---------------------

All available quickstarts can be found here: <http://site-jdf.rhcloud.com/quickstarts/get-started/>. You can filter by the quickstart name, the product, and the technologies demonstrated by the quickstart. You can also limit the results based on skill level and date published. The resulting page provides a brief description of each matching quickstart, the skill level, and the technologies used. Click on the quickstart to see more detailed information about how to run it. Some quickstarts require deployment of other quickstarts. This information is noted in the `Prerequisites` section of the quickstart README file.

Some quickstarts are designed to enhance or extend other quickstarts. These are noted in the **Prerequisites** column. If a quickstart lists prerequisites, those must be installed or deployed before working with the quickstart.

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

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform (EAP) 6.1 or later with the  Red Hat JBoss Web Framework Kit (WFK) 2.7.

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


Run the Quickstarts
-------------------

The root folder of each individual quickstart contains a README file with specific details on how to build and run the example. In most cases you do the following:

* [Start the JBoss EAP Server](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/START_JBOSS_EAP.md#start-the-jboss-eap-server)
* [Build and deploy the quickstarts](#build-and-deploy-the-quickstarts)


### Build and Deploy the Quickstarts

See the README file in each individual quickstart folder for specific details and information on how to run and access the example.

_Note:_ If you do not configure the Maven settings as described here, [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts), you must pass the configuration setting on every Maven command as follows: ` -s QUICKSTART_HOME/settings.xml`


#### Build the Quickstart Archive

In most cases, you can use the following steps to build the application to test for compile errors or to view the contents of the archive. See the specific quickstart README file for complete details.

1. Open a command prompt and navigate to the root directory of the quickstart you want to build.
2. Use this command if you only want to build the archive, but not deploy it:
   * If you have configured the Maven settings :

            mvn clean install
   * If you have NOT configured settings Maven settings:

            mvn clean install -s QUICKSTART_HOME/settings.xml

#### Build and Deploy the Quickstart Archive

In most cases, you can use the following steps to build and deploy the application. See the specific quickstart README file for complete details.

1. Make sure you start the JBoss EAP server as described in the quickstart README file.
2. Open a command prompt and navigate to the root directory of the quickstart you want to run.
3. Use this command to build and deploy the archive:

   * If you have configured the Maven settings :

            mvn clean install jboss-as:deploy
   * If you have NOT configured the Maven settings :

            mvn clean install jboss-as:deploy -s QUICKSTART_HOME/settings.xml

#### Undeploy an Archive

The command to undeploy the quickstart is simply: 

        mvn jboss-as:undeploy
 
### Verify the Quickstarts Build with One Command
-------------------------------------------------

You can verify the quickstarts build using one command. However, quickstarts that have complex dependencies must be skipped. For example, the _jax-rs-client_ quickstart is a RESTEasy client that depends on the deployment of the _helloworld-rs_ quickstart. As noted above, the root `pom.xml` file defines a `complex-dependencies` profile to exclude these quickstarts from the root build process. 

To build the quickstarts:

1. Do not start the JBoss EAP server.
2. Open a command prompt and navigate to the root directory of the quickstarts.
3. Use this command to build the quickstarts that do not have complex dependencies:

   * If you have configured the Maven settings :

            mvn clean install '-Pdefault,!complex-dependencies'

   * If you have NOT configured the Maven settings :

            mvn clean install '-Pdefault,!complex-dependencies' -s QUICKSTART_HOME/settings.xml

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


Use JBoss Developer Studio or Eclipse to Run the Quickstarts
------------------------------------------------------------

You can also deploy the quickstarts from Eclipse using JBoss tools. For more information on how to set up Maven and the JBoss tools, see the [Red Hat JBoss Enterprise Application Platform Documentation](https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/) _Getting Started Guide_ and _Development Guide_ or [Get Started with JBoss Developer Studio](http://www.jboss.org/products/devstudio/get-started/ "Get Started with JBoss Developer Studio").


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

* [Create Users Required by the Quickstarts](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CREATE_USERS.md#create-users-required-by-the-quickstarts): Add a Management or Application user for the quickstarts that run in a secured mode.


