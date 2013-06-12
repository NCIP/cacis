====
    Copyright 5AM Solutions Inc
    Copyright SemanticBits LLC
    Copyright AgileX Technologies, Inc
    Copyright Ekagra Software Technologies Ltd

    Distributed under the OSI-approved BSD 3-Clause License.
    See http://ncip.github.com/cacis/LICENSE.txt for details.
====

===================================================================================
Pre-requisites
===================================================================================

1: Install apache-tomcat-5.5.26 or higher.

2: Modify $CATALINA_HOME/conf/server.xml to have have https connector. Sample is https connector is provided below. 
   NOTE: Add the following connector (do not replace others).
   
	    <Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
               maxThreads="150" scheme="https" secure="true"
               clientAuth="true" sslProtocol="TLS" 
			   keystoreFile="${user.home}/.cacis/certs/cacis-keystore.jks"
               keystorePass="password"
               truststoreFile="${user.home}/.cacis/certs/cacis-truststore.jks"
               truststorePass="password"/>  

3: Follow OpenXDS Installation guide below to install openxds on Tomcat
	https://wiki.nci.nih.gov/display/caEHR/OpenXDS+Installation
	
===================================================================================
caCIS-XDS-Proxy deployment
===================================================================================

1: In profiles.xml or pco/pom.xml make sure the below properties have the correct values  
    <openxds.web.dir>${CATALINA_HOME}/webapps/openxds-web</openxds.web.dir>
    <openxds.http.base.url>localhost:8443/openxds-web</openxds.http.base.url>
	<cacis.xds.proxy.repository.url>https://${openxds.http.base.url}/services/cacis-proxy-xdsrepositoryb</cacis.xds.proxy.repository.url>
	<xds.repository.url>http://localhost:8020/axis2/services/xdsrepositoryb</xds.repository.url>
    <xds.registry.url>http://localhost:8010/axis2/services/xdsregistryb</xds.registry.url>
	<userhome.dir>${user.home}</userhome.dir>

2: Stop Tomcat

3: From gov.nih.nci.cacis.pco-1.0 folder execute "mvn clean deploy" (Make sure to start Virtuoso & Mirth Connect before you execute this goal)
	
4: Start Tomcat. Once Tomcat is up, start the actors as described in "Start up the OpenXDS servers" section of 
	https://wiki.nci.nih.gov/display/caEHR/OpenXDS+Installation.
	
5: Verify that you can reach 
	https://localhost:8443/opexds-web/services/cacis-proxy-xdsrepositoryb?wsdl
	http://localhost:8020/axis2/services/xdsrepositoryb	
	http://localhost:8010/axis2/services/xdsregistryb
				 