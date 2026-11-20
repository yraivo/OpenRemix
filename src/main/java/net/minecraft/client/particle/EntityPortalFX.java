package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPortalFX extends EntityFX {
    private double portalPosY;
    private double portalPosZ;
    private double portalPosX;
    private float portalParticleScale;

    public void renderParticle(
            WorldRenderer worldRenderer,
            Entity entity,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            float p171_6,
            float p171_7) {
        float f6 = (this.particleAge + p171_2) / this.particleMaxAge;
        f6 = 1.0F - f6;
        f6 *= f6;
        f6 = 1.0F - f6;
        this.particleScale = this.portalParticleScale * f6;
        super.renderParticle(worldRenderer, entity, p171_2, p171_3, p171_4, p171_5, p171_6, p171_7);
    }

    public int getBrightnessForRender(float p171_0) {
        int brightnessForRender = super.getBrightnessForRender(p171_0);
        float f1 = (float) this.particleAge / this.particleMaxAge;
        f1 *= f1;
        f1 *= f1;
        int j = brightnessForRender & 0xFF;
        int k = brightnessForRender >> 16 & 0xFF;
        k += (int) (f1 * 15.0F * 16.0F);
        if (k > 240) {
            k = 240;
        }

        return j | k << 16;
    }

    public float getBrightness(float p171_0) {
        float brightness = super.getBrightness(p171_0);
        float f2 = (float) this.particleAge / this.particleMaxAge;
        f2 = f2 * f2 * f2 * f2;
        return brightness * (1.0F - f2) + f2;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float f = (float) this.particleAge / this.particleMaxAge;
        float f1 = f;
        f = -f + f * f * 2.0F;
        f = 1.0F - f;
        this.posX = this.portalPosX + this.motionX * f;
        this.posY = this.portalPosY + this.motionY * f + (1.0F - f1);
        this.posZ = this.portalPosZ + this.motionZ * f;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }

    protected EntityPortalFX(
            World worldValue,
            double posXValue,
            double posYValue,
            double posZValue,
            double motionXValue,
            double motionYValue,
            double motionZValue) {
        super(
                worldValue,
                posXValue,
                posYValue,
                posZValue,
                motionXValue,
                motionYValue,
                motionZValue);
        this.motionX = motionXValue;
        this.motionY = motionYValue;
        this.motionZ = motionZValue;
        this.portalPosX = this.posX = posXValue;
        this.portalPosY = this.posY = posYValue;
        this.portalPosZ = this.posZ = posZValue;
        float f = this.rand.nextFloat() * 0.6F + 0.4F;
        this.portalParticleScale = this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F * f;
        this.particleGreen *= 0.3F;
        this.particleRed *= 0.9F;
        this.particleMaxAge = (int) (java.lang.Math.random() * 10.0) + 40;
        this.noClip = true;
        this.setParticleTextureIndex((int) (java.lang.Math.random() * 8.0));
    }
}
