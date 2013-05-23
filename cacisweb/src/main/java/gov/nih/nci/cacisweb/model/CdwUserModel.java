/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.model;

import java.util.ArrayList;

public class CdwUserModel {

    private String username = "";
    private String password = "";
    private ArrayList<CdwPermissionModel> userPermission = new ArrayList<CdwPermissionModel>();
    private String addUserButton = "";
    private String searchButton = "";
    private String deleteUserButton = "";
    private String addPermissionButton = "";
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public ArrayList<CdwPermissionModel> getUserPermission() {
        return userPermission;
    }
    
    public void setUserPermission(ArrayList<CdwPermissionModel> userPermission) {
        this.userPermission = userPermission;
    }

    
    public String getAddUserButton() {
        return addUserButton;
    }

    
    public void setAddUserButton(String addUserButton) {
        this.addUserButton = addUserButton;
    }

    
    public String getSearchButton() {
        return searchButton;
    }

    
    public void setSearchButton(String searchButton) {
        this.searchButton = searchButton;
    }

    
    public String getDeleteUserButton() {
        return deleteUserButton;
    }

    
    public void setDeleteUserButton(String deleteUserButton) {
        this.deleteUserButton = deleteUserButton;
    }

    
    public String getAddPermissionButton() {
        return addPermissionButton;
    }

    
    public void setAddPermissionButton(String addPermissionButton) {
        this.addPermissionButton = addPermissionButton;
    }
}
