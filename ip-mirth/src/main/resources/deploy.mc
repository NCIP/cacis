channel stop *
channel undeploy *

channel remove ftpAddressChannel
channel remove navEmailAddressChannel
channel remove secureEmailAddressChannel
channel remove DocumentRouter
channel remove HL7_V2_CLINICAL_NOTE_Channel
channel remove CCD_Channel
channel remove XMLITS_Channel
channel remove TransSplitChannel
channel remove CMP-1_WebServiceListener
channel remove CMP-2a_CanonicalSchemaValidation
channel remove CMP-2b_CanonicalSchematronValidation
channel remove CMP-3_CanonicalValidationAggregation
channel remove CMP-4_CDWLoader

import "${build.outputDirectory}/channels/ftpAddressChannel.xml" force
import "${build.outputDirectory}/channels/navEmailAddressChannel.xml"  force
import "${build.outputDirectory}/channels/secureEmailAddressChannel.xml"  force
import "${build.outputDirectory}/channels/DocumentRouter.xml"  force
import "${build.outputDirectory}/channels/HL7_V2_Channel.xml"  force
import "${build.outputDirectory}/channels/CCD_Channel.xml"  force
import "${build.outputDirectory}/channels/XMLITS_Channel.xml"   force
import "${build.outputDirectory}/channels/TransSplitChannel.xml"  force
import "${build.outputDirectory}/channels/RoutingInstructionValidationChannel.xml"  force
import "${build.outputDirectory}/channels/CMP-1_WebServiceListener.xml"  force
import "${build.outputDirectory}/channels/CMP-2a_CanonicalSchemaValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-2b_CanonicalSchematronValidation.xml"  force
import "${build.outputDirectory}/channels/CMP-3_CanonicalValidationAggregation.xml"  force
import "${build.outputDirectory}/channels/CMP-4_CDWLoader.xml"  force

importalerts "${build.outputDirectory}/alerts/CMP-alerts.xml"

deploy
channel start *
channel list