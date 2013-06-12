/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.sa.config;

import gov.nih.nci.cacis.sa.AcceptSourcePortType;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@Configuration
public class SemanticAdapterServiceConfig {

    @Value("${cacis-pco.sa.service.url}")
    private String saServiceURL;

    /**
     * SemanticAdapter web service
     * client
     *
     * @return AcceptSourcePortType service port type
     */
    @Bean
    public AcceptSourcePortType client() {
        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(AcceptSourcePortType.class);
        factory.setAddress(saServiceURL);
        return (AcceptSourcePortType) factory.create();
    }
}
