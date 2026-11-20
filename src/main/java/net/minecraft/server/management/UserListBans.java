package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import java.io.File;

public class UserListBans extends UserList {
    protected String getProfileId(GameProfile gameprofile) {
        return gameprofile.getId().toString();
    }

    protected UserListEntry createEntry(JsonObject jsonobject) {
        return new UserListBansEntry(jsonobject);
    }

    public UserListBans(File file1) {
        super(file1);
    }

    public GameProfile isUsernameBanned(String text) {
        for (java.util.Iterator<?> __recoveryIterator171_727 =
                        (this.getValues().values()).iterator();
                (__recoveryIterator171_727).hasNext(); ) {
            UserListBansEntry userListBansEntry =
                    (UserListBansEntry) (__recoveryIterator171_727).next();
            if (text.equalsIgnoreCase(((GameProfile) userListBansEntry.getValue()).getName())) {
                return (GameProfile) userListBansEntry.getValue();
            }
        }

        return null;
    }

    public String[] getKeys() {
        ServerConfigurationManager.sN();

        String[] astring = new String[this.getValues().size()];

        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_1228 =
                        (this.getValues().values()).iterator();
                (__recoveryIterator171_1228).hasNext(); ) {
            UserListBansEntry userListBansEntry =
                    (UserListBansEntry) (__recoveryIterator171_1228).next();
            astring[i++] = ((GameProfile) userListBansEntry.getValue()).getName();
        }

        return astring;
    }

    public boolean isBanned(GameProfile gameprofile) {
        return this.hasEntry(gameprofile);
    }

    protected String getObjectKey(Object p171_0) {
        return this.getProfileId((com.mojang.authlib.GameProfile) p171_0);
    }
}
