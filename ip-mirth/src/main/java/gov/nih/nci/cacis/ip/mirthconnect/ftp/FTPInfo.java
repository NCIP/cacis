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

/**
 * Holds information for an FTP site.
 * @author bpickeral
 * @since Sep 27, 2011
 */
public class FTPInfo {
	
	public static final String FTPS = "ftps";
	public static final String SFTP = "sftp";
	
    private String protocol;
    private String site;
    private int port;
    private String userName;
    private String password;
    private String rootDirectory;

    
	/**
     * @return the ftp protocol to be used
     */    
    public String getProtocol() {
		return protocol;
	}

    /**
     * @param protocol the ftp protocol to set
     */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
     * @return the site
     */
    public String getSite() {
        return site;
    }

    /**
     * @param site the site to set
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * @return the rootDirectory
     */
    public String getRootDirectory() {
        return rootDirectory;
    }


    /**
     * @param rootDirectory the rootDirectory to set
     */
    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

}
