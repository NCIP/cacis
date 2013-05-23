====
    Copyright Ekagra Software Technologies Ltd.
    Copyright SAIC, Inc
    Copyright 5AM Solutions
    Copyright SemanticBits Technologies

    Distributed under the OSI-approved BSD 3-Clause License.
    See http://ncip.github.com/cacis/LICENSE.txt for details.
====


This project contains a custom web service implementation for
the Mirth Connect Web Service Listener.


System Requirements
==========================================================
Java JDK 1.6
Maven 2.x+

 Build Instructions
==========================================================

Run 'mvn clean install' from /pco

Deployment to Mirth Connect
==========================================================
Run "mvn deploy" from /pco

 Test Instructions
==========================================================
To run the unit tests run 'mvn test'


Manually deploying channels
==========================================================
1. Copy target/ip-mirth-1.0-SNAPSHOT.jar to Mirth Connect/custom-lib folder

2. Startup Mirth

3. Import the channels listed in src/main/resources/deploy.mc into Mirth Connect (using the Mirth Connect web
start application)in the same order the channels are listed in the file. It is necessary to deploy mock channels
in order to run integration tests.  The channels copied to the target directory listed (target/classes/channels) will be
filtered with properties defined in the pom file (you can override these in profiles.xml).

4. Once the channel is deployed, the AcceptCanonicalService
   should be available at

   http://localhost:18081/services/AcceptCanonicalService?wsdl