package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import java.io.File;

public class UserListOps extends UserList {
    protected String getObjectKey(Object object) {
        return this.func_152699_b((GameProfile) object);
    }

    public String[] getKeys() {
        String[] astring = new String[this.getValues().size()];
        ServerConfigurationManager.kr();

        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_595 =
                        (this.getValues().values()).iterator();
                (__recoveryIterator171_595).hasNext(); ) {
            UserListOpsEntry userListOpsEntry =
                    (UserListOpsEntry) (__recoveryIterator171_595).next();
            astring[i++] = ((GameProfile) userListOpsEntry.getValue()).getName();
        }

        return astring;
    }

    protected UserListEntry createEntry(JsonObject jsonobject) {
        return new UserListOpsEntry(jsonobject);
    }

    public UserListOps(File file1) {
        super(file1);
    }

    protected String func_152699_b(GameProfile gameprofile) {
        return gameprofile.getId().toString();
    }

    public GameProfile getGameProfileFromName(String text) {
        for (java.util.Iterator<?> __recoveryIterator171_1362 =
                        (this.getValues().values()).iterator();
                (__recoveryIterator171_1362).hasNext(); ) {
            UserListOpsEntry userListOpsEntry =
                    (UserListOpsEntry) (__recoveryIterator171_1362).next();
            if (text.equalsIgnoreCase(((GameProfile) userListOpsEntry.getValue()).getName())) {
                return (GameProfile) userListOpsEntry.getValue();
            }
        }

        return null;
    }
}
