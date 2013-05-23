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

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

/**
 * @author <a href="mailto:monish.dombla@semanticbits.com">Monish Dombla</a>
 * @since May 20, 2011
 * 
 */
public class NotificationSenderImpl extends AbstractSendMail implements NotificationSender {

    
    private XDSNotificationSignatureBuilder signatureBuilder;    
    private String subject;
    private String instructions;

    /**
     * Default constructor
     */
    public NotificationSenderImpl() { // NOPMD
        super();
    }

    /**
     * Parameterized constructor.
     * 
     * @param signatureBuilder
     * @param mailProperties
     * @param subject
     * @param to
     * @param from
     * @param instructions
     */
    @SuppressWarnings( { "PMD.ExcessiveParameterList" })
    // CHECKSTYLE:OFF
    public NotificationSenderImpl(XDSNotificationSignatureBuilder signatureBuilder, Properties mailProperties,
            String subject, String from, String instructions, String host, int port, String protocol) { // NOPMD
        super(mailProperties, from, host, port, protocol);
        this.signatureBuilder = signatureBuilder;
        this.subject = subject;
        this.instructions = instructions;
    }

    // CHECKSTYLE:ON

    @Override
    public void send(String toEmailAddress, List<String> documentIds) throws NotificationSendException {
        Node sig = null;
        try {
            sig = getSignatureBuilder().buildSignature(documentIds);
        } catch (SignatureBuildingException ex) {
            throw new NotificationSendException("Couldn't build notification signature: " + ex.getMessage(), ex);
        }

        try {
            sendEmail(toEmailAddress, sig);
        } catch (AddressException e) {
            throw new NotificationSendException(e);
        } catch (TransformerConfigurationException e) {
            throw new NotificationSendException(e);
        } catch (UnsupportedEncodingException e) {
            throw new NotificationSendException(e);
        } catch (MessagingException e) {
            throw new NotificationSendException(e);
        } catch (TransformerException e) {
            throw new NotificationSendException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new NotificationSendException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.cacis.nav.NotificationSender#setCredentials(java.lang.String, java.lang.String)
     */
    @Override
    public void setCredentials(String userName, String password) {
        setLoginDetails(userName, password);
    }

    private void sendEmail(String to, Node sig) throws AddressException, MessagingException,
            TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError,
            UnsupportedEncodingException {

        final MimeMessage msg = getMailSender().createMimeMessage();

        msg.setFrom(new InternetAddress(getFrom()));
        msg.setSubject(getSubject());
        msg.setSentDate(new Date());
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // The readable part
        final MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText(getInstructions());
        mbp1.setHeader("Content-Type", "text/plain");

        // The notification
        final MimeBodyPart mbp2 = new MimeBodyPart();

        final DOMSource source = new DOMSource(sig);
        final StringWriter xmlAsWriter = new StringWriter();
        final StreamResult result = new StreamResult(xmlAsWriter);
        TransformerFactory.newInstance().newTransformer().transform(source, result);
        
        final String contentType = "application/xml; charset=UTF-8";
        final String fileName = "IHEXDSNAV-" + UUID.randomUUID() + ".xml";

        final DataSource ds = new AttachmentDS(fileName, xmlAsWriter.toString(), contentType);

        mbp2.setDataHandler(new DataHandler(ds));
        mbp2.setFileName(fileName);
        mbp2.setHeader("Content-Type", contentType);

        final Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);
        mp.addBodyPart(mbp2);
        msg.setContent(mp);
        msg.setSentDate(new Date());

        getMailSender().send(msg);

    }

    /**
     * @return the signatureBuilder
     */
    public XDSNotificationSignatureBuilder getSignatureBuilder() {
        return signatureBuilder;
    }

    /**
     * @param signatureBuilder the signatureBuilder to set
     */
    public void setSignatureBuilder(XDSNotificationSignatureBuilder signatureBuilder) {
        this.signatureBuilder = signatureBuilder;
    }

   
    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    
    /**
     * @return the instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * @param instructions the instructions to set
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}
