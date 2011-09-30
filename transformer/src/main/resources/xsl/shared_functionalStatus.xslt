<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<!-- functionalStatus section template -->
	<xsl:template name="functionalStatus_section">
		<xsl:param name="functionalStatus_sec"/>
		<component>
			<section>
				<templateId root="2.16.840.1.113883.3.88.11.83.109" assigningAuthorityName="HITSP/C83"/>
				<templateId root="2.16.840.1.113883.10.20.1.5" assigningAuthorityName="HL7 CCD"/>
				<code code="47420-5" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Functional status assessment"/>
				<title>Functional status</title>
				<text/>
				<xsl:for-each select="$functionalStatus_sec/trim:act/trim:observation">
					<xsl:call-template name="functionalStatus_organizer">
						<xsl:with-param name="functionalStatusOrganizer" select="current()"/>
						<xsl:with-param name="functionalStatusOriginalText" select="$functionalStatus_sec/trim:act/trim:title/trim:ST/text()"/>
					</xsl:call-template>
				</xsl:for-each>
			</section>
		</component>
	</xsl:template>
	<!-- functionalStatus observation organizer -->
	<xsl:template name="functionalStatus_organizer">
		<xsl:param name="functionalStatusOrganizer"/>
		<xsl:param name="functionalStatusOriginalText"/>
		<entry>
			<organizer classCode="CLUSTER" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.32" assigningAuthorityName="HL7 CCD"/>
				<id nullFlavor='NI'/>
				<code code="423690003" codeSystem="2.16.840.1.113883.6.96" displayName="general physical performance status">
					<originalText><xsl:value-of select="$functionalStatusOriginalText"/></originalText>
				</code>
				<statusCode code="completed"/>
				<effectiveTime>
					<xsl:attribute name="value"><xsl:value-of select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/></xsl:attribute>
				</effectiveTime>
				<xsl:for-each select="$functionalStatusOrganizer/trim:value">
					<xsl:call-template name="functionalStatus_observation">
						<xsl:with-param name="functionalStatusObs" select="current()"/>
					</xsl:call-template>
				</xsl:for-each>
			</organizer>
		</entry>
	</xsl:template>
	<!-- functionalStatus observation template -->
	<xsl:template name="functionalStatus_observation">
		<xsl:param name="functionalStatusObs"/>
		<component>
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="HL7 CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>

				<id nullFlavor='NI'/>
				<code code="423740007" codeSystem="2.16.840.1.113883.6.96" displayName="ECOG performance status">
					<originalText><xsl:value-of select="$functionalStatusObs/trim:label/text()"/></originalText>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id(current())"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime>
					<xsl:attribute name="value"><xsl:value-of select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/></xsl:attribute>
				</effectiveTime>
				<value>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="$functionalStatusObs/trim:CE/trim:displayName"/>
					</xsl:call-template>
				</value>
				<interpretationCode nullFlavor="NI"/>
				<referenceRange>
					<observationRange nullFlavor="NI"/>
				</referenceRange>
			</observation>
		</component>
	</xsl:template>

</xsl:stylesheet>