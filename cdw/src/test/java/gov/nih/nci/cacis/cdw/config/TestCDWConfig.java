/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw.config;

import gov.nih.nci.cacis.common.util.CommonsPropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Jul 18, 2011
 *
 */
@Configuration
@Import( {  CDWConfigImpl.class })
public class TestCDWConfig  {

    @Bean
    public PropertyPlaceholderConfigurer testPropertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer configurer = new CommonsPropertyPlaceholderConfigurer("test-cdw", "test-cacis-cdw.properties");
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}
