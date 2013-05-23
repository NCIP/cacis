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

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * This class implements the Spring ApplicationListener interface and hence it receives application event notifications.
 * This in turn dispatches the events to listeners that have registered with this object.
 * 
 * @author Ajay Nalamala
 */
@SuppressWarnings("unchecked")
@Component("eventDispatcher")
public class EventDispatcher implements org.springframework.context.ApplicationListener {

    List<EventListener> listeners = new ArrayList<EventListener>();

    /**
     * Method that allows registering of an Event Listener.
     */
    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    /**
     * Spring executes this method with the event object. This method iterates though the list of registered Listeners
     * and checks whether any listener can handle the event. Calls handle method of the Listener if it can handle the
     * event.
     */
    public void onApplicationEvent(ApplicationEvent event) {
        for (EventListener listener : listeners) {
            if (listener.canHandle(event)) {
                listener.handle(event);
            }
        }
    }
}
