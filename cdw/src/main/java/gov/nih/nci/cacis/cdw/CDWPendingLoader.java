package gov.nih.nci.cacis.cdw;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.helpers.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class CDWPendingLoader {

    private static final Logger LOG = Logger.getLogger(CDWPendingLoader.class.getName());

    @Value("${cdw.load.pending.directory}")
    private String cdwLoadPendingDirectory;

    @Value("${cdw.load.processed.directory}")
    private String cdwLoadProcessedDirectory;

    @Value("${cdw.load.error.directory}")
    private String cdwLoadErrorDirectory;

    @Value("${cdw.load.sender.host}")
    private String cdwLoadSenderHost;

    @Value("${cdw.load.sender.port}")
    private String cdwLoadSenderPort;

    @Value("${cdw.load.sender.user}")
    private String cdwLoadSenderUser;

    @Value("${cdw.load.sender.pw}")
    private String cdwLoadSenderPassword;

    @Value("${cdw.load.sender.address}")
    private String cdwLoadSenderAddress;

    @Value("${cdw.load.recipient.address}")
    private String cdwLoadRecipientAddress;

    @Value("${cdw.load.notification.subject}")
    private String cdwLoadNotificationSubject;

    @Value("${cdw.load.notification.message}")
    private String cdwLoadNotificationMessage;

    public void loadPendingCDWDocuments(CDWLoader loader) {
        LOG.debug("Pending folder: " + cdwLoadPendingDirectory);
        File pendingFolder = new File(cdwLoadPendingDirectory);
        FilenameFilter loadFileFilter = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return true;
            }
        };
        String[] loadFileNames = pendingFolder.list(loadFileFilter);
        for (String fileName : loadFileNames) {
            LOG.info("File Name: " + fileName);
            File loadFile = new File(cdwLoadPendingDirectory + "/" + fileName);
            try {
                final String[] params = StringUtils.split(fileName, "@@");
                String siteId = params[0];
                String studyId = params[1];
                String patientId = params[2];
                LOG.debug("SiteId: " + siteId);

                // the below code is not working, so parsing the file name for attributes.
                // DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                // DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                // Document document = documentBuilder.parse(loadFile);
                // XPathFactory factory = XPathFactory.newInstance();
                // XPath xPath = factory.newXPath();
                // String siteID = xPath.evaluate("/caCISRequest/clinicalMetaData/@siteIdRoot", document);

                loader
                        .load(FileUtils.getStringFromFile(loadFile), loader.generateContext(), studyId, siteId,
                                patientId);
                LOG.info(String.format("Successfully processed file [%s] and moving into [%s]", loadFile
                        .getAbsolutePath(), cdwLoadProcessedDirectory));
                org.apache.commons.io.FileUtils
                        .moveFileToDirectory(loadFile, new File(cdwLoadProcessedDirectory), true);
            } catch (CDWLoaderException e) {
                LOG.error(e.getMessage());
                e.printStackTrace();
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", cdwLoadSenderHost);
                    props.put("mail.smtp.port", cdwLoadSenderPort);

                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {

                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(cdwLoadSenderUser, cdwLoadSenderPassword);
                        }
                    });
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(cdwLoadSenderAddress));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cdwLoadRecipientAddress));
                    message.setSubject(cdwLoadNotificationSubject);
                    message.setText(cdwLoadNotificationMessage + " ["+e.getMessage() +"]");

                    Transport.send(message);
                    org.apache.commons.io.FileUtils
                            .moveFileToDirectory(loadFile, new File(cdwLoadErrorDirectory), true);
                    // TODO add logic to send email
                } catch (Exception e1) {
                    LOG.error(e1.getMessage());
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
