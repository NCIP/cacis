package gov.nih.nci.cacis.ip.mirthconnect;

import gov.nih.nci.cacis.AcceptCanonicalFault;
import gov.nih.nci.cacis.CaCISRequest;
import gov.nih.nci.cacis.CaCISResponse;
import gov.nih.nci.cacis.CanonicalModelProcessorPortType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class CanonicalModelProcessorClient {

    private final String keyStoreLocation, keyStorePassword,
            trustStoreLocation,trustStorePassword ;

    /**
     * Constructor
     *
     * @param keyStoreLocation   JKS keystore to use
     * @param keyStorePassword   keystore password
     * @param trustStoreLocation JKS trustStore location
     * @param trustStorePassword trustStore password
     */
    public CanonicalModelProcessorClient(String keyStoreLocation,
                                         String keyStorePassword,
                                         String trustStoreLocation,
                                         String trustStorePassword) {
        this.keyStoreLocation = keyStoreLocation;
        this.keyStorePassword = keyStorePassword;
        this.trustStoreLocation = trustStoreLocation;
        this.trustStorePassword = trustStorePassword;
        
        System.setProperty("javax.net.ssl.keyStore", keyStoreLocation);
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
        System.setProperty("javax.net.ssl.trustStore", trustStoreLocation);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

    }



    /**
     *   * invokes the accessCanonical operation on the CMP
     * service
     * @param wsdl WSDL location of the CMP service
     * @param requestStr request data
     * @return  CaCISResponse response
     * @throws gov.nih.nci.cacis.AcceptCanonicalFault Service fault
     * @throws java.net.MalformedURLException means WSDL URL is invalid
     * @throws javax.xml.bind.JAXBException request data cannot be serialized
     *
     */
    public CaCISResponse acceptCanonical(String wsdl,
                                         String requestStr)
            throws AcceptCanonicalFault, JAXBException, MalformedURLException {

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        if ("localhost".equals(hostname)) {
                            return true;
                        }
                        return false;
                    }
                });

        

        final JAXBContext ctx = JAXBContext.newInstance(CaCISRequest.class);
        final Unmarshaller unmarshaller = ctx.createUnmarshaller();
        final CaCISRequest request = (CaCISRequest) unmarshaller.unmarshal
                (new ByteArrayInputStream(requestStr.getBytes()));

        final URL wsdlUrl = new URL(wsdl);
        final CanonicalModelProcessorPortType service = new
                gov.nih.nci.cacis.CanonicalModelProcessor(wsdlUrl).getCanonicalModelProcessorPortSoap11();

        return service.acceptCanonical(request);


    }

}
