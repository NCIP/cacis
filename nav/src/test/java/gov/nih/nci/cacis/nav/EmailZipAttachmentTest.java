package gov.nih.nci.cacis.nav;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

public class EmailZipAttachmentTest {

    @Test
    public void testCreateMessage() {
        try {
            AbstractSendMail abstractSendMail = new AbstractSendMail(new Properties(), "cacisdevsender@gmail.com", "smtp.gmail.com", 587, "smtp");
            abstractSendMail.secEmailTempZipLocation = "C:/Users/ajay/.cacis/nav";
            abstractSendMail.createMessage("cacisdevsecureemailrecipient@gmail.com", "CLINICAL_NOTE", "RIMITS",
                    "Instructions", "<content>sample</content>", "<content>metadata</content>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // fail("Not yet implemented");
    }

}
