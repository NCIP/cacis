channel stop *
channel undeploy *

channel remove SA-1_SourceSystemInterfacing
channel remove SA-1a_validationchannel.xml
channel remove SA-2_XSLT_Transformation
channel remove SA-3_IdentifierAndVocabResolver
channel remove SA-4_InvokeIPCMPChannel 

importcodetemplates "${build.outputDirectory}/templates/cacis-code-templates.xml" force

import "${build.outputDirectory}/channels/SA-1_SourceSystemInterfacing.xml" force
import "${build.outputDirectory}/channels/SA-1a_validationchannel.xml" force
import "${build.outputDirectory}/channels/SA-2_XSLT_Transformation.xml" force
import "${build.outputDirectory}/channels/SA-3_IdentifierAndVocabResolver.xml" force
import "${build.outputDirectory}/channels/SA-4_InvokeIPCMPChannel.xml" force


deploy
channel start *
channel list