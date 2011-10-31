<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3"
  xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
  <xsl:template name="medications_section">
    <xsl:param name="trim_medications"/>
    <xsl:param name="trim_menopausalStatus"/>
    <xsl:param name="trim_hormoneTx"/>
    <xsl:param name="trim_hormoneDuration"/>
    <xsl:param name="trim_hormoneTime"/>
    <xsl:param name="trim_hormoneTherapy"/>
    <xsl:param name="trim_bisphosphonates"/>
    <xsl:param name="trim_chemotrmt"/>
    <xsl:param name="trim_xRegimens"/>

    <xsl:if
      test="$trim_medications or $trim_menopausalStatus or $trim_hormoneTx  or $trim_hormoneTherapy or $trim_bisphosphonates or $trim_chemotrmt or $trim_xRegimens">
      <component>
        <section>
          <!--Medications section templates-->
          <templateId root="2.16.840.1.113883.3.88.11.83.112" assigningAuthorityName="HITSP/C83"/>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.3.19" assigningAuthorityName="IHE PCC"/>
          <templateId root="2.16.840.1.113883.10.20.1.8" assigningAuthorityName="HL7 CCD"/>
          <code code="10160-0" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="History of medication use"/>
          <title>Medications</title>
          <text/>
          <xsl:if test="$trim_medications">
            <xsl:for-each select="$trim_medications/trim:act/trim:relationship[@name='medication']">
              <xsl:call-template name="medications_entry">
                <xsl:with-param name="trim_entry" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_menopausalStatus">
            <xsl:for-each select="$trim_menopausalStatus/trim:act/trim:relationship[@name='estrogenReplacement']">
              <xsl:call-template name="menopausalStatus_estrogenReplacement">
                <xsl:with-param name="trim_entry" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_hormoneTx">
            <xsl:call-template name="medications_hormoneTherapy">
              <xsl:with-param name="trim_hormoneTx" select="$trim_hormoneTx"/>
              <xsl:with-param name="trim_hormoneDuration" select="$trim_hormoneDuration"/>
              <xsl:with-param name="trim_hormoneTime" select="$trim_hormoneTime"/>
            </xsl:call-template>
          </xsl:if>
          <xsl:if test="$trim_hormoneTherapy">
            <xsl:for-each select="$trim_hormoneTherapy/trim:act/trim:relationship[@name='theraphy']">
              <xsl:call-template name="PBH_medTherapy">
                <xsl:with-param name="trimRel" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_bisphosphonates">
            <xsl:for-each select="$trim_bisphosphonates/trim:act/trim:relationship[@name='bisphosphonate']">
              <xsl:call-template name="PBH_medTherapy">
                <xsl:with-param name="trimRel" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_chemotrmt">
            <xsl:for-each select="$trim_chemotrmt/trim:act/trim:relationship[not (@name='growthFactors')]">
              <xsl:if test="@name='cycleNumber'">
                <xsl:call-template name="medObs">
                  <xsl:with-param name="trim_act" select="./trim:act"/>
                </xsl:call-template>
              </xsl:if>
              <xsl:if test="@name='chemoTherapyRegimens'">
                <xsl:call-template name="chemoregi">
                  <xsl:with-param name="trim_act" select="trim:act/trim:relationship[(@enabled='true') or not (@enabled)]/trim:act"/>
                </xsl:call-template>
              </xsl:if>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_xRegimens">
            <xsl:for-each select="$trim_xRegimens/trim:act">
              <xsl:call-template name="regimProc">
                <xsl:with-param name="trimAct" select="current()"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
        </section>
      </component>
    </xsl:if>
  </xsl:template>

  <xsl:template name="regimProc">
    <xsl:param name="trimAct"/>
    <xsl:variable name="effTm_low" select="$trimAct/trim:effectiveTime/trim:IVL_TS/trim:low/trim:TS/trim:value"/>
    <xsl:variable name="effTm_high" select="$trimAct/trim:effectiveTime/trim:IVL_TS/trim:high/trim:TS/trim:value"/>
    <entry>
      <procedure classCode="PROC" moodCode="EVN">
        <templateId root="2.16.840.1.113883.10.20.1.29" assigningAuthorityName="CCD"/>
        <id nullFlavor="NI"/>
        <code code="69960004" codeSystem="2.16.840.1.113883.6.96" displayName="cancer chemotherapy regimen">
          <originalText>Treatment Plan Name: taxaneRegimens</originalText>
        </code>
        <statusCode code="completed"/>
        <effectiveTime xsi:type="IVL_TS">
          <low>
            <xsl:choose>
              <xsl:when test="string-length($effTm_low)>0">
                <xsl:attribute name="value">
                  <xsl:value-of select="$effTm_low"/>
                </xsl:attribute>
              </xsl:when>
              <xsl:otherwise>
                <xsl:attribute name="nullFlavor">
                  <xsl:value-of select="'UNK'"/>
                </xsl:attribute>
              </xsl:otherwise>
            </xsl:choose>
          </low>
          <high>
            <xsl:choose>
              <xsl:when test="string-length($effTm_high)>0">
                <xsl:attribute name="value">
                  <xsl:value-of select="$effTm_high"/>
                </xsl:attribute>
              </xsl:when>
              <xsl:otherwise>
                <xsl:attribute name="nullFlavor">
                  <xsl:value-of select="'UNK'"/>
                </xsl:attribute>
              </xsl:otherwise>
            </xsl:choose>
          </high>
        </effectiveTime>
        <xsl:variable name="trmtEndRsonCodeVal">
          <xsl:choose>
            <xsl:when
              test="string-length($trimAct/trim:observation/trim:value[trim:label='Treatment end reason']/trim:CE/trim:displayName)>0">
              <xsl:value-of select="$trimAct/trim:observation/trim:value[trim:label='Treatment end reason']/trim:CE/trim:displayName"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:text/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <xsl:variable name="trmtEndRsonStrVal">
          <xsl:choose>
            <xsl:when test="string-length($trimAct/trim:observation/trim:value[trim:label='Other']/trim:ST)>0">
              <xsl:text> </xsl:text>
              <xsl:value-of select="$trimAct/trim:observation/trim:value[trim:label='Other']/trim:ST"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:text/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <xsl:if test="string-length($trmtEndRsonCodeVal)>0">
          <entryRelationship typeCode="REFR">
            <observation classCode="OBS" moodCode="EVN">
              <code code="419617009" displayName="reason for procedure cancelation" codeSystem="2.16.840.1.113883.6.96"
                codeSystemName="SNOMED-CT">
                <originalText>Treatment end reason</originalText>
              </code>
              <statusCode code="completed"/>
              <value>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="$trmtEndRsonCodeVal"/>
                  <xsl:with-param name="overriddenOriginalText" select="concat($trmtEndRsonCodeVal,$trmtEndRsonStrVal)"/>
                </xsl:call-template>
              </value>
            </observation>
          </entryRelationship>
        </xsl:if>
        <xsl:for-each select="$trimAct/trim:relationship[@name='regimen']/trim:act/trim:observation">
          <xsl:variable name="medMat" select="trim:value[trim:label='Agent Name']/trim:ST"/>
          <xsl:variable name="medRpt" select="trim:value[trim:label='Completed Cycles']/trim:ST"/>
          <xsl:if test="string-length($medMat)>0">
          <entryRelationship typeCode="COMP">
            <substanceAdministration classCode="SBADM" moodCode="EVN">
              <templateId root="2.16.840.1.113883.10.20.1.24" assigningAuthorityName="HL7 CCD"/>
              <id nullFlavor="NI"/>
              <statusCode code="completed"/>
              <effectiveTime nullFlavor="NI"/>
              <xsl:if test="string-length($medRpt)>0">
                <repeatNumber>
                  <xsl:attribute name="value">
                    <xsl:value-of select="$medRpt"/>
                  </xsl:attribute>
                </repeatNumber>  
              </xsl:if>
              <consumable>
                <manufacturedProduct>
                  <!-- Product template -->
                  <templateId root="2.16.840.1.113883.10.20.1.53"/>
                  <manufacturedMaterial>
                    <code>
                      <xsl:call-template name="translateCode">
                        <xsl:with-param name="trim_dispNm" select="$medMat"/>
                        <xsl:with-param name="noXSI" select="'true'"/>
                      </xsl:call-template>
                    </code>
                  </manufacturedMaterial>
                </manufacturedProduct>
              </consumable>
              <xsl:for-each select="trim:value[not(trim:label='Agent Name') and not(trim:label='Completed Cycles')]">
                <xsl:variable name="tp" select="trim:label"/>
                <xsl:variable name="vl" select="trim:ST"/>
                <entryRelationship typeCode="REFR">
                  <observation classCode="OBS" moodCode="EVN">
                    <code>
                      <xsl:call-template name="translateCode">
                        <xsl:with-param name="trim_dispNm" select="$tp"/>
                        <xsl:with-param name="noXSI" select="'true'"/>
                      </xsl:call-template>
                    </code>
                    <statusCode code="completed"/>
                    <value xsi:type="ST">
                        <xsl:value-of select="$vl"/>
                    </value>
                  </observation>
                </entryRelationship>
              </xsl:for-each>              
            </substanceAdministration>
          </entryRelationship>
          </xsl:if>
        </xsl:for-each>
      </procedure>
    </entry>
  </xsl:template>

  <xsl:template name="medObs">
    <xsl:param name="trim_act"/>
    <xsl:variable name="obsCd">
      <xsl:value-of select="$trim_act/trim:title/trim:ST"/>
    </xsl:variable>
    <entry typeCode="DRIV">
      <observation classCode="OBS" moodCode="EVN">
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <code>
          <xsl:call-template name="translateCode">
            <xsl:with-param name="noXSI" select="'true'"/>
            <xsl:with-param name="trim_dispNm" select="$obsCd"/>
          </xsl:call-template>
        </code>
        <text>
          <reference>
            <xsl:attribute name="value">
              <xsl:value-of select="generate-id($trim_act)"/>
            </xsl:attribute>
          </reference>
        </text>
        <statusCode code="completed"/>
        <effectiveTime nullFlavor="UNK"/>
        <value xsi:type="INT">
          <xsl:attribute name="value">
            <xsl:value-of select="$trim_act/trim:observation/trim:value/trim:INT/trim:value"/>
          </xsl:attribute>
        </value>
      </observation>
    </entry>

  </xsl:template>

  <xsl:template name="chemoregi">
    <xsl:param name="trim_act"/>
    <xsl:variable name="id" select="$trim_act/trim:id/trim:II"/>
    <entry>
      <substanceAdministration classCode="SBADM" moodCode="EVN">
        <templateId root="2.16.840.1.113883.10.20.1.24" assigningAuthorityName="CCD"/>
        <xsl:call-template name="make_II">
          <xsl:with-param name="II_root" select="$id/trim:root"/>
          <xsl:with-param name="II_ext" select="$id/trim:extension"/>
        </xsl:call-template>
        <text>
          <xsl:for-each select="$trim_act/trim:observation">
            <xsl:for-each select="trim:value">
              <xsl:choose>
                <xsl:when test="trim:PQ">
                  <xsl:if test="trim:PQ/trim:value">
                    <xsl:value-of select="trim:PQ/trim:label"/>
                    <xsl:text>:</xsl:text>
                    <xsl:value-of select="trim:PQ/trim:value"/>
                    <xsl:value-of select="trim:PQ/trim:unit"/>
                    <xsl:text>, </xsl:text>
                  </xsl:if>
                </xsl:when>
                <xsl:when test="trim:INT">
                  <xsl:if test="trim:INT/trim:value">
                    <xsl:value-of select="trim:INT/trim:label"/>
                    <xsl:text>:</xsl:text>
                    <xsl:value-of select="trim:INT/trim:value"/>
                    <xsl:text>, </xsl:text>
                  </xsl:if>
                </xsl:when>
                <xsl:when test="trim:CE">
                  <xsl:if test="string-length(trim:CE/trim:displayName)>0">
                    <xsl:value-of select="trim:label"/>
                    <xsl:text>:</xsl:text>
                    <xsl:value-of select="trim:CE/trim:displayName"/>
                    <xsl:text>, </xsl:text>
                  </xsl:if>
                </xsl:when>
              </xsl:choose>
            </xsl:for-each>
          </xsl:for-each>
        </text>
        <statusCode code="completed"/>
        <consumable>
          <manufacturedProduct>
            <templateId root="2.16.840.1.113883.10.20.1.53" assigningAuthorityName="CCD"/>
            <manufacturedMaterial>
              <code>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="noXSI" select="'true'"/>
                  <xsl:with-param name="trim_dispNm" select="$trim_act/trim:title/trim:ST"/>
                </xsl:call-template>
              </code>
            </manufacturedMaterial>
          </manufacturedProduct>
        </consumable>
      </substanceAdministration>
    </entry>

  </xsl:template>

  <xsl:template name="medications_hormoneTherapy">
    <xsl:param name="trim_hormoneTx"/>
    <xsl:param name="trim_hormoneDuration"/>
    <xsl:param name="trim_hormoneTime"/>
    <entry>
      <substanceAdministration classCode="SBADM" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.8" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.24" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7.1" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <statusCode code="completed"/>
        <xsl:choose>
          <xsl:when test="$trim_hormoneDuration/trim:act/trim:observation/trim:value/trim:ST != ''">
            <effectiveTime xsi:type="IVL_TS">
              <width unit="a">
                <xsl:attribute name="value">
                  <xsl:value-of select="$trim_hormoneDuration/trim:act/trim:observation/trim:value/trim:ST"/>
                </xsl:attribute>
                <xsl:variable name="durationType"
                  select="$trim_hormoneDuration/trim:act/trim:observation/trim:value/trim:CE/trim:displayName"/>
                <xsl:attribute name="unit">
                  <xsl:choose>
                    <xsl:when test="$durationType = 'years'">a</xsl:when>
                    <xsl:when test="$durationType = 'months'">mo</xsl:when>
                    <xsl:when test="$durationType = 'days'">d</xsl:when>
                    <xsl:when test="$durationType = 'weeks'">wk</xsl:when>
                  </xsl:choose>
                </xsl:attribute>
              </width>
            </effectiveTime>
          </xsl:when>
          <xsl:otherwise>
            <effectiveTime xsi:type="IVL_TS" nullFlavor="NI"/>
          </xsl:otherwise>
        </xsl:choose>
        <consumable>
          <manufacturedProduct>
            <templateId root="2.16.840.1.113883.3.88.11.83.8.2" assigningAuthorityName="HITSP C83"/>
            <templateId root="2.16.840.1.113883.10.20.1.53" assigningAuthorityName="CCD"/>
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7.2" assigningAuthorityName="IHE PCC"/>
            <manufacturedMaterial>
              <code>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="noXSI" select="'true'"/>
                  <xsl:with-param name="trim_dispNm" select="$trim_hormoneTx/trim:act/trim:observation/trim:value/trim:valueSet"/>
                </xsl:call-template>
              </code>
            </manufacturedMaterial>
          </manufacturedProduct>
        </consumable>
        <xsl:for-each select="$trim_hormoneTx/trim:act/trim:observation/trim:value/trim:SETCE">
          <entryRelationship typeCode="RSON">
            <observation classCode="OBS" moodCode="EVN">
              <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
              <value>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="current()/trim:displayName"/>
                </xsl:call-template>
              </value>
            </observation>
          </entryRelationship>
        </xsl:for-each>
        <xsl:if test="$trim_hormoneTime">
          <entryRelationship typeCode="REFR">
            <observation classCode="OBS" moodCode="EVN" negationInd="true">
              <xsl:if test="$trim_hormoneTime/trim:act/trim:observation/trim:value/trim:CE/trim:displayName != 'Yes'">
                <xsl:attribute name="negationInd">true</xsl:attribute>
              </xsl:if>
              <code code="ASSERTION" codeSystem="2.16.840.1.113883.5.4"/>
              <value xsi:type="CD" nullFlavor="OTH">
                <originalText>
                  <xsl:value-of select="$trim_hormoneTime/trim:act/trim:observation/trim:value/trim:label"/>
                </originalText>
              </value>
            </observation>
          </entryRelationship>
        </xsl:if>
      </substanceAdministration>
    </entry>
  </xsl:template>
  <xsl:template name="medications_entry">
    <xsl:param name="trim_entry"/>
    <entry typeCode="DRIV">
      <substanceAdministration classCode="SBADM" moodCode="EVN">
        <templateId root="2.16.840.1.113883.3.88.11.83.8" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.24" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7.1" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <statusCode code="completed"/>
        <!-- Start date -->
        <effectiveTime xsi:type="IVL_TS">
          <low>
            <xsl:attribute name="value">
              <xsl:value-of select="$trim_entry/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
            </xsl:attribute>
          </low>
        </effectiveTime>
        <!-- Frequency -->
        <effectiveTime xsi:type="PIVL_TS" institutionSpecified="false" operator="A">
          <period nullFlavor="OTH">
            <translation>
              <originalText>
                <xsl:value-of
                  select="$trim_entry/trim:act/trim:relationship[@name='frequency']/trim:act/trim:observation/trim:value/trim:ST"/>
              </originalText>
            </translation>
          </period>
        </effectiveTime>
        <!-- Route -->
        <routeCode nullFlavor="OTH">
          <originalText>
            <xsl:value-of select="$trim_entry/trim:act/trim:relationship[@name='route']/trim:act/trim:observation/trim:value/trim:ST"/>
          </originalText>
        </routeCode>
        <!-- Dose -->
        <doseQuantity nullFlavor="OTH">
          <translation>
            <originalText>
              <xsl:value-of select="$trim_entry/trim:act/trim:relationship[@name='dose']/trim:act/trim:observation/trim:value/trim:ST"/>
            </originalText>
          </translation>
        </doseQuantity>
        <!-- Medication -->
        <consumable>
          <manufacturedProduct>
            <templateId root="2.16.840.1.113883.3.88.11.83.8.2" assigningAuthorityName="HITSP C83"/>
            <templateId root="2.16.840.1.113883.10.20.1.53" assigningAuthorityName="CCD"/>
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7.2" assigningAuthorityName="IHE PCC"/>
            <manufacturedMaterial>
              <code nullFlavor="OTH">
                <originalText>
                  <xsl:value-of
                    select="$trim_entry/trim:act/trim:relationship[@name='medicationName']/trim:act/trim:observation/trim:value/trim:ST"/>
                  <reference>
                    <xsl:attribute name="value">
                      <xsl:value-of select="generate-id($trim_entry/trim:act/trim:relationship[@name='medicationName'])"/>
                    </xsl:attribute>
                  </reference>
                </originalText>
              </code>
            </manufacturedMaterial>
          </manufacturedProduct>
        </consumable>
      </substanceAdministration>
    </entry>
  </xsl:template>
  <xsl:template name="menopausalStatus_estrogenReplacement">
    <xsl:param name="trim_entry"/>
    <xsl:param name="trim_years" select="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Years']/trim:ST"/>
    <xsl:param name="trim_months" select="$trim_entry/trim:act/trim:observation/trim:value[trim:label='Months']/trim:ST"/>
    <entry>
      <!-- estrogen replacement, if no, natationInd="true"  -->
      <substanceAdministration classCode="SBADM" moodCode="EVN">
        <!--Medication activity templates -->
        <templateId root="2.16.840.1.113883.3.88.11.83.8" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.24" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7.1" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <text>
          <reference>
            <xsl:attribute name="value">
              <xsl:value-of select="generate-id($trim_entry)"/>
            </xsl:attribute>
          </reference>
        </text>
        <statusCode code="completed"/>
        <effectiveTime xsi:type="IVL_TS">
          <!-- 3 year 6 month -->
          <width>
            <xsl:choose>
              <xsl:when test="$trim_years != '' and $trim_months != ''">
                <xsl:attribute name="value">
                  <xsl:value-of select="($trim_years * 12) + $trim_months"/>
                </xsl:attribute>
                <xsl:attribute name="unit">mo</xsl:attribute>
              </xsl:when>
              <xsl:when test="$trim_years != ''">
                <xsl:attribute name="value">
                  <xsl:value-of select="$trim_years * 12"/>
                </xsl:attribute>
                <xsl:attribute name="unit">mo</xsl:attribute>
              </xsl:when>
              <xsl:when test="$trim_months != ''">
                <xsl:attribute name="value">
                  <xsl:value-of select="$trim_months"/>
                </xsl:attribute>
                <xsl:attribute name="unit">mo</xsl:attribute>
              </xsl:when>
              <xsl:otherwise>
                <xsl:attribute name="nullFlavor">NI</xsl:attribute>
              </xsl:otherwise>
            </xsl:choose>
          </width>
        </effectiveTime>
        <consumable>
          <manufacturedProduct>
            <templateId root="2.16.840.1.113883.3.88.11.83.8.2" assigningAuthorityName="HITSP C83"/>
            <templateId root="2.16.840.1.113883.10.20.1.53" assigningAuthorityName="CCD"/>
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7.2" assigningAuthorityName="IHE PCC"/>
            <!-- Product template -->
            <manufacturedMaterial>
              <code code="259263" codeSystem="2.16.840.1.113883.6.88" displayName="Estrogenic substances" codeSystemName="RxNorm">
                <originalText>estrogen replacement<reference/>
                </originalText>
              </code>
            </manufacturedMaterial>
          </manufacturedProduct>
        </consumable>
      </substanceAdministration>
    </entry>
  </xsl:template>
  <xsl:template name="PBH_medTherapy">
    <xsl:param name="trimRel"/>
    <xsl:variable name="lowTs">
      <xsl:value-of select="$trimRel/trim:act/trim:observation/trim:value[trim:label='Start Date']/trim:TS/trim:value"/>
    </xsl:variable>
    <xsl:variable name="highTs">
      <xsl:value-of select="$trimRel/trim:act/trim:observation/trim:value[trim:label='End Date']/trim:TS/trim:value"/>
    </xsl:variable>
    <xsl:variable name="episode">
      <xsl:value-of select="$trimRel/trim:act/trim:observation/trim:value[trim:label='Episode']/trim:CE/trim:displayName"/>
    </xsl:variable>
    <xsl:variable name="medLbl" select="$trimRel/trim:act/trim:observation/trim:value/trim:ST"/>
    <entry>
      <substanceAdministration classCode="SBADM" moodCode="EVN">
        <xsl:comment> Past Breast History/Treatment History - Hormone or Bisphosphonate therapy</xsl:comment>
        <templateId root="2.16.840.1.113883.3.88.11.83.8" assigningAuthorityName="HITSP C83"/>
        <templateId root="2.16.840.1.113883.10.20.1.24" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7" assigningAuthorityName="IHE PCC"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7.1" assigningAuthorityName="IHE PCC"/>
        <id nullFlavor="NI"/>
        <statusCode code="completed"/>
        <effectiveTime xsi:type="IVL_TS">
          <!-- Start date -->
          <low>
            <xsl:choose>
              <xsl:when test="string-length($lowTs)>0">
                <xsl:attribute name="value">
                  <xsl:value-of select="$lowTs"/>
                </xsl:attribute>
              </xsl:when>
              <xsl:otherwise>
                <xsl:attribute name="nullFlavor">UNK</xsl:attribute>
              </xsl:otherwise>
            </xsl:choose>
          </low>
          <!-- End date -->
          <high>
            <xsl:choose>
              <xsl:when test="string-length($highTs)>0">
                <xsl:attribute name="value">
                  <xsl:value-of select="$highTs"/>
                </xsl:attribute>
              </xsl:when>
              <xsl:otherwise>
                <xsl:attribute name="nullFlavor">UNK</xsl:attribute>
              </xsl:otherwise>
            </xsl:choose>
          </high>
        </effectiveTime>
        <consumable>
          <!-- Medication -->
          <manufacturedProduct>
            <!-- Product templates -->
            <templateId root="2.16.840.1.113883.3.88.11.83.8.2" assigningAuthorityName="HITSP C83"/>
            <templateId root="2.16.840.1.113883.10.20.1.53" assigningAuthorityName="CCD"/>
            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7.2" assigningAuthorityName="IHE PCC"/>
            <manufacturedMaterial>
              <xsl:choose>
                <xsl:when test="$medLbl='bisphosphonate medication'">
                  <code code="96281001" displayName="diphosphonate" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
                    <originalText>Medication: bisphosphonate medication<reference value="pointer"/>
                    </originalText>
                  </code>
                </xsl:when>
                <xsl:when test="$medLbl='hormone medication'">
                  <code code="C9026" displayName="HORMONES / SYNTHETICS / MODIFIERS" codeSystem="2.16.840.1.113883.3.26.1.5"
                    codeSystemName="NDF-RT (Drug Classification)">
                    <originalText>Medication: hormone medication</originalText>
                  </code>
                </xsl:when>
              </xsl:choose>
            </manufacturedMaterial>
          </manufacturedProduct>
        </consumable>
        <xsl:if test="string-length($episode)>0">
          <entryRelationship typeCode="REFR">
            <observation classCode="OBS" moodCode="EVN" negationInd="false">
              <!-- Problem observation templates -->
              <!--
							<templateId root="2.16.840.1.113883.10.20.1.28" assigningAuthorityName="CCD"/>
							<templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.5" assigningAuthorityName="IHE PCC"/>
							-->
              <id nullFlavor="NI"/>
              <code xsi:type="CD" code="246456000" displayName="episodicity" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT">
                <originalText>Episode</originalText>
              </code>
              <text>
                <reference value="pointer"/>
              </text>
              <statusCode code="completed"/>
              <effectiveTime>
                <low nullFlavor="UNK"/>
              </effectiveTime>
              <!-- hard code for now. The episode valueSet only has one code '1' -->
              <value xsi:type="CD" code="255217005" displayName="first episode" codeSystem="2.16.840.1.113883.6.96"
                codeSystemName="SNOMED CT">
                <originalText>1</originalText>
              </value>
            </observation>
          </entryRelationship>
        </xsl:if>
      </substanceAdministration>
    </entry>
  </xsl:template>
</xsl:stylesheet>
