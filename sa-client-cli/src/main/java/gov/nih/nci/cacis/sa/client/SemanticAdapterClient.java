/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.sa.client;

import gov.nih.nci.cacis.sa.AcceptSourceFault;
import gov.nih.nci.cacis.sa.AcceptSourcePortType;
import gov.nih.nci.cacis.sa.CaCISRequest;
import gov.nih.nci.cacis.sa.CaCISResponse;
import gov.nih.nci.cacis.sa.config.SemanticAdapterClientConfig;

import java.io.File;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class SemanticAdapterClient {

    private static final Logger LOG = Logger.getLogger(SemanticAdapterClient.class);


    /**
     * @param requestFileUrl file that has the request to be submitted
     * @return String response
     * @throws JAXBException     Exception
     * @throws AcceptSourceFault Service Fault
     */
    public String invoke(URL requestFileUrl) throws JAXBException, AcceptSourceFault {
        return this.invoke(requestFileUrl.getFile());
    }


    /**
     * @param requestFileUrl file that has the request to be submitted
     * @return String response
     * @throws JAXBException     Exception
     * @throws AcceptSourceFault Service Fault
     */
    public String invoke(String requestFileUrl) throws JAXBException, AcceptSourceFault {
        return this.invoke(new File(requestFileUrl));
    }

    /**
     * @param requestFile file that has the request to be submitted
     * @return String response
     * @throws JAXBException     Exception
     * @throws AcceptSourceFault Service Fault
     */
    public String invoke(File requestFile) throws JAXBException, AcceptSourceFault {

        final ApplicationContext ctx =
                new AnnotationConfigApplicationContext(SemanticAdapterClientConfig.class);
        final AcceptSourcePortType
                saClient = (AcceptSourcePortType) ctx.getBean("client");

        final JAXBContext jc = JAXBContext.newInstance(CaCISRequest.class);

        final CaCISRequest request = (CaCISRequest) jc.createUnmarshaller().unmarshal(requestFile);

        LOG.info("Sending request to SA Service ");
        //        todo serialize response
        final CaCISResponse response = saClient.acceptSource(request);

        if (response != null) {
            final StringWriter result = new StringWriter();
            jc.createMarshaller().marshal(response, result);

            return result.toString();
        }
        return null;

    }

    /**
     * Main method
     *
     * @param args argument. Only 1 is expected which is the path to the request data file
     * @throws JAXBException     Exception
     * @throws AcceptSourceFault Service fault
     */
    public static void main(String[] args) throws JAXBException, AcceptSourceFault {
        if (args.length != 1) {
            System.out.println("usage is " +                //NOPMD
                    "\"java SemanticAdapterClient <request_file_url>");
            System.exit(0);
        }
        final SemanticAdapterClient client = new SemanticAdapterClient();
        client.invoke(args[0]);

    }
}
