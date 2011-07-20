caCIS Enterprise Services Development (ESD) 
==================================================================
Please refer to the team wiki for project information:
https://wiki.nci.nih.gov/display/caEHR

Integration Platform IDE Guide:
https://wiki.nci.nih.gov/display/caEHR/IPF+IDE+Configuration+Guide+and+Sample+Project

Directory Structure:

/pco                  - Source code artifacts
/docs                 - Project Documentation

==================================================================
JDK Installation
==================================================================
If you dont have JDK installed, please download the correct JDK for your OS from here,
http://www.oracle.com/technetwork/java/javase/downloads/jdk-6u26-download-400750.html

After you have installed the JDK, please download the Java Cryptography Extension
http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html

and follow the installation instructions in the jce/README.txt of the zip file
==================================================================
Build the Project
==================================================================

There are several projects in the /pco directory.
To build the project, run the following in /pco:

mvn clean install

This will run all of the maven sub-projects using the parent
pom.xml in /pco. To add a sub-project to the build, add the
project as a module in pco/pom.xml.

==================================================================
Build sub-projects
==================================================================

Several sub-projects exist in the /pco directory.

To configure and build individual sub-projects
look at the instructions of the sub-projects

For eg. The configuration and build instructions
for the Integration Platform are in
/pco/ip/ReadMe.txt

Note - sub-projects have their own Maven pom file to build
and run tests/static analysis on the individual projects.

Note - Not all projects will need to be built directly.
That is they don't produce any deployable artifacts, and
only provide dependencies for other sub-projects


==================================================================
Repositories
==================================================================
The maven jar files are deployed to the following locations:

Snapshots:
https://ncimvn.nci.nih.gov/nexus/content/repositories/snapshots/

Releases:
https://ncimvn.nci.nih.gov/nexus/content/repositories/releases/

==================================================================
Maven Site
==================================================================
To generate and deploy a Maven Site execute

 mvn site

Since this is multi-module project, the site artifacts are
generated in the target/site folder of each module. These
might not link to each other (and the parent) correctly.

If you wish to generate a site which has links between
 modules correctly run the following

mvn site:stage

This will generate a site in the target/stage folder with
modules correctly linking to each other

*Note- Need to run mvn site first before running site:stage


If you would like to deploy the Maven site to an external
server (SCP) or directory run the following

mvn -Dmaven.site.url=file:///${user.home}/cacis-site site-deploy

This will generate the maven site in ${user.home}/cacis-site

For more information see
http://maven.apache.org/plugins/maven-site-plugin/usage.html



