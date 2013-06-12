/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav.config;

import gov.nih.nci.cacis.nav.DefaultXDSNotificationSignatureBuilder;
import gov.nih.nci.cacis.nav.InMemoryCacheDocumentHolder;
import gov.nih.nci.cacis.nav.InMemoryCacheXDSDocumentResolver;
import gov.nih.nci.cacis.nav.NotificationSender;
import gov.nih.nci.cacis.nav.NotificationSenderImpl;
import gov.nih.nci.cacis.nav.SendEncryptedMail;
import gov.nih.nci.cacis.nav.SendSignedMail;
import gov.nih.nci.cacis.nav.XDSDocumentResolver;
import gov.nih.nci.cacis.nav.XDSNotificationSignatureBuilder;

import java.security.KeyStoreException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Spreing config for NAV.
 * @author bpickeral
 * @since Aug 2, 2011
 */
@Configuration
public class NAVConfig {

    private static final String TRUE = "true";

    @Value("${xds.repo.oid}")
    private String repoOID;

    @Value("${nav.keystore.type}")
    private String keyStoreType;

    @Value("${nav.keystore.location}")
    private String keyStoreLocation;

    @Value("${nav.keystore.password}")
    private String keyStorePassword;

    @Value("${nav.keystore.key}")
    private String keyStoreKey;

    @Value("${nav.message.instructions}")
    private String instructions;

    @Value("${nav.message.subject}")
    private String subject;

    @Value("${nav.message.from}")
    private String mailFrom;

    @Value("${nav.sender.host}")
    private String host;

    @Value("${nav.sender.port}")
    private int port;

    @Value("${nav.sender.protocol}")
    private String protocol;

    @Value("${nav.sender.user}")
    private String user;

    @Value("${nav.sender.pass}")
    private String pass;
    
    @Value("${sec.email.keystore.location}")
    private String secEmailKeyStoreLocation;

    @Value("${sec.email.keystore.password}")
    private String secEmailKeyStorePassword;
    
    //email address is being used as keyalias
    @Value("${sec.email.message.from}")
    private String secEmailKeyStoreKey;

    @Value("${sec.email.truststore.location}")
    private String secEmailTrustStoreLocation;

    @Value("${sec.email.truststore.password}")
    private String secEmailTrustStorePassword;

    @Value("${sec.email.message.from}")
    private String secEmailFrom;

    @Value("${sec.email.sender.host}")
    private String secEmailHost;

    @Value("${sec.email.sender.port}")
    private int secEmailPort;

    @Value("${sec.email.sender.protocol}")
    private String secEmailProtocol;

    @Value("${sec.email.sender.user}")
    private String secEmailUser;

    @Value("${sec.email.sender.pass}")
    private String secEmailPass;
    
    @Value("${sec.email.temp.zip.location}")
    private String secEmailTempZipLocation;


    /**
     * Notification Sender.
     * @return notification sender
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public NotificationSender notificationSender() {
        final NotificationSenderImpl notificationSender =
            new NotificationSenderImpl(null, null, null, null, "", null, 0, null);

        final Properties props = new Properties();
        props.put("mail.smtp.auth", TRUE);
        props.put("mail.smtp.starttls.enable", TRUE);

        notificationSender.setSignatureBuilder(signatureBuilder());
        notificationSender.setHost(host);
        notificationSender.setPort(port);
        notificationSender.setProtocol(protocol);
        notificationSender.setInstructions(instructions);
        notificationSender.setSubject(subject);
        notificationSender.setFrom(mailFrom);
        notificationSender.setMailProperties(props);
        notificationSender.setLoginDetails(user, pass);

        //username will be set sending time

        return notificationSender;
    }

    /**
     * Notification Signature builder.
     * @return notification signature builder
     */
    @Bean
    public XDSNotificationSignatureBuilder signatureBuilder() {
        return new DefaultXDSNotificationSignatureBuilder(
                documentResolver(),
                SignatureMethod.RSA_SHA1, DigestMethod.SHA1, keyStoreType,
                keyStoreLocation, keyStorePassword, keyStoreKey);
    }

    /**
     * XDS Document Resolver
     * @return notification sender
     */
    @Bean
    public XDSDocumentResolver documentResolver() {
        return new InMemoryCacheXDSDocumentResolver(repoOID, new InMemoryCacheDocumentHolder());
    }
    
    /**
     * Secure signed mail Sender.
     * 
     * @return SendSignedMail sender
     * @throws MessagingException  exception thrown, if any
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SendSignedMail signedMailSender() throws MessagingException {
        final SendSignedMail sender = new SendSignedMail(new Properties(), secEmailFrom, 
                secEmailHost, secEmailPort, secEmailProtocol, secEmailKeyStoreLocation,
                secEmailKeyStorePassword, secEmailKeyStoreKey);
        sender.setLoginDetails(secEmailUser, secEmailPass);

        return sender;
    }
    
    /**
     * Secure encrypted mail Sender.
     * 
     * @return SendEncryptedMail sender
     * @throws MessagingException  exception thrown, if any
     * @throws KeyStoreException exception thrown, if any
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SendEncryptedMail encryptedMailSender() throws MessagingException, KeyStoreException {
        final Properties props = new Properties();
        props.put("mail.smtp.auth", TRUE);
        props.put("mail.smtp.starttls.enable", TRUE);
        
        final SendEncryptedMail sender = new SendEncryptedMail(props, secEmailFrom, 
                secEmailHost, secEmailPort, secEmailProtocol, secEmailTrustStoreLocation,
                secEmailTrustStorePassword, secEmailTempZipLocation);
        sender.setLoginDetails(secEmailUser, secEmailPass);

        return sender;
    }


}
