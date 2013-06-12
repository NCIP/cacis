/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect.config;


import gov.nih.nci.cacis.cdw.config.TestCDWConfig;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * Config for CDW tests.
 * @author bpickeral
 * @since Aug 2, 2011
 */
@Configuration
@Import( { TestCDWConfig.class } )
public class IPMirthTestConfig {

    @Value("${ftp.test.server.keystore.location}")
    private String keystoreLocation;

    @Value("${ftp.test.server.keystore.password}")
    private String keystorePassword;

    @Value("${ftp.test.server.port}")
    private int ftpPort;

    /**
     * Creates FTP Server.
     * @return ftp server
     * @throws URISyntaxException if URI can not be parsed
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public FtpServer ftpsServer() throws URISyntaxException {
        final FtpServerFactory serverFactory = new FtpServerFactory();

        final ListenerFactory factory = new ListenerFactory();

        // set the port of the listener
        factory.setPort(ftpPort);

        // define SSL configuration
        final SslConfigurationFactory ssl = new SslConfigurationFactory();
        ssl.setKeystoreFile(new File(keystoreLocation));
        ssl.setKeystorePassword(keystorePassword);

        // set the SSL configuration for the listener
        factory.setSslConfiguration(ssl.createSslConfiguration());
        factory.setImplicitSsl(true);

        // replace the default listener
        serverFactory.addListener("default", factory.createListener());

        final PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File(Thread.currentThread().getContextClassLoader()
                .getResource("ftpusers.properties").toURI()));

        serverFactory.setUserManager(userManagerFactory.createUserManager());

        return serverFactory.createServer();
    }

}
