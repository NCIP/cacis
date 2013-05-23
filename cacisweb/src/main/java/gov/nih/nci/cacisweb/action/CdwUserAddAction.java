/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.action;

import gov.nih.nci.cacisweb.dao.DAOFactory;
import gov.nih.nci.cacisweb.dao.ICDWUserPermissionDAO;
import gov.nih.nci.cacisweb.model.CdwPermissionModel;
import gov.nih.nci.cacisweb.model.CdwUserModel;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class CdwUserAddAction extends ActionSupport {

    private static final Logger log = Logger.getLogger(CdwUserAddAction.class);

    private static final long serialVersionUID = 1L;

    private CdwUserModel cdwUserBean;

    private CdwPermissionModel cdwPermissionBean;

    @Override
    public String execute() throws Exception {
        log.debug("execute() - START");
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        ICDWUserPermissionDAO cdwUserPermissionDAO = daoFactory.getCDWUserPermissionDAO();
        if (cdwUserPermissionDAO.isUserExists(getCdwUserBean())) {
            addActionError(getText("cdwUserBean.usernameAlreadyExists"));
            return INPUT;
        }
        if (cdwUserPermissionDAO.addUser(getCdwUserBean())) {
            addActionMessage(getText("cdwUserBean.addUserSuccessful"));
        }
        log.debug("execute() - END");
        return INPUT;
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
