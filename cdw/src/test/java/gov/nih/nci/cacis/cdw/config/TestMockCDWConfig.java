/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw.config;


import gov.nih.nci.cacis.cdw.CDWLoader;
import gov.nih.nci.cacis.cdw.CDWPendingLoader;
import gov.nih.nci.cacis.common.util.CommonsPropertyPlaceholderConfigurer;
import gov.nih.nci.cacis.config.TransformerConfig;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * /**
 * Uses an in-memory persistence for the Sesame
 * repository
 *
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @author kherm manav.kher@semanticbits.com
 * @since Jul 18, 2011
 */
@Configuration
@Import({ InMemorySesameJdbcConfig.class, TransformerConfig.class })
public class TestMockCDWConfig implements CDWConfig {


    /**
     * CDW Loader
     *
     * @return loader
     */
    @Bean
    public CDWLoader loader() {
        return new CDWLoader();
    }

    @Bean
    public PropertyPlaceholderConfigurer testPropertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer configurer = new CommonsPropertyPlaceholderConfigurer("test-cdw", "test-cacis-cdw.properties");
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

    @Override
    public CDWPendingLoader pendingLoader() {
        	// TODO Auto-generated method stub
        return new CDWPendingLoader();
    }
}

