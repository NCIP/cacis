/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The gov.nih.nci.cacis.nav-1.0 Software was developed in conjunction with the National
 * Cancer Institute (NCI) by NCI employees and subcontracted parties. To the extent government employees are authors,
 * any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 * 
 * This gov.nih.nci.cacis.nav-1.0 Software License (the License) is between NCI and You. You (or Your) shall mean a
 * person or an entity, and all other entities that control, are controlled by, or are under common control with the
 * entity. Control for purposes of this definition means (i) the direct or indirect power to cause the direction or
 * management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the
 * outstanding shares, or (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the gov.nih.nci.cacis.nav-1.0 Software to (i) use, install, access, operate, execute, copy, modify,
 * translate, market, publicly display, publicly perform, and prepare derivative works of the gov.nih.nci.cacis.nav-1.0
 * Software; (ii) distribute and have distributed to and by third parties the gov.nih.nci.cacis.nav-1.0 Software and any
 * modifications and derivative works thereof; and (iii) sublicense the foregoing rights set out in (i) and (ii) to
 * third parties, including the right to license such rights to further third parties. For sake of clarity, and not by
 * way of limitation, NCI shall have no right of accounting or right of payment from You or Your sub-licensees for the
 * rights granted under this License. This License is granted at no charge to You.
 * 
 * Your redistributions of the source code for the Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code
 * form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 
 * Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: This
 * product includes software developed by the National Cancer Institute and subcontracted parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 * 
 * You may not use the names "The National Cancer Institute", "NCI", or any subcontracted party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or theany of the subcontracted parties, except as required to comply with
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
