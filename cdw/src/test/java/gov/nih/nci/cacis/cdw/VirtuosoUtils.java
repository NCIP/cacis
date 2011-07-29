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

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import org.openrdf.model.URI;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import virtuoso.sesame2.driver.VirtuosoRepository;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Utility class for handling with virtuoso administration and other generic operations
 * 
 * @author vinodh.rc@semanticbits.com
 * 
 */
public class VirtuosoUtils {

    private SimpleJdbcTemplate dbaSimpleJdbcTemplate;

    /**
     * @param dbaSimpleJdbcTemplate - SimpleJDBCTemplate to access Virtuoso DB
     * @throws AuthzProvisioningException - error thrown, if any
     */
    public VirtuosoUtils(SimpleJdbcTemplate dbaSimpleJdbcTemplate) throws AuthzProvisioningException {
        super();
        if (dbaSimpleJdbcTemplate == null) {
            throw new AuthzProvisioningException("SimpleJDBCTemplate to access Virtuoso DB is required for the DBA user!");
        }
        this.dbaSimpleJdbcTemplate = dbaSimpleJdbcTemplate;
    }
    
    /**
     * Sets default permissions
     * 
     * @param userid - String representing the user id
     * @param permission - enter permission, noaccess(0) or read(1)
     * @throws AuthzProvisioningException - error thrown, if any
     */
    public void setDefaultPermissions(String userid, int permission) throws AuthzProvisioningException {
        try {
            final Object[] userset = { userid, Integer.valueOf(permission) };
            dbaSimpleJdbcTemplate.update("{ call DB.DBA.RDF_DEFAULT_USER_PERMS_SET(?, ?) }", userset);
        } catch (DataAccessException e) {
            throw new AuthzProvisioningException("Error setting default permission for user!", e);
        }
    }

    /**
     * Creates new user in the virtuoso
     * 
     * @param userid - String representing the user id
     * @param password - String representing the password
     * @throws AuthzProvisioningException - error thrown, if any
     */
    public void createUser(String userid, String password) throws AuthzProvisioningException {
        try {
            final Object[] userset = { userid, password };
            dbaSimpleJdbcTemplate.update("{ call user_create(?, ?, vector('DAV_ENABLE', 1, 'SQL_ENABLE', 1))}", userset);
            dbaSimpleJdbcTemplate.update("grant SPARQL_UPDATE to " + userid);
        } catch (DataAccessException e) {
            throw new AuthzProvisioningException("Error creating user!", e);
        }
    }

    /**
     * Drop user in the virtuoso
     * 
     * @param userid - String representing the user id
     * @throws AuthzProvisioningException - error thrown, if any
     */
    public void dropUser(String userid) throws AuthzProvisioningException {
        try {
            final Object[] userset = { userid, 1 };
            dbaSimpleJdbcTemplate.update("{ call user_drop(?, ?) }", userset);
        } catch (DataAccessException e) {
            throw new AuthzProvisioningException("Error removing user!", e);
        }
    }

    /**
     * Creates a new graph group
     * 
     * @param repository - Repository instance
     * @param graphGrpURIStr - URI string representing the IRI of the graph group
     * @return org.openrdf.model.URI for the graph group
     * @throws AuthzProvisioningException - error thrown, if any
     */
    public URI createGraphGroup(Repository repository, String graphGrpURIStr) throws AuthzProvisioningException {
        try {
            final URI graphGrpURI = repository.getValueFactory().createURI(graphGrpURIStr);
            // supply value 1 to silence error, if group already exists
            final Object[] oneSet = { graphGrpURI.toString(), Integer.valueOf(1) };
            
            dbaSimpleJdbcTemplate.update("{ call DB.DBA.RDF_GRAPH_GROUP_CREATE(?,?) }", oneSet);  
            return graphGrpURI;
        } catch (DataAccessException e) {
            throw new AuthzProvisioningException("Error creating new graph group!", e);
        } 
    }

