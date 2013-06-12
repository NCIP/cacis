<?xml version="1.0" encoding="UTF-8"?>
<!--

    Copyright 5AM Solutions Inc
    Copyright SemanticBits LLC
    Copyright AgileX Technologies, Inc
    Copyright Ekagra Software Technologies Ltd

    Distributed under the OSI-approved BSD 3-Clause License.
    See http://ncip.github.com/cacis/LICENSE.txt for details.

-->
<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="xs fn cda trim">
	<xsl:output method="xml" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<ClinicalDocument xmlns="urn:hl7-org:v3">
			<!-- <xsl:attribute name="xsi:schemaLocation" namespace="http://www.w3.org/2001/XMLSchema-instance"><xsl:value-of select="'urn:hl7-org:v3 D:/workspace/Transcend_Trim_Mapping/mapping/CDA_XSD/cda/CDA.xsd'"/></xsl:attribute> -->
			<xsl:for-each select="trim:trim/trim:act">
				<xsl:call-template name="baseline_cdaHeader">
					<xsl:with-param name="trimAct" select="current()"/>
				</xsl:call-template>
			</xsl:for-each>
		</ClinicalDocument>
	</xsl:template>
	<!-- ===================== -->
	<!-- cda header template -->
	<!-- ===================== -->
	<xsl:template name="baseline_cdaHeader">
		<xsl:param name="trimAct"/>
		<xsl:if test="$trimAct/trim:id/trim:II">
			<xsl:call-template name="make_II">
				<xsl:with-param name="II_root" select="$trimAct/trim:id/trim:II/trim:root"/>
				<xsl:with-param name="II_ext" select="$trimAct/trim:id/trim:II/trim:extension"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
