<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<xsl:import href="shared_functions.xslt"/>
	<xsl:import href="shared_diagnosticResult.xslt"/>
	<xsl:import href="shared_procedures.xslt"/>
	<xsl:import href="shared_problems.xslt"/>
	<xsl:import href="shared_physicalExam.xslt"/>
	<xsl:import href="shared_planOfCare.xslt"/>
	
	<xsl:param name="siteId"/>
	<xsl:param name="studyId"/>

	<xsl:output method="xml" encoding="UTF-8" indent="yes"/>
	
	<!-- Main -->
	<xsl:template match="/">
			<xsl:call-template name="ClinicalStagingNote">
				<xsl:with-param name="siteId" select="$siteId"/>
				<xsl:with-param name="studyId" select="$studyId"/>
			</xsl:call-template>
	</xsl:template>
	
	<!-- Main with template name-->
	<xsl:template name="ClinicalStagingNote" >
		<ClinicalDocument xsi:schemaLocation="urn:hl7-org:v3 http://caehrorg.jira.com/svn/CACIS/trunk/technical_artifacts/schema/CDA.xsd">			
			<xsl:for-each select="trim:trim/trim:act">
				<xsl:call-template name="baseline_cdaHeader">
					<xsl:with-param name="trimAct" select="current()"/>
				</xsl:call-template>
			</xsl:for-each>
			<component>
				<structuredBody>
					<!-- Diagnostic Results -->
					<xsl:call-template name="diagnosticResult_section">
						<xsl:with-param name="trim_stagingStatus" select="/trim:trim//trim:act/trim:relationship[@name='stagingStatus']"/>
						<xsl:with-param name="trim_metastasisEvidence" select="/trim:trim//trim:act/trim:relationship[@name='metastasisEvidence']"/>
						<xsl:with-param name="trim_metastaticWorkups" select="/trim:trim//trim:act/trim:relationship[@name='metastasisWorkUps']"/>
						<xsl:with-param name="trim_nodes" select="/trim:trim//trim:act/trim:relationship[@name='LeftSentinelLymphNodes' or @name='RightSentinelLymphNodes' or @name='LeftAxillaryLymphNodes' or @name='RightAxillaryLymphNodes' or @name='LeftAdditionalNodes' or @name='RightAdditionalNodes'][not(@enabled) or @enabled='true']"/>
						<xsl:with-param name="trim_positiveNodes" select="/trim:trim//trim:act/trim:relationship[@name='LeftTotalPositiveNodes' or @name='RightTotalPositiveNodes'][not(@enabled) or @enabled='true']"/>
						<xsl:with-param name="trim_totExamedNodes" select="/trim:trim//trim:act/trim:relationship[@name='LeftTotalExaminedNodes' or @name='RightTotalExaminedNodes'][not(@enabled) or @enabled='true']"/>
					</xsl:call-template>
					
					<!-- Procedures -->
					<xsl:call-template name="procedure_section">
						<xsl:with-param name="trim_csProcedures" select="/trim:trim//trim:act/trim:relationship[@name='procedures']"/>
						<xsl:with-param name="trim_studies" select="/trim:trim//trim:act/trim:relationship[@name='studies']"/>
						<xsl:with-param name="trim_rightStudies" select="/trim:trim//trim:act/trim:relationship[@name='rightStudies']"/>
						<xsl:with-param name="trim_priorProcedures" select="/trim:trim//trim:act/trim:relationship[@name='priorProcedures']"/>
					</xsl:call-template>
					
					<!-- Problems -->
					<xsl:call-template name="problems_section">
						<xsl:with-param name="trim_impressions" select="/trim:trim//trim:act/trim:relationship[@name='impressions']"/>
						<xsl:with-param name="trim_staging" select="/trim:trim/trim:act/trim:relationship[@name='staging']"/>
						<xsl:with-param name="trim_calcStaging" select="/trim:trim/trim:act/trim:relationship[@name='calculatedStaging']"/>
						<xsl:with-param name="trim_adjuStaging" select="/trim:trim/trim:act/trim:relationship[@name='adjucatedStging']"/>
						<xsl:with-param name="trim_surgeon" select="/trim:trim/trim:act/trim:relationship[@name='surgeon']"/>
						<xsl:with-param name="trim_t4tumor" select="/trim:trim/trim:act/trim:relationship[@name='t4TumorStatus']"/>
					</xsl:call-template>
				
					<!-- Physical Exam - breast exam-->
					<xsl:call-template name="physicalExam_section">
						<xsl:with-param name="trim_currentBreastExam" select="/trim:trim//trim:act/trim:relationship[@name='breastExam']"/>
						<xsl:with-param name="trim_findings" select="/trim:trim//trim:act/trim:relationship[@name='findings']"/>
					</xsl:call-template>

					<!-- Plan of Care -->
					<xsl:call-template name="planOfCare_section">
						<xsl:with-param name="trim_impressionPlan" select="/trim:trim/trim:act//trim:relationship[@name='impressionPlan']"/>
					</xsl:call-template>

				</structuredBody>
			</component>
		</ClinicalDocument>
	</xsl:template>

	<!-- ======================================== -->
	<!-- cda header template                                                     -->
	<!-- ======================================== -->
	<xsl:template name="baseline_cdaHeader">
		<xsl:param name="trimAct"/>
		<realmCode code="US"/>
		<typeId root="2.16.840.1.113883.1.3" extension="POCD_HD000040"/>
		<!--IHE medical documents specification -->
		<templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.1" assigningAuthorityName="IHE PCC"/>
		<xsl:if test="$trimAct/trim:id/trim:II">
			<xsl:call-template name="make_II">
				<xsl:with-param name="II_root" select="$trimAct/trim:id/trim:II/trim:root"/>
				<xsl:with-param name="II_ext" select="$trimAct/trim:id/trim:II/trim:extension"/>
			</xsl:call-template>
		</xsl:if>
		<code code="11542-8" codeSystem="2.16.840.1.113883.6.1" displayName="Subsequent visit evaluation note"/>
		<xsl:if test="$trimAct/trim:title/trim:ST">
			<title>
				<xsl:value-of select="$trimAct/trim:title/trim:ST"/>
			</title>
		</xsl:if>
		<xsl:if test="$trimAct/trim:effectiveTime/trim:TS/trim:value">
			<effectiveTime>
				<xsl:attribute name="value"><xsl:value-of select="$trimAct/trim:effectiveTime/trim:TS/trim:value"/></xsl:attribute>
			</effectiveTime>
		</xsl:if>
		<confidentialityCode code="N"/>
		<languageCode code="en-US"/>
		<xsl:if test="string-length($siteId)>0">
			<documentationOf>
				<serviceEvent classCode="CLNTRL">
					<id root="2.16.840.1.113883.3.26.1.7">
						<xsl:attribute name="extension"><xsl:value-of select="$siteId"/></xsl:attribute>
					</id>
					<code nullFlavor="OTH">
						<originalText>site-specific component of clinical trial</originalText>
					</code>
				</serviceEvent>
			</documentationOf>
		</xsl:if>
		<xsl:if test="string-length($studyId)>0">
			<documentationOf>
				<serviceEvent classCode="CLNTRL">
					<id root="2.16.840.1.113883.3.26.1.7">
						<xsl:attribute name="extension"><xsl:value-of select="$studyId"/></xsl:attribute>
					</id>
					<code nullFlavor="OTH">
						<originalText>clinical trial</originalText>
					</code>
				</serviceEvent>
			</documentationOf>
		</xsl:if>
		<!-- search DOB -->
		<xsl:variable name="ptDOB" select="//trim:value[trim:label='Patient date of birth']"/>
		<!-- search Marital Status -->
		<xsl:variable name="ptMarrital" select="//trim:value[trim:valueSet='maritalStatus']"/>
		<!-- search Race/Ethnicity-->
		<xsl:variable name="ptRace" select="//trim:value[trim:valueSet='race/ethnicity']"/>
		<xsl:for-each select="$trimAct/trim:participation[@typeCode='SBJ'][trim:role/@classCode='PAT']">
			<recordTarget>
				<patientRole>
					<xsl:for-each select="trim:role/trim:id">
						<xsl:call-template name="make_II">
							<xsl:with-param name="II_root" select="trim:II/trim:root"/>
							<xsl:with-param name="II_ext" select="trim:II/trim:extension"/>
						</xsl:call-template>
					</xsl:for-each>
					<addr nullFlavor="NI"/>
					<telecom nullFlavor="NI"/>
					<xsl:for-each select="trim:role/trim:player[@classCode='PSN']">
						<patient>
							<xsl:call-template name="make_name">
								<xsl:with-param name="trimNm" select="trim:name"/>
							</xsl:call-template>
							<administrativeGenderCode nullFlavor="NI"/>
							<birthTime>
								<xsl:choose>
									<xsl:when test="$ptDOB/trim:TS/trim:value">
										<xsl:attribute name="value"><xsl:value-of select="$ptDOB/trim:TS/trim:value"/></xsl:attribute>
									</xsl:when>
									<xsl:otherwise>
										<xsl:attribute name="nullFlavor">NI</xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
							</birthTime>
							<xsl:if test="$ptMarrital/trim:CE/trim:displayName">
								<maritalStatusCode>
									<xsl:call-template name="translateCode">
										<xsl:with-param name="noXSI" select="'true'"/>
										<xsl:with-param name="trim_dispNm" select="$ptMarrital/trim:CE/trim:displayName"/>
									</xsl:call-template>
								</maritalStatusCode>
							</xsl:if>
							<xsl:if test="$ptRace/trim:CE/trim:displayName">
								<raceCode>
									<xsl:call-template name="translateCode">
										<xsl:with-param name="noXSI" select="'true'"/>
										<xsl:with-param name="trim_dispNm" select="$ptRace/trim:CE/trim:displayName"/>
									</xsl:call-template>
								</raceCode>
							</xsl:if>
						</patient>
					</xsl:for-each>
				</patientRole>
			</recordTarget>
		</xsl:for-each>
		<xsl:for-each select="$trimAct/trim:participation[@typeCode='ENT'][trim:role/@classCode='ROL']">
			<author>
				<time nullFlavor="NI"/>
				<assignedAuthor>
					<xsl:call-template name="make_II">
						<xsl:with-param name="II_root" select="trim:role/trim:id/trim:II/trim:root"/>
						<xsl:with-param name="II_ext" select="trim:role/trim:id/trim:II/trim:extension"/>
					</xsl:call-template>
					<addr nullFlavor="NI"/>
					<telecom nullFlavor="NI"/>
					<assignedPerson>
						<name nullFlavor="NI"/>
					</assignedPerson>
				</assignedAuthor>
			</author>
		</xsl:for-each>
		<custodian>
			<assignedCustodian>
				<representedCustodianOrganization>
					<id root="2.16.840.1.113883.3.26.1.7" extension="TranscendTolven"/>
					<name nullFlavor="NI"/>
					<telecom nullFlavor="NI"/>
					<addr nullFlavor="NI"/>
				</representedCustodianOrganization>
			</assignedCustodian>
		</custodian>
		<!-- encounter effectiveTime -->
		<xsl:variable name="DOE" select="/trim:trim/trim:act/trim:effectiveTime[trim:label='Date of Exam']"/>
		<!-- timePoint [Pre-Study, On-Study] -->
		<xsl:variable name="ptTimePoint" select="//trim:value[trim:valueSet='timePoint']"/>
		<!-- patientType [new, follow-up] -->
		<xsl:variable name="ptType" select="//trim:value[trim:valueSet='patientType']"/>
		<componentOf>
			<encompassingEncounter>
				<id nullFlavor="NI"/>
				<code code="ONC" displayName="oncology" codeSystem="2.16.840.1.113883.5.4">
					<originalText>
						<xsl:choose>
							<xsl:when test="$ptTimePoint/trim:CE/trim:displayName or $ptType/trim:CE/trim:displayName">
								<xsl:value-of select="$ptTimePoint/trim:CE/trim:displayName"/>
								<xsl:if test="$ptType/trim:CE/trim:displayName">
									<xsl:text>; </xsl:text>
									<xsl:value-of select="$ptType/trim:CE/trim:displayName"/> patient	
							</xsl:if>
							</xsl:when>
						</xsl:choose>
					</originalText>
				</code>
				<effectiveTime>
					<xsl:choose>
						<xsl:when test="$DOE/trim:TS/trim:value">
							<xsl:attribute name="value"><xsl:value-of select="$DOE/trim:TS/trim:value"/></xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="nullFlavor">UNK</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
				</effectiveTime>
			</encompassingEncounter>
		</componentOf>
	</xsl:template>
</xsl:stylesheet>