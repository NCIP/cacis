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

import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import javax.security.auth.x500.X500Principal;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyName;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import javax.xml.crypto.dsig.keyinfo.X509Data;

// TODO: This class should be improved/replaced, see http://caehrorg.jira.com/browse/ESD-2833

// CHECKSTYLE:OFF
/**
 * A <code>KeySelector</code> that returns {@link PublicKey}s. If the selector is created as trusted, it only returns
 * public keys of trusted {@link X509Certificate}s stored in a {@link KeyStore}. Otherwise, it returns trusted or
 * untrusted public keys (it doesn't care as long as it finds one).
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
 * @author Sean Mullan
 */
public class X509KeySelector extends KeySelector {

    private KeyStore ks;
    private boolean trusted = true;

    /**
     * Creates a trusted <code>X509KeySelector</code>.
     * 
     * @param keyStore the keystore
     * @throws KeyStoreException if the keystore has not been initialized
     * @throws NullPointerException if <code>keyStore</code> is <code>null</code>
     */
    public X509KeySelector(KeyStore keyStore) throws KeyStoreException {
        this(keyStore, true);
    }

    public X509KeySelector(KeyStore keyStore, boolean trusted) throws KeyStoreException {
        if (keyStore == null) {
            throw new NullPointerException("keyStore is null");
        }
        this.trusted = trusted;
        this.ks = keyStore;
        // test to see if KeyStore has been initialized
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
     * @param an <code>XMLCryptoContext</code> that may contain additional useful information for finding an appropriate
     *            key
     * @return a key selector result
     * @throws KeySelectorException if an exceptional condition occurs while attempting to find a key. Note that an
     *             inability to find a key is not considered an exception (<code>null</code> should be returned in that
     *             case). However, an error condition (ex: network communications failure) that prevented the
     *             <code>KeySelector</code> from finding a potential key should be considered an exception.
     * @throws ClassCastException if the data type of <code>method</code> is not supported by this key selector
     */
    public KeySelectorResult select(KeyInfo keyInfo, KeySelector.Purpose purpose, AlgorithmMethod method,
            XMLCryptoContext context) throws KeySelectorException {
        final SignatureMethod sm = (SignatureMethod) method;
        KeySelectorResult ksr = null;
        try {
            // return null if keyinfo is null or keystore is empty
            if (keyInfo == null || ks.size() == 0) {
                return new SimpleKeySelectorResult(null);
            }
            ksr = getKeySelectorResult(keyInfo, sm, context);
        } catch (KeyStoreException kse) {
            // throw exception if keystore is uninitialized
            throw new KeySelectorException(kse);
        }
        
        if(ksr == null) {
            new SimpleKeySelectorResult(null);
        }
        // return null since no match could be found
        return ksr;
    }
    
    private KeySelectorResult getKeySelectorResult(KeyInfo keyInfo, SignatureMethod sm,
            XMLCryptoContext context) throws KeyStoreException, KeySelectorException {
        KeySelectorResult ksr = null;
        // Iterate through KeyInfo types
        final Iterator i = keyInfo.getContent().iterator();            
        while (i.hasNext()) {
            final XMLStructure kiType = (XMLStructure) i.next();
            // check X509Data
            if (kiType instanceof X509Data) {
                ksr = getX509Type(kiType, sm);
                // check KeyName
            } else if (kiType instanceof KeyName) {
                ksr = getForKeyName(kiType, sm);
                // check RetrievalMethod
            } else if (kiType instanceof RetrievalMethod) {
                ksr = getForRetrievalMethod(kiType, sm, context);
            }
        }
        return ksr;
    }

    private KeySelectorResult getX509Type(XMLStructure kiType, SignatureMethod sm) throws KeyStoreException,
            KeySelectorException {
        final X509Data xd = (X509Data) kiType;
        return x509DataSelect(xd, sm);
    }

    private KeySelectorResult getForKeyName(XMLStructure kiType, SignatureMethod sm) throws KeyStoreException {
        final KeyName kn = (KeyName) kiType;
        final Certificate cert = ks.getCertificate(kn.getName());
        if (cert != null && algEquals(sm.getAlgorithm(), cert.getPublicKey().getAlgorithm())) {
            return new SimpleKeySelectorResult(cert.getPublicKey());
        }
        return null;
    }

    private KeySelectorResult getForRetrievalMethod(XMLStructure kiType, SignatureMethod sm, XMLCryptoContext context)
            throws KeySelectorException {
        final RetrievalMethod rm = (RetrievalMethod) kiType;
        KeySelectorResult ksr = null;
        try {

            if (rm.getType().equals(X509Data.RAW_X509_CERTIFICATE_TYPE)) {
                final OctetStreamData data = (OctetStreamData) rm.dereference(context);
                final CertificateFactory cf = CertificateFactory.getInstance("X.509");
                final X509Certificate cert = (X509Certificate) cf.generateCertificate(data.getOctetStream());
                ksr = certSelect(cert, sm);
                // } else if (rm.getType().equals(X509Data.TYPE)) {
                // X509Data xd = (X509Data) ((DOMRetrievalMethod) rm).dereferenceAsXMLStructure(context);
                // ksr = x509DataSelect(xd, sm);
            }
        } catch (Exception e) {
            throw new KeySelectorException(e);
        }
        return ksr;
    }

    /**
     * Searches the specified keystore for a certificate that matches the criteria specified in the CertSelector.
     * 
     * @return a KeySelectorResult containing the cert's public key if there is a match; otherwise null
     */
    private KeySelectorResult keyStoreSelect(CertSelector cs) throws KeyStoreException { // NOPMD
        final Enumeration<String> aliases = ks.aliases();
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
     * Searches the specified keystore for a certificate that matches the specified X509Certificate and contains a
     * public key that is compatible with the specified SignatureMethod.
     * 
     * @return a KeySelectorResult containing the cert's public key if there is a match; otherwise null
     */
    private KeySelectorResult certSelect(X509Certificate xcert, SignatureMethod sm) throws KeyStoreException {
        // skip non-signer certs
        final boolean[] keyUsage = xcert.getKeyUsage();
        if (keyUsage != null && !keyUsage[0]) {
            return null;
        }
        final String alias = ks.getCertificateAlias(xcert);
        if (alias != null) {
            final PublicKey pk = ks.getCertificate(alias).getPublicKey();
            // make sure algorithm is compatible with method
            if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
                return new SimpleKeySelectorResult(pk);
            }
        }
        return null;
    }

    // /**
    // * Returns an OID of a public-key algorithm compatible with the specified signature algorithm URI.
    // */
    // private String getPKAlgorithmOID(String algURI) {
    // if (algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1)) {
    // return "1.2.840.10040.4.1";
    // } else if (algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1)) {
    // return "1.2.840.113549.1.1";
    // } else {
    // return null;
    // }
    // }

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

    /**
     * Checks if a JCA/JCE public key algorithm name is compatible with the specified signature algorithm URI.
     */
    // @@@FIXME: this should also work for key types other than DSA/RSA
    private boolean algEquals(String algURI, String algName) {
        return (algName.equalsIgnoreCase("DSA") && algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1))
                || (algName.equalsIgnoreCase("RSA") && algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1));
    }

    /**
     * Searches the specified keystore for a certificate that matches an entry of the specified X509Data and contains a
     * public key that is compatible with the specified SignatureMethod.
     * 
     * @return a KeySelectorResult containing the cert's public key if there is a match; otherwise null
     */
    private KeySelectorResult x509DataSelect(X509Data xd, SignatureMethod sm) throws KeyStoreException, // NOPMD
            KeySelectorException {

        // convert signature algorithm to compatible public-key alg OID

        // JAP: removing
        // String algOID = getPKAlgorithmOID(sm.getAlgorithm());
        final X509CertSelector subjectcs = new X509CertSelector();
        // try {
        // subjectcs.setSubjectPublicKeyAlgID(algOID);
        // } catch (IOException ioe) {
        // throw new KeySelectorException(ioe);
        // }

        final Collection certs = new ArrayList();
        final Iterator xi = xd.getContent().iterator();
        while (xi.hasNext()) {
            final Object o = xi.next();
            /*
             * // check X509IssuerSerial if (o instanceof X509IssuerSerial) { X509IssuerSerial xis = (X509IssuerSerial)
             * o; try { subjectcs.setSerialNumber(xis.getSerialNumber()); String issuer = new
             * X500Principal(xis.getIssuerName()).getName(); // strip off newline if (issuer.endsWith("\n")) { issuer =
             * new String(issuer.toCharArray(), 0, issuer.length() - 1); } subjectcs.setIssuer(issuer); } catch
             * (IOException ioe) { throw new KeySelectorException(ioe); } // check X509SubjectName } else
             */
            fillSubjectCS(o, subjectcs);
            /*
             * else if (o instanceof byte[]) { byte[] ski = (byte[]) o; // DER-encode ski - required by X509CertSelector
             * byte[] encodedSki = new byte[ski.length + 2]; encodedSki[0] = 0x04; // OCTET STRING tag value
             * encodedSki[1] = (byte) ski.length; // length System.arraycopy(ski, 0, encodedSki, 2, ski.length);
             * subjectcs.setSubjectKeyIdentifier(encodedSki); }
             */

            fillCerts(o, certs);
        }
        return getKeySelectorResultFromSubjectsAndCerts(subjectcs, certs);
    }
    
    private KeySelectorResult getKeySelectorResultFromSubjectsAndCerts(X509CertSelector subjectcs, Collection certs) throws KeyStoreException {
        final KeySelectorResult ksr = keyStoreSelect(subjectcs);
        if (ksr != null) {
            return ksr;
        }
        if (!certs.isEmpty() && !trusted) {
            // try to find public key in certs in X509Data
            final Iterator i = certs.iterator();
            while (i.hasNext()) {
                final X509Certificate cert = (X509Certificate) i.next();
                if (subjectcs.match(cert)) {
                    return new SimpleKeySelectorResult(cert.getPublicKey());
                }
            }
        }
        return null;
    }
    
    private void fillSubjectCS(Object o, X509CertSelector subjectcs) throws KeySelectorException {
        if (o instanceof String) {
            final String sn = (String) o;
            try {
                String subject = new X500Principal(sn).getName();
                // strip off newline
                if (subject.endsWith("\n")) {
                    subject = new String(subject.toCharArray(), 0, subject.length() - 1);
                }
                subjectcs.setSubject(subject);
            } catch (IOException ioe) {
                throw new KeySelectorException(ioe);
            }
            // check X509SKI
        }
    }
    
    private void fillCerts(Object o, Collection certs) {
        if (o instanceof X509Certificate) {
            certs.add((X509Certificate) o);
            // check X509CRL
            // not supported: should use CertPath API
        }
    }
}
// CHECKSTYLE:ON
