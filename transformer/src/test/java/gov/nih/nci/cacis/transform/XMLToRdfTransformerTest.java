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

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author kherm manav.kher@semanticbits.com
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-transformer.xml")
@DirtiesContext
public class XMLToRdfTransformerTest {

    @Autowired
    private XmlToRdfTransformer transform;

    private InputStream sampleMessageIS, sampleTrimIS;

    @Before
    public void init() throws URISyntaxException, IOException {
        sampleMessageIS = FileUtils.openInputStream(new File(getClass().getClassLoader().getResource(
                "caCISRequestSample3.xml").toURI()));

        sampleTrimIS = FileUtils.openInputStream(new File(getClass().getClassLoader().getResource(
                "sample_transcend_trim.xml").toURI()));
    }


    @Test
    public void transformStream() throws XMLStreamException, TransformerException, URISyntaxException, IOException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        final Map<String,String> params = new HashMap<String,String>();
        params.put("BaseURI", "http://yadda.com/someUUID");
        
        transform.transform(params, sampleMessageIS, os);
        assertNotNull(os);
        assertTrue(os.size() > 0);

        os.reset();
        transform.transform(params, sampleTrimIS, os);
        assertNotNull(os);
        assertTrue(os.size() > 0);
    }

}
