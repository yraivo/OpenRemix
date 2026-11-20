package net.minecraft.client.resources;

import net.minecraft.client.resources.SkinManagerLoadProfileTexturesRunnable;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import java.util.HashMap;

public class SkinManagerRunRunnable implements Runnable {
    HashMap val$hashMap;
    SkinManagerLoadProfileTexturesRunnable aKV;

    public SkinManagerRunRunnable(SkinManagerLoadProfileTexturesRunnable aKVValue, HashMap hashmap) {
        this.aKV = aKVValue;
        this.val$hashMap = hashmap;
    }

    @Override
    public void run() {
        if (this.val$hashMap.containsKey(Type.SKIN)) {
            this.aKV.aKU.loadSkin(
                    (MinecraftProfileTexture) this.val$hashMap.get(Type.SKIN), Type.SKIN, this.aKV.aKT);
        }

        if (this.val$hashMap.containsKey(Type.CAPE)) {
            this.aKV.aKU.loadSkin(
                    (MinecraftProfileTexture) this.val$hashMap.get(Type.CAPE), Type.CAPE, this.aKV.aKT);
        }
    }
}
