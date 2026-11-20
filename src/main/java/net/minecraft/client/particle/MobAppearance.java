package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MobAppearance extends EntityFX {
    private LivingEntity entity;

    public int getFXLayer() {
        return 3;
    }

    public void renderParticle(
            WorldRenderer worldRenderer,
            Entity entity,
            float f,
            float f5,
            float f6,
            float f7,
            float f8,
            float f9) {
        if (this.entity != null) {
            RenderManager renderManager = MinecraftClient.getMinecraft().getRenderManager();
            renderManager.setRenderPosition(
                    EntityFX.interpPosX, EntityFX.interpPosY, EntityFX.interpPosZ);
            float f1 = 0.42553192F;
            float f2 = (this.particleAge + f) / this.particleMaxAge;
            GlStateManager.depthMask(true);
            GlStateManager.enableBlend();
            GlStateManager.enableDepth();
            GlStateManager.blendFunc(770, 771);
            float f3 = 240.0F;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f3, f3);
            GlStateManager.pushMatrix();
            float f4 = 0.05F + 0.5F * MathHelper.sin(f2 * (float) Math.PI);
            GlStateManager.color(1.0F, 1.0F, 1.0F, f4);
            GlStateManager.translate(0.0F, 1.8F, 0.0F);
            GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(60.0F - 150.0F * f2 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.4F, -1.5F);
            GlStateManager.scale(f1, f1, f1);
            this.entity.rotationYaw = this.entity.prevRotationYaw = 0.0F;
            this.entity.rotationYawHead = this.entity.prevRotationYawHead = 0.0F;
            renderManager.renderEntityWithPosYaw(this.entity, 0.0, 0.0, 0.0, 0.0F, f);
            GlStateManager.popMatrix();
            GlStateManager.enableDepth();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.entity == null) {
            net.minecraft.entity.monster.EntityGuardian aLhValue = new net.minecraft.entity.monster.EntityGuardian(this.world);
            aLhValue.setElder();
            this.entity = aLhValue;
        }
    }

    protected MobAppearance(World worldValue, double p171_1, double p171_2, double p171_3) {
        super(worldValue, p171_1, p171_2, p171_3, 0.0, 0.0, 0.0);
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.motionX = this.motionY = this.motionZ = 0.0;
        this.particleGravity = 0.0F;
        this.particleMaxAge = 30;
    }
}
