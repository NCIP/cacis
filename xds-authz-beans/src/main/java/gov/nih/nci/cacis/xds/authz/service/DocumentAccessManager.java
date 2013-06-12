/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz.service;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;

/**
 * XDS Document Access Manager
 */
public interface DocumentAccessManager {

    /**
     * Grant Access to a XDS Document
     * to a user
     *
     * @param documentSetId Document ID
     * @param userId User ID
     * @throws AuthzProvisioningException Exception
     */
    void grantDocumentAccess(String documentSetId, String userId) throws AuthzProvisioningException;

    /**
     * Revoke access to a XDS Document
     * from a user
     * @param documentSetId Document ID
     * @param userId User ID
     * @throws AuthzProvisioningException Exception
     */
    void revokeDocumentAccess(String documentSetId, String userId) throws AuthzProvisioningException;

    /**
     * Check if a user has access
     * to a Document
     *
     * @param documentSetId Document ID
     * @param userId User ID
     * @return true if user has access.
     *         false if user does not have access
     *
     * @throws AuthzProvisioningException Exception
     */
    boolean checkDocumentAccess(String documentSetId, String userId) throws AuthzProvisioningException;


}
