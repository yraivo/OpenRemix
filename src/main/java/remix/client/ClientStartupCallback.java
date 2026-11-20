package remix.client;

import remix.client.ClientShutdownRunnable;
import remix.client.lifecycle.LifecycleCallback;
import remix.config.ConfigFileManager;

public class ClientStartupCallback implements LifecycleCallback {
    ClientServices clientServices;

    ClientStartupCallback(ClientServices clientServices) {
        this.clientServices = clientServices;
    }

    public void executeLifecycleEvent() {
        Runtime.getRuntime().addShutdownHook(new Thread(new ClientShutdownRunnable(null)));
        ConfigFileManager configs = ClientServices.configFileManager;
        if (configs != null) {
            configs.loadConfigs();
        }
    }
}
