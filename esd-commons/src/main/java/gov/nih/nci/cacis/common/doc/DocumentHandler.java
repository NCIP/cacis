/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.doc;

import java.io.InputStream;

import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;

/**
 * Interface for handling documents
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 * 
 * @param <T1> - Setup info object for the handler initialization
 * @param <T2> - Specifies the type of document metadata object required by the Impl class
 */
public interface DocumentHandler<T1, T2> {
    /**
     * Interface method to initialize the handler
     * @param setupInfo - setup info for the handler
     * @throws ApplicationRuntimeException - exception thrown, if any
     */
    void initialize(T1 setupInfo) throws ApplicationRuntimeException;
    
    /**
     * Interface method to handle the document
     * @param documentMetadata - the metadata for the document
     * @return String representing the document unique id
     * @throws ApplicationRuntimeException - exception thrown, if any
     */
    String handleDocument(T2 documentMetadata) throws ApplicationRuntimeException;
    
    /**
     * Interface method to get the input stream to the document in the repository
     * @param docUniqueID - identifier for the doc to be retrieved
     * @return InputStream to the doc
     * @throws ApplicationRuntimeException - exception thrown, if any
     */
    InputStream retrieveDocument(String docUniqueID) throws ApplicationRuntimeException;
}
