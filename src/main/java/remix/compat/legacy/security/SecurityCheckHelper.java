package remix.compat.legacy.security;

import remix.check.HackerCheckManager;
import remix.client.friend.FriendManager;
import remix.ui.account.AltLoginManager;
import remix.ui.account.AltServiceSwitcher;
import remix.ui.creative.CustomPotionsCreativeTab;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;
import remix.util.network.NordVpnProxyManager;
import remix.util.network.PortScanner;
import remix.util.reflection.ReflectiveClassWrapper;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.command.ClientCommandManager;
import remix.config.ConfigFileManager;
import remix.input.KeyBindingDispatcher;
import remix.module.ModuleManager;
import remix.setting.SettingRegistry;
import remix.ui.clickgui.ClickGuiScreen;

import org.lwjgl.opengl.Display;

import java.util.Optional;

public enum SecurityCheckHelper {
    bYy;

    public static ReflectiveClassWrapper<?> bYA;
    public static ReflectiveClassWrapper<?> clientServicesClass;
    public boolean lookupPerformed;
    public static ReflectiveClassWrapper<?> remixClientClass;
    private static FluentMapWrapper classesByLegacyId;

    // region String decoding support

    // endregion

    static {
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId = FluentCollections.newMutableMap();
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                10927, remix.compat.legacy.security.SecurityCheckHelper.clientServicesClass = new ReflectiveClassWrapper(ClientServices.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                25523387, remix.compat.legacy.security.SecurityCheckHelper.remixClientClass = new ReflectiveClassWrapper(RemixClient.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                239075, new ReflectiveClassWrapper(ModuleManager.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                690273, new ReflectiveClassWrapper(ClientCommandManager.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                473092578, new ReflectiveClassWrapper(FriendManager.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                98799793, new ReflectiveClassWrapper(ConfigFileManager.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                890632321, new ReflectiveClassWrapper(HackerCheckManager.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                578, new ReflectiveClassWrapper(NordVpnProxyManager.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                39423, new ReflectiveClassWrapper(AltServiceSwitcher.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                8364591, new ReflectiveClassWrapper(SettingRegistry.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                739457204, new ReflectiveClassWrapper(KeyBindingDispatcher.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(35289129, new ReflectiveClassWrapper(Display.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                92640, new ReflectiveClassWrapper(CustomPotionsCreativeTab.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                826236236, new ReflectiveClassWrapper(AltLoginManager.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                29836752, new ReflectiveClassWrapper(ClickGuiScreen.class));
        remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.putEntry(
                498156, new ReflectiveClassWrapper(PortScanner.class));
    }

    public Optional<ReflectiveClassWrapper<?>> findClassByLegacyId(Integer integer) {
        this.lookupPerformed = true;
        return remix.compat.legacy.security.SecurityCheckHelper.classesByLegacyId.findValueByKey(integer);
    }

    // region String decoding support

    // endregion

    private SecurityCheckHelper() {}
}
