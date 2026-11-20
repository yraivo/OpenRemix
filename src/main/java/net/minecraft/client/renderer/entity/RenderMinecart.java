package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderMinecart extends Render {
    protected ModelBase modelMinecart = new ModelMinecart();

    private static ResourceLocation minecartTextures;

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityMinecart) entity);
    }

    protected ResourceLocation getEntityTexture(EntityMinecart entityMinecart) {
        return net.minecraft.client.renderer.entity.RenderMinecart.minecartTextures;
    }

    public void doRender(
            EntityMinecart entityMinecart, double d0, double d1, double d2, float f, float f1) {
        GlStateManager.pushMatrix();
        this.bindEntityTexture(entityMinecart);
        long k = entityMinecart.getEntityId();

        long i = k * 493286711L;
        k = i * i;

        k *= 4392167121L;

        i = k + i * 98761L;
        k = i >> 16;

        float f2 = (((float) (k & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        k = i >> 20;

        float f3 = (((float) (k & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        k = i >> 24;

        float f4 = (((float) (k & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        GlStateManager.translate(f2, f3, f4);
        double d3 =
                entityMinecart.lastTickPosX
                        + (entityMinecart.posX - entityMinecart.lastTickPosX) * f1;
        double d4 =
                entityMinecart.lastTickPosY
                        + (entityMinecart.posY - entityMinecart.lastTickPosY) * f1;
        double d5 =
                entityMinecart.lastTickPosZ
                        + (entityMinecart.posZ - entityMinecart.lastTickPosZ) * f1;
        double d6 = 0.3F;
        Vec3 vec3 = entityMinecart.method_efda42170a89d3d2d9f9(d3, d4, d5);
        float f5 =
                entityMinecart.prevRotationPitch
                        + (entityMinecart.rotationPitch - entityMinecart.prevRotationPitch) * f1;
        if (vec3 != null) {
            Vec3 vec3Value = entityMinecart.method_450b342917e7b7196cd5(d3, d4, d5, d6);
            Vec3 vec3Value2 = entityMinecart.method_450b342917e7b7196cd5(d3, d4, d5, -d6);
            if (vec3Value == null) {
                vec3Value = vec3;
            }

            if (vec3Value2 == null) {
                vec3Value2 = vec3;
            }

            d0 += vec3.x - d3;
            d1 += (vec3Value.y + vec3Value2.y) / 2.0 - d4;
            d2 += vec3.z - d5;
            Vec3 vec3Value3 = vec3Value2.addVector(-vec3Value.x, -vec3Value.y, -vec3Value.z);
            if (vec3Value3.lengthVector() != 0.0) {
                vec3Value3 = vec3Value3.normalize();
                f = (float) (Math.atan2(vec3Value3.z, vec3Value3.x) * 180.0 / Math.PI);
                f5 = (float) (Math.atan(vec3Value3.y) * 73.0);
            }
        }

        GlStateManager.translate((float) d0, (float) d1 + 0.375F, (float) d2);
        GlStateManager.rotate(180.0F - f, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-f5, 0.0F, 0.0F, 1.0F);
        float f7 = entityMinecart.getRollingAmplitude() - f1;
        float f8 = entityMinecart.getDamage() - f1;
        if (f8 < 0.0F) {
            f8 = 0.0F;
        }

        if (f7 > 0.0F) {
            GlStateManager.rotate(
                    MathHelper.sin(f7) * f7 * f8 / 10.0F * entityMinecart.getRollingDirection(),
                    1.0F,
                    0.0F,
                    0.0F);
        }

        int j = entityMinecart.getDisplayTileOffset();
        BlockState blockState = entityMinecart.getDisplayTile();
        if (blockState.getBlock().getRenderType() != -1) {
            GlStateManager.pushMatrix();
            this.bindTexture(TextureMap.locationBlocksTexture);
            float f6 = 0.75F;
            GlStateManager.scale(f6, f6, f6);
            GlStateManager.translate(-0.5F, (j - 8) / 16.0F, 0.5F);
            this.method_483853dc58042e156d99(entityMinecart, f1, blockState);
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.bindEntityTexture(entityMinecart);
        }

        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        this.modelMinecart.render(entityMinecart, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
        super.doRender(entityMinecart, d0, d1, d2, f, f1);
    }

    public RenderMinecart(RenderManager renderManagerValue) {
        super(renderManagerValue);
        this.shadowSize = 0.5F;
    }

    static {
        net.minecraft.client.renderer.entity.RenderMinecart.minecartTextures =
                new ResourceLocation("textures/entity/minecart.png");
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityMinecart) entity, d0, d1, d2, f, f1);
    }

    protected void method_483853dc58042e156d99(
            EntityMinecart entityMinecart, float f, BlockState blockState) {
        GlStateManager.pushMatrix();
        MinecraftClient.getMinecraft()
                .getBlockRendererDispatcher()
                .renderBlockBrightness(blockState, entityMinecart.getBrightness(f));
        GlStateManager.popMatrix();
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
