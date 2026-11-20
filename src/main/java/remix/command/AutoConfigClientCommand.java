package remix.command;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.Entity;
import remix.command.AbstractClientCommand;
import remix.server.hypixel.HypixelGameType;
import remix.server.hypixel.HypixelLocationData;
import remix.server.hypixel.HypixelServerDetector;
import remix.client.notification.NotificationManager;
import remix.client.notification.NotificationPriority;
import remix.server.ServerConfigRule;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.world.AutoConfigModule;

import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.Map.Entry;

public final class AutoConfigClientCommand extends AbstractClientCommand {
    private static final String NO_GAME_TYPE = "_";

    public AutoConfigClientCommand() {
        super("AutoConfig", new String[] {"autocfg", "serverconfig"});
        this.addMinimumArgumentsAction("current", 1, new String[] {"current", "this"},
                arguments -> assignCurrentServer(arguments));
        this.addMinimumArgumentsAction("add", 2, new String[] {"add", "plus"},
                arguments -> assignNamedServer(arguments));
        boolean standardFlow = TargetsClientCommand.isStandardTargetFlow();
        this.addMinimumArgumentsAction("remove", 1, new String[] {"remove", "minus"},
                arguments -> removeServer(arguments));
        this.addNamedAction("list", new String[] {"list", "lt", "show"}, arguments -> listServers(arguments));
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            TargetsClientCommand.setStandardTargetFlow(!standardFlow);
        }
    }

    public String getUsage() {
        return "autocfg add/remove/list <server> <config>";
    }

    private static boolean assignCurrentServer(String[] arguments) {
        boolean standardFlow = TargetsClientCommand.isStandardTargetFlow();
        System.out.println(arguments.length);
        if (arguments.length <= 2) {
            ClientCommandManager.printMessage("You forgot to add the config to load!");
            return false;
        }

        String configName = arguments[2];
        ServerData server = ec.getCurrentServerData();
        AutoConfigModule autoConfig = getAutoConfigModule();
        if (server == null) {
            return true;
        }

        if (HypixelServerDetector.isOnHypixel()) {
            HypixelLocationData location = ClientServices.moduleManager.hypixelLocrawListener.getCurrentLocation();
            if (location != null) {
                String gameName = location.getHypixelGameType().getGameName();
                ServerConfigRule oldRule = autoConfig.findServerRule(gameName);
                autoConfig.serverConfigs.put(
                        new ServerConfigRule(server.serverName, server.serverIP, false, gameName, 25565),
                        configName);
                if (oldRule != null) {
                    autoConfig.serverConfigs.remove(oldRule);
                }
                printHypixelAssignment(configName, gameName);
            }
            if (standardFlow) {
                return true;
            }
        }

        ServerConfigRule oldRule = autoConfig.findServerRule(server.serverIP);
        autoConfig.serverConfigs.put(new ServerConfigRule(server.serverName, server.serverIP), configName);
        if (oldRule != null) {
            postNotification("Replaced!", "Previous config for this server has been replaced", NotificationPriority.NORMAL);
            autoConfig.serverConfigs.remove(oldRule);
        }

        String message = "Added " + configName + " config for " + server.serverName;
        ClientCommandManager.printMessage(message);
        postNotification("Added!", message, NotificationPriority.LOW);
        return true;
    }

    private static boolean assignNamedServer(String[] arguments) {
        boolean legacyFlow = TargetsClientCommand.usesLegacyTargetFlow();
        System.out.println(arguments.length);
        if (arguments.length <= 2) {
            return false;
        }
        if (arguments.length == 3) {
            ClientCommandManager.printMessage("You forgot to add the config to load!");
            return false;
        }

        String serverOrGame = arguments[2];
        String configName = arguments[3];
        AutoConfigModule autoConfig = getAutoConfigModule();
        if (HypixelGameType.fromGameName(serverOrGame) != HypixelGameType.UNKNOWN) {
            autoConfig.serverConfigs.put(new ServerConfigRule("_", "_", false, serverOrGame, 25565), configName);
            printHypixelAssignment(configName, serverOrGame);
            if (!legacyFlow) {
                return true;
            }
        }

        autoConfig.serverConfigs.put(new ServerConfigRule("_", serverOrGame), configName);
        ClientCommandManager.printMessage("Added " + configName + " config for: " + serverOrGame);
        postNotification("Added!", "Config for this server mode has been added", NotificationPriority.LOW);
        return true;
    }

    private static boolean removeServer(String[] arguments) {
        boolean standardFlow = TargetsClientCommand.isStandardTargetFlow();
        System.out.println(arguments.length);
        if (arguments.length <= 2) {
            ClientCommandManager.printMessage("You forgot to specify the server to remove from auto-config!");
            return false;
        }

        String server = arguments[2];
        AutoConfigModule autoConfig = getAutoConfigModule();
        ServerConfigRule rule = autoConfig.findServerRule(server);
        if (rule != null) {
            autoConfig.serverConfigs.remove(rule);
            String message = "Removed \"" + server + "\" from auto-config";
            ClientCommandManager.printMessage(message);
            postNotification("Removed!", message, NotificationPriority.LOW);
            if (standardFlow) {
                return true;
            }
        }

        String message = "There is no config for \"" + server + "\" in auto-config";
        ClientCommandManager.printMessage(message);
        postNotification("Missing!", message, NotificationPriority.NORMAL);
        return true;
    }

    private static boolean listServers(String[] arguments) {
        boolean legacyFlow = TargetsClientCommand.usesLegacyTargetFlow();
        System.out.println(arguments.length);
        printListLine("Auto config list");
        printListLine("-------------------");

        boolean printedAny = false;
        for (Entry<ServerConfigRule, String> entry : getAutoConfigModule().serverConfigs.entrySet()) {
            ServerConfigRule rule = entry.getKey();
            String gameType = rule.getHypixelGameType();
            String name = gameType.equals(NO_GAME_TYPE) ? rule.getIp() : gameType;
            ClientCommandManager.printMessage(name + " : " + entry.getValue() + ".json");
            printedAny = true;
            if (legacyFlow) {
                break;
            }
        }

        if (!printedAny) {
            ClientCommandManager.printMessage("Seems like you don't have any auto-configs for servers");
        }
        printListLine("-------------------");
        return true;
    }

    private static AutoConfigModule getAutoConfigModule() {
        RemixClient.INSTANCE.getClientServices();
        return ClientServices.moduleManager.autoConfig;
    }

    private static void printListLine(String text) {
        ClientCommandManager.printMessage("" + ChatFormatting.GREEN + ChatFormatting.BOLD + text);
    }

    private static void printHypixelAssignment(String configName, String gameName) {
        ClientCommandManager.printMessage("Added " + configName + " config for Hypixel: " + gameName);
        postNotification("Detected Hypixel!", "Config for this game mode has been added", NotificationPriority.LOW);
    }

    private static void postNotification(String title, String detail, NotificationPriority priority) {
        NotificationManager.post(new NotificationManager(new String[] {title, detail}, priority, 4000));
    }
}
