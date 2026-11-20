package remix.client;

import remix.client.lifecycle.LifecycleCallback;
import remix.config.ConfigFileManager;

class ClientShutdownCallback implements LifecycleCallback {
    ClientServices clientServices;

    public void executeLifecycleEvent() {
        ConfigFileManager configs = ClientServices.configFileManager;
        if (configs != null) {
            configs.saveConfigs();
        }

        RemixClient.uidFileStore.writeLicenseUidIfMissing();
    }

    ClientShutdownCallback(ClientServices clientServices) {
        this.clientServices = clientServices;
    }
}
