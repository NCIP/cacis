/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The caEHR Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and 5AM Solutions Inc, SemanticBits LLC, and AgileX Technologies, Inc (collectively 'SubContractors').
 * To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United
 * States Code, section 105.
 * 
 * This caEHR Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the caEHR Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly
 * display, publicly perform, and prepare derivative works of the caEHR Software; (ii) distribute and have distributed
 * to and by third parties the caEHR Software and any modifications and derivative works thereof; and (iii) sublicense
 * the foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
 * third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting or right of
 * payment from You or Your sub-licensees for the rights granted under this License. This License is granted at no
 * charge to You.
 * 
 * Your redistributions of the source code for the Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code
 * form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 
 * Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: This
 * product includes software developed by the National Cancer Institute and SubContractor parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 * 
 * You may not use the names "The National Cancer Institute", "NCI", or any SubContractor party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or any of the subcontracted parties, except as required to comply with
 * the terms of this License.
 * 
 * For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs
 * and into any third party proprietary programs. However, if You incorporate the Software into third party proprietary
 * programs, You agree that You are solely responsible for obtaining any permission from such third parties required to
 * incorporate the Software into such third party proprietary programs and for informing Your sub-licensees, including
 * without limitation Your end-users, of their obligation to secure any required permissions from such third parties
 * before incorporating the Software into such third party proprietary software programs. In the event that You fail to
 * obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to the
 * extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 
 * For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and
 * to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses
 * of modifications of the Software, or any derivative works of the Software as a whole, provided Your use,
 * reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
 * 
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, ANY OF ITS SUBCONTRACTED PARTIES OR THEIR AFFILIATES BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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

    /**
     * Cosntant value for the endpoint address
     */
    protected static final String ADDRESS = "http://localhost:9082/services/Mirth?wsdl";

    private JaxWsDynamicClientFactory dcf;
    private Client client;

    static final Log LOG = LogFactory.getLog(AbstractTransformerSystemTest.class);

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
     */
    public AbstractTransformerSystemTest() {
        super();
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
     * @return
     */
    protected String getWSDLAddress() {
        return ADDRESS;
    }

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
     * @param message
     * @param validator
     * @throws Exception
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
    abstract protected String getInvalidMessage();

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
     * @return
     */
    protected String getValidSOAPMessageFilename() {
        return null;
    }

    /**
     * @param res
     */
    private boolean isResponseValid(final Object[] res) {
        return ((String) res[0]).startsWith(SUCCESS_MSG);
    }

}
