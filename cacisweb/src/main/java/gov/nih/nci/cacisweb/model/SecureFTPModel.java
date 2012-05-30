package gov.nih.nci.cacisweb.model;

import java.io.File;

public class SecureFTPModel {

    private String certificateAlias;
    private String certificateDN;
    private File certificate;
    private String certificateContentType;
    private String certificateFileName;
    private String addButton;

    public String getCertificateAlias() {
        return certificateAlias;
    }

    public void setCertificateAlias(String certificateAlias) {
        this.certificateAlias = certificateAlias;
    }

    public String getCertificateDN() {
        return certificateDN;
    }

    public void setCertificateDN(String certificateDN) {
        this.certificateDN = certificateDN;
    }

    public File getCertificate() {
        return certificate;
    }

    public void setCertificate(File certificate) {
        this.certificate = certificate;
    }

    public String getCertificateContentType() {
        return certificateContentType;
    }

    public void setCertificateContentType(String certificateContentType) {
        this.certificateContentType = certificateContentType;
    }

    public String getCertificateFileName() {
        return certificateFileName;
    }

    public void setCertificateFileName(String certificateFileName) {
        this.certificateFileName = certificateFileName;
    }

    public String getAddButton() {
        return addButton;
    }

    public void setAddButton(String addButton) {
        this.addButton = addButton;
    }

    public String toString() {
        return "Certificate Alias: " + getCertificateAlias() + " DN:  " + getCertificateDN()
                + " Certificate File Name: " + getCertificateFileName() + " Certificate Content Type: "
                + getCertificateContentType();
    }
}
