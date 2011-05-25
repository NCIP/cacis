
package com.mirth.connect.connectors.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mirth.connect.connectors.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AcceptMessageResponse_QNAME = new QName("http://ws.connectors.connect.mirth.com/", "acceptMessageResponse");
    private final static QName _AcceptMessage_QNAME = new QName("http://ws.connectors.connect.mirth.com/", "acceptMessage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mirth.connect.connectors.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AcceptMessageResponse }
     * 
     */
    public AcceptMessageResponse createAcceptMessageResponse() {
        return new AcceptMessageResponse();
    }

    /**
     * Create an instance of {@link AcceptMessage }
     * 
     */
    public AcceptMessage createAcceptMessage() {
        return new AcceptMessage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcceptMessageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.connectors.connect.mirth.com/", name = "acceptMessageResponse")
    public JAXBElement<AcceptMessageResponse> createAcceptMessageResponse(AcceptMessageResponse value) {
        return new JAXBElement<AcceptMessageResponse>(_AcceptMessageResponse_QNAME, AcceptMessageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcceptMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.connectors.connect.mirth.com/", name = "acceptMessage")
    public JAXBElement<AcceptMessage> createAcceptMessage(AcceptMessage value) {
        return new JAXBElement<AcceptMessage>(_AcceptMessage_QNAME, AcceptMessage.class, null, value);
    }

}
