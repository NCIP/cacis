package gov.nih.nci.cacis.xds.client;

import gov.nih.nci.cacis.common.test.TestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.Assert.assertNotNull;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-xds.xml")
public class MetadataSuppliedDocumentHandlerIntegrationTest {

    @Autowired
    MetadataSuppliedDocumentHandler metadataSuppliedDocumentHandler;
    

    @Test
    public void init() throws IOException, URISyntaxException {
        assertNotNull(metadataSuppliedDocumentHandler);
        String ccdStr =
                new String(TestUtils.getBytesFromFile(getClass().getClassLoader().
                        getResource("ccd_message.xml")));
        metadataSuppliedDocumentHandler.handleDocument(ccdStr);

    }


}
