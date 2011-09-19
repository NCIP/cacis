/**
 * The software subject to this notice and license includes both human readable source code form and machine readable,
 * binary, object code form. The gov.nih.nci.cacis.cdw-authz-1.0 Software was developed in conjunction with the National
 * Cancer Institute (NCI) by NCI employees and subcontracted parties. To the extent government employees are authors,
 * any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 * 
 * This gov.nih.nci.cacis.cdw-authz-1.0 Software License (the License) is between NCI and You. You (or Your) shall mean
 * a person or an entity, and all other entities that control, are controlled by, or are under common control with the
 * entity. Control for purposes of this definition means (i) the direct or indirect power to cause the direction or
 * management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the
 * outstanding shares, or (iii) beneficial ownership of such entity.
 * 
 * This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its
 * rights in the gov.nih.nci.cacis.cdw-authz-1.0 Software to (i) use, install, access, operate, execute, copy, modify,
 * translate, market, publicly display, publicly perform, and prepare derivative works of the
 * gov.nih.nci.cacis.cdw-authz-1.0 Software; (ii) distribute and have distributed to and by third parties the
 * gov.nih.nci.cacis.cdw-authz-1.0 Software and any modifications and derivative works thereof; and (iii) sublicense the
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

package gov.nih.nci.cacis.nav.config;

import gov.nih.nci.cacis.common.doc.DocumentHandler;
import gov.nih.nci.cacis.nav.DefaultXDSNotificationSignatureBuilder;
import gov.nih.nci.cacis.nav.NotificationSender;
import gov.nih.nci.cacis.nav.NotificationSenderImpl;
import gov.nih.nci.cacis.nav.OpenXDSDocumentResolver;
import gov.nih.nci.cacis.nav.SendEncryptedMail;
import gov.nih.nci.cacis.nav.SendSignedMail;
import gov.nih.nci.cacis.nav.XDSDocumentResolver;
import gov.nih.nci.cacis.nav.XDSNotificationSignatureBuilder;

import java.security.KeyStoreException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

/**
 * Spring config for NAV.
 * 
 * @author bpickeral
 * @since Aug 2, 2011
 */
@Configuration
public class NAVTestConfig {

    @Value("${xds.repo.oid}")
    private String repoOID;

    @Autowired
    @Qualifier("wrapperDocumentHandler")
    private DocumentHandler docHndlr;

    @Value("${nav.keystore.type}")
    private String keyStoreType;

    @Value("${nav.keystore.location}")
    private String keyStoreLocation;

    @Value("${nav.keystore.password}")
    private String keyStorePassword;

    @Value("${nav.keystore.key}")
    private String keyStoreKey;

    @Value("${nav.message.instructions}")
    private String instructions;

    @Value("${nav.message.subject}")
    private String subject;

    @Value("${nav.message.from}")
    private String mailFrom;

    @Value("${sec.email.keystore.location}")
    private String secEmailKeyStoreLocation;

    @Value("${sec.email.keystore.password}")
    private String secEmailKeyStorePassword;
    
    //email address is being used as keyalias
    @Value("${sec.email.message.from}")
    private String secEmailKeyStoreKey;

    @Value("${sec.email.truststore.location}")
    private String secEmailTrustStoreLocation;

    @Value("${sec.email.truststore.password}")
    private String secEmailTrustStorePassword;

    @Value("${sec.email.message.from}")
    private String secEmailFrom;

    @Value("${sec.email.sender.host}")
    private String secEmailHost;

    @Value("${sec.email.sender.port}")
    private int secEmailPort;

    @Value("${sec.email.sender.protocol}")
    private String secEmailProtocol;

    @Value("${sec.email.sender.user}")
    private String secEmailUser;

    @Value("${sec.email.sender.pass}")
    private String secEmailPass;

    /**
     * GreenMail Bean.
     * 
     * @return server
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public GreenMail server() {
        return new GreenMail(new ServerSetup(3125, null, ServerSetup.PROTOCOL_SMTP));
    }

    /**
     * Notification Sender.
     * 
     * @return notification sender
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public NotificationSender notificationSender() {
        final NotificationSenderImpl notificationSender = new NotificationSenderImpl(null, null, null, null, "", null,
                0, null);

        notificationSender.setSignatureBuilder(signatureBuilder());
        notificationSender.setHost(server().getSmtp().getBindTo());
        notificationSender.setPort(server().getSmtp().getPort());
        notificationSender.setProtocol(server().getSmtp().getProtocol());
        notificationSender.setInstructions(instructions);
        notificationSender.setSubject(subject);
        notificationSender.setFrom(mailFrom);
        notificationSender.setMailProperties(new Properties());

        // username will be set sending time

        return notificationSender;
    }

    /**
     * Notification Signature builder.
     * 
     * @return notification signature builder
     */
    @Bean
    public XDSNotificationSignatureBuilder signatureBuilder() {
        return new DefaultXDSNotificationSignatureBuilder(documentResolver(), SignatureMethod.RSA_SHA1,
                DigestMethod.SHA1, keyStoreType, keyStoreLocation, keyStorePassword, keyStoreKey);
    }

    /**
     * XDS Document Resolver
     * 
     * @return notification sender
     */
    @Bean
    public XDSDocumentResolver documentResolver() {
        return new OpenXDSDocumentResolver(repoOID, docHndlr);
    }

    /**
     * Secure signed mail Sender.
     * 
     * @return SendSignedMail sender
     * @throws MessagingException  exception thrown, if any
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SendSignedMail signedMailSender() throws MessagingException {
        final SendSignedMail sender = new SendSignedMail(new Properties(), secEmailFrom, 
                secEmailHost, secEmailPort, secEmailProtocol, secEmailKeyStoreLocation,
                secEmailKeyStorePassword, secEmailKeyStoreKey);
        sender.setLoginDetails(secEmailUser, secEmailPass);

        return sender;
    }
    
    /**
     * Secure encrypted mail Sender.
     * 
     * @return SendEncryptedMail sender
     * @throws MessagingException  exception thrown, if any
     * @throws KeyStoreException  exception thrown, if any
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public SendEncryptedMail encryptedMailSender() throws MessagingException, KeyStoreException {
        final Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        final SendEncryptedMail sender = new SendEncryptedMail(props, secEmailFrom, 
                secEmailHost, secEmailPort, secEmailProtocol, secEmailTrustStoreLocation,
                secEmailTrustStorePassword);
        sender.setLoginDetails(secEmailUser, secEmailPass);

        return sender;
    }

}
