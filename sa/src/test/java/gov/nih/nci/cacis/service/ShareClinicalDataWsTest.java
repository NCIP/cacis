package gov.nih.nci.cacis.service;

import gov.nih.nci.cacis.sa.service.ShareClinicalDataWs;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.testutil.common.AbstractBusClientServerTestBase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author kherm manav.kher@semanticbits.com
 */

public class ShareClinicalDataWsTest extends AbstractBusClientServerTestBase {

    private JaxWsProxyFactoryBean factory;
    private ShareClinicalDataWs service;

    /**
     * start test servers
     */
    @BeforeClass
    public  static void startServers() {
        assertTrue("Could not launch server",
                launchServer(ShareClinicalDataServer.class, true));
    }

    @Before
    public void init() {
        if (factory == null) {
            factory = new JaxWsProxyFactoryBean();
            factory.setServiceClass(ShareClinicalDataWs.class);
            //        tolven service URL.
            //        Todo change to using a property
            factory.setAddress(ShareClinicalDataServer.ADDRESS);
            service = (ShareClinicalDataWs) factory.create();
        }
    }


    @Test
    public void invoke() {
        String response = service.recieve("Sample");
        assertNotNull(response);

    }

}
