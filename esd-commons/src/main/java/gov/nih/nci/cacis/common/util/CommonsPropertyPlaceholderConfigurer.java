package gov.nih.nci.cacis.common.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * PropertyPlaceholderConfigurer that resolves properties from any of following locations in the same order
 * <p/>
 * <pre>
 * #a)${user.home}/.cacis/${ant.project.name}/${ant.project.name}.properties
 * #b)${catalina.home}/conf/cacis/${ant.project.name} (where ${catalina.home) is a system property
 * #c)classpath:${ant.project.name}.properties
 * </pre>
 *
 * @author Saurabh.Agrawal@semanticbits.com
 * @since Feb 02,2011
 */
public final class CommonsPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    /**
     *
     * @param projectName  - project name
     * @param propertyFileName - property file name
     */
    public CommonsPropertyPlaceholderConfigurer(String projectName, String propertyFileName) {
        super();
        this.setProperties(CommonsPropertyLoaderUtil.loadProperties(projectName, propertyFileName));
    }

}

