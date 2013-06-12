/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.util;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import org.apache.commons.dbutils.DbUtils;

/**
 * @author Ajay Nalamala
 * 
 */
public class LoggableStatement implements PreparedStatement {

	private ArrayList<String> parameterValues;
	private String sqlTemplate;

	private PreparedStatement wrappedStatement;

	public LoggableStatement(Connection connection, String sql) throws SQLException {
		wrappedStatement = connection.prepareStatement(sql);
		sqlTemplate = sql;
		parameterValues = new ArrayList<String>();
	}

	// Save the parameter values in arraylist
	private void saveQueryParamValue(int position, Object obj) {
		String strValue;
		if (obj instanceof String || obj instanceof java.util.Date) {
			strValue = "'" + obj + "'";
		} else {
			if (obj == null) {
				strValue = "null";
			} else {
				strValue = obj.toString();
			}
		}
		while (position >= parameterValues.size()) {
			parameterValues.add(null);
		}
		parameterValues.set(position, strValue);
	}

	// Replace ? with corresponding values from parameterValues arraylist
	public String toString() {
		StringTokenizer st = new StringTokenizer(sqlTemplate, "?");
		int count = 1;
		// System.out.println("Size of parameterValues in LoggableStatement === "
		// + parameterValues.size());
		// System.out.println("Size of tokens in LoggableStatement === " +
		// st.countTokens());
		StringBuffer statement = new StringBuffer();
		while (st.hasMoreTokens()) {
			statement.append(st.nextToken());
			if (count < parameterValues.size()) {
				if (parameterValues.get(count) != null
						&& parameterValues.get(count).toString().length() > 0) {
					statement.append(parameterValues.get(count).toString());
				} else {
					statement.append("? " + "(missing variable # " + count + " ) ");
				}
			}
			count++;
		}
		return statement.toString();
	}

	// set methods frequently used for setting values in prepared statement.
	// implement other set methods if required.
	public void setLong(int parameterIndex, long x) throws SQLException {
		wrappedStatement.setLong(parameterIndex, x);
		saveQueryParamValue(parameterIndex, new Long(x));
	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		wrappedStatement.setLong(parameterIndex, x);
		saveQueryParamValue(parameterIndex, new Integer(x));
	}

	public void setString(int parameterIndex, String x) throws SQLException {
		wrappedStatement.setString(parameterIndex, x);
		saveQueryParamValue(parameterIndex, x);
	}

	public void setDate(int parameterIndex, java.sql.Date x) throws SQLException {
		wrappedStatement.setDate(parameterIndex, x);
		saveQueryParamValue(parameterIndex, x);
	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		wrappedStatement.setDouble(parameterIndex, x);
		saveQueryParamValue(parameterIndex, new Double(x));
	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		wrappedStatement.setFloat(parameterIndex, x);
		saveQueryParamValue(parameterIndex, new Float(x));
	}

	// methods for executing the prepared statement
	public ResultSet executeQuery() throws SQLException {
		return wrappedStatement.executeQuery();
	}

	public int executeUpdate() throws SQLException {
		return wrappedStatement.executeUpdate();
	}

	public String getQueryString() throws SQLException {
		return wrappedStatement.toString();
	}

	// Set Methods
	public void setArray(int i, java.sql.Array x) throws SQLException {
	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
	}

	public void setBlob(int parameterIndex, Blob x) throws SQLException {
	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
	}

	public void setByte(int parameterIndex, byte x) throws SQLException {
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length)
			throws SQLException {
	}

	public void setClob(int i, Clob x) throws SQLException {
	}

	public void setCursorName(String name) throws SQLException {
	}

	public void setDate(int parameterIndex, java.sql.Date x, Calendar cal) throws SQLException {
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
	}

	public void setFetchDirection(int direction) throws SQLException {
	}

	public void setFetchSize(int rows) throws SQLException {
	}

	public void setMaxFieldSize(int max) throws SQLException {
	}

	public void setMaxRows(int max) throws SQLException {
	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException {
	}

	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
	}

	public void setObject(int parameterIndex, Object x) throws SQLException {
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType, int scale)
			throws SQLException {
	}

	public void setQueryTimeout(int seconds) throws SQLException {
	}

	public void setRef(int i, Ref x) throws SQLException {
	}

	public void setShort(int parameterIndex, short x) throws SQLException {
	}

	public void setTime(int parameterIndex, Time x) throws SQLException {
	}

	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
	}

	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
	}

	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
	}

	// Get Methods
	public Connection getConnection() throws SQLException {
		return null;
	}

	public int getFetchDirection() throws SQLException {
		return 0;
	}

	public int getFetchSize() throws SQLException {
		return 0;
	}

	public int getMaxFieldSize() throws SQLException {
		return 0;
	}

	public int getMaxRows() throws SQLException {
		return 0;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return null;
	}

	public boolean getMoreResults() throws SQLException {
		return true;
	}

	public int getQueryTimeout() throws SQLException {
		return 0;
	}

	public ResultSet getResultSet() throws SQLException {
		return null;
	}

	public int getResultSetConcurrency() throws SQLException {
		return 0;
	}

	public int getResultSetType() throws SQLException {
		return 0;
	}

	public String getStatement() {
		return null;
	}

	public int getUpdateCount() throws SQLException {
		return 0;
	}

	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	// Other Methods
	public void addBatch() throws SQLException {
	}

	public void addBatch(String sql) throws SQLException {
	}

	public void cancel() throws SQLException {
	}

	public void clearBatch() throws SQLException {
	}

	public void clearParameters() throws SQLException {
	}

	public void clearWarnings() throws SQLException {
	}

	public void close() throws SQLException {
		DbUtils.closeQuietly(wrappedStatement);
	}

	// Execute Methods
	public boolean execute() throws SQLException {
		return true;
	}

	public boolean execute(String sql) throws SQLException {
		return true;
	}

	public int[] executeBatch() throws SQLException {
		return null;
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return null;
	}

	public int executeUpdate(String sql) throws SQLException {
		return 0;
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNString(int parameterIndex, String value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
