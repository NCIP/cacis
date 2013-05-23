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

import gov.nih.nci.cacisweb.CaCISWebConstants;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author Ajay Nalamala
 * @since Mar 12, 2013
 * 
 */
public class CacisUser extends User {

    private static final long serialVersionUID = -2362722829192571907L;

    public static final short MAX_FAILED_LOGIN_ATTEMPTS = 5;

    /**
     * An attribute to track the number of failed login attempts
     */
    private short failedLoginAttempts;

    private String lockOutTime;

    /**
     * @param username
     * @param password
     * @param authorities
     */
    public CacisUser(String username, String password, boolean accountNonLocked, short failedLoginAttempts,
            String lockOutTime, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, accountNonLocked, authorities);
        this.failedLoginAttempts = failedLoginAttempts;
        this.lockOutTime = lockOutTime;
    }

    /**
     * @param username
     * @param password
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     */
    public CacisUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isAccountNonLocked() {
        if (this.getFailedLoginAttempts() >= MAX_FAILED_LOGIN_ATTEMPTS) {
            return false;
        }
        return true;
    }

    public short getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(short failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public String getLockOutTime() {
        if (!isAccountNonLocked() && StringUtils.isBlank(lockOutTime)) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat(CaCISWebConstants.COM_DATE_FORMAT);
            return sDateFormat.format(new Date());
        }
        return lockOutTime;
    }

    public void setLockOutTime(String lockOutTime) {
        this.lockOutTime = lockOutTime;
    }

}
