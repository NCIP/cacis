/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.client;

import gov.nih.nci.cacis.common.doc.DocumentHandler;
import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;

import java.io.InputStream;
import java.util.HashMap;

import org.openhealthtools.ihe.xds.document.DocumentDescriptor;

/**
 * A wrapper DocumentHandler implementation for xds Document Handler
 * This deals with xds client and document properties as hashtable
 * 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 * 
 */
public class WrapperXDSDocumentHandler implements DocumentHandler<HashMap<String, String>, HashMap<String, String>> {
    
    private final DocumentHandler<XDSHandlerInfo, XDSDocumentMetadata> docHndler;
    /**
     * @param docHndler instance fo the XDSDocumentHandler
     */
    public WrapperXDSDocumentHandler(DocumentHandler<XDSHandlerInfo, XDSDocumentMetadata> docHndler) {
        super();
        this.docHndler = docHndler;
    }

    @Override
    public String handleDocument(HashMap<String, String> arg0) throws ApplicationRuntimeException { //NOPMD
        final XDSDocumentMetadata docMd = new XDSDocumentMetadata();
        docMd.setDocEntryContent(arg0.get("docentry"));
        docMd.setSubmissionSetContent(arg0.get("submissionset"));
        docMd.setDocumentType(DocumentDescriptor.XML);
        docMd.setDocOID(arg0.get("docoid")); // NOPMD
        docMd.setDocSourceOID(arg0.get("docsourceoid"));
        docMd.setDocumentContent(arg0.get("content"));
        
        return docHndler.handleDocument(docMd);
    }

    @Override
    public void initialize(HashMap<String, String> arg0) throws ApplicationRuntimeException { //NOPMD
        // need not do anything..as this class only delegates to the XDSDocumentHandler      
    }

    @Override
    public InputStream retrieveDocument(String arg0) throws ApplicationRuntimeException {
        return docHndler.retrieveDocument(arg0);
    }
    
}
