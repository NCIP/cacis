/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The caEHR Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and 5AM Solutions Inc, SemanticBits LLC, and AgileX Technologies, Inc (collectively 'SubContractors').
 * To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United
 * States Code, section 105.
 *
 * This caEHR Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the caEHR Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly
 * display, publicly perform, and prepare derivative works of the caEHR Software; (ii) distribute and have distributed
 * to and by third parties the caEHR Software and any modifications and derivative works thereof; and (iii) sublicense
 * the foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
 * third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting or right of
 * payment from You or Your sub-licensees for the rights granted under this License. This License is granted at no
 * charge to You.
 *
 * Your redistributions of the source code for the Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code
 * form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
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
package gov.nih.nci.cacis;

import org.apache.camel.ProducerTemplate;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openehealth.ipf.commons.ihe.xds.core.SampleData;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Document;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.DocumentEntry;
import org.openehealth.ipf.commons.ihe.xds.core.requests.ProvideAndRegisterDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RegisterDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RetrieveDocument;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RetrieveDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Response;
import org.openehealth.ipf.commons.ihe.xds.core.responses.RetrievedDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.openehealth.ipf.platform.camel.ihe.ws.StandardTestContainer;
import org.openehealth.ipf.tutorials.xds.ContentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(locations = {"/demo-xds-context.xml"})

/**
 * Test with the demo repo that comes with Openehealth IPF
 */
public class DemoXDSTest extends StandardTestContainer {
    @Autowired
    private ProducerTemplate producerTemplate;

//    IHE profiles http://repo.openehealth.org/confluence/display/ipf2/IHE+Support+aggregator+page
    private String xds43Uri = "xds-iti43://localhost:" + getPort() + "/xds-iti43";
    private String xds42Uri = "xds-iti42://localhost:" + getPort() + "/xds-iti42";
    private String xds41Uri = "xds-iti41://localhost:" + getPort() + "/xds-iti41";


    @BeforeClass
    public static void setUp() throws Exception {
        startServer(new CXFServlet(), "demo-xds-context.xml", false, 8999);
    }


    @Test
    public void storeAndRetrieve() throws Exception {
        ProvideAndRegisterDocumentSet documentSet = SampleData.createProvideAndRegisterDocumentSet();

        Document document = documentSet.getDocuments().get(0);
        DocumentEntry docEntry = document.getDocumentEntry();

        docEntry.getPatientId().setId(UUID.randomUUID().toString());
        docEntry.setUniqueId("4.3.2.1");
        docEntry.setHash(ContentUtils.sha1(document.getDataHandler()).toString());
        docEntry.setSize(Long.parseLong(ContentUtils.size(document.getDataHandler()).toString()));

        Response response = (Response) send(xds41Uri, documentSet, Response.class);
        assertEquals(response.toString(), Status.SUCCESS, response.getStatus());

        RetrieveDocumentSet retrieve = new RetrieveDocumentSet();
        RetrieveDocument doc1 = new RetrieveDocument();
        doc1.setDocumentUniqueId("4.3.2.1");
        doc1.setRepositoryUniqueId("something");
        retrieve.getDocuments().add(doc1);

        RetrievedDocumentSet retrieveResponse = (RetrievedDocumentSet) send(xds43Uri, retrieve, RetrievedDocumentSet.class);
        assertEquals(retrieveResponse.toString(), Status.SUCCESS, retrieveResponse.getStatus());
    }


    @Test
    public void register() {
        RegisterDocumentSet registerDocumentSet = SampleData.createRegisterDocumentSet();
        DocumentEntry docEntry = registerDocumentSet.getDocumentEntries().get(0);
        docEntry.getPatientId().setId(UUID.randomUUID().toString());
        docEntry.setUniqueId("5.3.2.1");


        Response registerResponse = (Response) send(xds42Uri, registerDocumentSet, Response.class);
        assertEquals(registerResponse.toString(), Status.SUCCESS, registerResponse.getStatus());

    }
}
