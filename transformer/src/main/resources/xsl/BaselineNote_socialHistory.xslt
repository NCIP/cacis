<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<xsl:template name="socialHistory_section">
		<xsl:param name="trim_sec"/>
		
		<component>
			<section>
				<!-- Social history section templates -->
				<templateId root="2.16.840.1.113883.3.88.11.83.126" assigningAuthorityName="HITSP/C83"/>
				<templateId root="2.16.840.1.113883.10.20.1.15" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.3.16" assigningAuthorityName="IHE PCC"/>
				<code code="29762-2" codeSystem="2.16.840.1.113883.6.1"/>
				<title>Social History</title>
				<text/>
				
				<!-- Smoking -->
				<xsl:variable name="currentSmoker" select="$trim_sec/trim:act/trim:relationship[@name='smoking']/trim:act/trim:relationship[@name='currentSmoker']"/>
				<xsl:variable name="previousSmoker" select="$trim_sec/trim:act/trim:relationship[@name='smoking']/trim:act/trim:relationship[@name='previousSmoker']"/>
				<xsl:variable name="noSmoker" select="$trim_sec/trim:act/trim:relationship[@name='smoking']/trim:act/trim:observation/trim:value[trim:valueSet='yesNo']/trim:CE/trim:displayName"/>
				<xsl:choose>
					<xsl:when test="$currentSmoker[trim:act/trim:observation/trim:value/trim:ST != ''] and $previousSmoker[@enabled='false']">
						<xsl:call-template name="socialHistory_pattern">
							<xsl:with-param name="codeOriginal" select="'Current Smoker? Yes'"/>
							<xsl:with-param name="codeCode" select="'230056004'"/>
							<xsl:with-param name="codeDisplay" select="'Cigarette consumption'"/>
							<xsl:with-param name="effectiveTimeLow" select="$currentSmoker/trim:act/trim:observation/trim:value[trim:label = 'Year started:']/trim:ST"/>
							<xsl:with-param name="valueValue" select="$currentSmoker/trim:act/trim:observation/trim:value[trim:label = 'Average#/week']/trim:ST"/>
							<xsl:with-param name="valueUnit" select="'{cigarettes}/wk'"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="$previousSmoker[@enabled='true' and trim:act/trim:observation/trim:value/trim:ST != '']">
						<xsl:call-template name="socialHistory_pattern">
							<xsl:with-param name="codeOriginal" select="'Smoked in the past'"/>
							<xsl:with-param name="codeCode" select="'230056004'"/>
							<xsl:with-param name="codeDisplay" select="'Cigarette consumption'"/>
							<xsl:with-param name="effectiveTimeWidth" select="$previousSmoker/trim:act/trim:observation/trim:value[trim:label = 'Total years smoking:']/trim:ST"/>
							<xsl:with-param name="effectiveTimeHigh" select="$previousSmoker/trim:act/trim:observation/trim:value[trim:label = 'Year quit:']/trim:ST"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="$noSmoker='No'">
						<xsl:call-template name="socialHistory_pattern">
							<xsl:with-param name="codeOriginal" select="'Current Smoker? Yes'"/>
							<xsl:with-param name="codeCode" select="'230056004'"/>
							<xsl:with-param name="codeDisplay" select="'Cigarette consumption'"/>
							<xsl:with-param name="valueUnit" select="'{cigarettes}/wk'"/>
							<xsl:with-param name="valueValue" select="'0'"/>
						</xsl:call-template>
					</xsl:when>
				</xsl:choose>
				
				<!-- Alcohol -->
				<xsl:variable name="currentAlcohol" select="$trim_sec/trim:act/trim:relationship[@name='alcohol']/trim:act/trim:relationship[@name='currentAlcohol']"/>
				<xsl:variable name="previousAlcohol" select="$trim_sec/trim:act/trim:relationship[@name='alcohol']/trim:act/trim:relationship[@name='previousAlcohol']"/>
				<xsl:variable name="noAlcohol" select="$trim_sec/trim:act/trim:relationship[@name='alcohol']/trim:act/trim:observation/trim:value[trim:valueSet='yesNo']/trim:CE/trim:displayName"/>
				<xsl:choose>
					<xsl:when test="$currentAlcohol[trim:act/trim:observation/trim:value/trim:ST != ''] and $previousAlcohol[@enabled='false']">
						<xsl:call-template name="socialHistory_pattern">
							<xsl:with-param name="codeOriginal" select="'Current Drinker? Yes'"/>
							<xsl:with-param name="codeCode" select="'160573003'"/>
							<xsl:with-param name="codeDisplay" select="'Alcohol intake'"/>
							<xsl:with-param name="effectiveTimeLow" select="$currentAlcohol/trim:act/trim:observation/trim:value[trim:label = 'Year started:']/trim:ST"/>
							<xsl:with-param name="valueValue" select="$currentAlcohol/trim:act/trim:observation/trim:value[trim:label = 'Average#/week']/trim:ST"/>
							<xsl:with-param name="valueUnit" select="'{drinks}/wk'"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="$previousAlcohol[@enabled='true' and trim:act/trim:observation/trim:value/trim:ST != '']">
						<xsl:call-template name="socialHistory_pattern">
							<xsl:with-param name="codeOriginal" select="'Drank in the past'"/>
							<xsl:with-param name="codeCode" select="'160573003'"/>
							<xsl:with-param name="codeDisplay" select="'Alcohol intake'"/>
							<xsl:with-param name="effectiveTimeWidth" select="$previousAlcohol/trim:act/trim:observation/trim:value[trim:label = 'Total years drinking:']/trim:ST"/>
							<xsl:with-param name="effectiveTimeHigh" select="$previousAlcohol/trim:act/trim:observation/trim:value[trim:label = 'Year quit:']/trim:ST"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="$noAlcohol='No'">
						<xsl:call-template name="socialHistory_pattern">
							<xsl:with-param name="codeOriginal" select="'Current Drinker? No'"/>
							<xsl:with-param name="codeCode" select="'160573003'"/>
							<xsl:with-param name="codeDisplay" select="'Alcohol intake'"/>
							<xsl:with-param name="valueUnit" select="'{drinks}/wk'"/>
							<xsl:with-param name="valueValue" select="'0'"/>
						</xsl:call-template>
					</xsl:when>
				</xsl:choose>
				
				<!-- Drugs -->
				<xsl:variable name="currentDrugs" select="$trim_sec/trim:act/trim:relationship[@name='recreationalDrugs']/trim:act/trim:relationship[@name='currentDrugs']"/>
				<xsl:variable name="pastDrugs" select="$trim_sec/trim:act/trim:relationship[@name='recreationalDrugs']/trim:act/trim:relationship[@name='pastDrugs']"/>
				<xsl:variable name="noDrugs" select="$trim_sec/trim:act/trim:relationship[@name='recreationalDrugs']/trim:act/trim:observation/trim:value[trim:valueSet='yesNo']/trim:CE/trim:displayName"/>
				<xsl:choose>
					<xsl:when test="$currentDrugs[trim:act/trim:observation/trim:value/trim:ST != ''] and $pastDrugs[@enabled='false']">
						<xsl:call-template name="socialHistory_pattern">
							<xsl:with-param name="codeOriginal" select="'Current User: Yes'"/>
							<xsl:with-param name="codeCode" select="'228390007'"/>
							<xsl:with-param name="codeDisplay" select="'frequency of drug misuse'"/>
							<xsl:with-param name="effectiveTimeLow" select="$currentDrugs/trim:act/trim:observation/trim:value[trim:label = 'Year started:']/trim:ST"/>
							<xsl:with-param name="valueValue" select="$currentDrugs/trim:act/trim:observation/trim:value[trim:label = 'Average#/week']/trim:ST"/>
							<xsl:with-param name="valueUnit" select="'{drugs}/wk'"/>
							<xsl:with-param name="text" select="$currentDrugs/trim:act/trim:observation/trim:value[1]/trim:ST"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="$pastDrugs[@enabled='true' and trim:act/trim:observation/trim:value/trim:ST != '']">
						<xsl:call-template name="socialHistory_pattern">
							<xsl:with-param name="codeOriginal" select="'Used in the past'"/>
							<xsl:with-param name="codeCode" select="'228390007'"/>
							<xsl:with-param name="codeDisplay" select="'frequency of drug misuse'"/>
							<xsl:with-param name="effectiveTimeWidth" select="$pastDrugs/trim:act/trim:observation/trim:value[trim:label = 'Total years using:']/trim:ST"/>
							<xsl:with-param name="effectiveTimeHigh" select="$pastDrugs/trim:act/trim:observation/trim:value[trim:label = 'Year quit:']/trim:ST"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="$noDrugs='No'">
						<xsl:call-template name="socialHistory_pattern">
							<xsl:with-param name="codeOriginal" select="'Current User: No'"/>
							<xsl:with-param name="codeCode" select="'228390007'"/>
							<xsl:with-param name="codeDisplay" select="'frequency of drug misuse'"/>
							<xsl:with-param name="valueUnit" select="'{drugs}/wk'"/>
							<xsl:with-param name="valueValue" select="'0'"/>
						</xsl:call-template>
					</xsl:when>
				</xsl:choose>
				
				<xsl:apply-templates select="$trim_sec/trim:act/trim:relationship" mode="socialHistoryPattern2"/>
			</section>
		</component>
	</xsl:template>
	
	<xsl:template name="socialHistory_pattern">		
		<xsl:param name="codeOriginal"/>
		<xsl:param name="codeCode"/>
		<xsl:param name="codeDisplay"/>
		<xsl:param name="effectiveTimeLow"/>
		<xsl:param name="effectiveTimeWidth"/>
		<xsl:param name="effectiveTimeHigh"/>
		<xsl:param name="valueValue"/>
		<xsl:param name="valueUnit"/>
		<xsl:param name="text"/>
		
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<!-- Yes, year started, #/week -->
				<!-- Social history observation templates -->
				<templateId root="2.16.840.1.113883.3.88.11.83.19" assigningAuthorityName="HITSP C83"/>
				<templateId root="2.16.840.1.113883.10.20.1.33" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.4" assigningAuthorityName="IHE PCC"/>
				<id nullFlavor="NI"/>
				<code codeSystem="2.16.840.1.113883.6.96">
					<xsl:if test="$codeCode">
						<xsl:attribute name="code"><xsl:value-of select="$codeCode"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="$codeDisplay">
						<xsl:attribute name="displayName"><xsl:value-of select="$codeDisplay"/></xsl:attribute>
					</xsl:if>
					<originalText><xsl:value-of select="$codeOriginal"/></originalText>
				</code>
				<xsl:if test="$text">
					<text><xsl:value-of select="$text"/></text>
				</xsl:if>
				<statusCode code="completed"/>
				<effectiveTime>
					<xsl:if test="$effectiveTimeWidth">
						<width>
							<xsl:attribute name="value"><xsl:value-of select="$effectiveTimeWidth"/></xsl:attribute>
						</width>
					</xsl:if>
					<xsl:if test="$effectiveTimeLow">
						<low>
							<xsl:attribute name="value"><xsl:value-of select="$effectiveTimeLow"/></xsl:attribute>
						</low>
					</xsl:if>
					<xsl:if test="$effectiveTimeHigh">
						<high>
							<xsl:attribute name="value"><xsl:value-of select="$effectiveTimeHigh"/></xsl:attribute>
						</high>
					</xsl:if>
					<xsl:if test="not($effectiveTimeLow) and not($effectiveTimeHigh) and not($effectiveTimeWidth)">
						<low nullFlavor="NINF"/>
						<high nullFlavor="PINF"/>
					</xsl:if>
				</effectiveTime>
				<value xsi:type="PQ">
					<xsl:if test="$valueUnit">
						<xsl:attribute name="unit"><xsl:value-of select="$valueUnit"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="$valueValue">
						<xsl:attribute name="value"><xsl:value-of select="$valueValue"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="not($valueUnit) and not($valueValue)">
						<xsl:attribute name="nullFlavor">NI</xsl:attribute>
					</xsl:if>
				</value>
			</observation>
		</entry>
	</xsl:template>
	
	<xsl:template match="@*|node()" mode="socialHistoryPattern2"/>
	
	<xsl:template match="trim:relationship[@name = 'education' or @name = 'occupation']" mode="socialHistoryPattern2">		
		<xsl:variable name="dateOfExam" select="/trim:trim/trim:act/trim:relationship[@name='generalTab']/trim:act/trim:relationship[@name='dateOfExam']/trim:act/trim:observation/trim:value/trim:TS/trim:value"/>
		<!-- remove the colon from the display text so that it matches the codes we have in the TRIM-codes.xml -->
		<xsl:variable name="codeDisplay">
			<xsl:call-template name="string-replace-all">
				<xsl:with-param name="text" select="trim:act/trim:title/trim:ST"/>
				<xsl:with-param name="replace" select="':'"/>
				<xsl:with-param name="by" select="''"/>
			</xsl:call-template>
		</xsl:variable>
		
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.3.88.11.83.19" assigningAuthorityName="HITSP C83"/>
				<templateId root="2.16.840.1.113883.10.20.1.33" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.4" assigningAuthorityName="IHE PCC"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="$codeDisplay"/>
						<xsl:with-param name="noXSI" select="true"/>
					</xsl:call-template>
				</code>
				<statusCode code="completed"/>
				<effectiveTime>
					<xsl:choose>
						<xsl:when test="@name = 'workStatus'">
							<low>
								<xsl:attribute name="value"><xsl:value-of select="number(substring($dateOfExam, 1, 4)) - 1"/><xsl:value-of select="substring($dateOfExam, 5)"/></xsl:attribute>
							</low>
							<high>
								<xsl:attribute name="value"><xsl:value-of select="$dateOfExam"/></xsl:attribute>
							</high>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="nullFlavor">NI</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
				</effectiveTime>
				<value>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:ST | trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
					</xsl:call-template>
				</value>
			</observation>
		</entry>
	</xsl:template>
	
	<xsl:template match="trim:relationship[@name = 'other']" mode="socialHistoryPattern2">
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.3.88.11.83.19" assigningAuthorityName="HITSP C83"/>
				<templateId root="2.16.840.1.113883.10.20.1.33" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.4" assigningAuthorityName="IHE PCC"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="trim:act/trim:title/trim:ST"/>
					</xsl:call-template>
				</code>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="NI"/>
				<value xsi:type="ST"><xsl:value-of select="trim:act/trim:observation/trim:value/trim:ST"/></value>
			</observation>
		</entry>
	</xsl:template>
	
	<xsl:template match="trim:relationship[@name = 'workStatus']" mode="socialHistoryPattern2">
		<xsl:variable name="dateOfExam" select="/trim:trim/trim:act/trim:relationship[@name='generalTab']/trim:act/trim:relationship[@name='dateOfExam']/trim:act/trim:observation/trim:value/trim:TS/trim:value"/>
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.3.88.11.83.19" assigningAuthorityName="HITSP C83"/>
				<templateId root="2.16.840.1.113883.10.20.1.33" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.4" assigningAuthorityName="IHE PCC"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="trim:act/trim:title/trim:ST"/>
					</xsl:call-template>
				</code>
				<statusCode code="completed"/>
				<effectiveTime>
					<low>
						<xsl:attribute name="value"><xsl:value-of select="number(substring($dateOfExam, 1, 4)) - 1"/><xsl:value-of select="substring($dateOfExam, 5)"/></xsl:attribute>
					</low>
					<high>
						<xsl:attribute name="value"><xsl:value-of select="$dateOfExam"/></xsl:attribute>
					</high>
				</effectiveTime>
				<value xsi:type="CD" nullFlavor="OTH">
					<originalText><xsl:value-of select="trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/></originalText>
				</value>
			</observation>
		</entry>
	</xsl:template>
</xsl:stylesheet>