/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.transform;

import javax.xml.transform.Transformer;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class XmlToRdfTransformer extends XSLTTransformer {
    /**
     * Constructor.
     *
     * @param transformer xslt transformer
     */
    public XmlToRdfTransformer(Transformer transformer) {
        super(transformer);
    }
}
