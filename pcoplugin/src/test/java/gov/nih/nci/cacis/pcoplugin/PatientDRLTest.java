package gov.nih.nci.cacis.pcoplugin;

import org.apache.cxf.testutil.common.AbstractBusClientServerTestBase;
import org.drools.RuleBase;
import org.drools.RuleBaseConfiguration;
import org.drools.RuleBaseConfiguration.AssertBehaviour;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderConfiguration;
import org.drools.rule.Package;
import org.drools.rule.builder.dialect.java.JavaDialectConfiguration;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tolven.app.entity.AccountMenuStructure;
import org.tolven.app.entity.MSColumn;
import org.tolven.app.entity.MenuData;
import org.tolven.core.entity.Account;
import org.tolven.trim.Trim;
import org.tolven.trim.parse.ParseTRIM;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class PatientDRLTest extends AbstractBusClientServerTestBase {

    private AccountMenuStructure msPatient;
    private AccountMenuStructure msPatientList;


    @BeforeClass
    public static void startServers() {
        assertTrue("Could not launch server",
                launchServer(SemanticAdapterServer.class, true));
    }

    @Test
    public void patientRule() throws Exception {
        RuleBase ruleBase = openRuleBase("Patient-test.drl", AssertBehaviour.EQUALITY);

        //load up the rulebase
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assertFalse(classLoader == null);
        InputStream inputStream = classLoader.getResourceAsStream("patient.trim.xml");
        Trim patientTrim = new ParseTRIM().parseStream(inputStream);

        StatefulSession workingMemory = ruleBase.newStatefulSession();
        workingMemory.setGlobal("out", System.out);
        workingMemory.insert(SemanticAdapterServer.ADDRESS + "?wsdl");
        workingMemory.insert(patientTrim);

        Account account = new Account();
        account.setId(111);

//        List
        msPatientList = new AccountMenuStructure();
        msPatientList.setPath("echr:patients:all");
        msPatientList.setAccount(account);
        msPatientList.setRole("placeholder");
        msPatientList.setNode("patient");
        msPatientList.setPath("echr:patients:all");
        workingMemory.insert(msPatientList);

        // Patient MS
        msPatient = new AccountMenuStructure();
        msPatient.setAccount(account);
        msPatient.setRole("placeholder");
        msPatient.setNode("patient");
        msPatient.setPath("echr:patient");
        MSColumn firstNameColumn = new MSColumn();
        firstNameColumn.setInternal("string01");
        firstNameColumn.setHeading("lastName");
        msPatient.getColumns().add(firstNameColumn);
        workingMemory.insert(msPatient);

        // Patient MD
        MenuData mdPatient = new MenuData();
        mdPatient.setId(123);    // Fake id
        mdPatient.setMenuStructure(msPatient);
        mdPatient.setPath("Patient: Thompson");
        mdPatient.setString01("Thompson");
        mdPatient.setDate01(new Date());
        workingMemory.insert(mdPatient);


        workingMemory.fireAllRules();
        workingMemory.dispose();

        assertTrue("SA was not called as expecteD",
                MockShareClinicalDataWs.wasCalled);
    }


    public RuleBase openRuleBase(String ruleFile, AssertBehaviour assertBehaviour) throws Exception {
        //read in the source
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assertFalse(classLoader == null);
        InputStream inputStream = classLoader.getResourceAsStream(ruleFile);
        assertFalse(inputStream == null);
        Reader source = new InputStreamReader(inputStream);

        //optionally read in the DSL (if you are using it).
        //Reader dsl = new InputStreamReader( DroolsTest.class.getResourceAsStream( "/mylang.dsl" ) );

        //Use package builder to build up a rule package.
        //An alternative lower level class called "DrlParser" can also be used...
        PackageBuilderConfiguration conf = new PackageBuilderConfiguration();
        JavaDialectConfiguration javaConf = (JavaDialectConfiguration) conf.getDialectConfiguration("java");
        javaConf.setJavaLanguageLevel("1.5");
        PackageBuilder builder = new PackageBuilder(conf);


        //this wil parse and compile in one step
        //NOTE: There are 2 methods here, the one argument one is for normal DRL.
        builder.addPackageFromDrl(source);

        //Use the following instead of above if you are using a DSL:
        //builder.addPackageFromDrl( source, dsl );

        //get the compiled package (which is serializable)
        Package pkg = builder.getPackage();

        RuleBaseConfiguration confRuleBase = new RuleBaseConfiguration();
        confRuleBase.setAssertBehaviour(assertBehaviour);

        //add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase(confRuleBase);

        ruleBase.addPackage(pkg);
        return ruleBase;
    }


}
