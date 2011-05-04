/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The caEHR Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and 5AM Solutions Inc, SemanticBits LLC, and AgileX Technologies, Inc (collectively 'SubContractors').
 * To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United
 * States Code, section 105.
 * 
 * This caEHR Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the caEHR Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly
 * display, publicly perform, and prepare derivative works of the caEHR Software; (ii) distribute and have distributed
 * to and by third parties the caEHR Software and any modifications and derivative works thereof; and (iii) sublicense
 * the foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
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
 * product includes software developed by the National Cancer Institute and SubContractor parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 * 
 * You may not use the names "The National Cancer Institute", "NCI", or any SubContractor party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or any of the subcontracted parties, except as required to comply with
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
package gov.nih.nci.cacis.common.util;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests the ExtractSchematron utility class.
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Aug 24, 2010
 * 
 */
public class ExtractSchematronTest {

    private static final Logger LOG = Logger.getLogger(ExtractSchematronTest.class);

    private static final String WSDL_PATH = "Schemas/OrganizationRegistryService_Query_wsdl.xsd";
    private static final String BAD_WSDL_PATH = "ReferralConsultServiceReferredTo_createWithError.xml";
    private static final String DATATYPES_PATH = "coreschemas/iso-21090_hl7-r2_datatypes.xsd";
    private static final String BAD_DATATYPES_PATH = "bad/datatypes/path.yadda";

    /* please add any custom rule file names (comma-separated) that are introduced */
    private static final String CUSTOM_RULES_LIST = "";


    private File concreteFile;
    private File abstractFile;
    private ExtractSchematron es;
    private String wsdlPath;
    private String datatypesPath;

    /**
     * Sets up temp files.
     * 
     * @throws IOException if there is an error creating temp files.
     * @throws TransformerFactoryConfigurationError on error
     * @throws IllegalAccessException on error
     * @throws InstantiationException on error
     * @throws ClassNotFoundException on error
     * @throws XPathExpressionException on error
     * @throws TransformerConfigurationException on error
     * @throws ClassCastException on error
     */
    @Before
    public void init() throws IOException, ClassCastException, TransformerConfigurationException,
            XPathExpressionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
            TransformerFactoryConfigurationError {
        concreteFile = File.createTempFile("concrete", null);
        abstractFile = File.createTempFile("abstract", null);
        es = new ExtractSchematron();
        final ClassLoader cl = ExtractSchematronTest.class.getClassLoader(); 
        wsdlPath = cl.getResource(WSDL_PATH).toString();
        datatypesPath = cl.getResource(DATATYPES_PATH).toString();
    }

    /**
     * Deletes temp files.
     * 
     */
    @After
    public void cleanUp() {
        try {
            concreteFile.delete();
            // CHECKSTYLE:OFF Want to throw original exception
        } catch (Exception ex) {
            // CHECKSTYLE:ON
            LOG.debug(ex);
        }
        try {
            // CHECKSTYLE:OFF Want to throw original exception
            abstractFile.delete();
        } catch (Exception ex) {
            // CHECKSTYLE:ON
            LOG.debug(ex);
        }
    }

    /**
     * Tests successful extraction.
     * 
     */
    @Test
    public void extractOK() {

        try {
            new ExtractSchematron().extract(wsdlPath, datatypesPath, abstractFile.getAbsolutePath(), concreteFile
                    .getAbsolutePath(), CUSTOM_RULES_LIST);
            // CHECKSTYLE:OFF Want to catch anything.
        } catch (Exception ex) {
            // CHECKSTYLE:ON
            fail("Error extracting schematron: " + ex.getMessage());
        }
    }

    /**
     * Tests failed extraction.
     * 
     * @throws TransformerFactoryConfigurationError on error.
     * @throws IllegalAccessException on error.
     * @throws InstantiationException on error.
     * @throws ClassNotFoundException on error.
     * @throws XPathExpressionException on error.
     * @throws TransformerConfigurationException on error.
     * @throws ClassCastException on error.
     */
    @Test
    public void extractFailed() throws ClassCastException, TransformerConfigurationException, XPathExpressionException,
            ClassNotFoundException, InstantiationException, IllegalAccessException,
            TransformerFactoryConfigurationError {
        try {
            new ExtractSchematron().extract(wsdlPath, BAD_DATATYPES_PATH, abstractFile.getAbsolutePath(), concreteFile
                    .getAbsolutePath(), CUSTOM_RULES_LIST);
            fail("Should have thrown Exception.");
        } catch (IOException ex) {
            assertTrue(true);
        }
    }

