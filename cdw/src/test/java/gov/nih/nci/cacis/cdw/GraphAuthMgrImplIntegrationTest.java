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
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openrdf.model.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Jul 18, 2011
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-cdw-test.xml")
public class GraphAuthMgrImplIntegrationTest extends BaseVirtuosoIntegrationTest {

    @Autowired
    private GraphAuthzMgr graphAuthzMgr;

    private SimpleJdbcTemplate site1UserSimpleJdbcTemplate;
    private SimpleJdbcTemplate study1UserSimpleJdbcTemplate;
    private SimpleJdbcTemplate p1UserSimpleJdbcTemplate;

    private static final String SITE_USER_1 = "siteuser1";
    private static final String STUDY_USER_1 = "studyuser1";
    private static final String P_USER_1 = "puser1";

    private static final String SMPL_RDF1_CONTEXT = CACIS_NS + "sample/rdf/1";
    private static final String GRPH_GROUP_URI_STR_STUDY1 = CACIS_NS + "study1";
    private static final String GRPH_GROUP_URI_STR_SITE1 = GRPH_GROUP_URI_STR_STUDY1 + "/site1";
    private static final String GRPH_GROUP_URI_STR_P1 = GRPH_GROUP_URI_STR_SITE1 + "/p1";

    private static final String SMPL_RDF1_NM = "sample-rdf.xml";

    private File smplRdfFile1;


    @Override
    @Before
    public void init() throws AuthzProvisioningException, URISyntaxException {
        super.init();

        site1URI = virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_URI_STR_SITE1);
        study1URI = virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_URI_STR_STUDY1);
        p1URI = virtuosoUtils.createGraphGroup(repository, GRPH_GROUP_URI_STR_P1);
        graphIRI = repository.getValueFactory().createURI(SMPL_RDF1_CONTEXT);

        smplRdfFile1 = new File(getClass().getClassLoader().getResource(SMPL_RDF1_NM).toURI());
        virtuosoUtils.loadGraphFromRDFXml(repoCon, smplRdfFile1, graphIRI);

        final DataSource ds1 = VirtuosoUtils.createDataSource(dbUrl, SITE_USER_1, SITE_USER_1, driver);
        site1UserSimpleJdbcTemplate = VirtuosoUtils.getSimpleJdbcTemplate(ds1);
        final DataSource ds2 = VirtuosoUtils.createDataSource(dbUrl, STUDY_USER_1, STUDY_USER_1, driver);
        study1UserSimpleJdbcTemplate = VirtuosoUtils.getSimpleJdbcTemplate(ds2);
        final DataSource ds3 = VirtuosoUtils.createDataSource(dbUrl, P_USER_1, P_USER_1, driver);
        p1UserSimpleJdbcTemplate = VirtuosoUtils.getSimpleJdbcTemplate(ds3);

        virtuosoUtils.createUser(SITE_USER_1, SITE_USER_1);
        virtuosoUtils.createUser(STUDY_USER_1, STUDY_USER_1);
        virtuosoUtils.createUser(P_USER_1, P_USER_1);

        virtuosoUtils.addUserToGraphGroup(SITE_USER_1, site1URI.toString());
        virtuosoUtils.addUserToGraphGroup(STUDY_USER_1, study1URI.toString());
        virtuosoUtils.addUserToGraphGroup(P_USER_1, p1URI.toString());
    }

    /**
     * cleanup method
     * @throws AuthzProvisioningException - exception thrown, if any
     */
    @Override
    @After
    public void cleanUp() throws AuthzProvisioningException {
        super.cleanUp();
        virtuosoUtils.dropUser(SITE_USER_1);
        virtuosoUtils.dropUser(STUDY_USER_1);
        virtuosoUtils.dropUser(P_USER_1);
    }
    
    /**
     * overriding this method from base class, as it wont apply for this test class
     */
    @Override
    public void checkGraphIsEmpty() {
        assertTrue(true);
    }

    /**
     * Checks adding graph to graph groups
     *
     * @throws AuthzProvisioningException - error thrown, if any
     */
    @Test
    public void checkAddAndRemoveGraphToGroups() throws AuthzProvisioningException {
        assertNotNull(graphIRI);

        // checks loaded graph has triples
        final int grphTriplesCnt = getNoOfTriples(dbaSimpleJdbcTemplate, graphIRI);
        assertEquals(557, grphTriplesCnt);

        // checks groups dont have triples accessible before graph is added to the groups
        assertEquals(0, getNoOfTriples(dbaSimpleJdbcTemplate, site1URI));
        assertEquals(0, getNoOfTriples(dbaSimpleJdbcTemplate, study1URI));
        assertEquals(0, getNoOfTriples(dbaSimpleJdbcTemplate, p1URI));

        final Set<URI> grphGrps = new HashSet<URI>();
        grphGrps.add(site1URI);
        grphGrps.add(study1URI);
        grphGrps.add(p1URI);

        // adds graph to groups
        graphAuthzMgr.add(graphIRI, grphGrps);

        // checks after adding graph to groups
        assertEquals(557, getNoOfTriples(dbaSimpleJdbcTemplate, site1URI));
        assertEquals(557, getNoOfTriples(dbaSimpleJdbcTemplate, study1URI));
        assertEquals(557, getNoOfTriples(dbaSimpleJdbcTemplate, p1URI));

        // removes graph from groups
        graphAuthzMgr.remove(graphIRI, grphGrps);

        // checks after removing graphs from groups
        assertEquals(0, getNoOfTriples(dbaSimpleJdbcTemplate, site1URI));
        assertEquals(0, getNoOfTriples(dbaSimpleJdbcTemplate, study1URI));
        assertEquals(0, getNoOfTriples(dbaSimpleJdbcTemplate, p1URI));
    }

    /**
     * Checks permissions on various graph groups as per user
     * @throws AuthzProvisioningException - error thrown, if any
     */
    @Test
    public void checkAccessForUsers() throws AuthzProvisioningException {
        final Set<URI> grphGrps = new HashSet<URI>();
        grphGrps.add(site1URI);
        grphGrps.add(study1URI);
        grphGrps.add(p1URI);

        // adds graph to groups
        graphAuthzMgr.add(graphIRI, grphGrps);

        //check permissions for site1user
        assertEquals(557, getNoOfTriples(site1UserSimpleJdbcTemplate, site1URI));
        assertEquals(0, getNoOfTriples(site1UserSimpleJdbcTemplate, study1URI));
        assertEquals(0, getNoOfTriples(site1UserSimpleJdbcTemplate, p1URI));

        //check permissions for study1user
        assertEquals(0, getNoOfTriples(study1UserSimpleJdbcTemplate, site1URI));
        assertEquals(557, getNoOfTriples(study1UserSimpleJdbcTemplate, study1URI));
        assertEquals(0, getNoOfTriples(study1UserSimpleJdbcTemplate, p1URI));

        //check permissions for p1user
        assertEquals(0, getNoOfTriples(p1UserSimpleJdbcTemplate, site1URI));
        assertEquals(0, getNoOfTriples(p1UserSimpleJdbcTemplate, study1URI));
        assertEquals(557, getNoOfTriples(p1UserSimpleJdbcTemplate, p1URI));

        //removes graph from groups
        graphAuthzMgr.remove(graphIRI, grphGrps);
    }

}
