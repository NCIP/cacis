This project is the pcoplugin that will be used to enable storage for the
sample PCO Trim

Build and Deploy Instructions
==========================================================

Copy example.local.properties to local.properties to override local properties including tolven locations.

Use the following to build the current basic build for the plugin:

1) Run the following from the pco directory:
mvn clean install

2) Run the following from pco/pcoplugin:
mvn gov.nih.nci.cacis:pcoplugin:build-pcoplugin