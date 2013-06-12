/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.domain.XdsWriteResource;
import gov.nih.nci.cacis.xds.authz.service.XdsWriteAuthzManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-xds-authz-test-hsqldb.xml")
public class XdsWriteAuthzManagerTest {


    @Autowired
    private XdsWriteAuthzManager manager;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void grantCheckAndRevoke() throws AuthzProvisioningException {
        final String dn = "juser@example.com,cn=Joe User,dc=example,dc=com,o=Example Inc.,c=US";

        manager.grantStoreWrite(dn);
         final XdsWriteResource writeResource = (XdsWriteResource) this.em.createQuery
                ("from " + XdsWriteResource.class.getSimpleName())
                .getSingleResult();

        assertEquals(1, writeResource.getSubjects().size());

        assertTrue(manager.checkStoreWrite(dn));

        manager.revokeStoreWrite(dn);
        assertEquals(0, writeResource.getSubjects().size());

        assertFalse(manager.checkStoreWrite(dn));

    }

}
