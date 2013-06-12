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
 * @param <T> out bound exception type created by the factory
 */

public interface OutboundFaultConfig<T extends Exception> {
    /**
     *
     * @return - Returns factory for outbound fault creation
     */
    OutBoundExceptionFactory<T> createOutBoundExceptionFactory();
}
