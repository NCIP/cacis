channel stop *
channel undeploy *
channel remove *

import "${build.outputDirectory}/channels/SA-1_SourceSystemInterfacing.xml" force
import "${build.outputDirectory}/channels/SA-2_XSLT_Transformation.xml" force
import "${build.outputDirectory}/channels/SA-3_IdentifierAndVocabResolver.xml" force
import "${build.outputDirectory}/channels/SA-4_InvokeIPCMPChannel.xml" force

deploy
channel start *
channel list