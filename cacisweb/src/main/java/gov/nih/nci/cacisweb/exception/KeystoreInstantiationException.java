/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.exception;

public class KeystoreInstantiationException extends CaCISWebException {

    private static final long serialVersionUID = 7017640562016786310L;

    public KeystoreInstantiationException() {
        super();
    }

    public KeystoreInstantiationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public KeystoreInstantiationException(String arg0) {
        super(arg0);
    }

    public KeystoreInstantiationException(Throwable arg0) {
        super(arg0);
    }

}
