package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import optifine.OptiFineConfig;
import shadersmod.client.Shaders;

public class LayerSpiderEyes implements LayerRenderer {
    private RenderSpider spiderRenderer;
    private static ResourceLocation SPIDER_EYES;

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.method_b5993b35cecd8909a0f1((EntitySpider) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.entity.layers.LayerSpiderEyes.SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
    }

    // region String decoding support

    // endregion

    public void method_b5993b35cecd8909a0f1(
            EntitySpider entitySpider,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        label17:
        {
            this.spiderRenderer.bindTexture(net.minecraft.client.renderer.entity.layers.LayerSpiderEyes.SPIDER_EYES);
            int[] aint1 = LayerArmorBase.method_650532ba484532b3efb0();
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            int[] aint = aint1;
            GlStateManager.blendFunc(1, 1);
            if (entitySpider.isInvisible()) {
                GlStateManager.depthMask(false);
                if (aint == null) {
                    break label17;
                }

                Entity.setLegacyGlobalFlowMarker(new String[3]);
            }

            GlStateManager.depthMask(true);
        }

        char c0 = '\uf0f0';
        int i = c0 % 65536;
        int j = c0 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (OptiFineConfig.isShaders()) {
            Shaders.beginSpiderEyes();
        }

        this.spiderRenderer.getMainModel().render(entitySpider, f, f1, f3, f4, f5, f6);
        int k = entitySpider.getBrightnessForRender(f2);
        i = k % 65536;
        j = k / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
        this.spiderRenderer.method_7b47d2e176a589b0659a(entitySpider, f2);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public LayerSpiderEyes(RenderSpider aGmValue) {
        this.spiderRenderer = aGmValue;
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
