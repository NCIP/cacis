<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="text" encoding="UTF-8"/>
  <xsl:variable name="fieldSeparator" select="/*/MSH/MSH.1"/>
  <xsl:variable name="compSeparator" select="substring(/*/MSH/MSH.2,1,1)"/>
  <xsl:variable name="repSeparator" select="substring(/*/MSH/MSH.2,2,1)"/>
  <xsl:variable name="escapeChar" select="substring(/*/MSH/MSH.2,3,1)"/>
  <xsl:variable name="subCompSeparator" select="substring(/*/MSH/MSH.2,4,1)"/>
  <xsl:template match="/">
    <xsl:apply-templates mode="v2ToXML" select="."/>
  </xsl:template>
  <xsl:template mode="v2ToXML" match="*">
    <xsl:apply-templates mode="v2ToXML" select="*"/>
  </xsl:template>
  <xsl:template mode="v2ToXML" match="text()[normalize-space(.)='']"/>
  <xsl:template mode="v2ToXML" match="*[string-length(name(.))=3]">
    <xsl:value-of select="name(.)"/>
    <xsl:if test="not(self::MSH)">
      <xsl:value-of select="$fieldSeparator"/>
    </xsl:if>
    <xsl:call-template name="doV2Content">
      <xsl:with-param name="position">
        <xsl:choose>
          <xsl:when test="self::MSH">2</xsl:when>
          <xsl:otherwise>1</xsl:otherwise>
        </xsl:choose>
      </xsl:with-param>
      <xsl:with-param name="separator" select="$fieldSeparator"/>
    </xsl:call-template>
    <xsl:text>&#x0A;</xsl:text>
  </xsl:template>
  <xsl:template name="doV2Content">
    <xsl:param name="position"/>
    <xsl:param name="lastPosition" select="number(substring-after(name(*[last()]), '.'))"/>
    <xsl:param name="separator"/>
    <xsl:if test="$position!=1">
      <xsl:value-of select="$separator"/>
    </xsl:if>
    <xsl:for-each select="*[ends-with(name(.), concat('.', $position))]">
      <xsl:if test="position()!=1">
        <xsl:value-of select="$repSeparator"/>
      </xsl:if>
      <xsl:choose>
        <xsl:when test="*">
          <xsl:call-template name="doV2Content">
            <xsl:with-param name="position" select="1"/>
            <xsl:with-param name="separator">
              <xsl:choose>
                <xsl:when test="$separator=$fieldSeparator">
                  <xsl:value-of select="$compSeparator"/>
                </xsl:when>
                <xsl:when test="$separator=$compSeparator">
                  <xsl:value-of select="$subCompSeparator"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:message terminate="no">Cannot handle sub-sub components</xsl:message>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:choose>
            <xsl:when test="self::MSH.2">
              <xsl:value-of select="."/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates mode="escapeV2Content" select="text()"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
    <xsl:if test="$position&lt;$lastPosition">
      <xsl:call-template name="doV2Content">
        <xsl:with-param name="position" select="$position + 1"/>
        <xsl:with-param name="lastPosition" select="$lastPosition"/>
        <xsl:with-param name="separator" select="$separator"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:template>
  <xsl:template mode="escapeV2Content" match="text()[normalize-space(.)='']"/>
  <xsl:template mode="escapeV2Content" match="text()">
    <xsl:variable name="escapedEscape">
      <xsl:call-template name="escapeV2">
        <xsl:with-param name="text" select="."/>
        <xsl:with-param name="search" select="$escapeChar"/>
        <xsl:with-param name="replace" select="'E'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="escapedField">
      <xsl:call-template name="escapeV2">
        <xsl:with-param name="text" select="$escapedEscape"/>
        <xsl:with-param name="search" select="$fieldSeparator"/>
        <xsl:with-param name="replace" select="'F'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="escapedComponent">
      <xsl:call-template name="escapeV2">
        <xsl:with-param name="text" select="$escapedField"/>
        <xsl:with-param name="search" select="$compSeparator"/>
        <xsl:with-param name="replace" select="'S'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="escapedSubComponent">
      <xsl:call-template name="escapeV2">
        <xsl:with-param name="text" select="$escapedComponent"/>
        <xsl:with-param name="search" select="$subCompSeparator"/>
        <xsl:with-param name="replace" select="'T'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="escapedRepetition">
      <xsl:call-template name="escapeV2">
        <xsl:with-param name="text" select="$escapedSubComponent"/>
        <xsl:with-param name="search" select="$repSeparator"/>
        <xsl:with-param name="replace" select="'R'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:call-template name="escapeV2">
      <xsl:with-param name="text" select="$escapedRepetition"/>
      <xsl:with-param name="search" select="'&#x0A;'"/>
      <xsl:with-param name="replace" select="'.br'"/>
    </xsl:call-template>
  </xsl:template>
  <xsl:template name="escapeV2">
    <xsl:param name="text"/>
    <xsl:param name="search"/>
    <xsl:param name="replace"/>
    <xsl:choose>
      <xsl:when test="contains($text, $search)">
        <xsl:choose>
          <xsl:when test="$search=$escapeChar and starts-with(substring-after($text, $escapeChar), '.')">
            <!-- Don't escape an existing escape sequence -->
            <xsl:value-of select="concat(substring-before($text, $escapeChar), $escapeChar, substring-before(substring-after($text, $escapeChar), $escapeChar), $escapeChar)"/>
            <xsl:call-template name="escapeV2">
              <xsl:with-param name="text" select="substring-after(substring-after($text, $escapeChar), $escapeChar)"/>
              <xsl:with-param name="search" select="$search"/>
              <xsl:with-param name="replace" select="$replace"/>
            </xsl:call-template>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="concat(substring-before($text, $search), $escapeChar, $replace, $escapeChar)"/>
            <xsl:call-template name="escapeV2">
              <xsl:with-param name="text" select="substring-after($text, $search)"/>
              <xsl:with-param name="search" select="$search"/>
              <xsl:with-param name="replace" select="$replace"/>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$text"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
</xsl:stylesheet>
