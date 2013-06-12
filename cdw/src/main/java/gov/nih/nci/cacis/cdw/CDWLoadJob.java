/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CDWLoadJob {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cdwContext = new ClassPathXmlApplicationContext(
                "classpath:applicationContext-cacis-cdw.xml");
        CDWPendingLoader pendingLoader = (CDWPendingLoader) cdwContext.getBean("pendingLoader");
        CDWLoader loader = (CDWLoader) cdwContext.getBean("loader");
        pendingLoader.loadPendingCDWDocuments(loader);
    }

}
