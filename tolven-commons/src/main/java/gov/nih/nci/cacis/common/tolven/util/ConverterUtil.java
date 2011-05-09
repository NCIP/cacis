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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.dozer.MappingException;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author hniedner
 * @since Feb 9, 2011
 *
 */
public final class ConverterUtil {
    
    private static final Logger LOG = Logger.getLogger(ConverterUtil.class);

    /**
     *  private constructor
     */
    private ConverterUtil() {
        // private constructor
    }
    
    /**
     * Qualify unqualified TRIM and RMIM class names. There will be no change to the className if
     * there is any package declaration already present (detected by occurrence of a dot)
     * 
     * @param className - class name
     * @param type - RMIM or TRIM class namespace
     * @return qualified class name
     */
    public static String qualifyClassName(String className, Namespace type) {
        if (type == null) {
            return className;
        }
        if (className.contains(".")) {
            return className;
        }
        return type.getNs() + "." + className;
    }

    /**
     * Wrap Class.forName(className).
     * 
     * @param className - class name
     * @return Class
     */
    public static Class<?> classForName(String className) {
        try {
            return Class.forName(className);
        } catch (final ClassNotFoundException e) {
            throw new MappingException("Specified declared class " + className + " cannot be found!", e);
        }
    }

    /**
     * Wrap Class.forName(className) and allow for submission of simple class names
     * which will be qualified to the submitted type.
     * 
     * @param className - class name
     * @param type - RMIM or TRIM class namespace
     * @return Class
     */
    public static Class<?> classForName(String className, Namespace type) {
        try {
            return Class.forName(qualifyClassName(className, type));
        } catch (final ClassNotFoundException e) {
            throw new MappingException("Specified declared class " + className + " cannot be found!", e);
        }
    }

    /**
     * Reflection to get the name value
     * @param bean - instance of the object the field value is extracted from.
     * @param fieldName - name of the field (of type String!)
     * @return String
     */
    public static String getStringFieldValue(Object bean, String fieldName) {
        try {
            return BeanUtils.getSimpleProperty(bean, fieldName);
        } catch (final IllegalAccessException e) {
            throw new MappingException(e);
        } catch (final NoSuchMethodException e) {
            throw new MappingException(e);
        } catch (final InvocationTargetException e) {
            throw new MappingException(e);
        }
    }

    /**
     * Reflection to see whether the submitted object has a NOT-Null NullFlavor field.
     * If the object has no nullFlavor field then the method returns false.
     * @param bean - instance of the object the field value is extracted from.
     * @return String
     */
    public static boolean isNullFlavored(Object bean) {
        String value = null;
        try {
            value = BeanUtils.getProperty(bean, "nullFlavor");
        } catch (final IllegalAccessException e) {
            LOG.debug(e);
            return false;
        } catch (final NoSuchMethodException e) {
            LOG.debug(e);
            return false;
        } catch (final InvocationTargetException e) {
            LOG.debug(e);
            return false;
        }
        return value != null;
    }

    /**
     * Reflection to get the name value
     * 
     * @param bean - instance of the object the field value is extracted from.
     * @param fieldName - name of the field (of type String!)
     * @param value - string value
     */
    public static void setStringFieldValue(Object bean, String fieldName, String value) {
        try {
            BeanUtils.setProperty(bean, fieldName, value);
        } catch (final IllegalAccessException e) {
            throw new MappingException(e);
        } catch (final InvocationTargetException e) {
            throw new MappingException(e);
        }
    }

    /**
     * @param <R> - wrapped RMIM type
     * @param value - JAXBElement value
     * @param fieldName - name of the JAXBElement field
     * @param parentClass - RMIM class harboring the JAXBElement field
     * @return JAXBElement instance
     */
    @SuppressWarnings("unchecked")
    public static <R> JAXBElement<R> createJAXBElement(R value, String fieldName, Class<?> parentClass) {
        if (value == null) {
            return null;
        }
        final JAXBElement<R> element = new JAXBElement<R>(new QName(Namespace.RMIM.getUrn(), fieldName), 
                (Class<R>) value.getClass(), parentClass, value);
        element.setNil(isNullFlavored(value));
        return element;
    }

    /**
     * Method creates a JAXBElement with a null value;
     * @param <R> - wrapped RMIM type
     * @param valueClass - wrapped RMIM class
     * @param fieldName - name of the JAXBElement field
     * @param parentClass - RMIM class harboring the JAXBElement field
     * @return JAXBElement instance
     */
    public static <R> JAXBElement<R> createNullValueJAXBElement(
            Class<R> valueClass, String fieldName, Class<?> parentClass) {
        final JAXBElement<R> element = new JAXBElement<R>(
                new QName(Namespace.RMIM.getUrn(), fieldName), valueClass, parentClass, null);
        element.setNil(true);
        return element;
    }
    
    /**
     * @param parentClass - parent class
     * @param fieldName - field name
     * @return field type - can be cast to Class<?>
     */
    public static Type getFieldType(Class<?> parentClass, String fieldName) {
        try {
            final Field field = parentClass.getDeclaredField(fieldName);
            return field.getGenericType();
        } catch (final SecurityException e) {
            throw new MappingException("Security exception!", e);
        } catch (final NoSuchFieldException e) {
            throw new MappingException(fieldName + " not found!", e);
        }
    }
    /**
     * @param parentClass - parent class
     * @param fieldName - field name
     * @return targetClass
     */
    public static Class<?> resolveTargetClass(Class<?> parentClass, String fieldName) {
        final Type fieldType = getFieldType(parentClass, fieldName);
        return resolveTargetClass(fieldType);
    }
    
    /**
     * @param fieldType - type of a java class field
     * @return targetClass
     */
    public static Class<?> resolveTargetClass(Type fieldType) {
        if (fieldType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) fieldType;
            LOG.info("reflection : " + type);
            if (List.class.isAssignableFrom((Class<?>) type.getRawType())) {
                LOG.info("target class is a List");
                type = (ParameterizedType) type.getActualTypeArguments()[0];
            }
            return (Class<?>) type.getActualTypeArguments()[0];
        } else {
            return (Class<?>) fieldType;
        }
    }

    /**
     * @param fieldType - field type
     * @return whether target type is a List
     */
    public static boolean isFieldTypeList(Type fieldType) {
        return fieldType instanceof ParameterizedType 
                && List.class.isAssignableFrom((Class<?>) ((ParameterizedType) fieldType).getRawType());
    }

    /**
     * @param fieldType - field type
     * @return whether target type is a List
     */
    public static boolean isFieldTypeJAXBElement(Type fieldType) {
        if (fieldType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) fieldType;
            if (List.class.isAssignableFrom((Class<?>) type.getRawType())) {
                type = (ParameterizedType) type.getActualTypeArguments()[0];
            }
            return JAXBElement.class.isAssignableFrom((Class<?>) type.getRawType());
        }
        return false;
    }
}
