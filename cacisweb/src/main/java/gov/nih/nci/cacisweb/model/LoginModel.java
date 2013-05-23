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


public class LoginModel {

    private String j_username;
    private String j_password;

    public String getJ_username() {
        return j_username;
    }

    public void setJ_username(String jUsername) {
        j_username = jUsername;
    }

    public String getJ_password() {
        return j_password;
    }

    public void setJ_password(String jPassword) {
        j_password = jPassword;
    }

    public String toString() {
        return "j_username: " + getJ_username() + " j_password:  " + getJ_password();
    }
}
