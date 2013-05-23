/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.action;

import gov.nih.nci.cacisweb.CaCISWebConstants;
import gov.nih.nci.cacisweb.exception.KeystoreInstantiationException;
import gov.nih.nci.cacisweb.model.SecureEmailModel;
import gov.nih.nci.cacisweb.util.CaCISUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class SecureEmailAction extends ActionSupport {

    private static final Logger log = Logger.getLogger(SecureEmailAction.class);

    private static final long serialVersionUID = 1L;

    private List<SecureEmailModel> secureEmailRecepientList;

    private SecureEmailModel secureEmailBean;

    @Override
    public String input() throws Exception {
        log.debug("input() - START");
        secureEmailRecepientList = new ArrayList<SecureEmailModel>();
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
            // List the aliases
            Enumeration<String> enumeration = keystore.aliases();
            while (enumeration.hasMoreElements()) {
                String alias = (String) enumeration.nextElement();
                X509Certificate x509Certificate = (X509Certificate) keystore.getCertificate(alias);
                SecureEmailModel secureEmailModel = new SecureEmailModel();
                secureEmailModel.setCertificateAlias(alias);
                secureEmailModel.setCertificateDN(x509Certificate.getSubjectDN().toString());
                secureEmailRecepientList.add(secureEmailModel);
                log.debug("Alias: " + alias + " DN: " + x509Certificate.getSubjectDN().getName());
            }
            caCISUtil.releaseKeystore();
        } catch (KeystoreInstantiationException kie) {
            log.error(kie.getMessage());
            addActionError(getText("exception.keystoreInstantiation"));
            return ERROR;
        }
        log.debug("input() - END");
        return INPUT;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        log.debug("delete() - START");
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
            // Delete the certificate
            keystore.deleteEntry(secureEmailBean.getCertificateAlias());

            // Save the new keystore contents
            FileOutputStream out = new FileOutputStream(new File(CaCISUtil.getPropertyFromPropertiesFile(
                    secureEmailPropertyFileLocation, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_TRUSTSTORE_LOCATION_PROP_NAME))));
            keystore.store(out, CaCISUtil.getPropertyFromPropertiesFile(secureEmailPropertyFileLocation,
                    CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECEMAIL_TRUSTSTORE_PASSWORD_PROP_NAME))
                    .toCharArray());
            out.close();
        } catch (KeystoreInstantiationException kie) {
            log.error(kie.getMessage());
            addActionError(getText("exception.keystoreInstantiation"));
            return ERROR;
        }
        addActionMessage(getText("secureEmailBean.deleteCertificateSuccessful"));
        log.debug("delete() - END");
        return SUCCESS;
    }

    public List<SecureEmailModel> getSecureEmailRecepientList() {
        return secureEmailRecepientList;
    }

    public void setSecureEmailRecepientList(List<SecureEmailModel> secureEmailRecepientList) {
        this.secureEmailRecepientList = secureEmailRecepientList;
    }

    public SecureEmailModel getSecureEmailBean() {
        return secureEmailBean;
    }

    public void setSecureEmailBean(SecureEmailModel secureEmailBean) {
        this.secureEmailBean = secureEmailBean;
    };

}
