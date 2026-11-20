package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelGuardian;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

public class RenderGuardian extends RenderLiving {
    private static ResourceLocation GUARDIAN_BEAM_TEXTURE;

    private static ResourceLocation GUARDIAN_TEXTURE;
    private static ResourceLocation GUARDIAN_ELDER_TEXTURE;
    int kZ = ((ModelGuardian) this.mainModel).method_9b417d27949cba33a0d0();

    public void doRender(
            EntityLiving entityLiving, double d0, double d1, double d2, float f, float f1) {
        this.method_5c348f8671a055de1148((EntityGuardian) entityLiving, d0, d1, d2, f, f1);
    }

    public RenderGuardian(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelGuardian(), 0.5F);
    }

    protected void method_7fddaf807fd89fbee90f(EntityGuardian entityGuardian, float f) {
        if (entityGuardian.isElder()) {
            GlStateManager.scale(2.35F, 2.35F, 2.35F);
        }
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_bf6ac96db1fa64e8c40c((EntityGuardian) entity);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.method_5c348f8671a055de1148((EntityGuardian) entity, d0, d1, d2, f, f1);
    }

    public void method_5c348f8671a055de1148(
            EntityGuardian entityGuardian, double d0, double d1, double d2, float f, float f1) {
        if (this.kZ != ((ModelGuardian) this.mainModel).method_9b417d27949cba33a0d0()) {
            this.mainModel = new ModelGuardian();
            this.kZ = ((ModelGuardian) this.mainModel).method_9b417d27949cba33a0d0();
        }

        super.doRender(entityGuardian, d0, d1, d2, f, f1);
        LivingEntity livingEntity = entityGuardian.getTargetedEntity();
        if (livingEntity != null) {
            float f2 = entityGuardian.func_175477_p(f1);
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.bindTexture(net.minecraft.client.renderer.entity.RenderGuardian.GUARDIAN_BEAM_TEXTURE);
            GL11.glTexParameterf(3553, 10242, 10497.0F);
            GL11.glTexParameterf(3553, 10243, 10497.0F);
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            float f3 = 240.0F;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f3, f3);
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            float f4 = (float) entityGuardian.world.getTotalWorldTime() + f1;
            float f5 = f4 * 0.5F % 1.0F;
            float eyeHeight = entityGuardian.getEyeHeight();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) d0, (float) d1 + eyeHeight, (float) d2);
            Vec3 vec3 =
                    this.method_3df27db840a266ae2e2e(livingEntity, livingEntity.height * 0.5, f1);
            Vec3 vec3Value = this.method_3df27db840a266ae2e2e(entityGuardian, eyeHeight, f1);
            Vec3 vec3Value2 = vec3.subtract(vec3Value);
            double d3 = vec3Value2.lengthVector() + 1.0;
            vec3Value2 = vec3Value2.normalize();
            float f7 = (float) Math.acos(vec3Value2.y);
            float f8 = (float) Math.atan2(vec3Value2.z, vec3Value2.x);
            GlStateManager.rotate(
                    ((float) (Math.PI / 2) + -f8) * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(f7 * (180.0F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
            byte b0 = 1;
            double d4 = f4 * 0.05 * (1.0 - (b0 & 1) * 2.5);
            worldRenderer.startDrawingQuads();
            float f9 = f2 * f2;
            worldRenderer.setVertexColor(
                    64 + (int) (f9 * 240.0F),
                    32 + (int) (f9 * 192.0F),
                    128 - (int) (f9 * 64.0F),
                    255);
            double d5 = b0 * 0.2;
            double d6 = d5 * 1.41;
            double d7 = 0.0 + Math.cos(d4 + (Math.PI * 3.0 / 4.0)) * d6;
            double d8 = 0.0 + Math.sin(d4 + (Math.PI * 3.0 / 4.0)) * d6;
            double d9 = 0.0 + Math.cos(d4 + (Math.PI / 4)) * d6;
            double d10 = 0.0 + Math.sin(d4 + (Math.PI / 4)) * d6;
            double d11 = 0.0 + Math.cos(d4 + (Math.PI * 5.0 / 4.0)) * d6;
            double d12 = 0.0 + Math.sin(d4 + (Math.PI * 5.0 / 4.0)) * d6;
            double d13 = 0.0 + Math.cos(d4 + (Math.PI * 7.0 / 4.0)) * d6;
            double d14 = 0.0 + Math.sin(d4 + (Math.PI * 7.0 / 4.0)) * d6;
            double d15 = 0.0 + Math.cos(d4 + Math.PI) * d5;
            double d16 = 0.0 + Math.sin(d4 + Math.PI) * d5;
            double d17 = 0.0 + Math.cos(d4 + 0.0) * d5;
            double d18 = 0.0 + Math.sin(d4 + 0.0) * d5;
            double d19 = 0.0 + Math.cos(d4 + (Math.PI / 2)) * d5;
            double d20 = 0.0 + Math.sin(d4 + (Math.PI / 2)) * d5;
            double d21 = 0.0 + Math.cos(d4 + (Math.PI * 3.0 / 2.0)) * d5;
            double d22 = 0.0 + Math.sin(d4 + (Math.PI * 3.0 / 2.0)) * d5;
            double d23 = 0.0;
            double d24 = 0.4999;
            double d25 = -1.0F + f5;
            double d26 = d3 * (0.5 / d5) + d25;
            worldRenderer.addVertexWithUV(d15, d3, d16, d24, d26);
            worldRenderer.addVertexWithUV(d15, 0.0, d16, d24, d25);
            worldRenderer.addVertexWithUV(d17, 0.0, d18, d23, d25);
            worldRenderer.addVertexWithUV(d17, d3, d18, d23, d26);
            worldRenderer.addVertexWithUV(d19, d3, d20, d24, d26);
            worldRenderer.addVertexWithUV(d19, 0.0, d20, d24, d25);
            worldRenderer.addVertexWithUV(d21, 0.0, d22, d23, d25);
            worldRenderer.addVertexWithUV(d21, d3, d22, d23, d26);
            double d27 = 0.0;
            if (entityGuardian.ticksExisted % 2 == 0) {
                d27 = 0.5;
            }

            worldRenderer.addVertexWithUV(d7, d3, d8, 0.5, d27 + 0.5);
            worldRenderer.addVertexWithUV(d9, d3, d10, 1.0, d27 + 0.5);
            worldRenderer.addVertexWithUV(d13, d3, d14, 1.0, d27);
            worldRenderer.addVertexWithUV(d11, d3, d12, 0.5, d27);
            instance.draw();
            GlStateManager.popMatrix();
        }
    }

    // region String decoding support

    // endregion

    public boolean shouldRender(Entity entity, ICamera iCamera, double d0, double d1, double d2) {
        return this.method_76ae969b38e173433b7a((EntityGuardian) entity, iCamera, d0, d1, d2);
    }

    static {
        net.minecraft.client.renderer.entity.RenderGuardian.GUARDIAN_TEXTURE =
                new ResourceLocation("textures/entity/guardian.png");
        net.minecraft.client.renderer.entity.RenderGuardian.GUARDIAN_ELDER_TEXTURE =
                new ResourceLocation("textures/entity/guardian_elder.png");
        net.minecraft.client.renderer.entity.RenderGuardian.GUARDIAN_BEAM_TEXTURE =
                new ResourceLocation("textures/entity/guardian_beam.png");
    }

    // region String decoding support

    // endregion

    public void doRender(
            LivingEntity livingEntity, double d0, double d1, double d2, float f, float f1) {
        this.method_5c348f8671a055de1148((EntityGuardian) livingEntity, d0, d1, d2, f, f1);
    }

    protected ResourceLocation method_bf6ac96db1fa64e8c40c(EntityGuardian entityGuardian) {
        return entityGuardian.isElder()
                ? net.minecraft.client.renderer.entity.RenderGuardian.GUARDIAN_ELDER_TEXTURE
                : net.minecraft.client.renderer.entity.RenderGuardian.GUARDIAN_TEXTURE;
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.method_7fddaf807fd89fbee90f((EntityGuardian) livingEntity, f);
    }

    public boolean method_76ae969b38e173433b7a(
            EntityGuardian entityGuardian, ICamera iCamera, double d0, double d1, double d2) {
        if (super.method_b3e52754d52b5cba1289(entityGuardian, iCamera, d0, d1, d2)) {
            return true;
        }

        if (entityGuardian.hasTargetedEntity()) {
            LivingEntity livingEntity = entityGuardian.getTargetedEntity();
            if (livingEntity != null) {
                Vec3 vec3 =
                        this.method_3df27db840a266ae2e2e(
                                livingEntity, livingEntity.height * 0.5, 1.0F);
                Vec3 vec3Value =
                        this.method_3df27db840a266ae2e2e(
                                entityGuardian, entityGuardian.getEyeHeight(), 1.0F);
                if (iCamera.isBoundingBoxInFrustum(
                        BoundingBox.fromBounds(
                                vec3Value.x, vec3Value.y, vec3Value.z, vec3.x, vec3.y, vec3.z))) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean method_b3e52754d52b5cba1289(
            EntityLiving entityLiving, ICamera iCamera, double d0, double d1, double d2) {
        return this.method_76ae969b38e173433b7a((EntityGuardian) entityLiving, iCamera, d0, d1, d2);
    }

    private Vec3 method_3df27db840a266ae2e2e(LivingEntity livingEntity, double d0, float f) {
        double d1 = livingEntity.lastTickPosX + (livingEntity.posX - livingEntity.lastTickPosX) * f;
        double d2 =
                d0
                        + livingEntity.lastTickPosY
                        + (livingEntity.posY - livingEntity.lastTickPosY) * f;
        double d3 = livingEntity.lastTickPosZ + (livingEntity.posZ - livingEntity.lastTickPosZ) * f;
        return new Vec3(d1, d2, d3);
    }
}
