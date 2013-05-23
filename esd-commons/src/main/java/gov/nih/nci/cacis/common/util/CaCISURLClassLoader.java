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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * CaCIS custom classloader that loads jars from custom lib location
 * 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 * 
 */

public class CaCISURLClassLoader extends URLClassLoader {

    /**
     * constructor that takes the custom lib location
     * 
     * @param jarsLocation String representing the location of the jars
     * @throws MalformedURLException - exception thrown, if any
     */
    public CaCISURLClassLoader(String jarsLocation) throws MalformedURLException {
        super(getCustomUrls(jarsLocation), CaCISURLClassLoader.class.getClassLoader());
    }
    
    /**
     * constructor that takes the custom lib location and parent classloader
     * 
     * @param jarsLocation String representing the location of the jars
     * @param parent parent classloader
     * @throws MalformedURLException - exception thrown, if any
     */
    public CaCISURLClassLoader(String jarsLocation, ClassLoader parent) throws MalformedURLException {
        super(getCustomUrls(jarsLocation), parent);
    }

    private static URL[] getCustomUrls(String jarsLocation) throws MalformedURLException {
        final File loc = new File(jarsLocation);
        if (!loc.exists()) {
            throw new IllegalArgumentException("The specified jars location does not exist, " + jarsLocation);
        }
        final File[] jarFiles = loc.listFiles();
        if (jarFiles == null || jarFiles.length == 0) {
            throw new IllegalArgumentException("The specified jars location does not contain any jars, " + jarsLocation);
        }

        final List<URL> urlsList = new ArrayList<URL>();
        for (File file : jarFiles) {
            urlsList.add(file.toURI().toURL());
        }
        return (URL[]) urlsList.toArray(new URL[urlsList.size()]);
    }
}
