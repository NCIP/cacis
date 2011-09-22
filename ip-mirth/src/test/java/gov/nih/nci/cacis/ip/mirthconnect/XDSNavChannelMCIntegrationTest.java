package gov.nih.nci.cacis.ip.mirthconnect;

import gov.nih.nci.cacis.xds.authz.service.DocumentAccessManager;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.binding.soap.SoapTransportFactory;
import org.apache.cxf.test.AbstractCXFTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Node;

import java.io.File;
import java.net.URL;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-ip-mirth-test.xml")
public class XDSNavChannelMCIntegrationTest extends AbstractCXFTest {

    @Autowired
    private DocumentAccessManager manager;


    @Test
    public void invokeXDSNavChannel() throws Exception {

        final URL url = getClass().getClassLoader().getResource("CMP_sample_soap.xml");
        String messageXml = FileUtils.readFileToString(new File(url.toURI()));

        final Node res = invoke(CanonicalModelProcessorMCIntegrationTest.ADDRESS, SoapTransportFactory.TRANSPORT_ID,
                messageXml.getBytes());
        assertNotNull(res);
        assertValid("//ns2:caCISResponse[@status='SUCCESS']", res);


    }
}
