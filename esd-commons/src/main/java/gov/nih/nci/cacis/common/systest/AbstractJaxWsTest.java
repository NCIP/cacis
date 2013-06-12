/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.systest;

import gov.nih.nci.cacis.common.test.TestUtils;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.binding.soap.SoapBindingConstants;
import org.apache.cxf.binding.soap.SoapBindingFactory;
import org.apache.cxf.binding.soap.SoapTransportFactory;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.test.AbstractCXFTest;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.transport.local.LocalTransportFactory;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl11.WSDLManagerImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Copied from CXF. Could not find ivy dependency for this test.
 */
public abstract class AbstractJaxWsTest extends AbstractCXFTest {


  /**
     * reset CXF bus
     */
    @BeforeClass
    public static void checkBus() {
        if (BusFactory.getDefaultBus(false) != null) {
            BusFactory.setDefaultBus(null);
        }
    }

    @Override
    @Before
    public void setUpBus() throws Exception { // NOPMD - setUpBus throws Exception
        super.setUpBus();

        final SoapBindingFactory bindingFactory = new SoapBindingFactory();
        bindingFactory.setBus(bus);
        bus.getExtension(BindingFactoryManager.class)
                .registerBindingFactory("http://schemas.xmlsoap.org/wsdl/soap/", bindingFactory);
        bus.getExtension(BindingFactoryManager.class)
                .registerBindingFactory("http://schemas.xmlsoap.org/wsdl/soap/http", bindingFactory);

        final DestinationFactoryManager dfm = bus.getExtension(DestinationFactoryManager.class);

        final SoapTransportFactory soapDF = new SoapTransportFactory();
        soapDF.setBus(bus);
        dfm.registerDestinationFactory("http://schemas.xmlsoap.org/wsdl/soap/", soapDF);
        dfm.registerDestinationFactory(SoapBindingConstants.SOAP11_BINDING_ID, soapDF);
        dfm.registerDestinationFactory("http://cxf.apache.org/transports/local", soapDF);

        final LocalTransportFactory localTransport = new LocalTransportFactory();
        localTransport.setUriPrefixes(new HashSet<String>(Arrays.asList("http", "local")));
        dfm.registerDestinationFactory("http://schemas.xmlsoap.org/soap/http", localTransport);
        dfm.registerDestinationFactory("http://schemas.xmlsoap.org/wsdl/soap/http", localTransport);
        dfm.registerDestinationFactory("http://cxf.apache.org/bindings/xformat", localTransport);
        dfm.registerDestinationFactory("http://cxf.apache.org/transports/local", localTransport);

        final ConduitInitiatorManager extension = bus.getExtension(ConduitInitiatorManager.class);
        extension.registerConduitInitiator(LocalTransportFactory.TRANSPORT_ID, localTransport);
        extension.registerConduitInitiator("http://schemas.xmlsoap.org/wsdl/soap/", localTransport);
        extension.registerConduitInitiator("http://schemas.xmlsoap.org/soap/http", localTransport);
        extension.registerConduitInitiator(SoapBindingConstants.SOAP11_BINDING_ID, localTransport);

        final WSDLManagerImpl manager = new WSDLManagerImpl();
        manager.setBus(bus);
        bus.setExtension(manager, WSDLManager.class);

        this.bus.getInInterceptors().add(new LoggingInInterceptor());
        this.bus.getOutInterceptors().add(new LoggingInInterceptor());
    }

    /**
     * Invokes the service with a wsdl soap message. 
     *
     * @param messageFile Will lookup SOAP message file on the classpath
     * @return Response response Node
     * @throws Exception exception message
     */
    protected Node invokeSOAPMessage(String messageFile) throws Exception { // NOPMD Exception type
        return invokeWithDocument(loadSoapMessage(messageFile));
    }

    /**
     * Loads the soap message for the given file as DOM document.
     *
     * @param messageFile message file located on the classpath
     * @return Document response w3c Document
     * @throws Exception exception message
     */
    protected Document loadSoapMessage(String messageFile) throws Exception { // NOPMD
        final URL messageFileURL = getClass().getClassLoader().getResource(messageFile);
        return TestUtils.getXMLDoc(messageFileURL);
    }

    /**
     * Invokes the service with a soap message document. 
     *
     * @param soapMsgDoc the JDOM document for the soap message
     * @return Response response Node
     * @throws Exception exception message
     */
    protected Node invokeWithDocument(Document soapMsgDoc) throws Exception { // NOPMD Exception type is
        return invoke(this.getEndpoint().getAddress(), 
                LocalTransportFactory.TRANSPORT_ID, 
                TestUtils.xmlToString(soapMsgDoc).getBytes());
    }
    
    /**
     * Abstract method, implemented by subclasses to return endpoint instance 
     * @return EndpointImpl instance
     */
    protected abstract EndpointImpl getEndpoint();

}
