package remix.client;

import remix.compat.legacy.auth.ClientAuthCoordinator;
import remix.client.ClientServicesProvider;
import remix.compat.legacy.http.DualEndpointProfileFetcher;
import remix.event.InputType;
import remix.compat.legacy.security.SecurityManagerRegistry;
import remix.input.KeyAction;

public abstract class AbstractClientBootstrap<T extends AbstractClientServices> {
    private SecurityManagerRegistry securityManagerRegistry;
    private ClientAuthCoordinator clientAuthCoordinator;
    private static int[] legacyBootstrapMarker;
    private DualEndpointProfileFetcher dualEndpointProfileFetcher;
    private ClientServicesProvider<T> clientServicesProvider;

    public static int[] getLegacyBootstrapMarker() {
        return remix.client.AbstractClientBootstrap.legacyBootstrapMarker;
    }

    public DualEndpointProfileFetcher getDualEndpointProfileFetcher() {
        return this.dualEndpointProfileFetcher;
    }

    public abstract void handleLegacyLicenseFailure();

    protected AbstractClientBootstrap(
            ClientServicesProvider<T> bONValue,
            ClientAuthCoordinator bOLValue,
            DualEndpointProfileFetcher bOMValue,
            SecurityManagerRegistry bOKValue) {
        this.clientAuthCoordinator = bOLValue;
        this.dualEndpointProfileFetcher = bOMValue;
        this.securityManagerRegistry = bOKValue;
        this.clientServicesProvider = bONValue;
    }

    static {
        getLegacyBootstrapMarker();
    }

    public ClientAuthCoordinator getClientAuthCoordinator() {
        return this.clientAuthCoordinator;
    }

    public void startClient() {
        this.getClientServices().getStartupServiceHandle().activate().executeLifecycleEvent();
    }

    public void stopClient() {
        this.getClientServices().getShutdownServiceHandle().activate().executeLifecycleEvent();
    }

    public abstract boolean handleKeyAction(
            KeyAction keyAction, InputType inputType, int i);

    public SecurityManagerRegistry getSecurityManagerRegistry() {
        return this.securityManagerRegistry;
    }

    public static void setLegacyBootstrapMarker(int[] aint) {
        remix.client.AbstractClientBootstrap.legacyBootstrapMarker = aint;
    }

    public T getClientServices() {
        return (T) this.clientServicesProvider.getAbstractClientServices();
    }

    public abstract void handleLegacyRemoteLicenseCallback(String text);
}
