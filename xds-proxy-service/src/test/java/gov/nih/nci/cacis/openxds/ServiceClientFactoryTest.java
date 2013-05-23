/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.openxds;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.context.MessageContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class ServiceClientFactoryTest {

    @Mock
    MessageContext mockMsgCtx;

    @Before
    public void setupAxisCtx() throws AxisFault {
        final URL axis2repo = this.getClass().getResource("/conf");
        final URL axis2xml = this.getClass().getResource("/conf/axis2.xml");


        MockitoAnnotations.initMocks(this);

        ConfigurationContext msgCtx = ConfigurationContextFactory
                .createConfigurationContextFromURIs(axis2xml, axis2repo);

        MessageContext.setCurrentMessageContext(mockMsgCtx);
        when(mockMsgCtx.getConfigurationContext()).thenReturn(msgCtx);

    }

    @Test
    public void validate() throws IOException, URISyntaxException {
        ServiceClientFactory factory = new ServiceClientFactory();
        assertNotNull(factory.getRepositoryServiceClient(""));
    }
}
