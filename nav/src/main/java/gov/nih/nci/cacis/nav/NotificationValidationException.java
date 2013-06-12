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

/**
 * Represents errors encountered during validation
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 5, 2011
 * 
 */
public class NotificationValidationException extends Exception {

    private static final long serialVersionUID = -2190511830922206453L;

    /**
     * Default constructor
     */
    public NotificationValidationException() {
        super();
    }

    /**
     * Takes the cause
     * 
     * @param cause the cause
     */
    public NotificationValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * Takes a message and the cause
     * 
     * @param msg a message
     * @param cause the cause
     */
    public NotificationValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Takes a message
     * 
     * @param msg a message
     */
    public NotificationValidationException(String msg) {
        super(msg);
    }
}
