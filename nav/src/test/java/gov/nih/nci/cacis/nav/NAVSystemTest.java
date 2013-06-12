/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
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
@ContextConfiguration(locations = { "classpath*:/applicationContext-nav-test.xml" } )
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
     * setup for system test
     */
    @Before
    public void setUp() {
        regId = "urn:oid:1.3.983249923.1234.3";
        docId1 = "urn:oid:1.3.345245354.435345";
        docId2 = "urn:oid:1.3.345245354.435346";
        docPath1 = "sample_exchangeCCD.xml";
        docPath2 = "purchase_order.xml";
        server = new GreenMail();
        //for some reason, the greenmail server doesnt start in time
        //will attempt max times to ensure all service gets startedup 
        int i = 0;
        final int max = 2;
        while ( i < max ) {
            try {
                server.start();
                //CHECKSTYLE:OFF
            } catch (RuntimeException e) { //NOPMD
              //CHECKSTYLE:ON
                i++;
                continue;
            }
            i = max;
        }
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

        final NotificationSender sender = new NotificationSenderImpl(sigBuilder, props, subject, mailbox, 
                instructions, server.getSmtp().getBindTo(), server.getSmtp().getPort(), server.getSmtp()
                        .getProtocol());
        sender.setCredentials("", "");
        final String[] keys = { docId1, docId2 };
        sender.send(to, new ArrayList<String>(Arrays.asList(keys)));

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
 
       
        final NotificationSender sender = new NotificationSenderImpl(sigBuilder, props, 
                subject, mailbox , instructions, server.getSmtp().getBindTo(),
                server.getSmtp().getPort(), null);
        final String[] keys = { docId1, docId2 };
        sender.setCredentials(user.getLogin(), user.getPassword());
        sender.send(to, new ArrayList<String>(Arrays.asList(keys)));

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

        final NotificationSender sender = new NotificationSenderImpl(sigBuilder, props, subject, mailbox, 
                instructions, server.getSmtps().getBindTo(), server.getSmtps()
                        .getPort(), server.getSmtps().getProtocol());
        sender.setCredentials(user.getLogin(), user.getPassword());
        final String[] keys = { docId1, docId2 };

        sender.send(to, new ArrayList<String>(Arrays.asList(keys)));

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

            final NotificationValidator v = 
                new DefaultNotificationValidator(new X509KeySelector(ks, factory, algorithmChecker),
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
