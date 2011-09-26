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
package gov.nih.nci.cacis.ip.mirthconnect;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;

import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.CanonicalModelProcessorPortType;
import gov.nih.nci.cacis.ClinicalData;
import gov.nih.nci.cacis.ClinicalMetadata;
import gov.nih.nci.cacis.DocumentType;
import gov.nih.nci.cacis.RoutingInstructions;
import gov.nih.nci.cacis.cdw.BaseVirtuosoIntegrationTest;
import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.binding.soap.SoapTransportFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Node;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-ip-mirth-test.xml")
public class CanonicalModelProcessorMCIntegrationTest extends BaseVirtuosoIntegrationTest {

    public static final String ADDRESS = "http://localhost:18081/services/CanonicalModelProcessor?wsdl";
    private static final Log LOG = LogFactory.getLog(CanonicalModelProcessorMCIntegrationTest.class);

    private static final String GRPH_GROUP_STUDY_ID = "mc_study_id";
    private static final String GRPH_GROUP_SITE_ID = "mc_site_id";
    private static final String GRPH_GROUP_P1_ID = "mc_patient_id";

    private static final String GRPH_GROUP_URI_STR_STUDY1 = CACIS_NS + "/" + GRPH_GROUP_STUDY_ID;
    private static final String GRPH_GROUP_URI_STR_SITE1 = GRPH_GROUP_URI_STR_STUDY1 + "/" + GRPH_GROUP_SITE_ID;
    private static final String GRPH_GROUP_URI_STR_P1 = GRPH_GROUP_URI_STR_SITE1 + "/" + GRPH_GROUP_P1_ID;

    @Override
    @Before
    public void init() throws AuthzProvisioningException, URISyntaxException {
        super.init();

        addNamespace("ns2", "http://cacis.nci.nih.gov");
        virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_URI_STR_SITE1);
        virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_URI_STR_STUDY1);
        virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_URI_STR_P1);
    }

    @Test
    public void invokeJaxWS() throws Exception {

        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(CanonicalModelProcessorPortType.class);
        // specify the URL. We are using the in memory test container
        factory.setAddress(ADDRESS);
        
        final JAXBContext jc = JAXBContext.newInstance(CaCISRequest.class);
        final InputStream is = getClass().getClassLoader().getResourceAsStream("CMP_valid_CR.xml");
        final CaCISRequest request = 
            (CaCISRequest) jc.createUnmarshaller().unmarshal(is);
        
        final CanonicalModelProcessorPortType client = (CanonicalModelProcessorPortType) factory.create();
       
        client.acceptCanonical(request);
    }

    @Test
    public void invokeSOAP() throws Exception {

        final Node res = invoke(ADDRESS, SoapTransportFactory.TRANSPORT_ID,
                getValidMessage().getBytes());
        assertNotNull(res);
        assertValid("//ns2:caCISResponse[@status='SUCCESS']", res);
        LOG.info("Echo response: " + res.getTextContent());

    }


    @Test
    public void failSchemaValidation() throws Exception {

        final URL url = getClass().getClassLoader().getResource("CMP_invalid_schema_soap.xml");
        String request = FileUtils.readFileToString(new File(url.toURI()));
        final Node res = invoke(ADDRESS, SoapTransportFactory.TRANSPORT_ID,
                request.getBytes());
        assertNotNull(res);

    }
}
