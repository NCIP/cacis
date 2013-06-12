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

/**
 * Interface for classes that check that the algorithm name matches the algorithm URI.
 * @author bpickeral
 * @since Jun 21, 2011
 */
public interface AlgorithmChecker {
    /**
     * Check that the algorithm name matches the algorithm URI.
     * @param algURI Algorithm URI
     * @param algName Algorithm Name
     * @return boolean representing if the algorithm name matches the algorithm URI
     */
    boolean algEquals(String algURI, String algName);
}
