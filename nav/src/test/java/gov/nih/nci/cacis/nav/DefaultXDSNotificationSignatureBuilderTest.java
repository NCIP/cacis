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

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Node;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 10, 2011
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-nav-test.xml" } )
public class DefaultXDSNotificationSignatureBuilderTest {

    @Value("${nav.keystore.location}")
    private String keyStoreLocation;

    /**
     *
     * @throws Exception on error
     */
    @Test
    public void testBuildNotificationSignature() throws Exception {
        final String regId = "urn:oid:1.3.983249923.1234.3";
        final String docId1 = "urn:oid:1.3.345245354.435345";
        final String docId2 = "urn:oid:1.3.345245354.435346";

        final InMemoryCacheDocumentHolder docCache = new InMemoryCacheDocumentHolder(2048);
        docCache.putDocument(docId1, new File("sample_exchangeCCD.xml"));
        docCache.putDocument(docId2, new File("purchase_order.xml"));

        final InMemoryCacheXDSDocumentResolver xdsDocResolver = new InMemoryCacheXDSDocumentResolver(regId, docCache);
        final XDSNotificationSignatureBuilder sigBuilder = new DefaultXDSNotificationSignatureBuilder(xdsDocResolver,
                SignatureMethod.RSA_SHA1, DigestMethod.SHA256, "JKS", keyStoreLocation, "changeit", "nav_test");

        final String[] keys = { docId1, docId2 };
        final Node sig = sigBuilder.buildSignature(new ArrayList<String>(Arrays.asList(keys)));

        assertNotNull(sig);

        // Test to see if it can be transformed without error
        final TransformerFactory tf = TransformerFactory.newInstance();
        final Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(sig), new StreamResult(System.out));
        // trans.transform(new DOMSource(sig), new StreamResult(new FileOutputStream(
        // "src/test/resources/notification_gen.xml")));
    }

}
