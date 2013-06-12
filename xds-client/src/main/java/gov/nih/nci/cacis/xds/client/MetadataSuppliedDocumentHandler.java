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
import gov.nih.nci.cacis.xds.MetadataSupplier;
import org.openhealthtools.ihe.xds.document.DocumentDescriptor;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class MetadataSuppliedDocumentHandler  implements DocumentHandler<String,String> {

    private final MetadataSupplier metadataSupplier;
    private final DocumentHandler<XDSHandlerInfo, XDSDocumentMetadata> docHndler;

    /**
     * constructor
     * @param metadataSupplier instance that supplies metadata
     * @param docHndler Document Handler
     */
    public MetadataSuppliedDocumentHandler(MetadataSupplier metadataSupplier,
                                           DocumentHandler<XDSHandlerInfo, XDSDocumentMetadata> docHndler) {
        this.metadataSupplier = metadataSupplier;
        this.docHndler = docHndler;
    }

    /**
     * Interface method to initialize the handler
     *
     * @param setupInfo - setup info for the handler
     * @throws gov.nih.nci.cacis.common.exception.ApplicationRuntimeException
     *          - exception thrown, if any
     */
    @Override
    public void initialize(String setupInfo) throws ApplicationRuntimeException {
       //does not need any initializing information
    }


    /**
     * Interface method to handle the document
     *
     * @param document - the metadata for the document
     * @return String representing the document unique id
     * @throws gov.nih.nci.cacis.common.exception.ApplicationRuntimeException
     *          - exception thrown, if any
     */
    @Override
    public String handleDocument(String document) throws ApplicationRuntimeException {
         final XDSDocumentMetadata docMd = new XDSDocumentMetadata();

        try {
                docMd.setDocEntryContent(metadataSupplier.createDocEntry());
            docMd.setSubmissionSetContent(metadataSupplier.createSubmissionSet());
        } catch (IOException e) {
            throw new ApplicationRuntimeException("Error getting metadata from metadata supplier",
                    e);
        }
        docMd.setDocumentType(DocumentDescriptor.XML);
        docMd.setDocOID(metadataSupplier.createDocOID()); // NOPMD
        docMd.setDocSourceOID(metadataSupplier.createDocSourceOID());
        docMd.setDocumentContent(document);

        return docHndler.handleDocument(docMd);

    }

    /**
     * Interface method to get the input stream to the document in the repository
     *
     * @param docUniqueID - identifier for the doc to be retrieved
     * @return InputStream to the doc
     * @throws gov.nih.nci.cacis.common.exception.ApplicationRuntimeException
     *          - exception thrown, if any
     */
    @Override
    public InputStream retrieveDocument(String docUniqueID) throws ApplicationRuntimeException {
        return null;
    }


}
