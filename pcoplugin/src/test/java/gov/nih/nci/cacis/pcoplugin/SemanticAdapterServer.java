package gov.nih.nci.cacis.pcoplugin;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.testutil.common.AbstractBusTestServerBase;

import javax.xml.ws.Endpoint;

public class SemanticAdapterServer extends AbstractBusTestServerBase {

    public static final String ADDRESS = "http://localhost:8178/ShareClinicalData";

    @Override
    protected void run() {


        final Object serviceBean = new MockShareClinicalDataWs();
        final Bus bus = new SpringBusFactory().createBus();
        SpringBusFactory.setDefaultBus(bus);

        bus.getOutInterceptors().add(new LoggingOutInterceptor());
        bus.getInInterceptors().add(new LoggingInInterceptor());

        final EndpointImpl ep = (EndpointImpl) Endpoint.create(serviceBean);
        ep.publish(ADDRESS);
    }
}
