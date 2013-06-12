/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.mirth;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * Test class for the XSLForMirthFormatter
 * 
 * @author vinodh.rc@semanticbits.com
 * 
 */
public class XSLForMirthFormatterTest {

    /**
     * Tests the xsl formatter
     * @throws IOException io error thrown, if any
     * @throws URISyntaxException error thrown, if any
     */
    @Test
    public void checkFormatter() throws IOException, URISyntaxException {
        final String origXslFile = getClass().getClassLoader().getResource("sample.xsl").getFile();
        final File xslFile = new File(origXslFile);
        final String xslFileNm = xslFile.getName();
        final String outputDir = xslFile.getParent() + "/formatted/";
        final String[] args = { outputDir, origXslFile };
        XSLForMirthFormatter.main(args);
        
        final File actualFrmtdXsl = new File(outputDir + xslFileNm);
        assertTrue(actualFrmtdXsl.exists());
        
        final String actualFrmtdXslContent = FileUtils.readFileToString(actualFrmtdXsl);
        assertNotNull(actualFrmtdXslContent);
        
        final String expectedFrmtdXsl = getClass().getClassLoader().getResource("sample.formatted").getFile();
        final String expectedFrmtdXslContent = FileUtils.readFileToString(new File(expectedFrmtdXsl));
        assertNotNull(expectedFrmtdXslContent);
        
        assertEquals(expectedFrmtdXslContent, actualFrmtdXslContent);
        
        
    }
}
