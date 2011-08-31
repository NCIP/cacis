channel stop *
channel undeploy *

channel remove Trim-2-CCD-xslt
channel remove SemanticAdapterChannel
channel remove IdentifierAndVocabResolverChannel

import "${build.outputDirectory}/channels/Trim-2-CCD-xslt.xml" force
import "${build.outputDirectory}/channels/SemanticAdapterChannel.xml" force
import "${build.outputDirectory}/channels/IdentifierAndVocabResolverChannel.xml" force

deploy
channel start *
channel list