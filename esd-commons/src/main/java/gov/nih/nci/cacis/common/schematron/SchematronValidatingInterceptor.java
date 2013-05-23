/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
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
