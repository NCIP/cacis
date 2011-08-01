package gov.nih.nci.cacis.xds.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.openhealthtools.ihe.atna.auditor.XDSConsumerAuditor;
import org.openhealthtools.ihe.atna.auditor.XDSSourceAuditor;
import org.openhealthtools.ihe.common.hl7v2.CX;
import org.openhealthtools.ihe.common.hl7v2.Hl7v2Factory;
import org.openhealthtools.ihe.utils.OID;
import org.openhealthtools.ihe.xds.consumer.B_Consumer;
import org.openhealthtools.ihe.xds.consumer.retrieve.DocumentRequestType;
import org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveDocumentSetRequestType;
import org.openhealthtools.ihe.xds.document.DocumentDescriptor;
import org.openhealthtools.ihe.xds.document.XDSDocument;
import org.openhealthtools.ihe.xds.document.XDSDocumentFromFile;
import org.openhealthtools.ihe.xds.metadata.AvailabilityStatusType;
import org.openhealthtools.ihe.xds.metadata.CodedMetadataType;
import org.openhealthtools.ihe.xds.metadata.MetadataFactory;
import org.openhealthtools.ihe.xds.response.XDSResponseType;
import org.openhealthtools.ihe.xds.response.XDSRetrieveResponseType;
import org.openhealthtools.ihe.xds.source.B_Source;
import org.openhealthtools.ihe.xds.source.SubmitTransactionData;

/**
 * TestCase which demonstrates
 * 1: Use of XDS Document Source API to Store a document in XDS repository 
 * 2: Use of XDS Document Consumer API to Retrieve a document from XDS repository 
 * 
 * @author monish.dombla@semanticbits.com
 * @since July 6, 2011 
 *
 */
public class XdsClientTest extends TestCase {
    
    private B_Source source;
    private SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Logger logger = Logger.getLogger(XdsClientTest.class);
    
    private B_Consumer consumer;
    private Map<String,URI> repositoryUriMap = new HashMap<String,URI>();
    
//    private String repositoryURL = "http://localhost:8080/openxds-web/services/DocumentRepository";
//    private String registryURL = "http://localhost:8080/openxds-web/services/DocumentRegistry";
    
//    private String repositoryURL = "https://localhost:8011/openxds/services/DocumentRepository";
//    private String registryURL = "https://localhost:8011/openxds/services/DocumentRegistry";
    
    private String repositoryURL = "https://localhost:8011/openxds-web/services/DocumentRepository";
    private String registryURL = "https://localhost:8011/openxds-web/services/DocumentRegistry";
    

//    private String repositoryURL = "https://localhost:8443/openxds-web/services/DocumentRepository";
//    private String registryURL = "https://localhost:8443/openxds-web/services/DocumentRegistry";


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        //Setting axis2.xml to avoid SOAP HEADER NOT FOUND exception from openxds
        System.setProperty("axis2.xml", getClass().getClassLoader().getResource("axis2.xml").getPath());
        
        //XDS Document Source setup
        source = new B_Source(URI.create(repositoryURL));
        //turn off auditing for now ...
        XDSSourceAuditor.getAuditor().getConfig().setAuditorEnabled(false);
        
        //XDS Document Consumer setup
        repositoryUriMap.put("1.3.6.1.4.1.21367.13.1150", URI.create(repositoryURL));
        consumer = new B_Consumer(URI.create(registryURL),null,repositoryUriMap);
        //turn off auditing for now ...
        XDSConsumerAuditor.getAuditor().getConfig().setAuditorEnabled(false);
        
        System.setProperty("javax.net.ssl.keyStore", "/Users/Moni/Misc/certs/OpenXDS_2011_Keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        System.setProperty("javax.net.ssl.trustStore", "/Users/Moni/Misc/certs/OpenXDS_2011_Truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
        
    }
    
