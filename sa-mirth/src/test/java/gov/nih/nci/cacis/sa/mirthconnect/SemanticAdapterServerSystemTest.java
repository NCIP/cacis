/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.sa.mirthconnect;

import gov.nih.nci.cacis.sa.AcceptSourcePortType;
import gov.nih.nci.cacis.sa.CaCISRequest;

import java.io.File;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.testutil.common.AbstractBusClientServerTestBase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class SemanticAdapterServerSystemTest extends AbstractBusClientServerTestBase {

    private AcceptSourcePortType semanticAdapter;

    @BeforeClass
    public static void startServers() {
        assertTrue("server did not launch correctly", launchServer(SemanticAdapterServer.class,
                true));
    }

    /**
     * Setup test instance objects
     */
    @Before
    public void setup() {
        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(AcceptSourcePortType.class);
        // specify the URL. We are using the in memory test container
        factory.setAddress(SemanticAdapterServer.ADDRESS);


        this.semanticAdapter = (AcceptSourcePortType) factory.create();
    }


    @Test
    public void invoke() throws Exception {

        InputStream sampleMessageIS = FileUtils.openInputStream(new File(getClass().getClassLoader()
                .getResource("SARequestSample.xml").toURI()));

        JAXBContext jc = JAXBContext.newInstance(CaCISRequest.class);
        final CaCISRequest request = (CaCISRequest) jc.createUnmarshaller().unmarshal(sampleMessageIS);
        semanticAdapter.acceptSource(request);


    }
}
