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
package gov.nih.nci.cacis.sa.client;

import gov.nih.nci.cacis.AcceptSourceFault;
import gov.nih.nci.cacis.AcceptSourcePortType;
import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.CaCISResponse;
import gov.nih.nci.cacis.sa.config.SemanticAdapterClientConfig;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.StringWriter;
import java.net.URL;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class SemanticAdapterClient {

    private static final Logger LOG = Logger.getLogger(SemanticAdapterClient.class);


    /**
     * @param requestFileUrl file that has the request to be submitted
     * @throws JAXBException     Exception
     * @throws AcceptSourceFault Service Fault
     * @return String response
     */
    public String invoke(URL requestFileUrl) throws JAXBException, AcceptSourceFault {
        return this.invoke(requestFileUrl.getFile());
    }


    /**
     * @param requestFileUrl file that has the request to be submitted
     * @throws JAXBException     Exception
     * @throws AcceptSourceFault Service Fault
     * @return String response
     */
    public String invoke(String requestFileUrl) throws JAXBException, AcceptSourceFault {
        return this.invoke(new File(requestFileUrl));
    }

    /**
     * @param requestFile file that has the request to be submitted
     * @throws JAXBException     Exception
     * @throws AcceptSourceFault Service Fault
     * @return String response
     */
    public String invoke(File requestFile) throws JAXBException, AcceptSourceFault {

        final ApplicationContext ctx =
                new AnnotationConfigApplicationContext(SemanticAdapterClientConfig.class);
        final AcceptSourcePortType
                saClient = (AcceptSourcePortType) ctx.getBean("client");

        final JAXBContext jc = JAXBContext.newInstance(CaCISRequest.class);

        final CaCISRequest request = (CaCISRequest) jc.createUnmarshaller().unmarshal(requestFile);

        LOG.info("Sending request to SA Service ");
        //        todo serialize response
        final CaCISResponse response = saClient.acceptSource(request);

        final StringWriter result = new StringWriter();
        jc.createMarshaller().marshal(response,result);

        return result.toString();

    }

    /**
     * Main method
     *
     * @param args argument. Only 1 is expected which is the path to the request data file
     * @throws JAXBException     Exception
     * @throws AcceptSourceFault Service fault
     */
    public static void main(String[] args) throws JAXBException, AcceptSourceFault {
        if (args.length != 1) {
            System.out.println("usage is " +                //NOPMD
                    "\"java SemanticAdapterClient <request_file_url>");
            System.exit(0);
        }
        final SemanticAdapterClient client = new SemanticAdapterClient();
        client.invoke(args[0]);

    }
}
