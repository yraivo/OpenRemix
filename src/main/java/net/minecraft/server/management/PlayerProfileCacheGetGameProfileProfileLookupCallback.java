package net.minecraft.server.management;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;

public final class PlayerProfileCacheGetGameProfileProfileLookupCallback implements ProfileLookupCallback {
    GameProfile[] bzk;

    public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
        this.bzk[0] = null;
    }

    public void onProfileLookupSucceeded(GameProfile gameprofile) {
        this.bzk[0] = gameprofile;
    }

    public PlayerProfileCacheGetGameProfileProfileLookupCallback(GameProfile[] agameprofile) {
        this.bzk = agameprofile;
    }
}
