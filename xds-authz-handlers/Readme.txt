Security Handler Setup
#######################

1: Modify  openxds-web.war/WEB-INF/conf/axis2.xml add the below two handlers in InFlow phaseOrder
    	<phase name="Security">
			<handler name="CacisXdsDocumentAuthzOutHandler" class="gov.nih.nci.cacis.xds.auth.axis.CacisXdsDocumentAuthzOutHandler">
				<order phase="Security" />
			</handler>        
			<handler name="CacisXdsDocumentAuthzInHandler" class="gov.nih.nci.cacis.xds.auth.axis.CacisXdsWriteAuthzInHandler">
				<order phase="Security" />
			</handler>        			
        </phase>
        
2: On CI execute mvn clean install -Pcacis-ci

3: On a local machine execute mvn clean install & then copy xds-authz-handlers-1.0-SNAPSHOT.jar to openxds-web.war/WEB-INF/lib folder