package gov.nih.nci.cacisweb.action.test;

import gov.nih.nci.cacisweb.action.SecureEmailAction;
import gov.nih.nci.cacisweb.model.SecureEmailModel;
import gov.nih.nci.cacisweb.util.CaCISUtil;

import java.util.List;

import junit.framework.TestCase;

public class SecureEmailListActionTest extends TestCase {

    public void testInput() {
        SecureEmailAction secureEmailListAction = new SecureEmailAction();
        try {
            secureEmailListAction.input();
            List<SecureEmailModel> secureEmailRecepients = secureEmailListAction.getSecureEmailRecepientList();
            System.out.println(secureEmailRecepients.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail(CaCISUtil.getStackTrace(e));
        }

    }

}
