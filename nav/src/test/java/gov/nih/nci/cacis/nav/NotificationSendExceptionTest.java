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
public class NotificationSendExceptionTest {
    private final String MESSAGE = "test message";

    /**
     * Test NotificationReceiveException default constructor
     */
    @Test
    public void createException() {
        final NotificationReceiveException nse = new NotificationReceiveException();
        assertNotNull(nse);
    }

    /**
     * Test NotificationSendException passing message
     */
    @Test
    public void createExceptionWithMessage() {
        final NotificationSendException nse = new NotificationSendException(MESSAGE);
        assertEquals(MESSAGE, nse.getMessage());
    }

    /**
     * Test NotificationSendException passing message + cause
     */
    @Test
    public void createExceptionWithMessageCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final NotificationSendException nse = new NotificationSendException(MESSAGE, e);

        assertEquals(MESSAGE, nse.getMessage());
        assertEquals(e, nse.getCause());
    }

    /**
     * Test NotificationSendException passing message + cause
     */
    @Test
    public void createExceptionWithCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final NotificationSendException nse = new NotificationSendException(MESSAGE, e);

        assertEquals(e, nse.getCause());
    }
}
