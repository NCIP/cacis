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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.i18n.ErrorBundle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.validator.SignedMailValidator;
import org.bouncycastle.mail.smime.validator.SignedMailValidatorException;
import org.bouncycastle.x509.PKIXCertPathReviewer;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

/**
 * An Example that reads a signed mail and validates its signature. Also validating the certificate path from the
 * signers key to a trusted entity
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
 * 
 * @author vinodh.rc@semanticbits.com
 */
public class ValidateSignedMail {

    private static final String DBL_TAB = "\t\t";
    private static final Logger LOG = Logger.getLogger(ValidateSignedMail.class);
    /**
     * Use trusted certificates from $JAVA_HOME/lib/security/cacerts as trustanchors
     */
    private final boolean useCaCerts;

    /**
     * Error bundle title indicator
     */
    public static final int TITLE = 0;
    /**
     * Error bundle text indicator
     */
    public static final int TEXT = 1;
    /**
     * Error bundle summary indicator
     */
    public static final int SUMMARY = 2;
    /**
     * Error bundle detail indicator
     */
    public static final int DETAIL = 3;

    private int dbgLvl = DETAIL;

    // set locale for the output
    private final Locale loc = Locale.ENGLISH;

    private static final String RESOURCE_NAME = "org.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";

    /**
     * Constructor
     * 
     * @param useCaCerts - flag to default cacerts
     */
    public ValidateSignedMail(boolean useCaCerts) {
        this.useCaCerts = useCaCerts;
    }

    /**
     * @param dbgLvl the dbgLvl to set
     */
    public void setDbgLvl(int dbgLvl) {
        this.dbgLvl = dbgLvl;
    }

    /**
     * Main method
     * 
     * @param args - array of arguments
     * @throws Exception - error thrown, if any
     */
    // CHECKSTYLE:OFF
    public static void main(String[] args) throws Exception { // NOPMD
        // CHECKSTYLE:ON

        // Get a Session object with the default properties.
        final Properties props = System.getProperties();

        final Session session = Session.getDefaultInstance(props, null);
        // read message
        final MimeMessage msg = new MimeMessage(session, new FileInputStream("signed.message"));

        final ValidateSignedMail vsm = new ValidateSignedMail(false);
        vsm.validate(msg, "securemail.p12", "changeit", "cacisnavtestuser@gmail.com");
    }

