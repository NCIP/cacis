channel stop *
channel undeploy *
channel remove *

importcodetemplates "${build.outputDirectory}/templates/dr-code-templates.xml" force

import "${build.outputDirectory}/channels/ftpAddressChannel.xml"
import "${build.outputDirectory}/channels/navEmailAddressChannel.xml"
import "${build.outputDirectory}/channels/DocumentRouter.xml"
import "${build.outputDirectory}/channels/AcceptCanonical_Channel.xml"
import "${build.outputDirectory}/channels/DR-4c_SecureFileTransfer.xml"  force
import "${build.outputDirectory}/channels/DR-4b_XDSNAVTransmission.xml"  force
import "${build.outputDirectory}/channels/DR-4a_SecureEmailTransmission.xml"  force
import "${build.outputDirectory}/channels/DR-3_DocumentRouterMuxDemux.xml"  force
import "${build.outputDirectory}/channels/DR-2c_RIMITSTransformation.xml"  force
import "${build.outputDirectory}/channels/DR-2b_HL7v2Transformation.xml"  force
import "${build.outputDirectory}/channels/DR-2a_CDACCDTransformation.xml"  force
import "${build.outputDirectory}/channels/DR-1_RoutingInstructionValidation.xml"  force
import "${build.testOutputDirectory}/channels/MockRoutingInstructionSource.xml"  force
import "${build.outputDirectory}/channels/CMP-1_WebServiceListener.xml"  force
import "${build.outputDirectory}/channels/CMP-2a_CanonicalSchemaValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-2b_CanonicalSchematronValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-3_CanonicalValidationAggregation.xml"  force
import "${build.outputDirectory}/channels/CMP-4_CDWLoader.xml"  force

deploy
channel start *
channel list