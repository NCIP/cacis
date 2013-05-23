/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.dao.virtuoso;

import gov.nih.nci.cacisweb.CaCISWebConstants;
import gov.nih.nci.cacisweb.dao.ICommonUtilityDAO;
import gov.nih.nci.cacisweb.exception.CaCISWebException;
import gov.nih.nci.cacisweb.exception.DAOException;
import gov.nih.nci.cacisweb.exception.DBUnavailableException;
import gov.nih.nci.cacisweb.util.CaCISUtil;
import gov.nih.nci.cacisweb.util.LoggableStatement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

/**
 * @author Ajay Nalamala
 * 
 */
public class VirtuosoCommonUtilityDAO implements ICommonUtilityDAO {

    /**
     * Commons Logging instance.
     */
    static Logger log = Logger.getLogger(VirtuosoCommonUtilityDAO.class);

    protected Connection cacisConnection;
    protected PreparedStatement pstmt;
    protected Statement stmt;
    protected CallableStatement cs;
    protected ResultSet rs;

    /**
     * declare only primary connections as static variables. If the primary connection is NOT available, the static
     * variable value will be set to the backup datasource name
     */
    public static String virtuosoDataSourceString = CaCISWebConstants.COM_PRIMARY_VIRTUOSO_DATASOURCE_NAME;

    /**
     * Constructor for OracleCommonUtilityDAO.
     */
    public VirtuosoCommonUtilityDAO() throws DAOException {
        super();
        manageCaCISConnection();
    }

    /**
     * This method is used to get KCC Datasource connection. It manages the switch to a secondary datasource should the
     * primary connection fail and vice versa.
     */
    public Connection manageCaCISConnection() throws DAOException {

        try {
            log.debug("Getting KCC Datasource from try  " + virtuosoDataSourceString);
            openCaCISConnection();

        } catch (DBUnavailableException dbue) {
            log.debug("Executing SQLException Block: Closing Connection");
            DbUtils.closeQuietly(cacisConnection);
            log.error(dbue.getMessage(), dbue);
        }
        return cacisConnection;
    }

    /**
     * This method wraps the exceptions and throws a single exception, to be handled by the calling object.
     * 
     * @throws DBUnavailableException
     */
    private void openCaCISConnection() throws DBUnavailableException {
        try {
            // DataSource dataSource = ServiceLocator.getInstance().getDataSourceByName(virtuosoDataSourceString);
            // cacisConnection = dataSource.getConnection();

            String driverName = CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_DATABASE_VIRTUOSO_DRIVER);
            Class.forName(driverName);

            // Create a connection to the database
           cacisConnection = DriverManager.getConnection(CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_DATABASE_VIRTUOSO_URL), CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_DATABASE_VIRTUOSO_USERNAME), CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_DATABASE_VIRTUOSO_PASSWORD));
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
            throw new DBUnavailableException(sqle.getMessage());
            // } catch (ServiceLocatorException sle) {
            // log.error(sle.getMessage());
            // throw new DBUnavailableException(sle.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            throw new DBUnavailableException(e.getMessage());
        } catch (CaCISWebException e) {
            log.error(e.getMessage());
            throw new DBUnavailableException(e.getMessage());
        }
    }

    /**
     * This method provides a central location for managing commit and close operations that need to be performed on the
     * database connection at the end of the transaction. Typically intended to be called from the session bean.
     */
    public void commitAndCloseCaCISConnection() throws DAOException {
        try {
            DbUtils.commitAndClose(cacisConnection);
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(stmt);
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
            throw new DAOException(sqle.getMessage());
        }
    }

    /**
     * This method provides a central location for managing rollback and close operations that need to be performed on
     * the database connection if the business decision to rollback the transaction was made by the session bean's
     * business logic.
     */
    public void rollbackAndCloseCaCISConnection() throws DAOException {
        try {
            DbUtils.rollback(cacisConnection);
            // DbUtils.closeQuietly(cacisConnection, stmt, rs);
            // DbUtils.closeQuietly(pstmt);
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
            throw new DAOException(sqle.getMessage());
        }

    }

    /**
     * This method provides a central location for close operations that need to be performed on the database
     * connection.
     * 
     * @throws KCCBusinessException
     */
    public void closeCaCISConnection() throws DAOException {
        try {
            DbUtils.closeQuietly(cacisConnection, pstmt, rs);
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(cs);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * This method closes any extra loggable statements that were opened during the execution of a business method.
     * Typically from a session bean.
     * 
     * @throws KCCBusinessException
     */
    public void closeExtraDBObjects() throws DAOException {
        try {
            DbUtils.closeQuietly(pstmt);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Gets the next sequence by suffixing '_SEQ' to the table name
     * 
     * @param tableName
     * @return
     * @throws DAOException
     */
    public long getNextSequenceBySequenceName(String sequenceName) throws DAOException {
        log.debug("getNextSequenceByTableName(String sequenceName) - start");
        long sequenceNum = 0;
        StringBuffer sequenceSQL = new StringBuffer();
        sequenceSQL.append("SELECT " + sequenceName + ".nextval from dual");
        try {
            pstmt = new LoggableStatement(cacisConnection, sequenceSQL.toString());
            log.info("SQL getNextSequenceByTableName(String sequenceName) : " + pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                sequenceNum = rs.getLong("NEXTVAL");
            }
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
            sqle.printStackTrace();
            throw new DAOException(sqle.getMessage());
        }
        return sequenceNum;
    }
}
