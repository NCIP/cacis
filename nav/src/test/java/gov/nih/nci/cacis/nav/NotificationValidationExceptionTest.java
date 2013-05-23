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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * @author bpickeral
 * @since Jun 22, 2011
 */
public class NotificationValidationExceptionTest {
    private final String MESSAGE = "test message";

    /**
     * Test NotificationValidationException default constructor
     */
    @Test
    public void createException() {
        final NotificationValidationException nve = new NotificationValidationException();
        assertNotNull(nve);
    }

    /**
     * Test NotificationValidationException passing message
     */
    @Test
    public void createExceptionWithMessage() {
        final NotificationValidationException nve = new NotificationValidationException(MESSAGE);
        assertEquals(MESSAGE, nve.getMessage());
    }

    /**
     * Test NotificationValidationException passing message + cause
     */
    @Test
    public void createExceptionWithMessageCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final NotificationValidationException nve = new NotificationValidationException(MESSAGE, e);

        assertEquals(MESSAGE, nve.getMessage());
        assertEquals(e, nve.getCause());
    }

    /**
     * Test NotificationValidationException passing message + cause
     */
    @Test
    public void createExceptionWithCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final NotificationValidationException nve = new NotificationValidationException(MESSAGE, e);

        assertEquals(e, nve.getCause());
    }
}
