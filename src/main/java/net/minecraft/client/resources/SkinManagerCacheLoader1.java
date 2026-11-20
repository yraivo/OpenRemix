package net.minecraft.client.resources;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.SkinManager;

import com.google.common.cache.CacheLoader;
import com.mojang.authlib.GameProfile;

import java.util.Map;

public class SkinManagerCacheLoader1 extends CacheLoader {
    SkinManager anA;

    public Object load(Object object) {
        return this.method_251bb7612e57f8726385((GameProfile) object);
    }

    public Map method_251bb7612e57f8726385(GameProfile gameprofile) {
        return MinecraftClient.getMinecraft().getSessionService().getTextures(gameprofile, false);
    }

    public SkinManagerCacheLoader1(SkinManager anAValue) {
        this.anA = anAValue;
    }
}
