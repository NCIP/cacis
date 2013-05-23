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

import gov.nih.nci.cacis.common.doc.DocumentHandler;

import java.io.InputStream;

/**
 * XDSDocumentResolver implementation for openxds
 * 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 * 
 */
public class OpenXDSDocumentResolver implements XDSDocumentResolver {

    private final String recommendedRegistry;

    private final DocumentHandler docHandler;

    /**
     * Resolver constructor
     * @param recommendedRegistry - recommendedRegistry id 
     * @param docHandler - instance of XDSDocumentHandler
     */
    public OpenXDSDocumentResolver(String recommendedRegistry,
            DocumentHandler docHandler) {
        super();
        this.recommendedRegistry = recommendedRegistry;
        this.docHandler = docHandler;
    }

    @Override
    public String getRecommendedRegistry() {
        return recommendedRegistry;
    }

    @Override
    public InputStream resolve(String documentId) throws XDSDocumentResolutionException {
        return docHandler.retrieveDocument(documentId);
    }

}
