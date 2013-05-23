/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect.config;


import gov.nih.nci.cacis.ip.mirthconnect.CanonicalModelProcessorClient;
import gov.nih.nci.cacis.ip.mirthconnect.ftp.FTPMapping;
import gov.nih.nci.cacis.ip.mirthconnect.ftp.FTPSender;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

/**
 * Config for IP Mirth.
 *
 * @author bpickeral
 * @since Aug 2, 2011
 */
@Configuration
@ImportResource(value = "classpath*:cmp-client-cxf.xml")
public class IPMirthConfig {

    @Value("${ftp.mappingFile}")
    private String ftpMappingFile;

    @Value("${cmp.client.keystore.location}")
    private String cmpKeystoreLocation;

    @Value("${cmp.client.keystore.password}")
    private String cmpKeystorePassword;

    @Value("${cmp.client.truststore.location}")
    private String cmpTruststoreLocation;

    @Value("${cmp.client.truststore.password}")
    private String cmpTruststorePassword;

    /**
     * Creates FTPSender.
     *
     * @return FTPSender
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public FTPSender sender() {
        return new FTPSender();
    }

    /**
     * Creates FTPSClient.
     *
     * @return FTPSClient
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public FTPSClient ftpsCient() {
        return new FTPSClient("TLS", true);
    }

    /**
     * Creates FTPMapp ing.
     * @return FTPMapping
     * @throws IOException on I/O error
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public FTPMapping ftpMapping() throws IOException {
        return new FTPMapping(ftpMappingFile);
    }

    /**
     * CMP Client which
     * can invoke a secure CMP service
     *
     * @return CanonicalModelProcessorClient
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public CanonicalModelProcessorClient canonicalModelProcessorClient() {
        return new CanonicalModelProcessorClient(cmpKeystoreLocation, cmpKeystorePassword,
                cmpTruststoreLocation, cmpTruststorePassword);

    }

}
