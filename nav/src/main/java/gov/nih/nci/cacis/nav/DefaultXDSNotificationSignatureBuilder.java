/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The nav Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and subcontracted parties. To the extent government employees are authors, any rights in such works
 * shall be subject to Title 17 of the United States Code, section 105.
 * 
 * This nav Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the nav Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly
 * display, publicly perform, and prepare derivative works of the nav Software; (ii) distribute and have distributed to
 * and by third parties the nav Software and any modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
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
 * product includes software developed by the National Cancer Institute and subcontracted parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 * 
 * You may not use the names "The National Cancer Institute", "NCI", or any subcontracted party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or theany of the subcontracted parties, except as required to comply with
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
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Manifest;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignatureProperties;
import javax.xml.crypto.dsig.SignatureProperty;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Default implementation of XDSNotificationSignatureBuilder.
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 10, 2011
 * 
 */
public class DefaultXDSNotificationSignatureBuilder implements XDSNotificationSignatureBuilder {

    /**
     * Value of Manifest element Id attribute
     */
    public static final String MANIFEST_ID = "IHEManifest";

    /**
     * XML Type of Manifest element
     */
    public static final String MANIFEST_TYPE = "http://www.w3.org/2000/09/xmldsig#Manifest";

    /**
     * Name of temporary element that will contain the Signature element
     */
    public static final String SIGNATURE_CONTAINER = "root";

    /**
     * Per NAV spec "recommendedRegistry"
     */
    public static final String RECOMMENDED_REGISTRY_ELEMENT = "recommendedRegistry";

    /**
     * Name of Signature element
     */
    public static final String SIGNATURE_ELEMENT = "Signature";

    private XDSDocumentResolver documentResolver;

    private String signatureMethod = SignatureMethod.RSA_SHA1;

    private String digestMethod = DigestMethod.SHA1;

    private String keyStoreType = "JKS";

    private String keyStoreName = "keystore";

    private String keyStorePassword = "changeit";

    private String keyEntry = "nav_sign";

    /**
     * This is the default constructor.
     */
    public DefaultXDSNotificationSignatureBuilder() { // NOPMD

    }

    /**
     * Parameterized constructor
     * 
     * @param documentResolver the XDSDocumentResolver
     * @param signatureMethod defaults to SignatureMethod.SHA1
     * @param digestMethod defaults to DigestMethod.RSA_SHA1
     * @param keyStoreType defaults to 'JKS'
     * @param keyStoreName defaults to 'keystore'
     * @param keyStorePassword defaults to 'changeit'
     * @param keyEntry defaults to 'nav_sign'
     */
    public DefaultXDSNotificationSignatureBuilder(XDSDocumentResolver documentResolver, String signatureMethod, // NOPMD
            String digestMethod, String keyStoreType, String keyStoreName, String keyStorePassword, String keyEntry) {
        this.documentResolver = documentResolver;
        this.signatureMethod = signatureMethod;
        this.digestMethod = digestMethod;
        this.keyStoreType = keyStoreType;
        this.keyStoreName = keyStoreName;
        this.keyStorePassword = keyStorePassword;
        this.keyEntry = keyEntry;
    }

    /*
     * (non-Javadoc) {@inheritDoc}
     */

    @Override
    public Node buildSignature(List<String> documentIds) throws SignatureBuildingException {

        final URIDereferencer uriDeref = getURIDereferener();

        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = null;
        try {
            doc = dbf.newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            throw new SignatureBuildingException(ex);
        }
        doc.appendChild(doc.createElement(SIGNATURE_CONTAINER));

        final String signatureId = UUID.randomUUID().toString();
        final XMLSignatureFactory fac = getXMLSignatureFactory();
        final SignedInfo si = handleSignedInfo(fac);
        final SignatureProperties sigProps = buildSignatureProperties(doc, fac, signatureId);
        final XMLObject object = handleXMLObject(fac, documentIds, sigProps);
        final KeyInfo ki = handleKeyInfo(fac);
        final Key signingKey = handleSigningKey();

        final DOMSignContext dsc = new DOMSignContext(signingKey, doc.getDocumentElement());
        dsc.setURIDereferencer(uriDeref);

        final XMLSignature signature = fac.newXMLSignature(si, ki, Collections.singletonList(object), signatureId, null);

        try {
            signature.sign(dsc);
        } catch (MarshalException ex) {
            throw new SignatureBuildingException(ex);
        } catch (XMLSignatureException ex) {
            throw new SignatureBuildingException(ex);
        }

        return doc.getElementsByTagNameNS(XMLSignature.XMLNS, SIGNATURE_ELEMENT).item(0);

    }

    private Key handleSigningKey() throws SignatureBuildingException {
        Key signingKey = null;
        try {
            signingKey = getSigningKey();
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureBuildingException(e);
        } catch (UnrecoverableEntryException e) {
            throw new SignatureBuildingException(e);
        } catch (KeyStoreException e) {
            throw new SignatureBuildingException(e);
        } catch (CertificateException e) {
            throw new SignatureBuildingException(e);
        } catch (IOException e) {
            throw new SignatureBuildingException(e);
        }
        return signingKey;
    }

