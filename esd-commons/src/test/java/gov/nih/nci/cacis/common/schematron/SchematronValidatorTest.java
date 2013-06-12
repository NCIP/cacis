/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.schematron;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Schematron validation for xml
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-esd-commons-test.xml")
public class SchematronValidatorTest {

    @Autowired
    @Qualifier("cacisRequestSASourceDataSchematronValidator")
    private SchematronValidator validator;
    
    /**
     * checks xsd validation with valid xml
     * @throws IOException - exception thrown if any
     */
    @Test
    public void validateValidXML() throws IOException {        
        final String validXmlFile = getClass().getClassLoader().getResource("schematron-test.xml").getFile();
        final String xmlString = FileUtils.readFileToString(new File(validXmlFile));
        Assert.assertNotNull(xmlString);
        final String output = validator.validate(xmlString);
        Assert.assertNotNull(output);
        Assert.assertEquals("", output);
    }
    
    /**
     * checks xsd validation with invalid xml
     * @throws IOException - exception thrown if any
     */
    @Test
    public void validateInValidXML() throws IOException {
        final String validXmlFile = getClass().getClassLoader().getResource("schematron-failure-test.xml").getFile();
        final String xmlString = FileUtils.readFileToString(new File(validXmlFile));
        Assert.assertNotNull(xmlString);
        final String output = validator.validate(xmlString);
        FileUtils.writeStringToFile(new File("output.txt"), output);
        Assert.assertNotNull(output);
        final String expOutputFile = 
            getClass().getClassLoader().getResource("schematron-failure-test-output.txt").getFile();        
        final String expOutput = FileUtils.readFileToString(new File(expOutputFile));
        Assert.assertEquals(expOutput, output);
    }

}
