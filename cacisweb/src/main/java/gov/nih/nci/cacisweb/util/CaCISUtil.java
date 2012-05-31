package gov.nih.nci.cacisweb.util;

import gov.nih.nci.cacisweb.CaCISWebConstants;
import gov.nih.nci.cacisweb.exception.CaCISWebException;
import gov.nih.nci.cacisweb.exception.KeystoreInstantiationException;
import gov.nih.nci.cacisweb.exception.PropFileAndKeystoreOutOfSyncException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author Tim Stone
 */
public class CaCISUtil {

    private static final Logger log = Logger.getLogger(CaCISUtil.class.getName());

    /**
	 * 
	 */
    private static long lastModified;

    /**
	 * 
	 */
    private static Properties properties;

    private FileInputStream certificateFileStream;

    /**
     * 
     * @param property
     * @return
     * @throws PropertyFileLoadException
     */
    public static String getProperty(String property) throws CaCISWebException {
        return getProperty(property, null);
    }

    /**
     * 
     * @param property
     * @param defaultValue
     * @return
     * @throws PropertyFileLoadException
     */
public static String getProperty(String property, String defaultValue) throws CaCISWebException {
        
        URL propsUrl = CaCISUtil.class.getClassLoader().getResource(CaCISWebConstants.COM_PROPERTIES_FILE_LOCATION);
        File propertiesFile = new File(propsUrl.getPath());

        if (properties == null || propertiesFile.lastModified() > lastModified) {
            properties = new Properties();

            try {
                properties.load(propsUrl.openStream());
            } catch (FileNotFoundException ex) {
                throw new CaCISWebException(String.format("The properties file %s does not exist or is not readable",
                        propertiesFile.getAbsolutePath()), ex);
            } catch (IOException ex) {
                throw new CaCISWebException(String.format(
                        "An error was encountered while attempting to read the properties file %s", propertiesFile
                                .getAbsolutePath()), ex);
            }
        }

        log.debug(property + " = " + properties.getProperty(property, defaultValue));

        return properties.getProperty(property, defaultValue);
    }

    /**
     * Return the string equivalent of the stack trace
     * 
     * @param arg0
     * @return
     */
    public static String getStackTrace(Throwable arg0) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        arg0.printStackTrace(printWriter);
        return result.toString();
    }

    /**
     * Loads the specific keystore using the location and the password supplied
     * 
     * @param keystoreLocation
     * @param keyStoreType
     * @param keystorePassword
     * @return
     * @throws KeystoreInstantiationException
     */
    public KeyStore getKeystore(String keystoreLocation, String keyStoreType, String keystorePassword)
            throws KeystoreInstantiationException {
        try {
            File file = new File(keystoreLocation);
            certificateFileStream = new FileInputStream(file);
            KeyStore keystore = KeyStore.getInstance(keyStoreType);
            keystore.load(certificateFileStream, keystorePassword.toCharArray());
            return keystore;
        } catch (FileNotFoundException e) {
            log.error(getStackTrace(e));
            throw new KeystoreInstantiationException(e);
        } catch (KeyStoreException e) {
            log.error(getStackTrace(e));
            throw new KeystoreInstantiationException(e);
        } catch (NoSuchAlgorithmException e) {
            log.error(getStackTrace(e));
            throw new KeystoreInstantiationException(e);
        } catch (CertificateException e) {
            log.error(getStackTrace(e));
            throw new KeystoreInstantiationException(e);
        } catch (IOException e) {
            log.error(getStackTrace(e));
            throw new KeystoreInstantiationException(e);
        }
    }

    public void releaseKeystore() throws KeystoreInstantiationException {
        try {
            certificateFileStream.close();
        } catch (IOException e) {
            log.error(getStackTrace(e));
            throw new KeystoreInstantiationException(e);
        }
    }

    /**
     * 
    	 * @param propertyFileLocation
    	 * @param keyStoreLocation
    	 * @param keyStoreType
    	 * @param keyStorePassword
    	 * @return
    	 * @throws PropFileAndKeystoreOutOfSyncException
     */
    public boolean isPropertyFileAndKeystoreInSync(String propertyFileLocation, String keyStoreLocation,
            String keyStoreType, String keyStorePassword) throws PropFileAndKeystoreOutOfSyncException {
        boolean isInSync = false;
        try {
            Properties configFile = new Properties();
            InputStream is = new FileInputStream(propertyFileLocation);
            configFile.load(is);
            is.close();
            KeyStore keystore = getKeystore(keyStoreLocation, keyStoreType, keyStorePassword);
            Enumeration<String> keystoreEnumeration = keystore.aliases();
            while (keystoreEnumeration.hasMoreElements()) {
                String alias = (String) keystoreEnumeration.nextElement();
                if (!configFile.containsKey(alias)) {
                    isInSync = false;
                    throw new PropFileAndKeystoreOutOfSyncException(String.format(
                            "Alias [%s] entry in key/trust store [%s] does not exist in the properties file [%s]. "
                                    + "It is recommended that you manually correct this before proceeding", alias,
                            keyStoreLocation, propertyFileLocation));
                }
            }

            Enumeration<Object> propertyEnumeration = configFile.keys();
            while (propertyEnumeration.hasMoreElements()) {
                String property = (String) propertyEnumeration.nextElement();
                if (!keystore.containsAlias(property)) {
                    isInSync = false;
                    throw new PropFileAndKeystoreOutOfSyncException(String.format(
                            "Property [%s] entry in properties file [%s] does not exist in the key/trust store [%s]. "
                                    + "It is recommended that you manually correct this before proceeding", property,
                            propertyFileLocation, keyStoreLocation));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new PropFileAndKeystoreOutOfSyncException(String.format("File [%] not found", propertyFileLocation));
        } catch (IOException e) {
            e.printStackTrace();
            throw new PropFileAndKeystoreOutOfSyncException(String.format("File [%] cannot be loaded.",
                    propertyFileLocation));
        } catch (KeyStoreException e) {
            e.printStackTrace(); 
            throw new PropFileAndKeystoreOutOfSyncException(String.format(
                    "Error verifying the contents inside the key/trust store [%].", keyStoreLocation));
        } catch (KeystoreInstantiationException e) {
            e.printStackTrace();
            throw new PropFileAndKeystoreOutOfSyncException(e.getMessage());
        }
        return isInSync;
    }
}
