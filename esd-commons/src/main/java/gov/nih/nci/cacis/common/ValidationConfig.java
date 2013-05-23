/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common;

import gov.nih.nci.cacis.common.schema.SchemaValidator;
import gov.nih.nci.cacis.common.schematron.SchematronValidator;
import gov.nih.nci.cacis.common.util.ClassPathURIResolver;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * cacis validation components config
 * 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 */
@Configuration
public class ValidationConfig {
    
    @Value("${cacis.validation.cacisrequest.sa.schema.file}")
    private String cacisReqSASchemaFile;
    
    @Value("${cacis.validation.cacisrequest.sa.sourcedata.schema.file}")
    private String cacisReqSrcDataSchemaFile;
    
    @Value("${cacis.validation.canonical.schema.file}")
    private String canonicalSchemaFile;
    
    @Value("${cacis.validation.schematron.extract.failures.xsl}")
    private String extractFailuresSchematronXsl;
    
    @Value("${cacis.validation.cacisrequest.sa.sourcedata.schematron.xsl}")
    private String cacisReqSASrcDataSchematronXsl;
    
    @Value("${cacis.validation.cacisrequest.canonical.schematron.xsl}")
    private String cacisReqCanonicalSchematronXsl;

    @Value("${cacis.validation.cacisrequest.schematron.xsl.baseClassPath}")
    private String xslBaseClassPath;


    /**
     * {@inheritDoc}
     */
    public URIResolver xslUriResolver() {
        return new ClassPathURIResolver(xslBaseClassPath,ValidationConfig.class);
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    public TransformerFactory xslTransformerFactory() {
        final TransformerFactory tf = TransformerFactory.newInstance();
        tf.setURIResolver(xslUriResolver());
        return tf;
    }


    /**
     * Schematron transformer for SA cacis request
     * @return Transformer SA Cacis Request Schematron Transformer
     * @throws TransformerException exception
     */
    @Bean
    public Transformer cacisRequestSASourceDataSchematronTransformer() throws TransformerException {
        final Transformer xslTransformer = xslTransformerFactory().
                newTransformer(xslUriResolver().resolve(cacisReqSASrcDataSchematronXsl, xslBaseClassPath));
        return xslTransformer;
    }
    
    /**
     * Schematron transformer for Canonical cacis request
     * @return Transformer Canonical Cacis Request Schematron Transformer
     * @throws TransformerException exception
     */
    @Bean
    public Transformer cacisRequestCanonicalSchematronTransformer() throws TransformerException {
        final Transformer xslTransformer = xslTransformerFactory().
                newTransformer(xslUriResolver().resolve(cacisReqCanonicalSchematronXsl, xslBaseClassPath));
        return xslTransformer;
    }
    
    /**
     * Extract schematron failures transformer
     * @return Transformer Extract Schematron Failures Transformer
     * @throws TransformerException exception
     */
    @Bean
    public Transformer extractSchematronFailuresTransformer() throws TransformerException {
        final Transformer xslTransformer = xslTransformerFactory().
                newTransformer(xslUriResolver().resolve(extractFailuresSchematronXsl, xslBaseClassPath));
        return xslTransformer;
    }
    
    /**
     * Cacis request SA schematron validator
     * @return SchematronValidator SA Cacis request schematron validator
     * @throws TransformerException exception
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SchematronValidator cacisRequestSASourceDataSchematronValidator() throws TransformerException {
        return new SchematronValidator(cacisRequestSASourceDataSchematronTransformer(), 
                extractSchematronFailuresTransformer());
    }
    
    /**
     * Cacis request Canonical schematron validator
     * @return SchematronValidator Canonical Cacis request schematron validator
     * @throws TransformerException exception
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SchematronValidator cacisRequestCanonicalSchematronValidator() throws TransformerException {
        return new SchematronValidator(cacisRequestCanonicalSchematronTransformer(), 
                extractSchematronFailuresTransformer());
    }
    
    /**
     * Cacis request schema validator
     * @return SchemaValidator Cacis request schema validator
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SchemaValidator cacisRequestSASchemaValidator() {
        return new SchemaValidator(cacisReqSASchemaFile);
    }
    
    /**
     * Cacis request source data schema validator
     * @return SchemaValidator Cacis request source data schema validator
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SchemaValidator cacisRequestSASourceDataSchemaValidator() {
        return new SchemaValidator(cacisReqSrcDataSchemaFile);
    }
    
    /**
     * Canonical schema validator
     * @return SchemaValidator canonical schema validator
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SchemaValidator canonicalSchemaValidator() {
        return new SchemaValidator(canonicalSchemaFile);
    }


}
