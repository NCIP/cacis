/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.xds;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class StaticMetadataSupplier implements MetadataSupplier {


    @Override
    public String createDocEntry() throws IOException {
        final InputStream staticDocEntry = getClass().getClassLoader().getResourceAsStream("docEntry.xml");
        final StringWriter writer = new StringWriter();
        IOUtils.copy(staticDocEntry, writer);
        return writer.toString();
    }

    @Override
    public String createSubmissionSet() throws IOException {
        final InputStream staticSubSet = getClass().getClassLoader().
                getResourceAsStream("submissionSet.xml");
        final StringWriter writer = new StringWriter();
        IOUtils.copy(staticSubSet, writer);
        return writer.toString();
    }

    @Override
    public String createDocOID() {
        return "1.2.3";
    }

    @Override
    public String createDocSourceOID() {
        return "1.3.6.1.4.1.21367.2010.1.2";
    }



}
