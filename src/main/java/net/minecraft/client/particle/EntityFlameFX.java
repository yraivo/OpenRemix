package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlameFX extends EntityFX {
    private float flameScale;

    public float getBrightness(float f) {
        float f1 = (this.particleAge + f) / this.particleMaxAge;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        float brightness = super.getBrightness(f);
        return brightness * f1 + (1.0F - f1);
    }

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
        this.particleScale = this.flameScale * (1.0F - f6 * f6 * 0.5F);
        super.renderParticle(worldRenderer, entity, p171_2, p171_3, p171_4, p171_5, p171_6, p171_7);
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.96F;
        this.motionY *= 0.96F;
        this.motionZ *= 0.96F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }
    }

    protected EntityFlameFX(
            World worldValue,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5,
            double p171_6) {
        super(worldValue, p171_1, p171_2, p171_3, p171_4, p171_5, p171_6);
        this.motionX = this.motionX * 0.01F + p171_4;
        this.motionY = this.motionY * 0.01F + p171_5;
        this.motionZ = this.motionZ * 0.01F + p171_6;
        double d6 = p171_1 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
        d6 = p171_2 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
        d6 = p171_3 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
        this.flameScale = this.particleScale;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleMaxAge = (int) (8.0 / (java.lang.Math.random() * 0.8 + 0.2)) + 4;
        this.noClip = true;
        this.setParticleTextureIndex(48);
    }

    public int getBrightnessForRender(float f) {
        float f1 = (this.particleAge + f) / this.particleMaxAge;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        int brightnessForRender = super.getBrightnessForRender(f);
        int j = brightnessForRender & 0xFF;
        int k = brightnessForRender >> 16 & 0xFF;
        j += (int) (f1 * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }
}
