<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<xsl:template name="encounters_section">
		<xsl:param name="trim_billing"/>
		<xsl:param name="trim_np"/>
		<xsl:param name="trim_md"/>
		<xsl:param name="trim_dictation"/>
		
		<component>
			<section>
				<templateId root="2.16.840.1.113883.10.20.1.3" assigningAuthorityName="HL7 CCD"/>
				<!--Encounters section template-->
				<code code="46240-8" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="History of encounters"/>
				<title>Encounters</title>
				
				<text>
					<xsl:if test="$trim_billing">
						<table border="1" width="100%">
							<thead>
								<tr>
									<th>Encounter Component</th>
									<th>Billing Value</th>
								</tr>
							</thead>
							<tbody>
								<xsl:for-each select="$trim_billing/trim:act/trim:relationship/trim:act/trim:observation/trim:value">
									<tr>
										<td><xsl:value-of select="current()/trim:label"/></td>
										<td><xsl:value-of select="current()/trim:CE/trim:displayName"/></td>
									</tr>
								</xsl:for-each>
							</tbody>
						</table>
					</xsl:if>
					
					<xsl:if test="$trim_np or $trim_md">
						<table border="1" width="100%">
							<thead>
								<tr>
									<th>Provider Type</th>
									<th>Face to Face Time</th>
									<th>Counseling Time</th>
								</tr>
							</thead>
							<tbody>
								<xsl:if test="$trim_np">
									<tr>
										<td>NP</td>
										<td><xsl:value-of select="$trim_np/trim:act/trim:relationship[@name='faceNP']/trim:act/trim:observation/trim:value/trim:ST"/> minutes</td>
										<td><xsl:value-of select="$trim_np/trim:act/trim:relationship[@name='counsellingNP']/trim:act/trim:observation/trim:value/trim:ST"/> minutes</td>
									</tr>
								</xsl:if>
								<xsl:if test="$trim_md">
									<tr>
										<td>MD</td>
										<td><xsl:value-of select="$trim_md/trim:act/trim:relationship[@name='faceMD']/trim:act/trim:observation/trim:value/trim:ST"/> minutes</td>
										<td><xsl:value-of select="$trim_md/trim:act/trim:relationship[@name='counsellingMD']/trim:act/trim:observation/trim:value/trim:ST"/> minutes</td>
									</tr>
								</xsl:if>
							</tbody>
						</table>
					</xsl:if>
					
					<xsl:if test="$trim_dictation">
						<list>
							<item><xsl:value-of select="$trim_dictation/trim:act/trim:title/trim:ST"/><xsl:text> </xsl:text>
								<xsl:value-of select="$trim_dictation/trim:act/trim:observation/trim:value/trim:label"/><xsl:text> </xsl:text>
								<xsl:value-of select="$trim_dictation/trim:act/trim:observation/trim:value/trim:ST"/></item>
						</list>
					</xsl:if>
				</text>
			</section>
		</component>
	</xsl:template>
</xsl:stylesheet>