/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software was developed in conjunction with the
 * National Cancer Institute (NCI) by NCI employees and subcontracted parties. To the extent government employees are
 * authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 * 
 * This gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software License (the License) is between NCI and You. You (or Your) shall
 * mean a person or an entity, and all other entities that control, are controlled by, or are under common control with
 * the entity. Control for purposes of this definition means (i) the direct or indirect power to cause the direction or
 * management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the
 * outstanding shares, or (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software to (i) use, install, access, operate, execute, copy,
 * modify, translate, market, publicly display, publicly perform, and prepare derivative works of the
 * gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software; (ii) distribute and have distributed to and by third parties the
 * gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software and any modifications and derivative works thereof; and (iii) sublicense
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
 * product includes software developed by the National Cancer Institute and subcontracted parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 * 
 * You may not use the names "The National Cancer Institute", "NCI", or any subcontracted party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or theany of the subcontracted parties, except as required to comply with
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
package gov.nih.nci.cacis.xds.client;

import gov.nih.nci.cacis.common.doc.DocumentHandler;
import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.service.DocumentAccessManager;
import gov.nih.nci.cacis.xds.authz.service.XdsWriteAuthzManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhealthtools.ihe.xds.document.DocumentDescriptor;
import org.openhealthtools.ihe.xds.metadata.extract.MetadataExtractionException;
import org.openhealthtools.ihe.xds.source.SubmitTransactionCompositionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test class for XDS document submission
 * 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-xds-test.xml")
public class XDSDocumentHandlerIntegrationTest {

    private static final String SUBJECT_DN = "EMAILADDRESS=kondayya.mullapudi@misys.com,"
            + " CN=XDSb_REP_MOSS.ihe.net, OU=MOSS, O=\"Misys Open Source Solutions, LLC.\","
            + " L=Chicago, ST=Illinois, C=US";
    
    @Autowired
    private DocumentHandler docHndlr;

    @Autowired
    private XdsWriteAuthzManager writeManager;

    @Autowired
    private DocumentAccessManager accessManager;

    private XDSDocumentMetadata docMd;

    /**
     * set up method
     * 
     * @throws IOException - exception thrown, if any
     * @throws URISyntaxException - exception thrown, if any
     * @throws AuthzProvisioningException - exception thrown, if any
     */
    @Before
    public void setup() throws URISyntaxException, IOException, AuthzProvisioningException {
        docMd = new XDSDocumentMetadata();
        docMd.setDocEntryContent(getFileContent("docEntry.xml"));
        docMd.setSubmissionSetContent(getFileContent("submissionSet.xml"));
        docMd.setDocumentType(DocumentDescriptor.XML);
        docMd.setDocOID("1.2.3.4"); // NOPMD
        docMd.setDocSourceOID("1.3.6.1.4.1.21367.2010.1.2");
        docMd.setDocumentContent(getFileContent("person.xml"));

        writeManager.grantStoreWrite(SUBJECT_DN);
    }

    private String getFileContent(String fileName) throws URISyntaxException, IOException {
        final File docEntryFile = new File(getClass().getClassLoader().getResource(fileName).toURI());
        return FileUtils.readFileToString(docEntryFile);
    }

    /**
     * Test handling and retrieving document with XDSDocument handler
     * 
     * @throws ApplicationRuntimeException - exception thrown, if any
     * @throws SubmitTransactionCompositionException - exception thrown, if any
     * @throws MetadataExtractionException - exception thrown, if any
     * @throws IOException - exception thrown, if any
     * @throws AuthzProvisioningException - exception thrown, if any
     */
    @Test
    public void handleAndRetrieveDocument() throws ApplicationRuntimeException, MetadataExtractionException,
            SubmitTransactionCompositionException, IOException, AuthzProvisioningException {
        String docUniqueId = null;
        InputStream ris = null;
        try {
            docUniqueId = docHndlr.handleDocument(docMd);
            Assert.assertNotNull(docUniqueId);

            accessManager.grantDocumentAccess(docUniqueId, SUBJECT_DN);

            ris = docHndlr.retrieveDocument(docUniqueId);
            Assert.assertNotNull(ris);
        } finally {
            if (docUniqueId != null) {
                accessManager.revokeDocumentAccess(docUniqueId, SUBJECT_DN);
            }
            writeManager.revokeStoreWrite(SUBJECT_DN);
        }

    }

}