/*
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The caEHR Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and 5AM Solutions Inc, SemanticBits LLC, and AgileX Technologies, Inc (collectively 'SubContractors').
 * To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United
 * States Code, section 105.
 * 
 * This caEHR Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
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
