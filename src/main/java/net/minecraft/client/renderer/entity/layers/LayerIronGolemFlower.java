package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;

public class LayerIronGolemFlower implements LayerRenderer {
    private RenderIronGolem ironGolemRenderer;

    public LayerIronGolemFlower(RenderIronGolem aGcValue) {
        this.ironGolemRenderer = aGcValue;
    }

    public void doRenderLayer(
            EntityIronGolem entityIronGolem,
            float f2,
            float f3,
            float f,
            float f4,
            float f5,
            float f6,
            float f7) {
        if (entityIronGolem.getHoldRoseTick() != 0) {
            BlockRendererDispatcher blockRendererDispatcher =
                    MinecraftClient.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();
            GlStateManager.rotate(
                    5.0F
                            + 180.0F
                                    * ((ModelIronGolem) this.ironGolemRenderer.getMainModel())
                                            .ironGolemRightArm
                                            .rotateAngleX
                                    / (float) Math.PI,
                    1.0F,
                    0.0F,
                    0.0F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(-0.9375F, -0.625F, -0.9375F);
            float f1 = 0.5F;
            GlStateManager.scale(f1, -f1, f1);
            int brightnessForRender = entityIronGolem.getBrightnessForRender(f);
            int j = brightnessForRender % 65536;
            int k = brightnessForRender / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.ironGolemRenderer.bindTexture(TextureMap.locationBlocksTexture);
            blockRendererDispatcher.renderBlockBrightness(
                    Blocks.red_flower.getDefaultState(), 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
        }
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
        this.doRenderLayer((EntityIronGolem) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
