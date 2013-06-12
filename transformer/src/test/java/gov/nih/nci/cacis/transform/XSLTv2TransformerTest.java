/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.transform;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
/**
 * XSLT transformer test for xslv2
 * @author vinodh.rc
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-transformer.xml")
public class XSLTv2TransformerTest {

    @Autowired
    private XmlToRdfTransformer transform;

    private InputStream sampleMessageIS;

    @BeforeClass
    public static void setupEnv() throws URISyntaxException {
        System.setProperty("cacis-pco.transformer.xml2rdf.xsl", "sampleXSLv2.xsl");
        final File smplxsl = 
            new File(XSLTv2TransformerTest.class.getClassLoader().getResource("xsl2/sampleXSLv2.xsl").toURI());
        System.setProperty("cacis-pco.transformer.xsl.baseClassPath", smplxsl.getParent()+"/");
    }

    @Before
    public void init() throws URISyntaxException, IOException {

        sampleMessageIS = FileUtils.openInputStream(new File(getClass().getClassLoader().getResource(
                "xsl2/sampleForXSLv2.xml").toURI()));
    }


    @Test
    public void transformStream() throws XMLStreamException, TransformerException, URISyntaxException, IOException,
            SAXException, ParserConfigurationException, XPathExpressionException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        final Map<String,String> params = new HashMap<String,String>();
        params.put("BaseURI", "http://yadda.com/someUUID");
        
        transform.transform(params, sampleMessageIS, os);
        assertNotNull(os);
        assertTrue(os.size() > 0);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(new ByteArrayInputStream(os.toByteArray()));
        assertNotNull(doc);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression expr = xpath.compile("/world/country[1]/city[1]");

        assertEquals("Tokyo", expr.evaluate(doc));

    }

}
