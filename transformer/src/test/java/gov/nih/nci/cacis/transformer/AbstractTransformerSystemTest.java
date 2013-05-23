/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.transformer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.BusFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.test.AbstractCXFTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Base class provides method to testing and validating transformer with various messages. As a default a sanity check
 * of invalid and valid message testing is provided.
 * 
 * @author bhumphrey
 * @author vinodh.rc@semanticbits.com
 * @since May 10, 2011
 * 
 */
public abstract class AbstractTransformerSystemTest extends AbstractCXFTest {

    /**
     * Constant message for successful transformation.
     */
    protected static final String SUCCESS_MSG = "File successfully written";

    private JaxWsDynamicClientFactory dcf;
    private Client client;

    private static final Log LOG = LogFactory.getLog(AbstractTransformerSystemTest.class);

    /**
     * reset CXF bus
     */
    @BeforeClass
    public static void checkBus() {
        if (BusFactory.getDefaultBus(false) != null) {
            BusFactory.setDefaultBus(null);
        }
    }

    /**
     * Setups up namespace and Endpoint
     * 
     * @throws Exception - exception thrown
     */
    @Before
    public void setUpBus() throws Exception { // NOPMD - setUpBus throws
        // Exception
        super.setUpBus();

        if (dcf == null || client == null) {
            dcf = JaxWsDynamicClientFactory.newInstance();
            client = dcf.createClient(getWSDLAddress());
        }
    }

    /**
     * @return string
     */
    protected abstract String getWSDLAddress();

    /**
     * Pass an invalid message and check for invalid response.
     * 
     * @throws Exception exception
     */
    @Test
    public void invalidMessage() throws Exception { // NOPMD Exception type is
        // thrown

        testTransformer(new Message() {

            String getMessage() {
                return getInvalidMessage();
            }
        }, new ResponseValidator() {

            String getDescription() {
                return "Invalid message should have returned NULL message";
            }

            boolean accept(Object[] res) {
                return isResponseInValid(res);
            }
        });

    }

    /**
     * Pass a valid message and check for success message. Sanity Check.
     * 
     * @throws Exception exception
     */
    @Test
    public void validMessage() throws Exception { // NOPMD Exception type is
        // thrown

        testTransformer(new Message() {

            String getMessage() {
                return getValidMessage();
            }
        }, new ResponseValidator() {

            String getDescription() {
                return "Valid message should have written file successfully";
            }

            boolean accept(Object[] res) {
                return isResponseValid(res);
            }
        });
    }

    /**
     * Structure to pass the operation to get the message.
     * 
     * @author bhumphrey
     * @since May 10, 2011
     * 
     */
    abstract class Message {

        /**
         * 
         * @return the message to test.
         */
        abstract String getMessage();
    }

    /**
     * Class to hold the validation assert fail message and the logic to validate the response.
     * 
     * @author bhumphrey
     * @since May 10, 2011
     * 
     */
    abstract class ResponseValidator {

        /**
         * Performs the validation. Can be validating for positive case or negative case. True just means that response
         * was as expected.
         * 
         * @param res
         * @return true is this passes validation
         */
        abstract boolean accept(Object[] res);

        /**
         * Housekeeping method to give useful assert messages.
         * 
         * @return description of the acceptance criteria
         */
        abstract String getDescription();
    }

    /**
     * Test transformer strategy. Send a message and validate the response.
     * 
     * @param message - Message instance for the transforer
     * @param validator - Response validator instance
     * @throws Exception - error thrown, if any
     */
    protected void testTransformer(Message message, ResponseValidator validator) throws Exception {
        final Object[] res = client.invoke("acceptMessage", message.getMessage());
        LOG.info("Echo response: " + res[0]);
        assertTrue(validator.getDescription(), validator.accept(res));
    }

    /**
     * @param res
     */
    private boolean isResponseInValid(Object[] res) {
        return res[0] == null;
    }

    /**
     * Part of sanity check of a negative test case. Proves that some validation is taking place and that the positive
     * test isn't always passing.
     * 
     * @return an invalid message.
     */
    protected abstract String getInvalidMessage();

    /**
     * Gets a valid Message. Default implementation reads a valid SOAPMessage that has been serialized to a file.
     * 
     * @see getValidSOAPMessageFilename
     * @return string representation of a valid message
     */
    protected String getValidMessage() {
        final URL url = getClass().getClassLoader().getResource(getValidSOAPMessageFilename());
        File msgFile = null;
        try {
            msgFile = new File(url.toURI());
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String validMessage = null;
        try {
            validMessage = FileUtils.readFileToString(msgFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return validMessage;
    }

    /**
     * Override this method to identify the message under test. default null implementation provided to ease extension.
     * But this method must be overridden for the test to pass.
     * 
     * @return String representing the valid soap message file name
     */
    protected String getValidSOAPMessageFilename() {
        return null;
    }

    /**
     * @param res
     */
    private boolean isResponseValid(final Object[] res) {
        return res != null && res[0] != null && ((String) res[0]).startsWith(SUCCESS_MSG);
    }

}
