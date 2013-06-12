/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz.config;

import gov.nih.nci.cacis.common.util.CommonsPropertyPlaceholderConfigurer;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @author kherm manav.kher@semanticbits.com
 */

@Import({ JpaConfig.class, ServiceConfig.class })
@Configuration
public class XdsAuthzConfig {

    /**
     * Java equivalent of   <aop:aspectj-autoproxy/>
     * @return AnnotationAwareAspectJAutoProxyCreator
     */
    @Bean
    public AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator() {
        return new AnnotationAwareAspectJAutoProxyCreator();
    }

    /**
     * Loads properties from classpath
     *
     * @return the property place holder configures
     */
    @Bean
    public PropertyPlaceholderConfigurer xdsPropertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer configurer =
                new CommonsPropertyPlaceholderConfigurer("cacis-xds", "cacis-xds-authz.properties");
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }


}
