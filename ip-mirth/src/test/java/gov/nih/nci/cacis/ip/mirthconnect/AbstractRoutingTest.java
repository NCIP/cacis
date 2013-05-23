/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.cxf.test.TestUtilities;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Base Test class for testing channel routing.
 * @author bpickeral
 * @since Aug 15, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-ip-mirth-test.xml")
public  abstract class AbstractRoutingTest {

    @Value("${cacis.mc.temp.dir}")
    protected String inputDir;

    protected static final int SLEEP_TIME = 15000;

    protected static TestUtilities testUtilities;

    /**
     * Initialize
     */
    @Before
    public void setUp() {
        testUtilities = new TestUtilities(AbstractRoutingTest.class);
        testUtilities.addDefaultNamespaces();
        testUtilities.addNamespace("p", "http://cacis.nci.nih.gov");
    }

    /**
     *
     * @param responseFile response file
     * @return root node
     */
    protected Node getRoutingInstructions(final File responseFile) throws Exception { // NOPMD

        final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        final Document doc = docBuilder.parse(responseFile);

        return doc.getDocumentElement();
    }
}