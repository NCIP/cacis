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

import java.io.File;
import java.io.FileInputStream;
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

    private String keyStoreLocation = "keystore";

    private String keyStorePassword = "changeit";

    private String keyEntry = "nav_sign";
    
    private KeyStore ks;
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
     * @param keyStoreLocation defaults to 'keystore'
     * @param keyStorePassword defaults to 'changeit'
     * @param keyEntry defaults to 'nav_sign'
     */
    public DefaultXDSNotificationSignatureBuilder(XDSDocumentResolver documentResolver, String signatureMethod, // NOPMD
            String digestMethod, String keyStoreType, String keyStoreLocation, String keyStorePassword, String keyEntry) {
        this.documentResolver = documentResolver;
        this.signatureMethod = signatureMethod;
        this.digestMethod = digestMethod;
        this.keyStoreType = keyStoreType;
        this.keyStoreLocation = keyStoreLocation;
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
                //The manifest reference will not be resolved through document resolver
                if (uriRef.getURI().contains(MANIFEST_ID)) {
                    return null;
                }
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
        if (ks != null) {
            return ks;
        }
        FileInputStream fos = null;
        ks = KeyStore.getInstance(getKeyStoreType());
        try {
            final File keyStore = new File(getKeyStoreLocation());
            fos = new FileInputStream(keyStore);
            ks.load(fos, getKeyStorePassword().toCharArray());
        } finally {
            if (fos != null) {
                fos.close();
            }
        }

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
     * @return the keyStoreLocation
     */

    public String getKeyStoreLocation() {

        return keyStoreLocation;
    }

    /**
     * @param keyStoreName the keyStoreName to set
     */

    public void setKeyStoreName(String keyStoreName) {

        this.keyStoreLocation = keyStoreName;
    }

}
