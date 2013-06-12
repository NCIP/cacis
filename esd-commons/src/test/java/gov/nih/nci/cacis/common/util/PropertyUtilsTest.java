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

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

/**
 * @author bpickeral
 * @since Jun 22, 2011
 */
public class PropertyUtilsTest {

    /**
     * Test loadProperties.
     * @throws Exception on error
     */
    @Test
    public void loadProperties() throws Exception {
        final Properties tolvenClientProps = PropertyUtils.loadProperties("test.properties", false);
        assertEquals("test", tolvenClientProps.getProperty("test.property"));
    }

    /**
     * Test loadProperties from absolute path.
     * @throws Exception on error
     */
    @Test
    public void loadPropertiesFromAbsolutePath() throws Exception {
        final Properties tolvenClientProps = PropertyUtils.loadProperties(getClass().getClassLoader()
                .getResource("test.properties").getPath(), true);
        assertEquals("test", tolvenClientProps.getProperty("test.property"));
    }

}
