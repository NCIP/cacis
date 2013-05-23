/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.nav;

import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Common methods for sending mails
 * 
 * @author vinodh.rc@semanticbits.com
 * 
 */

public class AbstractSendMail {

    private static final Logger LOG = Logger.getLogger(AbstractSendMail.class);
    private static final String XML_EXT = ".XML";
    private static final String TXT_EXT = ".TXT";

    /**
     * Keystore type
     */
    public static final String STORE_TYPE = "PKCS12";
    /**
     * Provider to use
     */
    public static final String PROVIDER_TYPE = "BC";

    /**
     * SMTP port to use, if not supplied
     */
    public static final int SMTP_PORT = 3025;

    private JavaMailSenderImpl mailSender;
    private Properties mailProperties;
    private String from;
    private String userName;
    private String password;
    private String host;
    private int port;
    private String protocol;
    protected String secEmailTempZipLocation;

    private File tempZipFolder;
    List<String> fileList = new ArrayList<String>();

    /**
     * Default constructor
     */
    public AbstractSendMail() {
        super();
        mailSender = new JavaMailSenderImpl();
    }

    /**
     * constructor with Mail sender configuration
     * 
     * @param mailProperties - properties for mail sender
     * @param from - from email address
     * @param host - email server host
     * @param port - email server port
     * @param protocol - email protocol
     * 
     */
    @SuppressWarnings( { "PMD.ExcessiveParameterList" })
    // CHECKSTYLE:OFF
    public AbstractSendMail(Properties mailProperties, String from, String host, int port, String protocol) {
        this();
        this.mailProperties = mailProperties;
        this.from = from;
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        this.secEmailTempZipLocation = secEmailTempZipLocation;
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(host);
        mailSender.setPort(port);
        if (StringUtils.isNotEmpty(protocol)) {
            mailSender.setProtocol(protocol);
        }
    }

    // CHECKSTYLE:ON

    /**
     * Sends MimeMessage(mail) over transport
     * 
     * @param message - MimeMessage
     */
    public void sendMail(MimeMessage message) {
        mailSender.send(message);
    }

    /**
     * Setting sender email account credentials
     * 
     * @param userName - username
     * @param password - password
     */
    public void setLoginDetails(String userName, String password) {
        this.userName = userName;
        this.password = password;
        mailSender.setUsername(userName);
        mailSender.setPassword(password);
    }

    /**
     * Set default command cap to support signing, encrypting and multipart
     */
    protected void setCommandCap() {
        final MailcapCommandMap mailcap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        // CHECKSTYLE:OFF
        mailcap
                .addMailcap("application/pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_signature");
        mailcap
                .addMailcap("application/pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_mime");
        mailcap
                .addMailcap("application/x-pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_signature");
        mailcap
                .addMailcap("application/x-pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_mime");
        mailcap
                .addMailcap("multipart/signed;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.multipart_signed");
        // CHECKSTYLE:ON
        CommandMap.setDefaultCommandMap(mailcap);
    }

    /**
     * Create a java mail session for a smtp server and port
     * 
     * @param smtpServer - server host name
     * @param smtpPort - smtp port
     * @param mailprops - Properties for the mail sender
     * @return instance of java mail session
     */
    protected Session createSession(String smtpServer, String smtpPort, Properties mailprops) {
        Properties props = mailprops;
        if (props == null) {
            props = System.getProperties();
        }
        props.put("mail.smtp.host", smtpServer);
        props.setProperty("mail.smtp.port", smtpPort);
        final Session session = Session.getDefaultInstance(props, null);

        return session;
    }

