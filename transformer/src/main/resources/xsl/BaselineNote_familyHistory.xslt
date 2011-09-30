<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<!-- family history section template-->
	<xsl:template name="familyHistory_section">
		<xsl:param name="fh_sec"/>
		<component>
			<section>
				<templateId root="2.16.840.1.113883.10.20.1.4" assigningAuthorityName="HL7 CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.3.14" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.125" assigningAuthorityName="HITSP/C83"/>
				<code code="10157-6" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
				<title>Family history</title>
				<xsl:for-each select="$fh_sec/trim:act/trim:relationship[@name='familyHistory']">
					<!-- call family history organizer template -->
					<xsl:call-template name="familyHistory_organizer">
						<xsl:with-param name="fh_org" select="current()"/>
					</xsl:call-template>
				</xsl:for-each>
			</section>
		</component>
	</xsl:template>
	<!-- familyHistory organizer template -->
	<xsl:template name="familyHistory_organizer">
		<xsl:param name="fh_org"/>
		<entry>
			<organizer classCode="CLUSTER" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.23" assigningAuthorityName="HL7 CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.15" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.18" assigningAuthorityName="HITSP C83"/>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="UNK"/>
				<xsl:variable name="side" select="$fh_org/trim:act/trim:relationship[@name='side']/trim:act/trim:observation/trim:value/trim:ST"/>
				<xsl:variable name="relative" select="$fh_org/trim:act/trim:relationship[@name='relative']/trim:act/trim:observation/trim:value/trim:ST"/>
				<xsl:variable name="trimDisp">
					<xsl:choose>
						<xsl:when test="string-length($side)>0">
							<xsl:value-of select="$side"/>|<xsl:value-of select="$relative"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$relative"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<subject>
					<relatedSubject classCode="PRS">
						<code>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="trim_dispNm" select="$trimDisp"/>
								<xsl:with-param name="noXSI" select="'yes'"/>							
							</xsl:call-template>
						</code>
					</relatedSubject>
				</subject>
				<xsl:choose>
					<!-- if have cancer history data, then process, otherwise, should use @nullFlavor="NI" -->
					<xsl:when test="count($fh_org/trim:act/trim:relationship//trim:CE)>0 or count($fh_org/trim:act/trim:relationship//trim:SETCE)>0">
				<xsl:for-each select="$fh_org/trim:act/trim:relationship[@name= 'cancerType']">
					<xsl:if test="trim:act/trim:observation/trim:value/trim:CE">
						<!-- call family history observation cancerType template -->
						<xsl:call-template name="familyHistory_observation_cancerType">
							<xsl:with-param name="cancerType" select="current()/trim:act/trim:observation/trim:value/trim:CE"/>
						</xsl:call-template>
					</xsl:if>
				</xsl:for-each>
				<xsl:for-each select="$fh_org/trim:act/trim:relationship[@name= 'test']">
					<xsl:if test="trim:act/trim:observation/trim:value/trim:SETCE">
						<!-- call family history observation geneticMarker template -->
						<xsl:call-template name="familyHistory_observation_geneticMarker">
							<xsl:with-param name="geneticMarker" select="current()/trim:act/trim:observation/trim:value/trim:SETCE"/>
						</xsl:call-template>
					</xsl:if>
				</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						 <component>
                        <observation classCode="OBS" moodCode="EVN">
							<code nullFlavor="NI"/>
                        </observation>
                        </component>
					</xsl:otherwise>
				</xsl:choose>
			</organizer>
		</entry>
	</xsl:template>
	<!-- familyHistory observation templates-->
	<xsl:template name="familyHistory_observation_cancerType">
		<xsl:param name="cancerType"/>
		<component>
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.22" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.3" assigningAuthorityName="IHE PCC"/>
				<id nullFlavor="NI"/>
				<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id(current())"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="UNK"/>
				<value>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="$cancerType/trim:displayName"/>
					</xsl:call-template>
				</value>
			</observation>
		</component>
	</xsl:template>
	<!-- familyHistory genetic marker template -->
	<xsl:template name="familyHistory_observation_geneticMarker">
		<xsl:param name="geneticMarker"/>
		<component xmlns="urn:hl7-org:v3">
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.22" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.3" assigningAuthorityName="IHE PCC"/>
				<id nullFlavor="NI"/>
				<code code="20889005" codeSystem="2.16.840.1.113883.6.96" displayName="Genetic marker"/>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id(current())"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="UNK"/>
				<xsl:if test="$geneticMarker/trim:displayName">
					<value>
						<xsl:call-template name="translateCode">
							<xsl:with-param name="trim_dispNm" select="$geneticMarker/trim:displayName"/>
						</xsl:call-template>
					</value>
				</xsl:if>
			</observation>
		</component>
	</xsl:template>
</xsl:stylesheet>