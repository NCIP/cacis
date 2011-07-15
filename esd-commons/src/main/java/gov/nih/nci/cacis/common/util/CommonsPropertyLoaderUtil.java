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

package gov.nih.nci.cacis.common.util;

import com.google.common.base.Preconditions;
import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * This utility class resolve properties from any of following locations in the same order
 * <pre>
 * #a)${user.home}/.cacis/${project.name}/${propertyFile.name}
 * #b)${catalina.home}/conf/cacis/${project.name}/${propertyFile.name} (where ${catalina.home) is a system property
 * #c)classpath:${propertyFile.name}
 * </pre>
 * property files will be used in order. So if property is available at
 * ${user.home}/.cacis/${project.name}/${propertyFile.name}
 * is available, project will simply ignore that property from other property files.
 *
 * @author Saurabh.Agrawal@semanticbits.com
 * @since Feb 02,2011
 */
public final class CommonsPropertyLoaderUtil {

    private static final Logger LOG = Logger.getLogger(CommonsPropertyLoaderUtil.class);

    private static final String USER_HOME = "user.home";
    private static final String TOMCAT_HOME = "catalina.home";

    /**
     * Utility classes cannot have public constructor
     */
    private CommonsPropertyLoaderUtil() {
        // private constructor
    }

    /**
     * Load properties  from  following locations in mentioned order
     * <pre>
     * #a)${user.home}/.cacis/${ant.project.name}/${ant.project.name}.properties  (where ${user.home) is a system property)
     * #b)${catalina.home}/conf/cacis/${ant.project.name} (where ${catalina.home) is a system property)
     * #c)classpath:${ant.project.name}.properties
     * </pre>
     *
     * @param projectName      - project name
     * @param propertyFileName - property file name
     * @return properties if it can resolve any of the property file, exceptions if no property file is available
     */
    public static Properties loadProperties(final String projectName, final String propertyFileName) {
        Preconditions.checkNotNull(projectName, "Project name must not be null");
        Preconditions.checkNotNull(propertyFileName, "property file name must not be null");

        final String userHomePathToScan = String.format("user.home/.cacis/%s/%s", projectName, propertyFileName);
        final String tomcatHomePathToScan = String.format("catalina.home/conf/cacis/%s/%s",
                projectName, propertyFileName);

        final Properties properties = new Properties();

        //1. first load property from classpath
        fillProperties(new ClassPathResource(propertyFileName), properties);

        //2. now merge properties from tomcat
        fillPropertiesFromFileSystem(properties, TOMCAT_HOME, tomcatHomePathToScan);

        //3. finally merge properties from user.home
        fillPropertiesFromFileSystem(properties, USER_HOME, userHomePathToScan);

        for (Map.Entry<Object,Object> entry : properties.entrySet()) {
            LOG.info(String.format("loaded property - %s:%s", entry.getKey(), entry.getValue()));
        }
        return properties;

    }

    /**
     * fill properties from file system
     *
     * @param properties          - properties to merge
     * @param environmentVariable - environment variable (for ex : user.home, tomcat.home etc)
     * @param pathToScan          - the detailed path to scan
     */
    private static void fillPropertiesFromFileSystem(Properties properties, final String environmentVariable,
                                                     String pathToScan) {
        if (StringUtils.isBlank(System.getProperty(environmentVariable))) {
            LOG.info(String.format("%s is not set. So skipping property lookup from %s",
                    environmentVariable, environmentVariable));
        } else {
            final String detailedEnvironmentPath =
                    StringUtils.replace(pathToScan, environmentVariable, System.getProperty(environmentVariable));
            fillProperties(new FileSystemResource(detailedEnvironmentPath), properties);
        }
    }


    /**
     * fill properties from given resource
     *
     * @param resource   - resource
     * @param properties - properties to fill
     */
    private static void fillProperties(Resource resource, Properties properties) {
        try {
            if (resource.exists()) {
                LOG.info("merging properties form : " + resource.getDescription());
                PropertiesLoaderUtils.fillProperties(properties, resource);
            } else {
                LOG.info(String.format("can not merge property from %s as resource does not exists.",
                        resource.getDescription()));
            }
        } catch (IOException e) {
            final String message = "error while loading properties from resource" + resource;
            LOG.error(message, e);
            throw new ApplicationRuntimeException(message, e);
        }
    }

}

