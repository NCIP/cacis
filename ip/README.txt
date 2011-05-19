
Project created using IPF Maven Archtype as follows

mvn org.apache.maven.plugins:maven-archetype-plugin:2.0:generate
 -DarchetypeGroupId=org.openehealth.ipf.archetypes
 -DarchetypeArtifactId=ipf-archetype-basic
 -DarchetypeVersion=2.2.3 -DgroupId=gov.nih.nci.cacis
 -DartifactId=ip -Dversion=1.0-SNAPSHOT -DinteractiveMode=false

Configuring local properties
================================================================
Copy profiles.xml.example to profiles.xml and change the properties as needed.  profiles.xml will overwrite properties in the pom file.

Build
================================================================
1) From pco/sa, run:
 	mvn clean install

2) From pco/ip, run:
 	mvn clean install

The war file (cacis-ip.war) should be deployed to the "deploy" directory of the jboss server.

3) Start JBoss
	./startTolvenJBoss.sh

Test
================================================================
The following web service listing will be available if configured correctly (change port if needed):
http://localhost:8080/cacis-ip/ws/

The Share Clinical Data wsdl:
http://localhost:8080/cacis-ip/ws/sa/shareClinicalData?wsdl