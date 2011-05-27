package gov.nih.nci.cacis.pcoplugin;

import gov.nih.nci.cacis.sa.service.ShareClinicalDataWs;

import javax.jws.WebService;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@WebService(targetNamespace = "http://sa.cacis.nci.nih.gov", name = "ShareClinicalData",
        serviceName = "ShareClinicalData")
public class MockShareClinicalDataWs implements ShareClinicalDataWs {

    public static boolean wasCalled;

    /**
     * @param text input
     * @return output
     */
    @Override
    public String recieve(String text) {
        System.out.println(text);
        wasCalled = true;
        return null;
    }
}
