kitchensink-cordova: Example Mobile Applications Using Hybrid HTML5 + REST with Apache Cordova
===============================================================================================
Author: Kris Borchers
Level: Intermediate
Technologies: HTML5, REST, Apache Cordova
Summary: Based on kitchensink, but uses hybrid HTML5 running as a native application on mobiles
Target Product: WFK
Product Versions: EAP 6.1, EAP 6.2, WFK 2.4
Source: <https://github.com/jboss-developer/jboss-wfk-quickstarts/>

What is it?
-----------

This project serves as an example of the HTML5 [kitchensink](https://github.com/jboss-developer/jboss-wfk-quickstarts/tree/master/kitchensink-html5-mobile) quickstart converted to a hybrid [Apache Cordova](http://cordova.apache.org/) application.

What does this mean? Basically, this takes our [HTML5 + REST](https://community.jboss.org/wiki/HTML5RESTApplications) / [jQuery Mobile](http://jquerymobile.com/) web application and converts it to a native application for both iOS and Android. 

These concepts can be applied to the conversion of most HTML5/JS based web apps by just replacing the specific paths described in this article with paths that match your environment. If you have an existing Web Application based on HTML5 + REST, use the following guide to convert it to a hybrid application with Apache Cordova: [Converting a Mobile HTML5 + REST Web App to a Hybrid App with Apache Cordova](http://aerogear.org/docs/guides/HTML5ToHybridWithCordova/)



Available Hybrid Applications
-----------------------------

The following is a list of instructions for all supported mobile platforms.

Click on the link below to see instructions to see instructions for each platform:

1. [Kitchensink Apache Cordova iOS](#kitchensink-apache-cordova-ios-example)
2. [Kitchensink Apache Cordova Android](#kitchensink-apache-cordova-android-example)



Kitchensink Apache Cordova iOS Example
-----------------------------------

### What is it?

This quickstart was built upon the HTML5 kitchensink quickstart, which was converted to an Apache Cordova based Hybrid Mobile application. Basically, this takes our HTML5 + REST / jQuery Mobile based web application and converts it to a native application for iOS.

### System requirements

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform (EAP) 6.1 or later with the Red Hat JBoss Web Framework Kit (WFK) 2.4.

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, the iOS SDK and JBDS 7 with [JBoss Hybrid Mobile Tools + CordovaSim](http://developer.android.com/tools/sdk/eclipse-adt.html) installed. The iOS SDK is available as part of Apple XCode. If you need more detailed instructions to setup JBDS 7 with JBoss Hybrid Mobile Tools + CordovaSim, you can take a look at the user guide to [Install Hybrid Mobile Tools and CordovaSim](https://access.redhat.com/site/documentation/en-US/Red_Hat_JBoss_Developer_Studio/7.1/html/User_Guide/Install_Hybrid_Mobile_Tools_and_CordovaSim.html).


### Import the Quickstart Code

First we need to import the existing code to JBDS

1. In JBDS, click on `File`, then `Import`.
2. Select `Existing Projects into Workspace` and click `Next`.
3. In `Root Directory`, click on the `Browse...` button, then find and select `QUICKSTART_HOME/kitchensink-cordova/`. Be sure to replace `QUICKSTART_HOME` with the root directory of your quickstarts.
4. Click the `Finish` button to start the project import.


### Deploy and Access the application 

1. Select your project in JBDS.
2. Click on `Run`, then `Run As` and `Run on iOS Simulator`.

The iOS simulator will started, and the application will be deployed and run on it.



Kitchensink Apache Cordova Android Example
-----------------------------------


### What is it?

This application is built upon the HTML5 kitchensink quickstart, which was converted to an Apache Cordova based Hybrid Mobile application. Basically, this takes our HTML5 + REST / jQuery Mobile based web application and converts it to a native application for Android. 

### System requirements

All you need to build this project is Java 6.0 (Java SDK 1.6) or later, the Android SDK and JBDS 7 with [JBoss Hybrid Mobile Tools + CordovaSim](http://developer.android.com/tools/sdk/eclipse-adt.html) and the Android Development Tools plugin from Google. If you need more detailed instructions to setup JBDS 7 with JBoss Hybrid Mobile Tools + CordovaSim, you can take a look at the user guide to [Install Hybrid Mobile Tools and CordovaSim](https://access.redhat.com/site/documentation/en-US/Red_Hat_JBoss_Developer_Studio/7.1/html/User_Guide/Install_Hybrid_Mobile_Tools_and_CordovaSim.html).

### Import the Quickstart Code

First we need to import the existing code to JBDS

1. In JBDS, click on `File`, then `Import`.
2. Select `Existing Projects into Workspace` and click `Next`.
3. In `Root Directory`, click on the `Browse...` button, then find and select `QUICKSTART_HOME/kitchensink-cordova/`. Be sure to replace `QUICKSTART_HOME` with the root directory of your quickstarts.
4. Click the `Finish` button to start the project import.


### Deploy and Access the application 

1. Select your project in JBDS.
2. Click on `Run`, then `Run As` and `Run on Android Emulator`.

The Android emulator will started, and the application will be deployed and run on it.

