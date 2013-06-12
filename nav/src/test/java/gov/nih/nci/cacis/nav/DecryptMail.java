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
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.bouncycastle.cms.RecipientId;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.RecipientInformationStore;
import org.bouncycastle.mail.smime.SMIMEEnveloped;
import org.bouncycastle.mail.smime.SMIMEUtil;

/**
 * a simple example that reads an encrypted email.
 * <p>
 * The key store can be created using the class in org.bouncycastle.jce.examples.PKCS12Example - the program expects
 * only one key to be present.
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
public class DecryptMail {

    private static final String PROVIDER_TYPE = "BC";
    private static final String STORE_TYPE = "PKCS12";
//    private static final String PROVIDER_TYPE = "SUN";
//    private static final String STORE_TYPE = "JKS";
    private static final Logger LOG = Logger.getLogger(DecryptMail.class);

    /**
     * Main method
     * 
     * @param args - array of arguments
     * @throws Exception - error thrown, if any
     */
    // CHECKSTYLE:OFF
    public static void main(String args[]) throws Exception { // NOPMD
        // CHECKSTYLE:ON
        if (args.length != 2) {
            LOG.error("usage: DecryptMail pkcs12Keystore password");
            System.exit(0);
        }
    }

    /**
     * Decrypts the encrypted MimeMessage based on supplied key
     * 
     * @param msg - encrypte MimeMessage
     * @param keystorepath - keystore to be used
     * @param storepass - keystore password (same as key)
     * @param keyAlias - alias for the key to be used
     * @return - Decrypted MimeBodyPart
     * @throws Exception - exception thrown, if any
     */
    public MimeBodyPart decrypt(MimeMessage msg, String keystorepath, String storepass, String keyAlias)
            throws Exception { // NOPMD
        //
        // Open the key store
        //
        final KeyStore ks = KeyStore.getInstance(STORE_TYPE, PROVIDER_TYPE);
        // final InputStream is = getClass().getClassLoader().getResourceAsStream(keystorePath);
        final InputStream is = new FileInputStream(keystorepath);
        ks.load(is, storepass.toCharArray());

        //
        // find the certificate for the private key and generate a
        // suitable recipient identifier.
        //
        final X509Certificate cert = (X509Certificate) ks.getCertificate(keyAlias);
        final RecipientId recId = new RecipientId();

        recId.setSerialNumber(cert.getSerialNumber());
        recId.setIssuer(cert.getIssuerX500Principal().getEncoded());

        final SMIMEEnveloped m = new SMIMEEnveloped(msg);
        

        final RecipientInformationStore recipients = m.getRecipientInfos();
        final RecipientInformation recipient = recipients.get(recId);

        final MimeBodyPart res = SMIMEUtil.toMimeBodyPart(recipient.getContent(ks.getKey(keyAlias, null), PROVIDER_TYPE));

        LOG.debug("Message Contents");
        LOG.debug("----------------");
        LOG.debug(res.getContent());

        return res;
    }
}
