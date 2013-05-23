/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Resolves a URI against any absolute base path.
 *
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Sep 13, 2010
 */
public class AnyBasePathURIResolver implements URIResolver, LSResourceResolver {
    
    private String base = "/";
    
    /**
     * Assumes the default base classpath.
     */
    public AnyBasePathURIResolver() {
        //do nothing
    }
    
    /**
     * Sets the default base classpath.
     * @param base - base classpath to use for all files to be resolved
     */
    public AnyBasePathURIResolver(String base) {
        this.base = base;
    }

    
    /**
     * {@inheritDoc}
     */
    public Source resolve(String href, String base) throws TransformerException {
        final String currBase = (base.length() == 0) ? this.base : base;
        try {
            final InputStream is = 
                new FileInputStream(currBase + href);
            return new StreamSource(is);
        } catch (FileNotFoundException e) {
            return null;
        }
        
    }

    @Override
    public LSInput resolveResource(String type, String namespaceURI,
                                   String publicId, String systemId, String baseURI) {
        final String currBase = (baseURI.length() == 0) ? this.base : baseURI;
        try {
            final InputStream resourceAsStream = 
                new FileInputStream(currBase + systemId);
            return new Input(publicId, systemId, resourceAsStream);
        } catch (FileNotFoundException e) {
            return new Input(publicId, systemId, null);
        }

    }

}
