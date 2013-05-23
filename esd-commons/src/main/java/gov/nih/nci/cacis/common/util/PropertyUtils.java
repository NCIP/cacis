/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for manipulating Properties
 *
 * @author kherm manav.kher@semanticbits.com
 */
public final class PropertyUtils {

    private PropertyUtils() {
    }

/**
     * Load properties from file
     */
    /**
     * Load properties from file
     *
     * @param propertiesFile   Property file
     * @param fromAbsolutePath To use absoulte path or class loader
     * @return Properties
     * @throws IOException exception loading properties
     */
    public static Properties loadProperties(String propertiesFile, boolean fromAbsolutePath) throws IOException {
        InputStream input = null;
        try {
            if (fromAbsolutePath) {
                input = new FileInputStream(propertiesFile);
            } else {
                input = PropertyUtils.class.getClassLoader().getResourceAsStream(propertiesFile);
            }

            final Properties properties = new Properties();
            properties.load(input);
            return properties;

        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
}
