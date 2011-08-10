package gov.nih.nci.cacis.sa.mirthconnect;

import com.mirth.connect.connectors.ws.WebServiceMessageReceiver;
import gov.nih.nci.cacis.AcceptSourceFault;
import gov.nih.nci.cacis.AcceptSourcePortType;
import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.CaCISResponse;
import gov.nih.nci.cacis.ResponseStatusType;
import gov.nih.nci.cacis.common.systest.AbstractJaxWsTest;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.ws.soap.SOAPFaultException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class SemanticAdapterJaxWSSystemTest extends AbstractJaxWsTest {

    /**
     * Cosntant value for the endpoint address
     */
    protected static final String ADDRESS = "http://localhost:18081/services/SA?wsdl";
    @Mock
    WebServiceMessageReceiver webServiceMessageReceiver;

    private EndpointImpl ep;
    private JaxWsProxyFactoryBean factory;

    /**
     * EndpointImpl
     *
     * @return Endpoint
     */
    @Override
    protected EndpointImpl getEndpoint() {
        return this.ep;
    }


    /**
     * Setups up namespace and Endpoint
     *
     * @throws Exception - exception thrown
     */
    @Before
    public void setup() throws Exception { // NOPMD - setUpBus throws Exception
        MockitoAnnotations.initMocks(this);

        final SemanticAdapter service = new SemanticAdapter(webServiceMessageReceiver);
        ep = new EndpointImpl(getBus(), service);
        ep.publish(ADDRESS);


        factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(AcceptSourcePortType.class);
        // specify the URL. We are using the in memory test container
        factory.setAddress(ADDRESS);

    }

    @Test
    public void invoke() throws Exception {

        when(webServiceMessageReceiver.processData(anyString())).thenReturn("");

        final CaCISRequest request = new CaCISRequest();

        final AcceptSourcePortType client = (AcceptSourcePortType) factory.create();
        final CaCISResponse response = client.acceptSource(request);
        assertEquals(ResponseStatusType.SUCCESS, response.getStatus());

        verify(webServiceMessageReceiver).processData(anyString());
    }




}