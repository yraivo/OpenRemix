package net.minecraft.client.renderer.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderTNTPrimed extends Render {
    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_0d007e7dda2eb269eef9((EntityTNTPrimed) entity);
    }

    public void doRender(
            EntityTNTPrimed entityTNTPrimed, double d0, double d1, double d2, float f, float f1) {
        BlockRendererDispatcher blockRendererDispatcher =
                MinecraftClient.getMinecraft().getBlockRendererDispatcher();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d0, (float) d1 + 0.5F, (float) d2);
        if (entityTNTPrimed.fuse - f1 + 1.0F < 10.0F) {
            float f2 = 1.0F - (entityTNTPrimed.fuse - f1 + 1.0F) / 10.0F;
            f2 = MathHelper.clamp(f2, 0.0F, 1.0F);
            f2 *= f2;
            f2 *= f2;
            float f3 = 1.0F + f2 * 0.3F;
            GlStateManager.scale(f3, f3, f3);
        }

        float f4 = (1.0F - (entityTNTPrimed.fuse - f1 + 1.0F) / 100.0F) * 0.8F;
        this.bindEntityTexture(entityTNTPrimed);
        GlStateManager.translate(-0.5F, -0.5F, 0.5F);
        blockRendererDispatcher.renderBlockBrightness(
                Blocks.tnt.getDefaultState(), entityTNTPrimed.getBrightness(f1));
        GlStateManager.translate(0.0F, 0.0F, 1.0F);
        if (entityTNTPrimed.fuse / 5 % 2 == 0) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 772);
            GlStateManager.color(1.0F, 1.0F, 1.0F, f4);
            GlStateManager.doPolygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
            GlStateManager.doPolygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
        }

        GlStateManager.popMatrix();
        super.doRender(entityTNTPrimed, d0, d1, d2, f, f1);
    }

    protected ResourceLocation method_0d007e7dda2eb269eef9(EntityTNTPrimed entityTNTPrimed) {
        return TextureMap.locationBlocksTexture;
    }

    public RenderTNTPrimed(RenderManager renderManagerValue) {
        super(renderManagerValue);
        this.shadowSize = 0.5F;
    }

    public void doRender(
            Entity entity,
            double p171_1,
            double p171_2,
            double p171_3,
            float p171_4,
            float p171_5) {
        this.doRender((net.minecraft.entity.item.EntityTNTPrimed) entity, p171_1, p171_2, p171_3, p171_4, p171_5);
    }
}
