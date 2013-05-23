/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.model;

public class CdwPermissionModel {

    private String studyID = "";
    private String siteID = "";
    private String patientID = "";
    private String graphGroupRGGIRI = "";

    public String getStudyID() {
        return studyID;
    }

    public void setStudyID(String studyID) {
        this.studyID = studyID;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
    
    public String getGraphGroupRGGIRI() {
        return graphGroupRGGIRI;
    }
    
    public void setGraphGroupRGGIRI(String graphGroupRGGIRI) {
        this.graphGroupRGGIRI = graphGroupRGGIRI;
    }    

}
