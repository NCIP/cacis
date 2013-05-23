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

import java.io.IOException;
import java.io.InputStream;

/**
 * XDS document resolver backed by InMemoryCache
 * 
 * @author vinodh.rc@semanticbits.com
 * @since May 13, 2011
 * 
 */

public class InMemoryCacheXDSDocumentResolver implements XDSDocumentResolver {

    private String recommendedRegistry;
    private InMemoryCacheDocumentHolder docCache;

    
    /**
     * 
     * @param recommendedRegistry - String identifier for the recommended registry
     * @param docCache - Instance of InMemoryCacheDocumentHolder
     */
    public InMemoryCacheXDSDocumentResolver(String recommendedRegistry, InMemoryCacheDocumentHolder docCache) {
        this.docCache = docCache;
        this.recommendedRegistry = recommendedRegistry;
    }

    /*
     * (non-Javadoc) {@inheritDoc}
     */

    @Override
    public InputStream resolve(String documentId) throws XDSDocumentResolutionException {
        InputStream in = null;
        if (getDocCache().containsDocument(documentId)) {
            try {
                in = getDocCache().getDocument(documentId);
            } catch (IOException e) {
                throw new XDSDocumentResolutionException(
                        "Error retrieving the document for the id, " + documentId, e);
            }
        }
        return in;

    }

    /*
     * (non-Javadoc) {@inheritDoc}
     */

    /**
     * @return the docCache
     */
    public InMemoryCacheDocumentHolder getDocCache() {
        return docCache;
    }

    /**
     * @param docCache the docCache to set
     */
    public void setDocCache(InMemoryCacheDocumentHolder docCache) {
        this.docCache = docCache;
    }

    @Override
    public String getRecommendedRegistry() {
        return recommendedRegistry;

    }

    /**
     * @param recommendedRegistry the recommendedRegistry to set
     */

    public void setRecommendedRegistry(String recommendedRegistry) {

        this.recommendedRegistry = recommendedRegistry;
    }

}
