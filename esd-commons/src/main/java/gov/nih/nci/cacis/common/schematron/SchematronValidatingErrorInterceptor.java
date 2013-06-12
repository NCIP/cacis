/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.schematron;

import gov.nih.nci.cacis.common.service.OutBoundExceptionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.interceptor.DocLiteralInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;

import javax.xml.stream.XMLStreamWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * @author dkokotov
 * @since Aug 13, 2010
 */
public class SchematronValidatingErrorInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final Log LOG = LogFactory.getLog(SchematronValidatingErrorInterceptor.class);

    private final OutBoundExceptionFactory outBoundExceptionFactory;

    /**
     * Constructor.
     *
     * @param outBoundExceptionFactory OutBoundExceptionFactory
     */
    public SchematronValidatingErrorInterceptor(OutBoundExceptionFactory outBoundExceptionFactory) {
        super(Phase.UNMARSHAL);
        addAfter(DocLiteralInInterceptor.class.getName());
        this.outBoundExceptionFactory = outBoundExceptionFactory;
    }

    /**
     * {@inheritDoc}
     */
    public void handleMessage(Message message) throws Fault {
        LOG.debug("Checking for schematron validation error");
        final SchematronException e = message.getExchange().get(SchematronException .class);
        LOG.debug("ReferralException: " + e);
        if (e != null) {
            throw createFault(message, e);
        }
    }

    private Fault createFault(Message message, SchematronException  e) {
        final Service service = message.getExchange().get(Service.class);
        final DataBinding db = service.getDataBinding();
        final Fault f = new Fault(e);
        final XMLStreamWriter xsw = new W3CDOMStreamWriter(f.getOrCreateDetail());
        final DataWriter<XMLStreamWriter> writer = db.createWriter(XMLStreamWriter.class);
        try {
            writer.write(getFaultInfo(outBoundExceptionFactory.createOutBoundException(e)), xsw);
            // CHECKSTYLE:OFF we don't want errors here to derail the message handling
        } catch (RuntimeException e1) {
            // CHECKSTYLE:ON
            LOG.warn("Could not create detail message", e);
            // CHECKSTYLE:OFF we don't want errors here to derail the message handling
        } catch (Exception e1) {
            // CHECKSTYLE:ON
            LOG.warn("Could not create detail message", e);
        }

        return f;
    }

    private Object getFaultInfo(Exception e) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        return e.getClass().getMethod("getFaultInfo").invoke(e);
    }

}
