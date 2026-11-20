package remix.command;

import net.minecraft.entity.Entity;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.client.friend.FriendEntry;
import remix.client.friend.FriendManager;
import remix.client.notification.NotificationManager;
import remix.client.notification.NotificationPriority;
import remix.config.FriendsJsonConfig;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public final class FriendClientCommand extends AbstractClientCommand {
    private static String[] packedTextFragments;

    static {
        remix.command.FriendClientCommand.packedTextFragments = new String[7];
        remix.command.FriendClientCommand.packedTextFragments[0] = new String("f");
        remix.command.FriendClientCommand.packedTextFragments[1] = new String("c");
        remix.command.FriendClientCommand.packedTextFragments[2] = new String("r");
        remix.command.FriendClientCommand.packedTextFragments[3] = new String("a");
        remix.command.FriendClientCommand.packedTextFragments[4] = new String(")");
        remix.command.FriendClientCommand.packedTextFragments[5] = new String(".");
    }

    public String getUsage() {
        return "friend <add/remove/list> <name> <alias>";
    }

    public FriendClientCommand() {
        this(TargetsClientCommand.usesLegacyTargetFlow());
    }

    private FriendClientCommand(boolean initialFlowFlag) {
        super("Friend", new String[] {"friend", packedTextFragments[0], "friends"});

        this.addNamedAction(
                "clear",
                new String[] {"clean", "c", "empty", "clear"},
                arguments -> clearFriends());
        this.addMinimumArgumentsAction(
                "remove",
                3,
                new String[] {"remove", "r", "gone", "delete"},
                arguments -> removeFriend(arguments[2]));
        this.addMinimumArgumentsAction(
                "add", 4, new String[] {"add", "a", "create"}, arguments -> addFriend(arguments));
        this.addNamedAction(
                "list", new String[] {"list", "lt", "show"}, arguments -> listFriends());
        if (initialFlowFlag) {
            Entity.setLegacyGlobalFlowMarker(new String[3]);
        }
    }

    private static boolean clearFriends() {
        FriendManager.friendsMap.clear();
        NotificationManager.post(
                new NotificationManager(
                        new String[] {"Empty!", "You no longer have friends"},
                        NotificationPriority.LOW,
                        4000));
        saveFriends();
        return true;
    }

    private static boolean removeFriend(String playerName) {
        boolean standardFriendFlow = TargetsClientCommand.isStandardTargetFlow();
        FriendManager friends = ClientServices.friendManager;
        boolean wasFriend = friends.isFriend(playerName);
        if (wasFriend) {
            friends.removeFriend(playerName);
            postFriendResult(
                    "Removed!",
                    String.format("Removed '%s' from your friends list!", playerName),
                    NotificationPriority.LOW);
        }
        if (!wasFriend || !standardFriendFlow) {
            postFriendResult(
                    "Missing!",
                    String.format("The player '%s' is not in your friends list!", playerName),
                    NotificationPriority.NORMAL);
        }
        saveFriends();
        return true;
    }

    private static boolean addFriend(String[] arguments) {
        String playerName = arguments[2];
        boolean legacyFriendFlow = TargetsClientCommand.usesLegacyTargetFlow();
        FriendManager friends = ClientServices.friendManager;
        if (!friends.isFriend(playerName)) {
            boolean hasAlias = arguments.length >= 4;
            if (hasAlias) {
                String alias = arguments[3];
                friends.addFriend(playerName, alias);
                postFriendResult(
                        "Added!",
                        String.format(
                                "Added '%s' to your friends list as '%s'!", playerName, alias),
                        NotificationPriority.LOW);
            }
            if (!hasAlias || legacyFriendFlow) {
                friends.addFriend(playerName);
                postFriendResult(
                        "Added!",
                        String.format("Added '%s' to your friends list!", playerName),
                        NotificationPriority.LOW);
            }
            saveFriends();
            if (!legacyFriendFlow) {
                return true;
            }
        }

        NotificationManager.post(
                new NotificationManager(
                        new String[] {
                            "Already in!", "The player '%s' is already in your friends list!"
                        },
                        NotificationPriority.NORMAL,
                        4000));
        ClientCommandManager.printMessage(
                String.format("The player '%s' is already in your friends list!", playerName));
        return true;
    }

    private static boolean listFriends() {
        boolean standardFriendFlow = TargetsClientCommand.isStandardTargetFlow();
        Map<String, FriendEntry> friends = FriendManager.friendsMap;
        if (!friends.isEmpty()) {
            ArrayList<Entry<String, FriendEntry>> entries = new ArrayList<>(friends.entrySet());
            StringBuilder description = new StringBuilder();
            for (int index = 0; index < entries.size(); index++) {
                Entry<String, FriendEntry> friend = entries.get(index);
                description
                        .append(friend.getKey())
                        .append(" (")
                        .append(friend.getValue().getAlias())
                        .append(')');
                if (index != entries.size() - 1) {
                    description.append(", ");
                }
                if (!standardFriendFlow) {
                    break;
                }
            }
            ClientCommandManager.printMessage("Friends: \u00a7f" + description.append('.'));
            if (standardFriendFlow) {
                return true;
            }
        }
        ClientCommandManager.printMessage("Seems like you don't have friends");
        return true;
    }

    private static void postFriendResult(
            String title, String message, NotificationPriority priority) {
        NotificationManager.post(
                new NotificationManager(new String[] {title, message}, priority, 4000));
        ClientCommandManager.printMessage(message);
    }

    private static void saveFriends() {
        RemixClient.INSTANCE.getClientServices();
        ClientServices.configFileManager.saveConfig(FriendsJsonConfig.class);
    }
}
