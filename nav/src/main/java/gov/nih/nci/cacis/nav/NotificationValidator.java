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

import javax.mail.Message;

import org.w3c.dom.Node;

/**
 * Defines the behavior of the NAV validation
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 5, 2011
 * 
 */
public interface NotificationValidator {

    /**
     * Validates a NAV message according to IHE ITI TF-2a
     * 
     * @param message the message to be validated
     * @throws NotificationValidationException on error
     */
    void validate(Message message) throws NotificationValidationException;
    
    /**
     * Validates the signature element using the Java XML Digital Signature API
     * 
     * @param sig the Signature element
     * @param resolver the document resolver. If null, Reference elements will be ignored
     * @throws NotificationValidationException - on error
     */
    void validateDigitalSignature(Node sig, XDSDocumentResolver resolver) throws NotificationValidationException;

}
