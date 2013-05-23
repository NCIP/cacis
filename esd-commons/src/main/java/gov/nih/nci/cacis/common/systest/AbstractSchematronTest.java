/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.systest;

import gov.nih.nci.cacis.common.schematron.SchematronValidatingErrorInterceptor;
import gov.nih.nci.cacis.common.schematron.SchematronValidatingInterceptor;
import gov.nih.nci.cacis.common.service.OutBoundExceptionFactory;
import gov.nih.nci.cacis.common.test.TestUtils;
import org.apache.commons.logging.Log;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxws.EndpointImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Schematron Test for Testing Schematron Validation for an Object.
 * @author bpickeral
 * @since Mar 10, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractSchematronTest extends AbstractJaxWsTest {

    @Autowired
    private SchematronValidatingInterceptor schematronValidatingInterceptor;

    @Autowired
    private OutBoundExceptionFactory outBoundExceptionFactory;

    private EndpointImpl ep;

    /**
     * Get the Log for the class
     * @return the log
     */
    protected abstract Log getLog();

    /**
     * Gets the file name of the SOAP message that should fail schema validation.
     * @return file name
     */
    protected abstract String getFailedSchemaFileName();

    /**
     * Gets the file name of the SOAP message that should fail schematron validation.
     * @return file name
     */
    protected abstract String getFailedSchematronFileName();

    /**
     * Asserts specific errors are present in the response.
     * @param res response
     *
     *  @throws Exception - exception thrown on error
     */
    protected abstract void assertSchemaErrorMessages(Node res) throws Exception; // NOPMD Exception type is thrown

    /**
     * Asserts specific errors are present in the response.
     * @param res response
     *
     * @throws Exception - exception thrown on error
     */
    protected abstract void assertSchematronErrorMessages(Node res)
    throws Exception; // NOPMD Exception type is thrown

    /**
     * Gets project namespace.
     * @return namespace
     */
    protected abstract String getProjectNamespace();

    /**
     * Gets address endpoint.
     * @return address endpoint
     */
    protected abstract String getAdressEndpoint();

    /**
     * Gets the Object Implementor for Endpoint creation.
     * @return Object Implementor
     */
    protected abstract Object getObjectImplementor();

    /**
     * setup to be run at class loading
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        System.setProperty("isMockBackend", "true");
    }

    /**
     * Setups up namespace and Endpoint
     *
     * @throws Exception - exception thrown on error
     */
    @Override
    @Before
    public void setUpBus() throws Exception { // NOPMD - setUpBus throws Exception
        super.setUpBus();

        this.bus.getInInterceptors().add(this.schematronValidatingInterceptor);
        this.bus.getInInterceptors().add(new SchematronValidatingErrorInterceptor(this.outBoundExceptionFactory));

        addNamespaces();

        this.ep = new EndpointImpl(getBus(), getObjectImplementor());

        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("schema-validation-enabled", Boolean.TRUE);
        getEndpoint().setProperties(properties);
        getEndpoint().publish(getAdressEndpoint());
        getLog().info("Published server endpoint.");
    }

    /**
     * Adds namespaces that will be used for XPath expressions.
     */
    protected void addNamespaces() {
        addNamespace("h", "http://service.jaxws.cxf.apache.org/");
        addNamespace("ns2", getProjectNamespace());
        addNamespace("ns3", "urn:hl7-org:v3");
        addNamespace("svc", "http://service.jaxws.cxf.apache.org/");
    }

    /**
     * EndpointImpl
     *
     * @return Endpoint
     */
    @Override
    protected EndpointImpl getEndpoint() {
        return this.ep;
    }

    /**
     * Verifies that the flavor-specific schema rules are caught by schema validation.
     *
     * @throws Exception exception
     */
    @Test
    public void checkSchemaFlavorValidation() throws Exception { // NOPMD Exception type is thrown
        if (!StringUtils.isEmpty(getFailedSchematronFileName())) {
            final Node res = invokeSOAPMessage(getFailedSchemaFileName());

            assertValid("//s:Body/s:Fault", res);

            assertSchemaErrorMessages(res);
        }
    }

    /**
     * Test add operation with a message that causes a schematron violation.
     *
     * @throws Exception exception
     */
    @Test
    public void addWithSchematronError() throws Exception { // NOPMD Exception type is thrown
        if (!StringUtils.isEmpty(getFailedSchematronFileName())) {
            final Node res = invokeSOAPMessage(getFailedSchematronFileName());
            getLog().info(TestUtils.nodeToString(res));
            assertNotNull(res);
            assertValid("//s:Body/s:Fault", res);

            assertSchematronErrorMessages(res);
        }

    }

    /**
     * @return the schematronValidatingInterceptor
     */
    public SchematronValidatingInterceptor getSchematronValidatingInterceptor() {
        return this.schematronValidatingInterceptor;
    }


    /**
     * @param schematronValidatingInterceptor the schematronValidatingInterceptor to set
     */
    public void setSchematronValidatingInterceptor(SchematronValidatingInterceptor schematronValidatingInterceptor) {
        this.schematronValidatingInterceptor = schematronValidatingInterceptor;
    }
}
