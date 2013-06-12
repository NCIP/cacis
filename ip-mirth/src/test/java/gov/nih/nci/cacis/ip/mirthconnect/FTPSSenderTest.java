/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import gov.nih.nci.cacis.ip.mirthconnect.ftp.FTPInfo;
import gov.nih.nci.cacis.ip.mirthconnect.ftp.FTPMapping;
import gov.nih.nci.cacis.ip.mirthconnect.ftp.FTPSSender;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.ftpserver.FtpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author bpickeral
 * @since Sep 14, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-ip-mirth-test.xml")
public class FTPSSenderTest {
    private final String EXTENSION = ".xml";

    @Autowired
    private FTPSSender sender;

    @Autowired
    private FtpServer server;

    @Autowired
    private FTPMapping ftpMapping;

    private static final String TEST_SERVER = "localhost";
    private FTPInfo ftpInfo;

    @Before
    public void before() throws Exception {
        server.start();
        ftpInfo = ftpMapping.getFTPInfo(TEST_SERVER);
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void sendDocument() throws Exception {
        final FTPSClient ftpsClient = sender.getFtpsClient();
        int numFiles = getNumFiles();
        final File inputFile = new File(Thread.currentThread().getContextClassLoader()
                .getResource("Sample_SFTP_File.xml").toURI());
        final InputStream inputStream = new FileInputStream(inputFile);
        sender.sendDocument(inputStream, TEST_SERVER, EXTENSION);

        assertTrue(FTPReply.isPositiveCompletion(ftpsClient.getReplyCode()));

        assertEquals(numFiles + 1, getNumFiles());
    }

    /**
     * To test, make sure you create the sub directory under /tmp locally, change the sub-directory in
     *  ftoConfig.properties to /test/test2 and remove the @Ignore on this test.  Please revert prior to commit.
     * @throws Exception on error
     */
    @Test
    @Ignore
    public void sendDocumentToSubDirectory() throws Exception {
        final FTPSClient ftpsClient = sender.getFtpsClient();
        int numFiles = getNumFiles();
        final File inputFile = new File(Thread.currentThread().getContextClassLoader()
                .getResource("Sample_SFTP_File.xml").toURI());
        final InputStream inputStream = new FileInputStream(inputFile);
        sender.sendDocument(inputStream, TEST_SERVER, EXTENSION);

        assertTrue(FTPReply.isPositiveCompletion(ftpsClient.getReplyCode()));

        assertEquals(numFiles + 1, getNumFiles());
    }

    @Test (expected = ApplicationRuntimeException.class)
    public void sendException() throws Exception {
        final File inputFile = new File(Thread.currentThread().getContextClassLoader()
                .getResource("Sample_SFTP_File.xml").toURI());
        final InputStream inputStream = new FileInputStream(inputFile);
        sender.sendDocument(inputStream, "no-such-address", EXTENSION);
    }

    private int getNumFiles() throws Exception {
        sender.connect(ftpInfo);
        final FTPSClient ftpsClient = sender.getFtpsClient();
        int numFiles = ftpsClient.listFiles().length;
        sender.disconnect();
        return numFiles;
    }
}
