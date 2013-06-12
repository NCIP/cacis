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

import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Validates the notification according to the rules in IHE ITI TF-2a.
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @author vinodh.rc@semanticbits.com
 * @since May 4, 2011
 */
public class DefaultNotificationValidator implements NotificationValidator {
    
    /**
     * Error msg while getting signature properties
     */
    private static final String ERROR_GET_SIG_PROPS_MSG = "Error getting signature properties";
    /**
     * Error msg for missing reqd doc refs
     */
    private static final String ERR_REQD_DOC_REFS_MSG = "Missing required document references in the manifest!";
    /**
     * Error msg for missing reqd recommend registry id
     */
    private static final String ERR_REQD_RECMND_REG_ID_MSG = "Missing required recommended registry id!";

    /**
     * Error msg for attachment mimetype
     */
    public static final String ERR_INVALID_ATTMNT_MIMETYPE_MSG = 
        "Not a valid attachment part: Second part of the multipart must be of 'application/xml' MIMEType!";
    
    /**
     * Error msg for attachment
     */
    public static final String ERR_INVALID_ATTMNT_PART_MSG = 
        "Not a valid attachment part: Second part of the multipart must be of attachment type!";

    /**
     * Error msg for text mimetype
     */
    public static final String ERR_INVALID_TEXT_PART_MSG = 
        "Not a valid text part: First part of the multipart must be of 'text/plain' MIMEType!";
    /**
     * Error msg for attachment filename format
     */
    public static final String ERR_INVALID_FILENAME_FORMAT = 
        "Not valid filename format!";
    /**
     * Error msg for not valid multipart msg
     */
    public static final String ERR_INVALID_MULTIPART_MSG = 
        "Not a valid multipart message!";
    /**
     * Error msg for 2 parts requirement for multipart
     */
    public static final String ERR_RQR_2_PRTS_MSG = 
        "Not a valid multipart message: Missing the required two parts!";
    /**
     * Error msg while getting signature
     */
    public static final String ERR_GET_SIG_FRM_MSG = 
        "Error getting signature from message: ";
    /**
     * Error msg for doc reference retrieval
     */
    public static final String ERR_RETREIVE_DOC_REF_ELMNTS = 
        "Error retrieving document reference elements: ";
    /**
     * Error msg for attachment mimetype
     */
    public static final String ERR_SIG_VALIDATION_FAILED_MSG = 
        "Signature validation failed";

    private static final String ATT_FILE_NM_PATTERN = 
        "^(IHEXDSNAV-([0-9a-fA-F]){8}-([0-9a-fA-F]){4}-([0-9a-fA-F]){4}-([0-9a-fA-F]){4}-([0-9a-fA-F]){12}.xml)$";

    private KeySelector keySelector;

    private DocumentReferenceValidator documentReferenceValidator;

    /**
     * Takes a KeySelector
     * 
     * @param keySelector selects the key for validating the signature
     * @param documentReferenceValidator resolves and validates digest on referenced documents
     */
    public DefaultNotificationValidator(KeySelector keySelector, DocumentReferenceValidator documentReferenceValidator) {
        this.keySelector = keySelector;
        this.documentReferenceValidator = documentReferenceValidator;
    }

