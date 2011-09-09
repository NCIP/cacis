channel stop *
channel undeploy *
channel remove *

import "${build.outputDirectory}/channels/ftpAddressChannel.xml"
import "${build.outputDirectory}/channels/navEmailAddressChannel.xml"
import "${build.outputDirectory}/channels/DocumentRouter.xml"
import "${build.outputDirectory}/channels/AcceptCanonical_Channel.xml"
import "${build.outputDirectory}/channels/DR-4_SecureEmailTransmission.xml"
import "${build.outputDirectory}/channels/DR-3_DocumentRouterMuxDemux.xml"
import "${build.outputDirectory}/channels/DR-2c_RIMITSTransformation.xml"
import "${build.outputDirectory}/channels/DR-2b_HL7v2Transformation.xml"
import "${build.outputDirectory}/channels/DR-2a_CDACCDTransformation.xml"
import "${build.outputDirectory}/channels/DR-1_RoutingInstructionValidation.xml"
import "${build.testOutputDirectory}/channels/MockRoutingInstructionSource.xml"
import "${build.outputDirectory}/channels/CMP-1_WebServiceListener.xml"  force
import "${build.outputDirectory}/channels/CMP-2a_CanonicalSchemaValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-2b_CanonicalSchematronValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-3_CanonicalValidationAggregation.xml"  force
import "${build.outputDirectory}/channels/CMP-4_CDWLoader.xml"  force

deploy
channel start *
channel list