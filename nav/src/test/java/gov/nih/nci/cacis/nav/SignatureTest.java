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

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        ks.load(new FileInputStream("src/test/resources/keystore.jks"), "changeit".toCharArray());
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

}
