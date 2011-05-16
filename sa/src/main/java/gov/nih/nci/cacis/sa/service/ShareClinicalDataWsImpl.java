package gov.nih.nci.cacis.sa.service;

import javax.jws.WebService;

/**
 * Service Implementation
 */
@WebService(endpointInterface = "gov.nih.nci.cacis.sa.service.ShareClinicalDataWs")
public class ShareClinicalDataWsImpl implements ShareClinicalDataWs {

    /**
     * Receive operation
     * @param trim input
     * @return output
     */
    public String recieve(String trim) {
        return "Received trim message";
    }
}

