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

import com.mirth.connect.connectors.ws.WebServiceMessageReceiver;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import gov.nih.nci.cacis.AcceptCanonicalFault;
import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.CanonicalModelProcessorPortTypeImpl;

import org.hl7.v3.II;
import org.hl7.v3.POCDMT000040ClinicalDocument;
import org.hl7.v3.POCDMT000040InfrastructureRootTypeId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Binding;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CanonicalModelProcessorTest {

    @Mock
    WebServiceMessageReceiver webServiceMessageReceiver;
    CaCISRequest request;
    CanonicalModelProcessor service;

    @Before
    public void init() throws JAXBException, ParserConfigurationException {
        MockitoAnnotations.initMocks(this);
        request = new CaCISRequest();
        request.getClinicalDocument().add(dummyClinicalDocument());

        service = new CanonicalModelProcessor(webServiceMessageReceiver);
        when(webServiceMessageReceiver.processData(anyString())).thenReturn("");
    }

    @Test
    public void acceptCanonical() throws AcceptCanonicalFault {
        service.acceptCanonical(request);

        verify(webServiceMessageReceiver).processData(anyString());

    }

    /**
     * Service throws exception when it is unable to process the incoming request
     * 
     * @throws AcceptCanonicalFault fault
     */
    @Test(expected = AcceptCanonicalFault.class)
    public void exception() throws AcceptCanonicalFault {
        when(webServiceMessageReceiver.processData(anyString())).thenThrow(new RuntimeException("Mirth Exception"));
        service.acceptCanonical(request);
    }

    @Test
    public void create() throws IOException, InterruptedException {

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 18010), 5);
        ExecutorService threads = Executors.newFixedThreadPool(5);
        server.setExecutor(threads);
        server.start();

        CanonicalModelProcessorPortTypeImpl service = new CanonicalModelProcessorPortTypeImpl();

        Endpoint webServiceEndpoint = Endpoint.create(service);
        Binding binding = webServiceEndpoint.getBinding();
        List<Handler> handlerChain = new LinkedList<Handler>();
        binding.setHandlerChain(handlerChain);
        HttpContext context = server.createContext("/services/sa");

        webServiceEndpoint.publish(context);

    }

    public static Element dummyClinicalDocument() throws JAXBException, ParserConfigurationException {
        II dummyIi = new II();
        dummyIi.setExtension("123");
        dummyIi.setRoot("1.2.3.456");
        POCDMT000040ClinicalDocument doc = new POCDMT000040ClinicalDocument();
        POCDMT000040InfrastructureRootTypeId typeId = new POCDMT000040InfrastructureRootTypeId();
        typeId.setRoot("2.16.840.1.113883.1.3");
        typeId.setExtension("123");

        doc.setTypeId(typeId);
        doc.setId(dummyIi);

        JAXBContext context = JAXBContext.newInstance("org.hl7.v3");
        Marshaller m = context.createMarshaller();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document domDoc = db.newDocument();

        m.marshal(new JAXBElement(new QName("urn:hl7-org:v3", "POCD_MT000040.ClinicalDocument"),
                POCDMT000040ClinicalDocument.class, doc), domDoc);

        return domDoc.getDocumentElement();

    }
}
