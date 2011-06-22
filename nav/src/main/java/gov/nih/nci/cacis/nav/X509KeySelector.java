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
            if (algorithmChecker.algEquals(method.getAlgorithm(), key.getAlgorithm())) {
                return keyStoreSelect(certSelectorFactory.createCertSelector(xcert));
            }
        }
        throw new KeySelectorException("No matching X509SubjectName found!");
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
