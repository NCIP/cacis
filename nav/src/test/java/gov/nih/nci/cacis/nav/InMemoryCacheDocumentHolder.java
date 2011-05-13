/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The nav Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and subcontracted parties. To the extent government employees are authors, any rights in such works
 * shall be subject to Title 17 of the United States Code, section 105.
 * 
 * This nav Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the nav Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly
 * display, publicly perform, and prepare derivative works of the nav Software; (ii) distribute and have distributed to
 * and by third parties the nav Software and any modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.io.CachedOutputStream;

/**
 * An in-memory cache for XDS documents. Based on the max threshold, it uses TEMPORARY file caching.
 * 
 * This implementation uses only temporary caching. Once this holder is destroyed, these temporary files will be
 * deleted.
 * 
 * @author vinodh.rc@semanticbits.com
 * 
 */
public class InMemoryCacheDocumentHolder {

    private final ConcurrentHashMap<String, CachedOutputStream> docMap = new ConcurrentHashMap<String, CachedOutputStream>();

    private String tempCacheDir = null;

    private long cacheThreshold = -1;

    /**
     * Default constructor
     */
    public InMemoryCacheDocumentHolder() {
        super();
    }

    /**
     * Max cache threshold to hold the document in memory, otherwise use a temporary file cache
     * 
     * @param cacheThreshold - threshold value in bytes
     */
    public InMemoryCacheDocumentHolder(long cacheThreshold) {
        super();
        this.cacheThreshold = cacheThreshold;
    }

    /**
     * Max cache threshold to hold the document in memory, otherwise use a temporary file cache
     * 
     * @param tempCacheDir - path to the cache dir, where the temporary cache files will be stored
     * @param cacheThreshold - threshold value in bytes
     */
    public InMemoryCacheDocumentHolder(String tempCacheDir, long cacheThreshold) {
        super();
        this.tempCacheDir = tempCacheDir;
        this.cacheThreshold = cacheThreshold;
    }

    /**
     * Checks if there is a document associated with the docId
     * 
     * @param docId - key to identify the document
     * @return boolean - indicates doc is present or not
     */
    public boolean containsDocument(String docId) {
        return docMap.containsKey(docId);
    }
    
    /**
     * Adds the document to the in memory cache with the docId as the key
     * 
     * @param docId - key to identify the document
     * @param docContent - document content as String
     * @throws IOException - thrown if any IO error happens
     */
    public void putDocument(String docId, String docContent) throws IOException {
        if ( StringUtils.isEmpty(docContent) ) {
            return;
        }        
        final InputStream docIn = new ByteArrayInputStream(docContent.getBytes());
        putDocument(docId, docIn);
    }
    
    /**
     * Adds the document to the in memory cache with the docId as the key
     * 
     * @param docId - key to identify the document
     * @param docContent - document content as byte[]
     * @throws IOException - thrown if any IO error happens
     */
    public void putDocument(String docId, byte[] docContent) throws IOException {
        if ( docContent == null ) {
            return;
        }        
        final InputStream docIn = new ByteArrayInputStream(docContent);
        putDocument(docId, docIn);
    }
    
    /**
     * Adds the document to the in memory cache with the docId as the key
     * 
     * @param docId - key to identify the document
     * @param docFile - File instance representing the document
     * @throws IOException - thrown if any IO error happens
     */
    public void putDocument(String docId, File docFile) throws IOException { 
        if ( docFile == null || !docFile.exists() ) {
            return;
        }
        final InputStream docIn = new FileInputStream(docFile);
        putDocument(docId, docIn);
    }
    
    /**
     * Adds the document to the in memory cache with the docId as the key
     * 
     * @param docId - key to identify the document
     * @param docIn - InputStream to the document
     * @throws IOException - thrown if any IO error happens
     */
    public void putDocument(String docId, InputStream docIn) throws IOException {
        if (containsDocument(docId)) {
            removeDocument(docId);
        }
        final CachedOutputStream cos = new CachedOutputStream(cacheThreshold);

        final File cacheDir = new File(tempCacheDir);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        cos.setOutputDir(cacheDir);

        CachedOutputStream.copyStream(docIn, cos, 1024);

        docMap.put(docId, cos);
    }

    /**
     * To read the document
     * 
     * @param docId - key to identify the document
     * @return InputStream - InputStream to read the document
     * @throws IOException - thrown if any IO error happens
     */
    public InputStream getDocument(String docId) throws IOException {
        final CachedOutputStream cos = docMap.get(docId);
        if (cos != null) {
            return cos.getInputStream();
        }
        return null;
    }

    /**
     * To remove the document
     * 
     * @param docId - key to identify the document
     * @throws IOException - thrown if any IO error happens
     */
    public void removeDocument(String docId) throws IOException {
        final CachedOutputStream cos = docMap.get(docId);
        if (cos != null) {
            // need to call close() to make sure, temp file is deleted
            cos.close();
            docMap.remove(docId);
        }
    }

    /**
     * To clear the holder of all documents
     * 
     * @throws IOException - thrown if any IO error happens
     */
    public void clearAll() throws IOException {
        final Collection<CachedOutputStream> values = docMap.values();
        for (CachedOutputStream cachedOutputStream : values) {
            // need to call close() to make sure, temp file is deleted
            cachedOutputStream.close();
        }
        docMap.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    // CHECKSTYLE:OFF
    protected void finalize() throws Throwable {
        // CHECKSTYLE:ON
        clearAll();
        super.finalize();
    }

}
