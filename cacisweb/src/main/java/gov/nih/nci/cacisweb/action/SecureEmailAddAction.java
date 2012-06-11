package gov.nih.nci.cacisweb.action;

import gov.nih.nci.cacisweb.CaCISWebConstants;
import gov.nih.nci.cacisweb.exception.CaCISWebException;
import gov.nih.nci.cacisweb.exception.KeystoreInstantiationException;
import gov.nih.nci.cacisweb.model.SecureEmailModel;
import gov.nih.nci.cacisweb.util.CaCISUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class SecureEmailAddAction extends ActionSupport {

    private static final Logger log = Logger.getLogger(SecureEmailAddAction.class);

    private static final long serialVersionUID = 1L;

    private SecureEmailModel secureEmailBean;

    @Override
    public String execute() throws Exception {
        log.debug("execute() - START");
        String secureEmailPropertyFileLocation = CaCISUtil
                .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_PROPERTIES_FILE_LOCATION);
        try {
            CaCISUtil caCISUtil = new CaCISUtil();
            KeyStore keystore = caCISUtil.getKeystore(CaCISUtil.getPropertyFromPropertiesFile(
                    secureEmailPropertyFileLocation, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_TRUSTSTORE_LOCATION_PROP_NAME)),
                    CaCISWebConstants.COM_KEYSTORE_TYPE_PKCS12,
                    CaCISUtil.getPropertyFromPropertiesFile(secureEmailPropertyFileLocation, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_TRUSTSTORE_PASSWORD_PROP_NAME)));

            caCISUtil.releaseKeystore();

            FileInputStream certificateStream = new FileInputStream(secureEmailBean.getCertificate());

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            java.security.cert.Certificate cert = cf.generateCertificate(certificateStream);
            // Add the certificate
            keystore.setCertificateEntry(secureEmailBean.getCertificateAlias(), cert);

            // Save the new keystore contents
            FileOutputStream out = new FileOutputStream(new File(CaCISUtil.getPropertyFromPropertiesFile(
                    secureEmailPropertyFileLocation, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_TRUSTSTORE_LOCATION_PROP_NAME))));
            keystore.store(out, CaCISUtil.getPropertyFromPropertiesFile(secureEmailPropertyFileLocation,
                    CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_TRUSTSTORE_PASSWORD_PROP_NAME))
                    .toCharArray());
            out.close();

            // send an email to the newly added recepient
            String host = CaCISUtil.getPropertyFromPropertiesFile(secureEmailPropertyFileLocation, CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_SENDER_HOST_PROP_NAME));
            String port = CaCISUtil.getPropertyFromPropertiesFile(secureEmailPropertyFileLocation, CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_SENDER_PORT_PROP_NAME));
            
            String senderEmail = CaCISUtil.getPropertyFromPropertiesFile(secureEmailPropertyFileLocation, CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_MESSAGE_FROM_PROP_NAME));
            String senderUser = CaCISUtil.getPropertyFromPropertiesFile(secureEmailPropertyFileLocation, CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_SENDER_USER_PROP_NAME));
            String senderPassword = CaCISUtil.getPropertyFromPropertiesFile(secureEmailPropertyFileLocation, CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_SENDER_PASSWORD_PROP_NAME));
            String subject = CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_SETUP_NOTIFICATION_SUBJECT_PROP_NAME);
            String message = CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_SETUP_NOTIFICATION_MESSAGE_PROP_NAME);

            caCISUtil.sendEmail(host, port, senderEmail, senderUser, senderPassword, subject, message, secureEmailBean
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
            addActionError(getText("secureEmailBean.emailException"));
            return INPUT;
        }
        addActionMessage(getText("secureEmailBean.addCertificateSuccessful"));
        log.debug("execute() - END");
        return SUCCESS;
    }

    public void validate() {
        try {
            String secureEmailPropertyFileLocation = CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_PROPERTIES_FILE_LOCATION);
            CaCISUtil caCISUtil = new CaCISUtil();
            KeyStore keystore = caCISUtil.getKeystore(CaCISUtil.getPropertyFromPropertiesFile(
                    secureEmailPropertyFileLocation, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_TRUSTSTORE_LOCATION_PROP_NAME)),
                    CaCISWebConstants.COM_KEYSTORE_TYPE_PKCS12,
                    CaCISUtil.getPropertyFromPropertiesFile(secureEmailPropertyFileLocation, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_TRUSTSTORE_PASSWORD_PROP_NAME)));

            if (keystore.containsAlias(secureEmailBean.getCertificateAlias())) {
                log.error(getText("secureEmailBean.duplicateKey"));
                addFieldError("secureEmailBean.certificateAlias", getText("secureEmailBean.duplicateKey"));
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

    public SecureEmailModel getSecureEmailBean() {
        return secureEmailBean;
    }

    public void setSecureEmailBean(SecureEmailModel secureEmailBean) {
        this.secureEmailBean = secureEmailBean;
    };

}
