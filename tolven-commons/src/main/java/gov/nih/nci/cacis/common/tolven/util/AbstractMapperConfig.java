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
package gov.nih.nci.cacis.common.tolven.util;

import gov.nih.nci.cacis.common.tolven.converters.MismatchedEnumsConverter;
import gov.nih.nci.cacis.common.tolven.converters.StringAndEnumConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Abstract BaseClass to initialize the Dozer Bean Mapper and load the mapping & configuration files.
 * This class is to be extended by the client project, allowing for the addition of extra converters and 
 * mapping files. While mapping files will be picked up automatically from the classpath, CustomConverter can
 * be added to either the globalCustomConverters or the customConvertersWithId collection. As described below
 * global converters much be added to the global dozer configuration.
 * 
 * There is a major drawback in the Dozer configuration options that allow for only a single global Configuration file.
 * @see "http://dozer.sourceforge.net/documentation/apimappings.html"
 * 
 * Currently the tolven-commons base configuration does not require global converters, hence there is no problem.
 * If that should change in the future then you will find the globalMappingConfig.xml in test/resource/mapping in
 * order to be available for the test. By the same token it will NOT be available in the main classpath. That
 * means in order for the registered tolven-commons globalCustomConverters to take effect in the client project
 * you must copy the file into you projects classpath (and extend it as needed).
 * 
 * You may also create a top-level converter using the static final DozerBeanMapper created by the AbstractMapperConfig.
 * 
 * <pre>
 *\@param destObject - destination object
 * \@param sourceObject - source object
 * \@param destinationClass - destination class
 * \@param sourceClass - source class
 * \@return - converted object
 * \@throws MappingException - exception
 *
    public Object convertObject(Object destObject, Object sourceObject, Class<?> destinationClass, Class<?> sourceClass)
            throws MappingException {
        LOG.info("convertObject - Enter");
        if (null != sourceObject) {
            if (null != destObject) {
                mapper.map(sourceObject, destObject);
                return destObject;
            }
            if (null != destinationClass) {
                return mapper.map(sourceObject, destinationClass);
            }
        }
        final StringBuilder sb = new StringBuilder().append("destObject :  ").append(destObject).append("sourceObject :  ")
                .append(sourceObject).append("destinationClass :  ").append(destinationClass).append("sourceClass :  ")
                .append(sourceClass);
        throw new MappingException("Could not convert the input object. Provided values are " + sb.toString());
    }
 * </pre>
 * 
 * @author hniedner
 */
public abstract class AbstractMapperConfig {

    private static final Logger LOG = Logger.getLogger(AbstractMapperConfig.class);

    private DozerBeanMapper mapper;
    private final List<String> mappingFiles = new ArrayList<String>();
    private final List<CustomConverter> globalCustomConverters = new ArrayList<CustomConverter>();
    private final Map<String, CustomConverter> customConvertersWithId = new HashMap<String, CustomConverter>();
    private boolean finalized = false;
    private static final String[] EXTENSIONS = new String[]{ "xml", "jar" };

    /**
     * This method allows for a one-time access to the DozerBeanMapper. The expectation is that the extending
     * subclass will make the mapper available to their project via a static final field.
     * Hence please add all converters to the configuration before you access the mapper!
     * 
     * @return the finalized DozerBeanMapper instance (once only!)
     */
    public DozerBeanMapper getMapper() {
        if (!isFinalized()) {
            configureMapper();
            return mapper;
        }
        throw new MappingException("You must configure the DozerBeanMapper first (call configure())");
    }

    /**
     * @return true if the Mapper has been configured and retrieved.
     */
    protected boolean isFinalized() {
        return finalized;
    }

    /**
     * @param id - identifier for the mapping file declaration
     * @param converter - CustomConverter which will be used as singleton by the Dozer mapper
     */
    protected void addCustomConverterWithId(String id, CustomConverter converter) {
        if (isFinalized()) {
            throw new MappingException("This configuration has already been finalized!");
        }
        customConvertersWithId.put(id, converter);
    }

