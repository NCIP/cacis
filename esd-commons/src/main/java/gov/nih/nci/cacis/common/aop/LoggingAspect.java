/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Aspect for logging concerns
 *
 * @author slava.zazansky@semanticbits.com
 */
@Aspect
public class LoggingAspect {

    /**
     * Logging advice, logs entry and exit of designated methods.
     *
     * @param pjp ProceedingJoinPoint
     * @return Object returned object
     * @throws Throwable thrown exception
     */
    @Around("PointCuts.withinService() && execution(public * *(..)) || "
            + "PointCuts.withinDao() && execution(* *..Dao.*(..))")
            // CHECKSTYLE:OFF to allow Throwable
            public Object loggingAdvice(ProceedingJoinPoint pjp) throws Throwable { // CHECKSTYLE:ON

        final Logger log = Logger.getLogger(pjp.getSignature().getDeclaringTypeName());
        if (log.isDebugEnabled()) {
            log.debug(pjp.getSignature().getName() + "() : Enter");
        }

        final Object retVal = pjp.proceed();

        if (log.isDebugEnabled()) {
            log.debug(pjp.getSignature().getName() + "() : Exit");
        }

        return retVal;
    }
}
