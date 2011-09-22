channel stop *
channel undeploy *

channel remove ftpAddressChannel
channel remove DocumentRouter
channel remove AcceptCanonical_Channel
channel remove DR-4c_SecureFileTransfer
channel remove DR-4b_XDSNAVTransmission
channel remove DR-4a_SecureEmailTransmission
channel remove DR-3_DocumentRouterMuxDemux
channel remove DR-2c_RIMITSTransformation
channel remove DR-2b_HL7v2Transformation
channel remove DR-2a_CDACCDTransformation
channel remove DR-1_RoutingInstructionValidation
channel remove CMP-1_WebServiceListener
channel remove CMP-2_CanonicalValidation
channel remove CMP-3_CDWLoader

importcodetemplates "${build.outputDirectory}/templates/mc-code-templates.xml" force

import "${build.outputDirectory}/channels/ftpAddressChannel.xml" force
import "${build.outputDirectory}/channels/DocumentRouter.xml"  force
import "${build.outputDirectory}/channels/AcceptCanonical_Channel.xml" force
import "${build.outputDirectory}/channels/DR-4c_SecureFileTransfer.xml"  force
import "${build.outputDirectory}/channels/DR-4b_XDSNAVTransmission.xml"  force
import "${build.outputDirectory}/channels/DR-4a_SecureEmailTransmission.xml"  force
import "${build.outputDirectory}/channels/DR-3_DocumentRouterMuxDemux.xml"  force
import "${build.outputDirectory}/channels/DR-2c_RIMITSTransformation.xml"   force
import "${build.outputDirectory}/channels/DR-2b_HL7v2Transformation.xml"  force
import "${build.outputDirectory}/channels/DR-2a_CDACCDTransformation.xml"  force
import "${build.outputDirectory}/channels/DR-1_RoutingInstructionValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-1_WebServiceListener.xml"  force
import "${build.outputDirectory}/channels/CMP-2_CanonicalValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-3_CDWLoader.xml"  force

importalerts "${build.outputDirectory}/alerts/CMP-alerts.xml"

deploy
channel start *
channel list