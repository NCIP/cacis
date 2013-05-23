/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds;

import java.io.IOException;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public interface MetadataSupplier {

    /**
     *
     * @return String
     * @throws IOException Exception
     */
    String createDocEntry() throws IOException;

    /**
     *
     * @return String
     * @throws IOException Exception
     */
    String createSubmissionSet() throws IOException;

    /**
     *
     * @return String
     */
    String createDocOID();

    /**
     *
     * @return String
     */
    String createDocSourceOID();
}
