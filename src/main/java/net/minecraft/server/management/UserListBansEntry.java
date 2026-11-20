package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import java.util.Date;
import java.util.UUID;

public class UserListBansEntry extends BanEntry {

    private static String[] packedTextFragments;

    public UserListBansEntry(GameProfile gameprofile) {
        this(gameprofile, (Date) null, (String) null, (Date) null, (String) null);
    }

    protected void onSerialization(JsonObject jsonobject) {
        if (this.getValue() != null) {
            jsonobject.addProperty(
                    "uuid",
                    ((GameProfile) this.getValue()).getId() == null
                            ? packedTextFragments[0]
                            : ((GameProfile) this.getValue()).getId().toString());
            jsonobject.addProperty("name", ((GameProfile) this.getValue()).getName());
            super.onSerialization(jsonobject);
        }
    }

    private static GameProfile func_152648_b(JsonObject jsonobject) {
        try {
            if (!jsonobject.has("uuid") || !jsonobject.has("name")) {
                return null;
            }
        } catch (Throwable throwable1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_b7e52da193241b30710d(throwable1));
        }

        String asString = jsonobject.get("uuid").getAsString();

        UUID uuid;
        try {
            uuid = UUID.fromString(asString);
        } catch (Throwable throwable) {
            return null;
        }

        return new GameProfile(uuid, jsonobject.get("name").getAsString());
    }

    public UserListBansEntry(
            GameProfile gameprofile, Date date1, String text, Date date, String textValue) {
        super(gameprofile, date, text, date, textValue);
    }

    public UserListBansEntry(JsonObject jsonobject) {
        super(func_152648_b(jsonobject), jsonobject);
    }

    static {
        net.minecraft.server.management.UserListBansEntry.packedTextFragments = new String[3];
        net.minecraft.server.management.UserListBansEntry.packedTextFragments[0] = new String("");
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }
}
