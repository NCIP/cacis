/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * An abstract class that is the parent for event listeners in this application. Any event listener should extend this
 * class.
 * 
 * @author Ajay Nalamala
 */
public abstract class EventListener implements InitializingBean {

    Log log = LogFactory.getLog(this.getClass());

    EventDispatcher eventDispatcher;

    // Spring will call this method after auto-
    // wiring is complete.
    public void afterPropertiesSet() throws Exception {
        // let us register this instance with
        // event dispatcher
        eventDispatcher.registerListener(this);
    }

    /**
     * Implementation of this method checks whether the given event can be handled in this class. This method will be
     * called by the event dispatcher.
     * 
     * @param event the event to handle
     * @return true if the implementing subclass can handle the event
     */
    public abstract boolean canHandle(Object event);

    /**
     * This method is executed by the event dispatcher with the event object.
     * 
     * @param event the event to handle
     */
    public abstract void handle(Object event);

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public void setEventDispatcher(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

}
