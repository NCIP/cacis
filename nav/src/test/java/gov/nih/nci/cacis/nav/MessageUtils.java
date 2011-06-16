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

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Utility class to create sample messages
 * @author sb-admin-cp
 *
 */

public final class MessageUtils {
    
    private static final int SMTP_PORT = 3025;
    private static final int POP3_PORT = 3110;
    
    /**
     * private constructor
     */
    private MessageUtils() {
        //empty constructor
    }
    
    /**
     * Creates sample smtp message
     * @return MimeMessage instance 
     * @throws Exception - error thrown
     */
    public static MimeMessage createSMTPMessage() throws Exception { // NOPMD

        final String from = "some.one@somewhere.com";
        final String to = "another.one@somewhere.com";
        final String subject = "Notification of Document Availability";

        final Properties mailProps = new Properties();        
        mailProps.setProperty("mail.smtp.host", "localhost");
        mailProps.setProperty("mail.smtp.port", String.valueOf(SMTP_PORT));
        mailProps.setProperty("mail.smtp.sendpartial", "true");
        
        final Session session = Session.getInstance(mailProps, null);
        session.setDebug(true);

        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

        msg.setContent(getMultipart());
        msg.setSentDate(new Date());

        return msg;
    }
    
    /**
     * Creates sample POP3 message
     * @return MimeMessage instance 
     * @throws Exception - error thrown
     */
    public static MimeMessage createPOP3Message() throws Exception { // NOPMD

        final Properties mailProps = new Properties();        
        mailProps.setProperty("mail.pop3.port", String.valueOf(POP3_PORT));
        
        final Session session = Session.getInstance(mailProps, null);
        session.setDebug(true);
        
        final String mailbox = "another.one@somewhere.com";
        final String recipient = "some.one@somewhere.com";
        final String subject = "Notification of Document Availability";
        
        // Construct a message
        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailbox));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        
        msg.setContent(getMultipart());
        msg.setSentDate(new Date());

        return msg;
    }
    
    private static Multipart getMultipart() throws Exception { //NOPMD
        final Multipart mp = new MimeMultipart();
        mp.addBodyPart(getTextBodypart());
        mp.addBodyPart(getAttachmentBodypart());
        
        return mp;
    }
    
    private static MimeBodyPart getTextBodypart() throws Exception { //NOPMD
        final MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText("Instructions to the user.");
        mbp1.setHeader("Content-Type", "text/plain");
        
        return mbp1;
    }
    
    private static MimeBodyPart getAttachmentBodypart() throws Exception { //NOPMD
        final MimeBodyPart mbp2 = new MimeBodyPart();
        final ClassLoader cl = TestMail.class.getClassLoader();
        final URLDataSource ds = new URLDataSource(cl.getResource("notification_gen.xml"));
        mbp2.setDataHandler(new DataHandler(ds));
        mbp2.setFileName("IHEXDSNAV-" + UUID.randomUUID() + ".xml");
        mbp2.setHeader("Content-Type", "application/xml; charset=UTF-8");
        
        return mbp2;
    }

    
}
