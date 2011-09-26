channel stop *
channel undeploy *
channel remove *

importcfg "${build.outputDirectory}/config/transformerConfig.xml"

import "${build.outputDirectory}/channels/WS2F-CDF-XCCD.xml" force
import "${build.outputDirectory}/channels/WS2F-XCCD-CDF.xml" force
importalerts "${build.outputDirectory}/config/transformerAlerts.xml"

deploy
channel start *
channel list