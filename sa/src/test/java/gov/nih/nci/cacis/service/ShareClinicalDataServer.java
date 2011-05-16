package gov.nih.nci.cacis.service;

import gov.nih.nci.cacis.common.systest.AbstractBusTestServer;
import gov.nih.nci.cacis.sa.ShareClinicalDataConfig;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class ShareClinicalDataServer  extends AbstractBusTestServer {
        /**
         * Default static ADDRESS to the local deployment
         */
        public static final String ADDRESS = "http://localhost:8178/ShareClinicalData";

        public ShareClinicalDataServer() {
            super("shareClinicalDataWs", ADDRESS, ShareClinicalDataConfig.class, true);
        }
    }

