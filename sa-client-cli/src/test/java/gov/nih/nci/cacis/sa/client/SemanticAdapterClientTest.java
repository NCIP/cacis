/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.sa.client;

import gov.nih.nci.cacis.sa.AcceptSourceFault;
import gov.nih.nci.cacis.sa.mirthconnect.SemanticAdapterServer;

import java.net.URL;

import javax.xml.bind.JAXBException;

import org.apache.cxf.testutil.common.AbstractBusClientServerTestBase;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class SemanticAdapterClientTest extends AbstractBusClientServerTestBase {


    /**
     * set the SA Service URL
     * to dummy service
     */
    @BeforeClass
    public static void startServers() {
        System.setProperty("cacis-pco.sa.service.url", SemanticAdapterServer.ADDRESS);
        assertTrue("server did not launch correctly", launchServer(SemanticAdapterServer.class,
                true));
    }


    /**
     * Test the client. This test
     * passes if the client does not
     * throw an exception as a
     * null response is expected
     *
     * @throws JAXBException     Exception
     * @throws AcceptSourceFault Web service fault
     */
    @Test
    public void invoke() throws JAXBException, AcceptSourceFault {
        final URL sampleFile = getClass().getClassLoader().getResource("sample_request.xml");
        final SemanticAdapterClient client = new SemanticAdapterClient();
        assertNull(client.invoke(sampleFile));
    }

}
