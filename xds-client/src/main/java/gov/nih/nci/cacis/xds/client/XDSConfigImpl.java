/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software was developed in conjunction with the
 * National Cancer Institute (NCI) by NCI employees and subcontracted parties. To the extent government employees are
 * authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 *
 * This gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software License (the License) is between NCI and You. You (or Your) shall
 * mean a person or an entity, and all other entities that control, are controlled by, or are under common control with
 * the entity. Control for purposes of this definition means (i) the direct or indirect power to cause the direction or
 * management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the
 * outstanding shares, or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software to (i) use, install, access, operate, execute, copy,
 * modify, translate, market, publicly display, publicly perform, and prepare derivative works of the
 * gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software; (ii) distribute and have distributed to and by third parties the
 * gov.nih.nci.cacis.cdw-1.0-SNAPSHOT Software and any modifications and derivative works thereof; and (iii) sublicense
 * the foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
 * third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting or right of
 * payment from You or Your sub-licensees for the rights granted under this License. This License is granted at no
 * charge to You.
 *
 * Your redistributions of the source code for the Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code
 * form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: This
 * product includes software developed by the National Cancer Institute and subcontracted parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or any subcontracted party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or theany of the subcontracted parties, except as required to comply with
 * the terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs
 * and into any third party proprietary programs. However, if You incorporate the Software into third party proprietary
 * programs, You agree that You are solely responsible for obtaining any permission from such third parties required to
 * incorporate the Software into such third party proprietary programs and for informing Your sub-licensees, including
 * without limitation Your end-users, of their obligation to secure any required permissions from such third parties
 * before incorporating the Software into such third party proprietary software programs. In the event that You fail to
 * obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to the
 * extent prohibited by law, resulting from Your failure to obtain such permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and
 * to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses
 * of modifications of the Software, or any derivative works of the Software as a whole, provided Your use,
 * reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, ANY OF ITS SUBCONTRACTED PARTIES OR THEIR AFFILIATES BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.cacis.xds.client;

import gov.nih.nci.cacis.common.doc.DocumentHandler;
import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.service.DocumentAccessManager;
import gov.nih.nci.cacis.xds.authz.service.XdsWriteAuthzManager;

import org.springframework.beans.factory.annotation.Value;
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