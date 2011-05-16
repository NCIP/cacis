package gov.nih.nci.cacis.sa.service;

import javax.jws.WebService;

/**
 * Web service interface for ShareClinicalData
 */
@WebService
public interface ShareClinicalDataWs {
    /**
     *
     * @param text input
     * @return output
     */
     String recieve(String text);
}

