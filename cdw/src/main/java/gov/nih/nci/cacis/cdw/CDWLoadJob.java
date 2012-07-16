package gov.nih.nci.cacis.cdw;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CDWLoadJob {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cdwContext = new ClassPathXmlApplicationContext(
                "classpath:applicationContext-cacis-cdw.xml");
        CDWPendingLoader pendingLoader = (CDWPendingLoader) cdwContext.getBean("pendingLoader");
        CDWLoader loader = (CDWLoader) cdwContext.getBean("loader");
        pendingLoader.loadPendingCDWDocuments(loader);
    }

}
