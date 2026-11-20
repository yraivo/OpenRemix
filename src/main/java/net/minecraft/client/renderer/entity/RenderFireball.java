package net.minecraft.client.renderer.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class RenderFireball extends Render {
    private float scale;

    public void doRender(
            EntityFireball entityFireball, double d0, double d1, double d2, float f, float f1) {
        GlStateManager.pushMatrix();
        this.bindEntityTexture(entityFireball);
        GlStateManager.translate((float) d0, (float) d1, (float) d2);
        GlStateManager.enableRescaleNormal();
        float scaleValue = this.scale;
        GlStateManager.scale(scaleValue / 1.0F, scaleValue / 1.0F, scaleValue / 1.0F);
        TextureAtlasSprite particleIcon =
                MinecraftClient.getMinecraft()
                        .getRenderItem()
                        .getItemModelMesher()
                        .getParticleIcon(Items.fire_charge);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        float minU = particleIcon.getMinU();
        float maxU = particleIcon.getMaxU();
        float minV = particleIcon.getMinV();
        float maxV = particleIcon.getMaxV();
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexNormal(0.0F, 1.0F, 0.0F);
        worldRenderer.addVertexWithUV(0.0F - f8, 0.0F - f9, 0.0, minU, maxV);
        worldRenderer.addVertexWithUV(f7 - f8, 0.0F - f9, 0.0, maxU, maxV);
        worldRenderer.addVertexWithUV(f7 - f8, 1.0F - f9, 0.0, maxU, minV);
        worldRenderer.addVertexWithUV(0.0F - f8, 1.0F - f9, 0.0, minU, minV);
        instance.draw();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entityFireball, d0, d1, d2, f, f1);
    }

    protected ResourceLocation method_b4b9c25921a9532b56bd(EntityFireball entityFireball) {
        return TextureMap.locationBlocksTexture;
    }

    public RenderFireball(RenderManager renderManagerValue, float scaleValue) {
        super(renderManagerValue);
        this.scale = scaleValue;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_b4b9c25921a9532b56bd((EntityFireball) entity);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityFireball) entity, d0, d1, d2, f, f1);
    }
}
