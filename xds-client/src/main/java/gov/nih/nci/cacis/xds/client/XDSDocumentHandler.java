/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
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
