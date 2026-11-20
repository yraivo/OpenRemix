package remix.module.world;

import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import remix.server.hypixel.HypixelLocationData;
import remix.server.hypixel.HypixelServerDetector;
import remix.client.notification.NotificationManager;
import remix.client.notification.NotificationPriority;
import remix.event.impl.PlayerUpdateEvent;
import remix.server.ServerConfigRule;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.command.ClientCommandManager;
import remix.config.CustomProfileConfigHelper;
import remix.config.ModulesJsonConfig;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.hidden.HypixelLocrawListenerModule;

import com.google.common.collect.Maps;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.Map;
import java.util.Map.Entry;

/** AutoConfig module: world events and 0 settings. */
public final class AutoConfigModule extends ModuleBase {
    public String loadedConfigName;
    public int joinTicks;
    public Map<ServerConfigRule, String> serverConfigs = Maps.newHashMap();

    public void loadConfigByName(String configName) {
        if (!configName.equals(this.loadedConfigName)) {
            try {
                if (CustomProfileConfigHelper.loadProfile(configName)) {
                    ClientCommandManager.printMessage(
                            "Successfully loaded a auto-config called '"
                                    + ChatFormatting.GREEN
                                    + configName
                                    + ChatFormatting.RESET
                                    + "'!");
                    NotificationManager.post(
                            new NotificationManager(
                                    new String[] {
                                        "Auto-config",
                                        "Config \"" + configName + "\" loaded"
                                    },
                                    NotificationPriority.LOW,
                                    4000));
                    ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                    ClientServices.configFileManager.saveConfig(ModulesJsonConfig.class);
                    this.loadedConfigName = configName;
                    return;
                }

                ClientCommandManager.printMessage(
                        "There is no config called '"
                                + ChatFormatting.RED
                                + configName
                                + ChatFormatting.RESET
                                + "'!");
                NotificationManager.post(
                        new NotificationManager(
                                new String[] {
                                    "Auto-config",
                                    "No config \"" + configName + "\" was found!"
                                },
                                NotificationPriority.NORMAL,
                                4000));
            } catch (Exception exception) {
                ClientCommandManager.printMessage(
                        "Failed at loading a auto-config called '"
                                + ChatFormatting.RED
                                + configName
                                + ChatFormatting.RESET
                                + "'!");
                NotificationManager.post(
                        new NotificationManager(
                                new String[] {
                                    "Auto-config",
                                    "Failed loading config \"" + configName + "\"!"
                                },
                                NotificationPriority.HIGH,
                                4000));
            }
        }
    }

    @Subscribe(mc = PlayerUpdateEvent.class, priority = SubscriberPriority.LOWEST)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (minecraft.player == null || minecraft.world == null || this.joinTicks >= 40) {
            return;
        }
        if (++this.joinTicks != 35) {
            return;
        }

        ServerData server = minecraft.getCurrentServerData();
        if (server == null) {
            return;
        }
        String serverName = server.serverName;
        String serverAddress = server.serverIP;
        for (Entry<ServerConfigRule, String> entry : this.serverConfigs.entrySet()) {
            if (matchesServerRule(entry.getKey(), serverName, serverAddress)) {
                this.loadConfigByName(entry.getValue());
            }
        }
    }

    private static boolean matchesServerRule(ServerConfigRule rule, String serverName, String serverAddress) {
        String ruleAddress = rule.getIp();
        String gameType = rule.getHypixelGameType();
        int port = rule.getPort();
        if (HypixelServerDetector.isOnHypixel() && !gameType.equals("_")) {
            RemixClient.INSTANCE.getClientServices();
            HypixelLocrawListenerModule locraw = ClientServices.moduleManager.hypixelLocrawListener;
            HypixelLocationData location = locraw.getCurrentLocation();
            return location != null
                    && (!rule.isLoadInLobby() || locraw.isInLobby())
                    && location.getHypixelGameType().getGameName().equals(gameType);
        }
        return (rule.getName().equals(serverName) || ruleAddress.equals(serverAddress))
                && (port == 25565 || port == ServerAddress.fromString(ruleAddress).getPort());
    }

    public ServerConfigRule findServerRule(String query) {
        for (Entry<ServerConfigRule, String> entry : this.serverConfigs.entrySet()) {
            ServerConfigRule key = entry.getKey();
            if (key.getName().equalsIgnoreCase(query)
                    || key.getIp().equalsIgnoreCase(query)
                    || key.getHypixelGameType().equalsIgnoreCase(query)) {
                return key;
            }
        }

        return null;
    }

    public AutoConfigModule() {
        super("AutoConfig", new ModuleDefaults(ModuleCategory.WORLD).visible(true));
        this.setName("Auto Config");
    }
}
