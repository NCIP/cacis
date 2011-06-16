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
