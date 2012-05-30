package gov.nih.nci.cacisweb.dao.virtuoso;

import gov.nih.nci.cacisweb.dao.DAOFactory;
import gov.nih.nci.cacisweb.dao.ICDWUserPermissionDAO;
import gov.nih.nci.cacisweb.dao.ICommonUtilityDAO;
import gov.nih.nci.cacisweb.exception.DAOException;

import org.apache.log4j.Logger;

/**
 * @author Ajay Nalamala
 * 
 */
public class VirtuosoDAOFactory extends DAOFactory {

	/**
	 * Commons Logging instance.
	 */
	static Logger log = Logger.getLogger(VirtuosoDAOFactory.class);

	/**
	 * Constructor for OracleDAOFactory.
	 */
	public VirtuosoDAOFactory() {
		super();
	}

	public ICommonUtilityDAO getCommonUtilityDAO() throws DAOException {
		return new VirtuosoCommonUtilityDAO();
	}

	public ICDWUserPermissionDAO getCDWUserPermissionDAO() throws DAOException {
		return new VirtuosoCDWUserPermissionDAO();
	}
	
}