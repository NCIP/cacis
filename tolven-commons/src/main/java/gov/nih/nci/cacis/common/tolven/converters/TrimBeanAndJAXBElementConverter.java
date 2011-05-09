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
package gov.nih.nci.cacis.common.tolven.converters;

import gov.nih.nci.cacis.common.tolven.util.ConverterUtil;
import gov.nih.nci.cacis.common.tolven.util.Namespace;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.ConfigurableCustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.dozer.MappingException;

import javax.xml.bind.JAXBElement;

/**
 * 
 * This converter will convert from Trim objects (org.tolven.trim.*) to JAXBElement wrapped elements.
 * The parameter to this converter accepts a ',' delimited string, thats composed of the fully qualified name 
 * of the class and the attribute name ..
 *  
 *  e.g to create JAXBElement<REPCMT999003USAssignedEntity> assignedEntity of REPCMT999003USAuthor , the 
 *  param string will be 'gov.nih.nci.cacis.common.hl7.v3.REPCMT999003USAuthor,assignedEntity'
 *  
 * 
 * @author hniedner
 * 
 */

public class TrimBeanAndJAXBElementConverter implements ConfigurableCustomConverter, MapperAware {

    
    private static final String STRING_DELIMETER = ",";
    private static final Logger LOG = Logger.getLogger(TrimBeanAndJAXBElementConverter.class);

    private Mapper mapper;
    private String jaxbFieldName;
    private Class<?> classRef;
    private Class<?> parentClassRef;
    
    /**
     * No arg. Constructor. DozerMapper gets injected via Dozer.
     */
    public TrimBeanAndJAXBElementConverter() {
        LOG.info("Instantiated converter.");
    }

    /**
     * {@inheritDoc}
     */
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    public void setParameter(String params) {
        if (StringUtils.isEmpty(params)) {
            throw new MappingException("parameters cannot be empty!");
        }
        final String[] paramArr = params.split(STRING_DELIMETER);
        if (paramArr.length < 2) {
            throw new MappingException("parameters must be of format, 'declaredType,fieldName'");
        }
        LOG.info("Parsed Parameters: RMIM parent class: " + paramArr[0] + " field name: " + paramArr[1]);
        parentClassRef = ConverterUtil.classForName(paramArr[0].trim(), Namespace.RMIM);
        jaxbFieldName = paramArr[1].trim();
        classRef = ConverterUtil.resolveTargetClass(parentClassRef, jaxbFieldName);
    }

    /**
     * {@inheritDoc}
     */
    public Object convert(Object dest, Object source, Class<?> destClass, Class<?> sourceClass) {
        if (source == null) {
            LOG.info("source == null returning: " + dest);
            return dest;
        }
        LOG.info("source: " + sourceClass + " : " + source);
        LOG.info("destination " + destClass + " : " + dest);
        checkConverterObjects(dest, source, sourceClass, destClass);

        if (source instanceof JAXBElement) {
            // Convert from JAXBElement<RMIMClass> to TRIM object
            final Object target = convertToTrimObject((JAXBElement<?>) source, dest, destClass);
            LOG.info("JAXBElement<RMIMClass> to TRIM conversion returning: " + target);
            return target;
        } else {
            // Convert from TRIM to JAXBElement<RMIMClass> object
            final Object target = convertToJAXBObject(source);
            LOG.info("JAXBElement<RMIMClass> to TRIM conversion returning: " + target);
            return target;
        }
    }

    private Object convertToTrimObject(JAXBElement<?> source, Object dest, Class<?> destClass) {
        final Object value = source.getValue();
        // Check null value element
        if (value == null) {
            LOG.info("JAXBElement.value == null returning: " + dest);
            return dest;
        }
        LOG.info("JAXBElement.value not null converting: " + value);
        Object target = dest;
        if (dest == null) {
            target =  mapper.map(value, destClass);
        } else {
            mapper.map(value, target);
        }
        LOG.info("JAXBElement.value converted to: " + target);
        return target;
    }

    private Object convertToJAXBObject(Object source) {
        final Object value = mapper.map(source, classRef);
        LOG.info("Trim source converted to JAXBElement.value: " + value);
        if (value != null && !classRef.isAssignableFrom(value.getClass())) {
            throw new MappingException("Invalid object returned from mapper. Type " + value.getClass().getName()
                    + " does not match expected " + classRef);
        }
        return ConverterUtil.createJAXBElement(value, jaxbFieldName, parentClassRef);
    }

    private void checkConverterObjects(Object dest, Object source, Class<?> sourceClass,Class<?> destClass) {
        if (JAXBElement.class.isAssignableFrom(sourceClass) || JAXBElement.class.isAssignableFrom(destClass)) {
            LOG.info(" Mapping Objects are valid: Source - " + sourceClass + " Destn - " + destClass);
        } else {
            throw new MappingException("Source and/or destination class is/are not of the correct type for mapping ");
        }
        if (!source.getClass().isAssignableFrom(sourceClass)) {
            throw new MappingException("Source object is not of type designated by the source class");
        }
        if (dest != null && ! dest.getClass().isAssignableFrom(destClass)) {
            throw new MappingException("Target object is not of type designated by the target class");
        }
    }
}
