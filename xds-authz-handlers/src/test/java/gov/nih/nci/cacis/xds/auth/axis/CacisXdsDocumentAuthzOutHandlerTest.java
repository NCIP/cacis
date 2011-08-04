/*
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The caEHR Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and 5AM Solutions Inc, SemanticBits LLC, and AgileX Technologies, Inc (collectively 'SubContractors').
 * To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United
 * States Code, section 105.
 *
 * This caEHR Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 *  of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 *
 * Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: This
 * product includes software developed by the National Cancer Institute and SubContractor parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or any SubContractor party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or any of the subcontracted parties, except as required to comply with
 * the terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs
 * and into any third party proprietary programs. However, if You incorporate the Software into third party proprietary
 * programs, You agree that You are solely responsible for obtaining any permission from such third parties required to
 * incorporate the Software into such third party proprietary programs and for informing Your sub-licensees, including
 * without limitation Your end-users, of their obligation to secure any required permissions from such third parties
 * before incorporating the Software into such third party proprietary software programs. In the event that You fail to
 * obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to the
 * extent prohibited by law, resulting from Your failure to obtain such permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and
 * to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses
 * of modifications of the Software, or any derivative works of the Software as a whole, provided Your use,
 * reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, ANY OF ITS SUBCONTRACTED PARTIES OR THEIR AFFILIATES BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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