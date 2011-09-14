package gov.nih.nci.cacis.openxds.service;

import gov.nih.nci.cacis.openxds.ServiceClientFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class XdsRepositoryProxy {

    private ServiceClientFactory serviceClientFactory;
    private static final Log LOG = LogFactory.getLog(XdsRepositoryProxy.class);


    /**
     * Service name
     *
     * @return name
     */
    public String getServiceName() {
        return "R.b";
    }

    /**
     * provideAndRegisterDocumentSetRequest
     *
     * @param request OMElement
     * @return OMElement
     * @throws AxisFault fault
     */
    public OMElement provideAndRegisterDocumentSetRequest
    (OMElement request) throws AxisFault {
        LOG.debug("in Proxy provideAndRegisterDocumentSetRequest");

        OMElement response = null;
        try {
            final ServiceClient sender = getServiceClientFactory().
                    getRepositoryServiceClient("urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b"
                    );
            response = sender.sendReceive(request);
        } catch (URISyntaxException e) {
            throw new AxisFault(e.getMessage()); //NOPMD needs to throw AxisFault
        } catch (IOException e) {
           throw new AxisFault(e.getMessage()); //NOPMD needs to throw AxisFault
        }
        return response;

    }


    /**
     *  RetrieveDocumentSetRequest operation
     * @param request retrieveDocumentSetRequest
     * @return Response
     * @throws AxisFault Fault
     */
    public OMElement retrieveDocumentSetRequest(OMElement request) throws AxisFault {
        LOG.debug("in Proxy retrieveDocumentSetRequest");

              OMElement response = null;
              try {
                  final ServiceClient sender = getServiceClientFactory().
                          getRepositoryServiceClient("urn:ihe:iti:2007:RetrieveDocumentSet"
                          );
                  response = sender.sendReceive(request);
              } catch (URISyntaxException e) {
                  throw new AxisFault(e.getMessage()); //NOPMD needs to throw AxisFault
              } catch (IOException e) {
                 throw new AxisFault(e.getMessage()); //NOPMD needs to throw AxisFault
              }
              return response;

    }
    /**
     * getter method
     *
     * @return ServiceClientFactory
     * @throws IOException          Exception
     * @throws URISyntaxException Exception
     */
    public ServiceClientFactory getServiceClientFactory() throws IOException, URISyntaxException {
        if (serviceClientFactory == null) {
            serviceClientFactory = new ServiceClientFactory();
        }
        return serviceClientFactory;
    }

    /**
     * Setter for ServiceClientFactory
     *
     * @param serviceClientFactory ServiceClientFactory
     */
    public void setServiceClientFactory(ServiceClientFactory serviceClientFactory) {
        this.serviceClientFactory = serviceClientFactory;
    }

    /**
     * called by XDSRawXMLInOutMessageReceiver
     *
     * @param inMessage in message
     */
    public void setMessageContextIn(MessageContext inMessage) {
        //currentMessageContext = inMessage ;
    }
}
