/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Represents an XDS Document
 * as a secured resource
 */
@Entity
@DiscriminatorValue("xds-document")
public class DocumentResource extends Resource {

    /**
     * Document ID
     */
    private String docId;


    /**
     * No args constructor
     */
    public DocumentResource() {
        super();
    }

    /**
     * Constructor that takes
     * a Document ID as a parameter
     * @param docId Document ID
     */
    public DocumentResource(String docId) {
        this.docId = docId;
    }

    /**
     * Getter for Document ID
     * @return Id ID
     */
    public String getDocId() {
        return docId;
    }

    /**
     * Setter for Document ID
     * @param docId ID
     */
    public void setDocId(String docId) {
        this.docId = docId;
    }
}
