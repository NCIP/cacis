/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.config;

import gov.nih.nci.cacis.common.util.AnyBasePathURIResolver;
import gov.nih.nci.cacis.transform.GenerateNarrativeTransformer;
import gov.nih.nci.cacis.transform.HL7V2Transformer;
import gov.nih.nci.cacis.transform.RIMITSTransformer;
import gov.nih.nci.cacis.transform.SourceTransformer;
import gov.nih.nci.cacis.transform.Trim2CCDTransformer;
import gov.nih.nci.cacis.transform.XmlToRdfTransformer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@Configuration
public class XSLTTransformerConfig {

    @Value("${cacis-pco.transformer.xml2rdf.xsl}")
    private String rdfToXmlXsl;

    @Value("${cacis-pco.transformer.hl7v2.xsl}")
    private String hl7v2Xsl;

    @Value("${cacis-pco.transformer.rimits.xsl}")
    private String rimITSXsl;

    @Value("${cacis-pco.transformer.xsl.baseClassPath}")
    private String xslBaseClassPath;

    @Value("${cacis-pco.transformer.trim2ccd.xsl}")
    private String trim2CCDXsl;

    @Value("${cacis-pco.transformer.generate-narrative.xsl}")
    private String generateNarrativeXsl;

    @Value("${cacis-pco.source.system.baseClassPath}")
    private String sourceSystemBaseClassPath;
    
    @Value("${cacis-pco.source.system.xslt.properties.file}")
    private String sourceSystemXSLTProperties;

    private static final String PROTOTYPE = "prototype";

    /**
     * {@inheritDoc}
     */
    public URIResolver xslUriResolver() {
        return new AnyBasePathURIResolver(xslBaseClassPath);
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
     * XML To RDF Transformer
     * 
     * @return XmlToRdfTransformer transformer
     * @throws TransformerException exception
     */
    @Bean
    @Scope(PROTOTYPE)
    public XmlToRdfTransformer xmlToRdfTransformer() throws TransformerException {
        final Transformer transformer = xslTransformerFactory().newTransformer(
                xslUriResolver().resolve(rdfToXmlXsl, xslBaseClassPath));
        return new XmlToRdfTransformer(transformer);
    }

    /**
     * 
     * @return HL7V2Transformer transformer
     * @throws TransformerException exception
     */
    @Bean
    @Scope(PROTOTYPE)
    public HL7V2Transformer hL7V2Transformer() throws TransformerException {
        final Transformer transformer = xslTransformerFactory().newTransformer(
                xslUriResolver().resolve(hl7v2Xsl, xslBaseClassPath));
        return new HL7V2Transformer(transformer);
    }

    /**
     * 
     * @return HL7V2Transformer transformer
     * @throws TransformerException exception
     */
    @Bean
    @Scope(PROTOTYPE)
    public RIMITSTransformer rIMITSTransformer() throws TransformerException {
        final Transformer transformer = xslTransformerFactory().newTransformer(
                xslUriResolver().resolve(rimITSXsl, xslBaseClassPath));
        return new RIMITSTransformer(transformer);
    }

    /**
     * Trim to CCD Transformer
     * 
     * @return HL7V2Transformer transformer
     * @throws TransformerException exception
     */
    @Bean
    @Scope(PROTOTYPE)
    public Trim2CCDTransformer trim2CCDTransformer() throws TransformerException {
        final Transformer transformer = xslTransformerFactory().newTransformer(
                xslUriResolver().resolve(trim2CCDXsl, xslBaseClassPath));
        return new Trim2CCDTransformer(transformer);
    }

    @Bean
    @Scope(PROTOTYPE)
    public SourceTransformer sourceTransformer(String sourceSystemIdentifier) throws TransformerException {
        try {
            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(sourceSystemXSLTProperties);
            String propertyValue = propertiesConfiguration.getString(sourceSystemIdentifier);
            if (propertyValue == null) {
                throw new TransformerException(String.format("Source system '%s' is not configured in [%s]!",
                        sourceSystemIdentifier, sourceSystemXSLTProperties));
            }
            final Transformer transformer = xslTransformerFactory().newTransformer(
                    xslUriResolver().resolve(propertyValue, sourceSystemBaseClassPath));
            return new SourceTransformer(transformer);
        } catch (ConfigurationException e) {
            throw new TransformerException(String.format("Property '%s' cannot be read from [%s]. Either the "
                    + "property / properties file does not exist or the properties file is not readable!",
                    sourceSystemIdentifier, sourceSystemXSLTProperties)
                    + e);
        }
    }

    /**
     * Generate Narrative Transformer
     * 
     * @return GenerateNarrativeTransformer transformer
     * @throws TransformerException exception
     */
    @Bean
    @Scope(PROTOTYPE)
    public GenerateNarrativeTransformer generateNarrativeTransformer() throws TransformerException {
        final Transformer transformer = xslTransformerFactory().newTransformer(
                xslUriResolver().resolve(generateNarrativeXsl, xslBaseClassPath));
        return new GenerateNarrativeTransformer(transformer);
    }

}
