/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect;

import gov.nih.nci.cacis.AcceptCanonicalFault;
import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.CaCISResponse;
import gov.nih.nci.cacis.ResponseStatusType;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;

import com.mirth.connect.connectors.ws.AcceptMessage;
import com.mirth.connect.connectors.ws.WebServiceMessageReceiver;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@WebService(serviceName = "CanonicalModelProcessor", portName = "CanonicalModelProcessor_Port_Soap11", targetNamespace = "http://cacis.nci.nih.gov", endpointInterface = "gov.nih.nci.cacis.CanonicalModelProcessorPortType")
public class CanonicalModelProcessor extends AcceptMessage {

    private static final String CACIS_NS = "http://cacis.nci.nih.gov";

    /**
     * Constructor
     * 
     * @param webServiceMessageReceiver Mirth/Mule webServiceMessageReceiver
     */
    public CanonicalModelProcessor(WebServiceMessageReceiver webServiceMessageReceiver) {
        super(webServiceMessageReceiver);
    }

    /**
     * Method accepts canonical data for processing
     * 
     * @param request CaCISRequest
     * @return response CaCISResponse
     * @throws AcceptCanonicalFault Fault
     * @throws
     */
    @WebResult(name = "caCISResponse", targetNamespace = CACIS_NS, partName = "parameter")
    @WebMethod
    public gov.nih.nci.cacis.CaCISResponse acceptCanonical(
            @WebParam(partName = "parameter", name = "caCISRequest", targetNamespace = CACIS_NS) CaCISRequest request)
            throws AcceptCanonicalFault {

        final CaCISResponse response = new CaCISResponse();

        final StringWriter sw = new StringWriter();
        try {
            final JAXBContext jc = JAXBContext.newInstance(CaCISRequest.class);
            final Marshaller m = jc.createMarshaller();

            final PrintWriter pw = new PrintWriter(sw);
            m.marshal(request, pw);
            response.setStatus(ResponseStatusType.SUCCESS);
        } catch (JAXBException jaxE) {
            throw new AcceptCanonicalFault("Error Marshalling object", jaxE);
        }

        try {
            String mcResponse = webServiceMessageReceiver.processData(sw.toString());

            if (mcResponse != null
                    && (mcResponse.indexOf("Error") > -1 || mcResponse.indexOf("Exception") > -1
                            || mcResponse.indexOf("ERROR") > -1 || mcResponse.indexOf("error") > -1)) {
                mcResponse = StringUtils.remove(mcResponse, "SUCCESS:");
                String channelUid = StringUtils.substringBetween(mcResponse, "(", ")");
                if(channelUid != null){
                    mcResponse = StringUtils.remove(mcResponse, "("+channelUid+")");
                }
                throw new AcceptCanonicalFault(StringUtils.substring(mcResponse, StringUtils.lastIndexOf(mcResponse, ':')));
            }
            response.setStatus(ResponseStatusType.SUCCESS);
            return response;
            // CHECKSTYLE:OFF
        } catch (Exception e) {
            // CHECKSTYLE:ON
            //throw new AcceptCanonicalFault("Error processing message!" + e.getMessage(), e);
            throw new AcceptCanonicalFault(e.getMessage(), e);
        }
    }

};
