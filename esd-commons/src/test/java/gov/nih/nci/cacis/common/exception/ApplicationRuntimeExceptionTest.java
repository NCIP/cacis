/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author bpickeral
 * @since Jun 22, 2011
 */
public class ApplicationRuntimeExceptionTest {
    private final String MESSAGE = "test message";

    /**
     * Test ApplicationRuntimeException passing message
     */
    @Test
    public void createExceptionWithMessage() {
        final ApplicationRuntimeException are = new ApplicationRuntimeException(MESSAGE);
        assertEquals(MESSAGE, are.getMessage());
    }

    /**
     * Test ApplicationRuntimeException passing message + cause
     */
    @Test
    public void createExceptionWithMessageCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final ApplicationRuntimeException are = new ApplicationRuntimeException(MESSAGE, e);

        assertEquals(MESSAGE, are.getMessage());
        assertEquals(e, are.getCause());
    }
}
