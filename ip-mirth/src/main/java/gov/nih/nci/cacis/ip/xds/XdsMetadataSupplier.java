/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.xds;

import java.io.IOException;

/**
 * Implementation will provide some way of
 * create XDS Metadata for a Clinical Document
 *
 * @author kherm manav.kher@semanticbits.com
 */
public interface XdsMetadataSupplier {

    /**
     *  XDS Metadata DocEntry
     *
     * @param  caCISRequest request 
     * @return XDS DocEntry
     * @throws IOException Exception
     */
    String createDocEntry(String caCISRequest) throws IOException;

    /**
     *  XDS Metadata Submission Set
     *
     * @param  caCISRequest request
     * @return XDS SubmissionSet
     * @throws IOException Exception
     */
    String createSubmissionSet(String caCISRequest) throws IOException;
    
    /**
     *  XDS Metadata Doc OID
     *  
     * @return XDS DocEntry
     */
    String createDocOID();

    /**
     *  XDS Metadata Source OID
     *
     * @return XDS DocEntry
     */
    String createDocSourceOID();
}
