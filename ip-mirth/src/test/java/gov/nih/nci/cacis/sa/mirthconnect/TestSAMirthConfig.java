/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.sa.mirthconnect;

import gov.nih.nci.cacis.common.util.CommonsPropertyPlaceholderConfigurer;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Config for sa-mirth tests.
 * @author monish.dombla@semanticbits.com
 * @since Aug 11, 2011
 */
@Configuration
public class TestSAMirthConfig {
    
    /**
     * 
     * @return propertyConfig
     */
    @Bean
    public PropertyPlaceholderConfigurer saMirthPropertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer configurer = new CommonsPropertyPlaceholderConfigurer("sa-mirth",
                "cacis-sa-mirth-test.properties");
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

}

