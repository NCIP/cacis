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

import static org.junit.Assert.assertTrue;
import gov.nih.nci.cacis.sa.CaCISRequest;
import gov.nih.nci.cacis.sa.CaCISResponse;
import gov.nih.nci.cacis.sa.ResponseStatusType;
import gov.nih.nci.cacis.sa.AcceptSourceFault;
import gov.nih.nci.cacis.sa.AcceptSourcePortType;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author monish.dombla@semanticbits.com
 * @since Aug 11, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-sa-mirth-test.xml")
public class SemanticAdapterChannelIntegrationTest {

    private static final String ADDRESS = "http://localhost:18091/services/SemanticAdapter?wsdl";


    /**
     * This test calls out acceptSource(..) operation on SematicAdapter WS.
     * The SemanticAdapter WS is the source connector to SemanticAdapterChannel in Mirth.
     * @throws Exception exception
     */
    @Test
    public void invokeSemanticAdapterWS() throws Exception { //NOPMD
        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(AcceptSourcePortType.class);
        factory.setAddress(ADDRESS);
        final AcceptSourcePortType client = (AcceptSourcePortType) factory.create();

        final InputStream sampleMessageIS = FileUtils.openInputStream(new File(getClass().getClassLoader()
                .getResource("SARequestSample.xml").toURI()));

        final JAXBContext jc = JAXBContext.newInstance(CaCISRequest.class);
        final Unmarshaller unm = jc.createUnmarshaller();

        final CaCISRequest request = (CaCISRequest) unm.unmarshal(sampleMessageIS);
        
        final Marshaller m = jc.createMarshaller();
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        m.marshal(request, pw);
        final String reqStr = sw.toString();
        
        final CaCISResponse response = client.acceptSource(request);
        assertTrue(response.getStatus() == ResponseStatusType.SUCCESS);

    }
    
    
    /**
     * This test calls out acceptSource(..) operation on SematicAdapter WS.
     * and expects the service to return an Exception
     *
     * @throws Exception exception
     */
    //ToDo Remove ignore after CDO-1257
    @Ignore
    @Test(expected = AcceptSourceFault.class)
    public void failure() throws Exception { //NOPMD
        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(AcceptSourcePortType.class);
        factory.setAddress(ADDRESS);
        final AcceptSourcePortType client = (AcceptSourcePortType) factory.create();

        final InputStream sampleMessageIS = FileUtils.openInputStream(new File(getClass().getClassLoader()
                .getResource("SARequestSample.xml").toURI()));

        final JAXBContext jc = JAXBContext.newInstance(CaCISRequest.class);
        final CaCISRequest request = (CaCISRequest) jc.createUnmarshaller().unmarshal(sampleMessageIS);
        client.acceptSource(request);
    }

}