    /**
     * validates signed MimeMessage
     * 
     * @param msg - signed MimeMessage
     * @param keystorePath - keystore to be used
     * @param storepass - Keystore password(same as key)
     * @param keyAlias - key to be used
     * @throws MessagingException - error thrown, if any
     */
    public void validate(MimeMessage msg, String keystorePath, String storepass, String keyAlias)
            throws MessagingException {
        try {

            Security.addProvider(new BouncyCastleProvider());

            // send empty CRL for now. Not being used
            final List crls = new ArrayList();

            final Certificate[] chain = getCertificateChain(keystorePath, storepass, keyAlias);

            final Set trustanchors = new HashSet();
            final TrustAnchor trust = getTrustAnchor((X509Certificate) chain[0]);
            trustanchors.add(trust);

            final PKIXParameters param = new PKIXParameters(trustanchors);

            final CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(crls),
                    "BC");

            // add crls, but disabling revocation checking for now
            param.addCertStore(certStore);

            // disable revocation checking
            param.setRevocationEnabled(false);

            verifySignedMail(msg, param);
            // CHECKSTYLE:OFF
        } catch (Exception e) { // NOPMD
            // CHECKSTYLE:ON
            throw new MessagingException("Validation failed!", e);
        }
    }

    /**
     * Verifies signed MimeMessage, as per the supplied PKIXParameters
     * 
     * @param msg - signed message
     * @param param - PKIXParameters
     * @throws MessagingException - error thrown, if any
     */
    public void verifySignedMail(MimeMessage msg, PKIXParameters param) throws MessagingException {

        try {
            // validate signatures
            final SignedMailValidator validator = new SignedMailValidator(msg, param);

            // iterate over all signatures and print results
            final Iterator it = validator.getSignerInformationStore().getSigners().iterator();

            while (it.hasNext()) {
                final SignerInformation signer = (SignerInformation) it.next();
                final SignedMailValidator.ValidationResult result = validator.getValidationResult(signer);

                final boolean validSign = handleValidateResults(result);

                handleNotifications(result);

                final PKIXCertPathReviewer review = result.getCertPathReview();

                boolean validCertPath = true;
                if (review != null) {
                    validCertPath = handleCertPathValidation(review);

                    handleCertPathReviewNotifications(review);

                    handleCertificateErrorsAndNotifications(review);
                }

                if (!validSign || !validCertPath) {
                    throw new MessagingException("Validation of signed message failed!");
                }
            }
        } catch (SignedMailValidatorException e) {
            throw new MessagingException("Validation of signed message failed!", e);
        }

    }

    private boolean handleValidateResults(SignedMailValidator.ValidationResult result) {
        boolean valid = true;
        if (result.isValidSignature()) {
            final ErrorBundle errMsg = getErrorBundle("SignedMailValidator.sigValid");
            LOG.warn(errMsg.getText(loc));
        } else {
            valid = false;
            final ErrorBundle errMsg = getErrorBundle("SignedMailValidator.sigInvalid");
            LOG.warn(errMsg.getText(loc));
            // print errors
            LOG.warn("Errors:");
            final Iterator errorsIt = result.getErrors().iterator();
            while (errorsIt.hasNext()) {
                final ErrorBundle errorMsg = (ErrorBundle) errorsIt.next();
                if (dbgLvl == DETAIL) {
                    LOG.warn(DBL_TAB + errorMsg.getDetail(loc));
                } else {
                    LOG.warn(DBL_TAB + errorMsg.getText(loc));
                }
            }
        }
        return valid;
    }

    private void handleNotifications(SignedMailValidator.ValidationResult result) {
        if (!result.getNotifications().isEmpty()) {
            LOG.warn("Notifications:");
            final Iterator notIt = result.getNotifications().iterator();
            while (notIt.hasNext()) {
                final ErrorBundle notMsg = (ErrorBundle) notIt.next();
                if (dbgLvl == DETAIL) {
                    LOG.warn(DBL_TAB + notMsg.getDetail(loc));
                } else {
                    LOG.warn(DBL_TAB + notMsg.getText(loc));
                }
            }
        }
    }

    private boolean handleCertPathValidation(PKIXCertPathReviewer review) {
        boolean valid = true;

        if (review.isValidCertPath()) {
            LOG.warn("Certificate path valid");
        } else {
            LOG.warn("Certificate path invalid");
            valid = false;
        }

        LOG.warn("\nCertificate path validation results:");
        // global errors
        LOG.warn("Errors:");
        final Iterator errorsIt = review.getErrors(-1).iterator();
        while (errorsIt.hasNext()) {
            final ErrorBundle errorMsg = (ErrorBundle) errorsIt.next();
            if (dbgLvl == DETAIL) {
                LOG.warn(DBL_TAB + errorMsg.getDetail(loc));
            } else {
                LOG.warn(DBL_TAB + errorMsg.getText(loc));
            }
        }

        return valid;
    }

    private void handleCertPathReviewNotifications(PKIXCertPathReviewer review) {
        LOG.warn("Notifications:");
        final Iterator notificationsIt = review.getNotifications(-1).iterator();
        while (notificationsIt.hasNext()) {
            final ErrorBundle noteMsg = (ErrorBundle) notificationsIt.next();
            LOG.warn("\t" + noteMsg.getText(loc));
        }
    }

    private void handleCertificateErrorsAndNotifications(PKIXCertPathReviewer review) {
        // per certificate errors and notifications
        final Iterator certIt = review.getCertPath().getCertificates().iterator();
        int i = 0;
        while (certIt.hasNext()) {
            final X509Certificate cert = (X509Certificate) certIt.next();
            LOG.warn("\nCertificate " + i + "\n========");
            LOG.warn("Issuer: " + cert.getIssuerDN().getName());
            LOG.warn("Subject: " + cert.getSubjectDN().getName());

            // errors
            LOG.warn("\tErrors:");
            final Iterator errorsIt = review.getErrors(i).iterator();
            while (errorsIt.hasNext()) {
                final ErrorBundle errorMsg = (ErrorBundle) errorsIt.next();
                if (dbgLvl == DETAIL) {
                    LOG.warn(DBL_TAB + errorMsg.getDetail(loc));
                } else {
                    LOG.warn(DBL_TAB + errorMsg.getText(loc));
                }
            }

            // notifications
            LOG.warn("\tNotifications:");
            final Iterator notificationsIt = review.getNotifications(i).iterator();
            while (notificationsIt.hasNext()) {
                final ErrorBundle noteMsg = (ErrorBundle) notificationsIt.next();
                if (dbgLvl == DETAIL) {
                    LOG.warn(DBL_TAB + noteMsg.getDetail(loc));
                } else {
                    LOG.warn(DBL_TAB + noteMsg.getText(loc));
                }
            }

            i++;
        }
    }

    private ErrorBundle getErrorBundle(String id) {
        final ErrorBundle errMsg = new ErrorBundle(RESOURCE_NAME, id);
        errMsg.setClassLoader(getClass().getClassLoader());
        return errMsg;
    }

    private static TrustAnchor getTrustAnchor(X509Certificate cert) throws IOException {
        if (cert != null) {
            final byte[] ncBytes = cert.getExtensionValue(X509Extensions.NameConstraints.getId());

            if (ncBytes != null) {
                final ASN1Encodable extValue = X509ExtensionUtil.fromExtensionValue(ncBytes);
                return new TrustAnchor(cert, extValue.getDEREncoded());
            }
            return new TrustAnchor(cert, null);
        }
        return null;
    }

    private Certificate[] getCertificateChain(String keystorePath, String storepass, String keyAlias)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
            IOException, NoSuchProviderException {
        KeyStore caCerts = null;
        if (useCaCerts) {
            caCerts = KeyStore.getInstance("JKS");
            final String javaHome = System.getProperty("java.home");
            caCerts.load(new FileInputStream(javaHome + "/lib/security/cacerts"), "changeit".toCharArray());
        } else {
            caCerts = KeyStore.getInstance("PKCS12", "BC");
            // final InputStream is = getClass().getClassLoader().getResourceAsStream(keystorePath);
            final InputStream is = new FileInputStream(keystorePath);
            caCerts.load(is, storepass.toCharArray());
        }

        Certificate[] chain = new Certificate[1];
        chain[0] = caCerts.getCertificate(keyAlias);
        return chain;

        // return caCerts.getCertificateChain(keyAlias);
    }

}
