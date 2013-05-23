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

import com.mchange.v2.c3p0.ComboPooledDataSource;
import gov.nih.nci.cacis.cdw.GraphAuthzMgr;
import gov.nih.nci.cacis.cdw.GraphAuthzMgrImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import virtuoso.sesame2.driver.VirtuosoRepository;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Jul 15, 2011
 */
@Configuration
public class VirtuosoJdbcConfigImpl implements SesameJdbcConfig {

    // datasource properties

    /**
     * The datasource url.
     */
    @Value("${cacis.virtuoso.db.url}")
    private String url;

    /**
     * The datasource username.
     */
    @Value("${cacis.virtuoso.db.username}")
    private String username;

    /**
     * The datasource password.
     */
    @Value("${cacis.virtuoso.db.password}")
    private String password;

    /**
     * The datasource driver class name.
     */
    @Value("${cacis.virtuoso.db.driver}")
    private String driverClassName;


    @Override
    @Bean
    public GraphAuthzMgr graphAuthzMgr() {
        return new GraphAuthzMgrImpl(simpleJdbcTemplate());
    }

    @Override
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public RepositoryConnection repositoryConnection() throws RepositoryException {
        final RepositoryConnection con = repository().getConnection();
        con.setAutoCommit(true);
        return con;
    }

    @Override
    @Bean
    public Repository repository() {
        return new VirtuosoRepository(url, username, password);
    }

    @Override
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public Connection connection() throws SQLException {
        return dataSource().getConnection();
    }

    @Override
    @Bean
    public DataSourceTransactionManager cacisTxManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Override
    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws ApplicationContextException {
        final ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setPassword(username);
        dataSource.setUser(password);

        try {
            dataSource.setDriverClass(driverClassName);
        } catch (PropertyVetoException e) {
            throw new ApplicationContextException("Error configuring c3p0 data source: " + e.getMessage(), e);
        }
        return dataSource;
    }

    @Override
    @Bean
    public SimpleJdbcTemplate simpleJdbcTemplate() {
        return new SimpleJdbcTemplate(dataSource());
    }

}
