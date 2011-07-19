/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The caEHR Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and 5AM Solutions Inc, SemanticBits LLC, and AgileX Technologies, Inc (collectively 'SubContractors').
 * To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United
 * States Code, section 105.
 * 
 * This caEHR Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the caEHR Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly
 * display, publicly perform, and prepare derivative works of the caEHR Software; (ii) distribute and have distributed
 * to and by third parties the caEHR Software and any modifications and derivative works thereof; and (iii) sublicense
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
 * product includes software developed by the National Cancer Institute and SubContractor parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 * 
 * You may not use the names "The National Cancer Institute", "NCI", or any SubContractor party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or any of the subcontracted parties, except as required to comply with
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
package gov.nih.nci.cacis.cdw.sesame;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfig;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.manager.LocalRepositoryManager;
import org.openrdf.repository.sail.config.SailRepositoryConfig;
import org.openrdf.sail.config.SailImplConfig;
import org.openrdf.sail.inferencer.fc.config.ForwardChainingRDFSInferencerConfig;
import org.openrdf.sail.memory.config.MemoryStoreConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import virtuoso.sesame2.driver.VirtuosoRepository;

/**
 * Abstract class to have some setup methods and constants for sesame tests
 * 
 * @author vinodh.rc@semanticbits.com
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-cdw-test.xml")
public abstract class AbstractSesameTest {

    /**
     * ID for the in-memory repo
     */
    public static final String IN_MEMORY_DB_ID = "in-memory-db";
    /**
     * ID for the virtuoso repo
     */
    public static final String VIRTUOSO_ID = "virtuoso-db";

    private static final Log LOG = LogFactory.getLog(AbstractSesameTest.class);

    // VIRTUOSO repository related constants
    @Value("${virtuoso.server}")
    private String virtuosoServer;

    @Value("${virtuoso.port}")
    private String virtuosoPort;

    @Value("${virtuoso.db.username}")
    private String virtuosoDBUserName;

    @Value("${virtuoso.db.password}")
    private String virtuosoDBPassword;

    @Value("${sesame.local.repo.managder.dir}")
    private String sesameLocalRepoMngrDir;

    @Value("${output.dir}")
    private String outputDirPath;

    @Value("${memory.store.persist}")
    private boolean memoryStorePersist;

    private LocalRepositoryManager manager;

    private File outputDir;

    private static final String SMPL_DATA_FILE_1 = "data.nt";
    
    private static final String SMPL_RDF_FILE_1 = "foaf.rdf";

    private File sampleDataFile1;
    
    private File sampleRdfFile1;
    
    private URL sampleRdfFile1Url;

    private boolean onceInitialized = false;

    /**
     * init method
     * 
     * @throws RepositoryException, error thrown if any
     * @throws MalformedURLException, error thrown if any
     */
    @Before
    public void init() throws RepositoryException, MalformedURLException {
        // items to be initialized once
        if (!onceInitialized) {
            final File baseDir = new File(sesameLocalRepoMngrDir);
            manager = new LocalRepositoryManager(baseDir);
            manager.initialize();
            
            sampleRdfFile1 = new File(getClass().getClassLoader().getResource(SMPL_RDF_FILE_1).getFile());
            sampleRdfFile1Url = sampleRdfFile1.toURI().toURL();
            
            sampleDataFile1 = new File(getClass().getClassLoader().getResource(SMPL_DATA_FILE_1).getFile());
            if (StringUtils.isEmpty(outputDirPath)) {
                outputDir = sampleDataFile1.getParentFile();
            }

            onceInitialized = true;
        }
    }

    private void initInMemoryRepo() throws RepositoryException, RepositoryConfigException {
        // check if already initialized
        if (getRepository(IN_MEMORY_DB_ID) != null) {
            return;
        }
        // create a configuration for the SAIL stack
        SailImplConfig backendConfig = new MemoryStoreConfig(memoryStorePersist);

        // stack an inferencer config on top of our backend-config
        backendConfig = new ForwardChainingRDFSInferencerConfig(backendConfig);

        // create a configuration for the repository implementation
        final SailRepositoryConfig repositoryTypeSpec = new SailRepositoryConfig(backendConfig);

        final RepositoryConfig repConfig = new RepositoryConfig(IN_MEMORY_DB_ID, repositoryTypeSpec);

        manager.addRepositoryConfig(repConfig);
    }

    /**
     * TODO : Config RDBMS store to work with virtuso
     * 
     * @throws RepositoryException error thrown if any
     * @throws RepositoryConfigException error thrown if any
     */
    /*
     * private void initVirtuosoRepo() throws RepositoryException, RepositoryConfigException { virtuosoServer =
     * "localhost"; virtuosoPort = "1111"; virtuosoDBUserName = "dba"; virtuosoDBPassword = "dba"; // create a
     * configuration for the SAIL stack RdbmsStoreConfig rdbmsStoreConfig = new RdbmsStoreConfig();
     * rdbmsStoreConfig.setJdbcDriver("virtuoso.jdbc3.Driver"); rdbmsStoreConfig.setUrl("jdbc:virtuoso://" +
     * virtuosoServer + ":" + virtuosoPort); rdbmsStoreConfig.setUser(virtuosoDBUserName);
     * rdbmsStoreConfig.setPassword(virtuosoDBPassword);
     * 
     * //Inferencer is not available for RDBMS repo // create a configuration for the repository implementation final
     * SailRepositoryConfig repositoryTypeSpec = new SailRepositoryConfig(rdbmsStoreConfig);
     * 
     * final RepositoryConfig repConfig = new RepositoryConfig(VIRTUOSO_ID, repositoryTypeSpec);
     * 
     * manager.addRepositoryConfig(repConfig); }
     */

    /**
     * Returns In-Memory repository
     * 
     * @return Repository instance
     * @throws RepositoryConfigException - error thrown, if any
     * @throws RepositoryException - error thrown, if any
     */
    public Repository getInMemoryRepository() throws RepositoryConfigException, RepositoryException {
        initInMemoryRepo();
        return getRepository(IN_MEMORY_DB_ID);
    }

    /**
     * Returns Virtuoso repository, if it is running
     * 
     * @return Repository instance
     * @throws RepositoryConfigException - error thrown, if any
     * @throws RepositoryException - error thrown, if any
     */
    public Repository getVirtuosoRepository() throws RepositoryConfigException, RepositoryException {
        // TODO : use manager once it is properly configured
        // initVirtuosoRepo();

        return new VirtuosoRepository("jdbc:virtuoso://" + virtuosoServer + ":" + virtuosoPort, virtuosoDBUserName,
                virtuosoDBPassword);
    }

    /**
     * Returns the sesame repository based on id, if it is added to the manager
     * 
     * @param repositoryId - String representing the id
     * @return Repository instance
     * @throws RepositoryConfigException - error thrown, if any
     * @throws RepositoryException - error thrown, if any
     */
    public Repository getRepository(String repositoryId) throws RepositoryConfigException, RepositoryException {
        return manager.getRepository(repositoryId);
    }

    /**
     * @return the virtuosoServer
     */
    public String getVirtuosoServer() {
        return virtuosoServer;
    }

    /**
     * @return the virtuosoPort
     */
    public String getVirtuosoPort() {
        return virtuosoPort;
    }

    /**
     * @return the virtuosoDBUserName
     */
    public String getVirtuosoDBUserName() {
        return virtuosoDBUserName;
    }

    /**
     * @return the virtuosoDBPassword
     */
    public String getVirtuosoDBPassword() {
        return virtuosoDBPassword;
    }

    /**
     * @return the sesameLocalRepoMngrDir
     */
    public String getSesameLocalRepoMngrDir() {
        return sesameLocalRepoMngrDir;
    }

    /**
     * @return the smplDataFile1
     */
    public File getSampleDataFile1() {
        return sampleDataFile1;
    }
    
    /**
     * @return the smplRdfFile1
     */
    public File getSampleRdfFile1() {
        return sampleRdfFile1;
    }
    
    /**
     * @return the smplRdfFile1Url
     */
    public URL getSampleRdfFile1Url() {
        return sampleRdfFile1Url;
    }

    /**
     * @return the inMemoryDbId
     */
    public static String getInMemoryDbId() {
        return IN_MEMORY_DB_ID;
    }

    /**
     * @return the log
     */
    public static Log getLog() {
        return LOG;
    }

    /**
     * @return the manager
     */
    public LocalRepositoryManager getManager() {
        return manager;
    }

    /**
     * @return the outputDir
     */
    public File getOutputDir() {
        return outputDir;
    }

    /**
     * @return the smplDataFile1 name
     */    
    public static String getSmplDataFile1Name() {
      return SMPL_DATA_FILE_1;
  }
    
    /**
     * @return the smplRdfFile1 name
     */
    public static String getSmplRdfFile1Name() {
        return SMPL_RDF_FILE_1;
    }

}
