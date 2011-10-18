<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:canon="urn:nci-gov:caCIS/rimExtension"
                version="1.0"
                xpath-default-namespace="urn:hl7-org:v3"
                exclude-result-prefixes="canon">
   <xsl:template match="ClinicalDocument" priority="10">
      <xsl:element name="rim-graph" namespace="urn:hl7-org:v3-rim">
         <xsl:attribute name="version" select="'0232'"/>
         <content xmlns:cda="ii:2.16.840.1.113883.1.3:POCD_HD000040" xmlns="urn:hl7-org:v3-rim"
                  templates="cda:ClinicalDocument">
            <xsl:call-template name="ClinicalDocument"/>
         </content>
      </xsl:element>
   </xsl:template>
   <xsl:template match="node()|@*">
      <xsl:copy>
         <xsl:apply-templates select="@*|node()"/>
      </xsl:copy>
   </xsl:template>
   <xsl:template match="*" priority="5">
      <xsl:element namespace="urn:hl7-org:v3-rim" name="{local-name(.)}">
         <xsl:apply-templates select="@*|node()"/>
      </xsl:element>
   </xsl:template>
   <xsl:template match="@xsi:schemaLocation"/>
   <xsl:template name="ClinicalDocument">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'ClinicalDocument'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|title|effectiveTime|confidentialityCode|languageCode"/>
      <xsl:for-each select="recordTarget">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'RecordTarget')"/>
            <xsl:call-template name="RecordTarget"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="author">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
            <xsl:call-template name="Author"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="dataEnterer">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'DataEnterer')"/>
            <xsl:call-template name="DataEnterer"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="custodian">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Custodian')"/>
            <xsl:call-template name="Custodian"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="informationRecipient">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'InformationRecipient')"/>
            <xsl:call-template name="InformationRecipient"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="legalAuthenticator">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'LegalAuthenticator')"/>
            <xsl:call-template name="LegalAuthenticator"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="authenticator">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Authenticator')"/>
            <xsl:call-template name="Authenticator"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="participation">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Participant1')"/>
            <xsl:call-template name="Participant1"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="inFulfillmentOf">
         <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'InFulfillmentOf')"/>
            <xsl:call-template name="InFulfillmentOf"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="documentationOf">
         <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'DocumentationOf')"/>
            <xsl:call-template name="DocumentationOf"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="relatedDocument">
         <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'RelatedDocument')"/>
            <xsl:call-template name="RelatedDocument"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="component">
         <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Component2')"/>
            <xsl:call-template name="Component2"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="componentOf">
         <xsl:element name="inboundRelationship" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Component1')"/>
            <xsl:call-template name="Component1"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:apply-templates select="setId|versionNumber|copyTime"/>
   </xsl:template>
   <xsl:template name="Authenticator">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Authenticator'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|time|signatureCode"/>
      <xsl:for-each select="assignedEntity">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
            <xsl:call-template name="AssignedEntity"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="AssignedEntity">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'AssignedEntity'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|code|addr|telecom"/>
      <xsl:for-each select="assignedPerson">
         <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Person')"/>
            <xsl:call-template name="Person"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="representedOrganization">
         <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Organization')"/>
            <xsl:call-template name="Organization"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Person">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Person'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="Organization">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Organization'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|id|name|telecom|addr"/>
      <xsl:for-each select="asOrganizationPartOf">
         <xsl:element name="playedRole" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'OrganizationPartOf')"/>
            <xsl:call-template name="OrganizationPartOf"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:apply-templates select="standardIndustryClassCode"/>
   </xsl:template>
   <xsl:template name="OrganizationPartOf">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'OrganizationPartOf'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|code|statusCode|effectiveTime"/>
      <xsl:for-each select="wholeOrganization">
         <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Organization')"/>
            <xsl:call-template name="Organization"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Author">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Author'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|functionCode|contextControlCode|time"/>
      <xsl:for-each select="assignedAuthor">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'AssignedAuthor')"/>
            <xsl:call-template name="AssignedAuthor"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="AssignedAuthor">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'AssignedAuthor'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|code|addr|telecom"/>
      <xsl:for-each select="assignedAuthorChoice">
         <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'AuthorChoice')"/>
            <xsl:call-template name="AuthorChoice"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="representedOrganization">
         <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Organization')"/>
            <xsl:call-template name="Organization"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="AuthorChoice">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'AuthorChoice'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="AuthoringDevice">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'AuthoringDevice'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="Component2">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Component2'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextConductionInd"/>
      <xsl:for-each select="structuredBody">
         <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'StructuredBody')"/>
            <xsl:call-template name="StructuredBody"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="StructuredBody">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'StructuredBody'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|confidentialityCode|languageCode"/>
      <xsl:for-each select="component">
         <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Component3')"/>
            <xsl:call-template name="Component3"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Component3">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Component3'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextConductionInd"/>
      <xsl:for-each select="section">
         <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Section')"/>
            <xsl:call-template name="Section"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Section">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Section'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|title|text|confidentialityCode|languageCode"/>
      <xsl:for-each select="subject">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
            <xsl:call-template name="Subject"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="author">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
            <xsl:call-template name="Author"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="entry">
         <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Entry')"/>
            <xsl:call-template name="Entry"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="component">
         <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Component5')"/>
            <xsl:call-template name="Component5"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Component5">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Component5'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextConductionInd"/>
      <xsl:for-each select="section">
         <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Section')"/>
            <xsl:call-template name="Section"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Entry">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Entry'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextConductionInd"/>
      <xsl:for-each select="clinicalStatement">
         <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'ClinicalStatement')"/>
            <xsl:call-template name="ClinicalStatement"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="ClinicalStatement">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'ClinicalStatement'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
      <xsl:for-each select="entryRelationship">
         <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
            <xsl:call-template name="EntryRelationship"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Observation">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Observation'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="RegionOfInterest">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'RegionOfInterest'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="Procedure">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Procedure'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="Act">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Act'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="EntryRelationship">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'EntryRelationship'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|inversionInd|contextConductionInd|sequenceNumber|negationInd|seperatableInd"/>
      <xsl:for-each select="clinicalStatement">
         <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'ClinicalStatement')"/>
            <xsl:call-template name="ClinicalStatement"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Subject">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Subject'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextControlCode|awarenessCode"/>
      <xsl:for-each select="relatedSubject">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'RelatedSubject')"/>
            <xsl:call-template name="RelatedSubject"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="RelatedSubject">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'RelatedSubject'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|code|addr|telecom"/>
      <xsl:for-each select="subject">
         <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'SubjectPerson')"/>
            <xsl:call-template name="SubjectPerson"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="SubjectPerson">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'SubjectPerson'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="Component1">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Component1'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
      <xsl:for-each select="encompassingEncounter">
         <xsl:element name="source" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'EncompassingEncounter')"/>
            <xsl:call-template name="EncompassingEncounter"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="EncompassingEncounter">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'EncompassingEncounter'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|effectiveTime"/>
      <xsl:for-each select="encounterParticipant">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'EncounterParticipant')"/>
            <xsl:call-template name="EncounterParticipant"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:apply-templates select="dischargeDispositionCode"/>
   </xsl:template>
   <xsl:template name="EncounterParticipant">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'EncounterParticipant'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|time"/>
      <xsl:for-each select="assignedEntity">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
            <xsl:call-template name="AssignedEntity"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Custodian">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Custodian'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
      <xsl:for-each select="assignedCustodian">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'AssignedCustodian')"/>
            <xsl:call-template name="AssignedCustodian"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="AssignedCustodian">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'AssignedCustodian'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode"/>
      <xsl:for-each select="representedCustodianOrganization">
         <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'CustodianOrganization')"/>
            <xsl:call-template name="CustodianOrganization"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="CustodianOrganization">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'CustodianOrganization'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="DataEnterer">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'DataEnterer'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextControlCode|time"/>
      <xsl:for-each select="assignedEntity">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
            <xsl:call-template name="AssignedEntity"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="DocumentationOf">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'DocumentationOf'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
      <xsl:for-each select="serviceEvent">
         <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'ServiceEvent')"/>
            <xsl:call-template name="ServiceEvent"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="ServiceEvent">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'ServiceEvent'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|effectiveTime"/>
      <xsl:for-each select="performer">
         <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Performer1')"/>
            <xsl:call-template name="Performer1"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Performer1">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Performer1'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|functionCode|time"/>
      <xsl:for-each select="assignedEntity">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
            <xsl:call-template name="AssignedEntity"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="InFulfillmentOf">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'InFulfillmentOf'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
      <xsl:for-each select="order">
         <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Order')"/>
            <xsl:call-template name="Order"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Order">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Order'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="InformationRecipient">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'InformationRecipient'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
      <xsl:for-each select="intendedRecipient">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'IntendedRecipient')"/>
            <xsl:call-template name="IntendedRecipient"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="IntendedRecipient">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'IntendedRecipient'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|addr|telecom"/>
      <xsl:for-each select="informationRecipient">
         <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Person')"/>
            <xsl:call-template name="Person"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="receivedOrganization">
         <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Organization')"/>
            <xsl:call-template name="Organization"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="LegalAuthenticator">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'LegalAuthenticator'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextControlCode|time|signatureCode"/>
      <xsl:for-each select="assignedEntity">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
            <xsl:call-template name="AssignedEntity"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Participant1">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Participant1'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|functionCode|contextControlCode|time"/>
      <xsl:for-each select="assignedEntity">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
            <xsl:call-template name="AssignedEntity"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="RecordTarget">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'RecordTarget'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextControlCode"/>
      <xsl:for-each select="patientRole">
         <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'PatientRole')"/>
            <xsl:call-template name="PatientRole"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="PatientRole">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'PatientRole'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|addr|telecom"/>
      <xsl:for-each select="patient">
         <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Patient')"/>
            <xsl:call-template name="Patient"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:for-each select="providerOrganization">
         <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'Organization')"/>
            <xsl:call-template name="Organization"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="Patient">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'Patient'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|id|name"/>
      <xsl:for-each select="languageCommunication">
         <xsl:element name="languageCommunication" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'LanguageCommunication')"/>
            <xsl:call-template name="LanguageCommunication"/>
         </xsl:element>
      </xsl:for-each>
      <xsl:apply-templates select="administrativeGenderCode|birthTime|maritalStatusCode|religiousAffiliationCode|raceCode|ethnicGroupCode"/>
   </xsl:template>
   <xsl:template name="LanguageCommunication">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'LanguageCommunication'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
   <xsl:template name="RelatedDocument">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'RelatedDocument'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
      <xsl:for-each select="parentDocument">
         <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
            <xsl:attribute name="templates" select="concat('cda:', 'ParentDocument')"/>
            <xsl:call-template name="ParentDocument"/>
         </xsl:element>
      </xsl:for-each>
   </xsl:template>
   <xsl:template name="ParentDocument">
      <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance"
                     select="'ParentDocument'"/>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates select="realmCode|typeId|templateId"/>
   </xsl:template>
</xsl:stylesheet>