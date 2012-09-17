<?xml version="1.0" encoding="UTF-8"?>
<!--
  - This transform converts a CDAr2 document into an HL7 v2.3 MDM message containing a rendered view of the CDA document.
  -
  - It performs this via a number of passes
  - 1. Run a transform to render the CDA r2 document as XHTML
  - 2. Transform the XHTML into a simpler markup scheme reflecting what HL7 v2's FT datatype can support
  - 3. Process the simply marked-up text to account for line width so that it renders nicely on fixed-width displays
  - 4. Convert the marked up text into HL7 v2 FT codes
  - 5. Using metadata from the document, create an XML view of the HL7 v2 instance
  - 6. Turn the v2-ish XML format into a standard v2 message, handling escaping as necessary
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:nci="gov.nci/functions"  xmlns:v3="urn:hl7-org:v3" xmlns:n1="urn:hl7-org:v3" exclude-result-prefixes="xs nci v3 n1">
  <xsl:import href="CDA.xsl"/>
  <xsl:output method="text" version="1.0" encoding="UTF-8" indent="no"/>
  <xsl:param name="SendingApp" as="xs:string" select="'Unspecified App'"/>
  <xsl:param name="SendingFacility" as="xs:string" select="'Unspecified Facility'"/>
  <xsl:param name="ReceivingApp" as="xs:string" select="'Unspecified App'"/>
  <xsl:param name="ReceivingFacility" as="xs:string" select="'Unspecified Facility'"/>
  <xsl:param name="MessageId" as="xs:string" select="'0'"/>
  <xsl:param name="ProcessingId" as="xs:string" select="'T'"/>
  <!-- The following parameters are optional -->
  <xsl:param name="pageWidth" as="xs:integer" select="60"/>
  <xsl:param name="maxOBXContent" as="xs:integer" select="99999"/>
  <!-- The following separators could be turned into params too if desired -->
  <xsl:variable name="FieldSep" as="xs:string" select="'|'"/>
  <xsl:variable name="CompSep" as="xs:string" select="'^'"/>
  <xsl:variable name="SubSep" as="xs:string" select="'&amp;'"/>
  <xsl:variable name="RepSep" as="xs:string" select="'~'"/>
  <xsl:variable name="Escape" as="xs:string" select="'/'"/>
  <!--
    - Begin processing steps
    -->
  <xsl:variable name="html" as="element(html)">
    <!-- Step 1 - render the document into HTML -->
    <xsl:apply-templates select="v3:ClinicalDocument"/>
  </xsl:variable>
  <xsl:variable name="simpleMarkup" as="element(markup)">
    <!-- Step 2 - Turn the HTML into simple markup -->
    <markup>
      <xsl:apply-templates mode="xhtmlToMarkupXML" select="$html/body/*[self::h1 or self::h2]"/>
    </markup>
  </xsl:variable>
  <xsl:variable name="wrappedMarkup" as="node()+">
    <!-- Step 3 - Turn the simple markup into even simpler markup with line wrapping -->
    <xsl:apply-templates mode="wrapText" select="$simpleMarkup/node()[1]">
      <xsl:with-param name="baseLength" select="$pageWidth"/>
      <xsl:with-param name="remainingLength" select="$pageWidth"/>
      <xsl:with-param name="contextWidth" select="$pageWidth"/>
    </xsl:apply-templates>
  </xsl:variable>
  <xsl:variable name="hl7FT" as="text()">
    <!-- Step 4 - convert the lines and markup into HL7 v2 FT format -->
    <xsl:variable name="text" as="text()+">
      <!-- Mark the text as non-wrapping -->
      <xsl:value-of select="concat($Escape, '.nf', $Escape)"/>
      <xsl:apply-templates mode="markupToFT" select="$wrappedMarkup"/>
    </xsl:variable>
    <xsl:value-of select="string-join($text, '')"/>
  </xsl:variable>
	<!--==============================================================
     - Root transform
     -==============================================================-->
	<xsl:template match="/" priority="10">
    <!-- Step 5 - Create HL7 v2 XML structure -->
    <xsl:variable name="XMLMessage" as="element(Segment)+">
      <xsl:apply-templates mode="v3ToSegments" select="//v3:ClinicalDocument"/>
    </xsl:variable>
    <!-- Step 6 - Turn XML structure into vertical bar syntax -->
    <xsl:variable name="v2" as="xs:string+">
      <xsl:apply-templates mode="v2" select="$XMLMessage"/>
    </xsl:variable>
    <xsl:value-of select="string-join($v2, '')"/>
	</xsl:template>
<!-- Use this to see a rendered version of the v2 text
	<xsl:template match="/" priority="20">
    <xsl:call-template name="markupToText">
      <xsl:with-param name="content" select="$wrappedMarkup"/>
    </xsl:call-template>
	</xsl:template>
