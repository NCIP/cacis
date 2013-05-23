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

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openrdf.model.Value;
import org.openrdf.query.*;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseCDWLoaderTest {
    protected static final String QUERY_END = "> WHERE {?s ?p ?o} LIMIT 1";
    protected static final String QUERY_PFX = "SELECT * FROM <";
    protected InputStream sampleMessageIS;


    @Before
    public void before() throws URISyntaxException, IOException, RepositoryConfigException, RepositoryException {
        sampleMessageIS = FileUtils.openInputStream(new File(getClass().getClassLoader()
                .getResource("caCISRequestSample3.xml").toURI()));
    }

    protected static Value[][] doTupleQuery(RepositoryConnection con, String query) throws RepositoryException,
            MalformedQueryException, QueryEvaluationException {
        final TupleQuery resultsTable = con.prepareTupleQuery(QueryLanguage.SPARQL, query);
        final TupleQueryResult bindings = resultsTable.evaluate();

        final List<Value[]> results = new ArrayList<Value[]>();
        BindingSet pairs = null;
        List<String> names = null;
        Value[] rv = null;

        while (bindings.hasNext()) {
            pairs = bindings.next();
            names = bindings.getBindingNames();
            rv = new Value[names.size()];
            for (int i = 0; i < names.size(); i++) {
                rv[i] = pairs.getValue(names.get(i));
            }
            results.add(rv);
        }
        return results.toArray(new Value[0][0]); // NOPMD
    }
}
