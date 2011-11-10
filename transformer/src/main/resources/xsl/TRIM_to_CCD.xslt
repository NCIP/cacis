<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:cda="urn:hl7-org:v3" xmlns:trim="urn:tolven-org:trim:4.0" exclude-result-prefixes="fn xs cda trim">
	
	<xsl:import href="BaselineNote.xslt"/>
	<xsl:import href="ClinicalStagingNote.xslt"/>
	<xsl:import href="SurgicalStudyVisitNote.xslt"/>
	<xsl:import href="MedicalStudyVisitNote.xslt"/>
	
	<xsl:output method="xml" encoding="UTF-8" indent="yes"/>
	
	<xsl:param name="siteId"/>
	<xsl:param name="studyId"/>
	
	<!-- Main -->
	<xsl:template match="/">
			
			<!-- get the trim description -->
			<xsl:variable name="trimdesc" select="//trim:description"/>
			
			<!-- Match the trim description to apply the correct template, add more when as required -->
			<xsl:choose>
				
				<xsl:when test="$trimdesc='Baseline Evaluation Note'">
					<xsl:call-template name="BaselineNote">
						<xsl:with-param name="siteId" select="$siteId"/>
						<xsl:with-param name="studyId" select="$studyId"/>
					</xsl:call-template>
				</xsl:when>
				
				<xsl:when test="$trimdesc='Clinical Staging'">
					<xsl:call-template name="ClinicalStagingNote">
						<xsl:with-param name="siteId" select="$siteId"/>
						<xsl:with-param name="studyId" select="$studyId"/>
					</xsl:call-template>
				</xsl:when>
				
				<xsl:when test="$trimdesc='Study Visit (Medical)'">
					<xsl:call-template name="MedicalStudyVisitNote">
						<xsl:with-param name="siteId" select="$siteId"/>
						<xsl:with-param name="studyId" select="$studyId"/>
					</xsl:call-template>
				</xsl:when>
				
				<xsl:when test="$trimdesc='Surgical Study Visit Note'">
					<xsl:call-template name="SurgicalStudyVisitNote">
						<xsl:with-param name="siteId" select="$siteId"/>
						<xsl:with-param name="studyId" select="$studyId"/>
					</xsl:call-template>
				</xsl:when>			
				
			</xsl:choose>			
	</xsl:template>
</xsl:stylesheet>