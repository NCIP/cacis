/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.sa.mirthconnect.error;

/**
 * Defines error codes for the semantic adapter.
 * @author bpickeral
 * @since Aug 10, 2011
 */
public enum SemanticAdapterError {

    /**
     *  An error occurred in the Canonical Model Processor.
     */
    CANONICAL_MODEL_PROCESSOR_ERROR(100, "An error occurred in the Canonical Model Processor."),
    /**
     * Failed to reach Canonical Model Processor Web Service.
     */
    CANONICAL_MODEL_PROCESSOR_RECIEVE_FAILURE(101, "Failed to reach Canonical Model Processor Web Service."),
    /**
     * Failed to store document in the Clinical Data Warehouse.
     */
    CLINICAL_DATA_WAREHOUSE_STORAGE_FAILURE(200, "Failed to store document in the Clinical Data Warehouse."),
    /**
     * Failed to send Document from the Canonical Model Processor to the Clinical Data Warehouse.
     */
    CLINICAL_DATA_WAREHOUSE_RECIEVE_FAILURE(201,
            "Failed to send Document from the Canonical Model Processor to the Clinical Data Warehouse."),
    /**
     * Failed to add Graph to graph group in the Clinical Data Warehouse.
     */
    CLINICAL_DATA_WAREHOUSE_GRAPH_GROUP_FAILURE(202, "Failed to add Graph to graph group in the Clinical Data Warehouse."),
    /**
     * Failed to send Document from the Canonical Model Processor to the Document Router.
     */
    DOCUMENT_ROUTER_RECIEVE_FAILURE(300,
            "Failed to send Document from the Canonical Model Processor to the Document Router."),
    /**
     * An error occurred in NAV.
     */
    NAV_ERROR(400, "An error occurred in NAV."),
    /**
     * Failed to send notification in NAV.
     */
    NAV_SEND_NOTIFICATION_FAILURE(401, "Failed to send notification in NAV."),
    /**
     * Failed to validate notification in NAV.
     */
    NAV_NOTIFICATION_VALIDATION_FAILURE(402, "Failed to validate notification in NAV."),
    /**
     * Failed to select key from keystore in NAV.
     */
    NAV_KEYSTORE_FAILURE(403, "Failed to select key from keystore in NAV."),
    /**
     * Failed to build signature in NAV.
     */
    NAV_SIGNATURE_BUIlDER_FAILURE(404, "Failed to build signature in NAV."),
    /**
     * Error formatting XSL for transformation.
     */
    TRANSFORMER_XSL_ERROR(501, "Error formatting XSL for transformation."),
    /**
     * Error transforming from CDF to XCCD.
     */
    TRANSFORMER_CDF_TO_XCCD_ERROR(502, "Error transforming from CDF to XCCD."),
    /**
     * Error transforming from XCCD to CDF.
     */
    TRANSFORMER_XCCD_TO_CDF_ERROR(503, "Error transforming from XCCD to CDF."),
    /**
     * Error transforming from Transcend Trim to CCD.
     */
    TRANSFORMER_TRANSCEND_TRIM_TO_CCD_ERROR(504, "Error transforming from Transcend Trim to CCD."),
    /**
     * Could not reach Mirth Connect.
     */
    MIRTH_CONNECT_RECIEVE_FAILURE(600, "Could not reach Mirth Connect");

    private final String message;
    private final int code;

    private SemanticAdapterError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return code + " - " + message;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
