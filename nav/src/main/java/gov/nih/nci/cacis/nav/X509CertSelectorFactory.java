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

import java.security.cert.Certificate;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;
import javax.xml.crypto.KeySelectorException;

/**
 * @author bpickeral
 * @since Jun 16, 2011
 */
public class X509CertSelectorFactory implements CertSelectorFactory<X509CertSelector> {

    /**
     * Generates the X509CertSelector used for finding the KeyStore based on Principal name (this is the
     *  X509SubjectName in the notification header).
     * @param cert X.509 certificate
     * @return X509CertSelector to be used in finding the KeyStore
     * @throws KeySelectorException on error when getting the CertSelector
     */
    @Override
    public X509CertSelector createCertSelector(Certificate cert) throws KeySelectorException {
        try {
            final X509Certificate xcert = (X509Certificate) cert;
            final X500Principal xPrincipal = xcert.getIssuerX500Principal();
            final X509CertSelector subjectcs = new X509CertSelector();
            subjectcs.setIssuer(xPrincipal.getName());

            return subjectcs;
        //CHECKSTYLE:OFF Generic Exception
        } catch (Exception e) {
        //CHECKSTYLE:ON
            throw new KeySelectorException(e);
        }
    }
}
