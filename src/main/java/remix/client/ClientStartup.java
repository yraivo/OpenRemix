package remix.client;

import net.minecraft.client.MinecraftClient;
import remix.ui.account.AltLoginManager;
import remix.ui.account.AltServiceSwitcher;
import remix.client.friend.FriendManager;
import net.minecraft.client.gui.GuiScreen;
import remix.check.HackerCheckManager;
import remix.util.network.NordVpnProxyManager;
import remix.util.network.PortScanner;
import remix.command.ClientCommandManager;
import remix.config.ConfigFileManager;
import remix.input.KeyBindingDispatcher;
import remix.module.ModuleManager;
import remix.setting.SettingRegistry;
import remix.ui.clickgui.ClickGuiScreen;
import remix.ui.screen.RemixMainMenuScreen;

/**
 * Direct typed startup assembly for Remix 1.7.1.
 * Assembles ClientServices singletons, event dispatchers, and configuration
 * without any reflection or string-based lookup.
 */
public final class ClientStartup {
    private static boolean initialized;

    private ClientStartup() {}

    public static synchronized void enter(GuiScreen screen) {
        if (!initialized) {
            // 1. Initialize ClientServices static singletons in canonical order
            ClientServices.settingRegistry = SettingRegistry.create();
            ClientServices.altLoginManager = AltLoginManager.create();
            ClientServices.friendManager = FriendManager.create();
            ClientServices.nordVpnProxyManager = NordVpnProxyManager.create();
            ClientServices.hackerCheckManager = HackerCheckManager.create();
            ClientServices.altServiceSwitcher = AltServiceSwitcher.create();
            ClientServices.portScanner = PortScanner.create();
            ClientServices.moduleManager = ModuleManager.create();
            ClientServices.commands = ClientCommandManager.create();
            ClientServices.clickGui = new ClickGuiScreen();
            ClientServices.configFileManager = ConfigFileManager.create();

            // 2. Register key binding dispatcher to event bus and load configs
            KeyBindingDispatcher.create(RemixClient.eventBus);
            System.out.println("LOCAL171_KEY_BINDINGS_READY");

            ClientServices.configFileManager.loadConfigs();
            initialized = true;

            System.out.println("LOCAL171_MODULES:" + ModuleManager.modules.size());
        }

        // 3. Display main menu screen
        MinecraftClient mc = MinecraftClient.getMinecraft();
        RemixMainMenuScreen menu = new RemixMainMenuScreen(RemixClient.INSTANCE);
        mc.displayGuiScreen(menu);
        System.out.println("LOCAL171_MENU:" + menu.getClass().getName());
    }
}
