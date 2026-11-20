package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.ResourceLocation;
import optifine.OptiFineConfig;
import shadersmod.client.Shaders;

public class LayerEnderDragonEyes implements LayerRenderer {
    private static ResourceLocation TEXTURE;
    private RenderDragon dragonRenderer;

    public LayerEnderDragonEyes(RenderDragon aDeValue) {
        this.dragonRenderer = aDeValue;
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.entity.layers.LayerEnderDragonEyes.TEXTURE =
                new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    public void doRenderLayer(
            EntityDragon entityDragon,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.dragonRenderer.bindTexture(net.minecraft.client.renderer.entity.layers.LayerEnderDragonEyes.TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(1, 1);
        GlStateManager.disableLighting();
        GlStateManager.depthFunc(514);
        char c0 = '\uf0f0';
        int i = c0 % 65536;
        int j = c0 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (OptiFineConfig.isShaders()) {
            Shaders.beginSpiderEyes();
        }

        this.dragonRenderer.getMainModel().render(entityDragon, f, f1, f3, f4, f5, f6);
        this.dragonRenderer.method_7b47d2e176a589b0659a(entityDragon, f2);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.depthFunc(515);
    }

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.doRenderLayer((EntityDragon) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    // region String decoding support

    // endregion
}
