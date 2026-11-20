package remix.config;

import remix.client.friend.FriendEntry;
import remix.client.friend.FriendManager;
import remix.client.ClientServices;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

public final class FriendsJsonConfig extends AbstractJsonConfigFile {

    public void loadJson(JsonObject jsonobject) throws IOException {
        AccountsTextConfig.getLegacyAccountConfigMarker();

        if (!jsonobject.isJsonNull() && jsonobject.has("name")) {
            if (jsonobject.has("alias")) {
                ClientServices.friendManager.addFriend(
                        jsonobject.get("name").getAsString(),
                        jsonobject.get("alias").getAsString());

                return;
            }

            ClientServices.friendManager.addFriend(jsonobject.get("name").getAsString());
        }
    }

    public FriendsJsonConfig(File file1) {
        super(file1);
    }

    public void saveTo(java.util.List list) throws IOException {
        AccountsTextConfig.getLegacyAccountConfigMarker();

        Iterator iterator = FriendManager.friendsMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            FriendEntry value = (FriendEntry) entry.getValue();
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("name", value.getName());
            jsonobject.addProperty("alias", value.getAlias());
            list.add(jsonobject);
        }
    }


    public void loadFrom(Object object) throws IOException {
        this.loadJson((JsonObject) object);
    }

}
