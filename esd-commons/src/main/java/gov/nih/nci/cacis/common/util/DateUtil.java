/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.util;

import java.util.Date;

/**
 * @author ramakrishnanr
 * @since Oct 1, 2010
 * 
 */
public final class DateUtil {

    /**
     * private default constructor.
     */
    private DateUtil() {
        
    }
    
    /**
     * 
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT 
     * represented by the  input Date object. 
     * 
     * @param inDate - date input
     * @return Long - null if date passed in is null else Date.getTime()
     */
    public static Long getTimeFromDate(Date inDate) {
        
        return (inDate == null ? null : Long.valueOf(inDate.getTime())) ;
    }

    
}
