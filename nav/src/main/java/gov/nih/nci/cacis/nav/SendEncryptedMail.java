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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMEEnvelopedGenerator;
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.util.Strings;

/**
 * Example that sends an encrypted mail message.
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
public class SendEncryptedMail extends AbstractSendMail {

    private static final Logger LOG = Logger.getLogger(SendEncryptedMail.class);
    private static final String ERROR_INITALISING_ENCRYPTER = "Error initalising encrypter!";

    private String truststore = null;
    private String storepass = null;
    
    private KeyStore trustStoreRef;

    /**
     * Initialize Mail Encrypter
     * 
     * @param mailProperties - properties for mail sender
     * @param from - from email address
     * @param host - email server host
     * @param port - email server port
     * @param protocol - email protocol
     * @param truststore - truststore to use for encrypting mail
     * @param storepass - truststore password
     * @throws MessagingException - exception thrown, if any
     * @throws KeyStoreException - exception thrown, if any
     */
    @SuppressWarnings( { "PMD.ExcessiveParameterList" })
    // CHECKSTYLE:OFF
    public SendEncryptedMail(Properties mailProperties, String from, String host,
            int port, String protocol, String truststore, String storepass, String secEmailTempZipLocation) throws MessagingException, KeyStoreException {
        super(mailProperties, from, host, port, protocol);
        super.secEmailTempZipLocation = secEmailTempZipLocation;
        this.truststore = truststore;
        this.storepass = storepass;

        init();
    }
    //CHECKSTYLE:ON
    /**
     * Initialize Mail Encrypter. Uses localhost smtp server and default port
     * 
     * @param mailProperties - properties for mail sender
     * @param from - from email address
     * @param truststore - truststore to use for encrypting mail
     * @param storepass - truststore password
     * @throws MessagingException - exception thrown, if any
     * @throws KeyStoreException - exception thrown, if any
     */
    public SendEncryptedMail(Properties mailProperties,String from, 
            String truststore, String storepass, String secEmailTempZipLocation) throws MessagingException, KeyStoreException {
        this(mailProperties, from, "localhost", SMTP_PORT, "SMTP", truststore, storepass, secEmailTempZipLocation);
    }
    
    /**
     * Initialize Mail Encrypter
     * 
     * @param mailProperties - properties for mail sender
     * @param from - from email address
     * @param host - email server host
     * @param port - email server port
     * @param protocol - email protocol
     * @throws MessagingException - exception thrown, if any
     * @throws KeyStoreException - exception thrown, if any
     */
    public SendEncryptedMail(Properties mailProperties, String from, String host,
            int port, String protocol, String secEmailTempZipLocation) throws MessagingException, KeyStoreException {
        super(mailProperties, from, host, port, protocol);
        super.secEmailTempZipLocation = secEmailTempZipLocation;
        init();
    }

    /**
     * Initialize Mail Encrypter. Uses localhost smtp server and default port
     * 
     * @param mailProperties - properties for mail sender
     * @param from - from email address
     * @throws MessagingException - exception thrown, if any
     * @throws KeyStoreException - exception thrown, if any
     */
    public SendEncryptedMail(Properties mailProperties,String from, String secEmailTempZipLocation) throws MessagingException, KeyStoreException {
        this(mailProperties, from, "localhost", SMTP_PORT, "SMTP", secEmailTempZipLocation);
    }

    private void init() throws MessagingException, KeyStoreException {
        /* CommandCap setting */
        setCommandCap();

        /* Add BC */
        Security.addProvider(new BouncyCastleProvider());
        
        if (!StringUtils.isEmpty(truststore)) {
            trustStoreRef = getTrustStoreRef();
        }
    }

    private Certificate getCert(String keyAlias) throws MessagingException {
        Certificate result = null;
        try {
            result = trustStoreRef.getCertificate(keyAlias);
            // CHECKSTYLE:OFF
        } catch (Exception ex) { // NOPMD
            // CHECKSTYLE:ON
            LOG.error(ERROR_INITALISING_ENCRYPTER, ex);
            throw new MessagingException(ERROR_INITALISING_ENCRYPTER, ex);
        }
        if (result == null) {
            throw new IllegalArgumentException(
              String.format("Could not find any certificate with key '%s' in truststore '%s'.", 
                            keyAlias, truststore));
        }
        return result;
    }

    /**
     * encrypting of mail
     * 
     * @param message - MimeMessage to be encrypted
     * @param keyAlias - keyAlias for the certificate
     * @return MimeMessage - encrypted MimeMessage
     * @throws MessagingException - exception thrown if any
     */
    public MimeMessage encryptMail(MimeMessage message, String keyAlias) throws MessagingException {
        return encryptMail(message, getCert(keyAlias));
    }

    /**
     * encrypting of mail
     * 
     * @param message - MimeMessage to be encrypted
     * @param cert - trusted Certificate
     * @return MimeMessage - encrypted MimeMessage
     */
    public MimeMessage encryptMail(MimeMessage message, Certificate cert) {
        /* Create the message to sign and encrypt */
        final Session session = createSession(getHost(), String.valueOf(getPort()), getMailProperties());
        return encryptMail(message, session, cert);
    }

    /**
     * encrypting of mail
     * 
     * @param message - MimeMessage to be encrypted
     * @param session - Mail Session
     * @param keyAlias - keyAlias for the certificate
     * @return MimeMessage - encrypted MimeMessage
     * @throws MessagingException - exception thrown, if any
     */
    public MimeMessage encryptMail(MimeMessage message, Session session, String keyAlias) throws MessagingException {
        return encryptMail(message, session, getCert(keyAlias));
    }

    /**
     * encrypting of mail
     * 
     * @param message - MimeMessage to be encrypted
     * @param session - Mail Session
     * @param cert - trusted Certificate
     * @return MimeMessage - encrypted MimeMessage
     */
    public MimeMessage encryptMail(MimeMessage message, Session session, Certificate cert) {
        try {
            return encryptMessage(message, session, cert);
            // CHECKSTYLE:OFF
        } catch (Exception ex) { // NOPMD
            // CHECKSTYLE:ON
            LOG.error("Error sending secure mail", ex);
        }
        return null;
    }

    private MimeMessage encryptMessage(MimeMessage message, Session session, Certificate cert)
            throws NoSuchAlgorithmException, NoSuchProviderException, SMIMEException, MessagingException, IOException {
        /* Create the encrypter */
        final SMIMEEnvelopedGenerator encrypter = new SMIMEEnvelopedGenerator();
        encrypter.addKeyTransRecipient((X509Certificate) cert);

        /* Encrypt the message */
        final MimeBodyPart encryptedPart = encrypter.generate(message, SMIMEEnvelopedGenerator.AES256_CBC, PROVIDER_TYPE);

        /*
         * Create a new MimeMessage that contains the encrypted and signed content
         */
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        encryptedPart.writeTo(out);

        final MimeMessage encryptedMessage = new MimeMessage(session, new ByteArrayInputStream(out.toByteArray()));

        /* Set all original MIME headers in the encrypted message */
        final Enumeration headers = message.getAllHeaderLines();
        while (headers.hasMoreElements()) {
            final String headerLine = (String) headers.nextElement();
            /*
             * Make sure not to override any content-* headers from the original message
             */
            if (!Strings.toLowerCase(headerLine).startsWith("content-")) {
                encryptedMessage.addHeaderLine(headerLine);
            }
        }

        return encryptedMessage;
    }

    private KeyStore getTrustStoreRef() throws KeyStoreException {
        /* Open the truststore */
        KeyStore truststoreRef = null;
        InputStream is = null;
        try {
            truststoreRef = KeyStore.getInstance(STORE_TYPE, PROVIDER_TYPE);
            is = new FileInputStream(truststore);
            truststoreRef.load(is, storepass.toCharArray());
            // CHECKSTYLE:OFF
        } catch (Exception e) { // NOPMD
            // CHECKSTYLE:ON
            throw new KeyStoreException("Error loading truststore!", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOG.debug("Error closing truststore reading stream!");
                }
            }
        }
        return truststoreRef;
    }
}
