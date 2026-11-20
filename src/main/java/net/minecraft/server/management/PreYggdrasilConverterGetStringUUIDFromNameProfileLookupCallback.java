package net.minecraft.server.management;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PreYggdrasilConverter;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;

import java.util.ArrayList;

public final class PreYggdrasilConverterGetStringUUIDFromNameProfileLookupCallback implements ProfileLookupCallback {
    MinecraftServer awJ;
    private static String c;
    ArrayList bzr;

    public void onProfileLookupSucceeded(GameProfile gameprofile) {
        this.awJ.getPlayerProfileCache().addEntry(gameprofile);
        this.bzr.add(gameprofile);
    }

    // region String decoding support

    // endregion

    static {
        c = "Could not lookup user whitelist entry for ";
    }

    public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
        PreYggdrasilConverter.vb().warn(c + gameprofile.getName(), exception);
    }

    public PreYggdrasilConverterGetStringUUIDFromNameProfileLookupCallback(MinecraftServer awJValue, ArrayList bzrValue) {
        this.awJ = awJValue;
        this.bzr = bzrValue;
    }

    // region String decoding support

    // endregion
}
