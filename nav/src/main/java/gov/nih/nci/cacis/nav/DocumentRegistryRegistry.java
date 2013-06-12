/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import java.net.URL;

/**
 * Responsible for returning a URL, given a unique DocumentRegistry ID.
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 4, 2011
 * 
 */
public interface DocumentRegistryRegistry {

    /**
     * Returns a URL, given a DocumentRegistry ID.
     * 
     * @param registryId the DocumentRegistry ID
     * @return a URL
     */
    URL lookup(String registryId);

}
