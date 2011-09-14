package gov.nih.nci.cacis.openxds;

import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.WSDL2Constants;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class ServiceClientFactory {

    private ConfigurationContext configctx;
    private String repoServiceUrl, registryServiceUrl;

    /**
     * No args constructor
     *
     * @throws URISyntaxException Exception loading Axis configuration file
     */
    /**
     * No args constructor
     * @throws IOException    Exception loading Axis configuration file
     * @throws URISyntaxException Exception loading Axis configuration file
     */
    public ServiceClientFactory() throws IOException, URISyntaxException {
        initContext();
        final Properties props = new Properties();
        props.load(this.getClass().getClassLoader()
            .getResourceAsStream("cacis-xds-proxy-service.properties"));
       this.repoServiceUrl = props.getProperty("xds.repository.url");
    }

    /**
     * @param action IHE action
     * @return ServiceClient XDS Repository Client
     * @throws AxisFault Fault
     */
    public ServiceClient getRepositoryServiceClient(String action) throws AxisFault {

        final ServiceClient sender = new ServiceClient(getConfigctx(), null);
        final boolean enableMTOM = true;

        sender.setOptions(getOptions(action, enableMTOM, getRepoServiceUrl()));
        sender.engageModule("addressing");

        return sender;
    }


    /**
     * Very specific to openxds 1.0.1 deployment
     * Will need modification to be deployed into a
     * vanilla axis2 deployment as the axis2.xml
     * is usually in WEB-INF/conf and NOT in WEB-INF/classes/conf/axis2repository
     * as assumed here
     *
     * @throws AxisFault Fault
     * @throws URISyntaxException Exception
     */
    private void initContext() throws AxisFault, URISyntaxException {

        this.configctx = MessageContext.getCurrentMessageContext().getConfigurationContext();

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
     * Getter method
     *
     * @return ConfigurationContext
     */
    public ConfigurationContext getConfigctx() {
        return configctx;
    }

    /**
     * Setter method
     *
     * @param configctx ConfiguraitonContext for Axis
     */
    public void setConfigctx(ConfigurationContext configctx) {
        this.configctx = configctx;
    }

    /**
     * Getter method
     *
     * @return XDS Repository Service URL
     */
    public String getRepoServiceUrl() {
        return repoServiceUrl;
    }

    /**
     * Setter method
     *
     * @param repoServiceUrl Repository Service URL
     */
    public void setRepoServiceUrl(String repoServiceUrl) {
        this.repoServiceUrl = repoServiceUrl;
    }

    /**
     * Getter method
     *
     * @return XDS Registry Service URL
     */
    public String getRegistryServiceUrl() {
        return registryServiceUrl;
    }

    /**
     * Setter method
     *
     * @param registryServiceUrl Registry Serivce URL
     */
    public void setRegistryServiceUrl(String registryServiceUrl) {
        this.registryServiceUrl = registryServiceUrl;
    }
}
