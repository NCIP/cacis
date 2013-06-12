/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz;

import gov.nih.nci.cacis.common.util.CommonsPropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Uses hsqldb as the testing database
 *
 * @author kherm manav.kher@semanticbits.com
 */
@Configuration
public class TestXdsAuthzConfig {

    /**
       * Loads properties
       * Test properties override the
       *  project properties
       * @return the property place holder configures
       */
      @Bean
      public PropertyPlaceholderConfigurer xdsTestPropertyPlaceholderConfigurer() {
          final PropertyPlaceholderConfigurer configurer =
                  new CommonsPropertyPlaceholderConfigurer("xds-test-authz", "test.properties");
          configurer.setIgnoreUnresolvablePlaceholders(true);
          return configurer;
      }

}
