/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect.ftp;

import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author bpickeral
 * @since Sep 14, 2011
 */
public class FTPSSender {

    @Autowired
    private FTPSClient ftpsClient;

    @Autowired
    private FTPMapping ftpMapping;

    /**
     * Sends Document to FTPS Server.
     * @param file Input Stream.
     * @param ftpAddress the ftp address in which to store the file.
     * @param extension File extension
     * @throws IOException on I/O error
     * @throws NoSuchProviderException on Provider error
     * @throws KeyStoreException on Keystore error
     * @throws NoSuchAlgorithmException on algorithm error
     * @throws CertificateException on certificate error
     * @throws UnrecoverableKeyException if key is not recoverable
     */
    public void sendDocument(InputStream file, String ftpAddress, String extension) throws IOException,
            NoSuchProviderException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
            UnrecoverableKeyException {
        final FTPInfo ftpInfo = ftpMapping.getFTPInfo(ftpAddress);
        if (ftpInfo == null) {
            throw new ApplicationRuntimeException("No server config exists for address [ " + ftpAddress +" ]");
        }

        connect(ftpInfo);

        ftpsClient.setFileTransferMode(FTPSClient.BLOCK_TRANSFER_MODE);
        ftpsClient.storeFile("IHEXIPFTP-" + UUID.randomUUID() + extension, file);

        disconnect();
    }

    /**
     * Connects to the FTPS server.
     * @param ftpInfo FTPInfo Object containing FTP connection information.
     * @throws SocketException on underlying protocol error
     * @throws IOException on I/O error
     */
    public void connect(FTPInfo ftpInfo) throws SocketException, IOException {
        ftpsClient.connect(ftpInfo.getSite(), ftpInfo.getPort());
        ftpsClient.login(ftpInfo.getUserName(), ftpInfo.getPassword());
        ftpsClient.changeWorkingDirectory(ftpInfo.getRootDirectory());
    }

    /**
     * Disconnects from the server.
     * @throws SocketException on underlying protocol error
     * @throws IOException on I/O error
     */
    public void disconnect() throws SocketException, IOException {
        ftpsClient.disconnect();
    }


    /**
     * @return the ftpsClient
     */
    public FTPSClient getFtpsClient() {
        return ftpsClient;
    }

}
