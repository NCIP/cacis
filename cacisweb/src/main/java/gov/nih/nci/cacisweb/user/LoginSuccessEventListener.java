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

import org.apache.log4j.Logger;
import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public class LoginSuccessEventListener extends EventListener {

    private static final Logger log = Logger.getLogger(LoginSuccessEventListener.class);

    CacisUserService cacisUserService;

    /**
     * A private method that gets the User object from the event if it is an AuthorizedEvent. Otherwise returns null.
     */
    private CacisUser getPrincipal(Object event) {
        if (event instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent authorizedEvent = (AuthenticationSuccessEvent) event;
            Object principal = authorizedEvent.getAuthentication().getPrincipal();
            if (principal instanceof CacisUser) {
                CacisUser cacisUser = (CacisUser) principal;
                cacisUser = (CacisUser) cacisUserService.loadUserByUsername(cacisUser.getUsername());
                return cacisUser;
            }
        }
        return null;
    }

    @Override
    public boolean canHandle(Object event) {
        CacisUser principal = this.getPrincipal(event);
        return (principal != null);
    }

    @Override
    public void handle(Object event) {
        CacisUser cacisUser = this.getPrincipal(event);
        log.debug("Successful Authentication Fired: " + cacisUser.getUsername() +" password: "+cacisUser.getPassword());
        try {
            if (cacisUser.getFailedLoginAttempts() > 0) {
                // reset failed login count to zero
                // on a successful login
                cacisUser.setFailedLoginAttempts((short) 0);
                // update user
                cacisUserService.updateUser(cacisUser);
            }
        } catch (Exception ex) {
            // just log the exception
            log.error("Failure in login success event handling", ex);
        }
    }

    public CacisUserService getCacisUserService() {
        return cacisUserService;
    }

    public void setCacisUserService(CacisUserService cacisUserService) {
        this.cacisUserService = cacisUserService;
    }

}
