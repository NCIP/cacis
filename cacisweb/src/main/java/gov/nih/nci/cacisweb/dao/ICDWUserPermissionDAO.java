/*
 * Created on Oct 13, 2006
 */
package gov.nih.nci.cacisweb.dao;

import gov.nih.nci.cacisweb.exception.DAOException;
import gov.nih.nci.cacisweb.model.CdwPermissionModel;
import gov.nih.nci.cacisweb.model.CdwUserModel;

import java.util.List;

/**
 * @author Ajay Nalamala
 */
public interface ICDWUserPermissionDAO extends ICommonUtilityDAO {
    
    public boolean isUserExists(CdwUserModel cdwUserModel) throws DAOException;

    public boolean addUser(CdwUserModel cdwUserModel) throws DAOException;

    public int deleteUser(CdwUserModel cdwUserModel) throws DAOException;

    public boolean addUserPermission(CdwUserModel userModel, CdwPermissionModel cdwPermissionModel) throws DAOException;

    public List<CdwPermissionModel> searchUserPermissions(CdwUserModel cdwUserModel) throws DAOException;

    public int deleteUserPermission(CdwUserModel userModel, CdwPermissionModel cdwPermissionModel) throws DAOException;

}
