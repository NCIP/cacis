/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.sa.mirthconnect;

import gov.nih.nci.cacis.common.util.CaCISURLClassLoader;
import gov.nih.nci.cacis.sa.AcceptSourceFault;
import gov.nih.nci.cacis.sa.CaCISRequest;
import gov.nih.nci.cacis.sa.CaCISResponse;
import gov.nih.nci.cacis.sa.ResponseStatusType;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mirth.connect.connectors.ws.AcceptMessage;
import com.mirth.connect.connectors.ws.WebServiceMessageReceiver;

/**
 * SA Web service implementation
 */
@WebService(serviceName = "SemanticAdapter", 
        portName = "AcceptSource_Port_Soap11", 
        targetNamespace = "http://cacis.nci.nih.gov", 
        endpointInterface = "gov.nih.nci.cacis.sa.AcceptSourcePortType")
public class SemanticAdapter extends AcceptMessage {
    
    private String customLibDir = "./custom-lib";

    /**
     * Constructor
     * 
     * @param webServiceMessageReceiver MC Mule webServiceMessageReceiver
     */
    public SemanticAdapter(WebServiceMessageReceiver webServiceMessageReceiver) {
        super(webServiceMessageReceiver);
    }

    private static final Logger LOG = Logger.getLogger(SemanticAdapter.class.getName());

    /**
     * AcceptSource operation accepts data from Clinical Source system and sends it to Mirth Connect for processing
     * 
     * @param parameter caCISRequest
     * @return CaCISResponse
     * @throws AcceptSourceFault Web Service Fault
     */
    @WebResult(name = "caCISResponse", targetNamespace = "http://cacis.nci.nih.gov", partName = "parameter")
    @WebMethod
    public CaCISResponse acceptSource(
            @WebParam(partName = "parameter", name = "caCISRequest", 
                    targetNamespace = "http://cacis.nci.nih.gov") CaCISRequest parameter)
            throws AcceptSourceFault {

        LOG.info("Executing operation acceptSource");

        final CaCISResponse response = new CaCISResponse();

        try {
            final String reqstr = getCaCISRequestxml(parameter);

            if (StringUtils.isEmpty(reqstr)) {
                throw new AcceptSourceFault("Error marshalling CaCISRequest!");
            }

            String mcResponse = webServiceMessageReceiver.processData(reqstr);

            LOG.info("MC RESPONSE:" + mcResponse);

            if (mcResponse != null
                    && (mcResponse.indexOf("Error") > -1 || mcResponse.indexOf("Exception") > -1
                            || mcResponse.indexOf("ERROR") > -1 || mcResponse.indexOf("error") > -1)) {
                mcResponse = StringUtils.remove(mcResponse, "SUCCESS:");
//                throw new AcceptSourceFault("Error processing Data from Source System: " + mcResponse);
                throw new AcceptSourceFault(mcResponse);
            }
            response.setStatus(ResponseStatusType.SUCCESS);
            return response;
            // CHECKSTYLE:OFF
        } catch (Exception ex) {
            // CHECKSTYLE:ON
            LOG.error(ex);
            throw new AcceptSourceFault(ex.getMessage(), ex);
        }
    }

    private String getCaCISRequestxml(final CaCISRequest parameter) {

        return AccessController.doPrivileged(new PrivilegedAction<String>() {

            public String run() {
                try {
                    final CaCISURLClassLoader cl = new CaCISURLClassLoader(customLibDir, ClassLoader
                            .getSystemClassLoader());
                    final Class jmClass = Class.forName("gov.nih.nci.cacis.sa.mirthconnect.JAXBMarshaller", true, cl);
                    final Method m = jmClass.getMethod("marshal", Object.class);
                    return (String) m.invoke(jmClass.newInstance(), parameter);
                    // CHECKSTYLE:OFF
                } catch (Exception ex) {
                    // CHECKSTYLE:ON
                    LOG.error("Error marshalling CaCISRequest!", ex);
                    return null;
                }
            }
        });

    }

    //Public getter/setters to support testing
    
    /**
     * @return the customLibDir
     */
    public String getCustomLibDir() {
        return customLibDir;
    }

    
    /**
     * @param customLibDir the customLibDir to set
     */
    public void setCustomLibDir(String customLibDir) {
        this.customLibDir = customLibDir;
    }
    
    

}
