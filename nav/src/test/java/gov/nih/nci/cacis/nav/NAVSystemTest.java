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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.xml.crypto.Data;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignatureProperty;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.icegreen.greenmail.util.GreenMail;

/**
 * Exercises all NAV components. testNotificationReceiver is the actual system test.
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 5, 2011
 * 
 */
public class NAVSystemTest {

    /**
     * This isn't much of a test. Rather, it is just some sample code for building the Signature.
     * 
     * @throws Exception
     */
    @Test
    public void testBuildSampleNotification() throws Exception {

        String notificationId = UUID.randomUUID().toString();
        String signatureId = notificationId;
        String recommendedRegistry = "urn:oid:1.3.983249923.1234.3";
        String docId = "urn:oid:1.3.345245354.435345";
        String docPath = "sample_exchangeCCD.xml";

        // Build the document into which the Signature will be inserted.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().newDocument();
        Element root = doc.createElement("root");
        doc.appendChild(root);

        // Build the Signature

        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

        List siRefs = Collections.singletonList(fac.newReference("#IHEManifest", fac.newDigestMethod(DigestMethod.SHA1,
                null), null, "http://www.w3.org/2000/09/xmldsig#Manifest", null));

        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                (C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), siRefs);

        List objectContent = new ArrayList();

        Text recRegText = doc.createTextNode(recommendedRegistry);
        SignatureProperty recRegProp = fac.newSignatureProperty(
                Collections.singletonList(new DOMStructure(recRegText)), signatureId, "recommendedRegistry");
        objectContent.add(fac.newSignatureProperties(Collections.singletonList(recRegProp), null));

        List manRefs = Collections.singletonList(fac.newReference(docId, fac.newDigestMethod(DigestMethod.SHA1, null),
                null, null, null));
        objectContent.add(fac.newManifest(manRefs, "IHEManifest"));

        XMLObject object = fac.newXMLObject(objectContent, null, null, null);

        ClassLoader cl = NAVSystemTest.class.getClassLoader();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(cl.getResourceAsStream("keystore.jks"), "changeit".toCharArray());
        KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("nav_test",
                new KeyStore.PasswordProtection("changeit".toCharArray()));
        X509Certificate cert = (X509Certificate) keyEntry.getCertificate();
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        List x509Content = new ArrayList();
        x509Content.add(cert.getSubjectX500Principal().getName());
        x509Content.add(cert);
        X509Data xd = kif.newX509Data(x509Content);
        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
        DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement());
        dsc.setURIDereferencer(new FileURIDereferencer(docPath, dsc.getURIDereferencer()));
        XMLSignature signature = fac.newXMLSignature(si, ki, Collections.singletonList(object), null, null);
        signature.sign(dsc);

        // Print the Signature to file
        // TransformerFactory tf = TransformerFactory.newInstance();
        // Transformer trans = tf.newTransformer();
        // trans.transform(new DOMSource(doc.getDocumentElement().getFirstChild()), new StreamResult(new
        // FileOutputStream(
        // "src/test/resources/notification_gen.xml")));

    }

    /**
     * Tests all NAV receiver components
     */
    @Test
    public void testNotificationReceiver() {

        GreenMail server = new GreenMail();

        try {

            String regId = "urn:oid:1.3.983249923.1234.3";
            URL regUrl = new URL("http://some.host/someXDSService");
            String docId = "urn:oid:1.3.345245354.435345";

            server.start();

            Properties props = new Properties();
            props.setProperty("mail.pop3.port", "" + POP3NotificationReceiverTest.POP3_PORT);

            // Populate the email and receive via POP3
            NotificationReceiver r = POP3NotificationReceiverTest.getNotificationReceiver(server, props);
            Message[] messages = r.receive();
            assertTrue(messages != null);
            assertTrue(messages.length == 1);

            // Validate the message, including cryptographic validation
            ClassLoader cl = NAVSystemTest.class.getClassLoader();
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(cl.getResourceAsStream("keystore.jks"), "changeit".toCharArray());
            KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("nav_test",
                    new KeyStore.PasswordProtection("changeit".toCharArray()));
            NotificationValidator v = new DefaultNotificationValidator(new X509KeySelector(ks));
            v.validate(messages[0]);

            // Pull out the registry and document IDs
            Document sig = NAVUtils.getSignature(messages[0]);
            assertEquals(NAVUtils.getRegistryId(sig), regId);
            List<String> ids = NAVUtils.getDocumentIds(sig);
            assertTrue(ids.size() == 1);
            assertEquals(ids.get(0), docId);

            // Resolve the registry ID to URL
            Map<String, String> mappings = new HashMap<String, String>();
            mappings.put(regId, regUrl.toString());
            MapDocumentRegistryRegistry regReg = new MapDocumentRegistryRegistry(mappings);
            assertEquals(regReg.lookup(regId), regUrl);

        } catch (Exception ex) {
            fail("Unexpected error: " + ex.getMessage());
        } finally {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class FileURIDereferencer implements URIDereferencer {

        String path;
        URIDereferencer derefer;

        FileURIDereferencer(String path, URIDereferencer derefer) {
            this.path = path;
            this.derefer = derefer;
        }

        @Override
        public Data dereference(URIReference ref, XMLCryptoContext ctx) throws URIReferenceException {
            if (ref.getURI().startsWith("urn:")) {
                ClassLoader cl = FileURIDereferencer.class.getClassLoader();
                return new OctetStreamData(cl.getResourceAsStream(path));
            }
            return null;
        }

    }
}