    /**
     * Tests failed extraction.
     * 
     * @throws TransformerFactoryConfigurationError on error.
     * @throws IllegalAccessException on error.
     * @throws InstantiationException on error.
     * @throws ClassNotFoundException on error.
     * @throws XPathExpressionException on error.
     * @throws TransformerConfigurationException on error.
     * @throws ClassCastException on error.
     */
    @Test
    public void extractFailed2() throws ClassCastException, TransformerConfigurationException,
            XPathExpressionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
            TransformerFactoryConfigurationError {
        try {
            new ExtractSchematron().extract(BAD_WSDL_PATH, datatypesPath, abstractFile.getAbsolutePath(), concreteFile
                    .getAbsolutePath(), CUSTOM_RULES_LIST);
            fail("Should have thrown Exception.");
        } catch (IOException ex) {
            assertTrue(true);
        }
    }

    /**
     * Does thorough validation of the rules document.
     * 
     * throws Exception
     * 
     * @throws TransformerFactoryConfigurationError - on error.
     * @throws IllegalAccessException - on error.
     * @throws InstantiationException - on error.
     * @throws ClassNotFoundException - on error.
     * @throws XPathExpressionException - on error.
     * @throws TransformerConfigurationException - on error.
     * @throws ClassCastException - on error.
     */
    @Ignore
    @Test
    public void validateRules() throws ClassCastException, TransformerConfigurationException, XPathExpressionException,
            ClassNotFoundException, InstantiationException, IllegalAccessException,
            TransformerFactoryConfigurationError {
        try {
            es.extract(wsdlPath, datatypesPath, abstractFile.getAbsolutePath(), concreteFile.getAbsolutePath(),
                    CUSTOM_RULES_LIST);
            // CHECKSTYLE:OFF Want to catch anything.
        } catch (Exception ex) {
            // CHECKSTYLE:ON
            fail("Error extracting schematron: " + ex.getMessage());
        }

        // Check well-formedness.
        final Document abstractRules = parseRules("abstract", abstractFile);
        final Document concreteRules = parseRules("concrete", concreteFile);

        // Check validity.
        // TODO get RelaxNG validator

        // Check that contexts are valid XPath.
        validateXPathExpressions(concreteRules, "//sch:rule/@context", "Invalid context in concrete rules");

        // Check that tests are valid XPath.
        validateXPathExpressions(abstractRules, "//sch:rule/sch:assert/@test", "Invalid test in abstract rules");

        // Check rule references.
        final XPathFactory xpFact = XPathFactory.newInstance();
        XPath xpath = xpFact.newXPath();
        xpath.setNamespaceContext(es.getNamespaceContext());
        XPathExpression xpathExp = xpath.compile("//sch:rule/sch:extends/@rule");
        final NodeList result = (NodeList) xpathExp
                .evaluate(concreteRules.getDocumentElement(), XPathConstants.NODESET);
        for (int i = 0; i < result.getLength(); i++) {
            final Attr attr = (Attr) result.item(i);
            final XPath xpath2 = xpFact.newXPath();
            xpath2.setNamespaceContext(es.getNamespaceContext());
            final XPathExpression xpathExp2 = xpath2.compile("count(//sch:rule[@id='" + attr.getValue() + "']) gt 0");
            final Boolean found = (Boolean) xpathExp2.evaluate(abstractRules, XPathConstants.BOOLEAN);
            assertTrue("Zero matches for ID: " + attr.getValue(), found.booleanValue());
        }

        // Spot checks
        final String aContext = "ns0:findOrganizationByExample/organization/hl7:code/hl7:originalText";
        
        xpath = xpFact.newXPath();
        xpath.setNamespaceContext(es.getNamespaceContext());
        xpathExp = xpath.compile("count(//sch:rule[starts-with(@context, \"" + aContext + "\")]) gt 0");
        final Boolean found = (Boolean) xpathExp.evaluate(concreteRules, XPathConstants.BOOLEAN);
        assertTrue("Zero concrete rules found for context '" + aContext + "'", found.booleanValue());

        checkAbstractRuleExists("hl7:item/@validTimeLow or hl7:item/@validTimeHigh", xpFact, abstractRules);
        checkAbstractRuleExists("(@nullFlavor and not(hl7:any|hl7:low|hl7:high|hl7:width)) or (not(@nullFlavor) "
                + "and (hl7:any|hl7:low|hl7:high|hl7:width))", xpFact, abstractRules);
    }
    
