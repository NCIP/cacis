/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.client;

import gov.nih.nci.cacis.common.doc.DocumentHandler;
import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.service.DocumentAccessManager;
import gov.nih.nci.cacis.xds.authz.service.XdsWriteAuthzManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


/** 
 * Config Impl for XDS client 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 *
 */
@Configuration
public class TestXDSConfigImpl implements XDSConfig  {
    
    /**
     * wrapper for xds doc handler
     * @return DocumentHandler instance
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public DocumentHandler wrapperDocumentHandler() {
        return documentHandler();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Bean
    //@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)    
    public DocumentHandler documentHandler() {
        final DocumentHandler<HashMap<String, String>, HashMap<String, String>> docH = 
            new DocumentHandler<HashMap<String, String>, HashMap<String, String>>() {
                
                private final Map<String, String> contHash = 
                    Collections.synchronizedMap(new HashMap<String, String>());
                
                @Override
                public String handleDocument(HashMap<String, String> documentMetadata) throws ApplicationRuntimeException {
                    final String docId = UUID.randomUUID().toString();
                    contHash.put(docId, documentMetadata.get("content"));
                    return docId;
                }

                @Override
                public void initialize(HashMap<String, String> setupInfo) throws ApplicationRuntimeException {
                    // dummyImpl do not do anything                    
                }

                @Override
                public InputStream retrieveDocument(String docUniqueID) throws ApplicationRuntimeException {
                    final String cont = contHash.get(docUniqueID);
                    if (StringUtils.isEmpty(cont)) {
                        return null;
                    } else {
                        return new ByteArrayInputStream(cont.getBytes());
                    }
                    
                }
            };
        return docH;
    }
    
    /**
     * Dummy XdsWriteAuthzManager until ESD-3073 lands, after that, has to be removed
     * @return XdsWriteAuthzManager
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public XdsWriteAuthzManager dummyXdsWriteAuthzManager() {
        return new XdsWriteAuthzManager() {
            
            @Override
            public void revokeStoreWrite(String arg0) throws AuthzProvisioningException {
                //do nothing - this is dummy impl
            }
            
            @Override
            public void grantStoreWrite(String arg0) throws AuthzProvisioningException {
                //do nothing - this is dummy impl
            }
            
            @Override
            public boolean checkStoreWrite(String arg0) throws AuthzProvisioningException {
                //always give access - this is dummy impl
                return true;
            }
        };
    }
    
    /**
     * Dummy DocumentAccessManager until ESD-3073 lands, after that, has to be removed
     * @return DocumentAccessManager
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public DocumentAccessManager dummyDocumentAccessManager() {
        return new DocumentAccessManager() {
            
            @Override
            public void revokeDocumentAccess(String arg0, String arg1) throws AuthzProvisioningException {
              //do nothing - this is dummy impl                
            }
            
            @Override
            public void grantDocumentAccess(String arg0, String arg1) throws AuthzProvisioningException {
              //do nothing - this is dummy impl
            }
            
            @Override
            public boolean checkDocumentAccess(String arg0, String arg1) throws AuthzProvisioningException {
                // always give access - this is dummy impl
                return true;
            }
        };
    }
}