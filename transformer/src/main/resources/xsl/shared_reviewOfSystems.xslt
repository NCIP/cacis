<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">

	<xsl:template name="ROS_entries">
		<xsl:param name="trim_menopausalStatus"/>
		<xsl:param name="trim_riskFactors"/>
		<xsl:param name="trim_ros"/>
		<xsl:choose>
		
			<xsl:when test="$trim_menopausalStatus">
				<xsl:variable name="menopausalStatusLastDate" select="$trim_menopausalStatus/trim:act/trim:relationship[@name='lastDate']"/>
				<xsl:if test="string-length($menopausalStatusLastDate/trim:act/trim:observation/trim:value[trim:label='Month']/trim:ST)>0">
					<xsl:call-template name="ROS_menopausalLastDate">
						<xsl:with-param name="trim_entry" select="$menopausalStatusLastDate"/>
					</xsl:call-template>
				</xsl:if>
			</xsl:when>
			
			<xsl:when test="$trim_riskFactors">
				<xsl:variable name="ageAtMenarche" select="$trim_riskFactors/trim:act/trim:observation/trim:value[trim:label='Age at menarche:']"/>
				<xsl:variable name="gravida" select="$trim_riskFactors/trim:act/trim:observation/trim:value[trim:label='Gravida:']"/>
				<xsl:variable name="para" select="$trim_riskFactors/trim:act/trim:observation/trim:value[trim:label='Para:']"/>
				<xsl:variable name="ageAtFirstLiveBirth" select="$trim_riskFactors/trim:act/trim:observation/trim:value[trim:label='Age at first live birth:']"/>
				<xsl:if test="$gravida and $gravida/trim:ST != ''">
					<xsl:call-template name="ROS_gravida">
						<xsl:with-param name="trim_value" select="$gravida"/>
					</xsl:call-template>
				</xsl:if>
				<xsl:if test="$para and $para/trim:ST != ''">
					<xsl:call-template name="ROS_para">
						<xsl:with-param name="trim_para" select="$para"/>
						<xsl:with-param name="trim_age" select="$ageAtFirstLiveBirth"/>
					</xsl:call-template>
				</xsl:if>
			</xsl:when>
			
			<xsl:when test="$trim_ros">
				<xsl:for-each select="$trim_ros/trim:act/trim:relationship[@name='rosObj'][not(@enabled) or @enabled='true']">
					<xsl:call-template name="ROS_rosObj">
						<xsl:with-param name="trim_organizer" select="current()"/>
					</xsl:call-template>
				</xsl:for-each>
			</xsl:when>
			
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="ROS_rosObj">
		<xsl:param name="trim_organizer"/>
		<entry typeCode="DRIV">
			<organizer classCode="CLUSTER" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.32" assigningAuthorityName="CCD"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$trim_organizer/trim:act/trim:title/trim:ST"/>
					</xsl:call-template>
				</code>
				<statusCode code="completed"/>
				<xsl:for-each select="$trim_organizer/trim:act/trim:observation/trim:value">
					<xsl:choose>
						<xsl:when test="trim:label='Normal'">
							<xsl:for-each select="trim:SETCE">
								<xsl:variable name="negId">
									<xsl:call-template name="getNegationInd">
										<xsl:with-param name="trim_dispNm" select="trim:displayName"/>
									</xsl:call-template>
								</xsl:variable>
								<component typeCode="COMP">
									<xsl:choose>
										<xsl:when test="$negId='true'">
											<observation classCode="OBS" moodCode="EVN" negationInd="true">
												<xsl:call-template name="ROS_rosObj_observation">
													<xsl:with-param name="negIndStr" select="$negId"/>
													<xsl:with-param name="nmlVal" select="'Normal'"/>
													<xsl:with-param name="trim_setce" select="current()"/>
												</xsl:call-template>
											</observation>
										</xsl:when>
										<xsl:otherwise>
											<observation classCode="OBS" moodCode="EVN">
												<xsl:call-template name="ROS_rosObj_observation">
													<xsl:with-param name="nmlVal" select="'Normal'"/>
													<xsl:with-param name="trim_setce" select="current()"/>
												</xsl:call-template>
											</observation>
										</xsl:otherwise>
									</xsl:choose>
								</component>
							</xsl:for-each>
						</xsl:when>
						<xsl:when test="trim:label='Abnormal'">
							<xsl:for-each select="trim:SETCE">
								<component typeCode="COMP">
									<observation classCode="OBS" moodCode="EVN">
										<xsl:call-template name="ROS_rosObj_observation">
											<xsl:with-param name="nmlVal" select="'Abnormal'"/>
											<xsl:with-param name="trim_setce" select="current()"/>
										</xsl:call-template>
									</observation>
								</component>
							</xsl:for-each>
						</xsl:when>
						<xsl:otherwise>
							<!-- future todo: Abnormal Description -->
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</organizer>
		</entry>
	</xsl:template>
	
	<xsl:template name="ROS_rosObj_observation">
		<xsl:param name="trim_setce"/>
		<xsl:param name="nmlVal"/>
		<xsl:param name="negIndStr"/>
		<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
		<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
		<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
		<id nullFlavor="NI"/>
		<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
		<text>
			<reference>
				<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_setce)"/></xsl:attribute>
			</reference>
		</text>
		<statusCode code="completed"/>
		<effectiveTime nullFlavor="UNK"/>
		<value>
			<xsl:call-template name="translateCode">
				<xsl:with-param name="trim_dispNm" select="$trim_setce/trim:displayName"/>
			</xsl:call-template>
		</value>
		<interpretationCode>
			<xsl:choose>
				<xsl:when test="$nmlVal='Normal' and $negIndStr='true'">
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="'Abnormal'"/>
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$nmlVal"/>
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
		</interpretationCode>
	</xsl:template>
	
	<xsl:template name="ROS_para">
		<xsl:param name="trim_para"/>
		<xsl:param name="trim_age"/>
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<!-- Result Observation templates -->
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE"/>
				<!-- IHE simple result observation -->
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.5" assigningAuthorityName="IHE"/>
				<!-- IHE Pregnancy Observation -->
				<id nullFlavor="NI"/>
				<code code="11636-8" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="BIRTHS LIVE (REPORTED)">
					<originalText>
						<xsl:value-of select="$trim_para/trim:label"/>
					</originalText>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_para)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="NI"/>
				<value xsi:type="INT">
					<xsl:attribute name="value"><xsl:value-of select="$trim_para/trim:ST"/></xsl:attribute>
				</value>
				<xsl:if test="$trim_age and $trim_age/trim:ST != ''">
					<entryRelationship typeCode="COMP">
						<!-- FIXED at "1" -->
						<sequenceNumber value="1"/>
						<observation classCode="OBS" moodCode="EVN">
							<code code="445518008" codeSystem="2.16.840.1.113883.6.96" displayName="age at onset of clinical finding" codeSystemName="SNOMED">
								<originalText>
									<xsl:value-of select="$trim_age/trim:label"/>
								</originalText>
							</code>
							<statusCode code="completed"/>
							<effectiveTime nullFlavor="NI"/>
							<value xsi:type="PQ" unit="a">
								<xsl:attribute name="value"><xsl:value-of select="$trim_age/trim:ST"/></xsl:attribute>
							</value>
						</observation>
					</entryRelationship>
				</xsl:if>
			</observation>
		</entry>
	</xsl:template>
	<xsl:template name="ROS_ageAtMenarche">
		<xsl:param name="trim_value"/>
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<!-- Result Observation template -->
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<id nullFlavor="NI"/>
				<code code="398700009" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT" displayName="age at menarche">
					<originalText>
						<xsl:value-of select="$trim_value/trim:label"/>
					</originalText>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_value)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="NI"/>
				<value xsi:type="PQ" unit="a">
					<xsl:attribute name="value"><xsl:value-of select="$trim_value/trim:ST"/></xsl:attribute>
				</value>
			</observation>
		</entry>
	</xsl:template>
	<xsl:template name="ROS_gravida">
		<xsl:param name="trim_value"/>
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<!-- Result Observation templates -->
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE"/>
				<!-- IHE simple result observation -->
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.5" assigningAuthorityName="IHE"/>
				<!-- IHE Pregnancy Observation -->
				<id nullFlavor="NI"/>
				<code code="11996-6" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Pregnancies">
					<originalText>
						<xsl:value-of select="$trim_value/trim:label"/>
					</originalText>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_value)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="NI"/>
				<value xsi:type="INT">
					<xsl:attribute name="value"><xsl:value-of select="$trim_value/trim:ST"/></xsl:attribute>
				</value>
			</observation>
		</entry>
	</xsl:template>
	<xsl:template name="ROS_menopausalLastDate">
		<xsl:param name="trim_entry"/>
		<xsl:param name="trim_entry_date" select="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Date']/trim:ST"/>
		<xsl:param name="trim_entry_month" select="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Month']/trim:ST"/>
		<xsl:param name="trim_entry_year" select="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Year']/trim:ST"/>
		<xsl:param name="trim_entry_unknown" select="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Unknown date status']/trim:SETCE"/>
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<!-- Result Observation template -->
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<id nullFlavor="NI"/>
				<code code="21840007" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT" displayName="date of last menstrual period">
					<originalText>
						<xsl:value-of select="$trim_entry/trim:act/trim:title/trim:ST"/>
					</originalText>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_entry)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<xsl:choose>
					<xsl:when test="$trim_entry_date!='' or $trim_entry_month!='' or $trim_entry_year!=''">
						<effectiveTime>
							<xsl:attribute name="value"><xsl:if test="$trim_entry_year!=''"><xsl:value-of select="$trim_entry_year"/></xsl:if><xsl:if test="$trim_entry_month!=''"><xsl:value-of select="$trim_entry_month"/></xsl:if><xsl:if test="$trim_entry_date!=''"><xsl:value-of select="$trim_entry_date"/></xsl:if></xsl:attribute>
						</effectiveTime>
						<value xsi:type="ST" nullFlavor="NI"/>
					</xsl:when>
					<xsl:otherwise>
						<effectiveTime nullFlavor="NI"/>
						<value xsi:type="ST">
							<xsl:value-of select="$trim_entry_unknown"/>
						</value>
					</xsl:otherwise>
				</xsl:choose>
			</observation>
		</entry>
	</xsl:template>
	
	
</xsl:stylesheet>