    public void testSubmission() throws Throwable{
       
        //##############################
        //Document Submission Code Start
        //##############################
        
        CX patientId = Hl7v2Factory.eINSTANCE.createCX();
        patientId.setIdNumber("NA5156");
        patientId.setAssigningAuthorityUniversalId("1.3.6.1.4.1.21367.13.20.3000");
        patientId.setAssigningAuthorityUniversalIdType("ISO");
        
        SubmitTransactionData txnData = new SubmitTransactionData();
        XDSDocument clinicalDocument = 
                new XDSDocumentFromFile
                    (DocumentDescriptor.XML, 
                            new File(getClass().getClassLoader().getResource("person.xml").toURI()));
        
       //Add document metadata
        File docEntryFile = new File (getClass().getClassLoader().getResource("docEntry.xml").toURI());
        FileInputStream fis = new FileInputStream(docEntryFile);
        String docEntryUUID = txnData.loadDocumentWithMetadata(clinicalDocument, fis);
        fis.close();
        
        // set documentUniqueID
        // say that you are assigned an organizational oid of "1.2.3.4"
        txnData.getDocumentEntry(docEntryUUID).setUniqueId(OID.createOIDGivenRoot("1.2.3.4"));
        //capturing the documentUniqueID to retrieve document using xds consumer
        String documentUniqueId = txnData.getDocumentEntry(docEntryUUID).getUniqueId();
        
        //Add submission set metadata
        File submissionSetFile = new File(getClass().getClassLoader().getResource("submissionSet.xml").toURI());
        fis = new FileInputStream(submissionSetFile);
        txnData.loadSubmissionSet(fis);
        fis.close();
        
        txnData.getSubmissionSet().setSourceId("1.3.6.1.4.1.21367.2010.1.2");
        // set uniqueID
        // say that you are assigned an organizational oid of "1.2.3.4"
        txnData.getSubmissionSet().setUniqueId(OID.createOIDGivenRoot("1.2.3.4"));
        txnData.getSubmissionSet().setSubmissionTime(date.format(new Date()));
        
        String folderEntryUUID = txnData.addFolder();
        txnData.getFolder(folderEntryUUID).setUniqueId(OID.createOIDGivenRoot("1.2.3.4"));
        txnData.getFolder(folderEntryUUID).setAvailabilityStatus(AvailabilityStatusType.APPROVED_LITERAL);
        txnData.getFolder(folderEntryUUID).setLastUpdateTime(date.format(new Date()));
        txnData.getFolder(folderEntryUUID).setPatientId(patientId);
        
        CodedMetadataType code = MetadataFactory.eINSTANCE.createCodedMetadataType();
        code.setCode("General Medicine");
        code.setSchemeName("Connect-a-thon practiceSettingCodes");
        
        txnData.getFolder(folderEntryUUID).getCode().add(code);
        
        
        txnData.addDocumentToFolder(docEntryUUID, folderEntryUUID);
        
        //Submitting Document;
        XDSResponseType response = source.submit(false,txnData);
        
        while (!response.isComplete()) {
            Thread.sleep(1000);
            System.out.println("still waiting on 1...");
        }
        
        assertEquals("Success",response.getStatus().getLiteral());

        //##############################
        //Document Submission Code End
        //##############################
        
        //Call to Retrieve the stored document 
        List<XDSDocument> documents = retrieveDocument(documentUniqueId);
        
        assertNotNull(documents);
        assertEquals(1,documents.size());
        
//        if (null != documents) {
//            logger.debug("Received " + documents.size() + " documents");
//            Iterator<XDSDocument> docIterator = documents.iterator();
//            while (docIterator.hasNext()) {
//                XDSDocument doc = docIterator.next();
//                System.out.println("Received Document: " + doc);
//                System.out.println(readStream(doc.getStream()));
//            }
//        } 
    }
    
    /**
     * This method uses XDS Consumer API to retrieve the document from XDS repository.
         * @param documentUniqueId
         * @return
         * @throws Throwable
     */
    private List<XDSDocument> retrieveDocument(String documentUniqueId) throws Throwable {
        
        logger.debug("BEGIN RetrieveDocument()");
        // make patient id
        CX patientId = Hl7v2Factory.eINSTANCE.createCX();
        patientId.setIdNumber("NA5156");
        patientId.setAssigningAuthorityUniversalId("1.3.6.1.4.1.21367.13.20.3000");
        patientId.setAssigningAuthorityUniversalIdType("ISO");

        RetrieveDocumentSetRequestType retrieveDocumentSetRequest = org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveFactory.eINSTANCE.createRetrieveDocumentSetRequestType();
        //Add Document Request
        DocumentRequestType documentRequest = org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveFactory.eINSTANCE.createDocumentRequestType();
        
        documentRequest = org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveFactory.eINSTANCE.createDocumentRequestType();
        documentRequest.setRepositoryUniqueId("1.3.6.1.4.1.21367.13.1150");
        documentRequest.setDocumentUniqueId(documentUniqueId); //
        retrieveDocumentSetRequest.getDocumentRequest().add(documentRequest);
        
        //Submit Request.
        XDSRetrieveResponseType response = consumer.retrieveDocumentSet(false, retrieveDocumentSetRequest, patientId);
        
        //Wait for Response
        while (!response.isComplete()) {
            Thread.sleep(1000);
            System.out.println("still waiting on 1...");
        }
        
        //Get documents
        List<XDSDocument> documents = response.getAttachments();
        
        return  documents;

    }

    /**
     * This method prints the retrieved document onto a out2.txt file.
         * @param inputStream
         * @return
         * @throws Exception
     */
    private String readStream(InputStream inputStream) throws Exception {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("/Users/Moni/Misc/out2.txt"));
        int c;
        while ((c = inputStream.read()) != -1) {
            out.write(c);
        }
        return "done";
    }
}

