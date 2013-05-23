/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.systest;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.testutil.common.AbstractBusTestServerBase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.xml.ws.Endpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for creating any CXF server
 *
 * @author Saurabh.agrawal@semanticbits.com
 * @since Oct 20, 2010
 */
public abstract class AbstractBusTestServer extends AbstractBusTestServerBase {


    private final String serviceBeanName;
    private final String address;
    private static ApplicationContext ctx = null;
    private java.lang.Class<?> annotatedClasses;
    private Map<String, Object> endPointProperties;


    /**
     * @param serviceBeanName       - the spring bean name of service
     * @param address               - the service address
     * @param annotatedClasses      - Spring confiugrations
     * @param applySchemaValidation - if schema validation should be enabled on the endpoint
     */
    public AbstractBusTestServer(String serviceBeanName, String address, java.lang.Class<?> annotatedClasses,
                                 boolean applySchemaValidation) {
        this(serviceBeanName, address, annotatedClasses);
        endPointProperties.put("schema-validation-enabled", applySchemaValidation);
    }


    /**
     * @param serviceBeanName - the spring bean name of service
     * @param address - the service address
     */
    /**
     * @param serviceBeanName  - the spring bean name of service
     * @param address          - the service address
     * @param annotatedClasses - Spring confiugrations
     */
    public AbstractBusTestServer(String serviceBeanName, String address, java.lang.Class<?> annotatedClasses) {
        this.serviceBeanName = serviceBeanName;
        this.address = address;
        this.annotatedClasses = annotatedClasses;
        endPointProperties = new HashMap<String, Object>();

    }

    @Override
    protected void run() {
        synchronized (this) {
            if (ctx == null) {
                ctx = new AnnotationConfigApplicationContext(annotatedClasses);
            }
        }

        final Object serviceBean = ctx.getBean(serviceBeanName);

        final Bus bus = new SpringBusFactory().createBus();
        SpringBusFactory.setDefaultBus(bus);

        bus.getOutInterceptors().add(new LoggingOutInterceptor());
        bus.getInInterceptors().add(new LoggingInInterceptor());

        final EndpointImpl ep = (EndpointImpl) Endpoint.create(serviceBean);
        ep.setProperties(endPointProperties);
        ep.publish(address);
    }

    /**
     * Provides access to the server Spring application context
     *
     * @return Application Context
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    /**
     * Sets application context
     * @param ctx - the application context to set
     */
    public static void setCtx(ApplicationContext ctx) {
        AbstractBusTestServer.ctx = ctx;
    }
}

