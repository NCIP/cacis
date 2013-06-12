/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Provides a set of utility operations for NAV-related messages.
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 5, 2011
 * 
 */
public final class NAVUtils {

    /**
     * XPath expression to retrieve the registry ID
     */
    public static final String GET_REG_ID_EXP = "/*[local-name()='Signature']/*[local-name()='Object']"
            + "/*[local-name()='SignatureProperties']"
            + "/*[local-name()='SignatureProperty' and @Id='recommendedRegistry']";

    /**
     * XPath expression to retrieve document IDs
     */
    public static final String GET_DOC_IDS_EXP = "/*[local-name()='Signature']/*[local-name()='Object']"
            + "/*[local-name()='Manifest']/*[local-name()='Reference']/@URI";

    /**
     * XPath expression to retrieve a single document ID from a Reference element
     */
    public static final String GET_DOC_ID_EXP = "@URI";

    /**
     * XPath expression to retrieve the digest method algorithm used on a Reference element
     */
    public static final String GET_DIG_ALG_EXP = "*[local-name()='DigestMethod']/@Algorithm";

    /**
     * XPath expression to retrieve the digest value of a Reference element
     */
    public static final String GET_DIG_VAL_EXP = "*[local-name()='DigestValue']";

    /**
     * XPath expression to retrieve document reference elements
     */
    public static final String GET_DOC_REFS_EXP = "/*[local-name()='Signature']/*[local-name()='Object']"
            + "/*[local-name()='Manifest']/*[local-name()='Reference']";

    private NAVUtils() {

    }
    
    /**
     * Returns the message content as Multipar, if it is,
     * Otherwise, throws NotificationValidationException
     * @param message Message instance
     * @return instance of Multipart
     * @throws NotificationValidationException - validation error thrown
     */
    public static Multipart getMessageContent(Message message) throws NotificationValidationException {
        Object content = null;
        try {
            content = message.getContent();
            if ( !(content instanceof Multipart) ) {
                throw new NotificationValidationException("Message content is not a valid multipart content!");
            }
        } catch (IOException e) {
            throw new NotificationValidationException("Error getting message content", e);
        } catch (MessagingException e) {
            throw new NotificationValidationException("Error getting message content", e);
        }
        
        return (Multipart)content;
    }    
   

    /**
     * Returns a Document representing the notification (Signature) element.
     * 
     * @param message the multipart MIME email message
     * @return the Document
     * @throws MessagingException on error getting email content
     * @throws IOException on communication error
     * @throws ParserConfigurationException on XML parser configuration error
     * @throws SAXException on XML parsing error
     */
    public static Document getSignature(Message message) throws IOException, MessagingException,
            ParserConfigurationException, SAXException {
        final Multipart content = (Multipart) message.getContent();
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        final DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(content.getBodyPart(1).getInputStream());
    }

    /**
     * Returns the ID of the recommendedRegistry from the Signature
     * 
     * @param sig the Signature element
     * @return the registry ID
     * @throws XPathExpressionException on XPath evaluation error
     */
    public static String getRegistryId(Node sig) throws XPathExpressionException {
        return getNodeValue(sig, GET_REG_ID_EXP);
    }

    /**
     * Returns a list of document IDs from the Signature element
     * 
     * @param sig the Signature element
     * @return the document IDs
     * @throws XPathExpressionException on XPath evaluation error
     * @throws DOMException on DOM manipulation erro
     */
    public static List<String> getDocumentIds(Node sig) throws DOMException, XPathExpressionException {
        return getNodeValues(sig, GET_DOC_IDS_EXP);
    }

    /**
     * Returns value of the Algorithm attribute of the DigestMethod node
     * 
     * @param reference the Reference node
     * @return the value
     * @throws XPathExpressionException on XPath evaluation error
     */
    public static String getDigestAlgorithm(Node reference) throws XPathExpressionException {
        return getNodeValue(reference, GET_DIG_ALG_EXP);
    }

    /**
     * Returns the value of DigestValue node
     * 
     * @param reference the Reference node
     * @return the value
     * @throws XPathExpressionException on XPath evaluation error
     */
    public static String getDigestValue(Node reference) throws XPathExpressionException {
        return getNodeValue(reference, GET_DIG_VAL_EXP);
    }

    /**
     * Returns the value of the Id attribute on the Reference node
     * 
     * @param reference the Reference node
     * @return the value
     * @throws XPathExpressionException on XPath evaluation error
     */
    public static String getDocumentId(Node reference) throws XPathExpressionException {
        return getNodeValue(reference, GET_DOC_ID_EXP);
    }

    /**
     * Returns a List of Reference elements from the Manifest
     * 
     * @param sig the signature node
     * @return the List
     * @throws XPathExpressionException on XPath evaluation error
     */
    public static List<Node> getDocumentReferences(Node sig) throws XPathExpressionException {
        return getNodes(sig, GET_DOC_REFS_EXP);
    }

    private static String getNodeValue(Node node, String expStr) throws XPathExpressionException {
        final XPathFactory fac = XPathFactory.newInstance();
        final XPath xpath = fac.newXPath();
        final XPathExpression exp = xpath.compile(expStr);
        final Node selected = (Node) exp.evaluate(node, XPathConstants.NODE);
        return selected.getTextContent();
    }

    private static List<String> getNodeValues(Node node, String expStr) throws DOMException, XPathExpressionException {
        final List<String> values = new ArrayList<String>();
        for (Node selected : getNodes(node, expStr)) {
            values.add(selected.getNodeValue());
        }
        return values;
    }

    private static List<Node> getNodes(Node node, String expStr) throws XPathExpressionException {
        final List<Node> nodes = new ArrayList<Node>();
        final XPathFactory fac = XPathFactory.newInstance();
        final XPath xpath = fac.newXPath();
        final XPathExpression exp = xpath.compile(expStr);
        final NodeList nl = (NodeList) exp.evaluate(node, XPathConstants.NODESET);
        for (int i = 0; i < nl.getLength(); i++) {
            nodes.add(nl.item(i));
        }
        return nodes;
    }

}
