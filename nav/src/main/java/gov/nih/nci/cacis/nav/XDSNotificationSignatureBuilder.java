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

import java.util.List;

import org.w3c.dom.Node;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 10, 2011
 * 
 */

public interface XDSNotificationSignatureBuilder {

    /**
     * 
     * @param documentIds list of document IDs
     * @return the Signature
     * @throws SignatureBuildingException on error
     */
    Node buildSignature(List<String> documentIds) throws SignatureBuildingException;

    /**
     * XDS Document resolver
     * @return XDSDocumentResolver
     */
    XDSDocumentResolver getDocumentResolver();

}
