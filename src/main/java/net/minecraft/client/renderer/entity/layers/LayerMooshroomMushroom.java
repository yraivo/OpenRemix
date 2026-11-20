package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderMooshroom;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;

public class LayerMooshroomMushroom implements LayerRenderer {
    private RenderMooshroom mooshroomRenderer;

    public void method_b94c55a52cf69dd8adb6(
            EntityMooshroom entityMooshroom,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        if (!entityMooshroom.isChild() && !entityMooshroom.isInvisible()) {
            BlockRendererDispatcher blockRendererDispatcher =
                    MinecraftClient.getMinecraft().getBlockRendererDispatcher();
            this.mooshroomRenderer.bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.enableCull();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, -1.0F, 1.0F);
            GlStateManager.translate(0.2F, 0.35F, 0.5F);
            GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5F, -0.5F, 0.5F);
            blockRendererDispatcher.renderBlockBrightness(
                    Blocks.red_mushroom.getDefaultState(), 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.1F, 0.0F, -0.6F);
            GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, 0.5F);
            blockRendererDispatcher.renderBlockBrightness(
                    Blocks.red_mushroom.getDefaultState(), 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            ((ModelQuadruped) this.mooshroomRenderer.getMainModel()).head.postRender(0.0625F);
            GlStateManager.scale(1.0F, -1.0F, 1.0F);
            GlStateManager.translate(0.0F, 0.7F, -0.2F);
            GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, 0.5F);
            blockRendererDispatcher.renderBlockBrightness(
                    Blocks.red_mushroom.getDefaultState(), 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.disableCull();
        }
    }

    public boolean shouldCombineTextures() {
        return true;
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
        this.method_b94c55a52cf69dd8adb6((EntityMooshroom) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public LayerMooshroomMushroom(RenderMooshroom aGdValue) {
        this.mooshroomRenderer = aGdValue;
    }
}
