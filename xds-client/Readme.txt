====
    Copyright Ekagra Software Technologies Ltd.
    Copyright SAIC, Inc
    Copyright 5AM Solutions
    Copyright SemanticBits Technologies

    Distributed under the OSI-approved BSD 3-Clause License.
    See http://ncip.github.com/cacis/LICENSE.txt for details.
====

###########
Resources:
############

OHF XDS Document Source.pdf API document with code samples.
https://www.projects.openhealthtools.org/integration/viewvc/viewvc.cgi/trunk/ihe/org.openhealthtools.ihe.xds.source/resources/doc/?root=iheprofiles&system=exsy1002

Testcase sample XDSb_IBMLswin10SubmitTest.java
https://www.projects.openhealthtools.org/integration/viewvc/viewvc.cgi/trunk/ihe/org.openhealthtools.ihe.xds.source/src_tests/org/openhealthtools/ihe/xds/source/test/?root=iheprofiles&system=exsy1002

OHT IHE Profiles Release 1.2.0 (org.openhealthtools.ihe_1.2.0.rc1.zip)
https://www.projects.openhealthtools.org/sf/frs/do/viewRelease/projects.iheprofiles/frs.incremental_builds.rc1

###########
Notes:
###########

1: After openxds-1.2RC2 is deployed into JBOSS perform the following.
	jboss-4.2.2.GA/server/default/deploy/openxds-web.war/WEB-INF/classes/conf/actors folder contains a few xml files.
	
	Change port to 8080 from 8010 
	Change UrlPath to have /openxds-web/services/<......> accordingly.
	Here are the files which might need change
	XcaInitiatingGatewayConnections.xml
	XcaRespondingGatewayConnections.xml
	XdsRepositoryConnections.xml
	
	
2:  Turn off patient id validation. 
	
	jboss-4.2.2.GA/server/default/deploy/openxds-web.war/WEB-INF/classes folder contains openxds.properties file
	look for validate.patient.id=true change it to validate.patient.id=false
	
3:	Restart Jboss. 	