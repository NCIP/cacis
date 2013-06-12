/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz.config;

import gov.nih.nci.cacis.xds.authz.service.DocumentAccessManager;
import gov.nih.nci.cacis.xds.authz.service.DocumentAccessManagerImpl;
import gov.nih.nci.cacis.xds.authz.service.XdsWriteAuthzManager;
import gov.nih.nci.cacis.xds.authz.service.XdsWriteAuthzManagerImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@Configuration
public class ServiceConfig {

    @PersistenceContext(unitName = JpaConfig.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    /**
     * XDS Authorization Write Access Manager
     *
     * @return XdsWriteAuthzManager
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public XdsWriteAuthzManager xdsWriteAuthzManager() {
        return new XdsWriteAuthzManagerImpl(em);
    }

    /**
     * Docuemnt Access Manager
     * @return DocumentAccessManager
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public DocumentAccessManager documentAccessManager() {
        return new DocumentAccessManagerImpl(em);
    }
}

