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

import com.google.common.base.Preconditions;
import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * This utility class resolve properties from any of following locations in the same order
 * <pre>
 * #a)${user.home}/.cacis/${project.name}/${propertyFile.name}
 * #b)${catalina.home}/conf/cacis/${project.name}/${propertyFile.name} (where ${catalina.home) is a system property
 * #c)classpath:${propertyFile.name}
 * </pre>
 * property files will be used in order. So if property is available at
 * ${user.home}/.cacis/${project.name}/${propertyFile.name}
 * is available, project will simply ignore that property from other property files.
 *
 * @author Saurabh.Agrawal@semanticbits.com
 * @since Feb 02,2011
 */
public final class CommonsPropertyLoaderUtil {

    private static final Logger LOG = Logger.getLogger(CommonsPropertyLoaderUtil.class);

    private static final String USER_HOME = "user.home";
    private static final String TOMCAT_HOME = "catalina.home";

    /**
     * Utility classes cannot have public constructor
     */
    private CommonsPropertyLoaderUtil() {
        // private constructor
    }

    /**
     * Load properties  from  following locations in mentioned order
     * <pre>
     * #a)${user.home}/.cacis/${ant.project.name}/${ant.project.name}.properties  (where ${user.home) is a system property)
     * #b)${catalina.home}/conf/cacis/${ant.project.name} (where ${catalina.home) is a system property)
     * #c)classpath:${ant.project.name}.properties
     * </pre>
     *
     * @param projectName      - project name
     * @param propertyFileName - property file name
     * @return properties if it can resolve any of the property file, exceptions if no property file is available
     */
    public static Properties loadProperties(final String projectName, final String propertyFileName) {
        Preconditions.checkNotNull(projectName, "Project name must not be null");
        Preconditions.checkNotNull(propertyFileName, "property file name must not be null");

        final String userHomePathToScan = String.format("user.home/.cacis/%s/%s", projectName, propertyFileName);
        final String tomcatHomePathToScan = String.format("catalina.home/conf/cacis/%s/%s",
                projectName, propertyFileName);

        final Properties properties = new Properties();

        //1. first load property from classpath
        fillProperties(new ClassPathResource(propertyFileName), properties);

        //2. now merge properties from tomcat
        fillPropertiesFromFileSystem(properties, TOMCAT_HOME, tomcatHomePathToScan);

        //3. finally merge properties from user.home
        fillPropertiesFromFileSystem(properties, USER_HOME, userHomePathToScan);

        for (Map.Entry<Object,Object> entry : properties.entrySet()) {
            LOG.info(String.format("loaded property - %s:%s", entry.getKey(), entry.getValue()));
        }
        return properties;

    }

    /**
     * fill properties from file system
     *
     * @param properties          - properties to merge
     * @param environmentVariable - environment variable (for ex : user.home, tomcat.home etc)
     * @param pathToScan          - the detailed path to scan
     */
    private static void fillPropertiesFromFileSystem(Properties properties, final String environmentVariable,
                                                     String pathToScan) {
        if (StringUtils.isBlank(System.getProperty(environmentVariable))) {
            LOG.info(String.format("%s is not set. So skipping property lookup from %s",
                    environmentVariable, environmentVariable));
        } else {
            final String detailedEnvironmentPath =
                    StringUtils.replace(pathToScan, environmentVariable, System.getProperty(environmentVariable));
            fillProperties(new FileSystemResource(detailedEnvironmentPath), properties);
        }
    }


    /**
     * fill properties from given resource
     *
     * @param resource   - resource
     * @param properties - properties to fill
     */
    private static void fillProperties(Resource resource, Properties properties) {
        try {
            if (resource.exists()) {
                LOG.info("merging properties form : " + resource.getDescription());
                PropertiesLoaderUtils.fillProperties(properties, resource);
            } else {
                LOG.info(String.format("can not merge property from %s as resource does not exists.",
                        resource.getDescription()));
            }
        } catch (IOException e) {
            final String message = "error while loading properties from resource" + resource;
            LOG.error(message, e);
            throw new ApplicationRuntimeException(message, e);
        }
    }

}

