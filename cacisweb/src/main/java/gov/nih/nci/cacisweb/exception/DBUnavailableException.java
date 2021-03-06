/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.exception;

/**
 * @author Ajay Nalamala
 */
public class DBUnavailableException extends CaCISWebException {

    private static final long serialVersionUID = -5845265416209197670L;

    /**
     * Constructor for DBUnavailableException.
     */
    public DBUnavailableException() {
        super();
    }

    /**
     * Constructor for DBUnavailableException.
     * 
     * @param s
     */
    public DBUnavailableException(String s) {
        super(s);
    }

}
