/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.dao.virtuoso.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import junit.framework.TestCase;

public class VirtuosoConnectionTest extends TestCase {
    
    @Test
    public void testVirtuosoConnection() {

        Connection connection = null;
        try { // Load the JDBC driver
            String driverName = "virtuoso.jdbc3.Driver";
            Class.forName(driverName);

            // Create a connection to the database
            String serverName = "localhost";
            String portNumber = "1111";
            String url = "jdbc:virtuoso://" + serverName + ":" + portNumber + "/";
            String username = "dba";
            String password = "dba";
            connection = DriverManager.getConnection(url, username, password);
            java.sql.CallableStatement cs = connection.prepareCall("SELECT * FROM SYS_USERS");
//             java.sql.CallableStatement cs = connection.prepareCall("SELECT * FROM DB.DBA.RDF_GRAPH_USER_PERMS_SET");
//             java.sql.CallableStatement cs = connection.prepareCall("{call caCIS_GRAPH_GROUP_USER_PERMS_RETRIEVE}");
            ResultSet rs = cs.executeQuery();
            System.out.println("######  TABLE METADATA  ######");
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                System.out.println(rs.getMetaData().getColumnName(i) + "   :   "
                        + rs.getMetaData().getColumnTypeName(i));
            }
            System.out.println("######  U_ID  |  U_NAME  ######");
            while (rs.next()) {
//                StringBuffer recordBuffer = new StringBuffer();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    recordBuffer.append(rs.getString(i) + "  |  ");
//                }
//                System.out.println(recordBuffer.toString());
                System.out.println(rs.getString(1)+"  |  "+rs.getString(2));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Could not find the database driver
        } catch (SQLException e) {

            e.printStackTrace(); // Could not connect to the database
        }

    }

}
