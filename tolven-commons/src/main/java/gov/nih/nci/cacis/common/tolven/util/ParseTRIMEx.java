package gov.nih.nci.cacis.common.tolven.util;

import org.tolven.trim.Trim;
import org.tolven.trim.parse.ParseTRIM;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class ParseTRIMEx extends ParseTRIM {

    /**
     * Marshall a Trim to a String
     *
     * @param trim trim message
     * @return String
     * @throws JAXBException exception
     */
    public String marshallToString(Trim trim) throws JAXBException {
        final StringWriter stringWriter = new StringWriter();

        final Marshaller marshaller = super.getMarshaller();
        marshaller.marshal(trim, stringWriter);

        return stringWriter.toString();
    }
}
