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
public class NotificationReceiveExceptionTest {
    private final String MESSAGE = "test message";

    /**
     * Test NotificationReceiveException default constructor
     */
    @Test
    public void createException() {
        final NotificationReceiveException nre = new NotificationReceiveException();
        assertNotNull(nre);
    }

    /**
     * Test NotificationReceiveException passing message
     */
    @Test
    public void createExceptionWithMessage() {
        final NotificationReceiveException nre = new NotificationReceiveException(MESSAGE);
        assertEquals(MESSAGE, nre.getMessage());
    }

    /**
     * Test NotificationReceiveException passing message + cause
     */
    @Test
    public void createExceptionWithMessageCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final NotificationReceiveException nre = new NotificationReceiveException(MESSAGE, e);

        assertEquals(MESSAGE, nre.getMessage());
        assertEquals(e, nre.getCause());
    }

    /**
     * Test NotificationReceiveException passing message + cause
     */
    @Test
    public void createExceptionWithCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final NotificationReceiveException nre = new NotificationReceiveException(MESSAGE, e);

        assertEquals(e, nre.getCause());
    }
}
