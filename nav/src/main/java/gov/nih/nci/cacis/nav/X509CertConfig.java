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

import java.security.cert.CertSelector;

/**
 * Config for X509CertSelectorFactory
 * @author bpickeral
 * @since Jun 17, 2011
 * @param <T>
 */
public interface X509CertConfig<T extends CertSelector> {
    /**
     * Creates Cert Selection Factory.
     * @return - Returns factory for cert creation
     */
    CertSelectorFactory<T> createCertSelectionFactory();

    /**
     * Creates Algorithm Checker.
     * @return algorithm Checker
     */
    AlgorithmChecker createAlgorithmChecker();
}
