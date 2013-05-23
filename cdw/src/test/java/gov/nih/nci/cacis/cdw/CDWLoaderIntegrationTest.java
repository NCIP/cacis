/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests CDWLoader.
 *
 * @author bpickeral
 * @since Jul 19, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-cdw-test.xml")
public class CDWLoaderIntegrationTest extends BaseVirtuosoIntegrationTest {

    @Autowired
    private CDWLoader loader;

    private static final String GRPH_GROUP_STUDY_ID = "study_id_test";
    private static final String GRPH_GROUP_SITE_ID = "site_id_test";
    private static final String GRPH_GROUP_P1_ID = "patient_id_test";

    private static final String GRPH_GROUP_STUDY = CACIS_NS + GRPH_GROUP_STUDY_ID;
    private static final String GRPH_GROUP_SITE = GRPH_GROUP_STUDY + "/" + GRPH_GROUP_SITE_ID;
    private static final String GRPH_GROUP_P1 = GRPH_GROUP_SITE + "/" + GRPH_GROUP_P1_ID;


    @Before
    public void before() throws URISyntaxException, RepositoryConfigException, RepositoryException,
            IOException, AuthzProvisioningException {
        super.init();
        graphIRI = repository.getValueFactory().createURI(loader.generateContext());
        site1URI = virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_SITE);
        study1URI = virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_STUDY);
        p1URI = virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_P1);
    }

    @Test
    public void loadString() throws Exception {
        final File xslF = new File(getClass().getClassLoader().getResource("caCISRequestSample3.xml").toURI());

        final String xmlString = FileUtils.readFileToString(xslF);

        loader.load(xmlString, graphIRI.toString(), GRPH_GROUP_STUDY_ID, GRPH_GROUP_SITE_ID, GRPH_GROUP_P1_ID);

        assertEquals(557, getNoOfTriples(dbaSimpleJdbcTemplate, graphIRI));

        // check rdf was added to graph to groups
        assertEquals(557, getNoOfTriples(dbaSimpleJdbcTemplate, site1URI));
        assertEquals(557, getNoOfTriples(dbaSimpleJdbcTemplate, study1URI));
        assertEquals(557, getNoOfTriples(dbaSimpleJdbcTemplate, p1URI));
    }

}
