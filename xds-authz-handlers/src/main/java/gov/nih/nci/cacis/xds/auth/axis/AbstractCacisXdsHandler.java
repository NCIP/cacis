/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
/**
 * Outbound XDS handler
 * @author monish.dombla@semanticbits.com
 * @since Jul 28, 2011 
 */

package gov.nih.nci.cacis.xds.auth.axis;

import java.security.Principal;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Outbound XDS handler
 * @author monish.dombla@semanticbits.com
 * @since Jul 28, 2011 
 */

public abstract class AbstractCacisXdsHandler extends AbstractHandler {
    
    private static final Log LOG = LogFactory.getLog(AbstractCacisXdsHandler.class);

    /**
     * 
     * @param msgContext Accepts the axis2 message context
     * @return returns the SubjectDN present in the x509 certificate presented by client.
     */
    protected String getSubjectDN(MessageContext msgContext) {
        
        final HttpServletRequest req = (HttpServletRequest) msgContext.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
        
        final X509Certificate[] certificate = (X509Certificate[])req.getAttribute("javax.servlet.request.X509Certificate");
        if (certificate == null) {
            LOG.debug("javax.servlet.request.X509Certificate NOT AVAILABLE");
        } else {
            final Principal clientDN = certificate[0].getSubjectDN();        // certificate[0] is the end of the chain.
            if (clientDN != null) {
                return clientDN.getName();
            }
        }
        return null;
    }
    
}

