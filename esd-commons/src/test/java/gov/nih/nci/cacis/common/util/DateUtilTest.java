package gov.nih.nci.cacis.common.util;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertNotNull;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class DateUtilTest {

    /**
     * Test the getTimeFromDate method
     */
    @Test
    public void getTimeFromDate() {
        assertNotNull(DateUtil.getTimeFromDate(Calendar.getInstance().getTime()));
    }
}
