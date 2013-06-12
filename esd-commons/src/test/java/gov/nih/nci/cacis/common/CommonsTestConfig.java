/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common;

import gov.nih.nci.cacis.common.util.CommonsPropertyPlaceholderConfigurer;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * cacis-commons test config
 * 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 *
 */
@Configuration
public class CommonsTestConfig {

    /**
     * Loads properties from "classpath*:/c.properties" location
     *
     * @return the property place holder configures
     */
    @Bean
    public PropertyPlaceholderConfigurer commonsPropertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer configurer =
                new CommonsPropertyPlaceholderConfigurer("cacis-commons", "cacis-commons-test.properties");
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}

