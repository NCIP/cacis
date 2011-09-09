channel stop *
channel undeploy *

channel remove ftpAddressChannel
channel remove navEmailAddressChannel
channel remove DocumentRouter
channel remove AcceptCanonical_Channel
channel remove DR-4_SecureEmailTransmission
channel remove DR-3_DocumentRouterMuxDemux
channel remove DR-2c_RIMITSTransformation
channel remove DR-2b_HL7v2Transformation
channel remove DR-2a_CDACCDTransformation
channel remove DR-1_RoutingInstructionValidation
channel remove CMP-1_WebServiceListener
channel remove CMP-2a_CanonicalSchemaValidation
channel remove CMP-2b_CanonicalSchematronValidation
channel remove CMP-3_CanonicalValidationAggregation
channel remove CMP-4_CDWLoader

import "${build.outputDirectory}/channels/ftpAddressChannel.xml" force
import "${build.outputDirectory}/channels/navEmailAddressChannel.xml"  force
import "${build.outputDirectory}/channels/DocumentRouter.xml"  force
import "${build.outputDirectory}/channels/AcceptCanonical_Channel.xml" force
import "${build.outputDirectory}/channels/DR-4_SecureEmailTransmission.xml"  force
import "${build.outputDirectory}/channels/DR-3_DocumentRouterMuxDemux.xml"  force
import "${build.outputDirectory}/channels/DR-2c_RIMITSTransformation.xml"   force
import "${build.outputDirectory}/channels/DR-2b_HL7v2Transformation.xml"  force
import "${build.outputDirectory}/channels/DR-2a_CDACCDTransformation.xml"  force
import "${build.outputDirectory}/channels/DR-1_RoutingInstructionValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-1_WebServiceListener.xml"  force
import "${build.outputDirectory}/channels/CMP-2a_CanonicalSchemaValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-2b_CanonicalSchematronValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-3_CanonicalValidationAggregation.xml"  force
import "${build.outputDirectory}/channels/CMP-4_CDWLoader.xml"  force

importalerts "${build.outputDirectory}/alerts/CMP-alerts.xml"

deploy
channel start *
channel list