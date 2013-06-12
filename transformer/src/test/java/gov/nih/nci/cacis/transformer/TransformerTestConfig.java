/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.transformer;

import gov.nih.nci.cacis.common.util.CommonsPropertyPlaceholderConfigurer;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Test configuration
 * @author bpickeral
 * @since Jul 8, 2011
 */
@Configuration
public class TransformerTestConfig {

    /**
     * Loads properties from classpath*:/"transformer-test.properties" location
     *
     * @return the property place holder configures
     */
    @Bean( name = "transformers-test")
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        final CommonsPropertyPlaceholderConfigurer configurer = new CommonsPropertyPlaceholderConfigurer(
                "transformers-test", "transformers-test.properties");
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

}
