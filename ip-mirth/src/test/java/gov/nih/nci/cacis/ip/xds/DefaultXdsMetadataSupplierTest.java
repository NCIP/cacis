/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.ip.xds;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;


/**
 * @author monish.dombla@semanticbits.com
 */
public class DefaultXdsMetadataSupplierTest {
    
    private final DefaultXdsMetadataSupplier defaultXdsMetadataSupplier =
        new DefaultXdsMetadataSupplier();
    
    
    /**
     * 
     * @throws IOException exception
     * @throws URISyntaxException exception
     * @throws JAXBException 
     */
    @Test
    public void createMetadata() throws IOException, JAXBException, URISyntaxException {
        
        final File file = new File(getClass().
                getClassLoader().getResource("caCISRequest_With_RoutingInstructions.xml").toURI());

        final String docEntryMetadata = defaultXdsMetadataSupplier.createDocEntry(FileUtils.readFileToString(file));
        
        
        assertTrue(docEntryMetadata.contains("HLv2 OBX Message"));
        assertTrue(docEntryMetadata.contains("DM123456"));
        
        assertNotNull(docEntryMetadata);
        
        final String submissionSetMetadata = 
            defaultXdsMetadataSupplier.createSubmissionSet(FileUtils.readFileToString(file));
        assertNotNull(submissionSetMetadata);
        assertTrue(docEntryMetadata.contains("DM123456"));
        
        assertNotNull(defaultXdsMetadataSupplier.createDocOID());
        assertNotNull(defaultXdsMetadataSupplier.createDocSourceOID());
    }
}