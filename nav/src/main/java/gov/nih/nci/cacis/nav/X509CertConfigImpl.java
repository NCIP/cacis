/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import java.security.cert.X509CertSelector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring config that encapsulates factory for X509CertSelector
 *
 * @author bpickeral
 * @since Jun 20, 2011
 */
@Configuration
public class X509CertConfigImpl implements X509CertConfig<X509CertSelector> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean
    public CertSelectorFactory<X509CertSelector> createCertSelectionFactory() {
        return new X509CertSelectorFactory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean
    public AlgorithmChecker createAlgorithmChecker() {
        return new DSARSAAlgorithmChecker();
    }

}
