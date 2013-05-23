/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@Entity
@DiscriminatorValue("operation-store")
public class XdsWriteResource extends Resource {

    private String name;

    /**
     * Getter for Name property
     * @return Name of the operation-store
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for Name property
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }
}
