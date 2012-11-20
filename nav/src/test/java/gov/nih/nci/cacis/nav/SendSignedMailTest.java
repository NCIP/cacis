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
package gov.nih.nci.cacis.nav;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

/**
 * Tests sending/receiving mail
 * 
 * @author joshua.phillips@semanticbits.com
 * @author vinodh.rc@semanticbits.com
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-nav-test.xml" })
public class SendSignedMailTest {

    private static final String INBOX = "INBOX";

    private static final String POP3_TYPE = "pop3";

    private static final String MAIL_POP3_PORT_KEY = "mail.pop3.port";

    private static final String UNEXPECTED_EXCEPTION = "Unexpected exception: ";

    private static final Logger LOG = Logger.getLogger(TestMail.class);

    private static final String LOCALHOST = "localhost";
    private static final int SMTP_PORT = 3025;
    private static final int POP3_PORT = 3110;

    private GreenMail server;

    @Value("${sec.email.keystore.location}")
    private String secEmailKeyStoreLocation;

    @Value("${sec.email.keystore.password}")
    private String secEmailKeyStorePassword;

    // Using the from email address to get the matching key
    @Value("${sec.email.message.from}")
    private String secEmailKeyStoreKey;

    @Value("${sec.email.truststore.location}")
    private String secEmailTrustStoreLocation;

    @Value("${sec.email.truststore.password}")
    private String secEmailTrustStorePassword;

    @Value("${sec.email.message.from}")
    private String secEmailFrom;

    // Using the same email address for from and to
    // as the test truststore has key for the from address only
    @Value("${sec.email.message.from}")
    private String secEmailTo;

    @Value("${sec.email.sender.user}")
    private String secEmailUser;

    @Value("${sec.email.sender.pass}")
    private String secEmailPass;
    
    @Autowired
    private SendSignedMail sender;
    
    @Autowired
    private SendEncryptedMail encSender;

    /**
     * starts GreenMain server for testing
     */
    @Before
    public void setUp() {
        server = new GreenMail(ServerSetupTest.ALL);
        // for some reason, the greenmail server doesnt start in time
        // will attempt max times to ensure all service gets startedup
        int i = 0;
        final int max = 2;
        while (i < max) {
            try {
                server.start();
                // CHECKSTYLE:OFF
            } catch (RuntimeException e) { // NOPMD
                // CHECKSTYLE:ON
                i++;
                continue;
            }
            i = max;
        }
    }

    /**
     * stops GreenMain server after testing
     */
    @After
    public void tearDown() {
        server.stop();
    }

    /**
     * tests error on invalid keystore
     * 
     * @throws MessagingException - exception thrown
     */
    @Test(expected = MessagingException.class)
    public void supplyInvalidKeystore() throws MessagingException {
        final SendSignedMail ssem = new SendSignedMail(new Properties(), secEmailFrom, "invalid keystore",
                secEmailKeyStorePassword, secEmailKeyStoreKey);
        // shouldnt reach this line
        ssem.signMail(null);
    }
    
    /**
     * send to gmail
     * @throws MessagingException exception thrown
     */
    @Test
    public void sendSignedAndEncryptedMailToGmail() throws MessagingException {        
        final MimeMessage msg = sender.createMessage(secEmailTo, "Clinical Note", "subj", "inst", "content", "metadata");
        final MimeMessage signedMsg = sender.signMail(msg);
        final MimeMessage encMsg = encSender.encryptMail(signedMsg, secEmailTo);
        sender.sendMail(encMsg);
    }

    /**
     * tests receiving signed mail
     * 
     * @throws IOException - io exception thrown
     */
    @Test
    public void receiveSignedMessage() throws IOException {
        try {

            final MimeMessage msg = createMessage();
            // TODO:fix sample certificate for email address
            msg.setFrom(new InternetAddress(secEmailFrom));

            final Properties smtpprops = new Properties();
            smtpprops.put("mail.smtp.auth", "true");
            smtpprops.put("mail.smtp.starttls.enable", "true");

            final SendSignedMail ssem = new SendSignedMail(smtpprops, secEmailFrom, secEmailKeyStoreLocation,
                    secEmailKeyStorePassword, secEmailKeyStoreKey);

            final MimeMessage signedMsg = ssem.signMail(msg);

            final GreenMailUser user = server.setUser(secEmailTo, secEmailUser, secEmailPass);
            user.deliver(signedMsg);
            assertEquals(1, server.getReceivedMessages().length);

            final Properties props = new Properties();
            props.setProperty(MAIL_POP3_PORT_KEY, String.valueOf(POP3_PORT));
            final Session session = Session.getInstance(props, null);
            session.setDebug(true);

            final Store store = session.getStore(POP3_TYPE);
            store.connect("localhost", secEmailUser, secEmailPass);
            final Folder folder = store.getFolder(INBOX);
            folder.open(Folder.READ_ONLY);

            final Message[] messages = folder.getMessages();
            assertTrue(messages != null);
            assertTrue(messages.length == 1);

            final MimeMessage retMsg = (MimeMessage) messages[0];

            final ValidateSignedMail vsm = new ValidateSignedMail(false);
            // TO email public key is tied to the email address itself, so using it as keyalias
            vsm.validate(retMsg, secEmailTrustStoreLocation, secEmailTrustStorePassword, secEmailTo);

            final Multipart mp = (Multipart) retMsg.getContent();
            assertTrue(mp.getCount() == 2);

            final Multipart origMsgMp = (Multipart) msg.getContent();

            final Part actualMsgPart = mp.getBodyPart(0);
            final Multipart actualMsgMp = (Multipart) actualMsgPart.getContent();

            validateMsgParts(origMsgMp, actualMsgMp);

            // CHECKSTYLE:OFF
        } catch (Exception e) { // NOPMD
            // CHECKSTYLE:ON
            fail(UNEXPECTED_EXCEPTION + e);
        }
    }

    private void validateMsgParts(Multipart origMsgMp, Multipart actualMsgMp) {
        try {
            final String textpart = (String) actualMsgMp.getBodyPart(0).getContent();
            assertNotNull(textpart);
            assertEquals((String) origMsgMp.getBodyPart(0).getContent(), textpart);

            final String attachPart = getPartContent(actualMsgMp.getBodyPart(1));
            assertNotNull(attachPart);
            assertEquals(getPartContent(origMsgMp.getBodyPart(1)), attachPart);
        } catch (IOException e) {
            fail(UNEXPECTED_EXCEPTION + e);
        } catch (MessagingException e) {
            fail(UNEXPECTED_EXCEPTION + e);
        }
    }

    private String getPartContent(Part part) {
        BufferedReader reader = null;
        String partMsg = null;
        try {
            final Writer writer = new StringWriter();
            final char[] buffer = new char[1024];
            reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
            int n = -1;
            while ((n = reader.read(buffer)) != -1) { // NOPMD
                writer.write(buffer, 0, n);
            }
            partMsg = writer.toString();
            // CHECKSTYLE:OFF
        } catch (Exception e) { // NOPMD
            // CHECKSTYLE:ON
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.error("Error closing BufferedReader!");
                }
            }
        }
        return partMsg;
    }

    private MimeMessage createMessage() throws Exception { // NOPMD

        final String subject = "Notification of Document Availability";

        final Properties mailProps = new Properties();
        final Session session = Session.getInstance(mailProps, null);
        mailProps.setProperty("mail.smtp.host", LOCALHOST);
        mailProps.setProperty("mail.smtp.port", String.valueOf(SMTP_PORT));
        mailProps.setProperty("mail.smtp.sendpartial", "true");
        session.setDebug(true);

        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(secEmailFrom));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(secEmailTo));

        final MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText("Instructions to the user.");
        mbp1.setHeader("Content-Type", "text/plain");

        final MimeBodyPart mbp2 = new MimeBodyPart();
        final ClassLoader cl = TestMail.class.getClassLoader();
        final URLDataSource ds = new URLDataSource(cl.getResource("purchase_order.xml"));

        mbp2.setDataHandler(new DataHandler(ds));
        mbp2.setFileName("IHEXDSNAV-" + UUID.randomUUID() + ".xml");
        mbp2.setHeader("Content-Type", "application/xml; charset=UTF-8");

        final Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);
        mp.addBodyPart(mbp2);

        msg.setContent(mp);
        msg.setSentDate(new Date());

        return msg;
    }

}
