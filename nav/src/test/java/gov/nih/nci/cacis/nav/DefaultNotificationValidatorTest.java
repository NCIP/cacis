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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;

/**
 * Test for DefaultNotificationValidator
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @author vinodh.rc@semanticbits.com
 * @since May 4, 2011
 * 
 */
public class DefaultNotificationValidatorTest {
    
    private final DefaultNotificationValidator v = new DefaultNotificationValidator(new SimpleX509KeySelector(),
            new DefaultDocumentReferenceValidator());

    /**
     * Test validate digital signature
     * 
     * @throws Exception - error thrown
     */
    @Test
    public void validateDigitalSignature() throws Exception { //NOPMD

        final String recommendedRegistry = "urn:oid:1.3.983249923.1234.3";
        final String docId1 = "urn:oid:1.3.345245354.435345";
        final String docId2 = "urn:oid:1.3.345245354.435346";
        final String docPath1 = "sample_exchangeCCD.xml";
        final String docPath2 = "purchase_order.xml";
        final String docPath3 = "purchase_order_bad.xml";

        final ClassLoader cl = DefaultNotificationValidatorTest.class.getClassLoader();

        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        final DocumentBuilder db = dbf.newDocumentBuilder();
        final Document doc = db.parse(cl.getResourceAsStream("notification_gen.xml"));
        
        v.validateDigitalSignature(doc.getDocumentElement(), null);

        final InMemoryCacheDocumentHolder h = new InMemoryCacheDocumentHolder();
        h.putDocument(docId1, cl.getResourceAsStream(docPath1));
        h.putDocument(docId2, cl.getResourceAsStream(docPath2));
        final XDSDocumentResolver r = new InMemoryCacheXDSDocumentResolver(recommendedRegistry, h);
        v.validateDigitalSignature(doc, r);

        h.removeDocument(docId2);
        h.putDocument(docId2, cl.getResourceAsStream(docPath3));
        try {
            v.validateDigitalSignature(doc, r);
            fail("Should've failed validation of modified document");
        } catch (NotificationValidationException ex) {
            assertTrue(true);
        }
    }
    
    /**
     * Test validate notification message
     * 
     * @throws Exception - error thrown
     */
    @Test
    public void validateNotificationMessage() throws Exception { //NOPMD
        final Message message = MessageUtils.createSMTPMessage();
        
        try {
            v.validate(message);
        } catch (NotificationValidationException e) {
            fail("Should not have thrown NotificationValidationException");
        }
        
    }
    
    /**
     * Test validate message match IHE ITI Nav Message format
     * 
     * @throws Exception - error thrown
     */
    @Test
    public void validateRequires2Parts() throws Exception { //NOPMD
        final Message message = MessageUtils.createSMTPMessage();
        
        final Multipart mp = (Multipart) message.getContent();
        mp.removeBodyPart(0);
        try {
            v.validate(message);
        } catch (NotificationValidationException e) {
            assertEquals(DefaultNotificationValidator.ERR_RQR_2_PRTS_MSG, e.getMessage());
        }
        
    }
    
    /**
     * Test validate message match IHE ITI Nav Message format
     * 
     * @throws Exception - error thrown
     */
    @Test
    public void validateTextPart() throws Exception { //NOPMD
        final Message message = MessageUtils.createSMTPMessage();
        
        final Multipart mp = (Multipart) message.getContent();
        mp.removeBodyPart(0);
        mp.addBodyPart(mp.getBodyPart(0));
        try {
            v.validate(message);
        } catch (NotificationValidationException e) {
            assertEquals(DefaultNotificationValidator.ERR_INVALID_TEXT_PART_MSG, e.getMessage());
        }
        
    }
    
    /**
     * Test validate message match IHE ITI Nav Message format
     * 
     * @throws Exception - error thrown
     */
    @Test
    public void validateAttachmentPartPresence() throws Exception { //NOPMD
        final Message message = MessageUtils.createSMTPMessage();
        
        final Multipart mp = (Multipart) message.getContent();
        mp.removeBodyPart(1);
        mp.addBodyPart(mp.getBodyPart(0));
        try {
            v.validate(message);
        } catch (NotificationValidationException e) {
            assertEquals(DefaultNotificationValidator.ERR_INVALID_ATTMNT_PART_MSG, e.getMessage());
        }        
    }
    
    /**
     * Test validate message match IHE ITI Nav Message format
     * 
     * @throws Exception - error thrown
     */
    @Test
    public void validateMissingAttachmentDisposition() throws Exception { //NOPMD
        final Message message = MessageUtils.createSMTPMessage();
        
        final Multipart mp = (Multipart) message.getContent();
        mp.getBodyPart(1).setDisposition(null);
        
        try {
            v.validate(message);
        } catch (NotificationValidationException e) {
            assertEquals(DefaultNotificationValidator.ERR_INVALID_ATTMNT_PART_MSG, e.getMessage());
        }        
    }
    
    /**
     * Test validate message match IHE ITI Nav Message format
     * 
     * @throws Exception - error thrown
     */
    @Test
    public void validateAttachmentMimeType() throws Exception { //NOPMD
        final Message message = MessageUtils.createSMTPMessage();
        
        final Multipart mp = (Multipart) message.getContent();
        mp.getBodyPart(1).setHeader("Content-Type", "text/plain");
        
        try {
            v.validate(message);
        } catch (NotificationValidationException e) {
            assertEquals(DefaultNotificationValidator.ERR_INVALID_ATTMNT_MIMETYPE_MSG, e.getMessage());
        }        
    }
    
    /**
     * Test validate message match IHE ITI Nav Message format
     * 
     * @throws Exception - error thrown
     */
    @Test
    public void validateAttachmentFileName() throws Exception { //NOPMD
        final Message message = MessageUtils.createSMTPMessage();
        
        final Multipart mp = (Multipart) message.getContent();
        
        mp.getBodyPart(1).setFileName("Not-valid-file-name.xml");
        try {
            v.validate(message);
        } catch (NotificationValidationException e) {
            assertEquals(DefaultNotificationValidator.ERR_INVALID_FILENAME_FORMAT, e.getMessage());
        }
        
        mp.getBodyPart(1).setFileName("IHEXDSNAV-invalid-uuid.xml");
        try {
            v.validate(message);
        } catch (NotificationValidationException e) {
            assertEquals(DefaultNotificationValidator.ERR_INVALID_FILENAME_FORMAT, e.getMessage());
        }
        
    }
}
