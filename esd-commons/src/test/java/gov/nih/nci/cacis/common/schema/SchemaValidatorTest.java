/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.schema;

import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;

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
 * Schema based validation for xml
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-esd-commons-test.xml")
public class SchemaValidatorTest {
    
    @Autowired
    @Qualifier("cacisRequestSASchemaValidator")
    private SchemaValidator validator;
    
    /**
     * checks xsd validation with valid xml
     * @throws IOException - exception thrown if any
     */
    @Test
    public void validateValidXML() throws IOException {
        final String validXmlFile = 
            getClass().getClassLoader().getResource("validXmlForSampleXSD.xml").getFile();
        
        final String xmlString = FileUtils.readFileToString(new File(validXmlFile));
        Assert.assertNotNull(xmlString);
        validator.validate(xmlString);
    }
    
    /**
     * checks xsd validation with invalid xml
     * @throws IOException - exception thrown if any
     */
    @Test(expected = ApplicationRuntimeException.class)
    public void validateInValidXML() throws IOException {
        final String validXmlFile = 
            getClass().getClassLoader().getResource("invalidXmlForSampleXSD.xml").getFile();
        final String xmlString = FileUtils.readFileToString(new File(validXmlFile));
        Assert.assertNotNull(xmlString);
        validator.validate(xmlString);
    }

}