-->
	<!--==============================================================
	   - Step 1: Handled via imported transform
     -==============================================================-->
	<!--==============================================================
	   - Step 2: Turn XHTML into simplified markup
     -==============================================================-->
  <xsl:template mode="xhtmlToMarkupXML" match="*" as="node()+">
    <!-- If this shows up, then the rendered CDA document includes HTML markup
       - we don't know how to convert.  Seeing as that could be a patient safety
       - issue, we terminate the process -->
    <xsl:message terminate="yes" select="concat('Error: Unhandled HTML element: ', name(.))"/>
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="h1|h2" as="node()+">
    <!-- This should only appear once, and we render as bold, centered text -->
    <xsl:if test="preceding-sibling::h1">
      <!-- If we're not the first heading, include a blank line before the heading -->
      <br/>
    </xsl:if>
    <center>
      <highlight>
        <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
      </highlight>
    </center>
    <br/>
    <!-- Content beneath a heading will be intended.  Content beneath a heading is everything between this heading and the next heading.
      However, because there might be Sub-headings, we separately process all content following this heading prior to the first Subheading
      and then all of the Subheadings before the next heading -->
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="following-sibling::*[not(self::h2 or self::h3)][count(preceding-sibling::h3)=count(current()/preceding-sibling::h3)][count(preceding-sibling::*[self::h1 or self::h2])=count(current()/preceding-sibling::*[self::h1 or self::h2])+1]"/>
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="following-sibling::h3[count(preceding-sibling::*[self::h1 or self::h2])=count(current()/preceding-sibling::*[self::h1 or self::h2]) + 1]"/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="h3[normalize-space(.)='']" as="empty-sequence()">
    <!-- If a heading is empty, we ignore it -->
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="h3" as="node()+">
    <!-- Capture the heading (we'll number and bold it later) with a line break before
       - and indent the content underneath -->
    <br/>
    <heading>
      <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
    </heading>
    <br/>
    <!-- Content within the section is everything following this header but prior to the next header -->
    <xsl:value-of select="'  '"/>
    <indent>
      <xsl:apply-templates mode="xhtmlToMarkupXML" select="following-sibling::node()[not(self::h3)][count(preceding-sibling::h3)=count(current()/preceding-sibling::h3) + 1][count(preceding-sibling::h2)=count(current()/preceding-sibling::h2)]"/>
    </indent>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="hr" as="element(line)">
    <line>
      <xsl:copy-of select="@align"/>
      <xsl:apply-templates mode="xhtmlToMarkupXML" select="@width"/>
    </line>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="br" as="element(br)">
    <br/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="p" as="node()+">
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
    <br/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="img" as="node()+">
    <!-- We can't handle images, but can at least indicate where an image should have appeared -->
    <!-- Todo: Should we consider capturing the images in Subsequent OBX repetitions?  If so, how do we manage the references? -->
    <br/>
    <xsl:value-of select="concat('[See image: ', @src, ']')"/>
    <br/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="i|u|sup|Sub" as="node()+">
    <!-- Todo: Is there a nomenclature we can use to expose Subscript or superscript? -->
    <xsl:message select="concat('Document contains markup rendering instructions that cannot be represented in FT: ', name(.))"/>
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="a" as="node()+">
    <!-- For Hyperlink anchors, there won't be content so they'll just be omitted.  For hypertext references, we'll just show the label but not the link. -->
    <!-- Todo: Is there any value in exposing the link? -->
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="div" as="node()*">
    <!-- We ignore divs and just process the content -->
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="big" as="node()+">
    <!-- Big text gets rendered by the node that actually handles the text node itself (gets changed to upper-case) -->
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
  </xsl:template>
  <xsl:template priority="5" mode="xhtmlToMarkupXML" match="text()[normalize-space(.)='']" as="empty-sequence()">
    <!-- Empty text nodes get stripped -->
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="text()" as="text()">
    <!-- If the text is inside a "big" format element, make it upper-case -->
    <xsl:choose>
      <xsl:when test="ancestor::big">
        <xsl:value-of select="upper-case(normalize-space(.))"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="normalize-space(.)"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="span[@style='font-weight:bold;']" as="node()+">
    <!-- This format pattern is used for "titles", so we center as well and put a blank line in front -->
    <br/>
    <center>
      <highlight>
        <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
      </highlight>
    </center>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="span[@class='td_label']|b" as="element(highlight)">
    <!-- This content is made bold -->
    <highlight>
      <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
    </highlight>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="span[not(@style='font-weight:bold;' or @class='td_label')]" as="node()+">
    <!-- This isn't one of the special spans we recognize, so raise a warning -->
    <xsl:message select="concat('Don''t know how to handle specified style on span: ', @style)"/>
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="ul|ol" as="node()+">
    <!-- We ignore the wrapping element that tells what kind of list it is - all formatting is handled at the item level -->
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
    <br/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="li[parent::ul]" as="node()+">
    <!-- This is an unordered list, so use an asterisk as the bullet and indent the content, putting a break after each line -->
    <xsl:text>* </xsl:text>
    <indent>
      <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
    </indent>
    <xsl:if test="following-sibling::li">
      <br/>
    </xsl:if>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="li[parent::ol]" as="node()+">
    <!-- This is an ordered list, so insert the line number and indent the content, putting a break after each line -->
    <xsl:value-of select="concat(count(preceding-sibling::li)+1, '. ')"/>
    <indent>
      <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
    </indent>
    <xsl:if test="following-sibling::li">
      <br/>
    </xsl:if>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="table" as="element()+">
    <!-- Tables are complicated :>
      - 1. Add a line-break before each table
      - 2. Treat all head boody and footer rows as rows, captured in that order
      -    (we don't have pages, so headers just appear at the very top and footers
      -     appear at the very end
      - 3. Figure out the number of columns and extract the percentage of size each
      -    should occupy
      -   - In theory, this can be done by looking at the relative sizes of the text
      -    both the maximum word length and the relative size in terms of overall
      -    length.  However, it turns out that in the specific document we're dealing
      -    with, the relative widths are already declared.  So we don't need the
      -    complicated stuff.  I've left the complicated stuff in (commented out) in
      -    the event it turns out to be needed later, but can't promise it's well
      -    tested. -->
<!--    <br/>-->
    <table>
      <xsl:if test="not(thead[not(*/@colspan)] or tbody/tr[not(@colspan)])">
        <xsl:message terminate="yes">This document contains a table where all rows have at least one cell spanning multiple column.  As such, column sizes cannot be calculated appropriately and may be visually unappealing</xsl:message>
      </xsl:if>
      <xsl:variable name="rows" as="element(row)+">
        <xsl:apply-templates mode="xhtmlToMarkupXML" select="thead/tr"/>
        <xsl:apply-templates mode="xhtmlToMarkupXML" select="tbody/tr"/>
        <xsl:apply-templates mode="xhtmlToMarkupXML" select="tfoot/tr"/>
      </xsl:variable>
      <xsl:variable name="rowCols" as="xs:integer+">
        <xsl:for-each select="thead/tr|tbody/tr|tfoot/tr">
          <xsl:value-of select="sum(*/@colspan) + count(*[not(@colspan)])"/>
        </xsl:for-each>
      </xsl:variable>
<!--      <xsl:variable name="columns">-->
        <xsl:call-template name="colInfo">
          <xsl:with-param name="currentColumn" select="1"/>
          <xsl:with-param name="numCols" select="max($rowCols)"/>
          <xsl:with-param name="rows" select="$rows"/>
        </xsl:call-template>
<!--      </xsl:variable>
      <xsl:apply-templates mode="xhtmlToMarkupXML" mode="determineProportion" select="$columns">
        <xsl:with-param name="columns" select="$columns"/>
      </xsl:apply-templates>-->
      <xsl:copy-of select="$rows"/>
    </table>
  </xsl:template>
<!--  <xsl:template mode="determineProportion" mode="xhtmlToMarkupXML" match="col">
    <xsl:param name="columns"/>
    <xsl:variable name="lengthProp">
      <xsl:choose>
        <xsl:when test="@width!=''">
          <xsl:value-of select="@width"/>
        </xsl:when>
        <xsl:when test="$columns/@width">
          <xsl:value-of select="(1 - sum($columns/@width[.!=''])) div count($columns[@width=''])"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="@maxLength div sum($columns/@maxLength)"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:copy>
      <xsl:copy-of select="@*"/>
      <xsl:attribute name="lengthProp">
        <xsl:value-of select="$lengthProp"/>
      </xsl:attribute>
    </xsl:copy>
  </xsl:template>-->
  <xsl:template mode="xhtmlToMarkupXML" match="tr" as="element(row)">
    <!-- header, footer and body rows are all just "rows".  Special formatting is handled
       - at the cell level -->
    <row>
      <xsl:apply-templates mode="xhtmlToMarkupXML" select="th|td"/>
    </row>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="th" as="element(cell)">
    <!-- header cells are bolded (though we check so as not to declare bold twice) -->
    <cell>
      <xsl:call-template name="cellContent"/>
      <xsl:choose>
        <xsl:when test="not(descendant::b)">
          <highlight>
            <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
          </highlight>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
        </xsl:otherwise>
      </xsl:choose>
    </cell>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="td" as="element(cell)">
    <cell>
      <xsl:call-template name="cellContent"/>
      <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
    </cell>
  </xsl:template>
  <xsl:template name="cellContent" as="attribute()*">
    <xsl:variable name="content" as="node()*">
      <xsl:apply-templates mode="xhtmlToMarkupXML" select="node()"/>
    </xsl:variable>
    <xsl:attribute name="length">
      <xsl:value-of select="string-length(string-join($content, ''))"/>
    </xsl:attribute>
    <xsl:attribute name="maxWord">
      <xsl:call-template name="maxWord">
        <xsl:with-param name="text" select="string-join($content, '')"/>
      </xsl:call-template>
    </xsl:attribute>
    <xsl:copy-of select="@align|@colspan"/>
    <xsl:apply-templates mode="xhtmlToMarkupXML" select="@width"/>
  </xsl:template>
  <xsl:template mode="xhtmlToMarkupXML" match="@width" as="attribute(width)">
    <!-- We assume all widths are expressed as % because that's all we've had to deal with so far -->
    <xsl:attribute name="width" select="number(substring-before(., '%')) div 100.0"/>
  </xsl:template>
  <xsl:template name="maxWord">
    <xsl:param name="text" as="xs:string" required="yes"/>
    <xsl:param name="currentMax" as="xs:integer" required="no" select="0"/>
    <xsl:choose>
      <xsl:when test="contains($text, ' ')">
        <xsl:call-template name="maxWord">
          <xsl:with-param name="text" select="substring-after($text, ' ')"/>
          <xsl:with-param name="currentMax">
            <xsl:choose>
              <xsl:when test="string-length(substring-before($text, ' '))&gt;$currentMax">
                <xsl:value-of select="string-length(substring-before($text, ' '))"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="$currentMax"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test="string-length($text)&gt;$currentMax">
        <xsl:value-of select="string-length($text)"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$currentMax"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template name="colInfo" as="element(col)+">
    <!--
      - Supplements a column with information summarizing the cells inside the column
      - - Grab the maximum width declared for a cell in the column that doesn't have a span 
      -->
    <xsl:param name="currentColumn" as="xs:integer"/>
    <xsl:param name="numCols" as="xs:integer"/>
    <xsl:param name="rows" as="element(row)+"/>
    <col>
      <xsl:variable name="infos" as="element(info)*">
        <xsl:for-each select="$rows">
          <xsl:for-each select="cell[(count(preceding-sibling::cell/@colspan) + count(preceding-sibling::cell[not(@colspan)]))=$currentColumn - 1]">
            <xsl:if test="not(@colspan)">
              <info>
                <xsl:copy-of select="@width|@maxWord|@length"/>
                <xsl:copy-of select="@width"/>
              </info>
            </xsl:if>
          </xsl:for-each>
        </xsl:for-each>
      </xsl:variable>
      <xsl:attribute name="width" select="max($infos/@width)"/>
      <xsl:attribute name="maxWord" select="max($infos/@maxWord)"/>
      <xsl:attribute name="maxLength" select="max($infos/@length)"/>
    </col>
    <xsl:if test="$currentColumn &lt; $numCols">
      <xsl:call-template name="colInfo">
        <xsl:with-param name="currentColumn" select="$currentColumn + 1"/>
        <xsl:with-param name="numCols" select="$numCols"/>
        <xsl:with-param name="rows" select="$rows"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:template>
	<!--==============================================================
	   - Step 3: Further simplify the markup and wrap lines
     -==============================================================-->
  <!-- This step processes elements in depth first manner with each template
       responsible for invoking templates on the next following sibling.  This
       approach is used because the results of the processing of the current
       node determine how much length is available on the current line for
       processing the next sibling node. -->
  <xsl:template mode="wrapText" match="*" as="item()*">
    <xsl:param name="baseLength" as="xs:integer" required="yes">
      <!-- The length of lines allowed based on current level of indentation -->
    </xsl:param>
    <xsl:param name="remainingLength" as="xs:integer" required="yes">
      <!-- The number of characters remaining for the current line -->
    </xsl:param>
    <xsl:param name="contextWidth" as="xs:integer" required="yes"/>
    <xsl:variable name="prefix" as="text()?">
      <!-- Text to insert in front of the text of this element and following elements -->
      <xsl:choose>
        <xsl:when test="self::heading">
          <xsl:value-of select="concat(count(preceding-sibling::heading) + 1, '. ')"/>
        </xsl:when>
        <xsl:when test="self::center">
          <xsl:value-of select="nci:whitespace(xs:integer(floor(($remainingLength - string-length(.)) div 2)))"/>
        </xsl:when>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="content" as="item()*">
      <!-- The wrapped content of this element -->
      <xsl:if test="self::heading or self::center">
        <!-- Always include a line-break before headings and centered content -->
        <xsl:call-template name="newLine">
          <xsl:with-param name="baseLength" select="$baseLength"/>
          <xsl:with-param name="contextWidth" select="$contextWidth"/>
        </xsl:call-template>
      </xsl:if>
      <xsl:copy-of select="$prefix"/>
      <xsl:apply-templates mode="wrapText" select="node()[1]">
        <xsl:with-param name="remainingLength" select="$remainingLength - string-length($prefix)"/>
        <xsl:with-param name="baseLength">
          <xsl:choose>
            <xsl:when test="self::indent">
              <xsl:value-of select="$baseLength - 2"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="$baseLength"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
        <xsl:with-param name="contextWidth" select="$contextWidth"/>
      </xsl:apply-templates>
    </xsl:variable>
    <xsl:choose>
      <xsl:when test="self::indent or self::heading or self::center">
        <!-- We drop indent, heading and center elements as their effects are already handled as part
             of this pass.  So we just include the content, not the wrapping element -->
        <xsl:copy-of select="$content"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:variable name="lines" as="element(line)*">
          <!-- Find the line breaks in the contained content and split the content into
            - a bunch of line elements -->
          <xsl:call-template name="handleLines">
            <xsl:with-param name="content" select="$content"/>
          </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="elementName" as="xs:string" select="name(.)"/>
        <xsl:for-each select="$lines">
          <!-- We wrap each line independently, as markup doesn't necessarily carry across lines in v2 -->
          <xsl:if test="position()!=1">
            <!-- Put in a break for each line -->
            <xsl:call-template name="newLine">
              <xsl:with-param name="baseLength" select="$baseLength"/>
              <xsl:with-param name="contextWidth" select="$contextWidth"/>
            </xsl:call-template>
          </xsl:if>
          <xsl:element name="{$elementName}">
            <xsl:copy-of select="node()"/>
          </xsl:element>
        </xsl:for-each>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:apply-templates mode="wrapText" select="following-sibling::node()[1]">
      <!-- Process the next node in line -->
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="remainingLength" select="$baseLength - string-length(string-join($content[not(following-sibling::br)], ''))"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:apply-templates>
  </xsl:template>
  <xsl:function name="nci:whitespace">
    <!-- Returns a string of spaces with the specified length -->
    <xsl:param name="length" as="xs:integer"/>
    <xsl:value-of select="nci:fillChar(' ', $length)"/>
  </xsl:function>
  <xsl:function name="nci:fillChar" as="text()?">
    <!-- Returns a string consisting of the specified number of repetitions of the string 'char' -->
    <xsl:param name="char" as="xs:string"/>
    <xsl:param name="length" as="xs:integer"/>
    <xsl:if test="$length &gt; 0">
      <xsl:value-of select="concat($char, nci:fillChar($char, $length - 1))"/>
    </xsl:if>
  </xsl:function>
  <xsl:template name="newLine" as="node()+">
    <!-- Inserts a break and indents the appropriate number of characters based on context -->
    <xsl:param name="baseLength" as="xs:integer" required="yes"/>
    <xsl:param name="contextWidth" as="xs:integer" select="$pageWidth"/>
    <br/>
    <xsl:value-of select="nci:whitespace($contextWidth - $baseLength)"/>
  </xsl:template>
  <xsl:template name="handleLines" as="element(line)*">
    <!-- Helper routine that takes a set of nodes and creates 'line' elements, 
       - grouping the nodes into those separated by line-breaks -->
    <xsl:param name="content" as="node()*" required="yes">
      <!-- The content to be processed -->
    </xsl:param>
    <xsl:variable name="contentNode" as="element(node)">
      <!-- Wrap the content in an element so we can use the preceding-sibling axis -->
      <node>
        <xsl:copy-of select="$content"/>
      </node>
    </xsl:variable>
    <xsl:choose>
      <xsl:when test="$contentNode/br">
        <!-- If there's a line-break in the line, then all content prior to the break is a line,
           - then find the lines in the remaining content -->
        <line>
          <xsl:copy-of select="$contentNode/node()[not(self::br or preceding-sibling::br)]"/>
        </line>
        <xsl:call-template name="handleLines">
          <xsl:with-param name="content" select="$contentNode/br[1]/following-sibling::node()"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <!-- The remaining nodes are a line, unless they have no string content in which case we won't
           - bother to avoid having a blank trailing line -->
        <xsl:variable name="stringContent" as="text()" select="$content"/>
        <xsl:if test="$content[not(normalize-space(.)='')]">
          <line>
            <xsl:copy-of select="$content"/>
          </line>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template mode="wrapText" match="br" as="node()+">
    <!-- For a line break, insert a line break (with appropriate indentation, then do the next node -->
    <xsl:param name="baseLength" as="xs:integer" required="yes"/>
    <xsl:param name="contextWidth" as="xs:integer" required="yes"/>
    <xsl:call-template name="newLine">
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:call-template>
    <xsl:apply-templates mode="wrapText" select="following-sibling::node()[1]">
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="remainingLength" select="$baseLength"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:apply-templates>
  </xsl:template>
  <xsl:template mode="wrapText" match="text()" as="node()+">
    <!-- If we're dealing with text, we need to handle line-wrapping -->
    <xsl:param name="baseLength" as="xs:integer" required="yes"/>
    <xsl:param name="remainingLength" as="xs:integer" required="yes"/>
    <xsl:param name="contextWidth" as="xs:integer" required="yes"/>
    <xsl:variable name="content" as="node()+">
      <xsl:choose>
        <xsl:when test="string-length(.) &lt; $remainingLength">
          <xsl:copy-of select="."/>
        </xsl:when>
        <xsl:when test="contains(substring(., 1, $remainingLength), ' ')">
          <!-- The length if the text doesn't fit inside the length available, however there
             - are spaces in the desired text within the length we have available, so we can fit 
             - some of the words.
             - Find the longest set of words from the text that will fit in the remaining space 
             - without going over -->
          <xsl:variable name="longestPhrase" as="text()" select="nci:longestPhrase(., $remainingLength)"/>
          <xsl:value-of select="normalize-space($longestPhrase)"/>
          <xsl:call-template name="newLine">
            <xsl:with-param name="baseLength" select="$baseLength"/>
            <xsl:with-param name="contextWidth" select="$contextWidth"/>
          </xsl:call-template>
          <xsl:variable name="text" as="element(text)">
            <text>
              <xsl:value-of select="substring-after(., $longestPhrase)"/>
            </text>
          </xsl:variable>
          <xsl:apply-templates mode="wrapText" select="$text/text()">
            <xsl:with-param name="baseLength" select="$baseLength"/>
            <xsl:with-param name="remainingLength" select="$baseLength"/>
            <xsl:with-param name="contextWidth" select="$contextWidth"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:when test="contains(substring(., 1, $baseLength), ' ') or ($remainingLength=1 and $baseLength!=1)">
          <!-- The length of the text doesn't fit in the length available, and we can't even fit one
            - of the words in the length available.  However, if we start a new line, at least one of the
            - words will fit in the space available then, so we add a line break and try again. -->
          <xsl:call-template name="newLine">
            <xsl:with-param name="baseLength" select="$baseLength"/>
            <xsl:with-param name="contextWidth" select="$contextWidth"/>
          </xsl:call-template>
          <xsl:variable name="text" as="text()" select="."/>
          <xsl:apply-templates mode="wrapText" select="$text">
            <xsl:with-param name="baseLength" select="$baseLength"/>
            <xsl:with-param name="remainingLength" select="$baseLength"/>
            <xsl:with-param name="contextWidth" select="$contextWidth"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:when test="substring(., $remainingLength + 1, 1) = ' ' or string-length(.)=$remainingLength">
          <!-- The text consists of exactly one word, so we don't need to break it -->
          <xsl:value-of select="substring(., 1, $remainingLength)"/>
          <xsl:call-template name="newLine">
            <xsl:with-param name="baseLength" select="$baseLength"/>
            <xsl:with-param name="contextWidth" select="$contextWidth"/>
          </xsl:call-template>
          <xsl:variable name="text" as="element(text)">
            <text>
              <xsl:copy-of select="substring(., $remainingLength + 2)"/>
            </text>
          </xsl:variable>
          <xsl:apply-templates mode="wrapText" select="$text/text()">
            <xsl:with-param name="baseLength" select="$baseLength"/>
            <xsl:with-param name="remainingLength" select="$baseLength"/>
            <xsl:with-param name="contextWidth" select="$contextWidth"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <!-- We can't fit the text, so we're going to have to truncate -->
          <xsl:value-of select="substring(., 1, $remainingLength - 1)"/>
          <xsl:text>-</xsl:text>
          <xsl:call-template name="newLine">
            <xsl:with-param name="baseLength" select="$baseLength"/>
            <xsl:with-param name="contextWidth" select="$contextWidth"/>
          </xsl:call-template>
          <xsl:variable name="text" as="element(text)">
            <text>
              <xsl:copy-of select="substring(., $remainingLength)"/>
            </text>
          </xsl:variable>
          <xsl:apply-templates mode="wrapText" select="$text/text()">
            <xsl:with-param name="baseLength" select="$baseLength"/>
            <xsl:with-param name="remainingLength" select="$baseLength"/>
            <xsl:with-param name="contextWidth" select="$contextWidth"/>
          </xsl:apply-templates>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="preceding-sibling::highlight and normalize-space(.)!=''">
      <!-- We've normalized space on the text, which might remove spaces between elements
         - and text, so put them back -->
      <xsl:value-of select="' '"/>
    </xsl:if>
    <xsl:copy-of select="$content"/>
    <xsl:if test="following-sibling::highlight and normalize-space(.)!=''">
      <!-- We've normalized space on the text, which might remove spaces between elements
         - and text, so put them back -->
      <xsl:value-of select="' '"/>
    </xsl:if>
    <xsl:apply-templates mode="wrapText" select="following-sibling::node()[1]">
      <!-- Continue on with the next node -->
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="remainingLength" select="$remainingLength - string-length(string-join($content[not(following-sibling::br)], ''))"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:apply-templates>
  </xsl:template>
  <xsl:function name="nci:longestPhrase" as="text()?">
    <!-- Returns the longest set of words (character separated by a space) starting with the beginning
       - of 'text' that will fit within the desired 'length' -->
    <xsl:param name="text" as="xs:string"/>
    <xsl:param name="length" as="xs:integer"/>
    <xsl:if test="contains(substring($text, 1, $length), ' ')">
      <xsl:variable name="word" as="xs:string">
        <xsl:value-of select="concat(substring-before($text, ' '), ' ')"/>
      </xsl:variable>
      <xsl:value-of select="concat($word, nci:longestPhrase(substring-after($text, ' '), $length - string-length($word)))"/>
    </xsl:if>
  </xsl:function>
  <xsl:template mode="wrapText" match="table" as="node()+">
    <!-- Tables are *still* ugly.
      - 1. Go through all the columns except the last and calculate the "actual" lengths by multiplying
      - out the relative proportions by the number of characters we've actually got available (accounting
      - for space taken up by the column borders).  If we didn't have fixed relative proportions, we might
      - also allow for adjustment to minimize the need to truncate words, but we don't have to worry about that
      - now.
      - 2. Determine the length of the last column as either whatever space is needed, or the size of the 
      - proportional column width, whichever is less.
      - 3. Now that we know the actual size of each column, do a pass through each row to figure out the number 
      - of lines needed to fit the content for each cell in thr row
      - 4. Then spit out the table:
      -  a) Start with a blank line
      -  b) Then a separator line, using "+" for the sides and column dividers and "-" elsewhere
      -  c) Then for each row:
      -   i) a line for each line in the cell with the maximum number of lines, padding the content of the
      -   cell with spaces as needed and separating cells with "|"
      -   ii) A separator line 
      -->
    <xsl:param name="baseLength" required="yes" as="xs:integer"/>
    <xsl:param name="contextWidth" required="yes" as="xs:integer"/>
    <xsl:variable name="cellBaseLength" as="xs:integer" select="$baseLength - count(col) - 1">
      <!-- Length available for cells = total available length - space needed for separators -->
    </xsl:variable>
    <xsl:variable name="firstCols" as="element(col)*">
      <!-- Figuring out the space to allocate to columns is *really* complicated.

        First, defining terms:
          maxWord = length of the longest word - ignore multi-span elements
          maxLength = length of the longest paragraph/line  - ignore multi-span elements
          lengthProp = the proportion of maxLength over the total of all maxLengths

        1. If there isn't space for at least two characters per column, then terminate
        
        2. If widths are asserted for all but the last column, use the widths provided (even if it means wrapping words)
        
        3. If maxWord couldn't be calculated, columnWidth = available space - 1/columns, round down (with last column taking up any available extra spaces)
        
        4. If available space > sum (maxLength) then columnWidth = maxLength
        
        5. Else If available space = sum(maxWord) then columWidth = maxWord
        
        6. Else If available space > sum(maxWord) then 
        a) start with columnWidth = maxLength.
        b) Calculate the newlengthProp (on first pass will be same as lengthProp)
        c) Take the first column with a columnWidth > maxWord where newlengthProp - lengthProp is highest and subtract 1 character
        d) iterate to b until available space = sum(columnWidth)
        
        7. else if available space < sum(maxWord) then
        a) start with columWidth = maxWord.
        b) Find the first column having the longest length and drop the length by 1.  Iterate until available space = sum(columnWidth)
        -->
      <xsl:variable name="origCols" select="col" as="element(col)+"/>
      <xsl:choose>
        <xsl:when test="$cellBaseLength &lt; count(col) * 2">
          <xsl:message terminate="yes" select="'Specified page width is too small to allow some tables to be rendered.  Rendering cannot be performed.'"/>
        </xsl:when>
        <xsl:when test="not(col[following-sibling::col][not(@width) or @width=''])">
          <xsl:apply-templates mode="wrapText" select="col[following-sibling::col]">
            <xsl:with-param name="baseLength" select="$cellBaseLength"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:when test="col[not(@maxWord)]">
          <xsl:for-each select="col[following-sibling::col]">
            <xsl:copy>
              <xsl:copy-of select="@*"/>
              <xsl:attribute name="width" select="floor($cellBaseLength div count($origCols))"/>
            </xsl:copy>
          </xsl:for-each>
        </xsl:when>
        <xsl:when test="sum(col/@maxLength) &lt; $cellBaseLength">
          <xsl:for-each select="col[following-sibling::col]">
            <xsl:copy>
              <xsl:copy-of select="@*"/>
              <xsl:attribute name="width" select="@maxLength"/>
            </xsl:copy>
          </xsl:for-each>
        </xsl:when>
        <xsl:when test="sum(col/@maxWord) = $cellBaseLength">
          <xsl:for-each select="col[following-sibling::col]">
            <xsl:copy>
              <xsl:copy-of select="@*"/>
              <xsl:attribute name="width" select="@maxWord"/>
            </xsl:copy>
          </xsl:for-each>
        </xsl:when>
        <xsl:when test="sum(col/@maxWord) &lt; $cellBaseLength">
          <xsl:variable name="cols" as="element(cols)">
            <xsl:variable name="totalLength" as="xs:double" select="sum(col/@maxLength)"/>
            <xsl:call-template name="adjustMaxLength">
              <xsl:with-param name="cellBaseLength" select="$cellBaseLength"/>
              <xsl:with-param name="cols" as="element(cols)">
                <cols>
                  <xsl:for-each select="col">
                    <xsl:copy>
                      <xsl:copy-of select="@*"/>
                      <xsl:attribute name="width" select="@maxLength"/>
                      <xsl:attribute name="lengthProp" select="@maxLength div $totalLength"/>
                      <xsl:attribute name="lengthPropDiff" select="0"/>
                    </xsl:copy>
                  </xsl:for-each>
                </cols>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:variable>
          <xsl:copy-of select="$cols/col[following-sibling::col]"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:variable name="cols" as="element(cols)">
            <xsl:call-template name="adjustMaxWord">
              <xsl:with-param name="cellBaseLength" select="$cellBaseLength"/>
              <xsl:with-param name="cols" as="element(cols)">
                <cols>
                  <xsl:for-each select="col">
                    <xsl:copy>
                      <xsl:copy-of select="@*"/>
                      <xsl:attribute name="width" select="@maxWord"/>
                    </xsl:copy>
                  </xsl:for-each>
                </cols>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:variable>
          <xsl:copy-of select="$cols/col[following-sibling::col]"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="cols" as="element(col)+">
      <!-- Take the first columns and determine the width of the last as either the space
        - remaining or the space allocated, whichever is less -->
      <xsl:copy-of select="$firstCols"/>
      <xsl:variable name="maxWidth" as="xs:integer" select="$cellBaseLength - xs:integer(sum($firstCols/@width))"/>
      <col width="{if (@width and ceiling(@width * $cellBaseLength) &lt; $maxWidth) then ceiling(@width * $cellBaseLength) else $maxWidth}"/>
    </xsl:variable>
    <xsl:variable name="rows" as="element(row)+">
      <!-- Using the column lengths, line-break the cell content for the rows -->
      <xsl:apply-templates mode="wrapText" select="row">
        <xsl:with-param name="cols" select="$cols"/>
      </xsl:apply-templates>
    </xsl:variable>
    <xsl:call-template name="newLine">
      <!-- All tables start on a new line -->
      <xsl:with-param name="baseLength" select="$baseLength"/>
    </xsl:call-template>
    <xsl:call-template name="rowLine">
      <!-- Create a vertical row with column markings -->
      <xsl:with-param name="cols" select="$cols"/>
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:call-template>
    <xsl:apply-templates mode="renderRow" select="$rows">
      <!-- Display the rows of the table -->
      <xsl:with-param name="cols" select="$cols"/>
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:apply-templates>
    <xsl:apply-templates mode="wrapText" select="following-sibling::node()[1]">
      <!-- Do the next element after the table -->
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="remainingLength" select="$baseLength"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:apply-templates>
  </xsl:template>
  <xsl:template name="adjustMaxLength" as="element(cols)">
    <!-- Keep dropping the width of the widest column until we fit in the space available -->
    <xsl:param name="cellBaseLength" as="xs:integer" required="yes"/>
    <xsl:param name="cols" as="element(cols)" required="yes"/>
    <xsl:variable name="totalWidth" as="xs:double" select="sum($cols/col/@width)"/>
    <xsl:choose>
      <xsl:when test="$totalWidth &lt;= $cellBaseLength">
        <xsl:copy-of select="$cols"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:variable name="maxDiff" as="xs:double" select="max($cols/col[xs:integer(@width) &gt; xs:integer(@maxWord)]/@lengthPropDiff)"/>
        <xsl:variable name="maxWidth" as="xs:double" select="max($cols/col[xs:integer(@width) &gt; xs:integer(@maxWord)][@lengthPropDiff=$maxDiff]/@width)"/>
        <xsl:call-template name="adjustMaxLength">
          <xsl:with-param name="cellBaseLength" select="$cellBaseLength"/>
          <xsl:with-param name="cols" as="element(cols)">
            <cols>
              <xsl:for-each select="$cols/col">
                <xsl:copy>
                  <xsl:copy-of select="@*"/>
                  <xsl:choose>
                    <xsl:when test="@lengthPropDiff=$maxDiff and @width=$maxWidth and not(preceding-sibling::col[@lengthPropDiff=$maxDiff and @width=$maxWidth])">
                      <xsl:attribute name="width" select="@width - 1"/>
                      <xsl:attribute name="lengthPropDiff" select="((@width - 1) div ($totalWidth - 1)) - @lengthProp"/>
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:attribute name="lengthPropDiff" select="(@width div ($totalWidth - 1)) - @lengthProp"/>
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:copy>
              </xsl:for-each>
            </cols>
          </xsl:with-param>
        </xsl:call-template>        
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template name="adjustMaxWord" as="element(cols)">
    <!-- Keep dropping the width of the widest column until we fit in the space available -->
    <xsl:param name="cellBaseLength" as="xs:integer" required="yes"/>
    <xsl:param name="cols" as="element(cols)" required="yes"/>
    <xsl:choose>
      <xsl:when test="sum($cols/col/@width)&lt;=$cellBaseLength">
        <xsl:copy-of select="$cols"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="adjustMaxWord">
          <xsl:with-param name="cellBaseLength" select="$cellBaseLength"/>
          <xsl:with-param name="cols" as="element(cols)">
            <cols>
              <xsl:for-each select="$cols/col">
                <xsl:copy>
                  <xsl:copy-of select="@*"/>
                  <xsl:if test="@width = max($cols/col/@width) and not(preceding-sibling::col[@width=current()/@width])">
                    <xsl:attribute name="width" select="@width - 1"/>
                  </xsl:if>
                </xsl:copy>
              </xsl:for-each>
            </cols>
          </xsl:with-param>
        </xsl:call-template>        
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template mode="wrapText" match="col[following-sibling::col]" as="element(col)">
    <!-- Calculate the width of the column based on the specified length, rounding up -->
    <xsl:param name="baseLength" as="xs:integer" required="yes"/>
    <xsl:copy>
      <xsl:copy-of select="@align"/>
      <xsl:attribute name="width">
        <xsl:value-of select="ceiling(@width * $baseLength)"/>
      </xsl:attribute>
    </xsl:copy>
  </xsl:template>
  <xsl:template mode="wrapText" match="row" as="element(row)">
    <!-- In the first pass of the rows, figure out the line wrapping for each cell -->
    <xsl:param name="cols" as="element(col)+" required="yes"/>
    <xsl:copy>
      <xsl:apply-templates mode="wrapText" select="cell[1]">
        <xsl:with-param name="cols" select="$cols"/>
      </xsl:apply-templates>
    </xsl:copy>
  </xsl:template>
  <xsl:template mode="wrapText" match="cell" as="node()+">
    <!-- Determine the wrapping for the content of the cell:
      - 1. Figure out the length as either the length ofr this specific column or the
      -   combined length for all spanned columns + extra space gained from the column
      -   separators
      - 2. Determine the content for the cell and then break it into lines based on
      -  that calculated cell length. -->
    <xsl:param name="cols" as="element(col)+" required="yes"/>
    <xsl:variable name="cellWidth" as="xs:integer">
      <xsl:choose>
        <xsl:when test="@colspan and count($cols)&gt;1">
          <xsl:value-of select="sum($cols[position()&lt;=current()/@colspan]/@width) + min((xs:integer(@colspan), count($cols))) - 1"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$cols[1]/@width"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="cellContent" as="node()*">
      <xsl:apply-templates mode="wrapText" select="node()[1]">
        <xsl:with-param name="baseLength" select="$cellWidth"/>
        <xsl:with-param name="remainingLength" select="$cellWidth"/>
        <xsl:with-param name="contextWidth" select="$cellWidth"/>
      </xsl:apply-templates>
    </xsl:variable>
    <xsl:copy>
      <!-- We pass back the width of the cell so we can use that when padding -->
      <xsl:attribute name="width" select="$cellWidth"/>
      <xsl:call-template name="handleLines">
        <xsl:with-param name="content" select="$cellContent"/>
      </xsl:call-template>
    </xsl:copy>
    <xsl:variable name="remainingCols" as="element(col)*">
      <!-- Identify what columns are still left in the table, taking
        - into account any "spanning" we've done -->
      <xsl:choose>
        <xsl:when test="@colspan">
          <xsl:copy-of select="$cols[position()&gt;$cellWidth]"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:copy-of select="$cols[position()&gt;1]"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:choose>
      <!-- Process the remaining cells (if any), and if not, pad for the remaining empty cells -->
      <xsl:when test="following-sibling::cell">
        <xsl:apply-templates mode="wrapText" select="following-sibling::cell[1]">
          <xsl:with-param name="cols" select="$remainingCols"/>
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:variable name="paddingCells" as="element(cells)">
          <cells>
            <xsl:for-each select="$remainingCols">
              <cell/>
            </xsl:for-each>
          </cells>
        </xsl:variable>
        <xsl:apply-templates mode="wrapText" select="$paddingCells/cell[1]">
          <xsl:with-param name="cols" select="$remainingCols"/>
        </xsl:apply-templates>        
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template name="rowLine" as="node()+">
    <!-- Create a table separator line:
      - 1. start with a "+" for the start of row
      - 2. For each column, specify "-" for the width of the column, then "+" for the separator -->
    <xsl:param name="cols" as="element(col)+" required="yes"/>
    <xsl:param name="baseLength" required="yes" as="xs:integer"/>
    <xsl:param name="contextWidth" required="yes" as="xs:integer"/>
    <xsl:text>+</xsl:text>
    <xsl:for-each select="$cols">
      <xsl:value-of select="concat(nci:fillChar('-', @width), '+')"/>
    </xsl:for-each>
    <xsl:call-template name="newLine">
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:call-template>
  </xsl:template>
  <xsl:template mode="renderRow" match="row" as="node()+">
    <!-- To render a row, first determine the maximum number of lines in any of the cells, then
      - render the row once for each possible line.  Finally, add a row separator line -->
    <xsl:param name="cols" as="element(col)+" required="yes"/>
    <xsl:param name="baseLength" required="yes" as="xs:integer"/>
    <xsl:param name="contextWidth" required="yes" as="xs:integer"/>
    <xsl:variable name="numLines" as="xs:integer" select="if (cell) then max(for $cell in cell return count($cell/line)) else 1"/>
    <xsl:variable name="cells" as="element(cell)+" select="cell"/>
    <xsl:apply-templates mode="renderRowLine" select=".">
      <xsl:with-param name="cols" select="$cols"/>
      <xsl:with-param name="currLine" select="1"/>
      <xsl:with-param name="maxLines" select="$numLines"/>
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:apply-templates>
    <xsl:call-template name="rowLine">
      <xsl:with-param name="cols" select="$cols"/>
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:call-template>
  </xsl:template>
  <xsl:template mode="renderRowLine" match="row" as="node()+">
    <!-- When rendering a row line, start with a "|" for the start of the table,
      - then include the content of the cell, followed by padding for the width of the cell
      - and a following "|" for each cell.  Then show any remaining lines
      - TODO: At present, we're ignoring alignment because it hasn't come into play, but
      - alignment should actually be checked to figgure out where to put the padding. -->
    <xsl:param name="cols" as="element(col)+" required="yes"/>
    <xsl:param name="currLine" as="xs:integer" required="yes"/>
    <xsl:param name="maxLines" as="xs:integer" required="yes"/>
    <xsl:param name="baseLength" required="yes" as="xs:integer"/>
    <xsl:param name="contextWidth" required="yes" as="xs:integer"/>
    <xsl:text>|</xsl:text>
    <xsl:for-each select="cell">
      <xsl:copy-of select="line[position()=$currLine]/node()"/>
      <xsl:value-of select="concat(nci:whitespace(xs:integer(@width - string-length(string-join(line[position()=$currLine]//text(), '')))), '|')"/>
    </xsl:for-each>
    <xsl:call-template name="newLine">
      <xsl:with-param name="baseLength" select="$baseLength"/>
      <xsl:with-param name="contextWidth" select="$contextWidth"/>
    </xsl:call-template>
    <xsl:if test="$currLine &lt; $maxLines">
      <xsl:apply-templates mode="renderRowLine" select=".">
        <xsl:with-param name="cols" select="$cols"/>
        <xsl:with-param name="currLine" select="$currLine + 1"/>
        <xsl:with-param name="maxLines" select="$maxLines"/>
        <xsl:with-param name="baseLength" select="$baseLength"/>
        <xsl:with-param name="contextWidth" select="$contextWidth"/>
      </xsl:apply-templates>
    </xsl:if>
  </xsl:template>
  
	<!--==============================================================
	   - Step 4: Convert the simple markup wrapped lines to HL7 v2 FT
     -==============================================================-->
	<xsl:template mode="markupToFT" priority="5" match="text()" as="text()">
    <!-- Text within an FT needs to have occurrences of the escape character escaped because
       - we can't escape them later - we embed other escape sequences for markup purposes -->
    <xsl:value-of select="replace(., $Escape, concat($Escape, 'E', $Escape))"/>
	</xsl:template>
	<xsl:template mode="markupToFT" match="highlight" as="text()+">
    <!-- Add markup tags for highlighting -->
    <xsl:value-of select="concat($Escape, 'H', $Escape)"/>
    <xsl:apply-templates mode="markupToFT" select="node()"/>
    <xsl:value-of select="concat($Escape, 'N', $Escape)"/>
	</xsl:template>
	<xsl:template mode="markupToFT" match="br" as="text()">
    <!-- Use the FT syntax for marking page breaks -->
    <xsl:value-of select="concat($Escape, '.br', $Escape)"/>
	</xsl:template>
	<xsl:template mode="markupToFT" match="node()">
    <!-- Debugging - check that there aren't any tags we weren't expecting -->
    <xsl:message select="'Unexpected tag when converting markup to FT:'"/>
    <xsl:message terminate="yes" select="."/>
	</xsl:template>
	<!--==============================================================
	   - Render FT content in HTML for debugging
     -==============================================================-->
  <xsl:template name="markupToHTML" as="element(html)">
    <xsl:param name="content" as="node()+"/>
    <html>
      <head>
        <title>Debug</title>
      </head>
      <body>
        <pre>
          <xsl:variable name="lineContent" as="element(line)+">
            <xsl:call-template name="handleLines">
              <xsl:with-param name="content" select="$content"/>
            </xsl:call-template>
          </xsl:variable>
          <xsl:apply-templates mode="markupToHTML" select="$lineContent"/>
        </pre>
      </body>    
    </html>    
  </xsl:template>
	<xsl:template mode="markupToHTML" priority="5" match="text()" as="text()">
    <xsl:value-of select="."/>
	</xsl:template>
	<xsl:template mode="markupToHTML" match="highlight" as="element(b)">
    <b>
      <xsl:apply-templates mode="markupToHTML" select="node()"/>
    </b>
	</xsl:template>
	<xsl:template mode="markupToHTML" match="line" as="element(p)">
    <p>
      <xsl:apply-templates mode="markupToHTML" select="node()"/>
    </p>
	</xsl:template>
	<!--==============================================================
	   - Render FT content in HTML for debugging
     -==============================================================-->
  <xsl:template name="markupToText" as="xs:string">
    <xsl:param name="content" as="node()+"/>
    <xsl:variable name="textContent" as="text()+">
      <xsl:apply-templates mode="markupToText" select="$content"/>
    </xsl:variable>
    <xsl:value-of select="string-join($textContent, '')"/>
  </xsl:template>
	<xsl:template mode="markupToText" priority="5" match="text()" as="text()">
    <xsl:value-of select="."/>
	</xsl:template>
	<xsl:template mode="markupToText" match="highlight" as="text()*">
    <xsl:apply-templates mode="markupToText" select="node()"/>
	</xsl:template>
	<xsl:template mode="markupToText" match="br" as="text()">
    <xsl:text>&#x0a;</xsl:text>
	</xsl:template>
	<!--==============================================================
	   - Step 5: Extract document metadata to create simple v2 XML
     -==============================================================-->
  <xsl:template mode="v3ToSegments" match="v3:ClinicalDocument" as="element(Segment)+">
    <!-- This is pretty ugly, but it works.  Element names are:
       - Segment, Field, Comp, Sub and Repeat -->
    <Segment name="MSH">
      <Field/>
      <Field>
        <xsl:value-of select="concat($CompSep, $RepSep, $Escape, $SubSep)"/>
      </Field>
      <Field>
        <xsl:value-of select="$SendingApp"/>
      </Field>
      <Field>
        <xsl:value-of select="$SendingFacility"/>
      </Field>
      <Field>
        <xsl:value-of select="$ReceivingApp"/>
      </Field>
      <Field>
        <xsl:value-of select="$ReceivingFacility"/>
      </Field>
      <Field>
        <xsl:value-of select="v3:effectiveTime/@value"/>
      </Field>
      <Field/>
      <Field>
        <Comp>MDM</Comp>
        <Comp>T02</Comp>
      </Field>
      <Field>
        <xsl:value-of select="$MessageId"/>
      </Field>
      <Field>
        <xsl:value-of select="$ProcessingId"/>
      </Field>
      <Field>2.3</Field>
      <Field/>
      <Field/>
      <Field>AL</Field>
      <Field>NE</Field>
    </Segment>
    <Segment name="EVN">
      <Field>T02</Field>
      <Field/>
      <Field/>
      <Field/>
      <Field>
        <xsl:for-each select="v3:author/v3:assignedAuthor[v3:assignedPerson][1]">
          <Comp>
            <xsl:value-of select="v3:id[1]/@extension"/>
          </Comp>
          <Comp>
            <xsl:for-each select="v3:assignedPerson/v3:name[1]/v3:family">
              <xsl:if test="position()!=1">
                <xsl:value-of select="' '"/>
              </xsl:if>
              <xsl:value-of select="."/>
            </xsl:for-each>
          </Comp>
          <Comp>
            <xsl:value-of select="v3:assignedPerson/v3:name[1]/v3:given[1]"/>
          </Comp>
          <Comp>
            <xsl:for-each select="v3:assignedPerson/v3:name[1]/v3:given[position()&gt;1]">
              <xsl:if test="position()!=1">
                <xsl:value-of select="' '"/>
              </xsl:if>
              <xsl:value-of select="."/>
            </xsl:for-each>
          </Comp>
          <Comp/>
          <Comp/>
          <Comp/>
          <Comp/>
          <Comp>
            <Sub/>
            <Sub>
              <xsl:value-of select="v3:id[1]/@root"/>
            </Sub>
            <Sub>ISO</Sub>
          </Comp>
        </xsl:for-each>
      </Field>
      <Field>
        <xsl:value-of select="v3:author[1]/v3:time/@value"/>
      </Field>
    </Segment>
    <xsl:if test="v3:recordTarget[2]">
      <xsl:message>Warning: Only one patient can be supported in the v2 extract.  Information on the first patient was extracted, all other patients were ignored.</xsl:message>
    </xsl:if>
    <xsl:for-each select="v3:recordTarget[1]/v3:patientRole">
      <Segment name="PID">
        <Field/>
        <Field/>
        <Field>
          <xsl:for-each select="v3:id">
            <Repeat>
              <Comp>
                <xsl:value-of select="@extension"/>
              </Comp>
              <Comp/>
              <Comp/>
              <Comp>
                <Sub/>
                <Sub>
                  <xsl:value-of select="@root"/>
                </Sub>
                <Sub>ISO</Sub>
              </Comp>
              <Comp>MR</Comp>
            </Repeat>
          </xsl:for-each>
        </Field>
        <Field/>
        <Field>
          <xsl:for-each select="v3:patient/v3:name">
            <Repeat>
              <Comp>
                <xsl:for-each select="v3:family">
                  <xsl:if test="position()!=1">
                    <xsl:value-of select="' '"/>
                  </xsl:if>
                  <xsl:value-of select="."/>
                </xsl:for-each>
              </Comp>
              <Comp>
                <xsl:value-of select="v3:given[1]"/>
              </Comp>
              <Comp>
                <xsl:for-each select="v3:given[position()&gt;1]">
                  <xsl:if test="position()!=1">
                    <xsl:value-of select="' '"/>
                  </xsl:if>
                  <xsl:value-of select="."/>
                </xsl:for-each>
              </Comp>
            </Repeat>
          </xsl:for-each>
        </Field>
        <Field/>
        <Field>
          <xsl:value-of select="v3:patient/v3:birthTime/@value"/>
        </Field>
        <Field>
          <xsl:value-of select="v3:patient/v3:administrativeGenderCode/@code"/>
        </Field>
      </Segment>
    </xsl:for-each>
    <xsl:for-each select="v3:componentOf[1]/v3:encompassingEncounter">
      <Segment name="PV1">
        <Field/>
        <Field>
          <xsl:value-of select="v3:code/@code"/>
        </Field>
        <Field>
          <Comp/>
          <Comp/>
          <Comp/>
          <Comp>
            <xsl:if test="v3:location/v3:healthCareFaclitity/v3:id[2]">
              <xsl:message>Warning: Only one encounter location id can be supported in the v2 extract.  Information on the first encounter location id was extracted, all other encounter location ids were ignored.</xsl:message>
            </xsl:if>
            <xsl:for-each select="v3:location/v3:healthCareFaclitity/v3:id[1]">
              <xsl:value-of select="concat(@root, ':', @id)"/>
            </xsl:for-each>
          </Comp>
        </Field>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field/>
        <Field>
          <xsl:if test="v3:id[2]">
            <xsl:message>Warning: Only one encounter location id can be supported in the v2 extract.  Information on the first encounter location id was extracted, all other encounter location ids were ignored.</xsl:message>
          </xsl:if>
          <xsl:for-each select="v3:id[1]">
            <Comp>
              <xsl:value-of select="@extension"/>
            </Comp>
            <Comp/>
            <Comp/>
            <Comp/>
            <Comp>
              <xsl:value-of select="@root"/>
            </Comp>
            <Comp>ISO</Comp>
            <Comp>VN</Comp>
          </xsl:for-each>
        </Field>
      </Segment>
    </xsl:for-each>
    <xsl:if test="v3:documentationOf[2]">
      <xsl:message>Warning: Only one event can be supported in the v2 extract.  Information on the first event was extracted, all other events were ignored.</xsl:message>
    </xsl:if>
    <Segment name="TXA">
      <Field/>
      <Field>
        <xsl:value-of select="v3:templateId/@root"/>
      </Field>
      <Field/>
      <Field>
        <xsl:value-of select="v3:documentationOf[1]/v3:serviceEvent/v3:effectiveTime/@value"/>
      </Field>
      <Field>
        <xsl:if test="v3:documentationOf[1]/v3:serviceEvent/v3:performer[2]">
          <xsl:message>Warning: Only one event performer can be supported in the v2 extract.  Information on the first event performer was extracted, all other event performers were ignored.</xsl:message>
        </xsl:if>
        <xsl:for-each select="v3:documentationOf[1]/v3:serviceEvent/v3:performer[1]/v3:assignedEntity">
          <xsl:if test="v3:id[2]">
            <xsl:message>Warning: Only one event performer id can be supported in the v2 extract.  Information on the first event performer id was extracted, all other event performer ids were ignored.</xsl:message>
          </xsl:if>
          <xsl:if test="v3:assignedPerson/v3:name[2]">
            <xsl:message>Warning: Only one event performer name can be supported in the v2 extract.  Information on the first event performer name was extracted, all other event performer names were ignored.</xsl:message>
          </xsl:if>
          <Comp>
            <xsl:value-of select="v3:id[1]/@extension"/>
          </Comp>
          <Comp>
            <xsl:for-each select="v3:assignedPerson/v3:name[1]/v3:family">
              <xsl:if test="position()!=1">
                <xsl:value-of select="' '"/>
              </xsl:if>
              <xsl:value-of select="."/>
            </xsl:for-each>
          </Comp>
          <Comp>
            <xsl:value-of select="v3:assignedPerson/v3:name[1]/v3:given[1]"/>
          </Comp>
          <Comp>
            <xsl:for-each select="v3:assignedPerson/v3:name[1]/v3:given[position()&gt;1]">
              <xsl:if test="position()!=1">
                <xsl:value-of select="' '"/>
              </xsl:if>
              <xsl:value-of select="."/>
            </xsl:for-each>
          </Comp>
          <Comp/>
          <Comp/>
          <Comp/>
          <Comp/>
          <Comp>
            <Sub/>
            <Sub>
              <xsl:value-of select="v3:id[1]/@root"/>
            </Sub>
            <Sub>ISO</Sub>
          </Comp>
        </xsl:for-each>
      </Field>
      <Field/>
      <Field>
        <xsl:if test="v3:participant[@typeCode='TRANS'][2]">
          <xsl:message>Warning: Only one set of transcription information can be supported in the v2 extract.  Information on the first set of transcription information was extracted, all other sets of transcription information were ignored.</xsl:message>
        </xsl:if>
        <xsl:value-of select="v3:participant[@typeCode='TRANS'][1]/v3:time/@value"/>
      </Field>
      <Field/>
      <Field>
        <xsl:if test="v3:author/v3:assignedAuthor[v3:assignedPerson][2]">
          <xsl:message>Warning: Only one person author can be supported in the v2 extract.  Information on the first person author was extracted, all other person authors were ignored.</xsl:message>
        </xsl:if>
        <xsl:for-each select="v3:author/v3:assignedAuthor[v3:assignedPerson][1]">
          <xsl:if test="v3:id[2]">
            <xsl:message>Warning: Only one author id can be supported in the v2 extract.  Information on the first author id was extracted, all other author ids were ignored.</xsl:message>
          </xsl:if>
          <xsl:if test="v3:name[2]">
            <xsl:message>Warning: Only one author name can be supported in the v2 extract.  Information on the first author name was extracted, all other author names were ignored.</xsl:message>
          </xsl:if>
          <Comp>
            <xsl:value-of select="v3:id[1]/@extension"/>
          </Comp>
          <Comp>
            <xsl:for-each select="v3:assignedPerson/v3:name[1]/v3:family">
              <xsl:if test="position()!=1">
                <xsl:value-of select="' '"/>
              </xsl:if>
              <xsl:value-of select="."/>
            </xsl:for-each>
          </Comp>
          <Comp>
            <xsl:value-of select="v3:assignedPerson/v3:name[1]/v3:given[1]"/>
          </Comp>
          <Comp>
            <xsl:for-each select="v3:assignedPerson/v3:name[1]/v3:given[position()&gt;1]">
              <xsl:if test="position()!=1">
                <xsl:value-of select="' '"/>
              </xsl:if>
              <xsl:value-of select="."/>
            </xsl:for-each>
          </Comp>
          <Comp/>
          <Comp/>
          <Comp/>
          <Comp/>
          <Comp>
            <Sub/>
            <Sub>
              <xsl:value-of select="v3:id[1]/@root"/>
            </Sub>
            <Sub>ISO</Sub>
          </Comp>
        </xsl:for-each>
      </Field>
      <Field/>
      <Field>
        <xsl:for-each select="v3:participant[@typeCode='TRANS'][1]/v3:assignedEntity">
          <xsl:if test="v3:id[2]">
            <xsl:message>Warning: Only one transcriptionist id can be supported in the v2 extract.  Information on the first transcriptionist id was extracted, all other transcriptionist ids were ignored.</xsl:message>
          </xsl:if>
          <xsl:if test="v3:name[2]">
            <xsl:message>Warning: Only one transcriptionist name can be supported in the v2 extract.  Information on the first transcriptionist name was extracted, all other transcriptionist names were ignored.</xsl:message>
          </xsl:if>
          <Comp>
            <xsl:value-of select="v3:id/@extension"/>
          </Comp>
          <Comp>
            <xsl:for-each select="v3:assignedPerson/v3:name[1]/v3:family">
              <xsl:if test="position()!=1">
                <xsl:value-of select="' '"/>
              </xsl:if>
              <xsl:value-of select="."/>
            </xsl:for-each>
          </Comp>
          <Comp>
            <xsl:value-of select="v3:assignedPerson/v3:name[1]/v3:given[1]"/>
          </Comp>
          <Comp>
            <xsl:for-each select="v3:assignedPerson/v3:name[1]/v3:given[position()&gt;1]">
              <xsl:if test="position()!=1">
                <xsl:value-of select="' '"/>
              </xsl:if>
              <xsl:value-of select="."/>
            </xsl:for-each>
          </Comp>
          <Comp/>
          <Comp/>
          <Comp/>
          <Comp/>
          <Comp>
            <Sub/>
            <Sub>
              <xsl:value-of select="v3:id[1]/@root"/>
            </Sub>
            <Sub>ISO</Sub>
          </Comp>
        </xsl:for-each>
      </Field>
      <Field>
        <Comp>
          <xsl:value-of select="v3:id/@extension"/>
        </Comp>
        <Comp/>
        <Comp>
          <xsl:value-of select="v3:id/@root"/>
        </Comp>
        <Comp>ISO</Comp>
      </Field>
      <Field/>
      <Field/>
      <Field/>
      <Field/>
      <Field>AU</Field>
      <Field>
        <xsl:value-of select="v3:confidentialityCode/@code"/>
      </Field>
      <Field/>
      <Field/>
      <Field/>
      <Field>
        <xsl:choose>
          <xsl:when test="v3:legalAuthenticator">
            <xsl:for-each select="v3:legalAuthenticator">
              <xsl:if test="v3:assignedEntity/v3:id[2]">
                <xsl:message>Warning: Only one legal authenticator id can be supported in the v2 extract.  Information on the first legal authenticator id was extracted, all other legal authenticator ids were ignored.</xsl:message>
              </xsl:if>
              <xsl:if test="v3:assignedEntity/v3:name[2]">
                <xsl:message>Warning: Only one legal authenticator name can be supported in the v2 extract.  Information on the first legal authenticator name was extracted, all other legal authenticator names were ignored.</xsl:message>
              </xsl:if>
              <Comp>
                <xsl:value-of select="v3:assignedEntity/v3:id/@extension"/>
              </Comp>
              <Comp>
                <xsl:for-each select="v3:assignedEntity/v3:assignedPerson/v3:name[1]/v3:family">
                  <xsl:if test="position()!=1">
                    <xsl:value-of select="' '"/>
                  </xsl:if>
                  <xsl:value-of select="."/>
                </xsl:for-each>
              </Comp>
              <Comp>
                <xsl:value-of select="v3:assignedEntity/v3:assignedPerson/v3:name[1]/v3:given[1]"/>
              </Comp>
              <Comp>
                <xsl:for-each select="v3:assignedEntity/v3:assignedPerson/v3:name[1]/v3:given[position()&gt;1]">
                  <xsl:if test="position()!=1">
                    <xsl:value-of select="' '"/>
                  </xsl:if>
                  <xsl:value-of select="."/>
                </xsl:for-each>
              </Comp>
              <Comp/>
              <Comp/>
              <Comp/>
              <Comp/>
              <Comp>
                <Sub/>
                <Sub>
                  <xsl:value-of select="v3:assignedEntity/v3:id[1]/@root"/>
                </Sub>
                <Sub>ISO</Sub>
              </Comp>
              <Comp/>
              <Comp/>
              <Comp/>
              <Comp/>
              <Comp/>
              <Comp>
                <xsl:value-of select="v3:time/@value"/>
              </Comp>
            </xsl:for-each>
          </xsl:when>
          <xsl:otherwise>
            <xsl:if test="v3:authenticator[2]">
              <xsl:message>Warning: Only one authenticator can be supported in the v2 extract.  Information on the first authenticator was extracted, all other authenticators were ignored.</xsl:message>
            </xsl:if>
            <xsl:for-each select="v3:authenticator[1]">
              <xsl:if test="v3:assignedEntity/v3:id[2]">
                <xsl:message>Warning: Only one authenticator id can be supported in the v2 extract.  Information on the first authenticator id was extracted, all other authenticator ids were ignored.</xsl:message>
              </xsl:if>
              <xsl:if test="v3:assignedEntity/v3:name[2]">
                <xsl:message>Warning: Only one authenticator name can be supported in the v2 extract.  Information on the first authenticator name was extracted, all other authenticator names were ignored.</xsl:message>
              </xsl:if>
              <Comp>
                <xsl:value-of select="v3:assignedEntity/v3:id/@extension"/>
              </Comp>
              <Comp>
                <xsl:for-each select="v3:assignedEntity/v3:assignedPerson/v3:name[1]/v3:family">
                  <xsl:if test="position()!=1">
                    <xsl:value-of select="' '"/>
                  </xsl:if>
                  <xsl:value-of select="."/>
                </xsl:for-each>
              </Comp>
              <Comp>
                <xsl:value-of select="v3:assignedEntity/v3:assignedPerson/v3:name[1]/v3:given[1]"/>
              </Comp>
              <Comp>
                <xsl:for-each select="v3:assignedEntity/v3:assignedPerson/v3:name[1]/v3:given[position()&gt;1]">
                  <xsl:if test="position()!=1">
                    <xsl:value-of select="' '"/>
                  </xsl:if>
                  <xsl:value-of select="."/>
                </xsl:for-each>
              </Comp>
              <Comp/>
              <Comp/>
              <Comp/>
              <Comp/>
              <Comp>
                <Sub/>
                <Sub>
                  <xsl:value-of select="v3:assignedEntity/v3:id[1]/@root"/>
                </Sub>
                <Sub>ISO</Sub>
              </Comp>
              <Comp/>
              <Comp/>
              <Comp/>
              <Comp/>
              <Comp/>
              <Comp>
                <xsl:value-of select="v3:time/@value"/>
              </Comp>
            </xsl:for-each>
          </xsl:otherwise>
        </xsl:choose>
      </Field>
      <Field>
        <xsl:for-each select="v3:informationRecipient/v3:intendedRecipient">
          <Repeat>
            <xsl:if test="v3:id[2]">
              <xsl:message>Warning: Only one recipient id can be supported in the v2 extract.  Information on the first recipient id was extracted, all other recipient ids were ignored.</xsl:message>
            </xsl:if>
            <xsl:if test="v3:name[2]">
              <xsl:message>Warning: Only one recipient name can be supported in the v2 extract.  Information on the first recipient name was extracted, all other recipient names were ignored.</xsl:message>
            </xsl:if>
            <Comp>
              <xsl:value-of select="v3:id/@extension"/>
            </Comp>
            <Comp>
              <xsl:for-each select="v3:informationRecipient/v3:name[1]/v3:family">
                <xsl:if test="position()!=1">
                  <xsl:value-of select="' '"/>
                </xsl:if>
                <xsl:value-of select="."/>
              </xsl:for-each>
            </Comp>
            <Comp>
              <xsl:value-of select="v3:informationRecipient/v3:name[1]/v3:given[1]"/>
            </Comp>
            <Comp>
              <xsl:for-each select="v3:informationRecipient/v3:name[1]/v3:given[position()&gt;1]">
                <xsl:if test="position()!=1">
                  <xsl:value-of select="' '"/>
                </xsl:if>
                <xsl:value-of select="."/>
              </xsl:for-each>
            </Comp>
            <Comp/>
            <Comp/>
            <Comp/>
            <Comp/>
            <Comp>
              <Sub/>
              <Sub>
                <xsl:value-of select="v3:id[1]/@root"/>
              </Sub>
              <Sub>ISO</Sub>
            </Comp>
          </Repeat>
        </xsl:for-each>
      </Field>
    </Segment>
    <xsl:variable name="obxContent" as="xs:string+">
      <xsl:call-template name="splitString">
        <xsl:with-param name="text" select="$hl7FT"/>
        <xsl:with-param name="length" select="$maxOBXContent"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="code" as="xs:string" select="v3:code/@code"/>
    <xsl:variable name="display" as="xs:string" select="v3:code/@displayName"/>
    <xsl:for-each select="$obxContent">
      <Segment name="OBX">
        <Field>
          <xsl:value-of select="position()"/>
        </Field>
        <Field>FT</Field>
        <Field>
          <Comp>
            <xsl:value-of select="$code"/>
          </Comp>
          <Comp>
            <xsl:value-of select="$display"/>
          </Comp>
          <Comp>LN</Comp>
        </Field>
        <Field/>
        <Field>
          <NoEscape>
            <xsl:value-of select="."/>
          </NoEscape>
        </Field>
      </Segment>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="splitString" as="xs:string+">
    <!-- Breaks the string 'text' into substrings of the specified length -->
    <xsl:param name="text" as="xs:string" required="yes"/>
    <xsl:param name="length" as="xs:integer" required="yes"/>
    <xsl:choose>
      <xsl:when test="string-length($text) &gt; $length">
        <xsl:value-of select="substring($text, 1, $length)"/>
        <xsl:call-template name="splitString">
          <xsl:with-param name="text" select="substring($text, $length + 1)"/>
          <xsl:with-param name="length" select="$length"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$text"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
	<!--==============================================================
	   - Step 6: Turn the v2 XML content into proper v2 syntax
     -==============================================================-->
  <xsl:template mode="v2" match="Segment" as="xs:string+">
    <!-- Segments start with the segment name and end with a line break -->
    <xsl:value-of select="@name"/>
    <xsl:apply-templates mode="v2" select="Field"/>
    <xsl:text>&#x0a;</xsl:text>
  </xsl:template>
  <xsl:template mode="v2" match="Field" as="xs:string+">
    <!-- Fields start with a field separator -->
    <xsl:value-of select="$FieldSep"/>
    <xsl:apply-templates mode="v2" select="Repeat|Comp|NoEscape|text()"/>
  </xsl:template>
  <xsl:template mode="v2" match="Segment[@name='MSH']/Field[2]" as="xs:string">
    <!-- We don't put a field separator in front of MSH.2 -->
    <xsl:copy-of select="text()"/>
  </xsl:template>
  <xsl:template mode="v2" match="Repeat" as="xs:string+">
    <!-- All extra (greater than one) repetitions start with a repeat separator -->
    <xsl:if test="preceding-sibling::Repeat">
      <xsl:value-of select="$RepSep"/>
    </xsl:if>
    <xsl:apply-templates mode="v2" select="Comp|NoEscape|text()"/>
  </xsl:template>
  <xsl:template mode="v2" match="Comp" as="xs:string*">
    <!-- All extra (greater than one) components start with a component separator -->
    <xsl:if test="preceding-sibling::Comp">
      <xsl:value-of select="$CompSep"/>
    </xsl:if>
    <xsl:apply-templates mode="v2" select="Sub|NoEscape|text()"/>
  </xsl:template>
  <xsl:template mode="v2" match="Sub" as="xs:string*">
    <!-- All extra (greater than one) sub-components start with a sub-component separator -->
    <xsl:if test="preceding-sibling::Sub">
      <xsl:value-of select="$CompSep"/>
    </xsl:if>
    <xsl:apply-templates mode="v2" select="text()"/>
  </xsl:template>
  <xsl:template mode="v2" match="text()" as="text()">
    <!-- Text needs to be escaped, including escaping the escape character -->
    <xsl:value-of select="nci:escapeText(replace(., $Escape, concat($Escape, 'E', $Escape)))"/>
  </xsl:template>
  <xsl:template mode="v2" match="NoEscape" as="text()">
    <!-- We don't escape the escape character if told not to (because we're dealing with FT content that's already escaped) -->
    <xsl:value-of select="nci:escapeText(text())"/>
  </xsl:template>
  <xsl:function name="nci:escapeText" as="text()">
    <!-- Escapes the various separator characters (field, component, sub-component and repeat) in v2 text -->
    <xsl:param name="text" as="xs:string"/>
    <!-- Note: Some of the separators must be escaped as part of regular expressions, others not.  So if the field separator characters
       - are changed, it may be necessary to change this function -->
    <xsl:variable name="pass1" as="xs:string" select="replace($text, concat('\', $FieldSep), concat($Escape, 'F', $Escape))"/>
    <xsl:variable name="pass2" as="xs:string" select="replace($pass1, concat('\', $CompSep), concat($Escape, 'S', $Escape))"/>
    <xsl:variable name="pass3" as="xs:string" select="replace($pass2, $SubSep, concat($Escape, 'T', $Escape))"/>
    <xsl:value-of select="replace($pass3, $RepSep, concat($Escape, 'R', $Escape))"/>
  </xsl:function>
</xsl:stylesheet>
