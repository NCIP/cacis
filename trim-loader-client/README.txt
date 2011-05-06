
This project contains a Trim Loader client for loading Trims into the
tolven Database


Build Instructions
==========================================================

1) At the cacis-esd/pco directory run:
mvn clean install

Note: esd-commons-1.0.jar is required in the cacis-esd/esd-commons/target directory.  This
	jar will be created when running the pco build.

2) Run the Maven plugin from pco/trim-loader-client:
mvn gov.nih.nci.cacis:trim-loader-client:build-trim-loader

This will create a trim-loader.jar file in the pco/trim-loader-client/target directory

3) Unzip the trim-loader.jar to the same directory of trim-loader.jar, this can be in any directory (the target directory is fine) :

unzip trim-loader.jar

4) Run the Trim loader (This requires tolven jboss to be running and contain a tolven account):
java -jar trim-loader.jar <tolven-config-location> <tolven-user-id> <tolven-password> <tolven-account-id> <tolvenClient-config-file> <trim-file>

Example:
java -jar trim-loader.jar /bpickeral/Applications/tolven/tolven-config/ tolven tolven 1045 tolvenClient-default.properties cacis-provider.trim.xml

Note: The tolvenClient-default.properties cacis-provider.trim.xml are packaged with the trim-loader.jar and are located in the same directory as trim-loader.jar after step 3.




