package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import java.io.File;
import java.util.Iterator;

public class UserListWhitelist extends UserList {
    protected String func_152704_b(GameProfile gameprofile) {
        return gameprofile.getId().toString();
    }

    public UserListWhitelist(File file1) {
        super(file1);
    }

    protected UserListEntry createEntry(JsonObject jsonobject) {
        return new UserListWhitelistEntry(jsonobject);
    }

    protected String getObjectKey(Object object) {
        return this.func_152704_b((GameProfile) object);
    }

    public GameProfile func_152706_a(String text) {
        for (java.util.Iterator<?> __recoveryIterator171_921 =
                        (this.getValues().values()).iterator();
                (__recoveryIterator171_921).hasNext(); ) {
            UserListWhitelistEntry userListWhitelistEntry =
                    (UserListWhitelistEntry) (__recoveryIterator171_921).next();
            if (text.equalsIgnoreCase(
                    ((GameProfile) userListWhitelistEntry.getValue()).getName())) {
                return (GameProfile) userListWhitelistEntry.getValue();
            }
        }

        return null;
    }

    public String[] getKeys() {
        ServerConfigurationManager.kr();

        String[] astring = new String[this.getValues().size()];
        int i = 0;
        Iterator iterator = this.getValues().values().iterator();

        while (iterator.hasNext()) {
            UserListWhitelistEntry userListWhitelistEntry =
                    (UserListWhitelistEntry) iterator.next();
            astring[i++] = ((GameProfile) userListWhitelistEntry.getValue()).getName();
        }

        return astring;
    }
}
