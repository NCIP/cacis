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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;
import javax.xml.crypto.KeySelectorException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author bpickeral
 * @since Jun 16, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-nav-test.xml" } )
public class X509CertSelectorTest {

    @Autowired
    private CertSelectorFactory factory;

    /**
     * Test successful creation of CertSelector.
     */
    @Test
    public void testCreateCertSelector() throws KeyStoreException, IOException, NoSuchAlgorithmException,
        CertificateException, UnrecoverableEntryException, KeySelectorException {
        final ClassLoader cl = NAVSystemTest.class.getClassLoader();
        final InputStream is = cl.getResourceAsStream("keystore.jks");
        final KeyStore ks = KeyStore.getInstance("JKS");

        try {
            ks.load( is, "changeit".toCharArray());
        } finally {
            is.close();
        }

        final KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("nav_test",
                new KeyStore.PasswordProtection("changeit".toCharArray()));
        final X509Certificate keyStoreCert = (X509Certificate) keyEntry.getCertificate();
        final X500Principal keyStorePrincipal = keyStoreCert.getIssuerX500Principal();

        final X509CertSelector selector = (X509CertSelector) factory.createCertSelector(keyStoreCert);
        assertEquals(keyStorePrincipal.getName(), selector.getIssuer().getName());

    }

    /**
     * Test Exception case of creating CertSelector
     */
    @Test (expected = KeySelectorException.class)
    public void testCreateCertSelectorException() throws KeySelectorException {
        factory.createCertSelector(null);
    }
}
