package net.minecraft.util;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;

import java.util.UUID;

public class Session {
    private String username;
    private String playerID;
    private String token;
    private static String e;
    private static String[] packedTextFragments;
    private SessionType sessionType;

    // region String decoding support

    // endregion

    public String getPlayerID() {
        return this.playerID;
    }

    public String getSessionID() {
        return e + this.token + packedTextFragments[0] + this.playerID;
    }

    // region String decoding support

    // endregion

    public SessionType getSessionType() {
        return this.sessionType;
    }

    public String getToken() {
        return this.token;
    }

    public String getUsername() {
        return this.username;
    }

    static {
        net.minecraft.util.Session.packedTextFragments = new String[2];
        net.minecraft.util.Session.packedTextFragments[0] = new String(":");
        net.minecraft.util.Session.packedTextFragments[1] = new String("Fq/6\u000f\u001d");
        e = "token:";
    }

    public GameProfile getProfile() {
        try {
            UUID uuid = UUIDTypeAdapter.fromString(this.getPlayerID());
            return new GameProfile(uuid, this.getUsername());
        } catch (IllegalArgumentException illegalargumentexception) {
            return new GameProfile((UUID) null, this.getUsername());
        }
    }

    public Session(
            String text, String textValue, String textValue2, String textValue3) {
        this.username = text;
        this.playerID = textValue;
        this.token = textValue2;
        this.sessionType = SessionType.setSessionType(textValue3);
    }

    // region String decoding support

    // endregion
}
