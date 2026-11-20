package net.minecraft.client.renderer.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderFish extends Render {
    private static ResourceLocation FISH_PARTICLES;

    public void method_45c41fd69f8ed1935032(
            EntityFishHook entityFishHook, double d0, double d1, double d2, float f, float f1) {
        GlStateManager.pushMatrix();
        Render.getLegacyEntityRenderState();

        GlStateManager.translate((float) d0, (float) d1, (float) d2);
        GlStateManager.enableRescaleNormal();

        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        this.bindEntityTexture(entityFishHook);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        byte b0 = 1;
        byte b1 = 2;
        float f2 = (b0 * 8 + 0) / 128.0F;
        float f3 = (b0 * 8 + 8) / 128.0F;
        float f4 = (b1 * 8 + 0) / 128.0F;
        float f5 = (b1 * 8 + 8) / 128.0F;
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.5F;
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexNormal(0.0F, 1.0F, 0.0F);
        worldRenderer.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0, f2, f5);
        worldRenderer.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0, f3, f5);
        worldRenderer.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0, f3, f4);
        worldRenderer.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0, f2, f4);
        instance.draw();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        if (entityFishHook.angler != null) {
            float swingProgress = entityFishHook.angler.getSwingProgress(f1);
            float f10 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
            Vec3 vec3 = new Vec3(-0.36, 0.03, 0.35);
            vec3 =
                    vec3.rotatePitch(
                            -(entityFishHook.angler.prevRotationPitch
                                            + (entityFishHook.angler.rotationPitch
                                                            - entityFishHook
                                                                    .angler
                                                                    .prevRotationPitch)
                                                    * f1)
                                    * (float) Math.PI
                                    / 180.0F);
            vec3 =
                    vec3.rotateYaw(
                            -(entityFishHook.angler.prevRotationYaw
                                            + (entityFishHook.angler.rotationYaw
                                                            - entityFishHook.angler.prevRotationYaw)
                                                    * f1)
                                    * (float) Math.PI
                                    / 180.0F);
            vec3 = vec3.rotateYaw(f10 * 0.5F);
            vec3 = vec3.rotatePitch(-f10 * 0.7F);
            double d3 =
                    entityFishHook.angler.prevPosX
                            + (entityFishHook.angler.posX - entityFishHook.angler.prevPosX) * f1
                            + vec3.x;
            double d4 =
                    entityFishHook.angler.prevPosY
                            + (entityFishHook.angler.posY - entityFishHook.angler.prevPosY) * f1
                            + vec3.y;
            double d5 =
                    entityFishHook.angler.prevPosZ
                            + (entityFishHook.angler.posZ - entityFishHook.angler.prevPosZ) * f1
                            + vec3.z;
            double eyeHeight = entityFishHook.angler.getEyeHeight();
            if (this.renderManager.options != null && this.renderManager.options.thirdPersonView > 0
                    || entityFishHook.angler != MinecraftClient.getMinecraft().player) {
                float f11 =
                        (entityFishHook.angler.prevRenderYawOffset
                                        + (entityFishHook.angler.renderYawOffset
                                                        - entityFishHook.angler.prevRenderYawOffset)
                                                * f1)
                                * (float) Math.PI
                                / 180.0F;
                double d7 = MathHelper.sin(f11);
                double d9 = MathHelper.cos(f11);

                d3 =
                        entityFishHook.angler.prevPosX
                                + (entityFishHook.angler.posX - entityFishHook.angler.prevPosX) * f1
                                - d9 * 0.35
                                - d7 * 0.8;
                d4 =
                        entityFishHook.angler.prevPosY
                                + eyeHeight
                                + (entityFishHook.angler.posY - entityFishHook.angler.prevPosY) * f1
                                - 0.45;
                d5 =
                        entityFishHook.angler.prevPosZ
                                + (entityFishHook.angler.posZ - entityFishHook.angler.prevPosZ) * f1
                                - d7 * 0.35
                                + d9 * 0.8;
                eyeHeight = entityFishHook.angler.isSneaking() ? -0.1875 : 0.0;
            }

            double d16 =
                    entityFishHook.prevPosX + (entityFishHook.posX - entityFishHook.prevPosX) * f1;
            double d8 =
                    entityFishHook.prevPosY
                            + (entityFishHook.posY - entityFishHook.prevPosY) * f1
                            + 0.25;
            double d10 =
                    entityFishHook.prevPosZ + (entityFishHook.posZ - entityFishHook.prevPosZ) * f1;
            double d12 = (float) (d3 - d16);
            double d14 = (float) (d4 - d8) + eyeHeight;
            double d15 = (float) (d5 - d10);
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            worldRenderer.startDrawing(3);
            worldRenderer.setOpaqueVertexColorFromRgb(0);
            byte b2 = 16;
            int i = 0;

            while (i <= b2) {
                float f12 = (float) i / b2;
                worldRenderer.addVertex(
                        d0 + d12 * f12, d1 + d14 * (f12 * f12 + f12) * 0.5 + 0.25, d2 + d15 * f12);
                i++;
            }

            instance.draw();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            super.doRender(entityFishHook, d0, d1, d2, f, f1);
        }
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityFishHook) entity);
    }

    public RenderFish(RenderManager renderManagerValue) {
        super(renderManagerValue);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.method_45c41fd69f8ed1935032((EntityFishHook) entity, d0, d1, d2, f, f1);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.entity.RenderFish.FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");
    }

    protected ResourceLocation getEntityTexture(EntityFishHook entityFishHook) {
        return net.minecraft.client.renderer.entity.RenderFish.FISH_PARTICLES;
    }

    // region String decoding support

    // endregion
}
