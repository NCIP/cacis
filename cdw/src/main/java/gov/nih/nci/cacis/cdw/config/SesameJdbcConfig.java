/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw.config;

import gov.nih.nci.cacis.cdw.GraphAuthzMgr;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.springframework.context.ApplicationContextException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public interface SesameJdbcConfig {

    /**
     * Authz Manager
     * 
     * @return GraphAuthzMgr
     */
    GraphAuthzMgr graphAuthzMgr();

    /**
     * Sesame Repository Connection
     * @return RepositoryConnection
     * @throws RepositoryException  exception
     */
    RepositoryConnection repositoryConnection() throws RepositoryException;

    /**
     * Sesame Repository
     * @return Repository
     * @throws RepositoryException exception
     */
    Repository repository() throws RepositoryException;

    /**
     * JDBC Connection
     * @return Connection
     * @throws SQLException exception
     */
   Connection connection() throws SQLException;

     /**
     * JDBC Transaction Manager
     * @return DataSourceTransactionManager transaction manager
     */
    DataSourceTransactionManager cacisTxManager();

    /**
     * JDBC Data source
     * @return DataSource
     * @throws ApplicationContextException exception
     */
    DataSource dataSource() throws ApplicationContextException;
    
    /**
     * SimpleJdbcTemplate
     * @return SimpleJdbcTemplate instance
     */
    SimpleJdbcTemplate simpleJdbcTemplate();
}
