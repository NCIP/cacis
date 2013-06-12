/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openrdf.model.*;
import org.openrdf.model.impl.GraphImpl;
import org.openrdf.query.*;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.ntriples.NTriplesWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for communicating with Virutoso using Sesame provider
 *
 * @author vinodh.rc@semanticbits.com
 *
 */
public class VirtuosoSesameTest extends AbstractSesameTest {

    private static final String QUERY_END = "> WHERE {?s ?p ?o} LIMIT 1";

    private static final String QUERY_PFX = "SELECT * FROM <";

    private static final Log LOG = LogFactory.getLog(VirtuosoSesameTest.class);

    private URI untesturi = null;
    private URI unname = null;

    private URI shermanmonroe = null;
    private BNode snode = null;
    private URI name = null;
    private Literal nameValue = null;

    private URI context = null;
    @Autowired
    private Repository repository;
    @Autowired
    private RepositoryConnection con;

    private boolean onceInitialized = false;

    /**
     * Initialized required items
     * @throws RepositoryConfigException - error thrown,  if any
     * @throws RepositoryException - error thrown,  if any
     */
    @Before
    public void setup() throws RepositoryConfigException, RepositoryException {
        if (!onceInitialized) {

            context = repository.getValueFactory().createURI("http://demo.openlinksw.com/demo#this");

            untesturi = repository.getValueFactory().createURI("http://mso.monrai.com/foaf/unicodeTest");
            unname = repository.getValueFactory().createURI("http://mso.monrai.com/foaf/name");

            shermanmonroe = repository.getValueFactory().createURI("http://mso.monrai.com/foaf/shermanMonroe");
            snode = repository.getValueFactory().createBNode("smonroeNode");
            name = repository.getValueFactory().createURI("http://mso.monrai.com/foaf/name");
            nameValue = repository.getValueFactory().createLiteral("Sherman Monroe");

            onceInitialized = true;
        }

        con = repository.getConnection();
        con.setAutoCommit(true);
    }

    /**
     * teardown for clearing up resources
     */
    @After
    public void tearDown() {
        if (con != null) {
            try {
                con.close();
            } catch (RepositoryException e) {
                LOG.info("Error while closing the repository connection", e);
            }
        }
    }

    /**
     * Tests adding data to repo through API
     * @throws RDFParseException - error thrown, if any
     * @throws RepositoryException - error thrown, if any
     * @throws IOException - error thrown, if any
     * @throws MalformedQueryException - error thrown, if any
     * @throws QueryEvaluationException - error thrown, if any
     */
    @Test
    public void addDataToRepo() throws RDFParseException, RepositoryException, IOException, MalformedQueryException,
            QueryEvaluationException {
        // test add data to the repository
        // test query data
        final String query = QUERY_PFX + context + QUERY_END;
        LOG.info("Loading data from URL: " + getSampleRdfFile1Url());
        con.add(getSampleRdfFile1Url(), "", RDFFormat.RDFXML, context);
        final Value[][] results = doTupleQuery(con, query);

        assertTrue(results.length > 0);
    }

    /**
     * Tests clearing triple store
     * @throws RepositoryException - error thrown, if any
     * @throws IOException - error thrown, if any
     * @throws RDFParseException - error thrown, if any
     */
    @Test
    public void clearTripleStore() throws RepositoryException, RDFParseException, IOException {
        LOG.info("Clearing triple store");
        con.add(getSampleRdfFile1Url(), "", RDFFormat.RDFXML, context);
        long sz = con.size(context);
        assertTrue(sz > 0);

        con.clear(context);

        sz = con.size(context);
        assertTrue(sz == 0);
    }

    /**
     * Tests adding triples data from file
     * @throws RDFParseException - error thrown, if any
     * @throws RepositoryException - error thrown, if any
     * @throws IOException - error thrown, if any
     * @throws MalformedQueryException - error thrown, if any
     * @throws QueryEvaluationException - error thrown, if any
     */
    @Test
    public void addDataFromFileToRepo() throws RDFParseException, RepositoryException, IOException,
            MalformedQueryException, QueryEvaluationException {
        // test add data from a flat file
        final File dataFile = getSampleDataFile1();
        LOG.info("Loading data from file: " + dataFile);
        con.add(dataFile, "", RDFFormat.NTRIPLES, context);
        final String query = QUERY_PFX + context + QUERY_END;
        final Value[][] results = doTupleQuery(con, query);

        assertNotNull(results);
        assertTrue(results.length > 0);
    }

