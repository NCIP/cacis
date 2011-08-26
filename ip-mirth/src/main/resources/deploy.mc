channel stop *
channel undeploy *

channel remove ftpAddressChannel
channel remove navEmailAddressChannel
channel remove secureEmailAddressChannel
channel remove DocumentRouter
channel remove AcceptCanonical_Channel
channel remove HL7_V2_CLINICAL_NOTE_Channel
channel remove CCD_Channel
channel remove XMLITS_Channel
channel remove TransSplitChannel
channel remove ConfigChannel

import "${build.outputDirectory}/channels/ftpAddressChannel.xml" force
import "${build.outputDirectory}/channels/navEmailAddressChannel.xml"  force
import "${build.outputDirectory}/channels/secureEmailAddressChannel.xml"  force
import "${build.outputDirectory}/channels/DocumentRouter.xml"  force
import "${build.outputDirectory}/channels/AcceptCanonical_Channel.xml" force
import "${build.outputDirectory}/channels/HL7_V2_Channel.xml"  force
import "${build.outputDirectory}/channels/CCD_Channel.xml"  force
import "${build.outputDirectory}/channels/XMLITS_Channel.xml"   force
import "${build.outputDirectory}/channels/TransSplitChannel.xml"  force
import "${build.outputDirectory}/channels/ConfigChannel.xml"  force

deploy
channel start *
channel list