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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of DocumentRegistryRegistry that uses a Map to map DocumentRegistry IDs to URLs.
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 4, 2011
 * 
 */
public class MapDocumentRegistryRegistry implements DocumentRegistryRegistry {

    private Map<String, String> map = new HashMap<String, String>();

    /**
     * Default constructor - empty constructor
     */
    public MapDocumentRegistryRegistry() {
        //empty constructor
    }

    /**
     * Takes a map containing mappings of registry IDs to URLs
     * 
     * @param map the Map
     */
    public MapDocumentRegistryRegistry(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public URL lookup(String documentId) {
        URL url = null;
        if (getMap().containsKey(documentId)) {
            try {
                url = new URL(getMap().get(documentId));
            } catch (MalformedURLException e) {
                throw new RuntimeException("Bad registry URL: " + e.getMessage(), e); //NOPMD
            }
        }
        return url;
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

}
