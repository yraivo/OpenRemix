package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityPaintingEnumArt;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderPainting extends Render {

    private static ResourceLocation KRISTOFFER_PAINTING_TEXTURE;

    static {
        net.minecraft.client.renderer.entity.RenderPainting.KRISTOFFER_PAINTING_TEXTURE =
                new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
    }

    private void renderPainting(EntityPainting entityPainting, int i, int j, int k, int l) {
        Render.getLegacyEntityRenderState();

        float f = -i / 2.0F;
        float f1 = -j / 2.0F;
        float f2 = 0.5F;
        float f3 = 0.75F;
        float f4 = 0.8125F;
        float f5 = 0.0F;
        float f6 = 0.0625F;

        float f7 = 0.75F;
        float f8 = 0.8125F;
        float f9 = 0.001953125F;
        float f10 = 0.001953125F;
        float f11 = 0.7519531F;
        float f12 = 0.7519531F;
        float f13 = 0.0F;
        float f14 = 0.0625F;
        int i1 = 0;

        while (i1 < i / 16) {
            int j1 = 0;

            while (true) {
                if (j1 < j / 16) {
                    float f15 = f + (i1 + 1) * 16;
                    float f16 = f + i1 * 16;
                    float f17 = f1 + (j1 + 1) * 16;
                    float f18 = f1 + j1 * 16;
                    this.setLightmap(entityPainting, (f15 + f16) / 2.0F, (f17 + f18) / 2.0F);
                    float f19 = (k + i - i1 * 16) / 256.0F;
                    float f20 = (k + i - (i1 + 1) * 16) / 256.0F;
                    float f21 = (l + j - j1 * 16) / 256.0F;
                    float f22 = (l + j - (j1 + 1) * 16) / 256.0F;
                    Tessellator instance = Tessellator.getInstance();
                    WorldRenderer worldRenderer = instance.getWorldRenderer();
                    worldRenderer.startDrawingQuads();
                    worldRenderer.setVertexNormal(0.0F, 0.0F, -1.0F);
                    worldRenderer.addVertexWithUV(f15, f18, -f2, f20, f21);
                    worldRenderer.addVertexWithUV(f16, f18, -f2, f19, f21);
                    worldRenderer.addVertexWithUV(f16, f17, -f2, f19, f22);
                    worldRenderer.addVertexWithUV(f15, f17, -f2, f20, f22);
                    worldRenderer.setVertexNormal(0.0F, 0.0F, 1.0F);
                    worldRenderer.addVertexWithUV(f15, f17, f2, f3, f5);
                    worldRenderer.addVertexWithUV(f16, f17, f2, f4, f5);
                    worldRenderer.addVertexWithUV(f16, f18, f2, f4, f6);
                    worldRenderer.addVertexWithUV(f15, f18, f2, f3, f6);
                    worldRenderer.setVertexNormal(0.0F, 1.0F, 0.0F);
                    worldRenderer.addVertexWithUV(f15, f17, -f2, f7, f9);
                    worldRenderer.addVertexWithUV(f16, f17, -f2, f8, f9);
                    worldRenderer.addVertexWithUV(f16, f17, f2, f8, f10);
                    worldRenderer.addVertexWithUV(f15, f17, f2, f7, f10);
                    worldRenderer.setVertexNormal(0.0F, -1.0F, 0.0F);
                    worldRenderer.addVertexWithUV(f15, f18, f2, f7, f9);
                    worldRenderer.addVertexWithUV(f16, f18, f2, f8, f9);
                    worldRenderer.addVertexWithUV(f16, f18, -f2, f8, f10);
                    worldRenderer.addVertexWithUV(f15, f18, -f2, f7, f10);
                    worldRenderer.setVertexNormal(-1.0F, 0.0F, 0.0F);
                    worldRenderer.addVertexWithUV(f15, f17, f2, f12, f13);
                    worldRenderer.addVertexWithUV(f15, f18, f2, f12, f14);
                    worldRenderer.addVertexWithUV(f15, f18, -f2, f11, f14);
                    worldRenderer.addVertexWithUV(f15, f17, -f2, f11, f13);
                    worldRenderer.setVertexNormal(1.0F, 0.0F, 0.0F);
                    worldRenderer.addVertexWithUV(f16, f17, -f2, f12, f13);
                    worldRenderer.addVertexWithUV(f16, f18, -f2, f12, f14);
                    worldRenderer.addVertexWithUV(f16, f18, f2, f11, f14);
                    worldRenderer.addVertexWithUV(f16, f17, f2, f11, f13);
                    instance.draw();
                    j1++;

                    continue;
                }

                i1++;

                break;
            }
        }
    }

    public void doRender(
            EntityPainting entityPainting, double d0, double d1, double d2, float f, float f1) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(d0, d1, d2);
        GlStateManager.rotate(180.0F - f, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableRescaleNormal();
        this.bindEntityTexture(entityPainting);
        EntityPaintingEnumArt baI = entityPainting.art;
        float f2 = 0.0625F;
        GlStateManager.scale(f2, f2, f2);
        this.renderPainting(entityPainting, baI.sizeX, baI.sizeY, baI.offsetX, baI.offsetY);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entityPainting, d0, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityPainting) entity, d0, d1, d2, f, f1);
    }

    private void setLightmap(EntityPainting entityPainting, float f, float f1) {
        int i = MathHelper.floor(entityPainting.posX);
        int j = MathHelper.floor(entityPainting.posY + f1 / 16.0F);
        int k = MathHelper.floor(entityPainting.posZ);
        Facing aZG = entityPainting.facingDirection;
        if (aZG == Facing.NORTH) {
            i = MathHelper.floor(entityPainting.posX + f / 16.0F);
        }

        if (aZG == Facing.WEST) {
            k = MathHelper.floor(entityPainting.posZ - f / 16.0F);
        }

        if (aZG == Facing.SOUTH) {
            i = MathHelper.floor(entityPainting.posX - f / 16.0F);
        }

        if (aZG == Facing.EAST) {
            k = MathHelper.floor(entityPainting.posZ + f / 16.0F);
        }

        int l = this.renderManager.worldObj.getCombinedLight(new BlockPos(i, j, k), 0);
        int i1 = l % 65536;
        int j1 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i1, j1);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
    }

    protected ResourceLocation method_db62450730533f51171e(EntityPainting entityPainting) {
        return net.minecraft.client.renderer.entity.RenderPainting.KRISTOFFER_PAINTING_TEXTURE;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_db62450730533f51171e((EntityPainting) entity);
    }

    public RenderPainting(RenderManager renderManagerValue) {
        super(renderManagerValue);
    }
}
