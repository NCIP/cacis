
This project contains a custom web service implementation for
the Mirth Connect Web Service Listener.


System Requirements
==========================================================
Java JDK 1.6
Maven 2.x+

 Build Instructions
==========================================================

Run 'mvn clean install'



 Test Instructions
==========================================================
To run the unit tests run 'mvn test'



Deployment to Mirth Connect
==========================================================

1. Copy target/ip-mirth-1.0-SNAPSHOT.jar to Mirth Connect/custom-lib folder

2. Startup Mirth

3. Import src/main/resources/channels/AcceptCanonical_Channel.xml
   into Mirth Connect (using the Mirth Connect web start application)

4. Once the channel is deployed, the AcceptCanonicalService
   should be available at

   http://localhost:18081/services/AcceptCanonicalService?wsdl


