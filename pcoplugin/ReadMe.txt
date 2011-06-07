This project is a tolven plugin that will be used to call the Semantic Adapter (not yet implemented).
Currently, pcoplugin contains a single rule to load a patient into the patient list when a patient Trim is loaded.

Build and Deploy Instructions
==========================================================

Copy example.local.properties to local.properties to override local properties including tolven locations.

Use the following to build the current basic build for the plugin:

1) Run the following from the pco directory:
mvn clean install

2) Run the following from pco/pcoplugin:
ant -f build-pcoplugin.build.xml deploy-to-local-repository

gov.nih.nci.cacis.tolven.pcoplugin-0.0.1.zip should now be in your local tolven repository.  You now need to deploy the tolven plugin.

Install Tolven Plugin
========================================================
Edit ${TOLVEN_INSTALL}/tolven-config/plugin.xml and add the
 following

<plugin id="gov.nih.nci.cacis.tolven.pcoplugin">
    <root/>
</plugin>

Run

${TOLVEN_INSTALL}/tolven-RC1/repositoryInit.sh

This command copies the Tolven plug-in from the local
repository to the runtime Tolven repository


Deploy Tolven Plugin
========================================================
1) Run

${TOLVEN_INSTALL}/tolven-RC1/bin/configPhase1.sh

This will rebuild the Tolven.ear and package this plug-in with it.

2) Start the tolven jboss:
${JBOSS_HOME}/bin/startTolvenJBoss.sh

Run

3) ${TOLVEN_INSTALL}/tolven-RC1/bin/configPhase3.sh

This will load all the metadata (rules, placeholders)
into Tolven.

Note: pcoplugin contains a single rule to load a patient into the patient list when a patient Trim is loaded.  This rule is a placeholder
for calling the semantic adapter once the semantic adapter is implemented.

Testing
========================================================
After deploying the pcoplugin you can then follow pco/trim-loader-client/ReadMe.txt to load a Trim with the trim loader client.  Once a Trim is loaded,
the pcoplugin will add the Patient to the patient list in tolven:

https://localhost:8443/Tolven

In the Patients list, the following patient should appear:
demo, pat5 ui
