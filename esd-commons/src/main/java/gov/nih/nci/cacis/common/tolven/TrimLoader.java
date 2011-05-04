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

package gov.nih.nci.cacis.common.tolven;

import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tolven.client.load.TolvenLoadClient;
import org.tolven.core.entity.AccountUser;
import org.tolven.core.entity.TolvenUser;
import org.tolven.doc.bean.TolvenMessage;
import org.tolven.doc.bean.TolvenMessageWithAttachments;
import org.tolven.trim.Trim;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class TrimLoader extends TolvenLoadClient {

    private static final String TRIM_NS = "urn:tolven-org:trim:4.0";
    private static final String TRIM_PACKAGE = "org.tolven.trim";

    private static JAXBContext jc;

    private static final Log LOG = LogFactory.getLog(TrimLoader.class);

    /**
     * constructor
     * 
     * @param configDir tolven config dir
     */
    public TrimLoader(String configDir) {
        super(configDir);
    }

    /**
     * Load trim to a user account
     * 
     * @param userName tolven username
     * @param adminPassword admin password
     * @param accountId account of the user
     * @param authFile tolven.auth file
     * @param tolvenClientProps tolven-client.properties file path
     * @param trimFile trim file path
     * @param fromAbsolutePath flag used for loading properties file
     */
    public void loadTrim(String userName, String adminPassword, long accountId, String authFile, // NOPMD
            String tolvenClientProps, String trimFile, boolean fromAbsolutePath) {
        InputStream trimFileIn = null; // NOPMD
        try {
            if (fromAbsolutePath) {
                trimFileIn = new FileInputStream(trimFile);
            } else {
                trimFileIn = TrimLoader.class.getClassLoader().getResourceAsStream(trimFile);
            }

            final Trim uploadTrim = parseTrim(trimFileIn);

            loadTrim(userName, adminPassword, accountId, authFile, tolvenClientProps, uploadTrim, fromAbsolutePath);
            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            final String msg = "Error occured in posting the trim message to the server ";
            LOG.error(msg, e);
            throw new ApplicationRuntimeException(msg, e);
        } finally {
            if (trimFileIn != null) {
                try {
                    trimFileIn.close();
                } catch (final IOException e) {
                    LOG.error("Failed to close trim file stream:", e);
                }
            }

        }
    }

    /**
     * Load trim to a user account
     * 
     * @param userName tolven username
     * @param adminPassword admin password
     * @param accountId account of the user
     * @param authFile tolven.auth file
     * @param tolvenClientProps tolven-client.properties file path
     * @param trim Trim object
     * @param fromAbsolutePath flag used for loading properties file
     */
    public void loadTrim(String userName, String adminPassword, long accountId, String authFile, // NOPMD
            String tolvenClientProps, Trim trim, boolean fromAbsolutePath) {
        try {
            String authFileURL;
            if (fromAbsolutePath) {
                authFileURL = authFile;
            } else {
                authFileURL = TrimLoader.class.getClassLoader().getResource(authFile).toURI().toString();
            }

            // Must login to run remote command
            login(userName, adminPassword.toCharArray(), authFileURL, loadProperties(tolvenClientProps,
                    fromAbsolutePath));

            final AccountUser accountUser = getAccountBean().findAccountUser(userName, accountId);
            final TolvenUser tolvenUser = accountUser.getUser();

            final TolvenMessageWithAttachments tm = createTolvenMessage(TRIM_NS, accountUser.getAccount().getId(),
                    tolvenUser.getId());
            addTrimAsPayload(trim, tm);
            getDocBean().queueTolvenMessage(tm);
            LOG.info("Trim message was successfully submitted to the server");
            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            final String msg = "Error occured in posting the trim message to the server ";
            LOG.error(msg, e);
            throw new ApplicationRuntimeException(msg, e);
        }
    }

    /**
     * Load properties from file
     */
    private Properties loadProperties(String propertiesFile, boolean fromAbsolutePath) throws IOException {
        InputStream input = null;
        try {
            if (fromAbsolutePath) {
                input = new FileInputStream(propertiesFile);
            } else {
                input = TrimLoader.class.getClassLoader().getResourceAsStream(propertiesFile);
            }
            final Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (final IOException ex) {
            throw new RuntimeException("Could not load properties file: " + propertiesFile, ex);// NOPMD
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * Create a TolvenMessage payload wrapper. Notice that the accountId and user id must be supplied in the wrapper.
     * Tolven does not accept anonymous messages.
     * 
     * @param ns The namespace that defines the payload
     * @param accountId account id of user
     * @param userId user id
     * @return TolvenMessage
     */
    public TolvenMessageWithAttachments createTolvenMessage(String ns, long accountId, long userId) {
        final TolvenMessageWithAttachments tm = new TolvenMessageWithAttachments();
        tm.setAccountId(accountId);
        tm.setAuthorId(userId);
        tm.setXmlNS(ns);
        return tm;
    }

    /**
     * Marshal a Trim object graph to XML and add it to the message.
     * 
     * @param trim trim file
     * @param tm Tolven Message
     * @throws javax.xml.bind.JAXBException exception
     */
    public void addTrimAsPayload(Trim trim, TolvenMessage tm) throws JAXBException {
        final JAXBContext jCtx = setupJAXBContext();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final Marshaller m = jCtx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(trim, output);
        tm.setPayload(output.toByteArray());
    }

    /**
     * parse Trim file
     * 
     * @param trimFile trim file
     * @return Trim
     * @throws JAXBException exception
     */
    public Trim parseTrim(InputStream trimFile) throws JAXBException {
        final JAXBContext jCtx = setupJAXBContext();
        final Unmarshaller um = jCtx.createUnmarshaller();
        return (Trim) um.unmarshal(trimFile);

    }

    /**
     * @return JAXBContext
     * @throws JAXBException exception
     */
    private static JAXBContext setupJAXBContext() throws JAXBException {
        if (jc == null) { // NOPMD
            jc = JAXBContext.newInstance(TRIM_PACKAGE);
        }
        return jc;
    }

}
