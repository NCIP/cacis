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

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.crypto.Data;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignatureProperty;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 10, 2011
 * 
 */

public class SignatureTest {

    /**
     * 
     * @throws Exception on errorf
     */
    @Test
    public void testDetachedSignature() throws Exception { // NOPMD

        final String notificationId = UUID.randomUUID().toString();
        final String signatureId = notificationId;
        final String recommendedRegistry = "urn:oid:1.3.983249923.1234.3";
        final String docId1 = "urn:oid:1.3.345245354.435345";
        final String docId2 = "urn:oid:1.3.345245354.435346";
        final String docPath1 = "src/test/resources/sample_exchangeCCD.xml";
        final String docPath2 = "src/test/resources/purchase_order.xml";
        final String docPath3 = "src/test/resources/purchase_order_bad.xml";
        final String docOut = "target/sig_detached.xml";

        final Map<String, String> derefMap = new HashMap<String, String>();
        derefMap.put(docId1, docPath1);
        derefMap.put(docId2, docPath2);

        final URIDereferencer uriDeref = new URIDereferencer() {

            @Override
            public Data dereference(URIReference uriRef, XMLCryptoContext ctx) throws URIReferenceException {

                try {
                    String uri = uriRef.getURI().toString();
                    if (derefMap.containsKey(uri)) {
                        return new OctetStreamData(new FileInputStream(derefMap.get(uri)));
                    } else {
                        return null;
                    }
                } catch (FileNotFoundException e) {
                    throw new URIReferenceException(e);
                }

            }

        };

        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        final Document doc = dbf.newDocumentBuilder().newDocument();
        doc.appendChild(doc.createElement("root"));

        final XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

        final List siRefs = Collections.singletonList(fac.newReference("#IHEManifest", fac.newDigestMethod(
                DigestMethod.SHA1, null), null, "http://www.w3.org/2000/09/xmldsig#Manifest", null));

        final SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                (C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), siRefs);

        final List objectContent = new ArrayList();

        final Text recRegText = doc.createTextNode(recommendedRegistry);
        final SignatureProperty recRegProp = fac.newSignatureProperty(Collections.singletonList(new DOMStructure(
                recRegText)), signatureId, "recommendedRegistry");
        objectContent.add(fac.newSignatureProperties(Collections.singletonList(recRegProp), null));

        final List<Reference> manRefs = new ArrayList<Reference>();
        manRefs.add(fac.newReference(docId1, fac.newDigestMethod(DigestMethod.SHA256, null), null, null, null));
        manRefs.add(fac.newReference(docId2, fac.newDigestMethod(DigestMethod.SHA256, null), null, null, null));

        objectContent.add(fac.newManifest(manRefs, "IHEManifest"));

        final XMLObject object = fac.newXMLObject(objectContent, null, null, null);

        final KeyStore ks = KeyStore.getInstance("JKS");
        final InputStream is = new FileInputStream("src/test/resources/keystore.jks");
        ks.load( is, "changeit".toCharArray());
        closeInputStream(is);
        final KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("nav_test",
                new KeyStore.PasswordProtection("changeit".toCharArray()));
        final X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

        final KeyInfoFactory kif = fac.getKeyInfoFactory();
        final List x509Content = new ArrayList();
        x509Content.add(cert.getSubjectX500Principal().getName());
        x509Content.add(cert);
        final X509Data xd = kif.newX509Data(x509Content);
        final KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

        final DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement());
        dsc.setURIDereferencer(uriDeref);

        final XMLSignature signature = fac.newXMLSignature(si, ki, Collections.singletonList(object), signatureId, null);

        signature.sign(dsc);

        final TransformerFactory tf = TransformerFactory.newInstance();
        final Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature").item(0)),
                new StreamResult(new FileOutputStream(docOut)));

        final Document doc2 = dbf.newDocumentBuilder().parse(new FileInputStream(docOut));

        final DOMValidateContext valContext = new DOMValidateContext(new SimpleX509KeySelector(), doc2
                .getElementsByTagNameNS(XMLSignature.XMLNS, "Signature").item(0));

        XMLSignature signature2 = fac.unmarshalXMLSignature(valContext);

        // Core validation will fail because we can't validate
        // the references (because they can't be resolved)
        assertTrue(!signature2.validate(valContext));

        // But we can validate the signature value
        assertTrue(signature2.getSignatureValue().validate(valContext));

        // If we do resolve the document...
        signature2 = fac.unmarshalXMLSignature(valContext);
        valContext.setURIDereferencer(uriDeref);

        // ...then it will validate.
        assertTrue(signature2.validate(valContext));
        
        // However, even if we modify a referenced document, it will still
        // validate. This is because Reference elements within a Manifest
        // have to be validated in an application-specific manner. See
        // http://www.w3.org/TR/xmldsig-core/#sec-Manifest
        derefMap.put(docId2, docPath3);
        signature2 = fac.unmarshalXMLSignature(valContext);
        valContext.setURIDereferencer(uriDeref);
        assertTrue(signature2.validate(valContext));
    }
    
    private void closeInputStream(InputStream is) throws IOException {
        if ( is != null ) {
            is.close();
        }
    }

}
