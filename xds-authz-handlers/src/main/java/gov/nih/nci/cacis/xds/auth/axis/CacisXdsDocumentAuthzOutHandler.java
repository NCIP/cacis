/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.auth.axis;


import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.service.DocumentAccessManager;

import java.util.Iterator;

import org.apache.axiom.om.OMElement;
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
public class CacisXdsDocumentAuthzOutHandler extends AbstractCacisXdsHandler implements Handler {
    
    private static final Log LOG = LogFactory.getLog(CacisXdsDocumentAuthzOutHandler.class);
    private static final String RETRIEVE_DOCUMENT_SET_B = "urn:ihe:iti:2007:RetrieveDocumentSet";
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
        final DocumentAccessManager docAccessMgr = 
            (DocumentAccessManager) applicationContext.getBean("documentAccessManager");
        boolean allowAccess = false;
        
        if (RETRIEVE_DOCUMENT_SET_B.equals(msgContext.getWSAAction())) {
           LOG.debug("Action-------- ::: " + msgContext.getWSAAction());
           final String documentUniqueID = getDocumentUniqueId(msgContext);
           final String subjectDn = getSubjectDN(msgContext);
           LOG.debug("DocumentUinqueID -------- ::: " + documentUniqueID);
           LOG.debug("SubjectDN -------- ::: " + subjectDn);
           try {
               allowAccess = docAccessMgr.checkDocumentAccess(documentUniqueID, subjectDn);
           } catch (AuthzProvisioningException e) {
               throw new AxisFault("Error while checking access",e); 
           }
           
           if (!allowAccess) {
               throw new AxisFault("Permission denied");
           }
        }
        return InvocationResponse.CONTINUE;        
    }
    

    /**
     * This method parses the SOAP Request and returns the DocumentUniqueID
     * @param msgContext Axis2 message context
     * @return String DocumentUniqueId
     */
    private String getDocumentUniqueId(MessageContext msgContext) {
        for (Iterator it1 = msgContext.getEnvelope().getBody().getChildElements(); it1.hasNext();) { //NOPMD
            final OMElement obj1 = (OMElement) it1.next();
            LOG.debug("NAME -------- " + obj1.getLocalName());
            for (Iterator it2 = obj1.getChildElements(); it2.hasNext();) { //NOPMD
                final OMElement obj2 = (OMElement) it2.next();
                LOG.debug("NAME -------- " + obj2.getLocalName());
                for (Iterator it3 = obj2.getChildElements(); it3.hasNext();) { //NOPMD
                    final OMElement obj3 = (OMElement) it3.next();
                    LOG.debug("NAME -------- " + obj3.getLocalName());
                    LOG.debug("TEXT -------- " + obj3.getText());
                    if (obj3.getLocalName().equals("DocumentUniqueId")) {
                        return obj3.getText();
                    }
                }
            }
         }
        return null;
    }
    
    /** 
     * @param applicationContext the applicationContext to set
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }    
}
