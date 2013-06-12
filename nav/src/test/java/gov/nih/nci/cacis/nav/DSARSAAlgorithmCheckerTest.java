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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.xml.crypto.dsig.SignatureMethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author bpickeral
 * @since Jun 21, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-nav-test.xml" } )
public class DSARSAAlgorithmCheckerTest {

    @Autowired
    private AlgorithmChecker algorithmChecker;

    /**
     * A matching DSA URI and algorithm name returns true.
     */
    @Test
    public void testAlgEqualsDSA() {
        assertTrue(algorithmChecker.algEquals(SignatureMethod.DSA_SHA1, DSARSAAlgorithmChecker.DSA_ALGORITHM));
    }

    /**
     * A matching RSA URI and algorithm name returns true.
     */
    @Test
    public void testAlgEqualsRSA() {
        assertTrue(algorithmChecker.algEquals(SignatureMethod.RSA_SHA1, DSARSAAlgorithmChecker.RSA_ALGORITHM));
    }

    /**
     * Case where URI and algorithm name does not match.
     */
    @Test
    public void testAlgEqualsNoMatch() {
        assertFalse(algorithmChecker.algEquals(SignatureMethod.RSA_SHA1, DSARSAAlgorithmChecker.DSA_ALGORITHM));
    }
}
