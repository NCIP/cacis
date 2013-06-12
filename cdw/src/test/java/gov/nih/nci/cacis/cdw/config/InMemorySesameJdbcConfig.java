/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw.config;

import gov.nih.nci.cacis.cdw.GraphAuthzMgr;
import gov.nih.nci.cacis.cdw.GraphAuthzMgrImpl;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;

/**
 * Provides an in-memory persistence for the Sesame
 * repository
 *
 * @author kherm manav.kher@semanticbits.com
 */
@Configuration
public class InMemorySesameJdbcConfig implements SesameJdbcConfig {

    /**
     * Authz Manager
     *
     * @return GraphAuthzMgr
     */
    @Bean
    public GraphAuthzMgr graphAuthzMgr() {
        return new GraphAuthzMgrImpl(simpleJdbcTemplate());
    }

    /**
     * Sesame Repository Connection
     *
     * @return RepositoryConnection
     * @throws org.openrdf.repository.RepositoryException
     *          exception
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public RepositoryConnection repositoryConnection() throws RepositoryException {
        final RepositoryConnection con = repository().getConnection();
        con.setAutoCommit(true);
        return con;
    }

    /**
     * Sesame Repository
     *
     * @return Repository
     * @throws RepositoryException exception
     */
    @Bean
    public Repository repository() throws RepositoryException {
        final Repository myRepository = new SailRepository(new MemoryStore());
        myRepository.initialize();
        return myRepository;
    }

    /**
     * JDBC Connection
     *
     * @return Connection
     * @throws java.sql.SQLException exception
     */
    @Bean
    public Connection connection() throws SQLException {
        return mock(Connection.class);
    }

    /**
     * JDBC Transaction Manager
     *
     * @return DataSourceTransactionManager transaction manager
     */
    @Bean
    public DataSourceTransactionManager cacisTxManager() {
        return mock(DataSourceTransactionManager.class);
    }

    /**
     * JDBC Data source
     *
     * @return DataSource
     * @throws org.springframework.context.ApplicationContextException
     *          exception
     */
    @Bean
    public DataSource dataSource() throws ApplicationContextException {
        return mock(DataSource.class);
    }

    /**
     * Simple JDBC Template
     * @return returns a mock simple jdbc template
     */
    @Bean
    public SimpleJdbcTemplate simpleJdbcTemplate() {
        return mock(SimpleJdbcTemplate.class);
    }
}
