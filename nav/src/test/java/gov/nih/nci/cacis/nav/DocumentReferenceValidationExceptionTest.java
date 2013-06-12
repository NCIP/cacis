/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author bpickeral
 * @since Jun 22, 2011
 */
public class DocumentReferenceValidationExceptionTest {
    private static final String MESSAGE = "test message";

    /**
     * Test DocumentReferenceValidationException passing message
     */
    @Test
    public void createExceptionWithMessage() {
        final DocumentReferenceValidationException drve = new DocumentReferenceValidationException(MESSAGE);
        assertEquals(MESSAGE, drve.getMessage());
    }

    /**
     * Test DocumentReferenceValidationException passing message + cause
     */
    @Test
    public void createExceptionWithMessageCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final DocumentReferenceValidationException drve = new DocumentReferenceValidationException(MESSAGE, e);

        assertEquals(MESSAGE, drve.getMessage());
        assertEquals(e, drve.getCause());
    }

    /**
     * Test DocumentReferenceValidationException passing message + cause
     */
    @Test
    public void createExceptionWithCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final DocumentReferenceValidationException drve = new DocumentReferenceValidationException(MESSAGE, e);

        assertEquals(e, drve.getCause());
    }
}
