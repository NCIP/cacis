/*
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The caEHR Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and 5AM Solutions Inc, SemanticBits LLC, and AgileX Technologies, Inc (collectively 'SubContractors').
 * To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United
 * States Code, section 105.
 *
 * This caEHR Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 *  of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 *
 * Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: This
 * product includes software developed by the National Cancer Institute and SubContractor parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or any SubContractor party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or any of the subcontracted parties, except as required to comply with
 * the terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs
 * and into any third party proprietary programs. However, if You incorporate the Software into third party proprietary
 * programs, You agree that You are solely responsible for obtaining any permission from such third parties required to
 * incorporate the Software into such third party proprietary programs and for informing Your sub-licensees, including
 * without limitation Your end-users, of their obligation to secure any required permissions from such third parties
 * before incorporating the Software into such third party proprietary software programs. In the event that You fail to
 * obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to the
 * extent prohibited by law, resulting from Your failure to obtain such permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and
 * to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses
 * of modifications of the Software, or any derivative works of the Software as a whole, provided Your use,
 * reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, ANY OF ITS SUBCONTRACTED PARTIES OR THEIR AFFILIATES BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.cacis.xds.authz.service;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.xds.authz.domain.DocumentResource;
import gov.nih.nci.cacis.xds.authz.domain.Subject;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Document Access Manager
 * for managing access to XDS Documents
 */
@Transactional
public class DocumentAccessManagerImpl implements DocumentAccessManager {

    private final EntityManager em;
    private static final Logger LOG = Logger.getLogger(DocumentAccessManagerImpl.class);


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
}
