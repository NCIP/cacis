====
    Copyright Ekagra Software Technologies Ltd.
    Copyright SAIC, Inc
    Copyright 5AM Solutions
    Copyright SemanticBits Technologies

    Distributed under the OSI-approved BSD 3-Clause License.
    See http://ncip.github.com/cacis/LICENSE.txt for details.
====

===================================================================================
caCIS
===================================================================================

Please refer to the team wiki for project information:
https://wiki.nci.nih.gov/display/caEHR


===================================================================================
Build and Installation instructions
===================================================================================

See https://wiki.nci.nih.gov/display/caEHR/caCIS+Deployment+Guide
for instructions on building and installing caCIS Integration Solution

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
