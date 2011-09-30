<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<xsl:template name="planOfCare_section">
		<xsl:param name="trim_impressionPlan"/>
		
		<component>
			<section>
				<templateId root="2.16.840.1.113883.3.88.11.83.124" assigningAuthorityName="HITSP/C83"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.3.31" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.10.20.2.7" assigningAuthorityName="HL7 HandP"/>
				<templateId root="2.16.840.1.113883.10.20.1.10" assigningAuthorityName="HL7 CCD"/>
				<code code="18776-5" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Treatment plan"/>
				<title>Plan of Care</title>
				<text/>
				
				<xsl:call-template name="planOfCare_breastWorkup_narrative">
					<xsl:with-param name="trim_value" select="$trim_impressionPlan/trim:act/trim:observation/trim:value"/>
				</xsl:call-template>
				
				<xsl:apply-templates select="$trim_impressionPlan/trim:act/trim:relationship" mode="impressionPlan"/>
			</section>
		</component>
	</xsl:template>
	
	<xsl:template match="@*|node()" mode="impressionPlan"/>
	
	<xsl:template name="planOfCare_breastWorkup" match="trim:relationship[@name='breastWorkUp' or @name='metastaticWorkUp' or @name='preTreatmentTesting' or @name='nonSurgicalTreatment' or @name='surgicalTreatment' or @name='reconstructiveSurgery'][not(@enabled) or @enabled='true']" mode="impressionPlan">
		<xsl:param name="trim_value" select="trim:act/trim:observation/trim:value"/>
		
		<entry typeCode="DRIV">
			<act classCode="ACT" moodCode="INT">
				<templateId root="2.16.840.1.113883.10.20.1.25" assigningAuthorityName="CCD"/>
				<id nullFlavor="NI"/>
				<code nullFlavor="OTH">
					<originalText><xsl:value-of select="trim:act/trim:title/trim:ST"/></originalText>
				</code>
				<statusCode code="new"/>
		
				<xsl:for-each select="$trim_value/trim:SETCE">
					<xsl:variable name="translatedCode">
						<code>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="trim_dispNm" select="trim:displayName"/>
								<xsl:with-param name="noXSI" select="'true'"/>
							</xsl:call-template>
						</code>
					</xsl:variable>
					
					<entryRelationship typeCode="COMP">
						<act classCode="ACT" moodCode="INT">
							<xsl:copy-of select="$translatedCode"/>
							<xsl:if test="$translatedCode/cda:code/@code = 'OTH'">
								<text><xsl:value-of select="$trim_value/trim:ST"/></text>
							</xsl:if>
						</act>
					</entryRelationship>
				</xsl:for-each>
			</act>
		</entry>
	</xsl:template>
	
	<xsl:template name="planOfCare_breastWorkup_narrative">
		<xsl:param name="trim_value"/>
		
		<xsl:if test="$trim_value/trim:ST != ''">
			<entry typeCode="DRIV">
				<act classCode="ACT" moodCode="INT">
					<id nullFlavor="NI"/>
					<code nullFlavor="OTH">
						<originalText><xsl:value-of select="$trim_value/trim:label"/></originalText>
					</code>
					<statusCode code="new"/>
					<entryRelationship typeCode="COMP">
						<act classCode="ACT" moodCode="INT">
							<code nullFlavor="OTH">
								<originalText><xsl:value-of select="$trim_value/trim:ST"/></originalText>
							</code>
						</act>
					</entryRelationship>
				</act>
			</entry>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>