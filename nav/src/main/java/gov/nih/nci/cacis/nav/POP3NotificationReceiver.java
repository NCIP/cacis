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

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Implementation of NotificationReceiver that retrieves notifications using POP3.
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 5, 2011
 * 
 */
public class POP3NotificationReceiver implements NotificationReceiver {

    private static final String POP3 = "pop3";

    /*
     * All required mail.pop3.* properties, e.g. port
     */
    private Properties props;

    /*
     * The POP3 hostname
     */
    private String host;

    /*
     * The email address
     */
    private String mailbox;

    /*
     * login/username for the mailbox
     */
    private String login;

    /*
     * password
     */
    private String password;

    /*
     * The name of the POP3 folder that should be checked
     */
    private String folder;
  
    /**
     * Takes parameters need to configure retrieval from a POP3 service
     * 
     * @param props JavaMail properties
     * @param host the host of the POP3 service
     * @param mailbox the mailbox that will be queried
     * @param login the login for the mailbox
     * @param password the password for the mailbox
     * @param folder the folder that should be queried
     */
    public POP3NotificationReceiver(Properties props, String host, String mailbox, String login, String password,
            String folder) {
        super();
        this.props = props;
        this.host = host;
        this.mailbox = mailbox;
        this.login = login;
        this.password = password;
        this.folder = folder;
    }



    /**
     * Default constructor
     */
    public POP3NotificationReceiver() {
        //empty constructor
    }

    @Override
    public Message[] receive() throws NotificationReceiveException {

        Message[] messages = null;

        try {
            final Session session = Session.getInstance(getProps(), null);
            final Store store = session.getStore(POP3);
            store.connect(getHost(), getLogin(), getPassword());
            final Folder f = store.getFolder(getFolder());
            f.open(Folder.READ_ONLY);
            messages = f.getMessages();
            // CHECKSTYLE:OFF
        } catch (Exception ex) {
            // CHECKSTYLE:ON
            throw new NotificationReceiveException("Error receiving notifications: " + ex.getMessage(), ex);
        }

        return messages;

    }

    /**
     * @return the props
     */

    public Properties getProps() {

        return props;
    }

    /**
     * @param props the props to set
     */

    public void setProps(Properties props) {

        this.props = props;
    }

    /**
     * @return the mailbox
     */

    public String getMailbox() {

        return mailbox;
    }

    /**
     * @param mailbox the mailbox to set
     */

    public void setMailbox(String mailbox) {

        this.mailbox = mailbox;
    }

    /**
     * @return the login
     */

    public String getLogin() {

        return login;
    }

    /**
     * @param login the login to set
     */

    public void setLogin(String login) {

        this.login = login;
    }

    /**
     * @return the password
     */

    public String getPassword() {

        return password;
    }

    /**
     * @param password the password to set
     */

    public void setPassword(String password) {

        this.password = password;
    }

    /**
     * @return the folder
     */

    public String getFolder() {

        return folder;
    }

    /**
     * @param folder the folder to set
     */

    public void setFolder(String folder) {

        this.folder = folder;
    }

    /**
     * @return the host
     */

    public String getHost() {

        return host;
    }

    /**
     * @param host the host to set
     */

    public void setHost(String host) {

        this.host = host;
    }

}
