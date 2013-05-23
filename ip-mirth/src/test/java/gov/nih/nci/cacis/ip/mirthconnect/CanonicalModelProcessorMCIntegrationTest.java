/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;

import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.CanonicalModelProcessorPortType;
import gov.nih.nci.cacis.ClinicalData;
import gov.nih.nci.cacis.ClinicalMetadata;
import gov.nih.nci.cacis.DocumentType;
import gov.nih.nci.cacis.RoutingInstructions;
import gov.nih.nci.cacis.cdw.BaseVirtuosoIntegrationTest;
import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.binding.soap.SoapTransportFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Node;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-ip-mirth-test.xml")
public class CanonicalModelProcessorMCIntegrationTest extends BaseVirtuosoIntegrationTest {

    public static final String ADDRESS = "http://localhost:18081/services/CanonicalModelProcessor?wsdl";
    private static final Log LOG = LogFactory.getLog(CanonicalModelProcessorMCIntegrationTest.class);

    private static final String GRPH_GROUP_STUDY_ID = "mc_study_id";
    private static final String GRPH_GROUP_SITE_ID = "mc_site_id";
    private static final String GRPH_GROUP_P1_ID = "mc_patient_id";

    private static final String GRPH_GROUP_URI_STR_STUDY1 = CACIS_NS + "/" + GRPH_GROUP_STUDY_ID;
    private static final String GRPH_GROUP_URI_STR_SITE1 = GRPH_GROUP_URI_STR_STUDY1 + "/" + GRPH_GROUP_SITE_ID;
    private static final String GRPH_GROUP_URI_STR_P1 = GRPH_GROUP_URI_STR_SITE1 + "/" + GRPH_GROUP_P1_ID;

    @Override
    @Before
    public void init() throws AuthzProvisioningException, URISyntaxException {
        super.init();

        addNamespace("ns2", "http://cacis.nci.nih.gov");
        virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_URI_STR_SITE1);
        virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_URI_STR_STUDY1);
        virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_URI_STR_P1);
    }

    @Test
    public void invokeJaxWS() throws Exception {

        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(CanonicalModelProcessorPortType.class);
        // specify the URL. We are using the in memory test container
        factory.setAddress(ADDRESS);
        
        final JAXBContext jc = JAXBContext.newInstance(CaCISRequest.class);
        final InputStream is = getClass().getClassLoader().getResourceAsStream("CMP_valid_CR.xml");
        final CaCISRequest request = 
            (CaCISRequest) jc.createUnmarshaller().unmarshal(is);
        
        final CanonicalModelProcessorPortType client = (CanonicalModelProcessorPortType) factory.create();
       
        client.acceptCanonical(request);
    }

    @Test
    public void invokeSOAP() throws Exception {

        final Node res = invoke(ADDRESS, SoapTransportFactory.TRANSPORT_ID,
                getValidMessage().getBytes());
        assertNotNull(res);
        assertValid("//ns2:caCISResponse[@status='SUCCESS']", res);
        LOG.info("Echo response: " + res.getTextContent());

    }


    @Test
    public void failSchemaValidation() throws Exception {

        final URL url = getClass().getClassLoader().getResource("CMP_invalid_schema_soap.xml");
        String request = FileUtils.readFileToString(new File(url.toURI()));
        final Node res = invoke(ADDRESS, SoapTransportFactory.TRANSPORT_ID,
                request.getBytes());
        assertNotNull(res);

    }
}
