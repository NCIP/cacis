/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.exception;

/**
 * Generic Application Runtime Exception. Thrown by application implementation code.
 * 
 * @author Slava Zazansky
 * @since Nov 1, 2010
 * 
 */
public class ApplicationRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Create ApplicationRuntimeException with given message.
     * 
     * @param message the message
     */
    public ApplicationRuntimeException(final String message) {
        super(message);
    }

    /**
     * Create ApplicationRuntimeException with given message and root cause.
     * 
     * @param message the message
     * @param cause the root cause
     */
    public ApplicationRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

}

