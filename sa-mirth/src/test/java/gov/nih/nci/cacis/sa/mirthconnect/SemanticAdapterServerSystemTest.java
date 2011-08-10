package gov.nih.nci.cacis.sa.mirthconnect;

import gov.nih.nci.cacis.AcceptSourceFault;
import gov.nih.nci.cacis.AcceptSourcePortType;
import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.CaCISResponse;
import gov.nih.nci.cacis.ClinicalData;
import gov.nih.nci.cacis.ResponseStatusType;
import gov.nih.nci.cacis.common.systest.AbstractBusTestServer;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.testutil.common.AbstractBusClientServerTestBase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.ws.soap.SOAPFaultException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class SemanticAdapterServerSystemTest extends AbstractBusClientServerTestBase {

    private AcceptSourcePortType semanticAdapter;

    @BeforeClass
    public static void startServers() {
        assertTrue("server did not launch correctly", launchServer(SemanticAdapterServer.class,
                true));
    }

    /**
     * Setup test instance objects
     */
    @Before
    public void setup() {
        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(AcceptSourcePortType.class);
        // specify the URL. We are using the in memory test container
        factory.setAddress(SemanticAdapterServer.ADDRESS);


        this.semanticAdapter = (AcceptSourcePortType) factory.create();
    }



    @Test(expected = SOAPFaultException.class)
    public void exception() throws Exception {

        final CaCISRequest request = new CaCISRequest();
        ClinicalData cData = new ClinicalData();

        request.setSourceData(cData);

        semanticAdapter.acceptSource(request);


    }
}
