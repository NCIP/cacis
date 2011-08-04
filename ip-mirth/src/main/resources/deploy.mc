channel stop *
import "${build.outputDirectory}/channels/AcceptCanonical_Channel.xml" force
import "${build.outputDirectory}/channels/DocumentRouter_Channel.xml" force
deploy
channel start *
channel list