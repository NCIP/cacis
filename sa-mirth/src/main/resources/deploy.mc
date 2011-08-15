channel stop *
channel remove *
import "${build.outputDirectory}/channels/Trim-2-CCD-xslt.xml
import "${build.outputDirectory}/channels/SemanticAdapterChannel.xml
deploy
channel start *
channel list