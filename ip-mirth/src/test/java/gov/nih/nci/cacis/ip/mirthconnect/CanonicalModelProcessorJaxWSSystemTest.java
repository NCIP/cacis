/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect;

import com.mirth.connect.connectors.ws.WebServiceMessageReceiver;
import gov.nih.nci.cacis.AcceptCanonicalFault;
import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.CanonicalModelProcessorPortType;
import gov.nih.nci.cacis.common.systest.AbstractJaxWsTest;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.ws.soap.SOAPFaultException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class CanonicalModelProcessorJaxWSSystemTest extends AbstractJaxWsTest {

    /**
     * Constant value for the endpoint address
     */
    protected static final String ADDRESS = "http://localhost:18081/services/Temp?wsdl";
    @Mock
    WebServiceMessageReceiver webServiceMessageReceiver;

    private JaxWsProxyFactoryBean factory;
    private EndpointImpl ep;

    /**
     * EndpointImpl
     *
     * @return Endpoint
     */
    @Override
    protected EndpointImpl getEndpoint() {
        return this.ep;
    }


    /**
     * Setups up namespace and Endpoint
     *
     * @throws Exception - exception thrown
     */
    @Before
    public void setup() throws Exception { // NOPMD - setUpBus throws
        MockitoAnnotations.initMocks(this);

        final CanonicalModelProcessor service = new CanonicalModelProcessor(webServiceMessageReceiver);
        ep = new EndpointImpl(getBus(), service);
        ep.publish(ADDRESS);

        factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(CanonicalModelProcessorPortType.class);
        // specify the URL. We are using the in memory test container
        factory.setAddress(ADDRESS);

    }


    @Test
    public void invoke() throws Exception {
        when(webServiceMessageReceiver.processData(anyString())).thenReturn("");

        final CanonicalModelProcessorPortType client = (CanonicalModelProcessorPortType) factory.create();
        final CaCISRequest request = new CaCISRequest();
        request.getClinicalDocument().add(CanonicalModelProcessorTest.dummyClinicalDocument());


        client.acceptCanonical(request);
    }


    @Test(expected = SOAPFaultException.class)
    public void exception() throws Exception {
        when(webServiceMessageReceiver.processData(anyString())).thenThrow(new RuntimeException());

        final CanonicalModelProcessorPortType client = (CanonicalModelProcessorPortType) factory.create();
        final CaCISRequest request = new CaCISRequest();
        request.getClinicalDocument().add(CanonicalModelProcessorTest.dummyClinicalDocument());


        client.acceptCanonical(request);
    }

}
