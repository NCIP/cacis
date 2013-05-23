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

import org.w3c.dom.Node;

/**
 * Represents validation of a Reference elements in an application-specific manner.
 * See http://www.w3.org/TR/xmldsig-core/#sec-Manifest
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Jun 9, 2011
 * 
 */

public interface DocumentReferenceValidator {

    /**
     * Validates the reference according to application-specific logic
     * 
     * @param reference the Reference element
     * @param resolver the document resolver
     * @throws DocumentReferenceValidationException on error
     */
    void validate(Node reference, XDSDocumentResolver resolver) throws DocumentReferenceValidationException;
}
