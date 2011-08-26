channel stop *
channel undeploy *

channel remove SemanticAdapterChannel

import "${build.outputDirectory}/channels/SemanticAdapterChannel.xml" force
deploy
channel start *
channel list