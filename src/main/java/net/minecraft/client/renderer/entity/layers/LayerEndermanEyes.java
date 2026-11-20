package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;
import optifine.OptiFineConfig;
import shadersmod.client.Shaders;

public class LayerEndermanEyes implements LayerRenderer {
    private RenderEnderman endermanRenderer;

    private static ResourceLocation dy;

    public void method_9e548b671cbf98f2a81c(
            EntityEnderman entityEnderman,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        {
            this.endermanRenderer.bindTexture(dy);
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.blendFunc(1, 1);
            int[] aint1 = LayerArmorBase.method_650532ba484532b3efb0();
            GlStateManager.disableLighting();
            int[] aint = aint1;
            if (entityEnderman.isInvisible()) {
                GlStateManager.depthMask(false);
                if (!(aint == null)) {

                    GlStateManager.depthMask(true);
                }
            } else {

                GlStateManager.depthMask(true);
            }
        }

        char c0 = '\uf0f0';
        int i = c0 % 65536;
        int j = c0 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (OptiFineConfig.isShaders()) {
            Shaders.beginSpiderEyes();
        }

        this.endermanRenderer.getMainModel().render(entityEnderman, f, f1, f3, f4, f5, f6);
        this.endermanRenderer.method_7b47d2e176a589b0659a(entityEnderman, f2);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
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
        this.method_9e548b671cbf98f2a81c((EntityEnderman) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    static {
        dy = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
    }

    // region String decoding support

    // endregion

    public LayerEndermanEyes(RenderEnderman aDgValue) {
        this.endermanRenderer = aDgValue;
    }

    // region String decoding support

    // endregion
}
