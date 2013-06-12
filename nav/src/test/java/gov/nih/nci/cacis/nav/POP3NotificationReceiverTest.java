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

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;

/**
 * Tests for POP3NotificationReceiver
 * 
 * @author joshua.phillips@semanticbits.com
 * 
 */
public class POP3NotificationReceiverTest {

    /**
     * Constant POP3_PORT
     */
    public static final int POP3_PORT = 3110;

    private GreenMail server;

    /**
     * starts GreenMain server for testing
     */
    @Before
    public void setUp() {
        server = new GreenMail();
        server.start();
    }

    /**
     * stops GreenMain server after testing
     */
    @After
    public void tearDown() {
        server.stop();
    }

    /**
     * Tests POP3 mail receiving
     * 
     * @throws Exception - error thrown
     */
    @Test
    public void testReceive() throws Exception {

        final Properties props = new Properties();
        props.setProperty("mail.pop3.port", String.valueOf(POP3_PORT));

        // Receive it using POP3
        final NotificationReceiver r = getNotificationReceiver(server, props);
        assertEquals(1, server.getReceivedMessages().length);

        final Message[] messages = r.receive();

        assertTrue(messages != null);
        assertTrue(messages.length == 1);

    }

    /**
     * constructs notification receiver
     * 
     * @param server - greenmail server
     * @param props - properties
     * @return NotificationReceiver isntance
     * @throws Exception on error
     */
    // CHECKSTYLE:OFF
    public static NotificationReceiver getNotificationReceiver(GreenMail server, Properties props) throws Exception { // NOPMD
        // CHECKSTYLE:ON
        final String host = "localhost";
        final String mailbox = "another.one@somewhere.com";
        final String login = "another.one";
        final String password = "secret";
        final String folder = "INBOX";
        final String recipient = "some.one@somewhere.com";
        final String subject = "Notification of Document Availability";

        // Construct a message
        final Session session = Session.getInstance(props, null);
        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailbox));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        // The readable part
        final MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText("Instructions to the user.");
        mbp1.setHeader("Content-Type", "text/plain");

        // The notification
        final MimeBodyPart mbp2 = new MimeBodyPart();
        final ClassLoader cl = POP3NotificationReceiverTest.class.getClassLoader();
        final URLDataSource ds = new URLDataSource(cl.getResource("notification_gen.xml"));
        mbp2.setDataHandler(new DataHandler(ds));
        mbp2.setFileName("IHEXDSNAV-" + UUID.randomUUID() + ".xml");
        mbp2.setHeader("Content-Type", "application/xml; charset=UTF-8");

        // Combine the parts
        final Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);
        mp.addBodyPart(mbp2);
        msg.setContent(mp);
        msg.setSentDate(new Date());

        // Put it in the mailbox
        final GreenMailUser user = server.setUser(mailbox, login, password);
        user.deliver(msg);

        return new POP3NotificationReceiver(props, host, mailbox, login, password, folder);
    }
}
