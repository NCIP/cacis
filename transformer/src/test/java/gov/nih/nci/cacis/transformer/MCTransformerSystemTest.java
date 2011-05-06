package gov.nih.nci.cacis.transformer;

import java.io.File;
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
	 * WS tests Mirth Connect transformer XCCD to CDF
	 *
	 * @author vinodh.rc@semanticbits.com
	 */
	public class MCTransformerSystemTest extends AbstractCXFTest {
	    /**
	     * Constant message for successful transformation.
	     */
	    protected static final String SUCCESS_MSG = "File successfully written";

	    private static final Log LOG = LogFactory.getLog(MCTransformerSystemTest.class);

	    /**
	     * Cosntant value for the endpoint address
	     */
	    protected static final String ADDRESS = "http://localhost:9082/services/Mirth?wsdl";
	    
	    private JaxWsDynamicClientFactory dcf;
	    
	    private Client client;
	    
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
	    public void setUpBus() throws Exception { // NOPMD - setUpBus throws Exception
	        super.setUpBus();
	        
	        if (dcf == null || client == null) {
	        	dcf = JaxWsDynamicClientFactory.newInstance();
	        	client = dcf.createClient(ADDRESS);
	        }
	    }
	   	    
	    /**
	     * Pass an invalid message and check for invalid response
	     *
	     * @throws Exception exception
	     */
	    @Test
	    public void invalidMessage() throws Exception {   // NOPMD Exception type is thrown

	        final String invalidMessage = "Invalid Message";

	        final Object[] res = client.invoke("acceptMessage", invalidMessage);
        	LOG.info("Echo response: " + res[0]);
        	
        	assertNull("Invalid message should have returned NULL message",
	                res[0]);
	    }
	    
	    /**
	     * Pass a valid message and check for success message
	     *
	     * @throws Exception exception
	     */
	    @Test
	    public void validMessage() throws Exception {   // NOPMD Exception type is thrown
	    	
	    	final URL url = getClass().getClassLoader().getResource("WS2-CDF-XCCD-valid-soap.xml");
	    	final File msgFile = new File(url.toURI());
	        final String validMessage = FileUtils.readFileToString(msgFile);

	        final Object[] res = client.invoke("acceptMessage", validMessage);
        	LOG.info("Echo response: " + res[0]);
        	
        	assertTrue("Valid message should have written file successfully",
	                ((String)res[0]).startsWith(SUCCESS_MSG));
	    }    
}
