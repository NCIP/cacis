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

/**
 * Utility class to create sample messages
 * @author sb-admin-cp
 *
 */

public final class MessageUtils {
    
    private static final int SMTP_PORT = 3025;
    private static final int POP3_PORT = 3110;
    
    /**
     * private constructor
     */
    private MessageUtils() {
        //empty constructor
    }
    
    /**
     * Creates sample smtp message
     * @return MimeMessage instance 
     * @throws Exception - error thrown
     */
    public static MimeMessage createSMTPMessage() throws Exception { // NOPMD

        final String from = "some.one@somewhere.com";
        final String to = "another.one@somewhere.com";
        final String subject = "Notification of Document Availability";

        final Properties mailProps = new Properties();        
        mailProps.setProperty("mail.smtp.host", "localhost");
        mailProps.setProperty("mail.smtp.port", String.valueOf(SMTP_PORT));
        mailProps.setProperty("mail.smtp.sendpartial", "true");
        
        final Session session = Session.getInstance(mailProps, null);
        session.setDebug(true);

        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

        msg.setContent(getMultipart());
        msg.setSentDate(new Date());

        return msg;
    }
    
    /**
     * Creates sample POP3 message
     * @return MimeMessage instance 
     * @throws Exception - error thrown
     */
    public static MimeMessage createPOP3Message() throws Exception { // NOPMD

        final Properties mailProps = new Properties();        
        mailProps.setProperty("mail.pop3.port", String.valueOf(POP3_PORT));
        
        final Session session = Session.getInstance(mailProps, null);
        session.setDebug(true);
        
        final String mailbox = "another.one@somewhere.com";
        final String recipient = "some.one@somewhere.com";
        final String subject = "Notification of Document Availability";
        
        // Construct a message
        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailbox));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        
        msg.setContent(getMultipart());
        msg.setSentDate(new Date());

        return msg;
    }
    
    private static Multipart getMultipart() throws Exception { //NOPMD
        final Multipart mp = new MimeMultipart();
        mp.addBodyPart(getTextBodypart());
        mp.addBodyPart(getAttachmentBodypart());
        
        return mp;
    }
    
    private static MimeBodyPart getTextBodypart() throws Exception { //NOPMD
        final MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText("Instructions to the user.");
        mbp1.setHeader("Content-Type", "text/plain");
        
        return mbp1;
    }
    
    private static MimeBodyPart getAttachmentBodypart() throws Exception { //NOPMD
        final MimeBodyPart mbp2 = new MimeBodyPart();
        final ClassLoader cl = TestMail.class.getClassLoader();
        final URLDataSource ds = new URLDataSource(cl.getResource("notification_gen.xml"));
        mbp2.setDataHandler(new DataHandler(ds));
        mbp2.setFileName("IHEXDSNAV-" + UUID.randomUUID() + ".xml");
        mbp2.setHeader("Content-Type", "application/xml; charset=UTF-8");
        
        return mbp2;
    }

    
}
