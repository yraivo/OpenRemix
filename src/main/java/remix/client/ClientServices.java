package remix.client;

import net.minecraft.client.MinecraftClient;
import remix.client.AbstractGameDirectoryProvider;
import remix.ui.account.AltLoginManager;
import remix.ui.account.AltServiceSwitcher;
import remix.compat.legacy.auth.AuthVerificationOutcome;
import remix.client.ClientGameDirectoryProvider;
import remix.client.ClientMetadata;
import remix.compat.legacy.lifecycle.DefaultShutdownServiceHandle;
import remix.compat.legacy.lifecycle.DefaultStartupServiceHandle;
import remix.client.friend.FriendManager;
import remix.check.HackerCheckManager;
import remix.compat.legacy.auth.LicenseProfileRecord;
import remix.compat.legacy.auth.LicenseSessionVerifier;
import remix.client.lifecycle.LifecycleCallback;
import remix.util.network.NordVpnProxyManager;
import remix.util.network.PortScanner;
import remix.client.RemixClientMetadata;
import remix.compat.legacy.lifecycle.ShutdownServiceHandle;
import remix.compat.legacy.lifecycle.StartupServiceHandle;
import remix.command.ClientCommandManager;
import remix.config.ConfigFileManager;
import remix.module.ModuleManager;
import remix.setting.SettingRegistry;
import remix.ui.clickgui.ClickGuiScreen;

import java.io.File;
import java.io.Serializable;

public class ClientServices extends AbstractClientServices implements Serializable {

    public static String CLIENT_VERSION;
    private static String[] packedTextFragments;
    public String commandPrefix;
    public static ModuleManager moduleManager;
    public static SettingRegistry settingRegistry;
    private StartupServiceHandle startupServiceHandle;
    private ShutdownServiceHandle shutdownServiceHandle;
    private final LifecycleCallback startupLifecycleCallback = new ClientStartupCallback(this);
    private ClientMetadata metadata;

    public static ConfigFileManager configFileManager;
    public static AltServiceSwitcher altServiceSwitcher;
    public static String CLIENT_NAME;
    private AbstractGameDirectoryProvider gameDirectoryProvider;
    public static PortScanner portScanner;
    private LicenseSessionVerifier licenseSessionVerifier;
    private LifecycleCallback shutdownLifecycleCallback;
    private int themeColor;
    public static ClickGuiScreen clickGui;
    public static NordVpnProxyManager nordVpnProxyManager;
    public static AltLoginManager altLoginManager;
    public static FriendManager friendManager;
    public static HackerCheckManager hackerCheckManager;
    private static int[] legacyServiceMarker;
    public static ClientCommandManager commands;

    public static void setLegacyServiceMarker(int[] aint) {
        remix.client.ClientServices.legacyServiceMarker = aint;
    }

    // region String decoding support

    // endregion

    public static int[] getLegacyServiceMarker() {
        return remix.client.ClientServices.legacyServiceMarker;
    }

    ClientServices() {
        this.commandPrefix = packedTextFragments[0];
        this.shutdownLifecycleCallback = new ClientShutdownCallback(this);
        this.themeColor = -13710223;
        this.shutdownServiceHandle = new DefaultShutdownServiceHandle(this.shutdownLifecycleCallback);
        this.startupServiceHandle = new DefaultStartupServiceHandle(this.startupLifecycleCallback);
        this.metadata =
                new RemixClientMetadata(
                        this, remix.client.ClientServices.CLIENT_NAME, "1.7.1", new String[] {"Mees", "Vladymyr", "Aidan"});
        this.gameDirectoryProvider =
                new ClientGameDirectoryProvider(
                        this,
                        new File(
                                MinecraftClient.getMinecraft().gameDirectory,
                                this.metadata.getName()));
        this.licenseSessionVerifier = new LicenseSessionVerifier(this);
    }

    public ShutdownServiceHandle getShutdownServiceHandle() {
        return this.shutdownServiceHandle;
    }

    public LicenseSessionVerifier getLicenseSessionVerifier() {
        return this.licenseSessionVerifier;
    }

    public void setThemeColor(int i) {
        this.themeColor = i;
    }

    public LicenseProfileRecord getVerifiedLicenseProfile() {
        return this.licenseSessionVerifier.getAuthVerificationOutcome() != AuthVerificationOutcome.SUCCESS ? null : this.licenseSessionVerifier.getLicenseProfileRecord();
    }

    // region String decoding support

    // endregion

    public ClientMetadata getMetadata() {
        return this.metadata;
    }

    // region String decoding support

    // endregion

    public AbstractGameDirectoryProvider getGameDirectoryProvider() {
        return this.gameDirectoryProvider;
    }

    // region Static initialization and string tables
    static {
        remix.client.ClientServices.packedTextFragments = new String[3];
        remix.client.ClientServices.packedTextFragments[0] = new String(".");
        setLegacyServiceMarker(null);
        remix.client.ClientServices.CLIENT_VERSION = "1.7.1";
        remix.client.ClientServices.CLIENT_NAME = new String("Remix");
    }

    // endregion

    public StartupServiceHandle getStartupServiceHandle() {
        return this.startupServiceHandle;
    }

    public int getThemeColor() {
        return this.themeColor;
    }
}
