/*
*
*/

package gov.nih.nci.cacis.sa.mirthconnect;

import com.mirth.connect.connectors.ws.AcceptMessage;
import com.mirth.connect.connectors.ws.WebServiceMessageReceiver;
import gov.nih.nci.cacis.AcceptSourceFault;
import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.CaCISResponse;
import gov.nih.nci.cacis.ResponseStatusType;
import org.apache.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * This class was generated by Apache CXF 2.3.2
 * 2011-08-09T13:14:20.648-04:00
 * Generated source version: 2.3.2
 */


@WebService(serviceName = "SemanticAdapter",
                      portName = "AcceptSource_Port_Soap11",
                      targetNamespace = "http://cacis.nci.nih.gov",
                      endpointInterface = "gov.nih.nci.cacis.AcceptSourcePortType")
public class SemanticAdapter extends AcceptMessage {

    /**
     * Constructor
     * @param webServiceMessageReceiver  MC Mule webServiceMessageReceiver
     */
    public SemanticAdapter(WebServiceMessageReceiver webServiceMessageReceiver) {
        super(webServiceMessageReceiver);
    }


    private static final Logger LOG = Logger.getLogger(SemanticAdapter.class.getName());


    /**
     *  AcceptSource operation
     *  accepts data from Clinical Source
     *  system and sends it to Mirth Connect
     *  for processing
     *
     * @param parameter caCISRequest
     * @return CaCISResponse
     * @throws AcceptSourceFault Web Service Fault
     */
    @WebResult(name = "caCISResponse", targetNamespace = "http://cacis.nci.nih.gov", partName = "parameter")
    @WebMethod
    public CaCISResponse acceptSource(
            @WebParam(partName = "parameter", name = "caCISRequest", targetNamespace = "http://cacis.nci.nih.gov")
            CaCISRequest parameter
    ) throws AcceptSourceFault {

        LOG.info("Executing operation acceptSource");

        try {
            final CaCISResponse response = new CaCISResponse();
            webServiceMessageReceiver.processData(parameter.toString());
            response.setStatus(ResponseStatusType.SUCCESS);

            return response;
            //CHECKSTYLE:OFF
        } catch (Exception ex) {
            //CHECKSTYLE:ON
            LOG.warn(ex);
            throw new AcceptSourceFault("Error accepting Data from Source System", ex);
        }
    }

}
