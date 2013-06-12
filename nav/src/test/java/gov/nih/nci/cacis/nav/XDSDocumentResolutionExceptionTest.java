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
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * @author bpickeral
 * @since Jun 22, 2011
 */
public class XDSDocumentResolutionExceptionTest {
    private final String MESSAGE = "test message";

    /**
     * Test XDSDocumentResolutionException default constructor
     */
    @Test
    public void createException() {
        final XDSDocumentResolutionException xdre = new XDSDocumentResolutionException();
        assertNotNull(xdre);
    }

    /**
     * Test XDSDocumentResolutionException passing message
     */
    @Test
    public void createExceptionWithMessage() {
        final XDSDocumentResolutionException xdre = new XDSDocumentResolutionException(MESSAGE);
        assertEquals(MESSAGE, xdre.getMessage());
    }

    /**
     * Test XDSDocumentResolutionException passing message + cause
     */
    @Test
    public void createExceptionWithMessageCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final XDSDocumentResolutionException xdre = new XDSDocumentResolutionException(MESSAGE, e);

        assertEquals(MESSAGE, xdre.getMessage());
        assertEquals(e, xdre.getCause());
    }

    /**
     * Test XDSDocumentResolutionException passing message + cause
     */
    @Test
    public void createExceptionWithCause() {
        final Exception e = new NullPointerException(MESSAGE);
        final XDSDocumentResolutionException xdre = new XDSDocumentResolutionException(MESSAGE, e);

        assertEquals(e, xdre.getCause());
    }
}
