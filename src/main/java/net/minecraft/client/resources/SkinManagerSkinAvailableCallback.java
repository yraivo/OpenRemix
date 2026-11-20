package net.minecraft.client.resources;

import net.minecraft.util.ResourceLocation;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

public interface SkinManagerSkinAvailableCallback {
    void skinAvailable(
            Type type,
            ResourceLocation resourceLocation,
            MinecraftProfileTexture minecraftprofiletexture);
}
