/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software was developed in conjunction with the
 * National Cancer Institute (NCI) by NCI employees and subcontracted parties. To the extent government employees are
 * authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 *
 * This gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software License (the License) is between NCI and You. You (or Your) shall
 * mean a person or an entity, and all other entities that control, are controlled by, or are under common control with
 * the entity. Control for purposes of this definition means (i) the direct or indirect power to cause the direction or
 * management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the
 * outstanding shares, or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software to (i) use, install, access, operate, execute, copy,
 * modify, translate, market, publicly display, publicly perform, and prepare derivative works of the
 * gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software; (ii) distribute and have distributed to and by third parties the
 * gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software and any modifications and derivative works thereof; and (iii) sublicense
 * the foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
 * third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting or right of
 * payment from You or Your sub-licensees for the rights granted under this License. This License is granted at no
 * charge to You.
 *
 * Your redistributions of the source code for the Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code
 * form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: This
 * product includes software developed by the National Cancer Institute and subcontracted parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or any subcontracted party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or theany of the subcontracted parties, except as required to comply with
 * the terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs
 * and into any third party proprietary programs. However, if You incorporate the Software into third party proprietary
 * programs, You agree that You are solely responsible for obtaining any permission from such third parties required to
 * incorporate the Software into such third party proprietary programs and for informing Your sub-licensees, including
 * without limitation Your end-users, of their obligation to secure any required permissions from such third parties
 * before incorporating the Software into such third party proprietary software programs. In the event that You fail to
 * obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to the
 * extent prohibited by law, resulting from Your failure to obtain such permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and
 * to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses
 * of modifications of the Software, or any derivative works of the Software as a whole, provided Your use,
 * reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, ANY OF ITS SUBCONTRACTED PARTIES OR THEIR AFFILIATES BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
