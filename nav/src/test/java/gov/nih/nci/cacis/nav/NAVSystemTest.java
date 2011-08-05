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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;

/**
 * Exercises all NAV components. testNotificationReceiver is the actual system test.
 *
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 5, 2011
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-nav.xml" } )
public class NAVSystemTest {

    @Autowired
    private CertSelectorFactory factory;

    @Autowired
    private AlgorithmChecker algorithmChecker;

    @Value("${nav.keystore.location}")
    private String keyStoreLocation;

    private static final String TRUE = "true";
    private static final String EMAIL1 = "another.one@somewhere.com";
    private static final String EMAIL2 = "some.one@somewhere.com";

    private GreenMail server;
    private String regId;
    private String docId1;
    private String docId2;
    private String docPath1;
    private String docPath2;

    /**
     *
     */
    @Before
    public void setUp() {
        regId = "urn:oid:1.3.983249923.1234.3";
        docId1 = "urn:oid:1.3.345245354.435345";
        docId2 = "urn:oid:1.3.345245354.435346";
        docPath1 = "sample_exchangeCCD.xml";
        docPath2 = "purchase_order.xml";
        server = new GreenMail();
        server.start();
    }

    /**
     *
     */
    @After
    public void tearDown() {
        server.stop();
    }

    /**
     *
     * @throws Exception on error
     */
    @Test
    public void testNotificationSenderNonSecure() throws Exception {

        final XDSNotificationSignatureBuilder sigBuilder = new DefaultXDSNotificationSignatureBuilder(
                getDocumentResolver(), SignatureMethod.RSA_SHA1, DigestMethod.SHA1, "JKS", keyStoreLocation, "changeit",
                "nav_test");

        final Properties props = new Properties();
        // props.setProperty("mail.debug", TRUE);

        final String mailbox = EMAIL1;
        final String to = EMAIL2;
        final String subject = "Notification of Document Availability";
        final String instructions = "Instructions to the user.";

        final NotificationSender sender = new NotificationSenderImpl(sigBuilder, props, subject, mailbox, to,
                instructions, "", "", server.getSmtp().getBindTo(), server.getSmtp().getPort(), server.getSmtp()
                        .getProtocol());

        final String[] keys = { docId1, docId2 };
        sender.send(new ArrayList<String>(Arrays.asList(keys)));

        assertTrue(server.getReceivedMessages().length == 1);

    }

    /**
     *
     * @throws Exception on error
     */
    @Test
    public void testNotificationSenderWithSMTPAndTLS() throws Exception {

        final String email = EMAIL1;
        final String login = "another.one";
        final String password = "secret";

        final GreenMailUser user = server.setUser(email, login, password);

        final Properties props = new Properties();
        props.setProperty("mail.smtp.auth", TRUE);
        props.setProperty("mail.smtp.starttls.enable", TRUE);
        // props.setProperty("mail.debug", TRUE);

        final XDSNotificationSignatureBuilder sigBuilder = new DefaultXDSNotificationSignatureBuilder(
                getDocumentResolver(), SignatureMethod.RSA_SHA1, DigestMethod.SHA1, "JKS", keyStoreLocation, "changeit",
                "nav_test");

        final String mailbox = EMAIL1;
        final String to = EMAIL2;
        final String subject = "Notification of Document Availability";
        final String instructions = "Instructions to the user.";

        final NotificationSender sender = new NotificationSenderImpl(sigBuilder, props, subject, mailbox, to,
                instructions, user.getLogin(), user.getPassword(), server.getSmtp().getBindTo(), server.getSmtp()
                        .getPort(), server.getSmtp().getProtocol());
        final String[] keys = { docId1, docId2 };

        sender.send(new ArrayList<String>(Arrays.asList(keys)));

        assertTrue(server.getReceivedMessages().length == 1);

    }

    /**
     *
     * @throws Exception on error
     */
    @Test
    public void testNotificationSenderWithSMTPSAndSSL() throws Exception {

        final String email = EMAIL1;
        final String login = "another.one";
        final String password = "secret";

        final GreenMailUser user = server.setUser(email, login, password);

        final Properties props = new Properties();

        props.setProperty("mail.smtps.auth", TRUE);
        props.setProperty("mail.smtps.starttls.enable", TRUE);
        props.setProperty("mail.smtps.socketFactory.class", DummySSLSocketFactory.class.getName());
        // props.setProperty("mail.debug", TRUE);

        final XDSNotificationSignatureBuilder sigBuilder = new DefaultXDSNotificationSignatureBuilder(
                getDocumentResolver(), SignatureMethod.RSA_SHA1, DigestMethod.SHA1, "JKS", keyStoreLocation, "changeit",
                "nav_test");

        final String mailbox = EMAIL1;
        final String to = EMAIL2;
        final String subject = "Notification of Document Availability";
        final String instructions = "Instructions to the user.";

        final NotificationSender sender = new NotificationSenderImpl(sigBuilder, props, subject, mailbox, to,
                instructions, user.getLogin(), user.getPassword(), server.getSmtps().getBindTo(), server.getSmtps()
                        .getPort(), server.getSmtps().getProtocol());
        final String[] keys = { docId1, docId2 };

        sender.send(new ArrayList<String>(Arrays.asList(keys)));

        assertTrue(server.getReceivedMessages().length == 1);

    }

    /**
     * Tests all NAV receiver components
     * @throws Exception will result in a failed test
     */
    @Test
    public void testNotificationReceiver() throws Exception {

        final ClassLoader cl = NAVSystemTest.class.getClassLoader();
        final InputStream is = cl.getResourceAsStream("keystore.jks");

        try {
            URL regUrl = new URL("http://some.host/someXDSService"); // NOPMD

            final Properties props = new Properties();
            props.setProperty("mail.pop3.port", "" + POP3NotificationReceiverTest.POP3_PORT); // NOPMD

            // Populate the email and receive via POP3
            final NotificationReceiver r = POP3NotificationReceiverTest.getNotificationReceiver(server, props);
            final Message[] messages = r.receive();
            assertTrue(messages != null);
            assertTrue(messages.length == 1);

            // Do validation of the message, prior to resolving
            final KeyStore ks = KeyStore.getInstance("JKS");

            ks.load( is, "changeit".toCharArray());

            final NotificationValidator v = new DefaultNotificationValidator(new X509KeySelector(ks, factory, algorithmChecker),
                    new DefaultDocumentReferenceValidator());
            v.validate(messages[0]);

            // Now resolve and validate the documents
            v.validateDigitalSignature(NAVUtils.getSignature(messages[0]), getDocumentResolver());

            // Pull out the registry and document IDs
            final Document sig = NAVUtils.getSignature(messages[0]);
            assertEquals(NAVUtils.getRegistryId(sig), regId);
            final List<String> ids = NAVUtils.getDocumentIds(sig);
            assertTrue(ids.size() == 2);
            assertEquals(ids.get(0), docId1);
            assertEquals(ids.get(1), docId2);

            // Resolve the registry ID to URL
            final Map<String, String> mappings = new HashMap<String, String>();
            mappings.put(regId, regUrl.toString());
            final MapDocumentRegistryRegistry regReg = new MapDocumentRegistryRegistry(mappings);
            assertEquals(regReg.lookup(regId), regUrl);

        } finally {
            closeInputStream(is);
        }


    }

    private void closeInputStream(InputStream is) throws IOException {
        if ( is != null ) {
            is.close();
        }
    }

    private XDSDocumentResolver getDocumentResolver() throws Exception { // NOPMD
        final ClassLoader cl = NAVSystemTest.class.getClassLoader();
        final InMemoryCacheDocumentHolder h = new InMemoryCacheDocumentHolder();
        h.putDocument(docId1, cl.getResourceAsStream(docPath1));
        h.putDocument(docId2, cl.getResourceAsStream(docPath2));
        return new InMemoryCacheXDSDocumentResolver(regId, h);
    }
}
