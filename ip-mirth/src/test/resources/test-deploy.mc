channel stop *
channel undeploy *
channel remove *
import "${build.outputDirectory}/channels/ftpAddressChannel.xml"
import "${build.outputDirectory}/channels/navEmailAddressChannel.xml"
import "${build.outputDirectory}/channels/secureEmailAddressChannel.xml"
import "${build.outputDirectory}/channels/DocumentRouter.xml"
import "${build.outputDirectory}/channels/AcceptCanonical_Channel.xml"
import "${build.outputDirectory}/channels/HL7_V2_Channel.xml"
import "${build.outputDirectory}/channels/CCD_Channel.xml"
import "${build.outputDirectory}/channels/XMLITS_Channel.xml"
import "${build.outputDirectory}/channels/TransSplitChannel.xml"
import "${build.outputDirectory}/channels/ConfigChannel.xml"
import "${build.testOutputDirectory}/channels/MockedConfigChannelSource.xml"
deploy
channel start *
channel list