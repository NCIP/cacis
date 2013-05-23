/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.xds;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author monish.dombla@semanticbits.com
 */
@Configuration
public class XdsMetadataConfig {
 
    /**
     * Return DefaultXdsMetadataSupplier
     * @return XdsMetadataSupplier
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public XdsMetadataSupplier xdsMetadataSupplier() {
        return new DefaultXdsMetadataSupplier();
    }
}