/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.mirthconnect;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-ip-mirth-test.xml")
public class CanonicalModelProcessorClientMCIntegrationTest {

    @Autowired
    CanonicalModelProcessorClient client;

   @Value("${cacis-ip.cmp.service.url}")
    private String serviceUrl;

    public static final String SOAP_MSG_FILENAME = "CMP_valid_CR.xml";


    @Test
    public void invokeStr() throws Exception, IOException {
         final URL url = getClass().getClassLoader().getResource(SOAP_MSG_FILENAME);
        String sampleMessage = FileUtils.readFileToString(new File(url.toURI()));
          client.acceptCanonical(serviceUrl,
                sampleMessage);
    }

}
