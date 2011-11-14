<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<xsl:import href="shared_functions.xslt"/>
	<xsl:import href="shared_diagnosticResult.xslt"/>
	<xsl:import href="shared_procedures.xslt"/>
	<xsl:import href="shared_problems.xslt"/>
	<xsl:import href="shared_physicalExam.xslt"/>	
	<xsl:import href="shared_planOfCare.xslt"/>
	<xsl:import href="shared_reviewOfSystems.xslt"/>
	<xsl:import href="shared_vitalSign.xslt"/>
	<xsl:import href="shared_medications.xslt"/>
	<xsl:import href="shared_functionalStatus.xslt"/>
	<xsl:import href="shared_adverseEvent.xslt"/>

	<xsl:param name="siteId"/>
	<xsl:param name="studyId"/>

	<xsl:output method="xml" encoding="UTF-8" indent="yes"/>
	
	<!-- Main -->
	<xsl:template match="/">
			<xsl:call-template name="MedicalStudyVisitNote">
				<xsl:with-param name="siteId" select="$siteId"/>
				<xsl:with-param name="studyId" select="$studyId"/>
			</xsl:call-template>
	</xsl:template>
	
	<!-- Main with template name-->
	<xsl:template name="MedicalStudyVisitNote" >
		<ClinicalDocument xsi:schemaLocation="urn:hl7-org:v3 http://caehrorg.jira.com/svn/CACIS/trunk/technical_artifacts/schema/CDA.xsd">			
			<xsl:for-each select="trim:trim/trim:act">
				<xsl:call-template name="medicalStudy_cdaHeader">
					<xsl:with-param name="trimAct" select="current()"/>
				</xsl:call-template>
			</xsl:for-each>
			<component>
				<structuredBody>
				
					<!-- Vital Signs Section -->
					<xsl:for-each select="trim:trim//trim:act/trim:relationship[@name='vitalSigns']">
						<xsl:call-template name="vitalSigns_section">
							<xsl:with-param name="vital_sec" select="current()"/>
						</xsl:call-template>
					</xsl:for-each>
					
					<!-- Functional Status Section -->
					<xsl:for-each select="trim:trim/trim:act/trim:relationship[@name='ECOGScore']">
						<xsl:call-template name="functionalStatus_section">
							<xsl:with-param name="functionalStatus_sec" select="current()"/>
						</xsl:call-template>
					</xsl:for-each>
				
					<xsl:variable name="menopausalStatusGroup" select="trim:trim/trim:act/trim:relationship[@name='menopausalStatus']"/>
					<!-- Problems Section -->
					<xsl:call-template name="problems_section">
						<xsl:with-param name="trim_impressions" select="/trim:trim//trim:act/trim:relationship[@name='impressions']"/>
						<xsl:with-param name="trim_menopausalStatus" select="$menopausalStatusGroup"/>
						<xsl:with-param name="trim_rspStatus" select="trim:trim/trim:act/trim:relationship[@name='responseStatus']"/>
						<xsl:with-param name="trim_rspDates" select="trim:trim/trim:act/trim:relationship[@name='responseDates']"/>						
					</xsl:call-template>
					
					<!-- Adverse Event-->
					<xsl:call-template name="adverseEvent_section">
						<xsl:with-param name="trim_toxicities" select="/trim:trim/trim:act/trim:relationship[@name='toxicities']"/>
						<xsl:with-param name="trim_priorToxicities" select="/trim:trim/trim:act/trim:relationship[@name='priorToxicities']"/>
					</xsl:call-template>					

					<!-- Medications Section -->
					<xsl:call-template name="medications_section">
						<xsl:with-param name="trim_medications" select="trim:trim/trim:act/trim:relationship[@name='medications']"/>
						<xsl:with-param name="trim_menopausalStatus" select="$menopausalStatusGroup"/>
						<xsl:with-param name="trim_hormoneTx" select="trim:trim/trim:act/trim:relationship[@name='riskFactor']/trim:act/trim:relationship[@name='hormoneTx']"/>
						<xsl:with-param name="trim_hormoneDuration" select="trim:trim/trim:act/trim:relationship[@name='riskFactor']/trim:act/trim:relationship[@name='hormoneDuration']"/>
						<xsl:with-param name="trim_hormoneTime" select="trim:trim/trim:act/trim:relationship[@name='riskFactor']/trim:act/trim:relationship[@name='hormoneTime']"/>
						<xsl:with-param name="trim_hormoneTherapy" select="trim:trim//trim:relationship[@name='hormoneTherapies']"/>
						<xsl:with-param name="trim_bisphosphonates" select="trim:trim//trim:relationship[@name='bisphosphonates']"/>
					</xsl:call-template>

					<!-- Procedures Section -->
					<xsl:call-template name="procedure_section">
						<xsl:with-param name="trim_menopausalStatus" select="$menopausalStatusGroup"/>
						<xsl:with-param name="trim_breastCancerSurgeries" select="trim:trim//trim:act/trim:relationship[@name='breastCancerSurgeries']"/>
						<xsl:with-param name="trim_xrt" select="trim:trim//trim:act/trim:relationship[@name='xrt']"/>
						<xsl:with-param name="trim_chemoBiologic" select="trim:trim//trim:act/trim:relationship[@name='Chemo']"/>
					</xsl:call-template>

					<!-- Review of Systems Section -->
					<xsl:if test="$menopausalStatusGroup or count(/trim:trim//trim:relationship[@name='riskFactor'])>0 or count(/trim:trim//trim:relationship[@name='ROS'])>0">
						<component>
							<section>
								<templateId root='2.16.840.1.113883.10.20.4.10' assigningAuthorityName="ConsultNote"/>
								<templateId root='1.3.6.1.4.1.19376.1.5.3.1.3.18' assigningAuthorityName="IHE"/>
								<templateId root='2.16.840.1.113883.3.88.11.83.120' assigningAuthorityName="HITSP"/>
								<id nullFlavor="NI"/>
								<code code="10187-3" displayName="REVIEW OF SYSTEMS" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
								<title>Review of systems</title>
								<text/>
								<xsl:call-template name="ROS_entries">
									<xsl:with-param name="trim_menopausalStatus" select="$menopausalStatusGroup"/>
								</xsl:call-template>
								<xsl:for-each select="/trim:trim//trim:relationship[@name='riskFactor']">
									<xsl:call-template name="ROS_entries">
										<xsl:with-param name="trim_riskFactors" select="current()"/>
									</xsl:call-template>
								</xsl:for-each>
								<xsl:for-each select="/trim:trim//trim:relationship[@name='ROS']">
									<xsl:call-template name="ROS_entries">
										<xsl:with-param name="trim_ros" select="current()"/>
									</xsl:call-template>
								</xsl:for-each>
							</section>
						</component>
					</xsl:if>

					<!-- diagnosticResult_section -->
					<xsl:call-template name="diagnosticResult_section">
						<xsl:with-param name="trim_geneticTesting" select="trim:trim//trim:act/trim:relationship[@name='geneticTesting']"/>
						<xsl:with-param name="trim_pastHistory" select="trim:trim//trim:act/trim:relationship[@name='pastHistory']"/>
						<xsl:with-param name="trim_gailCalculation" select="trim:trim//trim:act/trim:relationship[@name='gailCalculation']"/>
						<xsl:with-param name="trim_indexImaging" select="trim:trim//trim:act/trim:relationship[@name='indexImagingType']"/>
					</xsl:call-template>

					<!-- Physical Exam -->
					<xsl:call-template name="physicalExam_section">
						<xsl:with-param name="trim_physicalExam" select="/trim:trim//trim:act/trim:relationship[@name='physicalExam']"/>
						<xsl:with-param name="trim_priorBreastExams" select="/trim:trim//trim:act/trim:relationship[@name='priorBreastExam']"/>
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
	<xsl:template name="medicalStudy_cdaHeader">
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
		<code code="34806-0" codeSystem="2.16.840.1.113883.6.1" displayName="Oncology Evaluation and management note"/>
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
		<!-- encounter effectiveTime -->
		<xsl:variable name="DOE" select="/trim:trim/trim:act/trim:relationship[@name='examDate']/trim:act/trim:effectiveTime[trim:label='Date of Exam']"/>
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