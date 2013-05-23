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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.openrdf.model.Value;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@ContextConfiguration(locations = "classpath:applicationContext-mock-cdw-test.xml")
public class CDWLoaderSystemTest extends BaseCDWLoaderTest {

    @Autowired
    RepositoryConnection con;

    @Autowired
    CDWLoader loader;

    private static final String GRPH_GROUP_STUDY_ID = "2.3.4.2";
    private static final String GRPH_GROUP_SITE_ID = "2.3.4.0";
    private static final String GRPH_GROUP_P1_ID = "2.3.4.1";

    @Test
    public void xmlToRDFLoad() throws Exception {
        final org.openrdf.model.URI context = con.getRepository().getValueFactory().createURI(loader.generateContext());
        File xslF = new File(getClass().getClassLoader().getResource("caCISRequestSample3.xml").toURI());

        String xmlString = FileUtils.readFileToString(xslF);

        loader.load(xmlString, context.toString(), GRPH_GROUP_STUDY_ID, GRPH_GROUP_SITE_ID, GRPH_GROUP_P1_ID);

        final String query = QUERY_PFX + context + QUERY_END;
        final Value[][] results = doTupleQuery(con, query);
        assertTrue(results.length > 0);
    }

    @Test
    public void generateContext() throws TransformerException, RepositoryException, IOException, RDFParseException {
        assertTrue(StringUtils.startsWith(loader.generateContext(), "http://cacis.nci.nih.gov/"));
    }

}
