<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3"
  xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
  <!-- breast cancer history label prefix -->
  <xsl:import href="shared_functions.xslt"/>
  <xsl:variable name="bcLblPrfx" select="'Breast Cancer History:'"/>
  <xsl:template name="problems_section">
    <xsl:param name="trim_initialSymptom"/>
    <xsl:param name="trim_menopausalStatus"/>
    <!-- past breast history -->
    <xsl:param name="trim_PBH"/>
    <xsl:param name="trim_impressions"/>
    <xsl:param name="trim_histologyCalcification"/>
    <xsl:param name="trim_staging"/>
    <xsl:param name="trim_calcStaging"/>
    <xsl:param name="trim_adjuStaging"/>
    <xsl:param name="trim_surgeon"/>
    <xsl:param name="trim_t4tumor"/>
    <xsl:param name="trim_rspStatus"/>
    <xsl:param name="trim_rspDates"/>
    <xsl:param name="trim_surgicalComplications"/>
    <xsl:param name="trim_histry"/>
    <xsl:param name="trim_phistry"/>
    
    <!-- onstudy pathology -->
    <xsl:param name="trim_tumorLaterality"/>
    <xsl:param name="trim_massIdentified"/>
    <xsl:param name="trim_leftPathDetail"/>
    <xsl:param name="trim_rightPathDetail"/>
    <!-- lesion -->
    <xsl:param name="trim_lesion"/>
    <xsl:param name="trim_lesions"/>
    <!-- staging -->
    <xsl:param name="trim_rightstg"/>
    <xsl:param name="trim_leftstg"/>
    <xsl:param name="trim_surgeonstg"/>
    <xsl:param name="trim_rightSbrGrade"/>
    <xsl:param name="trim_leftSbrGrade"/>
    <!-- follow up -->
    <xsl:param name="trim_survivalSts"/>
    <xsl:param name="trim_rptPeriod" />
    
    <xsl:if
      test="$trim_PBH or $trim_menopausalStatus or $trim_initialSymptom or $trim_impressions or $trim_histologyCalcification or $trim_staging or $trim_rspDates 
		    or $trim_rspStatus or $trim_surgicalComplications or $trim_histry or $trim_phistry or $trim_tumorLaterality or $trim_massIdentified
		    or $trim_leftPathDetail or $trim_rightPathDetail or $trim_lesion or $trim_lesions or $trim_rightstg or $trim_leftstg or $trim_rightSbrGrade or $trim_leftSbrGrade
		    or $trim_survivalSts">
      <component>
        <section>
          <!-- Problems Section Templates -->
          <templateId root="2.16.840.1.113883.3.88.11.83.103" assigningAuthorityName="HITSP/C83"/>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.3.6" assigningAuthorityName="IHE PCC"/>
          <templateId root="2.16.840.1.113883.10.20.1.11" assigningAuthorityName="HL7 CCD"/>
          <code code="11450-4" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Problem list"/>
          <title>Problems</title>
          <text/>
          <xsl:if test="$trim_histry">
            <xsl:variable name="trim_c_hpi" select="$trim_histry/trim:act/trim:relationship[@name='historyDone']"/>
            <xsl:variable name="trim_c_phist" select="$trim_histry/trim:act/trim:relationship[@name='pastHistory']"/>
            <xsl:if test="$trim_c_hpi">
              <xsl:for-each select="$trim_c_hpi/trim:act/trim:observation">
                <xsl:call-template name="hpi_entry">
                  <xsl:with-param name="trim_entry" select="current()"/>
                  <xsl:with-param name="entryTitle" select="'History'"/>
                  <xsl:with-param name="effTime" select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
                </xsl:call-template>
              </xsl:for-each>
            </xsl:if>
            <xsl:if test="$trim_c_phist">
              <xsl:for-each select="$trim_c_phist/trim:act/trim:observation">
                <xsl:call-template name="pmh_entry">
                  <xsl:with-param name="trim_entry" select="current()"/>
                  <xsl:with-param name="entryTitle" select="'Past Medical History'"/>
                  <xsl:with-param name="effTime" select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
                </xsl:call-template>
              </xsl:for-each>
            </xsl:if>
          </xsl:if>
          <xsl:if test="$trim_phistry">
            <xsl:variable name="trim_p_hpi" select="$trim_phistry/trim:act/trim:relationship[@name='historyDone']"/>
            <xsl:variable name="trim_p_phist" select="$trim_phistry/trim:act/trim:relationship[@name='pastHistory']"/>
            <xsl:variable name="trim_p_effTime" select="$trim_phistry/trim:act/trim:observation/trim:value/trim:TS/trim:value"/>
            <xsl:if test="$trim_p_hpi">
              <xsl:for-each select="$trim_p_hpi/trim:act/trim:observation">
                <xsl:call-template name="hpi_entry">
                  <xsl:with-param name="trim_entry" select="current()"/>
                  <xsl:with-param name="entryTitle" select="'Prior History'"/>
                  <xsl:with-param name="effTime" select="$trim_p_effTime"/>
                </xsl:call-template>
              </xsl:for-each>
            </xsl:if>
            <xsl:if test="$trim_p_phist">
              <xsl:for-each select="$trim_p_phist/trim:act/trim:observation">
                <xsl:call-template name="pmh_entry">
                  <xsl:with-param name="trim_entry" select="current()"/>
                  <xsl:with-param name="entryTitle" select="'Prior Past Medical History'"/>
                  <xsl:with-param name="effTime" select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
                </xsl:call-template>
              </xsl:for-each>
            </xsl:if>
          </xsl:if>
          <xsl:if test="$trim_initialSymptom">
            <xsl:for-each select="$trim_initialSymptom/trim:act/trim:observation">
              <xsl:call-template name="initialSymptoms_entry">
                <xsl:with-param name="trim_entry" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_menopausalStatus">
            <xsl:for-each select="$trim_menopausalStatus/trim:act/trim:observation">
              <xsl:call-template name="menopausalStatus_observation">
                <xsl:with-param name="trim_entry" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_PBH">
            <xsl:call-template name="PBH_entry">
              <xsl:with-param name="theAct" select="$trim_PBH"/>
            </xsl:call-template>
          </xsl:if>
          <xsl:if test="$trim_impressions">
            <xsl:for-each select="$trim_impressions/trim:act/trim:relationship[@name='impression'][@enabled='true' or not(@enabled)]">
              <xsl:call-template name="problems_impression">
                <xsl:with-param name="trim_relationship" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_histologyCalcification">
            <xsl:for-each select="$trim_histologyCalcification">
              <xsl:call-template name="problems_histologyCalcification">
                <xsl:with-param name="trim_relationship" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_staging">
            <xsl:for-each select="$trim_staging/trim:act/trim:observation">
              <xsl:call-template name="problems_staging">
                <xsl:with-param name="trim_entry" select="current()"/>
                <xsl:with-param name="trim_calcStagingObs" select="$trim_calcStaging/trim:act/trim:observation"/>
                <xsl:with-param name="trim_adjuStagingObs" select="$trim_adjuStaging/trim:act/trim:observation"/>
                <xsl:with-param name="trim_surgeonParticipant" select="$trim_surgeon/trim:act/trim:participation"/>
                <xsl:with-param name="trim_t4TumorObs" select="$trim_t4tumor/trim:act/trim:observation"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_rspStatus">
            <xsl:for-each select="$trim_rspStatus/trim:act/trim:observation">
              <xsl:call-template name="RSP_ProblemObs">
                <xsl:with-param name="trimVal" select="current()/trim:value"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_rspDates">
            <xsl:call-template name="RspDates_problemAct">
              <xsl:with-param name="rspDatesAct" select="$trim_rspDates/trim:act"/>
            </xsl:call-template>
          </xsl:if>
          <xsl:if test="$trim_surgicalComplications">
            <xsl:for-each
              select="$trim_surgicalComplications/trim:act/trim:relationship[@name='infectious'][trim:act/trim:observation/trim:value[1]/trim:CE/trim:displayName!='']">
              <xsl:call-template name="problems_woundInfectious">
                <xsl:with-param name="trim_relationship" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
            <xsl:for-each select="$trim_surgicalComplications/trim:act/trim:relationship[@name='nonInfectious']">
              <xsl:call-template name="problems_woundNonInfectious">
                <xsl:with-param name="trim_relationship" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_tumorLaterality">
            <xsl:for-each select="$trim_tumorLaterality">
              <xsl:call-template name="Path_ProblemEntry">
                <xsl:with-param name="trim_act" select="current()/trim:act"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_massIdentified">
            <xsl:for-each select="$trim_massIdentified">
              <xsl:call-template name="Path_ProblemEntry">
                <xsl:with-param name="trim_act" select="current()/trim:act"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_leftPathDetail">
            <xsl:for-each select="$trim_leftPathDetail/trim:act/trim:relationship[@enabled='true' or not(@enabled)]/trim:act">
              <xsl:call-template name="Path_ProblemEntry">
                <xsl:with-param name="trim_act" select="current()"/>
                <xsl:with-param name="prefix" select="'Left Pathology Details'"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_rightPathDetail">
            <xsl:for-each select="$trim_rightPathDetail/trim:act/trim:relationship[@enabled='true' or not(@enabled)]/trim:act">
              <xsl:call-template name="Path_ProblemEntry">
                <xsl:with-param name="trim_act" select="current()"/>
                <xsl:with-param name="prefix" select="'Right Pathology Details'"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_lesion">
            <xsl:for-each select="$trim_lesion/trim:act">
              <xsl:call-template name="Les_ProblemEntry">
                <xsl:with-param name="trim_act" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_lesions">
            <xsl:for-each select="$trim_lesions/trim:act">
              <xsl:call-template name="Les_ProblemEntry2">
                <xsl:with-param name="trim_act" select="current()"/>
                <xsl:with-param name="obsType" select="'Lesions'"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_rightstg or $trim_rightSbrGrade">
            <xsl:call-template name="postSurg_ProblemEntry">
              <xsl:with-param name="trim_act" select="$trim_rightstg/trim:act"/>
              <xsl:with-param name="surgeon" select="$trim_surgeonstg"/>
              <xsl:with-param name="trim_sbrgrd" select="$trim_rightSbrGrade"/>
            </xsl:call-template>
          </xsl:if>
          <xsl:if test="$trim_leftstg or $trim_leftSbrGrade">
            <xsl:call-template name="postSurg_ProblemEntry">
              <xsl:with-param name="trim_act" select="$trim_leftstg/trim:act"/>
              <xsl:with-param name="surgeon" select="$trim_surgeonstg"/>
              <xsl:with-param name="trim_sbrgrd" select="$trim_leftSbrGrade"/>
            </xsl:call-template>
          </xsl:if>
          <xsl:if test="$trim_survivalSts">
            <xsl:call-template name="survSts_ProblemEntry">
              <xsl:with-param name="trim_act" select="$trim_survivalSts/trim:act"/>
              <xsl:with-param name="trim_prd" select="$trim_rptPeriod"/>
            </xsl:call-template>            
          </xsl:if>
        </section>
      </component>
    </xsl:if>
  </xsl:template>
  <xsl:template name="problems_woundNonInfectious">
    <xsl:param name="trim_relationship"/>
    <entry typeCode="DRIV">
      <act classCode="ACT" moodCode="EVN">
        <!-- Problem act templates -->
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code nullFlavor="NA">
          <originalText>
            <xsl:value-of select="$trim_relationship/trim:act/trim:title/trim:ST"/>
          </originalText>
        </code>
        <text>
          <reference>
            <xsl:attribute name="value">
              <xsl:value-of select="generate-id($trim_relationship)"/>
            </xsl:attribute>
          </reference>
        </text>
        <statusCode code="active"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <xsl:for-each select="$trim_relationship/trim:act/trim:observation/trim:value[trim:valueSet='nonInfectiousValues']/trim:SETCE">
          <xsl:call-template name="problems_surgicalComplicationAssertion">
            <xsl:with-param name="trim_setce" select="current()"/>
          </xsl:call-template>
        </xsl:for-each>
      </act>
    </entry>
  </xsl:template>
  <xsl:template name="problems_woundInfectious">
    <xsl:param name="trim_relationship"/>
    <entry typeCode="DRIV">
      <act classCode="ACT" moodCode="EVN">
        <!-- Problem act templates -->
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="trim_dispNm" select="$trim_relationship/trim:act/trim:title/trim:ST"/>
            <xsl:with-param name="noXSI" select="'true'"/>
          </xsl:call-template>
        </code>
        <text>
          <reference>
            <xsl:attribute name="value">
              <xsl:value-of select="generate-id($trim_relationship)"/>
            </xsl:attribute>
          </reference>
        </text>
        <statusCode code="active"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <entryRelationship typeCode="SUBJ" inversionInd="false">
          <observation classCode="OBS" moodCode="EVN" negationInd="false">
            <!-- Problem Observation template -->
            <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="HL7 CCD"/>
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
            <text>
              <reference>
                <xsl:attribute name="value">
                  <xsl:value-of select="generate-id($trim_relationship/trim:act/trim:observation)"/>
                </xsl:attribute>
              </reference>
            </text>
            <statusCode code="completed"/>
            <effectiveTime>
              <low>
                <xsl:attribute name="value">
                  <xsl:value-of select="$trim_relationship/trim:act/trim:observation/trim:value[trim:label='Date: ']/trim:TS/trim:value"/>
                </xsl:attribute>
              </low>
            </effectiveTime>
            <value xsi:type="CD" code="433202001" displayName="surgical site infection" codeSystem="2.16.840.1.113883.6.96"
              codeSystemName="SNOMED CT">
              <originalText>
                <xsl:value-of select="$trim_relationship/trim:act/trim:observation/trim:value[1]/trim:label"/>
                <xsl:value-of select="$trim_relationship/trim:act/trim:observation/trim:value[1]/trim:CE/trim:displayName"/>
              </originalText>
            </value>
            <xsl:variable name="trim_implantRecons"
              select="$trim_relationship/trim:act/trim:observation/trim:value[trim:label='Did patient have implant reconstruction? ']"/>
            <xsl:if test="$trim_implantRecons[not(@enabled) or @enabled='true']">
              <entryRelationship typeCode="SUBJ" inversionInd="false">
                <xsl:if test="$trim_implantRecons/trim:CE/trim:displayName!='Yes'">
                  <xsl:attribute name="inversionInd">true</xsl:attribute>
                </xsl:if>
                <procedure classCode="PROC" moodCode="EVN">
                  <!-- procedure template -->
                  <templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
                  <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.19" assigningAuthorityName="IHE"/>
                  <templateId root="2.16.840.1.113883.3.88.11.83.17" assigningAuthorityName="HITSP"/>
                  <id nullFlavor="NI"/>
                  <code code="172061002" displayName="reconstruction of breast with expander or prosthesis"
                    codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
                    <originalText>
                      <xsl:value-of select="$trim_implantRecons/trim:label"/>
                      <xsl:text>: </xsl:text>
                      <xsl:value-of select="$trim_implantRecons/trim:CE/trim:displayName"/>
                    </originalText>
                  </code>
                  <text>
                    <reference value="pointer"/>
                  </text>
                  <statusCode code="completed"/>
                  <effectiveTime xsi:type="IVL_TS">
                    <!-- Start date -->
                    <low nullFlavor="UNK"/>
                    <!-- End date -->
                    <high nullFlavor="UNK"/>
                  </effectiveTime>
                </procedure>
              </entryRelationship>
            </xsl:if>
            <xsl:for-each
              select="$trim_relationship/trim:act/trim:observation/trim:value[trim:label='Criteria' or trim:label='Treatment']/trim:SETCE">
              <xsl:call-template name="problems_surgicalComplicationAssertion">
                <xsl:with-param name="trim_setce" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </observation>
        </entryRelationship>
      </act>
    </entry>
  </xsl:template>
  <xsl:template name="problems_surgicalComplicationAssertion">
    <xsl:param name="trim_setce"/>
    <entryRelationship typeCode="SUBJ" inversionInd="false">
      <observation classCode="OBS" moodCode="EVN" negationInd="false">
        <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="HL7 CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
        <text>
          <reference>
            <xsl:attribute name="value">
              <xsl:value-of select="generate-id($trim_setce)"/>
            </xsl:attribute>
          </reference>
        </text>
        <statusCode code="completed"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <value>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="trim_dispNm" select="$trim_setce/trim:displayName"/>
          </xsl:call-template>
        </value>
        <xsl:if test="$trim_setce/trim:displayName = 'Skin Necrosis'">
          <xsl:variable name="skinNecrosisValue" select="$trim_setce/../../trim:value"/>
          <entryRelationship typeCode="REFR">
            <observation classCode="OBS" moodCode="EVN" negationInd="false">
              <!-- Result Observation template -->
              <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
              <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
              <templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
              <id nullFlavor="NI"/>
              <code xsi:type="CD" code="401238003" displayName="length of wound" codeSystem="2.16.840.1.113883.6.96"
                codeSystemName="SNOMED CT"/>
              <text>
                <reference>
                  <xsl:attribute name="value">
                    <xsl:value-of select="generate-id($skinNecrosisValue[2])"/>
                  </xsl:attribute>
                </reference>
              </text>
              <statusCode code="completed"/>
              <effectiveTime nullFlavor="UNK"/>
              <value xsi:type="PQ">
                <xsl:attribute name="value">
                  <xsl:value-of select="$skinNecrosisValue/trim:PQ/trim:value"/>
                </xsl:attribute>
                <xsl:attribute name="unit">
                  <xsl:value-of select="$skinNecrosisValue/trim:PQ/trim:unit"/>
                </xsl:attribute>
              </value>
            </observation>
          </entryRelationship>
        </xsl:if>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template name="RspDates_problemAct">
    <xsl:param name="rspDatesAct"/>
    <entry typeCode="DRIV">
      <act classCode="ACT" moodCode="EVN">
        <!-- problem act -->
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code nullFlavor="NA"/>
        <statusCode code="active"/>
        <effectiveTime nullFlavor="UNK"/>
        <xsl:for-each select="$rspDatesAct/trim:relationship[@enabled='true' or not(@enabled)]">
          <xsl:call-template name="RspDates_problemObs">
            <xsl:with-param name="trimAct" select="current()/trim:act"/>
          </xsl:call-template>
        </xsl:for-each>
      </act>
    </entry>
  </xsl:template>
  <xsl:template name="RspDates_problemObs">
    <xsl:param name="trimAct"/>
    <entryRelationship typeCode="SUBJ" inversionInd="false">
      <observation classCode="OBS" moodCode="EVN">
        <!-- Problem Observation template -->
        <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code code="102468005" displayName="therapeutic response" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
          <originalText>Response date</originalText>
        </code>
        <text>
          <reference>
            <xsl:attribute name="value">
              <xsl:value-of select="generate-id($trimAct)"/>
            </xsl:attribute>
          </reference>
        </text>
        <statusCode code="completed"/>
        <effectiveTime>
          <xsl:choose>
            <xsl:when test="string-length($trimAct/trim:effectiveTime/trim:TS/trim:value)>0">
              <low>
                <xsl:attribute name="value">
                  <xsl:value-of select="$trimAct/trim:effectiveTime/trim:TS/trim:value"/>
                </xsl:attribute>
              </low>
            </xsl:when>
            <xsl:otherwise>
              <low nullFlavor="UNK"/>
            </xsl:otherwise>
          </xsl:choose>
        </effectiveTime>
        <xsl:choose>
          <xsl:when test="string-length($trimAct/trim:title/trim:ST)>0">
            <value>
              <xsl:call-template name="translateCode">
                <xsl:with-param name="trim_dispNm" select="$trimAct/trim:title/trim:ST"/>
              </xsl:call-template>
            </value>
          </xsl:when>
          <xsl:otherwise>
            <value nullFlavor="NI"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$trimAct/trim:observation/trim:value/trim:SETCE/trim:displayName">
          <entryRelationship typeCode="MFST" inversionInd="true">
            <observation classCode="OBS" moodCode="EVN">
              <!-- Observation template -->
              <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
              <id nullFlavor="NI"/>
              <code>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="$trimAct/trim:observation/trim:value/trim:label"/>
                  <xsl:with-param name="noXSI" select="'true'"/>
                </xsl:call-template>
              </code>
              <statusCode code="completed"/>
              <effectiveTime>
                <low nullFlavor="UNK"/>
              </effectiveTime>
              <xsl:for-each select="$trimAct/trim:observation/trim:value/trim:SETCE">
                <value>
                  <xsl:call-template name="translateCode">
                    <xsl:with-param name="trim_dispNm" select="trim:displayName"/>
                  </xsl:call-template>
                </value>
              </xsl:for-each>
            </observation>
          </entryRelationship>
        </xsl:if>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template name="problems_histologyCalcification">
    <xsl:param name="trim_relationship"/>
    <entry typeCode="DRIV">
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code nullFlavor="NA"/>
        <statusCode code="active"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <!-- LeftHistologyCalcification -->
        <entryRelationship typeCode="SUBJ" inversionInd="false">
          <observation classCode="OBS" moodCode="EVN">
            <xsl:if test="$trim_relationship/trim:act/trim:observation/trim:value[not(trim:CE)]">
              <xsl:attribute name="nullFlavor">NI</xsl:attribute>
            </xsl:if>
            <xsl:if test="$trim_relationship/trim:act/trim:observation/trim:value/trim:CE/trim:displayName='No'">
              <xsl:attribute name="negationInd">true</xsl:attribute>
            </xsl:if>
            <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
            <text>
              <reference>
                <xsl:attribute name="value">
                  <xsl:value-of select="generate-id($trim_relationship)"/>
                </xsl:attribute>
              </reference>
            </text>
            <statusCode code="completed"/>
            <effectiveTime>
              <low nullFlavor="UNK"/>
            </effectiveTime>
            <value>
              <xsl:call-template name="translateCode">
                <xsl:with-param name="trim_dispNm" select="$trim_relationship/trim:act/trim:observation/trim:value/trim:label"/>
              </xsl:call-template>
            </value>
          </observation>
        </entryRelationship>
      </act>
    </entry>
  </xsl:template>
  <xsl:template name="problems_staging">
    <xsl:param name="trim_entry"/>
    <xsl:param name="trim_calcStagingObs"/>
    <xsl:param name="trim_adjuStagingObs"/>
    <xsl:param name="trim_surgeonParticipant"/>
    <xsl:param name="trim_t4TumorObs"/>
    <entry typeCode="DRIV">
      <!-- Problem act templates -->
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code nullFlavor="NA">
          <originalText>Staging</originalText>
        </code>
        <statusCode code="active"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <xsl:for-each select="trim:value">
          <entryRelationship typeCode="SUBJ" inversionInd="false">
            <observation classCode="OBS" moodCode="EVN">
              <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
              <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
              <id nullFlavor="NI"/>
              <xsl:choose>
                <xsl:when test="starts-with(trim:label, 'T')">
                  <code code="78873005" displayName="T category" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
                    <originalText>T</originalText>
                  </code>
                </xsl:when>
                <xsl:when test="starts-with(trim:label, 'N')">
                  <code code="277206009" displayName="N category" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
                    <originalText>N</originalText>
                  </code>
                </xsl:when>
                <xsl:when test="starts-with(trim:label, 'M') and not(contains(trim:label, 'Mobility'))">
                  <code code="277208005" displayName="M category" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
                    <originalText>M</originalText>
                  </code>
                </xsl:when>
                <xsl:otherwise>
                  <code>
                    <xsl:call-template name="translateCode">
                      <xsl:with-param name="noXSI" select="'true'"/>
                      <xsl:with-param name="trim_dispNm" select="trim:label"/>
                    </xsl:call-template>
                  </code>
                </xsl:otherwise>
              </xsl:choose>
              <text>
                <reference>
                  <xsl:attribute name="value">
                    <xsl:value-of select="generate-id(current())"/>
                  </xsl:attribute>
                </reference>
              </text>
              <statusCode code="completed"/>
              <effectiveTime>
                <low nullFlavor="UNK"/>
              </effectiveTime>
              <value xsi:type="CD" nullFlavor="NI">
                <originalText>
                  <xsl:value-of select="trim:ST"/>
                </originalText>
              </value>
            </observation>
          </entryRelationship>
        </xsl:for-each>
        <xsl:if test="$trim_calcStagingObs">
          <entryRelationship typeCode="SUBJ" inversionInd="false">
            <observation classCode="OBS" moodCode="EVN">
              <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
              <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
              <id nullFlavor="NI"/>
              <code code="399390009" displayName="tumor-node-metastasis (TNM) stage grouping" codeSystem="2.16.840.1.113883.6.96"
                codeSystemName="SNOMED CT">
                <originalText>Calculated Stage: </originalText>
              </code>
              <text>
                <reference>
                  <xsl:attribute name="value">
                    <xsl:value-of select="generate-id(current())"/>
                  </xsl:attribute>
                </reference>
              </text>
              <statusCode code="completed"/>
              <effectiveTime>
                <low nullFlavor="UNK"/>
              </effectiveTime>
              <value xsi:type="CD" nullFlavor="OTH">
                <originalText>
                  <xsl:value-of select="$trim_calcStagingObs/trim:value/trim:ST"/>
                </originalText>
              </value>
            </observation>
          </entryRelationship>
        </xsl:if>
        <xsl:if test="$trim_adjuStagingObs">
          <entryRelationship typeCode="SUBJ" inversionInd="false">
            <observation classCode="OBS" moodCode="EVN">
              <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
              <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
              <id nullFlavor="NI"/>
              <code code="399390009" displayName="tumor-node-metastasis (TNM) stage grouping" codeSystem="2.16.840.1.113883.6.96"
                codeSystemName="SNOMED CT">
                <originalText>Adjudicated Stage:</originalText>
              </code>
              <text>
                <reference>
                  <xsl:attribute name="value">
                    <xsl:value-of select="generate-id($trim_adjuStagingObs)"/>
                  </xsl:attribute>
                </reference>
              </text>
              <statusCode code="completed"/>
              <effectiveTime>
                <low nullFlavor="UNK"/>
              </effectiveTime>
              <value>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="$trim_adjuStagingObs/trim:value/trim:CE/trim:displayName"/>
                </xsl:call-template>
              </value>
              <xsl:if test="$trim_surgeonParticipant">
                <xsl:call-template name="mapPerformer">
                  <xsl:with-param name="trim_participation" select="$trim_surgeonParticipant"/>
                </xsl:call-template>
              </xsl:if>
            </observation>
          </entryRelationship>
        </xsl:if>
        <xsl:if test="$trim_t4TumorObs/trim:value/trim:CE/trim:displayName">
          <entryRelationship typeCode="SUBJ" inversionInd="false">
            <observation classCode="OBS" moodCode="EVN">
              <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
              <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
              <id nullFlavor="NI"/>
              <code code="396645007" displayName="status of extension by tumor" codeSystem="2.16.840.1.113883.6.96"
                codeSystemName="SNOMED CT">
                <originalText>If patient diagnosed with T4 at baseline, indicate current status of T4 tumor:</originalText>
              </code>
              <text>
                <reference>
                  <xsl:attribute name="value">
                    <xsl:value-of select="generate-id($trim_t4TumorObs)"/>
                  </xsl:attribute>
                </reference>
              </text>
              <statusCode code="completed"/>
              <effectiveTime>
                <low nullFlavor="UNK"/>
              </effectiveTime>
              <value>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="$trim_t4TumorObs/trim:value/trim:CE/trim:displayName"/>
                </xsl:call-template>
              </value>
            </observation>
          </entryRelationship>
        </xsl:if>
      </act>
    </entry>
  </xsl:template>
  <xsl:template name="problems_impression">
    <xsl:param name="trim_relationship"/>
    <xsl:variable name="trim_participation" select="$trim_relationship/trim:act/trim:participation"/>
    <xsl:variable name="trim_diagnosis"
      select="$trim_relationship/trim:act/trim:relationship[@name='diagnosis']/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
    <xsl:variable name="trim_status"
      select="$trim_relationship/trim:act/trim:relationship[@name='status']/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
    <xsl:variable name="trim_staging"
      select="$trim_relationship/trim:act/trim:relationship[@name='staging']/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
    <xsl:variable name="trim_effcTime" select="$trim_relationship/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
    <xsl:if test="string-length($trim_diagnosis)>0">
      <entry typeCode="DRIV">
        <act classCode="ACT" moodCode="EVN">
          <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
          <id nullFlavor="NI"/>
          <code nullFlavor="NA"/>
          <statusCode code="new"/>
          <effectiveTime>
            <xsl:choose>
              <xsl:when test="string-length($trim_effcTime)>0">
                <low>
                  <xsl:attribute name="value">
                    <xsl:value-of select="$trim_effcTime"/>
                  </xsl:attribute>
                </low>
              </xsl:when>
              <xsl:otherwise>
                <low nullFlavor="UNK"/>
              </xsl:otherwise>
            </xsl:choose>
          </effectiveTime>
          <!-- (Diagnosing) Clinician -->
          <xsl:call-template name="mapPerformer">
            <xsl:with-param name="trim_participation" select="$trim_participation"/>
          </xsl:call-template>
          <entryRelationship typeCode="SUBJ" inversionInd="false">
            <observation classCode="OBS" moodCode="EVN">
              <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
              <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
              <id nullFlavor="NI"/>
              <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
              <statusCode code="completed"/>
              <xsl:variable name="effcTm">
                <xsl:value-of select="$trim_relationship/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
              </xsl:variable>
              <effectiveTime>
                <xsl:choose>
                  <xsl:when test="string-length($effcTm)>0">
                    <low>
                      <xsl:attribute name="value">
                        <xsl:value-of select="$effcTm"/>
                      </xsl:attribute>
                    </low>
                  </xsl:when>
                  <xsl:otherwise>
                    <low nullFlavor="UNK"/>
                  </xsl:otherwise>
                </xsl:choose>
              </effectiveTime>
              <value>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="$trim_diagnosis"/>
                </xsl:call-template>
              </value>
              <xsl:if test="string-length($trim_status)>0">
                <entryRelationship typeCode="REFR">
                  <observation classCode="OBS" moodCode="EVN">
                    <code code="33999-4" codeSystem="2.16.840.1.113883.6.1" displayName="Status">
                      <originalText>Status</originalText>
                    </code>
                    <statusCode code="completed"/>
                    <value>
                      <xsl:call-template name="translateCode">
                        <xsl:with-param name="trim_dispNm" select="$trim_status"/>
                      </xsl:call-template>
                    </value>
                  </observation>
                </entryRelationship>
              </xsl:if>
              <xsl:if test="string-length($trim_staging)>0">
                <entryRelationship typeCode="COMP">
                  <observation classCode="OBS" moodCode="EVN">
                    <code codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED-CT" code="371508000" displayName="Tumor stage">
                      <originalText>Staging</originalText>
                    </code>
                    <statusCode code="completed"/>
                    <value>
                      <xsl:call-template name="translateCode">
                        <xsl:with-param name="trim_dispNm" select="$trim_staging"/>
                      </xsl:call-template>
                    </value>
                  </observation>
                </entryRelationship>
              </xsl:if>
            </observation>
          </entryRelationship>
        </act>
      </entry>
    </xsl:if>
  </xsl:template>
  <xsl:template name="hpi_entry">
    <xsl:param name="trim_entry"/>
    <xsl:param name="entryTitle"/>
    <xsl:param name="effTime"/>
    <entry typeCode="DRIV">
      <!-- Problem act templates -->
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm" select="$entryTitle"/>
          </xsl:call-template>
        </code>
        <statusCode code="active"/>
        <effectiveTime>
          <low>
            <xsl:attribute name="value">
              <xsl:value-of select="$effTime"/>
            </xsl:attribute>
          </low>
        </effectiveTime>
        <xsl:for-each select="$trim_entry/trim:value">
          <entryRelationship typeCode="SUBJ" inversionInd="false">
            <observation classCode="OBS" moodCode="EVN">
              <!-- Problem observation templates -->
              <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
              <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
              <id nullFlavor="NI"/>
              <code>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="noXSI" select="'true'"/>
                  <xsl:with-param name="trim_dispNm" select="trim:label"/>
                </xsl:call-template>
              </code>
              <text>
                <reference>
                  <xsl:attribute name="value">
                    <xsl:value-of select="generate-id(current())"/>
                  </xsl:attribute>
                </reference>
              </text>
              <statusCode code="completed"/>
              <effectiveTime>
                <low nullFlavor="UNK"/>
              </effectiveTime>
              <xsl:choose>
                <xsl:when test="string-length(trim:CE/trim:displayName)>0">
                  <value>
                    <xsl:call-template name="translateCode">
                      <xsl:with-param name="trim_dispNm" select="trim:CE/trim:displayName"/>
                    </xsl:call-template>
                  </value>
                </xsl:when>
                <xsl:when test="string-length(trim:ST)>0">
                  <value xsi:type="ST">
                    <xsl:value-of select="trim:ST"/>
                  </value>
                </xsl:when>
              </xsl:choose>
            </observation>
          </entryRelationship>
        </xsl:for-each>
      </act>
    </entry>
  </xsl:template>
  <!--past medical history-->
  <xsl:template name="pmh_entry">
    <xsl:param name="trim_entry"/>
    <xsl:param name="entryTitle"/>
    <xsl:param name="effTime"/>
    <entry typeCode="DRIV">
      <!-- Problem act templates -->
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm" select="$entryTitle"/>
          </xsl:call-template>
        </code>
        <statusCode code="active"/>
        <effectiveTime>
          <low>
            <xsl:attribute name="value">
              <xsl:value-of select="$effTime"/>
            </xsl:attribute>
          </low>
        </effectiveTime>
        <xsl:for-each select="$trim_entry/trim:value">
          <entryRelationship typeCode="SUBJ" inversionInd="false">
            <observation classCode="OBS" moodCode="EVN">
              <!-- Problem observation templates -->
              <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
              <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
              <id nullFlavor="NI"/>
              <code>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="noXSI" select="'true'"/>
                  <xsl:with-param name="trim_dispNm" select="trim:label"/>
                </xsl:call-template>
              </code>
              <text>
                <reference>
                  <xsl:attribute name="value">
                    <xsl:value-of select="generate-id(current())"/>
                  </xsl:attribute>
                </reference>
              </text>
              <statusCode code="completed"/>
              <effectiveTime>
                <low nullFlavor="UNK"/>
              </effectiveTime>
              <xsl:choose>
                <xsl:when test="string-length(trim:SETCE/trim:displayName)>0">
                  <value>
                    <xsl:call-template name="translateCode">
                      <xsl:with-param name="trim_dispNm" select="trim:SETCE/trim:displayName"/>
                    </xsl:call-template>
                  </value>
                </xsl:when>
                <xsl:when test="string-length(trim:ST)>0">
                  <value xsi:type="ST">
                    <xsl:value-of select="trim:ST"/>
                  </value>
                </xsl:when>
                <xsl:otherwise>
                  <value xsi:type="CD" nullFlavor="NI"/>
                </xsl:otherwise>
              </xsl:choose>
            </observation>
          </entryRelationship>
        </xsl:for-each>
      </act>
    </entry>
  </xsl:template>
  <xsl:template name="initialSymptoms_entry">
    <xsl:param name="trim_entry"/>
    <entry typeCode="DRIV">
      <!-- Problem act templates -->
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code nullFlavor="NA"/>
        <statusCode code="active"/>
        <effectiveTime>
          <low>
            <xsl:attribute name="value">
              <xsl:value-of select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
            </xsl:attribute>
          </low>
        </effectiveTime>
        <xsl:for-each select="$trim_entry/trim:value/trim:SETCE">
          <entryRelationship typeCode="SUBJ" inversionInd="false">
            <observation classCode="OBS" moodCode="EVN">
              <!-- Problem observation templates -->
              <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
              <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
              <id nullFlavor="NI"/>
              <code displayName="Finding reported by subject or history provider" code="418799008" codeSystemName="SNOMED-CT"
                codeSystem="2.16.840.1.113883.6.96">
                <originalText>Initial symptom at presentation</originalText>
              </code>
              <text>
                <reference>
                  <xsl:attribute name="value">
                    <xsl:value-of select="generate-id(current())"/>
                  </xsl:attribute>
                </reference>
              </text>
              <statusCode code="completed"/>
              <effectiveTime>
                <low>
                  <xsl:attribute name="value">
                    <xsl:value-of select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
                  </xsl:attribute>
                </low>
              </effectiveTime>
              <xsl:call-template name="translateValueSet">
                <xsl:with-param name="trim_valueSet" select="parent::*/trim:valueSet/text()"/>
                <xsl:with-param name="trim_dispNm" select="current()/trim:displayName/text()"/>
              </xsl:call-template>
            </observation>
          </entryRelationship>
        </xsl:for-each>
      </act>
    </entry>
  </xsl:template>
  <xsl:template name="menopausalStatus_observation">
    <xsl:param name="trim_entry"/>
    <xsl:if
      test="string-length($trim_entry/trim:value[1]/trim:CE/trim:displayName)>0 or 
			 string-length($trim_entry/trim:value[2]/trim:ST)>0 or
			 string-length($trim_entry/trim:value[3]/trim:CE/trim:displayName)>0 or
			 string-length($trim_entry/trim:value[4]/trim:TS/trim:value)>0">
      <entry typeCode="DRIV">
        <observation classCode="OBS" moodCode="EVN">
          <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
          <id nullFlavor="NI"/>
          <code code="161712005" displayName="Menopausal state" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
            <originalText>
              <xsl:value-of select="'Menopausal status'"/>
            </originalText>
          </code>
          <text>
            <reference>
              <xsl:attribute name="value">
                <xsl:value-of select="generate-id($trim_entry)"/>
              </xsl:attribute>
            </reference>
          </text>
          <statusCode code="completed"/>
          <effectiveTime>
            <low nullFlavor="UNK"/>
          </effectiveTime>
          <xsl:choose>
            <xsl:when test="string-length($trim_entry/trim:value[1]/trim:CE/trim:displayName)>0 ">
              <value xsi:type="CD" nullFlavor="OTH">
                <originalText>
                  <xsl:value-of select="$trim_entry/trim:value[1]/trim:CE/trim:displayName"/>
                </originalText>
              </value>
            </xsl:when>
            <xsl:otherwise>
              <value xsi:type="CD" nullFlavor="UNK">
                <originalText>Unknown</originalText>
              </value>
            </xsl:otherwise>
          </xsl:choose>
          <xsl:for-each select="$trim_entry/trim:value[position() != 1]">
            <xsl:variable name="val">
              <xsl:choose>
                <xsl:when test="string-length(trim:ST)>0">
                  <xsl:value-of select="trim:ST"/>
                </xsl:when>
                <xsl:when test="string-length(trim:CE/trim:displayName)>0">
                  <xsl:value-of select="trim:CE/trim:displayName"/>
                </xsl:when>
                <xsl:when test="string-length(trim:TS/trim:value)>0">
                  <xsl:value-of select="trim:TS/trim:value"/>
                </xsl:when>
              </xsl:choose>
            </xsl:variable>
            <xsl:if test="string-length($val)>0">
              <entryRelationship typeCode="COMP">
                <observation classCode="OBS" moodCode="EVN">
                  <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
                  <id nullFlavor="NI"/>
                  <code nullFlavor="OTH">
                    <originalText>
                      <xsl:value-of select="trim:label"/>
                    </originalText>
                  </code>
                  <statusCode code="completed"/>
                  <effectiveTime nullFlavor="UNK"/>
                  <xsl:choose>
                    <xsl:when test="number($val) = $val">
                      <value xsi:type="TS">
                        <xsl:attribute name="value">
                          <xsl:value-of select="$val"/>
                        </xsl:attribute>
                      </value>
                    </xsl:when>
                    <xsl:otherwise>
                      <value>
                        <xsl:call-template name="translateCode">
                          <xsl:with-param name="trim_dispNm" select="$val"/>
                        </xsl:call-template>
                      </value>
                    </xsl:otherwise>
                  </xsl:choose>
                </observation>
              </entryRelationship>
            </xsl:if>
          </xsl:for-each>
        </observation>
      </entry>
    </xsl:if>
  </xsl:template>
  <xsl:template name="PBH_entry">
    <xsl:param name="theAct"/>
    <entry typeCode="DRIV">
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code nullFlavor="NA">
          <originalText>Breast cancer history</originalText>
        </code>
        <statusCode code="active"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <xsl:for-each select="$theAct/trim:relationship">
          <xsl:choose>
            <xsl:when test="@name='historyDone'">
              <entryRelationship typeCode="SUBJ" inversionInd="false">
                <observation classCode="OBS" moodCode="EVN" negationInd="false">
                  <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
                  <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
                  <id nullFlavor="NI"/>
                  <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
                  <text>
                    <reference>
                      <xsl:attribute name="value">
                        <xsl:value-of select="generate-id(current())"/>
                      </xsl:attribute>
                    </reference>
                  </text>
                  <statusCode code="completed"/>
                  <effectiveTime>
                    <low nullFlavor="UNK"/>
                  </effectiveTime>
                  <value>
                    <xsl:call-template name="translateCode">
                      <xsl:with-param name="trim_dispNm" select="current()/trim:act/trim:observation/trim:value/trim:label"/>
                    </xsl:call-template>
                  </value>
                </observation>
              </entryRelationship>
            </xsl:when>
            <xsl:when test="@name='breastCancerDiagnoses'">
              <!-- the TRIM unit displayName is same as ucum code -->
              <xsl:variable name="ucum">
                <xsl:value-of
                  select="current()/trim:act/trim:relationship/trim:act/trim:observation/trim:value[trim:label='Unit']/trim:CE/trim:displayName"
                />
              </xsl:variable>
              <entryRelationship typeCode="SUBJ" inversionInd="false">
                <observation classCode="OBS" moodCode="EVN">
                  <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
                  <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
                  <id nullFlavor="NI"/>
                  <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
                  <text>
                    <reference>
                      <xsl:attribute name="value">
                        <xsl:value-of select="generate-id(current())"/>
                      </xsl:attribute>
                    </reference>
                  </text>
                  <statusCode code="completed"/>
                  <xsl:variable name="bcDate">
                    <xsl:value-of
                      select="current()/trim:act/trim:relationship/trim:act/trim:observation/trim:value[trim:label='Date']/trim:TS/trim:value"
                    />
                  </xsl:variable>
                  <effectiveTime>
                    <low>
                      <xsl:choose>
                        <xsl:when test="string-length($bcDate)>0">
                          <xsl:attribute name="value">
                            <xsl:value-of select="$bcDate"/>
                          </xsl:attribute>
                        </xsl:when>
                        <xsl:otherwise>
                          <xsl:attribute name="nullFlavor">
                            <xsl:value-of select="'UNK'"/>
                          </xsl:attribute>
                        </xsl:otherwise>
                      </xsl:choose>
                    </low>
                  </effectiveTime>
                  <xsl:variable name="bcType">
                    <xsl:value-of
                      select="current()/trim:act/trim:relationship/trim:act/trim:observation/trim:value[trim:label='Type']/trim:CE/trim:displayName"
                    />
                  </xsl:variable>
                  <xsl:variable name="bcLat">
                    <xsl:value-of
                      select="current()/trim:act/trim:relationship/trim:act/trim:observation/trim:value[trim:label='Lat.']/trim:CE/trim:displayName"
                    />
                  </xsl:variable>
                  <!-- cancer type and laterality -->
                  <value>
                    <xsl:call-template name="translateCode">
                      <xsl:with-param name="trim_dispNm" select="$bcType"/>
                    </xsl:call-template>
                    <xsl:if test="string-length($bcLat)>0">
                      <qualifier>
                        <name>
                          <xsl:call-template name="translateCode">
                            <xsl:with-param name="trim_dispNm">
                              <xsl:value-of select="$bcLblPrfx"/>
                              <xsl:value-of select="'Lat.'"/>
                            </xsl:with-param>
                            <xsl:with-param name="noXSI" select="'true'"/>
                          </xsl:call-template>
                        </name>
                        <value>
                          <xsl:call-template name="translateCode">
                            <xsl:with-param name="noXSI" select="'true'"/>
                            <xsl:with-param name="trim_dispNm" select="$bcLat"/>
                          </xsl:call-template>
                        </value>
                      </qualifier>
                    </xsl:if>
                  </value>
                  <xsl:for-each
                    select="trim:act/trim:relationship/trim:act/trim:observation/trim:value[trim:label!='Date' and trim:label!='Lat.' and trim:label!='Type' and trim:label!='Unit' ]">
                    <xsl:choose>
                      <xsl:when test="trim:label='Stage' or trim:label='Inflamm'">
                        <xsl:call-template name="PBH_ProblemObs">
                          <xsl:with-param name="trimVal" select="current()"/>
                        </xsl:call-template>
                      </xsl:when>
                      <xsl:when
                        test="trim:label='Episodes' or trim:label='ER.' or trim:label='PR.' 
                        or trim:label='HER2.' or trim:label='Pos. Nodes.' or trim:label='Hx Mets.' 
                        or trim:label='Curr. Mets.' or trim:label='Grade'">
                        <xsl:call-template name="PBH_ResultObs">
                          <xsl:with-param name="trimVal" select="current()"/>
                        </xsl:call-template>
                      </xsl:when>
                      <xsl:when test="trim:label='Tumor Size1.' or trim:label='Tumor Size2.'">
                        <xsl:call-template name="PBH_ResultObs">
                          <xsl:with-param name="trimVal" select="current()"/>
                          <xsl:with-param name="ucum" select="$ucum"/>
                        </xsl:call-template>
                      </xsl:when>
                    </xsl:choose>
                  </xsl:for-each>
                </observation>
              </entryRelationship>
            </xsl:when>
            <xsl:when test="@name='treatmentHistory'"> </xsl:when>
          </xsl:choose>
        </xsl:for-each>
      </act>
    </entry>
  </xsl:template>
  <xsl:template name="RSP_ProblemObs">
    <xsl:param name="trimVal"/>
    <entry>
      <observation classCode="OBS" moodCode="EVN">
        <!-- Problem Observation template -->
        <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm">
              <xsl:value-of select="$trimVal/trim:label"/>
            </xsl:with-param>
          </xsl:call-template>
        </code>
        <text>
          <reference>
            <xsl:attribute name="value">
              <xsl:value-of select="generate-id($trimVal)"/>
            </xsl:attribute>
          </reference>
        </text>
        <statusCode code="completed"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <xsl:choose>
          <xsl:when test="string-length($trimVal/trim:CE/trim:displayName)>0">
            <value>
              <xsl:call-template name="translateCode">
                <xsl:with-param name="trim_dispNm" select="$trimVal/trim:CE/trim:displayName"/>
              </xsl:call-template>
            </value>
          </xsl:when>
          <xsl:otherwise>
            <value xsi:type="CD" nullFlavor="NI"/>
          </xsl:otherwise>
        </xsl:choose>
      </observation>
    </entry>
  </xsl:template>
  <xsl:template name="PBH_ProblemObs">
    <xsl:param name="trimVal"/>
    <entryRelationship typeCode="REFR">
      <observation classCode="OBS" moodCode="EVN">
        <!-- Problem Observation template -->
        <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm">
              <xsl:value-of select="$bcLblPrfx"/>
              <xsl:value-of select="$trimVal/trim:label"/>
            </xsl:with-param>
          </xsl:call-template>
        </code>
        <text>
          <reference>
            <xsl:attribute name="value">
              <xsl:value-of select="generate-id($trimVal)"/>
            </xsl:attribute>
          </reference>
        </text>
        <statusCode code="completed"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <xsl:choose>
          <xsl:when test="string-length($trimVal/trim:ST)>0">
            <value>
              <xsl:call-template name="translateCode">
                <xsl:with-param name="trim_dispNm" select="$trimVal/trim:ST"/>
              </xsl:call-template>
            </value>
          </xsl:when>
          <xsl:otherwise>
            <value xsi:type="CD" nullFlavor="NI"/>
          </xsl:otherwise>
        </xsl:choose>
      </observation>
    </entryRelationship>
  </xsl:template>
  <xsl:template name="PBH_ResultObs">
    <xsl:param name="trimVal"/>
    <xsl:param name="ucum"/>
    <entryRelationship typeCode="REFR">
      <observation classCode="OBS" moodCode="EVN">
        <!-- Result Observation template -->
        <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
        <templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm">
              <xsl:value-of select="$bcLblPrfx"/>
              <xsl:value-of select="$trimVal/trim:label"/>
            </xsl:with-param>
          </xsl:call-template>
        </code>
        <text>
          <reference>
            <xsl:attribute name="value">
              <xsl:value-of select="generate-id($trimVal)"/>
            </xsl:attribute>
          </reference>
        </text>
        <statusCode code="completed"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <xsl:choose>
          <xsl:when test="starts-with($trimVal/trim:label,'Tumor Size')">
            <value xsi:type="PQ">
              <xsl:choose>
                <xsl:when test="string-length($trimVal/trim:ST)>0">
                  <xsl:attribute name="value">
                    <xsl:value-of select="$trimVal/trim:ST"/>
                  </xsl:attribute>
                  <xsl:attribute name="unit">
                    <xsl:value-of select="$ucum"/>
                  </xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:attribute name="nullFlavor">
                    <xsl:value-of select="'NI'"/>
                  </xsl:attribute>
                </xsl:otherwise>
              </xsl:choose>
            </value>
          </xsl:when>
          <xsl:otherwise>
            <value xsi:type="ST">
              <xsl:choose>
                <xsl:when test="string-length($trimVal/trim:ST)>0">
                  <xsl:value-of select="$trimVal/trim:ST"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:attribute name="nullFlavor">
                    <xsl:value-of select="'NI'"/>
                  </xsl:attribute>
                </xsl:otherwise>
              </xsl:choose>
            </value>
          </xsl:otherwise>
        </xsl:choose>
      </observation>
    </entryRelationship>
  </xsl:template>

  <xsl:template name="survSts_ProblemEntry">
    <xsl:param name="trim_act"/>
    <xsl:param name="trim_prd"/>
    <xsl:variable name="effTm" select="$trim_act/trim:observation/trim:value[trim:label='date: ']/trim:TS/trim:value"/>
    <xsl:variable name="actCode" select="$trim_act/trim:title/trim:ST"/>
    <xsl:variable name="surviveVal" select="$trim_act/trim:observation/trim:value[trim:label='Survival Status']/trim:CE/trim:displayName"/>
    <entry typeCode="DRIV">
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code nullFlavor="NA"/>
        <statusCode code="active"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <entryRelationship typeCode="SUBJ" inversionInd="false">
          <observation classCode="OBS" moodCode="EVN">
            <!-- Problem Observation template -->
            <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
            <xsl:choose>
              <xsl:when test="string-length($trim_act/trim:id/trim:II/trim:root)>0">
                <xsl:call-template name="make_II">
                  <xsl:with-param name="II_root" select="$trim_act/trim:id/trim:II/trim:root"/>
                  <xsl:with-param name="II_ext" select="$trim_act/trim:id/trim:II/trim:extension"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:otherwise>
                <id nullFlavor="NI"/>
              </xsl:otherwise>
            </xsl:choose>
            <code>
              <xsl:call-template name="translateCode">
                <xsl:with-param name="noXSI" select="'true'"/>
                <xsl:with-param name="trim_dispNm" select="$actCode"/>
              </xsl:call-template>
            </code>
            <statusCode code="active"/>
            <xsl:choose>
              <xsl:when test="string-length($effTm)>0">
                <effectiveTime>
                   <xsl:attribute name="value">
                     <xsl:value-of select="$effTm"/>
                   </xsl:attribute>
                </effectiveTime>
              </xsl:when>
              <xsl:otherwise>
                <effectiveTime nullFlavor="UNK"/>    
              </xsl:otherwise>
            </xsl:choose>
            <value>
               <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="$surviveVal"/>
                </xsl:call-template>
            </value>
            <xsl:if test="$trim_act/trim:observation/trim:value[starts-with(trim:label,'Has the patient had a documented')]">
              <entryRelationship typeCode="SUBJ" inversionInd="false">
                <observation classCode="OBS" moodCode="EVN">
                  <id nullFlavor="NI"/>
                  <code nullFlavor="OTH">
                     <originalText>
                       <xsl:text>Has the patient had a documented clinical assessment for this cancer since submission of the previous follow-up form?</xsl:text>
                     </originalText>
                  </code>
                  <statusCode code="completed"/>
                  <effectiveTime nullFlavor="UNK"/>
                  <value>
                    <xsl:call-template name="translateCode">
                      <xsl:with-param name="trim_dispNm" select="$trim_act/trim:observation/trim:value[starts-with(trim:label,'Has the patient had a documented')]/trim:CE/trim:displayName"/>
                    </xsl:call-template>
                  </value>
                </observation>
              </entryRelationship>
            </xsl:if>
            <xsl:if test="$surviveVal='Dead'">
              <xsl:if test="$trim_prd">
                <xsl:if test="$trim_prd/trim:act/trim:effectiveTime/trim:IVL_TS/trim:low">
                  <entryRelationship typeCode="COMP">
                    <observation classCode="OBS" moodCode="EVN">
                      <id nullFlavor="NI"/>
                      <code nullFlavor="OTH">
                        <originalText>
                          <xsl:value-of select="$trim_prd/trim:act/trim:effectiveTime/trim:IVL_TS/trim:low/trim:label"/>
                        </originalText>
                      </code>
                      <statusCode code="completed"/>
                      <effectiveTime nullFlavor="UNK"/>
                      <value xsi:type="TS">
                        <xsl:choose>
                          <xsl:when test="string-length($trim_prd/trim:act/trim:effectiveTime/trim:IVL_TS/trim:low/trim:TS/trim:value)>0">
                            <xsl:attribute name="value">
                              <xsl:value-of select="$trim_prd/trim:act/trim:effectiveTime/trim:IVL_TS/trim:low/trim:TS/trim:value"/>
                            </xsl:attribute>
                          </xsl:when>
                          <xsl:otherwise>
                            <xsl:attribute name="nullFlavor">NI</xsl:attribute>
                          </xsl:otherwise>
                        </xsl:choose>
                      </value>
                    </observation>
                  </entryRelationship>
                </xsl:if>
                <xsl:if test="$trim_prd/trim:act/trim:effectiveTime/trim:IVL_TS/trim:high">
                  <entryRelationship typeCode="COMP">
                    <observation classCode="OBS" moodCode="EVN">
                      <id nullFlavor="NI"/>
                      <code nullFlavor="OTH">
                        <originalText>
                          <xsl:value-of select="$trim_prd/trim:act/trim:effectiveTime/trim:IVL_TS/trim:high/trim:label"/>
                        </originalText>
                      </code>
                      <statusCode code="completed"/>
                      <effectiveTime nullFlavor="UNK"/>
                      <value xsi:type="TS">
                        <xsl:choose>
                          <xsl:when test="string-length($trim_prd/trim:act/trim:effectiveTime/trim:IVL_TS/trim:high/trim:TS/trim:value)>0">
                            <xsl:attribute name="value">
                              <xsl:value-of select="$trim_prd/trim:act/trim:effectiveTime/trim:IVL_TS/trim:high/trim:TS/trim:value"/>
                            </xsl:attribute>
                          </xsl:when>
                          <xsl:otherwise>
                            <xsl:attribute name="nullFlavor">NI</xsl:attribute>
                          </xsl:otherwise>
                        </xsl:choose>
                      </value>
                    </observation>
                  </entryRelationship>
                </xsl:if>
              </xsl:if>
            </xsl:if>
            <xsl:for-each select="$trim_act/trim:relationship[@name='deathDetails']/trim:act">
              <xsl:for-each select="trim:observation/trim:value">
                <xsl:variable name="c" select="trim:label"/>
                <xsl:variable name="v">
                  <xsl:value-of select="trim:CE/trim:displayName"/>
                  <xsl:value-of select="trim:SETCE/trim:displayName"/>
                  <xsl:value-of select="trim:ED/trim:value"/>
                </xsl:variable>
                <xsl:variable name="vt">
                  <xsl:choose>
                    <xsl:when test="trim:CE or trim:SETCE">
                      <xsl:value-of select="'CD'"/>
                    </xsl:when>
                    <xsl:when test="trim:ST or trim:ED">
                      <xsl:value-of select="'ST'"/>
                    </xsl:when>
                    <xsl:when test="trim:INT">
                      <xsl:value-of select="'INT'"/>
                    </xsl:when>
                    <xsl:when test="trim:PQ">
                      <xsl:value-of select="'PQ'"/>
                    </xsl:when>
                  </xsl:choose>
                </xsl:variable>
                <xsl:if test="string-length($c)>0 and string-length($v)>0">
                  <entryRelationship typeCode="COMP" inversionInd="false">
                    <xsl:call-template name="simpleObs">
                      <xsl:with-param name="theCode" select="$c"/>
                      <xsl:with-param name="theVal" select="$v"/>
                      <xsl:with-param name="theValType" select="$vt"/>
                    </xsl:call-template>
                  </entryRelationship>
                </xsl:if>
              </xsl:for-each>
            </xsl:for-each>
         </observation>
        </entryRelationship>
      </act>
    </entry>
  </xsl:template>

  <xsl:template name="postSurg_ProblemEntry">
    <xsl:param name="trim_act"/>
    <xsl:param name="suffix"/>
    <xsl:param name="surgeon"/>
    <xsl:param name="trim_sbrgrd"/>

    <xsl:variable name="effTm" select="$trim_act/trim:TS/trim:value"/>
    <xsl:variable name="actCode">
      <xsl:value-of select="$trim_act/trim:title/trim:ST"/>
      <xsl:text> </xsl:text>
      <xsl:value-of select="$suffix"/>
    </xsl:variable>
    <entry typeCode="DRIV">
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code nullFlavor="NA">
          <originalText>Staging</originalText>
        </code>
        <statusCode code="active"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <xsl:if test="$surgeon">
          <xsl:call-template name="mapPerformer">
            <xsl:with-param name="trim_participation" select="$surgeon"/>
          </xsl:call-template>
        </xsl:if>
        <entryRelationship typeCode="SUBJ" inversionInd="false">
          <observation classCode="OBS" moodCode="EVN">
            <!-- Problem Observation template -->
            <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
            <xsl:choose>
              <xsl:when test="string-length($trim_act/trim:id/trim:II/trim:root)>0">
                <xsl:call-template name="make_II">
                  <xsl:with-param name="II_root" select="$trim_act/trim:id/trim:II/trim:root"/>
                  <xsl:with-param name="II_ext" select="$trim_act/trim:id/trim:II/trim:extension"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:otherwise>
                <id nullFlavor="NI"/>
              </xsl:otherwise>
            </xsl:choose>
            <code>
              <xsl:call-template name="translateCode">
                <xsl:with-param name="noXSI" select="'true'"/>
                <xsl:with-param name="trim_dispNm" select="$actCode"/>
              </xsl:call-template>
            </code>
            <statusCode code="active"/>
            <effectiveTime>
              <low>
                <xsl:choose>
                  <xsl:when test="string-length($effTm)>0">
                    <xsl:attribute name="value">
                      <xsl:value-of select="$effTm"/>
                    </xsl:attribute>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:attribute name="nullFlavor">UNK</xsl:attribute>
                  </xsl:otherwise>
                </xsl:choose>
              </low>
            </effectiveTime>
            <xsl:if test="$surgeon">
              <xsl:call-template name="mapPerformer">
                <xsl:with-param name="trim_participation" select="$surgeon"/>
              </xsl:call-template>
            </xsl:if>
            <xsl:for-each select="$trim_act/trim:observation">
              <entryRelationship typeCode="SUBJ" inversionInd="false">
                <xsl:variable name="obscd">
                  <xsl:value-of select="$trim_act/trim:code/trim:CD/trim:displayName"/>
                  <xsl:value-of select="$trim_act/trim:text/trim:ST"/>
                </xsl:variable>
                <xsl:call-template name="path_problemObs">
                  <xsl:with-param name="obs" select="."/>
                  <xsl:with-param name="obsCode" select="$obscd"/>
                </xsl:call-template>
              </entryRelationship>
            </xsl:for-each>

            <xsl:if test="$trim_sbrgrd">
              <xsl:for-each select="$trim_sbrgrd/trim:act">
                <entryRelationship typeCode="SUBJ" inversionInd="false">
                  <xsl:variable name="obscd">
                    <xsl:value-of select="trim:title/trim:ST"/>
                  </xsl:variable>
                  <xsl:call-template name="path_problemObs">
                    <xsl:with-param name="obs" select="./trim:observation"/>
                    <xsl:with-param name="obsCode" select="$obscd"/>
                  </xsl:call-template>
                </entryRelationship>
              </xsl:for-each>
            </xsl:if>

            <xsl:for-each select="$trim_act/trim:relationship">
              <xsl:choose>
                <xsl:when test="@enabled='true' or not(@enabled)">
                  <xsl:variable name="c">
                    <xsl:choose>
                      <xsl:when test="string-length(trim:act/trim:observation/trim:value/trim:label)>0">
                        <xsl:value-of select="trim:act/trim:observation/trim:value/trim:label"/>
                      </xsl:when>
                      <xsl:when test="string-length(trim:act/trim:title/trim:ST)>0">
                        <xsl:value-of select="trim:act/trim:title/trim:ST"/>
                      </xsl:when>
                      <xsl:when test="string-length(trim:act/trim:observation/trim:value/trim:valueSet)">0"> <xsl:value-of
                          select="trim:act/trim:observation/trim:value/trim:valueSet"/>
                      </xsl:when>
                    </xsl:choose>
                  </xsl:variable>
                  <xsl:variable name="v">
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:SETCE/trim:displayName"/>
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:ST"/>
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:originalText"/>
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:INT/trim:value"/>
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:PQ/trim:value"/>
                    <xsl:if test="string-length(trim:act/trim:observation/trim:value/trim:PQ/trim:value)>0">
                      <xsl:text>^</xsl:text>
                      <xsl:value-of select="trim:act/trim:observation/trim:value/trim:PQ/trim:unit"/>
                    </xsl:if>
                  </xsl:variable>
                  <xsl:variable name="vt">
                    <xsl:choose>
                      <xsl:when test="trim:act/trim:observation/trim:value/trim:CE or trim:act/trim:observation/trim:value/trim:SETCE">
                        <xsl:value-of select="'CD'"/>
                      </xsl:when>
                      <xsl:when
                        test="trim:act/trim:observation/trim:value/trim:ST or trim:act/trim:observation/trim:value/trim:originalText">
                        <xsl:value-of select="'ST'"/>
                      </xsl:when>
                      <xsl:when test="trim:act/trim:observation/trim:value/trim:INT">
                        <xsl:value-of select="'INT'"/>
                      </xsl:when>
                      <xsl:when test="trim:act/trim:observation/trim:value/trim:PQ">
                        <xsl:value-of select="'PQ'"/>
                      </xsl:when>
                    </xsl:choose>
                  </xsl:variable>

                  <xsl:if test="string-length($c)>0 and string-length($v)>0">
                    <entryRelationship typeCode="SUBJ" inversionInd="false">
                      <xsl:call-template name="simpleObs">
                        <xsl:with-param name="theCode" select="$c"/>
                        <xsl:with-param name="theVal" select="$v"/>
                        <xsl:with-param name="theValType" select="$vt"/>
                      </xsl:call-template>
                    </entryRelationship>
                  </xsl:if>
                </xsl:when>
              </xsl:choose>
            </xsl:for-each>
          </observation>
        </entryRelationship>
      </act>
    </entry>
  </xsl:template>

  <xsl:template name="Les_ProblemEntry">
    <xsl:param name="trim_act"/>
    <xsl:param name="prefix"/>
    <xsl:variable name="effTm" select="$trim_act/trim:TS/trim:value"/>
    <entry typeCode="DRIV">
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code nullFlavor="NA"/>
        <statusCode code="active"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <entryRelationship typeCode="SUBJ" inversionInd="false">

          <observation classCode="OBS" moodCode="EVN">
            <!-- Problem Observation template -->
            <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
            <xsl:choose>
              <xsl:when test="string-length($trim_act/trim:id/trim:II/trim:root)>0">
                <xsl:call-template name="make_II">
                  <xsl:with-param name="II_root" select="$trim_act/trim:id/trim:II/trim:root"/>
                  <xsl:with-param name="II_ext" select="$trim_act/trim:id/trim:II/trim:extension"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:otherwise>
                <id nullFlavor="NI"/>
              </xsl:otherwise>
            </xsl:choose>
            <xsl:variable name="lbl">
              <xsl:if test="string-length($prefix)>0">
                <xsl:value-of select="$prefix"/>
                <xsl:text>: </xsl:text>
              </xsl:if>
              <xsl:value-of select="$trim_act/trim:title/trim:ST"/>
            </xsl:variable>
            <code>
              <xsl:call-template name="translateCode">
                <xsl:with-param name="noXSI" select="'true'"/>
                <xsl:with-param name="trim_dispNm" select="$lbl"/>
              </xsl:call-template>
            </code>
            <statusCode code="active"/>
            <effectiveTime>
              <low>
                <xsl:choose>
                  <xsl:when test="string-length($effTm)>0">
                    <xsl:attribute name="value">
                      <xsl:value-of select="$effTm"/>
                    </xsl:attribute>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:attribute name="nullFlavor">UNK</xsl:attribute>
                  </xsl:otherwise>
                </xsl:choose>
              </low>
            </effectiveTime>
            <xsl:for-each select="$trim_act/trim:observation">
              <entryRelationship typeCode="SUBJ" inversionInd="false">
                <xsl:variable name="obscd">
                  <xsl:value-of select="$trim_act/trim:code/trim:CD/trim:displayName"/>
                  <xsl:value-of select="$trim_act/trim:text/trim:ST"/>
                </xsl:variable>
                <xsl:call-template name="path_problemObs">
                  <xsl:with-param name="obs" select="."/>
                  <xsl:with-param name="obsCode" select="$obscd"/>
                </xsl:call-template>
              </entryRelationship>
            </xsl:for-each>
            <xsl:for-each select="$trim_act/trim:relationship">
              <xsl:choose>
                <xsl:when test="@enabled='true' or not(@enabled)">
                  <xsl:variable name="c">
                    <xsl:choose>
                      <xsl:when test="string-length(trim:act/trim:observation/trim:value/trim:label)>0">
                        <xsl:value-of select="trim:act/trim:observation/trim:value/trim:label"/>
                      </xsl:when>
                      <xsl:when test="string-length(trim:act/trim:title/trim:ST)>0">
                        <xsl:value-of select="trim:act/trim:title/trim:ST"/>
                      </xsl:when>
                    </xsl:choose>
                  </xsl:variable>
                  <xsl:variable name="v">
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:ST"/>
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:INT/trim:value"/>
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:PQ/trim:value"/>
                    <xsl:if test="string-length(trim:act/trim:observation/trim:value/trim:PQ/trim:value)>0">
                      <xsl:text>^</xsl:text>
                      <xsl:value-of select="trim:act/trim:observation/trim:value/trim:PQ/trim:unit"/>
                    </xsl:if>
                  </xsl:variable>
                  <xsl:variable name="vt">
                    <xsl:choose>
                      <xsl:when test="trim:act/trim:observation/trim:value/trim:CE or trim:act/trim:observation/trim:value/trim:SETCE">
                        <xsl:value-of select="'CD'"/>
                      </xsl:when>
                      <xsl:when
                        test="trim:act/trim:observation/trim:value/trim:ST or trim:act/trim:observation/trim:value/trim:originalText">
                        <xsl:value-of select="'ST'"/>
                      </xsl:when>
                      <xsl:when test="trim:act/trim:observation/trim:value/trim:INT">
                        <xsl:value-of select="'INT'"/>
                      </xsl:when>
                      <xsl:when test="trim:act/trim:observation/trim:value/trim:PQ">
                        <xsl:value-of select="'PQ'"/>
                      </xsl:when>
                    </xsl:choose>
                  </xsl:variable>

                  <xsl:if test="string-length($c)>0 and string-length($v)>0">
                    <entryRelationship typeCode="SUBJ" inversionInd="false">
                      <xsl:call-template name="simpleObs">
                        <xsl:with-param name="theCode" select="$c"/>
                        <xsl:with-param name="theVal" select="$v"/>
                        <xsl:with-param name="theValType" select="$vt"/>
                      </xsl:call-template>
                    </entryRelationship>
                  </xsl:if>
                </xsl:when>
              </xsl:choose>
            </xsl:for-each>
          </observation>
        </entryRelationship>
      </act>
    </entry>
  </xsl:template>

  <xsl:template name="Les_ProblemEntry2">
    <xsl:param name="trim_act"/>
    <xsl:param name="obsType"/>
    <entry typeCode="DRIV">
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code nullFlavor="NA"/>
        <statusCode code="active"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <entryRelationship typeCode="SUBJ" inversionInd="false">
          <observation classCode="OBS" moodCode="EVN">
            <!-- Problem Observation template -->
            <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
            <id nullFlavor="NI"/>
            <code>
              <xsl:call-template name="translateCode">
                <xsl:with-param name="noXSI" select="'true'"/>
                <xsl:with-param name="trim_dispNm" select="$obsType"/>
              </xsl:call-template>
            </code>
            <statusCode code="active"/>
            <effectiveTime>
              <low nullFlavor="UNK"/>
            </effectiveTime>
            <xsl:for-each select="$trim_act/trim:observation/trim:value">
              <xsl:variable name="c" select="trim:PQ/trim:label"/>
              <xsl:variable name="v">
                <xsl:value-of select="trim:PQ/trim:value"/>
                <xsl:if test="string-length(trim:PQ/trim:value)>0">
                  <xsl:text>^</xsl:text>
                  <xsl:value-of select="trim:PQ/trim:unit"/>
                </xsl:if>
              </xsl:variable>
              <xsl:if test="string-length($c)>0 and string-length($v)>0">
                <entryRelationship typeCode="SUBJ" inversionInd="false">
                  <xsl:call-template name="simpleObs">
                    <xsl:with-param name="theCode" select="$c"/>
                    <xsl:with-param name="theVal" select="$v"/>
                    <xsl:with-param name="theValType" select="'PQ'"/>
                  </xsl:call-template>
                </entryRelationship>
              </xsl:if>
            </xsl:for-each>
          </observation>
        </entryRelationship>
      </act>
    </entry>
  </xsl:template>

  <xsl:template name="Path_ProblemEntry">
    <xsl:param name="trim_act"/>
    <xsl:param name="prefix"/>
    <entry typeCode="DRIV">
      <!-- Problem act templates -->
      <act classCode="ACT" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.7" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.27" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.1" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5.2" assigningAuthorityName="IHE PCC"/>
        <xsl:choose>
          <xsl:when test="string-length($trim_act/trim:id/trim:II/trim:root)>0">
            <xsl:call-template name="make_II">
              <xsl:with-param name="II_root" select="$trim_act/trim:id/trim:II/trim:root"/>
              <xsl:with-param name="II_ext" select="$trim_act/trim:id/trim:II/trim:extension"/>
            </xsl:call-template>
          </xsl:when>
          <xsl:otherwise>
            <id nullFlavor="NI"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:variable name="lbl">
          <xsl:if test="string-length($prefix)>0">
            <xsl:value-of select="$prefix"/>
            <xsl:text>: </xsl:text>
          </xsl:if>
          <xsl:value-of select="$trim_act/trim:title/trim:ST"/>
        </xsl:variable>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm" select="$lbl"/>
          </xsl:call-template>
        </code>
        <statusCode code="active"/>
        <effectiveTime>
          <low nullFlavor="UNK"/>
        </effectiveTime>
        <xsl:for-each select="$trim_act/trim:observation">
          <entryRelationship typeCode="SUBJ" inversionInd="false">
            <xsl:variable name="obscd">
              <xsl:value-of select="$trim_act/trim:code/trim:CD/trim:displayName"/>
              <xsl:value-of select="$trim_act/trim:text/trim:ST"/>
            </xsl:variable>
            <xsl:call-template name="path_problemObs">
              <xsl:with-param name="obs" select="."/>
              <xsl:with-param name="obsCode" select="$obscd"/>
            </xsl:call-template>
          </entryRelationship>
        </xsl:for-each>
        <xsl:for-each select="$trim_act/trim:relationship">
          <xsl:choose>
            <xsl:when test="(@name='laterality') and (@enabled='true' or not(@enabled))">
              <entryRelationship typeCode="SUBJ" inversionInd="false">
                <xsl:call-template name="simpleObs">
                  <xsl:with-param name="theCode" select="'laterality'"/>
                  <xsl:with-param name="theVal" select="trim:act/trim:title/trim:ST"/>
                  <xsl:with-param name="theValType" select="'ST'"/>
                </xsl:call-template>
              </entryRelationship>
            </xsl:when>
            <xsl:when test="(@name='type') and (@enabled='true' or not(@enabled))">
              <entryRelationship typeCode="SUBJ" inversionInd="false">
                <xsl:call-template name="simpleObs">
                  <xsl:with-param name="theCode" select="'type'"/>
                  <xsl:with-param name="theVal" select="trim:act//trim:originalText"/>
                  <xsl:with-param name="theValType" select="'ST'"/>
                </xsl:call-template>
              </entryRelationship>
            </xsl:when>
            <xsl:when test="not(@name='type') and not(@name='laterality') and (@enabled='true' or not(@enabled))">
              <xsl:variable name="c">
                <xsl:choose>
                  <xsl:when test="string-length(trim:act/trim:observation/trim:value/trim:label)>0">
                    <xsl:value-of select="trim:act/trim:observation/trim:value/trim:label"/>
                  </xsl:when>
                  <xsl:when test="string-length(trim:act/trim:title/trim:ST)>0">
                    <xsl:value-of select="trim:act/trim:title/trim:ST"/>
                  </xsl:when>
                </xsl:choose>
              </xsl:variable>
              <xsl:variable name="v">
                <xsl:value-of select="trim:act/trim:observation/trim:value//trim:displayName"/>
                <xsl:value-of select="trim:act/trim:observation/trim:value//trim:originalText"/>
                <xsl:value-of select="trim:act/trim:observation/trim:value//trim:value"/>
              </xsl:variable>
              <xsl:variable name="vt">
                <xsl:choose>
                  <xsl:when test="trim:act/trim:observation/trim:value/trim:CE or trim:act/trim:observation/trim:value/trim:SETCE">
                    <xsl:value-of select="'CD'"/>
                  </xsl:when>
                  <xsl:when test="trim:act/trim:observation/trim:value/trim:ST or trim:act/trim:observation/trim:value/trim:originalText">
                    <xsl:value-of select="'ST'"/>
                  </xsl:when>
                  <xsl:when test="trim:act/trim:observation/trim:value/trim:INT">
                    <xsl:value-of select="'INT'"/>
                  </xsl:when>
                  <xsl:when test="trim:act/trim:observation/trim:value/trim:PQ">
                    <xsl:value-of select="'PQ'"/>
                  </xsl:when>
                </xsl:choose>
              </xsl:variable>

              <xsl:if test="string-length($c)>0 and string-length($v)>0">
                <entryRelationship typeCode="SUBJ" inversionInd="false">
                  <xsl:call-template name="simpleObs">
                    <xsl:with-param name="theCode" select="$c"/>
                    <xsl:with-param name="theVal" select="$v"/>
                    <xsl:with-param name="theValType" select="$vt"/>
                  </xsl:call-template>
                </entryRelationship>
              </xsl:if>
            </xsl:when>
          </xsl:choose>
        </xsl:for-each>
      </act>
    </entry>
  </xsl:template>

  <xsl:template name="path_problemObs">
    <xsl:param name="obs"/>
    <xsl:param name="obsCode"/>
    <xsl:variable name="obsCd">
      <xsl:choose>
        <xsl:when test="string-length($obsCode)>0">
          <xsl:value-of select="$obsCode"/>
        </xsl:when>
        <xsl:when test="string-length($obs/trim:value/trim:label)">
          <xsl:value-of select="$obs/trim:value/trim:label"/>
        </xsl:when>
      </xsl:choose>
    </xsl:variable>
    <observation classCode="OBS" moodCode="EVN">
      <!-- Problem Observation template -->
      <templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
      <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
      <id nullFlavor="NI"/>
      <code>
        <xsl:call-template name="translateCode">
          <xsl:with-param name="noXSI" select="'true'"/>
          <xsl:with-param name="trim_dispNm">
            <xsl:value-of select="$obsCd"/>
          </xsl:with-param>
        </xsl:call-template>
      </code>
      <text>
        <reference>
          <xsl:attribute name="value">
            <xsl:value-of select="generate-id(current())"/>
          </xsl:attribute>
        </reference>
      </text>
      <statusCode code="completed"/>
      <effectiveTime>
        <low nullFlavor="UNK"/>
      </effectiveTime>
      <xsl:choose>
        <xsl:when test="string-length($obs/trim:value/trim:CE/trim:displayName)>0">
          <value>
            <xsl:call-template name="translateCode">
              <xsl:with-param name="trim_dispNm" select="$obs/trim:value/trim:CE/trim:displayName"/>
            </xsl:call-template>
          </value>
        </xsl:when>
        <xsl:when test="string-length($obs/trim:value/trim:ST)>0">
          <value>
            <xsl:call-template name="translateCode">
              <xsl:with-param name="trim_dispNm" select="$obs/trim:value/trim:ST"/>
            </xsl:call-template>
          </value>
        </xsl:when>
        <xsl:when test="string-length($obs/trim:value/trim:TS/trim:value)>0">
          <value>
            <xsl:call-template name="translateCode">
              <xsl:with-param name="trim_dispNm" select="$obs/trim:value/trim:TS/trim:value"/>
            </xsl:call-template>
          </value>
        </xsl:when>
        <xsl:when test="string-length($obs/trim:value/trim:originalText)>0">
          <value>
            <xsl:call-template name="translateCode">
              <xsl:with-param name="trim_dispNm" select="$obs/trim:value/trim:originalText"/>
            </xsl:call-template>
          </value>
        </xsl:when>
        <xsl:otherwise>
          <value xsi:type="CD" nullFlavor="NI"/>
        </xsl:otherwise>
      </xsl:choose>
    </observation>
  </xsl:template>

  <xsl:template name="simpleObs">
    <xsl:param name="theCode"/>
    <xsl:param name="theVal"/>
    <xsl:param name="theValType"/>
    <observation classCode="OBS" moodCode="EVN">
      <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
      <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
      <templateId root="2.16.840.1.113883.3.88.11.83.15.1" assigningAuthorityName="HITSP C83"/>
      <id nullFlavor="NI"/>
      <code>
        <xsl:call-template name="translateCode">
          <xsl:with-param name="noXSI" select="'true'"/>
          <xsl:with-param name="trim_dispNm">
            <xsl:value-of select="$theCode"/>
          </xsl:with-param>
        </xsl:call-template>
      </code>
      <text>
        <reference>
          <xsl:attribute name="value">
            <xsl:value-of select="generate-id(current())"/>
          </xsl:attribute>
        </reference>
      </text>
      <statusCode code="completed"/>
      <effectiveTime nullFlavor="UNK"/>
      <xsl:choose>
        <xsl:when test="$theValType='PQ'">
          <xsl:choose>
            <xsl:when test="string-length($theVal)>0 and string-length(substring-before($theVal,'^'))>0">
              <value xsi:type="PQ">
                <xsl:attribute name="value">
                  <xsl:value-of select="substring-before($theVal,'^')"/>
                </xsl:attribute>
                <xsl:attribute name="unit">
                  <xsl:value-of select="substring-after($theVal,'^')"/>
                </xsl:attribute>
              </value>
            </xsl:when>
            <xsl:otherwise>
              <value xsi:type="PQ" nullFlavor="NI"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:when test="$theValType='CD' and string-length($theVal)>0">
          <value>
            <xsl:call-template name="translateCode">
              <xsl:with-param name="trim_dispNm" select="$theVal"/>
            </xsl:call-template>
          </value>
        </xsl:when>
        <xsl:when test="$theValType='ST'">
          <value xsi:type="ST">
            <xsl:value-of select="$theVal"/>
          </value>
        </xsl:when>
        <xsl:when test="$theValType='INT' and string-length($theVal)>0">
          <value xsi:type="INT">
            <xsl:attribute name="value">
              <xsl:value-of select="$theVal"/>
            </xsl:attribute>
          </value>
        </xsl:when>
        <xsl:when test="not($theValType) and string-length($theVal)>0">
          <value xsi:type="ST">
            <xsl:value-of select="$theVal"/>
          </value>
        </xsl:when>
        <xsl:otherwise>
          <value xsi:type="CD" nullFlavor="NI"/>
        </xsl:otherwise>
      </xsl:choose>
    </observation>
  </xsl:template>

</xsl:stylesheet>
