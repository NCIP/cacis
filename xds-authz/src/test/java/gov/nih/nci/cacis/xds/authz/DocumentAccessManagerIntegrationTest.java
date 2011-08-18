package gov.nih.nci.cacis.xds.authz;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Runs the tests from super class
 * with postgres
 *
 * @author kherm manav.kher@semanticbits.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-xds-authz-test.xml")
public class DocumentAccessManagerIntegrationTest extends DocumentAccessManagerTest {
}
