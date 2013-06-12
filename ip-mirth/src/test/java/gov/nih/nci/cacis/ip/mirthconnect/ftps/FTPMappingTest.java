/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect.ftps;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import gov.nih.nci.cacis.ip.mirthconnect.ftp.FTPInfo;
import gov.nih.nci.cacis.ip.mirthconnect.ftp.FTPMapping;

import org.junit.Test;

/**
 * @author bpickeral
 * @since Sep 28, 2011
 */
public class FTPMappingTest {

    private static final String MAPPING_FILE = "ftpConfig.properties";
    private static final String MAPPING_FILE_W_ERROR = "ftpConfigTestFile_Error.properties";

    @Test
    public void getFTPInfo() throws Exception {
        final FTPMapping ftpMapping = new FTPMapping("C:/Users/ajay/.cacis/cacis-xds/ftpConfig.properties");
        final FTPInfo ftpInfo = ftpMapping.getFTPInfo("cacis-dev.nci.nih.gov");
//        assertEquals("cacis-dev.nci.nih.gov", ftpInfo.getSite());
        assertEquals(22, ftpInfo.getPort());
//        assertEquals("user", ftpInfo.getUserName());
//        assertEquals("password", ftpInfo.getPassword());
//        assertEquals("path/to/directory", ftpInfo.getRootDirectory());
    }

//    @Test (expected = ApplicationRuntimeException.class)
//    public void error() throws Exception {
//        new FTPMapping(getClass().getClassLoader().getResource(MAPPING_FILE_W_ERROR).getFile());
//    }

}
