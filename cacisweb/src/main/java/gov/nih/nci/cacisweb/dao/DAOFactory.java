package gov.nih.nci.cacisweb.dao;

import gov.nih.nci.cacisweb.dao.virtuoso.VirtuosoDAOFactory;
import gov.nih.nci.cacisweb.exception.DAOException;

/**
 * @author Ajay Nalamala
 * 
 */
public abstract class DAOFactory {

	public abstract ICommonUtilityDAO getCommonUtilityDAO() throws DAOException;
	
	public abstract ICDWUserPermissionDAO getCDWUserPermissionDAO() throws DAOException;

	// List of DATABASES supported
	public static final int VIRTUOSO = 1;

	public static DAOFactory getDAOFactory() {

		int whichFactory = VIRTUOSO;

		switch (whichFactory) {
		case VIRTUOSO:
			return new VirtuosoDAOFactory();
		default:
			return null;
		}
	}

}
