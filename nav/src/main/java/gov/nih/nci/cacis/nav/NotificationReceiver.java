/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import javax.mail.Message;

/**
 * Defines the behavior of the NAV Notification Receiver
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 5, 2011
 * 
 */
public interface NotificationReceiver {

    /**
     * Returns a list of Messages from the mail server.
     * 
     * @return list of Messages
     * @throws NotificationReceiveException on error
     */
    Message[] receive() throws NotificationReceiveException;

}
