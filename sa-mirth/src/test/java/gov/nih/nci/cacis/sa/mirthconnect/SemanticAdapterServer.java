package gov.nih.nci.cacis.sa.mirthconnect;

import com.mirth.connect.connectors.ws.WebServiceMessageReceiver;
import gov.nih.nci.cacis.common.systest.AbstractBusTestServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class SemanticAdapterServer extends AbstractBusTestServer {

    /**
     * Default static ADDRESS to the local deployment of ReferralService
     */
    public static final String ADDRESS = "http://localhost:9010/SemanticAdapter";

    /**
     * Constructor.
     */
    public SemanticAdapterServer() {
        super("semanticAdapter",ADDRESS, TestSemanticAdapterConfig.class, true);
    }

    /**
     * @author kherm manav.kher@semanticbits.com
     */
    @Configuration
    public static class TestSemanticAdapterConfig {


        @Bean
        public SemanticAdapter semanticAdapter() {
            WebServiceMessageReceiver mockWebServiceMessageReceiver = mock(WebServiceMessageReceiver.class);
            when(mockWebServiceMessageReceiver.processData(anyString())).thenThrow(new RuntimeException());
            return new SemanticAdapter(mockWebServiceMessageReceiver);
        }


    }
}

