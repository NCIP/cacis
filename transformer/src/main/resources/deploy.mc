channel stop *
channel undeploy *
importcfg "${build.outputDirectory}/config/transformerConfig.xml"
channel remove transformationComponent
channel remove WS2F-CDF-XCCD
channel remove WS2F-XCCD-CDF
import "${build.outputDirectory}/channels/WS2F-CDF-XCCD.xml" force
import "${build.outputDirectory}/channels/WS2F-XCCD-CDF.xml" force
importalerts "${build.outputDirectory}/config/transformerAlerts.xml"
deploy
channel start *
channel list