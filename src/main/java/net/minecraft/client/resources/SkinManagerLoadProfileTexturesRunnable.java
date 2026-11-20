package net.minecraft.client.resources;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.SkinManagerRunRunnable;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;

import java.util.HashMap;

public class SkinManagerLoadProfileTexturesRunnable implements Runnable {
    public SkinManagerSkinAvailableCallback aKT;
    public SkinManager aKU;
    GameProfile auC;
    boolean b;

    SkinManagerLoadProfileTexturesRunnable(
            SkinManager aKUValue,
            GameProfile gameprofile,
            boolean flag,
            SkinManagerSkinAvailableCallback aKTValue) {
        this.aKU = aKUValue;
        this.auC = gameprofile;
        this.b = flag;
        this.aKT = aKTValue;
    }

    @Override
    public void run() {
        HashMap hashmap = Maps.newHashMap();

        try {
            hashmap.putAll(
                    SkinManager.method_36b847f74af2a364076a(this.aKU)
                            .getTextures(this.auC, this.b));
        } catch (InsecureTextureException insecuretextureexception) {
        }

        if (hashmap.isEmpty()
                && this.auC.getId().equals(MinecraftClient.getMinecraft().getSession().getProfile().getId())) {
            hashmap.putAll(
                    SkinManager.method_36b847f74af2a364076a(this.aKU)
                            .getTextures(
                                    SkinManager.method_36b847f74af2a364076a(this.aKU)
                                            .fillProfileProperties(this.auC, false),
                                    false));
        }

        MinecraftClient.getMinecraft()
                .addScheduledTask(new SkinManagerRunRunnable(this, hashmap));
    }

    private static InsecureTextureException method_72e8e7af663325627560(
            InsecureTextureException insecuretextureexception) {
        return insecuretextureexception;
    }
}
