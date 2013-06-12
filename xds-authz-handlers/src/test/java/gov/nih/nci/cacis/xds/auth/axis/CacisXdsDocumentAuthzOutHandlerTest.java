/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.auth.axis;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.service.DocumentAccessManager;

import java.security.Principal;
import java.security.cert.X509Certificate;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.jaxws.message.factory.SAAJConverterFactory;
import org.apache.axis2.jaxws.message.util.SAAJConverter;
import org.apache.axis2.jaxws.registry.FactoryRegistry;
import org.apache.axis2.transport.http.HTTPConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-xds-authz-handlers-hsqldb.xml")
public class CacisXdsDocumentAuthzOutHandlerTest {
    
    private static final String RETRIEVE_DOCUMENT_SET_B = "urn:ihe:iti:2007:RetrieveDocumentSet";
    
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DocumentAccessManager manager;
    
    private CacisXdsDocumentAuthzOutHandler outHandler;
    
    private  MessageContext msgContext;
    private  MockHttpServletRequest req;
    
    @Mock
    X509Certificate certificate;
    @Mock
    Principal principal;
    
    @Before
    public void setUp() {
        
        outHandler = new CacisXdsDocumentAuthzOutHandler();
        outHandler.setApplicationContext(applicationContext);
        msgContext = new MessageContext();
        //Set action
        msgContext.setWSAAction(RETRIEVE_DOCUMENT_SET_B);
        req = new MockHttpServletRequest();
        manager = (DocumentAccessManager)applicationContext.getBean("documentAccessManager");
    }
    
    @Test
    public void testOutHandlerInvoke_CheckAccessException() {

        MockitoAnnotations.initMocks(this);
        X509Certificate[] certificates = {certificate};
        req.setAttribute("javax.servlet.request.X509Certificate", certificates);
        //Set Mock Request
        msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST, req);
        String subjectDN = "subjectDN1";
        try {
            msgContext.setEnvelope(createSOAPEnvelope("1"));
            when(certificate.getSubjectDN()).thenReturn(principal);
            when(principal.getName()).thenReturn(subjectDN);
            outHandler.invoke(msgContext);
            fail("Expecting an Exception from the Handler");
        } catch(Exception e) {
            //Expecting Handler to throw exception.
            assertEquals("Error while checking access",e.getMessage());
        }
    }
    
    @Test
    @DirtiesContext
    public void testOutHandlerInvoke_CheckAccessPass() throws AuthzProvisioningException {
        
        MockitoAnnotations.initMocks(this);
        X509Certificate[] certificates = {certificate};
        req.setAttribute("javax.servlet.request.X509Certificate", certificates);
        //Set Mock Request
        msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST, req);
        String docId = "Document1234";
        String subjectDN = "User1234";
        try {
            manager.grantDocumentAccess(docId, subjectDN);
            msgContext.setEnvelope(createSOAPEnvelope(docId));
            when(certificate.getSubjectDN()).thenReturn(principal);
            when(principal.getName()).thenReturn(subjectDN);
            outHandler.invoke(msgContext);
        } catch(Exception e) {
            fail("NOT Expecting an Exception from the Handler");
        } finally {
            manager.revokeDocumentAccess(docId, subjectDN);
        }
    }
    

    @Test
    @DirtiesContext
    public void testOutHandlerInvoke_CheckAccessFail() throws AuthzProvisioningException {
        
        MockitoAnnotations.initMocks(this);
        X509Certificate[] certificates = {certificate};
        req.setAttribute("javax.servlet.request.X509Certificate", certificates);
        //Set Mock Request
        msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST, req);
        String docId = "Document12345";
        String subjectDN = "User12345";
        try {
            manager.grantDocumentAccess(docId, subjectDN);
            msgContext.setEnvelope(createSOAPEnvelope(docId));
            when(certificate.getSubjectDN()).thenReturn(principal);
            when(principal.getName()).thenReturn(subjectDN+"DD");
            outHandler.invoke(msgContext);
            fail("Expecting an Exception from the Handler");
        } catch(Exception e) {
            //Expecting an AxisFault Permission Denied.
            assertEquals("Permission denied",e.getMessage());
        } finally {
            manager.revokeDocumentAccess(docId, subjectDN);
        }
    }

    /**
     * Creates the DocumentRequest SOAP Envelope.
    	 * @param documentUniqueId
    	 * @return
    	 * @throws Exception
     */
    private org.apache.axiom.soap.SOAPEnvelope createSOAPEnvelope(String documentUniqueId) throws Exception {
        
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        SOAPBody body = soapEnvelope.getBody();
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        
        Name bodyName = soapFactory.createName("RetrieveDocumentSetRequest", "retrieve", "urn:ihe:iti:xds-b:2007");
        SOAPBodyElement purchaseLineItems = body.addBodyElement(bodyName);

        Name childName = soapFactory.createName("DocumentRequest");
        SOAPElement order = purchaseLineItems.addChildElement(childName);

        childName = soapFactory.createName("RepositoryUniqueId");
        SOAPElement product = order.addChildElement(childName);
        product.addTextNode("1.3.6.1.4.1.21367.13.1150");

        childName = soapFactory.createName("DocumentUniqueId");
        SOAPElement price = order.addChildElement(childName);
        //"1.2.3.4.109003122009225006.1312226605982.1"
        price.addTextNode(documentUniqueId);
        SAAJConverterFactory f = (SAAJConverterFactory) 
                FactoryRegistry.getFactory(SAAJConverterFactory.class);
        SAAJConverter converter = f.getSAAJConverter();
        org.apache.axiom.soap.SOAPEnvelope aSE = converter.toOM(soapEnvelope);
        return aSE;
    }
}