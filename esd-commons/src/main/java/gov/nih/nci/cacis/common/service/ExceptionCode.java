package gov.nih.nci.cacis.common.service;

import gov.nih.nci.cacis.common.service.enums.ExceptionSeverityCode;

/**
 * Interface to be implemented by the various project exception codes
 * 
 * @author vinodh.rc@semanticbits.com
 * @since Nov 22, 2010
 * 
 */
public interface ExceptionCode {

    /**
     * @return returns the ExceptionSeverityCode for this code
     */
    ExceptionSeverityCode getExceptionSeverity();

    /**
     * @return returns the name for this code
     */
    String name();

    /**
     * @return returns the displayname for this code
     */
    String getDisplayName();
}

