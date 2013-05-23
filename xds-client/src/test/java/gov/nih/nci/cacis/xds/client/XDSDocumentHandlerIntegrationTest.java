/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.client;

import static org.junit.Assert.fail;
import gov.nih.nci.cacis.common.doc.DocumentHandler;
import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.service.DocumentAccessManager;
import gov.nih.nci.cacis.xds.authz.service.XdsWriteAuthzManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhealthtools.ihe.xds.metadata.extract.MetadataExtractionException;
import org.openhealthtools.ihe.xds.source.SubmitTransactionCompositionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test class for XDS document submission
 * 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-xds.xml")
@Ignore
public class XDSDocumentHandlerIntegrationTest {

    private static final String SUBJECT_DN =
    "CN=XDSb_REG_MISYS.ihe.net, OU=Connectathon, O=IHE International, L=Chicago, ST=Illinois, C=US";
    
    @Autowired
    @Qualifier("wrapperDocumentHandler")
    private DocumentHandler docHndlr;

    @Autowired
    private XdsWriteAuthzManager writeManager;

    @Autowired
    private DocumentAccessManager accessManager;
    
    private final Map<String, String> md = new HashMap<String, String>();

    /**
     * set up method
     * 
     * @throws IOException - exception thrown, if any
     * @throws URISyntaxException - exception thrown, if any
     * @throws AuthzProvisioningException - exception thrown, if any
     */
    @Before
    public void setup() throws URISyntaxException, IOException, AuthzProvisioningException {        
        md.put("docentry", getFileContent("docEntry.xml"));
        md.put("submissionset", getFileContent("submissionSet.xml"));
        md.put("docoid", "1.2.3.4"); // NOPMD
        md.put("docsourceoid", "1.3.6.1.4.1.21367.2010.1.2");
        md.put("content", getFileContent("person.xml"));

        writeManager.grantStoreWrite(SUBJECT_DN);
    }

    private String getFileContent(String fileName) throws URISyntaxException, IOException {
        final File docEntryFile = new File(getClass().getClassLoader().getResource(fileName).toURI());
        return FileUtils.readFileToString(docEntryFile);
    }

    /**
     * Test handling and retrieving document with XDSDocument handler
     * 
     * @throws ApplicationRuntimeException - exception thrown, if any
     * @throws SubmitTransactionCompositionException - exception thrown, if any
     * @throws MetadataExtractionException - exception thrown, if any
     * @throws IOException - exception thrown, if any
     * @throws AuthzProvisioningException - exception thrown, if any
     * @throws InterruptedException - asds
     */
    @SuppressWarnings("unchecked")
    @Test
    public void handleAndRetrieveDocument() throws ApplicationRuntimeException, MetadataExtractionException,
            SubmitTransactionCompositionException, IOException, AuthzProvisioningException, InterruptedException {
        String docUniqueId = null;
        InputStream ris = null;
        try {
            docUniqueId = docHndlr.handleDocument(md);
            
            Assert.assertNotNull(docUniqueId);

            accessManager.grantDocumentAccess(docUniqueId, SUBJECT_DN);

            ris = docHndlr.retrieveDocument(docUniqueId);
            Assert.assertNotNull(ris);
            //CHECKSTYLE:OFF
        } catch (Exception e) {
            //CHECKSTYLE:ON            
            fail("Not Expecting any exception -- " + e.getMessage());
            
        } finally {
            if (docUniqueId != null) {
                accessManager.revokeDocumentAccess(docUniqueId, SUBJECT_DN);
            }
            writeManager.revokeStoreWrite(SUBJECT_DN);
        }

    }

}
