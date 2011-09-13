package gov.nih.nci.cacis;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.WSDL2Constants;


import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class XdsRegistryProxy {


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

        ConfigurationContext configctx = null;

        try {
            configctx = getContext();
        } catch (URISyntaxException e) {
            throw new AxisFault("", e);
        }

        final ServiceClient sender = new ServiceClient(configctx, null);
        final String action = "urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b";
        final boolean enableMTOM = true;

        sender.setOptions(getOptions(action, enableMTOM, "http://localhost:8020/axis2/services/xdsrepositoryb"));
        sender.engageModule("addressing");
        return sender.sendReceive(request);
    }

    private ConfigurationContext getContext() throws AxisFault, URISyntaxException {
        final URL axis2repo = XdsRegistryProxy.class.getResource("axis2repository");
        final URL axis2xml = XdsRegistryProxy.class.getResource("axis2repository/axis2.xml");
        final ConfigurationContext configctx = ConfigurationContextFactory
                .createConfigurationContextFromURIs(axis2xml, axis2repo);
        return configctx;
    }

    private Options getOptions(String action, boolean enableMTOM, String url) {
        final Options options = new Options();
        options.setAction(action);
        options.setProperty(WSDL2Constants.ATTRIBUTE_MUST_UNDERSTAND, "1");
        options.setTo(new EndpointReference(url));
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);

        if (enableMTOM) {
            options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
        } else {
            options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_FALSE);
        }
        //use SOAP12,
        options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        return options;
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
