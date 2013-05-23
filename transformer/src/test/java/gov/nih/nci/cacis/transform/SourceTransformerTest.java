/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.transform;

import static junit.framework.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * XSLT transformer test for xslv2
 * @author vinodh.rc
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-transformer.xml")
public class SourceTransformerTest {
    
    private static final Logger LOG = Logger.getLogger(SourceTransformerTest.class.getName());

    @Autowired
    private XmlToRdfTransformer transform;

    private InputStream sampleMessageIS;

    @BeforeClass
    public static void setupEnv() throws URISyntaxException {
        System.setProperty("cacis-pco.transformer.xml2rdf.xsl", "sampleXSLv2.xsl");
        final File smplxsl = 
            new File(SourceTransformerTest.class.getClassLoader().getResource("xsl2/sampleXSLv2.xsl").toURI());
        System.setProperty("cacis-pco.transformer.xsl.baseClassPath", smplxsl.getParent()+"/");
    }

    @Before
    public void init() throws URISyntaxException, IOException {

        sampleMessageIS = FileUtils.openInputStream(new File(getClass().getClassLoader().getResource(
                "xsl2/sampleForXSLv2.xml").toURI()));
    }


    @Test
    public void transformSourceMessage() {
        try {
//            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration("C:/Users/ajay/.cacis/sourcetransforms/cacis-source-system-xslt.properties");
//            String propertyValue = propertiesConfiguration.getString("2.16.840.1.113883.6.56:testExt");
//            assertNotNull(propertyValue);
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File("C:/Users/ajay/.cacis/sourcetransforms/cacis-source-system-xslt.properties")));
            String xsltName = properties.getProperty("2.16.840.1.113883.6.56_testExt");
            LOG.error("XSLT File Name:"+xsltName);
            assertNotNull(xsltName);
//        } catch (ConfigurationException e) {
//            e.printStackTrace();
        } catch (FileNotFoundException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
        } catch (IOException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
        }
    }

}
