/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw;

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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Abstract class to have some setup methods and constants for sesame tests
 *
 * @author vinodh.rc@semanticbits.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-mock-cdw-test.xml")
public abstract class AbstractSesameTest {

    /**
     * ID for the in-memory repo
     */
    public static final String IN_MEMORY_DB_ID = "in-memory-db";

    private static final Log LOG = LogFactory.getLog(AbstractSesameTest.class);

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

    public static String getInMemoryDbId() {
        return IN_MEMORY_DB_ID;
    }

    public static Log getLog() {
        return LOG;
    }

    public String getSesameLocalRepoMngrDir() {
        return sesameLocalRepoMngrDir;
    }

    public String getOutputDirPath() {
        return outputDirPath;
    }

    public boolean isMemoryStorePersist() {
        return memoryStorePersist;
    }

    public LocalRepositoryManager getManager() {
        return manager;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public static String getSmplDataFile1() {
        return SMPL_DATA_FILE_1;
    }

    public static String getSmplRdfFile1() {
        return SMPL_RDF_FILE_1;
    }

    public File getSampleDataFile1() {
        return sampleDataFile1;
    }

    public File getSampleRdfFile1() {
        return sampleRdfFile1;
    }

    public URL getSampleRdfFile1Url() {
        return sampleRdfFile1Url;
    }

    public boolean isOnceInitialized() {
        return onceInitialized;
    }
}
