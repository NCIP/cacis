/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.service.XdsWriteAuthzManager;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AppCtxTest {

    @Test
    public void init() throws AuthzProvisioningException {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"applicationContext-xds-authz-handlers-hsqldb.xml"});

        assertTrue(context.getBeanDefinitionCount() > 0);
        final XdsWriteAuthzManager docAccessMgr = (XdsWriteAuthzManager) context.getBean("xdsWriteAuthzManager");
        assertNotNull(docAccessMgr);


    }
}
