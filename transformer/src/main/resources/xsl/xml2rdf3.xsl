<?xml version="1.0" encoding="UTF-8"?>
<!-- This XSL has been taken from the xml2rdf3.xsl (XSLT stylesheet to transform XML into RDF/XML). It has been modified to invoke the CDAToRDF.xsl
      for converting the CDA Clinical Documents to RDF. This xsl takes the baseURI as the parameter for creating an RDF graph of XML for a caCIS request. 
      

    The following are the details of the original xml2rdf3.xsl file.
     Version             3.0  (2009-05-28)
     Changes to V2.5     rdf:value for all text, no attribute triples,
                         order predicates, comments as triples
     Web page            http://www.gac-grid.org/project-products/Software/XML2RDF.html
     Usage               xsltproc xml2rdf3.xsl file.xml
     Author              Frank Breitling (fbreitling at aip.de)
     Copyright 2009      AstroGrid-D (http://www.gac-grid.org/) 
   
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
 
         http://www.apache.org/licenses/LICENSE-2.0
 
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License. -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/TR/2008/REC-xml-20081126#" xmlns:cacis="http://cacis.nci.nih.gov" xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:v3="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:hl7="urn:hl7-org:functions" xmlns:DT="urn:hl7-org:v3/owl/DEFN/UV/DT/1.0#" exclude-result-prefixes="xsi xs v3 hl7">
	<xsl:import href="CDAToRDF.xsl"/>
	<xsl:strip-space elements="*"/>
	<xsl:output method="xml" indent="yes"/>
	<xsl:param name="BaseURI">http://cacis.nci.nih.gov/UUID</xsl:param>
	<!-- Begin RDF document -->
	<xsl:template match="/">
		<rdf:RDF xmlns:M0="urn:hl7-org:v3/owl/DEFN/UV/CD/MT/000040#" xmlns:_XML="http://www.w3.org/XML/1998/namespace#" xmlns:RIM="urn:hl7-org:v3/owl/DEFN/UV/RIM/207#" xmlns:DT="urn:hl7-org:v3/owl/DEFN/UV/DT/1.0#">
			<xsl:element name="Description" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
				<xsl:attribute name="about" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#"><xsl:value-of select="$BaseURI"/></xsl:attribute>
				<xsl:apply-templates select="*" mode="generic"/>
			</xsl:element>
		</rdf:RDF>
	</xsl:template>
	<xsl:template match="v3:ClinicalDocument" mode="generic">
		<xsl:apply-templates mode="M0_ClinicalDocument" select="."/>
	</xsl:template>
	<!-- Turn XML elements into RDF triples. -->
	<xsl:template match="*" mode="generic">
		<xsl:param name="subjectname"/>
		<!-- Build URI for subjects resources from acestors elements -->
		<xsl:variable name="newsubjectname">
			<xsl:if test="$subjectname=''">
				<xsl:value-of select="$BaseURI"/>
				<xsl:text>#</xsl:text>
			</xsl:if>
			<xsl:value-of select="$subjectname"/>
			<xsl:value-of select="name()"/>
			<!-- Add an ID to sibling element of identical name -->
			<xsl:variable name="number">
				<xsl:number/>
			</xsl:variable>
			<xsl:if test="$number > 1">
				<xsl:text>_</xsl:text>
				<xsl:number/>
			</xsl:if>
		</xsl:variable>
		<xsl:element name="{name()}" namespace="{concat(namespace-uri(),'#')}">
			<xsl:element name="Description" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
				<xsl:attribute name="about" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#"><xsl:value-of select="$newsubjectname"/></xsl:attribute>
				<xsl:apply-templates select="@*|node()" mode="generic">
					<xsl:with-param name="subjectname" select="concat($newsubjectname,'/')"/>
				</xsl:apply-templates>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!-- Create attribute triples. -->
	<xsl:template match="@*" name="attributes" mode="generic">
		<xsl:variable name="ns">
			<!-- If attribute doesn't have a namespace use element namespace -->
			<xsl:choose>
				<xsl:when test="namespace-uri()=''">
					<xsl:value-of select="namespace-uri(..)"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="namespace-uri()"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:element name="{name()}" namespace="{concat($ns,'#')}">
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>
	<!-- Enclose text in an rdf:value element -->
	<xsl:template match="text()" mode="generic">
		<xsl:element name="rdf:value">
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>
	<!-- Add triple to preserve comments -->
	<xsl:template match="comment()" mode="generic">
		<xsl:element name="xs:comment">
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
