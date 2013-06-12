/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.user;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;

/**
 * A listener that listens for the Login Failure Event. This listener updates the failed login attempt count which in
 * turn locks out the user.
 * 
 * @author Ajay Nalamala
 */
public class LoginFailureEventListener extends EventListener {

    private static final Logger log = Logger.getLogger(LoginFailureEventListener.class);
   
    CacisUserService cacisUserService;

    @Override
    public boolean canHandle(Object event) {
        return event instanceof AuthenticationFailureBadCredentialsEvent;
    }

    @Override
    public void handle(Object event) {
        AuthenticationFailureBadCredentialsEvent loginFailureEvent = (AuthenticationFailureBadCredentialsEvent) event;
        Object name = loginFailureEvent.getAuthentication().getPrincipal();
        log.debug("Failure Authentication Fired: " + name);
        CacisUser cacisUser = (CacisUser) cacisUserService.loadUserByUsername((String) name);
        if (cacisUser != null) {
            // update the failed login count
            short failedLoginAttempts = cacisUser.getFailedLoginAttempts();
            cacisUser.setFailedLoginAttempts(++failedLoginAttempts);
            // update user
            cacisUserService.updateUser(cacisUser);
        }
    }

    public CacisUserService getCacisUserService() {
        return cacisUserService;
    }

    public void setCacisUserService(CacisUserService cacisUserService) {
        this.cacisUserService = cacisUserService;
    }

}
