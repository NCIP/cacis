<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xpath-default-namespace="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:import href="HL7v2_XmlToVerticalBar.xslt"/>
  <!-- Todo: Figure out mechanism for representing v3 code systems in v2 -->
  <xsl:key name="code" match="code" use="@code"/>
  <xsl:variable name="v2xml">
    <xsl:apply-templates select="/ClinicalDocument"/>
  </xsl:variable>
  <!-- Override the separator variables because the context root won't be the v2.xml.  Need to do this positionally due to changing the xpath-default-namespace and no way to declare "no" namespace for these declarations -->
  <xsl:variable name="fieldSeparator" select="$v2xml/*/*[1]/*[1]"/>
  <xsl:variable name="compSeparator" select="substring($v2xml/*/*[1]/*[2],1,1)"/>
  <xsl:variable name="repSeparator" select="substring($v2xml/*/*[1]/*[2],2,1)"/>
  <xsl:variable name="escapeChar" select="substring($v2xml/*/*[1]/*[2],3,1)"/>
  <xsl:variable name="subCompSeparator" select="substring($v2xml/*/*[1]/*[2],4,1)"/>
  <xsl:template match="/">
    <xsl:apply-templates mode="v2ToXML" select="$v2xml/*"/>
  </xsl:template>
  <xsl:template match="ClinicalDocument">
    <ORU_R01>
      <MSH>
        <MSH.1>|</MSH.1>
        <MSH.2>^~\&amp;</MSH.2>
        <MSH.7>
          <xsl:value-of select="effectiveTime/@value"/>
        </MSH.7>
        <MSH.9>
          <MSG.1>ORU</MSG.1>
          <MSG.2>R01</MSG.2>
          <MSG.3>ORU_R01</MSG.3>
        </MSH.9>
        <MSH.10>
          <xsl:value-of select="concat(id/@root, ':', id/@extension)"/>
        </MSH.10>
        <MSH.11>
          <PT.1>P</PT.1>
        </MSH.11>
        <MSH.12>
          <VID.1>2.6</VID.1>
        </MSH.12>
        <MSH.15>NE</MSH.15>
        <MSH.16>NE</MSH.16>
        <MSH.18>UNICODE UTF-8</MSH.18>
        <xsl:for-each select="languageCode">
          <MSH.19>
            <CWE.1>
              <xsl:value-of select="@code"/>
            </CWE.1>
            <CWE.3>RFC3066</CWE.3>
          </MSH.19>
        </xsl:for-each>
        <xsl:for-each select="templateId[1]">
          <MSH.21>
            <EI.3>
              <xsl:value-of select="@root"/>
            </EI.3>
            <EI.4>ISO</EI.4>
          </MSH.21>
        </xsl:for-each>
      </MSH>
      <xsl:variable name="code">
        <CWE.1>
          <xsl:value-of select="code/@code"/>
        </CWE.1>
        <CWE.2>
          <xsl:value-of select="code/@displayName"/>
        </CWE.2>
        <CWE.3>
          <xsl:value-of select="code/@codeSystem"/>
        </CWE.3>
        <xsl:for-each select="componentOf/encompassingEncounter/code">
          <CWE.4>
            <xsl:value-of select="@code"/>
          </CWE.4>
          <CWE.5>
            <xsl:value-of select="@displayName"/>
          </CWE.5>
          <CWE.6>
            <xsl:value-of select="@codeSystem"/>
          </CWE.6>
          <CWE.9>
            <xsl:value-of select="originalText"/>
          </CWE.9>
        </xsl:for-each>
      </xsl:variable>
      <xsl:variable name="OBX">
        <OBX>
          <OBX.3>
            <xsl:copy-of select="$code"/>
          </OBX.3>
          <OBX.11>X</OBX.11>
          <xsl:for-each select="author/time[not(@nullFlavor)][1]">
            <OBX.14>
              <xsl:value-of select="@value"/>
            </OBX.14>
          </xsl:for-each>
          <xsl:for-each select="author/assignedAuthor">
            <OBX.16>
              <XCN.1>
                <xsl:value-of select="id/@extension"/>
              </XCN.1>