    private KeyInfo handleKeyInfo(XMLSignatureFactory fac) throws SignatureBuildingException {
        KeyInfo ki = null;
        try {
            ki = buildKeyInfo(fac);
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureBuildingException(e);
        } catch (UnrecoverableEntryException e) {
            throw new SignatureBuildingException(e);
        } catch (KeyStoreException e) {
            throw new SignatureBuildingException(e);
        } catch (CertificateException e) {
            throw new SignatureBuildingException(e);
        } catch (IOException e) {
            throw new SignatureBuildingException(e);
        }
        return ki;
    }

    private XMLObject handleXMLObject(XMLSignatureFactory fac, List<String> documentIds, SignatureProperties sigProps)
            throws SignatureBuildingException {
        XMLObject object = null;
        try {
            object = buildObject(fac, documentIds, sigProps);
        } catch (NoSuchAlgorithmException ex) {
            throw new SignatureBuildingException(ex);
        } catch (InvalidAlgorithmParameterException ex) {
            throw new SignatureBuildingException(ex);
        }
        return object;
    }

    private SignedInfo handleSignedInfo(XMLSignatureFactory fac) throws SignatureBuildingException {
        SignedInfo si = null;
        try {
            si = buildSignedInfo(fac);
        } catch (NoSuchAlgorithmException ex) {
            throw new SignatureBuildingException(ex);
        } catch (InvalidAlgorithmParameterException ex) {
            throw new SignatureBuildingException(ex);
        }
        return si;
    }

    /**
     * 
     * @return the URIDereferencer
     */
    protected URIDereferencer getURIDereferener() {

        final URIDereferencer uriDeref = new URIDereferencer() {

            @Override
            public Data dereference(URIReference uriRef, XMLCryptoContext ctx) throws URIReferenceException {

                InputStream in = null;
                try {
                    in = getDocumentResolver().resolve(uriRef.getURI().toString());
                } catch (XDSDocumentResolutionException ex) {
                    throw new URIReferenceException(ex);
                }
                if (in != null) {
                    return new OctetStreamData(in);
                }
                return null;

            }

        };
        return uriDeref;
    }

    /**
     * 
     * @return the Key to sign with
     * @throws NoSuchAlgorithmException on error
     * @throws UnrecoverableEntryException on error
     * @throws KeyStoreException on error
     * @throws CertificateException on error
     * @throws IOException on error
     */
    protected Key getSigningKey() throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException,
            CertificateException, IOException {
        return getPrivateKeyEntry().getPrivateKey();
    }

    /**
     * 
     * @param fac the XMLSginatureFactory
     * @return the KeyInfo
     * @throws NoSuchAlgorithmException on error
     * @throws UnrecoverableEntryException on error
     * @throws KeyStoreException on error
     * @throws CertificateException on error
     * @throws IOException on error
     */
    protected KeyInfo buildKeyInfo(XMLSignatureFactory fac) throws NoSuchAlgorithmException,
            UnrecoverableEntryException, KeyStoreException, CertificateException, IOException {
        final X509Certificate cert = getX509Certificate();
        final KeyInfoFactory kif = fac.getKeyInfoFactory();
        final List x509Content = new ArrayList();
        x509Content.add(cert.getSubjectX500Principal().getName());
        x509Content.add(cert);
        final X509Data xd = kif.newX509Data(x509Content);
        return kif.newKeyInfo(Collections.singletonList(xd));
    }

    /**
     * 
     * @return the X509Certificate
     * @throws NoSuchAlgorithmException on error
     * @throws UnrecoverableEntryException on error
     * @throws KeyStoreException on error
     * @throws CertificateException on error
     * @throws IOException on error
     */
    protected X509Certificate getX509Certificate() throws NoSuchAlgorithmException, UnrecoverableEntryException,
            KeyStoreException, CertificateException, IOException {
        return (X509Certificate) getPrivateKeyEntry().getCertificate();
    }

    /**
     * 
     * @return the KeyStore
     * @throws KeyStoreException on error
     * @throws NoSuchAlgorithmException on error
     * @throws CertificateException on error
     * @throws IOException on error
     */
    protected KeyStore getKeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
            IOException {
        final KeyStore ks = KeyStore.getInstance(getKeyStoreType());
        final ClassLoader cl = DefaultXDSNotificationSignatureBuilder.class.getClassLoader();
        ks.load(cl.getResourceAsStream(getKeyStoreName()), getKeyStorePassword().toCharArray());
        return ks;

    }

    /**
     * 
     * @return the KeyStore.PrivateKeyEntry
     * @throws NoSuchAlgorithmException on error
     * @throws UnrecoverableEntryException on error
     * @throws KeyStoreException on error
     * @throws CertificateException on error
     * @throws IOException on error
     */
    protected KeyStore.PrivateKeyEntry getPrivateKeyEntry() throws NoSuchAlgorithmException,
            UnrecoverableEntryException, KeyStoreException, CertificateException, IOException {
        return (KeyStore.PrivateKeyEntry) getKeyStore().getEntry(getKeyEntry(),
                new KeyStore.PasswordProtection(getKeyStorePassword().toCharArray()));
    }

