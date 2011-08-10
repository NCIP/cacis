channel stop *
channel remove *
import "${build.outputDirectory}/channels/ftpAddressChannel.xml"
import "${build.outputDirectory}/channels/navEmailAddressChannel.xml"
import "${build.outputDirectory}/channels/secureEmailAddressChannel.xml"
import "${build.outputDirectory}/channels/DocumentRouter.xml"
import "${build.outputDirectory}/channels/AcceptCanonical_Channel.xml"
import "${build.outputDirectory}/channels/ConfigChannel.xml"
import "${build.outputDirectory}/channels/TestConfigChannel.xml"
deploy
channel start *
channel list