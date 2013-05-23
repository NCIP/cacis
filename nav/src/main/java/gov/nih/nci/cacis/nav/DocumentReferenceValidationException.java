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

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Jun 9, 2011
 * 
 */

public class DocumentReferenceValidationException extends Exception {

    private static final long serialVersionUID = 5628083978741162919L;

    /**
     * @param message the message
     */

    public DocumentReferenceValidationException(String message) {
        super(message);
    }

    /**
     * @param arg0 the message
     */

    public DocumentReferenceValidationException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param message the message
     * @param throwable the throwable
     */

    public DocumentReferenceValidationException(String message, Throwable throwable) {
        super(message, throwable);

    }

}
