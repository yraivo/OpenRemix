package remix.client;

import remix.client.ClientServices;
import remix.client.ClientStartupCallback;
import remix.client.RemixClient;

import java.io.Serializable;

public final class ClientShutdownRunnable implements Serializable, Runnable {
    @Override
    public void run() {
        ((ClientServices) RemixClient.INSTANCE.getClientServices()).getShutdownServiceHandle().activate();
    }

    public ClientShutdownRunnable(ClientStartupCallback clientStartupCallback) {
        this();
    }

    private ClientShutdownRunnable() {}
}
