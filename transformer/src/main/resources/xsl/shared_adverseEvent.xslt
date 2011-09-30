<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<xsl:template name="adverseEvent_section">
		<xsl:param name="trim_toxicities"/>
		<xsl:param name="trim_priorToxicities"/>
		<xsl:if test="$trim_toxicities or trim_priorToxicities">
			<component>
				<!--Allergies/Adverse Reactions-->
				<section>
					<templateId root="2.16.840.1.113883.10.20.1.2" assigningAuthorityName="HL7 CCD"/>
					<!--Allergies/Reactions section template-->
					<code code="48765-2" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Allergies, adverse reactions, alerts"/>
					<title>Allergies and Adverse Reactions</title>
					<text/>
					<xsl:if test="$trim_toxicities">
						<xsl:call-template name="Toxicity_entry">
							<xsl:with-param name="toxAct" select="$trim_toxicities/trim:act"/>
						</xsl:call-template>
					</xsl:if>
					<xsl:if test="$trim_priorToxicities">
						<xsl:call-template name="PriToxicity_entry">
							<xsl:with-param name="toxAct" select="$trim_priorToxicities/trim:act"/>
						</xsl:call-template>
					</xsl:if>
				</section>
			</component>
		</xsl:if>
	</xsl:template>
	<xsl:template name="Toxicity_entry">
		<xsl:param name="toxAct"/>
		<entry typeCode="DRIV">
			<act classCode="ACT" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="HL7 CCD"/>
				<id root="36e3e930-7b14-11db-9fe1-0800200c9a66"/>
				<code nullFlavor="NA"/>
				<entryRelationship typeCode="SUBJ">
					<observation classCode="OBS" moodCode="EVN">
						<templateId root="2.16.840.1.113883.10.20.1.18" assigningAuthorityName="HL7 CCD"/>
						<id root="4adc1020-7b14-11db-9fe1-0800200c9a66"/>
						<code code="281647001" codeSystem="2.16.840.1.113883.6.96" displayName="Toxicity" codeSystemName="SNOMED CT">
							<originalText>Toxicities</originalText>
						</code>
						<text/>
						<statusCode code="completed"/>
						<effectiveTime nullFlavor="UNK"/>
						<xsl:for-each select="$toxAct/trim:relationship">
							<entryRelationship typeCode="SUBJ">
								<xsl:call-template name="Tox_Obs">
									<xsl:with-param name="trimAct" select="current()/trim:act"/>
								</xsl:call-template>
							</entryRelationship>
						</xsl:for-each>
					</observation>
				</entryRelationship>
			</act>
		</entry>
	</xsl:template>
	<xsl:template name="Tox_Obs">
		<xsl:param name="trimAct"/>
		<xsl:param name="toxSts" select="$trimAct/trim:observation/trim:value/trim:CE/trim:displayName"/>
		<xsl:param name="toxName" select="$trimAct/trim:title/trim:ST"/>
		<observation classCode="OBS" moodCode="EVN">
			<xsl:choose>
				<xsl:when test="$toxSts='No'">
					<xsl:attribute name="negationInd">true</xsl:attribute>
				</xsl:when>
				<xsl:when test="$toxSts='Not assessed'">
					<xsl:attribute name="nullFlavor">NASK</xsl:attribute>
				</xsl:when>
			</xsl:choose>
			<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
			<statusCode code="completed"/>
			<value>
				<xsl:call-template name="translateCode">
					<xsl:with-param name="trim_dispNm" select="$toxName"/>
				</xsl:call-template>
			</value>
			<xsl:choose>
				<xsl:when test="$toxSts='Yes'">
					<xsl:for-each select="$trimAct/trim:relationship[@name='subToxicity'][@enabled='true' or not(@enabled)]/trim:act">
						<xsl:variable name="subToxTitle" select="current()/trim:title/trim:ST"/>
						<xsl:variable name="subToxCode" select="current()/trim:code/trim:CD/trim:code"/>
						<xsl:variable name="effcTime" select="current()/trim:effectiveTime/trim:IVL_TS"/>
						<xsl:variable name="lowTm" select="$effcTime/trim:low/trim:TS/trim:value"/>
						<xsl:variable name="highTm" select="$effcTime/trim:high/trim:TS/trim:value"/>
						<entryRelationship typeCode="MFST">
							<observation classCode="OBS" moodCode="EVN">
								<templateId root="2.16.840.1.113883.10.20.1.54" assigningAuthorityName="CCD"/>
								<!--Reaction observation template -->
								<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
								<statusCode code="completed"/>
								<!-- create effectiveTime -->
								<xsl:call-template name="make_effectiveTime_lowHigh">
									<xsl:with-param name="lowTm" select="$lowTm"/>
									<xsl:with-param name="highTm" select="$highTm"/>
								</xsl:call-template>
								<!--create value-->
								<value xsi:type="CD">
									<xsl:choose>
										<xsl:when test="string-length($subToxCode)>0">
											<xsl:attribute name="code"><xsl:value-of select="$subToxCode"/></xsl:attribute>
											<xsl:attribute name="codeSystem">2.16.840.1.113883.6.163</xsl:attribute>
											<xsl:attribute name="codeSystemName">MedDRA</xsl:attribute>
										</xsl:when>
										<xsl:otherwise>
											<xsl:attribute name="nullFlavor">OTH</xsl:attribute>
										</xsl:otherwise>
									</xsl:choose>
									<originalText>
										<xsl:value-of select="$subToxTitle"/>
									</originalText>
								</value>
								<xsl:for-each select="current()/trim:observation/trim:value">
									<xsl:if test="trim:label != 'Status'">
										<xsl:call-template name="subTox_obs">
											<xsl:with-param name="valuedObs" select="current()"/>
										</xsl:call-template>
									</xsl:if>
								</xsl:for-each>
								<xsl:for-each select="current()/trim:relationship[@name='additionalQuestions'][@enabled='true' or not(@enabled)]/trim:act">
									<entryRelationship typeCode="REFR">
										<act classCode="ACT" moodCode="EVN">
											<code code="405672008" displayName="direct questioning" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT">
												<originalText>Additional Questions</originalText>
											</code>
											<xsl:for-each select="trim:observation/trim:value">
												<xsl:call-template name="assert_obs">
													<xsl:with-param name="valuedObs" select="current()"/>
												</xsl:call-template>
											</xsl:for-each>
										</act>
									</entryRelationship>
								</xsl:for-each>
							</observation>
						</entryRelationship>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</observation>
	</xsl:template>
	<xsl:template name="subTox_obs">
		<xsl:param name="valuedObs"/>
		<xsl:variable name="obsNm" select="$valuedObs/trim:label"/>
		<xsl:variable name="codedObsVal">
			<xsl:choose>
				<xsl:when test="$valuedObs[trim:CE]">
					<xsl:variable name="val" select="$valuedObs/trim:CE/trim:displayName"/>
					<xsl:variable name="valVs" select="$valuedObs/trim:valueSet"/>
					<xsl:choose>
						<xsl:when test="number($val) = $val ">
							<xsl:call-template name="getLabel">
								<xsl:with-param name="trimNode" select="/trim:trim"/>
								<xsl:with-param name="vsName" select="$valVs"/>
								<xsl:with-param name="dispNm" select="$val"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$val"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<entryRelationship typeCode="SUBJ">
			<observation classCode="OBS" moodCode="EVN">
				<!-- simple observation -->
				<xsl:choose>
					<xsl:when test="$obsNm='Resolved'">
						<xsl:choose>
							<xsl:when test="$codedObsVal='No'">
								<xsl:attribute name="negationInd">true</xsl:attribute>
							</xsl:when>
							<xsl:when test="$codedObsVal='Not assessed'">
								<xsl:attribute name="nullFlavor">NASK</xsl:attribute>
							</xsl:when>
						</xsl:choose>
						<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
						<!-- assertion pattern -->
						<id nullFlavor="NI"/>
						<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
						<statusCode code="completed"/>
						<effectiveTime nullFlavor="UNK"/>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="trim_dispNm" select="$obsNm"/>
							</xsl:call-template>
						</value>
					</xsl:when>
					<xsl:otherwise>
						<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
						<id nullFlavor="NI"/>
						<code>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$obsNm"/>
							</xsl:call-template>
						</code>
						<statusCode code="completed"/>
						<effectiveTime nullFlavor="UNK"/>
						<xsl:choose>
							<xsl:when test="$valuedObs[trim:CE]">
								<value>
									<xsl:call-template name="translateCode">
										<xsl:with-param name="trim_dispNm" select="$codedObsVal"/>
									</xsl:call-template>
								</value>
							</xsl:when>
							<xsl:when test="$valuedObs[trim:ST]">
								<value xsi:type="ST">
									<xsl:value-of select="$valuedObs/trim:ST"/>
								</value>
							</xsl:when>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>
			</observation>
		</entryRelationship>
	</xsl:template>
	<xsl:template name="assert_obs">
		<xsl:param name="valuedObs"/>
		<xsl:variable name="assert" select="$valuedObs/trim:CE/trim:displayName"/>
		<xsl:variable name="asstValue" select="$valuedObs/trim:label"/>
		<entryRelationship typeCode="COMP">
			<observation classCode="OBS" moodCode="EVN">
				<xsl:choose>
					<xsl:when test="$assert='No'">
						<xsl:attribute name="negationInd">true</xsl:attribute>
					</xsl:when>
					<xsl:when test="$assert='Not assessed'">
						<xsl:attribute name="nullFlavor">NASK</xsl:attribute>
					</xsl:when>
				</xsl:choose>
				<!-- simple observation -->
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<id nullFlavor="NI"/>
				<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="UNK"/>
				<value>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="$asstValue"/>
					</xsl:call-template>
				</value>
			</observation>
		</entryRelationship>
	</xsl:template>
	<xsl:template name="PriToxicity_entry">
		<xsl:param name="toxAct"/>
		<entry typeCode="DRIV">
			<act classCode="ACT" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="HL7 CCD"/>
				<id root="36e3e930-7b14-11db-9fe1-0800200c9a66"/>
				<code nullFlavor="NA"/>
				<entryRelationship typeCode="SUBJ">
					<observation classCode="OBS" moodCode="EVN">
						<templateId root="2.16.840.1.113883.10.20.1.18"/>
						<!--Alert/Adverse Event template.-->
						<id root="4adc1020-7b14-11db-9fe1-0800200c9a66"/>
						<code code="281647001" codeSystem="2.16.840.1.113883.6.96" displayName="Toxicity" codeSystemName="SNOMED CT">
							<originalText>Prior AE Reporting</originalText>
						</code>
						<text/>
						<statusCode code="completed"/>
						<effectiveTime nullFlavor="UNK"/>
						<xsl:for-each select="$toxAct/trim:relationship[@enabled='true' or not(@enabled)]">
							<entryRelationship typeCode="SUBJ">
								<xsl:call-template name="PriTox_Obs">
									<xsl:with-param name="trimAct" select="current()/trim:act"/>
								</xsl:call-template>
							</entryRelationship>
						</xsl:for-each>
					</observation>
				</entryRelationship>
			</act>
		</entry>
	</xsl:template>
	<xsl:template name="PriTox_Obs">
		<xsl:param name="trimAct"/>
		<xsl:param name="toxSts" select="$trimAct/trim:observation/trim:value[trim:label='Status']/trim:CE/trim:displayName"/>
		<xsl:param name="toxName" select="$trimAct/trim:title/trim:ST"/>
		<xsl:param name="toxCode" select="$trimAct/trim:code/trim:CD/trim:code"/>
		<xsl:variable name="effcTime" select="$trimAct/trim:effectiveTime/trim:IVL_TS"/>
		<xsl:variable name="lowTm" select="$effcTime/trim:low/trim:TS/trim:value"/>
		<xsl:variable name="highTm" select="$effcTime/trim:high/trim:TS/trim:value"/>
		<observation classCode="OBS" moodCode="EVN">
			<xsl:choose>
				<xsl:when test="$toxSts='No'">
					<xsl:attribute name="negationInd">true</xsl:attribute>
				</xsl:when>
				<xsl:when test="$toxSts='Not assessed'">
					<xsl:attribute name="nullFlavor">NASK</xsl:attribute>
				</xsl:when>
			</xsl:choose>
			<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
			<statusCode code="completed"/>
			<!-- create effectiveTime -->
			<xsl:call-template name="make_effectiveTime_lowHigh">
				<xsl:with-param name="lowTm" select="$lowTm"/>
				<xsl:with-param name="highTm" select="$highTm"/>
			</xsl:call-template>
			<value xsi:type="CD">
				<xsl:choose>
					<xsl:when test="string-length($toxCode)>0">
						<xsl:attribute name="code"><xsl:value-of select="$toxCode"/></xsl:attribute>
						<xsl:attribute name="codeSystem">2.16.840.1.113883.6.163</xsl:attribute>
						<xsl:attribute name="codeSystemName">MedDRA</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="nullFlavor">OTH</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<originalText>
					<xsl:value-of select="$toxName"/>
				</originalText>
			</value>
			<xsl:for-each select="$trimAct/trim:observation/trim:value">
				<xsl:if test="trim:label != 'Status'">
					<xsl:call-template name="subTox_obs">
						<xsl:with-param name="valuedObs" select="current()"/>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="$trimAct/trim:relationship[@name='additionalQuestions'][@enabled='true' or not(@enabled)]/trim:act">
				<entryRelationship typeCode="REFR">
					<act classCode="ACT" moodCode="EVN">
						<code code="405672008" displayName="direct questioning" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT">
							<originalText>Additional Questions</originalText>
						</code>
						<xsl:for-each select="trim:observation/trim:value">
							<xsl:call-template name="assert_obs">
								<xsl:with-param name="valuedObs" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</act>
				</entryRelationship>
			</xsl:for-each>
		</observation>
	</xsl:template>
</xsl:stylesheet>
