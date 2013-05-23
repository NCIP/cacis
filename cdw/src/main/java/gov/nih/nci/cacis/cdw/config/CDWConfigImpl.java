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

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Jul 15, 2011
 */
@Configuration
@Import({ VirtuosoJdbcConfigImpl.class,  TransformerConfig.class })
public class CDWConfigImpl implements CDWConfig {


    @Override    
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public CDWLoader loader() {
        return new CDWLoader();
    }
    
    @Override    
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public CDWPendingLoader pendingLoader() {
        return new CDWPendingLoader();
    }
    
    
    /**
     * Loads properties from classpath*:/"transformer-test.properties" location
     *
     * @return the property place holder configures
     */
    @Bean
    public PropertyPlaceholderConfigurer cdwPropertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer configurer =
                new CommonsPropertyPlaceholderConfigurer("cdw", "cacis-cdw.properties");
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

}
