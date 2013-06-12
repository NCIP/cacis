/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.transform;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author bpickeral
 * @since Sep 2, 2011
 */
public class XSLTTransformer {

    /**
     * Transformer using XSLT.
     */
    private final Transformer transformer;

    /**
     * Constructor
     *
     * @param transformer xslt transformer
     */
    public XSLTTransformer(Transformer transformer) {
        this.transformer = transformer;
    }

    /**
     * Transform XML to RDF
     *
     * @param params the set of parameters to pass to the transformation, may be null
     * @param in XML input stream
     * @param out XML output stream
     * @throws TransformerException exception
     */
    public void transform(Map<String, String> params, InputStream in, OutputStream out) throws TransformerException {
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                transformer.setParameter(entry.getKey(), entry.getValue());
            }
        }
        transformer.transform(new StreamSource(in), new StreamResult(out));
    }
}
