package remix.client;

import remix.client.AbstractClientServices;

public interface ClientServicesProvider<T extends AbstractClientServices> {
    T getAbstractClientServices();
}
