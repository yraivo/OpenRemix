package net.minecraft.server.management;

import com.mojang.authlib.GameProfile;

import java.util.Date;

public class PlayerProfileCacheProfileEntry {
    PlayerProfileCache bzl;
    private Date expirationDate;
    private GameProfile gameProfile;

    PlayerProfileCacheProfileEntry(
            PlayerProfileCache playerProfileCache,
            GameProfile gameprofile,
            Date date,
            Object object) {
        this(playerProfileCache, gameprofile, date);
    }

    private PlayerProfileCacheProfileEntry(
            PlayerProfileCache bzlValue, GameProfile gameprofile, Date date) {
        this.bzl = bzlValue;
        this.gameProfile = gameprofile;
        this.expirationDate = date;
    }

    public GameProfile getGameProfile() {
        return this.gameProfile;
    }

    static Date access$200(PlayerProfileCacheProfileEntry playerProfileCacheProfileEntry) {
        return playerProfileCacheProfileEntry.expirationDate;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }
}
