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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 * Tests for Navutils
 * @author joshua.phillips@semanticbits.com
 *
 */
public class NAVUtilsTest {

    private Document sig;
    
    /**
     * Setup sig document
     * @throws Exception - error thrown 
     */
    @Before
    public void setUp() throws Exception {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        final DocumentBuilder db = dbf.newDocumentBuilder();
        sig = db.parse(NAVUtilsTest.class.getClassLoader().getResourceAsStream("notification_gen.xml"));
    }

    /**
     * Tests registry id
     * @throws Exception - error thrown 
     */
    @Test
    public void testGetRegistryId() throws Exception {
        assertEquals(NAVUtils.getRegistryId(sig), "urn:oid:1.3.983249923.1234.3");
    }
    
    /**
     * Tests document ids
     * @throws Exception - error thrown 
     */
    @Test
    public void testGetDocumentIds() throws Exception {
        final List<String> ids = NAVUtils.getDocumentIds(sig);
        assertTrue(ids.size() == 2);
        assertEquals(ids.get(0), "urn:oid:1.3.345245354.435345");
    }
}
