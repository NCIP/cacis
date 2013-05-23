/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw.config;

import gov.nih.nci.cacis.cdw.CDWLoader;
import gov.nih.nci.cacis.cdw.CDWPendingLoader;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public interface CDWConfig {


    /**
     * CDW Loader
     * @return loader
     */
    CDWLoader loader();
    
    /**
     * CDW Loader
     * @return loader
     */
    CDWPendingLoader pendingLoader();
}
