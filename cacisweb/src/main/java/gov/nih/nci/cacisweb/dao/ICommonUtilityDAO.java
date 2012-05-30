package gov.nih.nci.cacisweb.dao;

import gov.nih.nci.cacisweb.exception.DAOException;

import java.sql.Connection;

/**
 * @author Ajay Nalamala
 * 
 */
public interface ICommonUtilityDAO {

	public Connection manageCaCISConnection() throws DAOException;

	public void closeCaCISConnection() throws DAOException;

}
