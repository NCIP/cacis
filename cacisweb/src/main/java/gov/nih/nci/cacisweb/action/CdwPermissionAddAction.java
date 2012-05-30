package gov.nih.nci.cacisweb.action;

import gov.nih.nci.cacisweb.dao.DAOFactory;
import gov.nih.nci.cacisweb.dao.ICDWUserPermissionDAO;
import gov.nih.nci.cacisweb.model.CdwPermissionModel;
import gov.nih.nci.cacisweb.model.CdwUserModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class CdwPermissionAddAction extends ActionSupport {

    private static final Logger log = Logger.getLogger(CdwPermissionAddAction.class);

    private static final long serialVersionUID = 1L;

    private CdwUserModel cdwUserBean;

    private CdwPermissionModel cdwPermissionBean;

    @Override
    public String execute() throws Exception {
        log.debug("execute() - START");
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        ICDWUserPermissionDAO cdwUserPermissionDAO = daoFactory.getCDWUserPermissionDAO();
        if (!cdwUserPermissionDAO.isUserExists(getCdwUserBean())) {
            addActionError(getText("cdwUserBean.usernameDoesNotExist"));
            return INPUT;
        }
        if (cdwUserPermissionDAO.addUserPermission(getCdwUserBean(), getCdwPermissionBean())) {
            addActionMessage(getText("cdwUserBean.addPermissionSuccessful"));
        }
        log.debug("execute() - END");
        return INPUT;
    }
    
    public void validate() {
        if(StringUtils.isNotBlank(cdwPermissionBean.getPatientID())){
            if(StringUtils.isBlank(cdwPermissionBean.getSiteID())){
                addActionError(getText("cdwPermissionBean.siteIDRequired"));
            }
        }
    }

    public CdwUserModel getCdwUserBean() {
        return cdwUserBean;
    }

    public void setCdwUserBean(CdwUserModel cdwUserBean) {
        this.cdwUserBean = cdwUserBean;
    }

    public CdwPermissionModel getCdwPermissionBean() {
        return cdwPermissionBean;
    }

    public void setCdwPermissionBean(CdwPermissionModel cdwPermissionBean) {
        this.cdwPermissionBean = cdwPermissionBean;
    }

}
