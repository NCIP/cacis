/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz.service;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public interface XdsWriteAuthzManager {

    /**
     * Grant access to Subject on
     * the XDS store/write operation
     *
     * @param subjectDN Subject Distinguished Name
     * to grant access to
     * @throws AuthzProvisioningException Exception
     */
    void grantStoreWrite(String subjectDN) throws AuthzProvisioningException;

    /**
     * Revoke access for Sunject on the
     * XDS store/write operation
     *
     * @param subjectDN Subject Distinguished Name
     * to revoke access from
     * @throws AuthzProvisioningException Exception
     */
    void revokeStoreWrite(String subjectDN) throws AuthzProvisioningException;

    /**
     * Checks if user has access to invoke
     * xds store operation
     *
     * @param subjectDN Subject Distinguished Name
     * @throws AuthzProvisioningException Exception
     * @return boolean value indicating if Subject has access
     */
    boolean checkStoreWrite(String subjectDN) throws AuthzProvisioningException;

}
