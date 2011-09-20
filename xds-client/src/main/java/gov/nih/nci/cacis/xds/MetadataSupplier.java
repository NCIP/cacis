package gov.nih.nci.cacis.xds;

import java.io.IOException;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public interface MetadataSupplier {

    String createDocEntry() throws IOException;

    String createSubmissionSet() throws IOException;

    String createDocOID();

    String createDocSourceOID();
}