    /**
     * Creates MimeMessage with supplied values
     * 
     * @param to - to email address
     * @param docType - String value for the attached document type
     * @param subject - Subject for the email
     * @param instructions - email body
     * @param content - content to be sent as attachment
     * @return MimeMessage instance
     */
    public MimeMessage createMessage(String to, String docType, String subject, String instructions, String content,
            String metadataXMl, String title, String indexBodyToken, String readmeToken) {
        final MimeMessage msg = mailSender.createMimeMessage();
        UUID uniqueID = UUID.randomUUID();
        tempZipFolder = new File(secEmailTempZipLocation + "/" + uniqueID);
        try {
            msg.setFrom(new InternetAddress(getFrom()));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // The readable part
            final MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(instructions);
            mbp1.setHeader("Content-Type", "text/plain");

            // The notification
            final MimeBodyPart mbp2 = new MimeBodyPart();

            final String contentType = "application/xml; charset=UTF-8";

            String extension;

            // HL7 messages should be a txt file, otherwise xml
            if (StringUtils.contains(instructions, "HL7_V2_CLINICAL_NOTE")) {
                extension = TXT_EXT;
            } else {
                extension = XML_EXT;
            }

            final String fileName = docType + UUID.randomUUID() + extension;

//            final DataSource ds = new AttachmentDS(fileName, content, contentType);
//            mbp2.setDataHandler(new DataHandler(ds));

            /******** START NHIN COMPLIANCE CHANGES *****/

            boolean isTempZipFolderCreated = tempZipFolder.mkdirs();
            if (!isTempZipFolderCreated) {
                LOG.error("Error creating temp folder for NHIN zip file: " + tempZipFolder.getAbsolutePath());
                throw new ApplicationRuntimeException("Error creating temp folder for NHIN zip file: "
                        + tempZipFolder.getAbsolutePath());
            }
            
            String indexFileString = FileUtils.readFileToString(new File(secEmailTempZipLocation + "/INDEX.HTM"));
            String readmeFileString = FileUtils.readFileToString(new File(secEmailTempZipLocation + "/README.TXT"));
            
            indexFileString = StringUtils.replace(indexFileString, "@document_title@", title);
            indexFileString = StringUtils.replace(indexFileString, "@indexBodyToken@", indexBodyToken);
            FileUtils.writeStringToFile(new File(tempZipFolder+"/INDEX.HTM"), indexFileString);
            
            readmeFileString = StringUtils.replace(readmeFileString, "@readmeToken@", readmeToken);
            FileUtils.writeStringToFile(new File(tempZipFolder+"/README.TXT"), readmeFileString);

            // move template files & replace tokens
//            FileUtils.copyFileToDirectory(new File(secEmailTempZipLocation + "/INDEX.HTM"), tempZipFolder, false);
//            FileUtils.copyFileToDirectory(new File(secEmailTempZipLocation + "/README.TXT"), tempZipFolder, false);

            // create sub-directories
            String nhinSubDirectoryPath = tempZipFolder + "/IHE_XDM/SUBSET01";
            File nhinSubDirectory = new File(nhinSubDirectoryPath);
            boolean isNhinSubDirectoryCreated = nhinSubDirectory.mkdirs();
            if (!isNhinSubDirectoryCreated) {
                LOG.error("Error creating NHIN sub-directory: " + nhinSubDirectory.getAbsolutePath());
                throw new ApplicationRuntimeException("Error creating NHIN sub-directory: "
                        + nhinSubDirectory.getAbsolutePath());
            }
            FileOutputStream metadataStream = new FileOutputStream(new File(nhinSubDirectoryPath + "/METADATA.XML"));
            metadataStream.write(metadataXMl.getBytes());
            metadataStream.flush();
            metadataStream.close();
            FileOutputStream documentStream = new FileOutputStream(new File(nhinSubDirectoryPath + "/DOCUMENT"
                    + extension));
            documentStream.write(content.getBytes());
            documentStream.flush();
            documentStream.close();

            String zipFile = secEmailTempZipLocation + "/" + tempZipFolder.getName() + ".ZIP";
            byte[] buffer = new byte[1024];
//            FileOutputStream fos = new FileOutputStream(zipFile);
//            ZipOutputStream zos = new ZipOutputStream(fos);

            List<String> fileList = generateFileList(tempZipFolder);
            ByteArrayOutputStream bout = new ByteArrayOutputStream(fileList.size());
            ZipOutputStream zos = new ZipOutputStream( bout );
//            LOG.info("File List size: "+fileList.size());
            for (String file : fileList) {
                ZipEntry ze = new ZipEntry(file);
                zos.putNextEntry(ze);
                FileInputStream in = new FileInputStream(tempZipFolder + File.separator + file);
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
            }
            zos.closeEntry();
            // remember close it
            zos.close();
            
            DataSource source = new ByteArrayDataSource( bout.toByteArray(), "application/zip" );
            mbp2.setDataHandler( new DataHandler( source ) );
            mbp2.setFileName(docType+ ".ZIP" );             

            /******** END NHIN COMPLIANCE CHANGES *****/

//            mbp2.setFileName(fileName);
//            mbp2.setHeader("Content-Type", contentType);

            final Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);
            msg.setContent(mp);
            msg.setSentDate(new Date());
            
//            FileUtils.deleteDirectory(tempZipFolder);
        } catch (AddressException e) {
            LOG.error("Error creating email message!");
            throw new ApplicationRuntimeException("Error creating message!", e);
        } catch (MessagingException e) {
            LOG.error("Error creating email message!");
            throw new ApplicationRuntimeException("Error creating message!", e);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new ApplicationRuntimeException(e.getMessage());
        } finally{
          //reset filelist contents
            fileList = new ArrayList<String>();
        }        
        return msg;
    }

    /**
     * Traverse a directory and get all files, and add the file into fileList
     * 
     * @param node file or directory
     */
    public List<String> generateFileList(File node) {
//        LOG.info("Temp Zip File location: "+node.getAbsolutePath());
        // add file only
        if (node.isFile()) {
//            LOG.info("File name: "+node.getName());
            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
        }
        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                generateFileList(new File(node, filename));
            }
        }
        return fileList;
    }

    /**
     * Format the file path for zip
     * 
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file) {
        return file.substring(tempZipFolder.getAbsolutePath().length() + 1, file.length());
    }

    /**
     * @return the mailProperties
     */
    public Properties getMailProperties() {
        return mailProperties;
    }

    /**
     * @param mailProperties the mailProperties to set
     */
    public void setMailProperties(Properties mailProperties) {
        this.mailProperties = mailProperties;
        mailSender.setJavaMailProperties(mailProperties);
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
        mailSender.setPassword(password);
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
        mailSender.setUsername(userName);
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
        mailSender.setHost(host);
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
        mailSender.setPort(port);
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
        if (StringUtils.isNotEmpty(protocol)) {
            mailSender.setProtocol(protocol);
        }
    }

    public String getSecEmailTempZipLocation() {
        return secEmailTempZipLocation;
    }

    public void setSecEmailTempZipLocation(String secEmailTempZipLocation) {
        this.secEmailTempZipLocation = secEmailTempZipLocation;
    }

    /**
     * @return the mailSender
     */
    public JavaMailSenderImpl getMailSender() {
        return mailSender;
    }

    /**
     * @param mailSender the mailSender to set
     */
    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * DataSource for the mail attachment
     * 
     * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
     * 
     */
    public static class AttachmentDS implements DataSource {

        private final String fileName;
        private final String content;
        private final String contentType;

        /**
         * Constructor for supplying the content
         * 
         * @param fileName - filename of the attachment
         * @param content - attachment as String content
         * @param contentType - attachment type
         */
        public AttachmentDS(String fileName, String content, String contentType) {
            super();
            this.fileName = fileName;
            this.content = content;
            this.contentType = contentType;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(content.getBytes("UTF-8"));
        }

        @Override
        public String getName() {
            return fileName;
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IllegalArgumentException("getOutputStream should not be called.");
        }

    }

}
