/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-mock-cdw-test.xml")
public class SesameInMemorySystemTest {

    public static final String CACIS_NS = "http://cacis.nci.nih.gov";

    public static final String RFF_FILE_NAME = "sample-rdf.xml";

    @Autowired
    RepositoryConnection con;
    

    @Test
    public void repository() throws RepositoryException, IOException, RDFParseException, URISyntaxException {

          try {
            Assert.assertNull(con.getNamespace(CACIS_NS));

            File rdfFile = new File(getClass().getClassLoader().getResource(SesameInMemorySystemTest.RFF_FILE_NAME).toURI());
            con.add(rdfFile, CACIS_NS, RDFFormat.RDFXML);

            Assert.assertNotNull("caCIS Namespace not found",
                    con.getNamespace("cacis"));
            Assert.assertEquals(CACIS_NS, con.getNamespace("cacis"));
        } finally {
            con.close();
        }

    }

}
