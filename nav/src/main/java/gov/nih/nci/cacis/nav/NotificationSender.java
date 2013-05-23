/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import java.util.List;

/**
 * Sends NAV notifications given a list of document IDs.
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 10, 2011
 * 
 */

public interface NotificationSender {
    
    /**
     * Sets the credentials
     * @param userName - user name
     * @param password - password
     */
    void setCredentials(String userName, String password);
    
    /**
     * Defines behavior for sending notifications
     * 
     * @param toEmailAddress recipient
     * @param documentIds list of document IDs
     * @throws NotificationSendException on error
     */
    void send(String toEmailAddress, List<String> documentIds) throws NotificationSendException;

}