    /**
     * Tests with UNICODE data
     * @throws UnsupportedEncodingException - error thrown, if any
     * @throws RepositoryException - error thrown, if any
     * @throws MalformedQueryException - error thrown, if any
     * @throws QueryEvaluationException - error thrown, if any
     */
    @Test
    public void addUnicodeDataToRepo() throws UnsupportedEncodingException, RepositoryException,
            MalformedQueryException, QueryEvaluationException {
        final byte[] utf8data = { (byte) 0xd0, (byte) 0xbf, (byte) 0xd1,
                (byte) 0x80, (byte) 0xd0, (byte) 0xb8, (byte) 0xd0,
                (byte) 0xb2, (byte) 0xd0, (byte) 0xb5, (byte) 0xd1, (byte) 0x82 };
        final String utf8str = new String(utf8data, "UTF8");

        final Literal unValue = repository.getValueFactory().createLiteral(utf8str);

        con.clear(context);
        LOG.info("Loading UNICODE single triple");
        con.add(untesturi, unname, unValue, context);
        final String query = QUERY_PFX + context + QUERY_END;
        final Value[][] results = doTupleQuery(con, query);

        assertTrue(results.length > 0);
        assertTrue(results[0][0].toString().equals(untesturi.toString()));
        assertTrue(results[0][1].toString().equals(unname.toString()));
        assertTrue(results[0][2].toString().equals(unValue.toString()));
    }

    /**
     * Tests adding a single triple with api
     * @throws RepositoryException - error thrown, if any
     * @throws MalformedQueryException - error thrown, if any
     * @throws QueryEvaluationException - error thrown, if any
     */
    @Test
    public void addSingleTripleRepo() throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        con.clear(context);
        LOG.info("Loading single triple");
        con.add(snode, name, nameValue, context);
        final String query = QUERY_PFX + context + QUERY_END;
        final Value[][] results = doTupleQuery(con, query);

        assertTrue(results.length > 0);

        LOG.info("Casted value type");

