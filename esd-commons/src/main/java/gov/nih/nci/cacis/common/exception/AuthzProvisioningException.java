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
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Jul 15, 2011
 * 
 */
public class AuthzProvisioningException extends Exception {

    private static final long serialVersionUID = 3840305759740086597L;

    /** 
     */

    public AuthzProvisioningException() {

        // TODO Auto-generated constructor stub

    }

    /**
     * @param msg a message
     */
    public AuthzProvisioningException(String msg) {
        super(msg);
    }

    /**
     * @param throwable the Throwable to wrap
     */

    public AuthzProvisioningException(Throwable throwable) {
        super(throwable);
    }

    /**
     * @param msg a message
     * @param throwable the Throwable to wrap
     */

    public AuthzProvisioningException(String msg, Throwable throwable) {

        super(msg, throwable);

    }

}
