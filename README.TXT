===================================================================================
caCIS Enterprise Services Development (ESD)
===================================================================================

Please refer to the team wiki for project information:
https://wiki.nci.nih.gov/display/caEHR

Integration Platform IDE Guide:
https://wiki.nci.nih.gov/display/caEHR/IPF+IDE+Configuration+Guide+and+Sample+Project

Directory Structure:

/pco                  - Source code artifacts
/docs                 - Project Documentation


===================================================================================
Pre-requisites
===================================================================================

To compile and run the unit tests you will need the following

- Oracle Java Runtime Environment version 1.6
- Apache Maven version 2.x

To install and use this software, the following software needs
to be installed first

- Virtuoso Database (http://virtuoso.openlinksw.com/)
  Please look at this ESD document
  (https://docs.google.com/a/semanticbits.com/document/d/
  1VFO4LuoWZWZuQWF3S21wBil67O3jLqL6twKcYQqpxHI/edit?hl=en_US&pli=1)

- PostgreSQL Database version version 8.x (recommended version 8.4)
  http://www.postgresql.org/

- Mirth Connect version 2.1
  (http://www.mirthcorp.com/community/mirth-connect)
  THis ESD document can help with installing and configuring Mirth
  Connect (https://wiki.nci.nih.gov/display/caEHR/Mirth+Connect+Configuration+Guide)


===================================================================================
JRE Installation
===================================================================================

If you dont have JDK/JRE installed, please download
JRE/JDK version 1.6 for your OS from here,

http://www.oracle.com/technetwork/java/javase/downloads/

After you have installed the JRE/JDK, please download the Java Cryptography Extension
http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html

and follow the installation instructions in the jce/README.txt of the zip file


===================================================================================
Build the Project
===================================================================================

There are several projects in the /pco directory.
To build the project, run the following in /pco:

 mvn clean install

This will run all of the maven sub-projects using the parent
pom.xml in /pco. To add a sub-project to the build, add the
project as a module in pco/pom.xml.

Note: This will also run all Unit and System tests and can take
some time to finish the build. To skip the tests from running, use

 "mvn clean install -DskipTests -Dskip.static.analysis=true -Dcobertura.skip=true"

===================================================================================
Build sub-projects  (Optional)
===================================================================================

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


===================================================================================
Deployment
===================================================================================

To install the caCIS components, do the following

- Copy profiles.xml.example to profiles.xml
- Edit profiles.xml and fill in all the properties for your local environment
- Start Virtuoso Database server
- Run "mvn deploy"
   or
   "mvn deploy -DskipTests -Dskip.static.analysis=true -Dcobertura.skip=true"

This will install all caCIS components in Mirth Connect. Once you start the
   Mirth Connect server, you will see caCIS channels in Mirth Connect



===================================================================================
Snapshots Repositories
===================================================================================

The caCIS artifacts are deployed to the following locations:

Snapshots:
https://ncimvn.nci.nih.gov/nexus/content/repositories/snapshots/

Releases:
https://ncimvn.nci.nih.gov/nexus/content/repositories/releases/


===================================================================================
Maven Site
===================================================================================

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



