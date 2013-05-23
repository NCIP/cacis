/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@Configuration
public class JpaConfig {

   // datasource properties

    /**
     * The datasource url.
     */
    @Value("${cacis.xds.authz.db.url}")
    private String url;

    /**
     * The datasource username.
     */
    @Value("${cacis.xds.authz.db.username}")
    private String username;

    /**
     * The datasource password.
     */
    @Value("${cacis.xds.authz.db.password}")
    private String password;

    /**
     * The datasource driver class name.
     */
    @Value("${cacis.xds.authz.db.driver}")
    private String driverClassName;



    // Hibernate/JPA properties


    /**
     * The database platform.
     */
    @Value("${cacis.xds.authz.db.hibernate.dialect}")
    private String databasePlatform;

    /**
     * The persistence unit name.
     */
    protected static final String PERSISTENCE_UNIT_NAME = "cacis-xds-authz";


     /**
     * Will return the Data source.
     *
     * @return the data source
     */
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setDriverClassName(driverClassName);

        return dataSource;
    }



    /**
     * Hiberante Jpa vendor adapter.
     *
     * @return the hibernate jpa vendor adapter
     */
    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        jpaVendorAdapter.setDatabasePlatform(databasePlatform);
        return jpaVendorAdapter;
    }

    /**
     * Entity manager factory.
     *
     * @return the entity manager factory
     */
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setPersistenceXmlLocation("classpath*:META-INF/xds-beans-persistence.xml");

        // must set the properties
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();
    }

    /**
     * Returns JPA tx manager.
     *
     * @return the jpa transaction manager
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
        jpaTransactionManager.setDataSource(dataSource());
        return jpaTransactionManager;
    }


}
