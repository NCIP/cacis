package gov.nih.nci.cacisweb.action;

import gov.nih.nci.cacisweb.CaCISWebConstants;
import gov.nih.nci.cacisweb.exception.CaCISWebException;
import gov.nih.nci.cacisweb.exception.KeystoreInstantiationException;
import gov.nih.nci.cacisweb.model.SecureFTPModel;
import gov.nih.nci.cacisweb.util.CaCISUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class SecureFTPAddAction extends ActionSupport {

    private static final Logger log = Logger.getLogger(SecureFTPAddAction.class);

    private static final long serialVersionUID = 1L;

    private SecureFTPModel secureFTPBean;

    @Override
    public String execute() throws Exception {
        log.debug("execute() - START");
        String secureFTPKeystoreLocation = CaCISUtil
                .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_RECEPIENT_KEYSTORE_LOCATION);
        try {
            CaCISUtil caCISUtil = new CaCISUtil();
            KeyStore keystore = caCISUtil.getKeystore(secureFTPKeystoreLocation,
                    CaCISWebConstants.COM_KEYSTORE_TYPE_JKS, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_RECEPIENT_KEYSTORE_PASSWORD));

            caCISUtil.releaseKeystore();

            FileInputStream certificateStream = new FileInputStream(secureFTPBean.getCertificate());

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            java.security.cert.Certificate cert = cf.generateCertificate(certificateStream);
            // Add the certificate
            keystore.setCertificateEntry(secureFTPBean.getCertificateAlias(), cert);

            // Save the new keystore contents
            FileOutputStream out = new FileOutputStream(new File(secureFTPKeystoreLocation));
            keystore.store(out, CaCISUtil.getProperty(
                    CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_RECEPIENT_KEYSTORE_PASSWORD).toCharArray());
            out.close();

            // add the new entry to FTP configuration properties file
            PropertiesConfiguration config = new PropertiesConfiguration(CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_RECEPIENT_CONFIG_FILE_LOCATION));
            config.setProperty(secureFTPBean.getCertificateAlias(), "");
            config.save();
        } catch (KeystoreInstantiationException kie) {
            log.error(kie.getMessage());
            addActionError(getText("exception.keystoreInstantiation"));
            return ERROR;
        } catch (CertificateException ce) {
            log.error(CaCISUtil.getStackTrace(ce));
            addActionError(getText("exception.certification"));
            return INPUT;
        }
        addActionMessage(getText("secureFTPBean.addCertificateSuccessful"));
        log.debug("execute() - END");
        return SUCCESS;
    }

    public void validate() {
        try {
            String secureFTPKeystoreLocation = CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_RECEPIENT_KEYSTORE_LOCATION);
            CaCISUtil caCISUtil = new CaCISUtil();
            KeyStore keystore = caCISUtil.getKeystore(secureFTPKeystoreLocation,
                    CaCISWebConstants.COM_KEYSTORE_TYPE_JKS, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_RECEPIENT_KEYSTORE_PASSWORD));

            if (keystore.containsAlias(secureFTPBean.getCertificateAlias())) {
                log.error(getText("secureFTPBean.duplicateKey"));
                addFieldError("secureFTPBean.certificateAlias", getText("secureFTPBean.duplicateKey"));
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

    public SecureFTPModel getSecureFTPBean() {
        return secureFTPBean;
    }

    public void setSecureFTPBean(SecureFTPModel secureFTPBean) {
        this.secureFTPBean = secureFTPBean;
    };

}
