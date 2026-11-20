package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class LayerSnowmanHead implements LayerRenderer {
    private RenderSnowMan snowManRenderer;

    public void doRenderLayer(
            EntitySnowman entitySnowman,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6,
            float f7) {
        if (!entitySnowman.isInvisible()) {
            GlStateManager.pushMatrix();
            this.snowManRenderer.Ad().head.postRender(0.0625F);
            float f = 0.625F;
            GlStateManager.translate(0.0F, -0.34375F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(f, -f, -f);
            MinecraftClient.getMinecraft()
                    .getItemRenderer()
                    .renderItem(
                            entitySnowman,
                            new ItemStack(Blocks.pumpkin, 1),
                            ItemCameraTransformsTransformType.HEAD);
            GlStateManager.popMatrix();
        }
    }

    public LayerSnowmanHead(RenderSnowMan aGlValue) {
        this.snowManRenderer = aGlValue;
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
        this.doRenderLayer((EntitySnowman) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
