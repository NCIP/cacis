<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:v3="urn:hl7-org:v3" xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:hl7="urn:hl7-org:functions"
                xmlns:DT="urn:hl7-org:v3/owl/DEFN/UV/DT/1.0#" exclude-result-prefixes="xsi xs v3 hl7">
    <xsl:variable name="dtURL" as="xs:string" select="'urn:hl7-org:v3/owl/DEFN/UV/DT/1.0#'"/>
    <xsl:template name="datatypeNamespace">
        <xsl:namespace name="DT" select="$dtURL"/>
    </xsl:template>
    <xsl:template name="datatypePropertyDefinitions" as="element()+">
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'AD.use')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'AD.useablePeriod')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'AD.parts')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ADXP.partType')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ANY.nullFlavor')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'BL.value')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'CD.code')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'CD.codeSystem')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'CD.codeSystemName')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'CD.codeSystemVersion')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'CD.displayName')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'CD.originalText')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'CD.qualifier')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'CD.translation')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'CR.inverted')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'CR.name')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'CR.value')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ED.representation')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ED.mediaType')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ED.language')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ED.compression')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'ED.integrityCheck')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ED.integrityCheckAlgorithm')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ED.reference')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ED.thumbnail')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'ED.value')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'EN.use')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'EN.validTime')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'EN.parts')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ENXP.partType')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'ENXP.qualifier')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'II.root')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'II.extension')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'II.assigningAuthorityName')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'II.displayable')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'INT.value')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'REAL.value')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'PQ.value')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'PQ.unit')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'PQ.translation')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'PQR.value')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'TEL.use')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'TEL.useablePeriod')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'TS.value')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'URL.scheme')}"/>
        <owl:DatatypeProperty rdf:about="{concat($dtURL, 'URL.address')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'IVL.low')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'IVL.lowClosed')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'IVL.high')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'IVL.highClosed')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'IVL.center')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'IVL.width')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'IVL.low')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'PIVL.alignment')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'PIVL.institutionSpecified')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'PIVL.phase')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'PIVL.period')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'EIVL.event')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'EIVL.offset')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'RTO.numerator')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'RTO.denominator')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'SLIST.origin')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'SLIST.scale')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'SLIST.digits')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'GLIST.period')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'GLIST.denominator')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'GLIST.head')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'GLIST.increment')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'HXIT.validTime')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'PPD.distributionType')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'PPD.standardDeviation')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'UVP.probability')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'QSU.terms')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'QSH.terms')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'QSI.terms')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'QSP.low')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'QSP.high')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'QSD.minuend')}"/>
        <owl:ObjectProperty rdf:about="{concat($dtURL, 'QSD.subtrahend')}"/>
    </xsl:template>
    <xsl:template mode="ANY" match="*" as="element()*">
        <xsl:apply-templates mode="_dispatch" select=".">
            <xsl:with-param name="type" select="@xsi:type"/>
        </xsl:apply-templates>
    </xsl:template>
    <xsl:template name="ANY.content" as="element()*">
        <xsl:for-each select="@nullFlavor">
            <DT:ANY.nullFlavor>
                <xsl:apply-templates mode="CS" select="."/>
            </DT:ANY.nullFlavor>
        </xsl:for-each>
        <xsl:if test="xsi:nil='true' and not(@nullFlavor)">
            <xsl:call-template name="CS">
                <xsl:with-param name="code" select="'NI'"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>
    <xsl:template mode="AD" match="*" as="element(DT:AD)">
        <DT:AD>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="@use">
                <DT:AD.use>
                    <rdf:Bag>
                        <xsl:for-each select="tokenize(., ' ')">
                            <rdf:li>
                                <xsl:call-template name="CS">
                                    <xsl:with-param name="code" select="."/>
                                </xsl:call-template>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </DT:AD.use>
            </xsl:for-each>
            <DT:AD.parts>
                <xsl:variable name="items" as="element(rdf:li)*">
                    <xsl:for-each select="node()[not(self::v3:useablePeriod)][self::* or normalize-space(.)!='']">
                        <rdf:li>
                            <xsl:apply-templates mode="ADXP" select="."/>
                        </rdf:li>
                    </xsl:for-each>
                </xsl:variable>
                <xsl:choose>
                    <xsl:when test="@isNotOrdered='true'">
                        <rdf:Bag>
                            <xsl:copy-of select="$items"/>
                        </rdf:Bag>
                    </xsl:when>
                    <xsl:otherwise>
                        <rdf:Seq>
                            <xsl:copy-of select="$items"/>
                        </rdf:Seq>
                    </xsl:otherwise>
                </xsl:choose>
            </DT:AD.parts>
            <xsl:for-each select="v3:useablePeriod[1]">
                <DT:AD.useablePeriod>
                    <xsl:apply-templates mode="GTS" select="."/>
                </DT:AD.useablePeriod>
            </xsl:for-each>
        </DT:AD>
    </xsl:template>
    <!--  <xsl:template mode="ADXP" match="text()[normalize-space(.)='']" as="empty-sequence()"/>-->
    <xsl:template mode="ADXP" match="node()" as="element(DT:ADXP)">
        <DT:ADXP>
            <xsl:call-template name="ED.content"/>
            <xsl:variable name="partType" as="xs:string?">
                <xsl:choose>
                    <xsl:when test="@partType">
                        <xsl:value-of select="@partType"/>
                    </xsl:when>
                    <xsl:when test="self::v3:delimiter">DEL</xsl:when>
                    <xsl:when test="self::v3:country">CNT</xsl:when>
                    <xsl:when test="self::v3:state">STA</xsl:when>
                    <xsl:when test="self::v3:county">CPA</xsl:when>
                    <xsl:when test="self::v3:city">CTY</xsl:when>
                    <xsl:when test="self::v3:postalCode">ZIP</xsl:when>
                    <xsl:when test="self::v3:streetAddressLine">SAL</xsl:when>
                    <xsl:when test="self::v3:houseNumber">BNR</xsl:when>
                    <xsl:when test="self::v3:houseNumberNumeric">BNN</xsl:when>
                    <xsl:when test="self::v3:direction">DIR</xsl:when>
                    <xsl:when test="self::v3:streetName">STR</xsl:when>
                    <xsl:when test="self::v3:streetNameBase">STB</xsl:when>
                    <xsl:when test="self::v3:streetNameType">STTYP</xsl:when>
                    <xsl:when test="self::v3:additionalLocator">ADL</xsl:when>
                    <xsl:when test="self::v3:unitID">UNID</xsl:when>
                    <xsl:when test="self::v3:unitType">UNIT</xsl:when>
                    <xsl:when test="self::v3:careOf">CAR</xsl:when>
                    <xsl:when test="self::v3:censusTract">CEN</xsl:when>
                    <xsl:when test="self::v3:deliveryAddressLine">DAL</xsl:when>
                    <xsl:when test="self::v3:deliveryInstallationType">DINST</xsl:when>
                    <xsl:when test="self::v3:deliveryInstallationArea">DINSTA</xsl:when>
                    <xsl:when test="self::v3:deliveryInstallationQualifier">DINSTQ</xsl:when>
                    <xsl:when test="self::v3:deliveryMode">DMOD</xsl:when>
                    <xsl:when test="self::v3:deliveryModeIdentifier">DMODID</xsl:when>
                    <xsl:when test="self::v3:buildingNumberSuffix">BNS</xsl:when>
                    <xsl:when test="self::v3:postBox">POB</xsl:when>
                    <xsl:when test="self::v3:precinct">PRE</xsl:when>
                </xsl:choose>
            </xsl:variable>
            <xsl:if test="$partType!=''">
                <DT:ADXP.partType>
                    <xsl:call-template name="CS">
                        <xsl:with-param name="code" select="$partType"/>
                    </xsl:call-template>
                </DT:ADXP.partType>
            </xsl:if>
        </DT:ADXP>
    </xsl:template>
    <xsl:template mode="BL" match="node()" as="element(DT:BL)">
        <DT:BL>
            <xsl:call-template name="ANY.content"/>
            <xsl:choose>
                <xsl:when test="@value">
                    <DT:BL.value>
                        <xsl:value-of select="@value"/>
                    </DT:BL.value>
                </xsl:when>
                <xsl:when test="not(@*)">
                    <DT:BL.value>
                        <xsl:value-of select="."/>
                    </DT:BL.value>
                </xsl:when>
            </xsl:choose>
        </DT:BL>
    </xsl:template>
    <xsl:template name="BL" as="element(DT:BL)">
        <xsl:param name="value" as="xs:boolean" required="yes"/>
        <DT:BL>
            <DT:BL.value>
                <xsl:value-of select="$value"/>
            </DT:BL.value>
        </DT:BL>
    </xsl:template>
    <xsl:template mode="CD" match="node()" as="element(DT:CD)">
        <DT:CD>
            <xsl:call-template name="CD.content"/>
        </DT:CD>
    </xsl:template>
    <xsl:template mode="CE" match="node()" as="element(DT:CE)">
        <DT:CE>
            <xsl:call-template name="CD.content"/>
        </DT:CE>
    </xsl:template>
    <xsl:template mode="CO" match="node()" as="element(DT:CO)">
        <DT:CO>
            <xsl:call-template name="CD.content"/>
        </DT:CO>
    </xsl:template>
    <xsl:template mode="CV" match="node()" as="element(DT:CV)">
        <DT:CV>
            <xsl:call-template name="CD.content"/>
        </DT:CV>
    </xsl:template>
    <xsl:template name="CD.content">
        <xsl:call-template name="ANY.content"/>
        <xsl:for-each select="@code">
            <DT:CD.code>
                <xsl:value-of select="."/>
            </DT:CD.code>
        </xsl:for-each>
        <xsl:for-each select="@codeSystem">
            <DT:CD.codeSystem>
                <xsl:value-of select="."/>
            </DT:CD.codeSystem>
        </xsl:for-each>
        <xsl:for-each select="@codeSystemName">
            <DT:CD.codeSystemName>
                <xsl:value-of select="."/>
            </DT:CD.codeSystemName>
        </xsl:for-each>
        <xsl:for-each select="@codeSystemVersion">
            <DT:CD.codeSystemVersion>
                <xsl:value-of select="."/>
            </DT:CD.codeSystemVersion>
        </xsl:for-each>
        <xsl:for-each select="@displayName">
            <DT:CD.displayName>
                <xsl:value-of select="."/>
            </DT:CD.displayName>
        </xsl:for-each>
        <xsl:for-each select="v3:originalText">
            <DT:CD.originalText>
                <xsl:apply-templates mode="ED" select="."/>
            </DT:CD.originalText>
        </xsl:for-each>
        <xsl:if test="v3:qualifier">
            <DT:CD.qualifier>
                <rdf:Bag>
                    <xsl:for-each select="v3:qualifier">
                        <rdf:li>
                            <xsl:apply-templates mode="CR" select="."/>
                        </rdf:li>
                    </xsl:for-each>
                </rdf:Bag>
            </DT:CD.qualifier>
        </xsl:if>
        <xsl:if test="v3:translation">
            <DT:CD.translation>
                <rdf:Bag>
                    <xsl:for-each select="v3:translation">
                        <rdf:li>
                            <xsl:apply-templates mode="CD" select="."/>
                        </rdf:li>
                    </xsl:for-each>
                </rdf:Bag>
            </DT:CD.translation>
        </xsl:if>
    </xsl:template>
    <xsl:template mode="CR" match="node()" as="element(DT:CR)">
        <DT:CR>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="@inverted">
                <DT:CR.inverted>
                    <xsl:value-of select="."/>
                </DT:CR.inverted>
            </xsl:for-each>
            <xsl:for-each select="v3:name">
                <DT:CR.name>
                    <xsl:apply-templates mode="CV" select="."/>
                </DT:CR.name>
            </xsl:for-each>
            <xsl:for-each select="v3:value">
                <DT:CR.value>
                    <xsl:apply-templates mode="CD" select="."/>
                </DT:CR.value>
            </xsl:for-each>
        </DT:CR>
    </xsl:template>
    <xsl:template mode="CS" match="node()" as="element(DT:CD)">
        <xsl:choose>
            <xsl:when test="self::*">
                <DT:CD>
                    <xsl:call-template name="ANY.content"/>
                    <xsl:for-each select="@code">
                        <DT:CD.code>
                            <xsl:value-of select="."/>
                        </DT:CD.code>
                    </xsl:for-each>
                </DT:CD>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="CS">
                    <xsl:with-param name="code" select="."/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="CS">
        <xsl:param name="code" as="xs:string" required="yes"/>
        <DT:CD>
            <!--      <DT:CD.code rdf:datatype="http://www.w3.org/2001/XMLSchema#string">-->
            <DT:CD.code>
                <xsl:choose>
                    <xsl:when test="$code">
                        <xsl:value-of select="$code"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="."/>
                    </xsl:otherwise>
                </xsl:choose>
            </DT:CD.code>
        </DT:CD>
    </xsl:template>
    <xsl:template mode="ED" match="*" as="element(DT:ED)">
        <DT:ED>
            <xsl:call-template name="ED.content"/>
        </DT:ED>
    </xsl:template>
    <xsl:template name="ED.content" as="element()*">
        <xsl:call-template name="ANY.content"/>
        <xsl:for-each select="@representation">
            <DT:ED.representation>
                <xsl:apply-templates mode="CS" select="."/>
            </DT:ED.representation>
        </xsl:for-each>
        <xsl:for-each select="@mediaType">
            <DT:ED.mediaType>
                <xsl:apply-templates mode="CS" select="."/>
            </DT:ED.mediaType>
        </xsl:for-each>
        <xsl:for-each select="@language">
            <DT:ED.language>
                <xsl:apply-templates mode="CS" select="."/>
            </DT:ED.language>
        </xsl:for-each>
        <xsl:for-each select="@compression">
            <DT:ED.compression>
                <xsl:apply-templates mode="CS" select="."/>
            </DT:ED.compression>
        </xsl:for-each>
        <xsl:for-each select="@integrityCheck">
            <DT:ED.integrityCheck>
                <xsl:value-of select="."/>
            </DT:ED.integrityCheck>
        </xsl:for-each>
        <xsl:for-each select="@integrityCheckAlgorithm">
            <DT:ED.integrityCheckAlgorithm>
                <xsl:apply-templates mode="CS" select="."/>
            </DT:ED.integrityCheckAlgorithm>
        </xsl:for-each>
        <xsl:for-each select="v3:reference[not(starts-with(@value, '#'))]">
            <DT:ED.reference>
                <xsl:apply-templates mode="TEL" select="."/>
            </DT:ED.reference>
        </xsl:for-each>
        <xsl:for-each select="v3:thumbnail">
            <DT:ED.thumbnail>
                <xsl:apply-templates mode="ED" select="."/>
            </DT:ED.thumbnail>
        </xsl:for-each>
        <xsl:if test="self::text() or node()[not(self::v3:reference or self::v3:thumbnail)][self::* or normalize-space(.)!='']">
            <DT:ED.value>
                <xsl:call-template name="ED.value.content">
                    <xsl:with-param name="content"
                                    select="self::text()|node()[not(self::v3:reference or self::v3:thumbnail)][self::* or normalize-space(.)!='']"/>
                </xsl:call-template>
            </DT:ED.value>
        </xsl:if>
        <xsl:if test="v3:reference[starts-with(@value, '#')]">
            <DT:ED.value>
                <xsl:call-template name="ED.value.content">
                    <xsl:with-param name="content"
                                    select="//*[@ID=substring-after(current()/v3:reference/@value, '#')]/node()"/>
                </xsl:call-template>
            </DT:ED.value>
        </xsl:if>
    </xsl:template>
    <xsl:template name="ED.value.content" as="node()*">
        <xsl:param name="content" as="node()*" required="yes"/>
        <xsl:choose>
            <xsl:when test="not($content[self::*])">
                <xsl:value-of select="hl7:trim($content)"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                <xsl:for-each select="$content">
                    <xsl:choose>
                        <xsl:when test="self::*">
                            <xsl:copy-of select="."/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="hl7:trim(.)"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:for-each>
                <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:function name="hl7:trim" as="xs:string?">
        <xsl:param name="string" as="xs:string"/>
        <xsl:choose>
            <xsl:when test="normalize-space($string)=''"/>
            <xsl:when test="normalize-space(substring($string, 1, 1))=''">
                <xsl:value-of select="hl7:trim(substring($string, 2))"/>
            </xsl:when>
            <xsl:when test="normalize-space(substring($string, string-length($string)))=''">
                <xsl:value-of select="hl7:trim(substring($string, 1, string-length($string) - 1))"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$string"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    <xsl:template mode="EN" match="*" as="element(DT:EN)">
        <DT:EN>
            <xsl:call-template name="EN.content"/>
        </DT:EN>
    </xsl:template>
    <xsl:template mode="ON" match="*" as="element(DT:ON)">
        <DT:ON>
            <xsl:call-template name="EN.content"/>
        </DT:ON>
    </xsl:template>
    <xsl:template mode="PN" match="*" as="element(DT:PN)">
        <DT:PN>
            <xsl:call-template name="EN.content"/>
        </DT:PN>
    </xsl:template>
    <xsl:template name="EN.content" as="element()*">
        <xsl:call-template name="ANY.content"/>
        <xsl:for-each select="@use">
            <DT:EN.use>
                <rdf:Bag>
                    <xsl:for-each select="tokenize(., ' ')">
                        <rdf:li>
                            <xsl:call-template name="CS">
                                <xsl:with-param name="code" select="."/>
                            </xsl:call-template>
                        </rdf:li>
                    </xsl:for-each>
                </rdf:Bag>
            </DT:EN.use>
        </xsl:for-each>
        <DT:EN.parts>
            <rdf:Seq>
                <xsl:for-each select="node()[not(self::v3:validTime)][self::* or normalize-space(.)!='']">
                    <rdf:li>
                        <xsl:apply-templates mode="ENXP" select="."/>
                    </rdf:li>
                </xsl:for-each>
            </rdf:Seq>
        </DT:EN.parts>
        <xsl:for-each select="v3:validTime">
            <DT:EN.validTime>
                <xsl:apply-templates mode="IVL_TS" select="."/>
            </DT:EN.validTime>
        </xsl:for-each>
    </xsl:template>
    <xsl:template mode="ENXP" match="node()" as="element(DT:ENXP)">
        <DT:ENXP>
            <xsl:call-template name="ED.content"/>
            <xsl:variable name="partType" as="xs:string?">
                <xsl:choose>
                    <xsl:when test="@partType">
                        <xsl:value-of select="@partType"/>
                    </xsl:when>
                    <xsl:when test="self::v3:delimiter">DEL</xsl:when>
                    <xsl:when test="self::v3:family">FAM</xsl:when>
                    <xsl:when test="self::v3:given">GIV</xsl:when>
                    <xsl:when test="self::v3:prefix">PFX</xsl:when>
                    <xsl:when test="self::v3:suffix">SFX</xsl:when>
                </xsl:choose>
            </xsl:variable>
            <xsl:if test="$partType!=''">
                <DT:ENXP.partType>
                    <xsl:call-template name="CS">
                        <xsl:with-param name="code" select="$partType"/>
                    </xsl:call-template>
                </DT:ENXP.partType>
            </xsl:if>
            <xsl:for-each select="@qualifier">
                <DT:ENXP.qualifier>
                    <rdf:Bag>
                        <xsl:for-each select="tokenize(., ' ')">
                            <rdf:li>
                                <xsl:call-template name="CS">
                                    <xsl:with-param name="code" select="."/>
                                </xsl:call-template>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </DT:ENXP.qualifier>
            </xsl:for-each>
        </DT:ENXP>
    </xsl:template>
    <xsl:template mode="II" match="node()" as="element(DT:II)">
        <DT:II>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="@root">
                <DT:II.root>
                    <xsl:value-of select="."/>
                </DT:II.root>
            </xsl:for-each>
            <xsl:for-each select="@extension">
                <DT:II.extension>
                    <xsl:value-of select="."/>
                </DT:II.extension>
            </xsl:for-each>
            <xsl:for-each select="@assigningAuthorityName">
                <DT:II.assigningAuthorityName>
                    <xsl:call-template name="ST">
                        <xsl:with-param name="value" select="."/>
                    </xsl:call-template>
                </DT:II.assigningAuthorityName>
            </xsl:for-each>
            <xsl:for-each select="@displayable">
                <DT:II.displayable>
                    <xsl:call-template name="BL">
                        <xsl:with-param name="value" select="."/>
                    </xsl:call-template>
                </DT:II.displayable>
            </xsl:for-each>
        </DT:II>
    </xsl:template>
    <xsl:template name="INT" mode="INT" match="node()" as="element(DT:INT)">
        <xsl:param name="value" as="xs:string" required="no" select="''"/>
        <DT:INT>
            <xsl:if test="$value='' and @*">
                <xsl:call-template name="ANY.content"/>
            </xsl:if>
            <xsl:choose>
                <xsl:when test="$value!=''">
                    <DT:INT.value>
                        <xsl:value-of select="$value"/>
                    </DT:INT.value>
                </xsl:when>
                <xsl:when test="@*">
                    <xsl:for-each select="@value">
                        <DT:INT.value>
                            <xsl:value-of select="."/>
                        </DT:INT.value>
                    </xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                    <DT:INT.value>
                        <xsl:value-of select="."/>
                    </DT:INT.value>
                </xsl:otherwise>
            </xsl:choose>
        </DT:INT>
    </xsl:template>
    <xsl:template mode="MO" match="node()" as="element(DT:MO)">
        <DT:MO>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="@value">
                <DT:MO.value>
                    <xsl:apply-templates mode="REAL" select="."/>
                </DT:MO.value>
            </xsl:for-each>
            <xsl:for-each select="@currency">
                <DT:MO.currency>
                    <xsl:apply-templates mode="CD" select="."/>
                </DT:MO.currency>
            </xsl:for-each>
        </DT:MO>
    </xsl:template>
    <xsl:template mode="PQ" match="node()" as="element(DT:PQ)">
        <DT:PQ>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="@value">
                <DT:PQ.value>
                    <xsl:apply-templates mode="REAL" select="."/>
                </DT:PQ.value>
            </xsl:for-each>
            <xsl:for-each select="@unit">
                <DT:PQ.unit>
                    <xsl:apply-templates mode="CS" select="."/>
                </DT:PQ.unit>
            </xsl:for-each>
            <xsl:if test="v3:translation">
                <DT:PQ.translation>
                    <rdf:Bag>
                        <xsl:for-each select="v3:translation">
                            <rdf:li>
                                <xsl:apply-templates mode="PQR" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </DT:PQ.translation>
            </xsl:if>
        </DT:PQ>
    </xsl:template>
    <xsl:template mode="PQR" match="node()" as="element(DT:PQR)">
        <DT:PQR>
            <xsl:call-template name="CD.content"/>
            <xsl:for-each select="@value">
                <DT:PQR.value>
                    <xsl:apply-templates mode="REAL" select="."/>
                </DT:PQR.value>
            </xsl:for-each>
        </DT:PQR>
    </xsl:template>
    <xsl:template mode="REAL" match="node()" as="element(DT:REAL)">
        <DT:REAL>
            <xsl:if test="@*">
                <xsl:call-template name="ANY.content"/>
            </xsl:if>
            <xsl:choose>
                <xsl:when test="@*">
                    <xsl:for-each select="@value">
                        <DT:REAL.value>
                            <xsl:value-of select="."/>
                        </DT:REAL.value>
                    </xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                    <DT:REAL.value>
                        <xsl:value-of select="."/>
                    </DT:REAL.value>
                </xsl:otherwise>
            </xsl:choose>
        </DT:REAL>
    </xsl:template>
    <xsl:template mode="SC" match="*" as="element(DT:SC)">
        <DT:SC>
            <xsl:call-template name="ED.content"/>
            <xsl:call-template name="CD.content"/>
        </DT:SC>
    </xsl:template>
    <xsl:template mode="ST" match="node()" as="element(DT:ST)">
        <DT:ST>
            <xsl:call-template name="ED.content"/>
        </DT:ST>
    </xsl:template>
    <xsl:template name="ST" as="element(DT:ST)">
        <xsl:param name="value" as="xs:string" required="yes"/>
        <DT:ST>
            <DT:ED.value>
                <xsl:value-of select="$value"/>
            </DT:ED.value>
        </DT:ST>
    </xsl:template>
    <xsl:template mode="TEL" match="*" as="element(DT:TEL)">
        <DT:TEL>
            <xsl:call-template name="ANY.content"/>
            <xsl:if test="@value">
                <xsl:choose>
                    <xsl:when test="contains(@value, ':')">
                        <DT:URL.scheme>
                            <xsl:value-of select="substring-before(@value, ':')"/>
                        </DT:URL.scheme>
                        <DT:URL.address>
                            <xsl:value-of select="substring-after(@value, ':')"/>
                        </DT:URL.address>
                    </xsl:when>
                    <xsl:otherwise>
                        <DT:URL.scheme>file</DT:URL.scheme>
                        <DT:URL.address>
                            <xsl:value-of select="@value"/>
                        </DT:URL.address>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
            <xsl:for-each select="@use">
                <DT:TEL.use>
                    <rdf:Bag>
                        <xsl:for-each select="tokenize(., ' ')">
                            <rdf:li>
                                <xsl:call-template name="CS">
                                    <xsl:with-param name="code" select="."/>
                                </xsl:call-template>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </DT:TEL.use>
            </xsl:for-each>
            <xsl:for-each select="v3:useablePeriod[1]">
                <DT:TEL.useablePeriod>
                    <xsl:apply-templates mode="GTS" select="."/>
                </DT:TEL.useablePeriod>
            </xsl:for-each>
        </DT:TEL>
    </xsl:template>
    <xsl:template mode="TS" match="node()" as="element(DT:TS)">
        <DT:TS>
            <!-- Todo: See if we can make this easier to query -->
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="@value">
                <DT:TS.value>
                    <xsl:value-of select="."/>
                </DT:TS.value>
            </xsl:for-each>
        </DT:TS>
    </xsl:template>
    <!--
    - Parameterized types
    -->
    <xsl:template mode="IVL_TS" match="node()" as="element(DT:IVL)">
        <xsl:call-template name="IVL">
            <xsl:with-param name="type" select="'TS'"/>
            <xsl:with-param name="widthType" select="'PQ'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template mode="IVL_PQ" match="node()" as="element(DT:IVL)">
        <xsl:call-template name="IVL">
            <xsl:with-param name="type" select="'PQ'"/>
            <xsl:with-param name="widthType" select="'PQ'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template mode="IVL_INT" match="node()" as="element(DT:IVL)">
        <xsl:call-template name="IVL">
            <xsl:with-param name="type" select="'INT'"/>
            <xsl:with-param name="widthType" select="'INT'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template mode="IVL_REAL" match="node()" as="element(DT:IVL)">
        <xsl:call-template name="IVL">
            <xsl:with-param name="type" select="'REAL'"/>
            <xsl:with-param name="widthType" select="'REAL'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template mode="IVL_PPD_TS" match="node()" as="element(DT:IVL)">
        <xsl:call-template name="IVL">
            <xsl:with-param name="type" select="'PPD_TS'"/>
            <xsl:with-param name="widthType" select="'PPD_PQ'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template mode="IVL_PPD_PQ" match="node()" as="element(DT:IVL)">
        <xsl:call-template name="IVL">
            <xsl:with-param name="type" select="'PPD_PQ'"/>
            <xsl:with-param name="widthType" select="'PPD_PQ'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template name="IVL" as="element(DT:IVL)">
        <xsl:param name="type" as="xs:string" required="yes"/>
        <xsl:param name="widthType" as="xs:string" required="yes"/>
        <DT:IVL>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="v3:low|self::*[@value]">
                <DT:IVL.low>
                    <xsl:apply-templates mode="_dispatch" select=".">
                        <xsl:with-param name="type" select="$type"/>
                    </xsl:apply-templates>
                </DT:IVL.low>
                <DT:IVL.lowClosed>
                    <xsl:call-template name="BL">
                        <xsl:with-param name="value" select="if (@inclusive) then @inclusive else true()"/>
                    </xsl:call-template>
                </DT:IVL.lowClosed>
            </xsl:for-each>
            <xsl:for-each select="v3:high|self::*[@value]">
                <DT:IVL.high>
                    <xsl:apply-templates mode="_dispatch" select=".">
                        <xsl:with-param name="type" select="$type"/>
                    </xsl:apply-templates>
                </DT:IVL.high>
                <DT:IVL.highClosed>
                    <xsl:call-template name="BL">
                        <xsl:with-param name="value" select="if (@inclusive) then @inclusive else true()"/>
                    </xsl:call-template>
                </DT:IVL.highClosed>
            </xsl:for-each>
            <xsl:for-each select="v3:center">
                <DT:IVL.center>
                    <xsl:apply-templates mode="_dispatch" select=".">
                        <xsl:with-param name="type" select="$type"/>
                    </xsl:apply-templates>
                </DT:IVL.center>
            </xsl:for-each>
            <xsl:for-each select="v3:width">
                <DT:IVL.width>
                    <xsl:apply-templates mode="_dispatch" select=".">
                        <xsl:with-param name="type" select="$type"/>
                    </xsl:apply-templates>
                </DT:IVL.width>
            </xsl:for-each>
        </DT:IVL>
    </xsl:template>
    <xsl:template mode="PIVL_TS" match="*" as="element(DT:PIVL)">
        <DT:PIVL>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="@alignment">
                <DT:PIVL.alignment>
                    <xsl:apply-templates mode="CS" select="."/>
                </DT:PIVL.alignment>
            </xsl:for-each>
            <xsl:for-each select="@institutionSpecified">
                <DT:PIVL.institutionSpecified>
                    <xsl:apply-templates mode="BL" select="."/>
                </DT:PIVL.institutionSpecified>
            </xsl:for-each>
            <xsl:for-each select="v3:phase">
                <DT:PIVL.phase>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </DT:PIVL.phase>
            </xsl:for-each>
            <xsl:for-each select="v3:period">
                <DT:PIVL.period>
                    <xsl:apply-templates mode="PQ" select="."/>
                </DT:PIVL.period>
            </xsl:for-each>
        </DT:PIVL>
    </xsl:template>
    <xsl:template mode="PIVL_PPD_TS" match="*" as="element(DT:PIVL)">
        <DT:PIVL>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="@alignment">
                <DT:PIVL.alignment>
                    <xsl:apply-templates mode="CS" select="."/>
                </DT:PIVL.alignment>
            </xsl:for-each>
            <xsl:for-each select="@institutionSpecified">
                <DT:PIVL.institutionSpecified>
                    <xsl:apply-templates mode="BL" select="."/>
                </DT:PIVL.institutionSpecified>
            </xsl:for-each>
            <xsl:for-each select="v3:phase">
                <DT:PIVL.phase>
                    <xsl:apply-templates mode="IVL_PPD_TS" select="."/>
                </DT:PIVL.phase>
            </xsl:for-each>
            <xsl:for-each select="v3:period">
                <DT:PIVL.period>
                    <xsl:apply-templates mode="PPD_PQ" select="."/>
                </DT:PIVL.period>
            </xsl:for-each>
        </DT:PIVL>
    </xsl:template>
    <xsl:template mode="EIVL_TS" match="*" as="element(DT:EIVL)">
        <DT:EIVL>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="v3:event">
                <DT:EIVL.event>
                    <xsl:apply-templates mode="CE" select="."/>
                </DT:EIVL.event>
            </xsl:for-each>
            <xsl:for-each select="v3:offset">
                <DT:EIVL.offset>
                    <xsl:apply-templates mode="PQ" select="."/>
                </DT:EIVL.offset>
            </xsl:for-each>
        </DT:EIVL>
    </xsl:template>
    <xsl:template mode="EIVL_PPD_TS" match="*" as="element(DT:EIVL)">
        <DT:EIVL>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="v3:event">
                <DT:EIVL.event>
                    <xsl:apply-templates mode="CE" select="."/>
                </DT:EIVL.event>
            </xsl:for-each>
            <xsl:for-each select="v3:offset">
                <DT:EIVL.offset>
                    <xsl:apply-templates mode="PPD_PQ" select="."/>
                </DT:EIVL.offset>
            </xsl:for-each>
        </DT:EIVL>
    </xsl:template>
    <xsl:template mode="RTO_PQ_PQ" match="node()" as="element(DT:RTO)">
        <xsl:call-template name="RTO">
            <xsl:with-param name="numerator" select="'PQ'"/>
            <xsl:with-param name="denominator" select="'PQ'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template mode="RTO_MO_PQ" match="node()" as="element(DT:RTO)">
        <xsl:call-template name="RTO">
            <xsl:with-param name="numerator" select="'MO'"/>
            <xsl:with-param name="denominator" select="'PQ'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template name="RTO" as="element(DT:RTO)">
        <xsl:param name="numerator" as="xs:string" required="yes"/>
        <xsl:param name="denominator" as="xs:string" required="yes"/>
        <DT:RTO>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="v3:numerator">
                <DT:RTO.numerator>
                    <xsl:apply-templates mode="_dispatch" select=".">
                        <xsl:with-param name="type" select="$numerator"/>
                    </xsl:apply-templates>
                </DT:RTO.numerator>
            </xsl:for-each>
            <xsl:for-each select="v3:denominator">
                <DT:RTO.denominator>
                    <xsl:apply-templates mode="_dispatch" select=".">
                        <xsl:with-param name="type" select="$denominator"/>
                    </xsl:apply-templates>
                </DT:RTO.denominator>
            </xsl:for-each>
        </DT:RTO>
    </xsl:template>
    <xsl:template mode="SLIST_TS" match="node()" as="element(DT:SLIST)">
        <xsl:call-template name="SLIST">
            <xsl:with-param name="type" select="'TS'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template name="SLIST" as="element(DT:SLIST)">
        <xsl:param name="type" as="xs:string" required="yes"/>
        <DT:SLIST>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="v3:origin">
                <DT:SLIST.origin>
                    <xsl:apply-templates mode="_dispatch" select=".">
                        <xsl:with-param name="type" select="$type"/>
                    </xsl:apply-templates>
                </DT:SLIST.origin>
            </xsl:for-each>
            <xsl:for-each select="v3:scale">
                <DT:SLIST.scale>
                    <xsl:apply-templates mode="PQ" select="."/>
                </DT:SLIST.scale>
            </xsl:for-each>
            <xsl:for-each select="v3:digits">
                <DT:SLIST.digits>
                    <rdf:Seq>
                        <xsl:for-each select="tokenize(., ' ')">
                            <rdf:li>
                                <xsl:call-template name="INT">
                                    <xsl:with-param name="value" select="."/>
                                </xsl:call-template>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Seq>
                </DT:SLIST.digits>
            </xsl:for-each>
        </DT:SLIST>
    </xsl:template>
    <xsl:template mode="GLIST_TS" match="node()" as="element(DT:GLIST)">
        <xsl:call-template name="GLIST">
            <xsl:with-param name="type" select="'TS'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template mode="GLIST_PQ" match="node()" as="element(DT:GLIST)">
        <xsl:call-template name="GLIST">
            <xsl:with-param name="type" select="'PQ'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template name="GLIST" as="element(DT:GLIST)">
        <xsl:param name="type" as="xs:string" required="yes"/>
        <DT:GLIST>
            <xsl:call-template name="ANY.content"/>
            <xsl:for-each select="@period">
                <DT:GLIST.period>
                    <xsl:apply-templates mode="INT" select="."/>
                </DT:GLIST.period>
            </xsl:for-each>
            <xsl:for-each select="@denominator">
                <DT:GLIST.denominator>
                    <xsl:apply-templates mode="INT" select="."/>
                </DT:GLIST.denominator>
            </xsl:for-each>
            <xsl:for-each select="v3:head">
                <DT:GLIST.head>
                    <xsl:apply-templates mode="_dispatch" select=".">
                        <xsl:with-param name="type" select="$type"/>
                    </xsl:apply-templates>
                </DT:GLIST.head>
            </xsl:for-each>
            <xsl:for-each select="v3:increment">
                <DT:GLIST.head>
                    <xsl:apply-templates mode="PQ" select="."/>
                </DT:GLIST.head>
            </xsl:for-each>
        </DT:GLIST>
    </xsl:template>
    <xsl:template mode="HXIT_PQ" match="node()" as="element()">
        <xsl:call-template name="HXIT">
            <xsl:with-param name="type" select="'PQ'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template mode="HXIT_CE" match="node()" as="element()">
        <xsl:call-template name="HXIT">
            <xsl:with-param name="type" select="'CE'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template name="HXIT" as="element()">
        <xsl:param name="type" as="xs:string" required="yes"/>
        <xsl:variable name="baseType" as="element()">
            <xsl:apply-templates mode="_dispatch" select=".">
                <xsl:with-param name="type" select="$type"/>
            </xsl:apply-templates>
        </xsl:variable>
        <xsl:variable name="histAtts" as="element(DT:HXIT.validTime)?">
            <xsl:for-each select="v3:validTime">
                <DT:HXIT.validTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </DT:HXIT.validTime>
            </xsl:for-each>
        </xsl:variable>
        <xsl:for-each select="$baseType">
            <xsl:copy>
                <xsl:copy-of select="*"/>
                <xsl:copy-of select="$histAtts"/>
            </xsl:copy>
        </xsl:for-each>
    </xsl:template>
    <xsl:template mode="PPD_PQ" match="node()" as="element()">
        <xsl:call-template name="PPD">
            <xsl:with-param name="type" select="'PQ'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template mode="PPD_TS" match="node()" as="element()">
        <xsl:call-template name="PPD">
            <xsl:with-param name="type" select="'TS'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template name="PPD" as="element()">
        <xsl:param name="type" as="xs:string" required="yes"/>
        <xsl:variable name="baseType" as="element()">
            <xsl:apply-templates mode="_dispatch" select=".">
                <xsl:with-param name="type" select="$type"/>
            </xsl:apply-templates>
        </xsl:variable>
        <xsl:variable name="ppdAtts" as="element()*">
            <xsl:for-each select="@distributionType">
                <DT:PPD.distributionType>
                    <xsl:apply-templates mode="CS" select="."/>
                </DT:PPD.distributionType>
            </xsl:for-each>
            <xsl:for-each select="v3:standardDeviation">
                <DT:PPD.standardDeviation>
                    <xsl:apply-templates mode="PQ" select="."/>
                </DT:PPD.standardDeviation>
            </xsl:for-each>
        </xsl:variable>
        <xsl:for-each select="$baseType">
            <xsl:copy>
                <xsl:copy-of select="*"/>
                <xsl:copy-of select="$ppdAtts"/>
            </xsl:copy>
        </xsl:for-each>
    </xsl:template>
    <xsl:template mode="UVP_TS" match="node()" as="element()">
        <xsl:call-template name="UVP">
            <xsl:with-param name="type" select="'TS'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template name="UVP" as="element()">
        <xsl:param name="type" as="xs:string" required="yes"/>
        <xsl:variable name="baseType" as="element()">
            <xsl:apply-templates mode="_dispatch" select=".">
                <xsl:with-param name="type" select="$type"/>
            </xsl:apply-templates>
        </xsl:variable>
        <xsl:variable name="uvpAtts" as="element()*">
            <xsl:for-each select="@probability">
                <DT:UVP.probability>
                    <xsl:apply-templates mode="CS" select="."/>
                </DT:UVP.probability>
            </xsl:for-each>
        </xsl:variable>
        <xsl:for-each select="$baseType">
            <xsl:copy>
                <xsl:copy-of select="*"/>
                <xsl:copy-of select="$uvpAtts"/>
            </xsl:copy>
        </xsl:for-each>
    </xsl:template>
    <xsl:template mode="GTS" match="*" as="element()?">
        <xsl:if test="not(preceding-sibling::*[@name=current()/@name])">
            <xsl:call-template name="GTS.content"/>
        </xsl:if>
    </xsl:template>
    <xsl:template mode="SXPR_TS" match="*" as="element()?">
        <xsl:apply-templates mode="GTS.content" select="v3:comp[1]"/>
    </xsl:template>
    <xsl:template mode="GTS.content" name="GTS.content" match="*" as="element()?">
        <xsl:choose>
            <xsl:when test="not(following-sibling::*[name(.)=name(current())])">
                <xsl:if test="not(@xsi:type)">
                    <xsl:message>
                        <xsl:text>No xsi:type defined for GTS reference</xsl:text>
                        <xsl:copy-of select="."/>
                    </xsl:message>
                </xsl:if>
                <xsl:apply-templates mode="_dispatch" select=".">
                    <xsl:with-param name="type" select="@xsi:type"/>
                </xsl:apply-templates>
            </xsl:when>
            <xsl:when test="@operator='E'">
                <DT:QSD>
                    <xsl:if test="v3:comp">
                        <xsl:call-template name="ANY.content"/>
                    </xsl:if>
                    <DT:QSD.minuend>
                        <xsl:apply-templates mode="_dispatch" select=".">
                            <xsl:with-param name="type" select="@xsi:type"/>
                        </xsl:apply-templates>
                    </DT:QSD.minuend>
                    <DT:QSD.subtrahend>
                        <xsl:apply-templates mode="GTS.content" select="following-sibling::*[1]"/>
                    </DT:QSD.subtrahend>
                </DT:QSD>
            </xsl:when>
            <xsl:when test="@operator='I'">
                <DT:QSU>
                    <xsl:if test="v3:comp">
                        <xsl:call-template name="ANY.content"/>
                    </xsl:if>
                    <DT:QSU.terms>
                        <rdf:Bag>
                            <rdf:li>
                                <xsl:apply-templates mode="_dispatch" select=".">
                                    <xsl:with-param name="type" select="@xsi:type"/>
                                </xsl:apply-templates>
                            </rdf:li>
                            <rdf:li>
                                <xsl:apply-templates mode="GTS.content" select="following-sibling::*[1]"/>
                            </rdf:li>
                        </rdf:Bag>
                    </DT:QSU.terms>
                </DT:QSU>
            </xsl:when>
            <xsl:when test="@operator='A'">
                <DT:QSI>
                    <xsl:if test="v3:comp">
                        <xsl:call-template name="ANY.content"/>
                    </xsl:if>
                    <DT:QSI.terms>
                        <rdf:Bag>
                            <rdf:li>
                                <xsl:apply-templates mode="_dispatch" select=".">
                                    <xsl:with-param name="type" select="@xsi:type"/>
                                </xsl:apply-templates>
                            </rdf:li>
                            <rdf:li>
                                <xsl:apply-templates mode="GTS.content" select="following-sibling::*[1]"/>
                            </rdf:li>
                        </rdf:Bag>
                    </DT:QSI.terms>
                </DT:QSI>
            </xsl:when>
            <xsl:when test="@operator='H'">
                <DT:QSH>
                    <xsl:if test="v3:comp">
                        <xsl:call-template name="ANY.content"/>
                    </xsl:if>
                    <DT:QSH.terms>
                        <rdf:Bag>
                            <rdf:li>
                                <xsl:apply-templates mode="_dispatch" select=".">
                                    <xsl:with-param name="type" select="@xsi:type"/>
                                </xsl:apply-templates>
                            </rdf:li>
                            <rdf:li>
                                <xsl:apply-templates mode="GTS.content" select="following-sibling::*[1]"/>
                            </rdf:li>
                        </rdf:Bag>
                    </DT:QSH.terms>
                </DT:QSH>
            </xsl:when>
            <xsl:when test="@operator='P'">
                <DT:QSP>
                    <xsl:if test="v3:comp">
                        <xsl:call-template name="ANY.content"/>
                    </xsl:if>
                    <DT:QSP.low>
                        <xsl:apply-templates mode="_dispatch" select=".">
                            <xsl:with-param name="type" select="@xsi:type"/>
                        </xsl:apply-templates>
                    </DT:QSP.low>
                    <DT:QSP.high>
                        <xsl:apply-templates mode="GTS.content" select="following-sibling::*[1]"/>
                    </DT:QSP.high>
                </DT:QSP>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template mode="_dispatch" match="node()" as="element()">
        <xsl:param name="type" as="xs:string" required="yes"/>
        <xsl:choose>
            <xsl:when test="$type='AD'">
                <xsl:apply-templates mode="AD" select="."/>
            </xsl:when>
            <xsl:when test="$type='ADXP'">
                <xsl:apply-templates mode="ADXP" select="."/>
            </xsl:when>
            <xsl:when test="$type='BL'">
                <xsl:apply-templates mode="BL" select="."/>
            </xsl:when>
            <xsl:when test="$type='CD'">
                <xsl:apply-templates mode="CD" select="."/>
            </xsl:when>
            <xsl:when test="$type='CE'">
                <xsl:apply-templates mode="CE" select="."/>
            </xsl:when>
            <xsl:when test="$type='CO'">
                <xsl:apply-templates mode="CO" select="."/>
            </xsl:when>
            <xsl:when test="$type='CV'">
                <xsl:apply-templates mode="CV" select="."/>
            </xsl:when>
            <xsl:when test="$type='CR'">
                <xsl:apply-templates mode="CR" select="."/>
            </xsl:when>
            <xsl:when test="$type='CS'">
                <xsl:apply-templates mode="CS" select="."/>
            </xsl:when>
            <xsl:when test="$type='ED'">
                <xsl:apply-templates mode="ED" select="."/>
            </xsl:when>
            <xsl:when test="$type='EN'">
                <xsl:apply-templates mode="EN" select="."/>
            </xsl:when>
            <xsl:when test="$type='ON'">
                <xsl:apply-templates mode="ON" select="."/>
            </xsl:when>
            <xsl:when test="$type='PN'">
                <xsl:apply-templates mode="PN" select="."/>
            </xsl:when>
            <xsl:when test="$type='ENXP'">
                <xsl:apply-templates mode="ENXP" select="."/>
            </xsl:when>
            <xsl:when test="$type='II'">
                <xsl:apply-templates mode="II" select="."/>
            </xsl:when>
            <xsl:when test="$type='INT'">
                <xsl:apply-templates mode="INT" select="."/>
            </xsl:when>
            <xsl:when test="$type='MO'">
                <xsl:apply-templates mode="MO" select="."/>
            </xsl:when>
            <xsl:when test="$type='PQ'">
                <xsl:apply-templates mode="PQ" select="."/>
            </xsl:when>
            <xsl:when test="$type='PQR'">
                <xsl:apply-templates mode="PQR" select="."/>
            </xsl:when>
            <xsl:when test="$type='REAL'">
                <xsl:apply-templates mode="REAL" select="."/>
            </xsl:when>
            <xsl:when test="$type='SC'">
                <xsl:apply-templates mode="SC" select="."/>
            </xsl:when>
            <xsl:when test="$type='ST'">
                <xsl:apply-templates mode="ST" select="."/>
            </xsl:when>
            <xsl:when test="$type='TEL'">
                <xsl:apply-templates mode="TEL" select="."/>
            </xsl:when>
            <xsl:when test="$type='TS'">
                <xsl:apply-templates mode="TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='IVL_TS'">
                <xsl:apply-templates mode="IVL_TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='IVL_PQ'">
                <xsl:apply-templates mode="IVL_PQ" select="."/>
            </xsl:when>
            <xsl:when test="$type='IVL_INT'">
                <xsl:apply-templates mode="IVL_INT" select="."/>
            </xsl:when>
            <xsl:when test="$type='IVL_REAL'">
                <xsl:apply-templates mode="IVL_REAL" select="."/>
            </xsl:when>
            <xsl:when test="$type='IVL_PPD_TS'">
                <xsl:apply-templates mode="IVL_PPD_TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='IVL_PPD_PQ'">
                <xsl:apply-templates mode="IVL_PPD_PQ" select="."/>
            </xsl:when>
            <xsl:when test="$type='PIVL_TS'">
                <xsl:apply-templates mode="PIVL_TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='PIVL_PPD_TS'">
                <xsl:apply-templates mode="PIVL_TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='EIVL_TS'">
                <xsl:apply-templates mode="EIVL_TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='EIVL_PPD_TS'">
                <xsl:apply-templates mode="EIVL_PPD_TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='RTO_PQ_PQ'">
                <xsl:apply-templates mode="RTO_PQ_PQ" select="."/>
            </xsl:when>
            <xsl:when test="$type='RTO_MO_PQ'">
                <xsl:apply-templates mode="RTO_MO_PQ" select="."/>
            </xsl:when>
            <xsl:when test="$type='SLIST_TS'">
                <xsl:apply-templates mode="SLIST_TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='GLIST_TS'">
                <xsl:apply-templates mode="GLIST_TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='GLIST_PQ'">
                <xsl:apply-templates mode="GLIST_PQ" select="."/>
            </xsl:when>
            <xsl:when test="$type='HXIT_PQ'">
                <xsl:apply-templates mode="HXIT_PQ" select="."/>
            </xsl:when>
            <xsl:when test="$type='HXIT_CE'">
                <xsl:apply-templates mode="HXIT_CE" select="."/>
            </xsl:when>
            <xsl:when test="$type='PPD_PQ'">
                <xsl:apply-templates mode="PPD_PQ" select="."/>
            </xsl:when>
            <xsl:when test="$type='PPD_TS'">
                <xsl:apply-templates mode="PPD_TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='UVP_TS'">
                <xsl:apply-templates mode="UVP_TS" select="."/>
            </xsl:when>
            <xsl:when test="$type='GTS'">
                <xsl:apply-templates mode="GTS" select="."/>
            </xsl:when>
            <xsl:when test="$type='SXPR_TS'">
                <xsl:apply-templates mode="SXPR_TS" select="."/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:message terminate="yes" select="concat('Unknown type - can''t dispatch: ', $type)"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="/">
        <rdf:RDF xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#" xmlns:_XML="http://www.w3.org/XML/1998/namespace#"
                 xmlns:RIM="urn:hl7-org:v3/owl/DEFN/UV/RIM/207#" xmlns:DT="urn:hl7-org:v3/owl/DEFN/UV/DT/1.0#">
            <owl:Ontology rdf:about="urn:hl7-org:v3/owl"/>
            <owl:ObjectProperty rdf:about="http://www.w3.org/XML/1998/namespace#id"/>
            <xsl:call-template name="datatypePropertyDefinitions"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/RIM/207#InfrastructureRoot.nullFlavor"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/RIM/207#InfrastructureRoot.typeId"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/RIM/207#InfrastructureRoot.realmCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/RIM/207#InfrastructureRoot.templateId"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.title"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.effectiveTime"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.confidentialityCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.languageCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.setId"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.versionNumber"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.copyTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.recordTarget"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.author"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.dataEnterer"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.informant"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.custodian"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.informationRecipient"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.legalAuthenticator"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.authenticator"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.participant"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.inFulfillmentOf"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.documentationOf"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.relatedDocument"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.authorization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.componentOf"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalDocument.component"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RecordTarget.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RecordTarget.contextControlCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RecordTarget.patientRole"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PatientRole.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PatientRole.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PatientRole.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PatientRole.telecom"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PatientRole.patient"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PatientRole.providerOrganization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.name"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.administrativeGenderCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.birthTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.maritalStatusCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.religiousAffiliationCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.raceCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.ethnicGroupCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.guardian"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.birthplace"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Patient.languageCommunication"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Guardian.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Guardian.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Guardian.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Guardian.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Guardian.telecom"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Guardian.guardianOrganization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Guardian.guardianPerson"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organization.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organization.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organization.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organization.name"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organization.telecom"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organization.addr"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organization.standardIndustryClassCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organization.asOrganizationPartOf"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#OrganizationPartOf.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#OrganizationPartOf.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#OrganizationPartOf.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#OrganizationPartOf.statusCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#OrganizationPartOf.effectiveTime"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#OrganizationPartOf.wholeOrganization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Person.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Person.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Person.name"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Birthplace.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Birthplace.place"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Place.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Place.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Place.name"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Place.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LanguageCommunication.languageCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LanguageCommunication.modeCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LanguageCommunication.proficiencyLevelCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LanguageCommunication.preferenceInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Author.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Author.functionCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Author.contextControlCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Author.time"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Author.assignedAuthor"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedAuthor.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedAuthor.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedAuthor.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedAuthor.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedAuthor.telecom"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedAuthor.assignedAuthoringDevice"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedAuthor.assignedPerson"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedAuthor.representedOrganization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AuthoringDevice.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AuthoringDevice.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AuthoringDevice.code"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AuthoringDevice.manufacturerModelName"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AuthoringDevice.softwareName"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AuthoringDevice.asMaintainedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#MaintainedEntity.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#MaintainedEntity.effectiveTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#MaintainedEntity.maintainingPerson"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#DataEnterer.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#DataEnterer.contextControlCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#DataEnterer.time"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#DataEnterer.assignedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedEntity.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedEntity.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedEntity.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedEntity.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedEntity.telecom"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedEntity.assignedPerson"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedEntity.representedOrganization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Informant12.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Informant12.contextControlCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Informant12.assignedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Informant12.relatedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedEntity.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedEntity.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedEntity.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedEntity.telecom"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedEntity.effectiveTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedEntity.relatedPerson"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Custodian.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Custodian.assignedCustodian"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedCustodian.classCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssignedCustodian.representedCustodianOrganization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#CustodianOrganization.classCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#CustodianOrganization.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#CustodianOrganization.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#CustodianOrganization.name"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#CustodianOrganization.telecom"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#CustodianOrganization.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#InformationRecipient.typeCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#InformationRecipient.intendedRecipient"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#IntendedRecipient.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#IntendedRecipient.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#IntendedRecipient.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#IntendedRecipient.telecom"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#IntendedRecipient.informationRecipient"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#IntendedRecipient.receivedOrganization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LegalAuthenticator.typeCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LegalAuthenticator.contextControlCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LegalAuthenticator.time"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LegalAuthenticator.signatureCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LegalAuthenticator.assignedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Authenticator.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Authenticator.time"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Authenticator.signatureCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Authenticator.assignedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Participant1.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Participant1.functionCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Participant1.contextControlCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Participant1.time"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Participant1.associatedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssociatedEntity.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssociatedEntity.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssociatedEntity.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssociatedEntity.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssociatedEntity.telecom"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssociatedEntity.associatedPerson"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#AssociatedEntity.scopingOrganization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#InFulfillmentOf.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#InFulfillmentOf.order"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Order.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Order.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Order.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Order.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Order.priorityCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#DocumentationOf.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#DocumentationOf.serviceEvent"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ServiceEvent.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ServiceEvent.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ServiceEvent.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ServiceEvent.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ServiceEvent.effectiveTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ServiceEvent.performer"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Performer1.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Performer1.functionCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Performer1.time"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Performer1.assignedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedDocument.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedDocument.parentDocument"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParentDocument.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParentDocument.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParentDocument.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParentDocument.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParentDocument.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParentDocument.setId"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParentDocument.versionNumber"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Authorization.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Authorization.consent"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Consent.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Consent.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Consent.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Consent.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Consent.statusCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component1.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component1.encompassingEncounter"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncompassingEncounter.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncompassingEncounter.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncompassingEncounter.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncompassingEncounter.code"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncompassingEncounter.effectiveTime"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncompassingEncounter.dischargeDispositionCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncompassingEncounter.responsibleParty"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncompassingEncounter.encounterParticipant"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncompassingEncounter.location"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ResponsibleParty.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ResponsibleParty.assignedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncounterParticipant.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncounterParticipant.time"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EncounterParticipant.assignedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Location.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Location.healthCareFacility"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#HealthCareFacility.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#HealthCareFacility.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#HealthCareFacility.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#HealthCareFacility.location"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#HealthCareFacility.serviceProviderOrganization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component2.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component2.contextConductionInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component2.nonXMLBody"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component2.structuredBody"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#NonXMLBody.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#NonXMLBody.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#NonXMLBody.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#NonXMLBody.confidentialityCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#NonXMLBody.languageCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#StructuredBody.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#StructuredBody.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#StructuredBody.confidentialityCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#StructuredBody.languageCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#StructuredBody.component"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component3.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component3.contextConductionInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component3.section"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.title"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.confidentialityCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.languageCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.subject"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.author"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.informant"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.entry"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Section.component"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Subject.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Subject.contextControlCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Subject.awarenessCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Subject.relatedSubject"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedSubject.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedSubject.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedSubject.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedSubject.telecom"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RelatedSubject.subject"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubjectPerson.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubjectPerson.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubjectPerson.name"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubjectPerson.administrativeGenderCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubjectPerson.birthTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.contextConductionInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.act"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.encounter"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.observation"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.observationMedia"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.organizer"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.procedure"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.regionOfInterest"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.substanceAdministration"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entry.supply"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalStatement.subject"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalStatement.specimen"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalStatement.performer"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalStatement.author"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalStatement.informant"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalStatement.participant"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalStatement.entryRelationship"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalStatement.reference"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ClinicalStatement.precondition"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Act.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Act.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Act.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Act.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Act.negationInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Act.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Act.statusCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Act.effectiveTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Act.priorityCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Act.languageCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Encounter.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Encounter.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Encounter.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Encounter.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Encounter.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Encounter.statusCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Encounter.effectiveTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Encounter.priorityCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.negationInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.derivationExpr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.statusCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.effectiveTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.priorityCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.repeatNumber"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.languageCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.value"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.interpretationCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.methodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.targetSiteCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Observation.referenceRange"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ReferenceRange.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ReferenceRange.observationRange"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationRange.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationRange.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationRange.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationRange.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationRange.value"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationRange.interpretationCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationMedia.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationMedia.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationMedia.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationMedia.languageCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ObservationMedia.value"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organizer.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organizer.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organizer.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organizer.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organizer.statusCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organizer.effectiveTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Organizer.component"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.contextConductionInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.sequenceNumber"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.seperatableInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.act"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.encounter"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.observation"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.observationMedia"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.organizer"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.procedure"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.regionOfInterest"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.substanceAdministration"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component4.supply"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.negationInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.statusCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.effectiveTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.priorityCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.languageCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.methodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.approachSiteCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Procedure.targetSiteCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RegionOfInterest.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RegionOfInterest.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RegionOfInterest.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RegionOfInterest.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#RegionOfInterest.value"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.code"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.negationInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.statusCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.effectiveTime"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.priorityCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.repeatNumber"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.routeCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.approachSiteCode"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.doseQuantity"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.rateQuantity"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.maxDoseQuantity"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.administrationUnitCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SubstanceAdministration.consumable"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Consumable.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Consumable.manufacturedProduct"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ManufacturedProduct.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ManufacturedProduct.id"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ManufacturedProduct.manufacturedLabeledDrug"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ManufacturedProduct.manufacturedMaterial"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ManufacturedProduct.manufacturerOrganization"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LabeledDrug.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LabeledDrug.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LabeledDrug.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#LabeledDrug.name"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Material.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Material.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Material.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Material.name"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Material.lotNumberText"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.statusCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.effectiveTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.priorityCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.repeatNumber"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.independentInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.quantity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.expectedUseTime"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Supply.product"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Product.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Product.manufacturedProduct"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Specimen.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Specimen.specimenRole"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SpecimenRole.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SpecimenRole.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#SpecimenRole.specimenPlayingEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PlayingEntity.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PlayingEntity.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PlayingEntity.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PlayingEntity.quantity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PlayingEntity.name"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#PlayingEntity.desc"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Performer2.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Performer2.time"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Performer2.modeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Performer2.assignedEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Participant2.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Participant2.contextControlCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Participant2.time"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Participant2.awarenessCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Participant2.participantRole"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParticipantRole.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParticipantRole.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParticipantRole.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParticipantRole.addr"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParticipantRole.telecom"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParticipantRole.playingDevice"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParticipantRole.playingEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ParticipantRole.scopingEntity"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Device.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Device.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Device.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Device.manufacturerModelName"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Device.softwareName"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entity.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entity.determinerCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entity.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entity.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Entity.desc"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.inversionInd"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.contextConductionInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.sequenceNumber"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.negationInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.seperatableInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.act"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.encounter"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.observation"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.observationMedia"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.organizer"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.procedure"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.regionOfInterest"/>
            <owl:ObjectProperty
                    rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.substanceAdministration"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#EntryRelationship.supply"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Reference.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Reference.seperatableInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Reference.externalAct"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Reference.externalDocument"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Reference.externalObservation"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Reference.externalProcedure"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalAct.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalAct.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalAct.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalAct.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalAct.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalDocument.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalDocument.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalDocument.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalDocument.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalDocument.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalDocument.setId"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalDocument.versionNumber"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalObservation.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalObservation.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalObservation.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalObservation.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalObservation.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalProcedure.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalProcedure.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalProcedure.id"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalProcedure.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#ExternalProcedure.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Precondition.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Precondition.criterion"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Criterion.classCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Criterion.moodCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Criterion.code"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Criterion.text"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Criterion.value"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component5.typeCode"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component5.contextConductionInd"/>
            <owl:ObjectProperty rdf:about="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#Component5.section"/>
            <xsl:for-each select="v3:ClinicalDocument">
                <xsl:apply-templates mode="M0_ClinicalDocument" select="."/>
            </xsl:for-each>
        </rdf:RDF>
    </xsl:template>
    <xsl:template name="infrastructureRootStuff">
        <xsl:for-each select="@ID">
            <xsl:element name="_XML:id" namespace="http://www.w3.org/XML/1998/namespace#">
                <xsl:value-of select="."/>
            </xsl:element>
        </xsl:for-each>
        <xsl:for-each select="@nullFlavor">
            <xsl:element name="RIM:InfrastructureRoot.nullFlavor" namespace="urn:hl7-org:v3/owl/DEFN/UV/RIM/207#">
                <xsl:apply-templates mode="CS" select="."/>
            </xsl:element>
        </xsl:for-each>
        <xsl:for-each select="v3:typeId">
            <xsl:element name="RIM:InfrastructureRoot.typeId" namespace="urn:hl7-org:v3/owl/DEFN/UV/RIM/207#">
                <xsl:apply-templates mode="II" select="."/>
            </xsl:element>
        </xsl:for-each>
        <xsl:if test="v3:realmCode">
            <xsl:element name="RIM:InfrastructureRoot.realmCode" namespace="urn:hl7-org:v3/owl/DEFN/UV/RIM/207#">
                <rdf:Bag/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="v3:templateId">
            <xsl:element name="RIM:InfrastructureRoot.templateId" namespace="urn:hl7-org:v3/owl/DEFN/UV/RIM/207#">
                <rdf:Bag>
                    <xsl:for-each select="v3:templateId">
                        <rdf:li>
                            <xsl:apply-templates mode="II" select="."/>
                        </rdf:li>
                    </xsl:for-each>
                </rdf:Bag>
            </xsl:element>
        </xsl:if>
    </xsl:template>
    <xsl:template mode="M0_ClinicalDocument" match="*">
        <M0:ClinicalDocument xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ClinicalDocument.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ClinicalDocument.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:ClinicalDocument.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ClinicalDocument.moodCode>
            </xsl:for-each>
            <xsl:for-each select="v3:id">
                <M0:ClinicalDocument.id>
                    <xsl:apply-templates mode="II" select="."/>
                </M0:ClinicalDocument.id>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:ClinicalDocument.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:ClinicalDocument.code>
            </xsl:for-each>
            <xsl:for-each select="v3:title">
                <M0:ClinicalDocument.title>
                    <xsl:apply-templates mode="ST" select="."/>
                </M0:ClinicalDocument.title>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:ClinicalDocument.effectiveTime>
                    <xsl:apply-templates mode="TS" select="."/>
                </M0:ClinicalDocument.effectiveTime>
            </xsl:for-each>
            <xsl:for-each select="v3:confidentialityCode">
                <M0:ClinicalDocument.confidentialityCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:ClinicalDocument.confidentialityCode>
            </xsl:for-each>
            <xsl:for-each select="v3:languageCode">
                <M0:ClinicalDocument.languageCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ClinicalDocument.languageCode>
            </xsl:for-each>
            <xsl:for-each select="v3:setId">
                <M0:ClinicalDocument.setId>
                    <xsl:apply-templates mode="II" select="."/>
                </M0:ClinicalDocument.setId>
            </xsl:for-each>
            <xsl:for-each select="v3:versionNumber">
                <M0:ClinicalDocument.versionNumber>
                    <xsl:apply-templates mode="INT" select="."/>
                </M0:ClinicalDocument.versionNumber>
            </xsl:for-each>
            <xsl:for-each select="v3:copyTime">
                <M0:ClinicalDocument.copyTime>
                    <xsl:apply-templates mode="TS" select="."/>
                </M0:ClinicalDocument.copyTime>
            </xsl:for-each>
            <xsl:if test="v3:recordTarget">
                <M0:ClinicalDocument.recordTarget>
                    <rdf:Bag>
                        <xsl:for-each select="v3:recordTarget">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_RecordTarget" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalDocument.recordTarget>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:ClinicalDocument.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalDocument.author>
            </xsl:if>
            <xsl:if test="v3:dataEnterer">
                <M0:ClinicalDocument.dataEnterer>
                    <xsl:for-each select="v3:dataEnterer">
                        <xsl:apply-templates mode="M0_DataEnterer" select="."/>
                    </xsl:for-each>
                </M0:ClinicalDocument.dataEnterer>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:ClinicalDocument.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalDocument.informant>
            </xsl:if>
            <xsl:if test="v3:custodian">
                <M0:ClinicalDocument.custodian>
                    <xsl:for-each select="v3:custodian">
                        <xsl:apply-templates mode="M0_Custodian" select="."/>
                    </xsl:for-each>
                </M0:ClinicalDocument.custodian>
            </xsl:if>
            <xsl:if test="v3:informationRecipient">
                <M0:ClinicalDocument.informationRecipient>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informationRecipient">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_InformationRecipient" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalDocument.informationRecipient>
            </xsl:if>
            <xsl:if test="v3:legalAuthenticator">
                <M0:ClinicalDocument.legalAuthenticator>
                    <xsl:for-each select="v3:legalAuthenticator">
                        <xsl:apply-templates mode="M0_LegalAuthenticator" select="."/>
                    </xsl:for-each>
                </M0:ClinicalDocument.legalAuthenticator>
            </xsl:if>
            <xsl:if test="v3:authenticator">
                <M0:ClinicalDocument.authenticator>
                    <rdf:Bag>
                        <xsl:for-each select="v3:authenticator">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Authenticator" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalDocument.authenticator>
            </xsl:if>
            <xsl:if test="v3:participant">
                <M0:ClinicalDocument.participant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:participant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Participant1" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalDocument.participant>
            </xsl:if>
            <xsl:if test="v3:inFulfillmentOf">
                <M0:ClinicalDocument.inFulfillmentOf>
                    <rdf:Bag>
                        <xsl:for-each select="v3:inFulfillmentOf">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_InFulfillmentOf" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalDocument.inFulfillmentOf>
            </xsl:if>
            <xsl:if test="v3:documentationOf">
                <M0:ClinicalDocument.documentationOf>
                    <rdf:Bag>
                        <xsl:for-each select="v3:documentationOf">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_DocumentationOf" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalDocument.documentationOf>
            </xsl:if>
            <xsl:if test="v3:relatedDocument">
                <M0:ClinicalDocument.relatedDocument>
                    <rdf:Bag>
                        <xsl:for-each select="v3:relatedDocument">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_RelatedDocument" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalDocument.relatedDocument>
            </xsl:if>
            <xsl:if test="v3:authorization">
                <M0:ClinicalDocument.authorization>
                    <rdf:Bag>
                        <xsl:for-each select="v3:authorization">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Authorization" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalDocument.authorization>
            </xsl:if>
            <xsl:if test="v3:componentOf">
                <M0:ClinicalDocument.componentOf>
                    <xsl:for-each select="v3:componentOf">
                        <xsl:apply-templates mode="M0_Component1" select="."/>
                    </xsl:for-each>
                </M0:ClinicalDocument.componentOf>
            </xsl:if>
            <xsl:if test="v3:component">
                <M0:ClinicalDocument.component>
                    <xsl:for-each select="v3:component">
                        <xsl:apply-templates mode="M0_Component2" select="."/>
                    </xsl:for-each>
                </M0:ClinicalDocument.component>
            </xsl:if>
        </M0:ClinicalDocument>
    </xsl:template>
    <xsl:template mode="M0_RecordTarget" match="*">
        <M0:RecordTarget xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:RecordTarget.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:RecordTarget.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextControlCode">
                <M0:RecordTarget.contextControlCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:RecordTarget.contextControlCode>
            </xsl:for-each>
            <xsl:if test="v3:patientRole">
                <M0:RecordTarget.patientRole>
                    <xsl:for-each select="v3:patientRole">
                        <xsl:apply-templates mode="M0_PatientRole" select="."/>
                    </xsl:for-each>
                </M0:RecordTarget.patientRole>
            </xsl:if>
        </M0:RecordTarget>
    </xsl:template>
    <xsl:template mode="M0_PatientRole" match="*">
        <M0:PatientRole xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:PatientRole.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:PatientRole.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:PatientRole.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:PatientRole.id>
            </xsl:if>
            <xsl:if test="v3:addr">
                <M0:PatientRole.addr>
                    <rdf:Bag>
                        <xsl:for-each select="v3:addr">
                            <rdf:li>
                                <xsl:apply-templates mode="AD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:PatientRole.addr>
            </xsl:if>
            <xsl:if test="v3:telecom">
                <M0:PatientRole.telecom>
                    <rdf:Bag>
                        <xsl:for-each select="v3:telecom">
                            <rdf:li>
                                <xsl:apply-templates mode="TEL" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:PatientRole.telecom>
            </xsl:if>
            <xsl:if test="v3:patient">
                <M0:PatientRole.patient>
                    <xsl:for-each select="v3:patient">
                        <xsl:apply-templates mode="M0_Patient" select="."/>
                    </xsl:for-each>
                </M0:PatientRole.patient>
            </xsl:if>
            <xsl:if test="v3:providerOrganization">
                <M0:PatientRole.providerOrganization>
                    <xsl:for-each select="v3:providerOrganization">
                        <xsl:apply-templates mode="M0_Organization" select="."/>
                    </xsl:for-each>
                </M0:PatientRole.providerOrganization>
            </xsl:if>
        </M0:PatientRole>
    </xsl:template>
    <xsl:template mode="M0_Patient" match="*">
        <M0:Patient xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Patient.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Patient.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:Patient.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Patient.determinerCode>
            </xsl:for-each>
            <xsl:for-each select="v3:id">
                <M0:Patient.id>
                    <xsl:apply-templates mode="II" select="."/>
                </M0:Patient.id>
            </xsl:for-each>
            <xsl:if test="v3:name">
                <M0:Patient.name>
                    <rdf:Bag>
                        <xsl:for-each select="v3:name">
                            <rdf:li>
                                <xsl:apply-templates mode="PN" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Patient.name>
            </xsl:if>
            <xsl:for-each select="v3:administrativeGenderCode">
                <M0:Patient.administrativeGenderCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Patient.administrativeGenderCode>
            </xsl:for-each>
            <xsl:for-each select="v3:birthTime">
                <M0:Patient.birthTime>
                    <xsl:apply-templates mode="TS" select="."/>
                </M0:Patient.birthTime>
            </xsl:for-each>
            <xsl:for-each select="v3:maritalStatusCode">
                <M0:Patient.maritalStatusCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Patient.maritalStatusCode>
            </xsl:for-each>
            <xsl:for-each select="v3:religiousAffiliationCode">
                <M0:Patient.religiousAffiliationCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Patient.religiousAffiliationCode>
            </xsl:for-each>
            <xsl:for-each select="v3:raceCode">
                <M0:Patient.raceCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Patient.raceCode>
            </xsl:for-each>
            <xsl:for-each select="v3:ethnicGroupCode">
                <M0:Patient.ethnicGroupCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Patient.ethnicGroupCode>
            </xsl:for-each>
            <xsl:if test="v3:guardian">
                <M0:Patient.guardian>
                    <rdf:Bag>
                        <xsl:for-each select="v3:guardian">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Guardian" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Patient.guardian>
            </xsl:if>
            <xsl:if test="v3:birthplace">
                <M0:Patient.birthplace>
                    <xsl:for-each select="v3:birthplace">
                        <xsl:apply-templates mode="M0_Birthplace" select="."/>
                    </xsl:for-each>
                </M0:Patient.birthplace>
            </xsl:if>
            <xsl:if test="v3:languageCommunication">
                <M0:Patient.languageCommunication>
                    <rdf:Bag>
                        <xsl:for-each select="v3:languageCommunication">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_LanguageCommunication" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Patient.languageCommunication>
            </xsl:if>
        </M0:Patient>
    </xsl:template>
    <xsl:template mode="M0_Guardian" match="*">
        <M0:Guardian xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Guardian.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Guardian.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Guardian.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Guardian.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:Guardian.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Guardian.code>
            </xsl:for-each>
            <xsl:if test="v3:addr">
                <M0:Guardian.addr>
                    <rdf:Bag>
                        <xsl:for-each select="v3:addr">
                            <rdf:li>
                                <xsl:apply-templates mode="AD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Guardian.addr>
            </xsl:if>
            <xsl:if test="v3:telecom">
                <M0:Guardian.telecom>
                    <rdf:Bag>
                        <xsl:for-each select="v3:telecom">
                            <rdf:li>
                                <xsl:apply-templates mode="TEL" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Guardian.telecom>
            </xsl:if>
            <xsl:if test="v3:guardianOrganization">
                <M0:Guardian.guardianOrganization>
                    <xsl:for-each select="v3:guardianOrganization">
                        <xsl:apply-templates mode="M0_Organization" select="."/>
                    </xsl:for-each>
                </M0:Guardian.guardianOrganization>
            </xsl:if>
            <xsl:if test="v3:guardianPerson">
                <M0:Guardian.guardianPerson>
                    <xsl:for-each select="v3:guardianPerson">
                        <xsl:apply-templates mode="M0_Person" select="."/>
                    </xsl:for-each>
                </M0:Guardian.guardianPerson>
            </xsl:if>
        </M0:Guardian>
    </xsl:template>
    <xsl:template mode="M0_Organization" match="*">
        <M0:Organization xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Organization.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Organization.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:Organization.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Organization.determinerCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Organization.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Organization.id>
            </xsl:if>
            <xsl:if test="v3:name">
                <M0:Organization.name>
                    <rdf:Bag>
                        <xsl:for-each select="v3:name">
                            <rdf:li>
                                <xsl:apply-templates mode="ON" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Organization.name>
            </xsl:if>
            <xsl:if test="v3:telecom">
                <M0:Organization.telecom>
                    <rdf:Bag>
                        <xsl:for-each select="v3:telecom">
                            <rdf:li>
                                <xsl:apply-templates mode="TEL" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Organization.telecom>
            </xsl:if>
            <xsl:if test="v3:addr">
                <M0:Organization.addr>
                    <rdf:Bag>
                        <xsl:for-each select="v3:addr">
                            <rdf:li>
                                <xsl:apply-templates mode="AD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Organization.addr>
            </xsl:if>
            <xsl:for-each select="v3:standardIndustryClassCode">
                <M0:Organization.standardIndustryClassCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Organization.standardIndustryClassCode>
            </xsl:for-each>
            <xsl:if test="v3:asOrganizationPartOf">
                <M0:Organization.asOrganizationPartOf>
                    <xsl:for-each select="v3:asOrganizationPartOf">
                        <xsl:apply-templates mode="M0_OrganizationPartOf" select="."/>
                    </xsl:for-each>
                </M0:Organization.asOrganizationPartOf>
            </xsl:if>
        </M0:Organization>
    </xsl:template>
    <xsl:template mode="M0_OrganizationPartOf" match="*">
        <M0:OrganizationPartOf xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:OrganizationPartOf.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:OrganizationPartOf.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:OrganizationPartOf.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:OrganizationPartOf.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:OrganizationPartOf.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:OrganizationPartOf.code>
            </xsl:for-each>
            <xsl:for-each select="v3:statusCode">
                <M0:OrganizationPartOf.statusCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:OrganizationPartOf.statusCode>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:OrganizationPartOf.effectiveTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:OrganizationPartOf.effectiveTime>
            </xsl:for-each>
            <xsl:if test="v3:wholeOrganization">
                <M0:OrganizationPartOf.wholeOrganization>
                    <xsl:for-each select="v3:wholeOrganization">
                        <xsl:apply-templates mode="M0_Organization" select="."/>
                    </xsl:for-each>
                </M0:OrganizationPartOf.wholeOrganization>
            </xsl:if>
        </M0:OrganizationPartOf>
    </xsl:template>
    <xsl:template mode="M0_Person" match="*">
        <M0:Person xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Person.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Person.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:Person.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Person.determinerCode>
            </xsl:for-each>
            <xsl:if test="v3:name">
                <M0:Person.name>
                    <rdf:Bag>
                        <xsl:for-each select="v3:name">
                            <rdf:li>
                                <xsl:apply-templates mode="PN" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Person.name>
            </xsl:if>
        </M0:Person>
    </xsl:template>
    <xsl:template mode="M0_Birthplace" match="*">
        <M0:Birthplace xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Birthplace.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Birthplace.classCode>
            </xsl:for-each>
            <xsl:if test="v3:place">
                <M0:Birthplace.place>
                    <xsl:for-each select="v3:place">
                        <xsl:apply-templates mode="M0_Place" select="."/>
                    </xsl:for-each>
                </M0:Birthplace.place>
            </xsl:if>
        </M0:Birthplace>
    </xsl:template>
    <xsl:template mode="M0_Place" match="*">
        <M0:Place xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Place.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Place.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:Place.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Place.determinerCode>
            </xsl:for-each>
            <xsl:for-each select="v3:name">
                <M0:Place.name>
                    <xsl:apply-templates mode="EN" select="."/>
                </M0:Place.name>
            </xsl:for-each>
            <xsl:for-each select="v3:addr">
                <M0:Place.addr>
                    <xsl:apply-templates mode="AD" select="."/>
                </M0:Place.addr>
            </xsl:for-each>
        </M0:Place>
    </xsl:template>
    <xsl:template mode="M0_LanguageCommunication" match="*">
        <M0:LanguageCommunication xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="v3:languageCode">
                <M0:LanguageCommunication.languageCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:LanguageCommunication.languageCode>
            </xsl:for-each>
            <xsl:for-each select="v3:modeCode">
                <M0:LanguageCommunication.modeCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:LanguageCommunication.modeCode>
            </xsl:for-each>
            <xsl:for-each select="v3:proficiencyLevelCode">
                <M0:LanguageCommunication.proficiencyLevelCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:LanguageCommunication.proficiencyLevelCode>
            </xsl:for-each>
            <xsl:for-each select="v3:preferenceInd">
                <M0:LanguageCommunication.preferenceInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:LanguageCommunication.preferenceInd>
            </xsl:for-each>
        </M0:LanguageCommunication>
    </xsl:template>
    <xsl:template mode="M0_Author" match="*">
        <M0:Author xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Author.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Author.typeCode>
            </xsl:for-each>
            <xsl:for-each select="v3:functionCode">
                <M0:Author.functionCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Author.functionCode>
            </xsl:for-each>
            <xsl:for-each select="@contextControlCode">
                <M0:Author.contextControlCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Author.contextControlCode>
            </xsl:for-each>
            <xsl:for-each select="v3:time">
                <M0:Author.time>
                    <xsl:apply-templates mode="TS" select="."/>
                </M0:Author.time>
            </xsl:for-each>
            <xsl:if test="v3:assignedAuthor">
                <M0:Author.assignedAuthor>
                    <xsl:for-each select="v3:assignedAuthor">
                        <xsl:apply-templates mode="M0_AssignedAuthor" select="."/>
                    </xsl:for-each>
                </M0:Author.assignedAuthor>
            </xsl:if>
        </M0:Author>
    </xsl:template>
    <xsl:template mode="M0_AssignedAuthor" match="*">
        <M0:AssignedAuthor xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:AssignedAuthor.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:AssignedAuthor.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:AssignedAuthor.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:AssignedAuthor.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:AssignedAuthor.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:AssignedAuthor.code>
            </xsl:for-each>
            <xsl:if test="v3:addr">
                <M0:AssignedAuthor.addr>
                    <rdf:Bag>
                        <xsl:for-each select="v3:addr">
                            <rdf:li>
                                <xsl:apply-templates mode="AD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:AssignedAuthor.addr>
            </xsl:if>
            <xsl:if test="v3:telecom">
                <M0:AssignedAuthor.telecom>
                    <rdf:Bag>
                        <xsl:for-each select="v3:telecom">
                            <rdf:li>
                                <xsl:apply-templates mode="TEL" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:AssignedAuthor.telecom>
            </xsl:if>
            <xsl:if test="v3:assignedAuthoringDevice">
                <M0:AssignedAuthor.assignedAuthoringDevice>
                    <xsl:for-each select="v3:assignedAuthoringDevice">
                        <xsl:apply-templates mode="M0_AuthoringDevice" select="."/>
                    </xsl:for-each>
                </M0:AssignedAuthor.assignedAuthoringDevice>
            </xsl:if>
            <xsl:if test="v3:assignedPerson">
                <M0:AssignedAuthor.assignedPerson>
                    <xsl:for-each select="v3:assignedPerson">
                        <xsl:apply-templates mode="M0_Person" select="."/>
                    </xsl:for-each>
                </M0:AssignedAuthor.assignedPerson>
            </xsl:if>
            <xsl:if test="v3:representedOrganization">
                <M0:AssignedAuthor.representedOrganization>
                    <xsl:for-each select="v3:representedOrganization">
                        <xsl:apply-templates mode="M0_Organization" select="."/>
                    </xsl:for-each>
                </M0:AssignedAuthor.representedOrganization>
            </xsl:if>
        </M0:AssignedAuthor>
    </xsl:template>
    <xsl:template mode="M0_AuthoringDevice" match="*">
        <M0:AuthoringDevice xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:AuthoringDevice.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:AuthoringDevice.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:AuthoringDevice.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:AuthoringDevice.determinerCode>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:AuthoringDevice.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:AuthoringDevice.code>
            </xsl:for-each>
            <xsl:for-each select="v3:manufacturerModelName">
                <M0:AuthoringDevice.manufacturerModelName>
                    <xsl:apply-templates mode="SC" select="."/>
                </M0:AuthoringDevice.manufacturerModelName>
            </xsl:for-each>
            <xsl:for-each select="v3:softwareName">
                <M0:AuthoringDevice.softwareName>
                    <xsl:apply-templates mode="SC" select="."/>
                </M0:AuthoringDevice.softwareName>
            </xsl:for-each>
            <xsl:if test="v3:asMaintainedEntity">
                <M0:AuthoringDevice.asMaintainedEntity>
                    <rdf:Bag>
                        <xsl:for-each select="v3:asMaintainedEntity">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_MaintainedEntity" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:AuthoringDevice.asMaintainedEntity>
            </xsl:if>
        </M0:AuthoringDevice>
    </xsl:template>
    <xsl:template mode="M0_MaintainedEntity" match="*">
        <M0:MaintainedEntity xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:MaintainedEntity.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:MaintainedEntity.classCode>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:MaintainedEntity.effectiveTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:MaintainedEntity.effectiveTime>
            </xsl:for-each>
            <xsl:if test="v3:maintainingPerson">
                <M0:MaintainedEntity.maintainingPerson>
                    <xsl:for-each select="v3:maintainingPerson">
                        <xsl:apply-templates mode="M0_Person" select="."/>
                    </xsl:for-each>
                </M0:MaintainedEntity.maintainingPerson>
            </xsl:if>
        </M0:MaintainedEntity>
    </xsl:template>
    <xsl:template mode="M0_DataEnterer" match="*">
        <M0:DataEnterer xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:DataEnterer.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:DataEnterer.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextControlCode">
                <M0:DataEnterer.contextControlCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:DataEnterer.contextControlCode>
            </xsl:for-each>
            <xsl:for-each select="v3:time">
                <M0:DataEnterer.time>
                    <xsl:apply-templates mode="TS" select="."/>
                </M0:DataEnterer.time>
            </xsl:for-each>
            <xsl:if test="v3:assignedEntity">
                <M0:DataEnterer.assignedEntity>
                    <xsl:for-each select="v3:assignedEntity">
                        <xsl:apply-templates mode="M0_AssignedEntity" select="."/>
                    </xsl:for-each>
                </M0:DataEnterer.assignedEntity>
            </xsl:if>
        </M0:DataEnterer>
    </xsl:template>
    <xsl:template mode="M0_AssignedEntity" match="*">
        <M0:AssignedEntity xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:AssignedEntity.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:AssignedEntity.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:AssignedEntity.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:AssignedEntity.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:AssignedEntity.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:AssignedEntity.code>
            </xsl:for-each>
            <xsl:if test="v3:addr">
                <M0:AssignedEntity.addr>
                    <rdf:Bag>
                        <xsl:for-each select="v3:addr">
                            <rdf:li>
                                <xsl:apply-templates mode="AD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:AssignedEntity.addr>
            </xsl:if>
            <xsl:if test="v3:telecom">
                <M0:AssignedEntity.telecom>
                    <rdf:Bag>
                        <xsl:for-each select="v3:telecom">
                            <rdf:li>
                                <xsl:apply-templates mode="TEL" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:AssignedEntity.telecom>
            </xsl:if>
            <xsl:if test="v3:assignedPerson">
                <M0:AssignedEntity.assignedPerson>
                    <xsl:for-each select="v3:assignedPerson">
                        <xsl:apply-templates mode="M0_Person" select="."/>
                    </xsl:for-each>
                </M0:AssignedEntity.assignedPerson>
            </xsl:if>
            <xsl:if test="v3:representedOrganization">
                <M0:AssignedEntity.representedOrganization>
                    <xsl:for-each select="v3:representedOrganization">
                        <xsl:apply-templates mode="M0_Organization" select="."/>
                    </xsl:for-each>
                </M0:AssignedEntity.representedOrganization>
            </xsl:if>
        </M0:AssignedEntity>
    </xsl:template>
    <xsl:template mode="M0_Informant12" match="*">
        <M0:Informant12 xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Informant12.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Informant12.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextControlCode">
                <M0:Informant12.contextControlCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Informant12.contextControlCode>
            </xsl:for-each>
            <xsl:if test="v3:assignedEntity">
                <M0:Informant12.assignedEntity>
                    <xsl:for-each select="v3:assignedEntity">
                        <xsl:apply-templates mode="M0_AssignedEntity" select="."/>
                    </xsl:for-each>
                </M0:Informant12.assignedEntity>
            </xsl:if>
            <xsl:if test="v3:relatedEntity">
                <M0:Informant12.relatedEntity>
                    <xsl:for-each select="v3:relatedEntity">
                        <xsl:apply-templates mode="M0_RelatedEntity" select="."/>
                    </xsl:for-each>
                </M0:Informant12.relatedEntity>
            </xsl:if>
        </M0:Informant12>
    </xsl:template>
    <xsl:template mode="M0_RelatedEntity" match="*">
        <M0:RelatedEntity xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:RelatedEntity.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:RelatedEntity.classCode>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:RelatedEntity.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:RelatedEntity.code>
            </xsl:for-each>
            <xsl:if test="v3:addr">
                <M0:RelatedEntity.addr>
                    <rdf:Bag>
                        <xsl:for-each select="v3:addr">
                            <rdf:li>
                                <xsl:apply-templates mode="AD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:RelatedEntity.addr>
            </xsl:if>
            <xsl:if test="v3:telecom">
                <M0:RelatedEntity.telecom>
                    <rdf:Bag>
                        <xsl:for-each select="v3:telecom">
                            <rdf:li>
                                <xsl:apply-templates mode="TEL" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:RelatedEntity.telecom>
            </xsl:if>
            <xsl:for-each select="v3:effectiveTime">
                <M0:RelatedEntity.effectiveTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:RelatedEntity.effectiveTime>
            </xsl:for-each>
            <xsl:if test="v3:relatedPerson">
                <M0:RelatedEntity.relatedPerson>
                    <xsl:for-each select="v3:relatedPerson">
                        <xsl:apply-templates mode="M0_Person" select="."/>
                    </xsl:for-each>
                </M0:RelatedEntity.relatedPerson>
            </xsl:if>
        </M0:RelatedEntity>
    </xsl:template>
    <xsl:template mode="M0_Custodian" match="*">
        <M0:Custodian xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Custodian.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Custodian.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:assignedCustodian">
                <M0:Custodian.assignedCustodian>
                    <xsl:for-each select="v3:assignedCustodian">
                        <xsl:apply-templates mode="M0_AssignedCustodian" select="."/>
                    </xsl:for-each>
                </M0:Custodian.assignedCustodian>
            </xsl:if>
        </M0:Custodian>
    </xsl:template>
    <xsl:template mode="M0_AssignedCustodian" match="*">
        <M0:AssignedCustodian xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:AssignedCustodian.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:AssignedCustodian.classCode>
            </xsl:for-each>
            <xsl:if test="v3:representedCustodianOrganization">
                <M0:AssignedCustodian.representedCustodianOrganization>
                    <xsl:for-each select="v3:representedCustodianOrganization">
                        <xsl:apply-templates mode="M0_CustodianOrganization" select="."/>
                    </xsl:for-each>
                </M0:AssignedCustodian.representedCustodianOrganization>
            </xsl:if>
        </M0:AssignedCustodian>
    </xsl:template>
    <xsl:template mode="M0_CustodianOrganization" match="*">
        <M0:CustodianOrganization xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:CustodianOrganization.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:CustodianOrganization.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:CustodianOrganization.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:CustodianOrganization.determinerCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:CustodianOrganization.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:CustodianOrganization.id>
            </xsl:if>
            <xsl:for-each select="v3:name">
                <M0:CustodianOrganization.name>
                    <xsl:apply-templates mode="ON" select="."/>
                </M0:CustodianOrganization.name>
            </xsl:for-each>
            <xsl:for-each select="v3:telecom">
                <M0:CustodianOrganization.telecom>
                    <xsl:apply-templates mode="TEL" select="."/>
                </M0:CustodianOrganization.telecom>
            </xsl:for-each>
            <xsl:for-each select="v3:addr">
                <M0:CustodianOrganization.addr>
                    <xsl:apply-templates mode="AD" select="."/>
                </M0:CustodianOrganization.addr>
            </xsl:for-each>
        </M0:CustodianOrganization>
    </xsl:template>
    <xsl:template mode="M0_InformationRecipient" match="*">
        <M0:InformationRecipient xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:InformationRecipient.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:InformationRecipient.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:intendedRecipient">
                <M0:InformationRecipient.intendedRecipient>
                    <xsl:for-each select="v3:intendedRecipient">
                        <xsl:apply-templates mode="M0_IntendedRecipient" select="."/>
                    </xsl:for-each>
                </M0:InformationRecipient.intendedRecipient>
            </xsl:if>
        </M0:InformationRecipient>
    </xsl:template>
    <xsl:template mode="M0_IntendedRecipient" match="*">
        <M0:IntendedRecipient xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:IntendedRecipient.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:IntendedRecipient.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:IntendedRecipient.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:IntendedRecipient.id>
            </xsl:if>
            <xsl:if test="v3:addr">
                <M0:IntendedRecipient.addr>
                    <rdf:Bag>
                        <xsl:for-each select="v3:addr">
                            <rdf:li>
                                <xsl:apply-templates mode="AD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:IntendedRecipient.addr>
            </xsl:if>
            <xsl:if test="v3:telecom">
                <M0:IntendedRecipient.telecom>
                    <rdf:Bag>
                        <xsl:for-each select="v3:telecom">
                            <rdf:li>
                                <xsl:apply-templates mode="TEL" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:IntendedRecipient.telecom>
            </xsl:if>
            <xsl:if test="v3:informationRecipient">
                <M0:IntendedRecipient.informationRecipient>
                    <xsl:for-each select="v3:informationRecipient">
                        <xsl:apply-templates mode="M0_Person" select="."/>
                    </xsl:for-each>
                </M0:IntendedRecipient.informationRecipient>
            </xsl:if>
            <xsl:if test="v3:receivedOrganization">
                <M0:IntendedRecipient.receivedOrganization>
                    <xsl:for-each select="v3:receivedOrganization">
                        <xsl:apply-templates mode="M0_Organization" select="."/>
                    </xsl:for-each>
                </M0:IntendedRecipient.receivedOrganization>
            </xsl:if>
        </M0:IntendedRecipient>
    </xsl:template>
    <xsl:template mode="M0_LegalAuthenticator" match="*">
        <M0:LegalAuthenticator xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:LegalAuthenticator.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:LegalAuthenticator.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextControlCode">
                <M0:LegalAuthenticator.contextControlCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:LegalAuthenticator.contextControlCode>
            </xsl:for-each>
            <xsl:for-each select="v3:time">
                <M0:LegalAuthenticator.time>
                    <xsl:apply-templates mode="TS" select="."/>
                </M0:LegalAuthenticator.time>
            </xsl:for-each>
            <xsl:for-each select="v3:signatureCode">
                <M0:LegalAuthenticator.signatureCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:LegalAuthenticator.signatureCode>
            </xsl:for-each>
            <xsl:if test="v3:assignedEntity">
                <M0:LegalAuthenticator.assignedEntity>
                    <xsl:for-each select="v3:assignedEntity">
                        <xsl:apply-templates mode="M0_AssignedEntity" select="."/>
                    </xsl:for-each>
                </M0:LegalAuthenticator.assignedEntity>
            </xsl:if>
        </M0:LegalAuthenticator>
    </xsl:template>
    <xsl:template mode="M0_Authenticator" match="*">
        <M0:Authenticator xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Authenticator.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Authenticator.typeCode>
            </xsl:for-each>
            <xsl:for-each select="v3:time">
                <M0:Authenticator.time>
                    <xsl:apply-templates mode="TS" select="."/>
                </M0:Authenticator.time>
            </xsl:for-each>
            <xsl:for-each select="v3:signatureCode">
                <M0:Authenticator.signatureCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Authenticator.signatureCode>
            </xsl:for-each>
            <xsl:if test="v3:assignedEntity">
                <M0:Authenticator.assignedEntity>
                    <xsl:for-each select="v3:assignedEntity">
                        <xsl:apply-templates mode="M0_AssignedEntity" select="."/>
                    </xsl:for-each>
                </M0:Authenticator.assignedEntity>
            </xsl:if>
        </M0:Authenticator>
    </xsl:template>
    <xsl:template mode="M0_Participant1" match="*">
        <M0:Participant1 xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Participant1.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Participant1.typeCode>
            </xsl:for-each>
            <xsl:for-each select="v3:functionCode">
                <M0:Participant1.functionCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Participant1.functionCode>
            </xsl:for-each>
            <xsl:for-each select="@contextControlCode">
                <M0:Participant1.contextControlCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Participant1.contextControlCode>
            </xsl:for-each>
            <xsl:for-each select="v3:time">
                <M0:Participant1.time>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:Participant1.time>
            </xsl:for-each>
            <xsl:if test="v3:associatedEntity">
                <M0:Participant1.associatedEntity>
                    <xsl:for-each select="v3:associatedEntity">
                        <xsl:apply-templates mode="M0_AssociatedEntity" select="."/>
                    </xsl:for-each>
                </M0:Participant1.associatedEntity>
            </xsl:if>
        </M0:Participant1>
    </xsl:template>
    <xsl:template mode="M0_AssociatedEntity" match="*">
        <M0:AssociatedEntity xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:AssociatedEntity.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:AssociatedEntity.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:AssociatedEntity.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:AssociatedEntity.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:AssociatedEntity.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:AssociatedEntity.code>
            </xsl:for-each>
            <xsl:if test="v3:addr">
                <M0:AssociatedEntity.addr>
                    <rdf:Bag>
                        <xsl:for-each select="v3:addr">
                            <rdf:li>
                                <xsl:apply-templates mode="AD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:AssociatedEntity.addr>
            </xsl:if>
            <xsl:if test="v3:telecom">
                <M0:AssociatedEntity.telecom>
                    <rdf:Bag>
                        <xsl:for-each select="v3:telecom">
                            <rdf:li>
                                <xsl:apply-templates mode="TEL" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:AssociatedEntity.telecom>
            </xsl:if>
            <xsl:if test="v3:associatedPerson">
                <M0:AssociatedEntity.associatedPerson>
                    <xsl:for-each select="v3:associatedPerson">
                        <xsl:apply-templates mode="M0_Person" select="."/>
                    </xsl:for-each>
                </M0:AssociatedEntity.associatedPerson>
            </xsl:if>
            <xsl:if test="v3:scopingOrganization">
                <M0:AssociatedEntity.scopingOrganization>
                    <xsl:for-each select="v3:scopingOrganization">
                        <xsl:apply-templates mode="M0_Organization" select="."/>
                    </xsl:for-each>
                </M0:AssociatedEntity.scopingOrganization>
            </xsl:if>
        </M0:AssociatedEntity>
    </xsl:template>
    <xsl:template mode="M0_InFulfillmentOf" match="*">
        <M0:InFulfillmentOf xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:InFulfillmentOf.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:InFulfillmentOf.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:order">
                <M0:InFulfillmentOf.order>
                    <xsl:for-each select="v3:order">
                        <xsl:apply-templates mode="M0_Order" select="."/>
                    </xsl:for-each>
                </M0:InFulfillmentOf.order>
            </xsl:if>
        </M0:InFulfillmentOf>
    </xsl:template>
    <xsl:template mode="M0_Order" match="*">
        <M0:Order xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Order.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Order.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:Order.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Order.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Order.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Order.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:Order.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Order.code>
            </xsl:for-each>
            <xsl:for-each select="v3:priorityCode">
                <M0:Order.priorityCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Order.priorityCode>
            </xsl:for-each>
        </M0:Order>
    </xsl:template>
    <xsl:template mode="M0_DocumentationOf" match="*">
        <M0:DocumentationOf xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:DocumentationOf.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:DocumentationOf.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:serviceEvent">
                <M0:DocumentationOf.serviceEvent>
                    <xsl:for-each select="v3:serviceEvent">
                        <xsl:apply-templates mode="M0_ServiceEvent" select="."/>
                    </xsl:for-each>
                </M0:DocumentationOf.serviceEvent>
            </xsl:if>
        </M0:DocumentationOf>
    </xsl:template>
    <xsl:template mode="M0_ServiceEvent" match="*">
        <M0:ServiceEvent xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ServiceEvent.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ServiceEvent.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:ServiceEvent.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ServiceEvent.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:ServiceEvent.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ServiceEvent.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:ServiceEvent.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:ServiceEvent.code>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:ServiceEvent.effectiveTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:ServiceEvent.effectiveTime>
            </xsl:for-each>
            <xsl:if test="v3:performer">
                <M0:ServiceEvent.performer>
                    <rdf:Bag>
                        <xsl:for-each select="v3:performer">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Performer1" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ServiceEvent.performer>
            </xsl:if>
        </M0:ServiceEvent>
    </xsl:template>
    <xsl:template mode="M0_Performer1" match="*">
        <M0:Performer1 xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Performer1.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Performer1.typeCode>
            </xsl:for-each>
            <xsl:for-each select="v3:functionCode">
                <M0:Performer1.functionCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Performer1.functionCode>
            </xsl:for-each>
            <xsl:for-each select="v3:time">
                <M0:Performer1.time>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:Performer1.time>
            </xsl:for-each>
            <xsl:if test="v3:assignedEntity">
                <M0:Performer1.assignedEntity>
                    <xsl:for-each select="v3:assignedEntity">
                        <xsl:apply-templates mode="M0_AssignedEntity" select="."/>
                    </xsl:for-each>
                </M0:Performer1.assignedEntity>
            </xsl:if>
        </M0:Performer1>
    </xsl:template>
    <xsl:template mode="M0_RelatedDocument" match="*">
        <M0:RelatedDocument xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:RelatedDocument.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:RelatedDocument.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:parentDocument">
                <M0:RelatedDocument.parentDocument>
                    <xsl:for-each select="v3:parentDocument">
                        <xsl:apply-templates mode="M0_ParentDocument" select="."/>
                    </xsl:for-each>
                </M0:RelatedDocument.parentDocument>
            </xsl:if>
        </M0:RelatedDocument>
    </xsl:template>
    <xsl:template mode="M0_ParentDocument" match="*">
        <M0:ParentDocument xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ParentDocument.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ParentDocument.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:ParentDocument.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ParentDocument.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:ParentDocument.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ParentDocument.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:ParentDocument.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:ParentDocument.code>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:ParentDocument.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:ParentDocument.text>
            </xsl:for-each>
            <xsl:for-each select="v3:setId">
                <M0:ParentDocument.setId>
                    <xsl:apply-templates mode="II" select="."/>
                </M0:ParentDocument.setId>
            </xsl:for-each>
            <xsl:for-each select="v3:versionNumber">
                <M0:ParentDocument.versionNumber>
                    <xsl:apply-templates mode="INT" select="."/>
                </M0:ParentDocument.versionNumber>
            </xsl:for-each>
        </M0:ParentDocument>
    </xsl:template>
    <xsl:template mode="M0_Authorization" match="*">
        <M0:Authorization xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Authorization.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Authorization.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:consent">
                <M0:Authorization.consent>
                    <xsl:for-each select="v3:consent">
                        <xsl:apply-templates mode="M0_Consent" select="."/>
                    </xsl:for-each>
                </M0:Authorization.consent>
            </xsl:if>
        </M0:Authorization>
    </xsl:template>
    <xsl:template mode="M0_Consent" match="*">
        <M0:Consent xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Consent.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Consent.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:Consent.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Consent.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Consent.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Consent.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:Consent.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Consent.code>
            </xsl:for-each>
            <xsl:for-each select="v3:statusCode">
                <M0:Consent.statusCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Consent.statusCode>
            </xsl:for-each>
        </M0:Consent>
    </xsl:template>
    <xsl:template mode="M0_Component1" match="*">
        <M0:Component1 xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Component1.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Component1.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:encompassingEncounter">
                <M0:Component1.encompassingEncounter>
                    <xsl:for-each select="v3:encompassingEncounter">
                        <xsl:apply-templates mode="M0_EncompassingEncounter" select="."/>
                    </xsl:for-each>
                </M0:Component1.encompassingEncounter>
            </xsl:if>
        </M0:Component1>
    </xsl:template>
    <xsl:template mode="M0_EncompassingEncounter" match="*">
        <M0:EncompassingEncounter xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:EncompassingEncounter.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:EncompassingEncounter.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:EncompassingEncounter.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:EncompassingEncounter.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:EncompassingEncounter.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:EncompassingEncounter.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:EncompassingEncounter.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:EncompassingEncounter.code>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:EncompassingEncounter.effectiveTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:EncompassingEncounter.effectiveTime>
            </xsl:for-each>
            <xsl:for-each select="v3:dischargeDispositionCode">
                <M0:EncompassingEncounter.dischargeDispositionCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:EncompassingEncounter.dischargeDispositionCode>
            </xsl:for-each>
            <xsl:if test="v3:responsibleParty">
                <M0:EncompassingEncounter.responsibleParty>
                    <xsl:for-each select="v3:responsibleParty">
                        <xsl:apply-templates mode="M0_ResponsibleParty" select="."/>
                    </xsl:for-each>
                </M0:EncompassingEncounter.responsibleParty>
            </xsl:if>
            <xsl:if test="v3:encounterParticipant">
                <M0:EncompassingEncounter.encounterParticipant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:encounterParticipant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_EncounterParticipant" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:EncompassingEncounter.encounterParticipant>
            </xsl:if>
            <xsl:if test="v3:location">
                <M0:EncompassingEncounter.location>
                    <xsl:for-each select="v3:location">
                        <xsl:apply-templates mode="M0_Location" select="."/>
                    </xsl:for-each>
                </M0:EncompassingEncounter.location>
            </xsl:if>
        </M0:EncompassingEncounter>
    </xsl:template>
    <xsl:template mode="M0_ResponsibleParty" match="*">
        <M0:ResponsibleParty xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:ResponsibleParty.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ResponsibleParty.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:assignedEntity">
                <M0:ResponsibleParty.assignedEntity>
                    <xsl:for-each select="v3:assignedEntity">
                        <xsl:apply-templates mode="M0_AssignedEntity" select="."/>
                    </xsl:for-each>
                </M0:ResponsibleParty.assignedEntity>
            </xsl:if>
        </M0:ResponsibleParty>
    </xsl:template>
    <xsl:template mode="M0_EncounterParticipant" match="*">
        <M0:EncounterParticipant xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:EncounterParticipant.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:EncounterParticipant.typeCode>
            </xsl:for-each>
            <xsl:for-each select="v3:time">
                <M0:EncounterParticipant.time>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:EncounterParticipant.time>
            </xsl:for-each>
            <xsl:if test="v3:assignedEntity">
                <M0:EncounterParticipant.assignedEntity>
                    <xsl:for-each select="v3:assignedEntity">
                        <xsl:apply-templates mode="M0_AssignedEntity" select="."/>
                    </xsl:for-each>
                </M0:EncounterParticipant.assignedEntity>
            </xsl:if>
        </M0:EncounterParticipant>
    </xsl:template>
    <xsl:template mode="M0_Location" match="*">
        <M0:Location xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Location.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Location.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:healthCareFacility">
                <M0:Location.healthCareFacility>
                    <xsl:for-each select="v3:healthCareFacility">
                        <xsl:apply-templates mode="M0_HealthCareFacility" select="."/>
                    </xsl:for-each>
                </M0:Location.healthCareFacility>
            </xsl:if>
        </M0:Location>
    </xsl:template>
    <xsl:template mode="M0_HealthCareFacility" match="*">
        <M0:HealthCareFacility xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:HealthCareFacility.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:HealthCareFacility.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:HealthCareFacility.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:HealthCareFacility.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:HealthCareFacility.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:HealthCareFacility.code>
            </xsl:for-each>
            <xsl:if test="v3:location">
                <M0:HealthCareFacility.location>
                    <xsl:for-each select="v3:location">
                        <xsl:apply-templates mode="M0_Place" select="."/>
                    </xsl:for-each>
                </M0:HealthCareFacility.location>
            </xsl:if>
            <xsl:if test="v3:serviceProviderOrganization">
                <M0:HealthCareFacility.serviceProviderOrganization>
                    <xsl:for-each select="v3:serviceProviderOrganization">
                        <xsl:apply-templates mode="M0_Organization" select="."/>
                    </xsl:for-each>
                </M0:HealthCareFacility.serviceProviderOrganization>
            </xsl:if>
        </M0:HealthCareFacility>
    </xsl:template>
    <xsl:template mode="M0_Component2" match="*">
        <M0:Component2 xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Component2.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Component2.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextConductionInd">
                <M0:Component2.contextConductionInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Component2.contextConductionInd>
            </xsl:for-each>
            <xsl:if test="v3:nonXMLBody">
                <M0:Component2.nonXMLBody>
                    <xsl:for-each select="v3:nonXMLBody">
                        <xsl:apply-templates mode="M0_NonXMLBody" select="."/>
                    </xsl:for-each>
                </M0:Component2.nonXMLBody>
            </xsl:if>
            <xsl:if test="v3:structuredBody">
                <M0:Component2.structuredBody>
                    <xsl:for-each select="v3:structuredBody">
                        <xsl:apply-templates mode="M0_StructuredBody" select="."/>
                    </xsl:for-each>
                </M0:Component2.structuredBody>
            </xsl:if>
        </M0:Component2>
    </xsl:template>
    <xsl:template mode="M0_NonXMLBody" match="*">
        <M0:NonXMLBody xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:NonXMLBody.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:NonXMLBody.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:NonXMLBody.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:NonXMLBody.moodCode>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:NonXMLBody.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:NonXMLBody.text>
            </xsl:for-each>
            <xsl:for-each select="v3:confidentialityCode">
                <M0:NonXMLBody.confidentialityCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:NonXMLBody.confidentialityCode>
            </xsl:for-each>
            <xsl:for-each select="v3:languageCode">
                <M0:NonXMLBody.languageCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:NonXMLBody.languageCode>
            </xsl:for-each>
        </M0:NonXMLBody>
    </xsl:template>
    <xsl:template mode="M0_StructuredBody" match="*">
        <M0:StructuredBody xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:StructuredBody.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:StructuredBody.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:StructuredBody.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:StructuredBody.moodCode>
            </xsl:for-each>
            <xsl:for-each select="v3:confidentialityCode">
                <M0:StructuredBody.confidentialityCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:StructuredBody.confidentialityCode>
            </xsl:for-each>
            <xsl:for-each select="v3:languageCode">
                <M0:StructuredBody.languageCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:StructuredBody.languageCode>
            </xsl:for-each>
            <xsl:if test="v3:component">
                <M0:StructuredBody.component>
                    <rdf:Bag>
                        <xsl:for-each select="v3:component">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Component3" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:StructuredBody.component>
            </xsl:if>
        </M0:StructuredBody>
    </xsl:template>
    <xsl:template mode="M0_Component3" match="*">
        <M0:Component3 xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Component3.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Component3.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextConductionInd">
                <M0:Component3.contextConductionInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Component3.contextConductionInd>
            </xsl:for-each>
            <xsl:if test="v3:section">
                <M0:Component3.section>
                    <xsl:for-each select="v3:section">
                        <xsl:apply-templates mode="M0_Section" select="."/>
                    </xsl:for-each>
                </M0:Component3.section>
            </xsl:if>
        </M0:Component3>
    </xsl:template>
    <xsl:template mode="M0_Section" match="*">
        <M0:Section xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Section.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Section.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:Section.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Section.moodCode>
            </xsl:for-each>
            <xsl:for-each select="v3:id">
                <M0:Section.id>
                    <xsl:apply-templates mode="II" select="."/>
                </M0:Section.id>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:Section.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Section.code>
            </xsl:for-each>
            <xsl:for-each select="v3:title">
                <M0:Section.title>
                    <xsl:apply-templates mode="ST" select="."/>
                </M0:Section.title>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:Section.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:Section.text>
            </xsl:for-each>
            <xsl:for-each select="v3:confidentialityCode">
                <M0:Section.confidentialityCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Section.confidentialityCode>
            </xsl:for-each>
            <xsl:for-each select="v3:languageCode">
                <M0:Section.languageCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Section.languageCode>
            </xsl:for-each>
            <xsl:if test="v3:subject">
                <M0:Section.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_Subject" select="."/>
                    </xsl:for-each>
                </M0:Section.subject>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:Section.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Section.author>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:Section.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Section.informant>
            </xsl:if>
            <xsl:if test="v3:entry">
                <M0:Section.entry>
                    <rdf:Bag>
                        <xsl:for-each select="v3:entry">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Entry" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Section.entry>
            </xsl:if>
            <xsl:if test="v3:component">
                <M0:Section.component>
                    <rdf:Bag>
                        <xsl:for-each select="v3:component">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Component5" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Section.component>
            </xsl:if>
        </M0:Section>
    </xsl:template>
    <xsl:template mode="M0_Subject" match="*">
        <M0:Subject xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Subject.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Subject.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextControlCode">
                <M0:Subject.contextControlCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Subject.contextControlCode>
            </xsl:for-each>
            <xsl:for-each select="v3:awarenessCode">
                <M0:Subject.awarenessCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Subject.awarenessCode>
            </xsl:for-each>
            <xsl:if test="v3:relatedSubject">
                <M0:Subject.relatedSubject>
                    <xsl:for-each select="v3:relatedSubject">
                        <xsl:apply-templates mode="M0_RelatedSubject" select="."/>
                    </xsl:for-each>
                </M0:Subject.relatedSubject>
            </xsl:if>
        </M0:Subject>
    </xsl:template>
    <xsl:template mode="M0_RelatedSubject" match="*">
        <M0:RelatedSubject xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:RelatedSubject.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:RelatedSubject.classCode>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:RelatedSubject.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:RelatedSubject.code>
            </xsl:for-each>
            <xsl:if test="v3:addr">
                <M0:RelatedSubject.addr>
                    <rdf:Bag>
                        <xsl:for-each select="v3:addr">
                            <rdf:li>
                                <xsl:apply-templates mode="AD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:RelatedSubject.addr>
            </xsl:if>
            <xsl:if test="v3:telecom">
                <M0:RelatedSubject.telecom>
                    <rdf:Bag>
                        <xsl:for-each select="v3:telecom">
                            <rdf:li>
                                <xsl:apply-templates mode="TEL" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:RelatedSubject.telecom>
            </xsl:if>
            <xsl:if test="v3:subject">
                <M0:RelatedSubject.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_SubjectPerson" select="."/>
                    </xsl:for-each>
                </M0:RelatedSubject.subject>
            </xsl:if>
        </M0:RelatedSubject>
    </xsl:template>
    <xsl:template mode="M0_SubjectPerson" match="*">
        <M0:SubjectPerson xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:SubjectPerson.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:SubjectPerson.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:SubjectPerson.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:SubjectPerson.determinerCode>
            </xsl:for-each>
            <xsl:if test="v3:name">
                <M0:SubjectPerson.name>
                    <rdf:Bag>
                        <xsl:for-each select="v3:name">
                            <rdf:li>
                                <xsl:apply-templates mode="PN" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:SubjectPerson.name>
            </xsl:if>
            <xsl:for-each select="v3:administrativeGenderCode">
                <M0:SubjectPerson.administrativeGenderCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:SubjectPerson.administrativeGenderCode>
            </xsl:for-each>
            <xsl:for-each select="v3:birthTime">
                <M0:SubjectPerson.birthTime>
                    <xsl:apply-templates mode="TS" select="."/>
                </M0:SubjectPerson.birthTime>
            </xsl:for-each>
        </M0:SubjectPerson>
    </xsl:template>
    <xsl:template mode="M0_Entry" match="*">
        <M0:Entry xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Entry.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Entry.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextConductionInd">
                <M0:Entry.contextConductionInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Entry.contextConductionInd>
            </xsl:for-each>
            <xsl:if test="v3:act">
                <M0:Entry.act>
                    <xsl:for-each select="v3:act">
                        <xsl:apply-templates mode="M0_Act" select="."/>
                    </xsl:for-each>
                </M0:Entry.act>
            </xsl:if>
            <xsl:if test="v3:encounter">
                <M0:Entry.encounter>
                    <xsl:for-each select="v3:encounter">
                        <xsl:apply-templates mode="M0_Encounter" select="."/>
                    </xsl:for-each>
                </M0:Entry.encounter>
            </xsl:if>
            <xsl:if test="v3:observation">
                <M0:Entry.observation>
                    <xsl:for-each select="v3:observation">
                        <xsl:apply-templates mode="M0_Observation" select="."/>
                    </xsl:for-each>
                </M0:Entry.observation>
            </xsl:if>
            <xsl:if test="v3:observationMedia">
                <M0:Entry.observationMedia>
                    <xsl:for-each select="v3:observationMedia">
                        <xsl:apply-templates mode="M0_ObservationMedia" select="."/>
                    </xsl:for-each>
                </M0:Entry.observationMedia>
            </xsl:if>
            <xsl:if test="v3:organizer">
                <M0:Entry.organizer>
                    <xsl:for-each select="v3:organizer">
                        <xsl:apply-templates mode="M0_Organizer" select="."/>
                    </xsl:for-each>
                </M0:Entry.organizer>
            </xsl:if>
            <xsl:if test="v3:procedure">
                <M0:Entry.procedure>
                    <xsl:for-each select="v3:procedure">
                        <xsl:apply-templates mode="M0_Procedure" select="."/>
                    </xsl:for-each>
                </M0:Entry.procedure>
            </xsl:if>
            <xsl:if test="v3:regionOfInterest">
                <M0:Entry.regionOfInterest>
                    <xsl:for-each select="v3:regionOfInterest">
                        <xsl:apply-templates mode="M0_RegionOfInterest" select="."/>
                    </xsl:for-each>
                </M0:Entry.regionOfInterest>
            </xsl:if>
            <xsl:if test="v3:substanceAdministration">
                <M0:Entry.substanceAdministration>
                    <xsl:for-each select="v3:substanceAdministration">
                        <xsl:apply-templates mode="M0_SubstanceAdministration" select="."/>
                    </xsl:for-each>
                </M0:Entry.substanceAdministration>
            </xsl:if>
            <xsl:if test="v3:supply">
                <M0:Entry.supply>
                    <xsl:for-each select="v3:supply">
                        <xsl:apply-templates mode="M0_Supply" select="."/>
                    </xsl:for-each>
                </M0:Entry.supply>
            </xsl:if>
        </M0:Entry>
    </xsl:template>
    <xsl:template mode="M0_Act" match="*">
        <M0:Act xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Act.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Act.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:Act.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Act.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Act.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Act.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:Act.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:Act.code>
            </xsl:for-each>
            <xsl:for-each select="@negationInd">
                <M0:Act.negationInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Act.negationInd>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:Act.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:Act.text>
            </xsl:for-each>
            <xsl:for-each select="v3:statusCode">
                <M0:Act.statusCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Act.statusCode>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:Act.effectiveTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:Act.effectiveTime>
            </xsl:for-each>
            <xsl:for-each select="v3:priorityCode">
                <M0:Act.priorityCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Act.priorityCode>
            </xsl:for-each>
            <xsl:for-each select="v3:languageCode">
                <M0:Act.languageCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Act.languageCode>
            </xsl:for-each>
            <xsl:if test="v3:subject">
                <M0:ClinicalStatement.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_Subject" select="."/>
                    </xsl:for-each>
                </M0:ClinicalStatement.subject>
            </xsl:if>
            <xsl:if test="v3:specimen">
                <M0:ClinicalStatement.specimen>
                    <rdf:Bag>
                        <xsl:for-each select="v3:specimen">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Specimen" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.specimen>
            </xsl:if>
            <xsl:if test="v3:performer">
                <M0:ClinicalStatement.performer>
                    <rdf:Bag>
                        <xsl:for-each select="v3:performer">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Performer2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.performer>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:ClinicalStatement.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.author>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:ClinicalStatement.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.informant>
            </xsl:if>
            <xsl:if test="v3:participant">
                <M0:ClinicalStatement.participant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:participant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Participant2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.participant>
            </xsl:if>
            <xsl:if test="v3:entryRelationship">
                <M0:ClinicalStatement.entryRelationship>
                    <rdf:Bag>
                        <xsl:for-each select="v3:entryRelationship">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_EntryRelationship" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.entryRelationship>
            </xsl:if>
            <xsl:if test="v3:reference">
                <M0:ClinicalStatement.reference>
                    <rdf:Bag>
                        <xsl:for-each select="v3:reference">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Reference" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.reference>
            </xsl:if>
            <xsl:if test="v3:precondition">
                <M0:ClinicalStatement.precondition>
                    <rdf:Bag>
                        <xsl:for-each select="v3:precondition">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Precondition" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.precondition>
            </xsl:if>
        </M0:Act>
    </xsl:template>
    <xsl:template mode="M0_Encounter" match="*">
        <M0:Encounter xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Encounter.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Encounter.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:Encounter.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Encounter.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Encounter.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Encounter.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:Encounter.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:Encounter.code>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:Encounter.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:Encounter.text>
            </xsl:for-each>
            <xsl:for-each select="v3:statusCode">
                <M0:Encounter.statusCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Encounter.statusCode>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:Encounter.effectiveTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:Encounter.effectiveTime>
            </xsl:for-each>
            <xsl:for-each select="v3:priorityCode">
                <M0:Encounter.priorityCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Encounter.priorityCode>
            </xsl:for-each>
            <xsl:if test="v3:subject">
                <M0:ClinicalStatement.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_Subject" select="."/>
                    </xsl:for-each>
                </M0:ClinicalStatement.subject>
            </xsl:if>
            <xsl:if test="v3:specimen">
                <M0:ClinicalStatement.specimen>
                    <rdf:Bag>
                        <xsl:for-each select="v3:specimen">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Specimen" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.specimen>
            </xsl:if>
            <xsl:if test="v3:performer">
                <M0:ClinicalStatement.performer>
                    <rdf:Bag>
                        <xsl:for-each select="v3:performer">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Performer2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.performer>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:ClinicalStatement.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.author>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:ClinicalStatement.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.informant>
            </xsl:if>
            <xsl:if test="v3:participant">
                <M0:ClinicalStatement.participant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:participant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Participant2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.participant>
            </xsl:if>
            <xsl:if test="v3:entryRelationship">
                <M0:ClinicalStatement.entryRelationship>
                    <rdf:Bag>
                        <xsl:for-each select="v3:entryRelationship">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_EntryRelationship" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.entryRelationship>
            </xsl:if>
            <xsl:if test="v3:reference">
                <M0:ClinicalStatement.reference>
                    <rdf:Bag>
                        <xsl:for-each select="v3:reference">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Reference" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.reference>
            </xsl:if>
            <xsl:if test="v3:precondition">
                <M0:ClinicalStatement.precondition>
                    <rdf:Bag>
                        <xsl:for-each select="v3:precondition">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Precondition" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.precondition>
            </xsl:if>
        </M0:Encounter>
    </xsl:template>
    <xsl:template mode="M0_Observation" match="*">
        <M0:Observation xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Observation.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Observation.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:Observation.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Observation.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Observation.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Observation.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:Observation.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:Observation.code>
            </xsl:for-each>
            <xsl:for-each select="@negationInd">
                <M0:Observation.negationInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Observation.negationInd>
            </xsl:for-each>
            <xsl:for-each select="v3:derivationExpr">
                <M0:Observation.derivationExpr>
                    <xsl:apply-templates mode="ST" select="."/>
                </M0:Observation.derivationExpr>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:Observation.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:Observation.text>
            </xsl:for-each>
            <xsl:for-each select="v3:statusCode">
                <M0:Observation.statusCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Observation.statusCode>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:Observation.effectiveTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:Observation.effectiveTime>
            </xsl:for-each>
            <xsl:for-each select="v3:priorityCode">
                <M0:Observation.priorityCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Observation.priorityCode>
            </xsl:for-each>
            <xsl:for-each select="v3:repeatNumber">
                <M0:Observation.repeatNumber>
                    <xsl:apply-templates mode="IVL_INT" select="."/>
                </M0:Observation.repeatNumber>
            </xsl:for-each>
            <xsl:for-each select="v3:languageCode">
                <M0:Observation.languageCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Observation.languageCode>
            </xsl:for-each>
            <xsl:for-each select="v3:value">
                <M0:Observation.value>
                    <xsl:apply-templates mode="ANY" select="."/>
                </M0:Observation.value>
            </xsl:for-each>
            <xsl:if test="v3:interpretationCode">
                <M0:Observation.interpretationCode>
                    <rdf:Bag>
                        <xsl:for-each select="v3:interpretationCode">
                            <rdf:li>
                                <xsl:apply-templates mode="CE" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Observation.interpretationCode>
            </xsl:if>
            <xsl:if test="v3:methodCode">
                <M0:Observation.methodCode>
                    <rdf:Bag>
                        <xsl:for-each select="v3:methodCode">
                            <rdf:li>
                                <xsl:apply-templates mode="CE" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Observation.methodCode>
            </xsl:if>
            <xsl:if test="v3:targetSiteCode">
                <M0:Observation.targetSiteCode>
                    <rdf:Bag>
                        <xsl:for-each select="v3:targetSiteCode">
                            <rdf:li>
                                <xsl:apply-templates mode="CD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Observation.targetSiteCode>
            </xsl:if>
            <xsl:if test="v3:subject">
                <M0:ClinicalStatement.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_Subject" select="."/>
                    </xsl:for-each>
                </M0:ClinicalStatement.subject>
            </xsl:if>
            <xsl:if test="v3:specimen">
                <M0:ClinicalStatement.specimen>
                    <rdf:Bag>
                        <xsl:for-each select="v3:specimen">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Specimen" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.specimen>
            </xsl:if>
            <xsl:if test="v3:performer">
                <M0:ClinicalStatement.performer>
                    <rdf:Bag>
                        <xsl:for-each select="v3:performer">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Performer2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.performer>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:ClinicalStatement.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.author>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:ClinicalStatement.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.informant>
            </xsl:if>
            <xsl:if test="v3:participant">
                <M0:ClinicalStatement.participant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:participant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Participant2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.participant>
            </xsl:if>
            <xsl:if test="v3:entryRelationship">
                <M0:ClinicalStatement.entryRelationship>
                    <rdf:Bag>
                        <xsl:for-each select="v3:entryRelationship">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_EntryRelationship" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.entryRelationship>
            </xsl:if>
            <xsl:if test="v3:reference">
                <M0:ClinicalStatement.reference>
                    <rdf:Bag>
                        <xsl:for-each select="v3:reference">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Reference" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.reference>
            </xsl:if>
            <xsl:if test="v3:precondition">
                <M0:ClinicalStatement.precondition>
                    <rdf:Bag>
                        <xsl:for-each select="v3:precondition">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Precondition" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.precondition>
            </xsl:if>
            <xsl:if test="v3:referenceRange">
                <M0:Observation.referenceRange>
                    <rdf:Bag>
                        <xsl:for-each select="v3:referenceRange">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_ReferenceRange" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Observation.referenceRange>
            </xsl:if>
        </M0:Observation>
    </xsl:template>
    <xsl:template mode="M0_ReferenceRange" match="*">
        <M0:ReferenceRange xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:ReferenceRange.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ReferenceRange.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:observationRange">
                <M0:ReferenceRange.observationRange>
                    <xsl:for-each select="v3:observationRange">
                        <xsl:apply-templates mode="M0_ObservationRange" select="."/>
                    </xsl:for-each>
                </M0:ReferenceRange.observationRange>
            </xsl:if>
        </M0:ReferenceRange>
    </xsl:template>
    <xsl:template mode="M0_ObservationRange" match="*">
        <M0:ObservationRange xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ObservationRange.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ObservationRange.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:ObservationRange.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ObservationRange.moodCode>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:ObservationRange.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:ObservationRange.code>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:ObservationRange.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:ObservationRange.text>
            </xsl:for-each>
            <xsl:for-each select="v3:value">
                <M0:ObservationRange.value>
                    <xsl:apply-templates mode="ANY" select="."/>
                </M0:ObservationRange.value>
            </xsl:for-each>
            <xsl:for-each select="v3:interpretationCode">
                <M0:ObservationRange.interpretationCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:ObservationRange.interpretationCode>
            </xsl:for-each>
        </M0:ObservationRange>
    </xsl:template>
    <xsl:template mode="M0_ObservationMedia" match="*">
        <M0:ObservationMedia xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ObservationMedia.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ObservationMedia.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:ObservationMedia.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ObservationMedia.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:ObservationMedia.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ObservationMedia.id>
            </xsl:if>
            <xsl:for-each select="v3:languageCode">
                <M0:ObservationMedia.languageCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ObservationMedia.languageCode>
            </xsl:for-each>
            <xsl:for-each select="v3:value">
                <M0:ObservationMedia.value>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:ObservationMedia.value>
            </xsl:for-each>
            <xsl:if test="v3:subject">
                <M0:ClinicalStatement.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_Subject" select="."/>
                    </xsl:for-each>
                </M0:ClinicalStatement.subject>
            </xsl:if>
            <xsl:if test="v3:specimen">
                <M0:ClinicalStatement.specimen>
                    <rdf:Bag>
                        <xsl:for-each select="v3:specimen">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Specimen" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.specimen>
            </xsl:if>
            <xsl:if test="v3:performer">
                <M0:ClinicalStatement.performer>
                    <rdf:Bag>
                        <xsl:for-each select="v3:performer">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Performer2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.performer>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:ClinicalStatement.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.author>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:ClinicalStatement.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.informant>
            </xsl:if>
            <xsl:if test="v3:participant">
                <M0:ClinicalStatement.participant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:participant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Participant2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.participant>
            </xsl:if>
            <xsl:if test="v3:entryRelationship">
                <M0:ClinicalStatement.entryRelationship>
                    <rdf:Bag>
                        <xsl:for-each select="v3:entryRelationship">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_EntryRelationship" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.entryRelationship>
            </xsl:if>
            <xsl:if test="v3:reference">
                <M0:ClinicalStatement.reference>
                    <rdf:Bag>
                        <xsl:for-each select="v3:reference">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Reference" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.reference>
            </xsl:if>
            <xsl:if test="v3:precondition">
                <M0:ClinicalStatement.precondition>
                    <rdf:Bag>
                        <xsl:for-each select="v3:precondition">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Precondition" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.precondition>
            </xsl:if>
        </M0:ObservationMedia>
    </xsl:template>
    <xsl:template mode="M0_Organizer" match="*">
        <M0:Organizer xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Organizer.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Organizer.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:Organizer.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Organizer.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Organizer.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Organizer.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:Organizer.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:Organizer.code>
            </xsl:for-each>
            <xsl:for-each select="v3:statusCode">
                <M0:Organizer.statusCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Organizer.statusCode>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:Organizer.effectiveTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:Organizer.effectiveTime>
            </xsl:for-each>
            <xsl:if test="v3:subject">
                <M0:ClinicalStatement.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_Subject" select="."/>
                    </xsl:for-each>
                </M0:ClinicalStatement.subject>
            </xsl:if>
            <xsl:if test="v3:specimen">
                <M0:ClinicalStatement.specimen>
                    <rdf:Bag>
                        <xsl:for-each select="v3:specimen">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Specimen" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.specimen>
            </xsl:if>
            <xsl:if test="v3:performer">
                <M0:ClinicalStatement.performer>
                    <rdf:Bag>
                        <xsl:for-each select="v3:performer">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Performer2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.performer>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:ClinicalStatement.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.author>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:ClinicalStatement.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.informant>
            </xsl:if>
            <xsl:if test="v3:participant">
                <M0:ClinicalStatement.participant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:participant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Participant2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.participant>
            </xsl:if>
            <xsl:if test="v3:entryRelationship">
                <M0:ClinicalStatement.entryRelationship>
                    <rdf:Bag>
                        <xsl:for-each select="v3:entryRelationship">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_EntryRelationship" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.entryRelationship>
            </xsl:if>
            <xsl:if test="v3:reference">
                <M0:ClinicalStatement.reference>
                    <rdf:Bag>
                        <xsl:for-each select="v3:reference">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Reference" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.reference>
            </xsl:if>
            <xsl:if test="v3:precondition">
                <M0:ClinicalStatement.precondition>
                    <rdf:Bag>
                        <xsl:for-each select="v3:precondition">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Precondition" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.precondition>
            </xsl:if>
            <xsl:if test="v3:component">
                <M0:Organizer.component>
                    <rdf:Bag>
                        <xsl:for-each select="v3:component">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Component4" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Organizer.component>
            </xsl:if>
        </M0:Organizer>
    </xsl:template>
    <xsl:template mode="M0_Component4" match="*">
        <M0:Component4 xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Component4.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Component4.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextConductionInd">
                <M0:Component4.contextConductionInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Component4.contextConductionInd>
            </xsl:for-each>
            <xsl:for-each select="v3:sequenceNumber">
                <M0:Component4.sequenceNumber>
                    <xsl:apply-templates mode="INT" select="."/>
                </M0:Component4.sequenceNumber>
            </xsl:for-each>
            <xsl:for-each select="v3:seperatableInd">
                <M0:Component4.seperatableInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Component4.seperatableInd>
            </xsl:for-each>
            <xsl:if test="v3:act">
                <M0:Component4.act>
                    <xsl:for-each select="v3:act">
                        <xsl:apply-templates mode="M0_Act" select="."/>
                    </xsl:for-each>
                </M0:Component4.act>
            </xsl:if>
            <xsl:if test="v3:encounter">
                <M0:Component4.encounter>
                    <xsl:for-each select="v3:encounter">
                        <xsl:apply-templates mode="M0_Encounter" select="."/>
                    </xsl:for-each>
                </M0:Component4.encounter>
            </xsl:if>
            <xsl:if test="v3:observation">
                <M0:Component4.observation>
                    <xsl:for-each select="v3:observation">
                        <xsl:apply-templates mode="M0_Observation" select="."/>
                    </xsl:for-each>
                </M0:Component4.observation>
            </xsl:if>
            <xsl:if test="v3:observationMedia">
                <M0:Component4.observationMedia>
                    <xsl:for-each select="v3:observationMedia">
                        <xsl:apply-templates mode="M0_ObservationMedia" select="."/>
                    </xsl:for-each>
                </M0:Component4.observationMedia>
            </xsl:if>
            <xsl:if test="v3:organizer">
                <M0:Component4.organizer>
                    <xsl:for-each select="v3:organizer">
                        <xsl:apply-templates mode="M0_Organizer" select="."/>
                    </xsl:for-each>
                </M0:Component4.organizer>
            </xsl:if>
            <xsl:if test="v3:procedure">
                <M0:Component4.procedure>
                    <xsl:for-each select="v3:procedure">
                        <xsl:apply-templates mode="M0_Procedure" select="."/>
                    </xsl:for-each>
                </M0:Component4.procedure>
            </xsl:if>
            <xsl:if test="v3:regionOfInterest">
                <M0:Component4.regionOfInterest>
                    <xsl:for-each select="v3:regionOfInterest">
                        <xsl:apply-templates mode="M0_RegionOfInterest" select="."/>
                    </xsl:for-each>
                </M0:Component4.regionOfInterest>
            </xsl:if>
            <xsl:if test="v3:substanceAdministration">
                <M0:Component4.substanceAdministration>
                    <xsl:for-each select="v3:substanceAdministration">
                        <xsl:apply-templates mode="M0_SubstanceAdministration" select="."/>
                    </xsl:for-each>
                </M0:Component4.substanceAdministration>
            </xsl:if>
            <xsl:if test="v3:supply">
                <M0:Component4.supply>
                    <xsl:for-each select="v3:supply">
                        <xsl:apply-templates mode="M0_Supply" select="."/>
                    </xsl:for-each>
                </M0:Component4.supply>
            </xsl:if>
        </M0:Component4>
    </xsl:template>
    <xsl:template mode="M0_Procedure" match="*">
        <M0:Procedure xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Procedure.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Procedure.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:Procedure.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Procedure.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Procedure.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Procedure.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:Procedure.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:Procedure.code>
            </xsl:for-each>
            <xsl:for-each select="@negationInd">
                <M0:Procedure.negationInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Procedure.negationInd>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:Procedure.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:Procedure.text>
            </xsl:for-each>
            <xsl:for-each select="v3:statusCode">
                <M0:Procedure.statusCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Procedure.statusCode>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:Procedure.effectiveTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:Procedure.effectiveTime>
            </xsl:for-each>
            <xsl:for-each select="v3:priorityCode">
                <M0:Procedure.priorityCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Procedure.priorityCode>
            </xsl:for-each>
            <xsl:for-each select="v3:languageCode">
                <M0:Procedure.languageCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Procedure.languageCode>
            </xsl:for-each>
            <xsl:if test="v3:methodCode">
                <M0:Procedure.methodCode>
                    <rdf:Bag>
                        <xsl:for-each select="v3:methodCode">
                            <rdf:li>
                                <xsl:apply-templates mode="CE" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Procedure.methodCode>
            </xsl:if>
            <xsl:if test="v3:approachSiteCode">
                <M0:Procedure.approachSiteCode>
                    <rdf:Bag>
                        <xsl:for-each select="v3:approachSiteCode">
                            <rdf:li>
                                <xsl:apply-templates mode="CD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Procedure.approachSiteCode>
            </xsl:if>
            <xsl:if test="v3:targetSiteCode">
                <M0:Procedure.targetSiteCode>
                    <rdf:Bag>
                        <xsl:for-each select="v3:targetSiteCode">
                            <rdf:li>
                                <xsl:apply-templates mode="CD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Procedure.targetSiteCode>
            </xsl:if>
            <xsl:if test="v3:subject">
                <M0:ClinicalStatement.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_Subject" select="."/>
                    </xsl:for-each>
                </M0:ClinicalStatement.subject>
            </xsl:if>
            <xsl:if test="v3:specimen">
                <M0:ClinicalStatement.specimen>
                    <rdf:Bag>
                        <xsl:for-each select="v3:specimen">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Specimen" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.specimen>
            </xsl:if>
            <xsl:if test="v3:performer">
                <M0:ClinicalStatement.performer>
                    <rdf:Bag>
                        <xsl:for-each select="v3:performer">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Performer2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.performer>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:ClinicalStatement.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.author>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:ClinicalStatement.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.informant>
            </xsl:if>
            <xsl:if test="v3:participant">
                <M0:ClinicalStatement.participant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:participant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Participant2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.participant>
            </xsl:if>
            <xsl:if test="v3:entryRelationship">
                <M0:ClinicalStatement.entryRelationship>
                    <rdf:Bag>
                        <xsl:for-each select="v3:entryRelationship">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_EntryRelationship" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.entryRelationship>
            </xsl:if>
            <xsl:if test="v3:reference">
                <M0:ClinicalStatement.reference>
                    <rdf:Bag>
                        <xsl:for-each select="v3:reference">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Reference" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.reference>
            </xsl:if>
            <xsl:if test="v3:precondition">
                <M0:ClinicalStatement.precondition>
                    <rdf:Bag>
                        <xsl:for-each select="v3:precondition">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Precondition" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.precondition>
            </xsl:if>
        </M0:Procedure>
    </xsl:template>
    <xsl:template mode="M0_RegionOfInterest" match="*">
        <M0:RegionOfInterest xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:RegionOfInterest.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:RegionOfInterest.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:RegionOfInterest.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:RegionOfInterest.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:RegionOfInterest.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:RegionOfInterest.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:RegionOfInterest.code>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:RegionOfInterest.code>
            </xsl:for-each>
            <xsl:if test="v3:value">
                <M0:RegionOfInterest.value>
                    <rdf:Seq>
                        <xsl:for-each select="v3:value">
                            <rdf:li>
                                <xsl:apply-templates mode="INT" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Seq>
                </M0:RegionOfInterest.value>
            </xsl:if>
            <xsl:if test="v3:subject">
                <M0:ClinicalStatement.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_Subject" select="."/>
                    </xsl:for-each>
                </M0:ClinicalStatement.subject>
            </xsl:if>
            <xsl:if test="v3:specimen">
                <M0:ClinicalStatement.specimen>
                    <rdf:Bag>
                        <xsl:for-each select="v3:specimen">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Specimen" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.specimen>
            </xsl:if>
            <xsl:if test="v3:performer">
                <M0:ClinicalStatement.performer>
                    <rdf:Bag>
                        <xsl:for-each select="v3:performer">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Performer2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.performer>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:ClinicalStatement.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.author>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:ClinicalStatement.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.informant>
            </xsl:if>
            <xsl:if test="v3:participant">
                <M0:ClinicalStatement.participant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:participant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Participant2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.participant>
            </xsl:if>
            <xsl:if test="v3:entryRelationship">
                <M0:ClinicalStatement.entryRelationship>
                    <rdf:Bag>
                        <xsl:for-each select="v3:entryRelationship">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_EntryRelationship" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.entryRelationship>
            </xsl:if>
            <xsl:if test="v3:reference">
                <M0:ClinicalStatement.reference>
                    <rdf:Bag>
                        <xsl:for-each select="v3:reference">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Reference" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.reference>
            </xsl:if>
            <xsl:if test="v3:precondition">
                <M0:ClinicalStatement.precondition>
                    <rdf:Bag>
                        <xsl:for-each select="v3:precondition">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Precondition" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.precondition>
            </xsl:if>
        </M0:RegionOfInterest>
    </xsl:template>
    <xsl:template mode="M0_SubstanceAdministration" match="*">
        <M0:SubstanceAdministration xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:SubstanceAdministration.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:SubstanceAdministration.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:SubstanceAdministration.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:SubstanceAdministration.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:SubstanceAdministration.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:SubstanceAdministration.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:SubstanceAdministration.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:SubstanceAdministration.code>
            </xsl:for-each>
            <xsl:for-each select="@negationInd">
                <M0:SubstanceAdministration.negationInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:SubstanceAdministration.negationInd>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:SubstanceAdministration.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:SubstanceAdministration.text>
            </xsl:for-each>
            <xsl:for-each select="v3:statusCode">
                <M0:SubstanceAdministration.statusCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:SubstanceAdministration.statusCode>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:SubstanceAdministration.effectiveTime>
                    <xsl:apply-templates mode="GTS" select="."/>
                </M0:SubstanceAdministration.effectiveTime>
            </xsl:for-each>
            <xsl:for-each select="v3:priorityCode">
                <M0:SubstanceAdministration.priorityCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:SubstanceAdministration.priorityCode>
            </xsl:for-each>
            <xsl:for-each select="v3:repeatNumber">
                <M0:SubstanceAdministration.repeatNumber>
                    <xsl:apply-templates mode="IVL_INT" select="."/>
                </M0:SubstanceAdministration.repeatNumber>
            </xsl:for-each>
            <xsl:for-each select="v3:routeCode">
                <M0:SubstanceAdministration.routeCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:SubstanceAdministration.routeCode>
            </xsl:for-each>
            <xsl:if test="v3:approachSiteCode">
                <M0:SubstanceAdministration.approachSiteCode>
                    <rdf:Bag>
                        <xsl:for-each select="v3:approachSiteCode">
                            <rdf:li>
                                <xsl:apply-templates mode="CD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:SubstanceAdministration.approachSiteCode>
            </xsl:if>
            <xsl:for-each select="v3:doseQuantity">
                <M0:SubstanceAdministration.doseQuantity>
                    <xsl:apply-templates mode="IVL_PQ" select="."/>
                </M0:SubstanceAdministration.doseQuantity>
            </xsl:for-each>
            <xsl:for-each select="v3:rateQuantity">
                <M0:SubstanceAdministration.rateQuantity>
                    <xsl:apply-templates mode="IVL_PQ" select="."/>
                </M0:SubstanceAdministration.rateQuantity>
            </xsl:for-each>
            <xsl:for-each select="v3:maxDoseQuantity">
                <M0:SubstanceAdministration.maxDoseQuantity>
                    <xsl:apply-templates mode="RTO_PQ_PQ" select="."/>
                </M0:SubstanceAdministration.maxDoseQuantity>
            </xsl:for-each>
            <xsl:for-each select="v3:administrationUnitCode">
                <M0:SubstanceAdministration.administrationUnitCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:SubstanceAdministration.administrationUnitCode>
            </xsl:for-each>
            <xsl:if test="v3:subject">
                <M0:ClinicalStatement.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_Subject" select="."/>
                    </xsl:for-each>
                </M0:ClinicalStatement.subject>
            </xsl:if>
            <xsl:if test="v3:specimen">
                <M0:ClinicalStatement.specimen>
                    <rdf:Bag>
                        <xsl:for-each select="v3:specimen">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Specimen" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.specimen>
            </xsl:if>
            <xsl:if test="v3:performer">
                <M0:ClinicalStatement.performer>
                    <rdf:Bag>
                        <xsl:for-each select="v3:performer">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Performer2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.performer>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:ClinicalStatement.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.author>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:ClinicalStatement.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.informant>
            </xsl:if>
            <xsl:if test="v3:participant">
                <M0:ClinicalStatement.participant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:participant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Participant2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.participant>
            </xsl:if>
            <xsl:if test="v3:entryRelationship">
                <M0:ClinicalStatement.entryRelationship>
                    <rdf:Bag>
                        <xsl:for-each select="v3:entryRelationship">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_EntryRelationship" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.entryRelationship>
            </xsl:if>
            <xsl:if test="v3:reference">
                <M0:ClinicalStatement.reference>
                    <rdf:Bag>
                        <xsl:for-each select="v3:reference">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Reference" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.reference>
            </xsl:if>
            <xsl:if test="v3:precondition">
                <M0:ClinicalStatement.precondition>
                    <rdf:Bag>
                        <xsl:for-each select="v3:precondition">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Precondition" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.precondition>
            </xsl:if>
            <xsl:if test="v3:consumable">
                <M0:SubstanceAdministration.consumable>
                    <xsl:for-each select="v3:consumable">
                        <xsl:apply-templates mode="M0_Consumable" select="."/>
                    </xsl:for-each>
                </M0:SubstanceAdministration.consumable>
            </xsl:if>
        </M0:SubstanceAdministration>
    </xsl:template>
    <xsl:template mode="M0_Consumable" match="*">
        <M0:Consumable xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Consumable.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Consumable.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:manufacturedProduct">
                <M0:Consumable.manufacturedProduct>
                    <xsl:for-each select="v3:manufacturedProduct">
                        <xsl:apply-templates mode="M0_ManufacturedProduct" select="."/>
                    </xsl:for-each>
                </M0:Consumable.manufacturedProduct>
            </xsl:if>
        </M0:Consumable>
    </xsl:template>
    <xsl:template mode="M0_ManufacturedProduct" match="*">
        <M0:ManufacturedProduct xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ManufacturedProduct.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ManufacturedProduct.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:ManufacturedProduct.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ManufacturedProduct.id>
            </xsl:if>
            <xsl:if test="v3:manufacturedLabeledDrug">
                <M0:ManufacturedProduct.manufacturedLabeledDrug>
                    <xsl:for-each select="v3:manufacturedLabeledDrug">
                        <xsl:apply-templates mode="M0_LabeledDrug" select="."/>
                    </xsl:for-each>
                </M0:ManufacturedProduct.manufacturedLabeledDrug>
            </xsl:if>
            <xsl:if test="v3:manufacturedMaterial">
                <M0:ManufacturedProduct.manufacturedMaterial>
                    <xsl:for-each select="v3:manufacturedMaterial">
                        <xsl:apply-templates mode="M0_Material" select="."/>
                    </xsl:for-each>
                </M0:ManufacturedProduct.manufacturedMaterial>
            </xsl:if>
            <xsl:if test="v3:manufacturerOrganization">
                <M0:ManufacturedProduct.manufacturerOrganization>
                    <xsl:for-each select="v3:manufacturerOrganization">
                        <xsl:apply-templates mode="M0_Organization" select="."/>
                    </xsl:for-each>
                </M0:ManufacturedProduct.manufacturerOrganization>
            </xsl:if>
        </M0:ManufacturedProduct>
    </xsl:template>
    <xsl:template mode="M0_LabeledDrug" match="*">
        <M0:LabeledDrug xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:LabeledDrug.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:LabeledDrug.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:LabeledDrug.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:LabeledDrug.determinerCode>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:LabeledDrug.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:LabeledDrug.code>
            </xsl:for-each>
            <xsl:for-each select="v3:name">
                <M0:LabeledDrug.name>
                    <xsl:apply-templates mode="EN" select="."/>
                </M0:LabeledDrug.name>
            </xsl:for-each>
        </M0:LabeledDrug>
    </xsl:template>
    <xsl:template mode="M0_Material" match="*">
        <M0:Material xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Material.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Material.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:Material.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Material.determinerCode>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:Material.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Material.code>
            </xsl:for-each>
            <xsl:for-each select="v3:name">
                <M0:Material.name>
                    <xsl:apply-templates mode="EN" select="."/>
                </M0:Material.name>
            </xsl:for-each>
            <xsl:for-each select="v3:lotNumberText">
                <M0:Material.lotNumberText>
                    <xsl:apply-templates mode="ST" select="."/>
                </M0:Material.lotNumberText>
            </xsl:for-each>
        </M0:Material>
    </xsl:template>
    <xsl:template mode="M0_Supply" match="*">
        <M0:Supply xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Supply.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Supply.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:Supply.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Supply.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Supply.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Supply.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:Supply.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:Supply.code>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:Supply.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:Supply.text>
            </xsl:for-each>
            <xsl:for-each select="v3:statusCode">
                <M0:Supply.statusCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Supply.statusCode>
            </xsl:for-each>
            <xsl:for-each select="v3:effectiveTime">
                <M0:Supply.effectiveTime>
                    <xsl:apply-templates mode="GTS" select="."/>
                </M0:Supply.effectiveTime>
            </xsl:for-each>
            <xsl:if test="v3:priorityCode">
                <M0:Supply.priorityCode>
                    <rdf:Bag>
                        <xsl:for-each select="v3:priorityCode">
                            <rdf:li>
                                <xsl:apply-templates mode="CE" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Supply.priorityCode>
            </xsl:if>
            <xsl:for-each select="v3:repeatNumber">
                <M0:Supply.repeatNumber>
                    <xsl:apply-templates mode="IVL_INT" select="."/>
                </M0:Supply.repeatNumber>
            </xsl:for-each>
            <xsl:for-each select="v3:independentInd">
                <M0:Supply.independentInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Supply.independentInd>
            </xsl:for-each>
            <xsl:for-each select="v3:quantity">
                <M0:Supply.quantity>
                    <xsl:apply-templates mode="PQ" select="."/>
                </M0:Supply.quantity>
            </xsl:for-each>
            <xsl:for-each select="v3:expectedUseTime">
                <M0:Supply.expectedUseTime>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:Supply.expectedUseTime>
            </xsl:for-each>
            <xsl:if test="v3:subject">
                <M0:ClinicalStatement.subject>
                    <xsl:for-each select="v3:subject">
                        <xsl:apply-templates mode="M0_Subject" select="."/>
                    </xsl:for-each>
                </M0:ClinicalStatement.subject>
            </xsl:if>
            <xsl:if test="v3:specimen">
                <M0:ClinicalStatement.specimen>
                    <rdf:Bag>
                        <xsl:for-each select="v3:specimen">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Specimen" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.specimen>
            </xsl:if>
            <xsl:if test="v3:performer">
                <M0:ClinicalStatement.performer>
                    <rdf:Bag>
                        <xsl:for-each select="v3:performer">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Performer2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.performer>
            </xsl:if>
            <xsl:if test="v3:author">
                <M0:ClinicalStatement.author>
                    <rdf:Bag>
                        <xsl:for-each select="v3:author">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Author" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.author>
            </xsl:if>
            <xsl:if test="v3:informant">
                <M0:ClinicalStatement.informant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:informant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Informant12" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.informant>
            </xsl:if>
            <xsl:if test="v3:participant">
                <M0:ClinicalStatement.participant>
                    <rdf:Bag>
                        <xsl:for-each select="v3:participant">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Participant2" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.participant>
            </xsl:if>
            <xsl:if test="v3:entryRelationship">
                <M0:ClinicalStatement.entryRelationship>
                    <rdf:Bag>
                        <xsl:for-each select="v3:entryRelationship">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_EntryRelationship" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.entryRelationship>
            </xsl:if>
            <xsl:if test="v3:reference">
                <M0:ClinicalStatement.reference>
                    <rdf:Bag>
                        <xsl:for-each select="v3:reference">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Reference" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.reference>
            </xsl:if>
            <xsl:if test="v3:precondition">
                <M0:ClinicalStatement.precondition>
                    <rdf:Bag>
                        <xsl:for-each select="v3:precondition">
                            <rdf:li>
                                <xsl:apply-templates mode="M0_Precondition" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ClinicalStatement.precondition>
            </xsl:if>
            <xsl:if test="v3:product">
                <M0:Supply.product>
                    <xsl:for-each select="v3:product">
                        <xsl:apply-templates mode="M0_Product" select="."/>
                    </xsl:for-each>
                </M0:Supply.product>
            </xsl:if>
        </M0:Supply>
    </xsl:template>
    <xsl:template mode="M0_Product" match="*">
        <M0:Product xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Product.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Product.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:manufacturedProduct">
                <M0:Product.manufacturedProduct>
                    <xsl:for-each select="v3:manufacturedProduct">
                        <xsl:apply-templates mode="M0_ManufacturedProduct" select="."/>
                    </xsl:for-each>
                </M0:Product.manufacturedProduct>
            </xsl:if>
        </M0:Product>
    </xsl:template>
    <xsl:template mode="M0_Specimen" match="*">
        <M0:Specimen xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Specimen.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Specimen.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:specimenRole">
                <M0:Specimen.specimenRole>
                    <xsl:for-each select="v3:specimenRole">
                        <xsl:apply-templates mode="M0_SpecimenRole" select="."/>
                    </xsl:for-each>
                </M0:Specimen.specimenRole>
            </xsl:if>
        </M0:Specimen>
    </xsl:template>
    <xsl:template mode="M0_SpecimenRole" match="*">
        <M0:SpecimenRole xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:SpecimenRole.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:SpecimenRole.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:SpecimenRole.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:SpecimenRole.id>
            </xsl:if>
            <xsl:if test="v3:specimenPlayingEntity">
                <M0:SpecimenRole.specimenPlayingEntity>
                    <xsl:for-each select="v3:specimenPlayingEntity">
                        <xsl:apply-templates mode="M0_PlayingEntity" select="."/>
                    </xsl:for-each>
                </M0:SpecimenRole.specimenPlayingEntity>
            </xsl:if>
        </M0:SpecimenRole>
    </xsl:template>
    <xsl:template mode="M0_PlayingEntity" match="*">
        <M0:PlayingEntity xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:PlayingEntity.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:PlayingEntity.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:PlayingEntity.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:PlayingEntity.determinerCode>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:PlayingEntity.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:PlayingEntity.code>
            </xsl:for-each>
            <xsl:if test="v3:quantity">
                <M0:PlayingEntity.quantity>
                    <rdf:Bag>
                        <xsl:for-each select="v3:quantity">
                            <rdf:li>
                                <xsl:apply-templates mode="PQ" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:PlayingEntity.quantity>
            </xsl:if>
            <xsl:if test="v3:name">
                <M0:PlayingEntity.name>
                    <rdf:Bag>
                        <xsl:for-each select="v3:name">
                            <rdf:li>
                                <xsl:apply-templates mode="PN" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:PlayingEntity.name>
            </xsl:if>
            <xsl:for-each select="v3:desc">
                <M0:PlayingEntity.desc>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:PlayingEntity.desc>
            </xsl:for-each>
        </M0:PlayingEntity>
    </xsl:template>
    <xsl:template mode="M0_Performer2" match="*">
        <M0:Performer2 xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Performer2.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Performer2.typeCode>
            </xsl:for-each>
            <xsl:for-each select="v3:time">
                <M0:Performer2.time>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:Performer2.time>
            </xsl:for-each>
            <xsl:for-each select="v3:modeCode">
                <M0:Performer2.modeCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Performer2.modeCode>
            </xsl:for-each>
            <xsl:if test="v3:assignedEntity">
                <M0:Performer2.assignedEntity>
                    <xsl:for-each select="v3:assignedEntity">
                        <xsl:apply-templates mode="M0_AssignedEntity" select="."/>
                    </xsl:for-each>
                </M0:Performer2.assignedEntity>
            </xsl:if>
        </M0:Performer2>
    </xsl:template>
    <xsl:template mode="M0_Participant2" match="*">
        <M0:Participant2 xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Participant2.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Participant2.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextControlCode">
                <M0:Participant2.contextControlCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Participant2.contextControlCode>
            </xsl:for-each>
            <xsl:for-each select="v3:time">
                <M0:Participant2.time>
                    <xsl:apply-templates mode="IVL_TS" select="."/>
                </M0:Participant2.time>
            </xsl:for-each>
            <xsl:for-each select="v3:awarenessCode">
                <M0:Participant2.awarenessCode>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Participant2.awarenessCode>
            </xsl:for-each>
            <xsl:if test="v3:participantRole">
                <M0:Participant2.participantRole>
                    <xsl:for-each select="v3:participantRole">
                        <xsl:apply-templates mode="M0_ParticipantRole" select="."/>
                    </xsl:for-each>
                </M0:Participant2.participantRole>
            </xsl:if>
        </M0:Participant2>
    </xsl:template>
    <xsl:template mode="M0_ParticipantRole" match="*">
        <M0:ParticipantRole xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ParticipantRole.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ParticipantRole.classCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:ParticipantRole.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ParticipantRole.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:ParticipantRole.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:ParticipantRole.code>
            </xsl:for-each>
            <xsl:if test="v3:addr">
                <M0:ParticipantRole.addr>
                    <rdf:Bag>
                        <xsl:for-each select="v3:addr">
                            <rdf:li>
                                <xsl:apply-templates mode="AD" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ParticipantRole.addr>
            </xsl:if>
            <xsl:if test="v3:telecom">
                <M0:ParticipantRole.telecom>
                    <rdf:Bag>
                        <xsl:for-each select="v3:telecom">
                            <rdf:li>
                                <xsl:apply-templates mode="TEL" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ParticipantRole.telecom>
            </xsl:if>
            <xsl:if test="v3:playingDevice">
                <M0:ParticipantRole.playingDevice>
                    <xsl:for-each select="v3:playingDevice">
                        <xsl:apply-templates mode="M0_Device" select="."/>
                    </xsl:for-each>
                </M0:ParticipantRole.playingDevice>
            </xsl:if>
            <xsl:if test="v3:playingEntity">
                <M0:ParticipantRole.playingEntity>
                    <xsl:for-each select="v3:playingEntity">
                        <xsl:apply-templates mode="M0_PlayingEntity" select="."/>
                    </xsl:for-each>
                </M0:ParticipantRole.playingEntity>
            </xsl:if>
            <xsl:if test="v3:scopingEntity">
                <M0:ParticipantRole.scopingEntity>
                    <xsl:for-each select="v3:scopingEntity">
                        <xsl:apply-templates mode="M0_Entity" select="."/>
                    </xsl:for-each>
                </M0:ParticipantRole.scopingEntity>
            </xsl:if>
        </M0:ParticipantRole>
    </xsl:template>
    <xsl:template mode="M0_Device" match="*">
        <M0:Device xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Device.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Device.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:Device.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Device.determinerCode>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:Device.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Device.code>
            </xsl:for-each>
            <xsl:for-each select="v3:manufacturerModelName">
                <M0:Device.manufacturerModelName>
                    <xsl:apply-templates mode="SC" select="."/>
                </M0:Device.manufacturerModelName>
            </xsl:for-each>
            <xsl:for-each select="v3:softwareName">
                <M0:Device.softwareName>
                    <xsl:apply-templates mode="SC" select="."/>
                </M0:Device.softwareName>
            </xsl:for-each>
        </M0:Device>
    </xsl:template>
    <xsl:template mode="M0_Entity" match="*">
        <M0:Entity xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Entity.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Entity.classCode>
            </xsl:for-each>
            <xsl:for-each select="@determinerCode">
                <M0:Entity.determinerCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Entity.determinerCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:Entity.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:Entity.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:Entity.code>
                    <xsl:apply-templates mode="CE" select="."/>
                </M0:Entity.code>
            </xsl:for-each>
            <xsl:for-each select="v3:desc">
                <M0:Entity.desc>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:Entity.desc>
            </xsl:for-each>
        </M0:Entity>
    </xsl:template>
    <xsl:template mode="M0_EntryRelationship" match="*">
        <M0:EntryRelationship xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:EntryRelationship.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:EntryRelationship.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@inversionInd">
                <M0:EntryRelationship.inversionInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:EntryRelationship.inversionInd>
            </xsl:for-each>
            <xsl:for-each select="@contextConductionInd">
                <M0:EntryRelationship.contextConductionInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:EntryRelationship.contextConductionInd>
            </xsl:for-each>
            <xsl:for-each select="v3:sequenceNumber">
                <M0:EntryRelationship.sequenceNumber>
                    <xsl:apply-templates mode="INT" select="."/>
                </M0:EntryRelationship.sequenceNumber>
            </xsl:for-each>
            <xsl:for-each select="@negationInd">
                <M0:EntryRelationship.negationInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:EntryRelationship.negationInd>
            </xsl:for-each>
            <xsl:for-each select="v3:seperatableInd">
                <M0:EntryRelationship.seperatableInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:EntryRelationship.seperatableInd>
            </xsl:for-each>
            <xsl:if test="v3:act">
                <M0:EntryRelationship.act>
                    <xsl:for-each select="v3:act">
                        <xsl:apply-templates mode="M0_Act" select="."/>
                    </xsl:for-each>
                </M0:EntryRelationship.act>
            </xsl:if>
            <xsl:if test="v3:encounter">
                <M0:EntryRelationship.encounter>
                    <xsl:for-each select="v3:encounter">
                        <xsl:apply-templates mode="M0_Encounter" select="."/>
                    </xsl:for-each>
                </M0:EntryRelationship.encounter>
            </xsl:if>
            <xsl:if test="v3:observation">
                <M0:EntryRelationship.observation>
                    <xsl:for-each select="v3:observation">
                        <xsl:apply-templates mode="M0_Observation" select="."/>
                    </xsl:for-each>
                </M0:EntryRelationship.observation>
            </xsl:if>
            <xsl:if test="v3:observationMedia">
                <M0:EntryRelationship.observationMedia>
                    <xsl:for-each select="v3:observationMedia">
                        <xsl:apply-templates mode="M0_ObservationMedia" select="."/>
                    </xsl:for-each>
                </M0:EntryRelationship.observationMedia>
            </xsl:if>
            <xsl:if test="v3:organizer">
                <M0:EntryRelationship.organizer>
                    <xsl:for-each select="v3:organizer">
                        <xsl:apply-templates mode="M0_Organizer" select="."/>
                    </xsl:for-each>
                </M0:EntryRelationship.organizer>
            </xsl:if>
            <xsl:if test="v3:procedure">
                <M0:EntryRelationship.procedure>
                    <xsl:for-each select="v3:procedure">
                        <xsl:apply-templates mode="M0_Procedure" select="."/>
                    </xsl:for-each>
                </M0:EntryRelationship.procedure>
            </xsl:if>
            <xsl:if test="v3:regionOfInterest">
                <M0:EntryRelationship.regionOfInterest>
                    <xsl:for-each select="v3:regionOfInterest">
                        <xsl:apply-templates mode="M0_RegionOfInterest" select="."/>
                    </xsl:for-each>
                </M0:EntryRelationship.regionOfInterest>
            </xsl:if>
            <xsl:if test="v3:substanceAdministration">
                <M0:EntryRelationship.substanceAdministration>
                    <xsl:for-each select="v3:substanceAdministration">
                        <xsl:apply-templates mode="M0_SubstanceAdministration" select="."/>
                    </xsl:for-each>
                </M0:EntryRelationship.substanceAdministration>
            </xsl:if>
            <xsl:if test="v3:supply">
                <M0:EntryRelationship.supply>
                    <xsl:for-each select="v3:supply">
                        <xsl:apply-templates mode="M0_Supply" select="."/>
                    </xsl:for-each>
                </M0:EntryRelationship.supply>
            </xsl:if>
        </M0:EntryRelationship>
    </xsl:template>
    <xsl:template mode="M0_Reference" match="*">
        <M0:Reference xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Reference.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Reference.typeCode>
            </xsl:for-each>
            <xsl:for-each select="v3:seperatableInd">
                <M0:Reference.seperatableInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Reference.seperatableInd>
            </xsl:for-each>
            <xsl:if test="v3:externalAct">
                <M0:Reference.externalAct>
                    <xsl:for-each select="v3:externalAct">
                        <xsl:apply-templates mode="M0_ExternalAct" select="."/>
                    </xsl:for-each>
                </M0:Reference.externalAct>
            </xsl:if>
            <xsl:if test="v3:externalDocument">
                <M0:Reference.externalDocument>
                    <xsl:for-each select="v3:externalDocument">
                        <xsl:apply-templates mode="M0_ExternalDocument" select="."/>
                    </xsl:for-each>
                </M0:Reference.externalDocument>
            </xsl:if>
            <xsl:if test="v3:externalObservation">
                <M0:Reference.externalObservation>
                    <xsl:for-each select="v3:externalObservation">
                        <xsl:apply-templates mode="M0_ExternalObservation" select="."/>
                    </xsl:for-each>
                </M0:Reference.externalObservation>
            </xsl:if>
            <xsl:if test="v3:externalProcedure">
                <M0:Reference.externalProcedure>
                    <xsl:for-each select="v3:externalProcedure">
                        <xsl:apply-templates mode="M0_ExternalProcedure" select="."/>
                    </xsl:for-each>
                </M0:Reference.externalProcedure>
            </xsl:if>
        </M0:Reference>
    </xsl:template>
    <xsl:template mode="M0_ExternalAct" match="*">
        <M0:ExternalAct xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ExternalAct.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ExternalAct.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:ExternalAct.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ExternalAct.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:ExternalAct.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ExternalAct.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:ExternalAct.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:ExternalAct.code>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:ExternalAct.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:ExternalAct.text>
            </xsl:for-each>
        </M0:ExternalAct>
    </xsl:template>
    <xsl:template mode="M0_ExternalDocument" match="*">
        <M0:ExternalDocument xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ExternalDocument.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ExternalDocument.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:ExternalDocument.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ExternalDocument.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:ExternalDocument.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ExternalDocument.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:ExternalDocument.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:ExternalDocument.code>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:ExternalDocument.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:ExternalDocument.text>
            </xsl:for-each>
            <xsl:for-each select="v3:setId">
                <M0:ExternalDocument.setId>
                    <xsl:apply-templates mode="II" select="."/>
                </M0:ExternalDocument.setId>
            </xsl:for-each>
            <xsl:for-each select="v3:versionNumber">
                <M0:ExternalDocument.versionNumber>
                    <xsl:apply-templates mode="INT" select="."/>
                </M0:ExternalDocument.versionNumber>
            </xsl:for-each>
        </M0:ExternalDocument>
    </xsl:template>
    <xsl:template mode="M0_ExternalObservation" match="*">
        <M0:ExternalObservation xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ExternalObservation.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ExternalObservation.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:ExternalObservation.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ExternalObservation.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:ExternalObservation.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ExternalObservation.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:ExternalObservation.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:ExternalObservation.code>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:ExternalObservation.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:ExternalObservation.text>
            </xsl:for-each>
        </M0:ExternalObservation>
    </xsl:template>
    <xsl:template mode="M0_ExternalProcedure" match="*">
        <M0:ExternalProcedure xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:ExternalProcedure.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ExternalProcedure.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:ExternalProcedure.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:ExternalProcedure.moodCode>
            </xsl:for-each>
            <xsl:if test="v3:id">
                <M0:ExternalProcedure.id>
                    <rdf:Bag>
                        <xsl:for-each select="v3:id">
                            <rdf:li>
                                <xsl:apply-templates mode="II" select="."/>
                            </rdf:li>
                        </xsl:for-each>
                    </rdf:Bag>
                </M0:ExternalProcedure.id>
            </xsl:if>
            <xsl:for-each select="v3:code">
                <M0:ExternalProcedure.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:ExternalProcedure.code>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:ExternalProcedure.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:ExternalProcedure.text>
            </xsl:for-each>
        </M0:ExternalProcedure>
    </xsl:template>
    <xsl:template mode="M0_Precondition" match="*">
        <M0:Precondition xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Precondition.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Precondition.typeCode>
            </xsl:for-each>
            <xsl:if test="v3:criterion">
                <M0:Precondition.criterion>
                    <xsl:for-each select="v3:criterion">
                        <xsl:apply-templates mode="M0_Criterion" select="."/>
                    </xsl:for-each>
                </M0:Precondition.criterion>
            </xsl:if>
        </M0:Precondition>
    </xsl:template>
    <xsl:template mode="M0_Criterion" match="*">
        <M0:Criterion xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@classCode">
                <M0:Criterion.classCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Criterion.classCode>
            </xsl:for-each>
            <xsl:for-each select="@moodCode">
                <M0:Criterion.moodCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Criterion.moodCode>
            </xsl:for-each>
            <xsl:for-each select="v3:code">
                <M0:Criterion.code>
                    <xsl:apply-templates mode="CD" select="."/>
                </M0:Criterion.code>
            </xsl:for-each>
            <xsl:for-each select="v3:text">
                <M0:Criterion.text>
                    <xsl:apply-templates mode="ED" select="."/>
                </M0:Criterion.text>
            </xsl:for-each>
            <xsl:for-each select="v3:value">
                <M0:Criterion.value>
                    <xsl:apply-templates mode="ANY" select="."/>
                </M0:Criterion.value>
            </xsl:for-each>
        </M0:Criterion>
    </xsl:template>
    <xsl:template mode="M0_Component5" match="*">
        <M0:Component5 xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#">
            <xsl:call-template name="infrastructureRootStuff"/>
            <xsl:for-each select="@typeCode">
                <M0:Component5.typeCode>
                    <xsl:apply-templates mode="CS" select="."/>
                </M0:Component5.typeCode>
            </xsl:for-each>
            <xsl:for-each select="@contextConductionInd">
                <M0:Component5.contextConductionInd>
                    <xsl:apply-templates mode="BL" select="."/>
                </M0:Component5.contextConductionInd>
            </xsl:for-each>
            <xsl:if test="v3:section">
                <M0:Component5.section>
                    <xsl:for-each select="v3:section">
                        <xsl:apply-templates mode="M0_Section" select="."/>
                    </xsl:for-each>
                </M0:Component5.section>
            </xsl:if>
        </M0:Component5>
    </xsl:template>
</xsl:stylesheet>
