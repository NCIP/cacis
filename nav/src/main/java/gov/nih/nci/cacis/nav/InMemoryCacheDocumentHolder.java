/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
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
        if (StringUtils.isEmpty(docContent)) {
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
        if (docContent == null) {
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
        if (docFile == null || !docFile.exists()) {
            return;
        }
        final byte[] docContent = FileUtils.readFileToByteArray(docFile);
        final InputStream docIn = new ByteArrayInputStream(docContent);
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

        if (tempCacheDir != null) {
            final File cacheDir = new File(tempCacheDir);
            if (!cacheDir.exists() && cacheDir.mkdirs() ) {
                    throw new IOException("Unable to create cache dir, " + cacheDir.getAbsolutePath());
            }
            cos.setOutputDir(cacheDir);
        }

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
