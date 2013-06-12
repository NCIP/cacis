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
