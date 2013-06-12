/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.transformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * WS tests Mirth Connect transformer XCCD to CDF
 * 
 * @author bhumphrey
 */
public class XCCDToCDFTransformerIntegrationTest extends AbstractTransformerSystemTest {

    private static final Log LOG = LogFactory.getLog(XCCDToCDFTransformerIntegrationTest.class);
    
    /**
     * Constant for wsdl address
     */
    private static final String ADDRESS = "http://localhost:9082/services/Mirth?wsdl";

    /**
     * 
     * @return invalid message string
     */
    protected String getInvalidMessage() {
        return "Invalid Message";
    }

    /**
     * 
     * @return String representing the valid soap message file name
     */
    @Override
    protected String getValidSOAPMessageFilename() {
        return "WS2-CDF-XCCD-valid-soap.xml";
    }

    @Override
    protected String getWSDLAddress() {
        return ADDRESS;
    }
}