    /**
     * Loads RDF Xml into a target graph
     * 
     * @param repoCon - RepositoryConnection instance
     * @param rdfFile - File instance for the rdf xml
     * @param graphURI - org.openrdf.model.URI instance representing target graph
     * @throws AuthzProvisioningException - error thrown, if any
     */
    public void loadGraphFromRDFXml(RepositoryConnection repoCon, File rdfFile, URI graphURI)
            throws AuthzProvisioningException {
        try {
            repoCon.add(rdfFile, "", RDFFormat.RDFXML, graphURI);
        } catch (RDFParseException e) {
            throw new AuthzProvisioningException("Error loading RDF xml into graph!", e);
        } catch (RepositoryException e) {
            throw new AuthzProvisioningException("Error loading RDF xml into graph!", e);
        } catch (IOException e) {
            throw new AuthzProvisioningException("Error loading RDF xml into graph!", e);
        }
    }

    /**
     * Clears all the triples from a graph
     * 
     * @param repoCon - RepositoryConnection instance
     * @param graphURI - org.openrdf.model.URI instance representing target graph
     * @throws AuthzProvisioningException - error thrown, if any
     */
    public void clearGraph(RepositoryConnection repoCon, URI graphURI) throws AuthzProvisioningException {
        try {
            repoCon.clear(graphURI);
        } catch (RepositoryException e) {
            throw new AuthzProvisioningException("Error clearing graph!", e);
        }
    }

    /**
     * Adds a user to a graph group, thereby providing read permission to all the member graphs of that group
     * 
     * @param userid - String representing the user id
     * @param graphGroup - String representing the URI of the graph group
     * @throws AuthzProvisioningException
     */
    public void addUserToGraphGroup(String userid, String graphGroupURI) throws AuthzProvisioningException {
        try {
            final Object[] oneset = { userid, graphGroupURI, Integer.valueOf(1) };
            dbaSimpleJdbcTemplate.update("{ call caCIS_GRAPH_GROUP_USER_PERMS_SET(?, ?, ?) }", oneset);
        } catch (DataAccessException e) {
            throw new AuthzProvisioningException("Error adding user to graph group!", e);
        }
    }

    /**
     * Create new DataSource with supplied credentials
     * 
     * @param url - String representing the url to connect to the DB
     * @param password - String representing the password
     * @param userid - String representing the userid
     * @param driverClassName - String representing the driver
     * @return instance of java.sql.DataSource
     * @throws AuthzProvisioningException - error thrown, if any
     */
    public static DataSource createDataSource(String url, String password, String userid, String driverClassName)
            throws AuthzProvisioningException {
        final ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setPassword(password);
        dataSource.setUser(userid);
        try {
            dataSource.setDriverClass(driverClassName);
        } catch (PropertyVetoException e) {
            throw new AuthzProvisioningException("Error configuring c3p0 data source: " + e.getMessage(), e);
        }
        
        return dataSource;
    }

    /**
     * Create Simple JDBC Template from a datasource
     * 
     * @param dataSource - DataSource instance
     * @return instance of SimpleJdbcTempalte
     */
    public static SimpleJdbcTemplate getSimpleJdbcTemplate(DataSource dataSource) {
        return new SimpleJdbcTemplate(dataSource);
    }

    /**
     * @return the dbaSimpleJdbcTemplate
     */
    public SimpleJdbcTemplate getDbaSimpleJdbcTemplate() {
        return dbaSimpleJdbcTemplate;
    }

    /**
     * @param dbaSimpleJdbcTemplate the dbaSimpleJdbcTemplate to set
     */
    public void setDbaSimpleJdbcTemplate(SimpleJdbcTemplate dbaSimpleJdbcTemplate) {
        this.dbaSimpleJdbcTemplate = dbaSimpleJdbcTemplate;
    }
    
    public static void main(String[] args) throws AuthzProvisioningException {
        final String url = "jdbc:virtuoso://localhost:1111";
        final String password= "dba";
        final String userid = "dba";
        final String driverClassName = "virtuoso.jdbc3.Driver";
        
        final DataSource ds = VirtuosoUtils.createDataSource(url, password, userid, driverClassName);
        final VirtuosoRepository repository = new VirtuosoRepository(url, userid, password);
        final VirtuosoUtils vu = new VirtuosoUtils(getSimpleJdbcTemplate(ds));
        vu.createGraphGroup(repository, "http://cacis.nci.nih.gov/site7");
    }
    
}
