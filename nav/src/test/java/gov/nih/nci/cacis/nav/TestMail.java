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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
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
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
public class TestMail {

    private static final String INBOX = "INBOX";

    private static final String POP3_TYPE = "pop3";

    private static final String MAIL_POP3_PORT_KEY = "mail.pop3.port";

    private static final String KEYALIAS = "cacisnavtestuser@gmail.com";

    private static final String STOREPASS = "changeit";

    private static final String KEYSTORE = "securemail.p12";

    private static final String TRUSTSTORE = "securemail_ts.p12";

    private static final String UNEXPECTED_EXCEPTION = "Unexpected exception: ";

    private static final String EMAIL = "cacisnavtestuser@gmail.com";
    private static final String LOGIN = "cacisnavtestuser";
    private static final String PASSWORD = "secret";
    private static final String TEMP_ZIP_LOCATION = "./";

    private static final Logger LOG = Logger.getLogger(TestMail.class);

    private static final String LOCALHOST = "localhost";
    private static final int SMTP_PORT = 3025;
    private static final int POP3_PORT = 3110;

    private GreenMail server;

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
     * Tests sending mail
     */
    @Test
    public void sendMessage() {
        try {

            Transport.send(createMessage());

            assertTrue(server.waitForIncomingEmail(5000, 1));

            final Message[] messages = server.getReceivedMessages();
            assertEquals(1, messages.length);
            // CHECKSTYLE:OFF
        } catch (Exception e) {
            // CHECKSTYLE:ON
            e.printStackTrace();
            fail(UNEXPECTED_EXCEPTION + e);
        }

    }

    /**
     * tests receiving mail
     * 
     * @throws IOException - io exception thrown
     */
    @Test
    public void receiveMessage() throws IOException {
        try {
            final MimeMessage msg = createMessage();

            final GreenMailUser user = server.setUser(EMAIL, LOGIN, PASSWORD);
            user.deliver(msg);
            assertEquals(1, server.getReceivedMessages().length);

            final Properties props = new Properties();
            props.setProperty(MAIL_POP3_PORT_KEY, String.valueOf(POP3_PORT));
            final Session session = Session.getInstance(props, null);
            session.setDebug(true);

            final Store store = session.getStore(POP3_TYPE);
            store.connect(LOCALHOST, LOGIN, PASSWORD);
            final Folder folder = store.getFolder(INBOX);
            folder.open(Folder.READ_ONLY);

            final Message[] messages = folder.getMessages();
            assertTrue(messages != null);
            assertTrue(messages.length == 1);

            final MimeMessage retMsg = (MimeMessage) messages[0];

            final Multipart mp = (Multipart) retMsg.getContent();
            assertTrue(mp.getCount() == 2);

            final String partMsg1 = getPartContent(mp.getBodyPart(0));
            assertNotNull(partMsg1);

            final String partMsg2 = getPartContent(mp.getBodyPart(1));
            assertNotNull(partMsg2);

            // CHECKSTYLE:OFF
        } catch (Exception e) { // NOPMD
            // CHECKSTYLE:ON
            fail(UNEXPECTED_EXCEPTION + e);
        }
    }
   
    /**
     * Test signed and encrypted message reading tests receiving signed and encrypted mail
     * 
     * @throws IOException - io exception thrown
     */
    @Test
    @SuppressWarnings("PMD.NcssMethodCount")
    public void receiveSignedAndEncryptedMessage() throws IOException {
        try {
            final String fromemail = "cacisnavtestuser@gmail.com";

            final MimeMessage orimsg = createMessage();

            orimsg.setFrom(new InternetAddress(fromemail));

            final String keystore = getClass().getClassLoader().getResource(KEYSTORE).getFile();
            final String trustStore = getClass().getClassLoader().getResource(TRUSTSTORE).getFile();

            final SendSignedMail ssm = new SendSignedMail(new Properties(), fromemail, LOCALHOST, POP3_PORT, "POP3",
                    keystore, STOREPASS, KEYALIAS);

            final SendEncryptedMail sem = new SendEncryptedMail(new Properties(), fromemail, LOCALHOST, POP3_PORT,
                    "POP3", trustStore, STOREPASS, TEMP_ZIP_LOCATION);
            
            final Properties props = new Properties();
            props.setProperty(MAIL_POP3_PORT_KEY, String.valueOf(POP3_PORT));
            final Session session = Session.getInstance(props, null);
            session.setDebug(true);
            
            final MimeMessage signedMsg = ssm.signMail(orimsg);
            final MimeMessage msgToBeSent = sem.encryptMail(signedMsg, KEYALIAS);

            final GreenMailUser user = server.setUser(EMAIL, LOGIN, PASSWORD);
            user.deliver(msgToBeSent);
            assertEquals(1, server.getReceivedMessages().length);

            final Store store = session.getStore(POP3_TYPE);
            store.connect("localhost", LOGIN, PASSWORD);
            final Folder folder = store.getFolder(INBOX);
            folder.open(Folder.READ_ONLY);

            final Message[] messages = folder.getMessages();
            assertTrue(messages != null);
            assertTrue(messages.length == 1);

            final MimeMessage retMsg = (MimeMessage) messages[0];

            final DecryptMail dm = new DecryptMail();
            final MimeBodyPart res = dm.decrypt(retMsg, keystore, STOREPASS, KEYALIAS);
            assertNotNull(res);
            
            final MimeMessage decryptedMessage = new MimeMessage(session);

            java.util.Enumeration hLineEnum = retMsg.getAllHeaderLines();
            while (hLineEnum.hasMoreElements()) {
              decryptedMessage.addHeaderLine((String) hLineEnum.nextElement());
            }

            decryptedMessage.setContent(res.getContent(), res.getContentType());
            
            //this is reqd for validating signature
            final MimeMessage decMsgCopy = copyMessage(decryptedMessage, session);
                        
            final ValidateSignedMail vsm = new ValidateSignedMail(false);
            vsm.validate(decMsgCopy, trustStore, STOREPASS, KEYALIAS);
            
            final MimeMultipart newContent = 
                (MimeMultipart) ((MimeMultipart)decMsgCopy.getContent()).getBodyPart(0).getContent();
            
            final MimeMessage origMsgCopy = copyMessage(orimsg, session);
            validateMsgParts((Multipart)origMsgCopy.getContent(), newContent);
            // CHECKSTYLE:OFF
        } catch (Exception e) { // NOPMD
            // CHECKSTYLE:ON
            e.printStackTrace();
            fail("Unexpected exception: " + e);
        }
    }

    private MimeMessage copyMessage(MimeMessage mm, Session s) throws MessagingException, java.io.IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        mm.writeTo(new com.sun.mail.util.CRLFOutputStream(baos));
        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return new MimeMessage(s, bais);
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

        final String from = "cacisnavtestuser@gmail.com";
        final String to = "cacisnavtestuser@gmail.com";
        final String subject = "Notification of Document Availability";

        final Properties mailProps = new Properties();
        final Session session = Session.getInstance(mailProps, null);
        mailProps.setProperty("mail.smtp.host", LOCALHOST);
        mailProps.setProperty("mail.smtp.port", String.valueOf(SMTP_PORT));
        mailProps.setProperty("mail.smtp.sendpartial", "true");
        session.setDebug(true);

        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

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
