package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityEnderman;

public class LayerHeldBlock implements LayerRenderer {
    private RenderEnderman endermanRenderer;

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.doRenderLayer((EntityEnderman) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    public LayerHeldBlock(RenderEnderman aDiValue) {
        this.endermanRenderer = aDiValue;
    }

    public void doRenderLayer(
            EntityEnderman entityEnderman,
            float f2,
            float f3,
            float f,
            float f4,
            float f5,
            float f6,
            float f7) {
        BlockState blockState = entityEnderman.getHeldBlockState();
        if (blockState.getBlock().getMaterial() != Material.AIR) {
            BlockRendererDispatcher blockRendererDispatcher =
                    MinecraftClient.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.6875F, -0.75F);
            GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.25F, 0.1875F, 0.25F);
            float f1 = 0.5F;
            GlStateManager.scale(-f1, -f1, f1);
            int brightnessForRender = entityEnderman.getBrightnessForRender(f);
            int j = brightnessForRender % 65536;
            int k = brightnessForRender / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.endermanRenderer.bindTexture(TextureMap.locationBlocksTexture);
            blockRendererDispatcher.renderBlockBrightness(blockState, 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
        }
    }
}
