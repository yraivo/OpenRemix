package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityEnchantmentTableParticleFX extends EntityFX {
    private double coordZ;
    private double coordX;
    private float field_70565_a;
    private double coordY;

    protected EntityEnchantmentTableParticleFX(
            World worldValue,
            double coordXValue,
            double coordYValue,
            double coordZValue,
            double motionXValue,
            double motionYValue,
            double motionZValue) {
        super(
                worldValue,
                coordXValue,
                coordYValue,
                coordZValue,
                motionXValue,
                motionYValue,
                motionZValue);
        this.motionX = motionXValue;
        this.motionY = motionYValue;
        this.motionZ = motionZValue;
        this.coordX = coordXValue;
        this.coordY = coordYValue;
        this.coordZ = coordZValue;
        this.posX = this.prevPosX = coordXValue + motionXValue;
        this.posY = this.prevPosY = coordYValue + motionYValue;
        this.posZ = this.prevPosZ = coordZValue + motionZValue;
        float f = this.rand.nextFloat() * 0.6F + 0.4F;
        this.field_70565_a = this.particleScale = this.rand.nextFloat() * 0.5F + 0.2F;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F * f;
        this.particleGreen *= 0.9F;
        this.particleRed *= 0.9F;
        this.particleMaxAge = (int) (java.lang.Math.random() * 10.0) + 30;
        this.noClip = true;
        this.setParticleTextureIndex((int) (java.lang.Math.random() * 26.0 + 1.0 + 224.0));
    }

    public float getBrightness(float p171_0) {
        float brightness = super.getBrightness(p171_0);
        float f2 = (float) this.particleAge / this.particleMaxAge;
        f2 *= f2;
        f2 *= f2;
        return brightness * (1.0F - f2) + f2;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float f = (float) this.particleAge / this.particleMaxAge;
        f = 1.0F - f;
        float f1 = 1.0F - f;
        f1 *= f1;
        f1 *= f1;
        this.posX = this.coordX + this.motionX * f;
        this.posY = this.coordY + this.motionY * f - f1 * 1.2F;
        this.posZ = this.coordZ + this.motionZ * f;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
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
}
