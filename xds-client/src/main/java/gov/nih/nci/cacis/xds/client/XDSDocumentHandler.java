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
package gov.nih.nci.cacis.xds.client;

import gov.nih.nci.cacis.common.doc.DocumentHandler;
import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.openhealthtools.ihe.atna.auditor.XDSConsumerAuditor;
import org.openhealthtools.ihe.atna.auditor.XDSSourceAuditor;
import org.openhealthtools.ihe.utils.OID;
import org.openhealthtools.ihe.xds.consumer.B_Consumer;
import org.openhealthtools.ihe.xds.consumer.retrieve.DocumentRequestType;
import org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveDocumentSetRequestType;
import org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveFactory;
import org.openhealthtools.ihe.xds.document.XDSDocument;
import org.openhealthtools.ihe.xds.document.XDSDocumentFromByteArray;
import org.openhealthtools.ihe.xds.response.XDSResponseType;
import org.openhealthtools.ihe.xds.response.XDSRetrieveResponseType;
import org.openhealthtools.ihe.xds.response.XDSStatusType;
import org.openhealthtools.ihe.xds.source.B_Source;
import org.openhealthtools.ihe.xds.source.SubmitTransactionData;

/**
 * DocumentHandler implementation for xds
 * 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 * 
 */
public class XDSDocumentHandler implements DocumentHandler<XDSHandlerInfo, XDSDocumentMetadata> {
        
    private B_Source source;
    private B_Consumer consumer;
    private XDSHandlerInfo setupInfo;
    private final Map<String,URI> repositoryUriMap = new HashMap<String,URI>();
    
    private final SimpleDateFormat dtFrmt = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
   
    @Override
    public void initialize(XDSHandlerInfo setupInfo) throws ApplicationRuntimeException {
        this.setupInfo = setupInfo;
        try {
            // Setting axis2.xml to avoid SOAP HEADER NOT FOUND exception from openxds
            System.setProperty("axis2.xml", setupInfo.getAxis2XmlLocation());
            //set properties for SSL support
//            System.setProperty("javax.net.ssl.keyStore", setupInfo.getXdsKeystorePath());
//            System.setProperty("javax.net.ssl.keyStorePassword", setupInfo.getXdsKestorePassword());
//            System.setProperty("javax.net.ssl.trustStore", setupInfo.getXdsTruststorePath());
//            System.setProperty("javax.net.ssl.trustStorePassword", setupInfo.getXdsTruststorePassword());
            
            final URI repoURI = URI.create(setupInfo.getRepositoryURL());
            // XDS Document Source setup
            source = new B_Source(repoURI);
            // turn off auditing for now ...
            XDSSourceAuditor.getAuditor().getConfig().setAuditorEnabled(setupInfo.isSourceAuditorEnable());
            
            //XDS Document Consumer setup
            repositoryUriMap.put(setupInfo.getRepoOIDRoot(), URI.create(setupInfo.getRepositoryURL()));
            consumer = new B_Consumer(URI.create(setupInfo.getRegistryURL()),null,repositoryUriMap);
            // turn off auditing for now ...
            XDSConsumerAuditor.getAuditor().getConfig().setAuditorEnabled(setupInfo.isConsumerAuditorEnable());
            
            //CHECKSTYLE:OFF
        } catch (Exception e) { //NOPMD
            //CHECKSTYLE:OFF
            throw new ApplicationRuntimeException("Error initialising XDS Document Handler" , e);
        }
    }

    @Override
    public String handleDocument(XDSDocumentMetadata documentMetadata) throws ApplicationRuntimeException {
        String documentUniqueId = null;
        //##############################
        //Document Submission Code Start
        //##############################
        try {
            final String currTime = dtFrmt.format(new Date());
            
            final SubmitTransactionData txnData = new SubmitTransactionData();
            final XDSDocument clinicalDocument = 
                    new XDSDocumentFromByteArray(
                            documentMetadata.getDocumentType(), 
                            documentMetadata.getDocumentContent().getBytes());
            
            //Add document metadata
            InputStream is = new ByteArrayInputStream(documentMetadata.getDocEntryContent().getBytes());
            final String docEntryUUID = txnData.loadDocumentWithMetadata(clinicalDocument, is);
            is.close();
            
            // set documentUniqueID
            // say that you are assigned an organizational oid of "1.2.3.4"
            txnData.getDocumentEntry(docEntryUUID).setUniqueId(OID.createOIDGivenRoot(documentMetadata.getDocOID()));
            //capturing the documentUniqueID to retrieve document using xds consumer
            documentUniqueId = txnData.getDocumentEntry(docEntryUUID).getUniqueId();
            
            //Add submission set metadata
            is = new ByteArrayInputStream(documentMetadata.getSubmissionSetContent().getBytes());
            txnData.loadSubmissionSet(is);
            is.close();
            
            // set uniqueID
            // say that you are assigned an organizational oid of "1.2.3.4"
            txnData.getSubmissionSet().setUniqueId(OID.createOIDGivenRoot(documentMetadata.getDocOID()));
            txnData.getSubmissionSet().setSubmissionTime(currTime);
            
            txnData.getSubmissionSet().setSourceId(documentMetadata.getDocSourceOID());
           
            //Submitting Document
            final XDSResponseType response = source.submit(false,txnData);
                        
            if (!XDSStatusType.SUCCESS_LITERAL.equals(response.getStatus())) {
                throw new ApplicationRuntimeException("Unsuccessful XDS document handling with status," 
                        + response.getStatus().getLiteral() 
                        , response.getCaughtException());
            }
            //CHECKSTYLE:OFF
        } catch (Exception e) { //NOPMD
            //CHECKSTYLE:OFF
            throw new ApplicationRuntimeException("Error handling XDS Document" , e);
        }
        
        return documentUniqueId;
    }

    @Override
    public InputStream retrieveDocument(String docUniqueID) throws ApplicationRuntimeException {
        final RetrieveDocumentSetRequestType retrieveDocumentSetRequest = 
            RetrieveFactory.eINSTANCE.createRetrieveDocumentSetRequestType();
        
        //Add Document Request
        final DocumentRequestType documentRequest = 
            RetrieveFactory.eINSTANCE.createDocumentRequestType();
        
        documentRequest.setRepositoryUniqueId(setupInfo.getRepoOIDRoot());
        documentRequest.setDocumentUniqueId(docUniqueID); 
        
        retrieveDocumentSetRequest.getDocumentRequest().add(documentRequest);
        
        //Submit Request.
        final XDSRetrieveResponseType response = 
            consumer.retrieveDocumentSet(false, retrieveDocumentSetRequest, null);
        
        //Get documents
        final List<XDSDocument> documents = response.getAttachments();
        if (documents == null || documents.isEmpty()) {
            return null;
        }
        
        return documents.get(0).getStream();
    }

}
