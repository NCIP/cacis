package gov.nih.nci.cacis.ip.mirthconnect.ftp;

import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;

import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class SFTPSender {

    @Autowired
    private FTPMapping ftpMapping;
    
    public static void sendDocument(InputStream file, String ftpAddress, String extension) {

    	final FTPInfo ftpInfo = ftpMapping.getFTPInfo(ftpAddress);
    	if (ftpInfo == null) {
    		throw new ApplicationRuntimeException("No server config exists for address: " + ftpAddress);
    	}
	  
    	String sftpURI = "sftp://"+ftpInfo.getUserName()+":"+ftpInfo.getPassword()+"@"+ftpInfo.getSite()+"/"+ftpInfo.getRootDirectory();
    	String fileName = "IHEXIPFTP-" + UUID.randomUUID() + extension;

        StandardFileSystemManager standardFileSystemManager = new StandardFileSystemManager();
    	
    	try {

	    	FileSystemOptions  fileSystemOptions = new FileSystemOptions();
	    	SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fileSystemOptions, "no");
	    	SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fileSystemOptions, true);
	      
	    	standardFileSystemManager.init();
	      
	    	FileObject fileObject = standardFileSystemManager.resolveFile(sftpURI + "/" + fileName, fileSystemOptions);
	      
	    	FileObject localFileObject = standardFileSystemManager.resolveFile(filePath);
	      
	    	fileObject.copyFrom(localFileObject, Selectors.SELECT_SELF);
    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		standardFileSystemManager.close();
    }
  }
}