<!--          Future: Expand this, if it will ever get populated
              <xsl:for-each select="assignedPerson/name[not(@nullFlavor)]">
              </xsl:for-each>-->
              <XCN.9>
                <HD.2>
                  <xsl:value-of select="id/@root"/>
                </HD.2>
                <HD.3>ISO</HD.3>
              </XCN.9>
            </OBX.16>
          </xsl:for-each>
          <OBX.21>
            <EI.1>
              <xsl:value-of select="id/@extension"/>
            </EI.1>
            <EI.3>
              <xsl:value-of select="id/@root"/>
            </EI.3>
            <EI.4>ISO</EI.4>
          </OBX.21>
          <xsl:for-each select="custodian/assignedCustodian/representedCustodianOrganization/id">
            <OBX.23>
              <XON.3>
                <xsl:value-of select="@extension"/>
              </XON.3>
              <XON.6>
                <HD.2>
                  <xsl:value-of select="@root"/>
                </HD.2>
                <HD.3>ISO</HD.3>
              </XON.6>
            </OBX.23>
          </xsl:for-each>
        </OBX>
      </xsl:variable>
      <ORU_R01.PATIENT_RESULT>
        <ORU_R01.PATIENT>
          <xsl:apply-templates select="recordTarget/patientRole"/>
        </ORU_R01.PATIENT>
        <ORU_R01.ORDER_OBSERVATION>
          <xsl:call-template name="OBXtoOBR">
            <xsl:with-param name="OBX" select="$OBX"/>
            <xsl:with-param name="id" select="1"/>
          </xsl:call-template>
          <ORU_R01.G7O>
            <ORU_R01.OBSERVATION>
              <xsl:copy-of select="$OBX"/>
              <xsl:call-template name="titleAndTextToNTE"/>
            </ORU_R01.OBSERVATION>
          </ORU_R01.G7O>
        </ORU_R01.ORDER_OBSERVATION>
        <xsl:apply-templates select="component/structuredBody/component/section">
          <xsl:with-param name="parentCode" select="$code"/>
          <xsl:with-param name="id" select="1"/>
        </xsl:apply-templates>
        <xsl:for-each select="documentationOf/serviceEvent[@classCode='CLNTRL']/id">
          <CTI>
            <CTI.1>
              <EI.1>
                <xsl:value-of select="@extension"/>
              </EI.1>
              <EI.3>
                <xsl:value-of select="@root"/>
              </EI.3>
              <EI.4>ISO</EI.4>
            </CTI.1>
          </CTI>
        </xsl:for-each>
      </ORU_R01.PATIENT_RESULT>
    </ORU_R01>
  </xsl:template>
  <xsl:template match="patientRole">
    <PID>
      <xsl:for-each select="id">
        <PID.3>
          <CX.1>
            <xsl:value-of select="@extension"/>
          </CX.1>
          <CX.4>
            <HD.2>
              <xsl:value-of select="@root"/>
            </HD.2>
            <HD.3>ISO</HD.3>
          </CX.4>
        </PID.3>
      </xsl:for-each>
      <xsl:for-each select="patient">
        <xsl:for-each select="name">
          <PID.5>
            <XPN.1>
              <FN.1>
                <xsl:value-of select="family"/>
              </FN.1>
            </XPN.1>
            <XPN.2>
              <xsl:value-of select="given[1]"/>
            </XPN.2>
            <xsl:if test="given[position()&gt;1][node()]">
              <XPN.3>
                <xsl:for-each select="given[position()&gt;1]">
                  <xsl:if test="position!=1">
                    <xsl:value-of select="' '"/>
                  </xsl:if>
                  <xsl:value-of select="."/>
                </xsl:for-each>
              </XPN.3>
            </xsl:if>
          </PID.5>
        </xsl:for-each>
        <xsl:for-each select="birthTime[not(@nullFlavor)]">
          <PID.7>
            <TS.1>
              <xsl:value-of select="@value"/>
            </TS.1>
          </PID.7>
        </xsl:for-each>
        <xsl:for-each select="administrativeGenderCode[not(@nullFlavor='NI')]">
          <PID.8>
            <!-- Todo: Translate codes, including null flavor -->
          </PID.8>
        </xsl:for-each>
      </xsl:for-each>
      <xsl:for-each select="addr[not(@nullFlavor)]">
        <PID.11>
          <!-- Todo: Handle this -->
        </PID.11>
      </xsl:for-each>
      <xsl:for-each select="telecom[not(@nullFlavor)]">
        <PID.13>
          <!-- Todo: Handle this (could also be PID.14) -->
        </PID.13>
      </xsl:for-each>
    </PID>
  </xsl:template>
  <xsl:template name="titleAndTextToNTE">
    <xsl:if test="title!='' or text/text()[normalize-space(.)!='']">
      <xsl:variable name="text">
        <xsl:value-of select="title/text()[normalize-space(.)!='']"/>
        <xsl:if test="title/text()[normalize-space(.)!=''] and text/text()[normalize-space(.)!='']">/.br/</xsl:if>
        <xsl:value-of select="text/text()[normalize-space(.)!='']">
          <!-- Todo: Need to handle markup -->
        </xsl:value-of>
      </xsl:variable>
      <NTE>
        <NTE.3>
          <xsl:value-of select="$text"/>
        </NTE.3>
        <NTE.4>
          <CWE.1>RE</CWE.1>
          <CWE.3>HL70364</CWE.3>
        </NTE.4>
      </NTE>
    </xsl:if>
  </xsl:template>
  <xsl:template match="section|organizer|act|observation|procedure">
    <xsl:param name="parentCode"/>
    <xsl:param name="parentSubIdentifier"/>
    <xsl:param name="id"/>
    <xsl:variable name="code">
      <xsl:for-each select="code">
        <xsl:choose>
          <xsl:when test="@code or originalText">
            <xsl:call-template name="doCWE"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:for-each select="parent::*/templateId[1]">
              <CWE.1>
                <xsl:value-of select="@root"/>
              </CWE.1>
              <CWE.3>SomeTemplateCodeSystem</CWE.3>
            </xsl:for-each>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:for-each>
    </xsl:variable>
    <xsl:variable name="subIdentifier">
      <xsl:if test="count(key('code', code/@code))!=1">
        <xsl:value-of select="count(preceding::code[@code=current()/code/@code])+1"/>
      </xsl:if>
    </xsl:variable>
    <xsl:variable name="newId" select="concat($id, '.', count(preceding-sibling::*)+1)"/>
    <xsl:variable name="OBX">
      <OBX>
        <xsl:for-each select="value">
          <OBX.2>
            <xsl:choose>
              <xsl:when test="@xsi:type='CD' or @code">CWE</xsl:when>
              <xsl:when test="@xsi:type='INT'">NM</xsl:when>
              <xsl:otherwise>ST</xsl:otherwise>
            </xsl:choose>
          </OBX.2>
        </xsl:for-each>
        <OBX.3>
          <xsl:copy-of select="$code"/>
        </OBX.3>
        <xsl:if test="$subIdentifier!=''">
          <OBX.4>
            <xsl:value-of select="$subIdentifier"/>
          </OBX.4>
        </xsl:if>
        <xsl:for-each select="value[not(@nullFlavor='NI')]">
          <OBX.5>
            <xsl:choose>
              <xsl:when test="@xsi:type='CD' or @code">
                <xsl:call-template name="doCWE"/>
              </xsl:when>
              <xsl:when test="@value">
                <xsl:value-of select="@value"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="."/>
              </xsl:otherwise>
            </xsl:choose>
          </OBX.5>
        </xsl:for-each>
        <OBX.11>
          <xsl:choose>
            <xsl:when test="not(value)">X</xsl:when>
            <xsl:when test="statusCode/@code='new'">I</xsl:when>
            <xsl:when test="statusCode/@code='active'">P</xsl:when>
            <xsl:otherwise>F</xsl:otherwise>
          </xsl:choose>
        </OBX.11>
        <xsl:for-each select="effectiveTime/@value|effectiveTime/low/@value">
          <OBX.14>
            <xsl:value-of select="."/>
          </OBX.14>
        </xsl:for-each>
        <xsl:for-each select="performer/assignedEntity[id/@root or assignedPerson]">
          <OBX.16>
            <xsl:for-each select="id/@extension">
              <XCN.1>
                <xsl:value-of select="."/>
              </XCN.1>
            </xsl:for-each>
            <xsl:for-each select="assignedPerson/name[node()]">
              <XCN.2>
                <FN.1>
                  <xsl:value-of select="."/>
                </FN.1>
              </XCN.2>
              <!-- Future: Handle names that actually use name parts -->
            </xsl:for-each>
          </OBX.16>
        </xsl:for-each>
        <xsl:for-each select="methodCode">
          <OBX.17>
            <xsl:call-template name="doCWE"/>
          </OBX.17>
        </xsl:for-each>
        <xsl:for-each select="targetSiteCode">
          <OBX.20>
            <xsl:call-template name="doCWE"/>
          </OBX.20>
        </xsl:for-each>
        <xsl:for-each select="id[@root]">
          <OBX.21>
            <EI.1>
              <xsl:value-of select="@extension"/>
            </EI.1>
            <EI.3>
              <xsl:value-of select="@root"/>
            </EI.3>
            <EI.4>ISO</EI.4>
          </OBX.21>
          <xsl:if test="@moodCode='INT'">
            <OBX.22>
              <CWE.1>INT</CWE.1>
              <CWE.3>HL70725</CWE.3>
            </OBX.22>
          </xsl:if>
        </xsl:for-each>
      </OBX>
    </xsl:variable>
    <ORU_R01.ORDER_OBSERVATION>
      <xsl:call-template name="OBXtoOBR">
        <xsl:with-param name="OBX" select="$OBX"/>
        <xsl:with-param name="id" select="$newId"/>
        <xsl:with-param name="parentCode" select="$parentCode"/>
        <xsl:with-param name="parentSubIdentifier" select="$parentSubIdentifier"/>
      </xsl:call-template>
      <ORU_R01.G7O>
        <ORU_R01.OBSERVATION>
          <xsl:copy-of select="$OBX"/>
          <xsl:call-template name="titleAndTextToNTE"/>
        </ORU_R01.OBSERVATION>
      </ORU_R01.G7O>
    </ORU_R01.ORDER_OBSERVATION>
    <xsl:apply-templates select="entry/*|component/section|entryRelationship/*">
      <xsl:with-param name="parentCode" select="$code"/>
      <xsl:with-param name="parentSubIdentifier" select="$subIdentifier"/>
      <xsl:with-param name="id" select="$newId"/>
    </xsl:apply-templates>
  </xsl:template>
  <xsl:template name="OBXtoOBR">
    <xsl:param name="OBX"/>
    <xsl:param name="id"/>
    <xsl:param name="parentCode"/>
    <xsl:param name="parentSubIdentifier"/>
    <OBR>
      <OBR.3>
        <EI.1>
          <xsl:value-of select="$id"/>
        </EI.1>
      </OBR.3>
      <OBR.4>
        <xsl:copy-of select="$OBX/*/*[name(.)='OBX.3']/node()"/>
      </OBR.4>
      <xsl:for-each select="$OBX/*/*[name(.)='OBX.14']">
        <OBR.7>
          <xsl:copy-of select="node()"/>
        </OBR.7>
      </xsl:for-each>
      <OBR.25>
        <xsl:value-of select="$OBX/*/*[name(.)='OBX.11']"/>
      </OBR.25>
      <xsl:if test="count($parentCode)!=0">
        <OBR.26>
          <PRL.1>
            <xsl:copy-of select="$parentCode"/>
          </PRL.1>
          <xsl:if test="$parentSubIdentifier!=''">
            <PRL.2>
              <xsl:value-of select="$parentSubIdentifier"/>
            </PRL.2>
          </xsl:if>
        </OBR.26>
      </xsl:if>
    </OBR>
  </xsl:template>
  <xsl:template name="doCWE">
    <xsl:for-each select="@code">
      <CWE.1>
        <xsl:value-of select="."/>
      </CWE.1>
    </xsl:for-each>
    <xsl:for-each select="@displayName">
      <CWE.2>
        <xsl:value-of select="."/>
      </CWE.2>
    </xsl:for-each>
    <xsl:for-each select="@codeSystem">
      <CWE.3>
        <xsl:value-of select="."/>
      </CWE.3>
    </xsl:for-each>
    <xsl:for-each select="originalText">
      <CWE.9>
        <xsl:value-of select="."/>
      </CWE.9>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>
