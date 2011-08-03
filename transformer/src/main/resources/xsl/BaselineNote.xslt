<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
	<xsl:output method="xml" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<ClinicalDocument xmlns="urn:hl7-org:v3">
			<!-- <xsl:attribute name="xsi:schemaLocation" namespace="http://www.w3.org/2001/XMLSchema-instance"><xsl:value-of select="'urn:hl7-org:v3 D:/workspace/Transcend_Trim_Mapping/mapping/CDA_XSD/cda/CDA.xsd'"/></xsl:attribute> -->
			<xsl:for-each select="trim:trim/trim:act">
				<xsl:call-template name="baseline_cdaHeader">
					<xsl:with-param name="trimAct" select="current()"/>
				</xsl:call-template>
			</xsl:for-each>
			<component>
				<structuredBody>
					<xsl:for-each select="trim:trim/trim:act/trim:relationship[@name='riskFactor']/trim:act/trim:relationship[@name='familyHistorys']">
						<!-- call family history section template -->
						<xsl:call-template name="familyHistory_section">
							<xsl:with-param name="fh_sec" select="current()"/>
						</xsl:call-template>
					</xsl:for-each>
					<xsl:for-each select="trim:trim/trim:act/trim:relationship[@name='vitalSigns']">
						<!-- call vital sign section template -->
						<xsl:call-template name="vitalSigns_section">
							<xsl:with-param name="vital_sec" select="current()"/>
						</xsl:call-template>
					</xsl:for-each>
				</structuredBody>
			</component>
		</ClinicalDocument>
	</xsl:template>
	<!-- ===================== -->
	<!-- cda header template -->
	<!-- ===================== -->
	<xsl:template name="baseline_cdaHeader">
		<xsl:param name="trimAct"/>
		<realmCode xmlns="urn:hl7-org:v3" code="US"/>
		<typeId xmlns="urn:hl7-org:v3" root="2.16.840.1.113883.1.3" extension="POCD_HD000040"/>
		<templateId xmlns="urn:hl7-org:v3" root="2.16.840.1.113883.10.20.1"/>
		<templateId xmlns="urn:hl7-org:v3" root="2.16.840.1.113883.3.88.11.32.1"/>
		<templateId xmlns="urn:hl7-org:v3" root="1.3.6.1.4.1.19376.1.5.3.1.1.1"/>
		<templateId xmlns="urn:hl7-org:v3" root="2.16.840.1.113883.10.20.3"/>
		<xsl:if test="$trimAct/trim:id/trim:II">
			<xsl:call-template name="make_II">
				<xsl:with-param name="II_root" select="$trimAct/trim:id/trim:II/trim:root"/>
				<xsl:with-param name="II_ext" select="$trimAct/trim:id/trim:II/trim:extension"/>
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="$trimAct/trim:code/trim:CD">
			<xsl:call-template name="make_code_CD">
				<xsl:with-param name="CD_code" select="$trimAct/trim:code/trim:CD/trim:code"/>
				<xsl:with-param name="CD_dispNm" select="$trimAct/trim:code/trim:CD/trim:displayName"/>
				<xsl:with-param name="CD_codeSys" select="$trimAct/trim:code/trim:CD/trim:codeSystem"/>
				<xsl:with-param name="CD_codeSysNm" select="$trimAct/trim:code/trim:CD/trim:codeSystemName"/>
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="$trimAct/trim:title/trim:ST">
			<title xmlns="urn:hl7-org:v3">
				<xsl:value-of select="$trimAct/trim:title/trim:ST"/>
			</title>
		</xsl:if>
		<xsl:if test="$trimAct/trim:effectiveTime/trim:TS/trim:value">
			<effectiveTime xmlns="urn:hl7-org:v3">
				<xsl:attribute name="value"><xsl:value-of select="$trimAct/trim:effectiveTime/trim:TS/trim:value"/></xsl:attribute>
			</effectiveTime>
		</xsl:if>
		<confidentialityCode xmlns="urn:hl7-org:v3" code="N"/>
		<xsl:for-each select="$trimAct/trim:participation[@typeCode='SBJ'][trim:role/@classCode='PAT']">
			<recordTarget xmlns="urn:hl7-org:v3">
				<patientRole>
					<xsl:for-each select="trim:role/trim:id">
						<xsl:call-template name="make_II">
							<xsl:with-param name="II_root" select="trim:II/trim:root"/>
							<xsl:with-param name="II_ext" select="trim:II/trim:extension"/>
						</xsl:call-template>
					</xsl:for-each>
					<xsl:for-each select="trim:role/trim:player[@classCode='PSN']">
						<patient>
							<xsl:call-template name="make_name">
								<xsl:with-param name="trimNm" select="trim:name"/>
							</xsl:call-template>
						</patient>
					</xsl:for-each>
				</patientRole>
			</recordTarget>
		</xsl:for-each>
		<xsl:for-each select="$trimAct/trim:participation[@typeCode='ENT'][trim:role/@classCode='ROL']">
			<author xmlns="urn:hl7-org:v3">
				<time nullFlavor="NI"/>
				<assignedAuthor>
					<xsl:call-template name="make_II">
						<xsl:with-param name="II_root" select="trim:role/trim:id/trim:II/trim:root"/>
						<xsl:with-param name="II_ext" select="trim:role/trim:id/trim:II/trim:extension"/>
					</xsl:call-template>
				</assignedAuthor>
			</author>
		</xsl:for-each>
		<custodian xmlns="urn:hl7-org:v3">
			<assignedCustodian>
				<representedCustodianOrganization>
					<!-- fake NIC oid -->
					<id root="2.16.840.1.113883.3.26.1.6"/>
				</representedCustodianOrganization>
			</assignedCustodian>
		</custodian>
	</xsl:template>
	<!-- =========================== -->
	<!--        body templates                          -->
	<!-- =========================== -->
	<!-- family history section template-->
	<xsl:template name="familyHistory_section">
		<xsl:param name="fh_sec"/>
		<component xmlns="urn:hl7-org:v3">
			<section>
				<templateId root="2.16.840.1.113883.3.88.11.83.125"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.3.14"/>
				<templateId root="2.16.840.1.113883.10.20.1.4"/>
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
		<entry xmlns="urn:hl7-org:v3">
			<organizer classCode="CLUSTER" moodCode="EVN">
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.15"/>
				<templateId root="2.16.840.1.113883.10.20.1.23"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.18"/>
				<statusCode code="completed"/>
				<xsl:for-each select="$fh_org/trim:act/trim:relationship[@name= 'relative']">
					<xsl:if test="trim:act/trim:observation/trim:value/trim:ST">
						<subject>
							<relatedSubject classCode="PRS">
								<xsl:call-template name="valueSet_relatives">
									<xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:ST"/>
								</xsl:call-template>
							</relatedSubject>
						</subject>
					</xsl:if>
				</xsl:for-each>
				<xsl:for-each select="$fh_org/trim:act/trim:relationship[@name= 'cancerType']">
					<xsl:if test="trim:act/trim:observation/trim:value/trim:CE">
						<!-- call family history observation cancerType template -->
						<xsl:call-template name="familyHistory_observation_cancerType">
							<xsl:with-param name="fh_obs" select="current()"/>
							<xsl:with-param name="fh_act" select="$fh_org/trim:act"/>
						</xsl:call-template>
					</xsl:if>
				</xsl:for-each>
				<xsl:for-each select="$fh_org/trim:act/trim:relationship[@name= 'test']">
					<xsl:if test="trim:act/trim:observation/trim:value/trim:SETCE">
						<!-- call family history observation geneticMarker template -->
						<xsl:call-template name="familyHistory_observation_geneticMarker">
							<xsl:with-param name="fh_obs" select="current()"/>
							<xsl:with-param name="fh_act" select="$fh_org/trim:act"/>
						</xsl:call-template>
					</xsl:if>
				</xsl:for-each>
			</organizer>
		</entry>
	</xsl:template>
	<!-- familyHistory observation templates-->
	<xsl:template name="familyHistory_observation_cancerType">
		<xsl:param name="fh_obs"/>
		<xsl:param name="fh_act"/>
		<component xmlns="urn:hl7-org:v3">
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.22" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.3" assigningAuthorityName="IHE PCC"/>
				<id nullFlavor="NI"/>
				<code code="404684003" displayName="Finding" codeSystemName="SNOMED CT" codeSystem="2.16.840.1.113883.6.96"/>
				<text>
					<reference/>
				</text>
				<statusCode code="completed"/>
				<xsl:if test="$fh_obs/trim:act/trim:observation/trim:value/trim:CE/trim:displayName">
					<xsl:call-template name="valueSet_cancerTypes">
						<xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
					</xsl:call-template>
				</xsl:if>
			</observation>
		</component>
	</xsl:template>
	<xsl:template name="familyHistory_observation_geneticMarker">
		<xsl:param name="fh_obs"/>
		<xsl:param name="fh_act"/>
		<component xmlns="urn:hl7-org:v3">
			<observation classCode="OBS" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.22" assigningAuthorityName="CCD"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.3" assigningAuthorityName="IHE PCC"/>
				<id nullFlavor="NI"/>
				<code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
				<text>
					<reference/>
				</text>
				<statusCode code="completed"/>
				<xsl:if test=" trim:act/trim:observation/trim:value/trim:SETCE/trim:displayName">
					<xsl:call-template name="valueSet_tests">
						<xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:SETCE/trim:displayName"/>
					</xsl:call-template>
				</xsl:if>
			</observation>
		</component>
	</xsl:template>
	<!-- vitalSign section template -->
	<xsl:template name="vitalSigns_section">
		<xsl:param name="vital_sec"/>
		<component>
			<section>
				<templateId root="2.16.840.1.113883.10.20.1.16"/>
				<templateId root="2.16.840.1.113883.3.88.11.83.119"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.3.25"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2"/>
				<code code="8716-3" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="vital signs"/>
				<title>Vital signs</title>
				<xsl:for-each select="$vital_sec/trim:act/trim:observation">
					<xsl:call-template name="vitalSigns_organizer">
						<xsl:with-param name="vitalOrgniz" select="current()"/>
						<xsl:with-param name="vitalId" select="$vital_sec/trim:id/trim:II"/>
					</xsl:call-template>
				</xsl:for-each>
			</section>
		</component>
	</xsl:template>
	<!-- vitalSign organizer -->
	<xsl:template name="vitalSigns_organizer">
		<xsl:param name="vitalOrgniz"/>
		<xsl:param name="vitalId"/>
		<entry>
			<organizer classCode="CLUSTER" moodCode="EVN">
				<templateId root="2.16.840.1.113883.10.20.1.32"/>
				<templateId root="2.16.840.1.113883.10.20.1.35"/>
				<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.1"/>
				<xsl:call-template name="make_II">
					<xsl:with-param name="II_root" select="$vitalId/trim:root"/>
					<xsl:with-param name="II_ext" select="$vitalId/trim:extension"/>
				</xsl:call-template>
				<code code="46680005" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT" displayName="Vital signs"/>
				<statusCode code="completed"/>
				<effectiveTime nullFlavor="NI"/>
				<xsl:for-each select="$vitalOrgniz/trim:value">
					<xsl:call-template name="vitalSign_observation">
						<xsl:with-param name="vital_value" select="current()"/>
					</xsl:call-template>
				</xsl:for-each>
			</organizer>
		</entry>
	</xsl:template>
	<!-- vitalSign observation -->
	<xsl:template name="vitalSign_observation">
		<xsl:param name="vital_value"/>
		<xsl:if test="string-length($vital_value/trim:ST) and not($vital_value/trim:valueSet)">
			<component>
				<observation classCode="OBS" moodCode="EVN">
					<templateId root="2.16.840.1.113883.10.20.1.31"/>
					<templateId root="2.16.840.1.113883.3.88.11.83.14"/>
					<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13"/>
					<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.2"/>
					<xsl:variable name="vital_label" select="trim:label"/>
					<code>
						<xsl:if test="($vital_label='T: ') or ($vital_label = 'P: ') or ($vital_label = 'Wt: ') or ($vital_label = 'R: ') or ($vital_label = 'BP: ') or ($vital_label = 'Ht: ')">
							<xsl:choose>
								<xsl:when test="($vital_label = 'T: ')">
									<xsl:attribute name="code"><xsl:value-of select="8310-5"/></xsl:attribute>
									<xsl:attribute name="displayName"><xsl:value-of select="'Body temperature'"/></xsl:attribute>
									<originalText>T:</originalText>
								</xsl:when>
								<xsl:when test="($vital_label = 'P: ')">
									<xsl:attribute name="code"><xsl:value-of select="8867-4"/></xsl:attribute>
									<xsl:attribute name="displayName"><xsl:value-of select="'Heart rate'"/></xsl:attribute>
									<originalText>P:</originalText>
								</xsl:when>
								<xsl:when test="($vital_label = 'Wt: ')">
									<xsl:attribute name="code"><xsl:value-of select="8310-5"/></xsl:attribute>
									<xsl:attribute name="displayName"><xsl:value-of select="'Body weight'"/></xsl:attribute>
									<originalText>Wt:</originalText>
								</xsl:when>
								<xsl:when test="($vital_label = 'R: ')">
									<xsl:attribute name="code"><xsl:value-of select="9279-1"/></xsl:attribute>
									<xsl:attribute name="displayName"><xsl:value-of select="'Respiration Rate'"/></xsl:attribute>
									<originalText>R:</originalText>
								</xsl:when>
								<xsl:when test="($vital_label = 'BP: ')">
									<xsl:attribute name="code"><xsl:value-of select="8480-6"/></xsl:attribute>
									<xsl:attribute name="displayName"><xsl:value-of select="'Systolic BP'"/></xsl:attribute>
								</xsl:when>
								<xsl:when test="($vital_label = 'Ht:: ')">
									<xsl:attribute name="code"><xsl:sequence select="'8302-2'"/></xsl:attribute>
									<xsl:attribute name="displayName"><xsl:value-of select="'Body height'"/></xsl:attribute>
									<originalText>Ht:</originalText>
								</xsl:when>
							</xsl:choose>
						</xsl:if>
						<xsl:if test="not($vital_label)">
							<xsl:attribute name="code"><xsl:value-of select="8462-4"/></xsl:attribute>
							<xsl:attribute name="displayName"><xsl:value-of select="'Diastolic BP'"/></xsl:attribute>
						</xsl:if>
						<xsl:attribute name="codeSystem"><xsl:value-of select="'2.16.840.1.113883.6.1'"/></xsl:attribute>
						<xsl:attribute name="codeSystemName"><xsl:value-of select="'LOINC'"/></xsl:attribute>
					</code>
					<statusCode code="completed"/>
					<effectiveTime nullFlavor="NI"/>
					<value>
						<xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"><xsl:value-of select="'PQ'"/></xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="$vital_value/trim:ST"/></xsl:attribute>
						<xsl:attribute name="unit">
							<xsl:choose>
								<xsl:when test="($vital_label = 'T: ')"><xsl:value-of select="'Cel'"/></xsl:when>
								<xsl:when test="($vital_label = 'P: ')"><xsl:value-of select="'min'"/></xsl:when>
								<xsl:when test="($vital_label = 'R: ')"><xsl:value-of select="'min'"/></xsl:when>
								<xsl:when test="($vital_label = 'Wt: ')"><xsl:value-of select="'kg'"/></xsl:when>
								<xsl:when test="($vital_label = 'Ht: ')"><xsl:value-of select="'cm'"/></xsl:when>
								<xsl:when test="($vital_label = 'BP: ')"><xsl:value-of select="'mm[Hg]'"/></xsl:when>
								<xsl:when test="not($vital_label) and (string-length($vital_value/trim:ST)>0)"><xsl:value-of select="'mm[Hg]'"/></xsl:when>
							</xsl:choose></xsl:attribute>
					</value>
				</observation>
			</component>
		</xsl:if>
	</xsl:template>
	<!-- ============================ -->
	<!-- valueSet mapping templates                  -->
	<!-- ============================ -->
	<xsl:template name="valueSet_relatives">
		<!--  baseline note, valueSet: relatives
            Mother
			Father
         -->
		<xsl:param name="trim_dispNm"/>
		<xsl:choose>
			<xsl:when test="$trim_dispNm='Mother'">
				<code xmlns="urn:hl7-org:v3" code="MTH" codeSystem="2.16.840.1.113883.5.111" codeSystemName="HL7 FamilyMember" displayName="mother">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</code>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Father'">
				<code xmlns="urn:hl7-org:v3" code="FTH" codeSystem="2.16.840.1.113883.5.111" codeSystemName="HL7 FamilyMember" displayName="father">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</code>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="valueSet_tests">
		<!--  baseline note, valueSet: tests
            BRCA 1 positive
			BRCA 2 positive
			Unknown
         -->
		<xsl:param name="trim_dispNm"/>
		<xsl:choose>
			<xsl:when test="$trim_dispNm='BRCA 1 positive'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="BRCA 1 positive">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='BRCA 2 positive'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="BRCA 2 positive">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Unknown'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" nullFlavor="UNK">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="valueSet_cancerTypes">
		<!-- baseline note, valueSet: cancerTypes 
				Breast Cancer
				Bone
				Brain, spinal cord (central nervous system)
                Cervical carcinoma, invasive
				Cervical carcinoma, in situ
				Colon/rectal
				Hodgkin's disease
				Intestinal
				Kidney
				Leukemia or abnormal bone marrow cells that may lead to leukemia (myelodysplasia)
				Lung
				Lymphoma
				Ovarian
				Pancreatic
				Prostate
				Skin: basal or squamous cell
				Skin: melanoma
				Other cancer:
		-->
		<xsl:param name="trim_dispNm"/>
		<xsl:choose>
			<xsl:when test="$trim_dispNm='Breast Cancer'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Breast Cancer"/>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Bone'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Bone"/>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Brain, spinal cord (central nervous system)'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Brain, spinal cord (central nervous system)">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Cervical carcinoma, invasive'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Cervical carcinoma, invasive">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Cervical carcinoma, in situ'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Cervical carcinoma, in situ">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Colon/rectal'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Colon/rectal">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="starts-with($trim_dispNm, 'Hodgkin')">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Hodgkin&quot;s disease">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Intestinal'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Intestinal">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Kidney'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Kidney">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Leukemia or abnormal bone marrow cells that may lead to leukemia (myelodysplasia)'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Leukemia or abnormal bone marrow cells that may lead to leukemia (myelodysplasia)">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Lung'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Lung">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Lymphoma'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Lymphoma">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Ovarian'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Ovarian">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Pancreatic'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Pancreatic">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Prostate'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Prostate">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Skin: basal or squamous cell'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Skin: basal or squamous cell">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='Skin: melanoma'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="Skin: melanoma">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="valueSet_breastCancerType">
		<!--  baseline note, valueSet: breastCancerType
			DCIS
			LCIS
			invasive
        -->
		<xsl:param name="trim_dispNm"/>
		<xsl:choose>
			<xsl:when test="$trim_dispNm='DCIS'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="DCIS">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='LCIS'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="LCIS">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
			<xsl:when test="$trim_dispNm='invasive'">
				<value xmlns="urn:hl7-org:v3" xsi:type="CD" code="xxx-xxx" codeSystem="2.16.840.1.113883.6.96" displayName="invasive">
					<originalText>
						<xsl:value-of select="$trim_dispNm"/>
					</originalText>
				</value>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!-- ============================ -->
	<!-- general utility templates                      -->
	<!-- ============================ -->
	<xsl:template name="make_II">
		<xsl:param name="II_root"/>
		<xsl:param name="II_ext"/>
		<xsl:param name="II_assAuth"/>
		<xsl:choose>
			<xsl:when test="string-length($II_root)>0">
				<id xmlns="urn:hl7-org:v3">
					<xsl:attribute name="root"><xsl:value-of select="$II_root"/></xsl:attribute>
					<xsl:if test="string-length($II_ext)>0">
						<xsl:attribute name="extension"><xsl:value-of select="$II_ext"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="string-length($II_assAuth)>0">
						<xsl:attribute name="assigningAuthorityName"><xsl:value-of select="$II_assAuth"/></xsl:attribute>
					</xsl:if>
				</id>
			</xsl:when>
			<xsl:otherwise>
				<id xmlns="urn:hl7-org:v3" nullFlavor="NI"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="make_code_CD">
		<xsl:param name="CD_code"/>
		<xsl:param name="CD_dispNm"/>
		<xsl:param name="CD_codeSys"/>
		<xsl:param name="CD_codeSysNm"/>
		<xsl:choose>
			<xsl:when test="string-length($CD_code)>0">
				<code xmlns="urn:hl7-org:v3">
					<xsl:attribute name="code"><xsl:value-of select="$CD_code"/></xsl:attribute>
					<xsl:if test="string-length($CD_dispNm)>0">
						<xsl:attribute name="displayName"><xsl:value-of select="$CD_dispNm"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="string-length($CD_codeSys)>0">
						<xsl:attribute name="codeSystem"><xsl:value-of select="$CD_codeSys"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="string-length($CD_codeSysNm)>0">
						<xsl:attribute name="codeSystemName"><xsl:value-of select="$CD_codeSysNm"/></xsl:attribute>
					</xsl:if>
				</code>
			</xsl:when>
			<xsl:otherwise>
				<code xmlns="urn:hl7-org:v3" nullFlavor="NI"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="make_name">
		<xsl:param name="trimNm"/>
		<name xmlns="urn:hl7-org:v3">
			<xsl:if test="string-length($trimNm/trim:EN/trim:use)">
				<xsl:attribute name="use"><xsl:value-of select="$trimNm/trim:EN/trim:use"/></xsl:attribute>
			</xsl:if>
			<xsl:for-each select="$trimNm/trim:EN/trim:part">
				<xsl:if test="trim:type='FAM'">
					<family>
						<xsl:value-of select="trim:ST"/>
					</family>
				</xsl:if>
				<xsl:if test="trim:type='GIV'">
					<given>
						<xsl:value-of select="trim:ST"/>
					</given>
				</xsl:if>
			</xsl:for-each>
		</name>
	</xsl:template>
</xsl:stylesheet>
