caCIS Enterprise Services Development (ESD) 
====================================================
Please refer to the team wiki for project information:
https://wiki.nci.nih.gov/display/caEHR

Integration Platform IDE Guide:
https://wiki.nci.nih.gov/display/caEHR/IPF+IDE+Configuration+Guide+and+Sample+Project

Directory Structure:

/pco                  - Source code artifacts
/docs                 - Project Documentation

====================================================
Build the Project
====================================================

There are several projects in the /pco directory.
To build the project, run the following in /pco:

mvn clean install

This will run all of the maven sub-projects using the parent
pom.xml in /pco. To add a sub-project to the build, add the
project as a module in pco/pom.xml.

====================================================
Build sub-projects
====================================================

Several sub-projects exist in the /pco directory.

To configure and build individual sub-projects
look at the instructions of the sub-projects

For eg. The configuration and build instructions
for the Semantic Adapter are in
/pco/sa/ReadMe.txt


sub-projects have their own Maven pom file to build 
and run tests/static analysis on the individual projects.

====================================================
Repositories
====================================================
The maven jar files are deployed to the following locations:

Snapshots:
https://ncimvn.nci.nih.gov/nexus/content/repositories/snapshots/

Releases:
https://ncimvn.nci.nih.gov/nexus/content/repositories/releases/
