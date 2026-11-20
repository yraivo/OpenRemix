package net.minecraft.client.resources;

import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public class DefaultPlayerSkin {
    private static ResourceLocation TEXTURE_STEVE;

    private static ResourceLocation TEXTURE_ALEX;

    static {
        net.minecraft.client.resources.DefaultPlayerSkin.TEXTURE_STEVE = new ResourceLocation("textures/entity/steve.png");
        net.minecraft.client.resources.DefaultPlayerSkin.TEXTURE_ALEX = new ResourceLocation("textures/entity/alex.png");
    }

    public static String getSkinType(UUID uuid) {
        return isSlimSkin(uuid) ? "slim" : "default";
    }

    public static ResourceLocation getDefaultSkin(UUID uuid) {
        return isSlimSkin(uuid) ? net.minecraft.client.resources.DefaultPlayerSkin.TEXTURE_ALEX : net.minecraft.client.resources.DefaultPlayerSkin.TEXTURE_STEVE;
    }

    private static boolean isSlimSkin(UUID uuid) {
        return (uuid.hashCode() & 1) == 1;
    }

    public static ResourceLocation getDefaultSkinLegacy() {
        return net.minecraft.client.resources.DefaultPlayerSkin.TEXTURE_STEVE;
    }
}
