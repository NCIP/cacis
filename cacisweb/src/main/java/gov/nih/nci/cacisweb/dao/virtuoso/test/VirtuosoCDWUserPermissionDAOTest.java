package gov.nih.nci.cacisweb.dao.virtuoso.test;

import gov.nih.nci.cacisweb.dao.virtuoso.VirtuosoCDWUserPermissionDAO;
import gov.nih.nci.cacisweb.exception.DAOException;
import gov.nih.nci.cacisweb.model.CdwPermissionModel;
import gov.nih.nci.cacisweb.model.CdwUserModel;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

public class VirtuosoCDWUserPermissionDAOTest extends TestCase {

    @Test
    public void testSearchUserPermissions() {
        CdwUserModel cdwUserModel = new CdwUserModel();
        cdwUserModel.setUsername("cdw_user_1");
        try {
            VirtuosoCDWUserPermissionDAO virtuosoCDWUserPermissionDAO = new VirtuosoCDWUserPermissionDAO();
            ArrayList<CdwPermissionModel> userPermissionList = (ArrayList<CdwPermissionModel>) virtuosoCDWUserPermissionDAO
                    .searchUserPermissions(cdwUserModel);
            for (int i = 0; i < userPermissionList.size(); i++) {
                System.out.println("Study: " + userPermissionList.get(i).getSiteID() + " Site: "
                        + userPermissionList.get(i).getSiteID() + " Patient: "
                        + userPermissionList.get(i).getPatientID());
            }

        } catch (DAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
