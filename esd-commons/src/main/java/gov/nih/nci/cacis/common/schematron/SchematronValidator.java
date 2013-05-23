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


import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.cxf.io.CachedOutputStream;

/**
 * Schematron based validation for xml
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 *
 */
public class SchematronValidator {

    private static final String ERROR_WITH_VALIDATION = "Schematron validation error,";

    /**
     * The evaluateRules Transformer.
     */
    private final Transformer evaluateRulesTransformer;

    /**
     * The extractErrors Transformer.
     */
    private final Transformer extractFailuresTransformer;
    
    
    /**
     * Constructor that takes transformers to evaluate rules and extract errors
     * @param evaluateRulesTransformer - XSL Transformer for evaluating rules
     * @param extractFailuresTransformer - XSL transformer for extracting errors
     */
    public SchematronValidator(Transformer evaluateRulesTransformer, Transformer extractFailuresTransformer) {
        super();
        this.evaluateRulesTransformer = evaluateRulesTransformer;
        this.extractFailuresTransformer = extractFailuresTransformer;
    }
    
    /**
     * validates xml string with schematron
     * @param xmlString - source xml string
     * @return String - Schematron validation output
     */
    public String validate(String xmlString) {

        try {
            final CachedOutputStream cos = new CachedOutputStream();

            // Step 1. Apply XSLT to candidate        
            evaluateRulesTransformer.transform(new StreamSource(new StringReader(xmlString)), new StreamResult(cos));

            // Step 2. Extract failures
            // Result message is small. Don't need cachedoutputstream.
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            extractFailuresTransformer.transform(new StreamSource(cos.getInputStream()), new StreamResult(baos));

            return new String(baos.toByteArray());
        } catch (TransformerException e) {
            throw new ApplicationRuntimeException(ERROR_WITH_VALIDATION + e.getMessage(), e);
        } catch (IOException e) {
            throw new ApplicationRuntimeException(ERROR_WITH_VALIDATION + e.getMessage(), e);
        }
    }

}
