package gov.nih.nci.cacis.common.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class FtpsClientTest {

    private final int ftpPort = 2221;
    public static final String keyStoreFilename = "src/test/resources/ftpkeystore.jks";
    public static final String keyStorePassword = "changeit";
    private static final String PROVIDER_TYPE = "JKS";


    @Before
    public void startServer() throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();

        ListenerFactory factory = new ListenerFactory();

// set the port of the listener
        factory.setPort(ftpPort);

// define SSL configuration
        SslConfigurationFactory ssl = new SslConfigurationFactory();
        ssl.setKeystoreFile(new File(keyStoreFilename));
        ssl.setKeystorePassword(keyStorePassword);


// set the SSL configuration for the listener
        factory.setSslConfiguration(ssl.createSslConfiguration());
        factory.setImplicitSsl(true);

// replace the default listener
        serverFactory.addListener("default", factory.createListener());

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File("src/test/resources/ftpusers.properties"));

        serverFactory.setUserManager(userManagerFactory.createUserManager());

// start the server
        FtpServer server = serverFactory.createServer();

        server.start();
    }


    @Test
    public void connect() throws IOException, NoSuchProviderException, KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        System.out.println("Running client");
        FTPSClient ftpsClient = new FTPSClient("TLS", true);

        ftpsClient.connect("localhost", ftpPort);
        ftpsClient.login("admin", "admin");

        assertNotNull(ftpsClient.listFiles());
        assertTrue(FTPReply.isPositiveCompletion(ftpsClient.getReplyCode()));
        ftpsClient.disconnect();
    }


}
