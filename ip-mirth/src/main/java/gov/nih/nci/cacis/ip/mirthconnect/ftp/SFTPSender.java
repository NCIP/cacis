package gov.nih.nci.cacis.ip.mirthconnect.ftp;

import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import gov.nih.nci.cacis.sa.mirthconnect.SemanticAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class SFTPSender {

    private static final Logger LOG = Logger.getLogger(SFTPSender.class.getName());

    @Autowired
    private FTPMapping ftpMapping;

    @Value("${sftp.file.directory}")
    private String sftpFileDirectory;

    public void sendDocument(InputStream file, String ftpAddress, String extension) {

        LOG.error("SFTP FILE DIRECTORY: " + sftpFileDirectory);
        StandardFileSystemManager standardFileSystemManager = new StandardFileSystemManager();
        try {
            final FTPInfo ftpInfo = ftpMapping.getFTPInfo(ftpAddress);
            if (ftpInfo == null) {
                throw new ApplicationRuntimeException("No server config exists for address: " + ftpAddress);
            }

            String sftpURI = "sftp://" + ftpInfo.getUserName() + ":" + ftpInfo.getPassword() + "@" + ftpInfo.getSite()
                    + "/" + ftpInfo.getRootDirectory();
            String fileName = "IHEXIPFTP-" + UUID.randomUUID() + extension;

            

            FileSystemOptions fileSystemOptions = new FileSystemOptions();
            SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fileSystemOptions, "no");
            SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fileSystemOptions, true);

            standardFileSystemManager.init();

            FileObject fileObject = standardFileSystemManager.resolveFile(sftpURI + "/" + fileName, fileSystemOptions);

            long timestamp = new Date().getTime();
            OutputStream out = new FileOutputStream(new File(sftpFileDirectory + timestamp + ".xml"));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = file.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            file.close();
            out.flush();
            out.close();

            FileObject localFileObject = standardFileSystemManager.resolveFile(sftpFileDirectory + timestamp + ".xml");

            fileObject.copyFrom(localFileObject, Selectors.SELECT_SELF);

            // TODO uncomment the below line after testing
            // FileUtils.forceDelete(new File(sftpFileDirectory+timestamp+".xml"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            standardFileSystemManager.close();
        }
    }
}
