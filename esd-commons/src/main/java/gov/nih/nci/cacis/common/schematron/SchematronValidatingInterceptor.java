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
package gov.nih.nci.cacis.common.schematron;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author dkokotov
 * @author joshua
 * @since Aug 13, 2010
 *
 */
public class SchematronValidatingInterceptor extends AbstractPhaseInterceptor<Message> implements
        ApplicationContextAware {

    private static final String ERROR_WITH_VALIDATION = "Error doing schematron validation, skipping it";

    private static final Log LOG = LogFactory.getLog(SchematronValidatingInterceptor.class);

    /**
     * Spring AOP is providing pooling for the Transformers.
     */
    private ApplicationContext applicationContext;

    /**
     * The bean name for the evaluateRules Transformer.
     */
    private final String evaluateRulesBeanName;

    /**
     * The bean name for the extractErrors Transformer.
     */
    private final String extractFailuresBeanName;

    /**
     * Constructs the interceptor.
     *
     * @param evaluateRulesBeanName bean name of Transformation to evaluate schematron rules.
     * @param extractFailuresBeanName bean name Transformation to extract failures after rules have been evaluated.
     */
    public SchematronValidatingInterceptor(String evaluateRulesBeanName, String extractFailuresBeanName) {
        super(Phase.POST_STREAM);
        addAfter(StaxInInterceptor.class.getName());
        this.evaluateRulesBeanName = evaluateRulesBeanName;
        this.extractFailuresBeanName = extractFailuresBeanName;
    }

    /**
     * {@inheritDoc}
    */
    public void handleMessage(Message message) throws Fault {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Beginning schematron validation");
        }
        /**
         * This outputstream implementation will cache to a temp file if the input size surpasses a certain
         * threshold. The threshold and temp file directory can be configured with the following two system
         * properties: org.apache.cxf.io.CachedOutputStream.Threshold
         * org.apache.cxf.io.CachedOutputStream.OutputDirectory
         *
         * See the Apache CXF source for more information.
         */
        final CachedOutputStream bos = new CachedOutputStream();
        Document doc = null;
        try {
            //Read the contents of the message into a DOM and process
            doc = StaxUtils.read(message.getContent(XMLStreamReader.class));

            try {
                final XMLSerializer serial = new XMLSerializer(bos, new OutputFormat(doc));
                serial.serialize(doc);
                
                final String validationResult = validateCandidate(bos.getInputStream());

                if (StringUtils.isEmpty(validationResult)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Schematron validation passed");
                    }
                } else {
                    final SchematronException e = new SchematronException(validationResult);
                    message.getExchange().put(SchematronException.class, e);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Schematron validation failed");
                    }
                }
            } catch (SAXException e) {
                LOG.error(ERROR_WITH_VALIDATION, e);
            } catch (TransformerException e) {
                LOG.error(ERROR_WITH_VALIDATION, e);
            } catch (IOException e) {
                LOG.error(ERROR_WITH_VALIDATION, e);
            }

            //set the reader back in the message
            final XMLStreamReader reader = StaxUtils.createXMLStreamReader(new DOMSource(doc));
            message.setContent(XMLStreamReader.class, reader);

        } catch (XMLStreamException e) {
            //this is a fatal error. Can no longer continue service invocation
            LOG.error("Error parsing XML. Creating Fault", e);
            throw new Fault(e);
        }
    }

    private String validateCandidate(InputStream candidateStream) throws SAXException, TransformerException,
            TransformerConfigurationException, IOException {

        final CachedOutputStream cos = new CachedOutputStream();

        // Step 1. Apply XSLT to candidate
        final Transformer evaluateRules = (Transformer) applicationContext.getBean(evaluateRulesBeanName);
        evaluateRules.transform(new StreamSource(candidateStream), new StreamResult(cos));

        // Step 2. Extract failures
        // Result message is small. Don't need cachedoutputstream.
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final Transformer extractFailures = (Transformer) applicationContext.getBean(extractFailuresBeanName);
        extractFailures.transform(new StreamSource(cos.getInputStream()), new StreamResult(baos));

        return new String(baos.toByteArray());
    }

    /**
     * {@inheritDoc}
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
