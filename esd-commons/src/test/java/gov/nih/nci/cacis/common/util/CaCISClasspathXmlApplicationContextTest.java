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
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CaCISClasspathXmlApplicationContextTest {
    private static final Logger LOG = Logger.getLogger(CaCISClasspathXmlApplicationContextTest.class);
    /**
     * Tests class loading without jcl
     */
    @Test(expected = CannotLoadBeanClassException.class)
    public void loadClassWithOutJCL() {        
        final ClassPathXmlApplicationContext appCnt1 = new ClassPathXmlApplicationContext(
                "classpath*:applicationContext-jcl-test.xml");

        LOG.info(" ac1 - jb = " + appCnt1.getBean("jb"));
        LOG.info(" ac1 - cj = " + appCnt1.getBean("cj"));
    }

    /**
     * Tests class loading with JCL
     * 
     * @throws MalformedURLException - exception thrown, if any
     * @throws BeansException - exception thrown, if any
     * @throws URISyntaxException  - exception thrown, if any
     */
    @Test
    public void loadClassWithJCL() throws BeansException, MalformedURLException, URISyntaxException {
        final File f = new File(getClass().getClassLoader().getResource("cacis-lib").toURI());
        final ClassPathXmlApplicationContext appCnt = 
            new CaCISClasspathXmlApplicationContext(f.getAbsolutePath(),
                "classpath*:applicationContext-jcl-test.xml");
        LOG.info("appCnt2  " + appCnt.getClass().getClassLoader());

        LOG.info(" ac1 - jb = " + appCnt.getBean("jb"));
        LOG.info(" ac1 - cj = " + appCnt.getBean("cj"));
        LOG.info(" ac1 - cj = " + appCnt.getBean("cj").getClass().getClassLoader());
        LOG.info(" ac1 - jb = " + appCnt.getBean("jb").getClass().getClassLoader());

    }

}
