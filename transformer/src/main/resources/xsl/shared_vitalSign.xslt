<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3"
  xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs cda trim">
  <!-- vitalSign section template -->
  <xsl:template name="vitalSigns_section">
    <xsl:param name="vital_sec"/>
    <xsl:param name="trim_chemotrmt"/>
    <xsl:if test="$vital_sec or $trim_chemotrmt">
      <component>
        <section>
          <templateId root="2.16.840.1.113883.10.20.1.16" assigningAuthorityName="HL7 CCD"/>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2" assigningAuthorityName="IHE PCC"/>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.3.25" assigningAuthorityName="IHE PCC"/>
          <templateId root="2.16.840.1.113883.3.88.11.83.119" assigningAuthorityName="HITSP/C83"/>
          <code code="8716-3" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="vital signs"/>
          <title>Vital signs</title>
          <text/>
          <xsl:if test="$vital_sec">
            <xsl:for-each select="$vital_sec/trim:act/trim:observation">
              <xsl:call-template name="vitalSigns_organizer">
                <xsl:with-param name="vitalOrgniz" select="current()"/>
                <xsl:with-param name="vitalId" select="$vital_sec/trim:id/trim:II"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
          <xsl:if test="$trim_chemotrmt">
            <xsl:for-each select="$trim_chemotrmt/trim:act/trim:observation">
              <xsl:call-template name="vitalSigns_organizer">
                <xsl:with-param name="vitalOrgniz" select="current()"/>
                <xsl:with-param name="vitalId" select="$trim_chemotrmt/trim:act/trim:id/trim:II"/>
              </xsl:call-template>
            </xsl:for-each>
          </xsl:if>
        </section>
      </component>
    </xsl:if>
  </xsl:template>
  <!-- vitalSign organizer -->
  <xsl:template name="vitalSigns_organizer">
    <xsl:param name="vitalOrgniz"/>
    <xsl:param name="vitalId"/>
    <entry>
      <organizer classCode="CLUSTER" moodCode="EVN">
        <templateId root="2.16.840.1.113883.10.20.1.32" assigningAuthorityName="CCD"/>
        <templateId root="2.16.840.1.113883.10.20.1.35" assigningAuthorityName="CCD"/>
        <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.1" assigningAuthorityName="IHE PCC"/>
        <xsl:call-template name="make_II">
          <xsl:with-param name="II_root" select="$vitalId/trim:root"/>
          <xsl:with-param name="II_ext" select="$vitalId/trim:extension"/>
        </xsl:call-template>
        <code code="46680005" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT" displayName="Vital signs"/>
        <statusCode code="completed"/>
        <effectiveTime>
          <xsl:attribute name="value">
            <xsl:value-of select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
          </xsl:attribute>
        </effectiveTime>
        <!-- taken by person -->
        <xsl:variable name="takenByName" select="$vitalOrgniz/trim:value[trim:label='Name: ']/trim:CE/trim:displayName"/>
        <xsl:if test="$takenByName != ''">
          <performer>
            <assignedEntity>
              <id nullFlavor="NI"/>
              <addr nullFlavor="NI"/>
              <telecom nullFlavor="NI"/>
              <assignedPerson>
                <name>Taken by: <xsl:value-of select="$takenByName"/></name>
              </assignedPerson>
            </assignedEntity>
          </performer>
        </xsl:if>
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
    <xsl:if test="$vital_value/trim:PQ">
      <component>
        <observation classCode="OBS" moodCode="EVN">
          <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.2" assigningAuthorityName="IHE PCC"/>
          <templateId root="2.16.840.1.113883.3.88.11.83.14" assigningAuthorityName="HITSP C83"/>
          <id>
            <xsl:attribute name="nullFlavor">NI</xsl:attribute>
          </id>
          <xsl:variable name="vital_label" select="$vital_value/trim:PQ/trim:label"/>
          <code>
            <xsl:call-template name="translateCode">
              <xsl:with-param name="trim_dispNm" select="$vital_label"/>
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
            <xsl:attribute name="value">
              <xsl:value-of select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
            </xsl:attribute>
          </effectiveTime>
          <value>
            <xsl:attribute name="xsi:type">
              <xsl:value-of select="'PQ'"/>
            </xsl:attribute>
            <xsl:attribute name="value">
              <xsl:value-of select="$vital_value/trim:PQ/trim:value"/>
            </xsl:attribute>
            <xsl:attribute name="unit">
              <xsl:value-of select="$vital_value/trim:PQ/trim:unit"/>
            </xsl:attribute>
          </value>
        </observation>
      </component>
    </xsl:if>
    
    <xsl:if test="string-length($vital_value/trim:ST) and not($vital_value/trim:valueSet)">
      <component>
        <observation classCode="OBS" moodCode="EVN">
          <templateId root="2.16.840.1.113883.10.20.1.31" assigningAuthorityName="CCD"/>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13" assigningAuthorityName="IHE PCC"/>
          <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.13.2" assigningAuthorityName="IHE PCC"/>
          <templateId root="2.16.840.1.113883.3.88.11.83.14" assigningAuthorityName="HITSP C83"/>
          <id>
            <xsl:attribute name="nullFlavor">NI</xsl:attribute>
          </id>
          <xsl:variable name="vital_label" select="trim:label"/>
          <code>
            <xsl:choose>
              <xsl:when test="$vital_label='BP: '">
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="'bp(s):'"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:when test="not($vital_label)">
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="'bp(d):'"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:otherwise>
                <xsl:call-template name="translateCode">
                  <xsl:with-param name="trim_dispNm" select="$vital_label"/>
                </xsl:call-template>
              </xsl:otherwise>
            </xsl:choose>
          </code>
          <text>
            <reference>
              <xsl:attribute name="value">
                <xsl:value-of select="generate-id(current())"/>
              </xsl:attribute>
            </reference>
          </text>
          <statusCode code="completed"/>
          <xsl:choose>
            <xsl:when test="starts-with($vital_label,'Prior')">
              <xsl:choose>
                <xsl:when test="parent::trim:*[$vital_value]/trim:value[trim:label='Prior2 Date']">
                  <effectiveTime>
                    <xsl:attribute name="value">
                      <xsl:value-of select="parent::trim:*[$vital_value]/trim:value[trim:label='Prior2 Date']/trim:TS/trim:value"/>
                    </xsl:attribute>
                  </effectiveTime>
                </xsl:when>
                <xsl:otherwise>
                  <effectiveTime nullFlavor="UNK"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
              <effectiveTime>
                <xsl:attribute name="value">
                  <xsl:value-of select="/trim:trim/trim:act/trim:effectiveTime/trim:TS/trim:value"/>
                </xsl:attribute>
              </effectiveTime>
            </xsl:otherwise>
          </xsl:choose>
          <xsl:choose>
            <xsl:when test="starts-with($vital_label,'Prior') or starts-with($vital_label,'Name')">
              <value>
                <xsl:attribute name="xsi:type">
                  <xsl:value-of select="'ST'"/>
                </xsl:attribute>
                <xsl:value-of select="$vital_value/trim:ST"/>
              </value>
            </xsl:when>
            <xsl:otherwise>
              <value>
                <xsl:attribute name="xsi:type">
                  <xsl:value-of select="'PQ'"/>
                </xsl:attribute>
                <xsl:attribute name="value">
                  <xsl:value-of select="$vital_value/trim:ST"/>
                </xsl:attribute>
                <xsl:attribute name="unit">
                  <xsl:choose>
                    <xsl:when test="($vital_label = 'T: ')">
                      <xsl:value-of select="'Cel'"/>
                    </xsl:when>
                    <xsl:when test="($vital_label = 'P: ')">
                      <xsl:value-of select="'/min'"/>
                    </xsl:when>
                    <xsl:when test="($vital_label = 'R: ')">
                      <xsl:value-of select="'/min'"/>
                    </xsl:when>
                    <xsl:when test="($vital_label = 'Wt: ')">
                      <xsl:value-of select="'kg'"/>
                    </xsl:when>
                    <xsl:when test="($vital_label = 'Ht: ')">
                      <xsl:value-of select="'cm'"/>
                    </xsl:when>
                    <xsl:when test="($vital_label = 'BP: ')">
                      <xsl:value-of select="'mm[Hg]'"/>
                    </xsl:when>
                    <xsl:when test="not($vital_label) and (string-length($vital_value/trim:ST)>0)">
                      <xsl:value-of select="'mm[Hg]'"/>
                    </xsl:when>
                  </xsl:choose>
                </xsl:attribute>
              </value>
            </xsl:otherwise>
          </xsl:choose>
        </observation>
      </component>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