    private void checkAbstractRuleExists(String rule, XPathFactory xpFact, Document abstractRules)
            throws XPathExpressionException {
        final XPath xpath = xpFact.newXPath();
        xpath.setNamespaceContext(es.getNamespaceContext());
        final XPathExpression xpathExp = xpath.compile("count(//sch:assert[@test=\"" + rule + "\"]) gt 0");
        final Boolean found = (Boolean) xpathExp.evaluate(abstractRules, XPathConstants.BOOLEAN);
        assertTrue("Zero abstract rules found for test '" + rule + "'", found.booleanValue());
        
    }

    /**
     * @param namespaceContext
     * @param concreteRules
     * @param string
     * @param string2
     * @throws XPathExpressionException
     */
    private void validateXPathExpressions(Document rules, String exp, String message) throws XPathExpressionException {
        final XPathFactory xpFact = XPathFactory.newInstance();
        final XPath xpath = xpFact.newXPath();
        xpath.setNamespaceContext(es.getNamespaceContext());
        final XPathExpression xpathExp = xpath.compile(exp);
        final NodeList result = (NodeList) xpathExp.evaluate(rules.getDocumentElement(), XPathConstants.NODESET);
        assertTrue("No nodes selected", result.getLength() > 0);
        for (int i = 0; i < result.getLength(); i++) {
            final Attr attr = (Attr) result.item(i);
            final String expValue = attr.getValue();
            try {
                final XPath xpath2 = xpFact.newXPath();
                xpath2.setNamespaceContext(es.getNamespaceContext());
                xpath2.compile(expValue);
            } catch (XPathExpressionException ex) {
                fail(message + ": " + expValue);
            }
        }
    }

    /**
     * @param abstractFile2
     * @return
     */
    private Document parseRules(String rulesType, File file) {
        try {
            final DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            fact.setNamespaceAware(true);
            return fact.newDocumentBuilder().parse(file);
        } catch (SAXException ex) {
            fail("SAXException parsing " + rulesType + " rules: " + ex.getMessage());
        } catch (IOException ex) {
            fail("IOException parsing " + rulesType + " rules: " + ex.getMessage());
        } catch (ParserConfigurationException ex) {
            fail("ParseConfigurationException parsing " + rulesType + " rules: " + ex.getMessage());
        }
        return null;
    }
    
    /**
     * Tests ExtractSchematron with multiple input WSDLs and no schematron custom rules. 
     * @throws XPathExpressionException if error occured
     */
    @Ignore
    @Test
    public void testMultipleWSDLs() throws XPathExpressionException {

        final ClassLoader cl = this.getClass().getClassLoader();
        final StringBuilder wsdlPath2 = new StringBuilder(
                cl.getResource("Schemas/ProviderRegistryService_Management_wsdl.xsd").toString());
        wsdlPath2.append(',');
        wsdlPath2.append(cl.getResource("Schemas/ProviderRegistryService_Query_wsdl.xsd").toString());
        wsdlPath2.append(',');
        wsdlPath2.append(this.wsdlPath); 
        
        try {
            es.extract(wsdlPath2.toString(), datatypesPath, abstractFile.getAbsolutePath(), concreteFile.getAbsolutePath(),
                    null);
            // CHECKSTYLE:OFF Want to catch anything.
        } catch (Exception ex) {
            // CHECKSTYLE:ON
            fail("Error extracting schematron: " + ex.getMessage());
        }
        
        final Document concreteRules = parseRules("concrete", concreteFile);
        // Spot checks
        
        final String aContext = "ns0:updateProvider/provider/hl7:healthCarePerson/hl7:name";
        
        final XPathFactory xpFact = XPathFactory.newInstance();
        final XPath xpath = xpFact.newXPath();
        xpath.setNamespaceContext(es.getNamespaceContext());
        final XPathExpression xpathExp = xpath.
            compile("count(//sch:rule[starts-with(@context, \"" + aContext + "\")]) gt 0");
        final Boolean found = (Boolean) xpathExp.evaluate(concreteRules, XPathConstants.BOOLEAN);
        assertTrue("Zero concrete rules found for context '" + aContext + "'", found.booleanValue());

    }
    
}
