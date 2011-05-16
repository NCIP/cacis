package gov.nih.nci.cacis.sa;

import gov.nih.nci.cacis.sa.service.ShareClinicalDataWs;
import gov.nih.nci.cacis.sa.service.ShareClinicalDataWsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@Configuration
@ImportResource("classpath:sa-beans.xml")
public class ShareClinicalDataConfig {

    /**
     * shareClinicalData Web Service
     * @return ShareClinicalDataWs web service
     */
    @Bean
    public ShareClinicalDataWs shareClinicalDataWs() {
        return new ShareClinicalDataWsImpl();

    }
}
