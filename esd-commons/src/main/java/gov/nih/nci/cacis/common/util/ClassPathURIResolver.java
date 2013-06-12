/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.util;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Resolves a URI against a base classpath. The default base classpath is "/".
 *
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Sep 13, 2010
 */
public class ClassPathURIResolver implements URIResolver, LSResourceResolver {

    private Class clazz = ClassPathURIResolver.class;
    
    private String base = "";
    
    /**
     * Assumes the default base classpath.
     * @param clazz The base class to use
     */
    public ClassPathURIResolver(Class clazz) {
        this.clazz = clazz;
    }
    
    /**
     * Sets the default base classpath.
     * @param base - base classpath to use for all files to be resolved
     */
    public ClassPathURIResolver(String base) {
        this.base = base;
    }

    /**
     * @param base - base classpath to use for all files to be resolved
     * @param clazz The base class to use.
     */
    public ClassPathURIResolver(String base, Class clazz) {
        this.base = base;
        this.clazz = clazz;
    }

    /**
     * {@inheritDoc}
     */
    public Source resolve(String href, String base) throws TransformerException {
        final String currBase = (base.length() == 0) ? this.base : base;
        final InputStream is = clazz.getResourceAsStream(currBase + href);
        return new StreamSource(is);
    }

    @Override
    public LSInput resolveResource(String type, String namespaceURI,
                                   String publicId, String systemId, String baseURI) {
        final InputStream resourceAsStream = clazz.getClassLoader()
                .getResourceAsStream(systemId);
        return new Input(publicId, systemId, resourceAsStream);

    }
}
