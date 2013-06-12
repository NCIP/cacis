/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chandrav vinodh.rc@semanticbits.com
 * 
 */
public class DomainBeanFactory {

    private final ApplicationContext context;

    /**
     * @param externalBeansConfigClass - Beans config class for the required external domain
     */
    public DomainBeanFactory(Class<?> externalBeansConfigClass) {
        super();
        this.context = new AnnotationConfigApplicationContext(externalBeansConfigClass);
    }


    /**
     * Creates bean for given bean class
     * @param beanClass - the bean class
     * @param <T> - the bean type
     * @return - the bean object.
     * @throws IllegalArgumentException - exception for any bean creation failures
     */
    @SuppressWarnings("unchecked")
    public <T> T createBean(final Class<T> beanClass) throws IllegalArgumentException {
        return (T) createBean(null, null, beanClass.getName());
    }

    /**
     * creates and return instances of object based on spring bean id. If not found, it returns creates the new instance
     * from the class
     * 
     * @param srcObject - Instance of the source object
     * @param srcClass - Instance of the source class
     * @param targetBeanID - key for creating the bean instance
     * 
     * @return returns the bean instance created
     * @throws IllegalArgumentException exception occurred in the creation of the bean
     */
    public Object createBean(Object srcObject, Class<?> srcClass, String targetBeanID) throws IllegalArgumentException {

        if (this.context.containsBean(targetBeanID)) {
            return this.context.getBean(targetBeanID);
        }

        try {
            final Class<?> destClass = Class.forName(targetBeanID);
            return destClass.newInstance();
        } catch (final ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to create class for " + targetBeanID, e);
        } catch (final InstantiationException e) {
            throw new IllegalArgumentException("Unable to instantiate class for " + targetBeanID, e);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException("Illegal Access of class for " + targetBeanID, e);
        }
    }
}
