<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:canon="urn:nci-gov:caCIS/rimExtension" version="1.0" xpath-default-namespace="urn:hl7-org:v3" exclude-result-prefixes="canon">
  <xsl:template match="ClinicalDocument" priority="10">
    <xsl:element name="rim-graph" namespace="urn:hl7-org:v3-rim">
      <xsl:attribute name="version" select="'207'"/>
      <content xmlns:cda="ii:2.16.840.1.113883.1.3:POCD_HD000040" xmlns="urn:hl7-org:v3-rim" templates="cda:ClinicalDocument">
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ClinicalDocument'"/>
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
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
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
    <xsl:for-each select="participant">
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
    <xsl:for-each select="authorization">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Authorization')"/>
        <xsl:call-template name="Authorization"/>
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
  <xsl:template name="RecordTarget">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'RecordTarget'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'PatientRole'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Patient'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|id|name"/>
    <xsl:for-each select="guardian">
      <xsl:element name="scopedRole" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Guardian')"/>
        <xsl:call-template name="Guardian"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="birthplace">
      <xsl:element name="scopedRole" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Birthplace')"/>
        <xsl:call-template name="Birthplace"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="languageCommunication">
      <xsl:element name="languageCommunication" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'LanguageCommunication')"/>
        <xsl:call-template name="LanguageCommunication"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:apply-templates select="administrativeGenderCode|birthTime|maritalStatusCode|religiousAffiliationCode|raceCode|ethnicGroupCode"/>
  </xsl:template>
  <xsl:template name="Guardian">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Guardian'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|code|addr|telecom"/>
    <xsl:for-each select="guardianOrganization">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Organization')"/>
        <xsl:call-template name="Organization"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="guardianPerson">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Person')"/>
        <xsl:call-template name="Person"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="GuardianChoice">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'GuardianChoice'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId"/>
  </xsl:template>
  <xsl:template name="Organization">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Organization'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|id|name|telecom"/>
    <xsl:for-each select="asOrganizationPartOf">
      <xsl:element name="playedRole" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'OrganizationPartOf')"/>
        <xsl:call-template name="OrganizationPartOf"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:apply-templates select="addr|standardIndustryClassCode"/>
  </xsl:template>
  <xsl:template name="OrganizationPartOf">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'OrganizationPartOf'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|code|statusCode|effectiveTime"/>
    <xsl:for-each select="wholeOrganization">
      <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Organization')"/>
        <xsl:call-template name="Organization"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Person">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Person'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|name"/>
  </xsl:template>
  <xsl:template name="Birthplace">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Birthplace'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode"/>
    <xsl:for-each select="place">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Place')"/>
        <xsl:call-template name="Place"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Place">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Place'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|name|addr"/>
  </xsl:template>
  <xsl:template name="LanguageCommunication">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'LanguageCommunication'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|languageCode|modeCode|proficiencyLevelCode|preferenceInd"/>
  </xsl:template>
  <xsl:template name="Author">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Author'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'AssignedAuthor'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|code|addr|telecom"/>
    <xsl:for-each select="assignedAuthoringDevice">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'AuthoringDevice')"/>
        <xsl:call-template name="AuthoringDevice"/>
      </xsl:element>
    </xsl:for-each>
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
  <xsl:template name="AuthorChoice">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'AuthorChoice'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId"/>
  </xsl:template>
  <xsl:template name="AuthoringDevice">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'AuthoringDevice'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|code"/>
    <xsl:for-each select="asMaintainedEntity">
      <xsl:element name="playedRole" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'MaintainedEntity')"/>
        <xsl:call-template name="MaintainedEntity"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:apply-templates select="manufacturerModelName|softwareName"/>
  </xsl:template>
  <xsl:template name="MaintainedEntity">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'MaintainedEntity'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|effectiveTime"/>
    <xsl:for-each select="maintainingPerson">
      <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Person')"/>
        <xsl:call-template name="Person"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="DataEnterer">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'DataEnterer'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextControlCode|time"/>
    <xsl:for-each select="assignedEntity">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
        <xsl:call-template name="AssignedEntity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="AssignedEntity">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'AssignedEntity'"/>
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
  <xsl:template name="Informant12">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Informant12'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextControlCode"/>
    <xsl:for-each select="assignedEntity">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
        <xsl:call-template name="AssignedEntity"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="relatedEntity">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'RelatedEntity')"/>
        <xsl:call-template name="RelatedEntity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="InformantChoice">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'InformantChoice'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId"/>
  </xsl:template>
  <xsl:template name="RelatedEntity">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'RelatedEntity'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|code|addr|telecom|effectiveTime"/>
    <xsl:for-each select="relatedPerson">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Person')"/>
        <xsl:call-template name="Person"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Custodian">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Custodian'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'AssignedCustodian'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'CustodianOrganization'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|id|name|telecom|addr"/>
  </xsl:template>
  <xsl:template name="InformationRecipient">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'InformationRecipient'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'IntendedRecipient'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'LegalAuthenticator'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextControlCode|time|signatureCode"/>
    <xsl:for-each select="assignedEntity">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
        <xsl:call-template name="AssignedEntity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Authenticator">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Authenticator'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|time|signatureCode"/>
    <xsl:for-each select="assignedEntity">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
        <xsl:call-template name="AssignedEntity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Participant1">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Participant1'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|functionCode|contextControlCode|time"/>
    <xsl:for-each select="associatedEntity">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'AssociatedEntity')"/>
        <xsl:call-template name="AssociatedEntity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="AssociatedEntity">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'AssociatedEntity'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|code|addr|telecom"/>
    <xsl:for-each select="associatedPerson">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Person')"/>
        <xsl:call-template name="Person"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="scopingOrganization">
      <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Organization')"/>
        <xsl:call-template name="Organization"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="InFulfillmentOf">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'InFulfillmentOf'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Order'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|priorityCode"/>
  </xsl:template>
  <xsl:template name="DocumentationOf">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'DocumentationOf'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ServiceEvent'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Performer1'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|functionCode|time"/>
    <xsl:for-each select="assignedEntity">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
        <xsl:call-template name="AssignedEntity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="RelatedDocument">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'RelatedDocument'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ParentDocument'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|text|setId|versionNumber"/>
  </xsl:template>
  <xsl:template name="Authorization">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Authorization'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
    <xsl:for-each select="consent">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Consent')"/>
        <xsl:call-template name="Consent"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Consent">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Consent'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|statusCode"/>
  </xsl:template>
  <xsl:template name="Component1">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Component1'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'EncompassingEncounter'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|effectiveTime"/>
    <xsl:for-each select="responsibleParty">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ResponsibleParty')"/>
        <xsl:call-template name="ResponsibleParty"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="encounterParticipant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EncounterParticipant')"/>
        <xsl:call-template name="EncounterParticipant"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="location">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Location')"/>
        <xsl:call-template name="Location"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:apply-templates select="dischargeDispositionCode"/>
  </xsl:template>
  <xsl:template name="ResponsibleParty">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ResponsibleParty'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
    <xsl:for-each select="assignedEntity">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
        <xsl:call-template name="AssignedEntity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="EncounterParticipant">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'EncounterParticipant'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|time"/>
    <xsl:for-each select="assignedEntity">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
        <xsl:call-template name="AssignedEntity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Location">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Location'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
    <xsl:for-each select="healthCareFacility">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'HealthCareFacility')"/>
        <xsl:call-template name="HealthCareFacility"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="HealthCareFacility">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'HealthCareFacility'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|code"/>
    <xsl:for-each select="location">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Place')"/>
        <xsl:call-template name="Place"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="serviceProviderOrganization">
      <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Organization')"/>
        <xsl:call-template name="Organization"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Component2">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Component2'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextConductionInd"/>
    <xsl:for-each select="nonXMLBody">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'NonXMLBody')"/>
        <xsl:call-template name="NonXMLBody"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="structuredBody">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'StructuredBody')"/>
        <xsl:call-template name="StructuredBody"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="BodyChoice">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'BodyChoice'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId"/>
  </xsl:template>
  <xsl:template name="NonXMLBody">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'NonXMLBody'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|text|confidentialityCode|languageCode"/>
  </xsl:template>
  <xsl:template name="StructuredBody">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'StructuredBody'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Component3'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Section'"/>
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
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
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
  <xsl:template name="Subject">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Subject'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'RelatedSubject'"/>
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
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'SubjectPerson'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|name|administrativeGenderCode|birthTime"/>
  </xsl:template>
  <xsl:template name="Entry">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Entry'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextConductionInd"/>
    <xsl:for-each select="act">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Act')"/>
        <xsl:call-template name="Act"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="encounter">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Encounter')"/>
        <xsl:call-template name="Encounter"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="observation">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Observation')"/>
        <xsl:call-template name="Observation"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="observationMedia">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ObservationMedia')"/>
        <xsl:call-template name="ObservationMedia"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="organizer">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Organizer')"/>
        <xsl:call-template name="Organizer"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="procedure">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Procedure')"/>
        <xsl:call-template name="Procedure"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="regionOfInterest">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'RegionOfInterest')"/>
        <xsl:call-template name="RegionOfInterest"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="substanceAdministration">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'SubstanceAdministration')"/>
        <xsl:call-template name="SubstanceAdministration"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="supply">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Supply')"/>
        <xsl:call-template name="Supply"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="ClinicalStatement">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ClinicalStatement'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId"/>
    <xsl:for-each select="subject">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
        <xsl:call-template name="Subject"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="specimen">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Specimen')"/>
        <xsl:call-template name="Specimen"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="performer">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Performer2')"/>
        <xsl:call-template name="Performer2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="author">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
        <xsl:call-template name="Author"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="participant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Participant2')"/>
        <xsl:call-template name="Participant2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="entryRelationship">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
        <xsl:call-template name="EntryRelationship"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="reference">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Reference')"/>
        <xsl:call-template name="Reference"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="precondition">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Precondition')"/>
        <xsl:call-template name="Precondition"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Act">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Act'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|negationInd|text|statusCode|effectiveTime|priorityCode|languageCode"/>
    <xsl:for-each select="subject">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
        <xsl:call-template name="Subject"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="specimen">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Specimen')"/>
        <xsl:call-template name="Specimen"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="performer">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Performer2')"/>
        <xsl:call-template name="Performer2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="author">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
        <xsl:call-template name="Author"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="participant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Participant2')"/>
        <xsl:call-template name="Participant2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="entryRelationship">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
        <xsl:call-template name="EntryRelationship"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="reference">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Reference')"/>
        <xsl:call-template name="Reference"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="precondition">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Precondition')"/>
        <xsl:call-template name="Precondition"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Encounter">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Encounter'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|text|statusCode|effectiveTime|priorityCode"/>
    <xsl:for-each select="subject">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
        <xsl:call-template name="Subject"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="specimen">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Specimen')"/>
        <xsl:call-template name="Specimen"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="performer">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Performer2')"/>
        <xsl:call-template name="Performer2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="author">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
        <xsl:call-template name="Author"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="participant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Participant2')"/>
        <xsl:call-template name="Participant2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="entryRelationship">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
        <xsl:call-template name="EntryRelationship"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="reference">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Reference')"/>
        <xsl:call-template name="Reference"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="precondition">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Precondition')"/>
        <xsl:call-template name="Precondition"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Observation">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Observation'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|negationInd|derivationExpr|text|statusCode|effectiveTime|priorityCode|repeatNumber|languageCode"/>
    <xsl:for-each select="subject">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
        <xsl:call-template name="Subject"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="specimen">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Specimen')"/>
        <xsl:call-template name="Specimen"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="performer">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Performer2')"/>
        <xsl:call-template name="Performer2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="author">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
        <xsl:call-template name="Author"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="participant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Participant2')"/>
        <xsl:call-template name="Participant2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="entryRelationship">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
        <xsl:call-template name="EntryRelationship"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="reference">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Reference')"/>
        <xsl:call-template name="Reference"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="precondition">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Precondition')"/>
        <xsl:call-template name="Precondition"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="referenceRange">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ReferenceRange')"/>
        <xsl:call-template name="ReferenceRange"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:apply-templates select="value|interpretationCode|methodCode|targetSiteCode"/>
  </xsl:template>
  <xsl:template name="ReferenceRange">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ReferenceRange'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
    <xsl:for-each select="observationRange">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ObservationRange')"/>
        <xsl:call-template name="ObservationRange"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="ObservationRange">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ObservationRange'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|code|text|value|interpretationCode"/>
  </xsl:template>
  <xsl:template name="ObservationMedia">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ObservationMedia'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|languageCode|value"/>
    <xsl:for-each select="subject">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
        <xsl:call-template name="Subject"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="specimen">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Specimen')"/>
        <xsl:call-template name="Specimen"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="performer">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Performer2')"/>
        <xsl:call-template name="Performer2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="author">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
        <xsl:call-template name="Author"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="participant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Participant2')"/>
        <xsl:call-template name="Participant2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="entryRelationship">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
        <xsl:call-template name="EntryRelationship"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="reference">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Reference')"/>
        <xsl:call-template name="Reference"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="precondition">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Precondition')"/>
        <xsl:call-template name="Precondition"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Organizer">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Organizer'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|statusCode|effectiveTime"/>
    <xsl:for-each select="subject">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
        <xsl:call-template name="Subject"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="specimen">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Specimen')"/>
        <xsl:call-template name="Specimen"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="performer">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Performer2')"/>
        <xsl:call-template name="Performer2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="author">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
        <xsl:call-template name="Author"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="participant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Participant2')"/>
        <xsl:call-template name="Participant2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="entryRelationship">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
        <xsl:call-template name="EntryRelationship"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="reference">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Reference')"/>
        <xsl:call-template name="Reference"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="precondition">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Precondition')"/>
        <xsl:call-template name="Precondition"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="component">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Component4')"/>
        <xsl:call-template name="Component4"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Component4">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Component4'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextConductionInd|sequenceNumber|seperatableInd"/>
    <xsl:for-each select="act">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Act')"/>
        <xsl:call-template name="Act"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="encounter">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Encounter')"/>
        <xsl:call-template name="Encounter"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="observation">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Observation')"/>
        <xsl:call-template name="Observation"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="observationMedia">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ObservationMedia')"/>
        <xsl:call-template name="ObservationMedia"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="organizer">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Organizer')"/>
        <xsl:call-template name="Organizer"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="procedure">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Procedure')"/>
        <xsl:call-template name="Procedure"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="regionOfInterest">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'RegionOfInterest')"/>
        <xsl:call-template name="RegionOfInterest"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="substanceAdministration">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'SubstanceAdministration')"/>
        <xsl:call-template name="SubstanceAdministration"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="supply">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Supply')"/>
        <xsl:call-template name="Supply"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Procedure">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Procedure'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|negationInd|text|statusCode|effectiveTime|priorityCode|languageCode|methodCode|approachSiteCode|targetSiteCode"/>
    <xsl:for-each select="subject">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
        <xsl:call-template name="Subject"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="specimen">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Specimen')"/>
        <xsl:call-template name="Specimen"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="performer">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Performer2')"/>
        <xsl:call-template name="Performer2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="author">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
        <xsl:call-template name="Author"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="participant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Participant2')"/>
        <xsl:call-template name="Participant2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="entryRelationship">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
        <xsl:call-template name="EntryRelationship"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="reference">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Reference')"/>
        <xsl:call-template name="Reference"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="precondition">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Precondition')"/>
        <xsl:call-template name="Precondition"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="RegionOfInterest">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'RegionOfInterest'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|value"/>
    <xsl:for-each select="subject">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
        <xsl:call-template name="Subject"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="specimen">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Specimen')"/>
        <xsl:call-template name="Specimen"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="performer">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Performer2')"/>
        <xsl:call-template name="Performer2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="author">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
        <xsl:call-template name="Author"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="participant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Participant2')"/>
        <xsl:call-template name="Participant2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="entryRelationship">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
        <xsl:call-template name="EntryRelationship"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="reference">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Reference')"/>
        <xsl:call-template name="Reference"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="precondition">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Precondition')"/>
        <xsl:call-template name="Precondition"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="SubstanceAdministration">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'SubstanceAdministration'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|negationInd|text|statusCode|effectiveTime|priorityCode|repeatNumber"/>
    <xsl:for-each select="subject">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
        <xsl:call-template name="Subject"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="specimen">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Specimen')"/>
        <xsl:call-template name="Specimen"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="performer">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Performer2')"/>
        <xsl:call-template name="Performer2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="author">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
        <xsl:call-template name="Author"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="participant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Participant2')"/>
        <xsl:call-template name="Participant2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="consumable">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Consumable')"/>
        <xsl:call-template name="Consumable"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="entryRelationship">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
        <xsl:call-template name="EntryRelationship"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="reference">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Reference')"/>
        <xsl:call-template name="Reference"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="precondition">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Precondition')"/>
        <xsl:call-template name="Precondition"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:apply-templates select="routeCode|approachSiteCode|doseQuantity|rateQuantity|maxDoseQuantity|administrationUnitCode"/>
  </xsl:template>
  <xsl:template name="Consumable">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Consumable'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
    <xsl:for-each select="manufacturedProduct">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ManufacturedProduct')"/>
        <xsl:call-template name="ManufacturedProduct"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="ManufacturedProduct">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ManufacturedProduct'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id"/>
    <xsl:for-each select="manufacturedLabeledDrug">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'LabeledDrug')"/>
        <xsl:call-template name="LabeledDrug"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="manufacturedMaterial">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Material')"/>
        <xsl:call-template name="Material"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="manufacturerOrganization">
      <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Organization')"/>
        <xsl:call-template name="Organization"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="DrugOrOtherMaterial">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'DrugOrOtherMaterial'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId"/>
  </xsl:template>
  <xsl:template name="LabeledDrug">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'LabeledDrug'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|code|name"/>
  </xsl:template>
  <xsl:template name="Material">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Material'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|code|name|lotNumberText"/>
  </xsl:template>
  <xsl:template name="Supply">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Supply'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|text|statusCode|effectiveTime|priorityCode|repeatNumber|independentInd"/>
    <xsl:for-each select="subject">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Subject')"/>
        <xsl:call-template name="Subject"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="specimen">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Specimen')"/>
        <xsl:call-template name="Specimen"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="performer">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Performer2')"/>
        <xsl:call-template name="Performer2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="author">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Author')"/>
        <xsl:call-template name="Author"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="informant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Informant12')"/>
        <xsl:call-template name="Informant12"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="participant">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Participant2')"/>
        <xsl:call-template name="Participant2"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="product">
      <xsl:element name="participation" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Product')"/>
        <xsl:call-template name="Product"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="entryRelationship">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'EntryRelationship')"/>
        <xsl:call-template name="EntryRelationship"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="reference">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Reference')"/>
        <xsl:call-template name="Reference"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="precondition">
      <xsl:element name="outboundRelationship" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Precondition')"/>
        <xsl:call-template name="Precondition"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:apply-templates select="quantity|expectedUseTime"/>
  </xsl:template>
  <xsl:template name="Product">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Product'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
    <xsl:for-each select="manufacturedProduct">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ManufacturedProduct')"/>
        <xsl:call-template name="ManufacturedProduct"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Specimen">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Specimen'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
    <xsl:for-each select="specimenRole">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'SpecimenRole')"/>
        <xsl:call-template name="SpecimenRole"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="SpecimenRole">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'SpecimenRole'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id"/>
    <xsl:for-each select="specimenPlayingEntity">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'PlayingEntity')"/>
        <xsl:call-template name="PlayingEntity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="PlayingEntity">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'PlayingEntity'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|code|quantity|name|desc"/>
  </xsl:template>
  <xsl:template name="Performer2">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Performer2'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|time|modeCode"/>
    <xsl:for-each select="assignedEntity">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'AssignedEntity')"/>
        <xsl:call-template name="AssignedEntity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Participant2">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Participant2'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextControlCode|time|awarenessCode"/>
    <xsl:for-each select="participantRole">
      <xsl:element name="role" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ParticipantRole')"/>
        <xsl:call-template name="ParticipantRole"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="ParticipantRole">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ParticipantRole'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|id|code|addr|telecom"/>
    <xsl:for-each select="playingDevice">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Device')"/>
        <xsl:call-template name="Device"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="playingEntity">
      <xsl:element name="player" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'PlayingEntity')"/>
        <xsl:call-template name="PlayingEntity"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="scopingEntity">
      <xsl:element name="scoper" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Entity')"/>
        <xsl:call-template name="Entity"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="EntityChoice">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'EntityChoice'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId"/>
  </xsl:template>
  <xsl:template name="Device">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Device'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|code|manufacturerModelName|softwareName"/>
  </xsl:template>
  <xsl:template name="Entity">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Entity'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|determinerCode|id|code|desc"/>
  </xsl:template>
  <xsl:template name="EntryRelationship">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'EntryRelationship'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|inversionInd|contextConductionInd|sequenceNumber|negationInd|seperatableInd"/>
    <xsl:for-each select="act">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Act')"/>
        <xsl:call-template name="Act"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="encounter">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Encounter')"/>
        <xsl:call-template name="Encounter"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="observation">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Observation')"/>
        <xsl:call-template name="Observation"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="observationMedia">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ObservationMedia')"/>
        <xsl:call-template name="ObservationMedia"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="organizer">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Organizer')"/>
        <xsl:call-template name="Organizer"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="procedure">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Procedure')"/>
        <xsl:call-template name="Procedure"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="regionOfInterest">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'RegionOfInterest')"/>
        <xsl:call-template name="RegionOfInterest"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="substanceAdministration">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'SubstanceAdministration')"/>
        <xsl:call-template name="SubstanceAdministration"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="supply">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Supply')"/>
        <xsl:call-template name="Supply"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Reference">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Reference'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|seperatableInd"/>
    <xsl:for-each select="externalAct">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ExternalAct')"/>
        <xsl:call-template name="ExternalAct"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="externalDocument">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ExternalDocument')"/>
        <xsl:call-template name="ExternalDocument"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="externalObservation">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ExternalObservation')"/>
        <xsl:call-template name="ExternalObservation"/>
      </xsl:element>
    </xsl:for-each>
    <xsl:for-each select="externalProcedure">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'ExternalProcedure')"/>
        <xsl:call-template name="ExternalProcedure"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="ExternalActChoice">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ExternalActChoice'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId"/>
  </xsl:template>
  <xsl:template name="ExternalAct">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ExternalAct'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|text"/>
  </xsl:template>
  <xsl:template name="ExternalDocument">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ExternalDocument'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|text|setId|versionNumber"/>
  </xsl:template>
  <xsl:template name="ExternalObservation">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ExternalObservation'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|text"/>
  </xsl:template>
  <xsl:template name="ExternalProcedure">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'ExternalProcedure'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|id|code|text"/>
  </xsl:template>
  <xsl:template name="Precondition">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Precondition'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode"/>
    <xsl:for-each select="criterion">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Criterion')"/>
        <xsl:call-template name="Criterion"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="Criterion">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Criterion'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|classCode|moodCode|code|text|value"/>
  </xsl:template>
  <xsl:template name="Component5">
    <xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'Component5'"/>
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates select="realmCode|typeId|templateId|typeCode|contextConductionInd"/>
    <xsl:for-each select="section">
      <xsl:element name="target" namespace="urn:hl7-org:v3-rim">
        <xsl:attribute name="templates" select="concat('cda:', 'Section')"/>
        <xsl:call-template name="Section"/>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>
