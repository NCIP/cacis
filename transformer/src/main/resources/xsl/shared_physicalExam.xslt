<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3"
  xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
  <xsl:template name="physicalExam_section">
    <xsl:param name="trim_physicalExam"/>
    <xsl:param name="trim_priorBreastExams"/>
    <xsl:param name="trim_currentBreastExam"/>
    <xsl:param name="trim_findings"/>

    <xsl:if test="$trim_physicalExam or $trim_priorBreastExams or $trim_currentBreastExam or $trim_findings">
      <component>
        <section>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.3.24" assigningAuthorityName="IHE PCC"/>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.15" assigningAuthorityName="IHE PCC"/>
          <templateId root="2.16.840.1.113883.3.88.11.83.118" assigningAuthorityName="HITSP C83"/>
          <templateId root="2.16.840.1.113883.10.20.2.10" assigningAuthorityName="HL7HistoryAndPhysicalNote"/>
          <id nullFlavor="UNK"/>
          <code code="29545-1" displayName="PHYSICAL EXAMINATION" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
            <originalText>Physical Exam</originalText>
          </code>
          <title>Physical exam</title>
          <text/>

          <xsl:if test="$trim_physicalExam">
            <xsl:for-each select="$trim_physicalExam/trim:act/trim:relationship[@name='peObj']">
              <!--skip empty sub section-->
              <xsl:if test="count(current()//trim:SETCE)>0">
                <xsl:call-template name="physicalExam_subSection">
                  <xsl:with-param name="trim_subsec" select="current()"/>
                </xsl:call-template>
              </xsl:if>
            </xsl:for-each>
          </xsl:if>

          <xsl:if test="$trim_priorBreastExams or $trim_currentBreastExam or $trim_findings">
            <xsl:call-template name="physicalExam_breastExams">
              <xsl:with-param name="trim_priorBreastExams" select="$trim_priorBreastExams"/>
              <xsl:with-param name="trim_currentBreastExam" select="$trim_currentBreastExam"/>
              <xsl:with-param name="trim_findings" select="$trim_findings"/>
            </xsl:call-template>
          </xsl:if>
        </section>
      </component>
    </xsl:if>
  </xsl:template>

  <xsl:template name="physicalExam_subSection">
    <xsl:param name="trim_subsec"/>
    <xsl:param name="secName" select="$trim_subsec/trim:act/trim:title/trim:ST"/>
    <component>
      <section>
        <xsl:choose>
          <xsl:when test="$secName='General'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.16" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="10210-3" displayName="GENERAL STATUS" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'Skin'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.17" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="29302-7" displayName="INTEGUMENTARY SYSTEM" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'Eye'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.19" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="10197-2" displayName="EYE" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'HENT'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.20" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="11393-6" displayName="EARS and NOSE and MOUTH and THROAT" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'Lymph'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.32" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="11447-0" displayName="HEMATOLOGIC+LYMPHATIC+IMMUNOLOGIC SYSTEM" codeSystem="2.16.840.1.113883.6.1"
              codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'Respiratory'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.30" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="11412-4" displayName="RESPIRATORY SYSTEM" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'Cardiovascular'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.29" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="10200-4" displayName="HEART" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'GI / Abdomen'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.31" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="10191-5" displayName="ABDOMEN" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'GU (Female)'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.36" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="11400-9" displayName="GENITALIA" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'GU (Male)'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.36" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="11400-9" displayName="GENITALIA" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'Musculoskeletal'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.34" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="11410-8" displayName="MUSCULOSKELETAL SYSTEM" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'Neurological'">
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.35" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="10202-0" displayName="NEUROLOGIC SYSTEM" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
          <xsl:when test="$secName = 'Psychiatric'">
            <id nullFlavor="NI"/>
            <code code="11452-0" displayName="Psychiatric findings" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
              <originalText>
                <xsl:value-of select="$secName"/>
              </originalText>
            </code>
          </xsl:when>
        </xsl:choose>
        <xsl:for-each select="$trim_subsec/trim:act/trim:observation/trim:value/trim:SETCE">
          <xsl:call-template name="physicalExam_observation">
            <xsl:with-param name="trim_entry" select="current()"/>
            <xsl:with-param name="trim_label" select="current()/../trim:label"/>
          </xsl:call-template>
        </xsl:for-each>
      </section>
    </component>
  </xsl:template>

  <xsl:template name="physicalExam_observation">
    <xsl:param name="trim_entry"/>
    <xsl:param name="trim_label"/>
    <entry>
      <!-- normal finding -->
      <observation classCode="OBS" moodCode="EVN">
        <xsl:variable name="negId">
          <xsl:call-template name="getNegationInd">
            <xsl:with-param name="trim_dispNm" select="$trim_entry/ trim:displayName"/>
          </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="codeTranslation">
          <value>
            <xsl:call-template name="translateCode">
              <xsl:with-param name="trim_dispNm" select="$trim_entry/trim:displayName"/>
            </xsl:call-template>
          </value>
        </xsl:variable>
        <!-- If negId='true', then instantiate negationInd-->
        <xsl:if test="$negId='true'">
          <xsl:attribute name="negationInd">true</xsl:attribute>
        </xsl:if>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
        <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="HL7 CCD"/>

        <id nullFlavor="NI"/>
        <code code="29545-1" displayName="Physical Finding" codeSystemName="LOINC" codeSystem="2.16.840.1.113883.6.1"/>
        <text>
          <reference value="PE3"/>
        </text>
        <statusCode code="completed"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <!-- Output the value element that was translated previously in the variable -->
        <xsl:copy-of select="$codeTranslation"/>
        <!-- Determine normal vs abnormal based on label -->
        <xsl:choose>
          <xsl:when test="$trim_label = 'Normal' and $negId='true'">
            <interpretationCode code="A" displayName="abnormal" codeSystem="2.16.840.1.113883.5.83"
              codeSystemName="HL7 ObservationIntepretation"/>
          </xsl:when>
          <xsl:when test="$trim_label = 'Normal' and not($negId='true')">
            <interpretationCode code="N" displayName="normal" codeSystem="2.16.840.1.113883.5.83"
              codeSystemName="HL7 ObservationIntepretation"/>
          </xsl:when>
          <xsl:when test="$trim_label = 'Abnormal'">
            <interpretationCode code="A" displayName="abnormal" codeSystem="2.16.840.1.113883.5.83"
              codeSystemName="HL7 ObservationIntepretation"/>
          </xsl:when>
        </xsl:choose>
      </observation>
    </entry>
  </xsl:template>

  <xsl:template name="physicalExam_breastExams">
    <xsl:param name="trim_priorBreastExams"/>
    <xsl:param name="trim_currentBreastExam"/>
    <xsl:param name="trim_findings"/>

    <component>
      <section>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.9.28" assigningAuthorityName="IHE"/>
        <id nullFlavor="NI"/>
        <code code="10193-1" displayName="Physical Findings - Breasts" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
        <text/>
        <xsl:if test="$trim_priorBreastExams">
          <xsl:for-each
            select="$trim_priorBreastExams/trim:act/trim:relationship[@name='findings']/trim:act/trim:relationship[@name='finding']">
            <xsl:call-template name="physicalExam_finding">
              <xsl:with-param name="trim_relationship" select="current()"/>
              <xsl:with-param name="examTitle" select="string('Prior Breast Exam')"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:if>

        <xsl:if test="$trim_currentBreastExam">
          <xsl:call-template name="physicalExam_currentBreastExam">
            <xsl:with-param name="trim_breastExam" select="$trim_currentBreastExam"/>
            <xsl:with-param name="examTitle" select="string('Breast Exam')"/>
          </xsl:call-template>
        </xsl:if>

        <xsl:if test="$trim_findings">
          <xsl:for-each select="$trim_findings/trim:act/trim:relationship[@name='finding']">
            <xsl:call-template name="physicalExam_finding">
              <xsl:with-param name="trim_relationship" select="current()"/>
              <xsl:with-param name="examTitle" select="string('Breast Findings')"/>
            </xsl:call-template>
          </xsl:for-each>
        </xsl:if>
      </section>
    </component>
  </xsl:template>
  <xsl:template match="@*|node()" mode="priorBreastExamFindings"/>
  <xsl:template match="@*|node()" mode="priorBreastExamFindingValue"/>

  <xsl:template name="physicalExam_finding">
    <xsl:param name="trim_relationship"/>
    <xsl:param name="examTitle"/>

    <xsl:variable name="trim_laterality"
      select="$trim_relationship/trim:act/trim:relationship[@name='laterality']/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
    <xsl:if test="$trim_relationship">
      <entry>
        <act classCode="ACT" moodCode="EVN">
          <!-- CCD procedure template -->
          <templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
          <id nullFlavor="NI"/>
          <code code="46662001" displayName="examination of breast" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT">
            <originalText>
              <xsl:value-of select="$examTitle"/>
            </originalText>
            <qualifier>
              <name code="272741003" displayName="laterality" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT"/>
              <value>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="$trim_laterality"/>
                  <xsl:with-param name="noXSI" select="'true'"/>
                </xsl:call-template>
              </value>
            </qualifier>
          </code>
          <text>
            <reference>
              <xsl:attribute name="value">
                <xsl:value-of select="generate-id($trim_relationship)"/>
              </xsl:attribute>
            </reference>
          </text>
          <statusCode code="completed"/>
          <!-- This is the date entered in the TRIM: /trim/act/relationship["priorBreastExam"]/act/relationship["examdate"]/act/effectiveTime/TS/value -->
          <effectiveTime>
            <xsl:attribute name="value">
              <xsl:value-of
                select="$trim_relationship/../../../trim:relationship[@name='examDate']/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
            </xsl:attribute>
          </effectiveTime>
          <xsl:variable name="trim_abnormality"
            select="$trim_relationship/trim:act/trim:relationship[@name='abnormality']/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
          <xsl:if test="$trim_abnormality != ''">
            <entryRelationship typeCode="COMP">
              <observation classCode="OBS" moodCode="EVN">
                <!-- CCD Problem observation template -->
                <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
                <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
                <statusCode code="completed"/>
                <!-- Abnormality -->
                <value>
                  <xsl:call-template name="translateCode">
                    <xsl:with-param name="trim_dispNm" select="$trim_abnormality"/>
                  </xsl:call-template>
                </value>
                <xsl:apply-templates
                  select="$trim_relationship/trim:act/trim:relationship[@name != 'abnormality' and @name != 'laterality']"
                  mode="priorBreastExamFindings"/>
              </observation>
            </entryRelationship>
          </xsl:if>
        </act>
      </entry>
    </xsl:if>
  </xsl:template>

  <xsl:template match="trim:relationship[@name = 'size']" mode="priorBreastExamFindings">
    <entryRelationship typeCode="SUBJ">
      <!-- Dimension -->
      <observation classCode="OBS" moodCode="EVN">
        <!-- CCD Result observation template -->
        <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm" select="'size'"/>
          </xsl:call-template>
        </code>
        <statusCode code="completed"/>
        <xsl:variable name="sz1" select="trim:act/trim:observation/trim:value[1]/trim:PQ/trim:value"/>
        <xsl:variable name="sz2" select="trim:act/trim:observation/trim:value[2]/trim:PQ/trim:value"/>
        <xsl:variable name="unit" select="trim:act/trim:observation/trim:value[3]/trim:CE/trim:displayName"/>
        <value xsi:type="ST">
          <xsl:if test="string-length($sz1)>0">
            <xsl:value-of select="$sz1"/>
          </xsl:if>
          <xsl:if test="string-length($sz2)>0"> x <xsl:value-of select="$sz2"/>
          </xsl:if>
          <xsl:if test="string-length($unit)>0">
            <xsl:text> </xsl:text>
            <xsl:choose>
              <xsl:when test="$unit='CM'">cm</xsl:when>
              <xsl:when test="$unit='MM'">mm</xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="$unit"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:if>
        </value>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template match="trim:relationship[@name = 'quality' or @name = 'mobility' or @name = 'suspicion']" mode="priorBreastExamFindings">
    <xsl:variable name="theCode">
      <code>
        <xsl:call-template name="translateCode">
          <xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value[1]/trim:label"/>
        </xsl:call-template>
      </code>
    </xsl:variable>
    <entryRelationship typeCode="SUBJ">
      <observation classCode="OBS" moodCode="EVN">
        <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
        <id nullFlavor="NI"/>
        <xsl:choose>
          <xsl:when test="$theCode/cda:code/@nullFlavor">
            <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:copy-of select="$theCode"/>
          </xsl:otherwise>
        </xsl:choose>
        <statusCode code="completed"/>
        <xsl:apply-templates select="." mode="priorBreastExamFindingValue"/>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template match="trim:relationship[@name = 'location']" mode="priorBreastExamFindings">
    <entryRelationship typeCode="SUBJ">
      <observation classCode="OBS" moodCode="EVN">
        <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
        <id nullFlavor="NI"/>
        <code code="443772002" displayName="clockface position of tumor" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT">
          <originalText>location</originalText>
        </code>
        <statusCode code="completed"/>
        <xsl:choose>
          <xsl:when test="string-length(trim:act/trim:observation/trim:value/trim:ST)>0">
            <value xsi:type="ST">
              <xsl:value-of select="trim:act/trim:observation/trim:value/trim:ST"/>
            </value>
          </xsl:when>
          <xsl:when test="string-length(trim:act/trim:observation/trim:value/trim:CE/trim:displayName)>0">
            <value>
              <xsl:call-template name="translateCode">
                <xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
              </xsl:call-template>
            </value>
          </xsl:when>
        </xsl:choose>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template match="trim:relationship[@name = 'biospy']" mode="priorBreastExamFindings">
    <xsl:if test="trim:act/trim:observation/trim:value/trim:CE/trim:displayName='Yes'">
      <entryRelationship typeCode="SUBJ">
        <procedure classCode="PROC" moodCode="EVN">
          <templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
          <id nullFlavor="NI"/>
          <code code="122548005" displayName="biopsy of breast" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
            <originalText>biopsy done</originalText>
          </code>
          <text>
            <reference>
              <xsl:attribute name="value">
                <xsl:value-of select="generate-id(.)"/>
              </xsl:attribute>
            </reference>
          </text>
          <statusCode code="completed"/>
        </procedure>
      </entryRelationship>
    </xsl:if>
  </xsl:template>
  <xsl:template match="trim:relationship[@name = 'correlation']" mode="priorBreastExamFindings">
    <xsl:if test="trim:act/trim:observation/trim:value/trim:CE/trim:displayName='Yes'">
      <entryRelationship typeCode="SUBJ">
        <observation classCode="OBS" moodCode="EVN">
          <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
          <id nullFlavor="NI"/>
          <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
          <statusCode code="completed"/>
          <value xsi:type="CD" code="426291003" displayName="radiographic image correlates with tumor pathology finding"
            codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT">
            <originalText>imaging correlation</originalText>
          </value>
        </observation>
      </entryRelationship>
    </xsl:if>
  </xsl:template>
  <xsl:template match="trim:relationship[@name='quality' or @name='mobility' or @name='suspicion']" mode="priorBreastExamFindingValue">
    <value>
      <xsl:call-template name="translateCode">
        <xsl:with-param name="trim_dispNm" select="trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
      </xsl:call-template>
    </value>
  </xsl:template>
  <xsl:template name="physicalExam_currentBreastExam">
    <xsl:param name="trim_breastExam"/>
    <xsl:param name="examTitle"/>
    <xsl:variable name="trim_breastDensity" select="$trim_breastExam/../trim:relationship[@name='breastDensity']"/>
    <xsl:variable name="trim_nippleCharacteristics"
      select="$trim_breastExam/../trim:relationship[@name='nipple']/trim:act/trim:observation/trim:value[contains(trim:label, 'Nipple Characteristics') or contains(trim:label, 'Nipple characteristics')]"/>
    <xsl:variable name="trim_nippleDischarge"
      select="$trim_breastExam/../trim:relationship[@name='nipple']/trim:act/trim:observation/trim:value[contains(trim:label, 'Nipple Discharge') or contains(trim:label, 'Nipple discharge')]"/>
    <xsl:variable name="trim_nippleDischargeDesp"
      select="$trim_breastExam/../trim:relationship[@name='nipple']/trim:act/trim:observation/trim:value[contains(trim:label, 'YesValueText')]"/>
    <xsl:variable name="trim_skin" select="$trim_breastExam/../trim:relationship[@name='skin']"/>
    <xsl:if test="$trim_breastExam">
      <entry>
        <act classCode="ACT" moodCode="EVN">
          <templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
          <id nullFlavor="NI"/>
          <code>
            <xsl:call-template name="translateCode">
              <xsl:with-param name="noXSI" select="'true'"/>
              <xsl:with-param name="trim_dispNm" select="$trim_breastExam/trim:act/trim:observation/trim:value/trim:valueSet"/>
              <xsl:with-param name="overriddenOriginalText" select="$examTitle"/>
            </xsl:call-template>
          </code>
          <text>
            <reference>
              <xsl:attribute name="value">
                <xsl:value-of select="generate-id($trim_breastExam)"/>
              </xsl:attribute>
            </reference>
          </text>
          <statusCode code="completed"/>
          <effectiveTime>
            <xsl:attribute name="value">
              <xsl:value-of select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
            </xsl:attribute>
          </effectiveTime>
          <!-- breast density -->
          <xsl:if test="$trim_breastDensity">
            <xsl:call-template name="physicalExam_currentBreastExam_density">
              <xsl:with-param name="trim_breastDensity" select="$trim_breastDensity"/>
            </xsl:call-template>
          </xsl:if>
          <!-- nipple characteristics -->
          <xsl:if test="$trim_nippleCharacteristics">
            <xsl:call-template name="physicalExam_currentBreastExam_nippleCharacteristics">
              <xsl:with-param name="trim_nippleCharacteristics" select="$trim_nippleCharacteristics"/>
            </xsl:call-template>
          </xsl:if>
          <!-- nipple discharge -->
          <xsl:if test="$trim_nippleDischarge">
            <xsl:call-template name="physicalExam_currentBreastExam_nippleDischarge">
              <xsl:with-param name="trim_nippleDischarge" select="$trim_nippleDischarge"/>
              <xsl:with-param name="trim_nippleDischargeDesp" select="$trim_nippleDischargeDesp"/>
            </xsl:call-template>
          </xsl:if>
          <xsl:if test="$trim_skin">
            <xsl:call-template name="physicalExam_currentBreastExam_skinChanges">
              <xsl:with-param name="trim_skin" select="$trim_skin"/>
            </xsl:call-template>
          </xsl:if>
        </act>
      </entry>
    </xsl:if>
  </xsl:template>
  <xsl:template name="physicalExam_currentBreastExam_density">
    <xsl:param name="trim_breastDensity"/>
    <entryRelationship typeCode="COMP">
      <observation classCode="OBS" moodCode="EVN">
        <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
        <id nullFlavor="NI"/>
        <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
        <statusCode code="completed"/>
        <value xsi:type="CD" code="125148006" displayName="abnormal density" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT">
          <originalText>Density</originalText>
        </value>
        <entryRelationship typeCode="SUBJ">
          <observation classCode="OBS" moodCode="EVN">
            <code nullFlavor="OTH">
              <originalText>
                <xsl:value-of select="$trim_breastDensity/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
              </originalText>
            </code>
          </observation>
        </entryRelationship>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template name="physicalExam_currentBreastExam_nippleCharacteristics">
    <xsl:param name="trim_nippleCharacteristics"/>
    <xsl:variable name="displayName">
      <xsl:call-template name="string-replace-all">
        <xsl:with-param name="text" select="$trim_nippleCharacteristics/trim:label"/>
        <xsl:with-param name="replace" select="':'"/>
        <xsl:with-param name="by" select="''"/>
      </xsl:call-template>
    </xsl:variable>
    <entryRelationship typeCode="SUBJ">
      <observation classCode="OBS" moodCode="EVN">
        <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm" select="$displayName"/>
          </xsl:call-template>
        </code>
        <statusCode code="completed"/>
        <value>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="trim_dispNm" select="$trim_nippleCharacteristics/trim:CE/trim:displayName"/>
          </xsl:call-template>
        </value>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template name="physicalExam_currentBreastExam_nippleDischarge">
    <xsl:param name="trim_nippleDischarge"/>
    <xsl:param name="trim_nippleDischargeDesp"/>
    <xsl:variable name="dischVal">
      <xsl:text>Nipple discharge </xsl:text>
      <xsl:choose>
			<xsl:when test="$trim_nippleDischarge/trim:CE/trim:displayName='No'">
				<xsl:value-of select="string('no')"/>
			</xsl:when>
	  </xsl:choose>
    </xsl:variable>
    <xsl:variable name="dischDesp" select="$trim_nippleDischargeDesp/trim:ST"/>
    <entryRelationship typeCode="SUBJ">
      <observation classCode="OBS" moodCode="EVN">
        <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm" select="$dischVal"/>
          </xsl:call-template>
        </code>
        <statusCode code="completed"/>
        <xsl:if test="string-length($dischDesp)>0">
          <value xsi:type="ST">
            <xsl:value-of select="$dischDesp"/>
          </value>
        </xsl:if>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template name="physicalExam_currentBreastExam_skinChanges">
    <xsl:param name="trim_skin"/>
    <xsl:if test="$trim_skin/trim:act/trim:observation/trim:value/trim:CE/trim:displayName = 'Yes'">
      <entryRelationship typeCode="SUBJ">
        <observation classCode="OBS" moodCode="EVN">
          <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
          <id nullFlavor="NI"/>
          <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
          <statusCode code="completed"/>
          <value xsi:type="CD" code="170859004" displayName="skin symptom changes" codeSystem="2.16.840.1.113883.6.96"
            codeSystemName="SNOMED-CT">
				<originalText>Skin changes</originalText>
            </value>
          <xsl:call-template name="physicalExam_currentBreastExam_skinDimensions">
            <xsl:with-param name="trim_dimensions" select="$trim_skin/trim:act/trim:relationship[@name='dimensions']"/>
          </xsl:call-template>
          <xsl:call-template name="physicalExam_currentBreastExam_skinLocation">
            <xsl:with-param name="trim_location" select="$trim_skin/trim:act/trim:relationship[@name='location']"/>
          </xsl:call-template>
          <xsl:for-each select="$trim_skin/trim:act/trim:observation/trim:value/trim:SETCE">
            <xsl:call-template name="physicalExam_currentBreastExam_skinChangeValue">
              <xsl:with-param name="trim_value" select="current()"/>
            </xsl:call-template>
          </xsl:for-each>
        </observation>
      </entryRelationship>
    </xsl:if>
  </xsl:template>
  <xsl:template name="physicalExam_currentBreastExam_skinDimensions">
    <xsl:param name="trim_dimensions"/>
    <entryRelationship typeCode="SUBJ">
      <!-- Dimension -->
      <observation classCode="OBS" moodCode="EVN">
        <!-- CCD Result observation template -->
        <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm" select="$trim_dimensions/trim:act/trim:title/trim:ST"/>
          </xsl:call-template>
        </code>
        <statusCode code="completed"/>
        <xsl:variable name="dm1" select="$trim_dimensions/trim:act/trim:observation/trim:value[1]/trim:ST"/>
        <xsl:variable name="dm2" select="$trim_dimensions/trim:act/trim:observation/trim:value[2]/trim:ST"/>
        <xsl:variable name="unit" select="$trim_dimensions/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
        <value xsi:type="ST">
          <xsl:if test="string-length($dm1)>0">
            <xsl:value-of select="$dm1"/>
          </xsl:if>
          <xsl:if test="string-length($dm2)>0"> x <xsl:value-of select="$dm2"/>
          </xsl:if>
          <xsl:if test="string-length($unit)>0">
            <xsl:text> </xsl:text>
            <xsl:choose>
              <xsl:when test="$unit='CM'">cm</xsl:when>
              <xsl:when test="$unit='MM'">mm</xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="$unit"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:if>
        </value>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template name="physicalExam_currentBreastExam_skinLocation">
    <xsl:param name="trim_location"/>
    <entryRelationship typeCode="SUBJ">
      <observation classCode="OBS" moodCode="EVN">
        <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm" select="$trim_location/trim:act/trim:observation/trim:value/trim:label"/>
          </xsl:call-template>
        </code>
        <statusCode code="completed"/>
        <value xsi:type="ST">
          <xsl:value-of select="$trim_location/trim:act/trim:observation/trim:value/trim:ST"/>
        </value>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template name="physicalExam_currentBreastExam_skinChangeValue">
    <xsl:param name="trim_value"/>
    <entryRelationship typeCode="SUBJ">
      <observation classCode="OBS" moodCode="EVN">
        <!-- Problem observation templates -->
        <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
        <id nullFlavor="NI"/>
        <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
        <statusCode code="completed"/>
        <value>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="trim_dispNm" select="$trim_value/trim:displayName"/>
          </xsl:call-template>
        </value>
      </observation>
    </entryRelationship>
  </xsl:template>
</xsl:stylesheet>
