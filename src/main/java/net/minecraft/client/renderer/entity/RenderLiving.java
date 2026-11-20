package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import optifine.OptiFineConfig;
import shadersmod.client.Shaders;

public abstract class RenderLiving extends RendererLivingEntity {
    public void method_7b47d2e176a589b0659a(EntityLiving entityLiving, float f) {
        int brightnessForRender = entityLiving.getBrightnessForRender(f);
        int j = brightnessForRender % 65536;
        int k = brightnessForRender / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityLiving) entity, d0, d1, d2, f, f1);
    }

    protected boolean canRenderName(EntityLiving entityLiving) {
        return super.canRenderName(entityLiving)
                && (entityLiving.getAlwaysRenderNameTagForRender()
                        || entityLiving.jvm171_d_45ffeb83d14f3c()
                                && entityLiving == this.renderManager.pointedEntity);
    }

    public void doRender(
            LivingEntity livingEntity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityLiving) livingEntity, d0, d1, d2, f, f1);
    }

    protected boolean canRenderName(Entity entity) {
        return this.canRenderName((EntityLiving) entity);
    }

    public void doRender(
            EntityLiving entityLiving, double d0, double d1, double d2, float f, float f1) {
        super.doRender(entityLiving, d0, d1, d2, f, f1);
        this.renderLeash(entityLiving, d0, d1, d2, f, f1);
    }

    public RenderLiving(RenderManager renderManagerValue, ModelBase modelBase, float p171_2) {
        super(renderManagerValue, modelBase, p171_2);
    }

    public boolean shouldRender(Entity entity, ICamera iCamera, double d0, double d1, double d2) {
        return this.method_b3e52754d52b5cba1289((EntityLiving) entity, iCamera, d0, d1, d2);
    }

    public boolean method_b3e52754d52b5cba1289(
            EntityLiving entityLiving, ICamera iCamera, double d0, double d1, double d2) {
        if (super.shouldRender(entityLiving, iCamera, d0, d1, d2)) {
            return true;
        } else {
            return entityLiving.getLeashed() && entityLiving.getLeashedToEntity() != null
                    ? iCamera.isBoundingBoxInFrustum(
                            entityLiving.getLeashedToEntity().getEntityBoundingBox())
                    : false;
        }
    }

    protected boolean canRenderName(LivingEntity livingEntity) {
        return this.canRenderName((EntityLiving) livingEntity);
    }

    protected void renderLeash(
            EntityLiving entityLiving, double d0, double d1, double d2, float f3, float f) {
        Render.getLegacyEntityRenderState();

        if (!OptiFineConfig.isShaders() || !Shaders.isShadowPass) {
            Entity entity = entityLiving.getLeashedToEntity();
            if (entity != null) {
                d1 -= (1.6 - entityLiving.height) * 0.5;
                Tessellator instance = Tessellator.getInstance();
                WorldRenderer worldRenderer = instance.getWorldRenderer();
                double d3 =
                        this.interpolateValue(
                                        entity.prevRotationYaw, entity.rotationYaw, f * 0.5F)
                                * (float) (Math.PI / 180.0);
                double d4 =
                        this.interpolateValue(
                                        entity.prevRotationPitch, entity.rotationPitch, f * 0.5F)
                                * (float) (Math.PI / 180.0);
                double d5 = Math.cos(d3);
                double d6 = Math.sin(d3);
                double d7 = Math.sin(d4);
                if (entity instanceof EntityHanging) {
                    d5 = 0.0;
                    d6 = 0.0;
                    d7 = -1.0;
                }

                double d8 = Math.cos(d4);
                double d9 =
                        this.interpolateValue(entity.prevPosX, entity.posX, f)
                                - d5 * 0.7
                                - d6 * 0.5 * d8;
                double d10 =
                        this.interpolateValue(
                                        entity.prevPosY + entity.getEyeHeight() * 0.7,
                                        entity.posY + entity.getEyeHeight() * 0.7,
                                        f)
                                - d7 * 0.5
                                - 0.25;
                double d11 =
                        this.interpolateValue(entity.prevPosZ, entity.posZ, f)
                                - d6 * 0.7
                                + d5 * 0.5 * d8;
                double d12 =
                        this.interpolateValue(
                                                entityLiving.prevRenderYawOffset, entityLiving.renderYawOffset, f)
                                        * (float) (Math.PI / 180.0)
                                + (Math.PI / 2);
                d5 = Math.cos(d12) * entityLiving.width * 0.4;
                d6 = Math.sin(d12) * entityLiving.width * 0.4;
                double d13 =
                        this.interpolateValue(
                                        entityLiving.prevPosX, entityLiving.posX, f)
                                + d5;
                double d14 =
                        this.interpolateValue(
                                entityLiving.prevPosY, entityLiving.posY, f);
                double d15 =
                        this.interpolateValue(
                                        entityLiving.prevPosZ, entityLiving.posZ, f)
                                + d6;
                d0 += d5;
                d2 += d6;
                double d16 = (float) (d9 - d13);
                double d17 = (float) (d10 - d14);
                double d18 = (float) (d11 - d15);
                GlStateManager.disableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableCull();
                if (OptiFineConfig.isShaders()) {
                    Shaders.beginLeash();
                }

                worldRenderer.startDrawing(5);
                int i = 0;

                while (i <= 24) {
                    {
                        if (i % 2 == 0) {
                            worldRenderer.setVertexColorFromFloats(0.5F, 0.4F, 0.3F, 1.0F);

                            
                        } else {

                            worldRenderer.setVertexColorFromFloats(0.35F, 0.28F, 0.21000001F, 1.0F);
                        }
                    }

                    float f1 = i / 24.0F;
                    worldRenderer.addVertex(
                            d0 + d16 * f1 + 0.0,
                            d1 + d17 * (f1 * f1 + f1) * 0.5 + ((24.0F - i) / 18.0F + 0.125F),
                            d2 + d18 * f1);
                    worldRenderer.addVertex(
                            d0 + d16 * f1 + 0.025,
                            d1
                                    + d17 * (f1 * f1 + f1) * 0.5
                                    + ((24.0F - i) / 18.0F + 0.125F)
                                    + 0.025,
                            d2 + d18 * f1);
                    i++;
                }

                instance.draw();
                worldRenderer.startDrawing(5);
                i = 0;

                while (i <= 24) {
                    {
                        if (i % 2 == 0) {
                            worldRenderer.setVertexColorFromFloats(0.5F, 0.4F, 0.3F, 1.0F);

                            
                        } else {

                            worldRenderer.setVertexColorFromFloats(0.35F, 0.28F, 0.21000001F, 1.0F);
                        }
                    }

                    float f2 = i / 24.0F;
                    worldRenderer.addVertex(
                            d0 + d16 * f2 + 0.0,
                            d1
                                    + d17 * (f2 * f2 + f2) * 0.5
                                    + ((24.0F - i) / 18.0F + 0.125F)
                                    + 0.025,
                            d2 + d18 * f2);
                    worldRenderer.addVertex(
                            d0 + d16 * f2 + 0.025,
                            d1 + d17 * (f2 * f2 + f2) * 0.5 + ((24.0F - i) / 18.0F + 0.125F),
                            d2 + d18 * f2 + 0.025);
                    i++;
                }

                instance.draw();
                if (OptiFineConfig.isShaders()) {
                    Shaders.endLeash();
                }

                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
                GlStateManager.enableCull();
            }
        }
    }

    private double interpolateValue(double p171_0, double p171_1, double p171_2) {
        return p171_0 + (p171_1 - p171_0) * p171_2;
    }
}
