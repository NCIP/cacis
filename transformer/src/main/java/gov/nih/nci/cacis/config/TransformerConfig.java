/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.config;

import gov.nih.nci.cacis.common.util.CommonsPropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author kherm manav.kher@semanticbits.com
 *         <p/>
 *         Main configura  tion class for the pco transofmer
 *         project
 */
@Configuration
@Import(XSLTTransformerConfig.class)
public class TransformerConfig {

    /**
     * Loads properties from classpath*:/"transformer-test.properties" location
     *
     * @return the property place holder configures
     */
    @Bean
    public PropertyPlaceholderConfigurer transformPropertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer configurer =
                new CommonsPropertyPlaceholderConfigurer("transformer", "cacis-pco-transformers.properties");
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

}
