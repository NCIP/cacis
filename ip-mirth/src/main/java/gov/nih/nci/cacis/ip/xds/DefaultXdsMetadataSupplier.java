/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.xds;

import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.ExchangeFormat;
import gov.nih.nci.cacis.RoutingInstructions.ExchangeDocument;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;

/**
 * Supplies metadata available from the caCISRequest
 * 
 * @author monish.domblar@semanticbits.com
 */
public class DefaultXdsMetadataSupplier implements XdsMetadataSupplier {
    
    private final SimpleDateFormat dtFrmt = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());

    @Override
    public String createDocEntry(String caCISRequest) throws IOException {
        
        String docEntryMetaData = null;
        try {
            final CaCISRequest request = unMarshalcaCisRequest(caCISRequest);
            docEntryMetaData = getFileContent("DocumnetEntryMetadata_Template.xml");
            docEntryMetaData = 
                docEntryMetaData.replace("$PATIENT_ID_NUMBER$", request.getClinicalMetaData().getPatientIdExtension());
            docEntryMetaData = docEntryMetaData.replace("$CREATION_TIME$", dtFrmt.format(new Date()));
            
            final List<ExchangeDocument> rtIns = request.getRoutingInstructions().getExchangeDocument();
            final String formatCode = rtIns.get(0).getExchangeFormat().value();
            if (ExchangeFormat.HL_7_V_2_CLINICAL_NOTE.value().equals(formatCode)) {
                docEntryMetaData = docEntryMetaData.replace("$FORMAT_CODE_code$", "HLv2 OBX Message");
                docEntryMetaData = docEntryMetaData.replace("$FORMAT_CODE_LocalizedString$", "HLv2 OBX Message");
            } else if (ExchangeFormat.CCD.value().equals(formatCode)) {
                docEntryMetaData = docEntryMetaData.replace("$FORMAT_CODE_code$", "CDA/CCD");
                docEntryMetaData = docEntryMetaData.replace("$FORMAT_CODE_LocalizedString$", "CDA/CCD");
            } else if (ExchangeFormat.RIMITS.value().equals(formatCode)) {
                docEntryMetaData = docEntryMetaData.replace("$FORMAT_CODE_code$", "RIM/ITS");
                docEntryMetaData = docEntryMetaData.replace("$FORMAT_CODE_LocalizedString$", "RIM/ITS");
            }
        } catch (JAXBException e) {
            throw new IOException("Excption while extracting metadata",e);
        }
        return docEntryMetaData;
    }
    
    private String getFileContent(String fileName) throws IOException {
        
        final InputStream in = DefaultXdsMetadataSupplier.class.getClassLoader().getResourceAsStream(fileName);
        return IOUtils.toString(in);
        
    }
    
    @Override
    public String createSubmissionSet(String caCISRequest) throws IOException {
        String submissionSetMetaData = null;
        try {
            final CaCISRequest request = unMarshalcaCisRequest(caCISRequest);
            submissionSetMetaData = getFileContent("SubmissionSetMetadata_Template.xml");
            
            submissionSetMetaData = submissionSetMetaData.replace("$PATIENT_ID_NUMBER$", request
                    .getClinicalMetaData().getPatientIdExtension());
            
        } catch (JAXBException e) {
            throw new IOException("Excption while extracting metadata",e);
        }
        return submissionSetMetaData;
    }

    @Override
    public String createDocOID() {
        return "1.2.3.4"; //NOPMD
    }

    @Override
    public String createDocSourceOID() {
        return "1.3.6.1.4.1.21367.2010.1.2"; //NOPMD
    }
    
    private CaCISRequest unMarshalcaCisRequest(String caCisRequest) throws JAXBException {
        final JAXBContext ctx = JAXBContext.newInstance(CaCISRequest.class);
        return (CaCISRequest) ctx.createUnmarshaller().unmarshal(new ByteArrayInputStream( caCisRequest.getBytes()));
    }
}