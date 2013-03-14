package gov.nih.nci.cacisweb.action;

import gov.nih.nci.cacisweb.model.LoginModel;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

    private LoginModel loginBean;

     private static final Logger log = Logger.getLogger(LoginAction.class);

    private static final long serialVersionUID = 1L;

    public String loginFailed() {
        log.debug("loginFailed() - START");
        log.error(getText("loginBean.loginFailed"));
        addActionError(getText("loginBean.loginFailed"));
        log.debug("loginFailed() - END");
        return SUCCESS;
    }
    
    public String loginFailedLockout() {
        log.debug("loginFailedLockout() - START");
        log.error(getText("loginBean.loginFailedLockout"));
        addActionError(getText("loginBean.loginFailedLockout"));
        log.debug("loginFailedLockout() - END");
        return SUCCESS;
    }
    
    public String logoutSuccessful() {
        log.debug("logoutSuccessful() - START");
        addActionMessage(getText("loginBean.logoutSuccessful"));
        log.debug("logoutSuccessful() - END");
        return SUCCESS;
    }
    
    public String sessionInvalid() {
        log.debug("sessionInvalid() - START");
        log.error(getText("loginBean.sessionInvalid"));
        addActionError(getText("loginBean.sessionInvalid"));
        log.debug("sessionInvalid() - END");
        return SUCCESS;
    }

}
