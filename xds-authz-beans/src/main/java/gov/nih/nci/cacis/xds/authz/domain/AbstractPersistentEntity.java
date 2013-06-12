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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract superclass for all persisted entities.
 */
@MappedSuperclass
public class AbstractPersistentEntity {

    private Long entityId;

    /**
     * @return entityId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getEntityId() {
        return entityId;
    }

    /**
     * @param entityId - new entity id
     *
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractPersistentEntity)) {
            return false;
        }
        if (this.getEntityId() == null) {
            return false;
        }
        final AbstractPersistentEntity other = (AbstractPersistentEntity)o;
        return new EqualsBuilder().append(this.getEntityId(), other.getEntityId()).isEquals();
    }

    @Override
    public int hashCode() {
         return new HashCodeBuilder(7, 17).append(this.getEntityId()).toHashCode();
    }
}
