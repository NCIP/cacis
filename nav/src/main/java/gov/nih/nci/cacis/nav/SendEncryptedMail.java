/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The caEHR Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and 5AM Solutions Inc, SemanticBits LLC, and AgileX Technologies, Inc (collectively 'SubContractors').
 * To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United
 * States Code, section 105.
 * 
 * This caEHR Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the caEHR Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly
 * display, publicly perform, and prepare derivative works of the caEHR Software; (ii) distribute and have distributed
 * to and by third parties the caEHR Software and any modifications and derivative works thereof; and (iii) sublicense
 * the foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
 * third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting or right of
 * payment from You or Your sub-licensees for the rights granted under this License. This License is granted at no
 * charge to You.
 * 
 * Your redistributions of the source code for the Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code
 * form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 
 * Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: This
 * product includes software developed by the National Cancer Institute and SubContractor parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 * 
 * You may not use the names "The National Cancer Institute", "NCI", or any SubContractor party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or any of the subcontracted parties, except as required to comply with
 * the terms of this License.
 * 
 * For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs
 * and into any third party proprietary programs. However, if You incorporate the Software into third party proprietary
 * programs, You agree that You are solely responsible for obtaining any permission from such third parties required to
 * incorporate the Software into such third party proprietary programs and for informing Your sub-licensees, including
 * without limitation Your end-users, of their obligation to secure any required permissions from such third parties
 * before incorporating the Software into such third party proprietary software programs. In the event that You fail to
 * obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to the
 * extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 
 * For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and
 * to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses
 * of modifications of the Software, or any derivative works of the Software as a whole, provided Your use,
 * reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
 * 
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, ANY OF ITS SUBCONTRACTED PARTIES OR THEIR AFFILIATES BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
            int port, String protocol, String truststore, String storepass) throws MessagingException, KeyStoreException {
        super(mailProperties, from, host, port, protocol);
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
            String truststore, String storepass) throws MessagingException, KeyStoreException {
        this(mailProperties, from, "localhost", SMTP_PORT, "SMTP", truststore, storepass);
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
            int port, String protocol) throws MessagingException, KeyStoreException {
        super(mailProperties, from, host, port, protocol);
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
    public SendEncryptedMail(Properties mailProperties,String from) throws MessagingException, KeyStoreException {
        this(mailProperties, from, "localhost", SMTP_PORT, "SMTP");
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
        try {
                        
            return trustStoreRef.getCertificate(keyAlias);
            // CHECKSTYLE:OFF
        } catch (Exception ex) { // NOPMD
            // CHECKSTYLE:ON
            LOG.error(ERROR_INITALISING_ENCRYPTER, ex);
            throw new MessagingException(ERROR_INITALISING_ENCRYPTER, ex);
        }
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
        final MimeBodyPart encryptedPart = encrypter.generate(message, SMIMEEnvelopedGenerator.RC2_CBC, PROVIDER_TYPE);

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
