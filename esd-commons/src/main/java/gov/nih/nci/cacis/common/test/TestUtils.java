/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.test;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public final class TestUtils {

    /**
     * private default constructor to avoid instantiation of utility class
     */
    private TestUtils() {

    }

    /**
     * Returns contents of a file as byte array
     *
     * @param fileName - URL of the source file
     * @return byte[] - content of the file as byte[]
     * @throws java.io.IOException        - IOExpetion thrown
     * @throws java.net.URISyntaxException - URISyntaxException thrown
     */
    public static byte[] getBytesFromFile(URL fileName) throws IOException, URISyntaxException {
        return FileUtils.readFileToByteArray(new File(fileName.toURI()));
    }

    /**
     * Reads an XML file as a DOM
     *
     * @param fileName XML file name
     * @return Document XML
     * @throws java.net.URISyntaxException           exception
     * @throws javax.xml.parsers.ParserConfigurationException exception
     * @throws java.io.IOException                  exception
     * @throws org.xml.sax.SAXException                 exception
     */
    public static Document getXMLDoc(URL fileName) throws URISyntaxException, ParserConfigurationException,
            IOException, SAXException {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        final DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(new File(fileName.toURI()));
    }

    /**
     * Will get an XML Document as a String
     *
     * @param doc XML Document
     * @return String xml as String
     * @throws javax.xml.transform.TransformerException exception
     */
    public static String xmlToString(Document doc) throws TransformerException {
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();

        // initialize StreamResult with File object to save to file
        final StreamResult result = new StreamResult(new StringWriter());
        final DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
        return result.getWriter().toString();
    }

    /**
     * Will get an w3c Node element as a String
     *
     * @param node XML Node
     * @return String xml as String
     * @throws javax.xml.transform.TransformerException exception
     */
    public static String nodeToString(Node node) throws TransformerException {
        final StringWriter sw = new StringWriter();
        final Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();
    }


}
