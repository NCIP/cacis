/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Point Cuts for caEHR aspects.
 * 
 * @author slava.zazansky@semanticbits.com
 */
@Aspect
public class PointCuts {

    /**
     * Scoping designator for caEHR service classes
     */
    @Pointcut("within(gov.nih.nci..services..*)")
    public void withinService() { // NOPMD
    }

    /**
     * Scoping designator for DAO classes
     */
    @Pointcut("within(gov.nih.nci..dao..*)")
    public void withinDao() { // NOPMD
    }

}
