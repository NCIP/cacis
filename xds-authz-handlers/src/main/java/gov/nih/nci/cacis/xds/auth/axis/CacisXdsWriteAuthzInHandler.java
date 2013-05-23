/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.auth.axis;


import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.service.XdsWriteAuthzManager;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.engine.Handler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * Outbound XDS handler
 * @author monish.dombla@semanticbits.com
 * @since Jul 28, 2011 

 */
public class CacisXdsWriteAuthzInHandler extends AbstractCacisXdsHandler implements Handler {
    
    private static final Log LOG = LogFactory.getLog(CacisXdsWriteAuthzInHandler.class);
    private static final String PROVIDE_AND_REGISETR_DOCUMENT_SET_B = "urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b";
    private ApplicationContext applicationContext;

    
    /**
     * 
     * {@inheritDoc} load context
     */
    @Override
    public void init(HandlerDescription handlerdesc) {
        super.init(handlerdesc);
        applicationContext = 
            new ClassPathXmlApplicationContext("classpath*:applicationContext-xds-authz-handlers.xml");
    }

    /**
     * 
     * {@inheritDoc} 
     */
    public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
        
        final XdsWriteAuthzManager xdsWriteAuthzManager = 
            (XdsWriteAuthzManager) applicationContext.getBean("xdsWriteAuthzManager");
        boolean hasAccess = false;
        
        if (PROVIDE_AND_REGISETR_DOCUMENT_SET_B.equals(msgContext.getWSAAction())) {
            LOG.debug("ACTION INVOKED-------- ::: " + msgContext.getWSAAction());
            final String subjectDN = getSubjectDN(msgContext);
            LOG.debug("SubjectDN -------- ::: " + subjectDN);
            try {
                hasAccess = xdsWriteAuthzManager.checkStoreWrite(subjectDN);
            } catch (AuthzProvisioningException e) {
                throw new AxisFault("Error while checking access",e);
                //CHECKSTYLE:OFF
            } catch (Exception e) {
                //CHECKSTYLE:ON
                throw new AxisFault("Error while checking access",e);
            }
            if (!hasAccess) {
                throw new AxisFault("Permission denied");
            }
        }
        return InvocationResponse.CONTINUE;        
    }
    
    /** 
     * @param applicationContext the applicationContext to set
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }    
}
