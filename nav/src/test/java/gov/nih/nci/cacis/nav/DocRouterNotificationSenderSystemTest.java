/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.cacis.common.doc.DocumentHandler;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

/**
 * Tests DocRouterNotificationSender.
 * @author bpickeral
 * @since Aug 4, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-nav-test.xml" } )
//@Ignore
public class DocRouterNotificationSenderSystemTest {
    
    private static final int TEST_SMTP_PORT = 14125;

    @Value("${ext.file.location}")
    private String extSupportedFilesLoc;
    
    private GreenMail server;
    
    @Autowired
    private NotificationSender sender;
    
    @Autowired
    @Qualifier("wrapperDocumentHandler")
    private DocumentHandler docHndlr;

    /**
     * Sample file name
     */
    public static final String MSG_FILENAME = "sample_exchangeCCD.xml";
    
    /**
     * setup method
     */
    @Before
    public void setup() {
        //for some reason, the greenmail server doesnt start in time
        //will attempt max times to ensure all service gets startedup 
        server = new GreenMail(new ServerSetup(TEST_SMTP_PORT, null, ServerSetup.PROTOCOL_SMTP));
        int i = 0;
        final int max = 2;
        while ( i < max ) {
            try {
                server.start();
                //CHECKSTYLE:OFF
            } catch (RuntimeException e) { //NOPMD
              //CHECKSTYLE:ON
                i++;
                continue;
            }
            i = max;
        }
        
    }
    
    /**
     * teardown method
     */
    @After
    public void tearDown() {
        server.stop();
    }

    /**
     *
     * @throws Exception on error
     */
    @Test
    public void testNotificationSenderNonSecure() throws Exception {
        final String docCont = getValidMessage();
        final String docId = docHndlr.handleDocument(getDocumentMetadata(docCont));
        assertNotNull(docId);
        final NotificationSenderImpl senderImpl = (NotificationSenderImpl) sender;
        senderImpl.setPort(TEST_SMTP_PORT);
        sender.setCredentials("", "");
        List<String> docIds = Arrays.asList( new String[]{docId} );
        sender.send("some.one@somewhere.com", docIds);

        assertTrue(server.getReceivedMessages().length == 1);
    }
    
    private HashMap<String, String> getDocumentMetadata(String docContent) throws URISyntaxException, IOException {        
        
        final HashMap<String, String> md = new HashMap<String, String>();
        md.put("docentry", getFileContent(extSupportedFilesLoc + "/docEntry.xml"));
        md.put("submissionset", getFileContent(extSupportedFilesLoc + "/submissionSet.xml"));
        md.put("docoid", "1.2.3.4"); // NOPMD
        md.put("docsourceoid", "1.3.6.1.4.1.21367.2010.1.2");
        md.put("content", getValidMessage());
        
        return md;
    }
    
    private String getFileContent(String fileName) throws URISyntaxException, IOException {
        final File docEntryFile = new File(fileName);
        return FileUtils.readFileToString(docEntryFile);
    }

    private String getValidMessage() throws IOException, URISyntaxException {
        final URL url = getClass().getClassLoader().getResource(MSG_FILENAME);
        return FileUtils.readFileToString(new File(url.toURI()));
    }
}
