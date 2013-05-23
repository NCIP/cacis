/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.client;

/**
 * Setup info object for XDS DocumentHandler
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 *
 */
public class XDSHandlerInfo {
    
    private String repositoryURL;
    
    private String registryURL;
    
    private String repoOIDRoot;
    
    private boolean sourceAuditorEnable = false;
    
    private boolean consumerAuditorEnable = false;
    
    private String axis2XmlLocation;
    
    private String xdsKeystorePath;
    
    private String xdsKestorePassword;
    
    private String xdsTruststorePath;
    
    private String xdsTruststorePassword;

    
    /**
     * @return the repositoryURL
     */
    public String getRepositoryURL() {
        return repositoryURL;
    }

    
    /**
     * @param repositoryURL the repositoryURL to set
     */
    public void setRepositoryURL(String repositoryURL) {
        this.repositoryURL = repositoryURL;
    }

    
    /**
     * @return the registryURL
     */
    public String getRegistryURL() {
        return registryURL;
    }

    
    /**
     * @param registryURL the registryURL to set
     */
    public void setRegistryURL(String registryURL) {
        this.registryURL = registryURL;
    }

    
    /**
     * @return the repoOIDRoot
     */
    public String getRepoOIDRoot() {
        return repoOIDRoot;
    }

    
    /**
     * @param repoOIDRoot the repoOIDRoot to set
     */
    public void setRepoOIDRoot(String repoOIDRoot) {
        this.repoOIDRoot = repoOIDRoot;
    }

    
    /**
     * @return the sourceAuditorEnable
     */
    public boolean isSourceAuditorEnable() {
        return sourceAuditorEnable;
    }

    
    /**
     * @param sourceAuditorEnable the sourceAuditorEnable to set
     */
    public void setSourceAuditorEnable(boolean sourceAuditorEnable) {
        this.sourceAuditorEnable = sourceAuditorEnable;
    }

    
    /**
     * @return the consumerAuditorEnable
     */
    public boolean isConsumerAuditorEnable() {
        return consumerAuditorEnable;
    }

    
    /**
     * @param consumerAuditorEnable the consumerAuditorEnable to set
     */
    public void setConsumerAuditorEnable(boolean consumerAuditorEnable) {
        this.consumerAuditorEnable = consumerAuditorEnable;
    }


    
    /**
     * @return the axis2XmlLocation
     */
    public String getAxis2XmlLocation() {
        return axis2XmlLocation;
    }


    
    /**
     * @param axis2XmlLocation the axis2XmlLocation to set
     */
    public void setAxis2XmlLocation(String axis2XmlLocation) {
        this.axis2XmlLocation = axis2XmlLocation;
    }


    
    /**
     * @return the xdsKeystorePath
     */
    public String getXdsKeystorePath() {
        return xdsKeystorePath;
    }


    
    /**
     * @param xdsKeystorePath the xdsKeystorePath to set
     */
    public void setXdsKeystorePath(String xdsKeystorePath) {
        this.xdsKeystorePath = xdsKeystorePath;
    }


    
    /**
     * @return the xdsKestorePassword
     */
    public String getXdsKestorePassword() {
        return xdsKestorePassword;
    }


    
    /**
     * @param xdsKestorePassword the xdsKestorePassword to set
     */
    public void setXdsKestorePassword(String xdsKestorePassword) {
        this.xdsKestorePassword = xdsKestorePassword;
    }


    
    /**
     * @return the xdsTruststorePath
     */
    public String getXdsTruststorePath() {
        return xdsTruststorePath;
    }


    
    /**
     * @param xdsTruststorePath the xdsTruststorePath to set
     */
    public void setXdsTruststorePath(String xdsTruststorePath) {
        this.xdsTruststorePath = xdsTruststorePath;
    }


    
    /**
     * @return the xdsTruststorePassword
     */
    public String getXdsTruststorePassword() {
        return xdsTruststorePassword;
    }


    
    /**
     * @param xdsTruststorePassword the xdsTruststorePassword to set
     */
    public void setXdsTruststorePassword(String xdsTruststorePassword) {
        this.xdsTruststorePassword = xdsTruststorePassword;
    }
    
    
}
