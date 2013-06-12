/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.w3c.dom.ls.LSInput;


/**
 * LSInput impl object returned by the LSResourceResolver
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class Input implements LSInput {

    private String publicId;

    private String systemId;
    
    /**
     * {@inheritDoc}
     */
    public String getPublicId() {
        return publicId;
    }
    
    /**
     * {@inheritDoc}
     */
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
    /**
     * {@inheritDoc}
     */
    public String getBaseURI() {
        return null;
    }
    /**
     * {@inheritDoc}
     */
    public InputStream getByteStream() {
        return null;
    }
    /**
     * {@inheritDoc}
     */
    public boolean getCertifiedText() {
        return false;
    }
    /**
     * {@inheritDoc}
     */
    public Reader getCharacterStream() {
        return null;
    }
    /**
     * {@inheritDoc}
     */
    public String getEncoding() {
        return null;
    }
    /**
     * {@inheritDoc}
     */
    public String getStringData() {
        synchronized (inputStream) {
            try {
                final byte[] input = new byte[inputStream.available()];
                inputStream.read(input);
                return new String(input);
            } catch (IOException e) {
                return null;
            }
        }
    }
    /**
     * {@inheritDoc}
     */
    public void setBaseURI(String baseURI) {
        //do nothing
    }
    /**
     * {@inheritDoc}
     */
    public void setByteStream(InputStream byteStream) {
        //do nothing
    }
    /**
     * {@inheritDoc}
     */
    public void setCertifiedText(boolean certifiedText) {
        //do nothing
    }
    /**
     * {@inheritDoc}
     */
    public void setCharacterStream(Reader characterStream) {
        //do nothing
    }
    /**
     * {@inheritDoc}
     */
    public void setEncoding(String encoding) {
        //do nothing
    }
    /**
     * {@inheritDoc}
     */
    public void setStringData(String stringData) {
        //do nothing
    }
    /**
     * {@inheritDoc}
     */
    public String getSystemId() {
        return systemId;
    }
    /**
     * {@inheritDoc}
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    /**
     * {@inheritDoc}
     */
    public BufferedInputStream getInputStream() {
        return inputStream;
    }
    /**
     * {@inheritDoc}
     */
    public void setInputStream(BufferedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    private BufferedInputStream inputStream;
    
    /**
     * constructor with fields
     * @param publicId - resource public id
     * @param sysId - resource system id
     * @param input - InputStream to the resource
     */
    public Input(String publicId, String sysId, InputStream input) {
        this.publicId = publicId;
        this.systemId = sysId;
        this.inputStream = new BufferedInputStream(input);
    }
}
