/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
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
