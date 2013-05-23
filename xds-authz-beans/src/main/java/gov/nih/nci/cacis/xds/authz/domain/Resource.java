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

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author kherm manav.kher@semanticbits.com
 */
@Entity
@Table(name = "tbl_resource")
@DiscriminatorColumn(name = "resource_type", discriminatorType = DiscriminatorType.STRING)
public class Resource extends AbstractPersistentEntity {

    private Set<Subject> subjects = new TreeSet<Subject>();


    /**
     * Getter for
     * Subjects that have access to this resource
     *
     * @return Subjects that have access to the resource
     */
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    public Set<Subject> getSubjects() {
        return subjects;
    }

    /**
     * Setter for subjects
     *
     * @param subjects subjects
     */
    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }


    /**
     * Add a subject to the list of Subjects
     * that have access to this Resource
     *
     * @param subjectDn Subject Distinguished Name
     * @return boolean Set.add()
     */
    public boolean addSubject(String subjectDn) {
        for (Subject subject : subjects) {
            if (subject.getDn().equals(subjectDn)) {
//                already exists
                return false;
            }
        }
        final Subject subject = new Subject();
        subject.setDn(subjectDn);
        return getSubjects().add(subject);
    }

    /**
     * Remove Subject from this Resource
     *
     * @param subjectDn Subject DN to be removed
     * @return boolean Set.remove()
     */
    public boolean removeSubject(String subjectDn) {
        for (Subject subject : subjects) {
            if (subject.getDn().equals(subjectDn)) {
                return this.getSubjects().remove(subject);
            }
        }
        return false;
    }
}
