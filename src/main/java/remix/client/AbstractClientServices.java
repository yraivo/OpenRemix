package remix.client;

import remix.client.AbstractGameDirectoryProvider;
import remix.client.ClientMetadata;
import remix.compat.legacy.auth.LicenseProfileRecord;
import remix.compat.legacy.auth.LicenseSessionVerifier;
import remix.compat.legacy.lifecycle.ShutdownServiceHandle;
import remix.compat.legacy.lifecycle.StartupServiceHandle;

import java.io.Serializable;

public abstract class AbstractClientServices implements Serializable {
    private static int[] legacyServiceBootstrapMarker;

    public static void setLegacyServiceBootstrapMarker(int[] aint) {
        remix.client.AbstractClientServices.legacyServiceBootstrapMarker = aint;
    }

    static {
        getLegacyServiceBootstrapMarker();
    }

    public abstract ShutdownServiceHandle getShutdownServiceHandle();

    public abstract StartupServiceHandle getStartupServiceHandle();

    public abstract LicenseProfileRecord getVerifiedLicenseProfile();

    public abstract LicenseSessionVerifier getLicenseSessionVerifier();

    public static int[] getLegacyServiceBootstrapMarker() {
        return remix.client.AbstractClientServices.legacyServiceBootstrapMarker;
    }

    public abstract ClientMetadata getMetadata();

    public abstract AbstractGameDirectoryProvider getGameDirectoryProvider();
}
