/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.sa.mirthconnect;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Marshals any object to xml based on its context
 * @author vinodh.rc@semanticbits.com
 *
 */
public class JAXBMarshaller {

    /**
     * Marshals Object to its xml format, based on its context
     * @param obj - object to be marshalled
     * @return marshalled string
     * @throws JAXBException exception thrown, if any
     */
    public String marshal(Object obj) throws JAXBException {
        final JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        
        final Marshaller m = jc.createMarshaller();
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        m.marshal(obj, pw);
        
        return sw.toString();
    }
}
