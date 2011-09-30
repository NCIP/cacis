<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<xsl:template name="procedure_section">
		<xsl:param name="trim_menopausalStatus"/>
		<xsl:param name="trim_breastCancerSurgeries"/>
		<xsl:param name="trim_xrt"/>
		<xsl:param name="trim_chemoBiologic"/>
		<xsl:param name="trim_csProcedures"/>
		<!-- Clinical Staging Procedures -->
		<xsl:param name="trim_priorProcedures"/>
		<xsl:param name="trim_studies"/>
		<xsl:param name="trim_rightStudies"/>
		<xsl:if test="$trim_menopausalStatus or $trim_breastCancerSurgeries or $trim_xrt or $trim_chemoBiologic or $trim_studies or $trim_rightStudies or $trim_priorProcedures">
			<component>
				<section>
					<templateId root="2.16.840.1.113883.10.20.1.12" assigningAuthorityName="CCD"/>
					<templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.13.2.11" assigningAuthorityName="IHE"/>
					<templateId root="2.16.840.1.113883.3.88.11.83.145" assigningAuthorityName="HITSP"/>
					<code code="47519-4" codeSystem="2.16.840.1.113883.6.1" displayName="History of procedures"/>
					<title>Procedures</title>
					<text/>
					<xsl:if test="$trim_menopausalStatus">
						<xsl:for-each select="$trim_menopausalStatus//trim:act/trim:relationship[@name='bilateralOophorectomy' or @name='hysterectomy']">
							<xsl:call-template name="procedures_menopausalStatus">
								<xsl:with-param name="trim_entry" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<!-- if bilaterla Oophorectomy is no, then check RiskFactor Surgery -->
					<xsl:if test="$trim_menopausalStatus and $trim_menopausalStatus//trim:act/trim:relationship[@name='bilateralOophorectomy']/trim:act/trim:observation/trim:value/trim:CE/trim:displayName='No'">
						<xsl:if test="/trim:trim//trim:relationship[@name='riskFactor']/trim:act/trim:relationship[@name='surgery']/trim:act/trim:relationship[@name='oophorectomy']/trim:act/trim:observation/trim:value/trim:SETCE/trim:displayName">
							<xsl:variable name="ooph" select="/trim:trim//trim:relationship[@name='riskFactor']/trim:act/trim:relationship[@name='surgery']/trim:act/trim:relationship[@name='oophorectomy']"/>
							<xsl:call-template name="procedures_riskFactor">
								<xsl:with-param name="trim_entry" select="$ooph"/>
							</xsl:call-template>
						</xsl:if>
					</xsl:if>
					<xsl:if test="$trim_breastCancerSurgeries">
						<xsl:for-each select="$trim_breastCancerSurgeries/trim:act/trim:relationship[@name='Surgery']">
							<xsl:call-template name="procedures_breastCancerSurgery">
								<xsl:with-param name="trim_entry" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_xrt">
						<xsl:for-each select="$trim_xrt">
							<xsl:call-template name="procedures_xrt">
								<xsl:with-param name="trim_xrt" select="$trim_xrt"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_chemoBiologic">
						<xsl:for-each select="$trim_chemoBiologic">
							<xsl:call-template name="procedures_chemoBiologic">
								<xsl:with-param name="trim_chemoBiologic" select="$trim_chemoBiologic"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_studies">
						<xsl:variable name="studyTitle" select="string('Breast Studies')"/>
						<xsl:for-each select="$trim_studies/trim:act/trim:relationship[@enabled='true' or not(@enabled)]">
							<xsl:call-template name="procedures_studies">
								<xsl:with-param name="trim_relationship" select="current()"/>
								<xsl:with-param name="studyTitle" select="$studyTitle"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_rightStudies">
						<xsl:variable name="studyTitle" select="string('Right Breast Studies')"/>
						<xsl:for-each select="$trim_rightStudies/trim:act/trim:relationship[@enabled='true' or not(@enabled)]">
							<xsl:call-template name="procedures_studies">
								<xsl:with-param name="trim_relationship" select="current()"/>
								<xsl:with-param name="studyTitle" select="$studyTitle"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_csProcedures">
						<xsl:for-each select="$trim_csProcedures/trim:act/trim:relationship">
							<xsl:call-template name="procedures_procedures">
								<xsl:with-param name="trim_relationship" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="$trim_priorProcedures">
						<xsl:for-each select="$trim_priorProcedures/trim:act/trim:relationship[@name='priorProcedure']">
							<xsl:call-template name="procedures_priorProcedure">
								<xsl:with-param name="trim_relationship" select="current()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
				</section>
			</component>
		</xsl:if>
	</xsl:template>
	<xsl:template name="procedures_priorProcedure">
		<xsl:param name="trim_relationship"/>
		<entry typeCode="DRIV">
			<procedure classCode="PROC" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
				<id nullFlavor="NI"/>
				<xsl:variable name="procNm" select="$trim_relationship/trim:act/trim:observation/trim:value[trim:label='Procedure']/trim:ST"/>
				<xsl:variable name="custProNm">
					<xsl:text>Prior Procedure: </xsl:text>
					<xsl:value-of select="$procNm"/>
				</xsl:variable>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$procNm"/>
						<xsl:with-param name="overriddenOriginalText" select="$custProNm"/>
					</xsl:call-template>
					<qualifier>
						<name code="272741003" displayName="laterality" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
							<originalText>laterality</originalText>
						</name>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$trim_relationship/trim:act/trim:observation/trim:value[trim:label='Laterality']/trim:ST"/>
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
				<effectiveTime>
					<xsl:attribute name="value"><xsl:value-of select="$trim_relationship/trim:act/trim:effectiveTime/trim:TS/trim:value"/></xsl:attribute>
				</effectiveTime>
				<targetSiteCode>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$trim_relationship/trim:act/trim:observation/trim:value[trim:label='Location']/trim:ST"/>
					</xsl:call-template>
				</targetSiteCode>
				<xsl:call-template name="mapPerformer">
					<xsl:with-param name="trim_ST" select="$trim_relationship/trim:act/trim:observation/trim:value[trim:label='Surgeon']/trim:ST"/>
				</xsl:call-template>
			</procedure>
		</entry>
	</xsl:template>
	<xsl:template name="procedures_procedures">
		<xsl:param name="trim_relationship"/>
		<xsl:variable name="id" select="$trim_relationship/trim:act/trim:id/trim:II"/>
		<xsl:variable name="prcdDisp" select="$trim_relationship/trim:act/trim:title/trim:ST"/>
		<xsl:variable name="effTime" select="$trim_relationship/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
		<xsl:variable name="ltrVal" select="$trim_relationship/trim:act/trim:relationship[@name='laterality']/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
		<xsl:variable name="locVal" select="$trim_relationship/trim:act/trim:relationship[@name='laterality']/trim:act/trim:relationship[@name='location']/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
		<xsl:variable name="pfmer" select="$trim_relationship/trim:act/trim:participation[@name='surgeon']"/>
		<xsl:variable name="methd" select="$trim_relationship/trim:act/trim:observation/trim:methodCode/trim:CE/trim:displayName"/>
		<entry typeCode="DRIV">
			<procedure classCode="PROC" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
				<xsl:call-template name="make_II">
					<xsl:with-param name="II_root" select="$id/trim:root"/>
					<xsl:with-param name="II_ext" select="$id/trim:extension"/>
				</xsl:call-template>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$prcdDisp"/>
					</xsl:call-template>
					<qualifier>
						<name code="272741003" displayName="laterality" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
							<originalText>laterality</originalText>
						</name>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$ltrVal"/>
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
				<effectiveTime>
					<xsl:attribute name="value"><xsl:value-of select="$effTime"/></xsl:attribute>
				</effectiveTime>
				<xsl:if test="string-length($methd)>0">
					<methodCode>
						<xsl:call-template name="translateCode">
							<xsl:with-param name="noXSI" select="'true'"/>
							<xsl:with-param name="trim_dispNm" select="$methd"/>
						</xsl:call-template>
					</methodCode>
				</xsl:if>
				<targetSiteCode>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$locVal"/>
					</xsl:call-template>
				</targetSiteCode>
				<xsl:call-template name="mapPerformer">
					<xsl:with-param name="trim_participation" select="$pfmer"/>
				</xsl:call-template>
			</procedure>
		</entry>
	</xsl:template>
	<xsl:template name="procedures_studies">
		<xsl:param name="trim_relationship"/>
		<xsl:param name="studyTitle"/>
		<entry>
			<procedure classCode="PROC" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
				<id nullFlavor="NI"/>
				<xsl:variable name="procName">
					<xsl:choose>
						<xsl:when test="string-length($trim_relationship/trim:act/trim:observation/trim:value/trim:ST)>0">
							<xsl:text>Other - </xsl:text>
							<xsl:value-of select="$trim_relationship/trim:act/trim:observation/trim:value/trim:ST"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$trim_relationship/trim:act/trim:title/trim:ST"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="origTxt">
					<xsl:value-of select="$studyTitle"/>
					<xsl:text>: </xsl:text>
					<xsl:value-of select="$procName"/>
				</xsl:variable>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$procName"/>
						<xsl:with-param name="overriddenOriginalText" select="$origTxt"/>
					</xsl:call-template>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id(current())"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime>
					<low>
						<xsl:choose>
							<xsl:when test="$trim_relationship/trim:act/trim:relationship/trim:act/trim:effectiveTime">
								<xsl:attribute name="value"><xsl:value-of select="$trim_relationship/trim:act/trim:relationship/trim:act/trim:effectiveTime/trim:TS/trim:value"/></xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="nullFlavor">UNK</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
					</low>
				</effectiveTime>
				<xsl:for-each select="$trim_relationship/trim:act/trim:relationship/trim:act/trim:relationship[not(@enabled) or @enabled='true']">
					<xsl:variable name="relationshipName" select="trim:act/trim:observation/trim:value[1]/trim:label"/>
					<entryRelationship typeCode="REFR">
						<observation classCode="OBS" moodCode="EVN" negationInd="false">
							<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
							<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
							<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
							<id nullFlavor="NI"/>
							<code>
								<xsl:choose>
									<xsl:when test="string-length($relationshipName)>0">
										<xsl:call-template name="translateCode">
											<xsl:with-param name="noXSI" select="'true'"/>
											<xsl:with-param name="trim_dispNm" select="$relationshipName"/>
										</xsl:call-template>
									</xsl:when>
									<xsl:otherwise>
										<xsl:call-template name="translateCode">
											<xsl:with-param name="noXSI" select="'true'"/>
											<xsl:with-param name="trim_dispNm" select="@name"/>
										</xsl:call-template>
									</xsl:otherwise>
								</xsl:choose>
							</code>
							<text>
								<reference>
									<xsl:attribute name="value"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
								</reference>
							</text>
							<statusCode code="completed"/>
							<effectiveTime>
								<xsl:choose>
									<xsl:when test="trim:act/trim:effectiveTime">
										<xsl:attribute name="value"><xsl:value-of select="trim:act/trim:effectiveTime/trim:TS/trim:value"/></xsl:attribute>
									</xsl:when>
									<xsl:otherwise>
										<xsl:attribute name="nullFlavor">UNK</xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
							</effectiveTime>
							<xsl:choose>
								<xsl:when test="$relationshipName = 'Size' or $relationshipName = 'size'">
									<value xsi:type="ST">
										<xsl:value-of select="trim:act/trim:observation/trim:value[1]/trim:PQ/trim:value"/>
										<xsl:text> x </xsl:text>
										<xsl:value-of select="trim:act/trim:observation/trim:value[2]/trim:PQ/trim:value"/>
										<xsl:text> </xsl:text>
										<xsl:value-of select="trim:act/trim:observation/trim:value[3]/trim:CE/trim:displayName"/>
									</value>
								</xsl:when>
								<xsl:otherwise>
								  <xsl:choose>
								    <xsl:when test="trim:act/trim:observation/trim:value/trim:CE/trim:displayName">
											<xsl:choose>
												<xsl:when test="string-length(trim:act/trim:observation/trim:value/trim:CE/trim:displayName)>0">
													<value>
														<xsl:call-template name="translateCode">
															<xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
														</xsl:call-template>
													</value>
												</xsl:when>
											  <xsl:otherwise>
											    <value xsi:type="CD" nullFlavor="NI"/>
											  </xsl:otherwise>
											</xsl:choose>
										</xsl:when>
								    <xsl:when test="trim:act/trim:observation/trim:value/trim:SETCE/trim:displayName">
									    <xsl:choose>
									      <xsl:when test="string-length(trim:act/trim:observation/trim:value/trim:SETCE/trim:displayName)>0">
									        <value>
									          <xsl:call-template name="translateCode">
									            <xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:SETCE/trim:displayName"/>
									          </xsl:call-template>
									        </value>
									      </xsl:when>
									      <xsl:otherwise>
									        <value xsi:type="CD" nullFlavor="NI"/>
									      </xsl:otherwise>
									    </xsl:choose>
									  </xsl:when>
										<xsl:when test="string-length(trim:act/trim:observation/trim:value/trim:ST)>0">
											<value>
												<xsl:attribute name="xsi:type">ST</xsl:attribute>
												<xsl:value-of select="trim:act/trim:observation/trim:value/trim:ST"/>
											</value>
										</xsl:when>
										<xsl:otherwise>
											<value xsi:type="CD" nullFlavor="NI"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:otherwise>
							</xsl:choose>
						</observation>
					</entryRelationship>
				</xsl:for-each>
			</procedure>
		</entry>
	</xsl:template>
	<xsl:template name="procedures_chemoBiologic">
		<xsl:param name="trim_chemoBiologic"/>
		<entry typeCode="DRIV">
			<procedure classCode="PROC" moodCode="EVN">
				<templateId root="2.16.840.1.113883.3.117.1.5.3.11"/>
				<templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$trim_chemoBiologic/trim:act/trim:observation/trim:value[trim:label='Regimen']/trim:ST"/>
					</xsl:call-template>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_chemoBiologic)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime xsi:type="IVL_TS">
					<!-- Start date -->
					<low>
						<xsl:attribute name="value"><xsl:call-template name="format-date"><xsl:with-param name="theDate" select="$trim_chemoBiologic/trim:act/trim:observation/trim:value[trim:label='Start']/trim:ST"/></xsl:call-template></xsl:attribute>
					</low>
					<high>
						<xsl:attribute name="value"><xsl:call-template name="format-date"><xsl:with-param name="theDate" select="$trim_chemoBiologic/trim:act/trim:observation/trim:value[trim:label='End']/trim:ST"/></xsl:call-template></xsl:attribute>
					</high>
				</effectiveTime>
				<xsl:for-each select="$trim_chemoBiologic/trim:act/trim:observation/trim:value[trim:label='Episode' or trim:label='D/C Reason' or trim:label='Cycles Given']">
					<entryRelationship typeCode="REFR">
						<observation classCode="OBS" moodCode="EVN" negationInd="false">
							<templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
							<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
							<id nullFlavor="NI"/>
							<code>
								<xsl:call-template name="translateCode">
									<xsl:with-param name="noXSI" select="'true'"/>
									<xsl:with-param name="trim_dispNm" select="current()/trim:label"/>
								</xsl:call-template>
							</code>
							<text>
								<reference>
									<xsl:attribute name="value"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
								</reference>
							</text>
							<statusCode code="completed"/>
							<!-- If 'Cycles Given' then use just effectiveTime and value/INT, otherwise use effectiveTime/low and value/CD -->
							<xsl:choose>
								<xsl:when test="trim:label='Cycles Given'">
									<effectiveTime>
										<low nullFlavor="UNK"/>
									</effectiveTime>
									<value xsi:type="INT">
										<xsl:attribute name="value"><xsl:value-of select="trim:ST"/></xsl:attribute>
									</value>
								</xsl:when>
								<xsl:otherwise>
									<effectiveTime>
										<low nullFlavor="UNK"/>
									</effectiveTime>
									<value>
										<xsl:call-template name="translateCode">
											<xsl:with-param name="trim_dispNm" select="trim:ST | trim:CE/trim:displayName"/>
										</xsl:call-template>
									</value>
								</xsl:otherwise>
							</xsl:choose>
						</observation>
					</entryRelationship>
				</xsl:for-each>
			</procedure>
		</entry>
	</xsl:template>
	<xsl:template name="procedures_xrt">
		<xsl:param name="trim_xrt"/>
		<xsl:variable name="trim_site" select="$trim_xrt/trim:act/trim:observation/trim:value[trim:label='Site']/trim:ST"/>
		<xsl:variable name="trim_laterality" select="$trim_xrt/trim:act/trim:observation/trim:value[trim:label='laterality']/trim:ST"/>
		<xsl:variable name="trim_episode" select="$trim_xrt/trim:act/trim:observation/trim:value[trim:label='Episode']/trim:CE/trim:displayName"/>
		<xsl:variable name="trim_dosePer" select="$trim_xrt/trim:act/trim:observation/trim:value[trim:label='Dose per FX(gCy)']"/>
		<xsl:variable name="trim_totalDose" select="$trim_xrt/trim:act/trim:observation/trim:value[trim:label='Total dose (gCy)']"/>
		<xsl:variable name="trim_totalNum" select="$trim_xrt/trim:act/trim:observation/trim:value[trim:label='Total # FX']"/>
		<entry typeCode="DRIV">
			<procedure classCode="PROC" moodCode="EVN">
				<!-- procedure template -->
				<templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
				<id nullFlavor="NI"/>
				<code code="108290001" displayName="radiation oncology AND/OR radiotherapy" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
					<originalText>XRT; Site: <xsl:value-of select="$trim_site"/>; Episode: <xsl:value-of select="$trim_episode"/>; laterality: <xsl:value-of select="$trim_laterality"/>
					</originalText>
					<!-- site -->
					<qualifier>
						<name>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="'procedure site'"/>
							</xsl:call-template>
						</name>
						<xsl:call-template name="translateOtherValueInCD">
							<xsl:with-param name="origTxt" select="$trim_site"/>
						</xsl:call-template>
					</qualifier>
					<!-- laterality -->
					<qualifier>
						<name>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="'laterality'"/>
							</xsl:call-template>
						</name>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$trim_laterality"/>
							</xsl:call-template>
						</value>
					</qualifier>
					<!-- episode -->
					<qualifier>
						<name>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="'Episode'"/>
							</xsl:call-template>
						</name>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$trim_episode"/>
							</xsl:call-template>
						</value>
					</qualifier>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_xrt)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime xsi:type="IVL_TS">
					<low nullFlavor="UNK"/>
					<high nullFlavor="UNK"/>
				</effectiveTime>
				<xsl:if test="$trim_dosePer/trim:ST != ''">
					<entryRelationship typeCode="REFR">
						<observation classCode="OBS" moodCode="EVN" negationInd="false">
							<!-- Result Observation template -->
							<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
							<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
							<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
							<id nullFlavor="NI"/>
							<code xsi:type="CD" code="228815006" displayName="incident radiation dose" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
								<originalText>Dose per FX(gCy)</originalText>
							</code>
							<text>
								<reference>
									<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_dosePer)"/></xsl:attribute>
								</reference>
							</text>
							<statusCode code="completed"/>
							<effectiveTime nullFlavor="UNK"/>
							<value xsi:type="PQ" unit="gCy/{{fraction}}">
								<xsl:attribute name="value"><xsl:value-of select="$trim_dosePer/trim:ST"/></xsl:attribute>
							</value>
						</observation>
					</entryRelationship>
				</xsl:if>
				<xsl:if test="$trim_totalNum/trim:ST != ''">
					<entryRelationship typeCode="REFR">
						<observation classCode="OBS" moodCode="EVN" negationInd="false">
							<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
							<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
							<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
							<id nullFlavor="NI"/>
							<code xsi:type="CD" code="228862004" displayName="number of fractions" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
								<originalText>Total # FX</originalText>
							</code>
							<text>
								<reference>
									<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_totalNum)"/></xsl:attribute>
								</reference>
							</text>
							<statusCode code="completed"/>
							<effectiveTime nullFlavor="UNK"/>
							<value xsi:type="INT">
								<xsl:attribute name="value"><xsl:value-of select="$trim_totalNum/trim:ST"/></xsl:attribute>
							</value>
						</observation>
					</entryRelationship>
				</xsl:if>
				<xsl:if test="$trim_totalDose/trim:ST != ''">
					<entryRelationship typeCode="REFR">
						<observation classCode="OBS" moodCode="EVN" negationInd="false">
							<templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
							<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
							<templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
							<id nullFlavor="NI"/>
							<code xsi:type="CD" code="228815006" displayName="incident radiation dose" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
								<originalText>Total dose (gCy)</originalText>
							</code>
							<text>
								<reference>
									<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_totalDose)"/></xsl:attribute>
								</reference>
							</text>
							<statusCode code="completed"/>
							<effectiveTime nullFlavor="UNK"/>
							<value xsi:type="PQ" unit="gCy{total}">
								<xsl:attribute name="value"><xsl:value-of select="$trim_totalDose/trim:ST"/></xsl:attribute>
							</value>
						</observation>
					</entryRelationship>
				</xsl:if>
			</procedure>
		</entry>
	</xsl:template>
	<xsl:template name="procedures_breastCancerSurgery">
		<xsl:param name="trim_entry"/>
		<xsl:param name="trim_date" select="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Date']/trim:TS/trim:value"/>
		<xsl:param name="trim_laterality" select="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Laterality/Location']/trim:ST"/>
		<xsl:param name="trim_episodicity" select="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Episode']/trim:CE/trim:displayName"/>
		<entry>
			<procedure classCode="PROC" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
				<id nullFlavor="NI"/>
				<code xsi:type="CD" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT" nullFlavor="OTH">
					<originalText>
						Breast cancer surgery <xsl:value-of select="$trim_episodicity"/>; Laterality/Location: <xsl:value-of select="$trim_laterality"/>; Episode: <xsl:value-of select="$trim_episodicity"/>
					</originalText>
					<!-- laterality -->
					<qualifier>
						<name>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="'Laterality/Location'"/>
							</xsl:call-template>
						</name>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$trim_laterality"/>
							</xsl:call-template>
						</value>
					</qualifier>
					<!-- episode -->
					<qualifier>
						<name>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="'Episode'"/>
							</xsl:call-template>
						</name>
						<value>
							<xsl:call-template name="translateCode">
								<xsl:with-param name="noXSI" select="'true'"/>
								<xsl:with-param name="trim_dispNm" select="$trim_episodicity"/>
							</xsl:call-template>
						</value>
					</qualifier>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_entry)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime>
					<xsl:attribute name="value"><xsl:value-of select="$trim_date"/></xsl:attribute>
				</effectiveTime>
				<xsl:if test="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Surgeon']">
					<performer>
						<assignedEntity>
							<id nullFlavor="NI"/>
							<addr nullFlavor="NI"/>
							<telecom nullFlavor="NI"/>
							<assignedPerson>
								<name>
									<xsl:value-of select="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Surgeon']/trim:ST"/>
								</name>
							</assignedPerson>
						</assignedEntity>
					</performer>
				</xsl:if>
			</procedure>
		</entry>
	</xsl:template>
	<xsl:template name="procedures_riskFactor">
		<xsl:param name="trim_entry"/>
		<xsl:param name="trim_effectiveTime" select="$trim_entry/trim:act/trim:observation/trim:value/trim:ST"/>
		<entry>
			<procedure classCode="PROC" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$trim_entry/trim:act/trim:observation/trim:value/trim:SETCE/trim:displayName"/>
					</xsl:call-template>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_entry)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime>
					<xsl:choose>
						<xsl:when test="string-length($trim_effectiveTime)>0">
							<xsl:attribute name="value"><xsl:value-of select="$trim_effectiveTime"/></xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="nullFlavor">UNK</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
				</effectiveTime>
			</procedure>
		</entry>
	</xsl:template>
	<xsl:template name="procedures_menopausalStatus">
		<xsl:param name="trim_entry"/>
		<xsl:param name="trim_effectiveTime" select="$trim_entry/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
		<entry>
			<procedure classCode="PROC" moodCode="EVN">
				<xsl:if test="$trim_entry/trim:act/trim:observation/trim:value/trim:CE/trim:displayName='No'">
					<xsl:attribute name="negationInd">true</xsl:attribute>
				</xsl:if>
				<templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
				<id nullFlavor="NI"/>
				<code>
					<xsl:call-template name="translateCode">
						<xsl:with-param name="noXSI" select="'true'"/>
						<xsl:with-param name="trim_dispNm" select="$trim_entry/trim:act/trim:observation/trim:value/trim:label"/>
					</xsl:call-template>
				</code>
				<text>
					<reference>
						<xsl:attribute name="value"><xsl:value-of select="generate-id($trim_entry)"/></xsl:attribute>
					</reference>
				</text>
				<statusCode code="completed"/>
				<effectiveTime>
					<xsl:choose>
						<xsl:when test="string-length($trim_effectiveTime)>0">
							<xsl:attribute name="value"><xsl:value-of select="$trim_effectiveTime"/></xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="nullFlavor">UNK</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
				</effectiveTime>
			</procedure>
		</entry>
	</xsl:template>
</xsl:stylesheet>
