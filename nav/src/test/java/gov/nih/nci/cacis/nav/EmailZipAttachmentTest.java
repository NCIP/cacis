/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import java.io.File;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.w3c.dom.Document;

public class EmailZipAttachmentTest {

    private static final Logger LOG = Logger.getLogger(EmailZipAttachmentTest.class.getName());

    @Test
    public void testCreateMessage() {
        try {
            AbstractSendMail abstractSendMail = new AbstractSendMail(new Properties(), "cacisdevsender@gmail.com",
                    "smtp.gmail.com", 587, "smtp");
            abstractSendMail.secEmailTempZipLocation = "C:/Users/ajay/.cacis/nav";
            abstractSendMail.createMessage("cacisdevsecureemailrecipient@gmail.com", "CLINICAL_NOTE", "RIMITS",
                    "HL7_V2_LINICAL_NOTE", "<content>sample</content>", "<content>metadata</content>", "title",
                    "indexbodytoken", "readmetoken");
            abstractSendMail.createMessage("cacisdevsecureemailrecipient@gmail.com", "CLINICAL_NOTE", "RIMITS",
                    "HL7_V2_CLINICAL_NOTE", "<content>sample</content>", "<content>metadata</content>", "title",
                    "indexbodytoken", "readmetoken");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }// fail("Not yet implemented"); }

//    @Test
//    public void testParseClinicalDocument() {
//        try {
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//            documentBuilderFactory.setNamespaceAware(true);
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//            Document document = documentBuilder.parse(new File("C:/projects/cacis/misc/Sample_Clinical_Document.xml"));
//
//            XPathFactory xpathFactory = XPathFactory.newInstance();
//            XPath xpath = xpathFactory.newXPath();
//            LOG.info(xpath.evaluate("/ClinicalDocument/title", document));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
