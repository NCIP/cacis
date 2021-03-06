/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.util;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Saurabh.Agrawal@semanticbits.com
 * @since Feb 02,2011
 */
public class CommonsPropertyLoaderUtilTest {

    /**
     * Test the loadProperties method
     */
    @Test
    public void loadProperties() {
        assertNotNull(CommonsPropertyLoaderUtil.loadProperties("caehr-esd-war.properties", "caehr-esd-war"));
    }
}
