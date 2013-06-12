/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.client;

import gov.nih.nci.cacis.common.doc.DocumentHandler;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/** 
 * Config for XDS client 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 *
 */
public interface XDSConfig {

    /**
     * DocumentHandler to store the documents in the xds
     * @return DocumentHandler for XDS
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    DocumentHandler documentHandler();

}
