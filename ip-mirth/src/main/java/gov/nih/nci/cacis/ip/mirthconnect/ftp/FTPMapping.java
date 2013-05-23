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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * Maps ftp site URLs to FTPInfo objects containing information about the ftp site.
 * @author bpickeral
 * @since Sep 27, 2011
 */
public final class FTPMapping {

    private static Map<String, FTPInfo> ftpInfoMap = null;

    private  FTPMapping() {
        // Empty
    }

    /**
     * Constructor.
     * @param ftpMappingFile name of mapping file
     * @throws IOException on I/O error
     */
    public FTPMapping(String ftpMappingFile) throws IOException {
        setupFTPInfoMap(ftpMappingFile);
    }

    /**
     * Retrieves the FTPInfo object containing information for a particular site.
     * @param url of the ftp site
     * @return FTPInfo object
     * @throws IOException on I/O error
     */
    public FTPInfo getFTPInfo(String url) throws IOException {
        return ftpInfoMap.get(url);
    }

    private void setupFTPInfoMap(String ftpMappingFile) throws IOException {
        final File mappingFile = new File(ftpMappingFile);
        
        InputStream is = null;
//        FileInputStream fis = null;
//        BufferedReader br = null;
        try {
            Properties configFile = new Properties();
            is = new FileInputStream(ftpMappingFile);
            configFile.load(is);
            
            ftpInfoMap = new HashMap<String, FTPInfo>();
            Enumeration<Object> enumeration = configFile.keys();
            while (enumeration.hasMoreElements()) {
                addFTPSiteToMap((String) enumeration.nextElement());
            }
            
//            fis = new FileInputStream(mappingFile);
//            br = new BufferedReader(new InputStreamReader(fis));
//
//            ftpInfoMap = new HashMap<String, FTPInfo>();
//            String currLine = br.readLine();
//
//            while (currLine != null) {
//                addFTPSiteToMap(currLine);
//                currLine = br.readLine();
//            }
        } finally {
            if (is != null) {
                is.close();
            }
//            if (br != null) {
//                br.close();
//            }
//            if (fis != null) {
//                fis.close();
//            }
        }
    }

    private void addFTPSiteToMap(String ftpInfoStr) {
        final String[] ftpParams = StringUtils.split(ftpInfoStr, ',');
        if (ftpParams.length != 6) {
            throw new ApplicationRuntimeException(
                    "FTP site properties must be in the form of '<site>,<port>,<user>,<password>,<directory>'");
        }
        final FTPInfo ftpInfo = new FTPInfo();
        ftpInfo.setProtocol(ftpParams[0]);
        ftpInfo.setSite(ftpParams[1]);
        ftpInfo.setPort(Integer.valueOf(ftpParams[2]));
        ftpInfo.setUserName(ftpParams[3]);
        ftpInfo.setPassword(ftpParams[4]);
        ftpInfo.setRootDirectory(ftpParams[5]);

        ftpInfoMap.put(ftpInfo.getSite(), ftpInfo);
    }

}
