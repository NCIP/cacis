package gov.nih.nci.cacisweb.user;

import gov.nih.nci.cacisweb.exception.CaCISWebException;
import gov.nih.nci.cacisweb.util.CaCISUtil;

import java.util.ArrayList;
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
            String userProperty = CaCISUtil.getProperty(arg0);
            if (userProperty == null) {
                throw new UsernameNotFoundException("User Not Found..");
            }
            String[] userDetails = StringUtils.split(userProperty, ',');
            if (userDetails.length > 0 && userDetails.length < 4) {
                throw new UsernameNotFoundException("User Not Configured Properly");
            }
            Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            int i = 3;
            while (i < userDetails.length) {
                authorities.add(new SimpleGrantedAuthority(userDetails[i]));
                i++;
            }

            CacisUser cacisUser = new CacisUser(arg0, userDetails[0], new Boolean(userDetails[1]).booleanValue(),
                    new Short(userDetails[2]).shortValue(), authorities);
            return cacisUser;
        } catch (CaCISWebException e) {
            throw new UsernameNotFoundException("User Not Found... " + e.getMessage());
        }
    }

    public void updateUser(CacisUser cacisUser) throws UsernameNotFoundException {
        try {
            String userProperty = CaCISUtil.getProperty(cacisUser.getUsername());
            if (userProperty == null) {
                throw new UsernameNotFoundException("User Not Found....");
            }
            String userPropertyValue = cacisUser.getPassword() + "," + cacisUser.isAccountNonLocked() + ","
                    + cacisUser.getFailedLoginAttempts() + ",";
            for(GrantedAuthority grantedAuthority:cacisUser.getAuthorities()){
                userPropertyValue += grantedAuthority.getAuthority() +",";
            }
            userPropertyValue = StringUtils.removeEnd(userPropertyValue, ",");
            log.debug("New User Property Value: "+userPropertyValue);
            CaCISUtil.setProperty(cacisUser.getUsername(), userPropertyValue);
        } catch (CaCISWebException e) {
            throw new UsernameNotFoundException("User update failed: " + e.getMessage());
        }
    }

}