    /**
     * @param converter - CustomConverter, new instance will be created for each mapping by the Dozer mapper
     */
    protected void addGlobalCustomConverter(CustomConverter converter) {
        if (isFinalized()) {
            throw new MappingException("This configuration has already been finalized!");
        }
        globalCustomConverters.add(converter);
    }

    /**
     * Load the base converters and mapping files.
     */
    private void configureMapper() {
        init(); //load the base converters
        mapper = new DozerBeanMapper();
        // Load the Mapping Files
        mapper.setMappingFiles(mappingFiles);
        LOG.info("Mapping Files Added :\n" + StringUtils.join(mappingFiles, "\n"));
        // Load the Custom converters..
        mapper.setCustomConverters(globalCustomConverters);
        LOG.info("Global Converters  Added :\n" + StringUtils.join(globalCustomConverters, "\n"));
        // Load the custom converters with Id
        mapper.setCustomConvertersWithId(customConvertersWithId);
        LOG.info("Converters with ID Added :\n" + StringUtils.join(globalCustomConverters, "\n"));
        // Finalize config
        finalized = true;
    }

    private void init() {
        customConvertersWithId.put("MismatchedEnumsConverter", new MismatchedEnumsConverter());
        customConvertersWithId.put("StringAndEnumConverter", new StringAndEnumConverter());
        final Map<String, String> fileMap = scanClassPath4MappingFiles();
        mappingFiles.addAll(fileMap.values());
    }

    private Map<String, String> scanClassPath4MappingFiles() {
        final Map<String, String> fileMap = new HashMap<String, String>();
        //load  mappings from WEB-INF/classes/mapping folder
        loadMappingsFromClasspath(fileMap, "/mapping");
        //load  mappings from WEB-INF/lib folder
        loadMappingsFromClasspath(fileMap, "WEB-INF/lib");
        final String classPath = System.getProperty("java.class.path");
        final String[] pathElements = classPath.split(System.getProperty("path.separator"));
        for (final String path : pathElements) {
            if (path.contains(".jar")) {
                scanJarFile(path, fileMap);
            }
            loadResourcesFromDirectory(fileMap, path);
        }
        return fileMap;
    }

    private void loadMappingsFromClasspath(final Map<String, String> fileMap, final String location) {
        try {
            final Enumeration<URL> resources = this.getClass().getClassLoader().getResources(location);
            while (resources.hasMoreElements()) {
                final URL resource = resources.nextElement();
                loadResourcesFromDirectory(fileMap, resource.getFile());
            }

        } catch (IOException e) {
            LOG.error(String.format("error while loading mappings from %s", location));
            throw new MappingException(e);
        }
    }

    private void loadResourcesFromDirectory(final Map<String, String> fileMap, final String path) {
        if (new File(path).isDirectory()) {
            @SuppressWarnings("unchecked")
            // 3rd party library
            final Collection<File> files = FileUtils.listFiles(new File(path), EXTENSIONS, true);
            for (final File f : files) {
                if (f.getAbsolutePath().contains("Mapping.")) {
                    fileMap.put(f.getName(), f.toURI().toString());
                } else if (f.getAbsolutePath().contains("jar")) {
                    scanJarFile(f.getAbsolutePath(), fileMap);
                }
            }
        }
    }

    private void scanJarFile(String fileName, Map<String, String> fileMap) {
        ZipFile zip = null;
        try {
            zip = new ZipFile(fileName);
            for (final Enumeration<?> list = zip.entries(); list.hasMoreElements();) {
                final ZipEntry entry = (ZipEntry) list.nextElement();
                if (entry.getName().contains("Mapping.xml")) {
                    fileMap.put(entry.getName(), "jar:file:" + fileName + "!/" + entry.getName());
                }
            }
        } catch (final IOException e) {
            throw new MappingException(e);
        } finally {
            try {
                if (zip != null) {
                    zip.close();
                }
            } catch (final IOException e) {
                LOG.warn(e);
            }
        }
    }
}
