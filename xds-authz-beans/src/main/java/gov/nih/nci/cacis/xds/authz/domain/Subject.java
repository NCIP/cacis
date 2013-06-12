/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@Entity
@Table(name = "tbl_subject")
public class Subject extends AbstractPersistentEntity
        implements Comparable {

    private String dn;

    /**
     * Distinguished Name property
     *
     * @return dn
     */
    public String getDn() {
        return dn;
    }

    /**
     * Setter for Distinguished Name
     *
     * @param dn Distinguished Name
     */
    public void setDn(String dn) {
        this.dn = dn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Subject)) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        final Subject that = (Subject) o;

        if (dn != null ? !dn.equals(that.dn) : that.dn != null) {   //NOPMD
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (dn != null ? dn.hashCode() : 0);  //NOPMD
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return ((Subject) o).getDn().compareTo(getDn());
    }
}
