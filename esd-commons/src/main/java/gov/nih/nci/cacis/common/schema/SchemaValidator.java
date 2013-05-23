/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.schema;

import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import gov.nih.nci.cacis.common.util.ClassPathURIResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * Schema based validation for xml
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 *
 */
public class SchemaValidator {

    // define the type of schema - we use W3C:
    private static final String SCH_LANG = "http://www.w3.org/2001/XMLSchema";
    
    private final Validator validator;
        
    /**
     * Constructor with the schema file location
     * if not found in absoulute file location, will check classpath
     * @param schemaFile - main schema file
     */
    public SchemaValidator(String schemaFile) {
        super();
        
        try {
            // get validation driver:
            final SchemaFactory factory = SchemaFactory.newInstance(SCH_LANG);
            factory.setResourceResolver(new ClassPathURIResolver(getClass()));
            // create schema by reading it from an XSD file:
            final InputStream is = getClass().getClassLoader().getResourceAsStream(schemaFile);
            final Schema schema = factory.newSchema(new StreamSource(is));            
            validator = schema.newValidator();
        } catch (SAXException e) {
            throw new ApplicationRuntimeException("Error initializing schema validator, " + e.getMessage(), e);
        }
    }
    
    
    /**
     * validate xml with schema
     * @param xmlString - xml string to be validated
     * @throws ApplicationRuntimeException - exception thrown, if any
     */
    public void validate(String xmlString) throws ApplicationRuntimeException {        
        try {
            //  perform validation:
            validator.validate(new StreamSource(new StringReader(xmlString)));

        } catch (SAXException ex) {
            throw new ApplicationRuntimeException("Validation error:" + ex.getMessage(), ex);
        } catch (IOException e) {
            throw new ApplicationRuntimeException("Error validating the source xml:" + e.getMessage(), e);
        }
    }
}
