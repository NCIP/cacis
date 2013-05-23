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

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Iterator;

import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.X509Data;

/**
 * A <code>KeySelector</code> that returns {@link PublicKey}s.
 *
 * <p>
 * This <code>KeySelector</code> uses the specified <code>KeyStore</code> to find a trusted <code>X509Certificate</code>
 * that matches information specified in the {@link KeyInfo} passed to the {@link #select} method. The public key from
 * the first match is returned. If no match, <code>null</code> is returned. See the <code>select</code> method for more
 * information.
 *
 * <p>
 * NOTE!: This X509KeySelector requires J2SE 1.4 because it uses the java.security.cert.X509CertSelector &
 * javax.security.auth.x500.X500Principal classes to parse X.500 DNs and match on certificate attributes.
 *
 * Original File copied from:
 * http://java2s.com/Open-Source/Java-Document/XML/xml-security-1.4.3/javax/xml/crypto/test/dsig/X509KeySelector.java.htm
 *
 * @author Sean Mullan
 *
 * @author bpickeral
 * @since Jun 15, 2011
 */
public class X509KeySelector extends KeySelector {

    private final KeyStore ks;

    private final CertSelectorFactory certSelectorFactory;

    private final AlgorithmChecker algorithmChecker;

    /**
     * Creates a trusted <code>X509KeySelector</code>.
     *
     * @param keyStore the keystore
     * @param certSelectorFactory the CertSelectorFactory
     * @param algorithmChecker the Algorithm Checker
     * @throws KeyStoreException if the keystore has not been initialized
     */
    public X509KeySelector(KeyStore keyStore, CertSelectorFactory certSelectorFactory,
            AlgorithmChecker algorithmChecker) throws KeyStoreException {
        if (keyStore == null) {
            throw new NullPointerException("keyStore can not be null");
        }
        if (certSelectorFactory == null) {
            throw new NullPointerException("certSelectorFactory can not be null");
        }
        if (algorithmChecker == null) {
            throw new NullPointerException("algorithmChecker can not be null");
        }
        this.algorithmChecker = algorithmChecker;
        this.ks = keyStore;
        this.certSelectorFactory = certSelectorFactory;
        // Throw Exception if KeyStore has not been initialized
        this.ks.size();
    }

    /**
     * Finds a key from the keystore satisfying the specified constraints.
     *
     * <p>
     * This method compares data contained in {@link KeyInfo} entries with information stored in the
     * <code>KeyStore</code>. The implementation iterates over the KeyInfo types and returns the first {@link PublicKey}
     * of an X509Certificate in the keystore that is compatible with the specified AlgorithmMethod according to the
     * following rules for each keyinfo type:
     *
     * X509Data X509Certificate: if it contains a <code>KeyUsage</code> extension that asserts the
     * <code>digitalSignature</code> bit and matches an <code>X509Certificate</code> in the <code>KeyStore</code>.
     * X509Data X509IssuerSerial: if the serial number and issuer DN match an <code>X509Certificate</code> in the
     * <code>KeyStore</code>. X509Data X509SubjectName: if the subject DN matches an <code>X509Certificate</code> in the
     * <code>KeyStore</code>. X509Data X509SKI: if the subject key identifier matches an <code>X509Certificate</code> in
     * the <code>KeyStore</code>. KeyName: if the keyname matches an alias in the <code>KeyStore</code>.
     * RetrievalMethod: supports rawX509Certificate and X509Data types. If rawX509Certificate type, it must match an
     * <code>X509Certificate</code> in the <code>KeyStore</code>.
     *
     * @param keyInfo a <code>KeyInfo</code> (may be <code>null</code>)
     * @param purpose the key's purpose
     * @param method the algorithm method that this key is to be used for. Only keys that are compatible with the
     *            algorithm and meet the constraints of the specified algorithm should be returned.
     * @param context the context
     * @return a key selector result
     * @throws KeySelectorException if an exceptional condition occurs while attempting to find a key. Note that an
     *             inability to find a key is not considered an exception (<code>null</code> should be returned in that
     *             case). However, an error condition (ex: network communications failure) that prevented the
     *             <code>KeySelector</code> from finding a potential key should be considered an exception.
     */
    @Override
    public KeySelectorResult select(KeyInfo keyInfo, KeySelector.Purpose purpose, AlgorithmMethod method,
            XMLCryptoContext context) throws KeySelectorException {

        final KeySelectorResult returnKey = new SimpleKeySelectorResult(null);
        try {
            // return null if keyinfo is null or keystore is empty
            if (keyInfo == null || ks.size() == 0) {
                return new SimpleKeySelectorResult(null);
            }

            // Iterate through KeyInfo types
            final Iterator i = keyInfo.getContent().iterator();
            while (i.hasNext()) {
                final XMLStructure kiType = (XMLStructure) i.next();
                // check X509Data
                if (kiType instanceof X509Data) {
                    return getKey((X509Data) kiType, method);
                }
            }
        } catch (KeyStoreException kse) {
            // throw exception if keystore is uninitialized
            throw new KeySelectorException(kse);
        }

        // return null since no match could be found
        return returnKey;
    }

    private KeySelectorResult getKey(X509Data xd, AlgorithmMethod method) throws KeyStoreException, KeySelectorException  {
        final Iterator xi = xd.getContent().iterator();
        while (xi.hasNext()) {
            final Object o = xi.next();
            if (!(o instanceof X509Certificate)) {
                continue;
            }
            final X509Certificate xcert = ((X509Certificate) o);
            final PublicKey key = xcert.getPublicKey();
            // Make sure the algorithm is compatible
            // with the method.
            final String algorithm = method.getAlgorithm();
            final String keyAlgorithm = key.getAlgorithm();
            if (algorithmChecker.algEquals(algorithm, keyAlgorithm)) {
                return keyStoreSelect(certSelectorFactory.createCertSelector(xcert));
            }
        }
        throw new KeySelectorException("No matching Key found!");
    }

    /**
     * Searches the specified keystore for a certificate that matches the criteria specified in the CertSelector.
     *
     * @return a KeySelectorResult containing the cert's public key if there is a match; otherwise null
     */
    private KeySelectorResult keyStoreSelect(CertSelector cs) throws KeyStoreException {
        final Enumeration aliases = ks.aliases();
        while (aliases.hasMoreElements()) {
            final String alias = (String) aliases.nextElement();
            final Certificate cert = ks.getCertificate(alias);
            if (cert != null && cs.match(cert)) {
                return new SimpleKeySelectorResult(cert.getPublicKey());
            }
        }
        return null;
    }

    /**
     * A simple KeySelectorResult containing a public key.
     */
    private static class SimpleKeySelectorResult implements KeySelectorResult {

        private final Key key;

        SimpleKeySelectorResult(Key key) {
            this.key = key;
        }

        public Key getKey() {
            return key;
        }
    }
}
