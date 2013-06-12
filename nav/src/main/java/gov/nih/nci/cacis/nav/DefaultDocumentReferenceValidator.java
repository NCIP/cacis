/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.crypto.dsig.DigestMethod;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Node;

/**
 * Validates digests of documents referenced by Reference element. This class supports the following algorithms by
 * default:
 * <ul>
 * <li>SHA-1</li>
 * <li>SHA-256</li>
 * <li>SHA-512</li>
 * </ul>
 * Additional algorithms can be supported by configuring the JCA provider and describing the mapping of algorithm URI to
 * the algorithm name in the supportedAlgorithm property. E.g. http://www.w3.org/2000/09/xmldsig#sha1 = SHA-1
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Jun 9, 2011
 * 
 */

public class DefaultDocumentReferenceValidator implements DocumentReferenceValidator {

    private Map<String, String> supportedAlgorithms = new HashMap<String, String>();

    /**
     * Default Constructor
     */
    public DefaultDocumentReferenceValidator() {
        supportedAlgorithms.put(DigestMethod.SHA1, "SHA-1");
        supportedAlgorithms.put(DigestMethod.SHA256, "SHA-256");
        supportedAlgorithms.put(DigestMethod.SHA512, "SHA-512");
    }

    /*
     * (non-Javadoc) {@inheritDoc}
     */

    @Override
    public void validate(Node reference, XDSDocumentResolver resolver) throws DocumentReferenceValidationException {

        // Pull out the necessary information
        final String alg;
        final String digestValue;
        final String documentId;
        try {
            alg = NAVUtils.getDigestAlgorithm(reference);
            digestValue = NAVUtils.getDigestValue(reference);
            documentId = NAVUtils.getDocumentId(reference);
            // CHECKSTYLE:OFF - All NAVUtils errors handled the same way.
        } catch (Exception ex) {
            // CHECKSTYLE:ON
            throw new DocumentReferenceValidationException("Error extracting info from Reference: " + ex.getMessage(),
                    ex);
        }

        if (!getSupportedAlgorithms().containsKey(alg)) {
            throw new DocumentReferenceValidationException("Unsupported digest algorithm: " + alg);
        }
        if (digestValue == null) {
            throw new DocumentReferenceValidationException("No DigestValue provided.");
        }

        // Resolve the document
        final InputStream in;
        try {
            in = resolver.resolve(documentId);
        } catch (XDSDocumentResolutionException ex) {
            throw new DocumentReferenceValidationException(ex);
        }

        // Generate the digest
        final byte[] bytes;
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final byte[] data = new byte[16384];
        int nRead;
        try {
            while ((nRead = in.read(data, 0, data.length)) != -1) { // NOPMD
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            bytes = buffer.toByteArray();
        } catch (IOException ex) {
            throw new DocumentReferenceValidationException(ex);
        }

        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(getSupportedAlgorithms().get(alg));
        } catch (NoSuchAlgorithmException ex) {
            throw new DocumentReferenceValidationException(ex);
        }
        digest.reset();
        final byte[] out = digest.digest(bytes);

        // BASE64 encode it
        final String outEnc = new String(Base64.encodeBase64(out));

        // Compare it
        if (!outEnc.equals(digestValue)) {
            throw new DocumentReferenceValidationException("Digests do not match.");
        }
    }

    /**
     * @return the supportedAlgorithms
     */

    public Map<String, String> getSupportedAlgorithms() {

        return supportedAlgorithms;
    }

    /**
     * @param supportedAlgorithms the supportedAlgorithms to set
     */

    public void setSupportedAlgorithms(Map<String, String> supportedAlgorithms) {

        this.supportedAlgorithms = supportedAlgorithms;
    }

}
