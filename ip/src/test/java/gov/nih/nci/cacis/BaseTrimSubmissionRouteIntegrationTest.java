package gov.nih.nci.cacis;

import gov.nih.nci.cacis.ip.IPTolvenWSTestConfig;

import java.io.File;
import java.util.List;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.cxf.CxfPayload;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.apache.cxf.binding.soap.SoapHeader;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.w3c.dom.Element;

/**
 * 
 * @author monish.dombla@semanticbits.com
 * @since May 24, 2011 
 *
 */

@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class BaseTrimSubmissionRouteIntegrationTest extends CamelSpringTestSupport {
    
    @Produce(uri = "direct:submit:pcotrim")
    private ProducerTemplate producerTemplate;
    
    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
        new javax.net.ssl.HostnameVerifier(){
 
            public boolean verify(String hostname,
                    javax.net.ssl.SSLSession sslSession) {
                if (hostname.equals("localhost")) {
                    return true;
                }
                return false;
            }
        });
    }
    
    public void setUp() throws Exception { //NOPMD
        super.setUp();
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new AnnotationConfigApplicationContext(IPTolvenWSTestConfig.class);
    }
    
    @Test
    public void testTrimSubmission() throws Exception {
        
        Object reply = producerTemplate.requestBody(new File(getClass().getClassLoader().getResource("sample-pco-patient-trim.xml").toURI()));
        assertNotNull(reply);
        
        if(reply instanceof CxfPayload){
            List<Element> elements = ((CxfPayload<SoapHeader>)reply).getBody();
            assertNotNull(elements);
            assertEquals(1,elements.size());
            Element ele = elements.get(0);
            assertNotNull(ele);
            assertEquals("success",ele.getFirstChild().getTextContent());
        }
    }
}