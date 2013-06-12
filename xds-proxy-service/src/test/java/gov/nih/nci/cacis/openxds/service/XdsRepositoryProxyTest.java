/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.openxds.service;

import gov.nih.nci.cacis.openxds.ServiceClientFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class XdsRepositoryProxyTest {

    private XdsRepositoryProxy xdsRepositoryProxy;

    @Mock
    private OMElement mockRequest;
    @Mock
    private ServiceClientFactory mockClientFactory;

    @Before
    public void setup() {
        xdsRepositoryProxy = new XdsRepositoryProxy();
        MockitoAnnotations.initMocks(this);
        xdsRepositoryProxy.setServiceClientFactory(mockClientFactory);


    }

    @Test
    public void provideAndRegisterDocumentSetRequest() throws AxisFault {
        ServiceClient mockClient = mock(ServiceClient.class);

        when(mockClientFactory.getRepositoryServiceClient(anyString())).thenReturn(mockClient);

         xdsRepositoryProxy.provideAndRegisterDocumentSetRequest(mockRequest);

        verify(mockClient).sendReceive(mockRequest);
    }
}
