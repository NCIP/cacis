/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.sa.mirthconnect;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.cacis.common.systest.AbstractBusTestServer;
import gov.nih.nci.cacis.sa.AcceptSourcePortType;
import gov.nih.nci.cacis.sa.AcceptSourcePortTypeImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mirth.connect.connectors.ws.WebServiceMessageReceiver;

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
        super("acceptSourcePortType", ADDRESS, TestSemanticAdapterConfig.class, true);
    }

    /**
     * @author kherm manav.kher@semanticbits.com
     */
    @Configuration
    public static class TestSemanticAdapterConfig {


        @Bean
        public SemanticAdapter semanticAdapter() {
            WebServiceMessageReceiver mockWebServiceMessageReceiver = mock(WebServiceMessageReceiver.class);
            when(mockWebServiceMessageReceiver.processData(anyString())).thenReturn("");
            return new SemanticAdapter(mockWebServiceMessageReceiver);
        }

        @Bean
        public AcceptSourcePortType acceptSourcePortType() {
            return new AcceptSourcePortTypeImpl();
        }
    }
}

