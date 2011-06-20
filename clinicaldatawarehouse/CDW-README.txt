============================================================================
Installing caGrid-1.4 & Installing/Configuring secure Tomcat container
============================================================================
1: Complete Steps 1 through 5 outlined at http://www.cagrid.org/display/downloads/caGrid+1.4+Installation+Quick+Start. 

Note :: 
1:	Training grid to be used as trust fabric
2:	Make sure to set the environment variables for ANT_HOME, GLOBUS_LOCATION & CATALINA_HOME

============================================================================
Build & deploy ClinicalDataWarehouse grid service
============================================================================
1: Change directory to pco/clinicaldatawarehouse & execute "mvn clean install"

Service should be up & running at 
https://localhost:8443/wsrf/services/cagrid/ClinicalDataWarehouse

To see the WSDL hit 
https://localhost:8443/wsrf/services/cagrid/ClinicalDataWarehouse?wsdl