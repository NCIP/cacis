package gov.nih.nci.cacisweb.action;

import gov.nih.nci.cacisweb.CaCISWebConstants;
import gov.nih.nci.cacisweb.exception.CaCISWebException;
import gov.nih.nci.cacisweb.exception.KeystoreInstantiationException;
import gov.nih.nci.cacisweb.model.SecureXDSNAVModel;
import gov.nih.nci.cacisweb.util.CaCISUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class SecureXDSNAVAddAction extends ActionSupport {

    private static final Logger log = Logger.getLogger(SecureXDSNAVAddAction.class);

    private static final long serialVersionUID = 1L;

    private SecureXDSNAVModel secureXDSNAVBean;

    @Override
    public String execute() throws Exception {
        log.debug("execute() - START");
        String secureXDSNAVKeystoreLocation = CaCISUtil
                .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_TRUSTSTORE_LOCATION);
        try {
            CaCISUtil caCISUtil = new CaCISUtil();
            KeyStore keystore = caCISUtil.getKeystore(secureXDSNAVKeystoreLocation,
                    CaCISWebConstants.COM_KEYSTORE_TYPE_JKS, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_TRUSTSTORE_PASSWORD));

            caCISUtil.releaseKeystore();

            FileInputStream certificateStream = new FileInputStream(secureXDSNAVBean.getCertificate());

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            java.security.cert.X509Certificate cert = (X509Certificate) cf.generateCertificate(certificateStream);
            // Add the certificate
            keystore.setCertificateEntry(secureXDSNAVBean.getCertificateAlias(), cert);

            // Save the new keystore contents
            FileOutputStream out = new FileOutputStream(new File(secureXDSNAVKeystoreLocation));
            keystore.store(out, CaCISUtil.getProperty(
                    CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_TRUSTSTORE_PASSWORD).toCharArray());
            out.close();

            // add the new entry to XDSNAV configuration properties file
            // PropertiesConfiguration config = new PropertiesConfiguration(CaCISUtil
            // .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_CONFIG_FILE_LOCATION));
            // config.setProperty(secureXDSNAVBean.getCertificateAlias(), cert.getSubjectDN().getName());
            // config.save();

            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_CONFIG_FILE_LOCATION))));
            properties.setProperty(secureXDSNAVBean.getCertificateAlias(), cert.getSubjectDN().getName());
            properties.store(new FileOutputStream(new File(CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_CONFIG_FILE_LOCATION))), "");

            // send an email to the newly added recepient
            // note that the same email properties files has xdsnav related properties also
            String secureXDSNAVPropertyFileLocation = CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_PROPERTIES_FILE_LOCATION);

            String host = CaCISUtil.getPropertyFromPropertiesFile(secureXDSNAVPropertyFileLocation, CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_SENDER_HOST_PROP_NAME));
            String port = CaCISUtil.getPropertyFromPropertiesFile(secureXDSNAVPropertyFileLocation, CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_SENDER_PORT_PROP_NAME));

            String senderEmail = CaCISUtil.getPropertyFromPropertiesFile(secureXDSNAVPropertyFileLocation, CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_MESSAGE_FROM_PROP_NAME));
            String senderUser = CaCISUtil.getPropertyFromPropertiesFile(secureXDSNAVPropertyFileLocation, CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_SENDER_USER_PROP_NAME));
            String senderPassword = CaCISUtil.getPropertyFromPropertiesFile(secureXDSNAVPropertyFileLocation, CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_SENDER_PASSWORD_PROP_NAME));
            String subject = CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_SETUP_NOTIFICATION_SUBJECT_PROP_NAME);
            String message = CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_SETUP_NOTIFICATION_MESSAGE_PROP_NAME);

            caCISUtil.sendEmail(host, port, senderEmail, senderUser, senderPassword, subject, message, secureXDSNAVBean
                    .getCertificateAlias());

        } catch (KeystoreInstantiationException kie) {
            log.error(kie.getMessage());
            addActionError(getText("exception.keystoreInstantiation"));
            return ERROR;
        } catch (CertificateException ce) {
            log.error(CaCISUtil.getStackTrace(ce));
            addActionError(getText("exception.certification"));
            return INPUT;
        } catch (MessagingException e) {
            log.error(CaCISUtil.getStackTrace(e));
            addActionError(getText("secureXDSNAVBean.emailException"));
            return INPUT;
        }
        addActionMessage(getText("secureXDSNAVBean.addCertificateSuccessful"));
        log.debug("execute() - END");
        return SUCCESS;
    }

    public void validate() {
        try {
            String secureXDSNAVKeystoreLocation = CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_TRUSTSTORE_LOCATION);
            CaCISUtil caCISUtil = new CaCISUtil();
            KeyStore keystore = caCISUtil.getKeystore(secureXDSNAVKeystoreLocation,
                    CaCISWebConstants.COM_KEYSTORE_TYPE_JKS, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_TRUSTSTORE_PASSWORD));

            if (keystore.containsAlias(secureXDSNAVBean.getCertificateAlias())) {
                log.error(getText("secureXDSNAVBean.duplicateKey"));
                addFieldError("secureXDSNAVBean.certificateAlias", getText("secureXDSNAVBean.duplicateKey"));
            }
            caCISUtil.releaseKeystore();
        } catch (KeystoreInstantiationException kie) {
            log.error(CaCISUtil.getStackTrace(kie));
            addActionError(getText("exception.keystoreInstantiation"));
        } catch (CaCISWebException e) {
            log.error(CaCISUtil.getStackTrace(e));
            addActionError(getText("exception.cacisweb"));
        } catch (KeyStoreException e) {
            log.error(CaCISUtil.getStackTrace(e));
            addActionError(getText("exception.keystoreAccess"));
        }

    }

    public SecureXDSNAVModel getSecureXDSNAVBean() {
        return secureXDSNAVBean;
    }

    public void setSecureXDSNAVBean(SecureXDSNAVModel secureXDSNAVBean) {
        this.secureXDSNAVBean = secureXDSNAVBean;
    };

}
