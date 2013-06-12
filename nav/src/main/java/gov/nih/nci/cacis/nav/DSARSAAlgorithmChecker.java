/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import javax.xml.crypto.dsig.SignatureMethod;

/**
 * @author bpickeral
 * @since Jun 21, 2011
 */
public class DSARSAAlgorithmChecker implements AlgorithmChecker {

    /**
     * DSA Algorithm Name.
     */
    public static final String DSA_ALGORITHM = "DSA";

    /**
     * RSA Algorithm Name.
     */
    public static final String RSA_ALGORITHM = "RSA";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean algEquals(String algURI, String algName) {
        return (algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1) && algName.equalsIgnoreCase(DSA_ALGORITHM))
                || (algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1) && algName.equalsIgnoreCase(RSA_ALGORITHM));
    }

}
