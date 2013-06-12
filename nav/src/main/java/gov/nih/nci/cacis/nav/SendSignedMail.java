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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.bouncycastle.asn1.smime.SMIMECapabilitiesAttribute;
import org.bouncycastle.asn1.smime.SMIMECapability;
import org.bouncycastle.asn1.smime.SMIMECapabilityVector;
import org.bouncycastle.asn1.smime.SMIMEEncryptionKeyPreferenceAttribute;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.mail.smime.SMIMESignedGenerator;

/**
 * Example that sends a signed mail message.
 * 
 * Copyright (c) 2000 - 2011 The Legion Of The Bouncy Castle (http://www.bouncycastle.org)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * @author vinodh.rc@semanticbits.com
 */
public class SendSignedMail extends AbstractSendMail {

    private static final Logger LOG = Logger.getLogger(SendSignedMail.class);

    private final String keystore;
    private final String storepass;
    private final String keyAlias;

    private Certificate[] chain;
    private PrivateKey privateKey;

    /**
     * Initialize mail signer
     * 
     * @param mailProperties - properties for mail sender
     * @param from - from email address
     * @param host - email server host
     * @param port - email server port
     * @param protocol - email protocol
     * @param keystore - keystore to use for signing mail
     * @param storepass - keystore password
     * @param keyAlias - key alias to use
     * @throws MessagingException - exception thrown, if any
     */
    @SuppressWarnings( { "PMD.ExcessiveParameterList" })
    // CHECKSTYLE:OFF
    public SendSignedMail(Properties mailProperties, String from, String host, int port, String protocol,
            String keystore, String storepass, String keyAlias) throws MessagingException {
        super(mailProperties, from, host, port, protocol);
        this.keystore = keystore;
        this.storepass = storepass;
        this.keyAlias = keyAlias;

        init();
    }

    // CHECKSTYLE:ON
    /**
     * Initialize mail signer. Uses local host and default port
     * 
     * @param mailProperties - properties for mail sender
     * @param from - from email address
     * @param keystore - keystore to use for signing mail
     * @param storepass - keystore password
     * @param keyAlias - key alias to use
     * @throws MessagingException - exception thrown, if any
     */
    public SendSignedMail(Properties mailProperties, String from, String keystore, String storepass, String keyAlias)
            throws MessagingException {
        this(mailProperties, from, "localhost", SMTP_PORT, "SMTP", keystore, storepass, keyAlias);
    }

    private void init() throws MessagingException {
        try {
            /* CommandCap setting */
            setCommandCap();

            /* Add BC */
            Security.addProvider(new BouncyCastleProvider());

            /* Get keystore ref */
            final KeyStore keystoreRef = getKeyStoreRef();
            chain = keystoreRef.getCertificateChain(keyAlias);

            /* Get the private key to sign the message with */
            privateKey = (PrivateKey) keystoreRef.getKey(keyAlias, storepass.toCharArray());
            if (privateKey == null) {
                throw new Exception("cannot find private key for alias: " + keyAlias);
            }
            // CHECKSTYLE:OFF
        } catch (Exception ex) { // NOPMD
            // CHECKSTYLE:ON
            LOG.error("Error initalising signer!", ex);
            throw new MessagingException("Error initalising signer!", ex);
        }
    }

    /**
     * signing of mail
     * 
     * @param message - MimeMessage to be signed
     * @return MimeMessage - signed MimeMessage
     */
    public MimeMessage signMail(MimeMessage message) {
        /* Create the message to sign and encrypt */
        final Session session = createSession(getHost(), String.valueOf(getPort()), getMailProperties());
        return signMail(message, session);
    }

    /**
     * signing of mail
     * 
     * @param message - MimeMessage to be signed
     * @param session - Mail Session
     * @return MimeMessage - signed MimeMessage
     */
    public MimeMessage signMail(MimeMessage message, Session session) {
        try {
            return signMessage(message, session, chain, privateKey);
            // CHECKSTYLE:OFF
        } catch (Exception ex) { // NOPMD
            // CHECKSTYLE:ON
            LOG.error("Error signing mail", ex);
        }
        return null;
    }

    private MimeMessage signMessage(MimeMessage message, Session session, Certificate[] chain, PrivateKey privateKey)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException,
            CertStoreException, SMIMEException, MessagingException {
        /* Create the SMIMESignedGenerator */
        final SMIMESignedGenerator signer = createSigner(chain, privateKey);

        /* Add the list of certs to the generator */
        final List certList = new ArrayList();
        certList.add(chain[0]);
        final CertStore certs = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
        signer.addCertificatesAndCRLs(certs);

        /* Sign the message */
        final MimeMultipart mm = signer.generate(message, "BC");
        final MimeMessage signedMessage = new MimeMessage(session);

        /* Set all original MIME headers in the signed message */
        final Enumeration headers = message.getAllHeaderLines();
        while (headers.hasMoreElements()) {
            signedMessage.addHeaderLine((String) headers.nextElement());
        }

        /* Set the content of the signed message */
        signedMessage.setContent(mm);
        signedMessage.saveChanges();

        return signedMessage;
    }

    private KeyStore getKeyStoreRef() throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException,
            CertificateException, IOException {
        /* Open the keystore */
        KeyStore keystoreRef = null;
        InputStream is = null;
        try {
            keystoreRef = KeyStore.getInstance(STORE_TYPE, PROVIDER_TYPE);
            is = new FileInputStream(keystore);
            keystoreRef.load(is, storepass.toCharArray());
            // CHECKSTYLE:OFF
        } catch (Exception e) { // NOPMD
            // CHECKSTYLE:ON
            throw new KeyStoreException("Error loading keystore!", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOG.debug("Error closing keystore reading stream!");
                }
            }
        }
        return keystoreRef;
    }

    private SMIMESignedGenerator createSigner(Certificate[] chain, PrivateKey privateKey) {
        final SMIMECapabilityVector capabilities = new SMIMECapabilityVector();
        capabilities.addCapability(SMIMECapability.dES_EDE3_CBC);
        capabilities.addCapability(SMIMECapability.rC2_CBC, 128);
        capabilities.addCapability(SMIMECapability.dES_CBC);

        final ASN1EncodableVector attributes = new ASN1EncodableVector();
        attributes
                .add(new SMIMEEncryptionKeyPreferenceAttribute(new IssuerAndSerialNumber(new X509Name(
                        ((X509Certificate) chain[0]).getIssuerDN().getName()), ((X509Certificate) chain[0])
                        .getSerialNumber())));
        attributes.add(new SMIMECapabilitiesAttribute(capabilities));

        final SMIMESignedGenerator signer = new SMIMESignedGenerator();
        signer.addSigner(privateKey, (X509Certificate) chain[0],
                "DSA".equals(privateKey.getAlgorithm()) ? SMIMESignedGenerator.DIGEST_SHA1
                        : SMIMESignedGenerator.DIGEST_MD5, new AttributeTable(attributes), null);

        return signer;
    }
}
