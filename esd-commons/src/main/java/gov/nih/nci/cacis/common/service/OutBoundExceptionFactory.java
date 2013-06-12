/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.service;

/**
 * @author: Ram Bhattaru
 * @since:  Dec 9, 2010
 * 
 * @param <T> out bound exception type
 */
public interface OutBoundExceptionFactory<T extends Exception> {
    /**
     * Handles/wraps the input exception into the implementation specific outbound exception
     * with empty message.
     * @param inputException - The input exception required for generating the outbound exception.
     * @return - Returns the appropriate parameterized exception for the requested operation.
     */
    T createOutBoundException(Exception inputException);

    /**
     * Handles/wraps the input exception into the implementation specific outbound exception
     * with the specified error message
     * @param errorMessage - String message for the wrapping exception returned
     * @param inputException - The input exception required for generating the outbound exception.
     * @return - Returns the appropriate parameterized exception for the requested operation.
     */
    T createOutBoundException(String errorMessage, Exception inputException);
}
