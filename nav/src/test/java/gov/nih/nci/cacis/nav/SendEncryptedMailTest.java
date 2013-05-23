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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

/**
 * Tests sending/receiving mail
 * 
 * @author joshua.phillips@semanticbits.com
 * @author vinodh.rc@semanticbits.com
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-nav-test.xml" })
public class SendEncryptedMailTest {

    private static final String INBOX = "INBOX";

    private static final String POP3_TYPE = "pop3";

    private static final String MAIL_POP3_PORT_KEY = "mail.pop3.port";
   
    private static final String UNEXPECTED_EXCEPTION = "Unexpected exception: ";

    private static final Logger LOG = Logger.getLogger(TestMail.class);

    private static final String LOCALHOST = "localhost";
    private static final int SMTP_PORT = 3025;
    private static final int POP3_PORT = 3110;

    private GreenMail server;

    @Value("${sec.email.keystore.location}")
    private String secEmailKeyStoreLocation;

    @Value("${sec.email.keystore.password}")
    private String secEmailKeyStorePassword;

    // Using the from email address to get the matching key
    @Value("${sec.email.message.from}")
    private String secEmailKeyStoreKey;

    @Value("${sec.email.truststore.location}")
    private String secEmailTrustStoreLocation;

    @Value("${sec.email.truststore.password}")
    private String secEmailTrustStorePassword;

    @Value("${sec.email.message.from}")
    private String secEmailFrom;

    // Using the same email address for from and to
    // as the test truststore has key for the from address only
    @Value("${sec.email.message.from}")
    private String secEmailTo;

    @Value("${sec.email.sender.user}")
    private String secEmailUser;

    @Value("${sec.email.sender.pass}")
    private String secEmailPass;
    
    @Value("${sec.email.temp.zip.location}")
    private String secEmailTempZipLocation;
    
    @Autowired
    private SendEncryptedMail sender;

    /**
     * starts GreenMain server for testing
     */
    @Before
    public void setUp() {
        server = new GreenMail(ServerSetupTest.ALL);
        // for some reason, the greenmail server doesnt start in time
        // will attempt max times to ensure all service gets startedup
        int i = 0;
        final int max = 2;
        while (i < max) {
            try {
                server.start();
                // CHECKSTYLE:OFF
            } catch (RuntimeException e) { // NOPMD
                // CHECKSTYLE:ON
                i++;
                continue;
            }
            i = max;
        }
    }

    /**
     * stops GreenMain server after testing
     */
    @After
    public void tearDown() {
        server.stop();
    }

    /**
     * tests error on invalid truststore
     * 
     * @throws MessagingException - exception thrown
     * @throws KeyStoreException - exception thrown
     */
    @Test(expected = KeyStoreException.class)
    public void supplyInvalidTruststore() throws MessagingException, KeyStoreException {
        final SendEncryptedMail ssem = new SendEncryptedMail(new Properties(), secEmailFrom, "invalid truststore",
                secEmailTrustStorePassword, secEmailTempZipLocation);
        // shouldnt reach this line
        ssem.encryptMail(null, "");
    }

    /**
     * tests error on invalid cert
     * 
     * @throws MessagingException - exception thrown, if any
     * @throws KeyStoreException - exception thrown
     * 
     */
    @Test
    public void supplyInvalidCert() throws MessagingException, KeyStoreException {
        final SendEncryptedMail ssem = new SendEncryptedMail(new Properties(), secEmailFrom, secEmailTempZipLocation);
        assertNull(ssem.encryptMail(null, (Certificate) null));
    }
    
    /**
     * send to gmail
     * @throws MessagingException exception thrown
     */
    @Test
    public void sendToGmail() throws MessagingException {        
        final MimeMessage msg = sender.createMessage(secEmailTo, "Clinical Note", "subj", "inst", "content", "metadata", "title", "indexBodyToken", "readmeToken");
        final MimeMessage encMsg = sender.encryptMail(msg, secEmailTo);
        sender.sendMail(encMsg);
    }
    
    /**
     * Failure case for invalid recipient.
     * @throws MessagingException should not happen
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRecipient() throws MessagingException {
        String invalidEmailAddr = secEmailTo + ".invalid";
        final MimeMessage msg = sender.createMessage(invalidEmailAddr, "Clinical Note", "subj", "inst", "content", "metadata", "title", "indexBodyToken", "readmeToken");
        final MimeMessage encMsg = sender.encryptMail(msg, invalidEmailAddr);
    }

    /**
     * tests receiving encrypted mail
     * 
     * @throws IOException - io exception thrown
     */
    @Test
    public void receiveEncryptedMessage() throws IOException {
        try {

            final MimeMessage msg = createMessage();

            final Properties smtpprops = new Properties();
            smtpprops.put("mail.smtp.auth", "true");
            smtpprops.put("mail.smtp.starttls.enable", "true");

            final SendEncryptedMail ssem = new SendEncryptedMail(smtpprops, secEmailFrom, LOCALHOST, POP3_PORT, "POP3",
                    secEmailTrustStoreLocation, secEmailTrustStorePassword, secEmailTrustStoreLocation);

            // use the to email address as the truststore keyalias is the email address
            final MimeMessage encMsg = ssem.encryptMail(msg, secEmailTo);

            final GreenMailUser user = server.setUser(secEmailTo, secEmailUser, secEmailPass);
            user.deliver(encMsg);
            assertEquals(1, server.getReceivedMessages().length);

            final Properties props = new Properties();
            props.setProperty(MAIL_POP3_PORT_KEY, String.valueOf(POP3_PORT));
            final Session session = Session.getInstance(props, null);
            session.setDebug(true);

            final Store store = session.getStore(POP3_TYPE);
            store.connect(LOCALHOST, secEmailUser, secEmailPass);
            final Folder folder = store.getFolder(INBOX);
            folder.open(Folder.READ_ONLY);

            final Message[] messages = folder.getMessages();
            assertTrue(messages != null);
            assertTrue(messages.length == 1);

            final MimeMessage retMsg = (MimeMessage) messages[0];

            final DecryptMail dm = new DecryptMail();
            final MimeBodyPart res = dm.decrypt(retMsg, secEmailKeyStoreLocation, secEmailKeyStorePassword,
                    secEmailKeyStoreKey);
            assertNotNull(res);

            final Multipart mp = (Multipart) res.getContent();
            assertTrue(mp.getCount() == 2);

            final Multipart origMsgMp = (Multipart) msg.getContent();

            validateMsgParts(origMsgMp, mp);

            // CHECKSTYLE:OFF
        } catch (Exception e) { // NOPMD
            // CHECKSTYLE:ON
            fail(UNEXPECTED_EXCEPTION + e);
        }
    }

    private void validateMsgParts(Multipart origMsgMp, Multipart actualMsgMp) {
        try {
            final String textpart = (String) actualMsgMp.getBodyPart(0).getContent();
            assertNotNull(textpart);
            assertEquals((String) origMsgMp.getBodyPart(0).getContent(), textpart);

            final String attachPart = getPartContent(actualMsgMp.getBodyPart(1));
            assertNotNull(attachPart);
            assertEquals(getPartContent(origMsgMp.getBodyPart(1)), attachPart);
        } catch (IOException e) {
            fail(UNEXPECTED_EXCEPTION + e);
        } catch (MessagingException e) {
            fail(UNEXPECTED_EXCEPTION + e);
        }
    }

    private String getPartContent(Part part) {
        BufferedReader reader = null;
        String partMsg = null;
        try {
            final Writer writer = new StringWriter();
            final char[] buffer = new char[1024];
            reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
            int n = -1;
            while ((n = reader.read(buffer)) != -1) { // NOPMD
                writer.write(buffer, 0, n);
            }
            partMsg = writer.toString();
            // CHECKSTYLE:OFF
        } catch (Exception e) { // NOPMD
            // CHECKSTYLE:ON
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.error("Error closing BufferedReader!");
                }
            }
        }
        return partMsg;
    }

    private MimeMessage createMessage() throws Exception { // NOPMD

        final String subject = "Notification of Document Availability";

        final Properties mailProps = new Properties();
        final Session session = Session.getInstance(mailProps, null);
        mailProps.setProperty("mail.smtp.host", LOCALHOST);
        mailProps.setProperty("mail.smtp.port", String.valueOf(SMTP_PORT));
        mailProps.setProperty("mail.smtp.sendpartial", "true");
        session.setDebug(true);

        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(secEmailFrom));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(secEmailTo));

        final MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText("Instructions to the user.");
        mbp1.setHeader("Content-Type", "text/plain");

        final MimeBodyPart mbp2 = new MimeBodyPart();
        final ClassLoader cl = TestMail.class.getClassLoader();
        final URLDataSource ds = new URLDataSource(cl.getResource("purchase_order.xml"));

        mbp2.setDataHandler(new DataHandler(ds));
        mbp2.setFileName("IHEXDSNAV-" + UUID.randomUUID() + ".xml");
        mbp2.setHeader("Content-Type", "application/xml; charset=UTF-8");

        final Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);
        mp.addBodyPart(mbp2);

        msg.setContent(mp);
        msg.setSentDate(new Date());

        return msg;
    }

}