    /**
     * 
     * @param fac the XMLSignatureFactory
     * @param documentIds list of document IDs
     * @param sigProps the SignatureProperties
     * @return the XMLObject
     * @throws NoSuchAlgorithmException on error
     * @throws InvalidAlgorithmParameterException on error
     */
    protected XMLObject buildObject(XMLSignatureFactory fac, List<String> documentIds, SignatureProperties sigProps)
            throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        final List<XMLStructure> objectContent = new ArrayList<XMLStructure>();
        objectContent.add(sigProps);

        final Manifest manifest = buildManifest(fac, documentIds);
        objectContent.add(manifest);

        return fac.newXMLObject(objectContent, null, null, null);

    }

    /**
     * 
     * @param fac the XMLSignatureFactory
     * @param documentIds the list of document IDs
     * @return the Manifest on error
     * @throws NoSuchAlgorithmException on error
     * @throws InvalidAlgorithmParameterException on error
     */
    protected Manifest buildManifest(XMLSignatureFactory fac, List<String> documentIds)
            throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        final List<Reference> manRefs = new ArrayList<Reference>();
        for (String docId : documentIds) {
            manRefs.add(fac.newReference(docId, fac.newDigestMethod(getDigestMethod(), null), null, null, null));
        }
        return fac.newManifest(manRefs, MANIFEST_ID);

    }

    /**
     * 
     * @param doc the parent Document
     * @param fac the XMLSignatureFactory on error
     * @param signatureId the Signature ID to which the properties will refer
     * @return the SignatureProperties on error
     */
    protected SignatureProperties buildSignatureProperties(Document doc, XMLSignatureFactory fac, String signatureId) {
        final Text recRegText = doc.createTextNode(getDocumentResolver().getRecommendedRegistry());
        final SignatureProperty recRegProp = fac.newSignatureProperty(
                Collections.singletonList(new DOMStructure(recRegText)), signatureId,
                RECOMMENDED_REGISTRY_ELEMENT);
        return fac.newSignatureProperties(Collections.singletonList(recRegProp), null);

    }

    /**
     * 
     * @param fac the XMLSignatureFactory
     * @return the SignedInfo
     * @throws NoSuchAlgorithmException on error
     * @throws InvalidAlgorithmParameterException on error
     */
    protected SignedInfo buildSignedInfo(XMLSignatureFactory fac) throws NoSuchAlgorithmException,
            InvalidAlgorithmParameterException {
        final List<Reference> siRefs = Collections.singletonList(fac.newReference("#" + MANIFEST_ID, fac.newDigestMethod(
                getDigestMethod(), null), null, MANIFEST_TYPE, null));
        return fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                (C14NMethodParameterSpec) null), fac.newSignatureMethod(getSignatureMethod(), null), siRefs);
    }

    /**
     * 
     * @return the XMLSignatureFactory
     */
    protected XMLSignatureFactory getXMLSignatureFactory() {
        return XMLSignatureFactory.getInstance("DOM");
    }

    /**
     * @return the documentResolver
     */

    public XDSDocumentResolver getDocumentResolver() {

        return documentResolver;
    }

    /**
     * @param documentResolver the documentResolver to set
     */

    public void setDocumentResolver(XDSDocumentResolver documentResolver) {

        this.documentResolver = documentResolver;
    }

    /**
     * @return the signatureMethod
     */

    public String getSignatureMethod() {

        return signatureMethod;
    }

    /**
     * @param signatureMethod the signatureMethod to set
     */

    public void setSignatureMethod(String signatureMethod) {

        this.signatureMethod = signatureMethod;
    }

    /**
     * @return the digestMethod
     */

    public String getDigestMethod() {

        return digestMethod;
    }

    /**
     * @param digestMethod the digestMethod to set
     */

    public void setDigestMethod(String digestMethod) {

        this.digestMethod = digestMethod;
    }

    /**
     * @return the keyStoreType
     */

    public String getKeyStoreType() {

        return keyStoreType;
    }

    /**
     * @param keyStoreType the keyStoreType to set
     */

    public void setKeyStoreType(String keyStoreType) {

        this.keyStoreType = keyStoreType;
    }

    /**
     * @return the keyStorePassword
     */

    public String getKeyStorePassword() {

        return keyStorePassword;
    }

    /**
     * @param keyStorePassword the keyStorePassword to set
     */

    public void setKeyStorePassword(String keyStorePassword) {

        this.keyStorePassword = keyStorePassword;
    }

    /**
     * @return the keyEntry
     */

    public String getKeyEntry() {

        return keyEntry;
    }

    /**
     * @param keyEntry the keyEntry to set
     */

    public void setKeyEntry(String keyEntry) {

        this.keyEntry = keyEntry;
    }

    /**
     * @return the keyStoreName
     */

    public String getKeyStoreName() {

        return keyStoreName;
    }

    /**
     * @param keyStoreName the keyStoreName to set
     */

    public void setKeyStoreName(String keyStoreName) {

        this.keyStoreName = keyStoreName;
    }

}
