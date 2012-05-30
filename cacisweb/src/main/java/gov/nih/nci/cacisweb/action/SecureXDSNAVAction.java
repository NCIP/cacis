package gov.nih.nci.cacisweb.action;

import gov.nih.nci.cacisweb.CaCISWebConstants;
import gov.nih.nci.cacisweb.exception.KeystoreInstantiationException;
import gov.nih.nci.cacisweb.exception.PropFileAndKeystoreOutOfSyncException;
import gov.nih.nci.cacisweb.model.SecureXDSNAVModel;
import gov.nih.nci.cacisweb.util.CaCISUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class SecureXDSNAVAction extends ActionSupport {

    private static final Logger log = Logger.getLogger(SecureXDSNAVAction.class);

    private static final long serialVersionUID = 1L;

    private List<SecureXDSNAVModel> secureXDSNAVRecepientList;

    private SecureXDSNAVModel secureXDSNAVBean;

    @Override
    public String input() throws Exception {
        log.debug("input() - START");
        secureXDSNAVRecepientList = new ArrayList<SecureXDSNAVModel>();

        String secureXDSNAVKeystoreLocation = CaCISUtil
                .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_TRUSTSTORE_LOCATION);
        String secureXDSNAVKeystorePassword = CaCISUtil
                .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_TRUSTSTORE_PASSWORD);

        CaCISUtil caCISUtil = new CaCISUtil();
        try {
            caCISUtil
                    .isPropertyFileAndKeystoreInSync(CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_CONFIG_FILE_LOCATION),
                            secureXDSNAVKeystoreLocation, CaCISWebConstants.COM_KEYSTORE_TYPE_JKS,
                            secureXDSNAVKeystorePassword);
        } catch (PropFileAndKeystoreOutOfSyncException e) {
            log.error(e.getMessage());
            addActionError(e.getMessage());
        }

        try {
            KeyStore keystore = caCISUtil.getKeystore(secureXDSNAVKeystoreLocation,
                    CaCISWebConstants.COM_KEYSTORE_TYPE_JKS, secureXDSNAVKeystorePassword);
            // List the aliases
            Enumeration<String> enumeration = keystore.aliases();
            while (enumeration.hasMoreElements()) {
                String alias = (String) enumeration.nextElement();
                X509Certificate x509Certificate = (X509Certificate) keystore.getCertificate(alias);
                SecureXDSNAVModel secureXDSNAVModel = new SecureXDSNAVModel();
                secureXDSNAVModel.setCertificateAlias(alias);
                secureXDSNAVModel.setCertificateDN(x509Certificate.getSubjectDN().toString());
                secureXDSNAVRecepientList.add(secureXDSNAVModel);
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
        String secureXDSNAVKeystoreLocation = CaCISUtil
                .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_TRUSTSTORE_LOCATION);
        try {
            CaCISUtil caCISUtil = new CaCISUtil();
            KeyStore keystore = caCISUtil.getKeystore(secureXDSNAVKeystoreLocation,
                    CaCISWebConstants.COM_KEYSTORE_TYPE_JKS, CaCISUtil
                            .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_TRUSTSTORE_PASSWORD));
            caCISUtil.releaseKeystore();
            // Delete the certificate
            keystore.deleteEntry(secureXDSNAVBean.getCertificateAlias());

            // Save the new keystore contents
            FileOutputStream out = new FileOutputStream(new File(secureXDSNAVKeystoreLocation));
            keystore.store(out, CaCISUtil.getProperty(
                    CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_TRUSTSTORE_PASSWORD).toCharArray());
            out.close();

            // delete the entry from XDSNAV configuration properties file
            PropertiesConfiguration config = new PropertiesConfiguration(CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_SECXDSNAV_RECEPIENT_CONFIG_FILE_LOCATION));
            config.clearProperty(secureXDSNAVBean.getCertificateAlias());
            config.save();
        } catch (KeystoreInstantiationException kie) {
            log.error(kie.getMessage());
            addActionError(getText("exception.keystoreInstantiation"));
            return ERROR;
        }
        addActionMessage(getText("secureXDSNAVBean.deleteCertificateSuccessful"));
        log.debug("delete() - END");
        return SUCCESS;
    }

    public List<SecureXDSNAVModel> getSecureXDSNAVRecepientList() {
        return secureXDSNAVRecepientList;
    }

    public void setSecureXDSNAVRecepientList(List<SecureXDSNAVModel> secureXDSNAVRecepientList) {
        this.secureXDSNAVRecepientList = secureXDSNAVRecepientList;
    }

    public SecureXDSNAVModel getSecureXDSNAVBean() {
        return secureXDSNAVBean;
    }

    public void setSecureXDSNAVBean(SecureXDSNAVModel secureXDSNAVBean) {
        this.secureXDSNAVBean = secureXDSNAVBean;
    };

}
