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
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 10, 2011
 * 
 */

public class MockXDSDocumentResolver implements XDSDocumentResolver {

    private String recommendedRegistry;
    private Map<String, String> map = new HashMap<String, String>();

    /**
     * 
     * @param recommendedRegistry the registry ID
     * @param map the map of documentID to classpath resource
     */
    public MockXDSDocumentResolver(String recommendedRegistry, Map<String, String> map) {
        this.map = map;
        this.recommendedRegistry = recommendedRegistry;
    }

    /*
     * (non-Javadoc) {@inheritDoc}
     */

    @Override
    public String getRecommendedRegistry() {
        return recommendedRegistry;

    }

    /*
     * (non-Javadoc) {@inheritDoc}
     */

    @Override
    public InputStream resolve(String documentId) throws XDSDocumentResolutionException {
        InputStream in = null;
        if (getMap().containsKey(documentId)) {
            final ClassLoader cl = MockXDSDocumentResolver.class.getClassLoader();
            in = cl.getResourceAsStream(getMap().get(documentId));
        }
        return in;

    }

    /**
     * @return the map
     */

    public Map<String, String> getMap() {

        return map;
    }

    /**
     * @param map the map to set
     */

    public void setMap(Map<String, String> map) {

        this.map = map;
    }

    /**
     * @param recommendedRegistry the recommendedRegistry to set
     */

    public void setRecommendedRegistry(String recommendedRegistry) {

        this.recommendedRegistry = recommendedRegistry;
    }

}
