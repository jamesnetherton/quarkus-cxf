package io.quarkiverse.cxf.transport;

import java.io.IOException;

import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.SoapTransportFactory;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

public class VertxDestinationFactory extends SoapTransportFactory implements DestinationFactory {

    public VertxDestinationFactory() {
        super();
    }

    @Override
    public Destination getDestination(EndpointInfo endpointInfo, Bus bus) throws IOException {
        if (endpointInfo == null) {
            throw new IllegalArgumentException("EndpointInfo cannot be null");
        }
        synchronized (DestinationRegistryFactory.INSTANCE) {
            AbstractHTTPDestination d = DestinationRegistryFactory.INSTANCE.getDestinationForPath(endpointInfo.getAddress());
            if (d == null) {
                d = new VertxDestination(endpointInfo, bus, DestinationRegistryFactory.INSTANCE);
                DestinationRegistryFactory.INSTANCE.addDestination(d);
                d.finalizeConfig();
            }
            return d;
        }
    }
}
