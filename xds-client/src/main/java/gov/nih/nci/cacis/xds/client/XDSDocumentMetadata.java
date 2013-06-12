/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.client;

import org.openhealthtools.ihe.xds.document.DocumentDescriptor;

/**
 * Document metadata object for XDS DocumentHandler
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 *
 */
public class XDSDocumentMetadata {
        
    private String docEntryContent;
    
    private String submissionSetContent;
    
    private String docSourceOID;
    
    private String docOID;
    
    private String documentContent;
    
    private DocumentDescriptor documentType;

    
    /**
     * @return the docEntryContent
     */
    public String getDocEntryContent() {
        return docEntryContent;
    }

    
    /**
     * @param docEntryContent the docEntryContent to set
     */
    public void setDocEntryContent(String docEntryContent) {
        this.docEntryContent = docEntryContent;
    }

    
    /**
     * @return the submissionSetContent
     */
    public String getSubmissionSetContent() {
        return submissionSetContent;
    }

    
    /**
     * @param submissionSetContent the submissionSetContent to set
     */
    public void setSubmissionSetContent(String submissionSetContent) {
        this.submissionSetContent = submissionSetContent;
    }

    
    /**
     * @return the docOID
     */
    public String getDocOID() {
        return docOID;
    }


    
    /**
     * @param docOID the docOID to set
     */
    public void setDocOID(String docOID) {
        this.docOID = docOID;
    }


    /**
     * @return the documentContent
     */
    public String getDocumentContent() {
        return documentContent;
    }

    
    /**
     * @param documentContent the documentContent to set
     */
    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }

    
    /**
     * @return the documentType
     */
    public DocumentDescriptor getDocumentType() {
        return documentType;
    }

    
    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(DocumentDescriptor documentType) {
        this.documentType = documentType;
    }


    
    /**
     * @return the docSourceOID
     */
    public String getDocSourceOID() {
        return docSourceOID;
    }


    
    /**
     * @param docSourceOID the docSourceOID to set
     */
    public void setDocSourceOID(String docSourceOID) {
        this.docSourceOID = docSourceOID;
    }
    
}
