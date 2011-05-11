/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The nav Software was developed in conjunction with the National Cancer Institute (NCI) by
 * NCI employees and subcontracted parties. To the extent government employees are authors, any rights in such works
 * shall be subject to Title 17 of the United States Code, section 105.
 * 
 * This nav Software License (the License) is between NCI and You. You (or Your) shall mean a person or an entity, and
 * all other entities that control, are controlled by, or are under common control with the entity. Control for purposes
 * of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 * (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the nav Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly
 * display, publicly perform, and prepare derivative works of the nav Software; (ii) distribute and have distributed to
 * and by third parties the nav Software and any modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the right to license such rights to further
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
 * product includes software developed by the National Cancer Institute and subcontracted parties. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party
 * acknowledgments normally appear.
 * 
 * You may not use the names "The National Cancer Institute", "NCI", or any subcontracted party to endorse or promote
 * products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade
 * names, logos or product names of either NCI or theany of the subcontracted parties, except as required to comply with
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

package gov.nih.nci.cacis.nav;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since May 10, 2011
 * 
 */

public class SMTPNotificationSender implements NotificationSender {

    private XDSNotificationSignatureBuilder signatureBuilder;
    private Properties mailProperties;
    private String subject;
    private String to;
    private String from;
    private String instructions;

    /**
     * Default constructor
     */
    public SMTPNotificationSender() { // NOPMD

    }

    /**
     * Parameterized constructor.
     * 
     * @param signatureBuilder the XDSNotificationSignatureBuilder
     * @param mailProperties Java mail properties
     * @param subject the subject
     * @param to the to address
     * @param from the from address
     * @param instructions instructions to the user
     */
    public SMTPNotificationSender(XDSNotificationSignatureBuilder signatureBuilder, Properties mailProperties,
            String subject, String to, String from, String instructions) {
        this.signatureBuilder = signatureBuilder;
        this.mailProperties = mailProperties;
        this.subject = subject;
        this.to = to;
        this.from = from;
        this.instructions = instructions;
    }

    /*
     * (non-Javadoc) {@inheritDoc}
     */

    @Override
    public void send(List<String> documentIds) throws NotificationSendException {

        Node sig = null;
        try {
            sig = getSignatureBuilder().buildSignature(documentIds);
        } catch (SignatureBuildingException ex) {
            throw new NotificationSendException("Couldn't build notification signature: " + ex.getMessage(), ex);
        }

        try {
            sendEmail(sig);
        } catch (AddressException e) {
            throw new NotificationSendException(e);
        } catch (TransformerConfigurationException e) {
            throw new NotificationSendException(e);
        } catch (UnsupportedEncodingException e) {
            throw new NotificationSendException(e);
        } catch (MessagingException e) {
            throw new NotificationSendException(e);
        } catch (TransformerException e) {
            throw new NotificationSendException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new NotificationSendException(e);
        }

    }

    private void sendEmail(Node sig) throws AddressException, MessagingException, TransformerConfigurationException,
            TransformerException, TransformerFactoryConfigurationError, UnsupportedEncodingException {
        final Session session = Session.getInstance(getMailProperties(), null);
        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(getFrom()));
        msg.setSubject(getSubject());
        msg.setSentDate(new Date());
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(getTo()));

        // The readable part
        final MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText(getInstructions());
        mbp1.setHeader("Content-Type", "text/plain");

        // The notification
        final MimeBodyPart mbp2 = new MimeBodyPart();

        final DOMSource source = new DOMSource(sig);
        final StringWriter xmlAsWriter = new StringWriter();
        final StreamResult result = new StreamResult(xmlAsWriter);
        TransformerFactory.newInstance().newTransformer().transform(source, result);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlAsWriter.toString().getBytes("UTF-8"));
        final String contentType = "application/xml; charset=UTF-8";
        final String fileName = "IHEXDSNAV-" + UUID.randomUUID() + ".xml";

        final DataSource ds = new DataSource() {

            @Override
            public String getContentType() {
                return contentType;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return inputStream;
            }

            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public OutputStream getOutputStream() throws IOException {
                throw new IllegalArgumentException("getOutputStream should not be called.");
            }

        };
        mbp2.setDataHandler(new DataHandler(ds));
        mbp2.setFileName(fileName);
        mbp2.setHeader("Content-Type", contentType);

        final Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);
        mp.addBodyPart(mbp2);
        msg.setContent(mp);
        msg.setSentDate(new Date());

        Transport.send(msg);
    }

    /**
     * @return the signatureBuilder
     */

    public XDSNotificationSignatureBuilder getSignatureBuilder() {

        return signatureBuilder;
    }

    /**
     * @param signatureBuilder the signatureBuilder to set
     */

    public void setSignatureBuilder(XDSNotificationSignatureBuilder signatureBuilder) {

        this.signatureBuilder = signatureBuilder;
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
    }

    /**
     * @return the subject
     */

    public String getSubject() {

        return subject;
    }

    /**
     * @param subject the subject to set
     */

    public void setSubject(String subject) {

        this.subject = subject;
    }

    /**
     * @return the to
     */

    public String getTo() {

        return to;
    }

    /**
     * @param to the to to set
     */

    public void setTo(String to) {

        this.to = to;
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
     * @return the instructions
     */

    public String getInstructions() {

        return instructions;
    }

    /**
     * @param instructions the instructions to set
     */

    public void setInstructions(String instructions) {

        this.instructions = instructions;
    }

}
