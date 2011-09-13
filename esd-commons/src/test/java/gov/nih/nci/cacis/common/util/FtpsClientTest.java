package gov.nih.nci.cacis.common.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class FtpsClientTest {

    private final int ftpPort = 2221;

    private static final String keyStoreFilename = "src/test/resources/ftpkeystore.jks";
    private static final String keyStorePassword = "changeit";

    private FtpServer server = null;

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
        server = serverFactory.createServer();

        server.start();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    @Ignore
    public void connect() throws IOException, NoSuchProviderException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException {
        System.out.println("Running client");
        FTPSClient ftpsClient = new FTPSClient("TLS", true);

        ftpsClient.connect("localhost", ftpPort);
        ftpsClient.login("admin", "admin");

        assertNotNull(ftpsClient.listFiles());
        assertTrue(FTPReply.isPositiveCompletion(ftpsClient.getReplyCode()));
        ftpsClient.disconnect();
    }

}
