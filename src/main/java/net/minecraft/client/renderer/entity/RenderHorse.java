package net.minecraft.client.renderer.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;

import java.util.Map;

public class RenderHorse extends RenderLiving {
    private static ResourceLocation donkeyTextures;

    private static ResourceLocation muleTextures;
    private static ResourceLocation whiteHorseTextures;
    private static ResourceLocation skeletonHorseTextures;
    private static Map aqF;
    private static ResourceLocation zombieHorseTextures;

    static {
        aqF = Maps.newHashMap();
        net.minecraft.client.renderer.entity.RenderHorse.whiteHorseTextures =
                new ResourceLocation("textures/entity/horse/horse_white.png");
        net.minecraft.client.renderer.entity.RenderHorse.donkeyTextures = new ResourceLocation("textures/entity/horse/mule.png");
        net.minecraft.client.renderer.entity.RenderHorse.muleTextures = new ResourceLocation("textures/entity/horse/donkey.png");
        net.minecraft.client.renderer.entity.RenderHorse.zombieHorseTextures =
                new ResourceLocation("textures/entity/horse/horse_zombie.png");
        net.minecraft.client.renderer.entity.RenderHorse.skeletonHorseTextures =
                new ResourceLocation("textures/entity/horse/horse_skeleton.png");
    }

    protected void method_60050b949203a0c7a467(EntityHorse entityHorse, float f) {
        float f1;
        {
            f1 = 1.0F;
            Render.getLegacyEntityRenderState();

            int horseType = entityHorse.getHorseType();

            if (horseType == 1) {
                f1 *= 0.87F;

                
            } else {

                if (horseType == 2) {
                    f1 *= 0.92F;
                }
            }
        }

        GlStateManager.scale(f1, f1, f1);
        super.preRenderCallback(entityHorse, f);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_3725e8deef52156840f7((EntityHorse) entity);
    }

    public RenderHorse(RenderManager renderManagerValue, ModelHorse modelHorse, float f) {
        super(renderManagerValue, modelHorse, f);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private ResourceLocation method_388a138eb759f30e947c(EntityHorse entityHorse) {
        String text = entityHorse.getHorseTexture();
        if (!entityHorse.func_175507_cI()) {
            return null;
        }

        ResourceLocation resourceLocation = (ResourceLocation) aqF.get(text);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(text);
            MinecraftClient.getMinecraft()
                    .getTextureManager()
                    .loadTexture(
                            resourceLocation,
                            new LayeredTexture(entityHorse.getVariantTexturePaths()));
            aqF.put(text, resourceLocation);
        }

        return resourceLocation;
    }

    protected ResourceLocation method_3725e8deef52156840f7(EntityHorse entityHorse) {
        if (!entityHorse.func_110239_cn()) {
            switch (entityHorse.getHorseType()) {
                case 0:
                default:
                    return net.minecraft.client.renderer.entity.RenderHorse.whiteHorseTextures;
                case 1:
                    return net.minecraft.client.renderer.entity.RenderHorse.muleTextures;
                case 2:
                    return net.minecraft.client.renderer.entity.RenderHorse.donkeyTextures;
                case 3:
                    return net.minecraft.client.renderer.entity.RenderHorse.zombieHorseTextures;
                case 4:
                    return net.minecraft.client.renderer.entity.RenderHorse.skeletonHorseTextures;
            }
        } else {
            return this.method_388a138eb759f30e947c(entityHorse);
        }
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.method_60050b949203a0c7a467((EntityHorse) livingEntity, f);
    }
}
