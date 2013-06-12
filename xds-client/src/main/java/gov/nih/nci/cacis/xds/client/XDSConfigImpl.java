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
import gov.nih.nci.cacis.xds.StaticMetadataSupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;


/** 
 * Config Impl for XDS client 
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 *
 */
@Configuration
//TODO: After ESD-3073 lands, this line should be uncommented
//@ImportResource("classpath*:applicationContext-xds-authz-handlers.xml")
public class XDSConfigImpl implements XDSConfig  {
    
    @Value("${xds.repository.url}")
    private String repositoryURL;
    
    @Value("${xds.registry.url}")
    private String registryURL;
    
    @Value("${xds.repo.oid}")
    private String repoOID;
    
    @Value("${axis2.xml.location}")
    private String axis2XmlLoc;
    
    @Value("${xds.keystore.path}")
    private String xdsKeystorePath;
    
    @Value("${xds.keystore.password}")
    private String xdsKeystorePassword;
    
    @Value("${xds.truststore.path}")
    private String xdsTruststorePath;
    
    @Value("${xds.truststore.password}")
    private String xdsTruststorePassword;
    
    /**
     * Bean for populating the XDSHandler info for initializing the document handler
     * @return XDSHandlerInfo
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public XDSHandlerInfo xdsHandlerInfo() {        
        
        final XDSHandlerInfo hndlrInfo = new XDSHandlerInfo();
        hndlrInfo.setRegistryURL(registryURL);
        hndlrInfo.setRepositoryURL(repositoryURL);
        hndlrInfo.setSourceAuditorEnable(false);
        hndlrInfo.setConsumerAuditorEnable(false);
        hndlrInfo.setRepoOIDRoot(repoOID);
        
        hndlrInfo.setAxis2XmlLocation(axis2XmlLoc);
        hndlrInfo.setXdsKeystorePath(xdsKeystorePath);
        hndlrInfo.setXdsKestorePassword(xdsKeystorePassword);
        hndlrInfo.setXdsTruststorePath(xdsTruststorePath);
        hndlrInfo.setXdsTruststorePassword(xdsTruststorePassword);
        
        return hndlrInfo;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public DocumentHandler documentHandler() {
        final DocumentHandler<XDSHandlerInfo, XDSDocumentMetadata> docH = 
            new XDSDocumentHandler();
        docH.initialize(xdsHandlerInfo());
        return docH;
    }
    
    /**
     * wrapper for xds doc handler
     * @return DocumentHandler instance of Wrapper xds doc handler
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public DocumentHandler wrapperDocumentHandler() {
        final DocumentHandler<HashMap<String, String>, HashMap<String, String>> docH = 
            new WrapperXDSDocumentHandler(documentHandler());
        return docH;
    }
    


    /**
     *  MetadataSuppliedDocumentHandler bean
     * @return MetadataSuppliedDocumentHandler
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public MetadataSuppliedDocumentHandler metadataSuppliedDocumentHandler() {
        return new MetadataSuppliedDocumentHandler(new StaticMetadataSupplier(), documentHandler());
    }
    
}