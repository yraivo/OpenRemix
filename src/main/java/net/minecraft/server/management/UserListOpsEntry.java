package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import java.util.UUID;

public class UserListOpsEntry extends UserListEntry {

    private int field_152645_a;
    private static String[] packedTextFragments;

    static {
        net.minecraft.server.management.UserListOpsEntry.packedTextFragments = new String[3];
        net.minecraft.server.management.UserListOpsEntry.packedTextFragments[0] = new String("");
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public int getPermissionLevel() {
        return this.field_152645_a;
    }

    private static GameProfile func_152643_b(JsonObject jsonobject) {
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

    protected void onSerialization(JsonObject jsonobject) {
        if (this.getValue() != null) {
            jsonobject.addProperty(
                    "uuid",
                    ((GameProfile) this.getValue()).getId() == null
                            ? packedTextFragments[0]
                            : ((GameProfile) this.getValue()).getId().toString());
            jsonobject.addProperty("name", ((GameProfile) this.getValue()).getName());
            super.onSerialization(jsonobject);
            jsonobject.addProperty("level", this.field_152645_a);
        }
    }

    public UserListOpsEntry(GameProfile gameprofile, int i) {
        super(gameprofile);
        this.field_152645_a = i;
    }

    public UserListOpsEntry(JsonObject jsonobject) {
        super(func_152643_b(jsonobject), jsonobject);
        this.field_152645_a = jsonobject.has("level") ? jsonobject.get("level").getAsInt() : 0;
    }
}
