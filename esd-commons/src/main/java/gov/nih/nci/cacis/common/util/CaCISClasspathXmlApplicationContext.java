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

import java.net.MalformedURLException;

import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Cacis spring application context that uses custom classloader
 * 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 * 
 */
public class CaCISClasspathXmlApplicationContext extends ClassPathXmlApplicationContext {

    private static final String MC_CACIS_LIB = "./cacis-lib/";

    /**
     * @param configLocations Array of spring xml file locations
     * @throws BeansException - Exception thrown, if any
     * @throws MalformedURLException - Exception thrown, if any
     */
    public CaCISClasspathXmlApplicationContext(String... configLocations) throws BeansException, MalformedURLException {
        this(MC_CACIS_LIB, configLocations);
    }

    /**
     * @param configLocation spring xml file location
     * @throws BeansException - Exception thrown, if any
     * @throws MalformedURLException - Exception thrown, if any
     */
    public CaCISClasspathXmlApplicationContext(String configLocation) throws BeansException, MalformedURLException {
        this(MC_CACIS_LIB, configLocation);
    }

    /**
     * @param jarLocation cutom cacis lib location
     * @param configLocations Array of spring xml file locations
     * @throws BeansException - Exception thrown, if any
     * @throws MalformedURLException - Exception thrown, if any
     */
    public CaCISClasspathXmlApplicationContext(String jarLocation, String... configLocations) throws BeansException,
            MalformedURLException {
        super();
        init(jarLocation);
        setConfigLocations(configLocations);
        refresh();
    }

    /**
     * @param jarLocation cutom cacis lib location
     * @param configLocation spring xml file location
     * @throws BeansException - Exception thrown, if any
     * @throws MalformedURLException - Exception thrown, if any
     */
    public CaCISClasspathXmlApplicationContext(String jarLocation, String configLocation) throws BeansException,
            MalformedURLException {
        super();
        init(jarLocation);
        setConfigLocation(configLocation);
        refresh();
    }

    private void init(String jarLocation) throws MalformedURLException {
        final CaCISURLClassLoader jcl = new CaCISURLClassLoader(jarLocation);
        setClassLoader(jcl);
    }
}