    @Override
    public void validate(Message message) throws NotificationValidationException {

        // Check that the Content-Type is multipart/mixed
        final Multipart multipart = NAVUtils.getMessageContent(message);
        
        // Check that it has two parts
        // Check that the first part has Content-Type text/plain (or a sub type)
        // Check that the first part contains some text
        // Check that the second part has Content-Type application/xml; charset=UTF-8
        // Check that Content-Disposition is an attachment
        // Check that the filename is of form 'IHEXDSNAV-<UUID>.xml'
        validateMultiparts(multipart);

        // get Signature node from message
        final Node sig = getSignature(message);
        
        /*
         * Validate using XML Digital Signature API
         * Validate against the IHE ITI Digital Signature Profile - 
         * SignatureProperties element contains -
         * recommendedRegistry: URN of XDS DocumentRegistry
         * homeCommunityId: optional, ignored
         * sendAcknowledgementTo: optional, ignored
         * Signature shall contain a Manifest which contains one or more
         * Reference elements.
         */
        validateSignatureProperties(sig);
        validateDigitalSignature(sig, null);        
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.cacis.nav.NotificationValidator#validateDigitalSignature()
     */
    @Override
    public void validateDigitalSignature(Node sig, final XDSDocumentResolver resolver)
            throws NotificationValidationException {

        boolean valid = false;

        try {
            final DOMValidateContext valContext = new DOMValidateContext(getKeySelector(), sig);
            final XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
            final XMLSignature signature = fac.unmarshalXMLSignature(valContext);

            // We cannot validate the References to documents in the XDS without
            // first retrieving them. So, for now, we can't do "core" validation.
            // We can only validate the Signature itself.
            valid = signature.getSignatureValue().validate(valContext);

            // CHECKSTYLE:OFF
        } catch (Exception ex) {
            // CHECKSTYLE:ON
            throw new NotificationValidationException("Error validating digital signature: " + ex.getMessage(), ex);
        }
        if (!valid) {
            throw new NotificationValidationException(ERR_SIG_VALIDATION_FAILED_MSG);
        }
        
        validateDocReferences(sig, resolver);
    }
    
    private Document getSignature(Message message) throws NotificationValidationException {
        try {
            return NAVUtils.getSignature(message);
            // CHECKSTYLE:OFF
        } catch (Exception ex) {
            // CHECKSTYLE:ON
            throw new NotificationValidationException(ERR_GET_SIG_FRM_MSG + ex.getMessage(), ex);
        }
    }
    
    private void validateDocReferences(Node sig, final XDSDocumentResolver resolver) throws NotificationValidationException {
        if (resolver != null) {
            // Now we can validate the references

            List<Node> refEls;
            try {
                refEls = NAVUtils.getDocumentReferences(sig);
                // CHECKSTYLE:OFF
            } catch (Exception ex) {
                // CHECKSTYLE:ON
                throw new NotificationValidationException(ERR_RETREIVE_DOC_REF_ELMNTS
                        + ex.getMessage(), ex);
            }

            for (Node referenceEl : refEls) {
                try {
                    getDocumentReferenceValidator().validate(referenceEl, resolver);
                } catch (DocumentReferenceValidationException ex) {
                    throw new NotificationValidationException(ex);
                }
            }
        }
    }
    
    private void validateSignatureProperties(Node sig) throws NotificationValidationException {        
            try {
                final String recRegId = NAVUtils.getRegistryId(sig);
                final List<String> docIds = NAVUtils.getDocumentIds(sig);
                
                if ( StringUtils.isEmpty(recRegId) ) {
                    throw new NotificationValidationException(ERR_REQD_RECMND_REG_ID_MSG);
                }
                
                if ( docIds == null || docIds.isEmpty() ) {
                    throw new NotificationValidationException(ERR_REQD_DOC_REFS_MSG);
                }
            } catch (XPathExpressionException e) {
                throw new NotificationValidationException(ERROR_GET_SIG_PROPS_MSG , e);
            } catch (DOMException e) {
                throw new NotificationValidationException(ERROR_GET_SIG_PROPS_MSG , e);
            }
         
    }
    
    
    private void validateMultiparts(Multipart multipart) throws NotificationValidationException {
        try {
            if ( multipart.getCount() != 2 ) {
                throw new NotificationValidationException(
                        ERR_RQR_2_PRTS_MSG); 
            }
            validateTextBodyPart(multipart.getBodyPart(0));
            validateAttachmentBodyPart(multipart.getBodyPart(1));
        } catch (MessagingException e) {
            throw new NotificationValidationException(ERR_INVALID_MULTIPART_MSG, e);
        }
    }
    
    private void validateTextBodyPart(BodyPart bodyPart) throws NotificationValidationException {
        try {           
            if ( !bodyPart.isMimeType("text/plain") ) {
                throw new NotificationValidationException(
                        ERR_INVALID_TEXT_PART_MSG);
            }
        } catch (MessagingException e) {
            throw new NotificationValidationException(ERR_INVALID_MULTIPART_MSG, e);
        }        
    }
    
    private void validateAttachmentBodyPart(BodyPart bodyPart) throws NotificationValidationException {
        try {
            final String disposition = bodyPart.getDisposition();            
            if ( StringUtils.isEmpty(disposition) 
                    || !disposition.equalsIgnoreCase(Part.ATTACHMENT) ) {
                throw new NotificationValidationException(
                    ERR_INVALID_ATTMNT_PART_MSG);
            }
            
            if ( !bodyPart.isMimeType("application/xml") ) {
                throw new NotificationValidationException(
                    ERR_INVALID_ATTMNT_MIMETYPE_MSG);
            }
            validateAttachmentFileName(bodyPart.getFileName());            
        } catch (MessagingException e) {
            throw new NotificationValidationException(ERR_INVALID_MULTIPART_MSG, e);
        }        
    }
    
    private void validateAttachmentFileName(String filename) throws NotificationValidationException {        
        if ( !filename.matches(ATT_FILE_NM_PATTERN) ) {
            throw new NotificationValidationException(ERR_INVALID_FILENAME_FORMAT);
        }
    }

    /**
     * @return the keySelector
     */

    public KeySelector getKeySelector() {

        return keySelector;
    }

    /**
     * @param keySelector the keySelector to set
     */

    public void setKeySelector(KeySelector keySelector) {

        this.keySelector = keySelector;
    }

    /**
     * @return the documentReferenceValidator
     */

    public DocumentReferenceValidator getDocumentReferenceValidator() {

        return documentReferenceValidator;
    }

    /**
     * @param documentReferenceValidator the documentReferenceValidator to set
     */

    public void setDocumentReferenceValidator(DocumentReferenceValidator documentReferenceValidator) {

        this.documentReferenceValidator = documentReferenceValidator;
    }

}
