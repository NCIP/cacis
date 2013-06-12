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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author bpickeral
 * @since Jun 23, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-nav-test.xml" } )
public class X509KeySelectorTest {
    @Autowired
    private CertSelectorFactory certSelectorFactory;

    @Autowired
    private AlgorithmChecker algorithmChecker;

    @Test
    public void select() throws Exception {

        final ClassLoader cl = NAVSystemTest.class.getClassLoader();
        final InputStream is = cl.getResourceAsStream("keystore.jks");
        try {
            final XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

            final KeyStore ks = KeyStore.getInstance("JKS");
            ks.load( is, "changeit".toCharArray());

            final KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("nav_test",
                    new KeyStore.PasswordProtection("changeit".toCharArray()));
            final X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

            final KeyInfoFactory kif = fac.getKeyInfoFactory();
            final List x509Content = new ArrayList();
            x509Content.add(cert.getSubjectX500Principal().getName());
            x509Content.add(cert);
            final X509Data xd = kif.newX509Data(x509Content);
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

            SignatureMethod sm = fac.newSignatureMethod(
                    SignatureMethod.RSA_SHA1, null);

            final X509KeySelector selector = new X509KeySelector(ks, certSelectorFactory, algorithmChecker);

            assertNotNull(selector.select(ki,
                    KeySelector.Purpose.VERIFY, sm, null));
        } finally {
            closeInputStream(is);
        }

    }

    @Test (expected = KeySelectorException.class)
    public void selectNoMatchingKey() throws Exception {

        final ClassLoader cl = NAVSystemTest.class.getClassLoader();
        final InputStream is = cl.getResourceAsStream("keystore.jks");
        try {
            final XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

            final KeyStore ks = KeyStore.getInstance("JKS");
            ks.load( is, "changeit".toCharArray());

            final KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("nav_test",
                    new KeyStore.PasswordProtection("changeit".toCharArray()));
            final X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

            final KeyInfoFactory kif = fac.getKeyInfoFactory();
            final List x509Content = new ArrayList();
            x509Content.add(cert.getSubjectX500Principal().getName());
            x509Content.add(cert);
            final X509Data xd = kif.newX509Data(x509Content);
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

            SignatureMethod sm = fac.newSignatureMethod(
                    SignatureMethod.DSA_SHA1, null);

            final X509KeySelector selector = new X509KeySelector(ks, certSelectorFactory, algorithmChecker);

            selector.select(ki, KeySelector.Purpose.VERIFY, sm, null);
        } finally {
            closeInputStream(is);
        }

    }

    @Test
    public void selectNullKeyInfo() throws Exception {

        final ClassLoader cl = NAVSystemTest.class.getClassLoader();
        final InputStream is = cl.getResourceAsStream("keystore.jks");
        try {
            final XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

            final KeyStore ks = KeyStore.getInstance("JKS");
            ks.load( is, "changeit".toCharArray());

            SignatureMethod sm = fac.newSignatureMethod(
                    SignatureMethod.RSA_SHA1, null);

            final X509KeySelector selector = new X509KeySelector(ks, certSelectorFactory, algorithmChecker);

            KeySelectorResult result = selector.select(null,
                    KeySelector.Purpose.VERIFY, sm, null);
            assertNotNull(result);
            assertNull(result.getKey());
        } finally {
            closeInputStream(is);
        }

    }

    private void closeInputStream(InputStream is) throws IOException {
        if ( is != null ) {
            is.close();
        }
    }

    @Test (expected = NullPointerException.class)
    public void constructorNullKeyStore() throws Exception {
        new X509KeySelector(null, certSelectorFactory, algorithmChecker);
    }

    @Test (expected = NullPointerException.class)
    public void constructorNullFactory() throws Exception {
        new X509KeySelector(KeyStore.getInstance("JKS"), null, algorithmChecker);
    }

    @Test (expected = NullPointerException.class)
    public void constructorNullAlgorithmChecker() throws Exception {
        new X509KeySelector(KeyStore.getInstance("JKS"), certSelectorFactory, null);
    }

}
