/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.service.DocumentAccessManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-xds-authz-handlers-hsqldb.xml")
public class DocumentAccessManagerTest {


    @Autowired
    private DocumentAccessManager manager;

    @PersistenceContext
    private EntityManager em;

    private final String docId = "Document123";
    private final String userId = "User123";


    @Test
    public void grantDocumentAccess() throws AuthzProvisioningException {
        manager.grantDocumentAccess(docId, userId);
        assertTrue(manager.checkDocumentAccess(docId, userId));

        manager.grantDocumentAccess(docId, userId);
        assertTrue(manager.checkDocumentAccess(docId, userId));
    }

    @Test
    public void revokeDocumentAccess() throws AuthzProvisioningException {
        manager.revokeDocumentAccess(docId, userId);
        assertFalse(manager.checkDocumentAccess(docId, userId));
    }


}
