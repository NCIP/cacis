/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The caEHR Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and 5AM Solutions Inc, SemanticBits LLC, and AgileX Technologies, Inc (collectively 'SubContractors').
 * To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United
 * States Code, section 105.
 *
 * This caEHR Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the caEHR Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly
 * display, publicly perform, and prepare derivative works of the caEHR Software; (ii) distribute and have distributed
 * to and by third parties the caEHR Software and any modifications and derivative works thereof; and (iii) sublicense
 * the foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
 * third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting or right of
 * payment from You or Your sub-licensees for the rights granted under this License. This License is granted at no
 * charge to You.
 *
 * Your redistributions of the source code for the Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code
 * form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
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

package gov.nih.nci.cacis.cdw;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import gov.nih.nci.cacis.transform.XmlToRdfTransformer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.openrdf.model.URI;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

/**
 * Converts the XML message to RDF and stores the output Document into the Clinical Data warehouse and
 *  adds the document to the study, site and patient graph groups.
 *
 * @author bpickeral
 * @since Jul 18, 2011
 */
public class CDWLoader {

    /**
     * caCIS context URI.
     */
    public static final String CACIS_NS = "http://cacis.nci.nih.gov";

    @Autowired
    private XmlToRdfTransformer transformer;
    @Autowired
    private RepositoryConnection con;
    @Autowired
    private GraphAuthzMgr graphAuthzMgr;

    /**
     * Transforms XML to RDF and then stores into Virtuoso.
     *
     * @param xmlString String containing RDF
     * @param context the context to add the data to, used for pulling out the data
     * @param studyId id of the study to be used in the IRI
     * @param siteId id of the site to be used in the IRI
     * @param patientId id of the patient to be used in the IRI
     * @throws CDWLoaderException on error transforming or loading the data into Virtuoso
     */
    public void load(String xmlString, String context, String studyId, String siteId,
            String patientId) throws CDWLoaderException {
        try {
            final org.openrdf.model.URI uriContext = con.getRepository().getValueFactory().createURI(context);
            load(new ByteArrayInputStream(xmlString.getBytes()), uriContext, studyId, siteId, patientId);
            //CHECKSTYLE:OFF
        } catch(Exception e) {
            //CHECKSTYLE:ON
            throw new CDWLoaderException(e);
        }
    }

    private void load(InputStream xmlStream, URI context, String studyId, String siteId,
            String patientId) throws TransformerException, RepositoryException, RDFParseException, IOException,
            AuthzProvisioningException, SAXException, ParserConfigurationException {
        
        final Map<String,String> params = new HashMap<String,String>();
        params.put("BaseURI", context.toString());
        final OutputStream os = new ByteArrayOutputStream();
        transformer.transform(params, xmlStream, os);
       
        ByteArrayOutputStream bos = null;
        InputStream repoStream = null;
        try {
            bos = (ByteArrayOutputStream) os;
            repoStream = new ByteArrayInputStream(bos.toByteArray());

            con.add(repoStream, context.toString(), RDFFormat.RDFXML, context);

            addToGraphGroups(context, studyId, siteId, patientId);
        } finally {
            bos.close();
        }
    }

    private void addToGraphGroups(URI context, String studyId, String siteId,
            String patientId) throws AuthzProvisioningException {
        final Set<URI> grphGrps = new HashSet<URI>();
        final URI studyURI = con.getRepository().getValueFactory().createURI(appendToIRI(CACIS_NS,
                studyId));
        final URI siteURI = con.getRepository().getValueFactory().createURI(appendToIRI(studyURI.toString(),
                siteId));
        final URI patientURI = con.getRepository().getValueFactory().createURI(appendToIRI(siteURI.toString(),
                patientId));

        grphGrps.add(studyURI);
        grphGrps.add(siteURI);
        grphGrps.add(patientURI);
        graphAuthzMgr.add(context, grphGrps);
    }


    private String appendToIRI(String s1, String s2) {
        return s1 + '/' + s2;
    }


    /**
     * Generates a caCIS context with a random UUID - http://cacis.nci.nih.gov/<UUID>
     *
     * @return context
     */
    public String generateContext() {
        final UUID uuid = UUID.randomUUID();
        return CDWLoader.CACIS_NS + '/' + uuid;
    }

}
