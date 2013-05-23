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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-cdw-test.xml")
public class VirtuosoJdbcConfigIntegrationTest {


    @Autowired
    DataSource dataSource;
    @Autowired
    Connection connection;


    @Test
    public void validateDS() {
        assertNotNull(dataSource);
    }

    @Test
    public void executeStatement() throws SQLException {
        final String query1 = "select id_to_iri(rggm_member_iid) as graph " + "from DB.DBA.RDF_GRAPH_GROUP_MEMBER "
                + "where id_to_iri(rggm_group_iid) = 'http://group1.cacis.com'";


        final Statement stmt = connection.createStatement();

        stmt.execute(query1);
        final ResultSet rs = stmt.getResultSet();

        assertNotNull(rs);

        assertTrue(rs.getFetchSize() > 0);

        int size = 0;
        while (rs.next()) {
            size++;
            assertNotNull(rs.getString(0));
            System.out.println(rs.getString(0));// NOPMD
        }

    }
}



