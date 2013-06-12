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

import gov.nih.nci.cacis.common.doc.DocumentHandler;
import gov.nih.nci.cacis.nav.DefaultXDSNotificationSignatureBuilder;
import gov.nih.nci.cacis.nav.NotificationSender;
import gov.nih.nci.cacis.nav.NotificationSenderImpl;
import gov.nih.nci.cacis.nav.OpenXDSDocumentResolver;
import gov.nih.nci.cacis.nav.SendEncryptedMail;
import gov.nih.nci.cacis.nav.SendSignedMail;
import gov.nih.nci.cacis.nav.XDSDocumentResolver;
import gov.nih.nci.cacis.nav.XDSNotificationSignatureBuilder;

import java.security.KeyStoreException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

/**
 * Spring config for NAV.
 * 
 * @author bpickeral
 * @since Aug 2, 2011
 */
@Configuration
public class NAVTestConfig {

    @Value("${xds.repo.oid}")
    private String repoOID;

    @Autowired
    @Qualifier("wrapperDocumentHandler")
    private DocumentHandler docHndlr;

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
     * GreenMail Bean.
     * 
     * @return server
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public GreenMail server() {
        return new GreenMail(new ServerSetup(3125, null, ServerSetup.PROTOCOL_SMTP));
    }

    /**
     * Notification Sender.
     * 
     * @return notification sender
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public NotificationSender notificationSender() {
        final NotificationSenderImpl notificationSender = new NotificationSenderImpl(null, null, null, null, "", null,
                0, null);

        notificationSender.setSignatureBuilder(signatureBuilder());
        notificationSender.setHost(server().getSmtp().getBindTo());
        notificationSender.setPort(server().getSmtp().getPort());
        notificationSender.setProtocol(server().getSmtp().getProtocol());
        notificationSender.setInstructions(instructions);
        notificationSender.setSubject(subject);
        notificationSender.setFrom(mailFrom);
        notificationSender.setMailProperties(new Properties());

        // username will be set sending time

        return notificationSender;
    }

    /**
     * Notification Signature builder.
     * 
     * @return notification signature builder
     */
    @Bean
    public XDSNotificationSignatureBuilder signatureBuilder() {
        return new DefaultXDSNotificationSignatureBuilder(documentResolver(), SignatureMethod.RSA_SHA1,
                DigestMethod.SHA1, keyStoreType, keyStoreLocation, keyStorePassword, keyStoreKey);
    }

    /**
     * XDS Document Resolver
     * 
     * @return notification sender
     */
    @Bean
    public XDSDocumentResolver documentResolver() {
        return new OpenXDSDocumentResolver(repoOID, docHndlr);
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
        final Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        final SendSignedMail sender = new SendSignedMail(props, secEmailFrom, 
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
     * @throws KeyStoreException  exception thrown, if any
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SendEncryptedMail encryptedMailSender() throws MessagingException, KeyStoreException {
        final Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        final SendEncryptedMail sender = new SendEncryptedMail(props, secEmailFrom, 
                secEmailHost, secEmailPort, secEmailProtocol, secEmailTrustStoreLocation,
                secEmailTrustStorePassword, secEmailTempZipLocation);
        sender.setLoginDetails(secEmailUser, secEmailPass);

        return sender;
    }

}
