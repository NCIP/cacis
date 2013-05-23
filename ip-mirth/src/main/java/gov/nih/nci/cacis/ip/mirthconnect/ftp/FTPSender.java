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
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author bpickeral
 * @since Sep 14, 2011
 */
public class FTPSender {

    @Autowired
    private FTPSSender ftpsSender;

    @Autowired
    private SFTPSender sftpSender;

    @Autowired
    private FTPMapping ftpMapping;

    /**
     * Sends Document to FTPS Server.
     * 
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
        try {
            final FTPInfo ftpInfo = ftpMapping.getFTPInfo(ftpAddress);
            if (ftpInfo == null) {
                throw new ApplicationRuntimeException("No server config exists for address[ " + ftpAddress + " ]");
            }

            if (ftpInfo.getProtocol().equalsIgnoreCase(FTPInfo.FTPS)) {
                ftpsSender.sendDocument(file, ftpAddress, extension);
            } else if (ftpInfo.getProtocol().equalsIgnoreCase(FTPInfo.SFTP)) {
                sftpSender.sendDocument(file, ftpAddress, extension);
            } else {
                throw new IOException(
                        "The FTP protocol entry in the configuration file has to be 'ftps' or 'sftp'. It is currently configured as [ "
                                + ftpInfo.getProtocol() + " ]");
            }
        } catch (Exception e) {
            throw new ApplicationRuntimeException("Error occured during FTP. " + e.getMessage());
        }
    }

}
