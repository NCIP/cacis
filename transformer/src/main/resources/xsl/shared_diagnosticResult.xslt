<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<xsl:template name="diagnosticResult_section">
		<xsl:param name="trim_geneticTesting"/>
		<xsl:param name="trim_pastHistory"/>
		<xsl:param name="trim_gailCalculation"/>
		<xsl:param name="trim_indexImaging"/>
		<xsl:param name="trim_stagingStatus"/>
		<xsl:param name="trim_metastasisEvidence"/>
		<xsl:param name="trim_metastaticWorkups"/>
		<xsl:param name="trim_nodes"/>
		<xsl:param name="trim_positiveNodes"/>
		<xsl:param name="trim_totExamedNodes"/>
		<xsl:if test="$trim_geneticTesting or $trim_pastHistory or $trim_gailCalculation or $trim_indexImaging or $trim_stagingStatus or $trim_metastasisEvidence or $trim_metastaticWorkups or $trim_nodes or $trim_positiveNodes or $trim_totExamedNodes">
			<component>
				<section>
					<!-- coded result section -->
					<templateId root="1.3.6.1.4.1.19376.1.5.3.1.3.28" assigningAuthorityName="IHE PCC"/>
					<!-- diagnostic results section -->
					<templateId root="2.16.840.1.113883.3.88.11.83.122" assigningAuthorityName="HITSP C83"/>
					<id nullFlavor="NI"/>
					<code code="30954-2" displayName="Relevant diagnostic tests/laboratory data" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
					<title>Diagnostic Results</title>
					<text/>
					<xsl:if test="$trim_geneticTesting">
						<xsl:for-each select="$trim_geneticTesting/trim:act">
							<xsl:call-template name="diagnosticResult_geneticTesting">
								<xsl:with-param name="trim_entry" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_pastHistory">
						<xsl:for-each select="$trim_pastHistory/trim:act/trim:observation/trim:value[trim:label = 'Past number of breast biopsies:']">
							<xsl:call-template name="diagnosticResult_biopsiesOfBreast">
								<xsl:with-param name="trim_pastBiopsies" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_gailCalculation">
						<xsl:for-each select="$trim_gailCalculation/trim:act/trim:observation/trim:value">
							<xsl:call-template name="diagnosticResult_gailCalculation">
								<xsl:with-param name="trim_entry" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_indexImaging">
						<xsl:for-each select="$trim_indexImaging/trim:act/trim:observation">
							<xsl:call-template name="diagnosticResult_indexImaging">
								<xsl:with-param name="trim_entry" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_stagingStatus">
						<xsl:for-each select="$trim_stagingStatus">
							<xsl:call-template name="diagnosticResult_stagingStatusOrganizer">
								<xsl:with-param name="trim_relationship" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_metastasisEvidence">
						<xsl:for-each select="$trim_metastasisEvidence">
							<xsl:call-template name="diagnosticResult_metastasisEvidenceOrganizer">
								<xsl:with-param name="trim_relationship" select="$trim_metastasisEvidence"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_metastaticWorkups">
						<xsl:for-each select="$trim_metastaticWorkups/trim:act/trim:relationship[@name='metastasisWorkUp']">
							<xsl:call-template name="diagnosticResult_metastaticWorkupOrganizer">
								<xsl:with-param name="trim_relationship" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_nodes">
						<xsl:for-each select="$trim_nodes">
							<xsl:call-template name="diagnosticResult_lymphNodes">
								<xsl:with-param name="trim_relationship" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_positiveNodes">
						<xsl:for-each select="$trim_positiveNodes">
							<xsl:call-template name="diagnosticResult_positiveNode">
								<xsl:with-param name="trim_relationship" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					
					<xsl:if test="$trim_totExamedNodes">
						<xsl:for-each select="$trim_totExamedNodes">
							<xsl:call-template name="diagnosticResult_totExaminedNodes">
								<xsl:with-param name="trim_relationship" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>

				</section>
			</component>
		</xsl:if>
	</xsl:template>
	<xsl:template name="diagnosticResult_positiveNode">
		<xsl:param name="trim_relationship"/>
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<id nullFlavor="NI"/>
				<code xsi:type="CD" code="252105006" displayName="number of metastases" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
					<originalText>Total positive nodes</originalText>
					<qualifier>
						<name code="272741003" displayName="laterality" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT"/>
						<xsl:choose>
							<xsl:when test="$trim_relationship/@name = 'LeftTotalPositiveNodes'">
								<value code="7771000" displayName="left" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT"/>
							</xsl:when>
							<xsl:when test="$trim_relationship/@name = 'RightTotalPositiveNodes'">
								<value code="24028007" displayName="right" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT"/>
							</xsl:when>
						</xsl:choose>
					</qualifier>
					<qualifier>
						<name code="116686009" displayName="has specimen" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT"/>
						<value code="309079007" displayName="lymph node biopsy sample" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT"/>
					</qualifier>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_relationship)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="UNK"/>
				<value xsi:type="INT">
					<xsl:attribute name="value"><xsl:value-of select="$trim_relationship/trim:act/trim:observation/trim:value/trim:INT/trim:value"/></xsl:attribute>
				</value>
			</observation>
		</entry>
	</xsl:template>
	
	<xsl:template name="diagnosticResult_totExaminedNodes">
		<xsl:param name="trim_relationship"/>
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<id nullFlavor="NI"/>
				<code code="444025001" displayName="number of lymph nodes examined" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
					<originalText>Total examined nodes</originalText>
					<qualifier>
						<name code="272741003" displayName="laterality" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT"/>
						<xsl:choose>
							<xsl:when test="$trim_relationship/@name = 'LeftTotalExaminedNodes'">
								<value code="7771000" displayName="left" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT"/>
							</xsl:when>
							<xsl:when test="$trim_relationship/@name = 'RightTotalExaminedNodes'">
								<value code="24028007" displayName="right" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT"/>
							</xsl:when>
						</xsl:choose>
					</qualifier>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_relationship)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="UNK"/>
				<value xsi:type="INT">
					<xsl:attribute name="value"><xsl:value-of select="$trim_relationship/trim:act/trim:observation/trim:value/trim:INT/trim:value"/></xsl:attribute>
				</value>
			</observation>
		</entry>
	</xsl:template>
	
	<xsl:template name="diagnosticResult_lymphNodes">
		<xsl:param name="trim_relationship"/>
		<xsl:variable name="trim_detectionMethodValue" select="$trim_relationship/trim:act/trim:relationship[@name='detectionMethod']/trim:act/trim:observation/trim:value"/>
		<entry typeCode="DRIV">
			<procedure classCode="PROC" moodCode="EVN">
				<!-- procedure template -->
				<templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
				<id nullFlavor="NI"/>
				<code>
					<!-- TODO: This is a result of a TRIM bug -->
					<xsl:choose>
						<xsl:when test="$trim_relationship[@name = 'LeftAxillaryLymphNodes' or @name = 'RightAxillaryLymphNodes']">
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="'Axilary nodes'"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$trim_relationship/trim:act/trim:title/trim:ST"/>
							</xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
					<qualifier>
						<name code="272741003" displayName="laterality" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT"/>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$trim_relationship/trim:act/trim:relationship[@name='laterality']/trim:act/trim:title/trim:ST"/>
							</xsl:call-template>
						</value>
					</qualifier>
					<qualifier>
						<name code="405813007" displayName="procedure site - Direct" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
							<originalText>Location</originalText>
						</name>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$trim_relationship/trim:act/trim:relationship[@name='location']/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
							</xsl:call-template>
						</value>
					</qualifier>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_relationship)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime xsi:type="IVL_TS">
					<low nullFlavor="UNK"/>
					<high nullFlavor="UNK"/>
				</effectiveTime>
				<entryRelationship typeCode="COMP">
					<procedure classCode="PROC" moodCode="EVN">
						<!-- procedure template -->
						<templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
						<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
						<templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
						<id nullFlavor="NI"/>
						<code code="284427004" displayName="examination of lymph nodes" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT"/>
						<text>
							<reference nullFlavor="NI"/>
						</text>
						<statusCode code="completed"/>
						<effectiveTime xsi:type="IVL_TS">
							<low nullFlavor="UNK"/>
							<high nullFlavor="UNK"/>
						</effectiveTime>
						<xsl:if test="$trim_detectionMethodValue">
							<methodCode>
								<xsl:choose>
									<xsl:when test="$trim_detectionMethodValue/trim:CE/trim:displayName">
										<xsl:call-template name="translateCode">
											<xsl:with-param name="noXSI" select="'true'"/>
											<xsl:with-param name="trim_dispNm" select="$trim_detectionMethodValue/trim:CE/trim:displayName"/>
											<xsl:with-param name="overriddenOriginalText">
												<xsl:value-of select="$trim_detectionMethodValue/trim:label"/>
												<xsl:text>: </xsl:text>
												<xsl:value-of select="$trim_detectionMethodValue/trim:CE/trim:displayName"/>
											</xsl:with-param>
										</xsl:call-template>
									</xsl:when>
									<xsl:otherwise>
										<xsl:attribute name="nullFlavor">NI</xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
							</methodCode>
						</xsl:if>
						<xsl:apply-templates select="$trim_relationship/trim:act/trim:relationship[not(@enabled) or @enabled = 'true']" mode="diagnosticResult_lymphNodeExaminations"/>
					</procedure>
				</entryRelationship>
			</procedure>
		</entry>
	</xsl:template>
	<xsl:template match="@*|node()" mode="diagnosticResult_lymphNodeExaminations"/>
	<xsl:template match="trim:relationship[@name='detectionMethodSize']" mode="diagnosticResult_lymphNodeExaminations">
		<xsl:variable name="cda_value">
			<value>
				<xsl:call-template name="translateCode">
					<xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
				</xsl:call-template>
			</value>
		</xsl:variable>
		<entryRelationship typeCode=" REFR">
			<observation classCode="OBS" moodCode="EVN" negationInd="false">
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:label"/>
					</xsl:call-template>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="UNK"/>
				<xsl:copy-of select="$cda_value"/>
				<xsl:if test="$cda_value/cda:value/@nullFlavor='OTH'">
					<xsl:variable name="trim_pq" select="trim:act/trim:observation/trim:value[trim:PQ]"/>
					<entryRelationship typeCode="SPRT">
						<observation classCode="OBS" moodCode="EVN" negationInd="false">
							<!-- Result Observation template -->
							<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
							<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
							<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
							<id nullFlavor="NI"/>
							<code xsi:type="CD" code="396791005" displayName="tumor size, largest metastasis" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT"/>
							<text>
								<reference>
									<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_pq)"/></xsl:attribute>
								</reference>
							</text>
							<statusCode code="completed"/>
							<effectiveTime nullFlavor="UNK"/>
							<value xsi:type="PQ">
								<xsl:attribute name="unit"><xsl:value-of select="$trim_pq/trim:unit"/></xsl:attribute>
								<xsl:attribute name="value"><xsl:value-of select="$trim_pq/trim:value"/></xsl:attribute>
							</value>
						</observation>
					</entryRelationship>
				</xsl:if>
			</observation>
		</entryRelationship>
	</xsl:template>
	<xsl:template match="trim:relationship[@name='extention']" mode="diagnosticResult_lymphNodeExaminations">
		<entryRelationship typeCode="REFR">
			<observation classCode="OBS" moodCode="EVN" nullFlavor="NI">
				<templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
				<id nullFlavor="NI"/>
				<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime>
					<low nullFlavor="UNK"/>
				</effectiveTime>
				<value>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:displayName"/>
						<xsl:with-param name="overriddenOriginalText">
							<xsl:value-of select="trim:act/trim:observation/trim:value/trim:label"/>
							<xsl:text>: </xsl:text>
							<xsl:value-of select="trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
						</xsl:with-param>
					</xsl:call-template>
				</value>
			</observation>
		</entryRelationship>
	</xsl:template>
	<xsl:template match="trim:relationship[@name='positiveNodes' or @name='examinedNodes']" mode="diagnosticResult_lymphNodeExaminations">
		<entryRelationship typeCode="REFR">
			<observation classCode="OBS" moodCode="EVN" negationInd="false">
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:label"/>
					</xsl:call-template>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="UNK"/>
				<value xsi:type="INT">
					<xsl:attribute name="value"><xsl:value-of select="trim:act/trim:observation/trim:value/trim:INT/trim:value"/></xsl:attribute>
				</value>
			</observation>
		</entryRelationship>
	</xsl:template>
	<xsl:template match="trim:relationship[@name='detectionMethodCellType']" mode="diagnosticResult_lymphNodeExaminations">
		<xsl:variable name="trim_setce" select="trim:act/trim:observation/trim:value/trim:SETCE"/>
		<entryRelationship typeCode="REFR">
			<observation classCode="OBS" moodCode="EVN" negationInd="false">
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<id nullFlavor="NI"/>
				<code xsi:type="CD" code="252987004" displayName="tumor cells" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
					<qualifier>
						<name code="246458004" displayName="distribution pattern" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT"/>
					</qualifier>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="UNK"/>
				<value xsi:type="CD">
					<xsl:choose>
						<xsl:when test="$trim_setce/@xsi:type = 'xs:string' and $trim_setce = 'Not Reported'">
							<xsl:attribute name="nullFlavor">UNK</xsl:attribute>
							<originalText>
								<xsl:value-of select="$trim_setce"/>
							</originalText>
						</xsl:when>
						<xsl:when test="$trim_setce/@xsi:type = 'xs:string'">
							<xsl:attribute name="nullFlavor">OTH</xsl:attribute>
							<originalText>
								<xsl:value-of select="$trim_setce"/>
							</originalText>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$trim_setce/trim:displayName"/>
							</xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
				</value>
			</observation>
		</entryRelationship>
	</xsl:template>
	<xsl:template name="diagnosticResult_metastaticWorkupOrganizer">
		<xsl:param name="trim_relationship"/>
		<xsl:variable name="organizerDisplay">Metastatic Work-up: <xsl:value-of select="$trim_relationship/trim:act/trim:title/trim:ST"/>
		</xsl:variable>
		<xsl:if test="$trim_relationship/trim:act/trim:relationship[trim:act/trim:observation/trim:value/trim:CE]">
			<entry>
				<!-- Metastatic Work-up: bone -->
				<organizer classCode="CLUSTER" moodCode="EVN">
					<templateId root="2.16.840.1.113883.10.20.1.32" assigningAuthorityName="CCD"/>
					<id nullFlavor="NI"/>
					<code>
						<xsl:call-template name="translateCode">
							<xsl:with-param name="noXSI" select="'true'"/>
							<xsl:with-param name="trim_dispNm" select="$organizerDisplay"/>
						</xsl:call-template>
					</code>
					<statusCode code="completed"/>
					<xsl:apply-templates select="$trim_relationship/trim:act/trim:relationship[trim:act/trim:observation/trim:value/trim:CE]" mode="diagnosticResult_metastaticWorkup"/>
				</organizer>
			</entry>
		</xsl:if>
	</xsl:template>
	<xsl:template match="@*|node()" mode="diagnosticResult_metastaticWorkup"/>
	<xsl:template match="trim:relationship" mode="diagnosticResult_metastaticWorkup">
		<component>
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE"/>
				<id nullFlavor="NI"/>
				<xsl:call-template name="translateValueSet">
					<xsl:with-param name="trim_valueSet" select="'metastaticWorkUp'"/>
					<xsl:with-param name="elementType" select="'code'"/>
					<xsl:with-param name="trim_dispNm" select="trim:act/trim:title/trim:ST"/>
				</xsl:call-template>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime>
					<low nullFlavor="UNK"/>
				</effectiveTime>
				<value>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
					</xsl:call-template>
				</value>
			</observation>
		</component>
	</xsl:template>
	<xsl:template name="diagnosticResult_metastasisEvidenceOrganizer">
		<xsl:param name="trim_relationship"/>
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<xsl:variable name="evidenceValue" select="$trim_relationship/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
				<xsl:choose>
					<xsl:when test="$evidenceValue = 'Yes'">
						<xsl:attribute name="negationInd">false</xsl:attribute>
					</xsl:when>
					<xsl:when test="$evidenceValue = 'No'">
						<xsl:attribute name="negationInd">true</xsl:attribute>
					</xsl:when>
					<xsl:when test="$evidenceValue = 'Not reported'">
						<xsl:attribute name="nullFlavor">NI</xsl:attribute>
					</xsl:when>
				</xsl:choose>
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE"/>
				<id nullFlavor="NI"/>
				<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_relationship)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime>
					<low nullFlavor="UNK"/>
				</effectiveTime>
				<xsl:choose>
					<xsl:when test="$evidenceValue != 'Yes' and $evidenceValue != 'No' and $evidenceValue != 'Not reported'">
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="trim_dispNm" select="$trim_relationship/trim:act/trim:title/trim:ST"/>
							</xsl:call-template>
							<qualifier>
								<name code="246103008" displayName="certainty" codeSystem="2.16.840.1.113883.6.96"/>
								<value>
									<xsl:call-template name="translateCode">
										<xsl:with-param name="trim_dispNm" select="$evidenceValue"/>
									</xsl:call-template>
								</value>
							</qualifier>
						</value>
					</xsl:when>
					<xsl:otherwise>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="trim_dispNm" select="$trim_relationship/trim:act/trim:title/trim:ST"/>
							</xsl:call-template>
						</value>
					</xsl:otherwise>
				</xsl:choose>
			</observation>
		</entry>
	</xsl:template>
	<xsl:template name="diagnosticResult_stagingStatusOrganizer">
		<xsl:param name="trim_relationship"/>
		<entry typeCode="DRIV">
			<organizer classCode="CLUSTER" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.32" assigningAuthorityName="CCD"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="$trim_relationship/trim:act/trim:title/trim:ST"/>
						<xsl:with-param name="noXSI" select="'true'"/>
					</xsl:call-template>
				</code>
				<statusCode code="completed"/>
				<xsl:for-each select="$trim_relationship/trim:act/trim:observation/trim:value">
					<xsl:call-template name="diagnosticResult_stagingStatusObservation">
						<xsl:with-param name="trim_value" select="current()"/>
					</xsl:call-template>
				</xsl:for-each>
			</organizer>
		</entry>
	</xsl:template>
	<xsl:template name="diagnosticResult_stagingStatusObservation">
		<xsl:param name="trim_value"/>
		<component typeCode="COMP">
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="$trim_value/trim:label"/>
					</xsl:call-template>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_value)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="NI"/>
				<value>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="$trim_value/trim:CE/trim:displayName"/>
					</xsl:call-template>
				</value>
			</observation>
		</component>
	</xsl:template>
	<xsl:template name="diagnosticResult_indexImaging">
		<xsl:param name="trim_entry"/>
		<entry>
			<observation classCode="OBS" moodCode="EVN">
				<!-- simple observation -->
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<id nullFlavor="NI"/>
				<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_entry)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="NI"/>
				<value>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="$trim_entry/trim:value/trim:CE/trim:displayName"/>
					</xsl:call-template>
				</value>
				<methodCode>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$trim_entry/trim:value/trim:label"/>
					</xsl:call-template>
				</methodCode>
			</observation>
		</entry>
	</xsl:template>
	<xsl:template name="diagnosticResult_geneticTesting">
		<xsl:param name="trim_entry"/>
		<entry>
			<observation classCode="OBS" moodCode="EVN">
				<!-- simple observation -->
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<!-- result observation -->
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<!-- result module -->
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="trim_dispNm" select="$trim_entry/trim:title/trim:ST"/>
						<xsl:with-param name="noXSI" select="true"/>
					</xsl:call-template>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_entry)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="NI"/>
				<xsl:choose>
					<xsl:when test="$trim_entry/trim:observation/trim:value/trim:SETCE/trim:displayName='Other'">
						<xsl:call-template name="translateOtherValueInCD">
							<xsl:with-param name="origTxt" select="$trim_entry/trim:observation/trim:value/trim:ST"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="trim_dispNm" select="$trim_entry/trim:observation/trim:value/trim:SETCE/trim:displayName"/>
							</xsl:call-template>
						</value>
					</xsl:otherwise>
				</xsl:choose>
			</observation>
		</entry>
	</xsl:template>
	<xsl:template name="diagnosticResult_biopsiesOfBreast">
		<xsl:param name="trim_pastBiopsies"/>
		<xsl:if test="$trim_pastBiopsies/trim:ST != ''">
			<entry typeCode="DRIV">
				<organizer classCode="CLUSTER" moodCode="EVN">
					<!-- result organizer -->
					<templateId root="2.16.840.1.113883.10.20.1.32" assigningAuthorityName="CCD"/>
					<id nullFlavor="NI"/>
					<code code="122548005" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT" displayName="biopsy of breast">
						<originalText>Biopsy of breast</originalText>
					</code>
					<statusCode code="completed"/>
					<xsl:call-template name="diagnosticResult_pastBiopsies">
						<xsl:with-param name="trim_entry" select="$trim_pastBiopsies"/>
					</xsl:call-template>
				</organizer>
			</entry>
		</xsl:if>
	</xsl:template>
	<xsl:template name="diagnosticResult_pastBiopsies">
		<xsl:param name="trim_entry"/>
		<component typeCode="COMP">
			<observation classCode="OBS" moodCode="EVN">
				<!-- Result Observation templates -->
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<!-- IHE simple result observation -->
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE"/>
				<id nullFlavor="NI"/>
				<code xsi:type="CD" code="122548005" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT" displayName="biopsy of breast">
					<originalText>
						<xsl:value-of select="$trim_entry/trim:label"/>
					</originalText>
					<qualifier>
						<name code="246444001" displayName="number of biopsies" codeSystem="2.16.840.1.113883.6.96"/>
					</qualifier>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_entry)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="NI"/>
				<value xsi:type="INT">
					<xsl:attribute name="value"><xsl:value-of select="$trim_entry/trim:ST"/></xsl:attribute>
				</value>
			</observation>
		</component>
		<xsl:if test="$trim_entry/trim:SETCE/trim:displayName = 'Atypia'">
			<component typeCode="COMP">
				<!--- presence of atypia in one or more of the breast biopsies reported above -->
				<observation classCode="OBS" moodCode="EVN" negationInd="false">
					<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
					<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
					<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
					<id nullFlavor="NI"/>
					<code code="122548005" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT" displayName="biopsy of breast">
						<originalText>Biopsy result</originalText>
					</code>
					<text>
						<reference>
							<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_entry/trim:SETCE)"/></xsl:attribute>
						</reference>
					</text>
					<statusCode code="completed"/>
					<effectiveTime nullFlavor="NI"/>
					<value>
						<xsl:call-template name="translateCode">
							<xsl:with-param name="trim_dispNm" select="$trim_entry/trim:SETCE/trim:displayName"/>
						</xsl:call-template>
					</value>
				</observation>
			</component>
		</xsl:if>
	</xsl:template>
	<xsl:template name="diagnosticResult_gailCalculation">
		<xsl:param name="trim_entry"/>
		<entry typeCode="DRIV">
			<observation classCode="OBS" moodCode="EVN">
				<!-- Result Observation templates -->
				<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
				<!-- IHE simple result observation -->
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE"/>
				<id nullFlavor="NI"/>
				<code code="102485007" codeSystem="2.16.840.1.113883.6.1" codeSystemName="SNOMED" displayName="Personal Risk Factors">
					<originalText>
						<xsl:value-of select="../../trim:title/trim:ST"/>
						<xsl:text> </xsl:text>
						<xsl:value-of select="$trim_entry/trim:label"/>
					</originalText>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_entry)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime>
					<xsl:choose>
						<xsl:when test="$trim_entry/trim:label = 'at 5 years;'">
							<low>
								<!-- Variables are used to calculate dateOfExam+5 years -->
								<xsl:variable name="dateOfExam" select="/trim:trim/trim:act/trim:relationship[@name='generalTab']/trim:act/trim:relationship[@name='dateOfExam']/trim:act/trim:observation/trim:value/trim:TS/trim:value"/>
								<xsl:variable name="newDateOfExam">
									<xsl:value-of select="number(substring($dateOfExam, 1, 4)) + 5"/>
									<xsl:value-of select="substring($dateOfExam, 5)"/>
								</xsl:variable>
								<xsl:attribute name="value"><xsl:value-of select="$newDateOfExam"/></xsl:attribute>
							</low>
						</xsl:when>
						<xsl:when test="$trim_entry/trim:label = 'lifetime'">
							<high nullFlavor="PINF"/>
						</xsl:when>
					</xsl:choose>
				</effectiveTime>
				<value xsi:type="PQ">
					<xsl:choose>
						<xsl:when test="contains($trim_entry/trim:ST, 'unknown')">
							<xsl:attribute name="nullFlavor">UNK</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="unit">%</xsl:attribute>
							<xsl:attribute name="value"><!-- XSLT1 does not contain a string replace function, so have to use a custom template that does the same thing instead --><xsl:call-template name="string-replace-all"><xsl:with-param name="text" select="$trim_entry/trim:ST"/><xsl:with-param name="replace" select="'%'"/><xsl:with-param name="by" select="''"/></xsl:call-template></xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
				</value>
			</observation>
		</entry>
	</xsl:template>
</xsl:stylesheet>
