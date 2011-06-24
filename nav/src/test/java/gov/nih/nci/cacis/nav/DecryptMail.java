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
