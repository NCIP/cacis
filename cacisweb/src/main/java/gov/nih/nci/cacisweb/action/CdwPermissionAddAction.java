/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.action;

import java.util.ArrayList;

import gov.nih.nci.cacisweb.dao.DAOFactory;
import gov.nih.nci.cacisweb.dao.ICDWUserPermissionDAO;
import gov.nih.nci.cacisweb.exception.DAOException;
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
    public String execute() {
        log.debug("execute() - START");
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        try {
            ICDWUserPermissionDAO cdwUserPermissionDAO = daoFactory.getCDWUserPermissionDAO();
            if (!cdwUserPermissionDAO.isUserExists(getCdwUserBean())) {
                addActionError(getText("cdwUserBean.usernameDoesNotExist"));
                return INPUT;
            }
            if (cdwUserPermissionDAO.addUserPermission(getCdwUserBean(), getCdwPermissionBean())) {
                addActionMessage(getText("cdwUserBean.addPermissionSuccessful"));
            }
            cdwUserBean.setUserPermission((ArrayList<CdwPermissionModel>) cdwUserPermissionDAO
                    .searchUserPermissions(getCdwUserBean()));
            cdwPermissionBean = new CdwPermissionModel();
        } catch (DAOException e) {
            if (StringUtils.contains(e.getMessage(), "Non unique primary key")) {
                addActionMessage(getText("cdwUserBean.permissionAlreadyExists"));
                return INPUT;
            } else {
                addActionError(e.getMessage());
                return ERROR;
            }
        }
        log.debug("execute() - END");
        return INPUT;
    }

    public void validate() {
        if (StringUtils.isNotBlank(cdwPermissionBean.getPatientID())) {
            if (StringUtils.isBlank(cdwPermissionBean.getSiteID())) {
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
