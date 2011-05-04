package gov.nih.nci.cacis.common.service;

import gov.nih.nci.cacis.common.service.enums.ExceptionSeverityCode;

/**
 * Enum to hold commong exception codes
 * 
 * @author vinodh.rc@semanticbits.com
 * @since Nov 22, 2010
 * 
 */
public enum CommonExceptionCode implements ExceptionCode {

    /****************************************************
     * Codes 100XX - belong to general Service Exceptions
     ****************************************************/

    /**
     *  CRE10000 service exception code
     */
    CRE10000(ExceptionSeverityCode.FATAL, "General service exception occured"),
    /**
     *  CRE10001 service exception code
     */
    CRE10001(ExceptionSeverityCode.FATAL, "Schematron validation failed exception occured");

    private ExceptionSeverityCode exceptionSeverity;
    private String displayName;

    /**
     * @param exceptionSeverity
     * @param displayName
     */

    private CommonExceptionCode(ExceptionSeverityCode exceptionSeverity, String displayName) {
        this.exceptionSeverity = exceptionSeverity;
        this.displayName = displayName;
    }

    /**
     * @return the exceptionSeverity
     */

    public ExceptionSeverityCode getExceptionSeverity() {
        return this.exceptionSeverity;
    }

    /**
     * @return the displayName
     */

    public String getDisplayName() {
        return this.displayName;
    }
}

