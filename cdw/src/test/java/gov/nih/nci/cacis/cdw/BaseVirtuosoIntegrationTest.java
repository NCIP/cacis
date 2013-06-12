/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.test.AbstractCXFTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openrdf.model.URI;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base test class for interaction with the Virtuoso DB.
 * @author bpickeral
 * @since Aug 1, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-cdw-test.xml")
public class BaseVirtuosoIntegrationTest extends AbstractCXFTest {

    @Autowired
    protected SimpleJdbcTemplate dbaSimpleJdbcTemplate;

    @Autowired
    protected Repository repository;

    @Autowired
    protected RepositoryConnection repoCon;

    @Value("${cacis.virtuoso.db.url}")
    protected String dbUrl;

    @Value("${cacis.virtuoso.db.driver}")
    protected String driver;

    protected static final String CACIS_NS = "http://cacis.nci.nih.gov/";
    public static final String SOAP_MSG_FILENAME = "CMP_sample_soap.xml";

    private static final String NOBODY = "nobody";

    protected static URI graphIRI = null;

    protected static VirtuosoUtils virtuosoUtils;
    protected URI site1URI;
    protected URI study1URI;
    protected URI p1URI;

    /**
     * Setup method for Integration testing
     *
     * @throws AuthzProvisioningException - error thrown, if any
     * @throws URISyntaxException - error thrown, if any
     */
    @Before
    public void init() throws AuthzProvisioningException, URISyntaxException {
        virtuosoUtils = new VirtuosoUtils(dbaSimpleJdbcTemplate);

        virtuosoUtils.setDefaultPermissions(NOBODY, 0);
    }

    /**
     * cleanup method
     */
    @After
    public void cleanUp() throws AuthzProvisioningException {
        virtuosoUtils.clearGraph(repoCon, graphIRI);
    }
    
    /**
     * Makes sure the graph is empty before testing
     */
    @Test
    public void checkGraphIsEmpty() {
        assertEquals(0, getNoOfTriples(dbaSimpleJdbcTemplate, graphIRI));
    }


    protected int getNoOfTriples(SimpleJdbcTemplate jdbcTemplate, URI graphIRI) {
        final String query = "SPARQL SELECT count(*) FROM <" + graphIRI + "> WHERE {?x ?y ?z}";

        final Object[] args = {};
        return jdbcTemplate.queryForInt(query, args);
    }

    /**
     * Gets a valid Message. Default implementation reads a valid SOAPMessage that has been serialized to a file.
     *
     * @return string representation of a valid message
     * @throws IOException on I/O error
     * @throws URISyntaxException on URI Syntax error
     */
    protected String getValidMessage() throws IOException, URISyntaxException {
        final URL url = getClass().getClassLoader().getResource(SOAP_MSG_FILENAME);
        return FileUtils.readFileToString(new File(url.toURI()));
    }

}
