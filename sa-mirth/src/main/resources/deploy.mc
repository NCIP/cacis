channel stop *
channel undeploy *

channel remove SemanticAdapterChannel
channel remove IdentifierAndVocabResolverChannel

import "${build.outputDirectory}/channels/SemanticAdapterChannel.xml" force
import "${build.outputDirectory}/channels/IdentifierAndVocabResolverChannel.xml" force

deploy
channel start *
channel list