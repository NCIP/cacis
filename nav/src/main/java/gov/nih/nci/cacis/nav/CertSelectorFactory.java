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

import java.security.cert.CertSelector;
import java.security.cert.Certificate;

import javax.xml.crypto.KeySelectorException;

/**
 * Factory class that creates a CertSelector
 * @author bpickeral
 * @since Jun 16, 2011
 * @param <T> Cert Selector type
 */
public interface CertSelectorFactory<T extends CertSelector> {
    /**
     * Creates the CertSelector based on the information in the Certificate.
     * @param cert - The certificate containing information used to create the CertSelector.
     * @return - Returns the instantiated CertSelector.
     * @throws KeySelectorException on error when generating the CertSelector
     */
    T createCertSelector(Certificate cert) throws KeySelectorException;
}
