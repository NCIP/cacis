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
import gov.nih.nci.cacisweb.exception.CaCISWebException;
import gov.nih.nci.cacisweb.util.CaCISUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CacisUserService implements UserDetailsService {

    private static final Logger log = Logger.getLogger(CacisUserService.class);

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        try {
            log.debug("Loading User..");
            String userProperty = CaCISUtil.getProperty(arg0);
            if (userProperty == null) {
                log.error("User Not Found");
                throw new UsernameNotFoundException("User Not Found");
            }
            
            String[] userDetails = StringUtils.splitPreserveAllTokens(userProperty, ',');
            if (userDetails.length > 0 && userDetails.length < 5) {
                log.error("User Not Configured Properly");
                throw new UsernameNotFoundException("User Not Configured Properly");
            }
            Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            int i = 4;
            while (i < userDetails.length) {
                authorities.add(new SimpleGrantedAuthority(userDetails[i]));
                i++;
            }
            SimpleDateFormat sDateFormat = new SimpleDateFormat(CaCISWebConstants.COM_DATE_FORMAT);
            if (!StringUtils.isBlank(userDetails[3])) {
                Calendar lockOutTimeCalendar = Calendar.getInstance();
                lockOutTimeCalendar.setTime(sDateFormat.parse(userDetails[3]));
                long lockoutTimeMillis = lockOutTimeCalendar.getTimeInMillis();
                long currentTimeMillis = Calendar.getInstance().getTimeInMillis();
                long diffMinutes = (currentTimeMillis - lockoutTimeMillis) / (1000 * 60);
                if (diffMinutes > 60) {
                    log.debug("Lockout Time greater than restricted time. Hence unlocking");
                    return new CacisUser(arg0, userDetails[0], true, new Short("0").shortValue(), "", authorities);
                }
            }

            return new CacisUser(arg0, userDetails[0], new Boolean(userDetails[1]).booleanValue(), new Short(
                    userDetails[2]).shortValue(), userDetails[3], authorities);

        } catch (CaCISWebException e) {
            log.error("User Not Found... " + e.getMessage());
            throw new UsernameNotFoundException("User Not Found. " + e.getMessage());
        } catch (ParseException e) {
            log.error("Lockout time not configured properly. " + e.getMessage());
            throw new UsernameNotFoundException("Lockout time not configured properly. " + e.getMessage());
        }
    }

    public void updateUser(CacisUser cacisUser) throws UsernameNotFoundException {
        try {
            log.debug("Updating user...");
            String userProperty = CaCISUtil.getProperty(cacisUser.getUsername());
            if (userProperty == null) {
                log.error("User Not Found");
                throw new UsernameNotFoundException("User Not Found");
            }
            String userPropertyValue = cacisUser.getPassword() + "," + cacisUser.isAccountNonLocked() + ","
                    + cacisUser.getFailedLoginAttempts() + "," + cacisUser.getLockOutTime() + ",";
            for (GrantedAuthority grantedAuthority : cacisUser.getAuthorities()) {
                userPropertyValue += grantedAuthority.getAuthority() + ",";
            }
            userPropertyValue = StringUtils.removeEnd(userPropertyValue, ",");
            log.debug("New User Property Value: " + userPropertyValue);
            CaCISUtil.setProperty(cacisUser.getUsername(), userPropertyValue);
        } catch (CaCISWebException e) {
            log.error("User update failed: " + e.getMessage());
            throw new UsernameNotFoundException("User update failed: " + e.getMessage());
        }
    }

}
