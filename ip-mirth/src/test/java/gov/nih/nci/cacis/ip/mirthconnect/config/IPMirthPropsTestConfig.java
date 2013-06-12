/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect.config;


import gov.nih.nci.cacis.common.util.CommonsPropertyPlaceholderConfigurer;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * test properties for NAV
 * @author vinodh.rc
 */
@Configuration
public class IPMirthPropsTestConfig {
    /**
     * Property file config used in ip-mirth-test.
     * @return PropertyPlaceholderConfigurer
     */
    @Bean
    public PropertyPlaceholderConfigurer testPropertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer configurer = new CommonsPropertyPlaceholderConfigurer("ip-mirth-test",
                "cacis-ip-mirth-test.properties");
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

}