        assertTrue(results[0][0] instanceof BNode);
        assertTrue(results[0][1] instanceof URI);
        assertTrue(results[0][2] instanceof Literal); // true
    }

    /**
     * Tests queryig property
     * @throws RepositoryException - error thrown, if any
     * @throws MalformedQueryException - error thrown, if any
     * @throws QueryEvaluationException - error thrown, if any
     */
    @Test
    public void queryProperty() throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        LOG.info("Selecting property");
        con.clear(context);
        con.add(shermanmonroe, name, nameValue, context);

        final String query = QUERY_PFX + context + "> WHERE {?s <http://mso.monrai.com/foaf/name> ?o} LIMIT 1";
        final Value[][] results = doTupleQuery(con, query);
        assertTrue(results.length > 0);

        boolean exists = false;
        exists = con.hasStatement(shermanmonroe, name, null, false, context);
        assertTrue("Triple wasn't added", exists);
        // test remove a statement
        con.remove(shermanmonroe, name, nameValue, (Resource) context);
        // test statement removed
        LOG.info("Statement does not exists");
        exists = con.hasStatement(shermanmonroe, name, null, false, context);
        assertTrue("Triple wasn't removed", !exists);
    }

    /**
     * checks for statements
     * @throws RepositoryException - error thrown, if any
     * @throws MalformedQueryException - error thrown, if any
     * @throws QueryEvaluationException - error thrown, if any
     */
    @Test
    public void checkStatementByResultsetSize() throws RepositoryException, MalformedQueryException,
            QueryEvaluationException {
        boolean exists = false;
        LOG.info("Statement exists (by resultset size)");
        con.clear(context);
        con.add(shermanmonroe, name, nameValue, context);
        exists = con.hasStatement(shermanmonroe, name, null, false, context);
        assertTrue("Triple wasn't added", exists);
        final String query = QUERY_PFX + context + "> WHERE {?s <http://mso.monrai.com/foaf/name> ?o} LIMIT 1";
        final Value[][] results = doTupleQuery(con, query);
        assertTrue(results.length > 0);
    }

    /**
     * Tests getting all statements
     * @throws RepositoryException - error thrown, if any
     * @throws RDFHandlerException - error thrown, if any
     */
    @Test
    public void getStatements() throws RepositoryException, RDFHandlerException {
        // test getStatements and RepositoryResult implementation
        LOG.info("Retrieving statement (" + shermanmonroe + " " + name + " " + null + ")");
        con.clear(context);
        con.add(shermanmonroe, name, nameValue, context);
        final RepositoryResult<Statement> statements = con.getStatements(shermanmonroe, name, null, false, context);
        assertTrue(statements.hasNext());
        // test export and handlers
        LOG.info("Writing the statements ");
        final StringWriter sw = new StringWriter();
        final RDFHandler ntw = new NTriplesWriter(sw);
        con.exportStatements(shermanmonroe, name, null, false, ntw);

        assertNotNull(sw.toString());
    }

    /**
     * Tests getting graph ids
     * @throws RepositoryException - error thrown, if any
     */
    @Test
    public void getGraphIds() throws RepositoryException {
        LOG.info("Retrieving graph ids");
        con.clear(context);
        con.add(shermanmonroe, name, nameValue, context);
        final RepositoryResult<Resource> contexts = con.getContextIDs();
        assertNotNull(contexts);
        assertTrue(contexts.hasNext());
    }

    /**
     * Tests with ASK query
     * @throws RepositoryException - error thrown, if any
     * @throws MalformedQueryException - error thrown, if any
     * @throws QueryEvaluationException - error thrown, if any
     */
    @Test
    public void askQuery() throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        // do ask
        LOG.info("Sending ask query");
        con.clear(context);
        con.add(shermanmonroe, name, nameValue, context);
        final String query = "ASK FROM <" + context + "> {?s <http://mso.monrai.com/foaf/name> ?o}";
        final boolean result = doBooleanQuery(con, query);
        assertTrue(result);
    }

    /**
     * Tests CONSTRUCT query
     * @throws RepositoryException - error thrown, if any
     * @throws MalformedQueryException - error thrown, if any
     * @throws QueryEvaluationException - error thrown, if any
     */
    @Test
    public void performConstruct() throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        // do construct
        boolean statementFound = true;
        LOG.info("Sending construct query");
        con.clear(context);
        con.add(shermanmonroe, name, nameValue, context);
        final String query = "CONSTRUCT {?s <http://mso.monrai.com/mlo/handle> ?o} FROM <" + context
                + "> WHERE {?s <http://mso.monrai.com/foaf/name> ?o}";
        final Graph graph = doGraphQuery(con, query);
        assertTrue(graph.size() > 0);
        final Iterator<Statement> it = graph.iterator();
        Statement st = null; //NOPMD
        while (it.hasNext()) {
            st = it.next();
            if (!st.getPredicate().stringValue().equals("http://mso.monrai.com/mlo/handle")) {
                statementFound = false;
            }
        }
        assertTrue(statementFound);
    }

    /**
     * Tests DESCRIBE query
     * @throws RepositoryException - error thrown, if any
     * @throws MalformedQueryException - error thrown, if any
     * @throws QueryEvaluationException - error thrown, if any
     */
    @Test
    public void describeQuery() throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        // do describe
        LOG.info("Sending describe query");
        con.clear(context);
        con.add(shermanmonroe, name, nameValue, context);
        final String query = "DESCRIBE ?s FROM <" + context + "> WHERE {?s <http://mso.monrai.com/foaf/name> ?o}";
        final Graph graph = doGraphQuery(con, query);
        final Iterator<Statement> it = graph.iterator();
        assertTrue(graph.size() > 0);
        assertTrue(it.hasNext());
    }

    private static boolean doBooleanQuery(RepositoryConnection con, String query) throws RepositoryException,
            MalformedQueryException, QueryEvaluationException {
        final BooleanQuery resultsTable = con.prepareBooleanQuery(QueryLanguage.SPARQL, query);
        return resultsTable.evaluate();
    }

    private static Value[][] doTupleQuery(RepositoryConnection con, String query) throws RepositoryException,
            MalformedQueryException, QueryEvaluationException {
        final TupleQuery resultsTable = con.prepareTupleQuery(QueryLanguage.SPARQL, query);
        final TupleQueryResult bindings = resultsTable.evaluate();

        final List<Value[]> results = new ArrayList<Value[]>();
        BindingSet pairs = null;
        List<String> names = null;
        Value[] rv = null;

        while (bindings.hasNext() ) {
            pairs = bindings.next();
            names = bindings.getBindingNames();
            rv = new Value[names.size()];
            for (int i = 0; i < names.size(); i++) {
                rv[i] = pairs.getValue(names.get(i));
            }
            results.add(rv);
        }
        return (Value[][]) results.toArray(new Value[0][0]); //NOPMD
    }

    private static Graph doGraphQuery(RepositoryConnection con, String query) throws RepositoryException,
            MalformedQueryException, QueryEvaluationException {
        final GraphQuery resultsTable = con.prepareGraphQuery(QueryLanguage.SPARQL, query);
        final GraphQueryResult statements = resultsTable.evaluate();
        final Graph gq = new GraphImpl();

        Statement pairs = null; //NOPMD
        while (statements.hasNext()) {
            pairs = statements.next();
            gq.add(pairs);
        }
        return gq;
    }

}
