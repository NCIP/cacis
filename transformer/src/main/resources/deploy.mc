channel stop *
channel remove *
import "${build.outputDirectory}/channels/Trim-2-CCD-xslt.xml" force
import "${build.outputDirectory}/channels/transcendSA.xml" force
import "${build.outputDirectory}/channels/WS2F-CDF-XCCD.xml" force
import "${build.outputDirectory}/channels/WS2F-XCCD-CDF.xml" force
deploy
channel start *
channel list