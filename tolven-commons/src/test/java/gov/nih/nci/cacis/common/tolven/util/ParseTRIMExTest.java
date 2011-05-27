package gov.nih.nci.cacis.common.tolven.util;

import org.junit.Before;
import org.junit.Test;
import org.tolven.trim.Trim;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class ParseTRIMExTest {

    ParseTRIMEx parseTRIMEx;

    @Before
    public void setUp() {
        parseTRIMEx = new ParseTRIMEx();

    }

    @Test
    public void marshallToString() throws Exception {
        InputStream trimIS = getClass().getClassLoader().getResourceAsStream("sample-cacisOrganization.trim.xml");
        Trim trim = parseTRIMEx.parseStream(trimIS);

        String trimStr = parseTRIMEx.marshallToString(trim);
        assertNotNull(trimStr);
    }
}
