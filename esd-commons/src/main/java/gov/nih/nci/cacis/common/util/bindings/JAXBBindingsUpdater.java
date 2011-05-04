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
package gov.nih.nci.cacis.common.util.bindings;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * JAXBBindingsUpdater class is to update the bindings file 
 * supplied to the XJC based code generator.
 * It takes a list of xsds and applies the xsl that generates the required jaxbbindings
 * and appends it to the caehr bindings file.
 * 
 * @author chandrav vinodh.rc@semanticbits.com
 * @since Oct 6, 2010
 * 
 */
public class JAXBBindingsUpdater {
   
    private DocumentBuilder builder;

    private Transformer transformer;
    private StreamResult result;

    /**
     * Entry method to start the bindings update
     * 
     * @param stylesheet - xsl file used for adding the jaxb bindings
     * @param baseinputfile - base file that holds the base content to which bindings are appended
     * @param outputfile - the file that contains the base content with the bindings appended
     * @param schemasLocationDir - directory where all the xsds are present
     * @param commaSeparatedXsdNamesToBeExcluded - xsds to be excluded from this binding
     * @throws JAXBBindingsUpdaterException instance of the JAXBBindingsUpdaterException
     */
    public void update(String stylesheet, String baseinputfile, String outputfile, String schemasLocationDir,
            String commaSeparatedXsdNamesToBeExcluded) throws JAXBBindingsUpdaterException {
        
        initialize(stylesheet);
        initializeResult(baseinputfile, outputfile);
        transformAllXsdsFromDir(schemasLocationDir, commaSeparatedXsdNamesToBeExcluded);
        completeWithBindings();
    }

    private void initialize(String stylesheetfile) throws JAXBBindingsUpdaterException {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            // factory.setValidating(true);
            builder = factory.newDocumentBuilder();

            // Use a Transformer for output
            final TransformerFactory tFactory = TransformerFactory.newInstance();
            final StreamSource stylesource = new StreamSource(new File(stylesheetfile));
            transformer = tFactory.newTransformer(stylesource);
        } catch (TransformerConfigurationException tce) {
            // Error generated by the parser
            // Use the contained JAXBBindingsUpdaterException, if any            
            throw new JAXBBindingsUpdaterException("TransformerConfiguration error " , tce);
        } catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            throw new JAXBBindingsUpdaterException("ParserConfiguration error " , pce);
        }
    }

    private void initializeResult(String baseFile, String outputFile) throws JAXBBindingsUpdaterException {
        try {
            final File output = new File(outputFile);
            FileUtils.copyFile(new File(baseFile), output);
            result = new StreamResult(new FileWriter(output, true));
        } catch (IOException e) {
            throw new JAXBBindingsUpdaterException("Unable to initialize the output file, " + outputFile , e);
        }
        
    }

    private void transformAllXsdsFromDir(String xsdLocationDir, final String commaSeparatedXsdNamesToBeExcluded)
            throws JAXBBindingsUpdaterException {
        final File schemasDir = new File(xsdLocationDir);
        if (!schemasDir.exists() || !schemasDir.isDirectory()) {
            throw new JAXBBindingsUpdaterException(xsdLocationDir + " is not valid location to find the schemas!");
        }
        final File[] xsds = schemasDir.listFiles(new XSDFileFilter(commaSeparatedXsdNamesToBeExcluded));
        for (final File xsdFile : xsds) {
            transform(xsdFile);
        }
    }

    private void completeWithBindings() throws JAXBBindingsUpdaterException {
        final Writer writer = result.getWriter();       
        try {
            writer.append("</jaxb:bindings>");
            writer.flush();
        } catch (IOException e) {
            throw new JAXBBindingsUpdaterException("Unable to complete the new bindings file" , e);
        }
    }

    private void transform(File sourceFile) throws JAXBBindingsUpdaterException {
        try {
            transformer.setParameter("xsdfilepath", sourceFile.toURI().toASCIIString());
            Document document;
            document = builder.parse(sourceFile);
            
            final DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
        } catch (IOException e) {
            throw new JAXBBindingsUpdaterException("Unable to parse the source xsd file", e);
        } catch (TransformerException e) {
            throw new JAXBBindingsUpdaterException("Transformation error ", e);
        } catch (SAXException sxe) {
            // Error generated by this application
            // (or a parser-initialization error)            
            throw new JAXBBindingsUpdaterException("SAX error " , sxe);
        }
    }
    

    /**
     * @param argv - array of input strings
     * @throws JAXBBindingsUpdaterException - instance of JAXBBindingsUpdaterException
     */
    public static void main(String[] argv) throws JAXBBindingsUpdaterException {
        if (argv.length < 4) {
            throw new JAXBBindingsUpdaterException(
                "Usage: java JAXBBindingsUpdater" + 
                 "stylesheet baseinputfile outputfile schemasLocationDir commaSeparatesdXsdNamesToBeExcluded(optional)");
        }
        final String[] arg = argv;
        /*
        final String[] arg = new String[5];
        arg[0] = "bin/extract-nillables-xpath.xsl";
        arg[1] = "software/caehr-esd-war/antfiles/base_caehr.xjb";
        arg[2] = "software/caehr-esd-war/antfiles/caehr_new.xjb"; // has to be caehr.xjb
        arg[3] = "bin/META-INF/Schemas";
        arg[4] = "REPC_MT000005US.xsd,REPC_MT000007US.xsd";
        */
        String xsdsToBeExcluded = null;
        if (arg.length == 5) {
            xsdsToBeExcluded = arg[4];
        }

        final JAXBBindingsUpdater jaxbBindingsUpdater = new JAXBBindingsUpdater();
        jaxbBindingsUpdater.update(arg[0], arg[1], arg[2], arg[3], xsdsToBeExcluded);        
    } // main

}
