package remix.client.friend;

import java.util.HashMap;
import java.util.Map;

public final class FriendManager {
    public static final Map<String, FriendEntry> friendsMap = new HashMap<>();

    public boolean isFriend(String text) {
        return remix.client.friend.FriendManager.friendsMap.containsKey(text);
    }

    public void addFriend(String text, String textValue) {
        if (!remix.client.friend.FriendManager.friendsMap.containsKey(text)) {
            remix.client.friend.FriendManager.friendsMap.put(text, new FriendEntry(text, textValue));
        }
    }

    public void removeFriend(String text) {
        remix.client.friend.FriendManager.friendsMap.remove(text);
    }

    public void addFriend(String text) {
        if (!remix.client.friend.FriendManager.friendsMap.containsKey(text)) {
            remix.client.friend.FriendManager.friendsMap.put(text, new FriendEntry(text, text));
        }
    }

    private FriendManager() {}

    public static FriendManager create() {
        return new FriendManager();
    }
}
