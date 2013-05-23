/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import java.io.InputStream;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 10, 2011
 * 
 */
public interface XDSDocumentResolver {

    /**
     * Resolves a document ID to an InputStream
     * 
     * @param documentId the document ID
     * @return the InputStream
     * @throws XDSDocumentResolutionException on error
     */
    InputStream resolve(String documentId) throws XDSDocumentResolutionException;

    /**
     * 
     * @return the ID of the document registry
     */
    String getRecommendedRegistry();
}
