package gov.nih.nci.cacisweb.dao.virtuoso;

import gov.nih.nci.cacisweb.CaCISWebConstants;
import gov.nih.nci.cacisweb.dao.ICDWUserPermissionDAO;
import gov.nih.nci.cacisweb.exception.CaCISWebException;
import gov.nih.nci.cacisweb.exception.DAOException;
import gov.nih.nci.cacisweb.model.CdwPermissionModel;
import gov.nih.nci.cacisweb.model.CdwUserModel;
import gov.nih.nci.cacisweb.util.CaCISUtil;
import gov.nih.nci.cacisweb.util.LoggableStatement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class VirtuosoCDWUserPermissionDAO extends VirtuosoCommonUtilityDAO implements ICDWUserPermissionDAO {

    public VirtuosoCDWUserPermissionDAO() throws DAOException {
        super();
    }

    @Override
    public boolean isUserExists(CdwUserModel cdwUserModel) throws DAOException {
        log.debug("isUserExists(CDWUserModel cdwUserModel) - start");
        boolean isUserExists = false;
        StringBuffer query = new StringBuffer();
        query.append("SELECT U_ID FROM DB.DBA.SYS_USERS WHERE U_NAME=?");
        try {
            pstmt = new LoggableStatement(cacisConnection, query.toString());
            pstmt.setString(1, cdwUserModel.getUsername());
            log.info("SQL in isUserExists(CDWUserModel cdwUserModel): " + pstmt.toString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                isUserExists = true;
            }
        } catch (SQLException sqle) {
            log.error(sqle.getMessage(), sqle);
            throw new DAOException(sqle.getMessage());
        }
        log.debug("isUserExists(CDWUserModel cdwUserModel) - end");
        return isUserExists;
    }

    @Override
    public boolean addUser(CdwUserModel cdwUserModel) throws DAOException {
        log.debug("addUser(CDWUserModel cdwUserModel) - start");
        StringBuffer query = new StringBuffer();
        query.append("{call DB.DBA.USER_CREATE (?, ?)}");
        boolean userAdded = true;
        boolean isGrantCallSuccessful = false;
        try {
            pstmt = new LoggableStatement(cacisConnection, query.toString());
            pstmt.setString(1, cdwUserModel.getUsername());
            pstmt.setString(2, cdwUserModel.getPassword());
            log.info("USER_CREATE call in addUser(CDWUserModel cdwUserModel): " + pstmt.toString());
            pstmt.executeUpdate();

            query = new StringBuffer();
            query.append("grant SPARQL_UPDATE to \"" + cdwUserModel.getUsername() + "\"");
            pstmt = new LoggableStatement(cacisConnection, query.toString());
            // pstmt.setString(1, cdwUserModel.getUsername());
            log.info("grant call in addUser(CDWUserModel cdwUserModel): " + pstmt.toString());
            pstmt.executeUpdate();
            isGrantCallSuccessful = true;

            query = new StringBuffer();
            query.append("{call DB.DBA.RDF_DEFAULT_USER_PERMS_SET (?, 0)}");
            pstmt = new LoggableStatement(cacisConnection, query.toString());
            pstmt.setString(1, cdwUserModel.getUsername());
            log.info("RDF_DEFAULT_USER_PERMS_SET call in addUser(CDWUserModel cdwUserModel): " + pstmt.toString());
            pstmt.executeUpdate();
        } catch (SQLException sqle) {
            userAdded = false;
            if (isGrantCallSuccessful) {
                query = new StringBuffer();
                try {
                    query.append("revoke SPARQL_UPDATE from \"" + cdwUserModel.getUsername() + "\"");
                    pstmt = new LoggableStatement(cacisConnection, query.toString());
                    log.info("grant call in addUser(CDWUserModel cdwUserModel): " + pstmt.toString());
                    pstmt.executeUpdate();
                } catch (SQLException sqle2) {
                    log.error(sqle2.getMessage(), sqle2);
                    throw new DAOException(sqle.getMessage() + sqle2.getMessage());
                }
            }
            log.error(sqle.getMessage(), sqle);
            throw new DAOException(sqle.getMessage());
        }
        log.debug("addUser(CDWUserModel cdwUserModel) - end");
        return userAdded;
    }

    @Override
    public int deleteUser(CdwUserModel cdwUserModel) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean addUserPermission(CdwUserModel userModel, CdwPermissionModel cdwPermissionModel) throws DAOException {
        log.debug("addUserPermission(CdwUserModel userModel, CdwPermissionModel cdwPermissionModel) - start");
        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO RDF_GRAPH_GROUP (RGG_IRI, RGG_IID) VALUES (?, iri_to_id(?))");
        boolean permissionAdded = true;
        StringBuffer graphGroupID = new StringBuffer();
        try {
            String graphGroupPrefix = CaCISUtil
                    .getProperty(CaCISWebConstants.COM_PROPERTY_NAME_CDW_GRAPH_GROUP_URL_PREFIX);
            graphGroupID.append(graphGroupPrefix
                    + CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_CDW_STUDY_ID_ROOT) + "."
                    + cdwPermissionModel.getStudyID() + "/");
            if (StringUtils.isNotBlank(cdwPermissionModel.getSiteID())) {
                graphGroupID.append(CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_CDW_SITE_ID_ROOT) + "."
                        + cdwPermissionModel.getSiteID());
            }
            graphGroupID.append("/");
            if (StringUtils.isNotBlank(cdwPermissionModel.getPatientID())) {
                graphGroupID.append(CaCISUtil.getProperty(CaCISWebConstants.COM_PROPERTY_NAME_CDW_PATIENT_ID_ROOT)
                        + "." + cdwPermissionModel.getPatientID());
            }

            pstmt = new LoggableStatement(cacisConnection, query.toString());
            pstmt.setString(1, graphGroupID.toString());
            pstmt.setString(2, graphGroupID.toString());
            log
                    .info("INSERT INTO RDF_GRAPH_GROUP SQL in addUser(CDWUserModel cdwUserModel, CdwPermissionModel cdwPermissionModel): "
                            + pstmt.toString());
            pstmt.executeUpdate();

            query = new StringBuffer();
            query.append("{call caCIS_GRAPH_GROUP_USER_PERMS_SET (?, ?, 1)}");
            pstmt = new LoggableStatement(cacisConnection, query.toString());
            pstmt.setString(1, userModel.getUsername());
            pstmt.setString(2, graphGroupID.toString());
            log
                    .info("caCIS_GRAPH_GROUP_USER_PERMS_SET call in addUser(CDWUserModel cdwUserModel, CdwPermissionModel cdwPermissionModel): "
                            + pstmt.toString());
            pstmt.executeUpdate();
        } catch (SQLException sqle) {
            permissionAdded = false;
            log.error(sqle.getMessage(), sqle);
            throw new DAOException(sqle.getMessage());
        } catch (CaCISWebException e) {
            permissionAdded = false;
            log.error(e.getMessage(), e);
            throw new DAOException(e.getMessage());
        }
        log.debug("addUser(CDWUserModel cdwUserModel) - end");
        return permissionAdded;
    }

    @Override
    public int deleteUserPermission(CdwUserModel userModel, CdwPermissionModel cdwPermissionModel) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<CdwPermissionModel> searchUserPermissions(CdwUserModel cdwUserModel) throws DAOException {
        log.debug("searchUserPermissions(CDWUserModel cdwUserModel) - start");
        ArrayList<CdwPermissionModel> userPermissionList = new ArrayList<CdwPermissionModel>();
        StringBuffer query = new StringBuffer();
        query.append("SELECT A.RGG_IRI from DB.DBA.RDF_GRAPH_GROUP A, DB.DBA.RDF_GRAPH_USER B, "
                + "DB.DBA.SYS_USERS C WHERE A.RGG_IID = B.RGU_GRAPH_IID and B.RGU_USER_ID = C.U_ID and "
                + "C.U_NAME = ?");
        try {
            pstmt = new LoggableStatement(cacisConnection, query.toString());
            pstmt.setString(1, cdwUserModel.getUsername());
            log.info("SQL searchUserPermissions(CDWUserModel cdwUserModel): " + pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String[] studySitePatientArray = StringUtils.split(rs.getString(1), "/");
                CdwPermissionModel cdwPermissionModel = new CdwPermissionModel();
                if (studySitePatientArray.length >= 3) {
                    cdwPermissionModel.setStudyID(studySitePatientArray[2]);
                    if (studySitePatientArray.length >= 4) {
                        cdwPermissionModel.setSiteID(studySitePatientArray[3]);
                        if (studySitePatientArray.length >= 5) {
                            cdwPermissionModel.setPatientID(studySitePatientArray[4]);
                        }
                    }
                }
                userPermissionList.add(cdwPermissionModel);
            }
        } catch (SQLException sqle) {
            log.error(sqle.getMessage(), sqle);
            throw new DAOException(sqle.getMessage());
        }
        log.debug("searchUserPermissions(CDWUserModel cdwUserModel) - end");
        return userPermissionList;
    }

}
