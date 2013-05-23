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

import gov.nih.nci.cacis.cdw.BaseVirtuosoIntegrationTest;
import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;

import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.binding.soap.SoapTransportFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Node;

/**
 * Tests full round trip from CanonicalModelProcessor to Virtuoso.
 * @author bpickeral
 * @since Aug 2, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-ip-mirth-test.xml")
public class CanonicalModelProcessorVirtuosoIntegrationTest extends BaseVirtuosoIntegrationTest {

    public static final String ADDRESS = "http://localhost:18081/services/CanonicalModelProcessor?wsdl";
    private static final Log LOG = LogFactory.getLog(CanonicalModelProcessorVirtuosoIntegrationTest.class);

    private static final String GRPH_GROUP_STUDY_ID = "study_id";
    private static final String GRPH_GROUP_SITE_ID = "site_id";
    private static final String GRPH_GROUP_P1_ID = "patient_id";

    private static final String GRPH_GROUP_STUDY = CACIS_NS + GRPH_GROUP_STUDY_ID;
    private static final String GRPH_GROUP_SITE = GRPH_GROUP_STUDY + "/" + GRPH_GROUP_SITE_ID;
    private static final String GRPH_GROUP_P1 = GRPH_GROUP_SITE + "/" + GRPH_GROUP_P1_ID;

    @Override
    @Before
    public void init() throws AuthzProvisioningException, URISyntaxException {
        super.init();
        addNamespace("ns2", "http://cacis.nci.nih.gov");

        site1URI = virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_SITE);
        study1URI = virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_STUDY);
        p1URI = virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_P1);
    }

    @Test
    public void invokeSOAP() throws Exception {

        final Node res = invoke(ADDRESS, SoapTransportFactory.TRANSPORT_ID,
                getValidMessage().getBytes());
        assertNotNull(res);
        LOG.info("Echo response: " + res.getTextContent());

        // Wait for MC to call CDWLoader
        Thread.sleep(30000);

        int grphTriplesCnt = getNoOfTriples(dbaSimpleJdbcTemplate, site1URI);
        assertTrue(grphTriplesCnt > 0);

        grphTriplesCnt = getNoOfTriples(dbaSimpleJdbcTemplate, study1URI);
        assertTrue(grphTriplesCnt > 0);

        grphTriplesCnt = getNoOfTriples(dbaSimpleJdbcTemplate, p1URI);
        assertTrue(grphTriplesCnt > 0);

    }

}
