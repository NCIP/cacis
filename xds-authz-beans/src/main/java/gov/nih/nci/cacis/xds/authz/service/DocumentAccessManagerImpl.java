/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds.authz.service;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.domain.DocumentResource;
import gov.nih.nci.cacis.xds.authz.domain.Subject;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Document Access Manager
 * for managing access to XDS Documents
 */
@Transactional
public class DocumentAccessManagerImpl implements DocumentAccessManager {


    @PersistenceContext(unitName = "cacis-xds-authz")
    private EntityManager em;
    private static final Logger LOG = Logger.getLogger(DocumentAccessManagerImpl.class);

    /**
     * No args constructor
     */
    public DocumentAccessManagerImpl() {
        //    no args constructor
    }

    /**
     * Constructor
     *
     * @param em JPA Entity Manager
     */
    public DocumentAccessManagerImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void grantDocumentAccess(String documentSetId, String userId)
            throws AuthzProvisioningException {

        DocumentResource docResource = findByDocumentSetId(documentSetId);
        if (docResource == null) {
            LOG.info("Document ID does not exist. Will create " + documentSetId);
            docResource = new DocumentResource(documentSetId);
        }

        docResource.addSubject(userId);
        em.persist(docResource);

    }

    @Override
    public void revokeDocumentAccess(String documentSetId, String userId)
            throws AuthzProvisioningException {

        final DocumentResource docResource = findByDocumentSetId(documentSetId);

        if (docResource == null) {
            throw new AuthzProvisioningException("Document with id " + documentSetId + " does not exist");

        } else {
            docResource.removeSubject(userId);
            em.persist(docResource);
        }
    }

    @Override
    public boolean checkDocumentAccess(String documentSetId, String userId) throws AuthzProvisioningException {

        final DocumentResource docResource = findByDocumentSetId(documentSetId);

        if (docResource == null) {
            throw new AuthzProvisioningException("Document with id " + documentSetId + " does not exist");

        } else {
            for (Subject subject : docResource.getSubjects()) {
                if (subject.getDn().equals(userId)) {
                    return true;
                }

            }
        }
        return false;
    }


    private DocumentResource findByDocumentSetId(String documentSetId) throws AuthzProvisioningException {
        try {
            final Query docQuery = this.em.createQuery("from " + DocumentResource.class.getSimpleName()
                    + " doc where doc.docId = :docId ");
            docQuery.setParameter("docId", documentSetId);

            return (DocumentResource) docQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Getter for Entity Manager
     *
     * @return EntityManager
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * Setter for Entity Manager
     *
     * @param em EntityManager
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
