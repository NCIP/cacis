channel stop *
channel undeploy *

channel remove SA-1_SourceSystemInterfacing
channel remove SA-2_XSLT_Transformation
channel remove SA-3_IdentifierAndVocabResolver
channel remove SA-4_InvokeIPCMPChannel 


import "${build.outputDirectory}/channels/SA-1_SourceSystemInterfacing.xml" force
import "${build.outputDirectory}/channels/SA-2_XSLT_Transformation.xml" force
import "${build.outputDirectory}/channels/SA-3_IdentifierAndVocabResolver.xml" force
import "${build.outputDirectory}/channels/SA-4_InvokeIPCMPChannel.xml" force


deploy
channel start *
channel list