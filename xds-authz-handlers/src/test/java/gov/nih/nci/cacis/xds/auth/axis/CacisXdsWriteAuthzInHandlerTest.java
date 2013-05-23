/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.auth.axis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.service.XdsWriteAuthzManager;

import java.security.Principal;
import java.security.cert.X509Certificate;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
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
public class CacisXdsWriteAuthzInHandlerTest {
    
    private static final String PROVIDE_AND_REGISETR_DOCUMENT_SET_B = "urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b";
    
    @Autowired
    private  ApplicationContext applicationContext;
    @Autowired
    private  XdsWriteAuthzManager manager;
    
    private  CacisXdsWriteAuthzInHandler inHandler;
    private  MessageContext msgContext;
    private  MockHttpServletRequest req;
    
    @Mock
    X509Certificate certificate;
    @Mock
    Principal principal;
    
    @Before
    public void setUp() {
        inHandler = new CacisXdsWriteAuthzInHandler();
        inHandler.setApplicationContext(applicationContext);
        msgContext = new MessageContext();
        //Set action
        msgContext.setWSAAction(PROVIDE_AND_REGISETR_DOCUMENT_SET_B);
        req = new MockHttpServletRequest();
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
            when(certificate.getSubjectDN()).thenReturn(principal);
            when(principal.getName()).thenReturn(subjectDN);
            inHandler.invoke(msgContext);
            fail("Expecting an Exception from the Handler");
        } catch(AxisFault e) {
            assertEquals("Error while checking access", e.getMessage());
        }
    }    
    

    @DirtiesContext
    @Test
    public void testOutHandlerInvoke_CheckAccessFail() throws AuthzProvisioningException {

        MockitoAnnotations.initMocks(this);
        X509Certificate[] certificates = {certificate};
        req.setAttribute("javax.servlet.request.X509Certificate", certificates);
        //Set Mock Request
        msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST, req);
        String subjectDN = "subjectDN1";
        try {
            manager.grantStoreWrite("subjectDN2");
            when(certificate.getSubjectDN()).thenReturn(principal);
            when(principal.getName()).thenReturn(subjectDN);
            inHandler.invoke(msgContext);
            fail("Expecting an Exception from the Handler");
        } catch(AxisFault e) {
            assertEquals("Permission denied", e.getMessage());
        } finally {
            manager.revokeStoreWrite("subjectDN2");
        }
    }

    @DirtiesContext
    @Test
    public void testOutHandlerInvoke_CheckAccessPass() throws AuthzProvisioningException {

        MockitoAnnotations.initMocks(this);
        X509Certificate[] certificates = {certificate};
        req.setAttribute("javax.servlet.request.X509Certificate", certificates);
        //Set Mock Request
        msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST, req);
        String subjectDN = "subjectDN1";
        try {
            manager.grantStoreWrite(subjectDN);
            when(certificate.getSubjectDN()).thenReturn(principal);
            when(principal.getName()).thenReturn(subjectDN);
            inHandler.invoke(msgContext);
        } catch(AuthzProvisioningException e) {
            fail("NOT Expecting an Exception from the Handler");
        } catch(AxisFault e) {
            fail("NOT Expecting an Exception from the Handler");
        } finally {
            manager.revokeStoreWrite(subjectDN);
        }
    }    
}