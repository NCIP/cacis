========================================================================
  Introduction
========================================================================

This is a command line client to invoke the Semantic Adapter web
service.

The client has the following folder structure

 -lib (required library files)
 -samples (Sample request files)

========================================================================
Instructions
========================================================================

To run the client

$ java -cp classes:lib/* -Dcacis-pco.sa.service.url=<sa.service.url>
   gov.nih.nci.cacis.sa.client.SemanticAdapterClient <request_file>

Replace <sa.service.url> with the URL to the Semantic Adapter web
service


========================================================================
  Sample File
========================================================================

There is a sample file in samples that can be used as follows


$ java -cp classes:lib/*
  -Dcacis-pco.sa.service.url=http://localhost:8090/SemanticAdapter
  gov.nih.nci.cacis.sa.client.SemanticAdapterClient
  samples/sample_request.xml