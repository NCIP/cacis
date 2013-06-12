/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.action;

import gov.nih.nci.cacisweb.CaCISWebConstants;
import gov.nih.nci.cacisweb.exception.KeystoreInstantiationException;
import gov.nih.nci.cacisweb.model.SecureFTPModel;
import gov.nih.nci.cacisweb.util.CaCISUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class SecureFTPAddAction extends ActionSupport {

    private static final Logger log = Logger.getLogger(SecureFTPAddAction.class);

    private static final long serialVersionUID = 1L;

    private SecureFTPModel secureFTPBean;

    @Override
    public String execute() throws Exception {
        log.debug("execute() - START");
        String secureFTPPropertyFileLocation = CaCISUtil
                .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_PROPERTIES_FILE_LOCATION);
        String secureFTPKeystoreLocation = CaCISUtil.getPropertyFromPropertiesFile(secureFTPPropertyFileLocation,
                CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_TRUSTSTORE_LOCATION_PROP_NAME));
        String secureFTPKeystorePassword = CaCISUtil.getPropertyFromPropertiesFile(secureFTPPropertyFileLocation,
                CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_TRUSTSTORE_PASSWORD_PROP_NAME));
        try {
            CaCISUtil caCISUtil = new CaCISUtil();
            KeyStore keystore = caCISUtil.getKeystore(secureFTPKeystoreLocation,
                    CaCISWebConstants.COM_KEYSTORE_TYPE_JKS, secureFTPKeystorePassword);

            if (keystore.containsAlias(secureFTPBean.getCertificateAlias())) {
                log.error(getText("secureFTPBean.duplicateKey"));
                addFieldError("secureFTPBean.certificateAlias", getText("secureFTPBean.duplicateKey"));                
            }

            if (StringUtils.contains(secureFTPBean.getCertificateAlias(), "ftps")) {
                if (StringUtils.isBlank(secureFTPBean.getCertificateFileName())) {
                    log.error(getText("secureFTPBean.certificateRequired"));
                    addFieldError("secureFTPBean.certificateFileName", getText("secureFTPBean.certificateRequired"));
                    caCISUtil.releaseKeystore();
                    return INPUT;
                } else {
                    caCISUtil.releaseKeystore();
                    FileInputStream certificateStream = new FileInputStream(secureFTPBean.getCertificate());

                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    java.security.cert.Certificate cert = cf.generateCertificate(certificateStream);
                    // Add the certificate
                    keystore.setCertificateEntry(secureFTPBean.getCertificateAlias(), cert);

                    // Save the new keystore contents
                    FileOutputStream out = new FileOutputStream(new File(secureFTPKeystoreLocation));
                    keystore.store(out, secureFTPKeystorePassword.toCharArray());
                    out.close();
                }
            }

            // add the new entry to FTP configuration properties file
            PropertiesConfiguration config = new PropertiesConfiguration(CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_CONFIG_FILE_LOCATION));
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

    /*public void validate() {
        try {
            String secureFTPPropertyFileLocation = CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_PROPERTIES_FILE_LOCATION);
            String secureFTPKeystoreLocation = CaCISUtil.getPropertyFromPropertiesFile(secureFTPPropertyFileLocation,
                    CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_TRUSTSTORE_LOCATION_PROP_NAME));
            String secureFTPKeystorePassword = CaCISUtil.getPropertyFromPropertiesFile(secureFTPPropertyFileLocation,
                    CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECFTP_TRUSTSTORE_PASSWORD_PROP_NAME));
            CaCISUtil caCISUtil = new CaCISUtil();
            KeyStore keystore = caCISUtil.getKeystore(secureFTPKeystoreLocation,
                    CaCISWebConstants.COM_KEYSTORE_TYPE_JKS, secureFTPKeystorePassword);

            if (keystore.containsAlias(secureFTPBean.getCertificateAlias())) {
                log.error(getText("secureFTPBean.duplicateKey"));
                addFieldError("secureFTPBean.certificateAlias", getText("secureFTPBean.duplicateKey"));
            }

            if (StringUtils.contains(secureFTPBean.getCertificateAlias(), "ftps")
                    && StringUtils.isBlank(secureFTPBean.getCertificateFileName())) {
                log.error(getText("secureFTPBean.certificateRequired"));
                addFieldError("secureFTPBean.certificateFileName", getText("secureFTPBean.certificateRequired"));
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

    }*/

    public SecureFTPModel getSecureFTPBean() {
        return secureFTPBean;
    }

    public void setSecureFTPBean(SecureFTPModel secureFTPBean) {
        this.secureFTPBean = secureFTPBean;
    };

}
