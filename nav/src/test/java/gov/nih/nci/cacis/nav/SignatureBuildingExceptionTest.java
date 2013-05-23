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
public class SignatureBuildingExceptionTest {
    private final String MESSAGE = "test message";

    /**
     * Test SignatureBuildingException default constructor
     */
    @Test
    public void createException() {
        final SignatureBuildingException sbe = new SignatureBuildingException();
        assertNotNull(sbe);
    }

    /**
     * Test SignatureBuildingException passing message
     */
    @Test
    public void createExceptionWithMessage() {
        final SignatureBuildingException sbe = new SignatureBuildingException(MESSAGE);
        assertEquals(MESSAGE, sbe.getMessage());
    }

    /**
     * Test SignatureBuildingException passing message + cause
     */
    @Test
    public void createExceptionWithMessageCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final SignatureBuildingException sbe = new SignatureBuildingException(MESSAGE, e);

        assertEquals(MESSAGE, sbe.getMessage());
        assertEquals(e, sbe.getCause());
    }

    /**
     * Test SignatureBuildingException passing message + cause
     */
    @Test
    public void createExceptionWithCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final SignatureBuildingException sbe = new SignatureBuildingException(MESSAGE, e);

        assertEquals(e, sbe.getCause());
    }
}
